package com.comittedpeople.englishlearningweb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comittedpeople.englishlearningweb.domain.DocGrammarForm;

public interface DocGrammarFormRepository extends JpaRepository<DocGrammarForm, Long>{
	
	List<DocGrammarForm> findByDocGrammarContentId (Long grammarContentID);
}
