package com.comittedpeople.englishlearningweb.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarContentSummaryDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarCategory;
import com.comittedpeople.englishlearningweb.domain.DocGrammarContent;

public class DocGrammarContentSummaryMapperTest {

	DocGrammarContentSummaryMapper mapper = DocGrammarContentSummaryMapper.INSTANCE;

	@Test
	public void testContentSummaryMapper() throws Exception {
		// Giả sử.
		DocGrammarContent content = new DocGrammarContent();
		
		content.setId(1L);
		content.setTitle("Category");
		content.setDescription("Description");
		
		DocGrammarContentSummaryDTO summaryDTO = mapper.getDto(content);
		
		assertEquals(1L, summaryDTO.getGrammarID());
		assertEquals("Category", summaryDTO.getGrammarTitle());
		assertEquals("Description", summaryDTO.getGrammarDescription());
	}

}
