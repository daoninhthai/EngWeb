package com.comittedpeople.englishlearningweb.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabCategoryDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabCategory;

@Service
public interface DocVocabCategoryService {

	List<DocVocabCategoryDTO> getAllVocabCategoryDTOs();
	
	DocVocabCategoryDTO postVocabCategory(DocVocabCategoryDTO dto);
	
	DocVocabCategoryDTO putVocabCategory(Long categoryID, DocVocabCategoryDTO dto);
	
	Boolean deleteVocabCategory(Long categoryID);
}
