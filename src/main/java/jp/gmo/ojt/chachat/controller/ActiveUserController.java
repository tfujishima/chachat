package jp.gmo.ojt.chachat.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.gmo.ojt.chachat.domain.model.ActiveUser;
import jp.gmo.ojt.chachat.domain.model.CanvasData;
import jp.gmo.ojt.chachat.domain.model.CanvasIdentity;
import jp.gmo.ojt.chachat.domain.model.UserIdentity;
import jp.gmo.ojt.chachat.domain.service.ActiveUserService;

@RestController
public class ActiveUserController {
	@Autowired
	ActiveUserService activeUserService;
	
	@RequestMapping(value="/rooms/{roomId}/users/" ,method=RequestMethod.GET)
	public List<ActiveUser> getCanvasData(@PathVariable("roomId") String roomId) {
/*		ActiveUser activeUser = new ActiveUser();
		activeUser.setRoomId(roomId);
		activeUser.setName("hgoe"+roomId);
		activeUserService.registUser(activeUser);
*/		List<ActiveUser> activeUsers = activeUserService.getActiveUsers(roomId);
		return activeUsers;
	}
	@RequestMapping(value="/rooms/{roomId}/users/", method=RequestMethod.POST)
	public ActiveUser postActive(@PathVariable("roomId") String roomId,
			@Valid @RequestBody ActiveUser activeUser, BindingResult error) {
		if (error.hasErrors()) {
			//do something
		}
		activeUser.setRoomId(roomId);
		activeUserService.saveUser(activeUser);
		return activeUserService.getActiveUser(activeUser.getUserIdentity());
	}

}
