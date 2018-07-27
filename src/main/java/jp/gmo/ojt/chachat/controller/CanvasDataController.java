package jp.gmo.ojt.chachat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.gmo.ojt.chachat.bean.CanvasDataResult;
import jp.gmo.ojt.chachat.domain.model.CanvasData;
import jp.gmo.ojt.chachat.domain.model.CanvasDrawHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasIdentity;
import jp.gmo.ojt.chachat.domain.model.CanvasObjectCreatedHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasObjectDeletedHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasObjectMovedHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasTextChangeHistory;
import jp.gmo.ojt.chachat.domain.service.CanvasDataService;
import jp.gmo.ojt.chachat.domain.service.CanvasHistoryService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/rooms/{roomId}/canvas/{canvasId}")
public class CanvasDataController {
	@Autowired
	CanvasDataService canvasDataService;
	@Autowired
	CanvasHistoryService<CanvasDrawHistory> canvasDrawHistoryService;
	@Autowired
	CanvasHistoryService<CanvasObjectCreatedHistory> canvasObjectCreatedHistoryService;
	@Autowired
	CanvasHistoryService<CanvasObjectMovedHistory> canvasObjectMovedHistoryService;
	@Autowired
	CanvasHistoryService<CanvasObjectDeletedHistory> canvasObjectDeletedHistoryService;
	@Autowired
	CanvasHistoryService<CanvasTextChangeHistory> canvasTextChangeHistoryService;

	
	@RequestMapping(method=RequestMethod.GET)
	public CanvasDataResult getCanvasData(@PathVariable("roomId") String roomId, @PathVariable("canvasId") Integer canvasId) {
		CanvasData canvasData = canvasDataService.getCanvasData(roomId, canvasId);
		List<CanvasDrawHistory> canvasDrawHistories;
		List<CanvasObjectCreatedHistory> canvasObjectCreatedHistories;
		List<CanvasObjectMovedHistory> canvasObjectMovedHistories;
		List<CanvasObjectDeletedHistory> canvasObjectDeletedHistories;
		List<CanvasTextChangeHistory> canvasTextChangeHistories;
		if(canvasData == null) {
			canvasDrawHistories = canvasDrawHistoryService.getHistories(new CanvasIdentity(roomId,canvasId));
			canvasObjectCreatedHistories = canvasObjectCreatedHistoryService.getHistories(new CanvasIdentity(roomId,canvasId));
			canvasObjectMovedHistories = canvasObjectMovedHistoryService.getHistories(new CanvasIdentity(roomId,canvasId));
			canvasObjectDeletedHistories = canvasObjectDeletedHistoryService.getHistories(new CanvasIdentity(roomId,canvasId));
			canvasTextChangeHistories = canvasTextChangeHistoryService.getHistories(new CanvasIdentity(roomId,canvasId));
		}else {
			canvasDrawHistories = canvasDrawHistoryService.getAfterHistoriesByDate(new CanvasIdentity(roomId,canvasId), canvasData.getUpdatedAt());
			canvasObjectCreatedHistories = canvasObjectCreatedHistoryService.getAfterHistoriesByDate(new CanvasIdentity(roomId,canvasId), canvasData.getUpdatedAt());
			canvasObjectMovedHistories = canvasObjectMovedHistoryService.getAfterHistoriesByDate(new CanvasIdentity(roomId,canvasId), canvasData.getUpdatedAt());
			canvasObjectDeletedHistories = canvasObjectDeletedHistoryService.getAfterHistoriesByDate(new CanvasIdentity(roomId,canvasId), canvasData.getUpdatedAt());
			canvasTextChangeHistories = canvasTextChangeHistoryService.getAfterHistoriesByDate(new CanvasIdentity(roomId,canvasId), canvasData.getUpdatedAt());
		}
		return new CanvasDataResult(canvasData,canvasDrawHistories,canvasObjectCreatedHistories,
				canvasObjectMovedHistories,canvasObjectDeletedHistories,canvasTextChangeHistories);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public CanvasData postCanvasData(@PathVariable("roomId") String roomId, @PathVariable("canvasId") Integer canvasId,
			@Valid @RequestBody CanvasData canvasData, BindingResult error) {
		if (error.hasErrors()) {
			//do something
		}
		canvasData.setCanvasIdentity(new CanvasIdentity(roomId, canvasId));
		canvasDataService.saveCanvasData(canvasData);
		return canvasDataService.getCanvasData(roomId, canvasId);
	}

}
