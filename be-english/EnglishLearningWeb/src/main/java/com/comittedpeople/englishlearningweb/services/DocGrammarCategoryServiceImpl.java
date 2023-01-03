package com.comittedpeople.englishlearningweb.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocGrammarCategoryMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarCategoryDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarCategory;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarCategoryRepository;

@Service
public class DocGrammarCategoryServiceImpl implements DocGrammarCategoryService{

	@Autowired
	DocGrammarCategoryMapper categoryMapper;
	
	@Autowired
	DocGrammarCategoryRepository categoryRepository;
	
	public DocGrammarCategoryServiceImpl(DocGrammarCategoryMapper categoryMapper,
			DocGrammarCategoryRepository categoryRepository) {
		this.categoryMapper = categoryMapper;
		this.categoryRepository = categoryRepository;
	}
	
	@Override
	public List<DocGrammarCategoryDTO> getDocGrammarCategoryServiceDTOs() {
		return categoryRepository.findAll()
				.stream()
				.map(categoryMapper::getDto)
				.collect(Collectors.toList());
	}

	@Override
	public DocGrammarCategoryDTO postDocGrammarCategory(DocGrammarCategoryDTO categoryDTO) {
		DocGrammarCategory category = categoryMapper.getEntity(categoryDTO);
		
		category = categoryRepository.save(category);
		
		return categoryMapper.getDto(category);
	}

	@Override
	public DocGrammarCategoryDTO patchDocGrammarCategory(Long categoryID, DocGrammarCategoryDTO categoryDTO) {
		return categoryRepository.findById(categoryID).map(category -> {
			if (categoryDTO.getDescription() != null) {
				category.setDescription(categoryDTO.getDescription());
			}
			if (categoryDTO.getTitle() != null) {
				category.setTitle(categoryDTO.getTitle());
			}
			
			DocGrammarCategoryDTO returnDTO = categoryMapper.getDto(categoryRepository.save(category));
			
			return returnDTO;
			
		}).orElseThrow(RuntimeException::new);
	}

	@Override
	public Boolean deleteDocGrammarCategory(Long id) {
		try {
			categoryRepository.deleteById(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
