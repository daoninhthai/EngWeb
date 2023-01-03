package com.comittedpeople.englishlearningweb.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.comittedpeople.englishlearningweb.domain.ChatRoomMessage;

public interface ChatRoomMessageRepository extends JpaRepository<ChatRoomMessage, Long>{
	
	//Luôn luôn chỉ trả về 10 tin nhắn tiếp theo.
	List<ChatRoomMessage> findTop10ByMessageSentDateGreaterThanOrderByMessageSentDateAsc(LocalDateTime timeStamp);
	
	//Luôn luôn chỉ trả về 10 tin nhắn đầu tiên.
	List<ChatRoomMessage> findTop10ByOrderByMessageSentDateAsc();
	
}
