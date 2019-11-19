/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables.records;


import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
import org.openforis.collect.persistence.jooq.tables.OfcCodeList;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OfcCodeListRecord extends UpdatableRecordImpl<OfcCodeListRecord> {

	private static final long serialVersionUID = -1508810620;

	/**
	 * Setter for <code>collect.ofc_code_list.id</code>.
	 */
	public void setId(Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.id</code>.
	 */
	public Long getId() {
		return (Long) getValue(0);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.survey_id</code>.
	 */
	public void setSurveyId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.survey_id</code>.
	 */
	public Integer getSurveyId() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.code_list_id</code>.
	 */
	public void setCodeListId(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.code_list_id</code>.
	 */
	public Integer getCodeListId() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.item_id</code>.
	 */
	public void setItemId(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.item_id</code>.
	 */
	public Integer getItemId() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.parent_id</code>.
	 */
	public void setParentId(Long value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.parent_id</code>.
	 */
	public Long getParentId() {
		return (Long) getValue(4);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.sort_order</code>.
	 */
	public void setSortOrder(Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.sort_order</code>.
	 */
	public Integer getSortOrder() {
		return (Integer) getValue(5);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.code</code>.
	 */
	public void setCode(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.code</code>.
	 */
	public String getCode() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.qualifiable</code>.
	 */
	public void setQualifiable(Boolean value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.qualifiable</code>.
	 */
	public Boolean getQualifiable() {
		return (Boolean) getValue(7);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.since_version_id</code>.
	 */
	public void setSinceVersionId(Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.since_version_id</code>.
	 */
	public Integer getSinceVersionId() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.deprecated_version_id</code>.
	 */
	public void setDeprecatedVersionId(Integer value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.deprecated_version_id</code>.
	 */
	public Integer getDeprecatedVersionId() {
		return (Integer) getValue(9);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.label1</code>.
	 */
	public void setLabel1(String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.label1</code>.
	 */
	public String getLabel1() {
		return (String) getValue(10);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.label2</code>.
	 */
	public void setLabel2(String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.label2</code>.
	 */
	public String getLabel2() {
		return (String) getValue(11);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.label3</code>.
	 */
	public void setLabel3(String value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.label3</code>.
	 */
	public String getLabel3() {
		return (String) getValue(12);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.description1</code>.
	 */
	public void setDescription1(String value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.description1</code>.
	 */
	public String getDescription1() {
		return (String) getValue(13);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.description2</code>.
	 */
	public void setDescription2(String value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.description2</code>.
	 */
	public String getDescription2() {
		return (String) getValue(14);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.description3</code>.
	 */
	public void setDescription3(String value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.description3</code>.
	 */
	public String getDescription3() {
		return (String) getValue(15);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.level</code>.
	 */
	public void setLevel(Integer value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.level</code>.
	 */
	public Integer getLevel() {
		return (Integer) getValue(16);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.image_content</code>.
	 */
	public void setImageContent(byte[] value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.image_content</code>.
	 */
	public byte[] getImageContent() {
		return (byte[]) getValue(17);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.image_file_name</code>.
	 */
	public void setImageFileName(String value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.image_file_name</code>.
	 */
	public String getImageFileName() {
		return (String) getValue(18);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.label4</code>.
	 */
	public void setLabel4(String value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.label4</code>.
	 */
	public String getLabel4() {
		return (String) getValue(19);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.label5</code>.
	 */
	public void setLabel5(String value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.label5</code>.
	 */
	public String getLabel5() {
		return (String) getValue(20);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.description4</code>.
	 */
	public void setDescription4(String value) {
		setValue(21, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.description4</code>.
	 */
	public String getDescription4() {
		return (String) getValue(21);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.description5</code>.
	 */
	public void setDescription5(String value) {
		setValue(22, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.description5</code>.
	 */
	public String getDescription5() {
		return (String) getValue(22);
	}

	/**
	 * Setter for <code>collect.ofc_code_list.color</code>.
	 */
	public void setColor(String value) {
		setValue(23, value);
	}

	/**
	 * Getter for <code>collect.ofc_code_list.color</code>.
	 */
	public String getColor() {
		return (String) getValue(23);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Long> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached OfcCodeListRecord
	 */
	public OfcCodeListRecord() {
		super(OfcCodeList.OFC_CODE_LIST);
	}

	/**
	 * Create a detached, initialised OfcCodeListRecord
	 */
	public OfcCodeListRecord(Long id, Integer surveyId, Integer codeListId, Integer itemId, Long parentId, Integer sortOrder, String code, Boolean qualifiable, Integer sinceVersionId, Integer deprecatedVersionId, String label1, String label2, String label3, String description1, String description2, String description3, Integer level, byte[] imageContent, String imageFileName, String label4, String label5, String description4, String description5, String color) {
		super(OfcCodeList.OFC_CODE_LIST);

		setValue(0, id);
		setValue(1, surveyId);
		setValue(2, codeListId);
		setValue(3, itemId);
		setValue(4, parentId);
		setValue(5, sortOrder);
		setValue(6, code);
		setValue(7, qualifiable);
		setValue(8, sinceVersionId);
		setValue(9, deprecatedVersionId);
		setValue(10, label1);
		setValue(11, label2);
		setValue(12, label3);
		setValue(13, description1);
		setValue(14, description2);
		setValue(15, description3);
		setValue(16, level);
		setValue(17, imageContent);
		setValue(18, imageFileName);
		setValue(19, label4);
		setValue(20, label5);
		setValue(21, description4);
		setValue(22, description5);
		setValue(23, color);
	}
}
