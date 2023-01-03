package com.comittedpeople.englishlearningweb.services;	

import java.util.List;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarFormDTO;

public interface DocGrammarFormService {

	List<DocGrammarFormDTO> getDocGrammarFormDTOsByGrammarContentID (Long grammarContentID);
	
	DocGrammarFormDTO getDocGrammarFormDTOByGrammarFormID (Long grammarFormID);
	
	DocGrammarFormDTO postDocGrammarFormDTOByGrammarContentID (Long grammarContentID, DocGrammarFormDTO formDTO);
	
	DocGrammarFormDTO patchDocGrammarForm (Long formID, DocGrammarFormDTO formDTO);
	
	boolean deleteDocGrammarFormByGrammarIDAndFormID(Long formID);
	
}
