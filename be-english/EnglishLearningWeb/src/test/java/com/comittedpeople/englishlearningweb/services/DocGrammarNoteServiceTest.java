package com.comittedpeople.englishlearningweb.services;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyLong;


import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocGrammarNoteMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarNoteDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarNote;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarNoteRepository;

public class DocGrammarNoteServiceTest {

	DocGrammarNoteService grammarNoteService;
	
	@Mock
	DocGrammarNoteRepository grammarNoteRepository;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		System.out.println("Before called");
		//grammarNoteService = new DocGrammarNoteServiceImpl(DocGrammarNoteMapper.INSTANCE, grammarNoteRepository);
	}
	
	@Test
	public void getNoteByFormID() throws Exception {
		System.out.println("Test called !");
		
		DocGrammarNote note1 = getGrammarNote(1L, "Note 1");
		DocGrammarNote note2 = getGrammarNote(2L, "Note 2");
		
		List<DocGrammarNote> grammarNotes = Arrays.asList(note1, note2);
		
		when(grammarNoteRepository.findByDocGrammarFormId(anyLong())).thenReturn(grammarNotes);
		
		List<DocGrammarNoteDTO> noteDTOs = grammarNoteService.getAllDocGrammarNoteDTOByFormID(1L);
		
		assertEquals(2, noteDTOs.size());		
		
	}
	
	private DocGrammarNote getGrammarNote(Long id, String content) {
		DocGrammarNote result = new DocGrammarNote();
		result.setId(id);
		result.setContent(content);
		
		return result;
	}
	
}
