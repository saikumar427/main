var _listId;
var j=0;
var addManualJS = function(eventfnc,event,jsonobj){
	var paramslist = eval(jsonobj);	
	addManual(paramslist.listId,paramslist.userId);
}
var mergeListJS = function(eventfnc,event,jsonobj){
	var paramslist = eval(jsonobj);	
	mergeLists(paramslist.listId,paramslist.userId)
}

var fileUploadJS = function(eventfnc,event,jsonobj){
	var paramslist = eval(jsonobj);	
	fileUpload(paramslist.listId,paramslist.userId)
}
function addNewMailList(userId){
	var url='maillistmanage!addMailList?userId='+userId;	
	YAHOO.ebee.popup.wait.show();
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:successMethod,failure:failure});
	
}
var successMethod = function(o){
	YAHOO.ebee.popup.wait.hide();
	YAHOO.ebee.popup.dialog.setHeader("Mail List");
	YAHOO.ebee.popup.dialog.setBody(o.responseText);
	var myButtons = [
	                 { text: "Submit", handler: saveMail},
	                 { text: "Cancel", handler: handleCancel }
	             ];
	YAHOO.ebee.popup.dialog.cfg.queueProperty("buttons", myButtons);   
	YAHOO.ebee.popup.dialog.render();
	YAHOO.ebee.popup.dialog.show();
}

var saveMail=function(){
++j
if(j==1)
saveMailList();
}

var saveMailList=function(){
YAHOO.ebee.popup.wait.show();
	 $('maillisterrormessages').update('Processing...');
		 $('mailListForm').request({
		   onFailure: function() { $('maillisterrormessages').update('Failed to get results') },
        onSuccess: function(t) {
	       var result=t.responseText;
	       if(result.indexOf("success")>-1){		       
	    	   YAHOO.ebee.popup.dialog.cancel();	        	
	    	   window.location.reload();		        	
	        }
	        else
	        {
	        	YAHOO.ebee.popup.wait.hide();
	        	$('maillisterrormessages').update(result);
	        	j=0;
	        }
	    }
    });
}
function editMailListDetails(eventfnc,event,jsonobj1){
	var jsonobj=eval(jsonobj1);
	listId=jsonobj.listId;
	userId=jsonobj.userId;
	YAHOO.ebee.popup.wait.show();
	var url='editmaillist!editMailList?userId='+userId+'&listId='+listId;	
	var dynatimestamp = new Date();
	url += "&dynatimestamp="+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:successMethod,failure:failure});
}
var failure = function(){
	alert("error");
	}

function mListManage(listId){
	window.location.href="../myemailmarketing/maillistmanage!listInfo?listId="+listId;
}

function editmember(memberId){
	YAHOO.ebee.popup.wait.show();
	var url='maillistmanage!editMemberManual?listId='+_listId+'&memberId='+memberId;
	var dynatimestamp = new Date();
	url += "&dynatimestamp="+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:manualAddSuccessMethod,failure:failure});
}
function addManual(listId,userId){
YAHOO.ebee.popup.wait.show();
var url='maillistmanage!addMemberManual?userId='+userId+'&listId='+listId;
var dynatimestamp = new Date();
url += "&dynatimestamp="+dynatimestamp.getTime();
var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:manualAddSuccessMethod,failure:failure});

}
var manualAddSuccessMethod=function(o){
	YAHOO.ebee.popup.wait.hide();
	YAHOO.ebee.popup.dialog.setHeader("Member Details");
	YAHOO.ebee.popup.dialog.setBody(o.responseText);

	var myButtons = [
	                 { text: "Submit", handler: saveManualMember},
	                 { text: "Cancel", handler: handleCancel }
	             ];
	YAHOO.ebee.popup.dialog.cfg.queueProperty("buttons", myButtons);   
	YAHOO.ebee.popup.dialog.render();
	YAHOO.ebee.popup.dialog.show();
}

var i=0;
var saveManualMember=function()
{
++i;
if(i==1)
saveMemberManual();
}

var saveMemberManual=function(){
var email=document.getElementById('email').value;
email=email.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
document.getElementById('email').value=email;
	 $('membermanualadderrors').update('Processing...');
	 YAHOO.ebee.popup.wait.show();
		 $('membermanualadd').request({
		   onFailure: function(t) { 
			 var result=t.responseText;
		    // alert("result"+result);
			 $('membermanualadderrors').update('Failed to get results') },
        onSuccess: function(t) {
	       var result=t.responseText;
	      // alert("result"+result);
	       if(result.indexOf("success")>-1){		       
	    	   YAHOO.ebee.popup.dialog.cancel();	        	
	        	window.location.reload();		        	
	        }
	        else
	        {
	        YAHOO.ebee.popup.wait.hide();
           $('membermanualadderrors').update(result);
           i=0;
	        }
	       
	       //window.location.reload(true);
	    }
    });
	//this.cancel();
	}
function mergeLists(listId,userId){
	YAHOO.ebee.popup.wait.show();
	var url='maillistmanage!mergeLists?userId='+userId+'&listId='+listId;
	var dynatimestamp = new Date();
	url += "&dynatimestamp="+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:mergeListSuccessMethod,failure:failure});

}
var mergeListSuccessMethod=function(o){
	YAHOO.ebee.popup.wait.hide();
	YAHOO.ebee.popup.dialog.setHeader("Merge Lists");
	YAHOO.ebee.popup.dialog.setBody(o.responseText);
	YAHOO.ebee.popup.dialog.cfg.setProperty("height", "200px");
	var myButtons = [
	                 { text: "Submit", handler: saveMergeList},
	                 { text: "Cancel", handler: handleCancel }
	             ];
	YAHOO.ebee.popup.dialog.cfg.queueProperty("buttons", myButtons);   
	YAHOO.ebee.popup.dialog.render();
	YAHOO.ebee.popup.dialog.show();
}
var saveMergeList=function(){
	 $('mergelistserrors').update('Processing...');
		 $('mergelists').request({
		   onFailure: function() { $('mergelistserrors').update('Failed to get results') },
       onSuccess: function(t) {
	       var result=t.responseText;
	       if(result.indexOf("success")>-1){		       
	    	   YAHOO.ebee.popup.dialog.cancel();	        	
	        	window.location.reload();	
	        	
	        }
	        else
	        {
          $('mergelistsadderrors').update(result);
	        }
	       
	       //window.location.reload(true);
	    }
   });
	//this.cancel();
}
var currentDivId="";
function getFileURL(divId,listId){
	currentDivId=divId;
	url="../membertasks/FileUpload?listId="+listId;
	 showYUIinIframe("File Upload",url,320,202);
	
}
var showFileUploadMethod=function(o){
	alert("in show file upload");
	YAHOO.ebee.popup.wait.hide();
	YAHOO.ebee.popup.dialog.setHeader("File Upload");
	YAHOO.ebee.popup.dialog.setBody(o.responseText);
	//YAHOO.ebee.popup.dialog.cfg.setProperty("height", "200px");
	var myButtons = [
	                 { text: "Submit", handler: saveUploadedFile},
	                 { text: "Cancel", handler: handleCancel }
	             ];
	YAHOO.ebee.popup.dialog.cfg.queueProperty("buttons", myButtons);  
	YAHOO.ebee.popup.dialog.render();
	YAHOO.ebee.popup.dialog.show();
}
var saveUploadedFile=function(){
	alert("in save file upload");
	var a=document.fileUploadForm.listId.value;
	document.fileUploadForm.action="../membertasks/FileUpload!save?listId="+a;
	alert("a"+a);
	//document.fileUploadForm.submit();
	  $('fileUploadForm').request({
		   onFailure: function(error) { alert("Error"+error.statusText); },
      onSuccess: function(t) {
	       var result=t.responseText;
	       alert("Success"+t.responseText);
	      // showMemberData("");	      
	    }
  });
	//this.cancel();
}
function closeFileUploadWindow(){
	YAHOO.ebee.popup.dialog1.cancel();
}
function setFilePath(ext){
	var fullPath='';
	document.getElementById(currentDivId).value=fullPath;
	YAHOO.ebee.popup.dialog1.cancel();
	showMemberData(fullPath,ext);
}
function fileUpload(listId,userId){
_listId=listId;
	getFileURL("filepath",listId);
}
function showMemberData(fullPath,ext){
	YAHOO.ebee.popup.wait.show();
	var url='maillistmanage!showValidationData?filePath='+fullPath+'&listId='+_listId+'&ext='+ext;
	var dynatimestamp = new Date();
	url += "&dynatimestamp="+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:showValidationDataSuccessMethod,failure:failure});

	//var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:showMemberDataSuccessMethod,failure:failure});

}
var showValidationDataSuccessMethod=function(o){
	YAHOO.ebee.popup.wait.hide();
	YAHOO.ebee.popup.dialog.setHeader("Validated Data Result");
	YAHOO.ebee.popup.dialog.setBody(o.responseText);
	var count = document.getElementById("mattribcount").value;
	var ext = document.getElementById("ext").value;
	var myButtons = [
	               //  { text: "Save", handler: {fn:saveFileMemberData,obj:{"mattribcount":count} }},
	                 { text: "Submit", handler:{fn:saveFileMemberData,obj:{"mattribcount":count,"ext":ext}}},
	                 { text: "Cancel", handler: {fn:cancelFunction,obj:{"listid":_listId,"ext":ext}} }
	             ];
	YAHOO.ebee.popup.dialog.cfg.queueProperty("buttons", myButtons);   
	YAHOO.ebee.popup.dialog.render();
	YAHOO.ebee.popup.dialog.show()
}


var cancelFunction=function(event,jsonobj)
{
var jsondata=eval(jsonobj);
var url="maillistmanage!deleteFile?listId="+jsondata.listid+'&ext='+jsondata.ext;
new Ajax.Request(url, {
			  onSuccess: function(transport) {
					YAHOO.ebee.popup.dialog.cancel();
		      }
		});	
}
var saveFileMemberData=function(event,jsonobj){
	var jsondata = eval(jsonobj);
	document.getElementById('ext').value=jsondata.ext;
	//createElements(jsondata.mattribcount);	
	//document.getElementById("memberdata").submit();
	 $("memberdatavalidatederrors").update('Processing...');
		 $("memberdatavalidated").request({
			onFailure: function(t) {
			 alert("failure"+t.responseText);
			 var result=t.responseText;
		       alert("result"+result);
			 $("memberdatavalidatederrors").update('Failed to get results') },
      onSuccess: function(t) {
	       var result=t.responseText;
	       if(result.indexOf("success")>-1){		       
	    	YAHOO.ebee.popup.dialog.cancel();	        	
	        window.location.reload();		        	
	        }
	        else
	        {
         $("memberdatavalidatederrors").update(result);
	        }
	       
	       //window.location.reload(true);
	    }
  });
	//this.cancel();
}
function createHiddenTextElement(index,val){
	var pname=document.createElement("input");
	pname.type="hidden";
	pname.name="optionsList["+index+"]";	
	pname.value=val;
	return pname;
}
/*function createElements(mattribscount){
	var parent = document.getElementById("optlist");
	parent.innerHTML  = "";	
	for (var i = 0; i < mattribscount; i++) {	
		var mattribobj = document.getElementById("mattrib");	
		var selindex = mattribobj.selectedIndex;
		var selected_text = mattribobj.options[selindex].value;
		var obj = createHiddenTextElement(i,selected_text);
		parent.appendChild(obj);
	}
}*/
/*var showValidationResult=function(event,jsonobj){
	var jsondata = eval(jsonobj);
	createElements(jsondata.mattribcount);
	$("memberdata").request({
		onFailure: function(t) {
		 alert("failure"+t.responseText);		
		 $('memberdataerors').update('Failed to get results') },
			 onSuccess: function(t) {
			 if(t.responseText=='success')
			 window.location.reload();
			 showValidationResultSuccess(t.responseText);
			 	
	}});
	//var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:showValidationResultSuccess,failure:failure});

}
var showValidationResultSuccess=function(o){
	YAHOO.ebee.popup.dialog.hide();
	YAHOO.ebee.popup.dialog.setHeader("Validated Data Result");
	YAHOO.ebee.popup.dialog.setBody(o);
	var count = document.getElementById("mattribcount").value
	var myButtons = [
	                 { text: "Submit", handler: {fn:saveFileMemberData,obj:{"mattribcount":count} }},
	                 //{ text: "Save", handler:{fn:showValidationResult,obj:{"mattribcount":count}}},
	                 { text: "Cancel", handler: {fn:cancelFunction,obj:{"listid":_listId}} }
	             ];
	YAHOO.ebee.popup.dialog.cfg.queueProperty("buttons", myButtons);   
	YAHOO.ebee.popup.dialog.render();
	YAHOO.ebee.popup.dialog.show();
}*/
var deleteMailListDetails=function(eventfnc,event,jsonobj){
	YAHOO.ebee.popup.wait.show();
	var jsondata=eval(jsonobj);
	var agree=true;
	agree=confirm("All members in the list will be deleted. Do you want to continue?");
	var url="maillistmanage!deleteList?listId="+jsondata.listId+"&userId="+jsondata.userId; 
	if (agree){
		 new Ajax.Request(url, {
			  method: 'get',
			  onSuccess: function(transport) {
				var result=transport.responseText;
				YAHOO.ebee.popup.wait.hide();
				if(!isValidActionMessage(transport.responseText)){
    				return;
    			}			
					window.location.href="../myemailmarketing/home";
				
		      }
		});
	}
	else{
		YAHOO.ebee.popup.wait.hide();
		return false;
	}
}
function deletemember(memberId,listId){
	YAHOO.ebee.popup.wait.show();
	agree=confirm("Member will be deleted permanently. Do you want to continue?");
	var url="maillistmanage!deleteMember?memberId="+memberId+"&listId="+listId;
	if (agree){
		 new Ajax.Request(url, {
			  method: 'get',
			  onSuccess: function(transport) {
				var result=transport.responseText;
				YAHOO.ebee.popup.wait.hide();
				if(!isValidActionMessage(transport.responseText)){
    				return;
    			}			
					window.location.reload();
				
		      }
		});
	}
	else{
		YAHOO.ebee.popup.wait.hide();
		return false;
	}
}
function bulkUploadJS(eventfnc,event,jsonobj) {
	var url = 'maillistmanage!bulkUpload?userId='+jsonobj.userId+'&listId='+jsonobj.listId
	loadURLBasedWindow(url, bulkSuccessMethod);
}
var bulkSuccessMethod = function(responseObj) {
	showPopUpDialogWindow(responseObj, "Bulk Upload",saveBulk, handleBulkUploadCancel);
}
var handleBulkUploadCancel=function(){
	YAHOO.ebee.popup.dialog.cancel();
}
var saveBulk = function() {
	submitBulkUplaodFormAndHandleNext('bulkuplaodform', 'summary');
}
function submitBulkUplaodFormAndHandleNext(formId, msgDivid){
YAHOO.ebee.popup.wait.show();
$(formId).request({
        onFailure: function() { $(msgDivid).update('Failed to get results'); },
        onSuccess: function(t) {
	        var result=t.responseText;
	        var jsondata=eval('('+result+')');
	        YAHOO.ebee.popup.wait.hide();
	        if(!isValidActionMessage(result)) return;
	        YAHOO.ebee.popup.dialog.cancel();
			showValidateData(jsondata);
			//prepareSummary(jsondata,msgDivid);	
           	document.getElementById("bulktextarea").value=jsondata.invalidlines;
           	}
    });
}
/*function prepareSummary(jsondata,msgDivid){
var duplicates=jsondata.duplicates;
	var invalid=jsondata.invalidcount;
	var total=jsondata.totallines;
	var valid=jsondata.validcount;
	var summary="&nbsp;Total Lines: "+total+"<br>";
	var x=Number(valid)-Number(duplicates);
	if(duplicates>0 && x>0){
	summary=summary+"&nbsp;Valid Lines: "+valid+", Added Members: "+(valid-duplicates)+", Duplicates: "+duplicates+"<br>";
	}else if(duplicates>0 && x<=0){
	summary=summary+"&nbsp;Valid Lines: "+valid+", Duplicates: "+duplicates+"<br>";
	}else if(valid>0 && duplicates<1){
				summary=summary+"&nbsp;Valid Lines: "+valid+", Added Members: "+valid+"<br>";
			}
	if(invalid>0){
	summary=summary+"&nbsp;Invalid Lines: "+invalid+" (Displayed in the box below)"+"<br>";
	}
	document.getElementById(msgDivid).innerHTML=summary;*/
	
	//window.location.reload();
//}


function showValidateData(jsondata)
{
YAHOO.ebee.popup.wait.show();
var url='maillistmanage!showBulkValidateData?userId='+jsondata.userId+'&listId='+jsondata.listId+'&bulkmails='+jsondata.data;
	var dynatimestamp = new Date();
	url += "&dynatimestamp="+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:showBulkValidationData,failure:failure});
}

var showBulkValidationData=function(o){
	YAHOO.ebee.popup.dialog.hide();
	YAHOO.ebee.popup.dialog.setHeader("Validated Data Result");
	YAHOO.ebee.popup.dialog.setBody(o.responseText);
	YAHOO.ebee.popup.wait.hide();
	var listid=document.getElementById('listId').value;
	var userid=document.getElementById('userId').value;
	var bulkmails=document.getElementById('bulkmails').value;
	var myButtons = [
	                 { text: "Submit", handler: {fn:saveBulkData,obj:{"bulkdata":bulkmails,"listid":listid,"userid":userid} }},
	                 //{ text: "Save", handler:{fn:showValidationResult,obj:{"mattribcount":count}}},
	                 { text: "Cancel", handler: {fn:showBulkData,obj:{"bulkdata":bulkmails,"listid":listid,"userid":userid}} }
	             ];
	YAHOO.ebee.popup.dialog.cfg.queueProperty("buttons", myButtons);   
	adjustYUIpopup();
}

var saveBulkData=function(event,jsonobj)
{
YAHOO.ebee.popup.wait.show();
var jsondata=eval(jsonobj);
var url="maillistmanage!SaveBulk?listId="+jsondata.listid+'&userId='+jsondata.userid+'&bulkmails='+jsondata.bulkdata+'&action=insert';
new Ajax.Request(url, {
			  onSuccess: function(transport) {
		      	window.location.reload();
		      }
		});	
}

var showBulkData=function(event,jsonobj)
{
var jsondata=eval(jsonobj);
var url = 'maillistmanage!bulkUpload?listId='+jsondata.listid+'&userId='+jsondata.userid+'&bulkmails='+jsondata.bulkdata+'&button=cancel';
loadURLBasedWindow(url, bulkSuccessMethod);
}


function bulkUploadHelp(){
	y=YAHOO.ebee.popup.dialog.cfg.getProperty("y")+30;
	x=YAHOO.ebee.popup.dialog.cfg.getProperty("x")+30;
	showPopupInPopup(bulkupload_helptitle, getHelpContent('bulkupload_helpcontent'),200,300,x,y);
}
function subscribeWidget(event,jsonobj){

	var url='maillistmanage!addWidget?userId='+jsonobj.userId+'&listId='+jsonobj.listId
		YAHOO.ebee.popup.wait.show();
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getWidgetSuccess,failure:getFailure});
	
}
function Widget(eid,trackcode,scode){
	var url="TrackURL!addWidget?eid="+eid+"&trackcode="+trackcode+"&scode="+scode;
	YAHOO.ebee.popup.wait.show();
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getWidgetSuccess,failure:getFailure});
	}
var getWidgetSuccess = function(responseObj){
	//showPopUpDialogWindow(responseObj, "Widget", handleCancel,handleCancel);
		var myButtons = [
	               { text: "Close", handler: handleCancel }
	             ];
	
	showPopUpDialogWithCustomButtons(responseObj,"Widget",myButtons);
	
}
 var getFailure = function(){
	alert("error");
}