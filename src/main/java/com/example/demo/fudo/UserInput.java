package com.example.demo.fudo;

import jakarta.validation.constraints.NotBlank;

public class UserInput {

	@NotBlank(message ="バリデーション")
	private String logId;

	@NotBlank(message ="バリデーション")
	private String pass1;

	@NotBlank(message ="バリデーション")
	private String pass2;

//	@NotBlank(message ="バリデーション")
	private String type;

	@NotBlank(message ="バリデーション")
	private String name;
	
	public UserInput() {
		
	}
	
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}
	
	public String getPass1() {
		return pass1;
	}

	public void setPass1(String pass1) {
		this.pass1 = pass1;
	}

	public String getPass2() {
		return pass2;
	}

	public void setPass2(String pass2) {
		this.pass2 = pass2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
