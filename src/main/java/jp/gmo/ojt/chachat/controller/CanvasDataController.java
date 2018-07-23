package jp.gmo.ojt.chachat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.gmo.ojt.chachat.bean.CanvasDataResult;
import jp.gmo.ojt.chachat.domain.model.CanvasData;
import jp.gmo.ojt.chachat.domain.model.CanvasDrawHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasIdentity;
import jp.gmo.ojt.chachat.domain.service.CanvasDataService;
import jp.gmo.ojt.chachat.domain.service.CanvasDrawHistoryService;

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
	CanvasDrawHistoryService canvasDrawHistoryService;
	
	@RequestMapping(method=RequestMethod.GET)
	public CanvasDataResult getCanvasData(@PathVariable("roomId") String roomId, @PathVariable("canvasId") Integer canvasId) {
		CanvasData canvasData = canvasDataService.getCanvasData(roomId, canvasId);
		List<CanvasDrawHistory> canvasDrawHistories = canvasDrawHistoryService.getAfterHistoriesByDate(new CanvasIdentity(roomId,canvasId), canvasData.getUpdatedAt());
		return new CanvasDataResult(canvasData,canvasDrawHistories);
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
