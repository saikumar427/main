package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.event.beans.RegEmailSettingsData;
import com.event.beans.ticketing.VolumeTicketData;
import com.eventbee.beans.DateTimeBean;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateConverter;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.CurrencyFormat;

public class VolumeTicketsDB {
	static String[] monthvals=new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
	public static boolean saveVolumeTicketData(VolumeTicketData volTicketData, String eid,String userTimeZone){
		
		String ticketNm=volTicketData.getTicketName().trim();
		String description = volTicketData.getTicketDescription().trim();
		String price = volTicketData.getTicketPrice().trim();
		String discountType = volTicketData.getDiscountType();
		String discountVal=volTicketData.getAmountDiscount().trim();
		if(discountType.equals("A"))
			discountVal=volTicketData.getAmountDiscount().trim();
		else{
			//double dval=  (Double.parseDouble(price) * Double.parseDouble(volTicketData.getPercentageDiscount()))/100;
			//discountVal=CurrencyFormat.getCurrencyFormat("",Double.toString(dval),false);
			discountVal=volTicketData.getPercentageDiscount().trim();
		}
		String triggerQty = volTicketData.getTriggerQty().trim();
		String minQty = volTicketData.getMinQty().trim();
		String maxQty = volTicketData.getMaxQty().trim();
		String activeDuration = volTicketData.getActiveDuration().trim();
		String postTriggerType=volTicketData.getPostTriggerType();
		String upperLimit = volTicketData.getUpperLimit().trim();
		if("".equals(upperLimit))
			upperLimit="0";
		
		if("2".equals(postTriggerType))
			upperLimit=String.valueOf(Integer.parseInt(upperLimit)+Integer.parseInt(triggerQty));
		
		String cyclesLimit = volTicketData.getCycles().trim();
		if("".equals(cyclesLimit))
			cyclesLimit="0";
		String triggerFailAction = volTicketData.getTriggerFailAction();
		String failDiscount=volTicketData.getTriggerFailDiscount().trim();
		if("".equals(failDiscount))
			failDiscount="0";
		String processingFee="0.00";
		if("processFeeAttendee".equalsIgnoreCase(volTicketData.getProcessFeePaidBy())){
		    processingFee=volTicketData.getProcessingFee().trim();
		}
		String toTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
		DateConverter dc=new DateConverter(userTimeZone,toTimeZone);
		DateTimeBean dtb=volTicketData.getStdateTimeBeanObj();
		boolean statusdc=dc.convertDateTime(dtb);
		
		String startDate=dc.getDatePart();
		String startTime=dc.getTimePart();
		
		dtb=volTicketData.getEnddateTimeBeanObj();
		statusdc=dc.convertDateTime(dtb);
		
		String endDate=dc.getDatePart();
		String endTime=dc.getTimePart();
		
		String startdate_tz=volTicketData.getStdateTimeBeanObj().getYyPart()+"-"+volTicketData.getStdateTimeBeanObj().getMonthPart()+"-"+volTicketData.getStdateTimeBeanObj().getDdPart();
		String starttime_tz=DateConverter.getHH24(volTicketData.getStdateTimeBeanObj().getHhPart(), volTicketData.getStdateTimeBeanObj().getAmpm())+":"+volTicketData.getStdateTimeBeanObj().getMmPart();
		if(volTicketData.getTicketId().equals("")){
			
			
			//String approvalStatus=getApprovalStatus(eid);
			String approvalStatus="A";
			String showStatus="Y";
			String soldQty="0";
			String currentSoldQty ="0";
			String currentCycle="1";
			
			String query="select nextval('seq_priceid') as priceid";
			String ticketId=DbUtil.getVal(query, null);
			String insertquery="insert into groupdeal_tickets (eventid,ticketid,ticket_name,description,price," +
					"discount_type,discount_value,trigger_qty,min_qty,max_qty,approval_status,show_status," +
					"sold_qty,current_soldqty,current_cycle,post_trigger_type,upper_limit,cycles_limit," +
					"trigger_fail_action,fail_discount,fee,act_start_date,act_starttime,end_date,end_time," +
					"active_duration,ticket_type,showhide,created_at,last_updated,start_date_tz,start_time_tz,sale_status) " +
					"values(?,CAST(? AS INTEGER),?,?,CAST(? AS NUMERIC),CAST(? AS CHAR)," +
					"CAST(? AS NUMERIC),CAST(? AS INTEGER),CAST(? AS INTEGER),CAST(? AS INTEGER)," +
					"CAST(? AS CHAR),CAST(? AS CHAR),CAST(? AS BIGINT),CAST(? AS INTEGER),CAST(? AS INTEGER)," +
					"CAST(? AS INTEGER),CAST(? AS INTEGER),CAST(? AS INTEGER),CAST(? AS INTEGER)," +
					"CAST(? AS NUMERIC),CAST(? AS NUMERIC),to_date(?,'YYYY MM DD'),?,to_date(?,'YYYY MM DD'),?,?,?,'Y'," +
					"to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_date(?,'YYYY MM DD'),?,?)";
			
			String[] inputParams=new String[]{eid,ticketId,ticketNm,description,price,discountType,discountVal,
					triggerQty,minQty,maxQty,approvalStatus,showStatus,soldQty,currentSoldQty,currentCycle,
					postTriggerType,upperLimit,cyclesLimit,triggerFailAction,failDiscount,processingFee,startDate,
					startTime,endDate,endTime,activeDuration,"isattendee",DateUtil.getCurrDBFormatDate(),
					DateUtil.getCurrDBFormatDate(),startdate_tz,starttime_tz,"Available"};
			
			DbUtil.executeUpdateQuery(insertquery, inputParams);
			System.out.println("inserted data in groupdeal_tickets successfully");
			
			CustomAttributesDB.saveTicketBaseProfileSettings(eid,"fname",ticketId,"Y");
			CustomAttributesDB.saveTicketBaseProfileSettings(eid,"lname",ticketId,"Y");
			generateWidgetCode(ticketId,eid);
			
		}else{
			
			String updateQuery="update groupdeal_tickets set ticket_name=?,description=?,price=CAST(? AS NUMERIC)," +
					"discount_type=CAST(? AS CHAR),discount_value=CAST(? AS NUMERIC),trigger_qty=CAST(? AS INTEGER)," +
					"min_qty=CAST(? AS INTEGER),max_qty=CAST(? AS INTEGER),post_trigger_type=CAST(? AS INTEGER)," +
					"upper_limit=CAST(? AS INTEGER),cycles_limit=CAST(? AS INTEGER),trigger_fail_action=CAST(? AS INTEGER)," +
					"fail_discount=CAST(? AS NUMERIC),fee=CAST(? AS NUMERIC),act_start_date=to_date(?,'YYYY MM DD')," +
					"act_starttime=?,end_date=to_date(?,'YYYY MM DD'),end_time=?,active_duration=?," +
					"last_updated=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),start_date_tz=to_date(?,'YYYY MM DD'),start_time_tz=? where eventid=? and ticketid=CAST(? AS INTEGER) ";

			String[] inputParams=new String[]{ticketNm,description,price,discountType,discountVal,triggerQty,
					minQty,maxQty,postTriggerType,upperLimit,cyclesLimit,triggerFailAction,failDiscount,
					processingFee,startDate,startTime,endDate,endTime,activeDuration,DateUtil.getCurrDBFormatDate(),
					startdate_tz,starttime_tz,eid,volTicketData.getTicketId()};

			DbUtil.executeUpdateQuery(updateQuery, inputParams);
			System.out.println("updated data in groupdeal_tickets successfully where ticketid="+volTicketData.getTicketId());
			
		}
		
		return true;
	}
	
	public static VolumeTicketData getVolumeTicketData(String eid, String ticketId,String userTimeZone){
		VolumeTicketData volTicketData = new VolumeTicketData();
		
		String selectQry = "select ticket_name,description,price,discount_type,discount_value,trigger_qty," +
				" min_qty,max_qty,approval_status,show_status,sold_qty,current_soldqty,current_cycle," +
				" post_trigger_type,upper_limit,cycles_limit,trigger_fail_action,fail_discount,fee," +
				" act_start_date,act_starttime,active_duration from groupdeal_tickets where eventid=? and ticketid=CAST(? AS INTEGER)";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(selectQry,new String []{eid,ticketId});
		int count=statobj.getCount();
		if(statobj.getStatus()&&count>0){
			volTicketData.setTicketName(dbmanager.getValue(0,"ticket_name",""));
			volTicketData.setTicketDescription(dbmanager.getValue(0,"description",""));
			volTicketData.setTicketPrice(dbmanager.getValue(0,"price",""));
			String discountType = dbmanager.getValue(0,"discount_type","");
			volTicketData.setDiscountType(discountType);
			if(discountType.equals("A"))
				volTicketData.setAmountDiscount(dbmanager.getValue(0,"discount_value",""));
			else
				volTicketData.setPercentageDiscount(dbmanager.getValue(0,"discount_value",""));
			
			
			volTicketData.setMinQty(dbmanager.getValue(0,"min_qty",""));
			volTicketData.setMaxQty(dbmanager.getValue(0,"max_qty",""));
			
			volTicketData.setCycles(dbmanager.getValue(0,"cycles_limit",""));
			volTicketData.setTriggerFailAction(dbmanager.getValue(0,"trigger_fail_action",""));
			volTicketData.setTriggerFailDiscount(dbmanager.getValue(0,"fail_discount",""));
			volTicketData.setProcessingFee(dbmanager.getValue(0,"fee",""));
			String processingFee = dbmanager.getValue(0,"fee","");
			processingFee=CurrencyFormat.getCurrencyFormat("",processingFee,false);
			if(!"0.00".equals(processingFee)){
				volTicketData.setProcessFeePaidBy("processFeeAttendee");
			}
			else{
				volTicketData.setProcessFeePaidBy("processFeeMgr");
			}
			String triggerQty=dbmanager.getValue(0,"trigger_qty","");
			String postTrigType=dbmanager.getValue(0,"post_trigger_type","");
			String upperLimit=dbmanager.getValue(0,"upper_limit","");
			if("2".equals(postTrigType))
				upperLimit=String.valueOf(Integer.parseInt(upperLimit)-Integer.parseInt(triggerQty));
			volTicketData.setTriggerQty(triggerQty);
			volTicketData.setPostTriggerType(postTrigType);
			volTicketData.setUpperLimit(upperLimit);
			
			String start_date=dbmanager.getValue(0,"act_start_date","");
			String start_time=dbmanager.getValue(0,"act_starttime","");
			String fromTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
			DateConverter dc=new DateConverter(fromTimeZone,userTimeZone);
			boolean statusdc=dc.convertDateTime(start_date,start_time,userTimeZone);
			DateTimeBean stdtb=dc.getDateTimeBeanObj();
			volTicketData.setStdateTimeBeanObj(stdtb);
			volTicketData.setActiveDuration(dbmanager.getValue(0,"active_duration",""));
			volTicketData.setTicketId(ticketId);
		}
		return volTicketData;
	}
	
	public static ArrayList<VolumeTicketData> getVolumeTicketsList(String eid){
		ArrayList<VolumeTicketData> volumeTicketList=new ArrayList<VolumeTicketData>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		
		String volTicketListQuery="select ticketid,ticket_name,price,fee,approval_status,show_status,discount_type,discount_value," +
				"trigger_qty,sold_qty,current_soldqty,current_cycle,upper_limit,cycles_limit,post_trigger_type,sale_status,showhide,"+
				" case when act_start_date+cast(cast(to_timestamp(COALESCE(act_starttime,'00'),'HH24:MI:SS') as text) as time )<=current_timestamp"+ 
				" then 'before' else 'after' end as starttimestatus,"+
				" case when end_date+cast(cast(to_timestamp(COALESCE(end_time,'00'),'HH24:MI:SS') as text) as time )<=current_timestamp"+ 
				" then 'before' else 'after' end as endtimestatus from groupdeal_tickets where eventid=? order by ticketid";
		statobj=dbmanager.executeSelectQuery(volTicketListQuery,new String []{eid});
		int count=statobj.getCount();
		if(statobj.getStatus()&&count>0){
			for(int k=0;k<count;k++){
				VolumeTicketData volTicketData=new VolumeTicketData();
				volTicketData.setTicketId(dbmanager.getValue(k,"ticketid",""));
				volTicketData.setTicketName(dbmanager.getValue(k,"ticket_name",""));
				volTicketData.setTriggerQty(dbmanager.getValue(k,"trigger_qty",""));
				volTicketData.setTicketPrice(dbmanager.getValue(k,"price",""));
				String price = dbmanager.getValue(k,"price","");
				String discountValue = dbmanager.getValue(k,"discount_value","");
				String discountType = dbmanager.getValue(k,"discount_type","");
				
				int triggerQty = Integer.parseInt(dbmanager.getValue(k,"trigger_qty","0"));
				int soldQty = Integer.parseInt(dbmanager.getValue(k,"sold_qty","0"));
				int currSoldQty = Integer.parseInt(dbmanager.getValue(k,"current_soldqty","0"));
				int currCycle = Integer.parseInt(dbmanager.getValue(k,"current_cycle","0"));
				int upperLimit = Integer.parseInt(dbmanager.getValue(k,"upper_limit","0"));
				int cycleLimit = Integer.parseInt(dbmanager.getValue(k,"cycles_limit","0"));
				int posttrigtype = Integer.parseInt(dbmanager.getValue(k,"post_trigger_type","0"));
				
				if(price.equals("") || price==null)
					price="0.00";
				if(discountValue.equals("") || discountValue==null)
					discountValue="0.00";
				
				if("P".equals(discountType)){
					double dval=  (Double.parseDouble(price) * Double.parseDouble(discountValue))/100;
					discountValue=CurrencyFormat.getCurrencyFormat("",Double.toString(dval),false);
				}
				double volprice = Double.parseDouble(price) - Double.parseDouble(discountValue);
				String volumePrice = CurrencyFormat.getCurrencyFormat("",Double.toString(volprice),false);
				volTicketData.setVolumePrice(volumePrice);
				
				
				
				String processfee=dbmanager.getValue(k,"fee","");
				processfee=CurrencyFormat.getCurrencyFormat("",processfee,false);
				volTicketData.setProcessingFee(processfee);
				String approvalStatus = dbmanager.getValue(k,"approval_status","");
				String showStatus = dbmanager.getValue(k,"show_status","");
				String endtimestatus = dbmanager.getValue(k,"endtimestatus","");
				String starttimestatus = dbmanager.getValue(k,"starttimestatus","");
				String salestatus = dbmanager.getValue(k,"sale_status","");
				volTicketData.setApprovalStatus(approvalStatus);
				volTicketData.setSimulate(showStatus);
				volTicketData.setShowhide(dbmanager.getValue(k,"showhide",""));
				/*if(approvalStatus.equals("P"))
					volTicketData.setStatus("Pending");
				else if(approvalStatus.equals("A") && showStatus.equals("Y")){
					if(endtimestatus.equals("before"))
						volTicketData.setStatus("Closed");
					else if(endtimestatus.equals("after") && starttimestatus.equals("before"))
						volTicketData.setStatus("Active");
					else 
						volTicketData.setStatus("Approved");
				}else if(approvalStatus.equals("A") && showStatus.equals("N")){
					if(endtimestatus.equals("before"))	
						volTicketData.setStatus("Closed");
					else if((posttrigtype==1 && triggerQty==currSoldQty) || (posttrigtype==2 && soldQty==upperLimit))
						volTicketData.setStatus("Closed");
					else if(posttrigtype==3 && currCycle==cycleLimit && triggerQty==currSoldQty)
						volTicketData.setStatus("Closed");
					else
						volTicketData.setStatus("Suspended");
				}*/
				if("Cancelled".equals(salestatus))
					volTicketData.setStatus("Cancelled");
				else if("Closed".equals(salestatus))
					volTicketData.setStatus("Closed");
				else if(approvalStatus.equals("P"))
					volTicketData.setStatus("Pending");
				else if(approvalStatus.equals("A") && showStatus.equals("Y") && starttimestatus.equals("after"))
					volTicketData.setStatus("Approved");
				else
					volTicketData.setStatus("Active");
					
				volumeTicketList.add(volTicketData);
			}
		}
		return volumeTicketList;
	}
	
	public static void saveConfirmationPageContent(String eid, String purpose, String content){
		DbUtil.executeUpdateQuery("delete from reg_flow_templates where eventid=CAST(? AS BIGINT) and purpose=?",new String [] {eid,purpose});
		DbUtil.executeUpdateQuery("insert into reg_flow_templates(eventid,purpose,content) values(CAST(? AS BIGINT),?,?)",new String [] {eid,purpose,content});
	}
	
	public static void saveConfirmationEmailContent(String eid,RegEmailSettingsData regEmailSettings, String purpose){
		
		String DELETE_TEMPLATE="delete from email_templates where  purpose=? and groupid=?";
		String INSERT_TEMPLATE="insert into email_templates(groupid,subjectformat,htmlformat,textformat, replytoemail,fromemail,purpose,unitid) values (?,?,?,?,?,?,?,?)";
		DbUtil.executeUpdateQuery(DELETE_TEMPLATE,new String[]{purpose,eid});
		DbUtil.executeUpdateQuery(INSERT_TEMPLATE,new String[]{eid,regEmailSettings.getSubject(),regEmailSettings.getTempalteContent(),null,"$mgrEmail", "$mgrEmail",purpose,"13579"});
		
	}
	
	public static void resetConfirmationEmailContent(String eid, String purpose){
		String DELETE_TEMPLATE="delete from email_templates where  purpose=? and groupid=?";
		DbUtil.executeUpdateQuery(DELETE_TEMPLATE,new String[]{purpose,eid});
	}
	
	public static void resetConfirmationPageContent(String eid, String purpose){
		DbUtil.executeUpdateQuery("delete from reg_flow_templates where eventid=CAST(? AS BIGINT) and purpose=?",new String [] {eid,purpose});
	}
	
	/*public static ArrayList<Entity> getWidgetCodes(String ticketid){
		ArrayList<Entity> widgetCodes=new ArrayList<Entity>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		String widgetcodequery="select widgetcode from vb_widgets where ticketid=?";
		
		statobj=dbmanager.executeSelectQuery(widgetcodequery,new String []{ticketid});
		int count=statobj.getCount();
		if(statobj.getStatus()&&count>0){
			for(int i=0;i<count;i++){
				String widgetcode=dbmanager.getValue(i,"widgetcode","");
				widgetCodes.add(new Entity(widgetcode,widgetcode));
			}
		}
		return widgetCodes;
	}*/
	
	public static void generateWidgetCode(String ticketId,String eid){
		try{
			
			String serveraddress = EventDB.serverAdderess();
			String visitingUrl = serveraddress+"/event?eid="+eid;
			String insertquery="insert into vb_widgets(widgetcode,ticketid,visiting_url) values(?,?,?)";
			DbUtil.executeUpdateQuery(insertquery,new String[]{"wc_"+ticketId, ticketId, visitingUrl});
		}catch(Exception e){
			System.out.println("Exception in generateWidgetCode Error: "+e.getMessage());
		}
	}
	
	public static String getWidgetCode(String ticketId){
		String widgetcode=DbUtil.getVal("select widgetcode from vb_widgets where ticketid=?", new String[]{ticketId});
		return widgetcode;
	}
	
	/*public static void generateWidgetCode(String widgetCode, String ticketId){
		
		String widgetcodecount=DbUtil.getVal("select count(*) from vb_widgets where widgetcode=? and ticketid=?", new String[]{widgetCode,ticketId});
		try{
			int count=Integer.parseInt(widgetcodecount);
			if(count>=1){
			   		
			}else{
				String insertquery="insert into vb_widgets(widgetcode,ticketid) values(?,?)";
				DbUtil.executeUpdateQuery(insertquery,new String[]{widgetCode, ticketId});
				System.out.println("data inserted successfully");
			}
		}catch(Exception e){
			System.out.println("Exception in generateWidgetCode Error: "+e.getMessage());
		}
	}*/
	
	public static void simulate(String showstatus, String ticketid){
		String updatequery="update groupdeal_tickets set show_status=? where ticketid=CAST(? AS INTEGER)";              
	    DbUtil.executeUpdateQuery(updatequery,new String[]{showstatus,ticketid});
	}
	
	public static ArrayList<HashMap<String, String>> getVolumeTicketsSummary(String eid){
		ArrayList<HashMap<String, String>> volumeTicketSummaryList=new ArrayList<HashMap<String,String>>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		
		String volTicketSummaryQuery="select ticketid,ticket_name,trigger_qty,approval_status," +
				" show_status,sold_qty,current_soldqty," +
				" current_cycle,post_trigger_type,upper_limit,cycles_limit," +
				" case when act_start_date+cast(cast(to_timestamp(COALESCE(act_starttime,'00'),'HH24:MI:SS') as text) as time )<=current_timestamp"+ 
				" then 'before' else 'after' end as starttimestatus,"+
				" case when end_date+cast(cast(to_timestamp(COALESCE(end_time,'00'),'HH24:MI:SS') as text) as time )<=current_timestamp"+ 
				" then 'before' else 'after' end as endtimestatus from groupdeal_tickets where eventid=? order by ticketid desc";
		statobj=dbmanager.executeSelectQuery(volTicketSummaryQuery,new String []{eid});
		int count=statobj.getCount();
		if(statobj.getStatus()&&count>0){
			for(int k=0;k<count;k++){
				HashMap<String, String> volTicketData=new HashMap<String, String>();
				
				int triggerQty = Integer.parseInt(dbmanager.getValue(k,"trigger_qty",""));
				int soldQty = Integer.parseInt(dbmanager.getValue(k,"sold_qty",""));
				int currSoldQty = Integer.parseInt(dbmanager.getValue(k,"current_soldqty",""));
				int currCycle = Integer.parseInt(dbmanager.getValue(k,"current_cycle",""));
				int upperLimit = Integer.parseInt(dbmanager.getValue(k,"upper_limit",""));
				int cycleLimit = Integer.parseInt(dbmanager.getValue(k,"cycles_limit",""));
				int posttrigtype = Integer.parseInt(dbmanager.getValue(k,"post_trigger_type",""));
				if(posttrigtype==2)	cycleLimit=0;
				else if(posttrigtype==3)upperLimit=0;
				else {upperLimit=0;cycleLimit=0;}
				
				volTicketData.put("ticketid",dbmanager.getValue(k,"ticketid",""));
				volTicketData.put("ticketnm",dbmanager.getValue(k,"ticket_name",""));
				volTicketData.put("triggerqty",dbmanager.getValue(k,"trigger_qty",""));
				volTicketData.put("soldqty",dbmanager.getValue(k,"sold_qty",""));
				volTicketData.put("currsoldqty",dbmanager.getValue(k,"current_soldqty",""));
				volTicketData.put("currcycle",dbmanager.getValue(k,"current_cycle",""));
				volTicketData.put("upperlimit",upperLimit+"");
				volTicketData.put("cycleslimit",cycleLimit+"");
				volTicketData.put("posttrigtype",dbmanager.getValue(k,"post_trigger_type",""));
				
				String approvalStatus = dbmanager.getValue(k,"approval_status","");
				String showStatus = dbmanager.getValue(k,"show_status","");
				
				String endtimestatus = dbmanager.getValue(k,"endtimestatus","");
				String starttimestatus = dbmanager.getValue(k,"starttimestatus","");
				if(approvalStatus.equals("P"))
					volTicketData.put("status","Pending");
				else if(approvalStatus.equals("A") && showStatus.equals("N")){
					if(endtimestatus.equals("before") && soldQty<triggerQty)
						volTicketData.put("status","Trigger Failed And Closed");
					else if(endtimestatus.equals("before") && soldQty>=triggerQty)
						volTicketData.put("status","Trigger Success And Closed");
					else
						volTicketData.put("status","Suspended");
				}
				else if(approvalStatus.equals("A") && showStatus.equals("Y")){
					if(endtimestatus.equals("before") && soldQty<triggerQty)
						volTicketData.put("status","Trigger Failed");
					else if(endtimestatus.equals("before") && soldQty>=triggerQty)
						volTicketData.put("status","Trigger Success");
					else if(endtimestatus.equals("after") && starttimestatus.equals("before") && soldQty>=triggerQty){
						if(cycleLimit != 0 && currCycle < cycleLimit)
							volTicketData.put("status","Trigger Success And Active");
						else if(upperLimit != 0 && soldQty < upperLimit)
							volTicketData.put("status","Trigger Success And Active");
						else
							volTicketData.put("status","Trigger Success");
						/*if(cycleLimit == 0 && upperLimit == 0 && soldQty == triggerQty)
							volTicketData.put("status","Trigger Success");
						else if(cycleLimit != 0 && currCycle == cycleLimit && currSoldQty == triggerQty)
							volTicketData.put("status","Trigger Success");
						else if(cycleLimit != 0 && currCycle < cycleLimit)
							volTicketData.put("status","Trigger Success And Active");
						else if(upperLimit != 0 && upperLimit == soldQty)
							volTicketData.put("status","Trigger Success");
						else if(upperLimit != 0 && soldQty < upperLimit)
							volTicketData.put("status","Trigger Success And Active");*/
					}else if(endtimestatus.equals("after") && starttimestatus.equals("before") && soldQty<triggerQty){
						volTicketData.put("status","Active");
					}else 
						volTicketData.put("status","Approved");
				}
				volumeTicketSummaryList.add(volTicketData);
			}
		}
		return volumeTicketSummaryList;
	}
	
	public static void defaultVolumeTicketStartDate(VolumeTicketData volTicketData){
		
		Calendar currentdate=Calendar.getInstance();
		currentdate.add(Calendar.HOUR,-4);
		String currentmonth=monthvals[currentdate.get(Calendar.MONTH)];
		int date = currentdate.get(Calendar.DATE);
		int hh=currentdate.get(Calendar.HOUR);
		int mm=currentdate.get(Calendar.MINUTE);
		String presentdate=(date<10)?("0"+date):(date+"");
		String dateformate=currentdate.get(Calendar.YEAR)+"-"+currentmonth+"-"+presentdate;
		System.out.println(dateformate);
		//String hrofday=getHours().get(currentdate.get(Calendar.HOUR)).toString();
		
		try{
			DateTimeBean dtbObj=new DateTimeBean();
			dtbObj.setYyPart(currentdate.get(Calendar.YEAR)+"");
			dtbObj.setDdPart(presentdate);
			dtbObj.setMonthPart(currentmonth);
			dtbObj.setHhPart(hh+"");
			dtbObj.setMmPart(mm+"");
			volTicketData.setStdateTimeBeanObj(dtbObj);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		}
	
	public static void setVolumeTicketEndDate(VolumeTicketData volTicketData){
		int styear=Integer.parseInt(volTicketData.getStdateTimeBeanObj().getYyPart());
		int stmonth=Integer.parseInt(volTicketData.getStdateTimeBeanObj().getMonthPart())-1;
		int sthourOfDay=Integer.parseInt(volTicketData.getStdateTimeBeanObj().getHhPart());
		String stampm=volTicketData.getStdateTimeBeanObj().getAmpm();
		int stdate=Integer.parseInt(volTicketData.getStdateTimeBeanObj().getDdPart());
		if(stampm.equals("PM") && sthourOfDay!=12){
			sthourOfDay=sthourOfDay+12;
		}
		if(stampm.equals("AM") && sthourOfDay==12){
			sthourOfDay=0;
		}
		int stminute=Integer.parseInt(volTicketData.getStdateTimeBeanObj().getMmPart());
		int activeDuration = Integer.parseInt(volTicketData.getActiveDuration().trim());
		Calendar givenCal=Calendar.getInstance();
		givenCal.set(styear, stmonth, stdate, sthourOfDay, stminute);
		givenCal.add(Calendar.HOUR,activeDuration);
		String month=monthvals[givenCal.get(Calendar.MONTH)];
		int date = givenCal.get(Calendar.DATE);
		int hh=givenCal.get(Calendar.HOUR);
		int mm=givenCal.get(Calendar.MINUTE);
		int ampm=givenCal.get(Calendar.AM_PM);
		
		String presentdate=(date<10)?("0"+date):(date+"");
		try{
			DateTimeBean dtbObj=new DateTimeBean();
			dtbObj.setYyPart(givenCal.get(Calendar.YEAR)+"");
			dtbObj.setDdPart(presentdate);
			dtbObj.setMonthPart(month);
			dtbObj.setHhPart(hh+"");
			dtbObj.setMmPart(mm+"");
			if(ampm==1)
				dtbObj.setAmpm("PM");
			else
				dtbObj.setAmpm("AM");
			
			volTicketData.setEnddateTimeBeanObj(dtbObj);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static boolean deleteVolumeTicket(String eid,String ticketId){
		String deleteQuery="delete from groupdeal_tickets where eventid=? and ticketid=CAST(? AS INTEGER)";
		DbUtil.executeUpdateQuery(deleteQuery,new String[]{eid, ticketId});
		return true;
	}
	public static String confirmCancelVolumeTicket(String eid,String ticketId){
		String transexist=DbUtil.getVal("select 'yes' from event_reg_gt_details_temp where eventid=? and ticketid=CAST(? AS BIGINT) "+
				"and status='Authorized'", new String[]{eid,ticketId});
		return transexist;
	}
	public static boolean cancelVolumeTicket(String eid,String ticketId){
		String cancelQuery="update groupdeal_tickets set show_status='N', sale_status='Cancelled' where eventid=? and ticketid=CAST(? AS INTEGER)";
		DbUtil.executeUpdateQuery(cancelQuery,new String[]{eid, ticketId});
		return true;
	}
	
	public static int getVolumeTicketCount(String eid){
		String count = DbUtil.getVal("select count(*) from groupdeal_tickets where eventid=?", new String[]{eid});
		if(count==null)count="0";
		return Integer.parseInt(count);
	}
	
	public static String getApprovalStatus(String eid){
		String status = DbUtil.getVal("select status from ticket_volume_approvalstatus where eventid=?", new String[]{eid});
		if(status==null)
			status = DbUtil.getVal("select status from ticket_volume_approvalstatus where userid=?", new String[]{EventDB.getUserId(eid)});
		if(status==null)
			status = DbUtil.getVal("select status from ticket_volume_approvalstatus where eventid='0' and userid='0'", null);
		if(status==null)
			status="P";
		return status;
	}
	
	public static String isVolumeTicketingEnabled(String eid,String name){
		String status = DbUtil.getVal("select value from config where config_id=(select mgr_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?", new String[]{eid, name});
		if(status==null) status="N";
		return status;
	}
	
	public static String showVolumeTicketing(String eid){
		String status="";
		String value = DbUtil.getVal("select value from config where config_id='0' and name='vbee.show.selected'", null);
		if("Y".equals(value)){ 
			value = DbUtil.getVal("select value from config where config_id='0' and name='vbee.show.selected.managers'", null);
			if(value != null){
				List<String> selectedMgrIds = Arrays.asList(value.split(","));
				if(selectedMgrIds.contains(DbUtil.getVal("select mgr_id from eventinfo where eventid=CAST(? AS BIGINT)", new String[]{eid})))
					status = "Y";
				else status="N";
			}else status = "N";
		}else status="Y";
		
		return status;
	}
	
	public static String getVolumeTicketServiceFee(String eventid){
		String ticketservicefee=DbUtil.getVal("select current_fee from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eventid});
		if(ticketservicefee==null || "".equals(ticketservicefee))ticketservicefee="1";
		return ticketservicefee;
	}

}
