/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.0.1"},
                            comments = "This class is generated by jOOQ")
public class OfcRecordRecord extends org.jooq.impl.UpdatableRecordImpl<org.openforis.collect.persistence.jooq.tables.records.OfcRecordRecord> {

	private static final long serialVersionUID = -1895865531;

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public void setId(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.ID, value);
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public java.lang.Integer getId() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.ID);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.ofc_record.root_entity_definition_id]
	 * REFERENCES ofc_schema_definition [collect.ofc_schema_definition.id]
	 * </pre></code>
	 */
	public void setRootEntityDefinitionId(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.ROOT_ENTITY_DEFINITION_ID, value);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.ofc_record.root_entity_definition_id]
	 * REFERENCES ofc_schema_definition [collect.ofc_schema_definition.id]
	 * </pre></code>
	 */
	public java.lang.Integer getRootEntityDefinitionId() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.ROOT_ENTITY_DEFINITION_ID);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.ofc_record.root_entity_definition_id]
	 * REFERENCES ofc_schema_definition [collect.ofc_schema_definition.id]
	 * </pre></code>
	 */
	public org.openforis.collect.persistence.jooq.tables.records.OfcSchemaDefinitionRecord fetchOfcSchemaDefinition() {
		return create()
			.selectFrom(org.openforis.collect.persistence.jooq.tables.OfcSchemaDefinition.OFC_SCHEMA_DEFINITION)
			.where(org.openforis.collect.persistence.jooq.tables.OfcSchemaDefinition.OFC_SCHEMA_DEFINITION.ID.equal(getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.ROOT_ENTITY_DEFINITION_ID)))
			.fetchOne();
	}

	/**
	 * An uncommented item
	 */
	public void setDateCreated(java.sql.Timestamp value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.DATE_CREATED, value);
	}

	/**
	 * An uncommented item
	 */
	public java.sql.Timestamp getDateCreated() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.DATE_CREATED);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.ofc_record.created_by_id]
	 * REFERENCES ofc_user [collect.ofc_user.id]
	 * </pre></code>
	 */
	public void setCreatedById(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.CREATED_BY_ID, value);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.ofc_record.created_by_id]
	 * REFERENCES ofc_user [collect.ofc_user.id]
	 * </pre></code>
	 */
	public java.lang.Integer getCreatedById() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.CREATED_BY_ID);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.ofc_record.created_by_id]
	 * REFERENCES ofc_user [collect.ofc_user.id]
	 * </pre></code>
	 */
	public org.openforis.collect.persistence.jooq.tables.records.OfcUserRecord fetchOfcUserByCreatedById() {
		return create()
			.selectFrom(org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER)
			.where(org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER.ID.equal(getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.CREATED_BY_ID)))
			.fetchOne();
	}

	/**
	 * An uncommented item
	 */
	public void setDateModified(java.sql.Timestamp value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.DATE_MODIFIED, value);
	}

	/**
	 * An uncommented item
	 */
	public java.sql.Timestamp getDateModified() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.DATE_MODIFIED);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.ofc_record.modified_by_id]
	 * REFERENCES ofc_user [collect.ofc_user.id]
	 * </pre></code>
	 */
	public void setModifiedById(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.MODIFIED_BY_ID, value);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.ofc_record.modified_by_id]
	 * REFERENCES ofc_user [collect.ofc_user.id]
	 * </pre></code>
	 */
	public java.lang.Integer getModifiedById() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.MODIFIED_BY_ID);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.ofc_record.modified_by_id]
	 * REFERENCES ofc_user [collect.ofc_user.id]
	 * </pre></code>
	 */
	public org.openforis.collect.persistence.jooq.tables.records.OfcUserRecord fetchOfcUserByModifiedById() {
		return create()
			.selectFrom(org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER)
			.where(org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER.ID.equal(getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.MODIFIED_BY_ID)))
			.fetchOne();
	}

	/**
	 * An uncommented item
	 */
	public void setModelVersion(java.lang.String value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.MODEL_VERSION, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.String getModelVersion() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.MODEL_VERSION);
	}

	/**
	 * An uncommented item
	 */
	public void setStep(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.STEP, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getStep() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.STEP);
	}

	/**
	 * An uncommented item
	 */
	public void setState(java.lang.String value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.STATE, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.String getState() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.STATE);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.ofc_record.locked_by_id]
	 * REFERENCES ofc_user [collect.ofc_user.id]
	 * </pre></code>
	 */
	public void setLockedById(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.LOCKED_BY_ID, value);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.ofc_record.locked_by_id]
	 * REFERENCES ofc_user [collect.ofc_user.id]
	 * </pre></code>
	 */
	public java.lang.Integer getLockedById() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.LOCKED_BY_ID);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [collect.ofc_record.locked_by_id]
	 * REFERENCES ofc_user [collect.ofc_user.id]
	 * </pre></code>
	 */
	public org.openforis.collect.persistence.jooq.tables.records.OfcUserRecord fetchOfcUserByLockedById() {
		return create()
			.selectFrom(org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER)
			.where(org.openforis.collect.persistence.jooq.tables.OfcUser.OFC_USER.ID.equal(getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.LOCKED_BY_ID)))
			.fetchOne();
	}

	/**
	 * An uncommented item
	 */
	public void setSkipped(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.SKIPPED, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getSkipped() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.SKIPPED);
	}

	/**
	 * An uncommented item
	 */
	public void setMissing(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.MISSING, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getMissing() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.MISSING);
	}

	/**
	 * An uncommented item
	 */
	public void setErrors(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.ERRORS, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getErrors() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.ERRORS);
	}

	/**
	 * An uncommented item
	 */
	public void setWarnings(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.WARNINGS, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getWarnings() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.WARNINGS);
	}

	/**
	 * An uncommented item
	 */
	public void setKey1(java.lang.String value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.KEY1, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.String getKey1() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.KEY1);
	}

	/**
	 * An uncommented item
	 */
	public void setKey2(java.lang.String value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.KEY2, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.String getKey2() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.KEY2);
	}

	/**
	 * An uncommented item
	 */
	public void setKey3(java.lang.String value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.KEY3, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.String getKey3() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.KEY3);
	}

	/**
	 * An uncommented item
	 */
	public void setCount1(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.COUNT1, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getCount1() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.COUNT1);
	}

	/**
	 * An uncommented item
	 */
	public void setCount2(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.COUNT2, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getCount2() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.COUNT2);
	}

	/**
	 * An uncommented item
	 */
	public void setCount3(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.COUNT3, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getCount3() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.COUNT3);
	}

	/**
	 * An uncommented item
	 */
	public void setCount4(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.COUNT4, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getCount4() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.COUNT4);
	}

	/**
	 * An uncommented item
	 */
	public void setCount5(java.lang.Integer value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.COUNT5, value);
	}

	/**
	 * An uncommented item
	 */
	public java.lang.Integer getCount5() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.COUNT5);
	}

	/**
	 * An uncommented item
	 */
	public void setData1(byte[] value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.DATA1, value);
	}

	/**
	 * An uncommented item
	 */
	public byte[] getData1() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.DATA1);
	}

	/**
	 * An uncommented item
	 */
	public void setData2(byte[] value) {
		setValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.DATA2, value);
	}

	/**
	 * An uncommented item
	 */
	public byte[] getData2() {
		return getValue(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD.DATA2);
	}

	/**
	 * Create a detached OfcRecordRecord
	 */
	public OfcRecordRecord() {
		super(org.openforis.collect.persistence.jooq.tables.OfcRecord.OFC_RECORD);
	}
}
