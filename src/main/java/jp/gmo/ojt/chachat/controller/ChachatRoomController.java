package jp.gmo.ojt.chachat.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/rooms/{roomId}/chachat")
public class ChachatRoomController {
	@Autowired HttpSession session;
	
	@RequestMapping(method=RequestMethod.GET)
	public String index(@PathVariable("roomId") String roomId) {
		return "chachat_room.html";
	}
}
