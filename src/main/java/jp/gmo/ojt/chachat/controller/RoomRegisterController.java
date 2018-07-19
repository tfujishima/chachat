package jp.gmo.ojt.chachat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;

//import jp.gmo.ojt.chachat.bean.RoomRegistResult;
import jp.gmo.ojt.chachat.form.RegistForm;

@Controller
@RequestMapping("/")
public class RoomRegisterController {
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
		// aaルーム名がからのときエラー文
		if (bindingResult.hasErrors()) {
			model.addAttribute("valid_error", "1");
			return "room_register.html";
		}
		model.addAttribute("valid_error", "0");

		return "room_register.html";
	}
}
