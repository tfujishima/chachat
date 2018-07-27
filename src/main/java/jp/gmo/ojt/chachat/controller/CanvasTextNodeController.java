package jp.gmo.ojt.chachat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import jp.gmo.ojt.chachat.domain.model.CanvasIdentity;
import jp.gmo.ojt.chachat.domain.model.CanvasTextChangeHistory;
import jp.gmo.ojt.chachat.domain.service.CanvasHistoryService;

@Controller
public class CanvasTextNodeController {
	@Autowired
	CanvasHistoryService<CanvasTextChangeHistory> canvasTextChangeHistoryService;
	
	@MessageMapping("/rooms/{roomId}/canvas/{canvasId}/object/text/change")
    @SendTo("/history/object/text/change/rooms/{roomId}/canvas/{canvasId}")
    public CanvasTextChangeHistory shareTextChangeHistories(@DestinationVariable("roomId") String roomId, @DestinationVariable("canvasId") Integer canvasId, CanvasTextChangeHistory canvasTextChangeHistory) throws Exception {
		canvasTextChangeHistory.setCanvasIdentity(new CanvasIdentity(roomId, canvasId));
		canvasTextChangeHistoryService.saveHistory(canvasTextChangeHistory);
        return canvasTextChangeHistory;
    }
}
