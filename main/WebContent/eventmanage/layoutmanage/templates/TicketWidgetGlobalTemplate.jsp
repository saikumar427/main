<%@page import="com.eventbee.layout.EventGlobalTemplates"%>
<%  
String eventlevelHiddenAttribs="",scriptTag="";  
eventlevelHiddenAttribs="<div id='backgroundPopup'></div><div id='fb-root'></div><script>var servadd='$serveraddress';var  fbavailable=false;window.fbAsyncInit=function(){fbavailable=true;FB.init({appId:$fbappid,status:true,cookie:true,xfbml:true})};(function(a){var b,c='facebook-jssdk';if(a.getElementById(c)){return}b=a.createElement('script');b.id=c;b.async=true;b.src='//connect.facebook.net/en_US/all.js';a.getElementsByTagName('head')[0].appendChild(b)})(document)</script>";
scriptTag="<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/jQuery.js'></script>"+
"<link rel='stylesheet' type='text/css' href='$serveraddress/main/build/container/assets/container.css' />"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/build/yahoo-dom-event/yahoo-dom-event.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/build/container/container-min.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/seating/jquery-ui-1.8.10.custom.min.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/tktWedget.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/prototype.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/ajax.js'>function ajaxdummy(){ }</script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/registration.js?timestamp=$time'></script>"+"<link rel='stylesheet' type='text/css' href='/home/css/seating.css' />"+
"#if($isRsvpEvent)"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/rsvpreg.js?timestamp=$time'></script> #end"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/registration/tickets_registration.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/common/tickets_common.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/common/profile_common.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/registration/profile_registration.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/common/payments_common.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/registration/payments_registration.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/common/confirmation_common.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/registration/confirmation_registration.js?timestamp=$time'></script>"+
"#if($isSeating)"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/registration/seating/getseatingsection_registration.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/common/seating/generateseating_common.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/registration/seating/generateseating_registration_v3.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/common/seating/seatingtimer_common.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/registration/seating/seatingtimer_registration.js?timestamp=$time'></script>"+
"#if($venuecss)<style type='text/css'>$venuecss</style>#end #end"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/controls/buildcontrol.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/controls/ctrlData.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/controls/checkboxWidget.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/controls/selectWidget.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/controls/radioWidget.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/controls/textboxWidget.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/controls/textareaWidget.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/popuphandler.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/effects.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/eventlinks.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/Tokenizer.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/advajax.js'>function dummy() { }</script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/popup.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/ebeepopup.js'></script><link rel='stylesheet' type='text/css' href='/home/css/popupcss.css' />"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/popuphandler.js'></script>"+"<script type='text/javascript' language='JavaScript' src='/main/js/registration/common/hold_js.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/main/js/registration/common/seating/getseatingsection_common.js?timestamp=$time'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/fbevent/fbntslogin.js'></script>"+
"<script type='text/javascript' language='JavaScript' src='$serveraddress/home/js/fbevent/shareonfacebook.js?timestamp=$time'></script>";
eventlevelHiddenAttribs+="<input type='hidden' name='nts_enable' id='nts_enable' value='$ntsenable'><input type='hidden' name='nts_commission' id='nts_commission' value='$ntscommission'>"+
"<input type='hidden' id='login-popup' name='login-popup' value='$loginpopup'><input type='hidden' name='referral_ntscode' id='referral_ntscode' value='$referralntscode'>"+
"<input type='hidden' name='fbappid' id='fbappid' value='$fbappid'><input type='hidden' name='notavailimage' id='notavailimage' value='$notavailimg'>"+
"<input type='hidden' name='notavailmsg' id='notavailmsg' value='$notavailmsg'>"+
"<input type='hidden' name='unassign' id='unassign' value='$unassignimg'>"+
"<input type='hidden' name='unassignmsg' id='unassignmsg' value='$unassignmsg'>"+
"<input type='hidden' name='trackcode' id='trackcode' value='$trackcode'/>"+
"<input type='hidden' name='discountcode' id='discountcode' value='$discountcode'/><input type='hidden' name='ticketurlcode' id='ticketurlcode' value='$ticketurlcode'/><input type='hidden' name='context' id='context' value='$context'/>"+
"<input type='hidden' name='fbsharepopup' id='fbsharepopup' value='$fbsharepopup'><input type='hidden' name='eventstatus' id='eventstatus' value='$eventstatus'/><input type='hidden' name='registrationsource' id='registrationsource' value='regular'>"+
"<input type='hidden' name='venueid' id='venueid' value='$venueid'/><input type='hidden' name='isseatingevent' value='$isseatingevent' id='isseatingevent'/>";

StringBuffer sbf=new StringBuffer();
StringBuffer sbf1=new StringBuffer();
sbf.append("<html><head>");
if(!"".equals(scriptTag) && scriptTag!=null)
	sbf.append(scriptTag);
sbf.append("<style type=\"text/css\">$customcss</style>");
sbf.append("</head>");
sbf.append("<body>");
if(!"".equals(eventlevelHiddenAttribs) && eventlevelHiddenAttribs!=null)
sbf.append(eventlevelHiddenAttribs);
sbf.append("#if ($isRsvpEvent)");
sbf.append("<div class=\"box\">");
sbf.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftboxheader\">");
sbf.append("<tr><td id=\"pageheader\" class=\"medium\" style=\"display:none\"></td></tr></table>");
sbf.append("<table  width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftboxcontent\">");
sbf.append("<tr><td id=\"rsvpreg\" colspan=\"6\"></td></tr>");
sbf.append("<tr><td id=\"rsvpprofilecontent\"></td></tr>");
sbf.append("<tr><td id=\"rsvpimageLoad\" align=\"center\"></td></tr></table>");
sbf.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftboxbottom\"><tr><td></td></tr></table>");
sbf.append("<script>getRsvpOptionsBlock($eid)</script></div>");
sbf.append("#elseif ($isTicketingEvent)<div class=\"box\">");
sbf.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftboxheader\">");
sbf.append("<tr><td id=\"pageheader\" class=\"medium\" style=\"display:none\"></td></tr></table>");
sbf.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftboxcontent\" >");
sbf.append("#if($recurreningSelect)"); 
sbf.append("<tr><td colspan=\"6\">");  
sbf.append("<table><tr><td>$recurringdateslabel: $recurreningSelect</td><tr></table></td><tr>#end");   
sbf.append("<tr><td id=\"registration\"></td></tr>");
sbf.append("<tr><td id=\"profile\" style=\"display:none\"></td></tr>");
sbf.append("<tr><td id=\"paymentsection\" style=\"display:none\"></td></tr>");
sbf.append("<tr><td id=\"imageLoad\" style=\"display:none\" align=\"center\"></td></tr></table>");
sbf.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftboxbottom\"><tr><td></td></tr></table>"); 
sbf.append("<script>getTicketsJson($eid)</script></div>#end");  
sbf.append("</body></html>");
EventGlobalTemplates.setGlobalTemplates("global_template_ticketingwidget", sbf.toString());
%> 
 