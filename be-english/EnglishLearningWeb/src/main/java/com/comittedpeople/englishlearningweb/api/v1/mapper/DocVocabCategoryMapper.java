package com.comittedpeople.englishlearningweb.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabCategoryDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabCategory;

@Mapper
public interface DocVocabCategoryMapper {

	DocVocabCategoryMapper INSTANCE = Mappers.getMapper(DocVocabCategoryMapper.class);
	
	@Mapping(source = "id", target = "id")
	DocVocabCategoryDTO getDto(DocVocabCategory category);
	
	@Mapping(source = "id", target = "id")
	DocVocabCategory getEntity(DocVocabCategoryDTO categoryDTO);
}
