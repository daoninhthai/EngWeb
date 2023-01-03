package com.comittedpeople.englishlearningweb.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarExampleDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarExample;

public class DocGrammarExampleMapperTest {

	private DocGrammarExampleMapper mapper = DocGrammarExampleMapper.INSTANCE;
	
	@Test
	public void testGrammarExampleDTOMapper () throws Exception {
		
		//Giả sử.
		DocGrammarExample grammarExample = new DocGrammarExample();
		
		grammarExample.setContent("The man on the left ...");
		grammarExample.setId(1L);
		grammarExample.setImageURL("abc.net");
		
		//Khi
		DocGrammarExampleDTO grammarExampleDTO = mapper.getDto(grammarExample);
		
		//Thì.
		assertEquals(1L, grammarExampleDTO.getId());
		assertEquals("The man on the left ...", grammarExampleDTO.getContent());
		assertEquals("abc.net", grammarExampleDTO.getImageURL());
	}
	
}
