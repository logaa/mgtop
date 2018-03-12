package com.logaa.view;

import com.logaa.enumer.ResponseCode;

public class BaseResponse<T> {

	protected int code;
	
	protected String msg;
	
	protected T data;
	
	public BaseResponse(T data){
		this.code = ResponseCode.SUCCESS.getCode();
		this.msg = ResponseCode.SUCCESS.getMsg();
		this.data = data;
	}
	
	public BaseResponse(ResponseCode code, T data){
		this.code = code.getCode();
		this.msg = code.getMsg();
		this.data = data;
	}

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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
