package com.comittedpeople.englishlearningweb.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabLessonDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabContent;
import com.comittedpeople.englishlearningweb.domain.DocVocabLesson;

@Service
public interface DocVocabLessonService {
	List<DocVocabLessonDTO> getAllVocabLessonsDTOByCategoryID(Long categoryID);
	
	DocVocabLessonDTO postVocabLesson (Long categoryID, DocVocabLessonDTO lessonDTO);
	
	DocVocabLessonDTO putVocabLesson (Long lessonID, DocVocabLessonDTO lessonDTO);
	
	Boolean deleteVocabLesson (Long lessonID);
}
