package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * 通用
 * 
 * @author Administrator
 *
 */
public class CommonBeanBoolean implements Serializable {
	@Expose
	private String result_code="";

	@Expose
	private String reason="";

	@Expose
	private boolean result;

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

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "CommonBeanBoolean [result_code=" + result_code + ", reason="
				+ reason + ", result=" + result + "]";
	}


}
