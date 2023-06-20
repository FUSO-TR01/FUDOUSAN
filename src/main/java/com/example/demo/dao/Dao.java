package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
				entity.getName(),entity.getSpace(), entity.getMoney(), entity.getAddress(),entity.getComment(), id);
	}
}