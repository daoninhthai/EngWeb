package com.comittedpeople.englishlearningweb.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabContentDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabContent;
import com.comittedpeople.englishlearningweb.domain.DocVocabLesson;

public class DocVocabContentMapperTest {

	private static final String CONTENT = "This is content";
	private static final String DESCRIPTION = "This is description";
	private static final String PRONOUNCE = "LALALA";
	private static final String SPELLING_URL = "HOHOHO";
	
	DocVocabContentMapper mapper = DocVocabContentMapper.INSTANCE;
	
	@Test
	public void contentToContentDTO() throws Exception {
		DocVocabLesson lesson;
		DocVocabContent docVocabContent = new DocVocabContent();
		docVocabContent.setContent(CONTENT);
		docVocabContent.setDescription(DESCRIPTION);
		docVocabContent.setId(1L);
		docVocabContent.setSpelling(PRONOUNCE);
		docVocabContent.setSpellingAudioURL(SPELLING_URL);
		
		DocVocabContentDTO docVocabContentDTO = mapper.getDto(docVocabContent);
		
		assertEquals(docVocabContentDTO.getContent(), CONTENT);
		assertEquals(docVocabContentDTO.getDescription(), DESCRIPTION);
		assertEquals(docVocabContentDTO.getId(), 1L);
		assertEquals(docVocabContentDTO.getSpelling(), PRONOUNCE);
		assertEquals(docVocabContentDTO.getSpellingAudioURL(), SPELLING_URL);
	}
}
