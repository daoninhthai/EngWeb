package com.comittedpeople.englishlearningweb.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocGrammarNoteMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarNoteDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarForm;
import com.comittedpeople.englishlearningweb.domain.DocGrammarNote;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarFormRepository;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarNoteRepository;

@Service
public class DocGrammarNoteServiceImpl implements DocGrammarNoteService {

	@Autowired
	DocGrammarNoteRepository noteRepository;
	
	@Autowired
	DocGrammarFormRepository formRepository;
	
	@Autowired
	DocGrammarNoteMapper noteMapper;

	public DocGrammarNoteServiceImpl(DocGrammarNoteRepository noteRepository, DocGrammarFormRepository formRepository,
			DocGrammarNoteMapper noteMapper) {
		super();
		this.noteRepository = noteRepository;
		this.formRepository = formRepository;
		this.noteMapper = noteMapper;
	}

	@Override
	public List<DocGrammarNoteDTO> getAllDocGrammarNoteDTOByFormID(Long formID) {
		
		List<DocGrammarNote> grammarNote;
		
		try {
			grammarNote = noteRepository.findByDocGrammarFormId(formID);
		}catch (Exception e) {
			// TODO: handle exception
			grammarNote = null;
		}
		
		if (grammarNote == null)
			return null;
		
		return grammarNote
				.stream()
				.map(noteMapper::getDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<DocGrammarNoteDTO> putAllDocGrammarNoteDTOByFormID(Long formID, List<DocGrammarNoteDTO> noteDTOs) {
		// TODO Auto-generated method stub
		
		DocGrammarForm form;
		try {
			form = formRepository.findById(formID).get();
		}catch (Exception e) {
			// TODO: handle exception
			form = null;
		}
		
		if (form == null)
			return null;
		
		List<DocGrammarNote> notes = noteDTOs
				.stream()
				.map(noteMapper::getEntity)
				.collect(Collectors.toList());
		
		for (DocGrammarNote note : notes) {
			note.setDocGrammarForm(form);
		}
		
		notes = noteRepository.saveAll(notes);
		
		form.setNotes(new HashSet<DocGrammarNote>(notes));
		formRepository.save(form);
		
		HashMap<Long, Boolean> noteExistInRequest = new HashMap<>();
		
		for (DocGrammarNote note : notes) {
			noteExistInRequest.put(note.getId(), true);
		}
		
		List<DocGrammarNote> formNotes = noteRepository.findByDocGrammarFormId(formID);
		List<DocGrammarNote> toBeDeleted = new ArrayList<DocGrammarNote>();
		
		for (DocGrammarNote note : formNotes) {
			Boolean idExistInUserRequest = noteExistInRequest.get(note.getId());
			
			if (idExistInUserRequest == null || idExistInUserRequest == false) {
				toBeDeleted.add(note);
			}
		}
		noteRepository.deleteAll(toBeDeleted);
		
		return notes.stream().map(noteMapper::getDto).collect(Collectors.toList());
	}

}
