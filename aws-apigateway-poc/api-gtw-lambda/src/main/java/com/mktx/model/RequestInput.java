package com.mktx.model;

public class RequestInput {

	private String action;
	private String data;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "RequestInput [action=" + action + ", data=" + data + "]";
	}
	
}
