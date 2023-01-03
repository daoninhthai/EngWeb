package com.comittedpeople.englishlearningweb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comittedpeople.englishlearningweb.domain.DocGrammarContent;

public interface DocGrammarContentRepository extends JpaRepository<DocGrammarContent, Long>{
	
	List<DocGrammarContent> findByCategoryId (Long categoryId);
	
}
