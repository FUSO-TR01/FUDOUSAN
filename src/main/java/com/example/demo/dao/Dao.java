package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.entity.UserEntity;

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
}