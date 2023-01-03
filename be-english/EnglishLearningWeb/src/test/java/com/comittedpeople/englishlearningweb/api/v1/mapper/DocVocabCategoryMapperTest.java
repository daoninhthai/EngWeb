package com.comittedpeople.englishlearningweb.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabCategoryDTO;
import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabLessonDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabCategory;
import com.comittedpeople.englishlearningweb.domain.DocVocabLesson;

public class DocVocabCategoryMapperTest {

	private static final String NAME = "General Category";
	DocVocabCategoryMapper docVocabCategoryMapper = DocVocabCategoryMapper.INSTANCE;
	
	@Test
	public void categoryToCategoryDTO() throws Exception {
		//Giả sử
		DocVocabCategory docVocabCategory = new DocVocabCategory();
		docVocabCategory.setId(1L);
		docVocabCategory.setTitle(NAME);
		docVocabCategory.setDescription("General Category Description");
		
		//Khi
		DocVocabCategoryDTO docVocabCategoryDTO = docVocabCategoryMapper.getDto(docVocabCategory);
		
		//Thì
		assertEquals(Long.valueOf(1L), docVocabCategoryDTO.getId());
		assertEquals(NAME, docVocabCategory.getTitle());
	}
}
