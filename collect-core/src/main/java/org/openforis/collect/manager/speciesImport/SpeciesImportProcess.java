package org.openforis.collect.manager.speciesImport;

import static org.openforis.idm.model.species.Taxon.TaxonRank.FAMILY;
import static org.openforis.idm.model.species.Taxon.TaxonRank.GENUS;
import static org.openforis.idm.model.species.Taxon.TaxonRank.SPECIES;
import static org.openforis.idm.model.species.Taxon.TaxonRank.SUBSPECIES;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openforis.collect.manager.SpeciesManager;
import org.openforis.collect.manager.process.AbstractProcess;
import org.openforis.collect.manager.speciesImport.TaxonCSVReader.TaxonCSVLine;
import org.openforis.collect.manager.speciesImport.TaxonParsingError.Type;
import org.openforis.collect.manager.speciesImport.TaxonTree.Node;
import org.openforis.idm.model.species.Taxon;
import org.openforis.idm.model.species.Taxon.TaxonRank;
import org.openforis.idm.model.species.TaxonVernacularName;
import org.openforis.idm.model.species.Taxonomy;

/**
 * 
 * @author S. Ricci
 * 
 */
public class SpeciesImportProcess extends AbstractProcess<Void, SpeciesImportStatus> {

	private static Log LOG = LogFactory.getLog(SpeciesImportProcess.class);

	private static final TaxonRank[] TAXON_RANKS = new TaxonRank[] {FAMILY, GENUS, SPECIES, SUBSPECIES};

	private static final String CSV = "csv";
	private static final String ZIP = "zip";

	private SpeciesManager speciesManager;
	private String taxonomyName;
	private File file;
	private String errorMessage;
	
	private TaxonTree taxonTree;
	private List<Long> processedLineNumbers;
	private TaxonCSVReader reader;
	private TaxonCSVLine currentLine;
	
	public SpeciesImportProcess(SpeciesManager speciesManager, String taxonomyName, File file) {
		super();
		this.speciesManager = speciesManager;
		this.taxonomyName = taxonomyName;
		this.file = file;
	}
	
	@Override
	protected void initStatus() {
		status = new SpeciesImportStatus();
	}

	@Override
	public Void call() throws Exception {
		status.start();
		prepare();
		processFile();
		if ( status.isRunning() ) {
			status.complete();
		}
		return null;
	}

	protected void prepare() {
		processedLineNumbers = new ArrayList<Long>();
		initCache();
	}

	protected void initCache() {
		taxonTree = new TaxonTree();
	}
	
	protected void processFile() throws IOException {
		String fileName = file.getName();
		String extension = FilenameUtils.getExtension(fileName);
		if ( CSV.equalsIgnoreCase(extension) ) {
			processCSV(file);
		} else if (ZIP.equals(extension) ) {
			processPackagedFile();
		} else {
			errorMessage = "File type not supported" + extension;
			LOG.error(errorMessage);
		}
	}

	protected void processPackagedFile() throws IOException {
		File unzippedCsvFile = null;
		ZipFile zipFile = new ZipFile(file);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) entries.nextElement();
			InputStream is = zipFile.getInputStream(zipEntry);
			String unzippedPath = file.getAbsolutePath() + "_unzipped.csv";
			FileOutputStream os = new FileOutputStream(unzippedPath);
			IOUtils.copy(is, os);
			unzippedCsvFile = new File(unzippedPath);
			processCSV(unzippedCsvFile);
		}
		zipFile.close();
	}

	protected void processCSV(File file) {
		for (TaxonRank rank : TAXON_RANKS) {
			if ( status.isRunning() ) {
				processCSV(file, rank);
			}
		}
		if ( status.isRunning() && ! status.hasErrors() ) {
			persistTaxa();
		} else {
			status.error();
		}
	}

	protected void processCSV(File file, TaxonRank rank) {
		InputStreamReader isReader = null;
		FileInputStream is = null;
		long currentRowNumber = 0;
		try {
			is = new FileInputStream(file);
			isReader = new InputStreamReader(is);
			
			reader = new TaxonCSVReader(isReader);
			reader.init();
			currentRowNumber = 2;
			while ( status.isRunning() && ! processedLineNumbers.contains(currentRowNumber) ) {
				try {
					currentLine = reader.readNextTaxonLine();
					if ( currentLine == null ) {
						break;
					} else {
						boolean processed = processCurrentLine(rank);
						if (processed) {
							processedLineNumbers.add(currentRowNumber);
							status.rowProcessed();
						}
					}
				} catch (TaxonParsingException e) {
					status.addError(currentRowNumber, e.getError());
				} finally {
					currentRowNumber ++;
				}
			}
		} catch (IOException e) {
			status.error();
			status.addError(currentRowNumber, new TaxonParsingError(Type.IOERROR, e.getMessage()));
			LOG.error("Error importing species CSV file", e);
		} catch (TaxonParsingException e) {
			status.addError(1, e.getError());
		} finally {
			close(isReader);
			close(is);
		}
	}
	
	protected boolean processCurrentLine(TaxonRank rank) throws TaxonParsingException {
		boolean mostSpecificRank = currentLine.getRank() == rank;
		switch (rank) {
		case FAMILY:
			createTaxonFamily();
			return mostSpecificRank;
		case GENUS:
			createTaxonGenus();
			return mostSpecificRank;
		case SPECIES:
			createTaxonSpecies();
			return mostSpecificRank;
		default:
			Taxon parent = findParentTaxon();
			if ( parent == null ) {
				return false;
			} else {
				createTaxon(rank, parent);
				return true;
			}
		}
	}

	protected void processVernacularNames(Taxon taxon) {
		List<String> languageColumnNames = reader.getLanguageColumnNames();
		for (String langCode : languageColumnNames) {
			List<String> vernacularNames = currentLine.getVernacularNames(langCode);
			for (String vernacularName : vernacularNames) {
				TaxonVernacularName taxonVN = new TaxonVernacularName();
				taxonVN.setLanguageCode(langCode);
				taxonVN.setVernacularName(StringUtils.normalizeSpace(vernacularName));
				taxonTree.addVernacularName(taxon, taxonVN);
			}
		}
	}

	protected void persistTaxa() {
		Taxonomy taxonomy = new Taxonomy();
		taxonomy.setName(taxonomyName);
		speciesManager.save(taxonomy);
		final Integer taxonomyId = taxonomy.getId();
		taxonTree.bfs(new TaxonTree.NodeVisitor() {
			@Override
			public void visit(Node node) {
				persistTaxonTreeNode(taxonomyId, node);
			}
		});
	}

	protected void persistTaxonTreeNode(Integer taxonomyId, Node node) {
		Taxon taxon = node.getTaxon();
		taxon.setTaxonomyId(taxonomyId);
		Node parent = node.getParent();
		if ( parent != null ) {
			Taxon parentTaxon = parent.getTaxon();
			taxon.setParentId(parentTaxon.getSystemId());
			taxon.setStep(9);
		}
		speciesManager.save(taxon);
		
		List<TaxonVernacularName> vernacularNames = node.getVernacularNames();
		if ( vernacularNames != null ) {
			for (TaxonVernacularName vernacularName : vernacularNames) {
				vernacularName.setTaxonSystemId(taxon.getSystemId());
				speciesManager.save(vernacularName);
			}
		}
	}

	private Taxon findParentTaxon() throws TaxonParsingException {
		TaxonRank rank = currentLine.getRank();
		TaxonRank parentRank = rank.getParent();
		String scientificName;
		switch ( parentRank ) {
		case FAMILY:
			scientificName = currentLine.getFamilyName();
			break;
		case GENUS:
			scientificName = currentLine.getGenus();
			break;
		case SPECIES:
			scientificName = currentLine.getSpeciesName();
			break;
		default:
			throw new RuntimeException("Unsupported rank");
		}
		Taxon result = taxonTree.findTaxonByScientificName(scientificName);
		return result;
	}
	
	protected Taxon createTaxonFamily() throws TaxonParsingException {
		String familyName = currentLine.getFamilyName();
		return createTaxon(FAMILY, null, familyName);
	}
	
	protected Taxon createTaxonGenus() throws TaxonParsingException {
		Taxon taxonFamily = createTaxonFamily();
		String normalizedScientificName = currentLine.getGenus();
		return createTaxon(GENUS, taxonFamily, normalizedScientificName);
	}

	protected Taxon createTaxonSpecies() throws TaxonParsingException {
		Taxon taxonGenus = createTaxonGenus();
		String normalizedScientificName = currentLine.getSpeciesName();
		return createTaxon(SPECIES, taxonGenus, normalizedScientificName);
	}
	
	protected Taxon createTaxon(TaxonRank rank, Taxon parent) throws TaxonParsingException {
		String normalizedScientificName = currentLine.getCanonicalScientificName();
		return createTaxon(rank, parent, normalizedScientificName);
	}
	
	protected Taxon createTaxon(TaxonRank rank, Taxon parent, String normalizedScientificName) throws TaxonParsingException {
		boolean mostSpecificRank = currentLine.getRank() == rank;
		Taxon taxon = taxonTree.findTaxonByScientificName(normalizedScientificName);
		boolean newTaxon = taxon == null;
		if ( newTaxon ) {
			taxon = new Taxon();
			taxon.setTaxonRank(rank);
			taxon.setScientificName(normalizedScientificName);
			taxonTree.addNode(parent, taxon);
		} else if (mostSpecificRank) {
			taxonTree.checkDuplicateScienfificName(parent, normalizedScientificName, getCurrentRowNumber());
		}
		if ( mostSpecificRank ) {
			String code = currentLine.getCode();
			Integer taxonId = currentLine.getTaxonId();
			taxonTree.checkDuplicates(taxonId, code, getCurrentRowNumber());
			taxon.setCode(code);
			taxon.setTaxonId(taxonId);
			taxonTree.index(taxon);
			processVernacularNames(taxon);
		}
		return taxon;
	}

	protected long getCurrentRowNumber() {
		return reader.getLinesRead() + 1;
	}

	private void close(InputStreamReader reader) {
		if ( reader != null ) {
			try {
				reader.close();
			} catch (IOException e) {
				LOG.error("Error closing reader", e);
			}
		}
	}
	
	private void close(InputStream is) {
		if ( is != null ) {
			try {
				is.close();
			} catch (IOException e) {
				LOG.error("Error closing reader", e);
			}
		}
	}
}
