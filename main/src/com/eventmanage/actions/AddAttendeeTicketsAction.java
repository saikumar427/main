package com.eventmanage.actions;



import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.eventbee.general.DbUtil;
import com.eventregister.dbhelper.RegistrationTiketingManager;
import com.opensymphony.xwork2.ActionSupport;

public class AddAttendeeTicketsAction extends ActionSupport{
String eid="";
String tid=null;
String jsondata=" ";
String resultobj="";
String amountsobj="";
String eventdate="";
String selseatsjson="";
String discountcode="";
public String getSelseatsjson() {
	return selseatsjson;
}

public void setSelseatsjson(String selseatsjson) {
	this.selseatsjson = selseatsjson;
}

public String getEventdate() {
	return eventdate;
}

public void setEventdate(String eventdate) {
	this.eventdate = eventdate;
}

RegistrationTiketingManager regTktMgr=new RegistrationTiketingManager();

public String getJsondata() {
	return jsondata;
}

public void setJsondata(String jsondata) {
	this.jsondata = jsondata;
}

public String getEid() {
	return eid;
}

public void setEid(String eid) {
	this.eid = eid;
}

public String getTid() {
	return tid;
}

public void setTid(String tid) {
	this.tid = tid;
}

public String 	execute(){
	ProcessTicketsData();
	return "Success";
	
}

public String getTotals(){
	ProcessTicketsData();
	HashMap amounts=regTktMgr.getRegTotalAmounts(tid);
	try{
	JSONObject jsonresponseobj=new JSONObject();
	jsonresponseobj.put("tid",tid);
	jsonresponseobj.put("eid",eid);
	regTktMgr.fillAmountDetails(amounts,jsonresponseobj,tid);
	amountsobj=jsonresponseobj.toString();
	}
	catch(Exception e){
		
	}
	return "totalscalculated";
}




public String getAmountsobj() {
	return amountsobj;
}

public void setAmountsobj(String amountsobj) {
	this.amountsobj = amountsobj;
}

void ProcessTicketsData(){
	System.out.println("AddAttendeeTicketsAction tid: "+tid+" eid: "+eid+" eventdate: "+eventdate);
try{
	HashMap contextdata=null;	
	HashMap ticketDetailsMap=regTktMgr.getTicketDetails(eid);
		discountcode=(discountcode==null)?"":discountcode;
	if(tid==null||"".equals(tid)){
		contextdata=new HashMap();
		contextdata.put("context","EB");
		contextdata.put("discountcode",discountcode);
		/*if(eventdate!=null&&!"".equals(eventdate))
			contextdata.put("eventdate",eventdate);*/	
		tid=regTktMgr.createNewTransaction(eid,contextdata);
	}else{
		contextdata=new HashMap();
		contextdata.put("discountcode",discountcode);
		regTktMgr.updateTransaction(tid,eid,contextdata);
	}
	
	if(eventdate != null && !"".equals(eventdate) && tid != null && !"".equals(tid))
		regTktMgr.updateTransactionEventdate(tid, eid, eventdate);
	
	JSONObject selectedseats=(JSONObject)new JSONTokener(selseatsjson).nextValue();
	JSONObject obj=new JSONObject();
	obj.put("tid",tid);
	resultobj=obj.toString();
	JSONArray ticketsarray=new JSONArray(jsondata);	
	regTktMgr.deleteTempDetails(tid);
	DbUtil.executeUpdateQuery("delete from event_reg_ticket_details_temp where tid=?",new String[]{tid});
	int disccount=0;
	if(ticketsarray!=null)	{
		for(int index=0;index<ticketsarray.length();index++){
			JSONObject ticketObj=(JSONObject)ticketsarray.get(index);
			HashMap<String,Object> ticketsMap=new HashMap();
			ticketsMap.put("tid",tid);
			ticketsMap.put("originalFee",(String)ticketObj.getString("originalfee"));
			ticketsMap.put("finalFee",(String)ticketObj.getString("finalfee"));
			ticketsMap.put("originalPrice",(String)ticketObj.getString("originalprice"));
			ticketsMap.put("finalPrice",(String)ticketObj.getString("finalprice"));
			ticketsMap.put("qty",(String)ticketObj.getString("qty"));
			ticketsMap.put("ticketid",(String)ticketObj.getString("ticketid"));
			ticketsMap.put("ticketGroupId",(String)ticketObj.getString("ticketGroupId"));
			ticketsMap.put("ticketType",(String)ticketObj.getString("ticketType"));
			if(ticketDetailsMap!=null&&ticketDetailsMap.size()>0){
				HashMap <String,String>priceDetails=(HashMap)ticketDetailsMap.get((String)ticketObj.getString("ticketid"));
				if(priceDetails!=null&&priceDetails.size()>0){
					ticketsMap.put("ticketName",priceDetails.get("ticketname"));
					ticketsMap.put("groupname",priceDetails.get("groupname"));
				}
				}
			try{
				JSONArray ja=selectedseats.getJSONArray((String)ticketObj.getString("ticketid"));
				ticketsMap.put("seat_index",ja);
				}
				catch(Exception ex){
				JSONArray ja=new JSONArray();
				ticketsMap.put("seat_index",ja);
				}
			double discount=0;
			try{
				if(Double.parseDouble((String)ticketObj.getString("originalprice"))<Double.parseDouble((String)ticketObj.getString("finalprice")))
					discount=0;
				else{				
				discount=Double.parseDouble((String)ticketObj.getString("originalprice"))-Double.parseDouble((String)ticketObj.getString("finalprice"));
				if(discount>0)
				disccount++;
				}
			}
			catch(Exception e){
				discount=0;
						}
			ticketsMap.put("discount",discount+"");
			ticketsMap.put("eventdate", eventdate);
			regTktMgr.InsertTicketDetailsToDb(ticketsMap,tid,eid);
			//System.out.println("tid: "+tid+" eid: "+eid);
			regTktMgr.setTransactionAmounts(eid,tid);
		}
		if(disccount==0) discountcode="";
		//if(!"".equals(discountcode))
			DbUtil.executeUpdateQuery("update event_reg_details_temp set discountcode=? where tid=?",new String[]{discountcode,tid});
	}
	
}
	catch(Exception e){
		System.out.println("exception is"+e.getMessage());	
		}
}

public String getResultobj() {
	return resultobj;
}

public void setResultobj(String resultobj) {
	this.resultobj = resultobj;
}

public String getDiscountcode() {
	return discountcode;
}

public void setDiscountcode(String discountcode) {
	this.discountcode = discountcode;
}

}
