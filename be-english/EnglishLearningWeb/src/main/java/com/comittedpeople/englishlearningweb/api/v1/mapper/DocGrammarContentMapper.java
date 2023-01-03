package com.comittedpeople.englishlearningweb.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarContentDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarContent;

@Mapper
public interface DocGrammarContentMapper {

	DocGrammarContentMapper INSTANCE = Mappers.getMapper(DocGrammarContentMapper.class);
		
	@Mapping(source = "id", target = "id")
	@Mapping(source = "category.id", target = "categoryID")
	@Mapping(source = "forms", target = "forms")
	DocGrammarContentDTO getDto (DocGrammarContent content);
	
	@Mapping(target  = "id", source = "id")
	@Mapping(target = "category.id", source = "categoryID")
	@Mapping(target = "forms", source = "forms")
	DocGrammarContent getEntity (DocGrammarContentDTO contentDTO);
}
