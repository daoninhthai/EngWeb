package com.comittedpeople.englishlearningweb.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarExampleDTO;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarFormDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarExample;
import com.comittedpeople.englishlearningweb.domain.DocGrammarForm;

public class DocGrammarFormMapperTest {

	private DocGrammarFormMapper mapper = DocGrammarFormMapper.INSTANCE;

	@Test
	public void testDocGrammarFormMapper() throws Exception {

		// Giả sử.
		DocGrammarExample grammarEx1 = new DocGrammarExample();
		grammarEx1.setContent("The man on the left ...");
		grammarEx1.setId(1L);
		grammarEx1.setImageURL("abc.net");
		
		DocGrammarExample grammarEx2 = new DocGrammarExample();
		grammarEx2.setContent("The man on the right ...");
		grammarEx2.setId(2L);
		grammarEx2.setImageURL("blablabla.net");
				
		DocGrammarForm form = new DocGrammarForm();
		form.setId(1L);
		form.setTitle("Comparative");
		form.setHow("Wide - Wider");
		form.setExamples(new HashSet<>(Arrays.asList(grammarEx1, grammarEx2)));
		
		
		//Khi.
		DocGrammarFormDTO formDTO = mapper.getDto(form);
		
		//Thì.
		assertEquals(Long.valueOf(1L), formDTO.getId());
		assertEquals("Comparative", formDTO.getTitle());
		assertEquals("Wide - Wider", formDTO.getHow());
		
		List<DocGrammarExampleDTO> examples = formDTO.getExamples();
		
		Collections.sort(examples, (d1, d2) -> {
			return d1.getId().intValue() - d2.getId().intValue();
		});
		
		DocGrammarExampleDTO exDTO1 = examples.get(0);
		DocGrammarExampleDTO exDTO2 = examples.get(1);
		
		assertEquals(1L, exDTO1.getId());
		assertEquals("The man on the left ...", exDTO1.getContent());
		assertEquals("abc.net", exDTO1.getImageURL());
		
		assertEquals(2L, exDTO2.getId());
		assertEquals("The man on the right ...", exDTO2.getContent());
		assertEquals("blablabla.net", exDTO2.getImageURL());
	}

}
