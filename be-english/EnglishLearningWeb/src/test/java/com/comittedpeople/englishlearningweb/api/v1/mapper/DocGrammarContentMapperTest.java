package com.comittedpeople.englishlearningweb.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarContentDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarCategory;
import com.comittedpeople.englishlearningweb.domain.DocGrammarContent;

public class DocGrammarContentMapperTest {

	DocGrammarContentMapper mapper = DocGrammarContentMapper.INSTANCE;
	
	@Test
	public void testContentMapper () {
		
		//Giả sử.
		DocGrammarCategory category = new DocGrammarCategory();
		category.setId(1L);
		
		DocGrammarContent docGrammarContent = new DocGrammarContent();
		docGrammarContent.setId(1L);
		docGrammarContent.setTitle("Comparative");
		docGrammarContent.setDescription("When we compare things ...");
		docGrammarContent.setCategory(category);
		
		//Khi.
		DocGrammarContentDTO docGrammarContentDTO = mapper.getDto(docGrammarContent);
		
		//Thì.
		assertEquals(Long.valueOf(1L), docGrammarContentDTO.getId());
		assertEquals("Comparative", docGrammarContentDTO.getTitle());
		assertEquals("When we compare things ...", docGrammarContentDTO.getDescription());
		assertEquals(Long.valueOf(1L), docGrammarContentDTO.getCategoryID());
		
	}
}
