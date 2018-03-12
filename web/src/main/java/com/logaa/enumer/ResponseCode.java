package com.logaa.enumer;

public enum ResponseCode {

	SUCCESS(200, "成功"),
	ERROR(201, "系统繁忙，请稍后再试");
	
	private int code;
	
	private String msg;
	
	
	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	ResponseCode(int code, String msg){
		this.code = code;
		this.msg = msg;
	}
}
