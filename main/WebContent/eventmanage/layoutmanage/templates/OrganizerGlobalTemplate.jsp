<%@page import="java.util.HashMap"%>
<%@page import="com.eventbee.layout.EventGlobalTemplates"%>
<% 
System.out.println("in OrganizerGlobalTemplate.jsp");
String contactMgrLink="<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/advajax.js'></script>";
contactMgrLink+="<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/eventlinks.js'></script>";
contactMgrLink+="#if($hostedby)Hosted by $hostedby <br/>#end";
contactMgrLink+="<a  href=javascript:Show('contactmgr')>#if($contactmgr)$contactmgr #end</a>";
contactMgrLink+=" <div id='contactmgr' style='display: none; margin: 10 5 10 5;'> " ;
contactMgrLink+=" <form name='AttendeeForm'  id='AttendeeForm' action='$action?UNITID=13579&id=$eventid&purpose=CONTACT_EVENT_MANAGER'  method='post' >" ;
contactMgrLink+=" Your Email ID* :<br> " ;
contactMgrLink+=" <input type='text' name='from_email' value=''  style='width: 200px;'><br><br>" ;
contactMgrLink+=" Your Name* :  <br>" ;
contactMgrLink+=" <input type='text' name='from_name' value=''  style='width: 200px;'><br><br> " ;
contactMgrLink+=" Subject :<br> " ;
contactMgrLink+=" <input type='text' name='subject' value='Re: $subject' style='width: 200px;'><br><br> " ;
contactMgrLink+=" Message :<br> " ;
contactMgrLink+=" <textarea name='note' style='width: 210px; height: 75px;'></textarea><br><br> " ;
contactMgrLink+=" <p align='center'> " ;
contactMgrLink+=" <div id='captchamsgmgr' style='display: none; color:red' >Enter Correct Code</div> " ;
contactMgrLink+="Enter the code as shown below:<div style='padding:5px;' valign='top' width='100%'><input type='text'   name='captchamgr'  value=''   valign='top'/>";
contactMgrLink+="  <img  id='captchaidmgr'  alt='Captcha'  /></div><br><br>";
contactMgrLink+="<input type='hidden' name='formnamemgr' value='AttendeeForm'/>";
contactMgrLink+=" <input type='button' name='sendmgr' value='Send'  onClick=' return checkAttendeeForm()' /> " ;
contactMgrLink+=" <input type='button' value='Cancel' onclick=javascript:Cancel('contactmgr'); />" ;
contactMgrLink+=" </p>" ;
contactMgrLink+=" </form> </div>#if($contactmgr)#end</a>";
contactMgrLink+=" <div id='urmessage'></div>";
contactMgrLink+=" <div id='message'></div>";
contactMgrLink+=" #if($notes)<br/>$notes #end";
System.out.println("Organizer Template: "+contactMgrLink);
EventGlobalTemplates.setGlobalTemplates("global_template_organizer",contactMgrLink);
%>
