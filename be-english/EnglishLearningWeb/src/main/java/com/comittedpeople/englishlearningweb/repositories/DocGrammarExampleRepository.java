package com.comittedpeople.englishlearningweb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comittedpeople.englishlearningweb.domain.DocGrammarExample;

public interface DocGrammarExampleRepository extends JpaRepository<DocGrammarExample, Long> {

	List<DocGrammarExample> findByDocGrammarFormId (Long formID);
	
}
