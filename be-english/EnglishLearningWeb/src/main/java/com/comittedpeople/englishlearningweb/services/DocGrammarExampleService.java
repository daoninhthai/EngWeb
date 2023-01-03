package com.comittedpeople.englishlearningweb.services;

import java.util.List;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarExampleDTO;

public interface DocGrammarExampleService {
	
	List<DocGrammarExampleDTO> getDocGrammarExampleDTOsByFormID (Long formID);
	
	List<DocGrammarExampleDTO> putDocGrammarExampleDTOsByFormID (Long formID, List<DocGrammarExampleDTO> exampleDTOs);
}
