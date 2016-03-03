package com.eventmanage.seating.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

public class SeatingAddManualHelper {
	//static SeatingDBHelper seatingdbhelper=new SeatingDBHelper();
	public static JSONObject getSectionSeatingDetails(HashMap hmap){
		JSONObject jsonObj=new JSONObject();
		try{
			//String venueid=(String)hmap.get("venueid");
			String sectionid=(String)hmap.get("sectionid");
			//String eid=(String)hmap.get("eid");
			//String eventdate=(String)hmap.get("eventdate");
			
			//HashMap section=seatingdbhelper.getSection(sectionid);
			HashMap section=(HashMap)hmap.get("getsectiondetails");
			jsonObj.put("headers_"+sectionid,hmap.get("sectionheader"));
			jsonObj.put("sectionid",section.get("Sectionid"));
			jsonObj.put("sectionname",section.get("Sectionname"));
			int rows=Integer.parseInt(section.get("No_of_rows").toString());
			int cols=Integer.parseInt(section.get("No_of_cols").toString());
			jsonObj.put("noofrows", rows);
			jsonObj.put("noofcols", cols);
			jsonObj.put("background_image", section.get("background_image"));
			jsonObj.put("seat_image_width", section.get("seat_image_width"));
			jsonObj.put("seat_image_height", section.get("seat_image_height"));
			HashMap<String,Object> h1=new HashMap<String,Object>();
			h1.put("layout_css",section.get("layout_css"));
			jsonObj.put("layout_css",h1);	
			
			
			
			JSONObject seatingJson=new JSONObject();
			ArrayList seats=(ArrayList)section.get("Seats");
			//HashMap<String, String> allotedseats=seatingdbhelper.getAllotedSeats(eid, sectionid);
			HashMap<String, String> allotedseats=(HashMap)hmap.get("allotedseats");
			//HashMap<String, String> seatgroups=seatingdbhelper.getAllotedSeatColors(eid, sectionid);
			HashMap<String, String> seatgroups=(HashMap)hmap.get("getAllotedSeatColors");
			HashMap allticket_id_name=(HashMap) hmap.get("allticket_id_name");
			HashMap hm=(HashMap) hmap.get("tktid_seat_grpid");		
			List soldoutseats= (List) hmap.get("getSoldOutSeats");
			List onholdseats=	(List) hmap.get("getOnHoldSeats");
			//List soldoutseats=seatingdbhelper.getSoldOutSeats(eid,sectionid,eventdate);
			//List onholdseats=seatingdbhelper.getOnHoldSeats(eid,eventdate);
			seatingJson.put("ticketnameids", allticket_id_name);
			for(int i=0;i<seats.size();i++){
				HashMap seat=(HashMap)seats.get(i);
					JSONObject temp=new JSONObject();
					/*temp.put("ticketnameids", allticket_id_name);
					temp.put("row_id", seat.get("row_id"));
					temp.put("col_id", seat.get("col_id"));
					temp.put("seat_ind", seat.get("seatindex"));*/
					temp.put("seatcode", seat.get("seatcode"));
					if("N".equals(seat.get("isseat"))){
						//temp.put("type", "noseat");
						continue;
					}
					String seatindex=seat.get("seatindex").toString();
					String groupid=allotedseats.get(seatindex);
					
					if(groupid!=null){
						String color=seatgroups.get(groupid);
						//ArrayList ticketnames=seatingdbhelper.getAllotedticketnames(eid,sectionid,color);
						ArrayList ticketnames=new ArrayList();
						//ArrayList ticketids=seatingdbhelper.getAllotedticketid(eid,sectionid,color);
						ArrayList ticketids=(ArrayList) hm.get(groupid);
						/*try{
						if(ticketids.size()>0){
							for(int l=0;l<ticketids.size();l++){
								String tktid=(String)ticketids.get(l);
								if(allticket_id_name.containsKey(tktid)){
									ticketnames.add(allticket_id_name.get(tktid));
								}
							}
							
						}
						}catch(Exception e){}*/
						if(soldoutseats.contains(seatindex))
							color=color+"_SO";
							
						if(onholdseats.contains(seatindex))
							color=color+"_Hold";
							
						temp.put("type", color);
						//temp.put("ticketname",ticketnames);
						temp.put("ticketids",ticketids);
					}
					seatingJson.put("s_"+seat.get("row_id")+"_"+seat.get("col_id"), temp);
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
			System.out.println(e.getMessage()+"--exception is"+e.getCause());
		}	
					
		return jsonObj;
	
	}
	
}

