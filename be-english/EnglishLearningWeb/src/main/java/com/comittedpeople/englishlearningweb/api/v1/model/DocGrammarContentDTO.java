package com.comittedpeople.englishlearningweb.api.v1.model;

import java.util.List;

public class DocGrammarContentDTO {

	private Long id;
	private String title;
	
	private Long categoryID;
	private String description;
	private List<DocGrammarFormDTO> forms;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(Long categoryID) {
		this.categoryID = categoryID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<DocGrammarFormDTO> getForms() {
		return forms;
	}
	public void setForms(List<DocGrammarFormDTO> forms) {
		this.forms = forms;
	}
}
