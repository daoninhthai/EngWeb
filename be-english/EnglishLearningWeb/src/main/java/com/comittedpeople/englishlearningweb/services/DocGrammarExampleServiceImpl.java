	package com.comittedpeople.englishlearningweb.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocGrammarExampleMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarExampleDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarExample;
import com.comittedpeople.englishlearningweb.domain.DocGrammarForm;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarExampleRepository;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarFormRepository;

@Service
public class DocGrammarExampleServiceImpl implements DocGrammarExampleService{

	@Autowired
	DocGrammarExampleRepository exampleRepository;
	
	@Autowired
	DocGrammarFormRepository formRepository;
	
	@Autowired
	DocGrammarExampleMapper exampleMapper;
	
	public DocGrammarExampleServiceImpl(DocGrammarExampleRepository exampleRepository,
			DocGrammarFormRepository formRepository, DocGrammarExampleMapper exampleMapper) {
		super();
		this.exampleRepository = exampleRepository;
		this.formRepository = formRepository;
		this.exampleMapper = exampleMapper;
	}

	@Override
	public List<DocGrammarExampleDTO> getDocGrammarExampleDTOsByFormID(Long formID) {
		try {
			return exampleRepository.findByDocGrammarFormId(formID)
					.stream()
					.map(exampleMapper::getDto)
					.collect(Collectors.toList());
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public List<DocGrammarExampleDTO> putDocGrammarExampleDTOsByFormID(Long formID,
			List<DocGrammarExampleDTO> exampleDTOs) {
		//Thử tìm xem form có hợp lệ hay không.
		DocGrammarForm form;
		try {
			form = formRepository.findById(formID).get();
		}catch (Exception e) {
			// TODO: handle exception
			form = null;
		}
		
		if (form == null)
			return null;
		
		List<DocGrammarExample> examples = exampleDTOs.stream().map(exampleMapper::getEntity).collect(Collectors.toList());
		
		for (DocGrammarExample example : examples) {
			example.setDocGrammarForm(form);
		}
		
		//Lưu hết tất cả example xuống database.
		examples = exampleRepository.saveAll(examples);
		
		//Lưu lại form nhé.
		form.setExamples(new HashSet<>(examples));
		formRepository.save(form);
		
		//Xoá đi những example nào không có trong put của user.
		HashMap<Long, Boolean> exampleExistInRequest = new HashMap<>();
		
		for (DocGrammarExample example : examples) {
			exampleExistInRequest.put(example.getId(), true);
		}
		
		List<DocGrammarExample> formExamples = exampleRepository.findByDocGrammarFormId(formID);
		List<DocGrammarExample> toBeDeleted = new ArrayList<DocGrammarExample>();
		
		for (DocGrammarExample example : formExamples) {
			Boolean idExistInUserRequest = exampleExistInRequest.get(example.getId());
			//Không tồn tại trong request của user thì đánh dấu chuẩn bị xoá.
			if (idExistInUserRequest == null || idExistInUserRequest == false) {
				toBeDeleted.add(example);
			}
		}
		exampleRepository.deleteAll(toBeDeleted);
		
		//Cuối cùng là ta trả về kết quả thu được.
		return examples.stream().map(exampleMapper::getDto).collect(Collectors.toList());
	}
}
