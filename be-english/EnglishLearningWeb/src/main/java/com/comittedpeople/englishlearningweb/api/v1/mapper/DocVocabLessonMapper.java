package com.comittedpeople.englishlearningweb.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabLessonDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabLesson;

@Mapper
public interface DocVocabLessonMapper {

	DocVocabLessonMapper INSTANCE = Mappers.getMapper(DocVocabLessonMapper.class);
	
	@Mapping(source = "id", target = "id")
	DocVocabLessonDTO getDto(DocVocabLesson lesson);
	
	@Mapping(source = "id", target = "id")
	DocVocabLesson getEntity(DocVocabLessonDTO lessonDTO);
	
}
