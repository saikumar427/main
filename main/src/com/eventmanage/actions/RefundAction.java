package com.eventmanage.actions;

import java.util.HashMap;
import java.util.ResourceBundle;
import com.event.beans.ReportsData;
import com.event.beans.TransactionNotesData;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.RefundDB;
import com.event.dbhelpers.TransactionDB;
import com.event.helpers.I18n;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class RefundAction extends ActionSupport implements Preparable,ValidationAware{
	
	private String eid="";
	private String tid="";
	private String paymentType="";
	private String actualAmount="";
	private String refundAmount="";
	private HashMap<String, String> refundMap=new HashMap<String, String>();
	private String msgType="refundfail";
	private String currency="";
	private TransactionNotesData transactionNotesData;
	private ReportsData reportsData=new ReportsData();
	
	public ReportsData getReportsData() {
		return reportsData;
	}
	public void setReportsData(ReportsData reportsData) {
		this.reportsData = reportsData;
	}
	public TransactionNotesData getTransactionNotesData() {
		return transactionNotesData;
	}
	public void setTransactionNotesData(TransactionNotesData transactionNotesData) {
		this.transactionNotesData = transactionNotesData;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
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
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	
public String execute(){
		System.out.println("PaymentRefundAction eid: "+eid+" tid: "+tid);
		currency=EventDB.getCurrencySymbol(eid);
		refundMap=RefundDB.getRefundDetails(tid, eid);
		actualAmount=refundMap.get("actualAmount");
		System.out.println("PaymentRefundAction actualAmount: "+actualAmount);
		return "refund";
	}

public String submitRefund(){
	ResourceBundle resourceBundle =I18n.getResourceBundle();
	boolean status = validateInputData();
    if (status){
		HashMap<String, String> result=RefundDB.refund(tid, eid, refundAmount, refundMap);
		System.out.println("RefundAction eid: "+eid+" tid: "+tid+" status: "+result.get("status"));
		if(result!=null && result.get("status").equalsIgnoreCase("success")){
			RefundDB.updateRefund(tid, eid, refundAmount,refundMap.get("paymenttype"));
			
			if("Full".equals(result.get("refundtype"))){
				//TransactionDB.insertStatus() is also calling in TransactionDetailsAction.java
				reportsData.setChangeStatus("Refund");
				TransactionDB.insertStatus(eid,tid,reportsData);
			}
			msgType="refundsuccess";
			return "success";
		}else{
			String errorMsg="Refund could not be processed";
			if(result!=null && result.get("ErrorMsg")!=null && !result.get("ErrorMsg").contains("null") && !"".equals(result.get("ErrorMsg").trim()))
				errorMsg=result.get("ErrorMsg");
			//else errorMsg=result.get("ErrorMsg");
			addFieldError("refundAmount", errorMsg);
			return "fail";
		}
    } else return "fail";
	
}

public boolean validateInputData(){
	ResourceBundle resourceBundle =I18n.getResourceBundle();
	if("".equals(refundAmount)) {
		addFieldError("refundAmount",resourceBundle.getString("ref.amnt.emty.lbl"));
	}else{

		try{
			Double refundAmt = Double.parseDouble(refundAmount);
			if (refundAmt < 0){
				addFieldError("refundAmount",resourceBundle.getString("ref.inv.ref.amnt.lbl"));
			}
		}catch (NumberFormatException e){
			addFieldError("refundAmount",resourceBundle.getString("ref.amnt.num.lbl"));

		}catch (Exception e){
			addFieldError("refundAmount",resourceBundle.getString("ref.amnt.num.lbl"));
		}
	}
	if (getFieldErrors().isEmpty()){
		Double refundAmt = Double.parseDouble(refundAmount);
		Double actualAmt = Double.parseDouble(refundMap.get("actualAmount"));
		if(refundAmt>actualAmt)
			addFieldError("refundAmount",resourceBundle.getString("ref.amnt.grt.actl.amnt.lbl"));
	}
	if (!getFieldErrors().isEmpty()){
		return false;
	}
	return true;
}
public HashMap<String, String> getRefundMap() {
	return refundMap;
}
public void setRefundMap(HashMap<String, String> refundMap) {
	this.refundMap = refundMap;
}

public void prepare() throws Exception {
	// TODO Auto-generated method stub
	
}

}
