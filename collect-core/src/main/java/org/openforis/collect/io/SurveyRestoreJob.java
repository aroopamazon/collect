/**
 * 
 */
package org.openforis.collect.io;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FilenameUtils;
import org.openforis.collect.io.internal.SurveyBackupInfoExtractorTask;
import org.openforis.collect.io.metadata.CodeListImagesImportTask;
import org.openforis.collect.io.metadata.IdmlImportTask;
import org.openforis.collect.io.metadata.IdmlUnmarshallTask;
import org.openforis.collect.io.metadata.samplingdesign.SamplingDesignImportTask;
import org.openforis.collect.io.metadata.species.SpeciesBackupImportTask;
import org.openforis.collect.manager.CodeListManager;
import org.openforis.collect.manager.SamplingDesignManager;
import org.openforis.collect.manager.SpeciesManager;
import org.openforis.collect.manager.SurveyManager;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.collect.persistence.SurveyStoreException;
import org.openforis.concurrency.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author S. Ricci
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SurveyRestoreJob extends AbstractSurveyRestoreJob {

	public static final String[] COMPLETE_BACKUP_FILE_EXTENSIONS = new String[] {
		SurveyBackupJob.OutputFormat.DESKTOP.getOutputFileExtension(),
		SurveyBackupJob.OutputFormat.DESKTOP_FULL.getOutputFileExtension()
	};

	@Autowired
	private transient SpeciesManager speciesManager;
	@Autowired
	private transient SamplingDesignManager samplingDesignManager;
	@Autowired
	private transient CodeListManager codeListManager;
	
	//output
	private SurveyBackupInfo backupInfo;

	//temporary instance variables
	private transient ZipFile zipFile;
	private BackupFileExtractor backupFileExtractor;
	
	@Override
	public void initInternal() throws Throwable {
		if ( isCompleteBackupFile() ) {
			this.zipFile = new ZipFile(file);
			this.backupFileExtractor = new BackupFileExtractor(zipFile);
			super.initInternal();
		} else {
			throw new IllegalArgumentException("File is not a valid survey backup ZIP file: " + file.getName());
		}
	}

	private boolean isCompleteBackupFile() {
		String ext = FilenameUtils.getExtension(file.getName());
		for (String allowedExt : COMPLETE_BACKUP_FILE_EXTENSIONS) {
			if ( allowedExt.equalsIgnoreCase(ext) ) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected void buildTasks() throws Throwable {
		addTask(SurveyBackupInfoExtractorTask.class);
		if ( surveyUri == null ) {
			//unmarshall xml file to get survey uri
			addTask(IdmlUnmarshallTask.class);
		}
		
		addTask(IdmlImportTask.class);
		
		addTask(CodeListImagesImportTask.class);
		
		//add sampling design task
		ZipEntry samplingDesignEntry = backupFileExtractor.findEntry(SurveyBackupJob.SAMPLING_DESIGN_ENTRY_NAME);
		if ( samplingDesignEntry != null && samplingDesignEntry.getSize() > 0 ) {
			addTask(SamplingDesignImportTask.class);
		}
		//add species import tasks
		if ( backupFileExtractor.containsEntriesInPath(SurveyBackupJob.SPECIES_FOLDER) ) {
			addSpeciesImportTasks();
		}
	}

	@Override
	protected void prepareTask(Task task) {
		if ( task instanceof SurveyBackupInfoExtractorTask ) {
			SurveyBackupInfoExtractorTask t = (SurveyBackupInfoExtractorTask) task;
			File infoFile = backupFileExtractor.extract(SurveyBackupJob.INFO_FILE_NAME);
			t.setFile(infoFile);
		} else if ( task instanceof IdmlUnmarshallTask ) {
			IdmlUnmarshallTask t = (IdmlUnmarshallTask) task;
			File idmlFile = backupFileExtractor.extractIdmlFile();
			t.setFile(idmlFile);
			t.setSurveyManager(surveyManager);
			t.setValidate(false);
		} else if ( task instanceof IdmlImportTask ) {
			IdmlImportTask t = (IdmlImportTask) task;
			t.setSurveyManager(surveyManager);
			File idmlFile = backupFileExtractor.extractIdmlFile();
			t.setFile(idmlFile);
			t.setSurveyUri(surveyUri);
			t.setSurveyName(surveyName);
			t.setImportInPublishedSurvey(restoreIntoPublishedSurvey);
			t.setValidate(validateSurvey);
		} else if (task instanceof CodeListImagesImportTask) {
			CodeListImagesImportTask t = (CodeListImagesImportTask) task;
			t.setCodeListManager(codeListManager);
			t.setZipFile(zipFile);
			t.setSurvey(survey);
		} else if ( task instanceof SamplingDesignImportTask ) {
			SamplingDesignImportTask t = (SamplingDesignImportTask) task;
			File samplingDesignFile = backupFileExtractor.extract(SurveyBackupJob.SAMPLING_DESIGN_ENTRY_NAME);
			t.setSamplingDesignManager(samplingDesignManager);
			t.setFile(samplingDesignFile);
			t.setSkipValidation(true);
			t.setOverwriteAll(true);
			t.setSurvey(survey);
		} else if ( task instanceof SpeciesBackupImportTask ) {
			SpeciesBackupImportTask t = (SpeciesBackupImportTask) task;
			t.setSpeciesManager(speciesManager);
			t.setSurvey(survey);
		}
		super.prepareTask(task);
	}
	
	@Override
	protected void onTaskCompleted(Task task) {
		super.onTaskCompleted(task);
		if ( task instanceof SurveyBackupInfoExtractorTask ) {
			SurveyBackupInfoExtractorTask t = (SurveyBackupInfoExtractorTask) task;
			this.backupInfo = t.getInfo();
		} else if ( task instanceof IdmlUnmarshallTask ) {
			CollectSurvey s = ((IdmlUnmarshallTask) task).getSurvey();
			this.surveyUri = s.getUri();
		} else if ( task instanceof IdmlImportTask ) {
			IdmlImportTask t = (IdmlImportTask) task;
			//get output survey and set it into job instance instance variable
			this.survey = t.getSurvey();
		} else if ( task instanceof SamplingDesignImportTask ) {
			saveSurvey();
		}
	}

	@SuppressWarnings("deprecation")
	private void saveSurvey() {
		try {
			if ( survey.isWork() ) {
				surveyManager.saveSurveyWork(survey);
			} else {
				surveyManager.updateModel(survey);
			}
		} catch (SurveyStoreException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void addSpeciesImportTasks() {
		List<String> speciesFilesNames = backupFileExtractor.listSpeciesEntryNames();
		for (String speciesFileName : speciesFilesNames) {
			String taxonomyName = FilenameUtils.getBaseName(speciesFileName);
			File file = backupFileExtractor.extract(speciesFileName);
			if ( file.length() > 0 ) {
				SpeciesBackupImportTask task = createTask(SpeciesBackupImportTask.class);
				task.setFile(file);
				task.setTaxonomyName(taxonomyName);
				task.setOverwriteAll(true);
				addTask(task);
			}
		}
	}
	
	@Override
	protected void onEnd() {
		super.onEnd();
		if ( zipFile != null ) {
			try {
				zipFile.close();
			} catch (IOException e) {
				log().warn("Error closing zip file", e);
			}
		}
	}

	public SurveyManager getSurveyManager() {
		return surveyManager;
	}
	
	public void setSurveyManager(SurveyManager surveyManager) {
		this.surveyManager = surveyManager;
	}
	
	public SamplingDesignManager getSamplingDesignManager() {
		return samplingDesignManager;
	}
	
	public void setSamplingDesignManager(SamplingDesignManager samplingDesignManager) {
		this.samplingDesignManager = samplingDesignManager;
	}
	
	public SpeciesManager getSpeciesManager() {
		return speciesManager;
	}
	
	public void setSpeciesManager(SpeciesManager speciesManager) {
		this.speciesManager = speciesManager;
	}
	
	public void setCodeListManager(CodeListManager codeListManager) {
		this.codeListManager = codeListManager;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}
	
	public String getSurveyUri() {
		return surveyUri;
	}
	
	public void setSurveyUri(String surveyUri) {
		this.surveyUri = surveyUri;
	}

	public boolean isRestoreIntoPublishedSurvey() {
		return restoreIntoPublishedSurvey;
	}
	
	public void setRestoreIntoPublishedSurvey(boolean restoreIntoPublishedSurvey) {
		this.restoreIntoPublishedSurvey = restoreIntoPublishedSurvey;
	}
	
	public boolean isValidateSurvey() {
		return validateSurvey;
	}
	
	public void setValidateSurvey(boolean validateSurvey) {
		this.validateSurvey = validateSurvey;
	}
	
	public CollectSurvey getSurvey() {
		return survey;
	}
	
	public SurveyBackupInfo getBackupInfo() {
		return backupInfo;
	}

}
