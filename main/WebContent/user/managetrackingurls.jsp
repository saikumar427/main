<%@taglib uri="/struts-tags" prefix="s"%>

<div style="padding:20px">

   <div class="row">
   <div id="errors" style="display:none;"class="alert alert-danger"></div>
   <div id="notification-holder"></div>
       <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><s:text name="turl.manage.url.section.header"/></h3>
            </div>
            <div class="panel-body">
            <div class="row">
                	<div class="col-md-2">
                	<s:text name="turl.manage.url.section.header"/>:
                	</div>
                	<div class="col-md-10">
                		<s:property value="%{eventURL}"></s:property>
                		<div class="pull-right">
                			<!-- <input type="button" value="Password" id="trckpwd" class="btn btn-primary" onclick="getTrackPassword();"/> -->
                			<input type="button" value="<s:text name="turl.pwd.lbl"/>" id="trckpwd" class="btn btn-primary" />
                		</div>
                	</div>
                	
                <div class="col-md-2"></div>
                <div class="col-md-10">
                	<font color="" style="font-size:11px"><s:text name="trul.manage.partners.affiliates.msg"/></font>
                </div>
            </div>
            </div>                              
       </div>
                            
		<div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><s:text name="turl.widget.code.lbl"/></h3>
            </div>
            <div class="panel-body">
            	<div class="row">
                <div class="col-md-12"><s:text name="trul.manage.cp.paste.blog"/>:</div>
                <div class="col-md-12">
                	<textarea cols="80" rows="5" onClick="this.select()"  readonly="true" class="form-control"><script type='text/javascript' language='JavaScript' src='${serveraddress}/home/js/widget/eventregistration.js'></script><iframe  id='_EbeeIFrame_${trackid}' name='_EbeeIFrame_${trackid}'  src='${serveraddress}/eregister?eid=${eid}&track=${trackcode}&viewType=iframe;resizeIFrame=true&context=web'  height='0' width='700'></iframe>
					</textarea>
				</div>
				</div>
           </div>                                
       </div>
                          
                            
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><s:text name="turl.photo.lbl"/></h3>
            </div>
             <div class="panel-body">
             <div class="row">
             	<div class="col-md-2">
             		<s:text name="turl.photo.lbl"/>:
             	</div>
             	<div class="col-md-10">
             	 <span id="trackphoto">   
                   <s:if test="%{photo!=''}">
				  		<img src='<s:property value="%{photo}" />' width="200">
					</s:if>
             	</span> 
             		<%-- <s:if test="%{photo!=''}"><img src=<s:property value="photo" /> width="200"></s:if> --%>
             		<div class="pull-right">
             			<input type="button" value="<s:text name="turl.add.change.btn.lbl"/>" id="addphotobtn" class="btn btn-primary"/>
             			<!-- <input type="button" value="Add/Change" id="addphotobtn" class="btn btn-primary" onclick="getTrackPhoto();"/> -->
             		</div>
             	</div>
             </div>
             </div>                                
         </div>
                            
         <div class="panel panel-primary">
             <div class="panel-heading">
                 <h3 class="panel-title"><s:text name="turl.msg.lbl"/></h3>
             </div>
             <div class="panel-body">
             <div class="row">
              	<div class="col-md-2">
              		<s:text name="turl.msg.lbl"/>:
              	</div>
              	<div class="col-md-10">
              		<span id="trackmsg">  <s:property value="message" /></span>
              		<div class="pull-right">
              			<input type="button" value=" <s:text name="turl.add.change.btn.lbl"/>" id="addmsgbtn" class="btn btn-primary"/>
              			<!-- <input type="button" value="Add/Change" id="addmsgbtn" class="btn btn-primary"  onclick="getTrackMessage();"/> -->
              		</div>
              	</div>
              </div>                                   
              </div>                                
        </div>
                            
       <div class="panel panel-primary">
           <div class="panel-heading">
               <h3 class="panel-title"><s:text name="trul.manage.report.lbl"/></h3>
           </div>
           <div class="panel-body">
               <a href="#" onclick="getReports()"> <s:text name="trul.manage.get.reports"/></a>
           </div>                                
       </div>
   </div>
</div>

<%-- <s:property value="password" />
<s:property value="%{password}" />
<s:property value="photo" />
<s:property value="%{photo}" />
<s:property value="message" />
<s:property value="%{message}" /> --%>
<s:hidden value="%{password}" id="hiddenpassword"></s:hidden>
<s:hidden value="%{message}" id="hiddenmsg"></s:hidden>
<s:hidden value="%{photo}" id="hiddenphoto"></s:hidden>




<script src="/main/bootstrap/js/custom.js"></script>
<script>

var eid='${eid}';
var trackcode='${trackcode}';
var scode='${secretcode}';

/* password start */
$('#trckpwd').click(function(){ 
	$('#onefieldtextoption').show();
	var defval=props.trul_manage_password_lbl;
	var lable='';
	openSinglePopUpWithOptions($(this).offset(),passwordsuccess,cancel,$('#hiddenpassword').val(),lable,defval);
	});
	var flag=false;
var passwordsuccess = function(value){
	var eid='${eid}';
	var trackcode='${trackcode}';
	var scode='${secretcode}';
	var password = value;
	if(password=="")
	{
		flag=true;
		//alert('password cannot be empty');
		$('#errors').show();
		$('#errors').html('<ul><li><span>'+props.epc_alert_password_empty+'</span></li> </ul>');
	}
	else{
		flag=false;
	var url="TrackUrlManage!savePassword?eid="+eid+"&trackcode="+trackcode+"&password="+password+"&secretcode="+scode;
	$.ajax({
	method:'POST',
	url:url,
	success: function( result ) {
		$('#notification-holder').html('');
			$('#hiddenpassword').val(value);
			$('#singleValuePopUpOptions').hide();
			$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
			notification(props.mtp_pwd_update_lbl,"success");
			$('#errors').hide();
			}
	}); 
		
}
}
/* password end */

/* photo start */
$('#addphotobtn').click(function(){
	$('#onefieldtextoption').show();
	var lable='';
	var defval=props.turl_photo_url_help_msg;
	openSinglePopUpWithOptions($(this).offset(),changePhotosuccess,cancel,$('#hiddenphoto').val(),lable,defval);
});
var changePhotosuccess = function(value){ 
	var eid='${eid}';
	var trackcode='${trackcode}';
	var scode='${secretcode}';
	var photo = value;
	var url ="TrackUrlManage!savePhotoUrl?eid="+eid+"&photo="+photo+"&trackcode="+trackcode+"&secretcode="+scode;
	$.ajax({
		method:'POST',
		url:url,
		success:function(result){
			$('#notification-holder').html('');
			$('#trackphoto').html('<img src="'+value+'" width="200">');
			$('#hiddenphoto').val(value);
			$('#singleValuePopUpOptions').hide();
			if($('#hiddenphoto').val() == '')
				$('#trackphoto').html('');
			else{
				$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
				notification(props.turl_photo_updated_msg,"success");
			}
		}
	});
}

/* photo end */

/* message start */
$('#addmsgbtn').click(function(){
	$('#onefieldtextoption').show();
	var lable='';
	var defval=props.global_message_lbl;
	openSinglePopUpWithOptions($(this).offset(),changeMessageccess,cancel,$('#hiddenmsg').val(),lable,defval);
});
var changeMessageccess = function(value){
	var eid='${eid}';
	var trackcode='${trackcode}';
	var scode='${secretcode}';
	var message=value;
	var url="TrackUrlManage!saveMessage?eid="+eid+"&message="+message+"&trackcode="+trackcode+"&secretcode="+scode;
	$.ajax({
		method:'POST',
		url:url,
		success:function(result){
			$('#notification-holder').html('');
			$('#trackmsg').html(value);
			$('#hiddenmsg').val(value);
			$('#singleValuePopUpOptions').hide();
			if($('#hiddenmsg').val()==''){
				//alert('no msg');
			}else{
				$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
				notification(props.turl_message_updated_msg,"success");
			}
		}
	});
}
/* message end */
var cancel=function(){
};


var isValidActionMessage=function(messageText){
	if(messageText.indexOf('nologin')>-1||messageText.indexOf('login!authenticate')>-1){
	    	alert(props.global_no_login_msg);
	    	window.location.href="/main/user/login";
	    	return false;
	    }
	 else if(messageText.indexOf('Something went wrong')>-1){
	 		alert(props.global_not_have_perm_msg);
	 		closediv();
	 		return false;
	 	} 
	    return true;	
	}


/* function getTrackPassword(){
   
   var url = "TrackUrlManage!getTrackPassword?eid="+eid+"&trackcode="+trackcode+"&secretcode="+scode;	
   popup(url,'Track Manage Password');
  
} */

/* function getTrackPhoto(){ 
	 var url="TrackUrlManage!getTrackPhoto?eid="+eid+"&trackcode="+trackcode+"&secretcode="+scode;	
	 popup(url,'Track Manage Photo');
} */

/* function getTrackMessage(){	  
   var url="TrackUrlManage!getTrackMessage?eid="+eid+"&trackcode="+trackcode+"&secretcode="+scode;		
	 popup(url,'Track Manage Message');
} */

function getReports(){
window.location.href='TrackUrlManage!getTrackUrlReports?eid='+eid+'&trackcode='+trackcode+'&secretcode='+scode;	 
}


/* function popup(url,header){

$('.modal-title').html(header);
$('#myModal').on('show.bs.modal', function () {
$('iframe#popup').attr("src",url);
$('iframe#popup').css("height","200px"); 
});
$('#myModal').modal('show');
}

$('#myModal').on('hide.bs.modal', function () {
$('iframe#popup').attr("src",'');
$('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" style="height:440px" frameborder="0"></iframe>');
}); */

/*function closepopup(){
$('#myModal').modal('hide');
window.location.href=window.location.href;
}*/

function submitform(formId,locurl){
	jQuery.ajax({  	  
  	   url: locurl,
  	   type: "POST",
  	   data: formId,
  	   success: function(response) {
  		if(!isValidActionMessage(response)) return;  
  	    window.location.href='TrackUrlManage!getManagePage?eid='+eid+'&trackcode='+trackcode+'&secretcode='+scode;	  
  	   },failure:function(){
  		  alert("fail");
  	   }});
}

</script>
