<%@taglib uri="/struts-tags" prefix="s" %>

<style>
.well {
    background-color: #f5f5f5 !important;
    border: 1px solid #e3e3e3 !important;
    border-radius: 4px !important;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.05) inset !important;
    min-height: 20px !important;
    padding: 19px !important;
}

</style>

<script>

function searchKeyPress(e) {
	   
	   if (e.keyCode === 13) {
	           e.preventDefault();
	           return false;
	   }
	   return true;
	}
</script>
 <form name="ticketURLfrm" action="TicketURLs!insertTicketURLs" id="ticketURLfrm" method="post">
 <s:hidden name="eid"></s:hidden>
<div class="col-sm-12 col-xs-12 col-md-12 well">
<div id="errormsg"  class=""></div>
	<div class="row col-sm-12 col-xs-12 col-md-12 bottom-gap">
		<div class="col-md-6 col-sm-6 col-xs-12">
			<input type="text" name="name" id="name" placeholder="<s:text name="ptu.name.ph.lbl"/>" size="62" onkeypress="searchKeyPress(event);" onkeyup="gettktcode()"; onkeydown="gettktcode()";  class="form-control"/>
		</div>
		<div class="col-md-6 col-sm-6 col-xs-12 sm-font" style="  margin-top: 7px;">
			 <s:text name="ticketURLsData.eventURL" ></s:text><label id="url" ></label>
		</div><span style="clear:both;"></span>
		
	</div><span style="clear:both;"></span>
	<div class="col-md-12 col-sm-12 col-xs-12 row" id="permissions" style="display: block;">
	
		<div class="col-xs-12 col-sm-12 col-md-12 row" style="margin-bottom: 10px;">
			<div class="col-sm-3 sm-font sm-font"><span><s:text name="ptu.applicable.tkts.lbl"/><span class="required">*</span></span></div>
			<%-- <div class="col-sm-9 text-right"><a href="#"  href="javascript:;" id="selectall"><s:text name="global.select.all.lbl"/></a> &nbsp;<a href="#"  href="javascript:;" id="clearall"><s:text name="global.clear.all.lbl"/></a></div> --%>
		</div>
		<!-- <div class="panel panel-default">
		  <div class="panel-body"> -->
		  <!-- <div style="  margin: 5px 0px;"><a href="#"  href="javascript:;" id="selectall">Select All</a> &nbsp; <a href="#"  href="javascript:;" id="clearall">Clear All</a></div> -->
		   <!--  <div style="overflow:auto;" class="ticketsblock"> -->
		    	<div class="col-sm-12 col-xs-12 col-md-12 xsm-font">
		    		
		    		
		    		<s:set value="%{alltickets.size()}" name="size"></s:set>
					<s:set value="%{alltickets}" id="ticketscheckbox"></s:set>
					<s:if test="%{#size==0}"><s:text name="global.currently.no.tkts.msg"/></s:if>
					<s:else>
					<s:iterator value="%{alltickets}" var="ticket">
						<div class="col-sm-12 col-xs-12" >
							<div id="eachCheck">			
								<s:checkbox name="seltickets"  data-name='%{value}' cssClass="attFields seltkts" id="seltkts" fieldValue="%{key}" value="%{seltickets.contains(key)}" theme="simple"/>&nbsp;${value}
							</div>
						</div>
					</s:iterator>
					</s:else>
		    	</div>
		    <!-- </div> -->
		  <!-- </div>
		</div> -->
		<div class="col-md-12 col-sm-12 col-xs-12 center" style="margin-top:10px;">
			<input type="button" value="<s:text name="global.add.btn.lbl"/>" class="btn btn-primary confirmTrackUrl" id="changeBtnName" onclick="create();" />
			<button type="button" class="btn btn-cancel cancelTrackUrl"><i class="fa fa-times"></i></button>
		</div>
	</div>
</div>

</form>
<style>
#permissionsTickets{
cursor: pointer;
}</style>
<script>
/* $('#permissionsTickets').click(function(){
	  $('#permissions').slideToggle(slideTime);
}); */

$(document).ready(function(){
	$('#createPrivateTickets').prop("disabled",true);
	$('input[name="name"]').focus();
});

$('#selectall').click(function(event) {  //on click
	event.preventDefault();
    $('.attFields').iCheck('check');
});

 $('#clearall').click(function(event) { 
	 event.preventDefault();
 	 $('.attFields').iCheck('uncheck');      
});
 
 $(function(){
	 $('input.attFields').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'});
 });
 
 function gettktcode(){
	 	parent.resizeIframe();
		document.getElementById('url').innerHTML=document.getElementById('name').value;
	}
 
 function setErrorMsg(msg){
		$('#errormsg').html(msg);
		$('#errormsg').addClass('alert alert-danger');		
	}
 
 function create(){	
	 
	 
	 var name=document.getElementById('name').value;
	name=name.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	name=name.replace(/[^a-z0-9]|\s+|\r?\n|\r/gmi, "");
	document.getElementById('name').value=name;
	$('#url').html(name);
	
	
	if(name==""){
		setErrorMsg(props.ptu_enter_name_errmsg);	
		     return;
		}		
	if($('form input[type=checkbox]:checked').size()==0)
		{
			setErrorMsg(props.ptu_sel_atleast_one_tkt_msg);
	     	//bootbox.confirm('Select Atleast One Ticket', function (result) {	   }); 		
		    return;
	}		
	submitForm(); 	 
	}
 
 function submitForm(){
	 $('#changeBtnName').prop('disabled',true);
	 var tckurl = getTicketUrlData();
			$.ajax({
			url : 'TicketURLs!insertTicketURLs',
			type : 'POST',
			data : $('#ticketURLfrm').serialize(),
			success : function(t){
				 var result=t;
			        if(result.indexOf("Name Exists")>-1){		
			        	hideProcessing();
			        	setErrorMsg(props.ptu_this_name_is_already_used_msg);
			        	 $('#changeBtnName').prop('disabled',false);	
					}else if(result.indexOf("spacesInUrl")>-1){	
						hideProcessing();
			        	setErrorMsg(props.global_use_alphanumeric_chars_msg);	
			        	$('#changeBtnName').prop('disabled',false);					
					}else{						
						//parent.window.location.reload(true);
						$('#createprivateurls').prop("disabled",false);							
						getURLsReloadedData();
						 $('#privateStatusMsg').html('');
						// statusMessage('privateStatusMsg',props.ptu_ticket_url_added_successfully_msg,"success");
						notification(props.ptu_ticket_url_added_successfully_msg,"success");
						$('#ticketurlsbox').html('');
//						hideProcessing();
					}			       
			    }
		});
}
 
 var getTicketUrlData = function(){
	 var tckurl = {};	
	 var arr = [];
	 $('.seltkts:checked') .each(function(){
	       arr.push($(this).data('name'));
	     });
	 var turl = '${ticketURLsData.eventURL}';
	 tckurl.name = $('#name').val();
	 var name=$('#url').html();
	 name=name.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	name=name.replace(/[^a-z0-9]|\s+|\r?\n|\r/gmi, "");
	 tckurl.email = turl+name;
	 tckurl.tickets = arr;	 
	 return tckurl;
 }
 

 
 function cancel(){
	 parent.closepopup();
 }

</script>
