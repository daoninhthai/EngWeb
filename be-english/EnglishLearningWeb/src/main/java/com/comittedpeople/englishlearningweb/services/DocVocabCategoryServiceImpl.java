	package com.comittedpeople.englishlearningweb.services;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocVocabCategoryMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabCategoryDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabCategory;
import com.comittedpeople.englishlearningweb.repositories.DocVocabCategoryRepository;

@Service
public class DocVocabCategoryServiceImpl implements DocVocabCategoryService {

	@Autowired
	private DocVocabCategoryMapper mapper;
	
	@Autowired
	private final DocVocabCategoryRepository repository;
	
	public DocVocabCategoryServiceImpl(DocVocabCategoryMapper mapper, DocVocabCategoryRepository repository) {
		this.mapper = mapper;
		this.repository = repository;
	}

	@Override
	public List<DocVocabCategoryDTO> getAllVocabCategoryDTOs() {
		return repository.findAll()
				.stream()
				.map(mapper::getDto)
				.collect(Collectors.toList());
	}

	@Override
	public DocVocabCategoryDTO postVocabCategory(DocVocabCategoryDTO dto) {
		
		DocVocabCategory vocabCategory = mapper.getEntity(dto);
		
		//Set ID bằng null để tạo mới category.
		vocabCategory.setId(null);
		
		//Lưu lại category vừa tạo.
		vocabCategory = repository.save(vocabCategory);
		
		return mapper.getDto(vocabCategory);
	}
	
	@Override
	public DocVocabCategoryDTO putVocabCategory(Long categoryID, DocVocabCategoryDTO dto) {
		
		DocVocabCategory fromDBCategory;
		try {
			fromDBCategory = repository.findById(categoryID).get();
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		fromDBCategory.setTitle(dto.getTitle());

		fromDBCategory = repository.save(fromDBCategory);
		
		return mapper.getDto(fromDBCategory);
	}

	@Override
	public Boolean deleteVocabCategory(Long categoryID) {
		// TODO Auto-generated method stub
		try {
			repository.deleteById(categoryID);
		}catch (Exception e) {
			return false;
		}
		return true;
	}
}
