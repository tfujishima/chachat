package jp.gmo.ojt.chachat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.gmo.ojt.chachat.bean.RoomRegistResult;

@Controller
@RequestMapping("/")
public class RoomRegisterController {
	@RequestMapping(method=RequestMethod.GET)
	public String index() {
		return "room_register.html";
	}

	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public RoomRegistResult register() {
		return new RoomRegistResult("12345");
	}
}
