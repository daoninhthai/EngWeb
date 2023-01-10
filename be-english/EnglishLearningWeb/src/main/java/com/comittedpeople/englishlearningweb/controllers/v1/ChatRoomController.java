package com.comittedpeople.englishlearningweb.controllers.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.comittedpeople.englishlearningweb.api.v1.model.ChatRoomMessageDTO;
import com.comittedpeople.englishlearningweb.domain.UserDetailsCustom;
import com.comittedpeople.englishlearningweb.services.ChatRoomMessageService;
import com.comittedpeople.englishlearningweb.services.UserAccountService;

@Controller
@RequestMapping("/api/v1/roomChat")
public class ChatRoomController {

	private final ChatRoomMessageService chatRoomMessageService;
	
	private final UserAccountService userAccountService;
	
	public ChatRoomController(ChatRoomMessageService chatRoomMessageService, UserAccountService userAccountService) {
		super();
		this.chatRoomMessageService = chatRoomMessageService;
		this.userAccountService = userAccountService;
	}

	@GetMapping
	public ResponseEntity<List<ChatRoomMessageDTO>> getMessagesWithLastMessID(@RequestParam(required = false) Long lastMessID){
		List<ChatRoomMessageDTO> messages;
		if (lastMessID == null)
			messages = chatRoomMessageService.getLatestChatRoomMessages();
		else
			messages = chatRoomMessageService.getChatMessageAfterID(lastMessID);
		
		if (messages != null)
			return new ResponseEntity<List<ChatRoomMessageDTO>>(messages, HttpStatus.OK);
		else
			return new ResponseEntity<List<ChatRoomMessageDTO>>(messages, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<ChatRoomMessageDTO> postMessage (@Valid @RequestBody String message){
		

		//1. Xoá trường ID .
		//2. UserID bắt buộc phải là User hiện tại.
		
		ChatRoomMessageDTO chatRoomMessageDTO = new ChatRoomMessageDTO();
		chatRoomMessageDTO.setContent(message);
		
		chatRoomMessageDTO.setId(0L);
		chatRoomMessageDTO.setUserSentID(getCurrentUserID());
		
		chatRoomMessageDTO = chatRoomMessageService.postChatRoomMessage(chatRoomMessageDTO);
		
		if (chatRoomMessageDTO == null)
			return new ResponseEntity<ChatRoomMessageDTO>(chatRoomMessageDTO, HttpStatus.FORBIDDEN);
		else
			return new ResponseEntity<ChatRoomMessageDTO>(chatRoomMessageDTO, HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity deleteMessage(@RequestParam Long message) {
		
		//Chỉ admin mới được xoá tin nhắn.
		if (!isCurrentUserAdmin())
			return new ResponseEntity (HttpStatus.FORBIDDEN);
		
		Boolean success = chatRoomMessageService.deleteChatRoomMessage(message);
		
		if (success)
			return new ResponseEntity (HttpStatus.OK);
		else
			return new ResponseEntity (HttpStatus.NOT_FOUND);
	}
	
	private Long getCurrentUserID() {
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentUserID;
		if (principle instanceof UserDetailsCustom) {
			currentUserID = ((UserDetailsCustom)principle).getUseraccount().getId();
		}else {
			currentUserID = -1L;
		}
		return currentUserID;
	}
	
	private Boolean isCurrentUserAdmin() {
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentUserID;
		if (principle instanceof UserDetailsCustom) {
			currentUserID = ((UserDetailsCustom)principle).getUseraccount().getId();
		}else {
			currentUserID = -1L;
			return false;
		}
		return userAccountService.isUserIDAdmin(currentUserID);
	}
	
}
