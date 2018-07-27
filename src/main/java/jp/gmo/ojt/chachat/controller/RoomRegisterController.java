package jp.gmo.ojt.chachat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jp.gmo.ojt.chachat.domain.model.Room;
import jp.gmo.ojt.chachat.domain.service.RoomService;
import jp.gmo.ojt.chachat.form.RegistForm;
import jp.gmo.ojt.chachat.util.RoomUtility;

@Controller
@RequestMapping("/")
public class RoomRegisterController {
	@Autowired
	RoomService roomService;
	Room room = new Room();
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		return "room_register.html";
	}

	@ModelAttribute
	RegistForm setUpForm() {
		RegistForm form = new RegistForm();

		return form;
	}

	@RequestMapping(method = RequestMethod.POST)
	// @ResponseBody
	public String register(@Validated RegistForm form, BindingResult bindingResult, Model model) {
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
		room.setRoomName(form.getRoomName());
		
		// aaルーム名がからのときエラー文
		if (bindingResult.hasErrors()) {
			model.addAttribute("valid_error", "1");
			return "room_register.html";
		}
		model.addAttribute("valid_error", "0");	

		roomService.registRoom(room);
		model.addAttribute("url",builder.toUriString() 
								+ "rooms/" 
								+ RoomUtility.makeRoomId(form.getRoomName()) 
								+ "/");
		
		return "room_register.html";
	}
}
