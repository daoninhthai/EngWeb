package com.comittedpeople.englishlearningweb.domain;

import java.util.ArrayList;
import java.util.List;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class DocVocabLesson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	private String imageURL;
	
	private String title;
	
	@OneToMany(
			mappedBy = "lesson",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	@JsonManagedReference
	private Set<DocVocabContent> vocabs;
	
	@ManyToOne
	@JoinColumn(name = "vocabCategory")
	@JsonBackReference
	private DocVocabCategory category;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public Set<DocVocabContent> getVocabs() {
		return vocabs;
	}

	public void setVocabs(Set<DocVocabContent> vocabs) {
		this.vocabs = vocabs;
	}
	
	public void setVocabs(List<DocVocabContent> vocabs) {
		this.vocabs.clear();
		if (vocabs == null)
			return;
		for (DocVocabContent vocab : vocabs) {
			this.vocabs.add(vocab);
		}
	}

	public DocVocabCategory getCategory() {
		return category;
	}

	public void setCategory(DocVocabCategory category) {
		this.category = category;
	}

}
