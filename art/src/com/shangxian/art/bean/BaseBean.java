package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * 实体基类
 * 
 * @author libo
 * @param <T>
 */
public class BaseBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1743101768678073867L;

	@Expose
	private Integer result_code;

	@Expose
	private String reason;

	@Expose
	private T result;

	public Integer getResult_code() {
		return result_code;
	}

	public void setResult_code(Integer result_code) {
		this.result_code = result_code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public boolean isSuccess(){
		return result_code == 200;
	}
	
	@Override
	public String toString() {
		return "BaseBean [result_code=" + result_code + ", reason=" + reason
				+ ", result=" + result + "]";
	}

}
