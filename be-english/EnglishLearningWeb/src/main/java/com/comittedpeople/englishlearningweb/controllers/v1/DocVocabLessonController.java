package com.comittedpeople.englishlearningweb.controllers.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabContentDTO;
import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabLessonDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabContent;
import com.comittedpeople.englishlearningweb.domain.DocVocabLesson;
import com.comittedpeople.englishlearningweb.services.DocVocabContentService;
import com.comittedpeople.englishlearningweb.services.DocVocabLessonService;
import com.comittedpeople.englishlearningweb.services.DocVocabLessonServiceImpl;

@Controller
public class DocVocabLessonController {
	
	private final DocVocabLessonService docVocabLessonService;
	
	private final DocVocabContentService docVocabContentService;
	
	public DocVocabLessonController (DocVocabLessonService docVocabLessonService, DocVocabContentService docVocabContentService) {
		this.docVocabLessonService = docVocabLessonService;
		this.docVocabContentService = docVocabContentService;
	}
	
	@GetMapping(value = "/api/v1/vocabLessons/{lessonID}")
	public ResponseEntity<List<DocVocabContentDTO>> getAllDocVocabContent (@PathVariable Long lessonID){
		return new ResponseEntity<List<DocVocabContentDTO>> (docVocabContentService.getAllVocabContentByLessonId(lessonID), HttpStatus.OK);
	}
	
	@PostMapping(value = "/api/v1/vocabCategories/{catID}/lessons")
	public ResponseEntity<DocVocabLessonDTO> postDocVocabLesson(@PathVariable Long catID, @Valid @RequestBody DocVocabLessonDTO lessonDTO) {
		DocVocabLessonDTO returnDTO = docVocabLessonService.postVocabLesson(catID, lessonDTO);
		
		if (returnDTO == null)
			return new ResponseEntity<DocVocabLessonDTO>(returnDTO, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<DocVocabLessonDTO>(returnDTO, HttpStatus.OK);
	}
	
	@PutMapping(value = "/api/v1/vocabLessons/{lessonID}")
	public ResponseEntity<DocVocabLessonDTO> putDocVocabLesson(@PathVariable Long lessonID, 
			@Valid @RequestBody DocVocabLessonDTO lessonDTO){
		DocVocabLessonDTO returnDTO = docVocabLessonService.putVocabLesson(lessonID, lessonDTO);
		
		if (returnDTO == null)
			return new ResponseEntity<DocVocabLessonDTO>(returnDTO, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<DocVocabLessonDTO>(returnDTO, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/api/v1/vocabLessons/{lessonID}")
	public ResponseEntity deleteDocVocabLesson(@PathVariable Long lessonID) {
		Boolean success = docVocabLessonService.deleteVocabLesson(lessonID);
		if (success)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
