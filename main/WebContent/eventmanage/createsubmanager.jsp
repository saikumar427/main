<%@taglib uri="/struts-tags" prefix="s" %>
<s:form action="SubManager!Save" name="submanagerform" id="submanagerform" method="post">
<div class="col-xs-12 col-sm-12 well" >
<s:hidden name="eid" ></s:hidden>
<s:hidden name="powertype" ></s:hidden>
<s:hidden name="subManagerData.subMgrId" id="submgridval"></s:hidden>
<s:set name="mgrid" value="subManagerData.subMgrId" />
<!-- <div class="row col-xs-12"><h2 class="section-main-header">Create Sub Manager</h2><hr></div> -->
<div class="row col-md-12">
	<div class="col-md-12">
		<div id="submgrformerrormessages" style="display:none" class="alert alert-danger"></div>
	</div>
</div>

<div class="row col-md-12 col-x-12 col-sm-12">
	<div class="col-md-6 col-sm-6 col-xs-12" style="margin-bottom: 5px;">
		<s:textfield name="subManagerData.name" size="40" cssClass="form-control" id="name" placeholder="%{getText('sm.name.ph')}" />
	</div>
	<div class="col-md-6 col-sm-6 col-xs-12" style="margin-bottom: 5px;">	
		<s:if test="%{#mgrid != ''}">
		<s:hidden name="subManagerData.email" ></s:hidden>
		<%-- <s:text name="subManagerData.email" ></s:text> --%>
		<input class="form-control" id="disabledInput" type="text" value="<s:text name='subManagerData.email' ></s:text>" disabled>
		
		</s:if>
		<s:else><s:textfield name="subManagerData.email" size="40" cssClass="form-control" id="email" placeholder="%{getText('global.email.lbl')}" /></s:else>
	</div>
	
	<!-- <div class="col-md-1  col-sm-9 col-xs-3 " style="margin-top: 8px;" id="permissionsSettings">
		<div class="pull-right"><i class="fa fa-cog" title="Permissions"></i></div>
	</div> -->
	<%-- <div class="col-md-2 col-sm-3 col-xs-9" >
		<!-- <button type="button" class="btn btn-primary confirmaddSubMgr" id="changeBtnName">Add</button> -->
		 <input type="button" value="<s:text name="global.add.btn.lbl"/>" class="btn btn-primary confirmaddSubMgr" id="changeBtnName" /> 
		<!-- <button type="button" class="btn btn-primary confirmaddSubMgr">Save</button> -->
		<button type="button" class="btn btn-active canceladdSubMgr"><i class="fa fa-times"></i></button>
	</div> --%>
</div>
<div style="clear:both;"></div>
<div class="row col-md-12" id="permissionDetails"  style=" display: block;">
 <div class="col-md-12 row" style="margin: 10px 0px;">
	<div class="col-md-2 row"><span class=""><s:text name="sm.permissions.lbl"/></span></div>
	<div class="col-md-10">
		<div class="row">
			<div class="col-md-12 sm-font">
				<a href="javascript:;"  id="CheckAll" ><s:text name="global.select.all.lbl"/></a>&nbsp;&nbsp; <a href="javascript:;"  id="UnCheckAll" ><s:text name="global.clear.all.lbl"/></a>
			</div>
		</div>
		<div class="col-md-12 row">
				<s:iterator value="%{actionPermissionList}" var="actions" status="liststat">
				<s:set name="mapkey" value="%{key}"/>
				<s:hidden name="%{'actionPermissionMap[\"'+#mapkey+'\"]'}" value="%{value}" />
				<s:if test="%{value=='Sell Tickets' || value=='Check In'}">
				</s:if>
				<s:else>
				<s:if test="#liststat.odd==true">
				<div class="col-md-6" style="padding: 3px;">
				<s:checkbox  cssClass="checkpermission" name="subMgrPermissionList"  fieldValue="%{key}"   value="%{subMgrPermissionList.contains(key)}" />&nbsp;${value}
				</div>
				</s:if>
				<s:else>
				<div class="col-md-6" style="padding: 3px;">
				<s:checkbox cssClass="checkpermission"  name="subMgrPermissionList"  fieldValue="%{key}"   value="%{subMgrPermissionList.contains(key)}" />&nbsp;${value}
				</div>
				</s:else>
				</s:else>
				</s:iterator>				
		</div>				
	</div></div> 
	<div class="row col-md-12" style="margin: 10px 0px;">
	<div class="col-md-12 row "><s:text name="sm.eventbee.mgr.app.perm.lbl1"/><br><s:text name="sm.eventbee.mgr.app.perm.lbl2"/></div>
	<!-- <div class="col-xs-9 col-xs-offset-3"> -->
	<div class="col-xs-2"></div>
	<div class="col-xs-10 row">
		<div class="col-md-6 col-sm-6 col-xs-12 row" style="padding: 3px;">
		<s:checkbox name="subMgrPermissionList" cssClass="checkpermission" fieldValue="%{'ST'}"  value="%{subMgrPermissionList.contains('ST')}" />&nbsp;<s:text name="sm.sell.tickets.lbl"/>
		</div>
		<div class="col-md-6 col-sm-6 col-xs-12 row" style="padding: 3px;">
		<s:checkbox name="subMgrPermissionList" cssClass="checkpermission" fieldValue="%{'CI'}"  value="%{subMgrPermissionList.contains('CI')}" />&nbsp;<s:text name="sm.attendee.check.in.lbl"/>
		</div>
	</div>
	<%-- <div class="col-xsmd-offset-3 col-md-9">
		<div class="col-xs-6" style="padding: 3px;">
		<s:checkbox name="subMgrPermissionList" cssClass="checkpermission" fieldValue="%{'ST'}"  value="%{subMgrPermissionList.contains('ST')}" />&nbsp;<s:text name="sm.sell.tickets.lbl"/>
		</div>
		<div class="col-md-6" style="padding: 3px;">
		<s:checkbox name="subMgrPermissionList" cssClass="checkpermission" fieldValue="%{'CI'}"  value="%{subMgrPermissionList.contains('CI')}" />&nbsp;<s:text name="sm.attendee.check.in.lbl"/>
		</div>
	</div> --%>
	<!-- </div> -->
</div>
<div class="row col-md-12 " style="margin: 10px 0px;">
		<div class="col-md-12 row"><s:if test="%{#mgrid == ''}"><input type="checkbox" id="sendmail"  class="sendemail" name="subManagerData.sendEmail" checked="checked"/>&nbsp;
           <s:text name="sm.email.login.info.sub.mgr.lbl"/>&nbsp;
        </s:if></div>
</div>
<div class="col-md-12 col-sm-12 col-xs-12 center" >
		<!-- <button type="button" class="btn btn-primary confirmaddSubMgr" id="changeBtnName">Add</button> -->
		 <input type="button" value="<s:text name="global.add.btn.lbl"/>" class="btn btn-primary confirmaddSubMgr" id="changeBtnName" /> 
		<!-- <button type="button" class="btn btn-primary confirmaddSubMgr">Save</button> -->
		<button type="button" class="btn btn-cancel canceladdSubMgr"><i class="fa fa-times"></i></button>
	</div>
</div>






</div><!-- final div col-md-12 -->
</s:form>
<style>
#permissionsSettings{
cursor: pointer;
}</style>
<script>
var submgrid = '${subManagerData.subMgrId}';
var subid=0;	
function submitSubManagerForm(){
	submitForm('submanagerform','submgrformerrormessages',++subid);
	
}
$(document).ready(function(){
	$('#submgrid').prop( "disabled", true );
});
function submitForm(formId, errMsgDivid,j){	
	if(j==1)
	submitFormAndHandle(formId, errMsgDivid, true);
}


var alertMessage='';
function submitFormAndHandle(formId, errMsgDivid, reloadYN){
	// var submanager = getSubmanagerData();
	alertMessage= $('#changeBtnName').val();
	if('Add'==alertMessage)
		showProcessing('submanagerdata');
		    $.ajax({
	    	url : "SubManager!Save",
	    	type : "POST",
	    	 data: $("#"+formId).serialize(),
	    	success : function(t){
	    		  var result=t;		
			        if(!isValidActionMessage(result)) return;
			       if(result.indexOf("success")>-1){
			    	   
			    	   rebuildData(alertMessage);
			    	   /* var subId=$('#submgrid').val();
			    	   if(subId==''){
				       var subMgrId=result.split('_')[1];
			    	 	createItem(jsub,{"submgrid":subMgrId,"isLast":"N","status":"Active","email":$('#email').val(),"isFirst":"Y"});
						$('#submnagerdata').dataTable().fnDestroy(false);
						callDataTable();
			    	   hideProcessing();
			        	if(submgrid!=null && submgrid!='' && submgrid!=undefined){
			        		$('#subMgrBox').html('');
			        	}
			        	else 
			        		$('#subMgrBox').html('');
			       }else{
			    	   hideProcessing();
			    	   $('#subMgrBox').html('');
			       } */
			    	   
			          }			       
			         else {		
			        	$('ul').attr('style','list-style-type:none');
		            	$('#'+errMsgDivid).html(result);
		            	$('#'+errMsgDivid).show();
		            	 hideProcessing();
			        	 subid=0;
			        	 $('html, body').animate({ scrollTop: $("#mainSubMgrBox").height()}, 1000);
			        }
	    	}	
	    });
	}

var getSubmanagerData = function() {
    var submanager = {};
	submanager.email = $('#email').val();
	submanager.id = submanager;	
    return submanager;
};


var getSubManagerDetails = function(){
	$.ajax({
		url : 'SubManager!getSubManagerDetails?eid='+eid,
		type : 'POST',
		success : function(response){	
			//oTable.fnDestroy();
			//oTable.fnClearTable();
			//oTable.fnDraw();
			//$('#submnagerdata').html('');
			loadedData(JSON.parse(response));
		}
	});
}

/* $('#permissionsSettings').click(function(){
	$('#permissionDetails').slideToggle(slideTime);
}); */
$('.confirmaddSubMgr').click(function(){
	trimSubMgrInputValues();
	 submitSubManagerForm();
});

/* $('.canceladdSubMgr').click(function(){
	$('#submgrid').prop("disabled",false);
    $('#subMgrBox').slideUp(slideTime);
    var sID = $('#submgridval').val();
    showLinks(sID);
    $('.editRow').remove();
});*/

$('.canceladdSubMgr').click(function(){ 
	 var sID = $('#submgridval').val();
	 $('html, body').animate({ scrollTop: $("#submgrid").offset().top-200}, 1000);
	$('#submgrid').prop("disabled",false);
	$('#mainSubMgrBox').slideUp(1000);
    $('#mainSubMgrBox').html('');
	$('#subMgrBox').parent().remove();
    $('#subMgrBox').slideUp(1000);
    showLinks(sID);
    $('.editRow').remove();
});  

$(function(){
	$('input.checkpermission').iCheck({
        checkboxClass: 'icheckbox_square-grey',
        radioClass: 'iradio_square-grey'
    });
 	
   $('input.sendemail').iCheck({
        checkboxClass: 'icheckbox_square-grey',
        radioClass: 'iradio_square-grey'
    });
	
	
}); 
$('#CheckAll').click(function(event) {  
    $('.checkpermission').iCheck('check');
});

 $('#UnCheckAll').click(function(event) { 
 	 $('.checkpermission').iCheck('uncheck');      
});
  
</script>
