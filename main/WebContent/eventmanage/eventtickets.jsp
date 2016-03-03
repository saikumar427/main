<%@taglib uri="/struts-tags" prefix="s" %>
<!-- <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" /> -->
<style>
/* .ticketsblock {
	background-color: #FFFFFF;
    background-image: none;
    border: 1px solid #CCCCCC;
    border-radius: 4px;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
    color: #555555;
    display: block;
    font-size: 14px;
    height: 110px;
    line-height: 1.42857;
    padding: 6px 12px;
    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
    vertical-align: middle;
    width: 1yyy;
} */

</style>
<s:form name="ticketform" action="TicketURLs!updateTicketURLs" id="ticketform" theme="simple" method="post">
<s:hidden name="eid"></s:hidden>
<s:hidden name="ticketURLsData.code" id="tktcode"></s:hidden>

<div class="col-md-12 col-sm-12 col-xs-12 well well-no-margin">
<div id="errormsg"  class=""></div>

	<s:set value="%{alltickets.size()}" name="size"></s:set>
	<s:if test="%{#size==0}">
	<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-12 row"><s:text name="ptu.applicable.tkts.lbl"/><span class="required">*</span></div>
	</div>
	</s:if>
	<s:else>
	<div class="col-md-6 col-sm-6 col-xs-6 sm-font"><s:text name="ptu.applicable.tkts.lbl"/><span class="required">*</span></div>
	<!-- <div class="col-md-12 col-sm-12 col-xs-12 center" >
		<div class="pull-right">
			<input type="button" class="btn btn-primary" value="Save" onclick="save();" />
			<button type="button" class="btn btn-active" id="rmblock"><i class="fa fa-times"></i></button>
		</div>
	</div> -->
	<div class="col-md-12 col-xs-12 col-sm-12 xsm-font" style="  margin-top: 10px;">
	<!-- <div class="panel panel-default">
		  <div class="panel-body"> -->
		  <%-- <div style="margin-bottom: 5px;"><a href="javascript:;" id="selectall"><s:text name="global.select.all.lbl"/></a> &nbsp;&nbsp; <a href="javascript:;" name ="UnCheckAll" id="clearall"><s:text name="global.clear.all.lbl"/></a></div> --%>
		   <!-- <div style="overflow:auto;" class="ticketsblock"> -->
		    	<div class="col-sm-12 col-xs-12 col-md-12">
		    		<s:iterator value="%{alltickets}" var="ticket" >
						<div class="col-md-12 col-xs-12">
						<s:checkbox name="seltickets" id="tickets" cssClass="attFields" fieldValue="%{key}" data-key="%{key}" data-name="%{value}" value="%{seltickets.contains(key)}" />&nbsp;${value}</div>
					</s:iterator>
		    	</div>
		    </div>
		  <!-- </div>
		</div>
		</div> -->
		<div class="col-md-12 col-sm-12 col-xs-12 center" style="margin-top:10px;">
			<input type="button" class="btn btn-primary" value="<s:text name="global.save.btn.lbl"/>" onclick="save();" id="ticketurlsave"/>
			<button type="button" class="btn btn-cancel" id="rmblock"><i class="fa fa-times"></i></button>
	</div>
		
	</s:else>
	
	
	
	<%-- <div class="row"><div class="col-xs-8"></div><div class="col-xs-4">
	<a href="javascript:;" id="selectall">Select All</a> &nbsp;&nbsp; <a href="javascript:;" name ="UnCheckAll" id="clearall">Clear All</a><br/>
	</div></div>
	<div class="row"><div class="col-xs-12">
	<div style="overflow:auto;" class="ticketsblock">
	<s:iterator value="%{alltickets}" var="ticket" >
	<div class="col-md-4 col-xs-6"><s:checkbox name="seltickets" id="tickets" cssClass="attFields" fieldValue="%{key}" data-key="%{key}" data-name="%{value}" value="%{seltickets.contains(key)}" />&nbsp;${value}</div>
	</s:iterator>
	</div></div></div>
	</s:else> --%>
</div>




<%-- <div class="col-xs-12 col-sm-12">

<s:set value="%{alltickets.size()}" name="size"></s:set>
<s:if test="%{#size==0}">
<div class="row">
<div class="col-xs-12">
<div style="overflow:auto;" class="ticketsblock">
Currently there are no tickets added to the event.</div>
</div>
</div>
</s:if>
<s:else>
<div class="row"><div class="col-xs-12">
Select Tickets to show (Unselect to hide)</div><br/>
</div>
<div class="row"><div class="col-xs-8"></div><div class="col-xs-4">
<a href="javascript:;" id="selectall">Select All</a> &nbsp;&nbsp; <a href="javascript:;" name ="UnCheckAll" id="clearall">Clear All</a><br/>
</div></div>
<div class="row"><div class="col-xs-12">
<div style="overflow:auto;" class="ticketsblock">
<s:iterator value="%{alltickets}" var="ticket" >
<s:checkbox name="seltickets" id="tickets" cssClass="attFields" fieldValue="%{key}" data-key="%{key}" data-name="%{value}" value="%{seltickets.contains(key)}" />&nbsp;${value}<br/><br/>
</s:iterator>
</div></div></div>
</s:else>
<br/>
<div class="col-sm-12">
	<div class="text-center">
		<input type="button" class="btn btn-primary" value="Submit" onclick="save();">&nbsp;<input type="button" class="btn btn-active" value="Cancel" onclick="cancel();">
	</div>
</div>
</div> --%>
</s:form>
<%-- <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="/main/js/bootstrap.min.js"></script>
<script src="/main/bootstrap/js/icheck.min.js"></script>
<script src="/main/bootstrap/js/bootbox.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/grey.css" /> --%>
<script>
/* $(document).ready(function(e) {
        var chkBoxArray = [];
	$('.attFields:checked').each(function() {
		chkBoxArray.push($('input[value='+$(this).val()+']').data('name'));
        });
	//alert(chkBoxArray);
	parent.updateTicketsView(chkBoxArray);
	//alert('success');
}); */

function setErrorMsg(msg){
	$('#errormsg').html(msg);
	$('#errormsg').addClass('alert alert-danger');		
}

$('#rmblock').click(function(){
	$('.editTktRow').html('');
	$('#createprivateurls').prop("disabled",false);
	enableLinks();
});


$('#selectall').click(function(event) {  //on click
    $('.attFields').iCheck('check');
});

 $('#clearall').click(function(event) { 
 	 $('.attFields').iCheck('uncheck');      
});
 
 $(function(){
	 $('input.attFields').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'});
 });
 function save(){

	 $('#ticketurlsave').prop("disabled",true);
		options=document.ticketform.seltickets;
		//alert("hi htis::"+options.length);
		
		   var value=0;
		     var count=0;
		     /* for(var i=0;i<options.length;i++){
		        	if(options[i].checked){
		     		value=options[i].value;
		     		count++;
		     	}
		     } */
$('input[name="seltickets"]').each(function(){

	if(this.checked){
 		value=this.value;
 		count++;
 	}
	
});
		     if(count==0){
		     	setErrorMsg(props.ptu_sel_atleast_one_tkt_msg);
		     	$('#ticketurlsave').prop("disabled",false);
		    	 return false;
		   }
		 
		     showProcessing('');
	    $.ajax({
	    	url : 'TicketURLs!updateTicketURLs',
	    	type : 'get',
	    	data : $('#ticketform').serialize(),
	    	success : function(t){
	    		/* var chkBoxArray = [];
	    		$('.attFields:checked').each(function() {
	    			chkBoxArray.push($('input[value='+$(this).val()+']').data('name'));
	    	        });
	    		var rowId = document.getElementById('tktcode').value; */
	    		//parent.updateTicketsView(chkBoxArray,rowId);
	    		$('#ticketurlsave').prop("disabled",false);
	    		getURLsReloadedData();
	    		 $('#privateStatusMsg').html('');
	    		// statusMessage('privateStatusMsg',props.global_update_success_msg,"success");
	    		notification(props.ptu_ticket_url_updated_successfully_msg,"success");
	    		
	    		//parent.closepopup();
			    // parent.window.location.reload(true);
	    		
	    	}	    
	    });
		
	}

/*  function cancel(){
	 parent.closepopup();
 } */
</script>