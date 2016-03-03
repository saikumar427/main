<%@ page import="java.util.*" %>
<%@ page import="com.eventbee.general.*" %>
<%@page import="com.eventbee.layout.DBHelper"%>
<%!
      public static String  GetRequestParam(HttpServletRequest req, String[] paramnames){
               for(int i=0;i<paramnames.length;i++)
                  if(req.getParameter(paramnames[i])!=null)
                     return req.getParameter(paramnames[i]);
              return null;
       }

     public static HashMap getTrackURLContet(String eventid,String trackcode){
          DBManager dbmanager=new DBManager();
	      HashMap hm=new HashMap();
	      String trackurldata="select * from trackURLs where eventid=? and trackingcode=?";
	      StatusObj statobj=dbmanager.executeSelectQuery(trackurldata,new String[]{eventid,trackcode});
	      if(statobj.getStatus()){
	      String [] columnnames=dbmanager.getColumnNames();
	      for(int i=0;i<statobj.getCount();i++){
	         for(int j=0;j<columnnames.length;j++){
	             hm.put(columnnames[j],dbmanager.getValue(i,columnnames[j],""));
	         }
	        }
	      }
	 return hm;
    }
     
     public static HashMap getConfigValuesFromDb(String configid){
    		String ConfigQuery="select * from config where config_id=CAST(? as INTEGER)";
    		DBManager db=new DBManager();
    		HashMap confighm=new HashMap();
    		StatusObj sb=null;
    		if(!"".equals(configid) && configid!=null)
    		sb=db.executeSelectQuery(ConfigQuery,new String[]{configid});
    		if(sb.getStatus()){
    			for(int i=0;i<sb.getCount();i++){
    				confighm.put(db.getValue(i,"name",""),db.getValue(i,"value",""));
    			}
    		}
    		return confighm;
    	}
     
     public static StatusObj insertHit(String[] inputparams){
    	 String Hit_Query="insert into Hit_Track(source,resource,sessionid,access_at,id,userid) values(?,?,?,now(),?,?)";
    	 StatusObj stob=DbUtil.executeUpdateQuery(Hit_Query,inputparams);
 		return stob;
 	}
     
     public String ValidateNTSCode(String groupid,String referral_ntscode){
    		String nts_code="";
    		nts_code=DbUtil.getVal("select nts_code from event_nts_partner where eventid=? and nts_code in (select nts_code from ebee_nts_partner where nts_code=? or nts_code_display=?)",new String[]{groupid,referral_ntscode,referral_ntscode});
    		if(!"".equals(nts_code)||nts_code!=null){
    			StatusObj updateStatusObj=DbUtil.executeUpdateQuery("update nts_visit_track set visit_count=visit_count+1, last_visited_at=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') where eventid=? and nts_code=?",new String[]{DateUtil.getCurrDBFormatDate(),groupid,nts_code});
    			if(updateStatusObj.getCount()==0){
    				DbUtil.executeUpdateQuery("insert into nts_visit_track (nts_code,eventid,last_visited_at,visit_count,ticket_sale_count) values (?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),1,0)",new String[]{nts_code,groupid,DateUtil.getCurrDBFormatDate()});
    			}		
    		}
    		return nts_code;
    	}

%>
<%
     String groupid=GetRequestParam(request,  new String []{"eid","eventid", "id","GROUPID","groupid","gid"});
     String participantid=GetRequestParam(request,  new String []{"pid","partnerid", "participantid","participant"});
     String platform=request.getParameter("platform");
     String report=request.getParameter("report");
     String trackcode=request.getParameter("track");
     System.out.println("trackcode in EventParam.jsp:::::"+trackcode);
     String manage=request.getParameter("manage");
     String register=request.getParameter("register");
     String discountcode=request.getParameter("code");
     if(discountcode==null)	discountcode="";
     String purpose=request.getParameter("purpose");
     String ticketurlcode=request.getParameter("tc");
     String context=request.getParameter("context");
     String display_ntscode=request.getParameter("nts");
     String preview=request.getParameter("preview_pwd");
     if(preview==null)preview="";

     HashMap ConfigmapHM=null;
     HashMap<String, String> dataHash = new HashMap<String, String>();
     HashMap<String, String> refHash = new HashMap<String, String>();
     System.out.println("discountcode in EventParams.jsp:"+discountcode);
     refHash.put("discountcode",discountcode);
     refHash.put("trackcode",trackcode);
     refHash.put("ntscode",display_ntscode);
     HashMap blockedEventsMap=(HashMap)EbeeCachingManager.get("blockedEvents");
     if(blockedEventsMap==null){
	     blockedEventsMap=new HashMap();
	     EbeeCachingManager.put("blockedEvents",blockedEventsMap);
     }
     
     if(groupid==null || "".equals(groupid)){
	     System.out.println("Event Page with no eventid in EventParams.jsp: ");
	     response.sendRedirect("/guesttasks/invalidpage.jsp");
	     return;
     }else {
	    if(blockedEventsMap.get(groupid)!=null){
            System.out.println("Event Page with blocked eventid in EventParams.jsp: "+groupid);
	        response.sendRedirect("/guesttasks/invalidpage.jsp");
	        return;
	 }

	try{
	    int eventid=Integer.parseInt(groupid);
	    dataHash=DBHelper.getEventinfo(groupid);
	    String configid=dataHash.get("config_id");
	    if(configid==null)configid="0";
	    ConfigmapHM=DBHelper.getConfigValuesFromDb(configid);
	    if(dataHash==null || dataHash.size()<1 ){
		       String userid=DbUtil.getVal("select userid from user_groupevents where event_groupid =?" ,new String[]{groupid});
		       if(userid==null){
		           System.out.println("Event Page with wrong eventid in EventParams.jsp: "+groupid);	
		           response.sendRedirect("/guesttasks/invalidpage.jsp");
		           return;
		       }else{
			       session.setAttribute("eventgroupid",groupid);
			       request.setAttribute("userid",userid);
			       response.sendRedirect("/customevents/groupThemeProcessor.jsp?groupid="+groupid);
			       return;
		       }
	    }
	   }catch(Exception e){
		    System.out.println("Event Page with wrong eventid in EventParams.jsp: "+groupid);	
		    response.sendRedirect("/guesttasks/invalidpage.jsp");
		    return;
	    }
	 }

	String eventstatus=dataHash.get("status");
	if(eventstatus==null) eventstatus="ACTIVE";
	if("CANCEL".equals(eventstatus)){
	      blockedEventsMap.put(groupid,"Y");
	      EbeeCachingManager.put("blockedEvents",blockedEventsMap);
	      System.out.println("Event Page with cancelled eventid in EventParams.jsp: "+groupid);	
		  response.sendRedirect("/guesttasks/invalidpage.jsp");
		  return;
	}
	String exturl=(String)ConfigmapHM.get("event.regexternal.url");
	if(exturl==null)exturl="";
	if(exturl!=null&& !"".equals(exturl)){
		response.sendRedirect(exturl);
		return;
	}

    if(context==null) context="EB";
    session.setAttribute("context",context);
    if("ning".equals(platform))	session.setAttribute("platform","ning");
    session.removeAttribute("trckcode");

    if(trackcode!=null){
	  HashMap trackshm=getTrackURLContet(groupid,trackcode);
	if(trackshm.size()>0){
	   session.setAttribute("trckcode",trackcode);
	   String status=(String)trackshm.get("status");//EventPageContent.getTrackInfoForKey("status",request,"");
	   if(status==null)status="";
	   if("manage".equals(manage) || "report".equals(report)){
		response.sendRedirect("/main/TrackUrlManage?eid="+groupid+"&trackcode="+trackcode);
		return;
    }
	
	if(!"Suspended".equals(status)){
         boolean trackURLsession=(session.getAttribute(groupid+"_"+trackcode)==null);
	     if(trackURLsession){
	         session.setAttribute(groupid+"_"+trackcode,trackcode);
	         DbUtil.executeUpdateQuery("update trackurls set count=cast(coalesce(cast(count as numeric),0) as numeric)+1 where trackingcode=? and eventid=?",new String[]{trackcode,groupid});
	     }
    }else{
      trackcode=null;
    }
   }else{
      trackcode=null;
    }
   }
   
    if(session.getAttribute("EVENT_PASSWORD_AUTH_DONE_"+groupid)==null && !"no".equals(preview)){
	 String password=DbUtil.getVal("select password from view_security where eventid=?",new String[]{groupid});
	 if(password!=null){
		if(session.getAttribute("EVENT_PASSWORD_AUTH_DONE_"+groupid)==null){
			response.sendRedirect("/guesttasks/eventpassword.jsp?"+request.getQueryString());
		return;
		}
	  }
     }

    boolean eventpagesession=(session.getAttribute("eventpagehit_"+groupid)==null && request.getParameter("preview_pwd")==null);
	  if(eventpagesession){
	       session.setAttribute("eventpagehit_"+groupid,"eventpagehit_"+groupid);
	       String sessid=(String)session.getId();
	       insertHit(new String[]{"eventhandler.jsp","Event Page",sessid,groupid,null});
	   }
	  
	String mgrid=dataHash.get("mgr_id");
	if(mgrid==null)mgrid="";
	String streamershow=(String)ConfigmapHM.get("eventpage.streamer.show");
	if(streamershow==null)streamershow="";
	if("Yes".equals(streamershow))
	{%>
	 <jsp:include page='userstreamer_n.jsp' >
	 <jsp:param name='mgrid' value='<%=mgrid%>' />
	 </jsp:include>
   <%}
	
	String referral_ntscode="";
	String nts_enable=dataHash.get("nts_enable");
	if(nts_enable==null)nts_enable="N";
	String nts_commission=dataHash.get("nts_commission");
	if(nts_commission==null)nts_commission="0";
	if("".equals(nts_enable) || "N".equals(nts_enable)){
		nts_enable="N";
		nts_commission="0";
		//display_ntscode="";
	}
	 refHash.put("ntsenable",nts_enable);
     refHash.put("ntscommission",nts_commission);
	try{
	if(Double.parseDouble(nts_commission)<0){
		nts_commission="0";
	}
	}catch(Exception e){
		nts_commission="0";
		System.out.println("exection is:"+e.getMessage());
	}
	if(display_ntscode==null)
		display_ntscode="";	

		if(!"".equals(display_ntscode)){
		
	       String nts=DbUtil.getVal("select nts_code from ebee_nts_partner where nts_code=?",new String[]{display_ntscode});
			
			 if(!"".equals(nts)&& nts!=null)
			{     
			        if(session.getAttribute(groupid+"ntsclick"+display_ntscode)==null)
					{  	   
						   referral_ntscode=ValidateNTSCode(groupid,display_ntscode);
						   session.setAttribute(groupid+"ntsclick"+display_ntscode,display_ntscode);
					}
		        referral_ntscode=nts;
		    }

	if(referral_ntscode==null)referral_ntscode="";
	}
	refHash.put("referralntscode",referral_ntscode);
 %> 