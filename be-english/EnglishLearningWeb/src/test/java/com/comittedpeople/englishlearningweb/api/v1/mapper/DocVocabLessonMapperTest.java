package com.comittedpeople.englishlearningweb.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabLessonDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabLesson;

public class DocVocabLessonMapperTest {

	private static final String TITLE = "Lesson Title";
	private static final String IMAGE_URL = "yahoo.com";
	
	DocVocabLessonMapper docVocabLessonMapper = DocVocabLessonMapper.INSTANCE;
	
	@Test
	public void lessonToLessonDTO() throws Exception {
		DocVocabLesson docVocabLesson = new DocVocabLesson();
		docVocabLesson.setId(1L);
		docVocabLesson.setImageURL(IMAGE_URL);
		docVocabLesson.setTitle(TITLE);
		
		DocVocabLessonDTO docVocabLessonDTO = docVocabLessonMapper.getDto(docVocabLesson);
		
		assertEquals(Long.valueOf(1L), docVocabLessonDTO.getId());
		assertEquals(TITLE, docVocabLessonDTO.getTitle());
		assertEquals(IMAGE_URL, docVocabLessonDTO.getImageURL());
	}
}
