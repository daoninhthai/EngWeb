package com.comittedpeople.englishlearningweb.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocVocabLessonMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabLessonDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabCategory;
import com.comittedpeople.englishlearningweb.domain.DocVocabContent;
import com.comittedpeople.englishlearningweb.domain.DocVocabLesson;
import com.comittedpeople.englishlearningweb.repositories.DocVocabCategoryRepository;
import com.comittedpeople.englishlearningweb.repositories.DocVocabLessonRepository;

@Service
public class DocVocabLessonServiceImpl implements DocVocabLessonService{

	@Autowired
	private DocVocabLessonRepository repository;
	
	@Autowired
	private DocVocabCategoryRepository categoryRepository;
	
	@Autowired
	private DocVocabLessonMapper mapper;
	
	public DocVocabLessonServiceImpl(DocVocabLessonRepository repository, DocVocabCategoryRepository categoryRepository,
			DocVocabLessonMapper mapper) {
		super();
		this.repository = repository;
		this.categoryRepository = categoryRepository;
		this.mapper = mapper;
	}

	@Override
	public List<DocVocabLessonDTO> getAllVocabLessonsDTOByCategoryID(Long categoryID) {
		return repository.findByCategoryId(categoryID)
				.stream()
				.map(mapper::getDto)
				.collect(Collectors.toList());	
	}

	@Override
	public DocVocabLessonDTO postVocabLesson(Long categoryID, DocVocabLessonDTO lessonDTO) {
		DocVocabCategory category;
		try {
			category = categoryRepository.findById(categoryID).get();
		} catch (Exception e) {
			return null;
		}
		
		DocVocabLesson vocabLesson = mapper.getEntity(lessonDTO);
		
		//Cài thêm một số thông tin mặc định khi mapper sẽ không có.
		vocabLesson.setId(null);
		vocabLesson.setCategory(category);
		vocabLesson.setVocabs((Set<DocVocabContent>)null);
		
		return mapper.getDto(repository.save(vocabLesson));
	}

	@Override
	public DocVocabLessonDTO putVocabLesson(Long lessonID, DocVocabLessonDTO lessonDTO) {
		// TODO Auto-generated method stub
		DocVocabLesson fromDBLesson;
		try {
			fromDBLesson = repository.findById(lessonID).get();
		}catch (Exception e) {
			return null;
		}
		fromDBLesson.setTitle(lessonDTO.getTitle());
		fromDBLesson.setImageURL(lessonDTO.getImageURL());
		
		fromDBLesson = repository.save(fromDBLesson);
		
		return mapper.getDto(fromDBLesson);
	}

	@Override
	public Boolean deleteVocabLesson(Long lessonID) {
		
		try {
			repository.deleteById(lessonID);
		}catch (Exception e) {
			return false;
		}
		return true;
	}
}
