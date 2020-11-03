package com.lw.clouddelivery.util;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;
public class JsonBase<T> implements Serializable{
	
	private static final long serialVersionUID = -6182189632617616248L;
	
	@SerializedName("code")
	private String code; //状态描述
	@SerializedName("msg")
	private String msg;//code说明字符串
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
