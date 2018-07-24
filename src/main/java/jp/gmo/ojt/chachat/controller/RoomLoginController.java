package jp.gmo.ojt.chachat.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.gmo.ojt.chachat.domain.model.Room;
import jp.gmo.ojt.chachat.domain.service.RoomService;
import jp.gmo.ojt.chachat.form.LoginForm;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/rooms/{roomId}/")
public class RoomLoginController {
	@Autowired
	RoomService roomService;
	@Autowired
	HttpSession session;
	
	@ModelAttribute
	LoginForm setupForm() {
		LoginForm loginForm = new LoginForm();
		return loginForm;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(@PathVariable("roomId") String roomId, Model model) {
		Optional<Room> rooms = roomService.searchRoomId(roomId);
		if (rooms.equals(Optional.empty())) {
			return "404";
		} else {
			Room room = rooms.get();
			session.setAttribute("room", room);
			return "room_login";
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public String loginAuth(@PathVariable("roomId") String roomId, LoginForm form) {
		session.setAttribute("userName",form.getUserName());
		return "redirect:/rooms/{roomId}/chachat";
		//return "redirect:/test/";
	}
}
