package com.event.dbhelpers;

import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import com.eventbee.creditcard.PaymentTypes;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.StatusObj;
import com.eventbee.util.CoreConnector;
import com.eventbee.util.ProcessXMLData;

public class RefundDB {
	final static String serverAddress = "http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
	public static HashMap<String,String> getRefundDetails(String tid, String eid){
    	
    	HashMap<String,String> hmap=new HashMap<String,String>();
    	DBManager dbm=new DBManager();
    	StatusObj statobj=null;
    	String query="select collected_servicefee,paymenttype,ext_pay_id,currency_code,original_amount-refund_amount as actual_amount,cc_vendor,collected_by,refund_amount from event_reg_transactions where tid=? and eventid=?";
    	statobj=dbm.executeSelectQuery(query,new String[]{tid,eid});
    	if(statobj.getStatus() && statobj.getCount()>0){
    		hmap.put("actualAmount",dbm.getValue(0,"actual_amount","0.00"));
    		hmap.put("ccvendor", dbm.getValue(0,"cc_vendor",""));
    		hmap.put("paymenttype",dbm.getValue(0,"paymenttype",""));
    		hmap.put("extpayid", dbm.getValue(0,"ext_pay_id",""));
    		hmap.put("currencycode",dbm.getValue(0,"currency_code",""));
    		hmap.put("ebeefee",dbm.getValue(0,"collected_servicefee","0.00"));
    		hmap.put("collectedby",dbm.getValue(0,"collected_by",""));
    		hmap.put("refundedAmount",dbm.getValue(0,"refund_amount","0.00"));
    	}
    	return hmap;
    }
	
	public static HashMap<String,String> refund(String tid, String eid, String refundAmount, HashMap<String,String> datain){
		String paymentType=datain.get("paymenttype");
		String ccvendor=datain.get("ccvendor");
		double actualamt = Double.parseDouble(datain.get("actualAmount"));
		double refundamt = Double.parseDouble(refundAmount);
		if(actualamt==refundamt)
			datain.put("refundtype","Full");
		else datain.put("refundtype","Partial");
		HashMap<String,String> inputparams=new HashMap<String,String>();
		String st=null;
		System.out.println("***** Refund for tid: "+tid+" paymentType: "+paymentType+" ccvendor: "+ccvendor);
		
		if("authorize.net".equals(paymentType)){
			System.out.println("calling authorize.net refund::::: tid: "+tid);
			inputparams.put("eid",eid);
			inputparams.put("tid",tid);
			inputparams.put("payment_type",paymentType);
			inputparams=getCardTransactionDetails(inputparams);
			inputparams=getAutherizationetAPIDetails(inputparams);
			inputparams.put("amount",refundAmount);
			inputparams.put("refundtype",datain.get("refundtype"));
			return callProcess(inputparams,serverAddress+"/main/payments/authorizerefund.jsp");
		}else if("braintree".equalsIgnoreCase(paymentType)){
			System.out.println("calling braintree refund::::: tid: "+tid);
			inputparams.put("eid",eid);
			inputparams.put("payment_type",paymentType);
			inputparams.put("recall_id",datain.get("extpayid"));
			inputparams.put("GrandTotal",refundAmount);
			inputparams.put("paytype","refund");
			inputparams.put("paypro","custom");
			inputparams.put("internalrefid",tid);
			inputparams.put("refundtype",datain.get("refundtype"));
			return callProcess(inputparams,serverAddress+"/main/payments/braintree/braintreerefund.jsp");
		}else if("stripe".equalsIgnoreCase(paymentType)){
			System.out.println("calling stripe refund::::: tid: "+tid);
			inputparams.put("tid",tid);
			inputparams.put("amount",refundAmount);
			inputparams.put("eid",eid);
			inputparams.put("payment_type",paymentType);
			inputparams.put("refundtype",datain.get("refundtype"));
			return callProcess(inputparams,serverAddress+"/embedded_reg/striperefund.jsp");
		}else if("payulatam".equalsIgnoreCase(paymentType)){
			System.out.println("calling PayU refund::::: tid: "+tid);
			inputparams.put("tid",tid);
			inputparams.put("amount",refundAmount);
			inputparams.put("eid",eid);
			inputparams.put("payment_type",paymentType);
			inputparams.put("refundtype",datain.get("refundtype"));
			return callProcess(inputparams,serverAddress+"/embedded_reg/payurefund.jsp");
		}else if("eventbee".equalsIgnoreCase(paymentType)){
			System.out.println("calling eventbee refund::::: tid: "+tid);
			inputparams.put("transactionId",datain.get("extpayid"));
			inputparams.put("amount",refundAmount);
			inputparams.put("refundtype",datain.get("refundtype"));
			inputparams.put("tid",tid);
			inputparams.put("eid",eid);
			inputparams.put("payment_type",paymentType);
			if("braintree_eventbee".equals(ccvendor)){
				inputparams.put("recall_id",datain.get("extpayid"));
				inputparams.put("GrandTotal",refundAmount);
				inputparams.put("paytype","refund");
				inputparams.put("internalrefid",tid);
				return callProcess(inputparams,serverAddress+"/main/payments/braintree/braintreerefund.jsp");
			}else{
				
				double refundedamt = Double.parseDouble(datain.get("refundedAmount"));
				if(refundedamt>0)
					inputparams.put("refundtype","Partial");
			
				return callProcess(inputparams,serverAddress+"/paypal/paypalprorefund.jsp");
			}
		}else if("paypal".equalsIgnoreCase(paymentType)){
			System.out.println("calling paypal refund::::: tid: "+tid);
			HashMap<String, String> paypalmap=getPaypalDetails(tid);
			String payment_option="paypal";
			String ebeefee=datain.get("ebeefee");
			if(paypalmap != null && paypalmap.get("paykey") != null && !"".equals(paypalmap.get("paykey")))
				payment_option="paypalx_chained";
			String collectedby=(String)datain.get("collectedby");
			if(!"paypalx".equals(collectedby)) ebeefee="0.00";
			
			inputparams.put("amount",refundAmount);
			inputparams.put("ebeefee",ebeefee);
			inputparams.put("paypal_payment_option",payment_option);  
			inputparams.put("email",paypalmap.get("email"));
			inputparams.put("paykey",paypalmap.get("paykey"));
			inputparams.put("transactionId",datain.get("extpayid"));
			inputparams.put("currency",datain.get("currencycode"));
			inputparams.put("refundtype",datain.get("refundtype"));
			inputparams.put("tid",tid);
			inputparams.put("eid",eid);
			inputparams.put("payment_type",paymentType);
			if("paypal".equals(payment_option))
				return callProcess(inputparams,serverAddress+"/paypal/paypalprorefund.jsp");
			else
				return callProcess(inputparams,serverAddress+"/embedded_reg/paypalxrefund.jsp");
			
		}/*else if("google".equalsIgnoreCase(paymentType)){
			System.out.println("calling google refund:::::");
			inputparams.put("google-order-number",datain.get("extpayid"));
			inputparams.put("amount",refundAmount);
			inputparams.put("currency",datain.get("currencycode"));
			HashMap attrinmap=PaymentTypes.getPaymentTypeInfo(eid,"Event","google");
			inputparams.put("Merchantid",""+attrinmap.get("attrib_1"));
			inputparams.put("Merchantkey",""+attrinmap.get("attrib_2"));
			System.out.println("google-inputparamm:: "+inputparams);	
			return callProcess(inputparams,serverAddress+"/main/payments/googlerefund.jsp");
		}*/else return null;
	}
	
	public static HashMap<String,String> getPaypalDetails(String tid){
    	
    	HashMap<String,String> hmap=new HashMap<String,String>();
    	DBManager dbm=new DBManager();
    	StatusObj statobj=null;
    	String query ="select manager_paypal_email,paykey from paypal_payment_data where ebee_tran_id=?";
    	statobj=dbm.executeSelectQuery(query,new String[]{tid});
    	if(statobj.getStatus() && statobj.getCount()>0){
    		hmap.put("paykey",dbm.getValue(0,"paykey",""));
    		hmap.put("email", dbm.getValue(0,"manager_paypal_email",""));
    	}
    	return hmap;
    }
	
	public static HashMap<String,String> getCardTransactionDetails(HashMap<String,String> apiMap){
		 System.out.println("tid in getCardTransactionDetails is: "+apiMap.get("tid"));
		 DBManager dbm=new DBManager();
		 StatusObj statobj=null;
		 String cardtranquery="select response_id,cardnum,eventid from cardtransaction c,event_reg_transactions e where e.tid=c.internal_ref and internal_ref=?";
		 statobj=dbm.executeSelectQuery(cardtranquery, new String[]{apiMap.get("tid")});
		 if(statobj.getStatus() && statobj.getCount()>0){
			 apiMap.put("cardnumber",dbm.getValue(0,"cardnum",""));
			 apiMap.put("responseid",dbm.getValue(0,"response_id",""));
			 apiMap.put("eid",dbm.getValue(0,"eventid",""));
		 }
		 System.out.println("getCardTransactionDetails are: "+apiMap);
		 return apiMap;
	}
	public static HashMap<String,String> getAutherizationetAPIDetails(HashMap<String,String> apiMap){
		String query="select mgr_api_login,mgr_api_transactionkey from autorizenet_payment_data where refid=?  and tid=?";
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(query,new String[]{apiMap.get("eid"),apiMap.get("tid")});
		if(sb.getStatus()){
			//apiMap=new HashMap<String,String>();
			apiMap.put("loginId",db.getValue(0,"mgr_api_login",""));
			apiMap.put("transactionKey",db.getValue(0,"mgr_api_transactionkey",""));
		}
		return apiMap;
	}
	public static HashMap<String,String> callProcess(HashMap<String,String>paydet,String url){
		HashMap<String,String> result=new HashMap<String,String>();
		CoreConnector cc1=null;
	    Map resMap=null;
		String data="";
		try{ 
			cc1=new CoreConnector(url);
			cc1.setArguments(paydet);
		  	cc1.setTimeout(50000);
		    data=cc1.MGet();
		    String [] xmltags={"Status","TransactionID","VaultId"};
		    Document doc=ProcessXMLData.getDocument(new StringBufferInputStream(data));
		    doc.getDocumentElement().normalize();
		    resMap=ProcessXMLData.getProcessedXMLData(doc,"Response",xmltags);
		    System.out.println("resmap for refund: "+resMap);
			if("success".equalsIgnoreCase((String)resMap.get("Status"))){ 
				result.put("status","Success");
				result.put("tid",(String)resMap.get("TransactionID"));
				result.put("refundtype", paydet.get("refundtype"));
			}else{
				 String[] errorxmltags={"Status","TransactionID","ErrorMsg"};
				 Document doc1=ProcessXMLData.getDocument(new StringBufferInputStream(data));
			     doc1.getDocumentElement().normalize();
			     resMap=ProcessXMLData.getProcessedXMLData(doc1,"Response",errorxmltags);
				 System.out.println("resMap is: "+resMap);
				 String errorMsg=(String)resMap.get("ErrorMsg");
				 System.out.println("ErrorMsg is: "+errorMsg);

				 result.put("status","fail");
			     result.put("ErrorMsg",errorMsg);
			}
		  }catch(Exception e){
			  System.out.println("Error While Processing payment::"+e.getMessage());
			  result.put("status","fail");
			  result.put("ErrorMsg","Error:: "+e.getMessage());
	  }
		return result;
	}
	
	public static void updateRefund(String tid, String eid, String refundAmount,String paymenttype){
		System.out.println("updating refundamount...");
		if("eventbee".equalsIgnoreCase(paymenttype))
			DbUtil.executeUpdateQuery("update event_reg_transactions set refund_amount=refund_amount+CAST(? AS NUMERIC),amount_we_have=amount_we_have::NUMERIC-CAST(? AS NUMERIC) where tid=? and eventid=?", new String[]{refundAmount,refundAmount,tid,eid});
		else
			DbUtil.executeUpdateQuery("update event_reg_transactions set refund_amount=refund_amount+CAST(? AS NUMERIC) where tid=? and eventid=?", new String[]{refundAmount,tid,eid});
	}
	
	

}
