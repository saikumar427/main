package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Vector;

import com.event.beans.BadgesData;

import com.eventbee.beans.Entity;
import com.eventbee.filters.DsnProperty;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;

public class BadgesDB {
	public static String [] getFontTypes(){
		String [] fonttypes={"Arial","Arial Black","sans-serif"};
		return fonttypes;
	}
	public static String [] getFontSizes(){
		String [] fontsizes={"8","10","12","14","16","18","20","22","24","26","28","30"};
		return fontsizes;
	}
	
	
	public static Vector getAttendeeListInfo(String groupid,BadgesData badgesData){	       
	        Vector tv=null;
	   try{
	        HashMap traninfo=null;
	        DBManager dbmanager=null;
	        String readDsn=DsnProperty.get("db.dsn.attendee.badges","").trim();
	        if(!"".equals(readDsn))
	        	dbmanager=new DBManager(readDsn);
	        else
	        	dbmanager=new DBManager();
	        boolean isrecurr = EventDB.isRecurringEvent(groupid);
	        String eventDate=badgesData.getEventDate();
	        String tids=badgesData.getTransactionId().trim();
	        
	        System.out.println("the staretdate is::"+badgesData.getStartDate()+"::end date::"+badgesData.getEndDate()+" tids: "+tids);
	        
	        badgesData.setSYear(badgesData.getStartDate().split("/")[2]);
	        badgesData.setSMonth(badgesData.getStartDate().split("/")[0]);
	        badgesData.setSDay(badgesData.getStartDate().split("/")[1]);
	        
	        
	        badgesData.setEYear(badgesData.getEndDate().split("/")[2]);
	        badgesData.setEMonth(badgesData.getEndDate().split("/")[0]);
	        badgesData.setEDay(badgesData.getEndDate().split("/")[1]);
	        
	        String startDate=badgesData.getSYear()+"-"
		                  	 + badgesData.getSMonth()+"-"
			                 + badgesData.getSDay();
        	String endDate=badgesData.getEYear()+"-"
			                +badgesData.getEMonth()+"-"
			                 +badgesData.getEDay();	
        	
        	System.out.println("final statdate is::"+startDate+"::"+endDate+" selecttype:: "+badgesData.getTransactionradio());
        	
        	
            String transactionradio=badgesData.getTransactionradio();
            String name=badgesData.getSortBy();
            String selectedTkts=badgesData.getSelectedTkts().toString();
            if("firstname".equals(name))name="fn";
            else if("lastname".equals(name))name="ln";
           // System.out.println("transactionrdio value"+transactionradio);
        	StatusObj stobj=null;

	       
	        String GET_ATTENDEELIST_INFO="select distinct UPPER(a.fname) as fn,UPPER(a.lname) as ln, " +
	        "a.fname,a.lname, initcap(COALESCE(a.fname,''))||' '||initcap(COALESCE(a.lname,'')) as name, " +
	        "a.seatcode,d.profilekey as buyerkey, a.profilekey as attendeekey, a.transactionid " +
	        "from profile_base_info a, event_reg_transactions c, buyer_base_info d " +
			"where (UPPER(paymentstatus)  in ('COMPLETED','CHARGED','PENDING') ) " +
			" and a.transactionid=c.tid and c.tid= d.transactionid and d.transactionid=a.transactionid " ;
	        int qtype=0;
	        tids=tids.replace(",", "','").replaceAll("\\s+","");
	    	if("3".equals(transactionradio)){
	        	 GET_ATTENDEELIST_INFO+=" and c.eventid=? and  c.tid in('"+tids+"')";
	        	 qtype=1;
	        }else{
	        	 if(!"".equals(eventDate)){
	        		 GET_ATTENDEELIST_INFO+=" and c.eventid||'_'||c.eventdate=?"; 
	        		 if( "2".equals(transactionradio) && !"".equals(startDate) && !"".equals(endDate)){
		        		 GET_ATTENDEELIST_INFO+=" and to_char(c.transaction_date,'yyyy-mm-dd') between ? and ?";
		        		 qtype=2;
		        	 }else if("4".equals(transactionradio) && !"".equals(selectedTkts)){
		        		 selectedTkts = selectedTkts.replace("[", "").replace("]", "").replace(", ", ",");
		        		 GET_ATTENDEELIST_INFO+=" and a.ticketid in("+selectedTkts+")";
		        		 qtype=3;
		        	 }else{
		        		qtype=3;
		        	 }
	        	 }else{
	        		 if( "2".equals(transactionradio) && !"".equals(startDate) && !"".equals(endDate)){
		        		 GET_ATTENDEELIST_INFO+=" and c.eventid=? and to_char(c.transaction_date,'yyyy-mm-dd') between ? and ?";
		        		 qtype=4;
		        	 }else if("4".equals(transactionradio)&& !"".equals(selectedTkts)){
		        		 selectedTkts = selectedTkts.replace("[", "").replace("]", "").replace(", ", ",");
		        		 GET_ATTENDEELIST_INFO+=" and c.eventid=? and a.ticketid in("+selectedTkts+")";
		        		 qtype=5;
		        	 }else{
	        	    	 GET_ATTENDEELIST_INFO+=" and c.eventid=? ";
		        		 qtype=5;
		        	 }
	        	 }
	         }
	         GET_ATTENDEELIST_INFO+=" order by "+name;
	         long attendeelistinfotime=System.currentTimeMillis();
	         if(qtype==1){
	        	 stobj=dbmanager.executeSelectQuery(GET_ATTENDEELIST_INFO,new String[]{groupid});
	         }
	         if(qtype==2){
	        	 stobj=dbmanager.executeSelectQuery(GET_ATTENDEELIST_INFO,new String[]{groupid+"_"+eventDate,startDate,endDate});
	         }
	         if(qtype==3){
	        	 stobj=dbmanager.executeSelectQuery(GET_ATTENDEELIST_INFO,new String[]{groupid+"_"+eventDate});
	         }
	         if(qtype==4){
        		 stobj=dbmanager.executeSelectQuery(GET_ATTENDEELIST_INFO,new String[]{groupid,startDate,endDate});
	         }
	         if(qtype==5){
	        	 stobj=dbmanager.executeSelectQuery(GET_ATTENDEELIST_INFO,new String[]{groupid});
             }
	         
	         long attendeelistinfototaltime=(System.currentTimeMillis())-attendeelistinfotime;
	 		 System.out.println("Attendee_Badges_eid:"+groupid+" time taken to execute GET_ATTENDEELIST_INFO query: "+attendeelistinfototaltime+" MS");

	       
	       	// StatusObj stobj=dbmanager.executeSelectQuery(GET_ATTENDEELIST_INFO,new String[]{groupid});
	       	 if(stobj.getStatus()){
				tv=new Vector();
				for(int i=0;i<stobj.getCount();i++){
				traninfo=new HashMap();
				traninfo.put("firstname",dbmanager.getValue(i,"fname",""));
				traninfo.put("lastname",dbmanager.getValue(i,"lname","") );
				traninfo.put("name",dbmanager.getValue(i,"name",""));
				traninfo.put("seatcode",dbmanager.getValue(i,"seatcode",""));
				traninfo.put("transactionid",dbmanager.getValue(i,"transactionid","") );	
				traninfo.put("attendeekey",dbmanager.getValue(i,"attendeekey","") );
				traninfo.put("buyerkey",dbmanager.getValue(i,"buyerkey","") );
				tv.addElement(traninfo);
			}
	}
	}catch(Exception e){
		System.out.println("exception occured in get badges data by get attendee list info"+e);
	}
	       	 
	return tv;
	}
	public static Vector getRSVPAttendeeListInfo(String groupid){	       
        Vector tv=null;
        HashMap traninfo=null;
        DBManager dbmanager=new DBManager();	        
        String GET_RSVPATTENDEELIST_INFO="select attendeeid,attendeekey,"
			 +" phone,comments,address,address1,firstname || ' ' || lastname  as name,firstname,lastname,email,company,attendeecount,attendingevent"
			 +" from rsvpattendee where eventid=CAST(? AS BIGINT) order by attendeeid";

       	 StatusObj stobj=dbmanager.executeSelectQuery(GET_RSVPATTENDEELIST_INFO,new String[]{groupid});
       	 if(stobj.getStatus()){
			tv=new Vector();
			for(int i=0;i<stobj.getCount();i++){
			traninfo=new HashMap();
			traninfo.put("firstname",dbmanager.getValue(i,"firstname",""));
			traninfo.put("lastname",dbmanager.getValue(i,"lastname","") );
			traninfo.put("name",dbmanager.getValue(i,"name",""));
			traninfo.put("transactionid",dbmanager.getValue(i,"attendeeid","") );				
			tv.addElement(traninfo);
		}
}
return tv;
}
public static String getBadgesData(String eid, BadgesData badgesData,ArrayList attributeFields,String purpose,Vector attendeelistvector){
		
		//Vector attendeelistvector=new Vector();
		//if("rsvpbadges".equals(purpose)){
			//attendeelistvector=getRSVPAttendeeListInfo(eid);
		//}else{
		/*try{
			//attendeelistvector=getAttendeeListInfo(eid,badgesData);
		//	}
		}catch(Exception e){
			System.out.println("exception occured in get badges data by get attendee list info"+e);
		}*/
		 
		String pageheight=badgesData.getPageheight();
		String pagewidth=badgesData.getPagewidth();
		String leftmargin=badgesData.getLeftmargin();
		String rightmargin=badgesData.getRightmargin();
		String topmargin=badgesData.getTopmargin();
		String bottommargin=badgesData.getBottommargin();
		String colwidth=badgesData.getColwidth();
		String colheight=badgesData.getColheight();
		String line1fontsize=badgesData.getLine1fontsize();
		String line2fontsize=badgesData.getLine2fontsize();
		String line1fontfamily=badgesData.getLine1fontfamily();
		String line2fontfamily=badgesData.getLine2fontfamily();
		String radiotype=badgesData.getTransactionradio();
		pageheight=("".equals(pageheight) || pageheight==null)?"11":pageheight;
		pagewidth=("".equals(pagewidth) || pagewidth==null)?"8.5":pagewidth;
		leftmargin=("".equals(leftmargin) || leftmargin==null)?"1":leftmargin;
		rightmargin=("".equals(rightmargin) || rightmargin==null)?"1":rightmargin;
		topmargin=("".equals(topmargin) || topmargin==null)?"1":topmargin;
		bottommargin=("".equals(bottommargin) || bottommargin==null)?"1":bottommargin;
		colwidth=("".equals(colwidth) || colwidth==null)?"4":colwidth;
		colheight=("".equals(colheight) || colheight==null)?"2":colheight;
		line1fontsize=("".equals(line1fontsize) || line1fontsize==null)?"10":line1fontsize;
		line2fontsize=("".equals(line2fontsize) || line2fontsize==null)?"9":line2fontsize;
		line1fontfamily=("".equals(line1fontfamily) || line1fontfamily==null)?"sans-serif":line1fontfamily;
		line2fontfamily=("".equals(line2fontfamily) || line2fontfamily==null)?"sans-serif":line2fontfamily;
		int items=0;
		if(attendeelistvector!=null)
		items=attendeelistvector.size();
		int cols=0;
		double colwid=0;
		int rows=0;
		String result=" ";
		String name="";
		String seatcode="";
		StringBuffer fopresult = new StringBuffer();
		int balance=0;
		double colhigh=0.0;
		double height=0.0;
		double tabwidth=0.0;
		double vermargin=Double.parseDouble(topmargin)+Double.parseDouble(bottommargin);
		double hormargin=Double.parseDouble(leftmargin)+Double.parseDouble(rightmargin);

		double tabheight=0.0;
		int rowsperpage=0;
		int pages=0;

		try{
			 tabwidth=Double.parseDouble(pagewidth)-(double)hormargin;
			 colwid=Double.parseDouble(colwidth);
			 cols=(int)(tabwidth/colwid);
			 tabheight=Double.parseDouble(pageheight)-vermargin;
			 colhigh=Double.parseDouble(colheight);
			 double d=items/cols;
			 rows=(int)java.lang.Math.ceil(d);
			 double e=tabheight/colhigh;
			 double f=java.lang.Math.floor(tabheight/colhigh);
			 rowsperpage=(f>e)?((int)f)-1:(int)f;
			 pages=(int)rows/rowsperpage;
			 balance=items%(rowsperpage*cols);
			 
			 
			 
		}catch(Exception e){
			System.out.println("Exception ocuured in BadgesDB.java is==="+e);
		}
		if(rowsperpage>rows)
			rowsperpage=rows;
			 //String custom_setid=RSVPReportsDB.getAttribSetID(eid,"EVENT");
		
		HashMap mainhm=new HashMap();
		if(attributeFields.size()>0)
			mainhm=getBadgesResponses(eid,badgesData.getTransactionId(),radiotype);
		long fopresulttime=System.currentTimeMillis();
		//end-indent='0in' in <fo:table-body>
		result= "<fo:root xmlns:fo='http://www.w3.org/1999/XSL/Format'>"
			+"<fo:layout-master-set>"
			+"<fo:simple-page-master page-width='"+pagewidth+"in' page-height='"+pageheight+"in' master-name='PageMaster'  margin-top='"+topmargin+"in' margin-bottom='"+bottommargin+"in' margin-left='"+leftmargin+"in' margin-right='"+rightmargin+"in' >"
			+"<fo:region-body margin='"+leftmargin+" "+rightmargin+" "+topmargin+" "+bottommargin+" '/>"
			+"<fo:region-after display-align='after' extent='10mm'/>"
			+"</fo:simple-page-master>"
			+"</fo:layout-master-set>";
			fopresult.append(result);
			int attendeeindex=0;
			for(int m=0;m<=pages;m++){
				if(attendeelistvector.size()<=attendeeindex)
					break;
				result="<fo:page-sequence format='1' initial-page-number='1' force-page-count='no-force' " +
				"master-reference='PageMaster'>"
				+"<fo:static-content flow-name='xsl-region-after'>"
				+"<fo:block font-size='9pt' text-align='right'>"
				//+"<fo:page-number/>"
				+"</fo:block>"
				+"</fo:static-content>"
				+"<fo:flow line-height='15pt' font-size='12pt' flow-name='xsl-region-body'>"
				+"<fo:block>"
				+"<fo:block font-weight='bold' color='red' font-size='10pt' text-align='center'>"
				+"</fo:block>"
				//+"<fo:block border-after-width='1pt' border-after-style='solid' font-weight='bold' text-align='right' font-size='10pt'  space-after='1em'>"
				//+"</fo:block>"
				+"<fo:block text-align='center' font-weight='bold' color='red' font-size='10pt' font-family='"+line1fontfamily+"' space-after='1em' />"
				+"<fo:table padding-start='15pt' space-after='20pt' table-layout='fixed'  >"
	
				+"<fo:table-column number-columns-repeated='"+cols+"'  column-width='"+colwid+"in'       column-number='1'  />"
				+"<fo:table-body>";
				fopresult.append(result);
				if(attendeelistvector!=null){

					if(m<pages){
						for(int j=0;j<rowsperpage;j++){
							result="<fo:table-row font-weight='bold'    height='"+colheight+"in'   >";
							fopresult.append(result);
							for(int i=0;i<cols;i++){
								int p=cols*j+i;
								HashMap attendeedata=(HashMap)attendeelistvector.elementAt(attendeeindex++);
							    seatcode=GenUtil.AllXMLEncode((String)attendeedata.get("seatcode"));
								seatcode=seatcode.trim();
								if(seatcode.equals("null") || seatcode.equals("")){	
									seatcode="";
								}else{	
								  seatcode=seatcode;
								} 
								name=GenUtil.AllXMLEncode((String)attendeedata.get("name"));
								if(name.equals("null") || name.equals("")){	
									name="";
								}else{name=name;}
								
								result="<fo:table-cell  width='"+colwid+"in' padding='2pt'  text-align='center'  " +
								"display-align='center' border-width='1pt' border-color='black'   border-style='solid'>" +
								"<fo:table  width='"+colwid+"in' padding-start='15pt' space-after='20pt' table-layout='fixed'" +
								"  ><fo:table-column number-columns-repeated='1'  column-width='100%'     " +
								"  column-number='1'  /><fo:table-body><fo:table-row font-weight='bold'      >" +
								"<fo:table-cell  width='"+colwid+"in' padding='2pt'  text-align='center'  " +
								"display-align='center' border-width='0pt' border-color='black'  " +
								" border-style='solid'><fo:block font-family='"+line1fontfamily+"'  " +
								" font-size='"+line1fontsize+"pt'>"+name+"</fo:block>" +"<fo:block font-family='"+line1fontfamily+"'  "+
								" font-size='"+line1fontsize+"pt'>"+seatcode+"</fo:block>"+
								"</fo:table-cell></fo:table-row>";
								fopresult.append(result);
								String tid=(String)attendeedata.get("attendeekey");				
								HashMap attribhm=new HashMap();
								HashMap buyerattribhm=new HashMap();			
				
								if (mainhm!=null&&mainhm.size()>0){		
									attribhm=(HashMap)mainhm.get((String)attendeedata.get("attendeekey"));
									buyerattribhm=(HashMap)mainhm.get((String)attendeedata.get("buyerkey"));
									if(buyerattribhm==null) buyerattribhm=new HashMap();
									if(attribhm==null) attribhm=new HashMap();
								}
								for(int q=0;q<attributeFields.size();q++){	
									String val=(String)attribhm.get(attributeFields.get(q));
								    if(val==null) val=(String)buyerattribhm.get(attributeFields.get(q));
									if(val==null)	val="";
									if(val.indexOf("##")>0)	val=val.replaceAll("##",", ");
									
									result="<fo:table-row font-weight='bold'><fo:table-cell  width='"+colwid+"in' padding='2pt'  text-align='center'  display-align='center' border-width='0pt' border-color='black'   border-style='solid'><fo:block font-family='"+line2fontfamily+"'   font-size='"+line2fontsize+"pt' text-align='center'>"+GenUtil.AllXMLEncode(val)+"</fo:block></fo:table-cell></fo:table-row>";
									fopresult.append(result);
								}
			
								fopresult.append("</fo:table-body></fo:table></fo:table-cell>");
							}

							fopresult.append("</fo:table-row>");
						}

					}
					if(m==pages){
						//boolean moreItemsExist=true;
						//while(moreItemsExist){
						while(attendeelistvector.size()>attendeeindex){
							result="<fo:table-row font-weight='bold'  font-size='"+line1fontsize+"pt' height='"+colheight+"in'  >";
							fopresult.append(result);
						for(int i=0;i<cols;i++){

							if(attendeelistvector.size()>attendeeindex){
								HashMap attendeedata=(HashMap)attendeelistvector.elementAt(attendeeindex++);
								String tid=(String)attendeedata.get("attendeekey");
								HashMap attribhm=new HashMap();
								seatcode=GenUtil.AllXMLEncode((String)attendeedata.get("seatcode"));
								seatcode=seatcode.trim();
								if(seatcode.equals("null")|| seatcode.equals("")){	
									seatcode="";
								}else{seatcode=seatcode;} 
							    name=GenUtil.AllXMLEncode((String)attendeedata.get("name"));
								if(name.equals("null") || name.equals(""))
									name="";
								else name=name;
				
								result="<fo:table-cell  width='"+colwid+"in' padding='2pt' " +
								" text-align='center'  display-align='center' border-width='1pt' " +
								"border-color='black'   border-style='solid'><fo:table  width='"+colwid+"in'" +
								" padding-start='15pt' space-after='20pt' table-layout='fixed'  >" +
								"<fo:table-column number-columns-repeated='1'  column-width='100%' " +
								"column-number='1'  /><fo:table-body><fo:table-row font-weight='bold' >" +
								"<fo:table-cell  width='"+colwid+"in' padding='2pt'  text-align='center'  display-align='center' " +
								"border-width='0pt' border-color='black'  " +
								" border-style='solid'><fo:block font-family='"+line1fontfamily+"'  " +
								" font-size='"+line1fontsize+"pt'>"+name+"</fo:block>" +
								"<fo:block font-family='"+line1fontfamily+"'  " +
								" font-size='"+line1fontsize+"pt'>"+seatcode+"</fo:block>"+
								"</fo:table-cell></fo:table-row>";
								fopresult.append(result);
				                HashMap buyerattribhm=new HashMap();			
								
								if (mainhm!=null&&mainhm.size()>0){		
									attribhm=(HashMap)mainhm.get((String)attendeedata.get("attendeekey"));
									buyerattribhm=(HashMap)mainhm.get((String)attendeedata.get("buyerkey"));
									if(buyerattribhm==null) buyerattribhm=new HashMap();
									if(attribhm==null) attribhm=new HashMap();
								}
								for(int q=0;q<attributeFields.size();q++){					
									String val=(String)attribhm.get(attributeFields.get(q));
									if(val==null) val=(String)buyerattribhm.get(attributeFields.get(q));
									if(val==null)	val="";
									if(val.indexOf("##")>0)	val=val.replaceAll("##",", ");
									result="<fo:table-row font-weight='bold'><fo:table-cell  width='"+colwid+"in' padding='2pt'  text-align='center'  display-align='center' border-width='0pt' border-color='black'   border-style='solid'><fo:block font-family='"+line2fontfamily+"'   font-size='"+line2fontsize+"pt' text-align='center'>"+GenUtil.AllXMLEncode(val)+"</fo:block></fo:table-cell></fo:table-row>";
									fopresult.append(result);
								}
								
								fopresult.append("</fo:table-body></fo:table></fo:table-cell>");
							}/*else{
								moreItemsExist=false;
							}*/
						}
						fopresult.append("</fo:table-row>");
						}
					}
				}else{
					result="<fo:table-row font-weight='bold'><fo:table-cell  width='"+colwid+"in' padding='2pt'  text-align='center'  display-align='center' border-width='0pt' border-color='black'   border-style='solid'><fo:block font-family='"+line2fontfamily+"'   font-size='"+line2fontsize+"pt' text-align='center'>No Attendees Registered</fo:block></fo:table-cell></fo:table-row>";
					fopresult.append(result);
				}

				fopresult.append("</fo:table-body></fo:table></fo:block></fo:flow></fo:page-sequence>");
			}
			fopresult.append("</fo:root>");
			long fopresulttotaltime=(System.currentTimeMillis())-fopresulttime;
			System.out.println("Attendee_Badges_eid:"+eid+" time taken to build fop result: "+fopresulttotaltime+" MS");
		return fopresult.toString();
	}

	public static ArrayList getEventDates(String eid){
		DBManager db=new DBManager();
		ArrayList eventDatesList=new ArrayList();
		StatusObj statobj=null;
		String query="";
		
		 String powertype=DbUtil.getVal("select value from config where config_id " +
				" in(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name='event.rsvp.enabled'",new  String[]{eid});
	
		 if("yes".equals(powertype)){
	         System.out.println("in if");
			 query="select date_display as evt_start_date from event_dates where eventid=CAST(? AS BIGINT)" +
			" order by date_key";
	
			
		 }else{
			System.out.println("in else");
			 query= " select to_char(zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00')," +
            " 'HH24:MI:SS') as text) as time ),'Dy, Mon DD, YYYY HH12:MI AM') as evt_start_date " +
            " from event_dates where eventid=CAST(? AS BIGINT)";

		}
		
		statobj=db.executeSelectQuery(query,new String[]{eid});
		//System.out.println("status in geteventdates"+statobj.getStatus());
		int count=statobj.getCount();
		if(statobj.getStatus() && count>0){
          for(int i=0;i<count;i++){
        	  eventDatesList.add(new Entity(db.getValue(i,"evt_start_date",""),db.getValue(i,"evt_start_date","")));
  			
        	  
          }
		}
		
		return eventDatesList;
	}
	
	public static ArrayList getCustomAttributes(String eid){
		
		String CUSTOM_ATTRIBUTES_QUERY="select attrib_id,attribname from custom_attribs where " +
		"attrib_setid=(select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT) " +
		"and purpose='EVENT') and attrib_id not in (select attribid from buyer_custom_questions where eventid=CAST(? AS BIGINT)) " +
		"order by position";
		
		DBManager dbmanager=new DBManager();
		ArrayList attribs_list=new ArrayList();
		StatusObj statobj=null;
		HashMap hm=new HashMap();
		statobj=dbmanager.executeSelectQuery(CUSTOM_ATTRIBUTES_QUERY,new String []{eid, eid});
				int count1=statobj.getCount();
					if(statobj.getStatus()&&count1>0){
					for(int k=0;k<count1;k++){
						attribs_list.add(new Entity(dbmanager.getValue(k,"attrib_id",""),dbmanager.getValue(k,"attribname","")));
					}
			}
		
		return attribs_list;
	 }
	
	public static HashMap getAllCustomAttributesMap(String eid){
		
		String CUSTOM_ATTRIBUTES_QUERY="select attrib_id,attribname,subquestionof,attrib_setid from custom_attribs where " +
		"attrib_setid=(select attrib_setid from custom_attrib_master where groupid=CAST(? AS BIGINT) " +
		"and purpose='EVENT') and attrib_id not in (select attribid from buyer_custom_questions where eventid=CAST(? AS BIGINT)) " +
		"order by position";
		
		DBManager dbmanager=new DBManager();
		ArrayList attribs_list=new ArrayList();
		ArrayList<Entity> subquestionlist=null;
		StatusObj statobj=null;
		HashMap hm=new HashMap();
		HashMap optionLabelMap=new HashMap();
		HashMap subquestionofMap=new HashMap();
		HashMap attribOptionMap=new HashMap();
		ArrayList attribOptionList=null;
		StringBuffer sb = new StringBuffer();
		String attribsetid="";
		statobj=dbmanager.executeSelectQuery(CUSTOM_ATTRIBUTES_QUERY,new String []{eid, eid});
		int count1=statobj.getCount();
		if(statobj.getStatus()&&count1>0){
			for(int k=0;k<count1;k++){
				String attribval=dbmanager.getValue(k,"attribname","");
				//attribs_list.add(new Entity(dbmanager.getValue(k,"attrib_id",""),attribval));
				
				if(dbmanager.getValue(k,"subquestionof","no").equals("no")){
					attribs_list.add(new Entity(dbmanager.getValue(k,"attrib_id",""),attribval));
				}else{
					
					if(subquestionofMap.containsKey(dbmanager.getValue(k,"subquestionof","")))
						subquestionlist=(ArrayList<Entity>) subquestionofMap.get(dbmanager.getValue(k,"subquestionof",""));
					else
						subquestionlist=new ArrayList<Entity>();
					
					subquestionlist.add(new Entity(dbmanager.getValue(k,"attrib_id",""),attribval));
					subquestionofMap.put(dbmanager.getValue(k,"subquestionof",""), subquestionlist);
					
					if(sb.length()>0)
						sb.append(",'"+dbmanager.getValue(k,"subquestionof","no")+"'");
					else sb.append("'"+dbmanager.getValue(k,"subquestionof","no")+"'");
				}
			}
			
			attribsetid=dbmanager.getValue(0,"attrib_setid","");
			if(sb.length()>0){
				String query="select attrib_id,option,option_val from custom_attrib_options where attrib_setid=CAST(? AS INTEGER) and attrib_id||'_'||option in("+sb.toString()+") order by attrib_id,position";
				statobj=dbmanager.executeSelectQuery(query,new String []{attribsetid});
				if(statobj.getStatus()&&statobj.getCount()>0){
					for(int j=0;j<statobj.getCount();j++){
						optionLabelMap.put(dbmanager.getValue(j,"attrib_id","")+"_"+dbmanager.getValue(j,"option",""),dbmanager.getValue(j,"option_val",""));
						if(attribOptionMap.containsKey(dbmanager.getValue(j,"attrib_id","")))
							attribOptionList=(ArrayList)attribOptionMap.get(dbmanager.getValue(j,"attrib_id",""));
						else attribOptionList=new ArrayList();
						attribOptionList.add(dbmanager.getValue(j,"attrib_id","")+"_"+dbmanager.getValue(j,"option",""));
						attribOptionMap.put(dbmanager.getValue(j,"attrib_id",""),attribOptionList);
					}
				}
			}
		}
		
		HashMap allmap =new HashMap();
		allmap.put("attriblist", attribs_list);
		allmap.put("optionlabelmap", optionLabelMap);
		allmap.put("subquestionofmap", subquestionofMap);
		allmap.put("attriboptionmap", attribOptionMap);
		return allmap;
	 }
	
	public static String getConfigValue(String eid) {
		String configValue=DbUtil.getVal("select value from config where name=? and config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT))",new String[] {"event.seating.enabled",eid});
		return configValue;
	}
	
	public static HashMap getBadgesResponses(String eid,String tid,String radiotype){
		HashMap hm=new HashMap();
		DBManager dbmanager=null;
        String readDsn=DsnProperty.get("db.dsn.badges.custom.response","").trim();
        if(!"".equals(readDsn))
        	dbmanager=new DBManager(readDsn);
        else
        	dbmanager=new DBManager();
		StatusObj statobj=null;
		
		String BADGES_RESPONSE_QUERY="select a.attribid,a.bigresponse as response,b.profilekey " +
		"from custom_questions_response a,custom_questions_response_master b where a.ref_id=b.ref_id " +
		"and b.groupid=CAST(? AS BIGINT) and a.attribid not in (select attribid from buyer_custom_questions where " +
		"eventid=CAST(? AS BIGINT)) and a.bigresponse!=''";
		
		if("3".equals(radiotype)){
			String tids=tid.trim();
			tids=tids.replace(",", "','").replaceAll("\\s+","");
			BADGES_RESPONSE_QUERY+=" and b.transactionid in('"+tids+"')";
		}
		BADGES_RESPONSE_QUERY+=" order by a.ref_id";
		long attribresponsesttime=System.currentTimeMillis();
		statobj=dbmanager.executeSelectQuery(BADGES_RESPONSE_QUERY,new String []{eid, eid});
		long attribresponsetotaltime=(System.currentTimeMillis())-attribresponsesttime;
		System.out.println("Attendee_Badges_eid:"+eid+" time taken to execute BADGES_RESPONSE_QUERY: "+attribresponsetotaltime+" MS");
		int count=statobj.getCount();
		if(statobj.getStatus()&&count>0){
		long attribresponseloopsttime=System.currentTimeMillis();
		for(int k=0;k<count;k++){
			String profilekey=dbmanager.getValue(k,"profilekey","");
			HashMap options=(HashMap)hm.get(profilekey);
			if (options==null)	options=new HashMap();
			options.put(dbmanager.getValue(k,"attribid","0"),dbmanager.getValue(k,"response","0"));
			hm.put(profilekey,options);
		}
		}
		return hm;
	}

	
}

