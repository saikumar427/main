<%@ page import="com.eventbee.general.DbUtil,com.eventbee.general.StatusObj,org.json.JSONObject,java.util.Date,java.text.SimpleDateFormat"%>
<%
   String name=request.getParameter("profile_name");
   String email=request.getParameter("profile_email");
   String phone=request.getParameter("profile_phone");
   String company=request.getParameter("profile_company");
   String domain=request.getParameter("domain");
   String ip=request.getParameter("ipaddress");
   
   int recordcount=0;
  String recordid="";
  java.util.Date currentdate = new java.util.Date();
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms");
  String createdat=sdf.format(currentdate);
   StatusObj statobj=null;
   JSONObject jsonobj=new  JSONObject();
   String count=DbUtil.getVal("select count(*) from vb_interested_bio",null);
	if(count==null)count="";
	if(!"".equals(count)){
	    try{
		int rowcount=Integer.parseInt(count);
		recordcount=rowcount+1;
		recordid=Integer.toString(recordcount);
		}catch(Exception e){}
	}
   String contactinfoquery="insert into vb_interested_bio(record_id,name,email,phone,company,domain,created_at,ip) values(?,?,?,?,?,?,?,?)";
   statobj=DbUtil.executeUpdateQuery(contactinfoquery, new String[]{recordid,name.trim(),email.trim(),phone,company,domain,createdat,ip});
   if(statobj.getStatus()){
   System.out.println("contactinformation inserted successfully");
   jsonobj.put("result","success");
   }else if(!statobj.getStatus()){
   System.out.println("contactinformation insert data is fail");
   jsonobj.put("result","fail");
   }
   out.println(jsonobj.toString());
%>