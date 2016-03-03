<%@taglib uri="/struts-tags" prefix="s"%>
<style>
.hr-color{
    border-top: 1px solid #e2e2e2 !important;
}
</style>
<div class="col-md-12">
	<div class="row"><div id="confirmationpageStatusMsg"></div></div>
	<s:form id="confirmationpageform" name="confirmationpageform"  method="post" theme="simple" >
	<s:hidden name="eid" id="eid"></s:hidden>
    <s:hidden name="type"></s:hidden>
<!--	<div class="row">		
	<s:if test="%{powertype=='RSVP'}">
	<s:if test="%{currentLevel=='90'}">
	<div class="alert alert-info hidden-xs">
	<i class="fa fa-info-circle"></i>&nbsp; 
	<s:text name="cps.RSVP.upgrade.msg1.lbl"></s:text> <a href="/main/pricing"><s:text name="cps.RSVP.upgrade.msg2.lbl"></s:text></a> <s:text name="cps.RSVP.upgrade.msg3.lbl"></s:text> </div>
	</s:if>
	<div class="alert alert-info hidden-xs">
	<i class="fa fa-info-circle"></i>&nbsp; 
	<s:text name="cps.RSVP.help.msg.lbl"></s:text></div>
	</s:if>
	<s:else>
	<div class="alert alert-info hidden-xs">
	<i class="fa fa-info-circle"></i>&nbsp; 
	<s:text name="cps.ticketing.help.msg.lbl"></s:text></div>
	</s:else>
	</div>  -->


		<div class="row">
			<%-- <span class="section-main-header"><s:text name="cps.section.header"></s:text></span> --%>
			<s:if test="%{powertype=='RSVP'}">
				<div style="height:10px;"></div>
			</s:if>
			<s:else>
			 <div class="row">
			 <div class="col-sm-12">
			 
			 	<div class="col-sm-4 row" style="margin-top: 2px;"><span class=""><s:text name="cps.confirmation.page.content.rb.lbl"></s:text></span>&nbsp;&nbsp;<i
							class="fa fa-info-circle info" style="cursor: pointer"
							id="confirmationPageInfo"
							></i>
			 	</div>
			 	<div class="col-sm-8">
			 		<input type="radio" name="templateType" class="confirmationPageOpt"  id="completed" onClick="getTemplate()" <s:if test="%{templateType=='' || templateType=='COMPLETED'}">checked="checked"</s:if> value="COMPLETED"/>&nbsp;<s:text name="cps.approved.rb.lbl"></s:text>
					&nbsp;&nbsp;&nbsp;
					<input type="radio" name="templateType" class="confirmationPageOpt"  id="pending" onClick="getTemplate()" <s:if test="%{templateType=='PENDING'}">checked="checked"</s:if> value="PENDING"/>&nbsp;<s:text name="cps.pending.rb.lbl"></s:text>
			 	</div>
			 </div>
				 <div id="forLoad"></div>
			</div><br>
			</s:else>
	
			<div id="totalContent">
				<s:textarea name="confirmationPageSettingsData.content" id="confirmationCompleted" rows="25"
					cols="100" theme="simple" cssClass="form-control" style="display: none;"></s:textarea>
					
				<s:textarea name="confirmationPageSettingsData.content" id="confirmationPending" rows="25"
					cols="100" theme="simple" cssClass="form-control" style="display: none;"></s:textarea>  
			</div>
			<s:if test='%{chkbuyerquestions=="Y" || chkattendeequestions=="Y"}'>
			
			<div class="col-md-12 row">
			<hr><span class="openDiv" style="cursor:pointer;"><s:text name="cps.more.options.lbl"></s:text><span id="changeIcon" class="small arrow-gap"><i class="glyphicon glyphicon-menu-right"></i></span></span>
				<%-- <br><span class="section-header"> <s:text name="cps.more.options.lbl"></s:text></span> &nbsp;<span id="changeIcon"><i class="fa fa-chevron-circle-down" style="cursor:pointer" id="showhide"> </i></span> --%>
			</div>
			<div class="col-md-12 row" id="advanced" style='display: none;'>
			<div class="row">
				<div class="background-options sm-font"><jsp:include page="/eventmanage/eventpagecontent/confirmscreenattribs.jsp"></jsp:include></div>
				</div>
			</div>
				<br> 
			</s:if>
		</div>
	 <br>
	 <div class="row">
	 	<div class="col-md-12 text-center"><br>
	 	<input type="button" id="submitBtn" value="<s:text name="global.save.btn.lbl"></s:text>" class="btn btn-primary"> &nbsp;
	 	<input type="button" id="resetBtn" value="<s:text name="global.reset.btn.lbl"></s:text>" class="btn btn-primary"> &nbsp;
	 	<input type="button" name="preview" value="<s:text name="global.preview.lnk.lbl"></s:text>" id="previewBtn" class="btn btn-primary"/> 	
	 	</div>	 	
	 </div> 
	
	</s:form>
	</div>
<script>
var selectedvalue="";
var selectType="";
var eventid='${eid}';
$(document).ready(function(){	
	$('#confirmationPageInfo').attr('title',props.cpa_tooltipster_help_msg_lbl);
	$('#applicableinfo1').attr('title',props.applicable_for_both_payment_statuses);
	$('#applicableinfo2').attr('title',props.applicable_for_both_payment_statuses);
	$('#confirmationCompleted').show();
	$('#confirmationPending').hide();
});
$('input.confirmationPageOpt').on('ifChecked', function(event){
	 showProcessing('forLoad');
	 $('#totalContent').css('visibility','hidden')
	 
   if(document.getElementById('completed').checked){
	   selectedvalue=document.getElementById("completed").value;
	   
	   
	   $('#confirmationPending').hide();
	   $('#confirmationPending').prop( "disabled", true );
	   $('#confirmationCompleted').show();
	   $('#confirmationCompleted').prop( "disabled", false );
	   
	   
	  // alert(selectedvalue);
   }
   if(document.getElementById('pending').checked){
	   selectedvalue=document.getElementById("pending").value;
	   $('#confirmationCompleted').hide();
	   $('#confirmationCompleted').prop( "disabled", true );
	   $('#confirmationPending').show();
	   $('#confirmationPending').prop( "disabled", false );
	   
	   //alert(selectedvalue);
   }
   selectType = selectedvalue;
 //window.location.href='/main/eventmanage/ConfirmationPageSettings?eid='+eventid+'&templateType='+selectedvalue;
	  $.ajax({
		type:'POST',
		url:"ConfirmationPageSettings!confirmationType?eid="+eventid+"&templateType="+selectedvalue,
			success:function(result){
				if('COMPLETED'==selectedvalue){
					$('#confirmationCompleted').val(JSON.parse(result).content);
					$('#totalContent').css('visibility','visible');
				}else{
					$('#confirmationPending').val(JSON.parse(result).content);
					$('#totalContent').css('visibility','visible');
				}
				hideProcessing();
				//alert(result);
			}	
	});  
});


//$("#showhide").off();
//$(document).off('click', '#showhide');



$('.openDiv').click(function(){
	if($('#advanced').is(':hidden')){
		$("#advanced").slideDown();
		$("#changeIcon").find('.glyphicon').removeClass('original').addClass('down');
	}else{
		$("#advanced").slideUp();
		 $("#changeIcon").find('.glyphicon').addClass('original').removeClass('down');
	}
});


/*  $(document).on('click',".openDiv",function(){
     //var imgid=this.id;     
     $("#advanced").slideToggle(
     function(){
     	var a=document.getElementById(this.id).style.display;
         if(a=="none"){
        	 $("#changeIcon").find('.glyphicon').addClass('original').removeClass('down');
         }
         else{
        	 $("#changeIcon").find('.glyphicon').removeClass('original').addClass('down');
         }
     }
     );    
 }); */
 
 $("#submitBtn").click(function(){
	var idContent='';
	if($('#confirmationPending').is(':hidden'))
		idContent= 'confirmationCompleted';
	else
		idContent='confirmationPending';

	
	var formname = "confirmationpageform";
	 	var divname = "confirmationpageStatusMsg";
	 	selectQuestionsList();
	 	
	 	var allData = $('#'+formname+' :input[name!="confirmationPageSettingsData.content"]').serialize()+'&'+$.param( {'confirmationPageSettingsData.content':$('#'+idContent).val()} );
	 	$.ajax( {
	 		  	type: "POST",
	 			url: 'ConfirmationPageSettings!save',	 		  
	 		   data: allData,
	 		   success: function( t ) {
	 			  if(!isValidActionMessage(t)) return;
	 			   	$('#notification-holder').html('');
	 				$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
	 				notification(t,"success");
	 				
	 				
	 			  	/* $("#"+divname).html(t);
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
		$('#confirmationPending').hide();
		   $('#confirmationPending').prop( "disabled", true );
		   $('#confirmationCompleted').show();
		   $('#confirmationCompleted').prop( "disabled", false );
	}else{
		$('#confirmationCompleted').hide();
		   $('#confirmationCompleted').prop( "disabled", true );
		   $('#confirmationPending').show();
		   $('#confirmationPending').prop( "disabled", false );
	}
	 var url="ConfirmationPageSettings!preview";
	    
	 
	 $.ajax({
    	 type: "POST",
 		   url: url,
 		   data: $("#confirmationpageform").serialize(),	       	
 		   success: function( t ) {
 			  if(!isValidActionMessage(t)) return;
 			  $('#myModal').on('show.bs.modal', function () {
 		        	$('#myModal .modal-title').html('<span style="color: rgb(176, 176, 176); font-size: 22px;">'+props.cps_page_preview+'</span>');	 		        		           	
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
	 
	 
	 
	 /* $.ajax({
	    	 type: "POST",
	 		   url: url,
	 		   data: $("#confirmationpageform").serialize(),	    	
	 		   success: function( t ) {
					//alert(t);	
	 			  $('#myModal').on('show.bs.modal', function () {
	 					var eventid=document.getElementById("eid").value;  
	 					//alert(eventid);		    
	 		        	$('#myModal .modal-title').html('<h3 class="section-main-header">Confirmation Page Preview</h3>');	 		        		           	
	 		        	$('#myModal .modal-body').html(t);		      	
	 		        	$('iframe#popup').attr("style","height:745px");
	 			});	 		     
	 		     $('#myModal').modal('show');        
	 			    $('#myModal').modal({
	 			        backdrop: 'static',
	 			        keyboard: false,
	 			        show:false
	 			    }); 			    
	 		
	 		   }  	
	    	
	    }); */
	 
	 
  
	
 }
 
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
 $('#confirmationPageInfo').tooltipster({
     	position: 'right',
	    fixedWidth:'100px',
	       // content: $('<span>If you have selected manual approval for Other Payment Method in Payment Settings,<br> you will get a Pending status content in the confirmation page. <br>Otherwise you will get a content with status Completed.</span>'),
	    });
 
  //$('#confirmationPageInfo').attr('title',props.cps_help_content_msg1); 
 
$(function(){
	 $('input.confirmationPageOpt').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	
});
 $("#resetBtn").click(function(){
	 var eid = "${eid}";
	 var type = "${type}";
	 var selectedvalue ="";
	 

		if($('#completed').is(':checked')){
			 selectedvalue = "COMPLETED";
			
		 }else{
			
			 selectedvalue = "PENDING";
		 }
	 var agree=props.cps_confirmation_alert_msg;
	 bootbox.confirm('<h3>'+props.cps_reset_page_content+'</h3>'+agree, function (result) {
	     	if (result){
	     		
	     		
		//parent.loadingPopup();
			if(type=='rsvp'){
				selectedvalue = "COMPLETED";
				var url="ConfirmationPageSettings!reset?eid="+eid+"&type="+type;
				//alert(url);
			}
			else
				var url="ConfirmationPageSettings!reset?eid="+eid+"&type="+type+"&templateType="+selectedvalue;
				
		
		//var url="ConfirmationPageSettings!reset?eid="+eid+"&powerType="+type+"&templateType="+selectedvalue;
		 $.ajax({
				type:'POST',
				url : url,
					success:function(result){
						 if(!isValidActionMessage(result)) return;
						//alert(JSON.parse(result).content);
						
						if('COMPLETED'==selectedvalue){ 
							$('#confirmationCompleted').val('');
							$('#confirmationCompleted').val(JSON.parse(result).content);
						}else{	
							$('#confirmationPending').val('');
							$('#confirmationPending').val(JSON.parse(result).content);
						}
						
						$('#confirmationpageStatusMsg').hide();
						$('#notification-holder').html('');
						$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
						notification(props.cps_reset_msg_lbl,"success");
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
 	moveOption(document.confirmationpageform.buyattribs,document.confirmationpageform.buyselattribs);
 }
 function moveTransactionQLeft(){
 	moveOption(document.confirmationpageform.buyselattribs,document.confirmationpageform.buyattribs);
 }
 function moveRight(){
 	moveOption(document.confirmationpageform.attribs, document.confirmationpageform.selattribs);
 }
 function moveLeft(){
 	moveOption(document.confirmationpageform.selattribs, document.confirmationpageform.attribs);
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
