package com.comittedpeople.englishlearningweb.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.comittedpeople.englishlearningweb.domain.DocVocabCategory;

public interface DocVocabCategoryRepository extends JpaRepository<DocVocabCategory, Long>{
}
