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
		
		//Tạo mới 1 form dựa trên content cho trước.
		DocGrammarForm form = formMapper.getEntity(formDTO);
		form.setDocGrammarContent(content);
		form = formRepository.save(form);
		
		//Add form vừa tạo vào content.
		content.getForms().add(form);
		contentRepository.save(content);
		
		return formMapper.getDto(form);
	}

	@Override
	public boolean deleteDocGrammarFormByGrammarIDAndFormID(Long formID) {
		try {
			//Đầu tiên ta cần tìm ra cái form và cái content.
			
			DocGrammarForm form = formRepository.findById(formID).get();
			DocGrammarContent content = form.getDocGrammarContent();
			
			//Sau đó, xoá form ra khỏi content. Sau đó mới xoá form thực sự.
			content.getForms().remove(form);
			formRepository.delete(form);
			
			//Sau khi mọi thứ hoàn thành thì nhớ lưu lại.
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
