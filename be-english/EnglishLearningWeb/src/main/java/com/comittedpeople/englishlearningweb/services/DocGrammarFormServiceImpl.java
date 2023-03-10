package com.comittedpeople.englishlearningweb.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocGrammarFormMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarFormDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarContent;
import com.comittedpeople.englishlearningweb.domain.DocGrammarExample;
import com.comittedpeople.englishlearningweb.domain.DocGrammarForm;
import com.comittedpeople.englishlearningweb.domain.DocGrammarNote;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarContentRepository;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarFormRepository;

@Service
public class DocGrammarFormServiceImpl implements DocGrammarFormService{

	@Autowired
	DocGrammarFormMapper formMapper;
	
	@Autowired
	DocGrammarFormRepository formRepository;
	
	@Autowired
	DocGrammarContentRepository contentRepository;
	
	public DocGrammarFormServiceImpl(DocGrammarFormMapper formMapper,
			DocGrammarFormRepository formRepository, DocGrammarContentRepository contentRepository) {		
		this.formMapper = formMapper;
		this.formRepository = formRepository;
		this.contentRepository = contentRepository;
	}
	
	@Override
	public List<DocGrammarFormDTO> getDocGrammarFormDTOsByGrammarContentID(Long grammarContentID) {
		// TODO Auto-generated method stub
		return formRepository.findByDocGrammarContentId(grammarContentID)
				.stream()
				.map(formMapper::getDto)
				.collect(Collectors.toList());
	}

	@Override
	public DocGrammarFormDTO postDocGrammarFormDTOByGrammarContentID(Long grammarContentID, DocGrammarFormDTO formDTO) {
		// TODO Auto-generated method stub
		DocGrammarContent content;
		try {
			content = contentRepository.findById(grammarContentID).get();
		}catch (Exception e) {
			// TODO: handle exception
			content = null;
		}
		if (content == null)
			return null;
		
		//T???o m???i 1 form d???a tr??n content cho tr?????c.
		DocGrammarForm form = formMapper.getEntity(formDTO);
		form.setDocGrammarContent(content);
		form = formRepository.save(form);
		
		//Add form v???a t???o v??o content.
		content.getForms().add(form);
		contentRepository.save(content);
		
		return formMapper.getDto(form);
	}

	@Override
	public boolean deleteDocGrammarFormByGrammarIDAndFormID(Long formID) {
		try {
			//?????u ti??n ta c???n t??m ra c??i form v?? c??i content.
			
			DocGrammarForm form = formRepository.findById(formID).get();
			DocGrammarContent content = form.getDocGrammarContent();
			
			//Sau ????, xo?? form ra kh???i content. Sau ???? m???i xo?? form th???c s???.
			content.getForms().remove(form);
			formRepository.delete(form);
			
			//Sau khi m???i th??? ho??n th??nh th?? nh??? l??u l???i.
			contentRepository.save(content);
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	@Override
	public DocGrammarFormDTO getDocGrammarFormDTOByGrammarFormID (Long grammarFormID) {
		DocGrammarForm form;
		try {
			form = formRepository.findById(grammarFormID).get();
		}catch (Exception e) {
			// TODO: handle exception
			form = null;
		}
		if (form == null)
			return null;
		return formMapper.getDto(form);
	}

	@Override
	public DocGrammarFormDTO patchDocGrammarForm(Long formID, DocGrammarFormDTO formDTO) {
		// TODO Auto-generated method stub
		return formRepository.findById(formID).map(form -> {
			if (formDTO.getHow() != null) {
				form.setHow(formDTO.getHow());
			}
			if (formDTO.getTitle() != null) {
				form.setTitle(formDTO.getTitle());
			}
			if (formDTO.getUsage() != null) {
				form.setUsage(formDTO.getUsage());
			}
			if (formDTO.getUseCase() != null) {
				form.setUseCase(formDTO.getUseCase());
			}
			
			DocGrammarFormDTO returnDTO = formMapper.getDto(formRepository.save(form));
			
			return returnDTO;
			
		}).orElseThrow(RuntimeException::new);
	}

}
