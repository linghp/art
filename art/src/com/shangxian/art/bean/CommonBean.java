package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * 通用
 * 
 * @author Administrator
 *
 */
public class CommonBean<T> implements Serializable {
	@Expose
	private String result_code;

	@Expose
	private String reason;

	@Expose
	private String result;

	@Expose
	private T object;

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "CommonBean [result_code=" + result_code + ", reason=" + reason
				+ ", result=" + result + ", object=" + object + "]";
	}

}
