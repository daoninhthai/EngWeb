package com.comittedpeople.englishlearningweb.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarCategoryDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarCategory;

@Mapper(uses = DocGrammarContentSummaryMapper.class)
public interface DocGrammarCategoryMapper {

	DocGrammarCategoryMapper INSTANCE = Mappers.getMapper(DocGrammarCategoryMapper.class);
	
	@Mapping(source = "id", target = "id")
	@Mapping(source = "grammars", target = "docGrammarContentSummary")
	DocGrammarCategoryDTO getDto (DocGrammarCategory category);
	
	@Mapping(source = "id", target = "id")
	@Mapping(source = "docGrammarContentSummary", target = "grammars")
	DocGrammarCategory getEntity (DocGrammarCategoryDTO categoryDTO);
}
