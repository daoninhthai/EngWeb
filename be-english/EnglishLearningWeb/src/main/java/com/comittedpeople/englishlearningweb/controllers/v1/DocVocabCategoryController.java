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
import org.springframework.web.bind.annotation.ResponseStatus;

import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabCategoryDTO;
import com.comittedpeople.englishlearningweb.api.v1.model.DocVocabLessonDTO;
import com.comittedpeople.englishlearningweb.domain.DocVocabCategory;
import com.comittedpeople.englishlearningweb.domain.DocVocabLesson;
import com.comittedpeople.englishlearningweb.services.DocVocabCategoryService;
import com.comittedpeople.englishlearningweb.services.DocVocabLessonService;

@Controller
@RequestMapping("/api/v1/vocabCategories")
public class DocVocabCategoryController {

	private final DocVocabCategoryService docVocabCategoryService;
	private final DocVocabLessonService docVocabLessonService;

	public DocVocabCategoryController(DocVocabCategoryService docVocabCategoryService, DocVocabLessonService docVocabLessonService) {
		this.docVocabCategoryService = docVocabCategoryService;
		this.docVocabLessonService = docVocabLessonService;
	}
	
	@GetMapping
	public ResponseEntity<List<DocVocabCategoryDTO>> getAllDocVocabCategories(){
		return new ResponseEntity<List<DocVocabCategoryDTO>>(docVocabCategoryService.getAllVocabCategoryDTOs(), HttpStatus.OK);
	}
	
	//Lấy lesson của từng category với ID nhất định.
	@GetMapping(value = "{categoryID}")
	public ResponseEntity<List<DocVocabLessonDTO>> getAllDocVocabLesson(@PathVariable Long categoryID){
		return new ResponseEntity<List<DocVocabLessonDTO>> (docVocabLessonService.getAllVocabLessonsDTOByCategoryID(categoryID), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<DocVocabCategoryDTO> postDocVocabCategory(@Valid @RequestBody DocVocabCategoryDTO docVocabCategory){
		
		docVocabCategory = docVocabCategoryService.postVocabCategory(docVocabCategory);
		
		if (docVocabCategory == null)
			return new ResponseEntity<DocVocabCategoryDTO>(docVocabCategory, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<DocVocabCategoryDTO>(docVocabCategory, HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<DocVocabCategoryDTO> putDocVocabCategory(@PathVariable Long id, @Valid @RequestBody DocVocabCategoryDTO docVocabCategory){
		DocVocabCategoryDTO returnDto = docVocabCategoryService.putVocabCategory(id, docVocabCategory);
		
		if (returnDto == null)
			return new ResponseEntity<DocVocabCategoryDTO>(returnDto, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<DocVocabCategoryDTO>(returnDto, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deleteDocVocabCategory(@PathVariable Long id){
		Boolean success = docVocabCategoryService.deleteVocabCategory(id);
		if (success)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
