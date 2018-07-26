package jp.gmo.ojt.chachat.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.gmo.ojt.chachat.domain.model.ChatHistory;
import jp.gmo.ojt.chachat.domain.repository.ChatHistoryRepository;

@Service
public class ChatHistoryService {
	@Autowired
	ChatHistoryRepository chatHistoryRepository;

	public List<ChatHistory> getChatHistories(String roomId) {
		return chatHistoryRepository.findByRoomId(roomId);
	}

	public void saveChatHistory(ChatHistory chatHistory) {
		chatHistoryRepository.save(chatHistory);
	}
}
