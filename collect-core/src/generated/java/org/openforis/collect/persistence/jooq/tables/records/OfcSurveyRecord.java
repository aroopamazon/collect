/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.UpdatableRecordImpl;
import org.openforis.collect.persistence.jooq.tables.OfcSurvey;


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
public class OfcSurveyRecord extends UpdatableRecordImpl<OfcSurveyRecord> implements Record12<Integer, String, String, String, String, Timestamp, Timestamp, String, Boolean, Integer, Integer, String> {

	private static final long serialVersionUID = -404475328;

	/**
	 * Setter for <code>collect.ofc_survey.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>collect.ofc_survey.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>collect.ofc_survey.name</code>.
	 */
	public void setName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>collect.ofc_survey.name</code>.
	 */
	public String getName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>collect.ofc_survey.uri</code>.
	 */
	public void setUri(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>collect.ofc_survey.uri</code>.
	 */
	public String getUri() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>collect.ofc_survey.idml</code>.
	 */
	public void setIdml(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>collect.ofc_survey.idml</code>.
	 */
	public String getIdml() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>collect.ofc_survey.target</code>.
	 */
	public void setTarget(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>collect.ofc_survey.target</code>.
	 */
	public String getTarget() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>collect.ofc_survey.date_created</code>.
	 */
	public void setDateCreated(Timestamp value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>collect.ofc_survey.date_created</code>.
	 */
	public Timestamp getDateCreated() {
		return (Timestamp) getValue(5);
	}

	/**
	 * Setter for <code>collect.ofc_survey.date_modified</code>.
	 */
	public void setDateModified(Timestamp value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>collect.ofc_survey.date_modified</code>.
	 */
	public Timestamp getDateModified() {
		return (Timestamp) getValue(6);
	}

	/**
	 * Setter for <code>collect.ofc_survey.collect_version</code>.
	 */
	public void setCollectVersion(String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>collect.ofc_survey.collect_version</code>.
	 */
	public String getCollectVersion() {
		return (String) getValue(7);
	}

	/**
	 * Setter for <code>collect.ofc_survey.temporary</code>.
	 */
	public void setTemporary(Boolean value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>collect.ofc_survey.temporary</code>.
	 */
	public Boolean getTemporary() {
		return (Boolean) getValue(8);
	}

	/**
	 * Setter for <code>collect.ofc_survey.published_id</code>.
	 */
	public void setPublishedId(Integer value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>collect.ofc_survey.published_id</code>.
	 */
	public Integer getPublishedId() {
		return (Integer) getValue(9);
	}

	/**
	 * Setter for <code>collect.ofc_survey.usergroup_id</code>.
	 */
	public void setUsergroupId(Integer value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>collect.ofc_survey.usergroup_id</code>.
	 */
	public Integer getUsergroupId() {
		return (Integer) getValue(10);
	}

	/**
	 * Setter for <code>collect.ofc_survey.availability</code>.
	 */
	public void setAvailability(String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>collect.ofc_survey.availability</code>.
	 */
	public String getAvailability() {
		return (String) getValue(11);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record12 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row12<Integer, String, String, String, String, Timestamp, Timestamp, String, Boolean, Integer, Integer, String> fieldsRow() {
		return (Row12) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row12<Integer, String, String, String, String, Timestamp, Timestamp, String, Boolean, Integer, Integer, String> valuesRow() {
		return (Row12) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return OfcSurvey.OFC_SURVEY.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return OfcSurvey.OFC_SURVEY.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return OfcSurvey.OFC_SURVEY.URI;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return OfcSurvey.OFC_SURVEY.IDML;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return OfcSurvey.OFC_SURVEY.TARGET;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field6() {
		return OfcSurvey.OFC_SURVEY.DATE_CREATED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field7() {
		return OfcSurvey.OFC_SURVEY.DATE_MODIFIED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field8() {
		return OfcSurvey.OFC_SURVEY.COLLECT_VERSION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Boolean> field9() {
		return OfcSurvey.OFC_SURVEY.TEMPORARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field10() {
		return OfcSurvey.OFC_SURVEY.PUBLISHED_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field11() {
		return OfcSurvey.OFC_SURVEY.USERGROUP_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field12() {
		return OfcSurvey.OFC_SURVEY.AVAILABILITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getUri();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getIdml();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getTarget();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value6() {
		return getDateCreated();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value7() {
		return getDateModified();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value8() {
		return getCollectVersion();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean value9() {
		return getTemporary();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value10() {
		return getPublishedId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value11() {
		return getUsergroupId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value12() {
		return getAvailability();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcSurveyRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcSurveyRecord value2(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcSurveyRecord value3(String value) {
		setUri(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcSurveyRecord value4(String value) {
		setIdml(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcSurveyRecord value5(String value) {
		setTarget(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcSurveyRecord value6(Timestamp value) {
		setDateCreated(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcSurveyRecord value7(Timestamp value) {
		setDateModified(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcSurveyRecord value8(String value) {
		setCollectVersion(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcSurveyRecord value9(Boolean value) {
		setTemporary(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcSurveyRecord value10(Integer value) {
		setPublishedId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcSurveyRecord value11(Integer value) {
		setUsergroupId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcSurveyRecord value12(String value) {
		setAvailability(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfcSurveyRecord values(Integer value1, String value2, String value3, String value4, String value5, Timestamp value6, Timestamp value7, String value8, Boolean value9, Integer value10, Integer value11, String value12) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		value10(value10);
		value11(value11);
		value12(value12);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached OfcSurveyRecord
	 */
	public OfcSurveyRecord() {
		super(OfcSurvey.OFC_SURVEY);
	}

	/**
	 * Create a detached, initialised OfcSurveyRecord
	 */
	public OfcSurveyRecord(Integer id, String name, String uri, String idml, String target, Timestamp dateCreated, Timestamp dateModified, String collectVersion, Boolean temporary, Integer publishedId, Integer usergroupId, String availability) {
		super(OfcSurvey.OFC_SURVEY);

		setValue(0, id);
		setValue(1, name);
		setValue(2, uri);
		setValue(3, idml);
		setValue(4, target);
		setValue(5, dateCreated);
		setValue(6, dateModified);
		setValue(7, collectVersion);
		setValue(8, temporary);
		setValue(9, publishedId);
		setValue(10, usergroupId);
		setValue(11, availability);
	}
}
