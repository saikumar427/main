<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" src="/main/js/dataTables.js"></script>
<script>
//var waitlistdata = ${jsonData};
var eid=${eid};
var waitlistdata = ${emailTemplateJson};
var serveraddress = '${serverAddress}';
//var eventname='${eventname}';
</script>

<s:hidden name="purpose" id="purpose"></s:hidden>
<s:form id="waitlistemailform" name="waitlistemailform" action="WaitList!save" method="post">
<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="eventname"></s:hidden>
<s:hidden name="mgrName"></s:hidden>

<div class="row">
	<div class="col-md-12 col-sm-12">
	<%-- <h1><small class="section-main-header"><s:text name="wl.customize.wl.email.section.header"/></small></h1><hr> --%>
	<div id="message" class='alert alert-success col-md-12' style='display: none'></div>
	 
	 <div class="row">
	 	<div class="form-group">
	        <label for="emailcontent" class="col-md-2 col-sm-2 control-label"><s:text name="global.email.content.lbl"/></label>
	        <div class="col-md-10 col-sm-10">
	        	<input type="radio" class="contenttype" name="purpose"  id="confirmed" checked="checked" value="confirmed"/>&nbsp;<s:text name="wl.req.confirm.lbl"/>
	        	<span style="margin-left:5px;"></span>
	        	<input type="radio" class="contenttype" name="purpose"  id="activation" value="activation"/>&nbsp;<s:text name="wl.actlnk.lbl"/>
	        </div>
        </div>
 	</div>
 	<div id="loading" style="display:none;"><center><img src="../images/ajax-loader.gif"></center></div>
 	<div id="contentdiv" >
 	
 	<br/>
	<div class="row">
	<div class="form-group">
		<label class="col-md-2 col-sm-2 control-label"><s:text name="global.send.to.lbl"/></label>
		<div class="col-md-10 col-sm-10">
			<input type="checkbox" name="" class="sendto" checked="checked" disabled="disabled"/><span class="checkbox-space">&nbsp;<s:text name="res.send.to.buyer.cb.lbl"/>&nbsp;</span>
			<input type="checkbox" name="waitListData.tomanager" id="evtMgr" class="sendto"/><span class="checkbox-space">&nbsp;<s:text name="global.event.mgr.lbl"/></span>
	 	</div>
	 	<div class="col-md-5 col-sm-5"></div>
	</div>
	</div>
	<br/>
	<div class="row">
	<div class="form-group">
	<label for="bccto" class="col-md-2 col-sm-2 control-label"><s:text name="global.bcc.to.lbl"/></label>
	<div class="col-md-5 col-sm-5"><s:textfield name="waitListData.bcc" size="91" cssClass="form-control" id="bcc"/></div>
	<div class="col-md-5 col-sm-5"></div>
	</div>
	</div>
	<br/>
	<div class="row">
	<div class="form-group">
		<label for="subject" class="col-md-2 col-sm-2 control-label"><s:text name="global.subject.lbl"/></label>
		<div class="col-md-5 col-sm-5">
		<%-- <s:property value="%{emailTemplateJson}"/> --%>
		
			<s:textfield name="waitListData.subject" size="91" cssClass="form-control" id="subject"/>
		</div>
		<div class="col-md-5 col-sm-5"></div>
	</div>
	</div>
	
	<br/>
	<div class="row form-group">
	<div class="col-md-12 col-sm-12 ">
		<s:textarea name="waitListData.emailcontent" rows="30" cols="97" id="previewcontent" cssClass="form-control"></s:textarea>
	</div>
	</div>
	
	
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-12 center">
			<input type="button" id="submitBtn" value="<s:text name="global.save.btn.lbl"/>" class="btn btn-primary" onclick="submitfm();">
			<input type="button" id="resetBtn" value="<s:text name="global.reset.btn.lbl"/>" class="btn btn-primary" onclick="resetContent();">
			<input type="button" id="previewBtn"  value="<s:text name="global.preview.btn.lbl"/>" name="preview"  class="btn btn-primary" onclick="getContentPreview();"/>
		</div>
	</div>
	</div>
	</div>
</div>
</s:form> 
<style type="text/css" title="currentStyle">
@import "../bootstrap/css/demo_table.css";
.dataTables_filter input {
@import '.form-control';
}

.modal-body table{
width:100% !important;
}
</style>
<script>
$('input[name="waitlist_id_all"]').iCheck({  
	checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey'});

$('input[name="purpose"]').iCheck({  
	checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey'});

$('input.sendto').iCheck({  
	checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey'});
customizeEmailTemplate(waitlistdata);

function customizeEmailTemplate(templatejsondata){
	if(templatejsondata.tomanager=='yes')
		$("#evtMgr").iCheck('check');
	$("#bcc").val(templatejsondata.bcc);
	$("#subject").val(templatejsondata.subject);
	$("#previewcontent").val(templatejsondata.content);
}
var typeOfMail = '';
$(document).on('ifChecked','.contenttype',function(){
	typeOfMail = $(this).attr('value');
	getTemplate($(this).attr('value'));
});
function getTemplate(purpose){
	$("#contentdiv").css('visibility','hidden')
	$('#loading').show();
	//showProcessing('forload');
	//$("#purpose").val(purpose);
	var url='WaitList!emailTemplate?eid='+eid+'&purpose='+purpose;
	$.ajax({
     	type: "POST",
         url:url,
         success:function(result){
        	 if(!isValidActionMessage(result)) return;
        	 customizeEmailTemplate(eval ("(" + result + ")"));
        	 $('#loading').hide();
        	 // hideProcessings();
        	 $("#contentdiv").css('visibility','visible');
         }
     });
}
function resetContent(){
	bootbox.confirm(props.wl_by_resetting_customization_will_lost_msg,function(result){
		if(result){
			var url='WaitList!resetWaitListEmailSettings';
			 $.ajax({
			     	type: "POST",
			         url:url,
			         data: $("#waitlistemailform").serialize(), 
			         success:function(result){
			        	 if(!isValidActionMessage(result)) return;
			        	 customizeEmailTemplate(eval ("(" + result + ")"));
						  $('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
			 				notification(props.wl_reset_success_lbl,"success");
			        	/* var msg='<div id="statusMessageBox" class="statusMessageBox"> <div style="float: left;valign: middle;"><img src="../images/check.png"/>'+
			        		 '&nbsp;'+props.global_update_success_msg+'</div><div><a class="closebtn" href="javascript:hidestatusmessage()"></a></div>';
			        	 $('#message').show().html(msg); */
			         }
			 });
		}
	});
}
function submitfm(){
	var url='WaitList!save';
	var data = $('#waitlistemailform').serialize();
	 $.ajax({
	     	type: "POST",
	         url:url,
	         data: data, 
	         success:function(result){
	        	 if(!isValidActionMessage(result)) return;
	        	 $('#notification-holder').html('');
	 				$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
	 				notification(result,"success");
	         },
	         error:function(){
	        	 //alert('error');
	         }
	   });
}
function getContentPreview(){
	
	var url='WaitList!preview';
	 $.ajax({
    	 type: "POST",
 		   url: url,
 		   data: $("#waitlistemailform").serialize(),	    	
 		   success: function( result ) {
 			  if(!isValidActionMessage(result)) return;
 			  $('#myModal').on('show.bs.modal', function () {
 		        	$('#myModal .modal-title').html('<span style="color: rgb(176, 176, 176); font-size: 22px;">'+props.globa_preview+'</span>');	 		        		           	
 		        	$('#myModal .modal-body').html(result);	
 		        	$('.modal-body a').click(function(e) {
 		        	   e.preventDefault();
 		        	 });
 			  });	 		     
 		     $('#myModal').modal('show');        
 			    $('#myModal').modal({
 			        backdrop: 'static',
 			        keyboard: false,
 			        show:false
 			    }); 			    
 		
 		   }  	
    	
    });
}

</script>
