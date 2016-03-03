package com.eventmanage.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.json.*;

import com.event.beans.DiscountData;
import com.event.beans.ticketing.GroupData;
import com.event.beans.ticketing.TicketData;
import com.event.beans.ticketing.VolumeTicketData;
import com.eventbee.beans.DateTimeBean;
import com.eventbee.general.DbUtil;

public class JsonBuilderHelper {

	private static final Logger log = Logger.getLogger(JsonBuilderHelper.class);

	public static JSONObject getDiscountsJson(List<DiscountData> discountsInfoList,String eventURL,String eid,int tktcount){
		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarrObj = new JSONArray();
		String currencySymbol=DbUtil.getVal("select currency_symbol from currency_symbols where currency_code=(select currency_code from event_currency where eventid=?)",new String[]{eid});
		  if(currencySymbol==null)
			  currencySymbol="$";
		try {
			for (DiscountData discountData : discountsInfoList) {
				JSONObject jsonDiscountData = new JSONObject();
				jsonDiscountData.put("discountName",discountData.getName());
				//jsonDiscountData.put("discountType",discountData.getDiscountType());				
				String discountcodes=discountData.getDiscountCode();				
				String[] result = discountcodes.split(",");
				if(result!=null){
					for (int i = 0; i < result.length; i++) {						
						jsonDiscountData.put("discountURL",eventURL+result[0]);
					}			
				}
				jsonDiscountData.put("discountCodes",discountcodes);
				jsonDiscountData.put("discountId",discountData.getId());
				String query="select discount from coupon_master,coupon_codes where coupon_master.couponid=? " +
						"and coupon_codes.couponid=coupon_master.couponid";
				String discountValue=DbUtil.getVal(query, new String[]{discountData.getId()});
				if("PERCENTAGE".equalsIgnoreCase(discountData.getDiscountType())) discountValue=discountValue+"%";
				if("ABSOLUTE".equalsIgnoreCase(discountData.getDiscountType())) discountValue=currencySymbol+discountValue;
				jsonDiscountData.put("discountValue",discountValue);
				jsonarrObj.put(jsonDiscountData);
			}
			jsonobj.put("DiscountDetails", jsonarrObj);
			jsonobj.put("tktcount", tktcount);
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
		return jsonobj;
	}

	public static JSONObject getTicketsJson(List<GroupData> groupDataList,HashMap tktStatusMap){
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonarrObj = new JSONArray();
		int grpIndex=0;
		int grpTotal = groupDataList.size();

		try {
			for (GroupData groupData : groupDataList) {

				String isFirst =(grpIndex==0)?"Y": "N";
				String isLast =(grpIndex == grpTotal-1)?"Y": "N";
				JSONObject jsonObj_grpDetails = new JSONObject();
				String gid = groupData.getGroupId();
				jsonObj_grpDetails.put("gid", gid);
				jsonObj_grpDetails.put("isFirst", isFirst);
				jsonObj_grpDetails.put("isLast", isLast);
				jsonObj_grpDetails.put("grpPos", groupData.getPosition());
				if(groupData.getGroupName().equals("")){
					buildTicketJson(groupData.getGroupTickets(),"looseticket",gid,jsonObj_grpDetails,jsonarrObj);
				}else{
					jsonObj_grpDetails.put("type", "group");
					jsonObj_grpDetails.put("name", groupData.getGroupName());
					jsonObj_grpDetails.put("ttype", "Ticket Group");
					jsonarrObj.put(jsonObj_grpDetails);
					JSONObject jsonObj_ticketdetails = new JSONObject();
					buildTicketJson(groupData.getGroupTickets(),"grpticket",gid,jsonObj_ticketdetails,jsonarrObj);

				}
				grpIndex++;
			}
			JSONObject tktStatusObj=
			
			jsonObj.put("TicketDetails", jsonarrObj);
			jsonObj.put("TicketStatus",tktStatusMap);
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
		return jsonObj;
	}

	private static void buildTicketJson(List<TicketData> ticketsList,String type,String gid,JSONObject jsonObj,JSONArray jsonarrObj){
		try {
			int ticketIndex = 0;
			int count = ticketsList.size();
			for (TicketData ticketData : ticketsList) {
				JSONObject jsonObj_tickets ;
				if("looseticket".equals(type)){
					jsonObj_tickets=jsonObj;

				}else{
					String isFirst =(ticketIndex==0)?"Y": "N";
					String isLast =(ticketIndex == count-1)?"Y": "N";
					jsonObj_tickets = new JSONObject();
					jsonObj_tickets.put("isFirst", isFirst);
					jsonObj_tickets.put("isLast", isLast);
					
				}
				jsonObj_tickets.put("type", type);
				jsonObj_tickets.put("gid", gid);
				jsonObj_tickets.put("name", ticketData.getTicketName());
				jsonObj_tickets.put("tid", ticketData.getTicketId());
				jsonObj_tickets.put("position", ticketData.getPosition());
				jsonObj_tickets.put("showyn",ticketData.getShowyn());
				jsonObj_tickets.put("maxticket",ticketData.getTotalTicketCount());
				jsonObj_tickets.put("min_qty", ticketData.getMinQty());
				jsonObj_tickets.put("max_qty", ticketData.getMaxQty());

				/*if it donation ticket*/
				
				if(ticketData.getIsDonation().equals("Yes")){
					jsonObj_tickets.put("ttype", "Donation");
					jsonObj_tickets.put("isdonation", "Y");
					jsonObj_tickets.put("soldqty", ticketData.getSoldqty());
				}else{
					if(ticketData.getIsAttendee().equals("Yes")){
						jsonObj_tickets.put("ttype", "Attendee");
					}else{
						jsonObj_tickets.put("ttype", "Non-Attendee");
					}
					
					
					jsonObj_tickets.put("isdonation", "N");
					jsonObj_tickets.put("price", ticketData.getTicketPrice());
					jsonObj_tickets.put("fee", ticketData.getProcessingFee());
					jsonObj_tickets.put("soldqty", ticketData.getSoldqty());
				}
				
				DateTimeBean st=ticketData.getStdateTimeBeanObj();
				String stdate=st.getMonthPart()+"/"+st.getDdPart()+"/"+st.getYyPart();
				stdate=st.getYyPart()+"/"+st.getMonthPart()+"/"+st.getDdPart();
				DateTimeBean edt=ticketData.getEnddateTimeBeanObj();
				String edate=edt.getMonthPart()+"/"+edt.getDdPart()+"/"+edt.getYyPart();
				edate=edt.getYyPart()+"/"+edt.getMonthPart()+"/"+edt.getDdPart();
				
				jsonObj_tickets.put("startdate",stdate);
				jsonObj_tickets.put("enddate", edate);
				jsonObj_tickets.put("starttime",st.getHhPart()+":"+st.getMmPart()+" "+st.getAmpm());
				jsonObj_tickets.put("endtime",edt.getHhPart()+":"+edt.getMmPart()+" "+edt.getAmpm());
				
				ticketIndex++;
				jsonarrObj.put(jsonObj_tickets);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	public static JSONObject getTicketsStatusJson(Vector ticStatusVec){
			JSONObject jsonObj = new JSONObject();
			JSONArray jsonarrObj = new JSONArray();
			try {
				for (Object object : ticStatusVec) {
					JSONObject jsonObj_ticstatus = new JSONObject();
					HashMap hMap = (HashMap) object;
					//System.out.println(hMap);
					jsonObj_ticstatus.put("status", hMap.get("status"));
					String ticketName = hMap.get("ticket_name").toString();
					if(hMap.get("groupname")!=null && !"".equals(hMap.get("groupname"))){
						ticketName += " ("+hMap.get("groupname").toString()+")";
					}
					jsonObj_ticstatus.put("ticket_name", ticketName);
					jsonObj_ticstatus.put("sold_qty", hMap.get("sold_qty"));
					jsonObj_ticstatus.put("total_qty", hMap.get("total_qty")!=null ? hMap.get("total_qty") : "NA");
					if("135790".equals(hMap.get("total_qty"))){
						jsonObj_ticstatus.put("total_qty","NA");
					}
					jsonObj_ticstatus.put("onlinesales", hMap.get("onlinesales"));
					jsonObj_ticstatus.put("offlinesales", hMap.get("offlinesales"));
					jsonObj_ticstatus.put("alldates", hMap.get("alldates"));
					jsonarrObj.put(jsonObj_ticstatus);
				}
				jsonObj.put("ticketssummary", jsonarrObj);
			} catch (Exception ex) {
				// TODO: handle exception
				System.out.println(ex.getMessage());
				log.error("Exception:"+ex);
			}
			return jsonObj;
	}
	public static JSONObject getNoticesListJson(ArrayList<HashMap<String,String>> notices){
		JSONObject allListsObject=new JSONObject();
		JSONArray listobj=new JSONArray();
		try{
			for(int i=0;i<notices.size();i++){
				JSONObject cObject=new JSONObject();
				HashMap<String,String> noticeMapObj= notices.get(i);
				//"id":"15266","noticetype":"Info","notice":"Test Notice","action":"Manage",
				//"postedat":"02/02/2010"
				cObject.put("notice",noticeMapObj.get("notice"));
				cObject.put("noticetype",noticeMapObj.get("noticetype"));
				cObject.put("id",noticeMapObj.get("noticeid"));
				cObject.put("action", "Manage");
				cObject.put("postedat",noticeMapObj.get("postedat"));
				listobj.put(cObject);
			}
			allListsObject.put("citems",listobj);
		}
		catch(Exception e){
			System.out.println("There is an error in processing the JSON Data for mail lists");
		}
	return allListsObject;		
	}
	
	public static JSONObject getVolumeTicketsJson(List<VolumeTicketData> volumeTicketList){
		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarrObj = new JSONArray();
		try {
			int volTicketIndex = 0;
			int count = volumeTicketList.size();
			for (VolumeTicketData volTicketData : volumeTicketList) {
				JSONObject jsonObj_tickets = new JSONObject();
				
				String isFirst =(volTicketIndex==0)?"Y": "N";
				String isLast =(volTicketIndex == count-1)?"Y": "N";
				
				jsonObj_tickets.put("isFirst", isFirst);
				jsonObj_tickets.put("isLast", isLast);
				
				jsonObj_tickets.put("name", volTicketData.getTicketName());
				jsonObj_tickets.put("tid", volTicketData.getTicketId());
				jsonObj_tickets.put("price", volTicketData.getTicketPrice());
				jsonObj_tickets.put("volprice", volTicketData.getVolumePrice());
				jsonObj_tickets.put("trigQty", volTicketData.getTriggerQty());
				jsonObj_tickets.put("fee", volTicketData.getProcessingFee());
				jsonObj_tickets.put("appstatus", volTicketData.getApprovalStatus());
				jsonObj_tickets.put("status", volTicketData.getStatus());
				jsonObj_tickets.put("simulate", volTicketData.getSimulate());
				jsonObj_tickets.put("showhide", volTicketData.getShowhide());
				volTicketIndex++;
				jsonarrObj.put(jsonObj_tickets);
			}
			jsonobj.put("VolumeTicketDetails", jsonarrObj);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return jsonobj;
	}
}
