package com.comittedpeople.englishlearningweb.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.CustomEntityDirtinessStrategy.DirtyCheckContext;
import org.springframework.data.jpa.repository.Query;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import antlr.collections.List;

@Entity
@Table(name = "DocGrammarContent")
public class DocGrammarContent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;

	@Lob
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "category")
	@JsonBackReference
	private DocGrammarCategory category;
	
	@OneToMany(mappedBy = "docGrammarContent", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<DocGrammarForm> forms;

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

	public DocGrammarCategory getCategory() {
		return category;
	}

	public void setCategory(DocGrammarCategory category) {
		this.category = category;
	}

	public Set<DocGrammarForm> getForms() {
		return forms;
	}

	public void setForms(Set<DocGrammarForm> forms) {
		this.forms = forms;
	}
}
