package jp.gmo.ojt.chachat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/rooms/{roomId}/")
public class RoomLoginController {
	@RequestMapping(method=RequestMethod.GET)
	public String index(@PathVariable("roomId") String roomId) {
		return "room_login";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String loginAuth(@PathVariable("roomId") String roomId) {
		return "room_login";
	}
}
