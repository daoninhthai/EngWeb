package com.comittedpeople.englishlearningweb.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocGrammarFormMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarFormDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarExample;
import com.comittedpeople.englishlearningweb.domain.DocGrammarForm;
import com.comittedpeople.englishlearningweb.domain.DocGrammarNote;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarFormRepository;

public class DocGrammarFormServiceTest {

	@Mock
	DocGrammarFormRepository formRepository;

	DocGrammarFormService formService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		System.out.println("Before called !");
//		formService = new DocGrammarFormServiceImpl(DocGrammarFormMapper.INSTANCE, formRepository);
	}

	@Test
	public void testFormService() throws Exception {

		// Giả sử.
		DocGrammarExample ex1 = getGrammarExample(1L, "Example 1", "Image 1");
		DocGrammarExample ex2 = getGrammarExample(2L, "Example 2", "Image 2");

		List<DocGrammarExample> docGrammarExamples = Arrays.asList(ex1, ex2);
		
		DocGrammarNote note1 = getGrammarNote(1L, "Note 1");
		DocGrammarNote note2 = getGrammarNote(2L, "Note 2");
		
		List<DocGrammarNote> grammarNotes = Arrays.asList(note1, note2);
		
		
		DocGrammarForm form = getGrammarForm(1L, "Forming the comparative", 
				"Words of one syllable ending in e",
				"Add -r to the end of word",
				"Wide - Wider", docGrammarExamples, grammarNotes);
		
		DocGrammarForm form2 = getGrammarForm(1L, "Forming the comparative", 
				"Words of one syllable ending in e",
				"Add -r to the end of word",
				"Wide - Wider", docGrammarExamples, grammarNotes);
		
		List<DocGrammarForm> grammarForms = Arrays.asList(form, form2);
		
		when(formRepository.findByDocGrammarContentId(anyLong())).thenReturn(grammarForms);
		
		List<DocGrammarFormDTO> grammarFormDTOs = formService.getDocGrammarFormDTOsByGrammarContentID(1L);
		
		assertEquals(2, grammarFormDTOs.size());
		
	}

	private DocGrammarForm getGrammarForm(Long ID, String title, String usage, String useCase, String how,
			List<DocGrammarExample> examples, List<DocGrammarNote> notes) {
		DocGrammarForm form = new DocGrammarForm();

		form.setExamples(new HashSet<DocGrammarExample>(examples));
		form.setHow(how);
		form.setId(ID);
		form.setNotes(new HashSet<DocGrammarNote>(notes));
		form.setTitle(title);
		form.setUsage(usage);
		form.setUseCase(useCase);

		return form;
	}

	private DocGrammarExample getGrammarExample(Long ID, String content, String imageURL) {
		DocGrammarExample res = new DocGrammarExample();

		res.setId(ID);
		res.setContent(content);
		res.setImageURL(imageURL);

		return res;

	}

	private DocGrammarNote getGrammarNote(Long id, String content) {
		DocGrammarNote result = new DocGrammarNote();
		result.setId(id);
		result.setContent(content);

		return result;
	}
}
