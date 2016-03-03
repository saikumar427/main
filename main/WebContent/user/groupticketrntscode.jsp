<%@page import="com.eventbee.general.DbUtil,com.eventbee.general.DateUtil,com.eventbee.general.EbeeConstantsF"%>
<%
   String fbuserid="",fname="",lname="",gender="",email="";
   String serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
   fbuserid=request.getParameter("fbuserid");
   fname=request.getParameter("fname");
   lname=request.getParameter("lname");
   gender=request.getParameter("gender");
   email=request.getParameter("email");
   String query="insert into fbusers(fbuserid,domain,fname,lname,email,date) values(?,'www.groupticketr.com',?,?,?,to_timestamp(?,'yyyy-MM-dd HH24:MI:ss.ms'))";
   String currentdate=DateUtil.getCurrDBFormatDate();
   DbUtil.executeUpdateQuery(query,new String[]{fbuserid,fname,lname,email,currentdate});
   System.out.println("data inserted successfully in fbusers");
%>
<script type="text/javascript" src="<%=serveraddress%>/main/js/prototype.js"></script>
<script type="text/javascript" src="<%=serveraddress%>/main/js/advajax.js"></script>
<script>
     var fbuid='<%=fbuserid%>';
     var fname='<%=fname%>';
     var lname='<%=lname%>';
     var gender='<%=gender%>';
     var email='<%=email%>';
     var serveraddress='<%=serveraddress%>';
     generatentscode();
     
     function generatentscode(){
     var url=serveraddress+'/attendee/authtoken?fbuid='+fbuid+'&fname='+fname+'&lname='+lname+'&email='+email+'&gender='+gender;
     new Ajax.Request(url, {
    	       method: 'post',
    	       onSuccess: function(t){
    	         //alert("success");
    	       },onFailure:function(){
    	         alert("Failure");
    	       }
    	       });
 }  
</script>