package com.comittedpeople.englishlearningweb.services;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Matchers.anyLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocVocabCategoryMapper;
import com.comittedpeople.englishlearningweb.api.v1.mapper.DocVocabLessonMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabCategoryDTO;
import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabLessonDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabCategory;
import com.comittedpeople.englishlearningweb.domain.DocVocabLesson;
import com.comittedpeople.englishlearningweb.repositories.DocVocabCategoryRepository;
import com.comittedpeople.englishlearningweb.repositories.DocVocabLessonRepository;
import com.comittedpeople.englishlearningweb.services.DocVocabCategoryService;
import com.comittedpeople.englishlearningweb.services.DocVocabCategoryServiceImpl;
import com.comittedpeople.englishlearningweb.services.DocVocabLessonService;
import com.comittedpeople.englishlearningweb.services.DocVocabLessonServiceImpl;

public class DocVocabCategoryServiceTest {

	DocVocabCategoryService docVocabCategoryService;
	
	@Mock
	DocVocabCategoryRepository docVocabCategoryRepository;
	
	@BeforeEach
	public void setUp () throws Exception{
		MockitoAnnotations.initMocks(this);
		System.out.println("Before called");
		docVocabCategoryService = new DocVocabCategoryServiceImpl(DocVocabCategoryMapper.INSTANCE, docVocabCategoryRepository);
	}
	
	@Test
	public void getAllDocVocabCategories() throws Exception{
		
		System.out.println("Test Called");
		
		//Giả sử.
		List<DocVocabCategory> categories = Arrays.asList(new DocVocabCategory(), new DocVocabCategory(), new DocVocabCategory());
		
		when(docVocabCategoryRepository.findAll()).thenReturn(categories);
		
		List<DocVocabCategoryDTO> categoryDTOs = docVocabCategoryService.getAllVocabCategoryDTOs();
		
		assertEquals(3, categoryDTOs.size());
	}
}
