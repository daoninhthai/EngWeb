package com.comittedpeople.englishlearningweb.api.v1.model;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.comittedpeople.englishlearningweb.domain.DocVocabLesson;

public class DocVocabContentDTO {

	private Long id;
	
	private String content;
	
	private String spelling;
	
	@Lob
	private String spellingAudioURL;
	
	@Lob
	private String description;

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

}
