package com.example.demo.fudo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.Dao;
import com.example.demo.entity.ChatEntity;
import com.example.demo.entity.Entity;
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
	public String sample(Model model, Input input) {
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
			return "merchant"; // タスク管理画面にリダイレクト
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

	//	業者新規登録
	@RequestMapping("/signup1")
	public String signup1(Model model, UserInput userinput) {
		return "signup1";
	}

	//	登録完了画面
	@RequestMapping("/register1")
	public String complete1(@Validated UserInput userinput, Model model, BindingResult result) {
		if (result.hasErrors()) {
			return "signup1";
		}
		if (userinput.getPass1().equals(userinput.getPass2())) {
			UserEntity userentform = new UserEntity();
			userentform.setLogId(userinput.getLogId());
			userentform.setPass(userinput.getPass1());
			userentform.setType(userinput.getType());
			userentform.setName(userinput.getName());
			dao.insertDb_login(userentform);
			return "register1";
		} else {
			model.addAttribute("mes1", "パスワードが一致していません");
			return "signup1";
		}
	}
	//	　--------------------------------------------------------------------------------------------------------------

	//	顧客新規登録
	@RequestMapping("/signup2")
	public String signup(Model model, UserInput userinput) {
		return "signup2";
	}

	//	登録完了画面
	@RequestMapping("/register2")
	public String complete(@Validated UserInput userinput, Model model, BindingResult result) {
		if (result.hasErrors()) {
			return "signup2";
		}

		if (userinput.getPass1().equals(userinput.getPass2())) {
			UserEntity userentform = new UserEntity();
			userentform.setLogId(userinput.getLogId());
			userentform.setPass(userinput.getPass1());
			userentform.setName(userinput.getName());
			dao.insertDb_loginC(userentform);
			return "register2";
		} else {
			model.addAttribute("mes1", "パスワードが一致していません");
			return "signup2";
		}
	}

	//	③--------------------------------------------------------------------------------------------------------------

	@RequestMapping("/merchant")
	public String merchant(Model model) {
		return "merchant";
	}

	//	④--------------------------------------------------------------------------------------------------------------
	@RequestMapping("/addhome")
	public String addhome(Model model, Input input) {
		//		Entity ent = new Entity();
		//		ent.setName(input.getName());
		//		ent.setSpace(input.getSpace());
		//		ent.setMoney(input.getMoney());
		//		ent.setAddress(input.getAddress());
		//		ent.setComment(input.getComment());
		//		dao.insertDb_addhome(ent);
		return "addhome";
	}

	@RequestMapping("/addhome2")
	public String addhome2(@Validated Input input, Model model) {
		Entity ent = new Entity();
		ent.setName(input.getName());
		ent.setSpace(input.getSpace());
		ent.setMoney(input.getMoney());
		ent.setAddress(input.getAddress());
		ent.setComment(input.getComment());
		dao.insertDb_addhome(ent);
		return "redirect:/addhome";
	}

	//	⑤--------------------------------------------------------------------------------------------------------------
	@RequestMapping("/viewhome")
	public String viewhome(Input input, Model model) {
		List<Entity> list = dao.getBKN();
		model.addAttribute("dbList", list);
		return "viewhome";
	}

	@RequestMapping("/sample")
	public String sample(Model model) {
		return "sample";
	}

	@RequestMapping("/sample2")
	public String sample2(Model model) {
		return "sample2";
	}

	//検索
	@RequestMapping("/merchantsearch")
	public String Search(@RequestParam("bkname") String name ,@RequestParam("space") String space,
			@RequestParam("start") Integer start ,@RequestParam("end") Integer end,
			@RequestParam("place") String place ,@RequestParam("comment") String comment ,Model model) {
		System.out.println(comment);
		List<Entity> list = dao.getSearch(name,space,start,end,place,comment);
		LocalDate nowDate = LocalDate.now();
		
		model.addAttribute("nowDate", nowDate);
		model.addAttribute("bkname", name);
		model.addAttribute("space", space);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("place", place);
		model.addAttribute("comment", comment);
		model.addAttribute("dbList", list);
		return "merchantsearch";
	}
	
	//並び替え
	@RequestMapping("/sort")
	public String sort(@RequestParam("sort") String sort,@RequestParam("bkname") String name ,
			@RequestParam("space") String space,@RequestParam("start") Integer start ,
			@RequestParam("end") Integer end,@RequestParam("place") String place ,
			@RequestParam("comment") String comment ,Model model) {
		List<Entity> list = dao.getSort(sort,name,space,start,end,place,comment);
		LocalDate nowDate = LocalDate.now();
		
		model.addAttribute("nowDate", nowDate);
		model.addAttribute("dbList", list);
		model.addAttribute("sort", "昇順並び替え");
		model.addAttribute("sort", "降順並び替え");
		model.addAttribute("bkname", name);
		model.addAttribute("space", space);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("place", place);
		model.addAttribute("comment", comment);
		return "merchantsearch";
	}
	


	//削除
	@RequestMapping("/del/{id}")
	public String destory(@PathVariable Long id) {
		dao.deleteBKN(id);
		return "redirect:/viewhome";
	}

	//編集画面の表示
	@RequestMapping("/edithome/{id}")
	public String editView(@PathVariable Long id, Model model) {
		List<Entity> list = dao.getOne(id);
		Entity entity = list.get(0);
		model.addAttribute("entity", entity);
		model.addAttribute("title", "編集ページ");

		return "edithome";
	}

	//更新
	@RequestMapping("/edit/{id}/exe")
	public String editExe(@PathVariable Long id, Model model, Input input) {
		Entity entform = new Entity();
		System.out.println(input.getName());
		System.out.println(input.getSpace());
		System.out.println(input.getMoney());
		System.out.println(input.getAddress());
		System.out.println(input.getComment());

		entform.setName(input.getName());
		entform.setSpace(input.getSpace());
		entform.setMoney(input.getMoney());
		entform.setAddress(input.getAddress());
		entform.setComment(input.getComment());

		dao.updateSample(id, entform);

		return "redirect:/viewhome";
	}

	//	⑦--------------------------------------------------------------------------------------------------------------

	@RequestMapping("/customermenu")
	public String customermenu(Model model) {
		return "customermenu";
	}

	//__________________________________________

	@RequestMapping("/chat")
	public String chat(ChatInput chatinput, Model model) {
		List<ChatEntity> list = dao.getChat();
		model.addAttribute("dbList", list);
		return "chat";
	}

	@RequestMapping("/addchat")
	public String addchat(@Validated ChatInput chatinput, Model model) {
		ChatEntity chatent = new ChatEntity();
		chatent.setChat(chatinput.getChat());
		chatent.setName(chatinput.getName());
		dao.insertDb_addchat(chatent);
		return "redirect:/chat";
	}
	
}
