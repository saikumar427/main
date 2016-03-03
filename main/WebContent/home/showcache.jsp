<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@ page import="com.eventbee.general.*"%>
<%@ page import="org.json.JSONObject"%>
<%
JSONObject job=new JSONObject();
job.put("cache",EbeeCachingManager.ebeeCache);
out.println("<center><h3>Cache Content</h3></center>");
HashMap hm=EbeeCachingManager.ebeeCache;
 Iterator it = hm.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        String key=(String)pairs.getKey();
        if(!key.contains("cachetime")){
        out.println("<b><u>"+key+" :</b></u><br>");
        if(EbeeCachingManager.get(key)!=null){
        	out.println("<b>Data</b>: "+EbeeCachingManager.get(key));
        	out.println("<br>");
        	out.println("<b>Cache Time</b>: "+EbeeCachingManager.get(key+"_cachetime"));
        	Long timedif=(System.currentTimeMillis()-(Long)EbeeCachingManager.get(key+"_cachetime"));
        	out.println("<br>");
        	out.println("<b>Time Difference</b>: "+((timedif)/1000)+" Seconds");
        	out.println("<br>");
        }else{
        	out.println("Empty<br>");
        }
        out.println("<hr>");
        }
    }
if(hm.size()==0){
	out.println("<center><b>Cache is Empty</b></center>");
}
/*out.println("<b>Latest Ticket Ticket Sold :</b><br>");
if(EbeeCachingManager.get("latestevent")!=null){
	out.println(EbeeCachingManager.get("latestevent"));
	out.println("<br>");
	out.println("Cache Time: "+EbeeCachingManager.get("latestevent_cachetime"));
	Long timedif=(System.currentTimeMillis()-(Long)EbeeCachingManager.get("latestevent_cachetime"));
	out.println("<br>");
	out.println("Time Difference : "+((timedif)/1000)+" Seconds");
	out.println("<br>");
}else{
	out.println("Empty<br>");
}
out.println("<b>FB Recent Promotion :</b><br>");
if(EbeeCachingManager.get("recentPromos")!=null){
	out.println(EbeeCachingManager.get("recentPromos"));
	out.println("<br>");
	out.println("Cache Time: "+EbeeCachingManager.get("recentPromos_cachetime"));
	Long timedif=(System.currentTimeMillis()-(Long)EbeeCachingManager.get("recentPromos_cachetime"));
	out.println("<br>");
	out.println("Time Difference : "+((timedif)/1000)+" Seconds");
	out.println("<br>");
}else{
	out.println("Empty<br>");
}
out.println("<b>Featured Events :</b><br>");
if(EbeeCachingManager.get("displayEvents")!=null){
	out.println(EbeeCachingManager.get("displayEvents"));
	out.println("<br>");
	out.println("Cache Time: "+EbeeCachingManager.get("displayEvents_cachetime"));
	Long timedif=(System.currentTimeMillis()-(Long)EbeeCachingManager.get("displayEvents_cachetime"));
	out.println("<br>");
	out.println("Time Difference : "+((timedif)/1000)+" Seconds");
	out.println("<br>");
}else{
	out.println("Empty<br>");
}
out.println("<b>Test Featured Events :</b><br>");
if(EbeeCachingManager.get("TestFeaturedEvents")!=null){
	out.println(EbeeCachingManager.get("TestFeaturedEvents"));
	out.println("<br>");
	out.println("Cache Time: "+EbeeCachingManager.get("TestFeaturedEvents_cachetime"));
	Long timedif=(System.currentTimeMillis()-(Long)EbeeCachingManager.get("TestFeaturedEvents_cachetime"));
	out.println("<br>");
	out.println("Time Difference : "+((timedif)/1000)+" Seconds");
	out.println("<br>");
}else{
	out.println("Empty<br>");
}*/
%>