package com.eventmanage.helpers;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.event.beans.ticketing.GroupData;
import com.event.beans.ticketing.TicketData;
import com.eventbee.beans.DateTimeBean;

public class NTSJSONHelper {
	
	private static final Logger log = Logger.getLogger(NTSJSONHelper.class);
	
	public static JSONObject getNTSTicketsJson(List<GroupData> groupDataList){
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
					buildNTSTicketJson(groupData.getGroupTickets(),"looseticket",gid,jsonObj_grpDetails,jsonarrObj);
				}else{
					jsonObj_grpDetails.put("type", "group");
					jsonObj_grpDetails.put("name", groupData.getGroupName());
					jsonObj_grpDetails.put("ttype", "Ticket Group");
					jsonarrObj.put(jsonObj_grpDetails);
					JSONObject jsonObj_ticketdetails = new JSONObject();
					buildNTSTicketJson(groupData.getGroupTickets(),"grpticket",gid,jsonObj_ticketdetails,jsonarrObj);
				}
				grpIndex++;
			}
			jsonObj.put("TicketDetails", jsonarrObj);
		} catch (Exception ex) {
			// TODO: handle exception
			log.error("Exception: "+ex);
		}
		return jsonObj;
	}
	
	private static void buildNTSTicketJson(List<TicketData> ticketsList,String type,String gid,JSONObject jsonObj,JSONArray jsonarrObj){
		try {
			int ticketIndex = 0;
			int count = ticketsList.size();
			for (TicketData ticketData : ticketsList) {
				//if(ticketData.getIsDonation().equals("No")){
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
					
					if(ticketData.getIsAttendee().equals("Yes")){
						jsonObj_tickets.put("ttype", "Attendee");
					}else{
						jsonObj_tickets.put("ttype", "Non-Attendee");
					}
					
					jsonObj_tickets.put("price", ticketData.getTicketPrice());
					jsonObj_tickets.put("fee", ticketData.getProcessingFee());
					jsonObj_tickets.put("commission", ticketData.getNetWorkCommission());
					jsonObj_tickets.put("credits", ticketData.getCredits());
					DateTimeBean st=ticketData.getStdateTimeBeanObj();
					String stdate=st.getMonthPart()+"/"+st.getDdPart()+"/"+st.getYyPart();
					DateTimeBean edt=ticketData.getEnddateTimeBeanObj();
					String edate=edt.getMonthPart()+"/"+edt.getDdPart()+"/"+edt.getYyPart();
					jsonObj_tickets.put("startdate",stdate);
					jsonObj_tickets.put("enddate", edate);
					ticketIndex++;
					jsonarrObj.put(jsonObj_tickets);
				//}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
}
