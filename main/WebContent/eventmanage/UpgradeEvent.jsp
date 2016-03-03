<html xmlns="http://www.w3.org/1999/xhtml">
<head profile="http://gmpg.org/xfn/11">
<meta name="verify-v1" content="/2obWcBcvVqISVfmAFOvfTgJIfdxFfmMOe+TE8pbDJg=" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META Http-Equiv="Cache-Control" Content="no-cache">
<META Http-Equiv="Pragma" Content="no-cache">
<META Http-Equiv="Expires" Content="0">
<%@taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript" src="../js/popup.js"></script>
<script type="text/javascript" src="/main/js/payments/creditpopup.js"></script>
<link rel="stylesheet" type="text/css" href="/main/css/creditpopupcs.css" />
<script type='text/javascript' language='JavaScript' src='http://www.eventbee.com/home/js/widget/eventregistration.js'></script>
<script type="text/javascript">
function getPriceData(){
	var accounttype="";	
	var listingcost='$0.00';	
	var selectedoption;
	var frm=document.getElementById("upgradeevent");
	var options=frm.premiumlisttype;
	if(options.length==undefined)
	selectedoption=frm.premiumlisttype.value;
	else
	{
	for(var j=0;j<options.length;j++){
				if(options[j].checked==true){
					selectedoption=options[j].value;
					break;
			}
		}
	}
	if(selectedoption=='premium'){
		listingcost=document.getElementById("premiumprice").value;		
		if(listingcost=='Free') listingcost='$0.00';
	}
	else if(selectedoption=='featured'){
		listingcost=document.getElementById("featuredprice").value;		
		if(listingcost=='Free') listingcost='$0.00';
	}
	else listingcost=document.getElementById("customprice").value;
	/*else if(selectedoption=='free')
		listingcost='Free';
	if(listingcost!='Free'){		
		listingcost=listingcost;
	}
	else listingcost='$0.00';*/
	
	document.getElementById("showAmountInfo").innerHTML="Total Charges: "+listingcost;
	document.getElementById("totalprice").value=listingcost.substr(1,listingcost.length);
	document.getElementById("selectedoption").value=selectedoption;
}

var popupWin;
var modelwin;
var val='';
var eventid='${eid}';
function StatusResponse(response){
	data=response.responseText;	
		var statusJson=eval('('+data+')');
		var status=statusJson.status;	
		if(status=='Success'){
			//window.location.href='';
		document.getElementById("statusmsg").style.display="block";	
		//document.upgradeevent.elements["premiumlisttype"][0].focus();
		document.getElementById("statusmsg").focus();		
		//document.getElementById("statusmsg").innerHTML="Updated Successfully";
		getStatusMsg();
		setTimeout("window.location.reload(true)",1000);
		}
}
function reloadPage(){
	window.location.reload(true);
}
function updatePremiumLevel(){
	var selectedoption;
	var frm=document.getElementById("upgradeevent");
	var options=frm.premiumlisttype;
	for(var j=0;j<options.length;j++){
				if(options[j].checked==true){
					selectedoption=options[j].value;
					break;
			}
		}		
		var eid=${eid};	
		new Ajax.Request('UpgradeEvent!updatePremium', {
			method: 'get',
			parameters:{premiumType:selectedoption,eid:eid},
			onSuccess: successfunction,
			onFailure:  failureJsonResponse
			});
	
}
function successfunction(){	
	getStatusMsg();
	setTimeout("window.location.reload(true)",1000);
}
function getStatusMsg(){
		new Ajax.Request('ajaxstatusmsg.jsp', {
		method: 'get',
		parameters:{msgType:'upgradeevent'},
		onSuccess: showStatusMsg,
		onFailure:  failureJsonResponse
		});
}
function showStatusMsg(response) {
  document.getElementById("statusmsg").focus();		
		document.getElementById("statusmsg").innerHTML=response.responseText;
			
		YAHOO.ebee.popup.wait.show();
}
function failureJsonResponse(){
	alert("Failed");
}
function getStatus(eid,seqID){
		new Ajax.Request('checkstatus.jsp', {
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
		document.getElementById("backgroundPopup").style.display="none";
		getStatus(eventid,document.getElementById("seqid").value);
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
function getEventbeecreditcardScreen(eid,seqID){
	var totalAmount=document.getElementById("totalprice").value;	
	if(totalAmount=='0.00'){
	    document.getElementById("statusmsg").style.display="block";
	    updatePremiumLevel();	
		/*document.upgradeevent.elements["premiumlisttype"][0].focus();
		document.getElementById("statusmsg").focus();		
		document.getElementById("statusmsg").innerHTML="Updated Successfully";	*/
		//window.location.href='UpgradeEvent!updateMessage?eid='+eid;
		return;
	}
	//document.getElementById("backgroundPopup").style.display="block";
	
	//windowOpener('UpgradeEvent!getPaymentScreen?upgradeEventSeqId='+seqID+'&eid='+eid,'Upgrade','WIDTH=740,HEIGHT=500,RESIZABLE=No,SCROLLBARS=YES,TOOLBAR=NO,LEFT=150,TOP=100',seqID);
 var Msg='<span style="font-weight: normal;font-size: 12px"><b>Amount: $'+totalAmount+'</b></span><br/>NOTE: CC processing is done by Eventbee. Eventbee appears on your credit card statement.';


 var setkey= new callPaykey();
 setkey.setCallbackurl("/main/payments!updateUpgrade?refId="+eid+"&seqId="+seqID+"&pur=upgrade");
 setkey.setPurpose("Upgrade");
 setkey.setPaymode("direct");
 setkey.setMessage(Msg);
 setkey.setAmount(totalAmount);
 setkey.setCurrency("USD");
 setkey.setMerchantid("No");
 setkey.setLang("En");
 setkey.setTitle("Credit Card Payment");
 setkey.setSoftdis("NOTHING");
 setkey.setRefid(eid);
 setkey.setVendor("braintree");
 setkey.setAppendDiv('maincontent');
 setkey.setInternalref('1234');
 var paykey=setkey.getPaykey();
     if(paykey!="")
	 {setkey.ccpopup(paykey);
	 }
	 else{
	 alert("unable to process Not valid paykey");
	 }
	
	
}


 

function onsucesscloseupgrade(){
closeEBpopup();
getStatus(eventid,document.getElementById("seqid").value);
}


function insertUpgradeData(eid){	
	var totalAmount=document.getElementById("totalprice").value;
	var selectedoption=document.getElementById("selectedoption").value;
	var seqId=document.getElementById("seqid").value;
	var url="UpgradeEvent!insertUpgradeData?eid="+eid;
	var dynatimestamp=new Date();
	url+='&dynatimestamp='+dynatimestamp.getTime();
	//YAHOO.displayinfo.wait.setHeader("Updating...");
	//YAHOO.displayinfo.wait.show();
	new Ajax.Request(url, {
		  method: 'post',
		  parameters: {totalAmount:totalAmount,selectedoption:selectedoption,upgradeEventSeqId:seqId},
		  onSuccess: function(transport) {
			var result=(transport.responseText).trim();	
			
		}
	});	
}


</script>
 </head>
<div id="backgroundPopup" ></div>
<div id="statusmsg" style="display: none"></div>
<div id="status"></div>	
<br/>
<s:form  action="UpgradeEvent!success" name="upgradeevent" id="upgradeevent">
<s:hidden name="sslserveraddress" id="sslserveraddress"></s:hidden>
<s:hidden name="eid"></s:hidden>
<s:hidden id="premiumprice" name="listingOptionsData.premiumprice"></s:hidden>
<s:hidden id="featuredprice" name="listingOptionsData.featuredprice"></s:hidden>
<s:hidden id="customprice" name="listingOptionsData.customprice"></s:hidden>
<s:hidden id="totalprice" name="totalprice" value=""></s:hidden>
<s:hidden id="seqid" name="listingOptionsData.seqId" ></s:hidden>
<s:hidden id="selectedoption" name="listingOptionsData.selectedoption"  ></s:hidden>
<s:set name="premiumlisttype" value="listingOptionsData.premiumlisttype"></s:set>
<s:if test="%{#premiumlisttype == 'Custom'}"><center><b>Your listing upgraded</b></center></s:if>
<s:else>
<div style="height:3px;"></div>
<div class="taskbox">
<span class="taskheader">Listing Options</span><br/>
<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tbody>
<!-- <tr><td><input type="radio" onclick="javascript:getPriceData();insertUpgradeData(${eid})" <s:if test="%{#premiumlisttype == 'Free'}">checked='checked'</s:if> value="free" id="premiumlisttype" name="listingOptionsData.premiumlisttype"/> Free Listing</td></tr>
<tr><td>
<table width="100%">
	<tbody><tr><td height="5"/></tr>
	<tr><td width="10%"/>
	<td width="3%" valign="top" align="left"><b>::</b></td>
	<td>Get your event displayed on Eventbee for Free! 
	</td></tr>
	<tr><td height="5"/></tr>
	
</tbody></table>
</td></tr> -->

<s:if test="%{#premiumlisttype != 'Premium' && #premiumlisttype != 'Featured'}">
<tr><td>
<table width="100%"><tbody><tr><td width="25%">
<input type="radio" onclick="javascript:getPriceData();insertUpgradeData(${eid})" value="premium" id="premiumlisttype" name="listingOptionsData.premiumlisttype" <s:if test="%{#premiumlisttype == 'Free'}">checked='checked'</s:if>/> Premium Listing -</td>
<td width="50" align="left" id="listingInfo"><s:text name="listingOptionsData.premiumprice"></s:text>
</td>

<td align="left"></td>
</tr>
</tbody></table></td></tr>
<tr><td>
<table width="100%">

	<tbody><tr><td height="5"/></tr>
	
	<tr><td width="10%"/>
	<td width="3%" valign="top" align="left"><b>::</b></td><td>Get your event displayed on the  <a href="javascript:popupwindow('http://www.eventbee.com/home/links/pevents.html','PremiumEvents','600','400')">Events, Classes tab under Premium
Events section</a>
	</td></tr>

	<tr><td height="5"/></tr>
	<tr><td width="10%"/>
	<td width="3%" valign="top" align="left"><b>::</b></td>
	<td>Your listing will be in <b>bold</b>, making it stand out from the others.
	</td></tr>
	<tr><td height="5"/></tr>
</tbody></table></td></tr>
<tr><td height="15"/></tr>
</s:if>


<s:if test="%{#premiumlisttype != 'Featured'}">
<tr><td>
	<table width="100%">
	<tbody><tr><td width="25%">
	<input type="radio" onclick="javascript:getPriceData();insertUpgradeData(${eid})" value="featured" id="premiumlisttype" name="listingOptionsData.premiumlisttype" <s:if test="%{#premiumlisttype == 'Premium'}">checked='checked'</s:if>/> Featured Listing -</td>
<td width="50" align="left" id="listingInfo1"><s:text name="listingOptionsData.featuredprice"></s:text></td>
	</tr>
	</tbody></table>
</td></tr>
<tr><td>
<table width="100%">
	<tbody><tr><td width="10%"/>
	</tr>
	<tr><td width="10%"/>
	<td width="3%" valign="top" align="left"><b>::</b></td><td> Get your event displayed on the <a href="javascript:popupwindow('http://www.eventbee.com/home/links/fevents.html','FeaturedEvents','600','400')">Eventbee home page under Featured Events section</a>
	</td></tr>
	<tr><td height="5"/></tr>
	<tr><td width="10%"/>
	<td width="3%" valign="top" align="left"><b>::</b></td>
	<td>Display your event on the <a href="javascript:popupwindow('http://www.eventbee.com/home/links/pevents.html','FeaturedEvents','600','400')">Events, Classes tab under Premium Events section</a>
	</td></tr>
	<tr><td height="5"/></tr>
	<tr><td width="10%"/>
	<td width="3%" valign="top" align="left"><b>::</b></td><td>Your listing will be in <b>bold</b>, making it stand out from the others.
	</td></tr>
	<tr><td height="5"/></tr>
	
	
</tbody></table></td></tr>
<tr><td height="15"/></tr>
</s:if>

<tr><td>
<table width="100%">
	<tbody><tr><td width="25%">
<input type="radio" onclick="javascript:getPriceData();insertUpgradeData(${eid})" value="custom" id="premiumlisttype" name="listingOptionsData.premiumlisttype" <s:if test="%{#premiumlisttype == 'Featured'}">checked='checked'</s:if>/> Custom Listing -</td>
<td width="50" align="left" id="listingInfo2"><s:text name="listingOptionsData.customprice"></s:text></td>
<td></td>
</tr>
</tbody></table>
</td></tr>
<tr><td>
<table width="100%" border="0">
	<tbody>
	<tr><td width="10%"/>
	<td width="3%" valign="top" align="left"><b>::</b></td><td> Avail our customization service to create branded Event Page to match with your website look and feel. <a href="javascript:popupwindow('http://picasaweb.google.com/eventbee/EventPageCustomizatin#slideshow' ,'CustomEvents','600','400')">Click here to see sample customizations we did for other customers</a>
	</td></tr>
	<tr><td height="5"/></tr>
	<tr><td width="10%"/>
	<td width="3%" valign="top" align="left"><b>::</b></td><td> Get your event displayed on the <a href="javascript:popupwindow('http://www.eventbee.com/home/links/fevents.html','FeaturedEvents','600','400')">Eventbee home page under Featured Events section</a>
	</td></tr>
	<tr><td height="5"/></tr>
	<tr><td width="10%"/>
	<td width="3%" valign="top" align="left"><b>::</b></td>
	<td>Display your event on the <a href="javascript:popupwindow('http://www.eventbee.com/home/links/pevents.html','FeaturedEvents','600','400')">Events, Classes tab under Premium Events section</a>
	</td></tr>
	<tr><td height="5"/></tr>
	<tr><td width="10%"/>
		<td width="3%" valign="top" align="left"><b>::</b></td><td>Your listing will be in <b>bold</b>, making it stand out from the others.
		</td></tr>
	<tr><td height="5"/></tr>
</tbody></table>
</td></tr>

</tbody></table>
	
	<table width="100%"><tbody><tr><td >
<div id="showAmountInfo">
Total Charges: $0.00 
 </div></td></tr>
  </tbody></table>
    
 </div>
<p/>
 <table width="100%">
  <tr><td align="center">  <input type="button" name="submit" id="upgradebtn" value="Continue" onclick="getEventbeecreditcardScreen(${eid},document.getElementById('seqid').value);"/></td></tr>
  </table>
</s:else> 
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
  <script type="text/javascript">
var btn = new YAHOO.widget.Button("upgradebtn", { onclick: { fn: upgradeThisEvent } });
function upgradeThisEvent(event, jsonObj){
	getEventbeecreditcardScreen(${eid},document.getElementById('seqid').value);
}
getPriceData();
insertUpgradeData(${eid});
</script>
 </s:form>
 
</html>