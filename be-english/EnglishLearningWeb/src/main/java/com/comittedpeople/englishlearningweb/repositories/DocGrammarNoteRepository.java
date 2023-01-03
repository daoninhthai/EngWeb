package com.comittedpeople.englishlearningweb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comittedpeople.englishlearningweb.domain.DocGrammarNote;

public interface DocGrammarNoteRepository extends JpaRepository<DocGrammarNote, Long>{

	List<DocGrammarNote> findByDocGrammarFormId (Long formID);
}
