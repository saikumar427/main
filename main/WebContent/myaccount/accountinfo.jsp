<%@taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript" src="../js/states.js"></script>
<script type="text/javascript" src="../js/creditcardpayment.js"></script>

<style>
/* #backgroundPopup {
background-color: #080808;
display: none;
height: 1700px;
left: 0;
opacity: 0.5;
filter:alpha(opacity=50);
position: absolute;
top: 0;
width: 100%;
z-index: 1000;
} */

ul.errorMessage li {
display: list-item;
}
</style>
<script>
var _amount='';
var seqID='';
var amt='';
var title='';
var montitle='';
var invoicemonth='';
var evtname='';
var reporttype='';
var _rtype='';
var cssarr=new Array();
var jsonobj={"jan":"January","feb":"February","mar":"March","apr":"April","may":"May","jun":"June","jul":"July","aug":"Auguest","sep":"September","oct":"October","nov":"November","dec":"December"};

var gPopupIsShown = false;
var gTabIndexes = new Array();
var gTabbableTags = new Array("A","BUTTON","TEXTAREA","INPUT","IFRAME");
if (!document.all) {
	document.onkeypress = keyDownHandler;
}	

function disableTabIndexes() {
  if (document.all) {
		var i = 0;
		for (var j = 0; j < gTabbableTags.length; j++) {
			var tagElements = document.getElementsByTagName(gTabbableTags[j]);
			for (var k = 0 ; k < tagElements.length; k++) {
				gTabIndexes[i] = tagElements[k].tabIndex;
				tagElements[k].tabIndex = "-1";
				i++;
			}
		}
	}
}


function keyDownHandler(e) {
    if (gPopupIsShown && e.keyCode == 9)  return false;
}

 
//alert(JSON.stringify(${jsondata}));
function callpopup(){
  var  url="/main/myaccount/home!getBeeCreditsHistory?userId="+userid; 
 $.ajax({
	 url : url,
	 type : 'post',
	 success : function(t){
		 var msg = t;
		 var total_Amount = 0.00;
		 //alert(msg);
		 if(!isValidActionMessage(t))return;  
		 if(msg.indexOf('HistoryDetails')>-1){  
	        	var jdata=eval('('+msg+')');  
	        	if(jdata.creditsdata.totalcredits==undefined)jdata.creditsdata.totalcredits=0;
	        	if(jdata.creditsdata.usedcredits==undefined)jdata.creditsdata.usedcredits=0;
	        	if(jdata.creditsdata.availablecredits==undefined)jdata.creditsdata.availablecredits=0;	        	
	        	/* var  content = "<table class='table'><tr><td>Total:&nbsp;B$"+jdata.creditsdata.totalcredits+"</td>"+
	                    		"<td>Used:&nbsp;B$"+jdata.creditsdata.usedcredits+"</td>"+
	                    		"<td>Available:&nbsp;B$"+jdata.creditsdata.availablecredits+"</td></tr></table>"; */  
	                    		var content = "<div class='row'><div class='col-sm-4'>"+props.bcredits_total_lbl+":&nbsp;B$"+jdata.creditsdata.totalcredits+"</div>"+
	                            "<div class='col-sm-4'>"+props.bcredits_used_lbl+":&nbsp;B$"+jdata.creditsdata.usedcredits+"</div>"+
	             				"<div class='col-sm-4'>"+props.bcredits_available_lbl+":&nbsp;B$"+jdata.creditsdata.availablecredits+"</div></div>";
	 			content+='<table class="table table-hover" id="Bcredits"><thead><tr><th>'+props.bcredits_event_name_lbl+'</th><th><div style="width:100px">'+props.bcresits_tkt_count_lbl+'</div></th><th><div style="width:130px">'+props.bcresits_used_crds_lbl+' (B$)</div></th></tr></thead><tbody>';        		
				jQuery.each( jdata.HistoryDetails, function( inkey, valueobj ) {
					if(valueobj.Event_Name==null)valueobj.Event_Name="";
		        	if(valueobj.Ticket_Count==undefined)valueobj.Ticket_Count=0;
		        	if(valueobj['Used_Credits&nbsp;(B$)']==undefined)valueobj['Used_Credits&nbsp;(B$)']=0;
		        	
		        	total_Amount = numberWithCommas(valueobj['Used_Credits&nbsp;(B$)']);
				content+='<tr><td>'+valueobj.Event_Name+'</td><td>'+valueobj.Ticket_Count+'</td><td>'+total_Amount+'</td></tr>';				
	        	});        		
	        	content+='</tbody></table><br/><br/>';        		
			getBeeCreditsData(content);		
	       }else
	          //alert("No History Available");
			 {
	    	   bootbox.confirm(props.bcredits_no_history_lbl, function (result) {		 
	    		          	
		        });
			 }
	 },
	 error : function(t){
		 alert("Failure");
	 }
	 
 });
}
var _userId='',_seqId='',saddress='';
function payPalFunc(month,amount){
	$('#showMenu').hide();
	document.getElementById('purpose').value="invoice_paypal";
    document.getElementById('invoicemnth').value=month;
    document.getElementById('amount').value=amount;
    document.getElementById('userid').value=userid;
    //document.getElementById("backgroundPopup").style.display="block";
    
    $.ajax({  	  
  	   url: 'home!insertPaypalData',
  	   type: "POST",
  	   data: $("#paypaldata").serialize(), 
  	   async : false,
    	 success: function(t){ 	 
    		 
			if(!isValidActionMessage(t))return; 
			var json=eval('('+t+')');
			 _userId=json.mgrid;
			 _seqId=json.seqid;
			 saddress=json.serveraddress;
			},failure:function(t){
				  // alert("fail"); 	   
	     }
	});
	
    if(document.location.href.indexOf('www.')>-1){}else
    	saddress=saddress.replace('www.','');
	windowOpener(saddress+'/main/myaccount/home!getPaypalPayment?userId='+_userId+'&seqId='+_seqId,'payment_'+_seqId,'WIDTH=975,HEIGHT=600,RESIZABLE=No,SCROLLBARS=YES,TOOLBAR=NO,LEFT=150,TOP=100');  
	gPopupIsShown=true;
	disableTabIndexes();
}

 function paynowFunc(data,amt){ 
	 $('#showMenu').hide();
 invoicemonth=data;
 getPaymentScreen('payinvoice_'+amt);
}


var popupWin="";
var modelwin;
var val='';

function windowOpener(url, name, args){
	val='';
	popupWin="";
	if (typeof(popupWin) != "object"){
		popupWin = window.open(url,name,args);
		if (popupWin && popupWin.top){}
		else{
			alert(props.bcredits_popup_blocker_msg);
			//if(document.getElementById("backgroundPopup"))
				//document.getElementById("backgroundPopup").style.display="none";
			clickcount=0;
			return;
		} 
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

function closeIt(){
	if (!popupWin.closed){
		setTimeout("closeIt()",1)//adjust timing
		try{}
		catch (err){
		}
	}
	else{
		if(document.getElementById("backgroundPopup"))
		//document.getElementById("backgroundPopup").style.display="none";
		getPaypalStatus();
		}
	}
function getPaypalStatus(){
	 url="/main/myaccount/home!checkPaypalStatus?userId="+_userId+"&seqId="+_seqId;	  
	       $.ajax({
	    	   url : url,
	    	   type : 'post',
	    	   success : function(t){
	    		   var msg=t;
	  	    	 if(!isValidActionMessage(t))return;
	  	          document.getElementById("invoicemessage").innerHTML=t;
	  	          if(msg.indexOf('errorMessage')>-1){}
	  	          else if(msg.indexOf('Invoice Payment successfully completed')>-1)
	  	        	window.location.reload();
	    	   },error : function(t){
		    	   alert("fail");
		       }
	       
	       });
	
}

 function reportexport(exporttype){
	var formele=document.invoice;	
	
	//document.getElementById('invoice').innerHTML='';
	if(document.getElementById('sortdir')){
		$('#exportreport').remove();
		$('#sortdir').remove();
		$('#sortfield').remove();
		$('#exptyp').remove();
		$('#invoicemnth').remove();
		$('#rtype').remove();
		
	}
	
	var dir="desc";
	var input=document.createElement("input");
	input.setAttribute("type","hidden");
	input.setAttribute("name","export");
	input.setAttribute("id","exportreport");
	input.setAttribute("value","true");
	formele.appendChild(input);
	var input1=document.createElement("input");
	input1.setAttribute("type","hidden");
	input1.setAttribute("name","sortDirection");
	input1.setAttribute("id","sortdir");
	input1.setAttribute("value",dir);
	formele.appendChild(input1);
	var input2=document.createElement("input");
	input2.setAttribute("type","hidden");
	input2.setAttribute("name","sortField");
	input2.setAttribute("id","sortfield");
	input2.setAttribute("value","gfdfg");
	formele.appendChild(input2);
	var input3=document.createElement("input");
	input3.setAttribute("type","hidden");
	input3.setAttribute("name","exporttype");
	input3.setAttribute("id","exptyp");
	input3.setAttribute("value",exporttype);
	formele.appendChild(input3);
	var input4=document.createElement("input");
	input4.setAttribute("type","hidden");
	input4.setAttribute("name","invoicemnth");
	input4.setAttribute("id","invoicemnth");
	input4.setAttribute("value",montitle);
	formele.appendChild(input4);
	var input5=document.createElement("input");
	input5.setAttribute("type","hidden");
	input5.setAttribute("name","rtype");
	input5.setAttribute("id","rtype");
	input5.setAttribute("value",_rtype);
	formele.appendChild(input5);
	formele.action="home!getInDuePopupDetails";
	formele.submit();
}

function buyCredits(){
  parent.loadingPopup();	
  getPaymentScreen('beecredits');
}

function getPaymentScreen(purpose){
  var Msg='';
  var setkey= new callPaykey();
   _purpose=purpose;
    setkey.setMessage(Msg);
    if(_purpose=='beecredits'){  
   setkey.setAmount("1.00");
   setkey.setPurpose("beecreditsquota");
   setkey.setPaymode("direct");
   setkey.setTitle(props.bcredits_popup_title);  
   //setkey.setCallbackurl("/main/myaccount/home!updateBeeCredits?userId="+userid);
  }else if(_purpose.indexOf('payinvoice_')>-1){
  var value=_purpose.split("_")[1];  
  var valuePlacedFloat = parseFloat(value).toFixed(2); 
  setkey.setAmount(value);
  setkey.setPurpose("payinvoice");
  setkey.setPaymode("directvault");
  //setkey.setPaymode("direct");
  setkey.setMonth(invoicemonth);
  setkey.setTitle(props.cc_details_lbl);
  //setkey.setCallbackurl("/main/myaccount/home!updateInvoiceCredits?userId="+userid+"&invoicemnth="+invoicemonth);
  setkey.setMessage(props.cc_invoiced_amount_lbl+": $"+valuePlacedFloat);      
  }else{
	  //_cctype='authorize';
  if(_cctype=='authorize'){
  setkey.setAmount("50.00");
  setkey.setPaymode("authvault");
  }else{	  
  setkey.setAmount("1.00");
  setkey.setPaymode("vault");
  }
  setkey.setPurpose("invoice_hold_accountinfo");
  setkey.setTitle(props.cc_details_lbl);
  //setkey.setCallbackurl("/main/myaccount/home!insertManagerccData?userId="+userid);
  }  
  setkey.setCurrency("USD");
  setkey.setMerchantid("");
  setkey.setLang("En");
  setkey.setSoftdis("");
  setkey.setRefid(userid);
  setkey.setVendor("Braintree");
  setkey.setAppendDiv('maincontent');
  setkey.setInternalref('111');
  var paykey=setkey.getPaykey();
 
     if(paykey!="")     
	 {
	 setkey.ccpopup(paykey);	 
     }
	 else{
		 bootbox.confirm(props.cc_limit_exceeded_msg, function (result) {	   }); 
	 //alert("unable to process Not valid paykey");
	 }
    // parent.hidePopup();
}

/* function isValidActionMessage(messageText){

	if(messageText.indexOf('nologin')>-1||messageText.indexOf('login!authenticate')>-1){
	        alert('You need to login to perform this action');
	        window.location.href="../user/login";
	        return false;
	    }
	 else if(messageText.indexOf('Something went wrong')>-1){
	         alert('Sorry! You do not have permission to perform this action');
	         return false;
	     }
	    return true;

	} */

function onsucessclose()
    {
	 closeEBpopup();
	 _purpose='';
	 responseDatacc();
	}

function closeEBpopup(){
	//alert("sdfsd");
	$('#myModal').modal('hide');
}

</script>
<style>.fa{color:#fff;}</style>
<div id="backgroundPopup" style="display:none"></div>
<div class="col-md-12 col-sm-12 col-xs-12">
	<div id="invoicemessage"></div>
	<s:if test="%{invoicesummary['invoiceindue']>0 || invoicesummary['invoicepaid']>0}"> 
	
	<s:if test="%{amount!='0.00' && size>1}">
	<div class="row sticky-out-button pull-right hideButton">
			<div class="col-md-12"> 
				 <div class="pull-right">			
					<ul class="dropdown-menu" role="menu" aria-labelledby="eventOptions" id="showMenu">
						<li id="paynowid" onclick="paynowFunc('total','${amount}');"><a role="menuitem" href="javascript:;"><s:text name='acc.invoice.pay.all.with.cc.btn.lbl'/></a></li>
						<li id="paypalid" onclick="payPalFunc('total','${amount}');"><a role="menuitem" href="javascript:;"><s:text name='acc.invoice.pay.all.with.paypal.btn.lbl'/></a></li>
					</ul>
					<button style="margin-left: 15px" id="menu" onclick="payType()" class="btn btn-primary"  ><s:text name="acc.pay.all" />&nbsp;<i class="fa fa-angle-down"></i></button>
		
				 </div> 
			 </div> 
			<div style="clear:both"></div>
     </div>
	</s:if>
	
	
	 <s:form name="invoice" id="invoice">
			<%-- <s:if test="%{amount!='0.00' && size>1}"> --%>
				<%-- <input type="button" class="btn btn-primary" style="margin:0px;" value="<s:text name='acc.invoice.pay.all.with.cc.btn.lbl'/>" id="paynowid" 
				onclick="paynowFunc('total','${amount}');">&nbsp;&nbsp;	
				<input type="button" class="btn btn-primary" style="margin:0px;" 
				value="<s:text name='acc.invoice.pay.all.with.paypal.btn.lbl'/>" id="paypalid" onclick="payPalFunc('total','${amount}');"> --%>
			<%-- </s:if> --%>
		<div class="white-box">
		<div class="tab-bottom-gap">
		<div class="btn-group btn-toggle" data-toggle="buttons">
			<label id="tabOne" class="optiontype btn btn-default no-radius">
				<input class="datesel"  value="1" type="radio"><s:text name="acc.invoices.tab.pending.lbl"/>&nbsp;(<span class='tab1Count'>0</span>)
			</label>
			<label id="tabTwo" class="optiontype btn btn-active no-radius">
				<input class="datesel"  value="2" type="radio"><s:text name="acc.invoices.tab.paid.lbl"/>&nbsp;(<span class='tab2Count'>0</span>)
			</label>
			
		</div><div style="clear:both;"></div>
		<div class="tab1" style="display:none; margin-top:10px;">
				<table class="table table-hover" id="tabl1">
					 <thead style="display:none;">
                         <th><s:text name="acc.invoice.name.lbl"/></th>
                         <th><s:text name="acc.invoice.amount.lbl"/></th>
                     </thead>
                     <tbody>
                     <tr class='nodata'> 
                      <td><s:text name="global.no.data.lbl"/></td> 
                     </tr>
                     </tbody>
				</table>
				<br id="tabl1_br">
		</div>
		<div class="tab2" style="display:none; margin-top:10px;">
				<table class="table table-hover" id="tabl2">
					 <thead style="display:none;">
                         <th><s:text name="acc.invoice.name.lbl"/></th>
                         <th><s:text name="acc.invoice.amount.lbl"/></th>                                           
                     </thead>
                     <tbody>
                     <tr class='nodata'>   
                       <td><s:text name="global.no.data.lbl"/></td>
                     </tr>
                     </tbody>
				</table>
				<br id="tabl2_br">
		</div>
  		</div>
		</div>
		</s:form>
		<div style="margin-bottom:50px;"></div>
	</s:if> 
	
	
	
	<!-- beecredits start -->
	<div>
	<div class="section-main-header"><s:text name="acc.bee.credits.section.header"/></div>
	<div class="row sticky-out-button pull-right">
		<button class="btn btn-primary" onclick="buyCredits();"><s:text name="acc.buy.bee.credits.btn.lbl"/></button>    
	</div>
	<div class="white-box">
		<s:if test="%{totalbeecredits!='0.00'}">
		   B$${availablebeecredits} <s:text name="global.available.txt"/>&nbsp;&nbsp;<a href="javascript:;" onclick="callpopup();"><span class="sm-font"><s:text name="global.view.details.lnk.lbl"/></span></a>
		</s:if>
		<s:else><s:text name="acc.pay.service.fee.msg"/></s:else>
	</div>
	<div style="clear:both;"></div>
	</div>
	<!-- beecredits end -->
	
	<!-- Account Verification & Service Fee Payment Credit Card start -->
	<s:if test="%{accounttype==null}"> 
		<div class="section-main-header box-top-gap" ><s:text name="acc.acc.verification.section.header"/></div>
		<div class="row sticky-out-button pull-right">
			<s:if test="%{cardinfo.size()>1}">
            	<button class="btn btn-primary"  name="cardstatus" onclick="changeCard();"><s:property value="cardinfo['cardlabel']"/></button> 
            </s:if><s:else>     
            	<button class="btn btn-primary" onclick="addCard();" id="addcard"><s:text name="acc.add.card.btn.lbl"/></button>  
            </s:else> 
		</div>
		<div class="white-box">
			<s:if test="%{cardinfo.size()>1}">
                <%-- <div class="row">
                	<div class="col-md-4"><b><s:text name="acc.card.number.lbl"/></b></div>
                	<div class="col-md-4"><b><s:text name="acc.exp.month.year.lbl"/></b></div>
                	<div class="col-md-4"><b><s:text name="global.status.lbl"/></b></div>
                </div> --%>
                <div class="row">
                	<div class="col-md-4"><s:text name="acc.card.star.lbl"/><s:property value="cardinfo['card4digits']"/></div>
                	<div class="col-md-4"><s:property value="cardinfo['expmnthyear']"/></div>
                	<div class="col-md-4"><s:property value="cardinfo['status']"/></div>
                </div>						                    
			</s:if><s:else><s:text name="acc.no.cards.added.msg"/></s:else> 
		</div>
	 </s:if>
	<!-- Account Verification & Service Fee Payment Credit Card end -->
	
	
		<!-- account start -->
		<div class="section-main-header box-top-gap" ><s:text name="acc.lbl" /></div>
		<div id="accountMessage"></div>
		<div class="row sticky-out-button pull-right">
			<button type="button" class="btn btn-primary" id="accountInfo"><s:text name="global.edit.lnk.lbl" /></button>
			<button type="button" class="btn btn-primary" id="changePwd"><s:text name="acc.change.pwd.section.header" /></button>
		</div>
		

		
		<div class="white-box">
			<div class="row" id="" style="">
			<form class="form-horizontal" >
				<div class="col-md-2 col-sm-4 col-xs-6 control-label"><label><s:text name="acc.box.office.lbl" />&nbsp;:</label></div>
				<div class="col-md-10 col-sm-8 col-xs-6"  id="boxOffice"></div><div style="clear:both;"></div>
				<div id="accountInfoData"></div>
				<%-- <div class="col-md-2 col-sm-4 col-xs-6 control-label"><label><s:text name="acc.contact.info.first.name.lbl"/>&nbsp;:</label></div>
				<div class="col-md-10 col-sm-8 col-xs-6 aFname"><s:property value="accountInfo.firstName"/> </div><div style="clear:both;"></div>
				
				<div class="col-md-2 col-sm-4 col-xs-6 control-label"><label><s:text name="acc.contact.info.last.name.lbl"/>&nbsp;:</label></div>
				<div class="col-md-10 col-sm-8 col-xs-6 aLname"><s:property value="accountInfo.lastName" /></div><div style="clear:both;"></div>
				
				<div class="col-md-2 col-sm-4 col-xs-6 control-label"><label><s:text name="acc.contact.info.email.lbl"/>&nbsp;:</label></div>
				<div class="col-md-10 col-sm-8 col-xs-6 aEmail"><s:property value="accountInfo.email" /></div><div style="clear:both;"></div>
				
				<div class="col-md-2 col-sm-4 col-xs-6 control-label"><label><s:text name="acc.contact.info.phone.lbl"/>&nbsp;:</label></div>
				<div class="col-md-10 col-sm-8 col-xs-6 aPhone"><s:property value="accountInfo.phone" /></div><div style="clear:both;"></div>
				
				<div class="col-md-2 col-sm-4 col-xs-6 control-label"><label><s:text name="acc.contact.info.company.lbl"/>&nbsp;:</label></div>
				<div class="col-md-10 col-sm-8 col-xs-6 aCompany"><s:property value="accountInfo.company" /></div><div style="clear:both;"></div>
				
				<div class="col-md-2 col-sm-4 col-xs-6 control-label"><label><s:text name="acc.contact.info.title.lbl"/>&nbsp;:</label></div>
				<div class="col-md-10 col-sm-8 col-xs-6 aTitle"><s:property value="accountInfo.title" /></div><div style="clear:both;"></div>
				
				<div class="col-md-2 col-sm-4 col-xs-6 control-label"><label><s:text name="acc.contact.info.industry.lbl"/>&nbsp;:</label></div>
				<div class="col-md-10 col-sm-8 col-xs-6 aIndustry"><s:property value="accountInfo.industry" /></div><div style="clear:both;"></div>
				
				<div class="col-md-2 col-sm-4 col-xs-6 control-label"><label><s:text name="acc.contact.info.address.lbl"/>&nbsp;:</label></div>
				<div class="col-md-10 col-sm-8 col-xs-6 aAddress"><s:property value="accountInfo.address" /></div><div style="clear:both;"></div>
				
				<div class="col-md-2 col-sm-4 col-xs-6 control-label"><label><s:text name="acc.contact.info.location.lbl"/>&nbsp;:</label></div>
				<div class="col-md-10 col-sm-8 col-xs-6 "><span class="aLocation"><s:property value="accountInfo.city" /></span><br>
					<span class="aCountry"><s:property value="accountInfo.country" /></span><br><span class="aState"><s:property value="accountInfo.state" /></span></div><div style="clear:both;"></div>
				
				
				
				
				<div class="col-md-2 col-sm-4 col-xs-6 control-label"><label><s:text name="acc.contact.info.zip.lbl"/>&nbsp;:</label></div>
				<div class="col-md-10 col-sm-8 col-xs-6 aZip"><s:property value="accountInfo.zip" /></div><div style="clear:both;"></div>
				
				<div class="col-md-2 col-sm-4 col-xs-6 control-label"><label><s:text name="global.pwd.ph" />&nbsp;:</label></div>
				<div class="col-md-10 col-sm-8 col-xs-6">******</div><div style="clear:both;"></div> --%>
			</form> 
			</div>
			<style>
			.form-horizontal .control-label{
				padding-top:0px !important;
			}
			</style>
			
			<div id="editAccountInfo" style="display:none;">
				<form id="accInfoForm"  name="accInfoForm" action="home!save" class="form-horizontal">
					<s:hidden name="state" id="hstate"></s:hidden>
					
					  <div style="margin-top:15px;">
					      <div class="">
					       <div id="changeproErrorMsg" class="alert alert-danger" style="display:none"></div>
					        <div class="row">
					          <div class="col-md-2 col-sm-3 control-label"><s:text name="acc.contact.info.first.name.lbl"/><span class="required">*</span></div>
					          <div class="col-md-5 col-sm-5"><s:textfield name="accountInfo.firstName"  size='30' cssClass="form-control"></s:textfield></div>
					          <div class="col-md-5 col-sm-4"></div>
					      </div>
					      <br>
					      <div class="row">
					          <div class="col-md-2 col-sm-3 control-label"><s:text name="acc.contact.info.last.name.lbl"/><span class="required">*</span></div>
					          <div class="col-md-5 col-sm-5"><s:textfield name="accountInfo.lastName"  size='30' cssClass="form-control"></s:textfield></div>
					          <div class="col-md-5 col-sm-4"></div>
					      </div>
					       <br>
					      <div class="row">
					          <div class="col-md-2 col-sm-3 control-label"><s:text name="acc.contact.info.email.lbl"/><span class="required">*</span></div>
					          <div class="col-md-5 col-sm-5"><s:textfield name="accountInfo.email"  size='30' cssClass="form-control"></s:textfield></div>
					          <div class="col-md-5 col-sm-4"></div>
					      </div>
					       <br>
					      <div class="row">
					          <div class="col-md-2 col-sm-3 control-label"><s:text name="acc.contact.info.phone.lbl"/></div>
					          <div class="col-md-5 col-sm-5"><s:textfield name="accountInfo.phone"  size='30' cssClass="form-control"></s:textfield></div>
					          <div class="col-md-5 col-sm-4"></div>
					      </div>
					       <br>
					      <div class="row">
					          <div class="col-md-2 col-sm-3 control-label"><s:text name="acc.contact.info.company.lbl"/></div>
					          <div class="col-md-5 col-sm-5"><s:textfield name="accountInfo.company"  size='30' cssClass="form-control"></s:textfield></div>
					          <div class="col-md-5 col-sm-4"></div>
					      </div>
					       <br>
					      <div class="row">
					          <div class="col-md-2 col-sm-3 control-label"><s:text name="acc.contact.info.title.lbl"/></div>
					          <div class="col-md-5 col-sm-5"><s:textfield name="accountInfo.title"  size='30' cssClass="form-control"></s:textfield></div>
					          <div class="col-md-5 col-sm-4"></div>
					      </div>
					       <br>
					      <div class="row">
					          <div class="col-md-2 col-sm-3 control-label"><s:text name="acc.contact.info.industry.lbl"/></div>
					          <div class="col-md-5 col-sm-5"><s:textfield name="accountInfo.industry"  size='30' cssClass="form-control"></s:textfield></div>
					          <div class="col-md-5 col-sm-4"></div>
					      </div>
					       <br>
					      <div class="row">
					          <div class="col-md-2 col-sm-3 control-label"><s:text name="acc.contact.info.address.lbl"/></div>
					          <div class="col-md-5 col-sm-5"><s:textfield name="accountInfo.address"  size='30' cssClass="form-control"></s:textfield></div>
					          <div class="col-md-5 col-sm-4"></div>
					      </div>
					       <br>
					      <div class="row">
					          <div class="col-md-2 col-sm-3 control-label"><s:text name="acc.contact.info.location.lbl"/></div>
					          <div class="col-md-5 col-sm-5"><s:textfield name="accountInfo.city" id="message" size='15' placeholder="%{getText('cc.city.lbl')}" cssClass="form-control"></s:textfield></div>
					          <div class="col-md-5 col-sm-4"></div>
					      </div>
					       <br>
					      
					      <div class="row">
					          <div class="col-md-2 col-sm-3 control-label"></div>
					          <div class="col-md-5 col-sm-5"><s:select label="Select Country" name="accountInfo.country" id='hcountry' headerKey="1"
					                              headerValue="%{getText('cc.select.country.lbl')}"
					                              list="countrylist" listKey="key" listValue="value" onchange="CallStates();CallRegions();" cssClass="form-control"/></div>
					                              <div class="col-md-5 col-sm-4"></div>
					      </div>
					       <br>
					      <div class="row">
					          <div class="col-md-2 col-sm-3 control-label"></div>
					          <div class="col-md-5 col-sm-5"><select  name="accountInfo.state" id='state_d' class="form-control"></select></div>
					          <div class="col-md-5 col-sm-4"></div>
					      </div>
					       <br>
					      <div class="row">
					          <div class="col-md-2 col-sm-3 control-label"><s:text name="acc.contact.info.zip.lbl"/></div>
					          <div class="col-md-5 col-sm-5"><s:textfield name="accountInfo.zip"  size='30' cssClass="form-control"></s:textfield></div>
					          <div class="col-md-5 col-sm-4"></div>
					      </div> 
					      <br>
					      <div class="row">
					           <div class="center col-md-12 col-sm-12 col-xs-12">
					      	 <input type="button" onclick="updateAccInfo();" class="btn btn-primary" value="<s:text name='global.save.btn.lbl'/>">
					      	  <button id="closeAcc" class="btn btn-cancel" ><i class="fa fa-times"></i></button>
					      	 </div>
					      </div>
					      </div> 
					                                     
					      </div>   
					      
					                                  
					      <%-- <div class="panel-footer">   
					      <div class="row"> 
					      <div class="col-md-1">                           
					      <input type="button" onclick="updateAccInfo();" class="btn btn-primary" value="<s:text name='global.update.btn.lbl'/>"></div>
					      <div class="col-md-11"><div id="changeproStatusMsg" style="padding: 6px;"></div></div></div>
					      </div> --%>
					
					 </form>
					
					   
					 </div> 
					 
					<div id="editPassword" style="display:none;">
						<form id="passwordForm"  name="passwordForm" action="home!savepassword" class="form-horizontal">	
		                  <s:hidden name="state" id="hstate"></s:hidden>						
		                  <div style="margin-top:15px;">
		                      <div class="">
		                       <div id="changepwdErrorMsg" style="display:none" class="alert alert-danger"></div>
		                      	<div class="row">
		                      		<div class="col-md-2 col-sm-3 control-label"><s:text name="acc.new.pwd.lbl"/><span class="required">*</span></div>
		                      		<div class="col-md-5 col-sm-5"><s:password name="accountInfo.newPassword" size='30' cssClass="form-control"></s:password>
		                      		</div>
		                      		<div class="col-md-5 col-sm-4"></div>
		                      	</div>
		                      	 <br>
		                      	<div class="row">
		                      		<div class="col-md-2 col-sm-3 control-label"><s:text name="acc.retype.pwd.lbl"/><span class="required">*</span></div>
		                      		<div class="col-md-5 col-sm-5"><s:password name="accountInfo.rePassword"  size='30' cssClass="form-control"></s:password>
		                      		</div>
		                      		<div class="col-md-5 col-sm-4"></div>
		                      	</div> 
		                      	<br>
		                      	<div class="row">
		                      		<div class="col-md-12 col-xs-12 col-md-12 center">
		                      		 <input type="button" class="btn btn-primary" onclick="updatePassword();" value="<s:text name='global.save.btn.lbl'/>">
		                      		 <button class="btn btn-cancel" id="closePassword"><i class="fa fa-times"></i></button></div>
		                      		 </div>
		                      	</div>                                 
		                      </div>
		                      <%-- <div class="panel-footer">
		                      <div class="row"> 
		                      <div class="col-md-1">
		                      <input type="button" class="btn btn-primary" onclick="updatePassword();" value="<s:text name='global.update.btn.lbl'/>"></div>
		                      <div class="col-md-11"><div id="changepwdStatusMsg" style="padding: 6px;"></div> </div>
		                       </div>
		                      </div> --%>
		                  </form>
		               </div>                       
			</div>
			<!-- account stop -->
                  
</div>
			
		
	
	



<s:form id="paypaldata" name="paypaldata" action="home!insertPaypalData">
<s:hidden name="amount" id="amount"/>
<s:hidden name="invoicemnth" id="invoicemnth"/>
<s:hidden name="purpose" id="purpose"/>
<s:hidden name="userId" id="userid"/>
</s:form>
	<script type="text/javascript" src="/main/js/dataTables.js"></script>
  <script type="text/javascript" src="/main/js/creditcardpopupscreen.js"></script>
  <script type="text/javascript" src="/main/js/myAccount.js"></script>
<script>



var labels={
		'accountInfo.firstName':'<s:text name="acc.contact.info.first.name.lbl"/>',
		'accountInfo.lastName':'<s:text name="acc.contact.info.last.name.lbl"/>',
		'accountInfo.email':'<s:text name="acc.contact.info.email.lbl"/>',
		'accountInfo.phone':'<s:text name="acc.contact.info.phone.lbl"/>',
		'accountInfo.company':'<s:text name="acc.contact.info.company.lbl"/>',
		'accountInfo.title':'<s:text name="acc.contact.info.title.lbl"/>',
		'accountInfo.industry':'<s:text name="acc.contact.info.industry.lbl"/>',
		'accountInfo.address':'<s:text name="acc.contact.info.address.lbl"/>',
		'accountInfo.city':'<s:text name="acc.contact.info.location.lbl"/>',
		'accountInfo.country':'<s:text name="acc.contact.info.country.lbl"/>',
		'accountInfo.state':'<s:text name="acc.contact.info.state.lbl"/>',
		'accountInfo.zip':'<s:text name="acc.contact.info.location.lbl"/>'		
};
var onLoadData= $('#accInfoForm').serializeObject();
updateAccountInformation(onLoadData);

function getBeeCreditsData(content){	
	$('#myModal .modal-header').html('<button type="button" class="close" data-dismiss="modal">&times;</button><h3 class="modal-title" >'+props.acc_buy_bee_credits_details_lbl+'</h3>');	
   	$('#myModal .modal-body').html(content);	
    $('#myModal').modal('show');	
    //$('#myModal .modal-dialog').removeClass('modal-lg');
	$('#Bcredits').dataTable( {
                    "sPaginationType": "full_numbers",
                    "iDisplayLength":5,	
                    "bLengthChange": false,
                    "aoColumns": [null,
                                  null,                                 
                                  null
                                ],
                        "bFilter": false                  
                } );					
						
}

var userid='${userId}';
var amount="1";
var _powertype='';
var _eventid='';
var _purpose='';
var _cctype='';
var data=${jsondata};


var boxOfficeUrl = data['boxURL'];

$('#boxOffice').html('<a href="'+boxOfficeUrl+'" target="_blank">'+boxOfficeUrl+'</a>');

function addCard(){
	parent.loadingPopup();
	getMgrStatus(userid);
    getPaymentScreen('addcard');
}

function changeCard(){
	getMgrStatus(userid);
    getPaymentScreen('changecard');
}

function buyCredits(){
  parent.loadingPopup();
  getPaymentScreen('beecredits');
}

function getMgrStatus(userid){
	    	var url='/main/myaccount/home!getCardStatus?userId='+userid;
	    	 $.ajax({
						url : url,
						type : 'post',
						async : false,
						success : function(t){
							if(!isValidActionMessage(t))return;
								var status=eval('('+t+')');
								_cctype=status.cctype;
							},
						error : function(t){alert("Failure");}
		    	 });
	
}

function invoiceTabs(){
/* 	alert('hi');
var inDue = (data['induedetail']);
var clearDue = (data['cleardetail']);
var count =  inDue.details.length;
if(inDue.details){
	$('.tab1Count').html(count);
	 $.each(inDue.deatails, function(inkey, obj){
		var month=obj.invoicedmonth;
		alert(month); 
	 }); 
}
  */
var inDue = (data['induedetail']);
  
  //console.log(inDue);
 
  
  var inDueCount = inDue.details.length;
  if(inDueCount<1){
	  $('#tabl1 .nodata').remove();
	  $('#tabl1').append('<tr  class="nodata"><td>'+props.acc_no_invoice_pending+'</td></tr>')
  }
	  
	if(inDue.details){		
	$('.tab1Count').text(inDue.details.length);
		$.each( inDue.details, function(inkey, obj) {
		var month = obj.invoicedmonth;	
		var headertitle=obj.headertitle;
	    var title=obj.title;	
		cname='indue';
		var dueData ='<tr><td>'+
		'<div class="col-md-3 col-sm-3 col-xs-3"><a href="javascript:;" onclick="getDetails(\''+month+'\',\''+cname+'\',\''+headertitle+'\');">'+title+'</a></div>'+
		'<div class="col-md-3 col-sm-3 col-xs-3">'+obj.amount+'&nbsp;&nbsp;($)</div>'+
		'<div class="col-md-3 col-sm-3 col-xs-3 hideShow" style="display:none"><a onclick=paynowFunc(\''+obj.invoicedmonth+'\','+obj.amount+'); style="cursor:pointer">'+props.acc_pay_with_cc_btn_lbl+'</a></div>'+
		'<div class="col-md-3 col-sm-3 col-xs-3 hideShow" style="display:none"><a onclick=payPalFunc(\''+obj.invoicedmonth+'\','+obj.amount+'); style="cursor:pointer">'+props.acc_pay_with_paypal_btn_lbl+'</a></div>'+
		'<div style="clear:both;"></div></td></tr>';
		//var tempentry=$('<tr><td><a href="javascript:;" onclick="getDetails(\''+month+'\',\''+cname+'\',\''+headertitle+'\');">'+title+'</a></td><td>'+obj.amount+'</td><td><input type="button" class="btn btn-primary btn-sm" onclick=paynowFunc(\''+obj.invoicedmonth+'\','+obj.amount+'); value="'+props.acc_pay_with_cc_btn_lbl+'"></td><td><input type="button" class="btn btn-primary btn-sm" onclick=payPalFunc(\''+obj.invoicedmonth+'\','+obj.amount+'); value="'+props.acc_pay_with_paypal_btn_lbl+'"/></td></tr>');
		 $('#tabl1 .nodata').remove();
		 $('#tabl1').append(dueData);
		
		});
		}
	var clearDue = (data['cleardetail']);
	
	 //console.log(clearDue);
	
	var inClearDueCount = clearDue.details.length;
	  if(inClearDueCount<1){
		  $('#tabl2 .nodata').remove();
		  $('#tabl2').append('<tr  class="nodata"><td>'+props.acc_no_invoice_paid+'</td></tr>')
	  }
	if(clearDue.details){
		$('.tab2Count').text(clearDue.details.length);
		$.each( clearDue.details, function(inkey, obj) {
		var month = obj.invoicedmonth;	
		var headertitle=obj.headertitle;
	    var title=obj.title;	
		cname='clear';
		//var tempentry=$('<tr><td><a href="javascript:;" onclick="getDetails(\''+month+'\',\''+cname+'\',\''+headertitle+'\');">'+title+'</a></td><td>'+obj.amount+'</td><td></td><td></td></tr>');
		var clearData ='<tr><td>'+
		'<div class="col-sm-6 col-md-6 col-xs-6"><a href="javascript:;" onclick="getDetails(\''+month+'\',\''+cname+'\',\''+headertitle+'\');">'+title+'</a></div>'+
		'<div class="col-sm-6 col-md-6 col-xs-6">'+obj.amount+'&nbsp;&nbsp;($)</div>'+
		'<div style="clear:both;"></div></td></tr>';	
		 $('#tabl2 .nodata').remove();
		 $('#tabl2').append(clearData);
		
		});
	}
	
	var countTab1 = data.induedetail.details.length;
	var countTab2 = data.cleardetail.details.length;
	
	
	if(data.induedetail.details.length>0)  
	    $('#tabl1').dataTable( {
	        "sPaginationType": "full_numbers",
	        "iDisplayLength":5,
	        "bLengthChange": false,
	        "bSort": false,
	        "aoColumns": [null] ,
	        "bFilter": false                  
	    } ); 
	
	if(countTab1<=5){ 
		removePagination('tabl1');
	}
	
		if(data.cleardetail.details.length>0)
	         $('#tabl2').dataTable( {
	        "sPaginationType": "full_numbers",
	        "iDisplayLength":5,
	        "bLengthChange": false,
	        "bSort": false,
	        "aoColumns": [null] ,
	       "bFilter": false                  
	    } );
		if(countTab2<=5){
			removePagination('tabl2');
		}
}           


function getDetails(month,cdiv,htitle){
 montitle=month;
 _rtype=cdiv;
 var arr=month.split("-");
   var title=arr[0]+" "+arr[1];   
   var capstitle=title.charAt(0).toUpperCase() + title.slice(1);    
    url="/main/myaccount/home!getInDuePopupDetails?invoicemnth="+month+"&userId="+userid+"&rtype="+cdiv;  
   $.ajax({
	     url : url,
	     type : 'POST',
	     success : function(t){
	         var msg =  t;    	 
	         //alert(msg);
	            if(!isValidActionMessage(t))return;
	             if(msg.indexOf('monthlyinvdetails')>-1){
	            	 var content="";
	                var jdata=eval('('+t+')'); 	
	                var totalAmount = 0.00;
	                var tempAmount=0.00;
	                //alert(JSON.stringify(jdata));
	                content='<div class="table-responsive">';
	                if($.inArray('Total_Service_Fee_Local_Currency',jdata.monthdetails.fields)>-1){
	                	content+='<table class="table table-hover" id="Invoice"><thead><tr><th>'+props.global_event_name_lbl+'</th><th><div>'+props.acc_service_fee_lbl+'</div></th><th><div>'+props.acc_ticket_count_lbl+'</div></th><th><div>'+props.acc_total_amt_local_currency_lbl+'</div></th><th><div>'+props.acc_total_service_fee_lbl+' ($)</div></th></tr></thead><tbody>';
	                	$.each(jdata.monthdetails, function( inkey, valueobj ) {   
			                if(inkey=='monthlyinvdetails')    {
			                $.each(valueobj , function( akey, avalue ) {  	                	
			                content+='<tr><td>'+avalue.Event_Name+'</td><td>'+avalue.Service_Fee+'</td><td>'+avalue.Ticket_Count+'</td><td>'+avalue.Total_Service_Fee_Local_Currency+'</td><td>'+avalue.Total_Service_Fee_Usd+'</td></tr>';
			                tempAmount+=parseFloat(avalue.Total_Service_Fee_Usd);
			                
			                });
			                }               
			                });
	                	totalAmount = numberWithCommas(tempAmount);
	                	 content+='<tr><td><b>'+props.bcredits_total_lbl+'</b></td><td></td><td></td><td></td><td>'+totalAmount+'</td></tr>';
	                }else{
		                content+='<table class="table table-hover" id="Invoice"><thead><tr><th>'+props.global_event_name_lbl+'</th><th><div>'+props.acc_service_fee_lbl+'</div></th><th><div>'+props.acc_ticket_count_lbl+'</div></th><th><div>'+props.acc_total_service_fee_lbl+' ($)</div></th></tr></thead><tbody>';
		                $.each(jdata.monthdetails, function( inkey, valueobj ) {   
	    	                if(inkey=='monthlyinvdetails')    {
	    	                $.each(valueobj , function( akey, avalue ) {  	                	
	    	                content+='<tr><td>'+avalue.Event_Name+'</td><td>'+avalue.Service_Fee+'</td><td>'+avalue.Ticket_Count+'</td><td>'+avalue.Total_Service_Fee_Usd+'</td></tr>';
	    	                tempAmount+=parseFloat(avalue.Total_Service_Fee_Usd);
	    	                });
	    	                }               
	    	                });
		                totalAmount = numberWithCommas(tempAmount);
		                content+='<tr><td><b>'+props.global_total_lbl+'</b></td><td></td><td></td><td>'+totalAmount+'</td></tr>';
	                }
	                content+='</tbody></table></div>';
	                content+='<table style="  margin-left: 15px;"><tr><td><b>'+props.global_export_to_lbl+':</b></td><td>&nbsp;&nbsp;<a href="javascript:reportexport(\'excel\')">'+props.global_excel_lbl+'</a>&nbsp;&nbsp;</td><td><a href="javascript:reportexport(\'csv\')">'+props.global_csv_lbl+'</a></td></tr></table>';
	                getMonthlyInvoiceData(content,htitle);
	                //var jdata=eval('('+t.responseText+')'); 
	                //getIndueHistoryYui(jdata);
	                }else
	              alert(props.acc_no_history_available_alert_msg);  
	     },
	     error : function(t){
	         alert("Failure");
	     }
	 });
}

function getMonthlyInvoiceData(content,capstitle){	
	$('#myModal .modal-header').html('<button type="button" class="close" data-dismiss="modal">&times;</button><h3 class="modal-title">'+capstitle+'</h3>');
	$('#myModal .modal-dialog').addClass('modal-lg');
	$('#myModal .modal-body').html(content);
	$('#myModal').modal('show');   		
	/* $('#Invoice').dataTable( {
                    "sPaginationType": "full_numbers",
                    "iDisplayLength":5,
                    "bLengthChange": false,
                    "aoColumns": [null,
                                  null,                                 
                                  null,
                                  null
                                ],
                        "bFilter": false                  
                } ); */					
	
}
				



invoiceTabs(); 
CallStates();

$(document).ready(function() {

	$('#closeAcc').click(function(e){
		e.preventDefault();
		$('#accountInfo').trigger('click');
		$('html, body').animate({ scrollTop: $("#accountInfo").offset().top-200 },1000);
		});

	$('#closePassword').click(function(e){
		e.preventDefault();
		$('#changePwd').trigger('click');
		});
	
} );
</script>
       <style type="text/css" title="currentStyle">
       @import "/main/bootstrap/css/demo_table.css";
          /*  @import "//www.citypartytix.com/home/css/blockManage/demo_page.css";
            @import "//www.citypartytix.com/home/css/blockManage/demo_table.css"; */
             .dataTables_filter input { 
            @import '.form-control'; 
            }
           
        </style>