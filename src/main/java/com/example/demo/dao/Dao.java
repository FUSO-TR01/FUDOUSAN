package com.example.demo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ChatEntity;
import com.example.demo.entity.Entity;
import com.example.demo.entity.UserEntity;

@Repository
public class Dao {
	private static JdbcTemplate db = new JdbcTemplate();

	@Autowired
	public Dao(JdbcTemplate db) {
		Dao.db = db;
	}

	//業者ログイン
	public static UserEntity findByUsername1(String logId) {
		String sql = "SELECT * FROM login WHERE logId = ?";
		List<Map<String, Object>> rows = db.queryForList(sql, logId);

		if (!rows.isEmpty()) {
			Map<String, Object> userData = rows.get(0);
			UserEntity user = new UserEntity();
			user.setId((int) userData.get("id"));
			user.setLogId((String) userData.get("logId"));
			user.setPass((String) userData.get("pass"));
			return user;
		}

		return null;
	}

	//顧客ログイン
	public static UserEntity findByUsername2(String logId) {
		String sql = "SELECT * FROM loginC WHERE logId = ?";
		List<Map<String, Object>> rows = db.queryForList(sql, logId);

		if (!rows.isEmpty()) {
			Map<String, Object> userData = rows.get(0);
			UserEntity user = new UserEntity();
			user.setId((int) userData.get("id"));
			user.setLogId((String) userData.get("logId"));
			user.setPass((String) userData.get("pass"));
			return user;
		}

		return null;
	}

	public List<UserEntity> getAll2() {

		List<Map<String, Object>> userqueryResult = db.queryForList("SELECT * FROM login");
		List<UserEntity> userdataList = new ArrayList<UserEntity>();

		for (Map<String, Object> record : userqueryResult) {
			UserEntity userentformItem = new UserEntity();

			userentformItem.setId((int) record.get("id"));

			userentformItem.setLogId((String) record.get("logId"));
			userentformItem.setPass((String) record.get("pass"));

			userdataList.add(userentformItem);
		}
		return userdataList;
	}

	public void insertDb_login(UserEntity userentform) {
		db.update("INSERT INTO login(logId,pass,type,name) VALUES(?,?,?,?)",
				userentform.getLogId(), userentform.getPass(), userentform.getType(), userentform.getName());
	}

	public void insertDb_loginC(UserEntity userentform) {
		db.update("INSERT INTO loginC(logId,pass,name) VALUES(?,?,?)",
				userentform.getLogId(), userentform.getPass(), userentform.getName());
	}

	//	________________________

	public List<Entity> getBKN() {

		List<Map<String, Object>> queryResult = db.queryForList("SELECT * FROM home");
		List<Entity> dataList = new ArrayList<Entity>();

		for (Map<String, Object> bkn : queryResult) {
			Entity entdb = new Entity();

			entdb.setId((int) bkn.get("id"));

			entdb.setName((String) bkn.get("name"));
			entdb.setSpace((String) bkn.get("space"));
			entdb.setMoney((BigDecimal) bkn.get("money"));
			entdb.setAddress((String) bkn.get("address"));
			entdb.setComment((String) bkn.get("comment"));

			dataList.add(entdb);
		}
		return dataList;
	}

	public void insertDb_addhome(Entity ent) {
		db.update("INSERT INTO home(name,space,money,address,comment) VALUES(?,?,?,?,?)",
				ent.getName(), ent.getSpace(), ent.getMoney(), ent.getAddress(), ent.getComment());
	}

	//削除
	public void deleteBKN(Long id) {
		System.out.println("削除しました");
		db.update("delete from home where id=?", id);
	}

	//編集
	public List<Entity> getOne(Long id) {
		System.out.println("編集画面を出します");

		List<Map<String, Object>> queryResult = db.queryForList("SELECT * FROM home where id=?", id);
		List<Entity> dataList = new ArrayList<Entity>();

		for (Map<String, Object> bkn : queryResult) {
			Entity entdb = new Entity();

			entdb.setId((int) bkn.get("id"));

			entdb.setName((String) bkn.get("name"));
			entdb.setSpace((String) bkn.get("space"));
			entdb.setMoney((BigDecimal) bkn.get("money"));
			entdb.setAddress((String) bkn.get("address"));
			entdb.setComment((String) bkn.get("comment"));

			dataList.add(entdb);
		}
		return dataList;
	}
	

	public void updateSample(Long id, Entity entity) {
		System.out.println("編集の実行");
		db.update("UPDATE home SET name=?,space=?,money = ?,address = ?,comment = ? WHERE id = ?",
				entity.getName(), entity.getSpace(), entity.getMoney(), entity.getAddress(), entity.getComment(), id);
	}

	//	________________________________________________________________________

	public List<ChatEntity> getChatmem(String tp) {

		List<Map<String, Object>> queryResult = null;
		List<ChatEntity> dataList = new ArrayList<ChatEntity>();
		if (tp.equals("merchant")) {
			queryResult = db.queryForList("SELECT id,name FROM LOGINC ");
		}
		if (tp.equals("customer")) {
			queryResult = db.queryForList("SELECT id,name FROM LOGIN ");
		}

		for (Map<String, Object> mem : queryResult) {
			ChatEntity entdb = new ChatEntity();
			entdb.setId((int) mem.get("id"));
			entdb.setName((String) mem.get("name"));
			dataList.add(entdb);
		}
		return dataList;
	}

	public static String memsql(String tp, String memname) {
		String memsql = "SELECT id, name FROM";
		String pattern = "'%" + memname + "%'";
		if (tp.equals("merchant")) {
			memsql = memsql + " LOGINC WHERE name LIKE " + pattern;
		}
		if (tp.equals("customer")) {
			memsql = memsql + " LOGIN WHERE name LIKE " + pattern;
		}
		return memsql;
	}

	public List<ChatEntity> getChatsearchmem(String tp, String memname) {
		List<Map<String, Object>> queryResult = null;
		List<ChatEntity> dataList = new ArrayList<ChatEntity>();
		String memsql = memsql(tp, memname);
		queryResult = db.queryForList(memsql);
		for (Map<String, Object> mem : queryResult) {
			ChatEntity entdb = new ChatEntity();
			entdb.setId((int) mem.get("id"));
			entdb.setName((String) mem.get("name"));
			dataList.add(entdb);
		}
		return dataList;
	}
	
	public static String chatresultsql(String logId, String toId) {

		String chatsql = "SELECT * FROM chat WHERE (logId = '"+logId+"' AND toId = '"+toId+"') OR (logId = '"+toId+"' AND toId = '"+logId+"')";
		return chatsql;
	}
	
	public static String toId(Integer id, String tp) {
		List<Map<String, Object>> queryTo = null;
		String toId=null;
		String to = "SELECT logId FROM";
		if (tp.equals("merchant")) {
			to = to + " LOGINC WHERE id = " + id;
		}
		if (tp.equals("customer")) {
			to = to + " LOGIN WHERE id = " + id;
		}
		queryTo = db.queryForList(to);
		for (Map<String, Object> map : queryTo) {
		    toId = ((String) map.get("logId"));
		}
		return toId;	
	}
	
	public String toname(Integer id, String tp) {
		List<Map<String, Object>> queryTo = null;
		String toname=null;
		String to = "SELECT name FROM";
		if (tp.equals("merchant")) {
			to = to + " LOGINC WHERE id = " + id;
		}
		if (tp.equals("customer")) {
			to = to + " LOGIN WHERE id = " + id;
		}
		queryTo = db.queryForList(to);
		for (Map<String, Object> map : queryTo) {
		    toname = ((String) map.get("name"));
		}
		return toname;	
	}
	
	public List<ChatEntity> getStartchat(String tp, String memname,String logId, Integer id) {
		
		List<Map<String, Object>> queryChat = null;
		List<ChatEntity> chatList = new ArrayList<ChatEntity>();
		String toId = toId(id, tp);
//		System.out.println(logId);
//		System.out.println(toId);
		String chatsql = chatresultsql(logId,toId);
		queryChat = db.queryForList(chatsql);
		for (Map<String, Object> mem : queryChat) {
			ChatEntity entdb = new ChatEntity();
			entdb.setLogId((String) mem.get("logId"));
//			System.out.println((String) mem.get("logId"));
			entdb.setToId((String) mem.get("toId"));
			entdb.setName((String) mem.get("name"));
			entdb.setToname((String) mem.get("toname"));
			entdb.setChat((String) mem.get("chat"));
			entdb.setChatC((String) mem.get("chatC"));

			chatList.add(entdb);
		}		
		return chatList;
	}

	public void insertDb_addchat(String tp,String message,String logId,Integer id) {
		String toId = toId(id, tp);
		String addsql = "INSERT INTO chat(logId,toId,chat,chatC,name,toname) VALUES(?,?,?,?,?,?)";
		if (tp.equals("merchant")) {
			db.update(addsql,logId,toId,message,"","","");
		}
		if (tp.equals("customer")) {
			db.update(addsql,logId,toId,"",message,"","");
		}		
	}

	//sql生成
	public static String sql(String name, String space, Integer start, Integer end, String place, String comment) {
		String sql = "SELECT * FROM home WHERE";
		if (!name.isEmpty()) {
			String SHname = "'%" + name + "%'";
			sql = sql + " name LIKE " + SHname + " AND";
		}
		if (!space.isEmpty()) {
			sql = sql + " space = '" + space + "' AND";
		}
		if (start != 0) {
			sql = sql + " money BETWEEN " + start + " AND ";
		} else {
			sql = sql + " money BETWEEN (SELECT MIN(money) FROM home) AND ";
		}
		if (end != 0) {
			sql = sql + end + " AND";
		} else {
			sql = sql + " (SELECT MAX(money) FROM home) AND";
		}
		if (!place.isEmpty()) {
			String SHplace = "'%" + place + "%'";
			sql = sql + " address LIKE " + SHplace + " AND";
		}
		if (!comment.isEmpty()) {
			String[] comments = comment.split(",");
			for (String word : comments) {
				String SHcomment = "'%" + word + "%'";
				sql = sql + " comment LIKE " + SHcomment + " AND";
			}
		}
		sql = sql.substring(0, sql.length() - 4);
		System.out.println(sql);

		return sql;
	}

	//検索
	public List<Entity> getSearch(String name, String space, Integer start, Integer end, String place, String comment) {

		String sql = sql(name, space, start, end, place, comment);
		List<Map<String, Object>> queryResult = db.queryForList(sql);
		List<Entity> dataList = new ArrayList<Entity>();

		for (Map<String, Object> bkn : queryResult) {
			Entity entdb = new Entity();
			entdb.setId((int) bkn.get("id"));
			entdb.setName((String) bkn.get("name"));
			entdb.setSpace((String) bkn.get("space"));
			entdb.setMoney((BigDecimal) bkn.get("money"));
			entdb.setAddress((String) bkn.get("address"));
			entdb.setComment((String) bkn.get("comment"));
			dataList.add(entdb);
		}
		return dataList;
	}

	//業者ソート
	public List<Entity> getSort(String sort, String name, String space, Integer start, Integer end, String place,
			String comment) {

		String sql = sql(name, space, start, end, place, comment);
		List<Map<String, Object>> queryResult = db.queryForList(sql);
		List<Entity> dataList = new ArrayList<Entity>();
		if (sort.equals("ASC")) {
			sql = sql + " ORDER BY money ASC";
			queryResult = db.queryForList(sql);
			System.out.println("昇順ソート");
		}
		if (sort.equals("DESC")) {
			sql = sql + " ORDER BY money DESC";
			queryResult = db.queryForList(sql);
			System.out.println("降順ソート");
		}
		for (Map<String, Object> bkn : queryResult) {
			Entity entdb = new Entity();
			entdb.setId((int) bkn.get("id"));
			entdb.setName((String) bkn.get("name"));
			entdb.setSpace((String) bkn.get("space"));
			entdb.setMoney((BigDecimal) bkn.get("money"));
			entdb.setAddress((String) bkn.get("address"));
			entdb.setComment((String) bkn.get("comment"));
			dataList.add(entdb);
		}
		return dataList;
	}

}