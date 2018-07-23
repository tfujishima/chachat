package jp.gmo.ojt.chachat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.gmo.ojt.chachat.domain.model.Room;

@Controller
@RequestMapping("/test/")
public class testController {
	@Autowired
	HttpSession session;
	
	@RequestMapping(method = RequestMethod.GET)
	public String test() {
		Room room = (Room) session.getAttribute("room");
		String userName = (String) session.getAttribute("userName");
		
		System.out.println("roomname: " + room.getRoomName() + "  username: " + userName);
		return "redirect:/";
	}
}
