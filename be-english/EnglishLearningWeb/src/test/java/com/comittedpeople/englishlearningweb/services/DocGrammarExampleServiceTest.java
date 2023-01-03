	package com.comittedpeople.englishlearningweb.services;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyLong;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocGrammarExampleMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarExampleDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarExample;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarExampleRepository;

public class DocGrammarExampleServiceTest {

	DocGrammarExampleService grammarExampleService;
	
	@Mock
	DocGrammarExampleRepository grammarExampleRepository;
	
	@Before
	public void setUp () throws Exception {
		MockitoAnnotations.initMocks(this);
		System.out.println("Before called !");
		//grammarExampleService = new DocGrammarExampleServiceImpl(DocGrammarExampleMapper.INSTANCE, grammarExampleRepository);
	}
	
	@Test
	public void testGrammarExampleService () {
		
		//Giả sử.
		DocGrammarExample ex1 = getGrammarExample(1L, "Example 1", "Image 1");
		DocGrammarExample ex2 = getGrammarExample(2L, "Example 2", "Image 2");
		
		List<DocGrammarExample> docGrammarExamples = Arrays.asList(ex1, ex2);
		
		when(grammarExampleRepository.findByDocGrammarFormId(anyLong())).thenReturn(docGrammarExamples);
		
		List<DocGrammarExampleDTO> dto = grammarExampleService.getDocGrammarExampleDTOsByFormID(1L);
		
		assertEquals(2, dto.size());
		
	}
	
	private DocGrammarExample getGrammarExample (Long ID, String content, String imageURL) {
		DocGrammarExample res = new DocGrammarExample();
		
		res.setId(ID);
		res.setContent(content);
		res.setImageURL(imageURL);
		
		return res;
		
	}
	
}
