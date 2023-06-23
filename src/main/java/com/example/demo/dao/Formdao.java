package com.example.demo.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Forment;
@Repository
public class Formdao {
	private static JdbcTemplate db = new JdbcTemplate();

	@Autowired
	public Formdao(JdbcTemplate db) {
		Formdao.db = db;
	}

	public List<Forment> getSelect(String Name) {
		List<Map<String, Object>> queryResult = db.queryForList(
				"SELECT * FROM INQUIRY WHERE Name = ? ORDER BY Date DESC LIMIT 5", Name);
		List<Forment> dataList = new ArrayList<Forment>();

		for (Map<String, Object> record : queryResult) {
			Forment entformItem = new Forment();

			entformItem.setId((int) record.get("id"));

			Date sqlDate = (Date) record.get("Date");
			LocalDate localDate = sqlDate.toLocalDate();
			entformItem.setDate(localDate);
			entformItem.setMail((String) record.get("Mail"));
			entformItem.setType((String) record.get("Type"));
			entformItem.setName((String) record.get("Name"));
			entformItem.setMessage((String) record.get("Message"));

			dataList.add(entformItem);
		}
		return dataList;
	}

	public static void insertDb(Forment entform) {

		db.update("INSERT INTO INQUIRY(Name,Mail,Type,Message,Date) VALUES(?,?,?,?,?)",
				entform.getName(), entform.getMail(), entform.getType(), entform.getMessage(), entform.getNowDate());

	}
}
