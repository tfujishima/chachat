package jp.gmo.ojt.chachat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import jp.gmo.ojt.chachat.domain.model.CanvasDrawHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasIdentity;
import jp.gmo.ojt.chachat.domain.model.CanvasObjectCreatedHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasObjectDeletedHistory;
import jp.gmo.ojt.chachat.domain.service.CanvasHistoryService;
import jp.gmo.ojt.chachat.domain.model.CanvasObjectMovedHistory;
@Controller
public class CanvasObjectController {
	@Autowired
	CanvasHistoryService<CanvasObjectCreatedHistory> canvasObjectCreatedHistoryService;
	@Autowired
	CanvasHistoryService<CanvasObjectMovedHistory> canvasObjectMovedHistoryService;
	@Autowired
	CanvasHistoryService<CanvasObjectDeletedHistory> canvasObjectDeletedHistoryService;
	
	@MessageMapping("/rooms/{roomId}/canvas/{canvasId}/object/create")
    @SendTo("/history/object/create/rooms/{roomId}/canvas/{canvasId}")
    public CanvasObjectCreatedHistory shareObjectCtratedHistory(@DestinationVariable("roomId") String roomId, @DestinationVariable("canvasId") Integer canvasId, CanvasObjectCreatedHistory canvasObjectCreatedHistory) throws Exception {
		canvasObjectCreatedHistory.setCanvasIdentity(new CanvasIdentity(roomId, canvasId));
		canvasObjectCreatedHistoryService.saveHistory(canvasObjectCreatedHistory);
        return canvasObjectCreatedHistory;
    }
	@MessageMapping("/rooms/{roomId}/canvas/{canvasId}/object/move")
    @SendTo("/history/object/move/rooms/{roomId}/canvas/{canvasId}")
    public CanvasObjectMovedHistory shareObjectMovedHistory(@DestinationVariable("roomId") String roomId, @DestinationVariable("canvasId") Integer canvasId, CanvasObjectMovedHistory canvasObjectMovedHistory) throws Exception {
		canvasObjectMovedHistory.setCanvasIdentity(new CanvasIdentity(roomId, canvasId));
		canvasObjectMovedHistoryService.saveHistory(canvasObjectMovedHistory);
        return canvasObjectMovedHistory;
    }
	@MessageMapping("/rooms/{roomId}/canvas/{canvasId}/object/delete")
    @SendTo("/history/object/delete/rooms/{roomId}/canvas/{canvasId}")
    public CanvasObjectDeletedHistory shareObjectDeletedHistory(@DestinationVariable("roomId") String roomId, @DestinationVariable("canvasId") Integer canvasId, CanvasObjectDeletedHistory canvasObjectDeletedHistory) throws Exception {
		canvasObjectDeletedHistory.setCanvasIdentity(new CanvasIdentity(roomId, canvasId));
		canvasObjectDeletedHistoryService.saveHistory(canvasObjectDeletedHistory);
        return canvasObjectDeletedHistory;
    }
}
