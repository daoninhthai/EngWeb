package com.comittedpeople.englishlearningweb.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarNoteDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarNote;

public class DocGrammarNoteMapperTest {
	
	private DocGrammarNoteMapper mapper = DocGrammarNoteMapper.INSTANCE;
	
	@Test
	public void testDocGrammarNoteDTOConversion() throws Exception {
		
		//Giả sử
		DocGrammarNote grammarNote = new DocGrammarNote();
		grammarNote.setId(1L);
		grammarNote.setContent("Grammar Note");
		
		//Khi
		DocGrammarNoteDTO grammarNoteDTO = mapper.getDto(grammarNote);

		//Thì
		assertEquals(1L, grammarNoteDTO.getId());
		assertEquals("Grammar Note", grammarNoteDTO.getContent());
		
	}
	
}
