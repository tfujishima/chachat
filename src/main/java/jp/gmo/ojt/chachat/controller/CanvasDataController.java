package jp.gmo.ojt.chachat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.gmo.ojt.chachat.domain.model.CanvasData;
import jp.gmo.ojt.chachat.domain.model.CanvasIdentity;
import jp.gmo.ojt.chachat.domain.service.CanvasDataService;

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
	
	@RequestMapping(method=RequestMethod.GET)
	public CanvasData getCanvasData(@PathVariable("roomId") String roomId, @PathVariable("canvasId") Integer canvasId) {
		return canvasDataService.getCanvasData(roomId, canvasId);
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
