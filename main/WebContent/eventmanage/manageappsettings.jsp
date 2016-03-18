<%@taglib uri="/struts-tags" prefix="s"%>
<style>
.options{
margin-left:1px !important;
}


</style>
<div class="white-box">
<s:set name="eventbeevendor" id="eventbeevendor" value="appsData.eventbeeVendor"></s:set>
<form name="boxofficeappsform"  id="boxofficeappsform" action="ManagerAppSettings!saveAppsSeetings" method="post">
<s:hidden name="eid"></s:hidden>
<s:hidden name="ticketstoshow" id="ticketstoshow"></s:hidden> 
<s:hidden name="ticketsnottoshow" id="ticketsnottoshow"></s:hidden>

<div class="row" id="paymentStatusMsg" style="display: none;"></div>
		<br />

<div class="row">
<div class="col-md-6">
<s:text name="ps.page.header"/>
</div>
<div class="col-md-6 pull-right">

<a href="https://play.google.com/store/apps/details?id=com.eventbee.eventbeemanager" target="_blank" style="float: right;">
<img src="/main/images/googleplayicon.png" width="130" style="margin-left:-35px"></img></a>
</div>

</div>

<div class="row">
			<div id="cccheckblock" class="col-md-5">
			
			<label>	
			<input type="checkbox" class="chkbox" checked="checked" name="appsData.eventbeeVendor" id="creditcardid" 
			class="creditcardicheck" disabled>
			<span class="checkbox-space" style="cursor: pointer; " id="ccbold">   
			<s:text name="ps.payment.cc.processing.lbl"/></span> </label>  
			<span id="ccpindb"></span>    
			
			</div>   
			</div>
			

	<div class="background-options options">
				<div class="row bottom-gap">
				<div class="col-md-3">
						<select id="ccdropdown" onchange="changecc();" class="form-control" name="appsData.eventbeeVendor">
						<option value="paypal_pro"> <s:text name="ps.eventbee.lbl"/> </option>
						<option value="authorize.net"> <s:text name="ps.authorize.net.lbl"/> </option>
						<option value="braintree_manager"> <s:text name="ps.braintree.lbl"/> </option>
						<option value="stripe"> <s:text name="ps.stripe.lbl"/>  </option>
						</select>
				</div>   
			    </div>
			
			
			<div id="authorize" style="<s:if test="%{#eventbeevendor == 'authorize.net'}">display:block</s:if><s:else>display:none</s:else>">
				
				<div class="row bottom-gap">	
					<div class="col-md-3 sm-font">
						<s:text name="ps.authorize.net.api.login.id.lbl"/>
					</div>
					
					<div class="col-md-4">
						<s:textfield  cssClass="form-control" id="apiloginid"  name="appsData.apiLoginId"></s:textfield>
					</div>
					</div>
					
					
					<div class="row">
					
					<div class="col-md-3 sm-font">
						<s:text name="ps.api.transaction.key"/>
					</div>
					<div class="col-md-4">
						<s:textfield  cssClass="form-control" id="apitransactionid" name="appsData.apiTransactionId"></s:textfield>
					</div>
					</div>
				
			</div>
			
			
				<div id="stripe" style="<s:if test="%{#eventbeevendor == 'stripe'}">display:block</s:if><s:else>display:none</s:else>" >
				<div style="padding-top:10px;" class="row bottom-gap">
					<div class="col-md-3 sm-font">
						<s:text name="ps.stripe.merchant.api.key"/>
					</div>
					<div class="col-md-4">
						<s:textfield name="appsData.stripekey" cssClass="form-control" id="stripekey" ></s:textfield>
					</div>
				</div>
				
				<div class="row">
				<div class="col-md-12 xsm-font">
						<a style="cursor: pointer;" onclick="getStripeInst();"><s:text name="ps.stripe.help.msg1"/></a> <s:text name="ps.stripe.help.msg2"/>
					</div>
				</div>
				</div>
				
				
				
			
						<div id="braintree" style="<s:if test="%{#eventbeevendor == 'braintree_manager'}">display:block</s:if><s:else>display:none</s:else>">
			
					<div class="row bottom-gap">
					
					<div class="col-md-3 sm-font">
						<s:text name="ps.braintree.merchant.id.lbl"/>
					</div>
					<div class="col-md-4">
						<s:textfield cssClass="form-control" id="braintreekey"  name="appsData.braintreekey"></s:textfield>
					</div> 
					</div>
					
					<div class="row bottom-gap">
					<div class="col-md-3 sm-font">
						<s:text name="ps.public.key"/>
					</div>
					<div class="col-md-4">
						<s:textfield cssClass="form-control" id="braintreepublickey" size="35" name="appsData.brainPubKey"></s:textfield>
					</div>
					</div>
					
					
					
					<div class="row bottom-gap">
					
					<div class="col-md-3">
						<h4> <small class="titleColor"><s:text name="ps.private.key"/></small> </h4>
					</div>
					<div class="col-md-4">
						<s:textfield type="text" cssClass="form-control" id="braintreeprivatekey"  size="35" name="appsData.brainPriKey"></s:textfield>
					</div>
					</div>
					
					<div class="row">
						<div style="clear: both;" class="col-md-12 xsm-font">
						<a style="cursor: pointer; " onclick="brainTree();"><s:text name="ps.braintree.help.msg1"/></a> <s:text name="ps.braintree.help.msg2"/></div>
					</div>
				</div>
			</div>	
			
	
			
		<div style="clear:both;"></div>
				<hr>		
		
		
<div class="row bottom-gap">
<div class="col-md-12 col-sm-12 col-xs-12">
<s:text name="mas.profile.coll.lbl"/>  
</div>
</div>

<div class="row">
<div class="col-md-12 col-sm-12 col-xs-12">
<label>
<input type="checkbox" class="chkbox" name="appsData.collectbuyer" 
					<s:if test='%{appsData.collectbuyer=="y"}'>checked</s:if>></input>
					<span class="checkbox-space"> <s:text name="mas.buyer.prof.coll.lbl"/></span>
				</label>
				</div></div>
<div class="row">
<div class="col-md-12 col-sm-12 col-xs-12">				
				
			<s:if test='%{appsData.showAttendee=="y"}'>
			<label>
			<input class="chkbox" type="checkbox" name="appsData.collectattendee" 
					<s:if test='%{appsData.collectattendee=="y"}'>checked</s:if>></input>
					<span class="checkbox-space"><s:text name="mas.attende.profile.coll.lbl"/></span>
				</label>
			</s:if>
			</div></div>



			
<div style="clear:both;"></div>
				<hr>


<div class="row bottom-gap">
<div class="col-md-12 col-sm-12 col-xs-12">
<s:text name="mas.select.ticket.to.show"/>  
</div>
</div>

		<%-- <div class="row ">

			<div class="col-md-4 col-sm-5 col-xs-5">
				 <s:select name="allTickets" list="allTickets" listKey="key" listValue="value" 
				multiple="true" size="10" theme="simple"/>
			</div>
			<div class="col-md-1 col-sm-1 col-xs-1 text-center" style="  margin: 65px -25px 0px -25px;">
				<i class="fa fa-arrow-right" style="cursor:pointer" onclick="moveRight();"></i><br>
				<i class="fa fa-arrow-left" style="cursor:pointer" onclick="moveLeft();"></i>
			</div>
			<div class="col-md-4 col-sm-5 col-xs-5 ">
				 <s:select name="selTickets" id="seltickets" list="selTickets" listKey="key" listValue="value" 
			multiple="true" size="10" theme="simple" /> 
			</div>
			<div class="col-md-1 col-sm-1 col-xs-1 text-left" style=" margin: 65px 0px 0px -15px;">
				<div id="up2"><i class="fa fa-arrow-up" style="cursor:pointer" title="Move Up" onclick="javascript:ManageQuestion_mvUp(document.getElementById('selattribs'))"></i></div>
				<div id="down2"><i class="fa fa-arrow-down" style="cursor:pointer" title="Move Down" onclick="ManageQuestion_mvDn(document.getElementById('selattribs'))"></i></div>
			</div>
		</div> --%>
		
		
		<div class="  row">
			<div class="col-md-5 col-sm-5 col-xs-5">
				<%-- <s:select name="allTickets" list="allTickets" listKey="key" listValue="value"  cssClass="form-control"
				multiple="true" size="10" theme="simple" id="attribs"/> --%>
				
				<s:select name="allTickets" list="allTickets" listKey="key" listValue="value" 
				multiple="true" size="10"  theme="simple" id="allTickets" cssClass="form-control"/> 
				
			</div>
			<div class="col-md-1 col-sm-1 col-xs-1 text-center" style="  margin: 65px -25px 0px -25px;">
				<i class="fa fa-arrow-right" style="cursor:pointer" onclick="moveRight();"></i><br>
				<i class="fa fa-arrow-left" style="cursor:pointer" onclick="moveLeft();"></i>
			</div>
			<div class="col-md-5 col-sm-5 col-xs-5">
				<%-- <s:select name="selTickets" id="seltickets" list="selTickets"  listKey="key" listValue="value" cssClass="form-control"
      			multiple="true" size="10"  theme="simple" id="selattribs" /> --%>
      			
      			<s:select name="selTickets" id="seltickets" list="selTickets" listKey="key" listValue="value" 
			multiple="true" size="10" theme="simple" cssClass="form-control" /> 
			</div>
			<!-- <div class="col-md-1 col-sm-1 col-xs-1 text-left" style=" margin: 65px 0px 0px -15px;">
				<div id="up2"><i class="fa fa-arrow-up" style="cursor:pointer" title="Move Up" onclick="javascript:ManageQuestion_mvUp(document.getElementById('selattribs'))"></i></div>
				<div id="down2"><i class="fa fa-arrow-down" style="cursor:pointer" title="Move Down" onclick="ManageQuestion_mvDn(document.getElementById('selattribs'))"></i></div>
			</div> -->
		</div>
 
 




</form>
<br/>

<div class="row">
<div class="col-md-12 col-sm-12 col-xs-12">
<div align="center" ><button  id="paymentsettings" class="btn btn-primary"><s:text name="global.save.btn.lbl"/></button></div>
</div>
</div>



</div>

<script>

$(document).ready(function(){
	$('.chkbox').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});

   
  $('#paymentsettings').click(function(){
	  updatePaymentSettings();
	  });

	 
});

var eventbeevendor='${eventbeevendor}';
$('#ccdropdown').val(eventbeevendor);

var ccDivs={"authorize.net":"authorize","stripe":"stripe","braintree_manager":"braintree"};
function changecc(){
	var ccVal=$('#ccdropdown').val();
	$.each(ccDivs,function(key,val){
         $('#'+val).hide();
		});

	if(ccDivs[ccVal]!=undefined)
		$('#'+ccDivs[ccVal]).slideDown();
	
}


function updatePaymentSettings(){
	$('#paymentStatusMsg').hide();
	if(!getTickets())return;
	getTickets();
    
    $.ajax({
    	url : 'ManagerAppSettings!saveAppsSeetings',
	   	type : 'post',  
	   	data:$('#boxofficeappsform').serialize(),
	   	success:function(result){

		   	if(result.indexOf("errorMessage")>-1){
		   	$('#paymentStatusMsg').show();
           	 $('#paymentStatusMsg').html(result);
           	 $('#paymentStatusMsg').addClass('alert alert-danger');			            	 
           	 $('html, body').animate({ scrollTop: $("#paymentStatusMsg").height()}, 1000);
			   	}else{
			   	  $('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
			   	   notification(props.ps_payment_updated_msg_succ,"success");
			   	   removeNotificationHeader();
				   	} 

		   	
		   	}

        });

	
}



function getTickets()
{ 
	   seltickts=[];alltickts=[];
	   var flag = true;
	    var x = document.getElementById('seltickets');	  
	    for (i = 0; i < x.length; i++) {	          
	    	seltickts.push(x.options[i].value);
	       }
	   var y = document.getElementById('allTickets');	   
	    for (j = 0; j < y.length; j++) {	         
	    	alltickts.push(y.options[j].value);
	       } 
	   document.getElementById('ticketstoshow').value=seltickts.toString();
	   document.getElementById('ticketsnottoshow').value=alltickts.toString();
	 
	   if(seltickts.length==0){
		   if(alltickts.length>0)	{		   
			   bootbox.alert(props.dt_select_one_tkt);
				  flag = false;
				}
				else
				flag = true;
	   }
	   
	   if(document.getElementById("seltickets"))
        document.getElementById("seltickets").selectedIndex = -1;
        if(document.getElementById("allTickets"))
        document.getElementById("allTickets").selectedIndex = -1;
	   
	   return flag; 
}





function moveLeft(){
	moveOption(document.boxofficeappsform.selTickets,document.boxofficeappsform.allTickets);
	
}

function moveRight(){
	moveOption(document.boxofficeappsform.allTickets,document.boxofficeappsform.selTickets);
}



function addOption(theSel, theText, theValue){
  var newOpt = new Option(theText, theValue);
  var selLength = theSel.length;
  theSel.options[selLength] = newOpt;
}

function deleteOption(theSel, theIndex){ 
  var selLength = theSel.length;
  if(selLength>0){
    theSel.options[theIndex] = null;
  }
}

function moveOption(theSelFrom, theSelTo)
{
  
  var selLength = theSelFrom.length;
  var selectedText = new Array();
  var selectedValues = new Array();
  var selectedCount = 0;
  
  var i;
  
  
  for(i=selLength-1; i>=0; i--)
  {
    if(theSelFrom.options[i].selected)
    {
      selectedText[selectedCount] = theSelFrom.options[i].text;
      selectedValues[selectedCount] = theSelFrom.options[i].value;
      deleteOption(theSelFrom, i);
      selectedCount++;
    }
  }
  
  for(i=selectedCount-1; i>=0; i--){
    addOption(theSelTo, selectedText[i], selectedValues[i]);
  }
}

function getStripeInst(){
	parent.loadingPopup();
	var eid='${eid}';
	var url="PaymentSettings!getStripeScreen?eid="+eid;     
     $('#myModal').on('show.bs.modal', function () {				  	
   	  $('#myModal .modal-title').html('<h3><span style="padding-left:10px">'+props.ps_stripe_ac_settings_popup_title+'</span></h3>');	
   	  $('iframe#popup').attr("src",url);
   	      });		        	     
   	   $('#myModal').modal('show');
   	   $('#myModal').on('hide.bs.modal', function () { 
   	        $('iframe#popup').attr("src",'');			        
   	        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="modalOnLoad()" frameborder="0"></iframe>');
   });
}
	
function brainTree(){
	parent.loadingPopup();
	var eid='${eid}';
	var url ="PaymentSettings!getBrinbtreeScreen?eid="+eid;
	
$('#myModal').on('show.bs.modal', function () {				  	
  $('#myModal .modal-title').html('<h3><span style="padding-left:10px">'+props.ps_braintree_ac_settings_popup_title+'</span></h3>');	
  $('iframe#popup').attr("src",url);  
      });		          
   $('#myModal').modal('show');
   $('#myModal').on('hide.bs.modal', function () { 
        $('iframe#popup').attr("src",'');			        
        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="modalOnLoad()" frameborder="0"></iframe>');
});
}


</script>



<div class="box-top-gap">
	
<%-- <s:form name="ScanIDsform" action="ScanIDs!createScanId" id="ScanIDsform"> --%>
<s:hidden name="eid"></s:hidden>
<div id="statusMsg"></div>
<div class="section-main-header"><s:text name="sid.section.header" />&nbsp;<span class="status"><s:text name="check.in.app.ios"/></span></div>
<div class="row sticky-out-button pull-right  box-top-gap">
	<div class="col-md-12"> 
		 <div class="pull-right">
			<button class="btn btn-primary" name="scanid"  id="scanids" type="button"><s:text name="sid.create.scanid.btn.lbl"/></button>   
		 </div> 
	 </div> 
</div>



<div class="white-box">

<div id="scandatadiv" class="bottom-gap" ></div>	
<div id="scandata_br" style="height:33px;"></div>
<div id="loading" class="box-top-gap" style="display:none;"><center><img src="../images/ajax-loader.gif"/></center></div>

<div id="loadedData"></div>

</div>                         

<%-- </s:form> --%>
</div>
<script>
var eid='${eid}';
</script>
<script type="text/javascript" src="ScanIDs!populateScanIdsList?eid=${eid}"></script>
<script type="text/javascript" src="/main/js/scanids.js"></script>
<script>
var scanIDjson = data;
</script>



<%-- <div class="taskbox">
<span class="taskheader">Manager App Settings</span><br/>

<div id="statusmsg" style="display:none"></div>

<form name="boxofficeappsform"  id="boxofficeappsform" action="ManagerAppSettings!saveAppsSeetings" method="post">
<s:hidden name="eid"></s:hidden>
<s:hidden name="ticketstoshow" id="ticketstoshow"></s:hidden> 
<s:hidden name="ticketsnottoshow" id="ticketsnottoshow"></s:hidden>
<table  border="0" cellpadding="0" cellspacing="0" width="100%" style="padding-left:10px;">
<tr><td colspan="2">Payment Settings&nbsp;
<td style="float:right">
<a href="https://play.google.com/store/apps/details?id=com.eventbee.eventbeemanager" target="_blank">
<img src="/main/images/googleplayicon.png" width="130" style="margin-left:-35px"></img></a>
</td>
</tr>
<tr id="eventbeedatablock" ><td><table cellspacing="0" cellpadding="0">
<tr>
<td width="13" id="space"></td>
<td id="currebee"><input type="radio" name="appsData.eventbeeVendor"   onclick="authorizeBlock('eventbee');" id="eventbeevendor" value="paypal_pro" <s:if test="%{#eventbeevendor == 'paypal_pro'}">checked='checked'</s:if>/>Eventbee</td>
<td width="13"></td>
<td><input type="radio" name="appsData.eventbeeVendor"  id="authorizevendor" onclick="authorizeBlock('authorize');" value="authorize.net" <s:if test="%{#eventbeevendor == 'authorize.net'}">checked='checked'</s:if>/>Authorize.Net</td>
<td width="13"></td>
<td><input type="radio" name="appsData.eventbeeVendor"  id="braintreevendor" onclick="authorizeBlock('braintree');" value="braintree_manager" <s:if test="%{#eventbeevendor == 'braintree_manager' }">checked='checked'</s:if>/>Braintree</td>
<td width="13"></td>
<td><input type="radio" name="appsData.eventbeeVendor"  id="stripevendor" onclick="authorizeBlock('stripe');" value="stripe" <s:if test="%{#eventbeevendor == 'stripe' }">checked='checked'</s:if>/>Stripe</td>


</tr>
</table>
</td></tr>



<tr id="authorize" style="<s:if test="%{#eventbeevendor == 'authorize.net'}">display:block</s:if><s:else>display:none</s:else>"><td>
<table cellspacing="0" cellpadding="0">
<tr>
<td width='50'></td>
<td class='taskcontent'>
<div class="infoheader">Your Authorize.Net API Login ID and API Transaction Key</div>
<div class="infocontent">
<table>					
<tr>
<td class='smallfont'>API Login ID<span style="padding-left:50px;"><s:textfield name="appsData.apiLoginId" id="apiloginid" theme="simple" size="35"></s:textfield></span></td>				
</tr>
<tr>
<td class='smallfont'>API Transaction Key&nbsp;<s:textfield name="appsData.apiTransactionId" id="apitransactionid" size="35" theme="simple"></s:textfield></td>				
</tr>
<tr height='2'><td></td></tr>
<!--tr>
<td width='440'><script>setHelpContent("paymentsettings_authorize_net_helpcontent")</script></td>
</tr-->
</table>
</div>
</td></tr></table>
</td></tr>


<tr id="braintree" style="<s:if test="%{#eventbeevendor == 'braintree_manager'}">display:block</s:if><s:else>display:none</s:else>"><td>
<table cellspacing="0" cellpadding="0">
<tr>
<td width='50'></td>
<td class='taskcontent'>
<div class="infoheader">Your Braintree Merchant ID</div>
<div class="infocontent">
<table >					
<tr>
<td class='smallfont'>Merchant ID<span style="padding-left:50px;"><s:textfield name="appsData.braintreekey" id="braintreekey" theme="simple" size="45"></s:textfield></span></td>				
</tr>
<tr>
<td class='smallfont'>Public Key<span style="padding-left:60px;"><s:textfield name="appsData.brainPubKey" id="braintreepublickey" theme="simple" size="45"></s:textfield></span></td>				
</tr>
<tr>
<td class='smallfont'>Private Key<span style="padding-left:56px;"><s:textfield name="appsData.brainPriKey" id="braintreeprivatekey" theme="simple" size="45"></s:textfield></span></td>				
</tr>
 <tr><td class="smallestfont"><a onclick="brainTree();" style="cursor: pointer;">Click here for instructions</a> to get keys for this payment method.
</td></tr> 
</table>
</div>
</td></tr></table>

</td></tr>

<tr id="stripe" style="<s:if test="%{#eventbeevendor == 'stripe'}">display:block</s:if><s:else>display:none</s:else>"><td>
<table cellspacing="0" cellpadding="0">
<tr>
<td width='50'></td>
<td class='taskcontent'>
<div class="infoheader">Your Stripe Merchant API Key</div>
<div class="infocontent">
<table >					
<tr>
<td class='smallfont'>Merchant API Secret Key<span style="padding-left:50px;"><s:textfield name="appsData.stripekey" id="stripekey" theme="simple" size="40"></s:textfield></span></td>				
</tr>
 <tr><td class="smallestfont"><a onclick="getStripeInst();" style="cursor: pointer;">Click here for instructions</a> to get Stripe Merchant API Secret Key.
</td></tr> 
</table>
</div>
</td></tr></table>
</td></tr>

<tr><td colspan="2">Profile Collection &nbsp;</td></tr>

 <tr style="padding-right: 10px"><td>
			<table cellspacing="0" cellpadding="0">
			
			<tr>
			<td width='13'></td>
				<td width='145'>
				<input type="checkbox" name="appsData.collectbuyer" 
					<s:if test='%{appsData.collectbuyer=="y"}'>checked</s:if>></input>Collect buyer profile
				</td>
			</tr>
			<s:if test='%{appsData.showAttendee=="y"}'>
			<tr>
			<td width='13'></td>
				<td width='165'><input type="checkbox" name="appsData.collectattendee" 
					<s:if test='%{appsData.collectattendee=="y"}'>checked</s:if>></input>Collect attendee profile
				</td>
			</tr>
			</s:if>
			
			</table>

</td></tr>


<tr><td colspan="2">Select Tickets To Show In Event Manager App &nbsp;</td></tr>
<tr style="padding-right: 10px">
<td>
 <table>
	<tr>
	 <td><b>All Tickets</b><br>
	   <s:select name="allTickets" list="allTickets" listKey="key" listValue="value" 
				multiple="true" size="10" style="width:300px;" theme="simple"/> 
	 </td>
	<td align="center" valign="middle">
	 <input type="button" value="&gt;" style="width: 35px"
			onclick="moveOptions(this.form.allTickets, this.form.selTickets);" /><br/>
	 <input type="button" value="&lt;" style="width: 35px"
			onclick="moveOptions(this.form.selTickets, this.form.allTickets);" />
	</td>
	<td ><b>Selected Tickets</b><br>
	  <s:select name="selTickets" id="seltickets" list="selTickets" listKey="key" listValue="value" 
			multiple="true" size="10" style="width:300px;" theme="simple" /> 
	</td>
	<!-- <td><img src="../images/up.gif" title="Move Up" onclick="javascript:ManageQuestion_mvUpOption(document.getElementById('selevents'))"><br/><img src="../images/dn.gif" title="Move Down" onclick="ManageQuestion_mvDnOption(document.getElementById('selevents'))"></td> -->
   </tr>
  </table>
  </td>
</tr>

<!-- <tr><td></td></tr> -->
</table>
</form>
</div>
<br/>

<div align="center"><input type="button" value="Submit" id="paymentsettings"></div>

<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<div id="iframepopupdialog"><div id="hd"></div><div id="bd"></div></div>





<script>
var allTicketsId=[];
var seltickts=[];
var alltickts=[];
var payment_btn = new YAHOO.widget.Button("paymentsettings",{onclick:{fn:updatePaymentSettings}});
function authorizeBlock(val){
	  if(val=='eventbee'){
		document.getElementById('authorize').style.display='none';
		document.getElementById('braintree').style.display='none';
		document.getElementById('stripe').style.display='none';
	}else if(val=='authorize'){
		document.getElementById('braintree').style.display='none';
		document.getElementById('stripe').style.display='none';
		document.getElementById('authorize').style.display='block';
	}else if(val=='braintree'){
		document.getElementById('authorize').style.display='none';
		document.getElementById('stripe').style.display='none';
		document.getElementById('braintree').style.display='block';
	}else{
		document.getElementById('authorize').style.display='none';
		document.getElementById('braintree').style.display='none';
		document.getElementById('stripe').style.display='block';

	}
	}

function updatePaymentSettings(){
	document.getElementById('statusmsg').style.display='none';
	if(!getTickets())return;
	getTickets();
	YAHOO.ebee.popup.wait.show();
	$('boxofficeappsform').request({
		   onComplete: function(o){
			   YAHOO.ebee.popup.wait.hide();
			   var result=o.responseText;
			   if(!isValidActionMessage(result)){
		  	  	return;
		    	}
			   	$('statusmsg').scrollTo();
		    	document.getElementById('statusmsg').style.display='block';
		    	document.getElementById('statusmsg').innerHTML=result;
		}
	});
}


function brainTree(){
	var url='/main/help/braintreepaymentapprovalhelp.html';
	showYUIinIframe("Braintree Account Settings",url,775,500);
	}

	function getStripeInst(){
	     var url='/main/help/stripesecretkeyhelp.html';
	     showYUIinIframe("Steps For Stripe Merchant API Secret Key ",url,775,500);
	}

	
	
	function moveOption(theSelFrom, theSelTo)
	{
	  var selLength = theSelFrom.length;
	  var selectedText = new Array();
	  var selectedValues = new Array();
	  var selectedCount = 0;	 
	  var i;
	  
	  // Find the selected Options in reverse order
	  // and delete them from the 'from' Select.
	  for(i=selLength-1; i>=0; i--)
	  {
	    if(theSelFrom.options[i].selected)
	    {
	      
	      selectedText[selectedCount] = theSelFrom.options[i].text;
	      selectedValues[selectedCount] = theSelFrom.options[i].value;
	      //deleteOption(theSelFrom, i);
	      selectedCount++;
	    }
	  }
	  
	  if(NS4) history.go(0);
	}
	
	
   function getTickets()
   { 
	   seltickts=[];alltickts=[];
	   var flag = true;
	    var x = document.getElementById('seltickets');	  
	    for (i = 0; i < x.length; i++) {	          
	    	seltickts.push(x.options[i].value);
	       }
	   var y = document.getElementById('allTickets');	   
	    for (j = 0; j < y.length; j++) {	         
	    	alltickts.push(y.options[j].value);
	       } 
	   document.getElementById('ticketstoshow').value=seltickts.toString();
	   document.getElementById('ticketsnottoshow').value=alltickts.toString();
	 
	   if(seltickts.length==0){
		   if(alltickts.length>0)	{		   
		   		alert("Select Atleast One Ticket");
				  flag = false;
				}
				else
				flag = true;
	   }
	   
	   if(document.getElementById("seltickets"))
           document.getElementById("seltickets").selectedIndex = -1;
           if(document.getElementById("allTickets"))
           document.getElementById("allTickets").selectedIndex = -1;
	   
	   return flag; 
   }
	
	
</script> --%>
