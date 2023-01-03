package com.comittedpeople.englishlearningweb.api.v1.model;

import java.util.List;

public class DocGrammarFormDTO {

	private Long id;
	private String title;
	private String usage;
	private String useCase;
	private String how;
	
	private List<DocGrammarExampleDTO> examples;
	private List<DocGrammarNoteDTO> notes;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<DocGrammarExampleDTO> getExamples() {
		return examples;
	}
	public void setExamples(List<DocGrammarExampleDTO> examples) {
		this.examples = examples;
	}
	public String getHow() {
		return how;
	}
	public void setHow(String how) {
		this.how = how;
	}
	public List<DocGrammarNoteDTO> getNotes() {
		return notes;
	}
	public void setNotes(List<DocGrammarNoteDTO> notes) {
		this.notes = notes;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public String getUseCase() {
		return useCase;
	}
	public void setUseCase(String useCase) {
		this.useCase = useCase;
	}
}
