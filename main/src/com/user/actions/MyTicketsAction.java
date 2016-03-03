package com.user.actions;

import java.util.HashMap;

import com.eventbee.beans.TransactionData;
import com.eventbee.general.DbUtil;
import com.mysettings.beans.AccountInfo;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.user.dbhelpers.MyTicketsActionDB;

public class MyTicketsAction extends ActionSupport implements Preparable,ValidationAware
{
	
	private static final long serialVersionUID = 1L;
	private String email_id="";
	private String transaction_id="";
	private String tid="";
	private String emid="";
	private String msgKey="";
	private String status="0";
	private TransactionData transactionData;
	
	
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	public String getEmid() {
		return emid;
	}
	public void setEmid(String emid) {
		this.emid = emid;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	
	public String execute(){
		return "";
	}
    public String getEmailId()
    {
    	/*String num=DbUtil.getVal("select count(*) from event_reg_transactions where paymentstatus='Completed' and email=?",new String[]{email_id});
    	int count=Integer.parseInt(num);*/
    	//if(count>0){	
    		HashMap<String,String> hmap=new HashMap<String,String>();
    		hmap=MyTicketsActionDB.getEmailTransactions(email_id);
    	    //status = String.valueOf(MyTicketsActionDB.getEmailTransactions(email_id));
    		status=hmap.get("status");
    		if(status.equals("3")){
    			String tid=hmap.get("tid");
    			String directmailstatus="";
    			if(!"".equals(tid)){
    				directmailstatus=String.valueOf(MyTicketsActionDB.resendDirectEmail(tid.trim()));
    			}
    		if(status.equals("3") && directmailstatus.equals("1")){
    			msgKey="tickets";
        		return "success";
    		}else{
    			msgKey="Mail sending failed";	
        		return "fail";
    		}
    		}
    		else if(status.equals("1")){
    			msgKey="transactionids";
    			return "success";
    		}else if(status.equals("2")){
    			msgKey="Please enter Email used in your registration";	
        		return "fail";
        	}
    		else{
    			msgKey="Mail sending failed";	
    			return "fail";
    		}
    }	
    public String getTransactionId(){
    	System.out.println("\n In MyTicketsAction getTransactionId method tid: "+transaction_id);
    	//String num=DbUtil.getVal("select count(*) from event_reg_transactions where tid=?",new String[]{transaction_id});
    	//int count=Integer.parseInt(num);
    	if(transaction_id != null && !"".equals(transaction_id.trim()))	
    		status = String.valueOf(MyTicketsActionDB.resendDirectEmail(transaction_id.trim()));
    	if(status.equals("1")){	
    		msgKey="tickets";
    		return "success";
    	}else if(status.equals("2")){
    		msgKey="Please enter valid Transaction ID";	
    		return "fail";
    	}else{
    		msgKey="Mail sending failed";	
    		return "fail";
    	}
   	}
    public String getticketsinfo(){
    	System.out.println("In MyTicketsAction getticketsinfo method tid: "+tid);
		if(tid != null && !tid.equals("")){
			status = String.valueOf(MyTicketsActionDB.resendDirectEmail(tid.trim()));
			transactionData = MyTicketsActionDB.getRegistrationDetails(tid.trim());
		}
    	return "ticketinfo";
    }
    
    public String getpdftickets(){
    	return "ticketinfo";
    }
    
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public TransactionData getTransactionData() {
		return transactionData;
	}
	public void setTransactionData(TransactionData transactionData) {
		this.transactionData = transactionData;
	}
	
}