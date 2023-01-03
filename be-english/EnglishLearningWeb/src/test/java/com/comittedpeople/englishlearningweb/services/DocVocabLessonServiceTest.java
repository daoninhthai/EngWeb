package com.comittedpeople.englishlearningweb.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocVocabCategoryMapper;
import com.comittedpeople.englishlearningweb.api.v1.mapper.DocVocabLessonMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabLessonDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabLesson;
import com.comittedpeople.englishlearningweb.repositories.DocVocabLessonRepository;
import com.comittedpeople.englishlearningweb.services.DocVocabCategoryServiceImpl;
import com.comittedpeople.englishlearningweb.services.DocVocabLessonService;
import com.comittedpeople.englishlearningweb.services.DocVocabLessonServiceImpl;

public class DocVocabLessonServiceTest {
	DocVocabLessonService docVocabLessonService;
	
	@Mock
	DocVocabLessonRepository docVocabLessonRepository;
	
	@BeforeEach
	public void setUp () throws Exception{
		MockitoAnnotations.initMocks(this);
		System.out.println("Before called");
		//docVocabLessonService = new DocVocabLessonServiceImpl(DocVocabLessonMapper.INSTANCE, docVocabLessonRepository);
	}
	
	@Test
	public void getAllDocVocabLesson() throws Exception{
		
		System.out.println("Test DocVocabLesson called");
		
		//Giả sử.
		DocVocabLesson vocabLesson1 = new DocVocabLesson();
		vocabLesson1.setId(1L);
		vocabLesson1.setImageURL("https://upload.wikimedia.org/wikipedia/commons/thumb/8/8a/ContractLaw.jpg/270px-ContractLaw.jpg");
		vocabLesson1.setTitle("Lesson Title");
		
		List<DocVocabLesson> lessons = Arrays.asList(vocabLesson1, new DocVocabLesson(), new DocVocabLesson());
		
		when(docVocabLessonRepository.findByCategoryId(anyLong())).thenReturn(lessons);
		
		List<DocVocabLessonDTO> lessonDTOs = docVocabLessonService.getAllVocabLessonsDTOByCategoryID(1L);
		assertEquals(3, lessonDTOs.size());
	}
}
