function submitAddNotice(){
	var url='NoticeBoard!addNotice?eid='+currenteid;
	loadURLBasedWindow(url, handleAddNoteSuccess);
}
var handleAddNoteSuccess = function (responseObj){
	showPopUpDialogWindow(responseObj, "Notice Details", handleAddNoteSubmit, handleCancel);	
}
function handleAddNoteSubmit(){
    var noticevalue=document.getElementById("notice").value;   
    if(noticevalue==""){   
    document.getElementById("noticedataformerrormsg").style.display="block";
    document.getElementById("noticedataformerrormsg").innerHTML="<li>Notice should not be empty</li>";
    } 
    else  { 
    YAHOO.displayinfo.wait.setHeader("Updating...");
	YAHOO.displayinfo.wait.show();	
	submitFormAndReload('noticedataform', 'noticedataformerrormsg');	
}
}
function submiteditNotice(noticeId){
	YAHOO.ebee.popup.wait.show();
	var url='NoticeBoard!editNoticeData?eid='+currenteid+'&noticeId='+noticeId;
	loadURLBasedWindow(url, handleAddNoteSuccess);
}
function deleteNotice(noticeId){ 
	var agree;
    agree=confirm("Do you really want to delete?");     
    if(agree){ 
    YAHOO.displayinfo.wait.setHeader("Deleting...");
	YAHOO.displayinfo.wait.show();	
	var url='NoticeBoard!deleteNotice?eid='+currenteid+'&noticeId='+noticeId;	
	callURLandReload(url);
}
}