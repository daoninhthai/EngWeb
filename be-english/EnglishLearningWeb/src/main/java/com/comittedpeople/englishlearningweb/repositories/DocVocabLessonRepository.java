package com.comittedpeople.englishlearningweb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comittedpeople.englishlearningweb.domain.DocVocabCategory;
import com.comittedpeople.englishlearningweb.domain.DocVocabLesson;

public interface DocVocabLessonRepository extends JpaRepository<DocVocabLesson, Long>{
	
	List<DocVocabLesson> findByCategoryId(Long categoryID);
	
}
