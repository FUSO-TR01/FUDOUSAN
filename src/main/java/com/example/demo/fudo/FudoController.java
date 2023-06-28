package com.example.demo.fudo;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
import com.example.demo.dao.Formdao;
import com.example.demo.entity.ChatEntity;
import com.example.demo.entity.Entity;
import com.example.demo.entity.Forment;
import com.example.demo.entity.UserEntity;

@Controller
public class FudoController {

	private final Dao dao;
	private final Formdao formdao;

	@Autowired
	public FudoController(Dao dao, Formdao formdao) {
		this.dao = dao;
		this.formdao = formdao;
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
		UserEntity user = Dao.findByUsername1(LogId);

		if (user != null && user.getPass().equals(pass)) {
			// ログイン成功の処理
			model.addAttribute("result", "ログイン成功");
			model.addAttribute("logId", LogId);
			return "merchant"; // タスク管理画面にリダイレクト
		} else {
			// ログイン失敗の処理
			model.addAttribute("result", "ユーザー名またはパスワードが間違っています");
			return "login1";
		}
	}

	//	　--------------------------------------------------------------------------------------------------------------
	//	ログイン画面２(顧客ログイン)
	@GetMapping("/login2")
	public String showLoginForm2(Model model) {
		return "login2";
	}

	@PostMapping("/login2")
	public String login2(Model model, @RequestParam("logId2") String LogId,
			@RequestParam("pass2") String pass) {
		UserEntity user = Dao.findByUsername2(LogId);
		if (user != null && user.getPass().equals(pass)) {
			// ログイン成功の処理
			model.addAttribute("result", "ログイン成功");
			model.addAttribute("logId", LogId);
			return "customermenu"; // タスク管理画面にリダイレクト
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
		ent.setMoney(BigDecimal.valueOf(input.getMoney()));
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

	@RequestMapping("/form2")
	public String form2(Model model, FormInput forminput) {
		return "form2";
	}

	@RequestMapping("/news")
	public String news(Input input, Model model) {
		List<Entity> list = dao.getBKN();
		model.addAttribute("dbList", list);
		return "news";
	}

	@RequestMapping("/intro")
	public String intro(Model model) {
		return "intro";
	}
	
	@RequestMapping("/viewchat")
	public String viewcht(Model model) {
		return "viewchat";
	}

	//検索
	@RequestMapping("/merchantsearch")
	public String Search(@RequestParam("bkname") String name, @RequestParam("space") String space,
			@RequestParam("start") Integer start, @RequestParam("end") Integer end,
			@RequestParam("place") String place, @RequestParam("comment") String comment, Model model) {
		System.out.println(comment);
		List<Entity> list = dao.getSearch(name, space, start, end, place, comment);
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
	public String sort(@RequestParam("sort") String sort, @RequestParam("bkname") String name,
			@RequestParam("space") String space, @RequestParam("start") Integer start,
			@RequestParam("end") Integer end, @RequestParam("place") String place,

			@RequestParam("comment") String comment, @RequestParam("type") String type, Model model) {
		List<Entity> list = dao.getSort(sort, name, space, start, end, place, comment);
		LocalDate nowDate = LocalDate.now();

		if (sort.equals("ASC")) {
			model.addAttribute("sort", "昇順並び替え");
		}
		if (sort.equals("DESC")) {
			model.addAttribute("sort", "降順並び替え");
		}
		model.addAttribute("nowDate", nowDate);
		model.addAttribute("dbList", list);
		model.addAttribute("bkname", name);
		model.addAttribute("space", space);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("place", place);
		model.addAttribute("comment", comment);

		if (type.equals("customer")) {
			return "customersearch";
		}
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
		entform.setMoney(BigDecimal.valueOf(input.getMoney()));
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

	//	⑧--------------------------------------------------------------------------------------------------------------
	@RequestMapping("/viewhome2")
	public String viewhome2(Input input, Model model) {
		return "viewhome2";
	}

	@RequestMapping("/customersearch")
	public String Search2(@RequestParam("bkname") String name, @RequestParam("space") String space,
			@RequestParam("start") Integer start, @RequestParam("end") Integer end,
			@RequestParam("place") String place, @RequestParam("comment") String comment, Model model) {
		System.out.println(comment);
		List<Entity> list = dao.getSearch(name, space, start, end, place, comment);
		LocalDate nowDate = LocalDate.now();

		model.addAttribute("nowDate", nowDate);
		model.addAttribute("bkname", name);
		model.addAttribute("space", space);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("place", place);
		model.addAttribute("comment", comment);
		model.addAttribute("dbList", list);
		return "customersearch";
	}

	//__________________________________________

	@RequestMapping("/chat")
	public String chat(ChatInput chatinput, @RequestParam("logId") String logId,
			@RequestParam("tp") String tp, Model model) {
		List<ChatEntity> list = dao.getChatmem(tp);
		model.addAttribute("dbList", list);
		model.addAttribute("logId", logId);
		model.addAttribute("tp", tp);
		if (tp.equals("merchant")) {
			return "merchantchat";
		} else {
			return "customerchat";
		}
	}

	@RequestMapping("/displaychat")
	public String displayChat(@RequestParam("memname") String memname,
			@RequestParam("Id") Integer id, @RequestParam("logId") String logId, @RequestParam("tp") String tp,
			 Model model) {
		
		List<ChatEntity> list = dao.getChatsearchmem(tp, memname);
		List<ChatEntity> chatlist = dao.getStartchat(tp, memname, logId, id);
		model.addAttribute("chatList", chatlist);
		model.addAttribute("dbList", list);
		model.addAttribute("Id", id);
		model.addAttribute("logId", logId);
		model.addAttribute("tp", tp);
		model.addAttribute("memname", memname);
		String toname = dao.toname(id, tp);
		model.addAttribute("toname", toname);

		if (tp.equals("merchant")) {
			return "viewchat";
		} else {
			return "viewchat2";
		}
	}

	@RequestMapping("/memsearch")
	public String memsearch(ChatInput chatinput, @RequestParam("memname") String memname,
			@RequestParam("logId") String logId, @RequestParam("tp") String tp, Model model) {
		List<ChatEntity> list = dao.getChatsearchmem(tp, memname);
		model.addAttribute("dbList", list);
		model.addAttribute("logId", logId);
		model.addAttribute("tp", tp);
		model.addAttribute("memname", memname);
		if (tp.equals("merchant")) {
			return "merchantchat";
		} else {
			return "customerchat";
		}
	}

	@RequestMapping("/startchat")
	public String startchat(ChatInput chatinput, @RequestParam("memname") String memname,
			@RequestParam("Id") Integer id,
			@RequestParam("logId") String logId, @RequestParam("tp") String tp, Model model) {
		List<ChatEntity> list = dao.getChatsearchmem(tp, memname);
		List<ChatEntity> chatlist = dao.getStartchat(tp, memname, logId, id);
		model.addAttribute("chatList", chatlist);
		model.addAttribute("dbList", list);
		model.addAttribute("Id", id);
		model.addAttribute("logId", logId);
		model.addAttribute("tp", tp);
		model.addAttribute("memname", memname);
		String toname = dao.toname(id, tp);
		model.addAttribute("toname", toname);
		if (tp.equals("merchant")) {
			return "merchantchat";
		} else {
			return "customerchat";
		}
	}

	@RequestMapping("/addchat")
	public String addchat(ChatInput chatinput, @RequestParam("Message") String message,
			@RequestParam("memname") String memname,
			@RequestParam("Id") Integer id, @RequestParam("logId") String logId, @RequestParam("tp") String tp,
			Model model) throws UnsupportedEncodingException {
		System.out.println(memname);
		dao.insertDb_addchat(tp, message, logId, id);
		String encodedMemname = URLEncoder.encode(memname, StandardCharsets.UTF_8.toString());
		String redirectUrl = "/startchat?Id=" + id + "&logId=" + logId + "&memname=" + encodedMemname + "&tp=" + tp;
		return "redirect:" + redirectUrl;
		}

	//	___________________________________

	@RequestMapping("/checkform")
	public String checkform(@Validated FormInput input, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "form";
		}

		return "checkform";
	}

	@RequestMapping("/compform")
	public String compform(Model model, FormInput input) {

		LocalDate nowDate = LocalDate.now();
		System.out.println(nowDate);

		Forment entform = new Forment();
		entform.setName(input.getName());
		entform.setMail(input.getMail());
		entform.setMessage(input.getMessage());
		entform.setType(input.getType());
		entform.setNowDate(nowDate);

		if (!isDuplicateEntry(entform, input)) {
			Formdao.insertDb(entform);
		}

		List<Forment> list = formdao.getSelect(input.getName());
		model.addAttribute("dbList", list);
		System.out.println("データ取得");

		return "compform";
	}

	private boolean isDuplicateEntry(Forment entform, FormInput input) {
		List<Forment> list = formdao.getSelect(input.getName());

		for (Forment existingForm : list) {
			if (existingForm.getName().equals(entform.getName()) && existingForm.getMail().equals(entform.getMail()) &&
					existingForm.getMessage().equals(entform.getMessage())
					&& existingForm.getType().equals(entform.getType())) {
				// 既存のデータと入力データが重複する場合はtrueを返す
				return true;
			}
		}

		// 重複しない場合はfalseを返す
		return false;
	}

	//	__________________________________

	@RequestMapping("/sampleview")
	public String sampleview(Input input, Model model) {
		List<Entity> list = dao.getBKN();
		model.addAttribute("dbList", list);
		return "sampleview";
	}
	
	@RequestMapping("/gomo")
	public String gomo(Input input, Model model) {
		List<Entity> list = dao.getBKN();
		model.addAttribute("dbList", list);
		return "gomo";
	}
}
