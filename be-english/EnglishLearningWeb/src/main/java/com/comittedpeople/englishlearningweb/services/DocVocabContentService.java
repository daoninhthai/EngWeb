package com.comittedpeople.englishlearningweb.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabContentDTO;

@Service
public interface DocVocabContentService {
	
	List<DocVocabContentDTO> getAllVocabContentByLessonId(Long lessonID);
	
	List<DocVocabContentDTO> putVocabContentByLessonID(Long lessonID, List<DocVocabContentDTO> contentDTOs);
	
	DocVocabContentDTO postVocabContentByLessonID(Long lessonID, DocVocabContentDTO contentDTO);

}
