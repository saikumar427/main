package com.eventmanage.seating.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.event.helpers.I18n;
import com.eventmanage.seating.beans.Seat;
import com.eventmanage.seating.beans.SeatColor;
import com.eventmanage.seating.beans.Section;
import com.eventmanage.seating.dbhelpers.SeatingDB;

public class SeatingJsonHelper {
	public static JSONObject getSectionSeatingDetails(String sectionid,String eid,String eventdate,String isrecurring){
		System.out.println("eventdate::"+eventdate);
		System.out.print("isrecurring:::"+isrecurring);
		JSONObject jsonObj=new JSONObject();
		Section section=SeatingDB.getSection(sectionid);
		try{
			jsonObj.put("sectionid",section.getSectionid());
			jsonObj.put("sectionname",section.getSectionname());
			int rows=Integer.parseInt(section.getNo_of_rows());
			int cols=Integer.parseInt(section.getNo_of_cols());
			jsonObj.put("noofrows", rows);
			jsonObj.put("noofcols", cols);
			jsonObj.put("background_image", section.getBackground_image());
			jsonObj.put("seat_image_width", section.getSeat_image_width());
			jsonObj.put("seat_image_height", section.getSeat_image_height());
			HashMap<String,Object> h1=new HashMap<String,Object>();
			h1.put("layout_css",section.getLayout_css());
			jsonObj.put("layout_css",h1);	
			
			JSONObject seatingJson=new JSONObject();
			ArrayList<Seat> seats=section.getSeats();	
			List blockedseats;
			HashMap<String, String> allotedseats=SeatingDB.getAllotedSeats(eid, sectionid);
			HashMap<String, String> seatgroups=SeatingDB.getAllotedSeatColors(eid);
			List soldoutseats=SeatingDB.getSoldOutSeats(eid, sectionid,eventdate);
			
			if("true".equals(isrecurring)){
		    blockedseats=SeatingDB.getBlockedSeats(eid,sectionid,eventdate);
		    if(eventdate!=null && !"".equals(eventdate))
		    blockedseats.addAll(SeatingDB.getBLockedSeatsEventData(eid,eventdate));
			}else 
				blockedseats=SeatingDB.getBLockedSeatsData(eid,sectionid,eventdate);
			
			for(int i=0;i<seats.size();i++){
				Seat seat=seats.get(i);
					JSONObject temp=new JSONObject();
					temp.put("row_id", seat.getRowid());
					temp.put("col_id", seat.getColid());
					temp.put("seat_ind", seat.getSeatIndex());
					temp.put("seatcode", seat.getSeatCode());
					/*if("N".equals(seat.getIsSeat())){
						temp.put("type", "noseat");
					}*/    
					String seatindex=seat.getSeatIndex();
					String groupid=allotedseats.get(seatindex);
					if(groupid!=null){
						String color=seatgroups.get(groupid);
						if(soldoutseats.contains(seatindex) && "false".equals(isrecurring))
							color="lightgray_sold";
						else if(soldoutseats.contains(seatindex) && "true".equals(isrecurring) && "".equals(eventdate))
						{}
						else if(soldoutseats.contains(seatindex) && "true".equals(isrecurring) && !("".equals(eventdate)))
						{
							color="lightgray_sold";
						}
						if(blockedseats.contains(seatindex)){
							color="lightgray_exclaimation";
							temp.put("blocked", "yes");
						}
							temp.put("type", color);
					
					}
					seatingJson.put("s_"+seat.getRowid()+"_"+seat.getColid(), temp);
			}
			/*for(int i=1;i<=rows;i++){                       
				for(int j=1;j<=cols;j++){
					String key="s_"+i+"_"+j;
					try{
						seatingJson.get(key);
						}catch(Exception je){
						JSONObject temp=new JSONObject();
						temp.put("row_id", i);
						temp.put("col_id", j);
						temp.put("seat_ind", 0);
						temp.put("seatcode", "");
						temp.put("type", "noseat");
						seatingJson.put("s_"+i+"_"+j, temp);
					}
					}
				}*/
			
			jsonObj.put("completeseats", seatingJson);
		}
		catch(Exception e){
			System.out.println(e.toString());
		}		return jsonObj;
	}
	public static JSONObject getSeatColorsJson(String eid,String type){
		ArrayList<SeatColor> seatcolors;
		if(type.equals("RSVP"))
			seatcolors=SeatingDB.getRSVPSeatColors(eid);
		else
			seatcolors=SeatingDB.getSeatColors(eid);
		System.out.println(seatcolors.size());
		JSONArray seattypeArr=new JSONArray();
		JSONObject jsonobj=new JSONObject();
		JSONObject unavail=new JSONObject();
		JSONObject avail=new JSONObject();
		JSONObject soldout=new JSONObject();
		try{
			unavail.put("color","lightgray_blank");
			avail.put("color", "lightgray_question");
			soldout.put("color","lightgray_sold");
			seattypeArr.put(avail);
			seattypeArr.put(unavail);
			seattypeArr.put(soldout);
			for(int i=0;i<seatcolors.size();i++){
			JSONObject seattype=new JSONObject();
			seattype.put("color", seatcolors.get(i).getColor());
			ArrayList<String> tickets=seatcolors.get(i).getTickets();
			if(tickets!=null){     
			JSONArray tcktArr=new JSONArray();
			for(int j=0;j<tickets.size();j++){
				if(!("".equals(tickets.get(j))))
					tcktArr.put(tickets.get(j));
			}
			seattype.put("tickets",tcktArr);
			}
			seattypeArr.put(seattype);
			}
			jsonobj.put("seattypes", seattypeArr);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return jsonobj;
	}
	
	public static String getLockedJSONData(ArrayList lockedList){
		
			JSONObject allticketURLsObject=new JSONObject();
			JSONArray trackURLobj=new JSONArray();
			try{
				for(int i=0;i<lockedList.size();i++){
					JSONObject cObject=new JSONObject();
					HashMap hmap= (HashMap)lockedList.get(i);
					String currentaction=(String)hmap.get("curraction");
					if(currentaction==null)currentaction="";
					if("profile page".equalsIgnoreCase(currentaction))
						currentaction=I18n.getString("seating.current.status.profile.page");
					else if("tickets page".equalsIgnoreCase(currentaction))
						currentaction=I18n.getString("seating.current.status.tickets.page");
					else if("payment section".equalsIgnoreCase(currentaction) || "eventbee".equalsIgnoreCase(currentaction) || "paypal".equalsIgnoreCase(currentaction) || "other".equalsIgnoreCase(currentaction))
						currentaction=I18n.getString("seating.current.status.payment.page");
					else if("confirmation page".equalsIgnoreCase(currentaction))
						currentaction=I18n.getString("seating.current.status.confirmation.page");
					cObject.put("tid",hmap.get("tid"));
					cObject.put("status",currentaction);
				    cObject.put("date",hmap.get("tdate"));
					cObject.put("scodes",fillTickets((ArrayList)hmap.get("seatcodes")));				
					cObject.put("action", "Manage");
					cObject.put("recurdate",hmap.get("recurdate"));
					trackURLobj.put(cObject);
				}
				allticketURLsObject.put("citems",trackURLobj);
			}
			catch(Exception e){
				System.out.println("There is an error in processing the JSON Data for track urls");
			}
		return allticketURLsObject.toString();
		}
	
		private static JSONArray fillTickets(ArrayList seatList){
			JSONArray trackURLTickets=new JSONArray();
			try{
			for(int i=0;i<seatList.size();i++){
				JSONObject cObject=new JSONObject();
				cObject.put("seatcodes",seatList.get(i));
				trackURLTickets.put(cObject);				
			}
			}catch(Exception ex){}
			return trackURLTickets;
		}
		
		
	
	
}
