package com.comittedpeople.englishlearningweb.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarCategoryDTO;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarContentSummaryDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarCategory;
import com.comittedpeople.englishlearningweb.domain.DocGrammarContent;

public class DocGrammarCategoryMapperTest {

	private DocGrammarCategoryMapper categoryMapper = DocGrammarCategoryMapper.INSTANCE;
	
	@Test
	public void grammarCategoryToCategoryDTO() throws Exception{
		
		DocGrammarContent content = new DocGrammarContent();
		content.setId(1L);
		content.setTitle("Grammar Content");
		content.setDescription("Grammar Description");
		
		DocGrammarCategory docGrammarCategory = getDocGrammarCategory(1L, 
				"This Category Title", "This Category Description", Arrays.asList(content));
		
		//Khi
		DocGrammarCategoryDTO docGrammarCategoryDTO = categoryMapper.getDto(docGrammarCategory);
		
		//Thì
		assertEquals(Long.valueOf(1L), docGrammarCategoryDTO.getId());
		assertEquals("This Category Title", docGrammarCategoryDTO.getTitle());
		assertEquals("This Category Description", docGrammarCategoryDTO.getDescription());
		
		DocGrammarContentSummaryDTO summaryDTO = docGrammarCategoryDTO.getDocGrammarContentSummary().get(0);
		
		assertEquals(1, docGrammarCategoryDTO.getDocGrammarContentSummary().size());
		assertEquals(1L, summaryDTO.getGrammarID());
		assertEquals("This Category Title", summaryDTO.getGrammarTitle());
		assertEquals("This Category Description", summaryDTO.getGrammarDescription());
		
	}
	
	private DocGrammarCategory getDocGrammarCategory(Long id, String title, String description, List<DocGrammarContent> contents) {
		DocGrammarCategory result = new DocGrammarCategory();
		result.setDescription(description);
		result.setId(id);
		result.setTitle(title);
		result.setGrammars(new HashSet<DocGrammarContent>(contents));
		
		return result;
	}
	
}
