package com.example.demo.fudo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.Dao;
import com.example.demo.entity.UserEntity;

@Controller
public class FudoController {

	private final Dao dao;

	@Autowired
	public FudoController(Dao dao) {
		this.dao = dao;
	}

	//	①--------------------------------------------------------------------------------------------------------------
	@RequestMapping("/home")
	public String sample(Model model) {
		return "home";
	}

	//	②--------------------------------------------------------------------------------------------------------------
	//	ログイン画面１(不動産ログイン)
	@GetMapping("/login1")
	public String showLoginForm1(Model model) {
		return "login1";
	}

	@PostMapping("/login1")
	public String login1(Model model, @RequestParam("logId1") String LogId,
			@RequestParam("pass1") String pass) {
		UserEntity user = Dao.findByUsername(LogId);

		if (user != null && user.getPass().equals(pass)) {
			// ログイン成功の処理
			model.addAttribute("result", "ログイン成功");
			return "redirect:/view"; // タスク管理画面にリダイレクト
		} else {
			// ログイン失敗の処理
			model.addAttribute("result", "ユーザー名またはパスワードが間違っています");
			return "login1";
		}
	}

	//	　--------------------------------------------------------------------------------------------------------------
	//	ログイン画面２(顧客ログイン)
	@GetMapping("/customer")
	public String showLoginForm2(Model model) {
		return "customer";
	}

	@PostMapping("/login2")
	public String login2(Model model, @RequestParam("logId2") String LogId,
			@RequestParam("pass2") String pass) {
		UserEntity user = Dao.findByUsername(LogId);
		if (user != null && user.getPass().equals(pass)) {
			// ログイン成功の処理
			model.addAttribute("result", "ログイン成功");
			return "redirect:/view"; // タスク管理画面にリダイレクト
		} else {
			// ログイン失敗の処理
			model.addAttribute("result", "ユーザー名またはパスワードが間違っています");
			return "login2";
		}
	}

	//	⑥--------------------------------------------------------------------------------------------------------------
	
	//	新規登録
	@RequestMapping("/signup")
	public String signup(Model model, UserInput userinput) {
		return "signup";
	}

	//	登録完了画面
	@RequestMapping("/register")
	public String complete(Model model, UserInput userinput) {
		UserEntity userentform = new UserEntity();
		userentform.setLogId(userinput.getLogId());
		userentform.setPass(userinput.getPass());
		dao.insertDb2(userentform);
		return "register";
	}

	//	③--------------------------------------------------------------------------------------------------------------

	@RequestMapping("/merchant")
	public String merchant(Model model) {
		return "merchant";
	}

	//	④--------------------------------------------------------------------------------------------------------------
	@RequestMapping("/addhome")
	public String addhome(Model model, Input onput) {
		return "addhome";
	}

	//	⑤--------------------------------------------------------------------------------------------------------------
	@RequestMapping("/viewhome")
	public String viewhome(Model model) {
		return "viewhome";
	}
}
