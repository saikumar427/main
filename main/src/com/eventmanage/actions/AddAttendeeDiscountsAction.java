package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.event.dbhelpers.AddAttendeeDiscountsDB;
import com.event.dbhelpers.DisplayAttribsDB;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class AddAttendeeDiscountsAction extends ActionSupport implements Preparable,ValidationAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5619788765542414959L;
	//------------------------------------------Properties-----------------------
	String eid="";
	String code="";
	String msgKey="";
	String msg="";
	//------------------------------------------Setters and Getters-------------
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	//------------------------------------------Action Methods-----------------
	public String execute(){
		JSONObject discountsMsgObject=new JSONObject();
		JSONObject jsonresponseobj=new JSONObject();
		JSONArray discountsArray=new JSONArray();
		code=code.trim();
		HashMap DiscountLabels=DisplayAttribsDB.getAttribValues(eid,"RegFlowWordings");
		HashMap discountinfomap=AddAttendeeDiscountsDB.getDiscountInfo(code, eid, "",DiscountLabels);
		String discountMsg=(String)discountinfomap.get("message");
		ArrayList eventTickets=(ArrayList)discountinfomap.get("discountedtickets");
		try{
		discountsMsgObject.put("validdiscount","N");
		if(eventTickets!=null&&eventTickets.size()>0){
			for(int i=0;i<eventTickets.size();i++){
			JSONObject DiscounMap =new JSONObject();
			HashMap hm=(HashMap)eventTickets.get(i);
			DiscounMap.put("ticketid",(String)hm.get("ticketid"));
			DiscounMap.put("final_price",(String)hm.get("final_price"));
			DiscounMap.put("discount",(String)hm.get("discount"));
			DiscounMap.put("price",(String)hm.get("price"));
			DiscounMap.put("haveDiscount",(String)hm.get("haveDiscount"));
			DiscounMap.put("isdonation",(String)hm.get("isdonation"));
			discountsArray.put(DiscounMap);
		       }
		discountsMsgObject.put("validdiscount","Y");
		}
		discountsMsgObject.put("IsCouponsExists","Y");
		discountsMsgObject.put("discountapplied","Y");
		if("".equals(code)) discountMsg="";
		discountsMsgObject.put("discountmsg",discountMsg);
		discountsMsgObject.put("discountcode",code);
		jsonresponseobj.put("discountedprices",discountsArray);
		jsonresponseobj.put("discounts",discountsMsgObject);
		msg=jsonresponseobj.toString();
		}
		catch(Exception e){
			
		}
		return "ajaxjson";
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
