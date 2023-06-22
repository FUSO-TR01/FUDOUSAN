package com.example.demo.dao;

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

	//ログイン
	public static UserEntity findByUsername(String logId) {
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
			entdb.setMoney(Integer.parseInt((String) bkn.get("money")));
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
			entdb.setMoney(Integer.parseInt((String) bkn.get("money")));
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

	//	____________________________

	public List<ChatEntity> getChat() {

		List<Map<String, Object>> queryResult = db.queryForList("SELECT * FROM chat");
		List<ChatEntity> dataList = new ArrayList<ChatEntity>();

		for (Map<String, Object> bkn : queryResult) {
			ChatEntity entdb = new ChatEntity();

			entdb.setId((int) bkn.get("id"));

			entdb.setChat((String) bkn.get("chat"));

			entdb.setName((String) bkn.get("name"));

			dataList.add(entdb);
		}
		return dataList;
	}

	public void insertDb_addchat(ChatEntity chatent) {
		db.update("INSERT INTO chat(chat,name) VALUES(?,?)",
				chatent.getChat(), chatent.getName());
	}

	//sql生成
	public static String sql(String name, String space, Integer start, Integer end, String place,String comment) {
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

		return sql;
	}

	//検索
	public List<Entity> getSearch(String name, String space, Integer start, Integer end, String place, String comment) {

		String sql = sql(name, space, start, end, place,comment);
		List<Map<String, Object>> queryResult = db.queryForList(sql);
		List<Entity> dataList = new ArrayList<Entity>();

		for (Map<String, Object> bkn : queryResult) {
			Entity entdb = new Entity();
			entdb.setId((int) bkn.get("id"));
			entdb.setName((String) bkn.get("name"));
			entdb.setSpace((String) bkn.get("space"));
			entdb.setMoney(Integer.parseInt((String) bkn.get("money")));
			entdb.setAddress((String) bkn.get("address"));
			entdb.setComment((String) bkn.get("comment"));
			dataList.add(entdb);
		}
		return dataList;
	}
	
	//業者ソート
		public List<Entity> getSort(String sort,String name, String space, Integer start, Integer end, String place, String comment) {

			String sql = sql(name, space, start, end, place,comment);
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
				entdb.setMoney(Integer.parseInt((String) bkn.get("money")));
				entdb.setAddress((String) bkn.get("address"));
				entdb.setComment((String) bkn.get("comment"));
				dataList.add(entdb);
			}
			return dataList;
		}

}