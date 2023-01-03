package com.comittedpeople.englishlearningweb.api.v1.model;

import java.util.List;

public class DocGrammarCategoryDTO {

	private Long id;
	private String title;
	private String description;
	private List<DocGrammarContentSummaryDTO> docGrammarContentSummary;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<DocGrammarContentSummaryDTO> getDocGrammarContentSummary() {
		return docGrammarContentSummary;
	}
	public void setDocGrammarContentSummary(List<DocGrammarContentSummaryDTO> docGrammarContentSummary) {
		this.docGrammarContentSummary = docGrammarContentSummary;
	}
}
