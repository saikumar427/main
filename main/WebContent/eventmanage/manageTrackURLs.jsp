<%@taglib uri="/struts-tags" prefix="s" %>

<s:form name="manageTrackURLform" >
<s:hidden name="eid"></s:hidden>
<div class="col-md-12">
<div class="row">
<div class="pull-left">
<%-- <s:property value='TrackURLData.eventURL' /><s:property value='trackcode' /> --%>
<a href="<s:property value='TrackURLData.eventURL' /><s:property value='trackcode' />" target="_blank"><s:property value='TrackURLData.eventURL' /><s:property value='trackcode' /></a>
</div>
<div class="pull-right">
<a href="IntegrationLinks?eid=${eid}" ><s:text name="turl.back.to.all.turl.lnk.lbl"/></a>
</div>
</div>
<br>
<s:iterator value="indivualTrackcodeList" var="TrackURLData" status="indivualTrackcodeListstatus">
	<div class="row">
		  <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><s:text name="turl.track.name.section.header"/></h3>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                    	<div class="col-md-2"><s:text name="turl.track.name.lbl"/>:</div>
                                    	<div class="col-md-10">
                                    	<s:property value="trackcode" />&nbsp;
                                    	<div class="pull-right">
                                    		<input type="button" class="btn btn-primary" id="report" value="<s:text name="turl.report.btn.lbl"/>" />
                                    	</div>
                                    	</div>
                                    </div>
                                </div>                                
                            </div>		
	</div>
	
		<div class="row">
		  <div class="panel panel-primary">
             <div class="panel-heading">
                 <h3 class="panel-title"><s:text name="turl.status.section.header"/></h3>
             </div>
             <div class="panel-body">
             <s:set name="stat" value="status"></s:set>                               
             <div class="row">
             	<div class="col-md-2"><s:text name="global.status.lbl"/>:</div>
             	<div class="col-md-10">
             	
             	<s:set name="stat" value="%{indivualTrackcodeList[0].getStatus()}"></s:set>
             	<span id="changeTlt"><s:if test="%{#stat == 'Approved'}"><s:text name="turl.approved.key"/></s:if><s:else><s:text name="turl.suspended.key"/></s:else></span>
             	<div class="pull-right">
             		<input type="button" class="btn btn-primary" id="changestatus" value="<s:if test="%{#stat == 'Approved'}"><s:text name="turl.suspend.btn.lbl"/></s:if><s:else><s:text name="turl.activate.btn.lbl"/></s:else>"/>
             		&nbsp;
             		<input type="button" class="btn btn-primary" onclick="deleteTrackURL();"  value="<s:text name="turl.delete.btn.lbl"/>" />
             	</div>
             	</div>
             </div>
                 <br>
                 <div class="row">
                 	<div class="col-md-2"></div>
                 	<div class="col-md-10">
                 		<h4><span class="sub-text"><s:text name="turl.status.help.msg"/></span></h4>
                 	</div>
                 </div>
             </div>                                
         </div>		
	</div>
	
		<div class="row">
		  <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><s:text name="turl.manage.url.section.header"/></h3>
                                </div>
                                <div class="panel-body">
                                	<div class="row">
                                    	<div class="col-md-2"><s:text name="turl.manage.url.lbl"/>:</div>
                                    	<div class="col-md-10">
                                    	<a href="<s:property value='TrackURLData.manageEventURL' />" target="_blank">
                                    		<s:property value="TrackURLData.manageEventURL" />
                                    	</a>
                                    	<%-- <s:property value="TrackURLData.manageEventURL" /> --%>&nbsp;<input type="button" value="<s:text name="turl.pwd.btn.lbl"/>"  id="changePassword" class="btn btn-primary pull-right">
                                    	</div>
                                    </div> 
                                    <div class="row">
                                    	<div class="col-md-2"></div>
                                    	<div class="col-md-10">
                                    		<h4><span class="sub-text"><s:text name="turl.manage.url.help.msg"/></span></h4>
                                    	</div>
                                    </div>                                   
                                </div>                                
                            </div>		
	</div>
	
		<div class="row">
		  <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><s:text name="turl.photo.section.header"/></h3>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                    	<div class="col-md-2"><s:text name="turl.photo.lbl"/>:</div>
                                    	<div class="col-md-10">
                                    	<span id="trackphoto">   
		                                <s:if test="%{indivualTrackcodeList[0].getPhoto()!=''}">
            							  <img src='<s:property value="%{indivualTrackcodeList[0].getPhoto()}" />' width="200">
             							</s:if></span>
                                    	&nbsp;<input type="button"  id="changePhoto"  value="<s:text name="turl.add.change.btn.lbl"/>" class="btn btn-primary pull-right">
                                    	</div>
                                    </div>                                     
                                </div>                                
                            </div>		
	</div>
	
		<div class="row">
		  <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><s:text name="turl.msg.section.header"/></h3>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                    	<div class="col-md-2"><s:text name="turl.msg.lbl"/>:
                                    	</div>
                                    	<div class="col-md-10">
                                    	<span id="trackmsg">  <s:property value="%{indivualTrackcodeList[0].getMessage()}" /></span>
                                    	&nbsp;<input type="button" value="<s:text name="turl.add.change.btn.lbl"/>" id="changeMessage"  class="btn btn-primary pull-right"/>
                                    	</div>
                                    </div>       
                                </div>                                
                            </div>		
	</div>
	</s:iterator>
	
	<%-- <s:property value="%{indivualTrackcodeList[0].getPassword()}"/>
     	<s:property value="%{indivualTrackcodeList[0].getMessage()}"/>
     	<s:property value="%{indivualTrackcodeList[0].getPhoto()}"/>
     	<s:property value="%{indivualTrackcodeList[0].getStatus()}"/> --%>
     	 <s:hidden value="%{#TrackURLData.getPassword()}" id="hiddenpassword"></s:hidden>
     	 <s:hidden value="%{#TrackURLData.getMessage()}" id="hiddenmsg"></s:hidden>
     	 <s:hidden value="%{#TrackURLData.getPhoto()}" id="hiddenphoto"></s:hidden>
		
</div><!-- col-md-9 -->
</s:form>

           
            

<script>
/* reports start */
$('#report').click(function(){
	var eid = "${eid}";
	var trackcode = "${trackcode}";
	var scode = "${scode}";
	var url="IntegrationLinks!trackURLReport?eid="+eid+"&trackcode="+trackcode+"&scode="+scode;
	window.location.href=url;
});
/* reports end */

/* suspend & active start */
$('#changestatus').click(function(){
	var eid = "${eid}";
	var trackcode = "${trackcode}";
	var scode = "${scode}";
	//alert(eid+':'+':'+trackcode+':'+scode);
	var status = document.getElementById('changestatus').value;
	if(status == 'Activate'){
		changestatus(trackcode,eid,'Approved',scode);
	}else{
		changestatus(trackcode,eid,'Suspended',scode);
		
	}
});


function changestatus(trackcode,eid,status,scode){
	var url="IntegrationLinks!changeTrackURLStatus?eid="+eid+"&trackcode="+trackcode+"&status="+status;
	$.ajax({
		url : url,
		type : 'get',
		success : function(){
			$('#changeTlt').html(status);
			if(status=='Suspended'){
				$('#changestatus').attr({"value":"Activate"});
				$('#notification-holder').html('');
				$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
				notification(props.turl_status_suspended,"success");
			}else{
				$('#changestatus').attr({"value":"Suspend"});
				$('#notification-holder').html('');
				$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
				notification(props.turl_status_approved,"success");
			}
			//window.location.href="TrackURL!manageTrackURL?eid="+eid+"&trackcode="+trackcode+"&scode="+scode;
		}
	});	
}
/* suspend & active end */

/* delete start */
function deleteTrackURL(){
	var eid = "${eid}";
	var trackcode = "${trackcode}";
	var scode = "${scode}";
    deleteURL(eid,trackcode,scode);
}

function deleteURL(eid,trackcode,scode){
	   var url="IntegrationLinks!checkTrackCodeReport?eid="+eid+"&trackcode="+trackcode;
	   $.ajax({
		   url: url,
		   type: 'get',
		   success: function(t){
			   var result = t;
				  if(result.indexOf('trackcodereportexists')>-1){
					//  alert("There are some registrations with this Track URL. So this Track URL can't be delete");
					 bootbox.confirm(props.turl_cannot_delete_msg, function (result) {	});
				  }else{ 
				     //var agree="Do you want to really delete this Track URL?";
				     bootbox.confirm('<h3>'+props.turl_delete_track_msg+'</h3>'+props.turl_delete_confirm_msg, function (result) {
				    	 	if (result){	
				    	    parent.loadingPopup();
				    	    var deleteurl="IntegrationLinks!deleteTrackURL?eid="+eid+"&trackcode="+trackcode;
		                	$.ajax({
		                		url : deleteurl,
		                		type : 'get',
		                		success: function(t){
		                			var msg = t;
		                			 if(msg.indexOf('delete')>-1)
		                                  window.location.href='/main/eventmanage/IntegrationLinks?eid='+eid;
		                              else
		                                  window.location.href='/main/eventmanage/IntegrationLinks!manageTrackURL?eid='+eid+'&trackcode='+trackcode+'&scode='+scode;
		                		}
		                	});
				    	}
				    });		          
				   }
		   }
		   
	   });
}
/* delete end */

/* password start */
$('#changePassword').click(function(){
	$('#onefieldtextoption').show();
	var eid='${eid}';
	var defval='Password:';
	var lable='';
	openSinglePopUpWithOptions($(this).offset(),passwordsuccess,cancel,$('#hiddenpassword').val(),lable,defval);
});
var passwordsuccess=function(value){
	var eid='${eid}';
	var trackcode = '${trackcode}';
	var password = value;
	var url="IntegrationLinks!changeTrackURLPwd?eid="+eid;
	var data={'trackcode':trackcode,'password':password};
	$.ajax({
	method:'POST',
	data:data,
	url:url,
	success: function( result ) {
			$('#hiddenpassword').val(value);
			$('#singleValuePopUpOptions').hide();
			$('#notification-holder').html('');
			if($('#hiddenpassword').val()=='')
				{
					//alert('No password');
				}else{
					$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
					notification(props.turl_manage_url_pwd_updated_msg,"success");
				}
			
			}
	}); 
		
}
/* password end */

/* photo start */
$('#changePhoto').click(function(){
	$('#onefieldtextoption').show();
	var eid='${eid}';
	var defval=props.turl_photo_url_help_msg;
	var lable='';
	openSinglePopUpWithOptions($(this).offset(),changePhotosuccess,cancel,$('#hiddenphoto').val(),lable,defval);
});
var changePhotosuccess=function(value){
	var eid='${eid}';
	var trackcode = '${trackcode}';
	var photoUrl = value;
	var url="IntegrationLinks!changeTrackURLPhoto?eid="+eid+"&trackcode="+trackcode+"&photourl="+photoUrl;
	$.ajax({
	method:'POST',
	url:url,
	success: function( result ) {
			$('#trackphoto').html('<img src="'+value+'" width="200">');
			$('#hiddenphoto').val(value);
			$('#singleValuePopUpOptions').hide();
			$('#notification-holder').html('');
			if($('#hiddenphoto').val() == '')
				{
					$('#trackphoto').html('');
					//alert('No photo');
				}else{
					$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
					notification(props.turl_photo_updated_msg,"success");
				}
			}
	});
}
/* photo end */

/* message start */
$('#changeMessage').click(function(){
	$('#onefieldtextoption').show();
	var eid='${eid}';
	var defval='Message:';
	var lable='';
	openSinglePopUpWithOptions($(this).offset(),changeStatusMsg,cancel,$('#hiddenmsg').val(),lable,defval);
});
var changeStatusMsg=function(value){
	var eid='${eid}';
	var trackcode = '${trackcode}';
	var message = value;
	var url="IntegrationLinks!changeTrackURLMessage?eid="+eid+"&trackcode="+trackcode+"&message="+message;
	$.ajax({
	method:'POST',
	url:url,
	success: function( result ) {
			$('#trackmsg').html(value);
			$('#hiddenmsg').val(value);
			$('#singleValuePopUpOptions').hide();
			$('#notification-holder').html('');
			if($('#hiddenmsg').val()==''){
					//alert('no message');
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

</script>