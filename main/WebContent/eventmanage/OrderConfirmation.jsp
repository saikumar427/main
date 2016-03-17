<%@taglib uri="/struts-tags" prefix="s"%>
<s:hidden value="%{powertype}" id="powertype" />
<s:hidden value="%{currentLevel}" id="currentLevel" />
<style>
	.btn-default{
			cursor:default;
	}
</style>



<div class="white-box row">
<div class="col-sm-12 col-xs-12 col-md-12 row"> 
    <div class="tab-bottom-gap">
		<div class="btn-group btn-toggle" data-toggle="buttons">
			<label id="confirmationPageDisplay" class="optiontype btn btn-default no-radius">
				<input class="datesel"  name="layoutOpt" value="1" type="radio"><s:text name="oc.conf.page.lbl"/>
			</label>
			<label id="confirmationEmailDisplay" class="optiontype btn btn-active no-radius">
				<input class="datesel"  name="layoutOpt" value="2" type="radio"><s:text name="oc.conf.email.lbl"/>
			</label>
			
			<s:if test="%{powertype!='RSVP' && isrecurring!=true}">
			<label id="customizeWaitlistEmail" class="optiontype btn btn-active no-radius">
				<input class="datesel"  name="layoutOpt" value="3" type="radio"><s:text name="oc.conf.waitlist.lbl"/>
			</label>
			</s:if>
			
		</div><div style="clear:both;"></div>
   </div>
   <div class="text-center">
   <div id="onLoad" class="text-center"><img src="../images/ajax-loader.gif"></div>
   	<div id="forload"></div>
   </div>
</div>
<div class="col-md-12 col-xs-12 col-sm-12 row" id="confirmationPage" style="display:none;"><div style="clear:both;"></div>
</div>

<div class="col-md-12 col-xs-12 col-sm-12 row" id="confirmationEmail" style="display:none;"><div style="clear:both;"></div>
</div>

<div class="col-md-12 col-xs-12 col-sm-12 row" id="customizeWaitEmail" style="display:none;"><div style="clear:both;"></div>
</div>

<script>
var eventid='${eid}';
var loadPage = function(){
	$.ajax({
		type:'POST',
		url:"ConfirmationPageSettings?eid="+eventid,
			success:function(result){
				if(!isValidActionMessage(result)) return;
				$('#onLoad').html('');
				$('#confirmationPage').html(result);
				hideProcessing();
				//$('#confirmationEmailDisplay').attr('disabled',false);
				//$('#customizeWaitlistEmail').attr('disabled',false);
			},
			error:function(){
				alert('error');
			}
	});
}
var loadEmail = function(){
$.ajax({
	type:'POST',
	url:"RegEmailSettings?eid="+eventid,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			$('#confirmationEmail').html(result);
			hideProcessing();
			//$('#confirmationPageDisplay').attr('disabled',false);
			//$('#customizeWaitlistEmail').attr('disabled',false);
		},
		error:function(){
			alert('error');
		}
}); 
}
var eid=eventid;
var loadWaitEmail = function(){
	$.ajax({
		type:'POST',
		url:"WaitList!waitEmailTemplate?eid="+eid,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			$('#customizeWaitEmail').html(result);
			hideProcessing();
			//$('#confirmationPageDisplay').attr('disabled',false);
			//$('#confirmationEmailDisplay').attr('disabled',false);
		},
		error:function(){
			alert('error');
		}
		
	});
	
	
}


$(document).ready(function(){
	if($('#currentLevel').val()==150 || $('#currentLevel').val()>=300){
		$('#confirmationPage').show();
		loadPage();
		$("#confirmationEmailDisplay").click(function(){
			//$('#confirmationPageDisplay').attr('disabled',true);
			//$('#customizeWaitlistEmail').attr('disabled',true);
			if(!$(this).hasClass("btn-default")){
				showProcessing('forload');
				$('#confirmationPage').hide();
				$('#confirmationEmail').show();
				$('#customizeWaitEmail').hide();
				$("#confirmationEmailDisplay").addClass("btn-default").removeClass("btn-active");
				$("#confirmationPageDisplay").removeClass("btn-default").addClass("btn-active");
				$('#customizeWaitlistEmail').removeClass("btn-default").addClass("btn-active");
				$('#confirmationPage').html('');
				$('#customizeWaitEmail').html('');
		   		loadEmail();
			}
		 });
		$("#confirmationPageDisplay").click(function(){ 
			//$('#confirmationEmailDisplay').attr('disabled',true);
			//$('#customizeWaitlistEmail').attr('disabled',true);
			if(!$(this).hasClass("btn-default")){
				showProcessing('forload');
				$('#confirmationPage').show();
				$('#confirmationEmail').hide();
				$('#customizeWaitEmail').hide();
				$("#confirmationPageDisplay").addClass("btn-default").removeClass("btn-active");
				$("#confirmationEmailDisplay").removeClass("btn-default").addClass("btn-active");
				$('#customizeWaitlistEmail').removeClass("btn-default").addClass("btn-active");
				$('#confirmationEmail').html('');
				$('#customizeWaitEmail').html('');
				loadPage();
			}
		 });
		
		$("#customizeWaitlistEmail").click(function(){ 
			//$('#confirmationEmailDisplay').attr('disabled',true);
			//$('#confirmationPageDisplay').attr('disabled',true);
			if(!$(this).hasClass("btn-default")){
				showProcessing('forload');
				$('#confirmationPage').hide();
				$('#confirmationEmail').hide();
				$('#customizeWaitEmail').show();
				$("#customizeWaitlistEmail").addClass("btn-default").removeClass("btn-active");
				$("#confirmationEmailDisplay").removeClass("btn-default").addClass("btn-active");
				$("#confirmationPageDisplay").removeClass("btn-default").addClass("btn-active");
				$('#confirmationEmail').html('');
				$('#confirmationPage').html('');
				loadWaitEmail();
			}
		 });
	}else{
		var eventid ='${eid}';
		if($('#powertype').val()=='Ticketing')
			specialFee(eventid,"300","OrderConfirmation","Ticketing");
		else
			specialFee(eventid,"150","OrderConfirmation","RSVP");
	}
});
function showProcessing(divid){
    var html='<div id="loading"><center><img src="../images/ajax-loader.gif"></center></div>';
    $('#'+divid).after(html);
}

function hideProcessing(){
    $('#loading').remove();
}
</script>

</div>
