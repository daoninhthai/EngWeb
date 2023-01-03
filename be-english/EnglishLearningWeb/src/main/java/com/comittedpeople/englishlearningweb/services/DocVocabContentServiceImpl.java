package com.comittedpeople.englishlearningweb.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocVocabContentMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabContentDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabContent;
import com.comittedpeople.englishlearningweb.domain.DocVocabLesson;
import com.comittedpeople.englishlearningweb.repositories.DocVocabContentRepository;
import com.comittedpeople.englishlearningweb.repositories.DocVocabLessonRepository;

@Service
public class DocVocabContentServiceImpl implements DocVocabContentService{

	@Autowired
	private DocVocabContentMapper mapper;
	
	@Autowired
	private final DocVocabContentRepository repository;
	
	@Autowired
	private final DocVocabLessonRepository lessonRepository;
	
	public DocVocabContentServiceImpl(DocVocabContentMapper mapper, DocVocabContentRepository repository,
			DocVocabLessonRepository lessonRepository) {
		super();
		this.mapper = mapper;
		this.repository = repository;
		this.lessonRepository = lessonRepository;
	}

	@Override
	public List<DocVocabContentDTO> getAllVocabContentByLessonId(Long lessonID) {
		return repository.findByLessonId(lessonID)
				.stream()
				.map(mapper::getDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<DocVocabContentDTO> putVocabContentByLessonID(Long lessonID, List<DocVocabContentDTO> contentDTOs) {
		
		DocVocabLesson lesson;
		try {
			lesson = lessonRepository.findById(lessonID).get();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		List<DocVocabContent> contents = contentDTOs.stream().map(mapper::getEntity).collect(Collectors.toList());
		
		//Load lại dữ liệu hiện tại đang có trong DB vào cho các contents để tiến hành update.
		List<DocVocabContent> lessonContents = repository.findByLessonId(lessonID);
		
		for (DocVocabContent content : contents) {
			content.setLesson(lesson);
		}
		
		
		
		//Lưu lại hết tất cả Vocab Content xuống database.
		contents = repository.saveAll(contents);
		
		System.out.println("Before error");
		
		//Lưu lại cả lesson chứa vocab content nữa.
		lesson.setVocabs(contents);
		lessonRepository.save(lesson);
		
		
		System.out.println("After Error");
		
		//Xoá đi những content không có trong input của user.
		HashMap<Long, Boolean> contentExistInRequest = new HashMap<>();
		
		for (DocVocabContent content : contents) {
			contentExistInRequest.put(content.getId(), true);
		}
		
		List<DocVocabContent> toBeDeleted = new ArrayList<DocVocabContent>();
		
		for (DocVocabContent content : lessonContents) {
			Boolean idExistInUserRequest = contentExistInRequest.get(content.getId());
			
			//Không tồn tại trong request của User thì ta xoá đi.
			if (idExistInUserRequest == null || idExistInUserRequest == false) {
				toBeDeleted.add(content);
			}
		}
		
		repository.deleteAll(toBeDeleted);
		
		//Trả về kết quả cuối cùng sau quá trình xử lý.
		return contents.stream().map(mapper::getDto).collect(Collectors.toList());
	}

	@Override
	public DocVocabContentDTO postVocabContentByLessonID(Long lessonID, DocVocabContentDTO contentDTO) {
		
		DocVocabLesson docVocabLesson;
		try {
			docVocabLesson = lessonRepository.findById(lessonID).get();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		DocVocabContent vocabContent = mapper.getEntity(contentDTO);
		vocabContent.setId(null);
		vocabContent.setLesson(docVocabLesson);
		
		docVocabLesson.getVocabs().add(vocabContent);
		
		vocabContent = repository.save(vocabContent);
		lessonRepository.save(docVocabLesson);
		return mapper.getDto(vocabContent);
	}
}
