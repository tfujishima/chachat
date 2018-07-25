package jp.gmo.ojt.chachat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	@RequestMapping(value = "/rooms/{roomId}/chat/", method = RequestMethod.GET)
	public List<ChatHistory> index(@PathVariable("roomId") String roomId) {
		return chatHistoryService.getChatHistories(roomId);
	}
}

