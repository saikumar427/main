<%@taglib uri="/struts-tags" prefix="s"%>
<style>
.hr-color{
    border-top: 1px solid #e2e2e2 !important;
}
</style>
<s:form id="confirmationemailform" name="confirmationemailform" action="RegEmailSettings!save" method="post" theme="simple">
<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="regEmailSettingsData.powerType"></s:hidden>
<s:set name="powerType" value="regEmailSettingsData.powerType"></s:set>
<s:set name="sendtoattendee" value="regEmailSettingsData.sendToAttendee"></s:set>
<s:set name="sendtomanager" value="regEmailSettingsData.sendToManager"></s:set>
<div class="col-md-12">
<div class="row"><div id="confirmationemailStatusMsg"></div></div>
<!-- <div id="gap"></div> -->
<!--<div class="row">
	<s:if test="%{powertype=='RSVP'}">
		<s:if test="%{currentLevel=='90'}">
		<div class="alert alert-info hidden-xs">
		<i class="fa fa-info-circle"></i>&nbsp; 
		<s:text name="cps.RSVP.upgrade.msg1.lbl"></s:text> <a href="/main/pricing"><s:text name="cps.RSVP.upgrade.msg2.lbl"></s:text></a> <s:text name="cps.RSVP.upgrade.msg3.lbl"></s:text> </div>
		</s:if>
		<div class="alert alert-info hidden-xs">
		<i class="fa fa-info-circle"></i>&nbsp; 
		<s:text name="res.RSVP.msg.lbl"></s:text>
		</div>
		</s:if>
		<s:else>
		<div class="alert alert-info hidden-xs">
		<i class="fa fa-info-circle"></i>&nbsp; 
		<s:text name="res.ticketing.msg.lbl"></s:text>
		</div>
		</s:else>
	</div>   -->

<div class="row">

		  	   		<%-- <h1><small class="section-main-header"><s:text name="res.section.header.lbl"></s:text></small></h1><hr> --%>
		  	   		
		  	   		<div class="row">
		  	   			<div class="col-md-2"><s:text name="res.send.to.cb.lbl"></s:text></div>	   			
		  	   			<div class="col-md-10">
		  	   			<input type="checkbox"  class="buyer" name="regEmailSettingsData.sendToAttendee" <s:if test="%{#sendtoattendee == 'yes'}">checked='checked'</s:if> />
						<s:if test="%{#powerType == 'RSVP'}">&nbsp;<s:text name="res.send.to.attendee.cb.lbl"></s:text>&nbsp;</s:if>
						<s:else>&nbsp;<s:text name="res.send.to.buyer.cb.lbl"></s:text>&nbsp;</s:else>
						<input type="checkbox"  class="eventmanager" name="regEmailSettingsData.sendToManager" <s:if test="%{#sendtomanager == 'yes'}">checked='checked'</s:if> />
						&nbsp;<s:text name="res.send.to.manager.cb.lbl"></s:text>
		  	   			</div>		  	   		  			
		  	   		</div><br>
		  	   		<div class="row">
		  	   			<div class="col-md-2"><s:text name="res.bcc.to.tb.lbl"></s:text></div>
		  	   			<div class="col-md-5"><s:textfield name="regEmailSettingsData.cc" theme="simple" size="91"  cssClass="form-control"/></div><div class="col-md-5"></div>  	   			
		  	   		</div><br>
		  	   		
		  	   		<s:if test="%{powertype=='RSVP'}">
		  	   		</s:if>
		  	   		<s:else>
		  	   		<div class="row">
		  	   			<div class="col-md-2"><s:text name="res.email.content.rb.lbl"></s:text>&nbsp;&nbsp;<i class="fa fa-info-circle info" style="cursor: pointer" id="confirmationEmailInfotiper" ></i></div>
		  	   			<div class="col-md-10">
		  	   				<input type="radio" name="templateType" class="confirmationEmailOpt"  id="completed" onClick="getTemplate()" <s:if test="%{templateType=='' || templateType=='COMPLETED'}">checked="checked"</s:if> value="COMPLETED"/>&nbsp;<s:text name="cps.approved.rb.lbl"></s:text>
							&nbsp;&nbsp;&nbsp;
							<input type="radio" name="templateType" class="confirmationEmailOpt"  id="pending" onClick="getTemplate()" <s:if test="%{templateType=='PENDING'}">checked="checked"</s:if> value="PENDING"/>&nbsp;<s:text name="cps.pending.rb.lbl"></s:text>
		  	   			</div>
		  	   		</div><div id="forLoad"></div>
		  	   		<br>
		  	   		</s:else>
		  	   		<%-- <div class="row">
		  	   			<div class="col-md-2">Send To</div>	   			
		  	   			<div class="col-md-10">
		  	   			<input type="checkbox"  class="buyer" name="regEmailSettingsData.sendToAttendee" <s:if test="%{#sendtoattendee == 'yes'}">checked='checked'</s:if> />
						<s:if test="%{#powerType == 'RSVP'}">&nbsp;Attendee&nbsp;</s:if>
						<s:else>&nbsp;Buyer&nbsp;</s:else>
						<input type="checkbox"  class="eventmanager" name="regEmailSettingsData.sendToManager" <s:if test="%{#sendtomanager == 'yes'}">checked='checked'</s:if> />
						&nbsp;Event Manager
		  	   			</div>		  	   		  			
		  	   		</div> --%>
		  	   		<div id="totalContent">		  	   		
			  	   		<div class="row">
			  	   			<div class="col-md-2"><s:text name="res.subject.tb.lbl"></s:text></div>
			  	   			<div class="col-md-5"><s:textfield name="regEmailSettingsData.subject" id="subjectEmail" theme="simple" size="91"  cssClass="form-control"/></div><div class="col-md-5"></div>
			  	   		</div>
			  	   		
			  	   		<%-- <div class="row">
			  	   			<div class="col-md-2">Bcc To</div>
			  	   			<div class="col-md-5"><s:textfield name="regEmailSettingsData.cc" theme="simple" size="91"  cssClass="form-control"/></div><div class="col-md-5"></div>  	   			
			  	   		</div> --%>
			  	   		<!-- <br>
			  	   		<div class="row">
			  	   			<div class="col-md-2">Email Content&nbsp;<i class="fa fa-info-circle info" style="cursor: pointer"
								id="confirmationEmailInfo"
								title="If you have selected manual approval for Other Payment Method in Payment Settings, we will first send out Payment Pending email content. Once Payment is approved by you, we will send out Payment Approved email content."></i></div>
			  	   			<div class="col-md-10">
			  	   				<input type="radio" name="templateType" class="confirmationEmailOpt"  id="completed" onClick="getTemplate()" <s:if test="%{templateType=='' || templateType=='COMPLETED'}">checked="checked"</s:if> value="COMPLETED"/>&nbsp;Payment Approved
								&nbsp;&nbsp;&nbsp;
								<input type="radio" name="templateType" class="confirmationEmailOpt"  id="pending" onClick="getTemplate()" <s:if test="%{templateType=='PENDING'}">checked="checked"</s:if> value="PENDING"/>&nbsp;Payment Pending
			  	   			</div>
			  	   		</div> -->
			  	   		<br>
			  	   		<div class="row">
			  	   			<div class="col-md-offset-2 col-md-10">
			  	   				<s:textarea name="regEmailSettingsData.tempalteContent" id="emailApproved" rows="30" cols="100"  theme="simple"  cssClass="form-control" style="display: none;"></s:textarea>
			  	   				<s:textarea name="regEmailSettingsData.tempalteContent" id="emailPending" rows="30" cols="100"  theme="simple"  cssClass="form-control" style="display: none;"></s:textarea>
			  	   			</div>
			  	   		</div>
		  	   		</div>
		  	   		<hr>
		  	   		<div class=""> 
		  	   		
		  	   		<span class="regDiv" style="cursor:pointer"><s:text name="cps.more.options.lbl"></s:text><span id="changeIconEmail" class="small arrow-gap">&nbsp;<i class="glyphicon glyphicon-menu-right"></i></span></span>
		  	   			<%-- <span class="section-header"> <s:text name="cps.more.options.lbl"></s:text></span> &nbsp;<span id="changeIconEmail"><i class="fa fa-chevron-circle-down" style="cursor:pointer" id="showhideDiv"> </i></span> --%>
		  	   			<!-- <div class="col-md-3 section-header">More Options &nbsp;<img id="showhide" src="../images/dn.gif" width="15px" /></div> -->		  	   		
		  	   		</div>   		
		  	   		<div class="row">
		  	   		<div id="advanced" class="background-options sm-font" style='display:none'>		  	   		
		  	   		 <div class="row">		  	   		 	
			  	   		 	<div class="col-md-12" style="">
			  	   		 	<s:if test="%{powertype!='RSVP'}">
			  	   		 	<div class="row col-md-3 co-md-3 col-sm-4 col-xs-6"  style="margin: 7px 0px 0px 1px;">
			  	   		 		<span class="sm-font"><s:text name="res.include.pdf.rb.lbl"/></span>
			  	   		 	</div>
			  	   		 		<s:set name="pdfval" value="%{pdfcheck}"></s:set>
			  	   		 		<s:set name="pdfvalpending" value="%{pdfcheckpending}"></s:set>
			  	   		 		<div class="col-md-9 row col-sm-8 col-xs-6">
			  	   		 		<input type="checkbox" class="pdfticketAppro" name="pdfcheck" value="Y" id="completed"<s:if test='%{#pdfval == "Y"}'>checked='checked'</s:if>/><span class="checkbox-space"><span class="xsm-font"><s:text name="res.include.pdf.approved.rb.lbl"/></span></span>
								<span style="margin-left: 20px;"></span>
								<input type="checkbox" class="pdfticketPend" name="pdfcheckpending" value="Y" id="pending"<s:if test='%{#pdfvalpending == "Y"}'>checked='checked'</s:if>/><span class="checkbox-space"><span class="xsm-font"><s:text name="res.include.pdf.pending.rb.lbl"/></span></span>
								</div>
			  	   		 		<!-- <br><input type="checkbox"  class="pdfticket" name="pdfcheck" value="Y" <s:if test='%{#pdfval == "Y"}'>checked='checked'</s:if>/>&nbsp;Include PDF tickets attachment -->		  	   		 		
			  	   		 	
			  	   		 	</s:if>
			  	   		 	
			  	   		 	</div> 		 	
						</div>
		  	   		
		  	   		 <s:if test='%{chkbuyerquestions=="Y" || chkattendeequestions=="Y"}'>
		  	   		 <div class="row">
		  	   		 <div class="col-md-12">
		  	   		 	<s:if test="%{powertype=='RSVP'}">
			  	   		</s:if>
			  	   		<s:else>
			  	   		<%-- <span class="col-md-12" style="margin-bottom: 10px;"><s:text name="res.applicable.settings.lbl"/></span> --%>
			  	   		</s:else>
						<div id="includedpage">
						
							<jsp:include page="/eventmanage/eventpagecontent/confirmscreenattribs.jsp"></jsp:include>
						
						</div>
		  	   		  </div></div></s:if><br>
		  	   		  
		  	   		 </div>
		  	   		 </div>
	                             
</div>

</div><!-- div col-md-9 -->
	 	<div class="row">
	 		<div class="text-center">
	 			<input type="button" id="submitBtn" value="<s:text name="global.save.btn.lbl"></s:text>" class="btn btn-primary"> &nbsp;
			 	<input type="button" id="resetBtn" value="<s:text name="global.reset.btn.lbl"></s:text>" class="btn btn-primary"> &nbsp;
			 	<input type="button" name="preview" value="<s:text name="global.preview.lnk.lbl"></s:text>" id="previewBtn" class="btn btn-primary"/> 	
	 		</div>
	 	</div>
	 <!-- <div class="row">	 	 	
	 	<div class="col-md-offset-5"><br>
	 	<input type="button" id="submitBtn" value="Submit" class="btn btn-primary"> &nbsp;
	 	<input type="button" id="resetBtn" value="Reset" class="btn btn-primary"> &nbsp;
	 	<input type="button" name="preview" value="Preview" id="previewBtn" class="btn btn-primary"/> 	
	 	</div>	 	
	 </div>  -->
	
</s:form>

<div class="modal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
                	<div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header"> <h3><s:text name="res.email.preview"/></h3>                         
                        </div>
                        <div class="modal-body">
                            <iframe id="popup" src="" width="100%" height="10" onload='javascript:resizeIframe(this)' frameborder="0"></iframe>
                        </div>
                        <!--  <div class="modal-footer">
                         </div> -->
                        </div>
                </div>
            </div>

<style>
	#myModal td{
		  padding-bottom: 5px;
	}
	#myModal table{
		border-collapse: initial !important;
	 	border-spacing: 1px !important;
	 	background-color: none !important;
	}
</style>
    
<script>

$(document).ready(function(){
	$('#confirmationEmailInfotiper').attr('title',props.res_email_content_help_msg_lbl);
	$('#applicableinfo1').attr('title',props.applicable_for_both_payment_statuses);
	$('#applicableinfo2').attr('title',props.applicable_for_both_payment_statuses);
	$('#emailApproved').show();
	$('#emailPending').hide();
	
	
	
});


$('#applicableinfo1').tooltipster({
    fixedWidth:'100px',
    position: 'right',
    /* content:"" */
    });	
$('#applicableinfo2').tooltipster({
    fixedWidth:'100px',
    position: 'right',
    /* content:"" */
    });

$('#confirmationEmailInfotiper').tooltipster({
    fixedWidth:'100px',
    position: 'right',
    /* content:"<s:text  name='props.res_email_content_help_msg_lbl' />" */
    });	




var selectedvalue="";
var eventid='${eid}';
$('input.confirmationEmailOpt').on('ifChecked', function(event){
	showProcessing('forLoad');
	$('#totalContent').css('visibility','hidden');
	
   if(document.getElementById('completed').checked){
	   
	   selectedvalue=document.getElementById("completed").value;
	   $('#emailPending').hide();
	   $('#emailPending').prop( "disabled", true );
	   $('#emailApproved').show();
	   $('#emailApproved').prop( "disabled", false );
	  // alert(selectedvalue);
   }
   if(document.getElementById('pending').checked){
	   
	   selectedvalue=document.getElementById("pending").value;
	   $('#emailApproved').hide();
	   $('#emailApproved').prop( "disabled", true );
	   $('#emailPending').show();
	   $('#emailPending').prop( "disabled", false );
	   //alert(selectedvalue);
   }
   selectType = selectedvalue;
// window.location.href='/main/eventmanage/RegEmailSettings?eid='+eventid+'&templateType='+selectedvalue;
    $.ajax({
		type:'POST',
		url:"RegEmailSettings!confirmationType?eid="+eventid+"&templateType="+selectedvalue,
			success:function(result){
				//alert(JSON.parse(result).subject);
				hideProcessing();
				if('COMPLETED'==selectedvalue){
					$('#emailApproved').val(JSON.parse(result).content);
					$('#subjectEmail').val(JSON.parse(result).subject);
					$('#totalContent').css('visibility','visible');
				}else{
					$('#emailPending').val(JSON.parse(result).content);
					$('#subjectEmail').val(JSON.parse(result).subject);
					$('#totalContent').css('visibility','visible');
				}
				
				//alert(result);
			}	
	}); 
 	
    });
    
    $('.regDiv').click(function(){
    	if($('#advanced').is(':hidden')){
    		$("#advanced").slideDown();
    		$("#changeIconEmail").find('.glyphicon').removeClass('original').addClass('down');
    	}else{
    		$("#advanced").slideUp();
    		 $("#changeIconEmail").find('.glyphicon').addClass('original').removeClass('down');
    	}
    });
//$(document).off('click', '#showhideDiv');

/* $(document).on('click',"#showhideDiv",function(){
    //var imgid=this.id;     
    $("#advanced").slideToggle(
    function(){
    	var a=document.getElementById(this.id).style.display;
        if(a=="none")
        $("#changeIconEmail").html('<i class="fa fa-chevron-circle-down" style="cursor:pointer" id="showhideDiv"> </i>');
        else
       $("#changeIconEmail").html('<i class="fa fa-chevron-circle-up" style="cursor:pointer" id="showhideDiv"> </i>');
    }
    );    
}); */



jQuery(function(){
	 jQuery('input.buyer').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 jQuery('input.eventmanager').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 jQuery('input.pdfticketAppro').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 jQuery('input.pdfticketPend').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
});


$(function(){
	 $('input.confirmationEmailOpt').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	
});


$("#submitBtn").click(function(){
	var idContent='';
	if($('#emailPending').is(':hidden'))
		idContent= 'emailApproved';
	else
		idContent='emailPending';
	
	 var formname = "confirmationemailform";
	 	var divname = "confirmationemailStatusMsg";
	 	selectQuestionsList();
	 	var allData = $('#'+formname+' :input[name!="regEmailSettingsData.tempalteContent"]').serialize()+'&'+$.param( {'regEmailSettingsData.tempalteContent':$('#'+idContent).val()} );
	 	$.ajax( {
	 		   type: "POST",
	 		   url: 'RegEmailSettings!save',
	 		   data: allData,
	 		   /* data: $("#"+formname).serialize(), */
	 		   success: function( t ) {
	 			  if(!isValidActionMessage(t)) return;
	 				$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
	 				notification(t,"success");
	 				/* $('#notification-holder').html('');
	 				$("#"+divname).html(t);
	 			  	$('.alert-success').show();
	 			  	$("#"+divname).addClass('alert alert-success');
	 				$('#gap').html('<br/>'); */
	 			}
	 	 });		
});




$("#previewBtn").click(function(){ 
	 getContentPreview();	 
});


function getContentPreview(){
	 selectQuestionsList();
	 
	 if($('#completed').is(':checked')){
		 $('#emailPending').hide();
		   $('#emailPending').prop( "disabled", true );
		   $('#emailApproved').show();
		   $('#emailApproved').prop( "disabled", false );
	 }else{
		 $('#emailApproved').hide();
		   $('#emailApproved').prop( "disabled", true );
		   $('#emailPending').show();
		   $('#emailPending').prop( "disabled", false );
	 }
	   
		var url="RegEmailSettings!preview";
		

		
		
		$.ajax({
	    	 type: "POST",
	 		   url: url,
	 		   data: $("#confirmationemailform").serialize(),	       	
	 		   success: function( t ) {
	 			  if(!isValidActionMessage(t)) return;
	 			  $('#myModal').on('show.bs.modal', function () {
	 		        	$('#myModal .modal-title').html('<span style="color: rgb(176, 176, 176); font-size: 22px;">'+props.res_preview_popup_header_lbl+'</span>');	 		        		           	
	 		        	$('#myModal .modal-body').html(t);	
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
		
		
		/* 
	    $.ajax({
	    	 type: "POST",
	 		   url: url,
	 		   data: $("#confirmationemailform").serialize(),	    	
	 		   success: function( t ) {
					//alert(t);	
	 			  $('#myModal').on('show.bs.modal', function () {
	 					var eventid=document.getElementById("eid").value;  
	 					//alert(eventid);		    
	 		        	$('#myModal .modal-header').html('<button type="button" class="close" data-dismiss="modal">&times;</button><h3 class="section-main-header">Confirmation Email Preview</h3>');
	 		        	$('#myModal .modal-footer').removeClass('modal-footer');		           	
	 		        	$('#myModal .modal-body').html(t);		      	
	 		        	$('iframe#popup').attr("style","height:745px");
	 				        });
	 		     
	 		     $('#myModal').modal('show');   
	 		  }  	
	    	
	    });
	  */
	 
 
	
}



$("#resetBtn").click(function(){
	 var eid = "${eid}";
	 var type = "${powerType}";
	 var selectedvalue="";
	
	 if($('#completed').is(':checked')){
		 selectedvalue = "COMPLETED";
	 }else{
		 selectedvalue = "PENDING";
	 }
	 //alert(selectedvalue);
	 agree=props.res_email_reset_warning_lbl;
	 bootbox.confirm('<h3>'+props.cps_reset_mail_content+'</h3>'+agree, function (result) {
	     	if (result){
	     		if(type=='RSVP'){ 
					selectedvalue = "COMPLETED";
					var url="RegEmailSettings!resetEmailSettings?eid="+eid+"&powerType="+type+"&templateType="+selectedvalue;
					//alert(url);
				}else
					var url ="RegEmailSettings!resetEmailSettings?eid="+eid+"&powerType="+type+"&templateType="+selectedvalue;
	     		$.ajax({
					type:'POST',
					url : url,
						success:function(result){
							  if(!isValidActionMessage(result)) return;
							
							//alert(JSON.parse(result).content);
							
							if('COMPLETED'==selectedvalue){ 
								$('#emailApproved').val('');
								$('#emailApproved').val(JSON.parse(result).content);
							}else{	
								$('#emailPending').val('');
								$('#emailPending').val(JSON.parse(result).content);
							}
							
							$('#confirmationpageStatusMsg').hide();
							$('#notification-holder').html('');
							$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
							notification(props.res_email_reset_lbl,"success");
							//$('#confirmationData').html(JSON.parse(result).content);
					//	parent.hideProcessing();
							//alert(result);
						}	
				});
		}
		 });
});


function selectQuestionsList(){
    var parent ='';
 	if(document.getElementById("attendeeqlist"))
 	parent=document.getElementById("attendeeqlist");
	parent.innerHTML  = "";
	var selectBox='';
	var buyselectBox='';
	if(document.getElementById("selattribs")){
	selectBox = document.getElementById("selattribs");	
	for (var i = 0; i < selectBox.options.length; i++) {
		var obj1 = createHiddenTextElement(i,"value",selectBox.options[i].text);			
		var obj2 = createHiddenTextElement(i,"key",selectBox.options[i].value);
		parent.appendChild(obj1);
		parent.appendChild(obj2);
	}
	}
	if(document.getElementById("buyselattribs")){
	buyselectBox=document.getElementById("buyselattribs");	
	for(var i=0;i<buyselectBox.options.length;i++){
	   var obj3 = createHiddenTextElement(i,"value","buyer_q_"+buyselectBox.options[i].text);			
		var obj4 = createHiddenTextElement(i,"key","buyer_q_"+buyselectBox.options[i].value);
		parent.appendChild(obj3);
		parent.appendChild(obj4);
	} 
	}
	if(document.getElementById("selattribs"))
	document.getElementById("selattribs").selectedIndex = -1
	if(document.getElementById("attribs"))
	document.getElementById("attribs").selectedIndex = -1
	if(document.getElementById("buyselattribs"))
	document.getElementById("buyselattribs").selectedIndex = -1
	if(document.getElementById("buyattribs"))
	document.getElementById("buyattribs").selectedIndex = -1
	
}

function createHiddenTextElement(index,etype,val){
	var pname=document.createElement("input");
	pname.type="hidden";
	pname.name="optionsList["+index+"]."+etype;	
	pname.value=val;
	return pname;
}



/* move Questions one section to another section start */
function moveTransactionQRight(){	
	moveOption(document.confirmationemailform.buyattribs,document.confirmationemailform.buyselattribs);
}
function moveTransactionQLeft(){
	moveOption(document.confirmationemailform.buyselattribs,document.confirmationemailform.buyattribs);
}
function moveRight(){
	moveOption(document.confirmationemailform.attribs, document.confirmationemailform.selattribs);
}
function moveLeft(){
	moveOption(document.confirmationemailform.selattribs, document.confirmationemailform.attribs);
}
function moveOption(theSelFrom,theSelTo)
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
      if(theSelFrom.options[i].text=="Attendee Name"){
    	  return;
      }
      selectedText[selectedCount] = theSelFrom.options[i].text;
      selectedValues[selectedCount] = theSelFrom.options[i].value;
      deleteOption(theSelFrom, i);
      selectedCount++;
    }
  }
  for(i=selectedCount-1; i>=0; i--)
  {
    addOption(theSelTo, selectedText[i], selectedValues[i]);
  }
  
  if(NS4) history.go(0);
}
	
/* move Questions one section to another section end */	
	
	 function showProcessing(divid){
     var html='<div id="loading"><center><img src="../images/ajax-loader.gif"></center></div>';
     $('#'+divid).after(html);
 }

 function hideProcessing(){
     $('#loading').remove();
 }
	
</script>
