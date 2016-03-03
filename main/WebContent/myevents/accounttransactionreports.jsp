<%@taglib uri="/struts-tags" prefix="s" %>
<style>
	.form-control{
		  margin-bottom: 5px;
	}
</style>
<div class="col-md-6 col-xs-12" id="salesreportfilter" style="display:none;">
	<div class="row" >	
			<h4>Search Filter</h4>
	</div>
	<div class="row">
			<s:if test="%{pendingApproval > 0 || recPendingAppSize > 0}">			
			<div id="showtrdiv" class="taskcontent" <s:if test="%{pendingApproval == 0 && recPendingAppSize > 0}">style="display:none;"</s:if><s:else>style="display:block;"</s:else>>
			<s:if test="%{pendingApproval == 1 && recPendingAppSize == 0}">
			1 transaction is waiting for your approval
			</s:if>
			<s:elseif test="%{pendingApproval > 1 && recPendingAppSize == 0}"><s:property value="pendingApproval"/> transactions are waiting for your approval</s:elseif>
			<s:elseif test="%{pendingApproval == 0 && recPendingAppSize > 0}"><span id="trpacount"></span></s:elseif>
			<input type="button" name="Submit" id="showregbtn" value="Show"/>
			</div>			
			</s:if>
	</div>
	<s:set name="transactioncheck" value="reportsData.selindex"></s:set>
	<s:set name="paymentstaus" value="reportsData.paymentstaus"></s:set>
	<s:hidden name="reportsData.isVolumeSale" id="isvolumesale"></s:hidden>
	<div class=" row">
		<input type="checkbox" name="reportsData.online" id="onlineid" checked="checked" class="online"></input>&nbsp;Online
		<input type="checkbox" name="reportsData.manual" id="manualid" checked="checked" class="manual"></input>&nbsp;Manual
		<s:if test='%{reportsData.isVolumeSale=="Y"}'>
		<input type="checkbox" name="reportsData.volumeTickets" id="volticketsid" checked="checked" class="vtckts"></input>&nbsp;Volume Tickets
		</s:if>
	</div>
	<div class=" row">
		<input type="radio" name="selindex" value="1"  class="allactivetran"<s:if test="%{#transactioncheck == 1}">checked='checked'</s:if> />&nbsp;All Active Transactions
	</div>
	<div class="row">
		<div class="col-sm-6 col-xs-12">
			<input type="radio" id="radpapid" name="selindex" value="5"  class="othrtrans"<s:if test="%{#transactioncheck == 5}">checked='checked'</s:if>/>&nbsp;Other Transactions
		</div>
		<div class="col-sm-6 col-xs-12">
			<select name='paymentstaus' class="form-control" onchange="transactionchange();">
			<s:if test="%{pendingApproval > 0 || recPendingAppSize > 0}">
			<option id='selpapid' value='Need Approval' <s:if test="%{#paymentstaus == 'Need Approval'}"> selected</s:if>>Pending Approval</option>
			</s:if>
			<option value='CANCELLED' <s:if test="%{#paymentstaus == 'CANCELLED}"> selected</s:if>>Deleted</option>
			<option value='Refunded' <s:if test="%{#paymentstaus == 'Refunded'}"> selected</s:if>>Refunded</option>
			<option value='Chargeback' <s:if test="%{#paymentstaus == 'Chargeback'}"> selected</s:if>>Chargebacks</option>
			<s:if test='%{reportsData.isVolumeSale=="Y"}'>
			<option id='volauth' value='Authorized' <s:if test="%{#paymentstaus == 'Authorized'}"> selected</s:if>>Volume Tickets Pending</option>
			</s:if>
			</select>
		</div>
	</div>
	
	<div class="row">
	<div class="col-sm-6 col-xs-12">
		<input type="radio" name="selindex" id="radtid" value="3" class="tid">&nbsp;Transaction ID
	</div>
	<div class="col-sm-6 col-xs-12">
		<input type="text" name="reportsData.transactionID" value="" id="reportsData_transactionID" class="form-control">
	</div>
	</div>
	
	<div class="row">
	<div class="col-sm-6 col-xs-12">
		<input type="radio" name="selindex" id="radonid" value="6"  class="ordernum"<s:if test="%{#transactioncheck == 6}">checked='checked'</s:if>/>&nbsp;Order Number
	</div>
	<div class="col-sm-6 col-xs-12">
		<s:textfield name="reportsData.orderNumber" theme="simple" cssClass="form-control"></s:textfield>
	</div>
	</div>
	
	<div class="row">
	<div class="col-sm-6 col-xs-12">
		<input type="radio" name="selindex" id="attradio" value="4" class="attname" <s:if test="%{#transactioncheck == 4}">checked='checked'</s:if>/>&nbsp;Attendee Name
	</div>
	<div class="col-sm-6 col-xs-12">
		<s:textfield name="reportsData.attendeeName" theme="simple" cssClass="form-control"></s:textfield>
	</div>
	</div>
	
	<div class="row">
	<div class="col-sm-6 col-xs-12">
		<input type="radio" name="selindex" value="2" id="startdate_radio" class="sdate"<s:if test="%{#transactioncheck == 2}" >checked='checked'</s:if>/>&nbsp;Start Date
	</div>
	<div class="col-sm-6 col-xs-12">
		<input type="text" class="form-control" id="startdate_field">
	</div>
	</div>
	
	<div class="row">
	<div class="col-sm-6 col-xs-12">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;End Date 
	</div>
	<div class="col-sm-6 col-xs-12">
		<input type="text" class="form-control" id="enddate_field">
	</div>
	</div>
	
	<div class="row">
	<div class="col-sm-6 col-xs-12">
		<input type="radio" id="tsource" name="selindex" value="7"  class="tsource" <s:if test="%{#transactioncheck == 7}">checked='checked'</s:if>/>&nbsp;Transaction Source
	</div>
	<div class="col-sm-6 col-xs-12">
		<s:select name="reportsData.source" id='source' headerKey="1" theme="simple" cssClass="form-control"   onchange="tsourcechange()"   
	    		headerValue="-- Select Source --"
	    		list="bookingSource" listKey="key" listValue="value"  />
	</div>
	</div>
	
	
	<div class="col-sm-12 col-xs-12">
		More Options &nbsp;<span id="hide"><i class="fa fa-chevron-circle-down"></i></span>
	</div>
	
	
	<div id="more" style="display: none;" class="col-xs-12 col-sm-12">
	 <div class=" col-sm-6 col-xs-12">Payment Type</div>
	 <div class=" col-sm-6 col-xs-12">
	 	<s:select name="reportsData.paymenttypeindex" id='pti' headerKey="6" theme="simple" cssClass="form-control"
	headerValue="--- All ---" list="#{'0':'Eventbee CC','1':'Google','2':'PayPal','7':'Authorize.Net','8':'Braintree','9':'Stripe','3':'Eventbee Credits','4':'No Payment','5':'Other'}"  />
	 </div>
	</div>
	</div>
	
<div class="col-md-6 col-xs-12" id="salesreportdisplayfields" style="display:none;">
<div class=" row ">
 
	<div class=" col-xs-6" style="padding-left:30px;">
		<h4>Display Fields Filter</h4>
	</div>
	<div class="row col-xs-6" style="padding-top:7px">
	
		<a href="javascript:;" name="CheckAll"  id="checkall">Select All</a>  <a href="javascript:;" name ="unCheckAll" id="uncheckall">Clear All</a>
	
	</div>
	
</div>

<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="TD" class="feilds tdate"<s:if test="%{Fields.contains('TD')}" >checked</s:if>></input>&nbsp;Transaction Date
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="TID" id="transtid"  class="feilds tid"<s:if test="%{Fields.contains('TID')}" >checked</s:if>></input>&nbsp;Transaction ID
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="FN" class="feilds fname"<s:if test="%{Fields.contains('FN')}" >checked</s:if>></input>&nbsp;First Name
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="LN"  class="feilds lname"<s:if test="%{Fields.contains('LN')}" >checked</s:if>></input>&nbsp;Last Name
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="EN" class="feilds ename"<s:if test="%{Fields.contains('EN')}" >checked</s:if>></input>&nbsp;Event Name
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="TN"   class="feilds tname"<s:if test="%{Fields.contains('TN')}">checked</s:if>></input>&nbsp;Ticket Name
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="SF" class="feilds sfee"<s:if test="%{Fields.contains('SF')}" >checked</s:if>></input>&nbsp;Service Fee
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="Di"  class="feilds discount"<s:if test="%{Fields.contains('Di')}" >checked</s:if>></input>&nbsp;Discount
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="TC" class="feilds tktcnt"<s:if test="%{Fields.contains('TC')}" >checked</s:if>></input>&nbsp;Tickets Count
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="CCPF"  class="feilds ccfee"<s:if test="%{Fields.contains('CCPF')}" >checked</s:if>></input>&nbsp;CC Processing Fee
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="TP" class="feilds tckprc"<s:if test="%{Fields.contains('TP')}" >checked</s:if>></input>&nbsp;Ticket Price
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="TTC"  class="feilds ttp"<s:if test="%{Fields.contains('TTC')}" >checked</s:if>></input>&nbsp;Total Ticket Price
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="ESF" class="feilds ebeesfee"<s:if test="%{Fields.contains('ESF')}" >checked</s:if>></input>&nbsp;Eventbee Service Fee
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="ECCF"  class="feilds ebeeccfee"<s:if test="%{Fields.contains('ECCF')}" >checked</s:if>></input>&nbsp;Eventbee CC Processing Fee
</div>
<s:if test="%{otherCCPFee=='Yes'}" >
	<div class="col-xs-12 col-sm-12">
		<input type="checkbox" name="Fields" value="OCCF" class="feilds othrccpfee"<s:if test="%{Fields.contains('OCCF')}" >checked</s:if>></input>&nbsp;Other CC Processing Fee
	</div>
</s:if>
<div class="col-xs-12 col-sm-12">
	<input type="checkbox" name="Fields" value="NTSC" class="feilds ntsc"<s:if test="%{Fields.contains('NTSC')}" >checked</s:if>></input>&nbsp;Network Ticket Selling Commission
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="TNet" class="feilds ntp"<s:if test="%{Fields.contains('TNet')}" >checked</s:if>></input>&nbsp;Net Ticket Price
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="CSF"  class="feilds csf"<s:if test="%{Fields.contains('CSF')}" >checked</s:if>></input>&nbsp;Collected Service Fee
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="Bal" class="feilds balance"<s:if test="%{Fields.contains('Bal')}" >checked</s:if>></input>&nbsp;Balance
</div>
<div class="col-xs-12 col-sm-6">
	<input type="checkbox" name="Fields" value="PT"  class="feilds paytype"<s:if test="%{Fields.contains('PT')}" >checked</s:if>></input>&nbsp;Payment Type
</div>
<div class="col-xs-12 col-sm-12">
	More Options &nbsp;<span id="displayfilter"><i class="fa fa-chevron-circle-down"></i></span>
</div>

<div class="" id="search" style="display: none;">
	<div class="col-xs-12 col-sm-6">
		<input type="checkbox"  class="feilds ordernum" name="Fields" value="ON" <s:if test="%{Fields.contains('ON')}">checked</s:if>></input>&nbsp;Order Number
	</div>
	<div class="col-xs-12 col-sm-6">
		<input type="checkbox"  class="feilds phone" name="Fields" value="Ph"  class="feilds phone"<s:if test="%{Fields.contains('Ph')}">checked</s:if>></input>&nbsp;Phone
	</div>
	<div class="col-xs-12 col-sm-6">
		<input type="checkbox" name="Fields" value="Em" class="feilds email"<s:if test="%{Fields.contains('Em')}">checked</s:if>></input>&nbsp;Email
	</div>
	<div class="col-xs-12 col-sm-6">
		<input type="checkbox" name="Fields" value="St"  class="feilds status"<s:if test="%{Fields.contains('St')}">checked</s:if>></input>&nbsp;Status
	</div>
	<div class="col-xs-12 col-sm-6">
		<input type="checkbox" name="Fields" value="TU" class="feilds trackurl"<s:if test="%{Fields.contains('TU')}">checked</s:if>></input>&nbsp;Tracking URL
	</div>
	<div class="col-xs-12 col-sm-6">
		<input type="checkbox" name="Fields" value="So" class="feilds source" <s:if test="%{Fields.contains('So')}">checked</s:if>></input>&nbsp;Source
	</div>
	<div class="col-xs-12 col-sm-6">
		<input type="checkbox" name="Fields" value="No" class="feilds notes"<s:if test="%{Fields.contains('No')}">checked</s:if>></input>&nbsp;Notes
	</div>
	<div class="col-xs-12 col-sm-6">
		<input type="checkbox" name="Fields" value="DC" class="feilds dcode"<s:if test="%{Fields.contains('DC')}">checked</s:if>></input>&nbsp;Discount Code
	</div>
	<div class="col-xs-12 col-sm-6">
		<input type="checkbox" name="Fields" value="EPID" class="feilds extpayid"<s:if test="%{Fields.contains('EPID')}">checked</s:if>></input>&nbsp;External Pay ID
	</div>
	<div class="col-xs-12 col-sm-12">
		<input type="checkbox" name="Fields" value="PID"  class="feilds ntspi"<s:if test="%{Fields.contains('PID')}">checked</s:if>></input>&nbsp;Network Ticket Selling Partner ID
	</div>
	<s:if test='%{reportsData.isVolumeSale=="Y"}'>
		<div class="col-xs-12 col-sm-12">
			<input type="checkbox" name="Fields" value="VTSC"  class="feilds vtsc"<s:if test="%{Fields.contains('VTSC')}">checked</s:if>></input>&nbsp;Volume Ticket Selling Commission
		</div>
	</s:if>	
</div>
</div>

<script>

$(document).ready(function()
		{
	$('#reportsData_transactionID').on('focus', function() {
        $('#radtid').iCheck('check');
    });
	
	$('#reportsData_orderNumber').on('focus', function() {
        $('#radonid').iCheck('check');
    });
	
	$('#reportsData_attendeeName').on('focus', function() {
        $('#attradio').iCheck('check');
    });
	
	$('#startdate_field').on('focus', function() {
        $('#startdate_radio').iCheck('check');
    });
	
	$('#enddate_field').on('focus', function() {
        $('#startdate_radio').iCheck('check');
    });
	
	 $('#startdate_field').datetimepicker({
	        format:'m/d/Y',
	        timepicker:false,
	        step:5
	    });
	 
	 $('#enddate_field').datetimepicker({
	        format:'m/d/Y',
	        timepicker:false,
	        step:5
	    });
	 
		});

function transactionchange()
{
	$('#radpapid').iCheck('check');
}

function tsourcechange()
{
	$('#tsource').iCheck('check');
}



var expan=false;
var unchkfields={"FieldsFilter":1,"VTSC":2,"PID":3,"No":4,"EPID":5,"DC":6,"So":7,"TU":8,"Ph":9,"Em":10,"St":11,"ON":12,"1":13,"2":14,"3":15,"4":16,"5":17,"6":18,"7":19,"8":20,"9":21,"10":22,"11":23,"12":24,"13":25,"14":26,"15":27,"16":28,"17":29,"18":30,"19":31,"20":32};
    $('#checkall').click(function(event) {  //on click      
        $('input[name="Fields"]').each(function(){
            if(!expan){
                    if(unchkfields[$(this).val()]==undefined)
                         $(this).iCheck('check');
                 }
                 else
                         $(this).iCheck('check');
         });
    });
    
     $('#uncheckall').click(function(event) { 
    	  $('input[name="Fields"]').each(function(){
              if(!expan){
                      if(unchkfields[$(this).val()]==undefined)
                           $(this).iCheck('uncheck');
                   }
                   else
                           $(this).iCheck('uncheck');
           });     
    });



$(function(){
 $('input.sfee').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 
 $('input.tdate').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'});
  $('input.lname').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'});
 $('input.tname').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'});
 $('input.ename').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'});
 $('input.fname').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'}); 
 
 
 $('input.tktcnt').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.tckprc').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.ebeesfee').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.othrccpfee').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.ntsc').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.ntp').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.balance').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.discount').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.ttp').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.ebeeccfee').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.csf').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
 $('input.paytype').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.online').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.manual').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.vtckts').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.allactivetran').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.othrtrans').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.attname').iCheck({  
 checkboxClass: 'icheckbox_square-grey',    
 radioClass: 'iradio_square-grey'});
  
  $('input.tid').iCheck({  
	  checkboxClass: 'icheckbox_square-grey',    
	  radioClass: 'iradio_square-grey'});
  
  $('input.sdate').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.tsource').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
   $('input.ordernum').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
    $('input.ccfee').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
    $('input.ordernum').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
    $('input.phone').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.email').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.status').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.trackurl').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.source').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.notes').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.dcode').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.extpayid').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.ntspi').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  
 });
 
/* $("#hide").click(function(){
    var imgid=this.id
    $("#more").slideToggle(
    function(){
    	var c=document.getElementById(this.id).style.display;
        if(c=="none")
        $("#hide").attr("src","../images/dn.gif")
        else
        $("#hide").attr("src","../images/up.gif")
    });    
}); */

$("#displayfilter").click(function(){
    var imgid=this.id
    $("#search").slideToggle(
    function(){
    	var d=document.getElementById(this.id).style.display;
        if(d=="none"){
        	$("#displayfilter").html('<i class="fa fa-chevron-circle-down" style="cursor:pointer"> </i>');
        expan=false;
        }else{
        	 $("#displayfilter").html('<i class="fa fa-chevron-circle-up" style="cursor:pointer"> </i>');
    	expan=true;
        }
}
    );    
}); 
 
 $('#hide').click(function(){
		$("#more").slideToggle(
			    function(){
			    	var a=document.getElementById(this.id).style.display;
			        if(a=="none")
			        $("#hide").html('<i class="fa fa-chevron-circle-down" style="cursor:pointer"> </i>');
			        else
			       $("#hide").html('<i class="fa fa-chevron-circle-up" style="cursor:pointer"> </i>');
			    }
			    ); 
	});
 
 /* $('#displayfilter').click(function(){
		$("#search").slideToggle(
			    function(){
			    	var a=document.getElementById(this.id).style.display;
			        if(a=="none")
			        $("#displayfilter").html('<i class="fa fa-chevron-circle-down" style="cursor:pointer" id="showhide"> </i>');
			        else
			       $("#displayfilter").html('<i class="fa fa-chevron-circle-up" style="cursor:pointer" id="showhide"> </i>');
			    }
			    ); 
	});
 */

function showTransResults(){
	$('#transtid').iCheck('check');	
formaction('registration');
}





</script>
<%--         <style type="text/css" title="currentStyle">
  			            @import "//www.citypartytix.com/home/css/blockManage/demo_page.css";
            @import "//www.citypartytix.com/home/css/blockManage/demo_table.css";
             .dataTables_filter input { 
            @import '.form-control'; 
            }
            .popover-content {
   color:#000;
   font-size:0.9em
}
         
        </style> --%>