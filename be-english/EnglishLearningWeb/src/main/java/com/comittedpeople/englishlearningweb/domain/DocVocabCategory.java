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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class DocVocabCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	@Lob
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "parentCategory")
	@JsonBackReference
	private DocVocabCategory parentCategory;
	
	@OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<DocVocabCategory> subCategories;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<DocVocabLesson> vocabLessons;
	
	public DocVocabCategory() {
		
	}
	
	public DocVocabCategory(Long ID) {
		id = ID;
	}
	
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

	public DocVocabCategory getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(DocVocabCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

	public Set<DocVocabCategory> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(Set<DocVocabCategory> subCategories) {
		this.subCategories = subCategories;
	}
}
