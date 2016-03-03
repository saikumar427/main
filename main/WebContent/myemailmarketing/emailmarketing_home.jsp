<%@taglib uri="/struts-tags" prefix="s"%>
<html>

<head>
<link rel="stylesheet" type="text/css" href="/main/css/creditpopupcs.css" />
<script src="../js/emailmarketing/emailmarketing.js" language="JavaScript"	type="text/javascript"></script>
<script type="text/javascript" src="/main/js/payments/creditpopup.js"></script>
<script type="text/javascript" src="../js/popup.js">

function popupdummy(){}
</script>


<script>
var popupWin;
var modelwin;
var val='';
var userid='${userId}';
function StatusResponse(response){
	
	    data=response.responseText;
		var statusJson=eval('('+data+')');
		var status=statusJson.status;	
		if(status=='Success'){
			
			//window.location.href='';
	    document.getElementById("statusmsg").style.display="block";	
		//document.upgradeevent.elements["premiumlisttype"][0].focus();
			
		//document.getElementById("statusmsg").focus();	
		
		getStatusMsg();
		setTimeout("window.location.reload(true)",1000);
		}
}

function getStatusMsg(){

		new Ajax.Request('../eventmanage/ajaxstatusmsg.jsp', {
		method: 'get',
		parameters:{msgType:'upgradeevent'},
		onSuccess: showStatusMsg,
		onFailure:  failureJsonResponse
		});
}
function showStatusMsg(response) {

        document.getElementById("statusmsg").focus();		
		document.getElementById("statusmsg").innerHTML=response.responseText;
}

function failureJsonResponse(){
	alert("Failed");
}
function getStatus(eid,seqID){
		new Ajax.Request('../ccprocessing/checkstatus.jsp', {
		method: 'get',
		parameters:{eid:eid,seqID:seqID},
		onSuccess: StatusResponse,
		onFailure:  failureJsonResponse
		});
}

function closeIt(){
	if (!popupWin.closed){
	setTimeout("closeIt()",1)//adjust timing
	}else{	
		var seqID="<s:text name='creditCardData.seqId'></s:text>";
		document.getElementById("backgroundPopup").style.display="none";
		getStatus(userid,seqID);
	}
}
function windowOpener(url,name,args,seqID){
	if (typeof(popupWin) != "object"){
	popupWin = window.open(url,name,args);
	closeIt();
	} 
	else{
		if (!popupWin.closed){
			popupWin.location.href = url;
		}
		else{
		popupWin = window.open(url, name,args);
		closeIt();
		}
	}
	popupWin.focus();
}
function buymailquota(userid){
	var seqID="<s:text name='creditCardData.seqId'></s:text>";

	var price=document.getElementById("price").value;
	document.getElementById("backgroundPopup").style.display="block";
	
	//windowOpener('home!getPaymentScreen?SeqId='+seqID+'&amount='+price+'&userid='+userid+'&purpose=buymailquota','CCProcessing','WIDTH=740,HEIGHT=500,RESIZABLE=No,SCROLLBARS=YES,TOOLBAR=NO,LEFT=150,TOP=100',seqID);
	if($('creditcardpopup_div')){}
	else{
	var divpopup=document.createElement("div");
	divpopup.setAttribute('id','creditcardpopup_div');
	divpopup.className='creditcardpopup_div';
	var cell=$('maincontent');
	cell.appendChild(divpopup);
	}
	 
  var Msg='<span style="font-weight: normal;font-size: 12px;"><b>Amount: $'+price+'</b></span><br/>NOTE: CC processing is done by Eventbee. Eventbee appears on your credit card statement.';
  var setkey= new callPaykey();
  setkey.setCallbackurl("/main/payments!updateBuyEmail?refId="+userid+"&seqId="+seqID+"&amount="+price);
  setkey.setPurpose("BuyEmailQuota");
  setkey.setPaymode("direct");
  setkey.setMessage(Msg);
  setkey.setAmount(price);
  setkey.setCurrency("USD");
  setkey.setMerchantid("");
  setkey.setLang("En");
  setkey.setTitle("Credit Card Payment");
  setkey.setSoftdis("");
  setkey.setRefid(userid);
  setkey.setVendor("Braintree");
  setkey.setAppendDiv('maincontent');
  setkey.setInternalref('111');
  var paykey=setkey.getPaykey();
     if(paykey!="")
	 {setkey.ccpopup(paykey);
	 }
	 else{
	 alert("unable to process Not valid paykey");
	 }
}
function onsucessclose(){
var seqID="<s:text name='creditCardData.seqId'></s:text>";
getStatus(userid,seqID);
closeEBpopup();
}

</script>
</head>
<div id="backgroundPopup" ></div>
<jsp:include page="/help/myemailmarketing_messages.jsp"></jsp:include>
<s:set name="config" value="configentry"/>
<s:if test="%{#config=='no'}">
<table cellspacing="0" cellpadding="0" border="0" width="100%">
<tr>
<td valign="top" width="644">
To avail this feature email us at <a style="cursor: pointer;" onclick="openPopUp('/main/user/supportemail.jsp','350','330','layoutwidget2')">support@eventbee.com</a></td>
</tr>
</table>
</s:if>
<s:else>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
<tr>
<td valign="top" width="644" style="padding-right:20px">

<table cellspacing="0" cellpadding="0" width="100%">
<tr><td>
</tr>
</table>
<table cellspacing="0" cellpadding="0" width="100%">
<tr><td>
<div class="box" align="left" >
<div class="boxheader">My Mail Quota <span class="helpboxlink" id="buymailquotahelp"><img	src="../images/questionMark.gif" /></span></div>
<div id="buymailquotahelppanel" style="visibility: hidden">
<div class="hd"><script>setHelpTitle(home_buymailquota_helptitle)</script></div>
<div class="bd"><script>setHelpContent("home_buymailquota_helpcontent")</script></div>
</div>
<div class="boxcontent"> 
<div  id="statusmsg" style="display: none"></div>	
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

    <tr>
   <td  colspan="2" height="40" >
Your available Mail Quota: ${mailQuota} </td></tr>
</table>
</div>
<div class="boxfooter">

<select name="MailQuotaDetails.quotaPrice" id="price">
<s:iterator value="%{mailQuotaList}" var="type">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#statusType==#cval}">selected='selected'</s:if> >
<s:property value="value"/></option>
</s:iterator>
</select>
<input type="button" value="Buy Mail Quota" id="buymailquotabtn"></input>
</div>
</div>
</td>

</tr>

<tr><td>
<div class="box" align="left">
<div class="boxheader">My Campaigns <span class="helpboxlink" id="mycampaignshelp"><img	src="../images/questionMark.gif" /></span></div>
<div id="mycampaignshelppanel" style="visibility: hidden">
<div class="hd"><script>setHelpTitle(home_mycampaigns_helptitle)</script></div>
<div class="bd"><script>setHelpContent("home_mycampaigns_helpcontent")</script></div>
</div>
    <div class="boxcontent"> 
<jsp:include page="campaignlist.jsp"></jsp:include>
   
    </div>
    <div class="boxfooter">
    <input class="button" type="button" value="Create Campaign" id="campaignbtn" ></input>
    </div>
</div>
</td></tr>

<tr><td>
<div class="box" align="left">
<div class="boxheader">My Mailing Lists <span class="helpboxlink" id="mymailinglistshelp"><img	src="../images/questionMark.gif" /></span></div>
<div id="mymailinglistshelppanel" style="visibility: hidden">
<div class="hd"><script>setHelpTitle(home_mymailinglists_helptitle)</script></div>
<div class="bd"><script>setHelpContent("home_mymailinglists_helpcontent")</script></div>
</div>
    <div class="boxcontent"> 
    <jsp:include page="maillist.jsp" ></jsp:include>
    </div>
    <div class="boxfooter">
    <input class="button" type="button" value="Create Mailing List" id="newlist" ></input>
    </div>
</div>
</td>
</tr>
</table>
</td>

<td valign="top"><jsp:include page="myemailmarketinghome_sidecontent.jsp"></jsp:include></td>
</tr>
</table>
</s:else>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<div id="iframepopupdialog"><div id="hd"></div><div id="bd"></div></div>
</html>
<script>
loadHelpPanel("buymailquotahelppanel", "buymailquotahelp", "500px");
loadHelpPanel("mycampaignshelppanel", "mycampaignshelp", "500px");
loadHelpPanel("mymailinglistshelppanel", "mymailinglistshelp", "500px");
var btn = new YAHOO.widget.Button("buymailquotabtn", { onclick: {fn: buymailquotafn} });
function buymailquotafn(){
	buymailquota(${userId});
}
var btn1 = new YAHOO.widget.Button("campaignbtn", { onclick: {fn: campaignbtnfn} });
function campaignbtnfn(){
	window.location.href='createcampaign';
}
var btn2 = new YAHOO.widget.Button("newlist", { onclick: {fn: newlistfn} });
function newlistfn(){
	addNewMailList(${userId});
}

</script>