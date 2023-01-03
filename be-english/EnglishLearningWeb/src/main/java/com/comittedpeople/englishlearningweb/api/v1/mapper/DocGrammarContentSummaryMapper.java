package com.comittedpeople.englishlearningweb.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarContentSummaryDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarContent;

@Mapper
public interface DocGrammarContentSummaryMapper {
	
	DocGrammarContentSummaryMapper INSTANCE = Mappers.getMapper(DocGrammarContentSummaryMapper.class);
	
	@Mapping(source = "id", target = "grammarID")
	@Mapping(source = "title", target = "grammarTitle")
	@Mapping(source = "description", target = "grammarDescription")
	DocGrammarContentSummaryDTO getDto (DocGrammarContent grammarContent);
	
	@Mapping(source = "grammarID", target = "id")
	@Mapping(source = "grammarTitle", target = "title")
	@Mapping(source = "grammarDescription", target = "description")
	DocGrammarContent getEntity (DocGrammarContentSummaryDTO summaryDTO);
	
}
