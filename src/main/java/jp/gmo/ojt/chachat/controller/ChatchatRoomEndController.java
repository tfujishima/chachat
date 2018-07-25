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
@RequestMapping("/end/")
public class ChatchatRoomEndController {
	@Autowired
	RoomService roomService;
	Room room = new Room();
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		return "chachat_end";
	}

	@ModelAttribute
	RegistForm setUpForm() {
		RegistForm form = new RegistForm();

		return form;
	}

}
