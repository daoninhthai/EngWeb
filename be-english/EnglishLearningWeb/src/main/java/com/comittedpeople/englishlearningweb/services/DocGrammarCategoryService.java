package com.comittedpeople.englishlearningweb.services;

import java.util.List;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarCategoryDTO;

public interface DocGrammarCategoryService {

	List<DocGrammarCategoryDTO> getDocGrammarCategoryServiceDTOs ();
	
	DocGrammarCategoryDTO postDocGrammarCategory(DocGrammarCategoryDTO categoryDTO);
	
	DocGrammarCategoryDTO patchDocGrammarCategory(Long id, DocGrammarCategoryDTO categoryDTO);
	
	Boolean deleteDocGrammarCategory (Long id);
}
