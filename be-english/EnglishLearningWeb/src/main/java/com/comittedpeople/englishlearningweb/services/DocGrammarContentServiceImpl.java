package com.comittedpeople.englishlearningweb.services;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.comittedpeople.englishlearningweb.api.v1.mapper.DocGrammarContentMapper;
import com.comittedpeople.englishlearningweb.api.v1.mapper.DocGrammarContentSummaryMapper;
import com.comittedpeople.englishlearningweb.api.v1.mapper.DocGrammarFormMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarContentDTO;
import com.comittedpeople.englishlearningweb.api.v1.model.DocGrammarContentSummaryDTO;
import com.comittedpeople.englishlearningweb.domain.DocGrammarCategory;
import com.comittedpeople.englishlearningweb.domain.DocGrammarContent;
import com.comittedpeople.englishlearningweb.domain.DocGrammarForm;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarCategoryRepository;
import com.comittedpeople.englishlearningweb.repositories.DocGrammarContentRepository;

@Service
public class DocGrammarContentServiceImpl implements DocGrammarContentService{

	@Autowired
	private DocGrammarContentMapper contentMapper;
	
	@Autowired
	private DocGrammarContentSummaryMapper contentSummaryMapper;
	
	@Autowired
	private DocGrammarFormMapper formMapper;
	
	@Autowired
	private DocGrammarContentRepository contentRepository;
	
	@Autowired
	private DocGrammarCategoryRepository categoryRepository;
	
	@Override
	public List<DocGrammarContentDTO> getDocGrammarContentDTOsByDocGrammarCategoryID(Long categoryID) {
		return contentRepository.findByCategoryId(categoryID)
				.stream()
				.map(contentMapper::getDto)
				.collect(Collectors.toList());
	}

	@Override
	public DocGrammarContentDTO getDocGrammarContentByID(Long grammarID) {
		DocGrammarContentDTO returnDTO;
		try{
			returnDTO = contentRepository.findById(grammarID).map(contentMapper::getDto).get();
		}catch (Exception e) {
			// TODO: handle exception
			returnDTO = null;
		}
		return returnDTO;
	}
	
	@Override
	public DocGrammarContentDTO postDocGrammarContent(DocGrammarContentDTO contentDTO) {
		DocGrammarContent content = contentMapper.getEntity(contentDTO);
		
		content = contentRepository.save(content);
		
		return contentMapper.getDto(content);
	}

	@Override
	public boolean deleteDocGrammarContent(Long contentID) {
		// TODO Auto-generated method stub
		try {
			contentRepository.deleteById(contentID);
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	@Override
	public DocGrammarContentDTO patchDocGrammarContent(Long grammarID, DocGrammarContentDTO contentDTO) {
		return contentRepository.findById(grammarID).map(content -> {
			if (contentDTO.getCategoryID() != null) {
				DocGrammarCategory category;
				
				//Delete Grammar in old category.
				DocGrammarCategory oldCategory = content.getCategory();
				oldCategory.getGrammars().remove(content);
				
				//Then try to add it to new category.
				try{
					category = categoryRepository.findById(contentDTO.getCategoryID()).get();
					category.getGrammars().add(content);
				}catch (Exception e) {
					// TODO: handle exception
					category = null;
				}
				
				content.setCategory(category);
			}
			if (contentDTO.getDescription() != null) {
				content.setDescription(contentDTO.getDescription());
			}
			
			if (contentDTO.getForms() != null) {
//				List<DocGrammarForm> forms = contentDTO.getForms()
//				.stream()
//				.map(formMapper::getEntity)
//				.collect(Collectors.toList());
//				
//				content.setForms(new HashSet<DocGrammarForm>(forms));
			}
			
			if (contentDTO.getTitle() != null) {
				content.setTitle(contentDTO.getTitle());
			}
			
			DocGrammarContentDTO returnDTO = contentMapper.getDto(contentRepository.save(content));
			
			return returnDTO;
		}).orElseThrow(RuntimeException::new);
	}

	@Override
	public DocGrammarContentSummaryDTO postDocGrammarContentSummary(Long categoryID, DocGrammarContentSummaryDTO summaryDTO) {
		//Đầu tiên là ta phải tạo mới content.
		//Sau đó gắn content với category.
		//Sau đó lưu lại content.
		//Sau đó gắn category với content.
		//Sau đó lưu lại category.
		//Sau đó chuyển content vừa tạo thành DTO và trả về.
		
		DocGrammarContent content = contentSummaryMapper.getEntity(summaryDTO);
		
		DocGrammarCategory category;
		try {
			category = categoryRepository.findById(categoryID).get();
		} catch (Exception e) {
			return null;
		} 
		
		content.setCategory(category);
		content = contentRepository.save(content);
		
		category.getGrammars().add(content);
		categoryRepository.save(category);
		
		return contentSummaryMapper.getDto(content);
	}
}
