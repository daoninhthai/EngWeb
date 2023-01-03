package com.comittedpeople.englishlearningweb.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comittedpeople.englishlearningweb.api.v1.mapper.ChatRoomMessageMapper;
import com.comittedpeople.englishlearningweb.api.v1.model.ChatRoomMessageDTO;
import com.comittedpeople.englishlearningweb.domain.ChatRoomMessage;
import com.comittedpeople.englishlearningweb.repositories.ChatRoomMessageRepository;

@Service
public class ChatRoomMessageServiceImpl implements ChatRoomMessageService {

	@Autowired
	ChatRoomMessageMapper chatRoomMessageMapper;
	
	@Autowired
	ChatRoomMessageRepository chatRoomMessageRepository;
	
	public ChatRoomMessageServiceImpl(ChatRoomMessageMapper chatRoomMessageMapper,
			ChatRoomMessageRepository chatRoomMessageRepository) {
		super();
		this.chatRoomMessageMapper = chatRoomMessageMapper;
		this.chatRoomMessageRepository = chatRoomMessageRepository;
	}

	@Override
	public ChatRoomMessageDTO postChatRoomMessage(ChatRoomMessageDTO chatRoomMessageDTO) {
		// TODO Auto-generated method stub
		ChatRoomMessage message = chatRoomMessageMapper.getEntityWithNowSentTime(chatRoomMessageDTO);
		message = chatRoomMessageRepository.save(message);
		return chatRoomMessageMapper.getDto(message);
	}

	@Override
	public Boolean deleteChatRoomMessage(Long messageID) {
		// TODO Auto-generated method stub
		try {
			chatRoomMessageRepository.deleteById(messageID);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	@Override
	public List<ChatRoomMessageDTO> getLatestChatRoomMessages() {
		// TODO Auto-generated method stub
		List<ChatRoomMessage> messages = chatRoomMessageRepository.findTop10ByOrderByMessageSentDateAsc();
		List<ChatRoomMessageDTO> messagesDTOs = messages
				.stream()
				.map(chatRoomMessageMapper::getDto)
				.collect(Collectors.toList());
		return messagesDTOs;
	}

	@Override
	public List<ChatRoomMessageDTO> getChatMessageAfterID(Long chatID) {
		// TODO Auto-generated method stub
		ChatRoomMessage requestedMessage = chatRoomMessageRepository.findById(chatID).get();
		if (requestedMessage == null)
			return null;
		List<ChatRoomMessage> messages = chatRoomMessageRepository.findTop10ByMessageSentDateGreaterThanOrderByMessageSentDateAsc(requestedMessage.getMessageSentDate());
		
		List<ChatRoomMessageDTO> messageDTOs = messages
				.stream()
				.map(chatRoomMessageMapper::getDto)
				.collect(Collectors.toList());
		
		return messageDTOs;
	}

	
	
}
