package com.eventmanage.groupticketing.helpers;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.eventmanage.groupticketing.beans.GroupDealTicketsData;
import com.eventbee.general.DbUtil;


public class GroupTicketJsonBuilderHelper {
	
		private static final Logger log = Logger.getLogger(GroupTicketJsonBuilderHelper.class);

		public static JSONObject getGroupTicketJson(List<GroupDealTicketsData> groupTicketsList,String eid){
			JSONObject jsonobj = new JSONObject();
			JSONArray jsonarrObj = new JSONArray();
			String currencySymbol=DbUtil.getVal("select currency_symbol from currency_symbols where currency_code=(select currency_code from event_currency where eventid=?)",new String[]{eid});
			  if(currencySymbol==null)
				  currencySymbol="$";
			String percentageSymbol="%";
			try {
				for (GroupDealTicketsData groupdealticketsdata : groupTicketsList) {
					JSONObject jsonGroupTicktesData = new JSONObject();
					jsonGroupTicktesData.put("price",groupdealticketsdata.getPrice());
					jsonGroupTicktesData.put("discountvalue",groupdealticketsdata.getDiscountvalue());
					jsonGroupTicktesData.put("ticketName",groupdealticketsdata.getTicketName());
					jsonGroupTicktesData.put("triggerqty",groupdealticketsdata.getTriggerqty());
					jsonGroupTicktesData.put("groupticketid",groupdealticketsdata.getGroupticketid());
					jsonGroupTicktesData.put("currentcycle",groupdealticketsdata.getCurrentcycle());
					jsonGroupTicktesData.put("approvalstatus",groupdealticketsdata.getApproval_status());
					jsonGroupTicktesData.put("soldqty",groupdealticketsdata.getSoldqty());
			        jsonarrObj.put(jsonGroupTicktesData);
				}
				jsonobj.put("GroupTicketDetails", jsonarrObj);
			} catch (Exception ex) {
				// TODO: handle exception
				log.error("Exception: "+ex);
			}
			return jsonobj;
		}

	

}
