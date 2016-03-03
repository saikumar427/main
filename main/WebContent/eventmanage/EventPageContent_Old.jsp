<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" src="../js/eventmanage/specialfee.js"></script>
<script type="text/javascript" src="../js/eventmanage/eventcontent.js"></script>
<s:hidden name="EventPageContentData.photoname" id="photoname"></s:hidden>
<s:hidden name="EventPageContentData.uploadurl" id="uploadurl"></s:hidden>
<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="eventphotoURL" id="eventPageContentData.eventphotoURL" id="eventphotoURL" ></s:hidden>
	
	
<div class="panel panel-primary">
<div class="panel-heading">
<h3 class="panel-title"><s:text name="epc.photo.section.header.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="photoinfo"  ></i></h3>
</div>
<s:set name="eventphoto" value="eventPageContentData.eventphotoURL"></s:set>
<div class="panel-body" id="eventphotourlimage">
<s:if test="%{#eventphoto!=''}">
	<s:if test="%{imgtype=='uploaded'}">
		<img id="imgupload"  src="<s:text name='eventPageContentData.eventphotoURL'></s:text>"   />
		<div id="noimg" class="smallestfont" style="display: none; padding-top: 10px;"></div>
	
	</s:if>
	<s:else>
		<img alt="" id="imgupload" 	src="<s:text name='eventPageContentData.eventphotoURL'></s:text>" width="100px" height="75px" />
	</s:else>
</s:if>
</div>
<div class="panel-footer">
<button class="btn btn-primary" onclick="addexternalphoto();"> <s:text name="epc.add.external.photo.btn.lbl"></s:text>  </button>&nbsp;
<button class="btn btn-primary" onclick="uploadphoto();"> <s:text name="epc.external.photo.upload.btn.lbl"></s:text>  </button>&nbsp;
<button class="btn btn-primary  <s:if test='%{#eventphoto=="" || #eventphoto==null}'>disabled</s:if> " onclick="deletePhotoURL();" id="delextphoto">  <s:text name="global.delete.link.lbl"></s:text>  </button>
</div>
</div>
<br/>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title"><s:text name="epc.logo.message.section.header.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="logoinfo" ></i></h3>
	</div>
	<s:set name="imageURL" value="imageurl"></s:set>
	<div class="panel-body">
		<div class="row">
			<div class="col-md-2"><s:text name="epc.logo.lbl"></s:text>:</div>
			<div class="col-md-6" id="logourlimage"><s:set name="imageURL" value="imageurl"></s:set>
				<s:if test="%{#imageURL!=''}">
					<div id="logoimageurl"> <img src='<s:property value="imageurl"/>' width="100" height="100" > </div>
				</s:if>
			</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-2"><s:text name="epc.message.lbl"></s:text>:</div>
			<div class="col-md-6" id="logomsg"><s:property value="message" /></div>
		</div>
	</div>
	<div class="panel-footer">
		<button class="btn btn-primary" id="eventlogourl"><s:text name="epc.add.external.photo.btn.lbl"></s:text>         </button>&nbsp;
		<button class="btn btn-primary" onclick="uploadlogobtn();"><s:text name="epc.external.photo.upload.btn.lbl"></s:text></button>&nbsp;
		<button class="btn btn-primary <s:if test='%{#imageURL == ""}'> disabled  </s:if>   " onclick="deleteLogoURL();"  id="dellogoimgbtn">  <s:text name="global.delete.link.lbl"></s:text></button>
	</div>
</div>

<div class="panel panel-primary">
<div class="panel-heading">
<h3 class="panel-title"><s:text name="epc.social.section.header.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="socialinfo" ></i></h3>
</div>
<div class="panel-body">
	<form name="socialnwForm" id="socialnwForm" action="EventPageContent!updateSocialNwSettings">
		<s:hidden name="eid" id="eid"></s:hidden> 
		<s:hidden name="mgrId"></s:hidden> 
		<s:hidden name="powertype" id="ptype"> </s:hidden> 
		<s:hidden name="currentLevel" id="clevel"></s:hidden>
 		<s:hidden name="currentFee"></s:hidden>
  			<s:if test="%{powertype=='RSVP' && currentLevel=='90'}">
				<span class="smallestfont"> <s:text name="epc.rsvpevent.level.help.lbl"></s:text> <a href="/main/pricing" target="_blank"><s:text name="epc.rsvpevent.level.click.lbl"></s:text></a> <s:text name="epc.rsvpevent.level.details.lbl"></s:text></span>
			</s:if>
		<div class="row">
			<div class="col-md-3">
				<b><s:text name="epc.share.button.lbl"></s:text>:</b>
				<br>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4"><input type="checkbox" class="twitter" name="socialnwchkbox" id="twitter" value="twitter"
				<s:if test="%{socialnwchkbox.contains('twitter') || configValues.isEmpty}">checked='checked'</s:if> />&nbsp;<s:text name="epc.share.button.twitter.cb.lbl"></s:text>
			</div>
			<div class="col-md-4"><input type="checkbox" class="facebook" name="socialnwchkbox" id="fblike" value="fblike"
				<s:if test="%{socialnwchkbox.contains('fblike') || configValues.isEmpty}">checked='checked'</s:if> />&nbsp;<s:text name="epc.share.button.fb.cb.lbl"></s:text>
			</div>
			<div class="col-md-4"><input type="checkbox" class="google" name="socialnwchkbox" id="googleplusone" value="googleplusone"
				<s:if test="%{socialnwchkbox.contains('googleplusone') || configValues.isEmpty}">checked='checked'</s:if> />&nbsp;<s:text name="epc.share.button.google.cb.lbl"></s:text>
			</div>
		</div><br>
		<div class="row">
			<div class="col-md-3">
				<b><s:text name="epc.commenting.header.lbl"></s:text>:</b>
			</div>
		</div>
		<div class="row">
		<div class="col-md-4"><input type="checkbox" class="fbcomment" 	name="socialnwchkbox" id="fbcomment" value="fbcomment"
			<s:if test="%{socialnwchkbox.contains('fbcomment') || configValues.isEmpty}">checked='checked'</s:if> />&nbsp;<s:text name="epc.commenting.fb.comment.cb.lbl"></s:text>
		</div>
		<div class="col-md-4"><input type="checkbox" class="fbsend" 	name="socialnwchkbox" value="fbsend"
			<s:if test="%{socialnwchkbox.contains('fbsend') || configValues.isEmpty}">checked='checked'</s:if> />&nbsp;<s:text name="epc.commenting.fb.send.cb.lbl"></s:text>
		</div>
		</div><br>

		<div class="row">
			<div class="col-md-3">
				<b><s:text name="epc.attending.header.lbl"></s:text>:</b><br>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6"><input type="checkbox" class="fbattending" id="fbattending" name="socialnwchkbox" value="fbattending"
				<s:if test="%{socialnwchkbox.contains('fbattending')}">checked='checked'</s:if> />&nbsp;<s:text name="epc.attending.fb.cb.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="fbattendinginfo" ></i>
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4"><input type="text" class="form-control" name="facebookeventid" id="facebookeventid" value="<s:property value="facebookeventid"/>" /></div>
			<!-- <div id="fbeventidvalidationmessage"  style="display:none"></div> -->
		</div>
		<div class="row">
			<div class="col-md-6"><input type="checkbox" class="morfcount" 	name="socialnwchkbox" value="gendercount"
				<s:if test="%{socialnwchkbox.contains('gendercount')}">checked='checked'</s:if> />&nbsp;<s:text name="epc.attending.male.female.cb.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="genderinfo" ></i>
			</div>
			<div class="col-md-6" id="fbiderrormsg" style="color:Red; display:none;"> </div>
			</div><br>
			
		<s:if test="%{powertype=='Ticketing'}">
		<div class="row">
			<div class="col-md-3">
				<b><s:text name="epc.share.box.lbl"></s:text>:</b>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6"><input type="checkbox" class="fbsharebox" name="socialnwchkbox" value="sharepopup"
				<s:if test="%{socialnwchkbox.contains('sharepopup') || configValues.isEmpty}">checked='checked'</s:if> />&nbsp;<s:text name="epc.share.box.fb..cb.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="shareboxinfo" ></i>
			</div>
		</div><br>
		<div class="row">
			<div class="col-md-5">
				<b><s:text name="epc.fb.login.during.registration.lbl"></s:text>:</b>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6"><input type="checkbox" class="fbloginbox"	name="socialnwchkbox" value="fblogin"
				<s:if test='%{ntsEnable=="Y"}'>disabled="disabled"</s:if>
				<s:if test='%{socialnwchkbox.contains("fblogin") || configValues.isEmpty || ntsEnable=="Y" || regloginpopup=="N" }'>checked='checked'</s:if> />&nbsp;<s:text name="epc.fb.login.box.cb.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="fbloginboxinfo"  ></i>
			</div>
		</div>
		</s:if>
	</form>
</div>
<div class="panel-footer">
	<div class="row">
		<div class="col-md-1">
			<button class="btn btn-primary" id="socialnwsubmit"><s:text name="global.update.link.lbl"></s:text></button>
			
		</div>
		<div class="col-md-11">
			 <div id="socialNetworkUpdateMessage" style="padding:6px; margin-bottom: -15px;" ></div>
		</div>
	</div>
</div>
</div><!-- social  -->
<br/>
 
 <div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title"><s:text name="epc.notice.board.section.header.lbl"></s:text>&nbsp;
		<img src="/main/images/pro.jpg" alt="Pro Ticketing" id="noticeproticketing" />&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="noticinfo" ></i></h3>
	</div>
	<div class="panel-body">
	<s:if test="%{currentLevel=='90' || currentLevel=='100'}">
		<div id="description" style="display:block">
	</s:if>
	<s:else>
		<div id="description" style="display:none">
	</s:else>
			
				<div class="col-md-11">Notice is Pro Feature. If you want to enable this, click on Enable Notice button.</div>
			</div>
	
	<s:if test="%{currentLevel=='90' || currentLevel=='100'}">
		<div id="noticedatatable" style="display:none">
	</s:if>
	<s:else>
		<div id="noticedatatable" style="display:block">
	</s:else>
	<div class="table-responsive">
		<table class="table " id="noticeboarddata">
			<thead>
				<th><s:text name="epc.notice.type.header.lbl"></s:text></th>
				<th><s:text name="epc.notice.header.lbl"></s:text></th>
				<th><s:text name="epc.posting.time.header.lbl"></s:text></th>
				<th></th>
			</thead>
			<tbody id="rebuild">
				<tr class='nodata'>
				<td class="load" colspan="3"><s:text name="global.no.data.lbl"></s:text></td>
				</tr>
			</tbody>
		</table>
	</div>	
	</div>	<!-- style close -->	
		<div  id="noticeboardadd" style="display:none;"></div>
	</div> <!-- panel body close -->
	<div class="panel-footer">
		<s:if test="%{currentLevel=='90' || currentLevel=='100'}">
				<div id="upgradenoticebtn" style="display:block">		
		</s:if>
		<s:else>
				<div id="upgradenoticebtn" style="display:none">	
		</s:else>
			<button class="btn btn-primary" onclick="enableNotice();"><s:text name="epc.enable.notice.btn.lbl"></s:text></button>  
		</div>
		
		<s:if test="%{currentLevel=='90' || currentLevel=='100'}">
			<div id="noticebtn" style="display:none">
		</s:if>
		<s:else>
			<div id="noticebtn" style="display:block">
		</s:else>
			<button class="btn btn-primary" id="addNoticeBtn"><s:text name="epc.post.notice.btn.lbl"></s:text></button>
		</div> 
	</div>   <!-- footer -->
</div> <!-- panel -->
<br>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title"><s:text name="epc.password.protection.section.header.lbl"></s:text>&nbsp;
		<img src="/main/images/pro.jpg" alt="Pro Ticketing" id="passwordimagetitle" />&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="pwdinfo" ></i></h3>
	</div>
	<div class="panel-body">
		<s:if test="%{currentLevel=='90' || currentLevel=='100'}">
			<div id="pwddesc" style="display:block">
		</s:if>
			
		<s:else>
		<div id="pwddesc" style="display:none">
		</s:else>
		
			
				<div class="col-md-11">Password Protection is Pro Feature. If you want to enable this, click on Enable Password Protection button.</div>
			</div>
		
		
		<s:if test="%{currentLevel=='90' || currentLevel=='100'}">
			<div id="pwdfield" style="display:none">
		</s:if>
			
		<s:else>
			<div id="pwdfield" style="display:block">
		</s:else>
		
			<div class="row">
				<div class="col-md-4"><s:text name="epc.password.lbl"></s:text>:</div>
					<s:hidden name="eventPageContentData.eventpassword" id="hiddenpassword"></s:hidden> 
					<s:hidden name="powertype" id="powertype"></s:hidden>
				<div class="col-md-8"> <span id='changepwd'> <s:text name="eventPageContentData.eventpassword"></s:text> </span> </div>
			</div>
		</div>  <!-- style close -->	
		
	</div>   <!-- body -->
	<div class="panel-footer">
	
		<s:if test="%{currentLevel=='90' || currentLevel=='100'}">
			<div id="pwdenablebtn" style="display:block">
		</s:if>
		<s:else>
			<div id="pwdenablebtn" style="display:none">
		</s:else>
		
			<button class="btn btn-primary" onclick="enableProtection();"><s:text name="epc.password.protection.th.lbl"></s:text></button> 
		</div>
		
		<s:if test="%{currentLevel=='90' || currentLevel=='100'}">
			<div id="setpwdbtn" style="display:none">
		</s:if>
		<s:else>
			<div id="setpwdbtn" style="display:block">
		</s:else>
			<input type="button" name="button" value="<s:text name="epc.set.password.btn.lbl"></s:text>" id="insertpwdbtn"  class="btn btn-primary"/>
		</div>  <!-- style close -->
	</div>  <!-- footer close -->
</div> 
<div class="panel panel-primary">
<s:set name="promotionsshowtype" value="showpromotions"></s:set>
	<div class="panel-heading">
		<h3 class="panel-title"><s:text name="epc.promotions.otherinfo.header.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="allinfo" ></i></h3>
	</div>
	<div class="panel-body">
	<form name="socialsettings" id="socialsettings" action="EventPageContent!updatePromoSettings">
	<s:hidden name="eid" id="eid"></s:hidden> 
	<s:hidden name="mgrId"></s:hidden> 
	<s:hidden name="powertype" id="ptype"> </s:hidden>	
	
	<s:if test="%{powertype=='Ticketing'}">
	<div class="row">
		<div class="col-md-4"><input type="checkbox" class="showsopromo" name="promosettings" id="socialmediapromotions" value="socialmediapromotions" 
			<s:if test="%{promosettings.contains('socialmediapromotions')  }">checked="checked"</s:if> />&nbsp;<s:text name="epc.social.promotions.cb.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="spromoinfo" ></i>
		</div>
	</div>	
	</s:if>
	<div class="row">     
		<div class="col-md-4"><input type="checkbox" class="promotion" name="promosettings" id="promotions" value="promotions"
			<s:if test="%{promosettings.contains('promotions') ||  configValuesofpromotions.isEmpty }">checked="checked"</s:if> />&nbsp;<s:text name="epc.promotions.cb.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="promoinfo"></i>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4"><input type="checkbox" class="maps" name="promosettings" id="maps" value="maps"
			<s:if test="%{promosettings.contains('maps') || configValuesofpromotions.isEmpty}">checked="checked"</s:if> />&nbsp;<s:text name="epc.google.maps.cb.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="googleinfo" ></i>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4"><input type="checkbox" class="attendee" name="promosettings" id="attendee" value="attendee"
			<s:if test="%{promosettings.contains('attendee') || configValuesofpromotions.isEmpty}">checked="checked"</s:if> /><s:text name="epc.whos.attensing.cb.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="whosinfo" ></i>
		</div>
		<a  style="top-margin:10px; cursor:pointer;"  id="editdisplaybtn"><s:text name="epc.display.fields.link.lbl"></s:text></a>
	</div>
	</form>
	</div>
	<div class="panel-footer">
	<div class="row">
		<div class="col-md-1">
			<button class="btn btn-primary" id="updatepromotions"><s:text name="global.update.link.lbl"></s:text></button> 
		</div>
		<div class="col-md-11">
			<div id="promoupdateMessage" style="padding:6px;"></div>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript" src="NoticeBoard!getNoticeItems?eid=${eid}"></script> 
<script>
var uploadpurpose='';
var eid = ${eid};
var serveraddress='${serveraddress}';
$(document).ready(function(){	
	
	
	$('#photoinfo').attr('title',props.epc_photo_img_help_lbl);
	$('#logoinfo').attr('title',props.epc_logo_message_help_msg);
	$('#socialinfo').attr('title',props.epc_social_img_help_msg);
	$('#fbattendinginfo').attr('title',props.epc_fb_attending_help_lbl);
	$('#genderinfo').attr('title',props.epc_malefemale_help_msg_lbl);
	$('#shareboxinfo').attr('title',props.epc_share_box_fb_share_box_help_msg);
	$('#fbloginboxinfo').attr('title',props.epc_fb_login_box_help_msg_lbl);
	
	$('#noticinfo').attr('title',props.epc_notice_board_help_msg_lbl);
	$('#noticeproticketing').attr('title',props.epc_notice_board_img_msg_lbl);
	
	$('#pwdinfo').attr('title',props.epc_password_protection_help_msg_lbl);
	$('#passwordimagetitle').attr('title',props.epc_notice_board_img_msg_lbl);
	
	$('#allinfo').attr('title',props.epc_promotions_otherinfo_help_msg_lbl);
	$('#spromoinfo').attr('title',props.epc_social_promotions_help_msg_lbl);
	$('#promoinfo').attr('title',props.epc_promotions_help_msg_lbl);
	$('#googleinfo').attr('title',props.epc_google_maps_help_msg_lbl);
	$('#whosinfo').attr('title',props.epc_whos_attending_help_msg_lbl);
	
	
	
	
	
	
	 $('#addNoticeBtn').click(function(){
		 openNewNotice();
	}); 
	 
	 $(document).on('click','#cancel',function(){
		 $('#notice').html('');
		$('.editNoticeRow').remove();
		$('#addNoticeBtn').prop("disabled",false);
		      $('#noticeboardadd').fadeOut('slow'); 
		      $('#addNoticeBtn').removeAttr("disabled");
		      $('#manage_'+globalEditNotice+" a").each(function(){
				 $(this).css({"pointer-events": "visible","cursor":"pointer","color":"#5388C4"});
			        $(this).prop("disabled",false);
			}); 
		 });

	$('#insertpwdbtn').click(function(){
		 $('#onefieldtextoption').show();
		    var defoption='Leave blank to remove password protection';
		    var label='';
		 	 openSinglePopUpWithOptions($(this).offset(),pwdsuccess,cancel,$('#hiddenpassword').val(),lable,defoption);
		 }); 
	
	builddata(data);
    
     $('input.attendee,input.maps,input.promotion,input.showsopromo,input.hidemap,input.showmap,input.showsopromo').iCheck({
         checkboxClass: 'icheckbox_square-grey',
         radioClass: 'iradio_square-grey'
     });
     $('input.google,input.fbcomment,input.facebook,input.twitter,input.showpromo,input.hidesopromo,input.hidepromo').iCheck({
        checkboxClass: 'icheckbox_square-grey',
        radioClass: 'iradio_square-grey'
    });
     $('input.fbloginbox,input.morfcount,input.fbattending,input.fbsend,input.fbsharebox,input.hideatt,input.showatt').iCheck({
        checkboxClass: 'icheckbox_square-grey',
        radioClass: 'iradio_square-grey'
    });
    $('input.showatt').on('ifChecked', function(event){
      configureEventView(eid,'Yes');
    });   
    $('input.hideatt').on('ifChecked', function(event){
      configureEventView(eid,'No');
    });  
   
     $('input.showmap').on('ifChecked', function(event){
      configureEventView(eid,'Yes','googlemap');
    }); 
     $('input.hidemap').on('ifChecked', function(event){
      configureEventView(eid,'No','googlemap');
    }); 
     $('input.hidepromo').on('ifChecked', function(event){
      configureEventView(eid,'No','promotions');
    }); 
     $('input.showpromo').on('ifChecked', function(event){
      configureEventView(eid,'Yes','promotions');
    }); 
     $('#photoinfo,#logoinfo,#logoinfo,#whosinfo,#spromoinfo,#shareboxinfo,#allinfo').tooltipster({fixedWidth:'100px',position: 'right'});
     $('#promoinfo,#googleinfo,#socialinfo,#noticinfo,#pwdinfo').tooltipster({fixedWidth:'100px',position: 'right'});
	 $('#fbattendinginfo,#genderinfo,#fbloginboxinfo').tooltipster({fixedWidth:'100px',position: 'right'});		    

	 $('#eventlogourl').click(function(){
	getLogoAndMessage(eid);
});

$('#editdisplaybtn').click(function(){
editdisplayfn(eid);
});

$('#socialnwsubmit').click(function(){
	$('#fbiderrormsg').hide();
	if(document.getElementById("fbattending").checked){
		var fbid= document.getElementById('facebookeventid').value
		if(fbid==""){
			$('#fbiderrormsg').show();
			$('#fbiderrormsg').html('You need to set Facebook event id to get the code');
			}else{
			if(isNaN(fbid)){
				$('#fbiderrormsg').show();
				$('#fbiderrormsg').html('Please enter valid Facebook event id only.');
				}else
				updateSocialnw();
			}
		}
	else
		updateSocialnw();
});

 $('#updatepromotions').click(function(){
	updatePromotionSettings();
 }); 

$(document).on('click','.noticeboardlbl',function(){	
	 var noticetype=$(this).attr('id');
});

});

var globalEditNotice ='';
function getccContent(rfid,purp)
{
  var Msg=getPopMsg();
  var setkey= new callPaykey();
  setkey.setCallbackurl("/main/myaccount/home!insertManagerccData");
  setkey.setPurpose(purp);
  setkey.setPaymode("vault");
  setkey.setMessage(Msg);
  setkey.setAmount("1.00");
  setkey.setCurrency("USD");
  setkey.setMerchantid("");
  setkey.setLang("En");
  setkey.setTitle("Credit Card Details");
  setkey.setSoftdis("");
  setkey.setRefid(rfid);
  setkey.setVendor("Braintree");
  setkey.setAppendDiv('maincontent');
  setkey.setInternalref('111');
  var paykey=setkey.getPaykey();
     if(paykey!="")
	 {setkey.ccpopup(paykey);
	 }
	 else{
	 alert("unable to process Not valid paykey");
	 }

}

function setWebPath(fullPath,uploadurl,photoname){
	var data=new Object();
//alert(fullPath+"::"+uploadurl);	
if(uploadpurpose=='imageupload'){
	$('#eventphotourlimage').html('<img src="'+fullPath+'" id="imgupload" width="100px" height="75px">');
	$('#delextphoto').removeClass('disabled');
	var url="EventPageContent!updateeventphotoURL?eid="+eid;
	data={'eventphotoURL':fullPath,'uploadurl':uploadurl,'photoname':photoname};
}else if(uploadpurpose=='logoupload'){
	parent.$('#logourlimage').show();
	$('#logourlimage').html('<img id="imgupload" src="'+fullPath+'" width="100px" height="75px">');
	$('#dellogoimgbtn').removeClass("disabled");
	var url="EventPageContent!updateEventLogoURL?eid="+eid;
	data={'eventLogoURL':fullPath};
}

$.ajax({url:url,
	 type: 'POST',
	 data:data,
	 success:function(result){
		 closepopup();
		 $.ajax({
			   url:'https://graph.facebook.com', 
			   type: 'post',
			   data:{id:serveraddress+'event?eid='+eid, scrape:true},
			   });
		 
			}
});




}
</script>
