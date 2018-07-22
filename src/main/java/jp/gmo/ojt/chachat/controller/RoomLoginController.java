package jp.gmo.ojt.chachat.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	@RequestMapping(method = RequestMethod.GET)
	public String index(@PathVariable("roomId") String roomId) {
		Optional<Room> rooms = roomService.serchRoomId(roomId);
		System.out.println(rooms);
		System.out.println(rooms.equals(Optional.empty()));
		if (rooms.equals(Optional.empty())) {
			return "404";
		} else {
			Room room = rooms.get();
			return "room_login";
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public String loginAuth(@PathVariable("roomId") String roomId) {
		return "room_login";
	}
}
