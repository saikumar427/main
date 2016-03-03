package com.eventbee.general;


public class  StatusObj{
	private boolean status = false;
	private String errorMsg = null;
	private Object data = null;
	private int count =  0;
	   
	public StatusObj(boolean status, String msg, Object data) {
		this(status, msg, data, 0);
	}
	public StatusObj(boolean status, String msg, Object data, int count) {
		this.status = status;
		this.data = data;
		this.errorMsg = msg;
		this.count = count;
	}
	public int getCount(){
		return count;
	}
	public void setCount(int count){
		this.count = count;
	}
	public boolean getStatus(){
		return status;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public Object getData() {
		return data;
	}
	public void setStatus(boolean status){
		this.status = status;
	}
	public void setErrorMsg(String msg) {
		this.errorMsg = msg;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
}