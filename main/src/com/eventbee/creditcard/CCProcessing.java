package com.eventbee.creditcard;

import com.eventbee.general.StatusObj;
import com.eventbee.general.EventbeeConnection;
import com.event.beans.ProfileData;
import com.eventbee.general.EbeeConstantsF;

import com.eventbee.general.*;
import java.sql.*;
import java.util.Vector;
import java.util.HashMap;

public class CCProcessing {

	public StatusObj sobj=null;
	public ResponseObj robj=null;

	//for card insert info -authorize and deposit
	final String CARD_INFO_INSERT =" insert into cardtransaction( "
					    +" transactionid,process_vendor,transaction_date,app_name," +
					    "internal_ref,cardtype,cardmm,cardyy,cardnum,amount," +
					    "proces_status,transaction_type,response_id,response_status," +
					    "response_scode,response_fcode) "
	 				    +" values (to_number(?,'9999999999999'),?,now(),?,?,?," +
	 				    "to_number(?,'9999999999999'),to_number(?,'9999999999999'),?," +
	 				    "to_number(?,'9999999999999.99'),?,?,?,?,?,?) ";
		//to get internal transaction id
	final String TRANSACTION_ID_GET="select nextval('seq_cardtransaction_id') as transactionid";

//used for local validations called from CreditCardModel's localValidate
	public StatusObj localValidate(CreditCardInfo cci){
			StatusObj sobj=null;
			boolean flag=false;
			sobj=cci.checkFormat();
			if(sobj.getStatus()){
					sobj=new StatusObj(false,"tmp","");
			}else{
					robj=new ResponseObj();
					robj.setData("","","R","","");
					robj.message=sobj.getErrorMsg();
					robj.data=sobj.getData();
					sobj=new StatusObj(true,"tmp",sobj.getData());
			}
			return sobj;
	}


	//for processing the authorize and deposit of any vendor
	public StatusObj processCard(CreditCardInfo cci,String counter,String requestapp,String intref){
	EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"CCProcessing","submitSkipJack()","ented  processCard()",null);
			StatusObj sobj=null;
			boolean flag=false;
			sobj=cci.checkFormat();
				String cardvendor=EbeeConstantsF.get("cardvendor","openecho");
				EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"CCProcessing","submitPaypal()","entering  submitPaypal",null);
						sobj=submitPaypal(cci,true);
						EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"CCProcessing","submitPaypal()","exited  submitPaypal",null);
						flag=setCardInfoToDB((ResponseObj)sobj.getData(),cci,cardvendor,requestapp,intref);
						if(flag){
							EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"CCProcessing","processCard(CreditCardInfo cci,String counter,String requestapp,String intref)","INSERT CARD INFO INTO DB SUCCESSFULLY",null);
						}
                       	ResponseObj robj=(ResponseObj)sobj.getData();

						if(!sobj.getStatus()){
							EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"CCProcessing","processCard(CreditCardInfo cci,String counter,String requestapp,String intref)","CARD FAILED STATUS :" +sobj.getStatus()+"Stat code "+robj.getStatus_code(),null);

										if("F".equals(robj.getStatus_code())){


												Vector v=new Vector();
												v.add("FATAL ERROR");
												robj=new ResponseObj();
												robj.setData("","","F","","");
												sobj=new StatusObj(false,"tmp",robj);
												EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.ERROR,"CCProcessing","processCard(CreditCardInfo cci,String counter,String requestapp,String intref)","IN PAYPAL CCPROCESSING WITH STATUS F",null);
										}else if("R".equals(robj.getStatus_code())){

												Object v=robj.data;
												robj=new ResponseObj();
												robj.setData("","","Fail","","");
												robj.data=v;
												sobj=new StatusObj(false,"tmp",robj);
												EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"CCProcessing","processCard(CreditCardInfo cci,String counter,String requestapp,String intref)","IN PAYPAL CCPROCESSING WITH STATUS R  vector :"+v,null);
										}
						EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"CCProcessing","processCard(CreditCardInfo cci,String counter,String requestapp,String intref)","robj.getStatus_code() = "+robj.getStatus_code(),null);
						}





			return sobj;
	}


	public boolean processRecurringCard(String userid,int duemonths,String startdate,String amount,CreditCardInfo cci,String purpose){
	boolean flag=true;
	return flag;
	}


	///////////////***************************////////////////////method for paypal

	public StatusObj submitPaypal(CreditCardInfo cci,boolean isavs){
		sobj=new StatusObj(true,"tmp",null);
		try{
			ProfileData profiledata=null;
			boolean ifavs=false;
			PaypalProcessing paypalprocessing=new PaypalProcessing(cci);
			robj=paypalprocessing.processResposeData();
			sobj=new StatusObj(robj.status,robj.message,robj);
			EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"CCProcessing.java","submitPaypal(CreditCardInfo cci,boolean isavs)","Status: "+robj.status,null);

			}
		catch(Exception e1){
			EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "CCProcessing.java", "submitPaypal", e1.getMessage(), e1 ) ;
		}

		return sobj;
	}



// to get internal transaction id for both authorize and deposit and recurring transactions
  public String getcardTransactionid(){
	String transactionid=null;
	try{
		
        DBManager dbm=new DBManager();
        StatusObj statobj=null;
		statobj=dbm.executeSelectQuery(TRANSACTION_ID_GET, null);
        if(statobj.getStatus() && statobj.getCount()>0){
			transactionid=dbm.getValue(0,"transactionid","");
		}else{
			transactionid="1";
		}
	}catch(Exception e){
			EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "CCProcessing", "Error in getcardTransactionid():" , e.getMessage(), e ) ;
	 }
		return transactionid;
    }




   //to set card info to database for authorize and deposite transaction
   public boolean setCardInfoToDB(ResponseObj robj,CreditCardInfo cci, String vendor,String requestapp,String intref){
		
	     System.out.println("In setCardInfoToDB method in CCProcessing");
	     boolean flag=false;
		
		String transactionid=null,cc_number=null;
        StatusObj statobj=null;
	  try{

		  transactionid=getcardTransactionid();
		  cc_number=cci.getCC_number(vendor);
		  cc_number=cc_number.substring(cc_number.length()-4);
		  statobj=DbUtil.executeUpdateQuery(CARD_INFO_INSERT, new String[]{transactionid,vendor,requestapp,intref,cci.getcardtype(vendor),
        		  cci.getCC_expmonth(vendor),cci.getCC_expyear(vendor),cc_number,cci.getgrand_total(vendor),robj.getStatus_code(),"AD",robj.order_number,robj.status_code,
        		  robj.auth_code,robj.decline_code});
	      /* pstmt=con.prepareStatement(CARD_INFO_INSERT);
		  pstmt.setString(1,transactionid);
		  pstmt.setString(2,vendor);
		  //pstmt.setString(3,"now()");
		  pstmt.setString(3,requestapp);
		  pstmt.setString(4,intref);
		  pstmt.setString(5,cci.getcardtype(vendor));
		  pstmt.setString(6,cci.getCC_expmonth(vendor));
		  pstmt.setString(7,cci.getCC_expyear(vendor));
		  pstmt.setString(8,cc_number);
		  pstmt.setString(9,cci.getgrand_total(vendor));
		  pstmt.setString(10,robj.getStatus_code());
		  pstmt.setString(11,"AD");
		  pstmt.setString(12,robj.order_number);
		  pstmt.setString(13,robj.status_code);
		  pstmt.setString(14,robj.auth_code);
		  pstmt.setString(15,robj.decline_code);
		  rcount=pstmt.executeUpdate();
		  System.out.println("count:::"+rcount);
		  pstmt.close();
		  pstmt=null;
		  con.close();*/
		  if(statobj.getCount()>0){
				flag=true;
		  }
	  }catch(Exception e1){
			EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "CCProcessing", "setCardInfoToDB", e1.getMessage(), e1 ) ;
			flag=false;
	  }
	  return flag;
    }



}
