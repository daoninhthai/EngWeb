package com.comittedpeople.englishlearningweb.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarFormDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarForm;

@Mapper
public interface DocGrammarFormMapper {

	DocGrammarFormMapper INSTANCE = Mappers.getMapper(DocGrammarFormMapper.class);
	
	@Mapping(source = "id", target = "id")
	DocGrammarFormDTO getDto (DocGrammarForm grammarForm);
	
	@Mapping(source = "id", target = "id")
	DocGrammarForm getEntity (DocGrammarFormDTO grammarFormDTO);
}
