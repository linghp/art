package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * 我的订单
 * 
 * @author Administrator
 *
 */
public class MyOrderItem_all implements Serializable {
private List<LinkedHashMap<String, MyOrderItem>> result;

public List<LinkedHashMap<String, MyOrderItem>> getResult() {
	return result;
}

public void setResult(List<LinkedHashMap<String, MyOrderItem>> result) {
	this.result = result;
}

@Override
public String toString() {
	return "MyOrderItem_all [result=" + result + "]";
}

}