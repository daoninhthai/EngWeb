package com.comittedpeople.englishlearningweb.api.v1.model;

public class DocGrammarExampleDTO {
	private Long id;
	private String content;
	private String imageURL;
	
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
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
}
