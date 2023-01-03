package com.comittedpeople.englishlearningweb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comittedpeople.englishlearningweb.domain.DocVocabContent;

public interface DocVocabContentRepository extends JpaRepository<DocVocabContent, Long> {
	
	List<DocVocabContent> findByLessonId (Long lessonID);
	
}
