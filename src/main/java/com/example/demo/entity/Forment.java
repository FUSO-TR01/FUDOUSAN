package com.example.demo.entity;

import java.time.LocalDate;

public class Forment {
	private int id;
	private String Name;
	private String Mail;
	private String Type;
	private String Message;
	private LocalDate NowDate;
	private LocalDate Date;

	public Forment() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getType() {
		return Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String Message) {
		this.Message = Message;
	}

	public LocalDate getNowDate() {
		return NowDate;
	}

	public void setNowDate(LocalDate NowDate) {
		this.NowDate = NowDate;
	}

	public LocalDate getDate() {
		return Date;
	}

	public void setDate(LocalDate Date) {
		this.Date = Date;
	}

	public String getMail() {
		return Mail;
	}

	public void setMail(String mail) {
		Mail = mail;
	}

}
