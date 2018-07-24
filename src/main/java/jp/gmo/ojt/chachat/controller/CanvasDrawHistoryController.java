package jp.gmo.ojt.chachat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import jp.gmo.ojt.chachat.domain.model.CanvasDrawHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasIdentity;
import jp.gmo.ojt.chachat.domain.service.CanvasHistoryService;

@Controller
public class CanvasDrawHistoryController {
	@Autowired
	CanvasHistoryService<CanvasDrawHistory> canvasDrawHistoryService;
	
	@MessageMapping("/rooms/{roomId}/canvas/{canvasId}/draw")
    @SendTo("/history/draw/rooms/{roomId}/canvas/{canvasId}")
    public CanvasDrawHistory shareDrawHistories(@DestinationVariable("roomId") String roomId, @DestinationVariable("canvasId") Integer canvasId, CanvasDrawHistory canvasDrawHistory) throws Exception {
		canvasDrawHistory.setCanvasIdentity(new CanvasIdentity(roomId, canvasId));
		canvasDrawHistoryService.saveHistory(canvasDrawHistory);
        return canvasDrawHistory;
    }
}
