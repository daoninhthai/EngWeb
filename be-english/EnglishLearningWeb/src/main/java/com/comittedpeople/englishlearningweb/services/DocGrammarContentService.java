package com.comittedpeople.englishlearningweb.services;

import java.util.List;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarContentDTO;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarContentSummaryDTO;

public interface DocGrammarContentService {
	List<DocGrammarContentDTO> getDocGrammarContentDTOsByDocGrammarCategoryID (Long categoryID);
	DocGrammarContentDTO getDocGrammarContentByID (Long grammarID);
	DocGrammarContentDTO postDocGrammarContent(DocGrammarContentDTO contentDTO);
	DocGrammarContentSummaryDTO postDocGrammarContentSummary (Long categoryID, DocGrammarContentSummaryDTO summaryDTO);
	DocGrammarContentDTO patchDocGrammarContent(Long grammarID, DocGrammarContentDTO contentDTO);
	boolean deleteDocGrammarContent(Long contentID);
}
