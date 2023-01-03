package com.comittedpeople.englishlearningweb.controllers.v1;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarExampleDTO;
import com.comittedpeople.englishlearningweb.services.DocGrammarExampleService;

@Controller
public class DocGrammarExampleController {
	
	private final DocGrammarExampleService docGrammarExampleService;
	public DocGrammarExampleController(DocGrammarExampleService docGrammarExampleService) {
		super();
		this.docGrammarExampleService = docGrammarExampleService;
	}
	
	@GetMapping ("/api/v1/grammarForms/{formID}/examples")
	public ResponseEntity<List<DocGrammarExampleDTO>> getDocGrammarExample (@PathVariable Long formID){
		
		HttpStatus returnStatus;
		
		List<DocGrammarExampleDTO> exampleDTOs = docGrammarExampleService.getDocGrammarExampleDTOsByFormID(formID);
		if (exampleDTOs == null)
			returnStatus = HttpStatus.NOT_FOUND;
		else {
			returnStatus = HttpStatus.OK;
		}
		
		return new ResponseEntity<List<DocGrammarExampleDTO>>(exampleDTOs, returnStatus);
		
	}

	@PutMapping ("/api/v1/grammarForms/{formID}/examples")
	public ResponseEntity<List<DocGrammarExampleDTO>> putDocGrammarExample (@PathVariable Long formID, 
			@Valid @RequestBody List<DocGrammarExampleDTO> exampleDTO){
		
		HttpStatus returnStatus;
		
		List<DocGrammarExampleDTO> returnDTOs = docGrammarExampleService.putDocGrammarExampleDTOsByFormID(formID, exampleDTO);
		
		if (returnDTOs == null)
			returnStatus = HttpStatus.NOT_FOUND;
		else 
			returnStatus = HttpStatus.OK;
		
		return new ResponseEntity<List<DocGrammarExampleDTO>>(returnDTOs, returnStatus);
	}
	
}
