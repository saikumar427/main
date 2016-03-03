package com.eventbee.creditcard;

import com.eventbee.general.StatusObj;
import java.util.*;
import com.eventbee.general.*;


public class  PaymentTypes{

	public static Vector getAllPaymentTypes(String refid,String Purpose){
		Vector paytypevector=new Vector();
		String GET_PAYMENT_DETAILS="select * from payment_types where refid=? and purpose=?";
		DBManager dbmanager=new DBManager();
		HashMap hm=null;
		StatusObj stobj=dbmanager.executeSelectQuery(GET_PAYMENT_DETAILS,new String[]{refid,Purpose});
		if(stobj.getStatus()){
				String [] columnnames=dbmanager.getColumnNames();
				for(int i=0;i<stobj.getCount();i++){
					hm=new HashMap();
					for(int j=0;j<columnnames.length;j++){
						hm.put(columnnames[j],dbmanager.getValue(i,columnnames[j],""));
					}
					paytypevector.add(hm);
				}


		}
		return paytypevector;
	 }

	 public static HashMap getPaymentTypeInfo(String refid,String Purpose,String Paytype){
		String GET_PAYMENT_TYPE_INFO="select * from payment_types where refid=? and purpose=? and paytype=?";
		DBManager dbmanager=new DBManager();
		HashMap hm=new HashMap();
		StatusObj stobj=dbmanager.executeSelectQuery(GET_PAYMENT_TYPE_INFO,new String[]{refid,Purpose,Paytype});
		if(stobj.getStatus()){
				String [] columnnames=dbmanager.getColumnNames();
				for(int i=0;i<stobj.getCount();i++){
					for(int j=0;j<columnnames.length;j++){
							hm.put(columnnames[j],dbmanager.getValue(i,columnnames[j],""));
						}
				}
			}
		 return hm;
	 }

	 public static HashMap getPaymentTypesStatus(String refid,String Purpose){
		String GET_PAYMENT_TYPE_STATUS="select status,paytype from payment_types where refid=? and purpose=?";
		DBManager dbmanager=new DBManager();
			 StatusObj stobj=dbmanager.executeSelectQuery(GET_PAYMENT_TYPE_STATUS,new String[]{refid,Purpose});
			 HashMap hm=new HashMap();
			 Vector v=new Vector();
				 if(stobj.getStatus()){
					for(int i=0;i<stobj.getCount();i++){
						hm.put(dbmanager.getValue(i,"paytype",""),dbmanager.getValue(i,"status",""));
					}
				}
		 return hm;
	 }


	public static int UpdatePaymentData(String refid,String Purpose, String Paytype, String attrib_1,String attrib_2){

		int refval=0;
		StatusObj statobj=null;
		boolean isexists=false;
		String [] insertparams=null;

		String UPDATE_PAYMENT_DATA="update payment_types set attrib_1=?, status='Enabled' where refid=? and purpose=? and paytype=?";
		isexists=isExists(refid,Purpose,Paytype);

			if(isexists){
			   if(attrib_2!=null){
				  UPDATE_PAYMENT_DATA="update payment_types set attrib_1=?,attrib_2=?, status='Enabled' where refid=? and purpose=? and paytype=?";
				  statobj=DbUtil.executeUpdateQuery(UPDATE_PAYMENT_DATA,new String []{attrib_1,attrib_2,refid,Purpose,Paytype});
				  if(statobj.getStatus()){
				  		refval=1;
					}

			   }else{
				  statobj=DbUtil.executeUpdateQuery(UPDATE_PAYMENT_DATA,new String []{attrib_1,refid,Purpose,Paytype});
				  if(statobj.getStatus()){
				  		refval=1;
					}

				}
		}else{
			if(attrib_2!=null){

				insertparams=new String[6];

				insertparams[0]=refid;
				insertparams[1]=Purpose;
				insertparams[2]=Paytype;
				insertparams[3]="Enabled";
				insertparams[4]=attrib_1;
				insertparams[5]=attrib_2;
				refval=InsertPaymentData(insertparams);

			 }else{

				 insertparams=new String[5];

				 insertparams[0]=refid;
				 insertparams[1]=Purpose;
				 insertparams[2]=Paytype;
				 insertparams[3]="Enabled";
				 insertparams[4]=attrib_1;
				 refval=InsertPaymentData(insertparams);

			  }


		}



		return refval;
	 }


	 public static int UpdatePaymentStatus(String refid, String purpose, String paytype, String Status){
		int refval=0;
		String UPDATE_PAYMENT_STATUS="update payment_types set status=? where refid=? and purpose=? and paytype=?";


		StatusObj statobj=DbUtil.executeUpdateQuery(UPDATE_PAYMENT_STATUS,new String []{Status,refid,purpose,paytype});
		if(statobj.getStatus()){
			refval=1;
		}
		return refval;
	 }


	 public static int InsertPaymentData(String [] params){
		int refval=0;

		String INSERT_PAYMENT_DATA="insert into payment_types(refid,purpose,paytype,status,attrib_1) values(?,?,?,?,?)";
		if(params.length==6){
			INSERT_PAYMENT_DATA="insert into payment_types(refid,purpose,paytype,status,attrib_1,attrib_2) values(?,?,?,?,?,?)";
		}

		StatusObj statobj=DbUtil.executeUpdateQuery(INSERT_PAYMENT_DATA,params);


		if(statobj.getStatus()){
			refval=1;
		}
		return refval;
	 }

	 public static boolean isExists(String refid,String Purpose,String Paytype){
		boolean IS_EXISTS=false;
		 String Pay_Type_Exists=DbUtil.getVal("select 'yes' from payment_types where refid=? and paytype=? and purpose=?", new String [] {refid,Paytype,Purpose});
		if("yes".equals(Pay_Type_Exists))
			IS_EXISTS=true;

		return IS_EXISTS;
	 }
 }