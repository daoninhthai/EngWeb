package com.comittedpeople.englishlearningweb.controllers.v1;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocGrammarContentMapper;
import com.comittedpeople.englishlearningweb.api.v1.mapper.DocGrammarFormMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarContentDTO;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarFormDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarForm;
import com.comittedpeople.englishlearningweb.services.DocGrammarContentService;
import com.comittedpeople.englishlearningweb.services.DocGrammarFormService;

@Controller
public class DocGrammarFormController {

	private final DocGrammarContentService docGrammarContentService;

	private final DocGrammarFormMapper docGrammarFormMapper;

	private final DocGrammarFormService docGrammarFormService;

	public DocGrammarFormController(DocGrammarContentService docGrammarContentService,
			DocGrammarFormMapper docGrammarFormMapper, DocGrammarFormService docGrammarFormService) {
		super();
		this.docGrammarContentService = docGrammarContentService;
		this.docGrammarFormMapper = docGrammarFormMapper;
		this.docGrammarFormService = docGrammarFormService;
	}

	@GetMapping(value = "/api/v1/grammar/{grammarID}/forms")
	public ResponseEntity<List<DocGrammarFormDTO>> getGrammarForm(@PathVariable Long grammarID) {
		List<DocGrammarFormDTO> formDTO = new ArrayList<DocGrammarFormDTO>();

		DocGrammarContentDTO content = docGrammarContentService.getDocGrammarContentByID(grammarID);

		if (content != null) {
			formDTO = content.getForms();
			return new ResponseEntity<List<DocGrammarFormDTO>>(formDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<DocGrammarFormDTO>>(formDTO, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/api/v1/grammar/{grammarID}/forms")
	public ResponseEntity<DocGrammarFormDTO> postGrammarForm(@PathVariable Long grammarID,
			@Valid @RequestBody DocGrammarFormDTO formDTO) {

		DocGrammarFormDTO newFormDTO = docGrammarFormService.postDocGrammarFormDTOByGrammarContentID(grammarID,
				formDTO);

		if (newFormDTO == null) {
			return new ResponseEntity<DocGrammarFormDTO>(newFormDTO, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<DocGrammarFormDTO>(newFormDTO, HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/api/v1/grammarForms/{formID}")
	public ResponseEntity<DocGrammarFormDTO> getGrammarFormByID(@PathVariable Long formID) {
		DocGrammarFormDTO formDTO = docGrammarFormService.getDocGrammarFormDTOByGrammarFormID(formID);
		HttpStatus returnStatus;
		if (formDTO != null) {
			returnStatus = HttpStatus.OK;
		} else {
			returnStatus = HttpStatus.NOT_FOUND;
		}

		return new ResponseEntity<DocGrammarFormDTO>(formDTO, returnStatus);
	}

	@DeleteMapping(value = "/api/v1/grammarForms/{formID}")
	public ResponseEntity deleteGrammarForm(@PathVariable Long formID) {
		boolean success = docGrammarFormService.deleteDocGrammarFormByGrammarIDAndFormID(formID);
		if (success) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PatchMapping(value = "/api/v1/grammarForms/{formID}")
	public ResponseEntity<DocGrammarFormDTO> patchGrammarForm (@PathVariable Long formID, @Valid @RequestBody DocGrammarFormDTO formDTO) {
		DocGrammarFormDTO newDTO;
		HttpStatus returnStatus;
		try {
			newDTO = docGrammarFormService.patchDocGrammarForm(formID, formDTO);	
		}catch (Exception e) {
			// TODO: handle exception
			newDTO = null;
		}
		if (newDTO == null) {
			returnStatus = HttpStatus.NOT_FOUND;
		} else {
			returnStatus = HttpStatus.OK;
		}
			
		return new ResponseEntity<DocGrammarFormDTO> (newDTO, returnStatus);
	}
}
