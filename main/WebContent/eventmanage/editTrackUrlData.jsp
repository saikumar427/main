<%@taglib uri="/struts-tags" prefix="s" %>
<style>
.form-horizontal .control-label{
	padding-top: 0px !important;
}

</style>
<s:hidden name="eid"></s:hidden>
<div class="col-md-12">
<div class="row">
	<div class="well well-no-margin"  >
	<div class="row">
	<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="alert alert-danger errorMsg" style="display:none;"></div>
		</div>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="alert alert-danger errorpwd" style="display:none;"></div>
		</div>
		<form class="form-horizontal">
		
		<div class="form-group">
			<label class="col-sm-2 col-md-2 col-xs-12 control-label"><s:text name="turl.track.name.lbl"/>&nbsp;:</label>
			<div class="col-md-10 col-sm-12 col-xs-12"><span id="nameTrack"></span></div>
			<div style="clear:both;"></div>
		</div>
		
		<div class="form-group">
			<label class="col-md-2 col-sm-2 col-xs-12 control-label" style="margin-top:7px;"><s:text name="turl.pwd.btn.lbl"/>&nbsp;:</label>
			<div class="col-md-3 col-sm-3 col-xs-12">
				<div class="row">
					<div class="col-md-10 col-xs-10 col-sm-10"><input class="form-control" type="password" value="" name="" id="pwd" /></div>
					<div class="col-md-2 col-xs-2 col-sm-2 changeIcon" style="margin-top: 7px; margin-left: -25px;"><i class="fa fa-eye" title="<s:text name="global.show.lbl"/>" id="showI" style="cursor: pointer;"></i></div>
				</div>
				<%-- <input class="form-control" type="password" value="" name="" id="pwd" />
				<span class="input-group-addon changeIcon"><i class="fa fa-eye" id="showI"></i></span> --%> 
			</div>
			<div class="col-md-7 col-sm-7"></div>
			<div style="clear:both;"></div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 col-md-2 col-xs-12 control-label"><s:text name="turl.status.section.header"/>&nbsp;:</label>
			<div class="col-md-10 col-sm-12 col-xs-12">
				
				<div class="tab-bottom-gap">
				<div class="btn-group btn-toggle" data-toggle="buttons" >
					<label id="approvedTrack" class="optiontype btn btn-xs btn-default no-radius">
						<input class="datesel"  name="statusTrack" value="Approved" type="radio"><s:text name="turl.activate.btn.lbl" />
					</label>
					<label id="SuspendedTrack" class="optiontype btn btn-xs btn-active no-radius">
						<input class="datesel"  name="statusTrack" value="Suspended" type="radio"><s:text name="turl.suspend.btn.lbl" />
					</label>
				</div>
				
				<s:set name="stat" value="status"></s:set>
				<s:set name="stat" value="%{indivualTrackcodeList[0].getStatus()}"></s:set>
				
				<div style="clear:both;"></div>
		   </div>
				
			</div>
			<div style="clear:both;"></div>
		</div>
		
		
		</form>
		
		
		
		
		<div class="form-group">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<input type="checkbox" name="" id="checkMsgPic" class=""/>&nbsp;&nbsp;<label class="showMsgPic" style="cursor:pointer;"><s:text name="turl.add.pic.msg" />&nbsp;<span class=" sm-font" style="display:none;"><i class="glyphicon glyphicon-menu-right"></i></span></label>
			</div>
			
			<div style="clear:both;"></div>
			<div class="openMsgPicDiv background-options sm-font row" style="display:none; margin-top:5px;">
				<div class="col-sm-6 col-md-6 col-xs-12">
					<input type="text" value="" placeholder="<s:text name="trul.enter.photo.url.lbl" />" class="form-control" name="" id="img"/>
					<span class="sm-font"><s:text name="turl.photo.url.msg" /></span>
				</div>
				<div class="col-sm-6 col-md-6 col-xs-12">
					<input type="text" value="" placeholder="<s:text name="trul.message.here" />" class="form-control" name="" id="msg"/>
				</div>
			</div>
			
			<div style="clear:both;"></div>
		</div>
		
	</div>
	<div class="center">
		<input type="button" class="save btn btn-primary" value="<s:text name="global.save.btn.lbl"/>"/>
		<button class="btn btn-cancel " id="closeWell"><i class="fa fa-times"></i></button>
	</div>
	</div>
	
</div>
</div>


<script>
var data = ${trackCodeData};
$(document).ready(function(){
	
	var setPassword = data['password'];
	var setMessage =data['message'];
	var setPhoto = data['photo'];
	var setStatus = data['status'];
	var setTrackcode = data['trackingcode'];
	$('#pwd').val(setPassword);
	if(setPassword==''){
		$('#pwd').val(setTrackcode);
	}
	$('#nameTrack').html(setTrackcode);
	$('#msg').val(setMessage);
	$('#img').val(setPhoto);
	if(setStatus=='Approved'){
		jQuery("input[value='Approved']").attr('checked', true);
		$("#approvedTrack").addClass("btn-default").removeClass("btn-active");
		$("#SuspendedTrack").removeClass("btn-default").addClass("btn-active");
	}
	else{
		jQuery("input[value='Suspended']").attr('checked', true);
		$("#SuspendedTrack").addClass("btn-default").removeClass("btn-active");
		$("#approvedTrack").removeClass("btn-default").addClass("btn-active");
	}
	
	if(!setMessage=='' || !setPhoto==''){
		$("#checkMsgPic").iCheck('check');
		$('.openMsgPicDiv').slideDown();
		$('.showMsgPic .glyphicon').addClass('down').removeClass('original');
	}
});
$('input[type="checkbox"]').iCheck({  
	checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey'
});
$("#approvedTrack").click(function(){
	if(!$(this).hasClass("btn-default")){
		$("#approvedTrack").addClass("btn-default").removeClass("btn-active");
		$("#SuspendedTrack").removeClass("btn-default").addClass("btn-active");
		
	}
 });
$("#SuspendedTrack").click(function(){
	if(!$(this).hasClass("btn-default")){
		$("#SuspendedTrack").addClass("btn-default").removeClass("btn-active");
		$("#approvedTrack").removeClass("btn-default").addClass("btn-active");
	}
 });
$('.showMsgPic').click(function(){
	if($('.openMsgPicDiv').is(':hidden')){
		$('.openMsgPicDiv').slideDown();
		$("#checkMsgPic").iCheck('check');
		$(this).find('.glyphicon').addClass('down').removeClass('original');
	}
	else{
		$('.openMsgPicDiv').slideUp();
		$(this).find('.glyphicon').addClass('original').removeClass('down');
	}
});
$('#checkMsgPic').on('ifChecked', function(event){
	  $('.openMsgPicDiv').slideDown();
	  $('.showMsgPic .glyphicon').addClass('down').removeClass('original');
	});
$('#checkMsgPic').on('ifUnchecked', function(event){
	  $('.openMsgPicDiv').slideUp();
	  $('.showMsgPic .glyphicon').addClass('original').removeClass('down');
	});
var flag=true;
$('.save').click(function(){
	$('.save').prop("disabled",true);
	$('.errorMsg').slideUp();
	$('.errorpwd').slideUp();
	var photourl = '';
	var message = '';
	if($('#pwd').val()==""){
		$('.errorpwd').slideDown();
		$('.errorpwd').html(props.trul_password_not_empty_lbl);
		$('.save').prop("disabled",false);
		$('#pwd').focus();
		flag = false;
		return;
	}else
		flag = true;
	if($("#checkMsgPic").is(':checked')){
		$('.errorMsg').slideUp();
		if($('#img').val()=='' && $('#msg').val()==''){
			$('.openMsgPicDiv').slideDown();
			$('.errorMsg').slideDown();
			$('.errorMsg').html(props.trul_img_not_empty_lbl+'<br>'+props.trul_message_empty_lbl);
			$('.save').prop("disabled",false);
			$('#img').focus();
			flag=false;
		}/* else if($('#img').val()==''){
			$('.openMsgPicDiv').slideDown();
			$('.errorMsg').slideDown();
			$('.errorMsg').html('Image url empty');
			$('#img').focus();
			flag=false;
		}else if($('#msg').val()==''){
			$('.openMsgPicDiv').slideDown();
			$('.errorMsg').slideDown();
			$('.errorMsg').html('Message empty');
			$('#msg').focus();
			flag=false;
		} */
		photourl = $('#img').val();
		message = $('#msg').val();
	}else{
		photourl = '';
		message = '';
		flag = true;
	}
		
	
	if(flag){
			var status = $('input[name="statusTrack"]:checked').val();
			var password = $('#pwd').val();
			
			var trackcode = "${trackcode}";
			var eid = "${eid}";
			 var url='TrackURL!updateTrackUrlData?eid='+eid+'&trackcode='+trackcode+'&status='+status+'&photourl='+photourl+'&password='+password+'&message='+message;
			$.ajax({
				type:'POST',
				url:url,
				success:function(result){
					parent.changeStatus(trackcode,status);
					parent.closeTR();
					$('.save').prop("disabled",false);
				},
				error:function(){
					alert('Error at update trackurls');
				}
				
			});
	}
});
$(document).on('click','#showI', function(){
	$('#pwd').attr("type","text");
	$('.changeIcon').html('<i class="fa fa-eye-slash" id="hideI" title="'+props.global_hide_lbl+'" style="cursor: pointer;"></i>');
});
	
$(document).on('click', '#hideI', function(){
	$('#pwd').attr("type","password");
	$('.changeIcon').html('<i class="fa fa-eye" id="showI" title="'+props.global_show_lbl+'" style="cursor: pointer;"></i>');
}); 


$('#closeWell').click(function(){
	parent.closeTR();
});


</script>
