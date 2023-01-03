package com.comittedpeople.englishlearningweb.controllers.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarCategoryDTO;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarContentDTO;
import com.comittedpeople.englishlearningweb.services.DocGrammarCategoryService;
import com.comittedpeople.englishlearningweb.services.DocGrammarContentService;

@Controller
@RequestMapping("/api/v1/grammarCategories")
public class DocGrammarCategoryController {

	private final DocGrammarCategoryService docGrammarCategoryService;
	private final DocGrammarContentService docGrammarContentService;

	public DocGrammarCategoryController(DocGrammarCategoryService docGrammarCategoryService,
			DocGrammarContentService docGrammarContentService) {
		this.docGrammarCategoryService = docGrammarCategoryService;
		this.docGrammarContentService = docGrammarContentService;
	}
	
	@GetMapping
	public ResponseEntity<List<DocGrammarCategoryDTO>> getAllGrammarCategories () {
		return new ResponseEntity<List<DocGrammarCategoryDTO>>(docGrammarCategoryService.getDocGrammarCategoryServiceDTOs(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<DocGrammarCategoryDTO> postGrammarCategory(@Valid @RequestBody DocGrammarCategoryDTO categoryDTO){
		DocGrammarCategoryDTO returnDTO = docGrammarCategoryService.postDocGrammarCategory(categoryDTO);
		if (returnDTO == null)
			return new ResponseEntity<DocGrammarCategoryDTO>(returnDTO, HttpStatus.FORBIDDEN);
		else
			return new ResponseEntity<DocGrammarCategoryDTO>(returnDTO, HttpStatus.OK);
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<DocGrammarCategoryDTO> putGrammarCategory(@PathVariable Long id, @Valid @RequestBody DocGrammarCategoryDTO categoryDTO){
		DocGrammarCategoryDTO returnDTO = docGrammarCategoryService.patchDocGrammarCategory(id, categoryDTO);
		if (returnDTO == null)
			return new ResponseEntity<DocGrammarCategoryDTO>(returnDTO, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<DocGrammarCategoryDTO>(returnDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deleteGrammarCategory (@PathVariable Long id) {
		Boolean success = docGrammarCategoryService.deleteDocGrammarCategory(id);
		
		if (success)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	//Tạm thời chưa cần phần này.
//	
//	@GetMapping(value = "{categoryID}")
//	public ResponseEntity<List<DocGrammarContentDTO>> getAllGrammarContent (@PathVariable Long categoryID){
//		return new ResponseEntity<List<DocGrammarContentDTO>> 
//		(docGrammarContentService.getDocGrammarContentDTOsByDocGrammarCategoryID(categoryID), HttpStatus.OK);
//	}
//	
}
