package com.mycommunities.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.GenUtil;
import com.eventbee.general.LoadConfigData;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.CurrencyFormat;
import com.mycommunities.beans.ClubInfo;
import com.mycommunities.beans.ClubMemberShip;
import com.mycommunities.beans.CommunityReportData;

public class CommunityDB {

	public static ArrayList<ClubMemberShip> getMemberShipInfo(String clubId,String userId){
	 String CLUB_MEMBERSHIP_QUERY = " select * from club_membership_master where  clubid=to_number(?,'99999999999')";
	 DBManager dbmanager=new DBManager();
	 ArrayList<ClubMemberShip> listObj=new ArrayList<ClubMemberShip>();
	 StatusObj statobj=dbmanager.executeSelectQuery( CLUB_MEMBERSHIP_QUERY,new String[]{clubId});
 	 if(statobj !=null && statobj.getStatus()){
 			int recordcounttodata=statobj.getCount();
 			for(int i=0;i<recordcounttodata;i++){
 				ClubMemberShip membershipData=new ClubMemberShip();
 				membershipData.setMemberShipId(dbmanager.getValue(i,"membership_id",""));
 				membershipData.setMemberShipName(dbmanager.getValue(i,"membership_name",""));
 				membershipData.setDescription(dbmanager.getValue(i,"description",""));
 				membershipData.setPrice(dbmanager.getValue(i,"price",""));
 				membershipData.setMstatus(dbmanager.getValue(i,"status",""));
 				listObj.add(membershipData);
 			}//End of for.
 	 }//End of if block.
		return listObj;
	}
	
	public static ClubInfo getTermsCond(String userId, String clubid){
		String termsCond="";
		ClubInfo cInfo=new ClubInfo();
		String GET_TERSMS_COND="select  termscond from clubinfo where clubid=to_number(?,'99999999999')";
		try{
			termsCond=DbUtil.getVal(GET_TERSMS_COND,new String[]{clubid});
			cInfo.setTermsCond(termsCond);
		}catch(Exception e){
		}
		return cInfo;
	}
	
	public static boolean updateTermsCond(String clubId,String userId,ClubInfo cInfo){
		String UPDATE_TERMS_COND="update clubinfo set termscond=? where clubid=to_number(?,'99999999999')";
		String termsCond=cInfo.getTermsCond();
		DbUtil.executeUpdateQuery(UPDATE_TERMS_COND,new String[]{termsCond,clubId});
		return true;
	}
	
	public static ArrayList<LinkedHashMap<String,String>> getMembershipSignupsReport(String clubId,String userId,CommunityReportData reportData){
		ArrayList<LinkedHashMap<String,String>> resultData= new ArrayList<LinkedHashMap<String,String>>();
		String ebeefee="";
		String totamt="";
		String mgrfee="";
		String discount="";
		String cardfee="";
		double netfee=0.0;
		String GET_MEMBERSHIP_TILLDATE="select a.transactionid,first_name,last_name,discount,discountcode,"
			 +" to_char(trandate,'mm/dd/yyyy') as trandate1,membership_name,totalamount as price,mgrfee,ebeefee,cardfee "
			 +" from transaction a,user_profile c ,club_membership_master m,club_member p "
			 +" where p.clubid=to_number(?,'99999999999') and a.refid=c.user_id and a.refid=p.userid"
			 +" and m.membership_id=p.membership_id and "
			 +"purpose='CLUB_MEMBERSHIP' order by trandate desc";

		String GET_MEMBERSHIP_BETWEENDATES="select a.transactionid,first_name,last_name,discount,discountcode,"
			+" to_char(trandate,'mm/dd/yyyy') as trandate1,membership_name,totalamount as price,mgrfee,ebeefee,cardfee"
			+" from transaction a,user_profile b,club_membership_master m,club_member p "
			+" where p.clubid=to_number(?,'99999999999') and a.refid=b.user_id and a.refid=p.userid and m.membership_id=p.membership_id "
			+" and purpose='CLUB_MEMBERSHIP' and trandate between to_date(?,'yyyy-mm-dd')"
			+" and to_date(?,'yyyy-mm-dd')+1 order by trandate desc";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		int selectedvalue=Integer.parseInt(reportData.getSelindex());
		if(selectedvalue==1){
			statobj=dbmanager.executeSelectQuery( GET_MEMBERSHIP_TILLDATE,new String[]{clubId});
		}
		else if(selectedvalue==2){
			String startdate="",enddate="";
			if(!"".equals( reportData.getStartDay()) && !"".equals(reportData.getStartMonth()) && !"".equals(reportData.getStartYear()))
			startdate=reportData.getStartYear()+"-"+ reportData.getStartMonth()+"-"+ reportData.getStartDay();
			if(!"".equals(reportData.getEndDay()) && !"".equals(reportData.getEndMonth()) && !"".equals(reportData.getEndYear())){
			enddate=reportData.getEndYear()+"-"+reportData.getEndMonth()+"-"+reportData.getEndDay();
			enddate =enddate+" 23:59";
			}
			if(!"".equals(startdate) && !"".equals(enddate))
			statobj=dbmanager.executeSelectQuery( GET_MEMBERSHIP_BETWEENDATES,new String[]{clubId,startdate,enddate});
		}
		if(statobj !=null && statobj.getStatus()){
	 	int recordcounttodata=statobj.getCount();
	 	for(int i=0;i<recordcounttodata;i++){
	 	LinkedHashMap<String,String> rData=new LinkedHashMap<String,String>();
		rData.put("Transaction ID", dbmanager.getValue(i, "transactionid", ""));
		rData.put("Date", dbmanager.getValue(i, "trandate1", ""));
		rData.put("First Name", dbmanager.getValue(i, "first_name", ""));
		rData.put("Last Name", dbmanager.getValue(i, "last_name", ""));
		rData.put("Membership Name", dbmanager.getValue(i, "membership_name", ""));
		String name=dbmanager.getValue(i, "first_name", "")+" "+dbmanager.getValue(i, "last_name", "");
		rData.put("Name",name);
		discount=CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i, "discount", "") , true);
		totamt=dbmanager.getValue(i, "price", "");
		ebeefee=dbmanager.getValue(i, "ebeefee", "");
		mgrfee=dbmanager.getValue(i, "mgrfee", "");
        if(totamt==null || "".equals(totamt))
		totamt="0";
		if(ebeefee==null || "".equals(ebeefee))
		ebeefee="0";
		double memfee;
		try{
		memfee=Double.parseDouble(totamt)-Double.parseDouble(ebeefee);
		//netfee=Double.parseDouble(totamt)-Double.parseDouble(mgrfee);
		}
		catch(Exception e){
			System.out.println("Exception in getMembershipSignupsReport"+e.getMessage());
			memfee=0.00;
		}
		totamt=CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i, "price", "") , true);
		ebeefee=CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i, "ebeefee", "") , true);
		mgrfee=CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i, "mgrfee", "") , true);
		cardfee=dbmanager.getValue(i, "cardfee", "");
		if(cardfee==null)
		cardfee="0";
		if(mgrfee==null)
		mgrfee="0";
		if(memfee<0)
		memfee=0.0;
		if(discount==null)
		discount="0";
		String memfeeTemp=CurrencyFormat.getCurrencyFormat("",memfee+"", true);
		String cardfeeTemp=CurrencyFormat.getCurrencyFormat("",cardfee+"", true);
		rData.put("Membership Fee",memfeeTemp);
		rData.put("Ebee Fee", ebeefee);
		rData.put("Total Amount", totamt);
		rData.put("Card Fee",cardfeeTemp);
		rData.put("Manager Fee",mgrfee);
		String discountcode=dbmanager.getValue(i, "discountcode", "");
		if(discountcode==null)
		discountcode="";
		/*if(!"".equals(discountcode)){
		 discount=discount+"<br/>"+"("+discountcode+")";
		}*/
		rData.put("Discount Code",discountcode);

		rData.put("Discount",discount);
		try{
			 netfee=Double.parseDouble(totamt)-Double.parseDouble(mgrfee);
		}
		catch(Exception e){
		netfee=0.0;
		}
		if(netfee<0)
		netfee=0.0;
		String netfeeTemp=CurrencyFormat.getCurrencyFormat("",netfee+"", true);
		rData.put("Net Fee",netfeeTemp);
		resultData.add(rData);
	 	}//End of for
		}//End of if block.
		return resultData;
	}
	
	public static ArrayList<LinkedHashMap<String,String>> getRenewMembershipReport(String clubId,String userId,CommunityReportData reportData){
		ArrayList<LinkedHashMap<String,String>> resultData= new ArrayList<LinkedHashMap<String,String>>();
		
		String GET_RENEW_MEMBERSHIP_TILLDATE="select a.transactionid,first_name,last_name, "
            +" to_char(trandate,'mm/dd/yyyy') as trandate1,membership_name,totalamount as price,mgrfee,ebeefee,cardfee "
            +" from transaction a,user_profile c ,club_membership_master m,club_member p "
            +" where p.clubid=to_number(?,'99999999999') and a.refid=c.user_id and a.refid=p.userid " 
            +"and m.membership_id=p.membership_id and "
            +"purpose='MEMBER_SUBSCRIPTION' and to_char(trandate,'yyyy-mm-dd') < to_char(now(),'yyyy-mm-dd') "
            +"order by trandate desc";
		String GET_RENEW_MEMBERSHIP_BETWEENDATES="select a.transactionid,first_name,last_name,"
			 +" to_char(trandate,'mm/dd/yyyy') as trandate1,membership_name,totalamount as price,mgrfee," +
			 		"ebeefee,cardfee"
			 +" from transaction a,user_profile b,club_membership_master m,club_member p "
			 +" where  p.clubid=to_number(?,'99999999999') and a.refid=b.user_id and a.refid=p.userid " +
			 		"and m.membership_id=p.membership_id "
			 +" and purpose='MEMBER_SUBSCRIPTION' and trandate between to_date(?,'yyyy-mm-dd') "
			 +" and to_date(?,'yyyy-mm-dd') order by trandate desc";  
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		
		int selectedvalue=Integer.parseInt(reportData.getSelindex());
		
		if(selectedvalue==1){
			statobj=dbmanager.executeSelectQuery(GET_RENEW_MEMBERSHIP_TILLDATE,new String[]{clubId});
		}
		else if(selectedvalue==2){
			String startdate="",enddate="";
			if(!"".equals( reportData.getStartDay()) && !"".equals(reportData.getStartMonth()) && !"".equals(reportData.getStartYear()))
			startdate=reportData.getStartYear()+"-"+ reportData.getStartMonth()+"-"+ reportData.getStartDay();
			if(!"".equals(reportData.getEndDay()) && !"".equals(reportData.getEndMonth()) && !"".equals(reportData.getEndYear())){
			enddate=reportData.getEndYear()+"-"+reportData.getEndMonth()+"-"+reportData.getEndDay();
			enddate =enddate+" 23:59";
			}
			if(!"".equals(startdate) && !"".equals(enddate))
			statobj=dbmanager.executeSelectQuery(GET_RENEW_MEMBERSHIP_BETWEENDATES,new String[]{clubId,startdate,enddate});
		 }
		if(statobj !=null && statobj.getStatus()){
		 	int recordcounttodata=statobj.getCount();
		 	for(int i=0;i<recordcounttodata;i++){
		 	LinkedHashMap<String,String> rData=new LinkedHashMap<String,String>();
		 	rData.put("Date", dbmanager.getValue(i, "trandate1", ""));
			rData.put("Transaction ID", dbmanager.getValue(i, "transactionid", ""));
			
			String name=dbmanager.getValue(i, "first_name", "")+" "+dbmanager.getValue(i, "last_name", "");
			String ebeefee=dbmanager.getValue(i, "ebeefee", "");
			String totamt= dbmanager.getValue(i, "price", "");
			String mgrfee=dbmanager.getValue(i, "mgrfee", "");
			String cardFee=CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i, "cardfee", "") , true);
			double memfee=0.00;
			double netfee=0.00;
			if(totamt==null || "".equals(totamt))
				totamt="0.00";
			if(ebeefee==null || "".equals(ebeefee))
				ebeefee="0.00";
			try{
			memfee=Double.parseDouble(totamt)-Double.parseDouble(ebeefee);
			//netfee=Double.parseDouble(totamt)-Double.parseDouble(mgrfee);
			}
			catch(Exception e){
				System.out.println("Exception in getRenewMembershipReport for getting memfee"+e.getMessage());
			}
			if(memfee<0)
			memfee=0.0;
			
			ebeefee=CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i, "ebeefee", "") , true);
			totamt= CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i, "price", "") , true);
			mgrfee=CurrencyFormat.getCurrencyFormat("",dbmanager.getValue(i, "mgrfee", "") , true);
			rData.put("Name",name);
			rData.put("Type", dbmanager.getValue(i, "membership_name", ""));
			rData.put("Total Amount", totamt);
			rData.put("Eventbee Fee", ebeefee);
			rData.put("Processing Fee",cardFee);
			rData.put("Manager Fee",mgrfee);
			
			if(totamt==null || "".equals(totamt))
			totamt="0.00";
			if(mgrfee==null || "".equals(mgrfee))
			mgrfee="0.00";	
			try{
			netfee=Double.parseDouble(totamt)-Double.parseDouble(mgrfee);
			}catch(Exception e){
				System.out.println("Exception occured in getRenewMembershipReport for getting netfee:"+e.getMessage());
			}
			if(netfee<0)
			netfee=0.0;
			String netfeeTemp=CurrencyFormat.getCurrencyFormat("",netfee+"", true);
			
			rData.put("Net",netfeeTemp);
			resultData.add(rData);
		 	}//End of for.
		}//End of if block.
		return resultData;
	}
	
	public static ArrayList<HashMap<String,String>> getSalesReport(String clubId,String userId,CommunityReportData reportData){
		ArrayList<HashMap<String,String>> resultData= new ArrayList<HashMap<String,String>>();
		HashMap<String,String> rData=new HashMap<String,String>();
		resultData.add(rData);
		return resultData;
	}
	public static boolean saveMembershipType(String groupId,ClubMemberShip mship){
		String INSERT_CLUB_MEMBERSHIP="insert into club_membership_master(clubid,membership_id,membership_name," +
				"description,currency_type,price,created_by,created_at,updated_by,updated_at,mship_term,term_fee," +
				"cycle_type,status) "
			+" values(to_number(?,'99999999999'),nextval('seq_clubmembershipid'),?,?,'us',CAST(? as numeric)," +
					"'mshipadd',now(),'mshipadd',now(),?,CAST(? as numeric),?,?)";
		
		String UPDATE_CLUB_MEMBERSHIP="update club_membership_master set membership_name=?,description=?," +
				"price=CAST(? as numeric),updated_at='now()',mship_term=?,term_fee=CAST(? as numeric)" +
				"where clubid=to_number(?,'99999999999') and membership_id=to_number(?,'99999999999')";
		
		String membershipId=mship.getMemberShipId();
		String membershipName=mship.getMemberShipName();
		String description=mship.getDescription();
		String price=mship.getPrice();
		String term=mship.getMshipTerm();
		String termFee=mship.getTermPrice();
		String status="ACTIVE";
		String cycle_type="Join Date";
		if("".equals(membershipId)){
		DbUtil.executeUpdateQuery(INSERT_CLUB_MEMBERSHIP, new String[]{groupId,membershipName,description,
				price,term,termFee,cycle_type,status});
		}
		else{
		//Update Membership will follow here.
		DbUtil.executeUpdateQuery(UPDATE_CLUB_MEMBERSHIP, new String[]{membershipName,description,price,
					term,termFee,groupId,membershipId});
		}
		return true;
	}
	public static boolean deleteMembershipType(String groupId,String membershipId){
		String query="select count(*) from club_member_profile where membership_id=to_number(?,'99999999999')";
		String memberCount=DbUtil.getVal(query,new String[]{membershipId});
		if("0".equals(memberCount)){
			//Delete Membership only if there are no members with the membership id.
			String deleteQuery="delete from club_membership_master where membership_id=to_number(?,'99999999999')";
			DbUtil.executeUpdateQuery(deleteQuery, new String[]{membershipId});
		}
		return true;
	}
	public static ClubMemberShip getMembershipTypeData(String groupId,String membershipId){
		String query="select * from club_membership_master where clubid=to_number(?,'99999999999')" +
				" and membership_id=to_number(?,'99999999999')";
		DBManager dbmanager=new DBManager();
		ClubMemberShip mshipObj=new ClubMemberShip();
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{groupId,membershipId});
	 	if(statobj !=null && statobj.getStatus()){
	 		mshipObj.setMemberShipId(dbmanager.getValue(0,"membership_id", ""));
	 		mshipObj.setMemberShipName(dbmanager.getValue(0,"membership_name", ""));
	 		mshipObj.setDescription(dbmanager.getValue(0,"description" , ""));
	 		mshipObj.setMshipTerm(dbmanager.getValue(0,"mship_term", ""));
	 		mshipObj.setTermPrice(dbmanager.getValue(0,"term_fee", ""));
	 		mshipObj.setPrice(dbmanager.getValue(0,"price", ""));
	 	}
		return mshipObj;
	}
	public static ArrayList<LinkedHashMap<String,String>> getMemberReportData(String groupId,HashMap<String,HashMap<String,String>> attribresponse,ArrayList<Entity> customattributes, CommunityReportData reportData){
		String query="";
		String[] inputParams;
		int selectedvalue=Integer.parseInt(reportData.getSelindex());
		if(selectedvalue==1){
		query="select usp.user_id,first_name||' '||last_name as name,usp.email,member_id,membership_id," +
					"to_char(start_date,'MM/DD/YYYY') as start_date,to_char(pay_next_due_date,'MM/DD/YYYY') " +
					"as pay_next_due_date,acct_status, login_name," +
					"password,auth.membership_status from user_profile usp,club_member cmp, "+
					" authentication auth where  usp.user_id=cmp.userid and  auth.user_id=usp.user_id " +
					" and (ismgr='false' or ismgr is null) and cmp.clubid=to_number(?,'99999999999')";
		inputParams=new String[]{groupId};
		return processMemberReportData(query,inputParams,groupId,attribresponse,customattributes);
		}
		else if(selectedvalue==2){
		query="select usp.user_id,first_name||' '||last_name as name,usp.email,member_id,membership_id," +
					" to_char(start_date,'MM/DD/YYYY') as start_date,to_char(pay_next_due_date,'MM/DD/YYYY')" +
					" as pay_next_due_date,acct_status, login_name," +
					" password,auth.membership_status  from user_profile usp,club_member cmp, "+
					" authentication auth where usp.user_id=cmp.userid and  auth.user_id=usp.user_id " +
					" and (ismgr='false' or ismgr is null) and" +
					" (UPPER(usp.first_name) like ? or UPPER(usp.last_name) like ? or" +
					" UPPER(usp.first_name||' '||usp.last_name)  like ?) and cmp.clubid=to_number(?,'99999999999')";
		String name="%"+reportData.getName().trim()+"%";
		inputParams=new String[]{name.toUpperCase(),name.toUpperCase(),name.toUpperCase(),groupId};
		return processMemberReportData(query,inputParams,groupId,attribresponse,customattributes);
		}
		else if(selectedvalue==3){
		query="select usp.user_id,first_name||' '||last_name as name,usp.email,member_id,membership_id," +
					"to_char(start_date,'MM/DD/YYYY') as start_date,to_char(pay_next_due_date,'MM/DD/YYYY') " +
					"as pay_next_due_date,acct_status, login_name," +
					"password,auth.membership_status  from user_profile usp,club_member cmp, "+
					" authentication auth where  usp.user_id=cmp.userid and  auth.user_id=usp.user_id " +
					" and (ismgr='false' or ismgr is null)" +
					" and (UPPER(usp.email) like ?) and cmp.clubid=to_number(?,'99999999999')";
		String email="%"+reportData.getEmail().trim()+"%";
		inputParams=new String[]{email.toUpperCase(),groupId};
		return processMemberReportData(query,inputParams,groupId,attribresponse,customattributes);
		}
		else if(selectedvalue==4){
			query="select usp.user_id,first_name||' '||last_name as name,usp.email,member_id,membership_id," +
			"to_char(start_date,'MM/DD/YYYY') as start_date,to_char(pay_next_due_date,'MM/DD/YYYY')" +
			" as pay_next_due_date,acct_status, login_name," +
			"password,auth.membership_status  from user_profile usp,club_member cmp, "+
			" authentication auth where  usp.user_id=cmp.userid and  auth.user_id=usp.user_id " +
			" and start_date between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') and" +
			" cmp.clubid=to_number(?,'99999999999')";
			
			String startdate=reportData.getStYear()+"-"+ reportData.getStMonth()+"-"+ reportData.getStDay();
			String enddate=reportData.getEnYear()+"-"+reportData.getEnMonth()+"-"+reportData.getEnDay();
			enddate =enddate+" 23:59";
			inputParams=new String[]{startdate,enddate,groupId};
			return processMemberReportData(query,inputParams,groupId,attribresponse,customattributes);

		}
		else if(selectedvalue==6){
			query="select usp.user_id,first_name||' '||last_name as name,usp.email,member_id,membership_id," +
			"to_char(start_date,'MM/DD/YYYY') as start_date,to_char(pay_next_due_date,'MM/DD/YYYY')" +
			" as pay_next_due_date,acct_status, login_name," +
			" password,auth.membership_status  from user_profile usp,club_member cmp, "+
			" authentication auth where  usp.user_id=cmp.userid and  auth.user_id=usp.user_id" +
			" and (ismgr='false' or ismgr is null) and acct_status=to_number(?,'99999999999') and cmp.clubid=to_number(?,'99999999999')";
			String statusId=reportData.getTypeId();
			if("1".equals(statusId)){
				statusId="3";
			}
			inputParams=new String[]{statusId,groupId};
			return processMemberReportData(query,inputParams,groupId,attribresponse,customattributes);

		}
		else if(selectedvalue==5){
			query="select usp.user_id,first_name||' '||last_name as name,usp.email,member_id,membership_id," +
			"to_char(start_date,'MM/DD/YYYY') as start_date,to_char(pay_next_due_date,'MM/DD/YYYY')" +
			" as pay_next_due_date,acct_status, login_name," +
			"password,auth.membership_status  from user_profile usp,club_member cmp, "+
			" authentication auth where  usp.user_id=cmp.userid and  auth.user_id=usp.user_id" +
			" and (ismgr='false' or ismgr is null) and cmp.membership_id=to_number(?,'99999999999') and cmp.clubid=to_number(?,'99999999999')";
			inputParams=new String[]{reportData.getMembershipId(),groupId};
			return processMemberReportData(query,inputParams,groupId,attribresponse,customattributes);

		}
		else if(selectedvalue==7){
			int subSelectedvalue=Integer.parseInt(reportData.getSubSelIndex());
			if(subSelectedvalue==1){
				String hours="0 days";
				query="select usp.user_id,first_name||' '||last_name as name,usp.email,member_id,membership_id," +
				"to_char(start_date,'MM/DD/YYYY') as start_date,to_char(pay_next_due_date,'MM/DD/YYYY')" +
				" as pay_next_due_date,acct_status, login_name," +
				"password,auth.membership_status  from user_profile usp,club_member cmp, "+
				" authentication auth where  usp.user_id=cmp.userid and  auth.user_id=usp.user_id" +
				" and to_char(pay_next_due_date,'YYYY-MM-DD') <=to_char(now() -interval '"+hours +"','YYYY-MM-DD')" +
				" and cmp.clubid=to_number(?,'99999999999')";
				System.out.println("query"+query);
				inputParams=new String[]{groupId};
				return processMemberReportData(query,inputParams,groupId,attribresponse,customattributes);
			}
			else if(subSelectedvalue==2){
				String hours=reportData.getSubscrdaysmore()+" days";
				query="select usp.user_id,first_name||' '||last_name as name,usp.email,member_id,membership_id," +
				"to_char(start_date,'MM/DD/YYYY') as start_date,to_char(pay_next_due_date,'MM/DD/YYYY')" +
				" as pay_next_due_date,acct_status, login_name," +
				"password,auth.membership_status  from user_profile usp,club_member cmp, "+
				" authentication auth where  usp.user_id=cmp.userid and  auth.user_id=usp.user_id" +
				" and to_char(pay_next_due_date,'YYYY-MM-DD')  between " +
				"to_char(now() -interval '"+hours +"','YYYY-MM-DD') and to_char(now(),'YYYY-MM-DD')" +
				" and cmp.clubid=to_number(?,'99999999999')";
				inputParams=new String[]{groupId};
				return processMemberReportData(query,inputParams,groupId,attribresponse,customattributes);
			}
			else if(subSelectedvalue==3){
				String hours=reportData.getSubscrdays()+" days";
				query="select usp.user_id,first_name||' '||last_name as name,usp.email,member_id,membership_id," +
				"to_char(start_date,'MM/DD/YYYY') as start_date,to_char(pay_next_due_date,'MM/DD/YYYY')" +
				" as pay_next_due_date,acct_status, login_name," +
				"password,auth.membership_status  from user_profile usp,club_member cmp, "+
				" authentication auth where  usp.user_id=cmp.userid and  auth.user_id=usp.user_id" +
				" and  to_char(pay_next_due_date,'YYYY-MM-DD') between  to_char(now(),'YYYY-MM-DD') and  " +
				"to_char(now() +interval '"+hours +"','YYYY-MM-DD') and" +
				" cmp.clubid=to_number(?,'99999999999')";
				System.out.println("query"+query);
				inputParams=new String[]{groupId};
				return processMemberReportData(query,inputParams,groupId,attribresponse,customattributes);
			}

		}
		
		return null;
	}
	public static ArrayList getAttributes(String setid){
		String RESPONSE_QUERY_FOR_ATTRIBUTE="select distinct  attrib_name from custom_attrib_response a,custom_attrib_response_master b"
		  				   +" where a.responseid  =b.responseid   and b.attrib_setid=to_number(?,'99999999999')";

		DBManager dbmanager=new DBManager();
		ArrayList attribs_list=new ArrayList();
		StatusObj statobj=null;
		HashMap hm=new HashMap();
		statobj=dbmanager.executeSelectQuery(RESPONSE_QUERY_FOR_ATTRIBUTE,new String []{setid});
				int count1=statobj.getCount();
					if(statobj.getStatus()&&count1>0){
					for(int k=0;k<count1;k++){
						  hm.put(dbmanager.getValue(k,"attrib_name",""),"y");
	                     if(!"".equals(dbmanager.getValue(k,"attrib_name","")))
						attribs_list.add(new Entity(dbmanager.getValue(k,"attrib_name",""),dbmanager.getValue(k,"attrib_name","")));
					}
			}
		
		
		return attribs_list;
	 }
	public static ArrayList<LinkedHashMap<String,String>> processMemberReportData(String query,String[] inputParams,
		String groupId,HashMap<String,HashMap<String,String>> attribresponse,ArrayList<Entity> customattributes){
		
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(query,inputParams);
		ArrayList<LinkedHashMap<String,String>> memberDataList=new ArrayList<LinkedHashMap<String,String>>();	
		if(statobj !=null && statobj.getStatus()){
		 	int recordcounttodata=statobj.getCount();
		 	for(int i=0;i<recordcounttodata;i++){
		 		String usrId = dbmanager.getValue(i,"user_id","");
		 		LinkedHashMap<String,String> memberInfoMap=new LinkedHashMap<String,String>();
		 		memberInfoMap.put("Name",dbmanager.getValue(i,"name",""));
		 		memberInfoMap.put("Email",dbmanager.getValue(i,"email",""));
		 		memberInfoMap.put("usrId",dbmanager.getValue(i,"user_id",""));
		 				 		
		 		HashMap<String,String> attribhm=null;
				if (attribresponse!=null&&attribresponse.size()>0)		
					attribhm=attribresponse.get(dbmanager.getValue(i,"user_id",""));
					if(attribhm!=null&&attribhm.size()>0){
						for(int p=0;p<customattributes.size();p++){
							Entity a=customattributes.get(p);
							String val=(String)attribhm.get(a.getKey());
							if(val==null)val="";
							memberInfoMap.put(a.getKey(),GenUtil.AllXMLEncode(val));
						}
					}
					else{
						for(int p=0;p<customattributes.size();p++){
							Entity a= customattributes.get(p);
							memberInfoMap.put(a.getKey(),"");
						}
					}
					HashMap<String,String> memshipTypeMap=new HashMap<String,String>();
			 		memshipTypeMap=getMemberships(groupId);
			 		memberInfoMap.put("Membership Type",memshipTypeMap.get(dbmanager.getValue(i,"membership_id","")));
			 		memberInfoMap.put("Join Date",dbmanager.getValue(i,"start_date",""));
			 		memberInfoMap.put("Due Date",dbmanager.getValue(i,"pay_next_due_date",""));
			 		HashMap<String,String> tempObj=new HashMap<String,String>();
			 		tempObj=getStatusTypes();
			 		/*String membertype=DbUtil.getVal("select value from community_config_settings where " +
			 				"key='MEMBER_ACCOUNT_TYPE' and clubid=? ",new String [] {groupId});*/
			 		String memactstatus="";
			 		if("3".equals(dbmanager.getValue(i,"acct_status",""))){
			 			memactstatus="Active";
			 		}
			 		else
			 			memactstatus=(String) tempObj.get(dbmanager.getValue(i,"acct_status",""));
			 		memberInfoMap.put("Status",memactstatus);
			 		memberDataList.add(memberInfoMap);
		 	}//End of for loop.
		}//End of if block.
		return memberDataList;
	}
	public static HashMap<String,String> getMemberships(String clubid){
		String query="select membership_id,membership_name from club_membership_master where " +
				"clubid=to_number(?,'99999999999') order by created_at";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{clubid});
		HashMap<String,String> returnObj=new HashMap<String,String>();
		if(statobj !=null && statobj.getStatus()){
		 	int recordcounttodata=statobj.getCount();
		 	for(int i=0;i<recordcounttodata;i++){
		 		String name=GenUtil.getEncodedXML( dbmanager.getValue(i,"membership_name",""));
				name="members".equals(name.trim())?" ":name;
		 		returnObj.put(dbmanager.getValue(i,"membership_id",""),name);
		 	}
		}
		return returnObj;
	}
	public static ArrayList<Entity> getMemberships(String clubid,String purpose){
		String query="select membership_id,membership_name from club_membership_master where " +
				"clubid=to_number(?,'99999999999') order by created_at";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{clubid});
		ArrayList<Entity> returnObj=new ArrayList<Entity>();
		if(statobj !=null && statobj.getStatus()){
		 	int recordcounttodata=statobj.getCount();
		 	for(int i=0;i<recordcounttodata;i++){
		 		
		 		String name=GenUtil.getEncodedXML( dbmanager.getValue(i,"membership_name",""));
				name="members".equals(name.trim())?" ":name;
				Entity entObj=new Entity(dbmanager.getValue(i,"membership_id",""),name);
		 		returnObj.add(entObj);
		 	}
		}
		return returnObj;
	}
	public static HashMap<String,String> getStatusTypes(){
		String query="select type_desc,type_id from account_type where type_id in(1,2,6,8,9)";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(query,null);
		HashMap<String,String> returnObj=new HashMap<String,String>();
		if(statobj !=null && statobj.getStatus()){
		 	int recordcounttodata=statobj.getCount();
		 	for(int i=0;i<recordcounttodata;i++){
		 		returnObj.put(dbmanager.getValue(i,"type_id",""),dbmanager.getValue(i,"type_desc",""));
		 	}
		}
		return returnObj;
	}
	public static ArrayList<Entity> getStatusTypes(String purpose){
		String query="select type_desc,type_id from account_type where type_id in(1,2,6,8,9)";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(query,null);
		ArrayList<Entity> returnObj=new ArrayList<Entity>();
		if(statobj !=null && statobj.getStatus()){
		 	int recordcounttodata=statobj.getCount();
		 	for(int i=0;i<recordcounttodata;i++){
		 		Entity entObj=new Entity(dbmanager.getValue(i,"type_id",""),dbmanager.getValue(i,"type_desc",""));
		 		returnObj.add(entObj);
		 	}
		}
		return returnObj;
	}
	public static HashMap<String,HashMap<String,String>> getResponses(String setid){
		String RESPONSE_QUERY="select attrib_name,response,userid,a.responseid,b.attrib_setid from " +
				"custom_attrib_response a,custom_attrib_response_master b where a.responseid=b.responseid  " +
				"and b.attrib_setid=to_number(?,'999999999999999') order by a.responseid";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		HashMap<String,HashMap<String,String>> hm=null;
		statobj=dbmanager.executeSelectQuery(RESPONSE_QUERY,new String []{setid});
		int count=statobj.getCount();
		if(statobj.getStatus()&&count>0){
				hm=new HashMap<String,HashMap<String,String>>();
				for(int k=0;k<count;k++){
					String userid=dbmanager.getValue(k,"userid","");
					HashMap<String,String> options= hm.get(userid);
					if (options==null)
						options=new HashMap<String,String>();
					options.put(dbmanager.getValue(k,"attrib_name","0"),dbmanager.getValue(k,"response","0"));
					hm.put(userid,options);
				}
		}

	return hm;
}
	public static ClubInfo getClubInfo(String clubId){
		String GET_CLUB_INFO="select * from  clubinfo where clubid=to_number(?,'999999999999999')";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		ClubInfo cData=new ClubInfo();
		statobj=dbmanager.executeSelectQuery(GET_CLUB_INFO,new String []{clubId});
		int count=statobj.getCount();
		if(statobj.getStatus()&&count>0){
			cData.setClubId(dbmanager.getValue(0,"clubid",""));
			cData.setClubName(dbmanager.getValue(0,"clubname",""));
			cData.setManagerId(dbmanager.getValue(0,"mgr_id",""));
			cData.setCommunityURL(dbmanager.getValue(0, "clublogo", ""));
			cData.setCategory(dbmanager.getValue(0, "category", ""));
			cData.setCity(dbmanager.getValue(0, "city", ""));
			cData.setCountry(dbmanager.getValue(0, "country", ""));
			cData.setDescription(dbmanager.getValue(0, "description", ""));
		}
		return cData;
	}
	public static ArrayList<HashMap<String,String>> getCommunitiesList(String userId){
		ArrayList<HashMap<String,String>> cList=new ArrayList<HashMap<String,String>>();
		String selQry="select clubid,clubname,description,city,state," +
				"to_char(created_at,'Mon DD, YYYY')" +
				" as created_at,country,status from clubinfo ci," +
				"config where ci.config_id=config.config_id and upper(config.value)='PAID' " +
				"and config.name='club.memberapproval.type' and mgr_id=to_number(?,'999999999999999') order by " +
				"created_at desc";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(selQry,new String []{userId});
		int count=statobj.getCount();
		if(statobj.getStatus()&&count>0){
			for(int k=0;k<count;k++){
				HashMap<String,String> hm =new HashMap<String,String>();
				hm.put("clubid",dbmanager.getValue(k,"clubid",""));
				hm.put("clubname",dbmanager.getValue(k,"clubname",""));
				hm.put("city",dbmanager.getValue(k,"city",""));
				hm.put("state",dbmanager.getValue(k,"state",""));
				hm.put("created_at",dbmanager.getValue(k,"created_at",""));
				hm.put("country",dbmanager.getValue(k,"country",""));
				hm.put("status",dbmanager.getValue(k,"status",""));
				cList.add(hm);
			}//End of for loop.
		}//End of if block.
		return cList;
	}//End of getCommunitiesList.

	public static HashMap<String,String> getClubData(String userId,String clubId){
		String GET_HUB_INFO="select c.config_id,c.clubname,c.status,c.mgr_id,c.unitid," +
 		"c.description,c.clublogo,c.category,c.city,c.state,c.country,to_char(c.created_at,'Mon DD, YYYY')" +
 		" as created_at,to_char(c.updated_at,'Mon DD, YYYY') as updated_at,clubtype,created_by" +
 		" from clubinfo c where c.clubid=to_number(?,'999999999999999') ";
		String HUBMANAGER_QUERY="select first_name,last_name,email,user_id from user_profile u,club_member c " +
				"where u.user_id=c.userid and c.clubid=to_number(?,'999999999999999') and " +
				"upper(c.ismgr)='TRUE'";
		HashMap<String,String> clubMap=new HashMap<String,String>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(GET_HUB_INFO,new String []{clubId});
			int count=statobj.getCount();
			if(statobj.getStatus()&&count>0){
				clubMap.put("clubid",dbmanager.getValue(0,"hubid",""));
				clubMap.put("clubname",dbmanager.getValue(0,"clubname",""));
				clubMap.put("mgr_id",dbmanager.getValue(0,"mgr_id",""));
				clubMap.put("unitid",dbmanager.getValue(0,"unitid",""));
				clubMap.put("description",dbmanager.getValue(0,"description",""));
				clubMap.put("category",dbmanager.getValue(0,"category",""));
				clubMap.put("city",dbmanager.getValue(0,"city",""));
				clubMap.put("state",dbmanager.getValue(0,"state",""));
				clubMap.put("country",dbmanager.getValue(0,"country",""));
				clubMap.put("config_id",dbmanager.getValue(0,"config_id",""));
				clubMap.put("name",dbmanager.getValue(0,"name",""));
				clubMap.put("created_at",dbmanager.getValue(0,"created_at",""));
				clubMap.put("updated_at",dbmanager.getValue(0,"updated_at",""));
				clubMap.put("status",dbmanager.getValue(0,"status",""));
				clubMap.put("members",dbmanager.getValue(0,"members",""));
				clubMap.put("code",dbmanager.getValue(0,"clublogo",""));
				clubMap.put("clubtype",dbmanager.getValue(0,"clubtype",""));
				clubMap.put("created_by",dbmanager.getValue(0,"created_by",""));
				DBManager dbmanager1=new DBManager();
				StatusObj statobj1=dbmanager1.executeSelectQuery(HUBMANAGER_QUERY,new String []{clubId});
				if(statobj1.getStatus()){
				String name=(dbmanager1.getValue(0,"first_name","").trim()+" "+dbmanager1.getValue(0,"last_name","")).trim();
				clubMap.put("moderator",name);
				}
				clubMap=LoadConfigData.getConfig(dbmanager.getValue(0,"config_id",""),clubMap);
				/*String membertype=DbUtil.getVal("select value from community_config_settings where" +
						" key='MEMBER_ACCOUNT_TYPE' and clubid=?",new String [] {clubId});*/
				/*String activecount="select count(*) from mail_list_members b,member_profile c where" +
						" b.status='available' and b.member_id=c.member_id and  " +
						"b.list_id in (select list_id from mail_list where list_name " +
						"like 'Active Members%' and manager_id=to_number(?,'999999999999999'))";
				String passivecount="select count(*) from mail_list_members b,member_profile c where" +
						" b.status='available' and b.member_id=c.member_id and  " +
						"b.list_id in (select list_id from mail_list where list_name like 'Passive Members%' " +
						"and manager_id=to_number(?,'999999999999999'))";*/
				/*String acount=DbUtil.getVal(activecount,new String []{userId}); 
			    String pcount=DbUtil.getVal(passivecount,new String []{userId}); */
				String mcount="";
				//clubMap.put("membertype",membertype);
				//if("EXCLUSIVE".equals(membertype)){
					String qry="select count(*) from club_member where clubid =to_number(?,'999999999999999')";
				    mcount=DbUtil.getVal(qry,new String []{clubId});  
				    clubMap.put("members",mcount);
				/*}else{
					clubMap.put("acount",acount);
					clubMap.put("pcount",pcount);
				}*/
			}//End of if block.
		return clubMap;
	}//End of getClubData() method
	static final String GET_CLUB_INFO="select a.clublogo,a.config_id,a.clubid,a.clubname,a.unitid,a.category,a.description,a.city,a.state,a.country,a.mgr_id, "
		+" a.created_at from clubinfo a where clubid=to_number(?,'99999999999') ";

	public static HashMap<String,String> getClubDetails(String clubId){
		HashMap<String,String> clubinfo=new HashMap<String,String>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(GET_CLUB_INFO,new String []{clubId});
		 if(statobj.getStatus()){
				for(int i=0;i<statobj.getCount();i++){
					clubinfo.put("clubname",dbmanager.getValue(i,"clubname",""));
					clubinfo.put("unitid",dbmanager.getValue(i,"unitid",""));
					clubinfo.put("city",dbmanager.getValue(i,"city",""));
					clubinfo.put("state",dbmanager.getValue(i,"state",""));
					clubinfo.put("country",dbmanager.getValue(i,"country",""));
					clubinfo.put("description",dbmanager.getValue(i,"description",""));
					clubinfo.put("created_at",dbmanager.getValue(i,"created_at",""));
					clubinfo.put("code",dbmanager.getValue(i,"clublogo",""));
					clubinfo.put("mgr_id",dbmanager.getValue(i,"mgr_id",""));
					String cluburl=DbUtil.getVal("select value from config where config_id=to_number(?,'99999999999') and name=?",new String[]{dbmanager.getValue(i,"config_id",""),"club.url"});
					if(cluburl==null) cluburl="";
					if(!("".equals(cluburl.trim()))){
						if(!(cluburl.startsWith("http"))){
							cluburl="http://"+cluburl;
						}
					}
					clubinfo.put("cluburl",cluburl);
				}				
		 }
		 return clubinfo;
	}
	
	public static void createCommunity(ClubInfo clubInfo, String mgrid){
		String SEQ_GET="select nextval('seq_configid') as configid ";
		String SEQ_GET_GROUPID="select nextval('seq_groupid') as groupid ";
		String INSERT_CONFIG="insert into config (config_id,name,value) values (to_number(?,'999999999999999'),?,?) ";		
		String configid=DbUtil.getVal(SEQ_GET,null);
		String groupid=DbUtil.getVal(SEQ_GET_GROUPID,null);
		String INSERT_HUB_QUERY="insert into clubinfo (config_id,clubid,clubname,unitid,mgr_id,description," +
								"category,city,country,created_by,created_at,updated_at,status,clublogo,desctype) "
								+" values(to_number(?,'999999999999999'),to_number(?,'999999999999999'),?,CAST('13579' as integer)," +
								"to_number(?,'999999999999999'),?,?,?,?,'com.eventbee.hub.hubInfo',now(),now(),'ACTIVE',?,'html') ";
		String INSERTMEM="insert into club_member(clubid,userid,isMgr,status, "			
						+" created_by,created_at,updated_by,updated_at) "
						+" values(to_number(?,'999999999999999'),?,?,?, "			
						+" ?,now(),?,now()) ";
		String INSERT_FORUM="insert into forum(forumid,groupid,"
							+" grouptype,forumname,owner,description,createdby,updatedby,status)"
							+" values(to_number(?,'999999999999999'),to_number(?,'999999999999999'),?,?,to_number(?,'999999999999999'),?,?,?,?)";
		String forumid=DbUtil.getVal("select nextVal('seq_forum')",null);
		
		StatusObj stobj=DbUtil.executeUpdateQuery(INSERT_HUB_QUERY,new String[]{configid,groupid,clubInfo.getClubName(),mgrid,clubInfo.getDescription(),clubInfo.getCategory(),clubInfo.getCity(),clubInfo.getCountry(),clubInfo.getCommunityURL()});
		//System.out.println("club info insert status:::::::::"+stobj.getStatus());
		if(stobj.getCount()>0){
			stobj.setData(groupid);
			/*for(int l=0;l<2;l++){
					String listname="";
					if(l==0) listname="Active Members -"+clubInfo.getClubName();
					else listname="Passive Members -"+clubInfo.getClubName();
					StatusObj stobinsert=DbUtil.executeUpdateQuery("insert into mail_list(list_id,list_desc,unit_id,role,list_name," +
							"manager_id,created_at)values(nextval('seq_list'),?,to_number(?,'999999999999999'),?,?,to_number(?,'999999999999999'),now())",
							new String []{"Hub maillist",groupid,"MGR",listname,mgrid});
					//System.out.println("stobinsert::mail list:::::::"+stobinsert.getStatus());
			}*/
			DbUtil.executeUpdateQuery(INSERT_CONFIG,new String[]{configid,"club.memberapproval.type","Paid"});
			DbUtil.executeUpdateQuery(INSERT_CONFIG,new String[]{configid,"hub.forumalert.enable","yes"});

			StatusObj statusobj=DbUtil.executeUpdateQuery("insert into user_roller_themes " +
					"(cssurl,themecode,themetype,userid,module,refid) values(?,?,?,to_number(?,'999999999999999'),?,?)",
					new String []{"basic.css","basic","DEFAULT",mgrid,"hubpage",groupid} );
			StatusObj statusobjinertmem=DbUtil.executeUpdateQuery(INSERTMEM,new String[]{groupid,mgrid,"true","ACTIVE","Manager","Manager"});
			StatusObj statusobjINSERT_FORUM=DbUtil.executeUpdateQuery(INSERT_FORUM,new String[]{forumid,groupid,"Club",clubInfo.getClubName()+" Forum",mgrid,"Add Your Discussion topics here","com.mycommunities.dbhelpers.CommunityDB","com.mycommunities.dbhelpers.CommunityDB","Yes"});
			}
			
		}
	
	
	public static void editCommunity(ClubInfo clubInfo){
		String query = "update clubinfo set clubname=?,description=?,category=?," +
				"city=?,country=?,clublogo=?,updated_at=now() where clubid=to_number(?,'999999999999999')";
		DbUtil.executeUpdateQuery(query, new String[]{clubInfo.getClubName(),
				clubInfo.getDescription(),clubInfo.getCategory(),clubInfo.getCity(),
				clubInfo.getCountry(),clubInfo.getCommunityURL(),clubInfo.getClubId()});
		
	}
		

}

	
