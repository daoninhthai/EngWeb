package com.comittedpeople.englishlearningweb.services;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyLong;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocGrammarContentMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarContentDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarContent;
import com.comittedpeople.englishlearningweb.domain.DocGrammarExample;
import com.comittedpeople.englishlearningweb.domain.DocGrammarForm;
import com.comittedpeople.englishlearningweb.domain.DocGrammarNote;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarContentRepository;

public class DocGrammarContentServiceTest {

	DocGrammarContentService grammarContentService;

	@Mock
	DocGrammarContentRepository grammarContentRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		System.out.println("Before called !");
//		grammarContentService = new DocGrammarContentServiceImpl(DocGrammarContentMapper.INSTANCE,
//				grammarContentRepository);
	}

	@Test
	public void docGrammarContentServiceTest() throws Exception {
		// Giả sử.
		DocGrammarExample ex1 = getGrammarExample(1L, "Example 1", "Image 1");
		DocGrammarExample ex2 = getGrammarExample(2L, "Example 2", "Image 2");

		List<DocGrammarExample> docGrammarExamples = Arrays.asList(ex1, ex2);

		DocGrammarNote note1 = getGrammarNote(1L, "Note 1");
		DocGrammarNote note2 = getGrammarNote(2L, "Note 2");

		List<DocGrammarNote> grammarNotes = Arrays.asList(note1, note2);

		DocGrammarForm form = getGrammarForm(1L, "Forming the comparative", "Words of one syllable ending in e",
				"Add -r to the end of word", "Wide - Wider", docGrammarExamples, grammarNotes);

		DocGrammarForm form2 = getGrammarForm(1L, "Forming the comparative", "Words of one syllable ending in e",
				"Add -r to the end of word", "Wide - Wider", docGrammarExamples, grammarNotes);

		List<DocGrammarForm> grammarForms = Arrays.asList(form, form2);
		
		DocGrammarContent content1 = getGrammarContent(1L, "Comparative", "When we compare things...", grammarForms);
		
		DocGrammarContent content2 = getGrammarContent(2L, "Comparative 2", "When we ...", grammarForms);
		
		List<DocGrammarContent> contents = Arrays.asList(content1, content2);
		
		when(grammarContentRepository.findByCategoryId(anyLong())).thenReturn(contents);
		
		List<DocGrammarContentDTO> dtos = grammarContentService.getDocGrammarContentDTOsByDocGrammarCategoryID(1L);
		
		assertEquals(2, dtos.size());
		
	}
	
	private DocGrammarContent getGrammarContent(Long id, String title, String description, List<DocGrammarForm> forms) {
		DocGrammarContent grammarContent = new DocGrammarContent();
		
		grammarContent.setTitle(title);
		grammarContent.setId(id);
		grammarContent.setDescription(description);
		grammarContent.setForms(new HashSet<>(forms));
		
		return grammarContent;
		
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
