package com.comittedpeople.englishlearningweb.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabContentDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabContent;

@Mapper
public interface DocVocabContentMapper {

	DocVocabContentMapper INSTANCE = Mappers.getMapper(DocVocabContentMapper.class);
	
	@Mapping (source = "id", target = "id")
	DocVocabContentDTO getDto (DocVocabContent docVocabContent);
	
	@Mapping (source = "id", target = "id")
	DocVocabContent getEntity (DocVocabContentDTO docVocabContentDTO);
}
