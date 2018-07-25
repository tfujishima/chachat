package jp.gmo.ojt.chachat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import jp.gmo.ojt.chachat.domain.model.ChatHistory;
import jp.gmo.ojt.chachat.domain.service.ChatHistoryService;

@Controller
public class ChatController {
	@Autowired
	ChatHistoryService chatHistoryService;

	@MessageMapping("/rooms/{roomId}/chat")
	@SendTo("/history/chat/rooms/{roomId}")
	public ChatHistory shareChatHistory(@DestinationVariable("roomId") String roomId, ChatHistory chatHistory) throws Exception {
		chatHistory.setRoomId(roomId);
		chatHistoryService.saveChatHistory(chatHistory);
		return chatHistory;
	}
}

