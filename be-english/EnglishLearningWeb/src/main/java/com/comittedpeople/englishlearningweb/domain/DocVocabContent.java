package com.comittedpeople.englishlearningweb.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class DocVocabContent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String content;
	
	private String spelling;
	
	@Lob
	private String spellingAudioURL;
	
	@Lob
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lesson")
	@JsonBackReference
	private DocVocabLesson lesson;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSpelling() {
		return spelling;
	}

	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}

	public String getSpellingAudioURL() {
		return spellingAudioURL;
	}

	public void setSpellingAudioURL(String spellingAudioURL) {
		this.spellingAudioURL = spellingAudioURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DocVocabLesson getLesson() {
		return lesson;
	}

	public void setLesson(DocVocabLesson lesson) {
		this.lesson = lesson;
	}
}
