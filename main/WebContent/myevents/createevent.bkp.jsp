<%@taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<script type="text/javascript" src="../build/calendar/calendar-min.js"></script>
<!--script type="text/javascript" language="JavaScript" src="../js/categories.js"></script-->
<script type="text/javascript" language="JavaScript" src="../js/myevents/listevent1.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>
<script type="text/javascript" language="JavaScript" src="/main/js/states.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/common/calendar.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/nice.js"></script>
<script type="text/javascript" src="/main/js/ccpayment.js"></script>
<script type="text/javascript" src="/main/js/payments/creditpopup.js"></script>
<script type="text/javascript" src="/main/js/advajax.js"></script>
<link rel="stylesheet" type="text/css" href="/main/css/creditpopupcs.css" />
<link rel="stylesheet" type="text/css" href="../build/calendar/assets/skins/sam/calendar.css" />

<style>
.yui-overlay { position:absolute;border:0px dotted black; }
.yui-overlay .hd { border:0px solid red; }
.yui-overlay .bd { border:0px solid green; }
.yui-overlay .ft { border:0px solid blue; }
</style>
<script>
var o = {
buttonList: ['save','bold','italic','underline','left','center','right','justify','ol','ul','fontSize','fontFamily','fontFormat','indent','outdent','image','unlink','forecolor','bgcolor','xhtml','textalign','align','removeformat','strikethrough','subscript','superscript','arrow','upload','close','link','unlink'],
iconsPath:('../images/nicEditIcons-latest.gif'),
maxHeight : 250
};
	
</script>
<div id="eventerrors" >
<s:if test="%{errorsExists==true}">
<s:fielderror theme="simple" />
</s:if>
</div>
<s:form name="addeventform" id="addeventform" action="createevent!listevent" method="post" theme="simple" >
<s:hidden name="state" id="hstate"></s:hidden>
<s:hidden name="eid" id="eventid"></s:hidden>
<s:hidden name="mgrId" id="managerid"></s:hidden>
<s:hidden name="addEventData.region" id="hregion"></s:hidden>
<s:hidden name="subcategory" id="hsub"></s:hidden>
<jsp:include page="../help/myevents_messages.jsp" />
<!--div class='taskcontent'><script>setHelpContent("listevent_topheader_helpcontent")</script></div-->
<!--div style="height:8px;"></div-->

<div class="taskbox">
<span class="taskheader">Event</span><br/>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr>
<td  width="30%"> Event Title *</td>
<td><s:textfield name="addEventData.eventTitle" theme="simple" size="60" /></td>
</tr>
<tr><td width="30%" >Registration Type&nbsp;*
<span class="helpboxlink" id="registrationtypehelp">
<img src="../images/questionMark.gif" /></span></td>
<td>
<s:set name="currency" value="addEventData.currency"></s:set>
<s:set name="powertype" value="addEventData.powerWithType"></s:set>
<input type='radio' id='powerWithTypeTicketing' name='addEventData.powerWithType' value='ticketing' <s:if test="%{#powertype == 'ticketing'}">checked="checked"</s:if> onclick="registrationsettings('ticketing')"/>Tickets - Collect payment from attendee
<span id="rsvp_id">
<input type='radio' id='powerWithTypeRSVP' name='addEventData.powerWithType' value='rsvp' <s:if test="%{#powertype == 'rsvp'}">checked="checked"</s:if> onclick="registrationsettings('rsvp')" />RSVP - No payment
required from attendee
</span>
</td></tr>
<tr>
<td>
<div id="registrationtypehelppanel" style="visibility: hidden">
<div class="hd"><script>setHelpTitle(listevent_registrationtype_helptitle)</script></div>
<div class="bd"><script>setHelpContent("listevent_registrationtype_helpcontent")</script></div>
</div>
</td>
</tr>
<tr><td colspan="2">
<div id="ticketingcontent" style="display:block">
</div>
<input type="hidden" name="addEventData.rsvptype" value="0" />
</td></tr>
<!--tr>
<td >Category * </td>
<td align="left" >
<s:select label="Select Category" name="addEventData.category" id='category' headerKey="1"
headerValue="-- Select Category --"	list="categorytype" onchange="javascript:callCategeory()" />
</td>
</tr>
<tr>
<td valign="top" >Sub Category</td>
<td><select  name="addEventData.subcategory" id='sub'></select></td>
</tr-->
</table> 
</div>
<br/>
<div class="taskbox">
<span  class="taskheader">When</span><br/>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr>
<td  width="30%">Time Zone *</td>
<td>
<s:select name="addEventData.timezones" id='timezones' headerKey="1"
headerValue="-- Select Time Zone --" list="timezones" listKey="key" listValue="value"  />
</td>
</tr>
<tr><td>Recurring Event<span class="helpboxlink" id="recurringhelp">
<img src="../images/questionMark.gif" /></span></td>
<td>
<s:checkbox id="isRecurring" name="isRecurring" onclick="showCal()" theme="simple"/>Yes
</td>
</tr>
<tr>
<td>
<div id="recurringhelppanel" style="visibility: hidden">
<div class="hd"><script>setHelpTitle(listevent_recurring_helptitle)</script></div>
<div class="bd"><script>setHelpContent("listevent_recurring_helpcontent")</script></div>
</div>
</td>
</tr>


<tr>
<td valign="top" style="padding-bottom:0px;">Start Time *</td>
<td align="left" style="padding-bottom:0px;">
<table cellpadding="0" cellspacing="0">
<tr>
<td id="startdate" style="padding-bottom:0px;">
<table><tr>
<td ><s:textfield name="addEventData.startmonth" id="start_month_field" size="2"/></td>
<td ><s:textfield name="addEventData.startday" id="start_day_field" size="2"/></td>
<td ><s:textfield name="addEventData.startyear" id="start_year_field" size="4"/></td>
<td ><img src="../images/calendar_icon.gif" id="start_btn_cal" onclick="showDateCal('start')"/>
<div id="overlayContainer_start"></div></td>
</tr>
</table>
</td>
<td style="padding-bottom:0px;"><table><tr>
<td >
<select name="addEventData.starthour" id="addEventData.starthour" >
<s:iterator value="%{hours}" var="type">
<s:set name="cval" value="key"/>
<s:set name="starthour" value="addEventData.starthour"/>
<option value="<s:property value="key"/>" <s:if test="%{#starthour==#cval}">selected='selected'</s:if>>${value}</option>
</s:iterator>
</select></td>
<td >
<select name="addEventData.startminute" id="addEventData.startminute" >
<s:iterator value="%{minutes}" var="type">
<s:set name="cval" value="key"/>
<s:set name="startminute" value="addEventData.startminute"/>
<option value="<s:property value="key"/>" <s:if test="%{#startminute==#cval}">selected='selected'</s:if>>${value}</option>
</s:iterator>
</select></td>
<td ><select name="addEventData.stampm" id="addEventData.stampm"><option value="AM" >AM</option><option value="PM">PM</option></select></td>
</tr></table></td>
</tr>
</table>
</td>
</tr>
<tr>
<td valign="top" style="padding-bottom:0px;">End Time *</td>
<td align="left" style="padding-bottom:0px;">
<table cellpadding="0" cellspacing="0">
<tr>
<td id="enddate" style="padding-bottom:0px;">
<table>
<tr>					
<td ><s:textfield name="addEventData.endmonth" id="end_month_field" size="2"/></td>
<td ><s:textfield name="addEventData.endday" id="end_day_field" size="2"/></td>
<td ><s:textfield name="addEventData.endyear" id="end_year_field" size="4"/></td>						
<td ><img src="../images/calendar_icon.gif" id="end_btn_cal" border="0" onclick="showDateCal('end')">
<div id="overlayContainer_end"></div></td>
</tr>
</table>
</td>
<td style="padding-bottom:0px;"><table><tr>
<td >
<select name="addEventData.endhour" id="addEventData.endhour" >
<s:iterator value="%{hours}" var="type">
<s:set name="cval" value="key"/>
<s:set name="endhour" value="addEventData.endhour"/>
<option value="<s:property value="key"/>" <s:if test="%{#endhour==#cval}">selected='selected'</s:if>>${value}</option>
</s:iterator>
</select></td>
<td >
<select name="addEventData.endminute" id="addEventData.endminute" >
<s:iterator value="%{minutes}" var="type">
<s:set name="cval" value="key"/>
<s:set name="endminute" value="addEventData.endminute"/>
<option value="<s:property value="key"/>" <s:if test="%{#endminute==#cval}">selected='selected'</s:if>>${value}</option>
</s:iterator>
</select></td>
<td ><select name="addEventData.endampm" id="addEventData.endampm"><option value="AM" >AM</option><option value="PM">PM</option></select>
</td>
</tr></table></td>
</tr>

</table>
<input type="button"  id="adddatebtn" value="ADD Date" style="display:none;"></input>
</td>

</tr>

<tr>
<td colspan="2">
<div id="recurdates" style="display: none;">
<table cellpadding="0" cellspacing="0">
<tr>
<td><div id="cal1Container"></div></td>
<td width="2"></td>
<td>
<table valign="middle" cellpadding="0" cellspacing="0">
<tr><td style="padding-bottom:0px;">Event Schedule</td></tr>
<tr><td>
<s:select list="recurringDates" listKey="key" listValue="value"   
id="seldates" theme="simple" size="10" cssStyle="float: right;width:275px;" />
</td></tr>
<tr>
<td><input type="button" id="deletedatebtn" value="Delete"></input></td>
</tr>
</table>
</td>
</tr>

</table></div>
</td>
</tr>
</table>
</div>
<br/>
<div class="taskbox">
<span class="taskheader">Where</span><br/>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr>
<td width="30%" >Venue</td>
<td><s:textfield name="addEventData.venue" theme="simple" size="60"/></td>
</tr>
<tr>
<td >Address Line1</td>
<td><s:textfield name="addEventData.address1" theme="simple" size="60"/></td>
</tr>
<tr>
<td>Address Line2</td>
<td><s:textfield name="addEventData.address2" theme="simple" size="60"/></td>
</tr>
<tr>
<td >City *</td>
<td><s:textfield name="addEventData.city" theme="simple" size="60"/></td>
</tr>
<tr>
<td valign="top" >Country *</td>
<td>
<s:select label="Select Country" name="addEventData.country" id='hcountry' headerKey="1"
headerValue="-- Select Country --"
list="countrylist" listKey="key" listValue="value" onchange="CallStates();CallRegions();" />
</td>
</tr>
<!--tr>
<td valign="top" >State/Province/Region</td>
<td><select  name="addEventData.state" id='state_d'>
</select>		
</td>
</tr-->
</table>
</div>
<br/>
<div class="taskbox">
<span class="taskheader">Description</span><br/>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr>
<td width="30%"></td>
<td><s:iterator value="%{descriptiontype}" var="type">
<s:radio name="addEventData.descriptiontype" list="#{key: value}" 
value="%{addEventData.descriptiontype}" onclick="reloadeditors1('addevent');"/>&nbsp;
</s:iterator>
</td> 
</tr>
<tr>
<td align="center" colspan="2">
<div id="fckdesctxtcontent" style="display:none;">
<s:textarea name="addEventData.textcontent" rows="15" cols="101" id="textmsg"></s:textarea>
</div>
</td>
</tr>
<tr>
<td align="center" colspan="2">
<div id="fckdesccontent" style="display:block;width:700px;max-width: 700px;">
<s:textarea name="addEventData.description"  rows="15" cols="120" id="msg"></s:textarea>
</div>
</td>
</tr>
</table>
</div>
<br/>

<div class="taskbox">
<span class="taskheader">Host</span><br/>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr>
<td width="30%" >Name *</td>
<td><s:textfield name="addEventData.name" theme="simple" size="60"/></td>
</tr>
<tr>
<tr>
<td >Contact Email *</td>
<td><s:textfield name="addEventData.email" id="contactemail" theme="simple" size="60"/></td>
</tr>
</table>
</div>
<br/>
<!--div class="taskbox">
<span class="taskheader">Listing Options</span><br/>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr><td width="30%" >Registration Type&nbsp;*
<span class="helpboxlink" id="registrationtypehelp">
<img src="../images/questionMark.gif" /></span></td>
<td>
<s:set name="currency" value="addEventData.currency"></s:set>
<s:set name="powertype" value="addEventData.powerWithType"></s:set>
<input type='radio' id='powerWithTypeTicketing' name='addEventData.powerWithType' value='ticketing' <s:if test="%{#powertype == 'ticketing'}">checked="checked"</s:if> onclick="registrationsettings('ticketing')"/>Tickets

<span id="rsvp_id">
<input type='radio' id='powerWithTypeRSVP' name='addEventData.powerWithType' value='rsvp' <s:if test="%{#powertype == 'rsvp'}">checked="checked"</s:if> onclick="registrationsettings('rsvp')" /> RSVP
</span>
</td></tr>
<tr>
<td>
<div id="registrationtypehelppanel" style="visibility: hidden">
<div class="hd"><script>setHelpTitle(listevent_registrationtype_helptitle)</script></div>
<div class="bd"><script>setHelpContent("listevent_registrationtype_helpcontent")</script></div>
</div>
</td>
</tr>
<tr><td colspan="2">
<div id="ticketingcontent" style="display:block">
</div>

<input type="hidden" name="addEventData.rsvptype" value="0" />
</td></tr>
<tr>
<td width="30%" >Listing Type</td>
<td><s:iterator value="%{listingtype}" var="type">
<s:radio name="addEventData.listtype" list="#{key: value}" value="%{addEventData.listtype}"   />&nbsp;
</s:iterator>
</td>
</tr>
<tr>
<td >Listing Privacy<span class="helpboxlink" id="listingprivacyhelp">
<img src="../images/questionMark.gif" /></span></td>
<td><s:iterator value="%{listingprivacy}" var="type">
<s:radio name="addEventData.listingprivacy" list="#{key: value}" value="%{addEventData.listingprivacy}"   />&nbsp;
</s:iterator>
</td>
</tr>
<tr>
<td>
<div id="listingprivacyhelppanel" style="visibility: hidden">
<div class="hd"><script>setHelpTitle(listevent_listingprivacy_helptitle)</script></div>
<div class="bd"><script>setHelpContent("listevent_listingprivacy_helpcontent")</script></div>
</div>
</td>
</tr>

</table>
</div-->
<!--<div class="taskbox" id="nts">
<span class="taskheader" >Network Ticket Selling<img src="../images/questionMark.gif" id="ntshelp" class="helpboxlink" style="padding-left: 3px"/></span><br/>
<div id="ntshelppanel" style="visibility: hidden;">
<div class="hd"><script>setHelpTitle(nts_helptitle)</script></div>
<div class="bd"><script>setHelpContent("nts_helpcontent");</script></div>
</div>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >

<tr>
	<td><input type="radio" name="addEventData.ntsEnable" value="Y" id="" <s:if test='%{addEventData.ntsEnable == "Y"}'>checked="checked"</s:if>>Enable&nbsp;<s:textfield name="addEventData.ntsCommission" theme="simple" size="5"/>% Commission
	<span class="helpboxlink" id="commissionhelp"><img src="../images/questionMark.gif" /></span></td>
</tr>

<tr>
<td>
<div id="commissionhelppanel" style="visibility: hidden">
<div class="hd"><script>setHelpTitle(commission_helptitle)</script></div>
<div class="bd"><script>setHelpContent("commission_helpcontent")</script></div>
</div>
</td>
</tr>

<tr>
	<td><input type="radio" name="addEventData.ntsEnable" value="N" id="" <s:if test='%{addEventData.ntsEnable == "N"}'>checked="checked"</s:if>>Disable</td>
</tr>
</table>
</div>-->
<table align="center">
<tr><td colspan="2" height="5"></td></tr>
<tr><td colspan="2" align="center">
<div class="submitbtn-style" id="submitBtn" onclick="addThisEvent()">Continue To Payment Processing Settings</div>
<div class="submitbtn-style" id="submitBtn2" onclick="addThisEvent()" style="display:none;">Processing...</div>

</td></tr>
</table>
<div id="optlist"></div>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
</s:form>
<script>
selectedDatehash=[];
var keysofsdh=[];
var j=0;
new nicEditor(o).panelInstance('msg');
 var selectedtype='';
function reloadeditors1(eventmanagetype){
 
	if(eventmanagetype=="addevent"){
		options=document.forms['addeventform'].elements['addEventData.descriptiontype'];
	}else{
		options=document.forms['editeventform'].elements['editEventData.descriptiontype'];
	}
	
	for(i=0;i<options.length;i++){
	opt=options[i];
	if(opt.checked) 
	selectedtype=opt.value;
	}
	if (selectedtype == 'text' || selectedtype == 'html') {
		document.getElementById('fckdesccontent').style.display = 'none';
		document.getElementById('fckdesctxtcontent').style.display = 'block';
	} else {
		document.getElementById('fckdesccontent').style.display = 'block';
		document.getElementById('fckdesctxtcontent').style.display = 'none';
	}
}

//reloadeditors1('addd');
//alert(document.getElementById('isRecurring').checked);
if(document.getElementById('isRecurring').checked){
 var eventid=document.getElementById('eventid').value;
 if(eventid!='' && eventid!='undefined'){
 url="/main/myevents/createevent!eventListRecurringDates?eid="+eventid;
     new Ajax.Request(url, {
    	       method: 'post',
    	       onSuccess: function(t){
    	        var json=eval('('+t.responseText+')');
    	        var recurringdatekeys=json.recurringdatekeys;
    	        var recurringdates=json.recurringdates;
    	        for(var i=0;i<recurringdatekeys.length;i++){
    	          addOption(recurringdatekeys[i],recurringdates[recurringdatekeys[i]],i);
    	          keysofsdh.push(recurringdatekeys[i]);
    	          selectedDatehash[recurringdatekeys[i]]=recurringdates[recurringdatekeys[i]];
    	        }
    	       },onFailure:function(){alert("Failure");}
         });
    }
}

try{YAHOO.ebee.recurringevent.calendar.init();}catch(err){}
try{showCal();}catch(err){}
try{registrationsettings("${addEventData.powerWithType}");}catch(err){}
//try{callCategeory();}catch(err){alert("cat loading failed")}
try{CallStates();}catch(err){}
try{fillRecurringdates();}catch(err){}
try{reloadeditors1("addevent");}catch(err){}
//var btn = new YAHOO.widget.Button("submitBtn", { onclick: { fn: addThisEvent } });
var btn1 = new YAHOO.widget.Button("deletedatebtn", { onclick: { fn: removeOptionSelected } });

function addThisEvent(event, jsonObj){
    //YAHOO.ebee.popup.wait.show();
    ++j;
    document.getElementById('submitBtn').style.display='none';
	document.getElementById('submitBtn2').style.display='block';
	var email=document.getElementById('contactemail').value;
	email=email.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	document.getElementById('contactemail').value=email;
	if(j==1)	
	listEvent('addevent',document.getElementById('isRecurring').checked,selectedtype);
	
}
loadHelpPanel("listingprivacyhelppanel", "listingprivacyhelp", "500px");
loadHelpPanel("registrationtypehelppanel", "registrationtypehelp", "500px");
loadHelpPanel("recurringhelppanel", "recurringhelp", "500px");
loadHelpPanel("ntshelppanel", "ntshelp", "500px");
loadHelpPanel("commissionhelppanel", "commissionhelp", "600px");

function convert(text){
 var chars = ["ò","δ","σ","Σ","ρ","ω","Ω","Ψ","Ο","ξ","Ξ","ν","μ","Λ","κ","ι","θ","Θ","η","ζ","π","☻","☺","♠","♣","♦","♥","©","Û","®","ž","Ü","Ÿ","Ý","Þ","%","¡","ß","¢","à","£","á","À","¤","â","Á","¥","ã","Â","¦","ä","Ã","§","å","Ä","¨","æ","Å","©","ç","Æ","ª","è","Ç","«","é","È","¬","ê","É","­","ë","Ê","®","ì","Ë","¯","í","Ì","°","î","Í","±","ï","Î","²","ð","Ï","³","ñ","Ð","´","ò","Ñ","µ","ó","Õ","¶","ô","Ö","·","õ","Ø","¸","ö","Ù","¹","÷","Ú","º","ø","Û","»","ù","Ü","@","¼","ú","Ý","½","û","Þ","€","¾","ü","ß","¿","ý","à","‚","À","þ","á","ƒ","Á","ÿ","å","„","Â","æ","…","Ã","ç","†","Ä","è","‡","Å","é","ˆ","Æ","ê","‰","Ç","ë","Š","È","ì","‹","É","í","Œ","Ê","î","Ë","ï","Ž","Ì","ð","Í","ñ","Î","ò","‘","Ï","ó","’","Ð","ô","“","Ñ","õ","”","Ò","ö","•","Ó","ø","–","Ô","ù","—","Õ","ú","˜","Ö","û","™","×","ý","š","Ø","þ","›","Ù","ÿ","œ","Ú"];
  var codes = ["&ograve;","&delta;","&sigma;","&Sigma;","&rho;","&omega;","&Omega;","&Psi;","&Omicron;","&xi","&Xi;","&nu","&mu;","&Lambda;","&kappa","&iota;","&theta;","&Theta;","&eta;","&zeta;","&pi;","&#9787;","&#9785;","&spades;","&clubs;","&diams;","&hearts;","&copy;","&#219;","&reg;","&#158;","&#220;","&#159;","&#221;","&#222;","&#37;","&#161;","&#223;","&#162;","&#224;","&#163;","&#225;","&Agrave;","&#164;","&#226;","&Aacute;","&#165;","&#227;","&Acirc;","&#166;","&#228;","&Atilde;","&#167;","&#229;","&Auml;","&#168;","&#230;","&Aring;","&#169;","&#231;","&AElig;","&#170;","&#232;","&Ccedil;","&#171;","&#233;","&Egrave;","&#172;","&#234;","&Eacute;","&#173;","&#235;","&Ecirc;","&#174;","&#236;","&Euml;","&#175;","&#237;","&Igrave;","&#176;","&#238;","&Iacute;","&#177;","&#239;","&Icirc;","&#178;","&#240;","&Iuml;","&#179;","&#241;","&ETH;","&#180;","&#242;","&Ntilde;","&#181;","&#243;","&Otilde;","&#182;","&#244;","&Ouml;","&#183;","&#245;","&Oslash;","&#184;","&#246;","&Ugrave;","&#185;","&#247;","&Uacute;","&#186;","&#248;","&Ucirc;","&#187;","&#249;","&Uuml;","&#64;","&#188;","&#250;","&Yacute;","&#189;","&#251;","&THORN;","&#128;","&#190;","&#252","&szlig;","&#191;","&#253;","&agrave;","&#130;","&#192;","&#254;","&aacute;","&#131;","&#193;","&#255;","&aring;","&#132;","&#194;","&aelig;","&#133;","&#195;","&ccedil;","&#134;","&#196;","&egrave;","&#135;","&#197;","&eacute;","&#136;","&#198;","&ecirc;","&#137;","&#199;","&euml;","&#138;","&#200;","&igrave;","&#139;","&#201;","&iacute;","&#140;","&#202;","&icirc;","&#203;","&iuml;","&#142;","&#204;","&eth;","&#205;","&ntilde;","&#206;","&ograve;","&#145;","&#207;","&oacute;","&#146;","&#208;","&ocirc;","&#147;","&#209;","&otilde;","&#148;","&#210;","&ouml;","&#149;","&#211;","&oslash;","&#150;","&#212;","&ugrave;","&#151;","&#213;","&uacute;","&#152;","&#214;","&ucirc;","&#153;","&#215;","&yacute;","&#154;","&#216;","&thorn;","&#155;","&#217;","&yuml;","&#156;","&#218;"];
  for(x=0; x<chars.length; x++){
  text=text.replace(new RegExp(chars[x], 'g'),codes[x]);
  }
  return text;
  }
</script>
<s:iterator value="recurringDates" var="recurdate">
	<script>
//selectDate("${key}");
</script>
</s:iterator>

