<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" src="../js/eventmanage/specialfee.js"></script>
<%-- <script type="text/javascript" src="../js/eventmanage/eventcontent.js"></script> --%>
<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="mgrId" id="mgrID"></s:hidden> 
<s:hidden name="powertype" id="ptype"> </s:hidden> 
<s:hidden name="currentLevel" id="clevel"></s:hidden>
<s:hidden name="currentFee" id="cfee"></s:hidden>
	
	<div class="white-box">
	<div class="alert alert-danger" style="display:none;">
 		 
	</div>
	
	<s:if test="%{powertype=='Ticketing'}">
	<div class="col-md-12 col-sm-12 col-xs-12 row">
		<input type="checkbox" class="fbsharebox" name="socialnwchkbox" value="sharepopup"
		<s:if test="%{socialnwchkbox.contains('sharepopup') || configValues.isEmpty}">checked='checked'</s:if> />&nbsp;<s:text name="epc.share.box.fb..cb.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="shareboxinfo" ></i>
	</div>
	<div class="col-md-12 col-sm-12 col-xs-12 row">
		<input type="checkbox"  class="fbloginbox"	name="socialnwchkbox" value="fblogin"
		<s:if test='%{ntsEnable=="Y"}'>disabled="disabled"</s:if>
		<s:if test='%{socialnwchkbox.contains("fblogin") || configValues.isEmpty || ntsEnable=="Y" || regloginpopup=="N" }'>checked='checked'</s:if> />&nbsp;<s:text name="epc.fb.login.during.registration.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="fbloginboxinfo"  ></i>
	</div>
	</s:if>
	
	<div class="col-md-12 col-sm-12 col-xs-12 row">
		<input type="checkbox"  class="promotion" name="promosettings" id="promotions" value="promotions"
		<s:if test="%{promosettings.contains('promotions')}">checked="checked"</s:if> />&nbsp;<s:text name="epc.promotions.cb.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="promoinfo"></i>
	</div>
	<div class="col-md-12 col-sm-12 col-xs-12 row">
		<s:hidden name="eventPageContentData.eventpassword" id="hiddenpassword"></s:hidden> 
		<s:hidden name="powertype" id="powertype"></s:hidden>
		<div style="float:left;">
		<input type="checkbox"  class="password" name="password" value="" id="checkPro" />
		<s:text name="epc.password.protection.section.header.lbl"></s:text>&nbsp;&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="pwdinfo" ></i>
		</div>&nbsp;&nbsp;
		<div id="showPassword" style="float:left; display:none;" >
			<div style="float:left;" >
				<input type="password" value="" id="eventpwd" class="form-control" style="margin-left:5px;"/>
			</div>
			<div style="float:left;" class="changeI">
				<i class="fa fa-eye" title="Show" id="showI" style="cursor: pointer;margin:7px 0px 0px 10px;"></i> 
			</div>
		</div>	
		<div style="clear:both;"></div>
		
		
		
	</div><div style="clear:both;"></div>
		
		<div class="center">
		<br/>
			<button class="btn btn-primary" id="submit"><s:text name="global.save.btn.lbl"/></button>
		</div>
		
	</div>
	
	
	<script>
	var eid=$('#eid').val();
	var ptype=$('#ptype').val();
	var mgrId=$('#mgrID').val();
	var fbShareConfirmation='';
 	var fbLoginduringRegistration='';
 	var promotions='';
 	var password='';
 	
	$(document).ready(function(){
		if($('#clevel').val()==100 || $('#clevel').val()==90)
			$('#showPassword').hide();
		else
			$('#showPassword').show();
	});
	$('input.password').on('ifChecked', function(event){
		 if($('#clevel').val()==100 || $('#clevel').val()==90){
			if(ptype=='Ticketing')
				specialFee(eid,'200','EditEvent','Ticketing');
			else
				specialFee(eid,'150','EditEvent','RSVP');
		 }
	 });
	 function enableButtons(){
		 $('#showPassword').show();
	 }
	 function closepopup(){
		 parent.$('#myModal').modal('hide');  
		 $('.password').iCheck('unCheck');
	 }
	
	var passwordReg = $('#hiddenpassword').val();
	if(!passwordReg==''){
		$('.password').attr('checked',true);
		$('#eventpwd').attr('value',passwordReg);
	}
	
     
 	$('#submit').click(function(){
 		$('.alert-danger').slideUp();
 		$('.fbsharebox').is(':checked')?fbShareConfirmation='Y':fbShareConfirmation='';
 		$('.fbloginbox').is(':checked')?fbLoginduringRegistration='Y':fbLoginduringRegistration='';
 		$('.promotion').is(':checked')?promotions='Y':promotions='';
 		if($('.password').is(':checked')){
 			if($('#eventpwd').val()==''){
 				$('.alert-danger').slideDown();
 	 			$('.alert-danger').html(props.epc_alert_password_empty);
 	 			return;
 			}else
 				password=$('#eventpwd').val();
 			
 		}else
 			password='';
 		
 	
 		
 		var data={'eid':eid,'mgrID':mgrId,'powertype':ptype,'fbShareConfirmation':fbShareConfirmation,'fbLoginduringRegistration':fbLoginduringRegistration,'promotions':promotions,'password':password};
 		$.ajax({
 			type:'POST',
 			url:'EventPageContent!updateContentFields',
 			data:data,
 			success:function(result){
 				 if(!isValidActionMessage(result)) return;
 				$('#notification-holder').html('');
				$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
				notification(props.display_options_update_lbl,"success");
				removeNotificationHeader();
 			},
 			error:function(){
 				alert('error while updating');
 			}
 		}); 	
 		
 		
 	});
 	$('#showI').attr('title',props.global_show_lbl);
 	$(document).on('click','#showI',function(){
 		$('#eventpwd').attr("type","text");
 		$('.changeI').html('<i class="fa fa-eye-slash" id="hideI" title="'+props.global_hide_lbl+'" style="cursor: pointer; margin:7px 0px 0px 10px;"></i>');
 	   /*   $('#hideI').attr('title',props.global_hide_lbl);
 		 $('#showI,#hideI').tooltipster({fixedWidth:'100px',position: 'right'}); */
 	});
	$(document).on('click','#hideI',function(){
		$('#eventpwd').attr("type","password");
		$('.changeI').html('<i class="fa fa-eye" id="showI" title="'+props.global_show_lbl+'" style="cursor: pointer; margin:7px 0px 0px 10px;"></i>');
		/*  $('#showI').attr('title',props.global_show_lbl);
		 $('#showI,#hideI').tooltipster({fixedWidth:'100px',position: 'right'}); */
 	});
     $('input.fbsharebox,input.fbloginbox,input.promotion,input.password').iCheck({
    	 checkboxClass: 'icheckbox_square-grey',
         radioClass: 'iradio_square-grey'
     });
     
     $('#shareboxinfo').attr('title',props.epc_share_box_fb_share_box_help_msg);
     $('#fbloginboxinfo').attr('title',props.epc_fb_login_box_help_msg_lbl);
     $('#promoinfo').attr('title',props.epc_promotions_help_msg_lbl);
     $('#pwdinfo').attr('title',props.epc_password_protection_help_msg_lbl);
    /*  $('#showI').attr('title',props.global_show_lbl);
     $('#hideI').attr('title',props.global_hide_lbl); */
     $('#shareboxinfo,#fbloginboxinfo,#promoinfo,#pwdinfo').tooltipster({fixedWidth:'100px',position: 'right'});
     
 	
	</script>