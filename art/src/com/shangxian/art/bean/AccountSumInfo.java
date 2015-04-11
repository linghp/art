package com.shangxian.art.bean;

import java.io.Serializable;

/**
 * 爱农币、爱农元
 * @author libo
 * 
 */
public class AccountSumInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int _id;
	private double alb;
	private double aly;
	
	public AccountSumInfo() {
		super();
	}

	public AccountSumInfo(double alb, double aly) {
		super();
		this.alb = alb;
		this.aly = aly;
	}

	public AccountSumInfo(int _id, double alb, double aly) {
		super();
		this._id = _id;
		this.alb = alb;
		this.aly = aly;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public double getAlb() {
		return alb;
	}

	public void setAlb(double alb) {
		this.alb = alb;
	}

	public double getAly() {
		return aly;
	}

	public void setAly(double aly) {
		this.aly = aly;
	}

	@Override
	public String toString() {
		return "AccountSumInfo [_id=" + _id + ", alb=" + alb + ", aly=" + aly
				+ "]";
	}
	
	public boolean isNull(){
		return alb == Integer.MIN_VALUE || aly == Integer.MIN_VALUE;
	}
}
