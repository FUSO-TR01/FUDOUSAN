package com.example.demo.fudo;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public class FormInput {
	@NotBlank(message ="お名前を入力してください")
	private String Name;

	@NotBlank(message ="メールアドレスを入力してください")
	private String Mail;
	
	@NotBlank(message ="種別を選択してください")
	private String Type;
  
	@NotBlank(message ="お問い合わせ内容を入力してください")
	private String Message;
	
	private static LocalDate dueDate;

	public FormInput() {
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
	
	public static  LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getMail() {
		return Mail;
	}

	public void setMail(String mail) {
		Mail = mail;
	}

}
