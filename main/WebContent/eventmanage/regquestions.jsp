<%@taglib uri="/struts-tags" prefix="s"%>
    <script>
        window.resizeIframe = function() {
        	if(global_eb_condQnLevel=='TransactionLevel'){
        		var mydiv = $("#subqns_iframe").contents().find("#subqns_content");
                var hgt     = mydiv.height()+50;
        		var obj=document.getElementById('subqns_iframe');
        		obj.style.height = hgt + 'px';
        	}
        	else if(global_eb_condQnLevel=='TicketLevel'){
        		var mydiv = $("#tktsubqns_iframe").contents().find("#subqns_content");
                var hgt     = mydiv.height()+50;
        		var obj=document.getElementById('tktsubqns_iframe');
        		obj.style.height = hgt + 'px';
        	}
        }
        
        window.customResizeIframe=function(size){
        	var obj = document.getElementById('popup');
            var isize=obj.contentWindow.document.body.scrollHeight-size;
            obj.style.height = isize + 'px';
        }
    
        window.resizeIframePage = function() {
            var pageobj = window.top.document.getElementById('iframepage');
            pageobj.style.height = (pageobj.contentWindow.document.body.scrollHeight)+ 'px';
        }
        
		window.customResizeIframePage = function(size) {
            var pageobj = window.top.document.getElementById('iframepage');
            pageobj.style.height = (size)+ 'px';
        }
		
		function resizeIframePopup(size,ope){
			
			if(global_eb_condQnLevel=='TransactionLevel'){
        		var obj=document.getElementById('subqns_iframe');
        		var totalht=0;
    			if(ope=='remove') 
    				totalht=obj.contentWindow.document.body.scrollHeight-size;
    			else totalht=obj.contentWindow.document.body.scrollHeight+size;
                obj.style.height =  totalht+ 'px';
        	}
        	else if(global_eb_condQnLevel=='TicketLevel'){
        		var obj=document.getElementById('tktsubqns_iframe');
        		var totalht=0;
    			if(ope=='remove') 
    				totalht=obj.contentWindow.document.body.scrollHeight-size;
    			else totalht=obj.contentWindow.document.body.scrollHeight+size;
                obj.style.height =  totalht+ 'px';
        	}
			
		}
		
		/* function statusMessageQuestions(message, type) {
		    var html = '<div class="alert alert-' + type + ' alert-dismissable page-alert" style="margin-bottom:0px !important;margin-top:15px;">';    
		    html += '<button type="button" class="close close-notification"><span aria-hidden="true">x</span><span class="sr-only">Close</span></button>';
		    html += message;
		    html += '</div>';   
		    $("#questions_notification_holder").html('');
		    $("#questions_notification_holder").show();
		    $(html).hide().prependTo('#questions_notification_holder').slideDown();
		    setTimeout(function(){ 
		    	if($('#questions_notification_holder').is(":visible")){
		    		$('#questions_notification_holder').slideUp(); 
    			}
		    }, 5000);
		}; */
    </script>

<script src="../js/eventmanage/questions.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>
<link type="text/css" rel="stylesheet" href="../css/questions.css" />
<div class="row sticky-out-button">				
   <div class="col-md-12">
    <div class="pull-right">
    <div style="position: relative;" id="transaction_qn_div">
    <button class="btn btn-primary"    onclick="enabletransactionquestion();">+&nbsp;<s:text name="qn.btn.lbl"/></button>  <!-- id="addtransactionquestion" -->
    </div>
    <div style="display:none;position: relative;" id="ticket_qn_div" >
    <button class="btn btn-primary"    onclick="enableticketqno();">+&nbsp;<s:text name="qn.btn.lbl"/></button>    <!-- id="addticketquestion" -->
    </div>
    </div>                            
    </div>
    <div style="clear:both"></div>
</div>
<div class="white-box no-button" style="background-color:#fff;">
<div class="row bottom-gap">
<div class="col-md-12">
	 <div class="pull-left" style="">
       <div data-toggle="buttons" class="center btn-group btn-toggle" id="taggling-menu">
			<label class="qninfo btn-default btn no-radius labels" id="transaction_block">
				<input type="radio" value="transaction" name="questionsinfo" id="transaction" class="qninfo_radiotype"><s:text name="qn.buyer.info.lbl"/>
			</label>
			<label class="qninfo btn-active btn no-radius labels" id="ticket_block">
				<input type="radio" value="ticket" name="questionsinfo" id="ticket" class="qninfo_radiotype">
				<s:if test="%{powertype=='Ticketing'}"><s:text name="qn.ticket.info.lbl"/></s:if><s:else><s:text name="qn.attendee.info.lbl"/></s:else>
			</label>
			
		</div>
	</div> 
</div>
</div>

<%-- <div class="row">	
<div class="col-md-12">	
<s:if test="%{powertype=='Ticketing'}">
	<div class="alert alert-info hidden-xs" id="tran_qn_help_msg" style="display:block;margin-bottom: 15px;margin-top: 15px;">
	<i class="fa fa-info-circle"></i>&nbsp;<s:text name="qn.buyer.level.help.msg"/></div>
	<div class="alert alert-info hidden-xs" id="tkt_qn_help_msg" style="display:none;margin-bottom: 15px;margin-top: 15px;">
	<i class="fa fa-info-circle"></i>&nbsp;<s:text name="qn.ticket.level.help.msg"/></div>
</s:if>
<s:else>
	<div class="alert alert-info hidden-xs" id="tran_qn_help_msg" style="display:block;margin-bottom: 15px;margin-top: 15px;">
	<i class="fa fa-info-circle"></i>&nbsp;<s:text name="qn.rsvp.buyer.level.help.msg"/></div>
	<div class="alert alert-info hidden-xs" id="tkt_qn_help_msg" style="display:none;margin-bottom: 15px;margin-top: 15px;">
	<i class="fa fa-info-circle"></i>&nbsp;<s:text name="qn.rsvp.attendee.level.help.msg"/></div>
</s:else>
</div>
</div> --%>
<div class="row">
	<div class="col-md-12">
		<div id="transactionqns">
			<s:if test="%{powertype=='Ticketing'}">
				<ul class="list-group" style="margin-bottom: 0px !important;padding-left:0px !important;" id="transaction_basic_qns">
					<li class="lists listsLabel hover-item" style="border-top: 0px;padding-bottom: 17px;"><s:text name="qn.buyer.first.name.lbl"/></li>
					<li class="lists listsLabel hover-item" style="padding-bottom: 17px;"><s:text name="qn.buyer.last.name.lbl"/></li>
					<li class="lists listsLabel hover-item" style="padding-bottom: 17px;"><s:text name="global.email.lbl"/></li>
					<li class="lists hover-item" id="lasttransaction" style="padding-top: 12px;padding-bottom: 4px;padding-left: 6px;">
						<s:set name="dtype" value="displayPhoneYN" />
						<div class="row">
							<div class="col-md-5 col-sm-5 bottom-gap"><s:text name="global.phone.lbl"/></div>							
							<div class="col-md-5 col-sm-4 sm-font">
								<div class="btn-group btn-multiple btn-group-xs bottom-gap" data-toggle="buttons">
								<label id="Required_block"
									onclick="phoneSettings('Required','Optional','requiredoptional','customattribute.isRequired');"
									class="requiredoptional  col-xs-6 col-md-6 col-sm-6 btn <s:if test='%{customattribute.isRequired=="Required"}'>btn-default active</s:if><s:else>btn-active</s:else>">
									<input id="Required" name="customattribute.isRequired"
									type="radio" value="Required"
									<s:if test='%{#dtype=="Y" && customattribute.isRequired=="Required"}'>checked=true</s:if>
									<s:if test='%{#dtype=="N"}'>disabled=true</s:if>><s:text name="global.required.lbl"/>
								</label> <label  id="Optional_block"
									onclick="phoneSettings('Optional','Required','requiredoptional','customattribute.isRequired');"
									class="requiredoptional btn  col-xs-6 col-md-6 col-sm-6  <s:if test='%{customattribute.isRequired=="Optional"}'>btn-default active</s:if><s:else>btn-active</s:else>">
									<input id="Optional" name="customattribute.isRequired"
									type="radio" value="Optional"
									<s:if test='%{customattribute.isRequired=="Optional"}'>checked=true</s:if>><s:text name="global.optional.lbl"/>
								</label>
								</div>
							</div>				
						
                    
							<%-- <span class="extramarginques"> --%>
							<div class="col-md-2 col-sm-3 btn-group btn-group-xs btn-multiple sm-font" data-toggle="buttons">
								<label id="Y_block"
									onclick="phoneSettings('Y','N','phoneshowhide','displayPhoneYN');"
									class="phoneshowhide btn <s:if test='%{#dtype=="Y"}'>btn-default active</s:if><s:else>btn-active</s:else>">
									<input id="Y" name="displayPhoneYN" type="radio" value="Y"
									onclick="" <s:if test='%{#dtype=="Y"}'>checked=true</s:if>><s:text name="global.show.lbl"/>
								</label> <label id="N_block"
									onclick="phoneSettings('N','Y','phoneshowhide','displayPhoneYN');"
									class="phoneshowhide btn <s:if test='%{#dtype=="N"}'>btn-default active</s:if><s:else>btn-active</s:else>">
									<input id="N" name="displayPhoneYN" type="radio" value="N"
									<s:if test='%{#dtype=="N"}'>checked=true</s:if>><s:text name="global.hide.lbl"/>
								</label>
								
							</div>
							<%-- </span> --%>
						</div>
					</li>
				</ul>
			</s:if>
			<div id='noInfoMsg' class="not-found" style="display:none;"><s:text name="qn.no.buyer.info.msg.lbl"/></div>
			<ul class="list-group example" id="transactionquestions" style="padding-left:0px !important;"></ul>
			<div id='questionbox' style="display:none;"></div>
		</div>
		<div id="ticketqns" style="display: none;">
			<s:if test="%{powertype=='Ticketing'}">
				<ul class="list-group" style="margin-bottom: 0px !important;padding-left:0px !important;" id="ticket_basic_qns">
					<li class="lists listsLabel hover-item each-tqn" style="border-top: 0px;" data-questionid="-5" id="basicpf-5">
					<div class="row">
						<div class="col-md-5 col-sm-5"><s:text name="qn.attendee.first.name.lbl"/></div>
						<div class="col-md-4 col-sm-4 sm-font">
							<label class="shown-to" style="cursor: pointer;">
							<span id="-5_seltqns_lbl"><s:text name="qn.shown.to.lbl"/></span><span style="margin-top: 3px;" id="-5_seltqns_img" class="glyphicon glyphicon-menu-right original arrow-gap"></span></label>
						</div>
						<div class="col-md-2 col-sm-2 sm-font links-div">
							<span class="hidden manage-tqn pull-right">
								<a href="javascript:;" class="selectTickets"><s:text name="global.edit.lnk.lbl"/></a>
							</span>
						</div>
						<!-- <div class="col-md-1 col-sm-1 tickets-popup"></div> -->
						</div>
						<div class="background-options sm-font" id="-5_seltqns" style="display: none;"></div>
					</li>
					<li class="lists listsLabel hover-item each-tqn" data-questionid="-6" id="basicpf-6">
					<div class="row">
						<div class="col-md-5 col-sm-5"><s:text name="qn.attendee.last.name.lbl"/></div> 
						<div class="col-md-4 col-sm-4 sm-font">
						<label class="shown-to" style="cursor: pointer;">
						<span id="-6_seltqns_lbl"><s:text name="qn.shown.to.lbl"/></span><span style="cursor: pointer;  margin-top: 3px;" id="-6_seltqns_img" class="glyphicon glyphicon-menu-right original arrow-gap"></span></label>
					</div>
					<div class="col-md-2 col-sm-2 sm-font links-div">
							<span class="hidden manage-tqn pull-right">
								<a href="javascript:;" class="selectTickets"><s:text name="global.edit.lnk.lbl"/></a>
							</span>
					</div>
					<!-- <div class="col-md-1 col-sm-1 tickets-popup"></div> -->
					</div>
					<div class="background-options sm-font" id="-6_seltqns" style="display: none;"></div>
					</li>
					<li class="lists listsLabel hover-item each-tqn" data-questionid="-7" id="basicpf-7">
					<div class="row">
					<div class="col-md-5 col-sm-5"><s:text name="global.email.lbl"/></div> 
					<div class="col-md-4 col-sm-4 sm-font">
					<label class="shown-to" style="cursor: pointer;"><span id="-7_seltqns_lbl"><s:text name="qn.shown.to.lbl"/></span><span style="cursor: pointer;  margin-top: 3px;" 
					id="-7_seltqns_img" class="glyphicon glyphicon-menu-right original arrow-gap"></span></label>
					</div>
					<div class="col-md-2 col-sm-2 sm-font links-div">
							<span class="hidden manage-tqn pull-right">
								<a href="javascript:;" class="selectTickets"><s:text name="global.edit.lnk.lbl"/></a>
							</span>
					</div>
					<!-- <div class="col-md-1 col-sm-1 tickets-popup"></div> -->
					</div>
					<div class="background-options sm-font" id="-7_seltqns" style="display: none;"></div>
					</li>
					<li class="lists listsLabel hover-item each-tqn" data-questionid="-8" id="basicpf-8">
					<div class="row">
						<div class="col-md-5 col-sm-5"><s:text name="global.phone.lbl"/></div> 
						<div class="col-md-4 col-sm-4 sm-font">
							<label class="shown-to" style="cursor: pointer;"><span id="-8_seltqns_lbl"><s:text name="qn.shown.to.lbl"/></span><span style="cursor: pointer;  margin-top: 3px;" 
							id="-8_seltqns_img" class="glyphicon glyphicon-menu-right original arrow-gap"></span></label>
						</div>
						<div class="col-md-2 col-sm-2 sm-font links-div">
							<span class="hidden manage-tqn pull-right">
								<a href="javascript:;" class="selectTickets"><s:text name="global.edit.lnk.lbl"/></a>
							</span>
						</div>
						<!-- <div class="col-md-1 col-sm-1 tickets-popup"></div> -->
					</div>
					<div class="background-options sm-font" id="-8_seltqns" style="display: none;"></div>
					</li>
				</ul>
			</s:if>
			<s:else>
				<ul class="list-group" style="margin-bottom: 0px !important;padding-left:0px !important;" id="rsvp_tktbasic_qns">
					<li class="lists hover-item each-tqn" style="padding: 14px 6px !important;border-top: 0px;"><s:text name="qn.attendee.first.name.lbl"/></li>
					<li class="lists hover-item each-tqn" style="padding: 14px 6px !important;"><s:text name="qn.attendee.last.name.lbl"/></li>
					<li class="lists hover-item each-tqn" style="padding: 14px 6px !important;"><s:text name="global.email.lbl"/></li>
				</ul>
			</s:else>
			<ul class="list-group" id="ticketquestions" style="padding-left:0px !important;"></ul>
			<div id='tktquestionbox' style="display:none;"></div>
		</div>
	</div>
</div>
</div>
<script>
var eid='${eid}';
var powertype='${powertype}';
</script>
<script>
var global_eb_savewarning=false;
var global_eb_condQnLevel='';
var global_eb_counter=6;
var global_eb_buyer_purpose='add';
var global_eb_ticket_purpose='add';
var transactionQuestionData=${transcationQuestionsJsonData};
var ticketQuestionData=${responseQuestionJsonData};
var ticketsJson=${ticketsJson};
var questionTicketJson=${questionTicketJson};
var transactionObj=transactionQuestionData;
var ticketObj=ticketQuestionData;
$(document).ready(function(){
	arrangeQuestions(transactionObj);
	arrangeTktQuestions(ticketObj);
});
$('#myModal').modal({
	backdrop: 'static',
	keyboard: false,
	show:false
});
$('#myModal').on('hide.bs.modal', function () {
	$('iframe#popup').attr("src",'');
	$('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" style="height:440px" frameborder="0" onload="modalOnLoad()"></iframe>');
	if(global_eb_savewarning && global_eb_condQnLevel=='TransactionLevel')
		$("#transavewarning").show();
	if(global_eb_savewarning && global_eb_condQnLevel=='TicketLevel')
		$("#tktsavewarning").show();
	//console.log(global_eb_savewarning+' - '+global_eb_condQnLevel);
});

function enabletransactionquestion(){	
	var pwtype="${powertype}";
	
	if(pwtype=='RSVP')
	specialFee(eid,'150','questions','RSVP');
	else
	specialFee(eid,'300','questions','Ticketing');	
}

function enableticketqno(){	
	var pwtype="${powertype}";
	
	if(pwtype=='RSVP')
	specialFee(eid,'150','tktquestions','RSVP');
	else
	specialFee(eid,'300','tktquestions','Ticketing');	
}



function closePopup(){
	 parent.$('#myModal').modal('hide');    
}

$(function() {
	var flag=false;
	
	$(document).on("click",".shown-to",function(){
		if(flag) return;
		var liObj=$(this).parents('li');
		var qnid=$(liObj).data('questionid');
		var divid=qnid+'_seltqns';
		if($("#"+divid).is(":visible")){
			$(this).find("span#"+divid+"_img").removeClass("down").addClass("original");
        	$(this).find("span#"+divid+"_lbl").removeClass("highlighted-text");
        	$("#"+divid).slideUp();
		}else{
			$('#'+qnid+'_seltqns').empty();
			var selectedFlag=false;
			var selTkts="<ul>";
			$(ticketsJson).each(function(key,value){
				var found=$.inArray(value.tktId, questionTicketJson[qnid]) > -1;
				if(found){
			    	//$('<div>'+value.tktNm+'&nbsp;&nbsp;</div>').appendTo('#'+qnid+"_seltqns");
			    	selTkts+="<li class='li_disc'>"+value.tktNm+"</li>";
			    	selectedFlag=true;
				}
			});
			selTkts+="</ul>";
			if(selectedFlag)
				$(selTkts).appendTo('#'+qnid+"_seltqns");
			else $('<div>'+props.reg_qustn_no_tckts_lbl+'</div>').appendTo('#'+qnid+"_seltqns");
			$(this).find("span#"+divid+"_img").removeClass("original").addClass("down");
	     	$(this).find("span#"+divid+"_lbl").addClass("highlighted-text");
			$('#'+qnid+'_seltqns').slideDown();
		}
		
	});
	
	/* $(document).on('mouseenter','.shown-to',function(){
		//each-tqn
		if(flag) return;
		var liObj=$(this).parents('li');
		$('ul#event_tickets').empty();
		getEventTickets($(liObj).data('questionid'));
		$('input.popup-tickets').iCheck({
			checkboxClass : 'icheckbox_square-grey',
			radioClass : 'iradio_square-grey'
		});
		
		//var position=$(this).position();
		var position=$(liObj).position();
		
		
		if(($("#tickets_pop_up").height()/2)>position.top+51){//if popup is going beyond questions container
			$('#tickets_pop_up').css({top:-38,right:0});
			$("#tickets_pop_up .arrow-box-before").css({'top':(position.top+$(liObj).height()+8)-15,'border-width':$(liObj).height()-(($(liObj).height()/2)-2)});
			$("#tickets_pop_up .arrow-box-after").css({'top':(position.top+$(liObj).height()+8)-14,'border-width':$(liObj).height()-(($(liObj).height()/2)-1)});
		}
		else{	
			//console.log("bottom space::"+(($("#ticketqns div:first-child").height()-position.top)>$("#tickets_pop_up").height()));
			if(position.top>$("#tickets_pop_up").height() && !($("#ticketqns div:first-child").height()-position.top>$("#tickets_pop_up").height()) ){//if enough space is there at top and not there at bottom
				//console.log("have space at top");
				$('#tickets_pop_up').css({top:position.top+$(liObj).height()+10-$("#tickets_pop_up").height(),right:0});
				$("#tickets_pop_up .arrow-box-before").css({'top':($("#tickets_pop_up").height()-$(liObj).height())-15,'border-width':$(liObj).height()-(($(liObj).height()/2)-2)});
				$("#tickets_pop_up .arrow-box-after").css({'top':($("#tickets_pop_up").height()-$(liObj).height())-14,'border-width':$(liObj).height()-(($(liObj).height()/2)-1)});
			}
			else{
				//console.log("have bottom space too ");
				$('#tickets_pop_up').css({top:position.top-$("#tickets_pop_up").height()/2+10,right:0});			
				$("#tickets_pop_up .arrow-box-before").css({'top':($("#tickets_pop_up").height()/2)-15,'border-width':$(liObj).height()-(($(liObj).height()/2)-2)});
				$("#tickets_pop_up .arrow-box-after").css({'top':($("#tickets_pop_up").height()/2)-14,'border-width':$(liObj).height()-(($(liObj).height()/2)-1)});
			}
		}
		$('ul#event_tickets').attr("data-forquestion",$(liObj).data('questionid'));
		$('#tickets_pop_up').show();
	}); 
	 
	
	 $(document).on('mouseenter','#tickets_pop_up',function(){
		 ticketsPopupHoverEfects($(this).find("#event_tickets").attr("data-forquestion"),'enter');
	});
	 
	 $(document).on('mouseleave','#tickets_pop_up',function(){
		 ticketsPopupHoverEfects($(this).find("#event_tickets").attr("data-forquestion"),'leave');
	});
	
	 $(document).on('mouseleave','.eventmanage-main-content-body',function(){
		 $('#tickets_pop_up').hide();
	}); 
	 
	 $(document).on('mouseleave','.each-tqn',function(){
		 var isHovered = $('#tickets_pop_up').is(":hover");
		 if(!isHovered) $('#tickets_pop_up').hide();
	});
	 
	$(document).on('ifChecked','.popup-tickets',function(){
		attribSettingsForTickets($(this).attr('value'),$(this).data('attribid'),'true');
 	});
	
	$(document).on('ifUnchecked','.popup-tickets',function(){
		attribSettingsForTickets($(this).attr('value'),$(this).data('attribid'),'false');
 	});*/
	
	});
	
/* function ticketsPopupHoverEfects(qnid,purpose){
			var qidlabel="tktqnid_";
			if(qnid.includes("-")) qidlabel="basicpf";
			if(purpose=='enter'){
				 $('#'+qidlabel+qnid).addClass('backgroundclr');
				 $('#'+qidlabel+qnid).find('.manage-tqn').removeClass('hidden').addClass('visible');
				 $('#'+qidlabel+qnid).find('.manage-subqn').removeClass('visible').addClass('hidden');
				 $("#tickets_pop_up .arrow-box-after").css({'border-left-color':'#f6f6fa'});
			}else{
				$('#'+qidlabel+qnid).addClass('each-tqn').addClass('hover-item').removeClass('backgroundclr');
				$('#'+qidlabel+qnid).find('.manage-tqn').removeClass('visible').addClass('hidden');
				$('#'+qidlabel+qnid).find('.manage-subqn').removeClass('hidden').addClass('visible');
			}
} 
	
 function getEventTickets(qnid){
	if(ticketsJson.length==0){
		$('<li>No tickets added <br/>to the event</li>').appendTo('#event_tickets');
	}else{
		$(ticketsJson).each(function(key,value){
			var found=$.inArray(value.tktId, questionTicketJson[qnid]) > -1;
			var chk='';
			if(found) chk='checked="checked"';
			var html='';
		    $('<li><label>'+value.tktNm+'&nbsp;&nbsp;<input class="popup-tickets" type="checkbox"'+chk+' value="'+value.tktId+'" data-attribid="'+qnid+'" /></label>'+html+'</li>').appendTo('#event_tickets');
		});
	}
} 

 function attribSettingsForTickets(ticketId,attribId,isCheck){
	var url='ManageRegistrationForm!updateCustomAttribTickets?eid='+eid+'&attribId='+attribId+'&ticketId='+ticketId+'&isCheck='+isCheck;
	if(attribId<0)
		url='ManageRegistrationForm!updateBaseProfileTickets?eid='+eid+'&attribId='+attribId+'&ticketId='+ticketId+'&isCheck='+isCheck;
	$.ajax({
		type:'POST',
		url:url,
		async:false,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			if(isCheck=='false')
				questionTicketJson[attribId].splice($.inArray(ticketId, questionTicketJson[attribId]),1);
			else{
				if(questionTicketJson[attribId]==undefined)
					questionTicketJson[attribId]=new Array();
				questionTicketJson[attribId].splice(0,0,ticketId);
			}
		}
	});
} */
</script>

