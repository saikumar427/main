package com.eventmanage.groupticketing.dbhelpers;

import java.util.ArrayList;

import com.event.dbhelpers.CustomAttributesDB;
import com.eventbee.beans.DateTimeBean;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateConverter;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.CurrencyFormat;
import com.eventmanage.groupticketing.beans.GroupDealTicketsData;



public class GroupDealTicketsDB {

	public static final String GROUPTICKET_INSERT = "insert into groupdeal_tickets(eventid,ticketid,name,description"
			+ ",price,discount_type,discount_value,trigger_qty,min_qty,max_qty,start_date_req,start_time_req,"
			+ "startampm,created_at,post_trigger_type,upper_limit,cycles_limit,position,trigger_fail_action,fail_discount)"
			+ " values(to_number(?,'99999999999'),to_number(?,'99999999'),?,?,to_number(?,'99999999D99'),?,to_number(?,'99999999D99')"
			+ ",to_number(?,'99999999'),to_number(?,'99999999'),to_number(?,'99999999'),to_date(?,'YYYY-MM-DD'),?,?,now(),to_number(?,'99999999'),"
			+ "to_number(?,'999999999'),to_number(?,'99999999'),to_number(?,'99999999'),to_number(?,'99999999'),to_number(?,'99999999D99'))";

	
	public static final String GROUPTICKET_SELECT="select name,description,price,discount_value,trigger_qty,min_qty,max_qty,start_date_req,to_char(start_date_req,'yyyy') as start_yy," +
	"to_char(start_date_req,'mm') as start_mm,to_char(start_date_req,'dd') as start_dd,start_time_req,startampm,approval_status," +
	"to_char(act_start_date,'yyyy') as act_start_yy,to_char(act_start_date,'mm') as act_start_mm,to_char(act_start_date,'dd') as act_start_dd," +
	"act_starttime,act_startampm,to_char(end_date,'yyyy') as end_yy,to_char(end_date,'mm') as end_mm,to_char(end_date,'dd') as end_dd,end_time,endampm," +
	"active_duration,show_status,sold_qty,current_cycle,post_trigger_type,upper_limit,cycles_limit,position,trigger_fail_action," +
	"start_before_days,start_before_hours,start_before_minutes,start_type,fail_discount from groupdeal_tickets" +
	" where eventid=to_number(?,'9999999999') and ticketid=to_number(?,'99999999999')";

  public static final String GROUPTICKET_UPDATE="update groupdeal_tickets set name=?,description=?,price=to_number(?,'99999999D99')," +
	"discount_value=to_number(?,'99999999D99'),trigger_qty=to_number(?,'99999999')," +
	"min_qty=to_number(?,'99999999'),max_qty=to_number(?,'99999999'),start_date_req=to_date(?,'YYYY-MM-DD')," +
	"start_time_req=?,startampm=?,post_trigger_type=to_number(?,'99999999')," +
	"upper_limit=to_number(?,'99999999'),cycles_limit=to_number(?,'99999999'),trigger_fail_action=to_number(?,'99999999')," +
	"fail_discount=to_number(?,'99999999D99') where eventid=to_number(?,'99999999999') and ticketid=to_number(?,'99999999999')";


  public static void saveGroupTicket(GroupDealTicketsData grptcktData,String eid,String userTimeZone){
	  String toTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
	  DateConverter dc=new DateConverter(userTimeZone,toTimeZone);
	  	DateTimeBean dtb=grptcktData.getStdateTimeBeanObj();
	  	boolean statusdc=dc.convertDateTime(dtb);
	  String startDate=dc.getDatePart();
	  String startTime=dc.getTimePart();
	  String upperlimit="0";
	  String cyclelimit="0";
	  String fail_discount="0";
	  if("2".equals(grptcktData.getPost_trigger_type())){
	  upperlimit=grptcktData.getUpperlimit().trim();
	  if(upperlimit==null || "".equals(upperlimit))
	  	upperlimit="0";
	  }
	  if("3".equals(grptcktData.getPost_trigger_type())){
	  	cyclelimit=grptcktData.getCycleslimit().trim();
	  	if(cyclelimit==null || "".equals(cyclelimit))
	  		cyclelimit="0";
	  }
	  if("1".equals(grptcktData.getTrigger_fail_action())){
	  	fail_discount=grptcktData.getTriggerfailDicount().trim();
	  	if(fail_discount==null || "".equals(fail_discount))
	  		fail_discount="0";
	  }
	  if("".equals(grptcktData.getGroupticketid())){
	  	String position=DbUtil.getVal("select max(position) from groupdeal_tickets where eventid=to_number(?,'999999999')",new String[]{eid});
	  	String tcktid=DbUtil.getVal("select nextval('seq_priceid')",null);
	  	if(position==null || "".equals(position))
	  		position="0";
	  	int tcktpostion=Integer.parseInt(position);
	  	tcktpostion=tcktpostion+1;

	  String[] inparams={
	  		eid,
	  		tcktid,
	  		grptcktData.getTicketName(),
	  		grptcktData.getDescription(),
	  		grptcktData.getPrice(),
	  		grptcktData.getDiscounttype(),
	  		grptcktData.getDiscountvalue(),
	  		grptcktData.getTriggerqty(),
	  		grptcktData.getMinqty(),
	  		grptcktData.getMaxqty(),
	  		startDate,
	  		startTime,
	  		grptcktData.getStdateTimeBeanObj().getAmpm(),
	  		grptcktData.getPost_trigger_type(),
	  		upperlimit,
	  		cyclelimit,
	  		""+tcktpostion,
	  		grptcktData.getTrigger_fail_action(),
	  		fail_discount
	  };
	  DbUtil.executeUpdateQuery(GROUPTICKET_INSERT, inparams);
	  CustomAttributesDB.saveTicketBaseProfileSettings(eid,"fname",tcktid,"Y");
	  CustomAttributesDB.saveTicketBaseProfileSettings(eid,"lname",tcktid,"Y");
	  }
	  else{
	  	String[] inparams={
	  			grptcktData.getTicketName(),
	  			grptcktData.getDescription(),
	  			grptcktData.getPrice(),
	  			grptcktData.getDiscountvalue(),
	  			grptcktData.getTriggerqty(),
	  			grptcktData.getMinqty(),
	  			grptcktData.getMaxqty(),
	  			startDate,
	  			startTime,
	  			grptcktData.getStdateTimeBeanObj().getAmpm(),
	  			grptcktData.getPost_trigger_type(),
	  			upperlimit,
	  			cyclelimit,
	  			grptcktData.getTrigger_fail_action(),
	  			fail_discount,
	  			eid,
	  			grptcktData.getGroupticketid()
	  	};
	  DbUtil.executeUpdateQuery(GROUPTICKET_UPDATE, inparams);
	  }
	  }
	  	

	
	
	
	public static ArrayList<GroupDealTicketsData> getGroupTicketsList(
			String eventid, String userTimeZone) {
		System.out.println("we r in get groupticketslist");

		ArrayList<GroupDealTicketsData> groupTicketsList = new ArrayList<GroupDealTicketsData>();
		DBManager db = new DBManager();
		String query = "select name,ticketid,price,discount_value,trigger_qty,current_cycle,approval_status,sold_qty from "
				+ " groupdeal_tickets where eventid=to_number(?,'9999999999') ";
		StatusObj statobj = db.executeSelectQuery(query,
				new String[] { eventid });
		System.out.println("Status of getgroupticketsList"
				+ statobj.getStatus());
		int count = statobj.getCount();
		//System.out.println(count);
		if (statobj.getStatus() && count > 0) {
			for (int k = 0; k < count; k++) {
				GroupDealTicketsData obj = new GroupDealTicketsData();
				obj.setTicketName(db.getValue(k, "name", ""));
				obj.setGroupticketid(db.getValue(k, "ticketid", ""));
				String org_price=db.getValue(k, "price", "");
				String price=CurrencyFormat.getCurrencyFormat("", org_price, false);
				obj.setPrice(price);
				String org_discount=db.getValue(k,"discount_value","");
				String discount=CurrencyFormat.getCurrencyFormat("", org_discount, false);
				obj.setDiscountvalue(discount);
				obj.setTriggerqty(db.getValue(k, "trigger_qty", ""));
				obj.setCurrentcycle(db.getValue(k, "current_cycle", ""));
				obj.setApproval_status(db.getValue(k, "approval_status", ""));
				obj.setApproval_status(db.getValue(k, "sold_qty", ""));
				groupTicketsList.add(obj);
			}
		}
		return groupTicketsList;
	}
      
	
	
	public static GroupDealTicketsData getGroupTicketData(String eid,String ticketId,String userTimeZone){
		GroupDealTicketsData grptcktData=new GroupDealTicketsData();
		DBManager db=new DBManager();
		//System.out.println("eid:"+eid+" "+"tid:"+ticketId);
		StatusObj statobj=db.executeSelectQuery(GROUPTICKET_SELECT, new String[]{eid,ticketId});
		grptcktData.setTicketName(db.getValue(0, "name", ""));
		grptcktData.setDescription(db.getValue(0, "description", ""));
			String org_price=db.getValue(0, "price", "");
			String price=CurrencyFormat.getCurrencyFormat("", org_price, false);
		grptcktData.setPrice(price);
			String org_discount=db.getValue(0,"discount_value","");
			String discount=CurrencyFormat.getCurrencyFormat("", org_discount, false);
		grptcktData.setDiscountvalue(discount);
		grptcktData.setTriggerqty(db.getValue(0, "trigger_qty", ""));
		grptcktData.setMinqty(db.getValue(0, "min_qty",""));
		grptcktData.setMaxqty(db.getValue(0, "max_qty",""));
		grptcktData.setApproval_status(db.getValue(0, "approval_status", ""));
		grptcktData.setActiveduration(db.getValue(0,"active_duration", ""));
		grptcktData.setShow_status(db.getValue(0, "show_status", ""));
			String soldqty=db.getValue(0, "sold_qty", "");
			if("".equals(soldqty) || soldqty==null)
				soldqty="0";
		grptcktData.setSoldqty(soldqty);
		grptcktData.setCurrentcycle(db.getValue(0, "current_cycle", ""));
		grptcktData.setPost_trigger_type(db.getValue(0, "post_trigger_type", ""));
		grptcktData.setUpperlimit(db.getValue(0,"upper_limit", ""));
		grptcktData.setCycleslimit(db.getValue(0, "cycles_limit", ""));
		grptcktData.setPosition(db.getValue(0, "position",""));
		grptcktData.setTrigger_fail_action(db.getValue(0, "trigger_fail_action", ""));
		grptcktData.setStart_type(db.getValue(0, "start_type", ""));
		grptcktData.setTriggerfailDicount(db.getValue(0,"fail_discount", ""));
		grptcktData.setGroupticketid(ticketId);
		String start_date=db.getValue(0,"start_date_req", "");
		String start_time=db.getValue(0, "start_time_req", "");
		String fromTimeZone=EbeeConstantsF.get("Server.time.zone","SystemV/EST5");
		DateConverter dc=new DateConverter(fromTimeZone,userTimeZone);
		boolean statusdc=dc.convertDateTime(start_date,start_time,userTimeZone);
		DateTimeBean stdtb=dc.getDateTimeBeanObj();
		grptcktData.setStdateTimeBeanObj(stdtb);
		return grptcktData;
	}
    
	public static void deleteGroupTicketsData(String eventid,
			String groupticketid) {
		// System.out.println("we r in delete groupticketsdata");
		StatusObj statobj = null;
		String query = "delete from  groupdeal_tickets where eventid=to_number(?,'999999999') and ticketid=to_number(?,'999999999')";
		statobj = DbUtil.executeUpdateQuery(query, new String[] { eventid,
				groupticketid });
		// System.out.println("status in delete groupticketsdata"+statobj.
		// getStatus());

	}

}
