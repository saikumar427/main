<%@page import="com.event.dbhelpers.TransactionDB,com.eventbee.general.formatting.CurrencyFormat"%>
<%@page import="com.eventbee.util.ProcessXMLData,java.io.*"%>
<%@ page import="java.util.*,com.eventbee.general.*,org.w3c.dom.Document" %>

<%!
String serverAddress = "http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
public int processThread(String threadid){
     System.out.println(" ##### Enter into processThread: "+threadid+" #####");
	 String status=checkforStatus(threadid);
	 if(status==null)status="";
	 if(!"".equals(status))
		 if("START".equalsIgnoreCase(status))
		 	processOneTransaction();
	 return 1;
	}
	
public String checkforStatus(String threadid){
   System.out.println("##### Enter into checkforStatus : "+threadid+" #####");
   String status=DbUtil.getVal("select status from beeadmin_schedular_threads where threadid=?",new String[]{threadid});
   System.out.println("##### volumebee schedular status is: "+status+" #####");   
   return status;
  }
  
public void processOneTransaction(){
    System.out.println("Enter into  processOneTransaction");
	try{
	
	/*String triggertidsquery = "select a.tid from event_reg_details_temp a, groupdeal_tickets b, event_reg_gt_details_temp c "
	+"where a.status='Authorized' and  b.trigger_qty <= current_soldqty and b.show_status='Y' and b.ticketid=c.ticketid "
	+"and c.tid=a.tid";*/
	String triggertidsquery = "select a.tid from event_reg_gt_details_temp a, groupdeal_tickets b "
		+"where a.status='Authorized' and  b.trigger_qty <= b.current_soldqty and b.show_status='Y' and b.ticketid=a.ticketid ";
	
	List triggerTids = DbUtil.getValues(triggertidsquery);
	DbUtil.executeUpdateQuery("update event_reg_gt_details_temp set status='triggered' where tid in ("+triggertidsquery+")",null);
	
	DbUtil.executeUpdateQuery("update groupdeal_tickets set show_status='N', sale_status='Closed' where end_date+cast(cast(to_timestamp(COALESCE(end_time,'00'),'HH24:MI:SS') as text) as time )<=current_timestamp",null);
	DbUtil.executeUpdateQuery("update groupdeal_tickets set show_status='N', sale_status='Closed' where trigger_qty <= current_soldqty and show_status='Y' and post_trigger_type=1",null);
	DbUtil.executeUpdateQuery("update groupdeal_tickets set show_status='N', sale_status='Closed' where upper_limit <= current_soldqty and show_status='Y' and post_trigger_type=2",null);
	DbUtil.executeUpdateQuery("update groupdeal_tickets set show_status='N', sale_status='Closed' where trigger_qty <= current_soldqty and show_status='Y' and post_trigger_type=3 and current_cycle=cycles_limit",null);
	DbUtil.executeUpdateQuery("update groupdeal_tickets set current_cycle=current_cycle+1,current_soldqty=0 where trigger_qty <= current_soldqty and show_status='Y' and post_trigger_type=3 and current_cycle<cycles_limit",null);
	
	/*String notriggercapturetidsquery = "select a.tid from event_reg_details_temp a, groupdeal_tickets b, event_reg_gt_details_temp c "
		+"where a.status='Authorized' and  b.show_status='N' and b.ticketid=c.ticketid "
		+"and c.tid=a.tid and c.notriggeraction='Y'";*/
	String notriggercapturetidsquery = "select a.tid from event_reg_gt_details_temp a,groupdeal_tickets b "
		+"where a.status='Authorized' and  b.show_status='N' and b.sale_status!='Cancelled' and b.ticketid=a.ticketid and a.notriggeraction='Y'";
	List notriggercaptureTids = DbUtil.getValues(notriggercapturetidsquery);
	DbUtil.executeUpdateQuery("update event_reg_gt_details_temp set status='notriggered' where tid in ("+notriggercapturetidsquery+")",null);
	
	/*String notriggervoidtidsquery = "select a.tid from event_reg_details_temp a, groupdeal_tickets b, event_reg_gt_details_temp c "
		+"where a.status='Authorized' and  b.show_status='N' and b.ticketid=c.ticketid "
		+"and c.tid=a.tid and c.notriggeraction='N'";*/
	// all notriggeraction='N' and cancelled transactions are void.
	String notriggervoidtidsquery = "select a.tid from event_reg_gt_details_temp a, groupdeal_tickets b "
		+"where a.status='Authorized' and  b.show_status='N' and b.ticketid=a.ticketid";
	
	List notriggervoidTids = DbUtil.getValues(notriggervoidtidsquery);
	DbUtil.executeUpdateQuery("update event_reg_gt_details_temp set status='void' where tid in ("+notriggervoidtidsquery+")",null);
	
	ArrayList triggered_success_tids = new ArrayList();
	ArrayList notriggered_success_tids = new ArrayList();
	ArrayList void_success_tids = new ArrayList();
	HashMap hm = new HashMap();
	DBManager dbmanager=new DBManager();
	StatusObj statobj=null;
	//for triggercapture
	System.out.println("triggerTids size: "+triggerTids.size());
	for(int i=0;i<triggerTids.size();i++){
		String tid = (String)triggerTids.get(i);
		dbmanager=new DBManager();
		String triggercpturequery = "select	ticketid,eventid,trigger_grandtotal,trigger_granddiscount,trigger_tax,original_price,"+
		"trigger_nettotal,trigger_finalprice,trigger_discount,notriggeraction,totalticketsqty,trigger_cardfee,trigger_ebeefee from event_reg_gt_details_temp where tid=?";
		statobj=dbmanager.executeSelectQuery(triggercpturequery,new String []{tid});
		if(statobj.getStatus()){
			String ticketid = dbmanager.getValue(0,"ticketid","");
			String eventid = dbmanager.getValue(0,"eventid","");
			String notriggeraction = dbmanager.getValue(0,"notriggeraction","");
			String t_grandtotal = CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(0,"trigger_grandtotal",""),true);
			String t_granddiscount = CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(0,"trigger_granddiscount",""),true);
			String t_tax = CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(0,"trigger_tax",""),true);
			String t_nettotal = CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(0,"trigger_nettotal",""),true);
			String original_price = CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(0,"original_price",""),true);
			String t_finalprice = CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(0,"trigger_finalprice",""),true);
			String t_discount = CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(0,"trigger_discount",""),true);
			double totalticketsqty = Double.parseDouble(dbmanager.getValue(0,"totalticketsqty","0"));
			double trigger_cardfee = Double.parseDouble(dbmanager.getValue(0,"trigger_cardfee","0"));
			double trigger_ebeefee = Double.parseDouble(dbmanager.getValue(0,"trigger_ebeefee","0"));
			double trigger_grandtotal = Double.parseDouble(dbmanager.getValue(0,"trigger_grandtotal","0"));
			String amount_we_have= String.valueOf(trigger_grandtotal-trigger_cardfee-(trigger_ebeefee*totalticketsqty));
			amount_we_have=CurrencyFormat.getCurrencyFormat("",amount_we_have,true);
			//paypal processing
			String status[] = processDiscountSale(tid,t_grandtotal);
			//if status is not fail, it contains response_id given by paypal 
			if(status[0].equals("Fail")){
				updateTempStatus(tid,"triggered_fail");
				updateCardTransaction(tid,t_grandtotal,status[1]+"-CAPTURE","Fail");
			}else{
				updatePaymentStatusForDiscounSale(tid,t_grandtotal,t_granddiscount,t_tax,original_price,t_nettotal,status[0],amount_we_have);
				if(notriggeraction.equals("Y"))
					updateTicketDetails(tid,t_finalprice,t_discount);
				updateTempStatus(tid,"triggered_success");
				updateCardTransaction(tid,t_grandtotal,status[1]+"-CAPTURE",status[0]);
				TransactionDB.resendingMail(eventid,tid,"");
			}
		}
	}
	//for notriggercapture
	System.out.println("notriggercaptureTids size: "+notriggercaptureTids.size());
	for(int i=0;i<notriggercaptureTids.size();i++){
		String tid = (String)notriggercaptureTids.get(i);
		dbmanager=new DBManager();
		String notriggercpturequery = "select notrigger_grandtotal,notrigger_granddiscount,original_price,notrigger_nettotal,eventid,ticketid from event_reg_gt_details_temp where tid=?";
		//String notriggergrandtotal = DbUtil.getVal(notriggercpturequery,new String[]{tid});
		statobj=dbmanager.executeSelectQuery(notriggercpturequery,new String []{tid});
		String nt_grandtotal = CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(0,"notrigger_grandtotal",""),true);
		String nt_granddiscount =CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(0,"notrigger_granddiscount",""),true);
		String nt_nettotal = CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(0,"notrigger_nettotal",""),true);
		String original_price =CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(0,"original_price",""),true);
		String eventid = dbmanager.getValue(0,"eventid","");
		String ticketid = dbmanager.getValue(0,"ticketid","");
		
		dbmanager=new DBManager();
		statobj=dbmanager.executeSelectQuery("select ticket_name,regular_ticket_name from groupdeal_tickets where ticketid=CAST(? AS INTEGER)",new String []{ticketid});
		String regTicketName=dbmanager.getValue(0,"regular_ticket_name","");
		if("".equals(regTicketName))
			regTicketName=dbmanager.getValue(0,"ticket_name","");
		//paypal processing
		String status[] = processActualSale(tid,nt_grandtotal);
		//if status is not fail, it contains response_id given by paypal
		if(status[0].equals("Fail")){
			updateTempStatus(tid,"notriggered_fail");
			updateCardTransaction(tid,nt_grandtotal,status[1]+"-CAPTURE","Fail");
		}else{
			updatePaymentStatusForActualSale(tid,nt_grandtotal,nt_granddiscount,original_price,nt_nettotal,regTicketName,status[0]);
			updateTempStatus(tid,"notriggered_success");
			updateCardTransaction(tid,nt_grandtotal,status[1]+"-CAPTURE",status[0]);
			TransactionDB.resendingMail(eventid,tid,"");
		}
	}
	//for void
	System.out.println("notriggervoidTids size: "+notriggervoidTids.size());
	for(int i=0;i<notriggervoidTids.size();i++){
		String tid = (String)notriggervoidTids.get(i);
		dbmanager=new DBManager();
		String triggervoidquery = "select trigger_grandtotal,eventid,ticketid,totalticketsqty from event_reg_gt_details_temp where tid=?";
		statobj=dbmanager.executeSelectQuery(triggervoidquery,new String []{tid});
		if(statobj.getStatus()){
			String eventid = dbmanager.getValue(0,"eventid","");
			String ticketid = dbmanager.getValue(0,"ticketid","");
			String totalticketsqty = dbmanager.getValue(0,"totalticketsqty","");
			String triggergrandtotal = CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(0,"trigger_grandtotal",""),true); 
			//paypal processing
			String status[] =processVoid(tid,triggergrandtotal);
			//if status is not fail, it contains response_id given by paypal
			if(status[0].equals("Fail")){
				updateTempStatus(tid,"void_fail");
				updateCardTransaction(tid,triggergrandtotal,status[1]+"-VOID","Fail");
			}
			else{
				updatePaymentStatusForVoid(tid,eventid,ticketid,totalticketsqty,status[0]);
				updateTempStatus(tid,"void_success");
				updateCardTransaction(tid,triggergrandtotal,status[1]+"-VOID",status[0]);
				TransactionDB.resendingMail(eventid,tid,"");
			}
		  }
	   }
	}catch(Exception e){
	System.out.println("Exception in :"+e.getMessage());
   }
}	
public String[] processDiscountSale(String tid, String trigger_grandtotal){
	System.out.println("In processDiscountSale... ");
	String status[] = processResponseData(tid,trigger_grandtotal,"capture");
	return status;	
}
public String[] processActualSale(String tid, String notrigger_grandtotal){
	System.out.println("In processActualSale... ");
	String status[] = processResponseData(tid,notrigger_grandtotal,"capture");
	return status;	
}
public String[] processVoid(String tid, String trigger_grandtotal){
	System.out.println("In processVoid... ");
	String status[] = processResponseData(tid,trigger_grandtotal,"void");
	return status;
}
public String[] processResponseData(String tid, String grandtotal,String ccprocesstype){
	System.out.println("In processResponseData ccprocesstype: "+ccprocesstype);
	boolean paypalStatus=false;
	String status="Fail";
	String vendor="";
	String [] xmltags={"Ack","AVSCode","CVV2Code","TransactionID"};
	String [] xmlerrtags={"ErrorCode","ShortMsg"};
	String [] xmlresponsetag={"response"};
	Map resMap=new HashMap();
      Map  responsemap=new HashMap();
	Vector errList=null;
    Vector errorcodes=new Vector();
		try{
			String reponsedata[]=getResponseData(tid,grandtotal,ccprocesstype);
				if(reponsedata[0]!=null&& "PAYPALPRO".equals(reponsedata[1])){
				vendor="PAYPALPRO";
				Document doc=ProcessXMLData.getDocument(new StringBufferInputStream(reponsedata[0]));
				doc.getDocumentElement ().normalize ();
				resMap=ProcessXMLData.getProcessedXMLData(doc,"DoDirectPaymentResponse",xmltags);
                responsemap=ProcessXMLData.getProcessedXMLData(doc,"DoDirectPaymentResponse",xmlresponsetag);
                System.out.println("*** responseMap from paypal: "+resMap);
				if("Success".equals((String)resMap.get("Ack"))){
					System.out.println("*** response from paypal is success for tid: "+tid);
					status=GenUtil.getHMvalue(resMap,"TransactionID",null);
               
				}else{
					System.out.println("*** response from paypal is fail for tid: "+tid);
				}
					String responsePaypal=(String)responsemap.get("response");
		            StatusObj statobjn= DbUtil.executeUpdateQuery("insert into paypal_pro_responses (transactionid,response,date) values (?,?,now())",new String[]{tid,responsePaypal});

				
			}else if(reponsedata[0]!=null&& "BRAINTREE".equals(reponsedata[1])){
			vendor="BRAINTREE";
			String [] bxmltags={"Status","TransactionID","VaultId"};
	         Document doc=ProcessXMLData.getDocument(new StringBufferInputStream(reponsedata[0]));
		     doc.getDocumentElement ().normalize ();
	         resMap=ProcessXMLData.getProcessedXMLData(doc,"Response",bxmltags);
	         System.out.println("resMap::g::"+resMap);
			 
			          if("Success".equals((String)resMap.get("Status"))){
					   System.out.println("*** response from braintree is success for tid: "+tid);
					   status=GenUtil.getHMvalue(resMap,"TransactionID",null);               
				      }else{
					   System.out.println("*** response from braintree is fail for tid: "+tid);
				      }
			}
			else if(reponsedata[0]==null) 
			System.out.println("*** reponsedata from paypal is null for tid: "+tid);
				
				
				

	
		}
		catch(Exception e){
			System.out.println("Exception in processResponseData: "+e.getMessage());
		}
	return new String[]{status,vendor};

}

public String[] getResponseData(String tid, String grandtotal, String ccprocesstype){
	
	String st=null;
	String process_vendor="";
	try{
		Map paramMap=new HashMap();
		DBManager dbmanager=new DBManager();
	    StatusObj statobj=null;
		String authidqry ="select response_id,process_vendor from cardtransaction where response_status='Success' and internal_ref=?";
		String AuthorizationID ="";
		
		
		statobj=dbmanager.executeSelectQuery(authidqry,new String []{tid});
		if(statobj.getStatus()){
			 AuthorizationID = dbmanager.getValue(0,"response_id","");
		     process_vendor = dbmanager.getValue(0,"process_vendor","");
			 System.out.println("*** AuthorizationID: "+AuthorizationID+" to capture for tid: "+tid+"  process_vendor:: "+process_vendor);
		     com.eventbee.util.CoreConnector cc1=null;
		     if("PAYPALPRO-AUTH".equals(process_vendor)){
		    	 process_vendor="PAYPALPRO";
				 String environment=EbeeConstantsF.get("PAYPAL_PRO_ENVIRONMENT","sandbox");
			     paramMap.put("Environment",environment);
	             paramMap.put("AuthorizationID",AuthorizationID);
	             paramMap.put("CaptureAmt", grandtotal);
	             paramMap.put("ccprocesstype",ccprocesstype);        
	             cc1=new com.eventbee.util.CoreConnector(EbeeConstantsF.get("CONNECTING_PAYPAL_URL",serverAddress+"/paypal/paypal.jsp"));
			     cc1.setArguments(paramMap);
			     cc1.setTimeout(30000);
			     st=cc1.MGet();
			}else if("BRAINTREE-AUTH".equals(process_vendor)){
				process_vendor="BRAINTREE";
				String ptyp="capture".equals(ccprocesstype)?"settlement":ccprocesstype;
				String environment=EbeeConstantsF.get("BRAINTREE_ENVIRONMENT","sandbox");
			    paramMap.put("env",environment);
	            paramMap.put("recall_id",AuthorizationID);
	            paramMap.put("GrandTotal", grandtotal);
	            paramMap.put("paytype",ptyp);        
	            cc1=new com.eventbee.util.CoreConnector(EbeeConstantsF.get("CONNECTING_BRAINTREE_URL",serverAddress+"/main/payments/braintree/BraintreePayment.jsp"));
			    cc1.setArguments(paramMap);
			    cc1.setTimeout(50000);
			    st=cc1.MGet();
			
			}
		
		}
	}
	catch(Exception e){
		System.out.println("Exception in getResponseData: "+e.getMessage());
	}
	return new String[]{st,process_vendor};
}

public void updatePaymentStatusForDiscounSale(String tid, String t_grandtotal,String t_granddiscount,String t_tax,String original_price,String t_nettotal,String ext_pay_id,String amount_we_have){
	System.out.println("In updatePaymentStatusForDiscounSale tid: "+tid);
	try{
	String updatepaymentstatusqry = "update event_reg_transactions set paymentstatus='Completed',current_amount=CAST(? AS NUMERIC), "
	+"original_amount=CAST(? AS NUMERIC),current_discount=CAST(? AS NUMERIC),original_discount=CAST(? AS NUMERIC), "
	+"current_tax=CAST(? AS NUMERIC),original_tax=CAST(? AS NUMERIC),bookingdomain='VBEE_TRIGGER_SUCCESS',ext_pay_id=?,amount_we_have=? where tid=?";
	DbUtil.executeUpdateQuery(updatepaymentstatusqry,new String[]{t_grandtotal,t_grandtotal,t_granddiscount,t_granddiscount,t_tax,t_tax,ext_pay_id,amount_we_have,tid});
	
	String updatetransactiontickets = "update transaction_tickets set ticketprice=CAST(? AS NUMERIC),discount=CAST(? AS NUMERIC),ticketstotal=CAST(? AS NUMERIC) where tid=?";
	DbUtil.executeUpdateQuery(updatetransactiontickets,new String[]{original_price,t_granddiscount,t_nettotal,tid});
	}catch(Exception e){
		System.out.println("Exception in updatePaymentStatusForDiscounSale: "+e.getMessage());
	}
}

public void updatePaymentStatusForActualSale(String tid,String nt_grandtotal,String nt_granddiscount,String original_price,String nt_nettotal,String regTicketName,String ext_pay_id){
	System.out.println("In updatePaymentStatusForActualSale tid: "+tid);
	try{
	String updatepaymentstatusqry = "update event_reg_transactions set paymentstatus='Completed',current_amount=CAST(? AS NUMERIC), "
	+"original_amount=CAST(? AS NUMERIC),current_discount=CAST(? AS NUMERIC),original_discount=CAST(? AS NUMERIC),bookingdomain='VBEE_NOTRIGGER_SUCCESS',ext_pay_id=? where tid=?";
	DbUtil.executeUpdateQuery(updatepaymentstatusqry,new String[]{nt_grandtotal,nt_grandtotal,nt_granddiscount,nt_granddiscount,ext_pay_id,tid});
	
	String updatetransactiontickets = "update transaction_tickets set ticketprice=CAST(? AS NUMERIC),discount=CAST(? AS NUMERIC),ticketstotal=CAST(? AS NUMERIC),ticketname=? where tid=?";
	DbUtil.executeUpdateQuery(updatetransactiontickets,new String[]{original_price,nt_granddiscount,nt_nettotal,regTicketName,tid});
	
	String updateticketdetailstemp = "update event_reg_ticket_details_temp set ticketname=? where tid=?";
	DbUtil.executeUpdateQuery(updateticketdetailstemp,new String[]{regTicketName,tid});
	}catch(Exception e){
		System.out.println("Exception in updatePaymentStatusForActualSale: "+e.getMessage());
	}
}

public void updatePaymentStatusForVoid(String tid,String eid,String ticketid,String totalticketsqty,String ext_pay_id){
	System.out.println("In updatePaymentStatusForVoid tid: "+tid);
	try{
	DbUtil.executeUpdateQuery("update event_reg_transactions set paymentstatus='Cancelled',ext_pay_id=? where tid=? and eventid=?", new String[] {ext_pay_id,tid,eid });
	
	String updatesoldqty="update groupdeal_tickets set current_soldqty=current_soldqty-CAST(? AS INTEGER), sold_qty=sold_qty-CAST(? AS INTEGER) where ticketid=CAST(? AS BIGINT) and eventid=?";
	DbUtil.executeUpdateQuery(updatesoldqty,new String[]{totalticketsqty,totalticketsqty,ticketid,eid});
	
	
	}catch(Exception e){
		System.out.println("Exception In updatePaymentStatusForVoid: "+e.getMessage());
	}
	
}


public void updateTicketDetails(String tid,String t_finalprice,String t_discount){
	System.out.println("In updateTicketDetails tid: "+tid);
	try{
		
		String updatetransactiontickets = "update event_reg_ticket_details_temp set finalprice =CAST(? AS NUMERIC),discount=CAST(? AS NUMERIC) where tid=?";
		DbUtil.executeUpdateQuery(updatetransactiontickets,new String[]{t_finalprice,t_discount,tid});
		
	}catch(Exception e){
		System.out.println("Exception In updateTicketDetails "+e.getMessage());
	}
}


public void updateTempStatus(String tid, String status){
	DbUtil.executeUpdateQuery("update event_reg_gt_details_temp set status=? where tid=?",new String[]{status,tid});
}

public void updateCardTransaction(String tid,String grandtotal,String process_vendor,String status){
	System.out.println("In updateCardTransaction response_id: "+status);
	try{
	StatusObj statobj=null;
	String transactionid = DbUtil.getVal("select nextval('seq_cardtransaction_id')",null);
	
	String CARD_INFO_SUCCESS_INSERT =" insert into cardtransaction(transactionid,process_vendor,transaction_date,app_name," +
	    "internal_ref,cardtype,cardmm,cardyy,cardnum,amount,proces_status,transaction_type,response_id,response_status" +
	    ") select to_number(?,'9999999999999'),?,now(),'VOLUME_REGISTRATION',"+
	    "internal_ref,cardtype,cardmm,cardyy,cardnum,to_number(?,'9999999999999.99'),'Success',transaction_type,?,'Success' from cardtransaction where internal_ref=?";
	
	String CARD_INFO_FAIL_INSERT =" insert into cardtransaction(transactionid,process_vendor,transaction_date,app_name," +
    "internal_ref,cardtype,cardmm,cardyy,cardnum,proces_status,transaction_type" +
    ") select to_number(?,'9999999999999'),?,now(),'VOLUME_REGISTRATION',"+
    "internal_ref,cardtype,cardmm,cardyy,cardnum,'F',transaction_type from cardtransaction where internal_ref=?";
	
	if(status.equals("Fail")){
		statobj = DbUtil.executeUpdateQuery(CARD_INFO_FAIL_INSERT,new String[]{transactionid,process_vendor,tid});
	}else{
		// here status is response_id retun by paypal.
		statobj = DbUtil.executeUpdateQuery(CARD_INFO_SUCCESS_INSERT,new String[]{transactionid,process_vendor,grandtotal,status,tid});
	}
	}catch(Exception e){
		System.out.println("Exception In updateCardTransaction: "+e.getMessage());
	}
}

%>
<%
  String threadid=request.getParameter("threadId");
  processThread(threadid);
%>

