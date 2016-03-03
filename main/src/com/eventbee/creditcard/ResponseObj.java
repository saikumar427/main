package com.eventbee.creditcard;

import java.util.HashMap;


public class  ResponseObj{

	public String status_code =null;
	public String auth_code= null;
	public String decline_code=null;
	public String order_number=null;
	public String cardvendor=null;
	public String message=null;
	public Object data=null;

	public boolean status=false;

	public HashMap hm=new HashMap();

	public ResponseObj(){

		hm.put("PAYPALPRO_STATUS_M","A");
		hm.put("PAYPALPRO_STATUS_E","R");
		hm.put("openecho_STATUS_G","A");
		hm.put("openecho_STATUS_D","R");
		hm.put("SKIPJACK_STATUS_1","A");//failure
		hm.put("SKIPJACK_STATUS_0","R");//success
		hm.put("SKIPJACK_STATUS_2","C");//for AVS rejection
		hm.put("_STATUS_R","R");

		hm.put("LOCAL_STATUS_1","A");//success for local testing
		hm.put("LOCAL_STATUS_0","R");//failure for local testing


	}

	public void setData(String auth_code,String decline_code,String status_code,String order_number,String cardvendor){
		this.auth_code=auth_code;
		this.decline_code=decline_code;
		this.status_code=status_code;
		this.order_number=order_number;
		this.cardvendor=cardvendor;

	}

	public String getStatus_code(){
		System.out.println("status_code---"+status_code);
			if("Success".equals(status_code)){
			return "Success";
		}
		else if("Fail".equals(status_code))
		return "R";
		else{
			return "F";
		}
	}
	public String getAuth_code() {
		return auth_code;
	}

	public String getDecline_code() {
		return decline_code;
	}

	public String getOrder_number() {
		return order_number;
	}


	public boolean getStatus(){
			return "Success".equals(getStatus_code());
	}

	public static void main(String args[]){
		ResponseObj robj=new ResponseObj();
		robj.setData("INVALID","0","0","1","LOCAL");


	}


}