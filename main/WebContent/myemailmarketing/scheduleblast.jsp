<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" language="JavaScript" src="../js/common/calendar.js"></script>
<script type="text/javascript" src="../build/calendar/calendar-min.js"></script>
<link rel="stylesheet" type="text/css" href="../build/calendar/assets/skins/sam/calendar.css" />
<script type="text/javascript" src="../js/emailmarketing/campaignmanage.js"></script>
<script type="text/javascript" language="JavaScript" src="../js/Tokenizer.js"></script>

<s:form theme="simple" action="campaignlistmanage!blastSave" name="emailblastform" id="emailblastform">

<s:hidden name="campId"/>
<s:hidden name="mailQuota"/>
<s:hidden name="emailScheduleData.campName"/>
<s:hidden name="emailCampId"/>
<s:hidden name="editselectedcount"/>
<s:if test="%{errorsExists==true}">
<div class="error"><s:fielderror theme="simple" /></div>
</s:if>
<div style="display: none;" id="errorblock">
<div id="fromerror"></div>
<div id="toerror"></div>
<div id="subjecterror"></div>
</div>
<jsp:include page="/help/myemailmarketing_messages.jsp"></jsp:include>
<!--  <div class="taskcontent"><script>setHelpContent("scheduleblast_topheader_helpcontent")</script></div>
-->
<div style="height:3px;"></div>
<!-- <div class="taskbox"><span class="taskheader">Mail Quota</span><br />
<table border="0" cellpadding="3" cellspacing="0" width="100%">
<tbody><tr>
<td>Available Mail Quota: ${mailQuota}</td></tr>
<tr><td>
<a target="_blank" href="#">Buy more Mail Quota</a>
</td></tr>
</tbody>
</table>
</div>
<br /> -->
<div class="taskbox"><span class="taskheader">Settings</span><br />
<table border="0" cellpadding="3" cellspacing="0" width="100%">
<tr>
<td  width="30%">Subject *</td>
<td><s:textfield name="emailScheduleData.campSubject" theme="simple" size="60" id="subject"/></td>
</tr>
<tr>
<td  width="30%">From *</td>
<td><s:textfield name="emailScheduleData.campFrom" theme="simple" size="60" id="fromAddress"/></td>
</tr>
<tr>
<td  width="30%">Reply To *</td>
<td><s:textfield name="emailScheduleData.campReplyTo" theme="simple" size="60" id="replytoAddress"/></td>
</tr>
<!-- <tr>
<td  width="30%">Display powered by ${appName} logo?</td>
<td>
<s:set name="displayLogo" value="emailScheduleData.displayEbeeLogo"></s:set>
<input type='radio' id='descType' name='emailScheduleData.displayEbeeLogo' value='yes' <s:if test="%{#displayLogo == 'yes'}">checked="checked"</s:if>/> Yes
<input type='radio' id='descType' name='emailScheduleData.displayEbeeLogo' value='no' <s:if test="%{#displayLogo == 'no'}">checked="checked"</s:if>/>No
</td>
</tr> -->
</table>
</div>
<br />
<div class="taskbox"><span class="taskheader">To</span><br />
<table border="0" cellpadding="3" cellspacing="0" width="100%">
<tr>
<td  width="30%">To *<br/><span class="smallestfont">Select mailing lists</span></td>
<td>
<div STYLE="height : 250px; width: 570px; font-size: 12px; overflow: auto; border-style:outset;">
<table>
<s:iterator value="%{allMailLists}" var="mailListData">
<tr>
<td>
<s:checkbox name="selectedMailLists" fieldValue="%{listId}" value="%{selectedMailLists.contains(listId)}" />${listName}
<s:set name="temp" value='listId'/>
<s:set name="aa" value='listMembersCount[#temp]'/>
<s:if test="%{#aa == ''}">
<s:set name="aa" value="0"/>
</s:if>
(Member Count: <s:property value='#aa'/>)
</td>
</tr>
</s:iterator>
</table>
</div>
</td>
</tr>

</table>
</div>
<br />
<div class="taskbox"><span class="taskheader">Test Mail</span><br />
<table border="0" cellpadding="3" cellspacing="0" width="100%">
<tr><td colspan="2">
<table  align="left" >
<tbody>
<tr><!--Send Test Mail-->
<td>Review your campaign by sending
test mail to yourself. This optional step helps you to correct any mistakes in the campaign before sending out to your mailing lists
</td>
</tr>
<tr><td width="30%" align="left"   colspan="2">
<div id="emailerror" class="errorMessage" style="display:none"></div>
</td></tr>
<tr><td width="30%" align="left" colspan="2"><table><tbody><tr><td>Emails <br><span class="smallestfont">Comma separated</span></td>
<td><input type="text" size="70" name="emailScheduleData.mailtotest" id="mailtotest">
<input type="button" value="Send" name="sendmail" id="sendmail">
</td>
</tr> </tbody></table></td></tr>	               
<tr><td width="30%" align="center" colspan="2"><div id="result"  style="display:none"></div></td></tr> <tr></tr>
</tbody></table></td>
</tr>
</table>
</div>
<br/>
<div class="taskbox"><span class="taskheader">When</span><br />
<table border="0" cellpadding="3" cellspacing="0" width="100%">
<tr>
<td  width="30%" valign="top">Schedule Campaign</td>
<td>
<table>
<s:set name="type" value="emailScheduleData.schtimeType"></s:set>
<s:if test="%{#type != 'date'}">
<tr><td>
<input type='radio' id='schType' name='emailScheduleData.schtimeType' value='now'  <s:if test="%{#type == 'now'}">checked="checked"</s:if>/> Blast Now <span class="smallestfont">Actual blast happens within an hour</span>
</td></tr>
</s:if>
<tr><td>
<table cellpadding="0" cellspacing="0">
<tr><td>
<input type='radio' id='schType' name='emailScheduleData.schtimeType' value='date' <s:if test="%{#type == 'date'}">checked="checked"</s:if>/> Blast At </td>
<td id="startdate">
<table><tr>
<td width="2"></td>
<td><s:textfield name="emailScheduleData.month" id="start_month_field" size="2"/></td>
<td><s:textfield name="emailScheduleData.day" id="start_day_field" size="2"/></td>
<td><s:textfield name="emailScheduleData.year" id="start_year_field" size="4"/></td>
<td><img src="../images/calendar_icon.gif" id="start_btn_cal" onclick="showDateCal('start')">
<div id="overlayContainer_start"></div></td>
<td>
<s:text	name="emailScheduleData.hour"></s:text></td>
<td><span class="smallestfont">(EST)</span></td>
</tr>
</table>
</td>
</tr></table>
</td></tr>
<tr><td>
<input type='radio' id='schType' name='emailScheduleData.schtimeType' value='later' <s:if test="%{#type == 'later'}">checked="checked"</s:if>/>Schedule Later
</td></tr>
</table>
</td>
</tr>
</table>
</div>
<br/>

<table  width="100%">
<tr>
<td colspan='1' align='right'>
<input id="submitBtn" type="button"  value="Submit"/>
</td>
<td colspan='1' align='left'>
<!-- <a id="cancellink"	href="javascript:history.back(1)" >Cancel</a>-->
<input id="cancellink" type="button"  value="Cancel"/>
</td>
</tr>
</table>
</s:form>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<script>
var j=0;
var currentCampId='${campId}';
function submitForm(event, jsonobj){
YAHOO.ebee.popup.wait.show();
//document.getElementById("emailblastform").action="createcampaign!saveCampaign";
var email=document.getElementById('fromAddress').value;
	email=email.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	document.getElementById('fromAddress').value=email;
document.getElementById("emailblastform").action="campaignlistmanage!blastSave";
$("emailblastform").request({
onSuccess:function(t)
{
var result=t.responseText;
if(result.indexOf('success')>-1)
window.location.href="campaignlistmanage!blastSave?campId="+currentCampId+"&res=respone";
else if(result.indexOf('errorMessage')>-1)
{
YAHOO.ebee.popup.wait.hide();
	$('errorblock').show();
	$('errorblock').scrollTo();
	$('errorblock').update(result);
	j=0;
}
else{
var json=eval('('+result+')');
var selectedcount=json.selectedmailcount;
var totalleft=json.quotaleft;
YAHOO.ebee.popup.wait.hide();
$('errorblock').hide();
alert("Your available mail quota is "+totalleft+" and selected email count is "+selectedcount);
j=0;
return false;
	}
}
});
}


function init() {
var submitBtn = new YAHOO.widget.Button("submitBtn", { onclick: { fn: submit } });
var cancellink = new YAHOO.widget.Button("cancellink", { type:"link",href:"javascript:history.back(1)" });
var sendmail = new YAHOO.widget.Button("sendmail", { onclick: { fn: submitEmailBlastForm }  });
}
function submit(event,jsonobj)
{
++j;
if(j==1)
submitForm(event,jsonobj);
}
YAHOO.util.Event.onDOMReady(init);
</script>