<%@taglib uri="/struts-tags" prefix="s" %>
<meta http-equiv="Content-type" content="text/html;charset=UTF-8">
<style>
.drop-image{
display: none;
}
.checkbox-space{
cursor: pointer;
}

.options{
margin-left:7px !important;
}

</style>

 <form action="TicketDetails!save" name="ticketingForm" id="ticketingForm" method="post" class="form-horizontal">
 <div class="well" id="addtickettemplate">
 <div class="row">
 <div class="bottom-gap options">
<div id="ticketformerrormessages"  class='alert alert-danger col-md-12' style='display:none' ></div>

<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="ticketData.ticketId" id="ticketid"></s:hidden>
<s:hidden name="evtlevel" id="evtlevel"></s:hidden>
<s:hidden name="specialfee" id="specialfee"></s:hidden>
<s:hidden name="advSpecialfee" id="advspecialfee"></s:hidden>
<s:hidden value="%{ticketData.processingFee}" id="tktservicfee"></s:hidden>

<s:hidden name="ticketData.waitListType" id="waitlisttype"></s:hidden>
<s:hidden name="ticketData.processFeePaidBy" id="processfee" value="processFeeMgr"></s:hidden>
<s:hidden name="ticketData.isAttendee" value="ATTENDEE"></s:hidden>
<s:hidden name="ticketData.minQty" id="hidminqty"> </s:hidden>
<s:hidden name="ticketData.maxQty" id="hidmaxqty"> </s:hidden>

<span id="currsymbol" style="display:none;"></span>


<s:set name="grplen" value="groupId.length()"></s:set>
<s:if test="%{#grplen==0}"><s:hidden name="groupId" value="0"></s:hidden></s:if>
<s:else><s:hidden name="groupId"></s:hidden></s:else>
 <div class="col-md-6 col-sm-6 col-xs-12 bottom-gap">
    <s:textfield name="ticketData.ticketName" cssClass="form-control" theme="simple" size="52" placeholder="%{getText('global.ticket.name.lbl')}" id="tktname"></s:textfield>
   </div>
   
   <div class="col-md-3 col-sm-3 col-xs-12 bottom-gap">
   		<s:textfield id="ticketprice" name="ticketData.ticketPrice" cssClass="form-control" theme="simple" size="12" placeholder="%{getText('mt.price.ph.lbl')}" autofocus="autofocus"></s:textfield>
   </div>
                                   
     <div class="col-md-3 col-sm-3 col-xs-12" >
         <s:textfield id="tktavailid" cssClass="form-control" name="ticketData.totalTicketCount" theme="simple" size="12" placeholder="%{getText('mt.tkt.qty.ph.lbl')}" ></s:textfield>
      </div>
           
             <%-- <div class="col-md-2 col-sm-2 col-xs-12" >
             <select id="minqty" name= "ticketData.minQty" class="form-control"></select>
             <span class="sub-text">&nbsp;&nbsp;Min Quantity</span>
             </div>
             
             
               <div class="col-md-2 col-sm-2 col-xs-12" >
              <select id="maxqty" name= "ticketData.maxQty" class="form-control"></select>
              <span class="sub-text">&nbsp;&nbsp;Max Quantity</span>
               </div> --%>                                                         
                                   
   </div>
   
   
   <div class="row bottom-gap sm-font options" >
    <div class="col-md-12 col-xs-12 col-sm-12" data-div="purchase-quantity">
	<label>
							<s:checkbox id="quantity"  name=""  cssClass="ticketsettings"> </s:checkbox>
							</label>
							<span id="quantitytitle" class="checkbox-space"><s:text name="mt.use.cutom.tkt.limit"/></span>
							
							 <span class="glyphicon glyphicon-menu-right drop-image" id="descriptionspin" data-block="description" style="cursor:pointer;"></span> 			
							</div>			
    </div>
    
    
    <div class="background-options row sm-font"  id="quantityblock" style="display:none;"> 
    <div class="col-md-1 col-xs-6 col-sm-3" style="margin-top: 10px;"> <s:text name="mt.min.lbl"/> </div>
    <div class="col-md-2 col-xs-6 col-sm-3 bottom-gap">
     <select id="minqty" name="minqty" class="form-control"></select>
    </div>
    <div class="col-md-1"></div> 
    <div class="col-md-1 col-xs-6 col-sm-3" style="margin-top: 10px;"> <s:text name="mt.max.lbl"/> </div>
    <div class="col-md-2 col-xs-6 col-sm-3">
   <select id="maxqty" name="maxqty" class="form-control"></select>
    </div>
    </div>
    
    
    
    <div class="row bottom-gap sm-font options" >
    <div class="col-md-12 col-xs-12 col-sm-12" data-div="date-time">
	<label>
							<s:checkbox id="datetime"  name=""  cssClass="ticketsettings"> </s:checkbox></label>
							
							<span id="datetimetitle" class="checkbox-space"><s:text name="mt.custom.tkt.date"/><%-- <span class="required">*</span> --%></span>
							
							 <span class="glyphicon glyphicon-menu-right drop-image" id="descriptionspin" data-block="description" style="cursor:pointer;"></span> 			
							</div>			
    </div>
    
    
    <div class="background-options row"  id="datetimeblock" style="display:none;"> 
    
    
    
    <s:if test="%{isrecurring==true}">
   
   <div class="row bottom-gap sm-font" style="margin-left: 4px;">
  <%--  <label for="name" class="col-md-2 col-sm-2 col-xs-2 control-label sm-font">Ticket sale date & time<span class="required">*</span></label> --%>
   <div class='col-md-1 col-sm-1 col-xs-1' style="margin-top:10px;"><s:text name="mt.starts.lbl"/></div>
   
   
   <s:if test="%{ticketData.ticketId==''}"><s:set name="startdays" value="100"></s:set></s:if>
					<s:else><s:set name="startdays" value="ticketData.startBefore"></s:set></s:else>								 
					<div class="col-md-2 col-sm-3 col-xs-3" >
						<s:textfield cssClass='form-control' name="ticketData.startBefore" placeholder="%{getText('mt.days.lbl')}" size="2" theme="simple" value="%{#startdays}"></s:textfield>
						<span class="sub-text"><s:text name="mt.days.lbl"/></span>
					</div>
					<s:hidden name="ticketData.stdateTimeBeanObj.monthPart"></s:hidden>
					<s:hidden name="ticketData.stdateTimeBeanObj.ddPart"></s:hidden>
					<s:hidden name="ticketData.stdateTimeBeanObj.yyPart"></s:hidden>
					<div class="col-md-2 col-sm-3 col-xs-3" >
						<s:textfield cssClass='form-control' name="ticketData.startHoursBefore" placeholder="%{getText('mt.hours.lbl')}" size="2" theme="simple"></s:textfield>
						<span class="sub-text"><s:text name="mt.hours.lbl"/></span>
						<s:hidden name="ticketData.stdateTimeBeanObj.hhPart"></s:hidden>
					</div>
					<div class="col-md-2 col-sm-3 col-xs-3" >
						<s:textfield cssClass='form-control' name="ticketData.startMinutesBefore" placeholder="%{getText('mt.mins.ph.lbl')}" size="2" theme="simple"></s:textfield>
						<span class="sub-text"><s:text name="mt.min.before.lbl"/></span>
						<s:hidden name="ticketData.stdateTimeBeanObj.mmPart"></s:hidden>
						<s:hidden name="ticketData.stdateTimeBeanObj.ampm"></s:hidden>
					</div>  
   </div>
   
   <div class="row sm-font" style="margin-left: 4px;">
  					 <!-- <label for="name" class="col-md-2 col-sm-2 col-xs-2 control-label"></label> -->
					<div class='col-md-1 col-sm-1 col-xs-1' style="margin-top:10px;"><s:text name="mt.ends.lbl"/></div>
					<div class="col-md-2 col-sm-3 col-xs-3" >
						<s:textfield   cssClass='form-control' name="ticketData.endBefore" size="2" placeholder="%{getText('mt.days.lbl')}" theme="simple"></s:textfield>
						<span class="sub-text"><s:text name="mt.days.lbl"/></span>
					</div>
					<s:hidden name="ticketData.enddateTimeBeanObj.monthPart"></s:hidden>
					<s:hidden name="ticketData.enddateTimeBeanObj.ddPart"></s:hidden>
					<s:hidden name="ticketData.enddateTimeBeanObj.yyPart"></s:hidden>
					<div class="col-md-2 col-sm-3 col-xs-3" >                                                                      
						<s:textfield  cssClass='form-control' name="ticketData.endHoursBefore" size="2" theme="simple" placeholder="%{getText('mt.hours.lbl')}" ></s:textfield>
						<span class="sub-text"><s:text name="mt.hours.lbl"/></span>
						<s:hidden name="ticketData.enddateTimeBeanObj.hhPart"></s:hidden>
					</div>
					<div class="col-md-2 col-sm-3 col-xs-3" >
						<s:textfield  cssClass='form-control' name="ticketData.endMinutesBefore" size="2" theme="simple" placeholder="%{getText('mt.mins.ph.lbl')}"></s:textfield>
						<span class="sub-text"><s:text name="mt.min.before.lbl"/></span>
						<s:hidden name="ticketData.enddateTimeBeanObj.mmPart"></s:hidden>
						<s:hidden name="ticketData.enddateTimeBeanObj.ampm"></s:hidden>
					</div>
				 
   
   
   </div>
   
   
   </s:if>
   <s:else>
   <div class="row bottom-gap">
   			<div class="col-md-1 col-sm-1 col-xs-6 control-label sm-font"><s:text name="mt.starts.lbl"/></div>
   			
		<div class="col-sm-4 col-md-3 col-xs-6 bottom-gap">
		<s:textfield cssClass="form-control" id="tktstart" type="text" name="ticketData.newStartDate" />
        <div class="sub-text">&nbsp;<s:text name="global.mm.dd.yyyy.lbl"/></div>
        </div>
        
        <div class="col-md-1 col-sm-1 col-xs-6 control-label sm-font"><s:text name="mt.ends.lbl"/></div>
        
        <div class="col-sm-4 col-md-3 col-xs-6 bottom-gap">
        <s:textfield cssClass="form-control" id="tktend" type="text" name="ticketData.newEndDate" />
       <div class="sub-text">&nbsp;<s:text name="global.mm.dd.yyyy.lbl"/></div>
    	</div>
   </div>
   </s:else>
    </div>
   
   <div class="row bottom-gap sm-font options" >
    <div class="col-md-12 col-xs-12 col-sm-12" data-div="specific-tickets">
	<label>
							<s:checkbox id="description"  name=""  cssClass="ticketsettings" > </s:checkbox></label>
							
							<span id="descriptiontitle" class="checkbox-space"><s:text name="mt.description.lbl"/></span>
							
							 <span class="glyphicon glyphicon-menu-right drop-image" id="descriptionspin" data-block="description" style="cursor:pointer;"></span> 				
							</div>			
    </div>
    
    
    <div class="background-options"  id="descriptionblock" style="display:none;"> 
    <s:textarea rows="3" cols="49" cssClass='form-control ' id="tktdescription" name="ticketData.ticketDescription" theme="simple" placeholder="%{getText('mt.tkt.desc.ph.lbl')}"></s:textarea>
    </div>
    
    
    
     <div class="row bottom-gap sm-font options" >
    <div class="col-md-12 col-xs-12 col-sm-12 ">
 	 <label>
							<s:checkbox id="paidby" name=""  cssClass="ticketsettings"> </s:checkbox></label>
							<span id="paidbytitle" class="checkbox-space"> <s:text name="mt.attendee.pay.lbl"/> </span>
							 <span class="glyphicon glyphicon-menu-right drop-image" id="paidbyspin" data-block="paidby" style="cursor:pointer;"></span>	 
    
    </div>
    </div>
    
    
   <div class="background-options sm-font" style="display:none;" id="paidbyblock">
   <!-- Attendee pays&nbsp; -->
   
   
   <s:text  name='ticketData.currency'></s:text>
  <s:textfield  id="processingfee" name="ticketData.processingFee" theme="simple" size="12" placeholder="%{getText('mt.enter.num.lbl')}" onfocus="getTicketLevelAgreeDiv();"  onkeyup="resetFee();"></s:textfield><br/>
    <div style="height:10px"></div>
    <div id="tktlevelagree" style="display:none;padding-left: 5px;border: 1px solid #ddd;padding: 10px;width: 345px;margin-left: 23px;text-align: center;"><b> <s:text name="up.thid.belongs.pro.tkting"/> </b><br/><br/>${currency}${specialfee} <s:text name="ps.upgradepopup.msg1.lbl"/><br><s:text name="ps.upgradepopup.msg2.lbl"/>   <br/><br/><input type="button" class="btn btn-primary" value="<s:text name="up.upgrade.btn.lbl"/>" id="agreebtn" onclick="responseStatus('agreebtn');"/>&nbsp;&nbsp;<input type="button" class="btn btn-active" value="<s:text name="up.nothanks.btn.lbl"/>" id="cancelbtn" onclick="responseStatus('cancelbtn');"/></div>
    </div>
   
   
   
   
   <s:if test="%{isrecurring==false}">
    <div class="row bottom-gap sm-font options">
    <div class="col-md-12 col-xs-12 col-sm-12" >
	<label>
							<s:checkbox id="waitlist"  name=""  cssClass="ticketsettings" onselect="getWaitListAgreeDiv('NO');"> </s:checkbox></label>
							<span id="waitlisttitle" class="checkbox-space"><s:text name="mt.allow.waitlist.lbl"/></span>
							 <span class="glyphicon glyphicon-menu-right drop-image" id="waitlistspin" data-block="waitlist" style="cursor:pointer;"></span>	 			
							</div>			
    </div>
    
    
     
     <div class="background-options row sm-font" style="display:none" id="waitlistblock">
     
     <div>
  		<div class="col-md-3 col-sm-3 col-xs-12" >
  		 <input class="tktichk waitlists" type="radio" name="waitlist" id="noLimitWaitListType" value="NOLIMIT" onclick="getWaitListAgreeDiv('NOLIMIT');" checked="checked">&nbsp;<s:text name="mt.waitlist.unlimited.lbl"/>
				</div>
				
       <div class="col-md-5 col-sm-5 col-xs-12">
				<input class="tktichk waitlists" type="radio"  name="waitlist" id="limitWaitListType" value="LIMIT" onclick="getWaitListAgreeDiv('LIMIT');" >
				&nbsp;<s:text name="mt.waitlist.limit.lbl"/>
				<s:textfield class="form-control" id="" name="ticketData.waitListLimit" theme="simple" size="12" placeholder="%{getText('mt.enter.num.lbl')}"   ></s:textfield>
  		</div></div>
  		<div class="row">
  		<div class="col-md-6 col-xs-12 col-sm-12">
  		<div style="height:8px;"></div>
  		<div id="waitlistagree" style="display:none;padding-left: 5px;border: 1px solid #ddd;padding: 10px;margin-left: 23px;text-align: center;"><b><s:text name="up.thisis.adv.tkting"/> </b><br/><br/>${currency}${advSpecialfee} <s:text name="up.flat.fee.per.tkt"/><br><s:text name="up.tip.lbl"/><br/><br/><input type="button" class="btn btn-primary" value="<s:text name="up.upgrade.btn.lbl"/>" id="waitlistagreebtn" onclick="responseStatus('waitlistagreebtn');"/>&nbsp;&nbsp;<input type="button" class="btn btn-active" value="<s:text name="up.nothanks.btn.lbl"/>" id="waitlist_cancelbtn" onclick="responseStatus('waitlist_cancelbtn');"/></div>
  		</div></div>
    </div>
    </s:if>
								

<div class="row">
<div class="col-md-12 col-sm-12 col-xs-12 center" style="margin-top:10px;">
        <button type="button" class="btn btn-primary confirmaddticket"><s:text name="global.add.btn.lbl"/></button>
        <button type="button"  class="btn btn-cancel canceladdticket"><i class="fa fa-times" ></i></button>
</div>
</div>


</div>


</div>
</form>
<script>
var ticketprice='<s:text  name="ticketData.currency"></s:text>';
	$('#currsymbol').html(ticketprice);
	$('#ticketprice').attr('placeholder',$('#currsymbol').html()+ props.mt_tkt_price_ph_lbl);

fillMinimumQuantity();
fillMaximumQuantity();


/* var defaultData=$('#ticketingForm').serializeArray();



alert(defaultData['eid']); */



var paramObj = {};
$.each($('#ticketingForm').serializeArray(), function(key,value) {
  paramObj[value.name] = value.value;
});



$('#tktstart').datetimepicker({
    format:'m/d/Y g:i A',
    formatTime:'g:i A'
});

$('#tktend').datetimepicker({
    format:'m/d/Y g:i A',
    formatTime:'g:i A'
});

$('input.tktichk').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey' 
	
		});


$('#description,#paidby,#waitlist,#quantity,#datetime').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey' 
	
		});

var blockdivs=["descriptionblock","paidbyblock","waitlistblock","quantityblock","datetimeblock"];
var textspan=["descriptiontitle","paidbytitle","waitlisttitle",,"quantitytitle","datetimetitle"];
var spinimage=["descriptionspin","paidbyspin","waitlistspin"];



$('.tktichk').on('ifChecked',function(){
	 $('#waitlisttype').val($(this).val());
});



$('#description,#paidby,#waitlist,#quantity,#datetime').on('ifChecked',function(){

	var id=$(this).attr('id');
    
  //  $('#'+id+'title').addClass('highlighted-text');
   $('#'+id+'block').slideDown();
   $('#'+id+'spin').removeClass('original').addClass('down');


    if(id=='waitlist'){
      $('#waitlisttype').val('NOLIMIT');
      getWaitListAgreeDiv('NOLIMIT');
      }else if(id=='paidby')
	    $('#processfee').val('processFeeAttendee');
    
   
});


$('#description,#paidby,#waitlist,#quantity,#datetime').on('ifUnchecked',function(){
	var id=$(this).attr('id');
	   $('#'+id+'block').slideUp();
	   // $('#'+id+'title').removeClass('highlighted-text'); 
	    $('#'+id+'spin').removeClass('down').addClass('original');

	    if(id=='waitlist'){
	        $('#waitlisttype').val('NO');
	        getWaitListAgreeDiv('NO');
	    }else if(id=='paidby')
		    $('#processfee').val('processFeeMgr');
	});


$(document).on('click','.drop-image',function(){
	var id=$(this).attr('data-block');

	 $.each(blockdivs,function(key,val){
		 if(val!=id+'block')
         $('#'+val).hide();
        });

    /* $.each(textspan,function(key,val){
    	 if(val!=id+'title')
        $('#'+val).removeClass('highlighted-text');
       }); */

   /*  $.each(spinimage,function(key,val){
    	if(val!=id+'spin')
        $('#'+val).removeClass('down').addClass('original');
       }); */
	
	

if($('#'+id+'block').is(':hidden')){
	$('#'+id+'block').slideDown();
	//$('#'+id+'title').addClass('highlighted-text');
	$('#'+id+'spin').removeClass('original').addClass('down');
	$('#'+id).iCheck('check');
}else{
	$('#'+id+'block').slideUp();
	//$('#'+id+'title').removeClass('highlighted-text');
	$('#'+id+'spin').removeClass('down').addClass('original');
}
		
		
		
});




$('#servicefee').tooltipster({
	   maxWidth:'500',
       content: $('<span>'+props.mt_collect_from_attendee+'</span>'),
    });


$('#attendeename').tooltipster({
	   maxWidth:'500',
    content: $('<span>'+props.mt_defaultcollect_fee+'</span>'),
 });

/* function getWaitListAgreeDiv(val){
	 $('#waitlisttype').val(val);
}
 */


function fillMinimumQuantity(){
	 var options='';
	 for(var i=1;i<100;i++)
		 options+='<option value="'+i+'">'+i+'</option>';
	 $('#minqty').html(options);
 }


function fillMaximumQuantity(){
	var options='';
	 for(var i=1;i<100;i++)
		 if(i==5)
		 options+='<option value="'+i+'" selected="selected">'+i+'</option>';
		   else	 
		 options+='<option value="'+i+'">'+i+'</option>';

	 $('#maxqty').html(options);

}
 

</script>

