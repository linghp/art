package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * 通用
 * 
 * @author Administrator
 *
 */
public class CommonBeanObject<T> implements Serializable {
	@Expose
	private String result_code="";

	@Expose
	private String reason="";

	@Expose
	private T object;

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "CommonBeanObject [result_code=" + result_code + ", reason="
				+ reason + ", object=" + object + "]";
	}



}
