package jp.gmo.ojt.chachat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.gmo.ojt.chachat.domain.model.Room;
import jp.gmo.ojt.chachat.domain.service.RoomService;

import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/rooms/{roomId}/")
public class RoomLoginController {

	@Autowired
	RoomService roomService;
	Room room = new Room();

	@RequestMapping(method = RequestMethod.GET)
	public String index(@PathVariable("roomId") String roomId, Model model) {
		if (roomService.roomIdisInDB(roomId)) {
			// model.addAttribute("room", attributeValue)

			return "room_login";
		} else {
			return "404";
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public String loginAuth(@PathVariable("roomId") String roomId) {
		return "room_login";
	}
}
