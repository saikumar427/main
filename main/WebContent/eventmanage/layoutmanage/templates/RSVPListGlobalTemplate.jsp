<%@page import="java.util.HashMap"%>
<%@page import="com.eventbee.layout.EventGlobalTemplates"%> 
<% 
System.out.println("in RSVPListGlobalTemplate.jsp");
StringBuffer buffer=new StringBuffer("");
buffer.append("<html><head>");
buffer.append("<link rel='stylesheet' type='text/css' href='http://www.citypartytix.com/home/css/pagination.css' />");
buffer.append("<script type='text/javascript' language='JavaScript' src='http://www.citypartytix.com/home/js/jQuery.js'></script>");
buffer.append("<script type='text/javascript' language='JavaScript' src='http://www.citypartytix.com/home/js/prototype.js'></script>");
buffer.append("<script type='text/javascript' language='JavaScript' src='http://www.citypartytix.com/home/js/ajax.js'>function ajaxdummy(){ }</script>");
buffer.append("<script type='text/javascript' language='JavaScript' src='/main/js/layoutmanage/fbrsvpeventlist.js'></script>");
buffer.append("<script type='text/javascript' language='JavaScript' src='http://www.citypartytix.com/home/js/fbevent/jquery.pagination.js'></script>");
buffer.append("<script type='text/javascript' src='https://connect.facebook.net/en_US/all.js'></script>");
buffer.append("</head><body>");
buffer.append("<div id='fb-root'></div><div id='fbattendeelist' style='padding:0px;margin:0px;'></div><div id='fbattend' style='padding:0px;margin:0px;'></div>");
buffer.append("<script>");
//buffer.append("window.fbAsyncInit = function() {");
buffer.append("FB.init({appId: '$fbappid' ,status: true, cookie: true, xfbml: true});");
//buffer.append("FB.Event.subscribe('auth.login', function(response) {login();});");
//buffer.append("FB.Event.subscribe('auth.logout', function(response) {logout();});");
//buffer.append("FB.getLoginStatus(function(response) {if (response.authResponse) {login();}});");
//buffer.append("};");
//buffer.append("(function() {var e = document.createElement('script');e.type = 'text/javascript';e.src = document.location.protocol +'//connect.facebook.net/en_US/all.js';e.async = true;document.getElementById('fb-root').appendChild(e);}());");
buffer.append("#if($fbeventid)fbeid=$fbeventid;#end fbshowgenderlink='$showgender';");
buffer.append("fbboxtype='$boxtype';showFBAttendeeList($eid,fbeid);");
buffer.append("var serveraddress='$serveraddress';" +
		"var source='$source';" +
		" var domain='$domain';" +
		" var venueid='$venueid';" + 
		" var sec='$sec';" +
		" var fbuid='$fbuid';" +
		" var eid='$eid';" +
		" var record_id='$record_id';" +
		" var sec='$sec';");
buffer.append("</script></body></html>");
EventGlobalTemplates.setGlobalTemplates("global_template_rsvplist",buffer.toString());
%>