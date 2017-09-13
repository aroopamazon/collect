/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


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
public class OfcSurveyFile implements Serializable {

	private static final long serialVersionUID = 619344577;

	private Integer id;
	private Integer surveyId;
	private String  type;
	private String  filename;
	private byte[]  content;

	public OfcSurveyFile() {}

	public OfcSurveyFile(OfcSurveyFile value) {
		this.id = value.id;
		this.surveyId = value.surveyId;
		this.type = value.type;
		this.filename = value.filename;
		this.content = value.content;
	}

	public OfcSurveyFile(
		Integer id,
		Integer surveyId,
		String  type,
		String  filename,
		byte[]  content
	) {
		this.id = id;
		this.surveyId = surveyId;
		this.type = type;
		this.filename = filename;
		this.content = content;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSurveyId() {
		return this.surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getContent() {
		return this.content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}