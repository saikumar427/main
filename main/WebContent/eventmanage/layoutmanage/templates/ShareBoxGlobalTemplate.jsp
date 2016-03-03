<%@page import="com.eventbee.layout.EventGlobalTemplates"%>
<%
  String emailfriendLink="",fbsend="",EventURL=""; 
  StringBuffer stbf=new StringBuffer();
  emailfriendLink="<a  href=javascript:Show('Invitation'); style='text-decoration:none;'>$emailfrndlabel</a>";
  emailfriendLink+=" <div id='Invitation' style='display: none; margin: 10 5 10 5;'> " ;
  emailfriendLink+=" <form name='invitationForm'  id='invitationForm' action='$serveraddress/emailprocess/emailsend.jsp?UNITID=13579&id=$eid&purpose=INVITE_FRIEND_TO_EVENT'  method='post' >" ;
  emailfriendLink+="<input type='hidden' name='url' value='$serveraddress/event?eid=$eid' />";
  emailfriendLink+=" To* :<br> ";
  emailfriendLink+=" <textarea id='toheader' name='toemails' style='width: 210px; height: 70px;'></textarea><br>(separate emails with commas) " ;
  emailfriendLink+=" <br><br> " ;
  emailfriendLink+=" Your Email ID* :<br> " ;
  emailfriendLink+=" <input type='text' name='fromemail' value='$email'  style='width: 200px;'><br><br>" ;
  emailfriendLink+=" Your Name* :  <br>" ;
  emailfriendLink+=" <input type='text' name='fromname' value='$name'  style='width: 200px;'><br><br> " ;
  emailfriendLink+=" Subject :<br> " ;
  emailfriendLink+=" <input type='text' name='subject' value='Fw: $subject' style='width: 200px;'><br><br> " ;
  emailfriendLink+=" Message :<br> " ;
  emailfriendLink+=" <textarea name='personalmessage' style='width: 210px; height: 75px;'>$message</textarea><br><br> " ;
  emailfriendLink+=" <p align='center'> " ;
  emailfriendLink+=" <div id='captchamsg' style='display: none; color:red' >Enter Correct Code</div> " ;
  emailfriendLink+="Enter the code as shown below:<div style='padding:5px;' valign='top' width='100%'><input type='text'   name='captcha'  value=''   valign='top'/>";
  emailfriendLink+="  <img  id='captchaid'  alt='Captcha' src='/captcha?fid=invitationForm&pt=$time' /></div><br><br>";
  emailfriendLink+="<input type='hidden' name='formname' value='invitationForm'/>";
  emailfriendLink+=" <input type='button' name='sendmsg' value='Send'  onClick=' return checkInvitationForm()' /> " ;
  emailfriendLink+=" <input type='button' value='Cancel' onclick=javascript:Cancel('Invitation'); />" ;
  emailfriendLink+=" </p>" ;
  emailfriendLink+=" </form> </div>";
  emailfriendLink+=" <div id='message'></div>";
  
  fbsend="<fb:send href='$serveraddress/event?eid=$eid' font='arial'></fb:send>";
  
  EventURL="<a href=javascript:Show('eventurl') style='text-decoration:none;'>$eventurllabel</a>";
  EventURL+="<div id='eventurl' style='display: none;margin: 10 5 10 5;'>";
  EventURL+="<textarea  cols='27' rows='2' onClick='this.select()'>$serveraddress/event?eid=$eid</textarea></div>";
  
  stbf.append("<table width=\"100%\"><tr><td align=\"left\"><table>");   
  stbf.append("#if($emailfriendLink)<tr><td><table><tr><td valign=\"middle\">"+emailfriendLink+"</td></tr></table></td></tr>#end" );
  stbf.append("#if($fbsend)<tr><td><table><tr><td valign=\"bottom\" style=\"padding-bottom:2px;\">"+fbsend+"</td></tr></table></td></tr>#end");
  stbf.append("#if($EventURL)<tr><td><table><tr><td valign=\"bottom\" style=\"padding-bottom:2px;\">"+EventURL+"</td></tr></table></td></tr>#end");
  stbf.append("</table></td></tr></table>");
  
  EventGlobalTemplates.setGlobalTemplates("global_template_shareboxwidget", stbf.toString());

%>