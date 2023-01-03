package com.comittedpeople.englishlearningweb.controllers.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabContentDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabContent;
import com.comittedpeople.englishlearningweb.services.DocVocabContentService;

@Controller
public class DocVocabContentController {

	@Autowired
	DocVocabContentService docVocabContentService;
	
	@GetMapping("/api/v1/vocabLessons/{lessonID}/content")
	public ResponseEntity<List<DocVocabContentDTO>> getAllVocabByLesson (@PathVariable Long lessonID){
		List<DocVocabContentDTO> returnDTOs = docVocabContentService.getAllVocabContentByLessonId(lessonID);
		
		if (returnDTOs == null)
			return new ResponseEntity<List<DocVocabContentDTO>>(returnDTOs, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<List<DocVocabContentDTO>>(returnDTOs, HttpStatus.OK);
	}
	
	@PutMapping("/api/v1/vocabLessons/{lessonID}/content")
	public ResponseEntity<List<DocVocabContentDTO>> putAllVocabByLesson (@PathVariable Long lessonID, 
			@Valid @RequestBody List<DocVocabContentDTO> contentDTOs){
		List<DocVocabContentDTO> returnDTOs = docVocabContentService.putVocabContentByLessonID(lessonID, contentDTOs);
		
		if (returnDTOs == null)
			return new ResponseEntity<List<DocVocabContentDTO>>(returnDTOs, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<List<DocVocabContentDTO>>(returnDTOs, HttpStatus.OK);
	}
	
	@PostMapping("/api/v1/vocabLessons/{lessonID}/content")
	public ResponseEntity<DocVocabContentDTO> postVocabByLesson(@PathVariable Long lessonID, 
			@Valid @RequestBody DocVocabContentDTO contentDTO){
		DocVocabContentDTO returnDTO = docVocabContentService.postVocabContentByLessonID(lessonID, contentDTO);
		
		if (returnDTO == null)
			return new ResponseEntity<DocVocabContentDTO>(returnDTO, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<DocVocabContentDTO>(returnDTO, HttpStatus.OK);
	}
}
