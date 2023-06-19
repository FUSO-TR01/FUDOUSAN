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
	//	①
	@RequestMapping("/home")
	public String sample(Model model) {
		return "home";
	}

	//	②
	//	ログイン画面
	@GetMapping("/login")
	public String showLoginForm(Model model) {
//		model.addAttribute("title", "ログイン");
		return "login";
	}

	@PostMapping("/login")
	public String login(Model model, @RequestParam("logId") String LogId,
			@RequestParam("pass") String pass) {
		UserEntity user = Dao.findByUsername(LogId);
//		model.addAttribute("title", "ログイン");

		if (user != null && user.getPass().equals(pass)) {
			// ログイン成功の処理
			model.addAttribute("result", "ログイン成功");
			return "redirect:/view"; // タスク管理画面にリダイレクト
		} else {
			// ログイン失敗の処理
			model.addAttribute("result", "ユーザー名またはパスワードが間違っています");
			return "login";
		}
	}

	//	新規登録
	@RequestMapping("/signup")
	public String signup(Model model, UserInput userinput) {
		model.addAttribute("title", "todoリスト");
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
}
