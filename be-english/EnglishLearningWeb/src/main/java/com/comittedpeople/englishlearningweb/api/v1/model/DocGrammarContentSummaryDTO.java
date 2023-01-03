package com.comittedpeople.englishlearningweb.api.v1.model;

public class DocGrammarContentSummaryDTO {

	Long grammarID;
	String grammarTitle;
	String grammarDescription;
	
	public Long getGrammarID() {
		return grammarID;
	}
	public void setGrammarID(Long grammarID) {
		this.grammarID = grammarID;
	}
	public String getGrammarTitle() {
		return grammarTitle;
	}
	public void setGrammarTitle(String grammarTitle) {
		this.grammarTitle = grammarTitle;
	}
	public String getGrammarDescription() {
		return grammarDescription;
	}
	public void setGrammarDescription(String grammarDescription) {
		this.grammarDescription = grammarDescription;
	}
}
