package com.comittedpeople.englishlearningweb.services;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocVocabContentMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabContentDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabContent;
import com.comittedpeople.englishlearningweb.repositories.DocVocabContentRepository;
import com.comittedpeople.englishlearningweb.services.DocVocabContentService;
import com.comittedpeople.englishlearningweb.services.DocVocabContentServiceImpl;

public class DocVocabContentServiceTest {

	DocVocabContentService docVocabContentService;
	
	@Mock
	DocVocabContentRepository docVocabContentRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		//docVocabContentService = new DocVocabContentServiceImpl(DocVocabContentMapper.INSTANCE, docVocabContentRepository);
	}
	
	@Test
	public void getAllVocabByLesson() {
		
		DocVocabContent content1 = new DocVocabContent();
		
		content1.setContent("Vocab 1");
		content1.setDescription("Description 1");
		content1.setId(1L);
		content1.setLesson(null);
		content1.setSpelling("");
		content1.setSpellingAudioURL("");
		
		List<DocVocabContent> vocabContent = Arrays.asList(content1, new DocVocabContent(), new DocVocabContent());
		
		when(docVocabContentRepository.findByLessonId(anyLong())).thenReturn(vocabContent);
		
		List<DocVocabContentDTO> vocabContentDTOs = docVocabContentService.getAllVocabContentByLessonId(1L);
		
		assertEquals(3, vocabContentDTOs.size());
		assertEquals(content1.getId(), vocabContentDTOs.get(0).getId());
		assertEquals("Vocab 1", vocabContentDTOs.get(0).getContent());
	}
	
}
