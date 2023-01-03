package com.comittedpeople.englishlearningweb.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class DocGrammarForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String how;
	
	private String usage;
	
	private String useCase;
	
	@ManyToOne
	@JoinColumn(name = "docgrammar")
	@JsonBackReference
	private DocGrammarContent docGrammarContent;
	
	//Một nhiều đến các Example.
	@OneToMany(mappedBy = "docGrammarForm", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<DocGrammarExample> examples;
	
	//Một nhiêu đến các Note.
	@OneToMany(mappedBy = "docGrammarForm", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<DocGrammarNote> notes;

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

	public String getHow() {
		return how;
	}

	public void setHow(String how) {
		this.how = how;
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

	public DocGrammarContent getDocGrammarContent() {
		return docGrammarContent;
	}

	public void setDocGrammarContent(DocGrammarContent docGrammarContent) {
		this.docGrammarContent = docGrammarContent;
	}

	public Set<DocGrammarExample> getExamples() {
		return examples;
	}

	public void setExamples(Set<DocGrammarExample> examples) {
		this.examples = examples;
	}

	public Set<DocGrammarNote> getNotes() {
		return notes;
	}

	public void setNotes(Set<DocGrammarNote> notes) {
		this.notes = notes;
	}
}
