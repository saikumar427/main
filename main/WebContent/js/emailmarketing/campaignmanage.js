function submitEmailBlastForm(){ 
	document.getElementById('emailerror').innerHTML="";
	var mailids=document.getElementById('mailtotest').value;
		var tokens = mailids.tokenize(",", " ", true);	
		for(var i=0; i<tokens.length; i++){
			if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(tokens[i])){
				setErrorMsg(tokens[i] + ' is not a valid email address');
				//document.getElementById('emailerror').innerHTML=tokens[i] + ' is not a valid email address.';
			return;
			}
		}
			if(mailids.length<=0){		
				$("emailerror").show();
				$("result").hide();
				document.getElementById('emailerror').innerHTML="Email ID is empty";
				return;
			} else{
			
			    document.getElementById('sendmail').value="Sending...";
				
				document.emailblastform.action='campaignlistmanage!emailblastTestMail';	
				
				$('emailblastform').request({
			            onSuccess: function(obj) {
			        	var restxt=obj.responseText;
			        	if(restxt.indexOf("Success")>-1){
						//document.emailblastform.sendmail.value="Send";
						var testMailstatusMsg="<div id='statusMessageBox' class='statusMessageBox'><div style='float: left;valign: middle;'><img src='../images/check.png'> Test mail sent successfully, review it before scheduling the blast, <a href='../myemailmarketing/editcampaign!editCampaign?campId="+currentCampId+"'>click here</a> to edit if there are any mistakes</div><div><a class='closebtn' href='javascript:hidestatusmessage()'></a></div></div>";
						document.getElementById("result").innerHTML=testMailstatusMsg;
						$("result").show();
						$("emailerror").hide();
						}else{}
			        },
			        onFailure : function(obj) { 
						alert("Error: " + obj.status);
						}
			    });
		}
}
function finalSubmit(){
	document.getElementById('fromerror').innerHTML="";  
	document.getElementById('toerror').innerHTML="";
	document.getElementById('subjecterror').innerHTML="";
	var subject=document.getElementById('subject').value;
	var from=document.getElementById('fromAddress').value;	
	var reply=document.getElementById('replytoAddress').value;	
	var temp=0;
	if(subject==""){
		document.getElementById('subjecterror').innerHTML="Subject is empty.";
		temp++;
	}
	if(reply==""){
		document.getElementById('toerror').innerHTML="Reply To is empty.";
		temp++;
	}
	if(from==""){
		document.getElementById('fromerror').innerHTML="From is empty.";
		temp++;
	}
	if(from!=""){
	var mailids=document.getElementById('fromAddress').value;
	var tokens = mailids.tokenize(",", " ", true);	
	for(var i=0; i<tokens.length; i++){
		if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(tokens[i])){
			document.getElementById('fromerror').innerHTML=tokens[i] + ' is not a valid email address.';
			temp++;
		}
	}
	}
	if(reply!=""){
		var mails=document.getElementById('replytoAddress').value;
		var token = mails.tokenize(",", " ", true);	
		for(var j=0;j<token.length;j++){
			if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(token[j])){
				document.getElementById('toerror').innerHTML=token[j] + ' is not a valid email address.';
				temp++;
			}
		}
	}
if(temp==0){
	return true;
}
else{
	document.getElementById("errorblock").style.display  = 'block'; 
	return false;
}
}
function editCampaign(campId){
var schCount=YAHOO.emailblastsdata.Data.SCHEDULED.length;
	if(schCount>1)
	alert("There are "+schCount + "blasts scheduled on this campaign content. Changes will not be applied to already scheduled blasts");
	else if (schCount==1)
	alert("There is one blast scheduled on this campaign content. Changes will not be applied to already scheduled blasts");
	window.location.href='editcampaign!editCampaign?campId='+campId;
}
function editEmailBlast(emailCampId){
var action='edit';
	window.location.href="../myemailmarketing/campaignlistmanage!editEmailBlast?campId="+currentCampId+"&emailCampId="+emailCampId+"&action="+action;
}
function blastPreview(emailCampId){
	//window.location.href="../myemailmarketing/campaignlistmanage!previewEmailBlast?campId="+currentCampId+"&emailCampId="+emailCampId;
	var url="../myemailmarketing/campaignlistmanage!previewEmailBlast?campId="+currentCampId+"&emailCampId="+emailCampId;
	showYUIinIframe("Preview",url,600,500);
}
function blastReport(emailCampId){
	var url="../myemailmarketing/campaignlistmanage!getBlastReports?campId="+currentCampId+"&emailCampId="+emailCampId;
	loadURLBasedWindow(url, successMethod);	
}
var successMethod = function(o){
	var myButtons = [
	                 { text: "Cancel", handler: handleCancel }
	             ];
	showPopUpDialogWithCustomButtons(o, "Email Blast Report", myButtons);
	
}
var failure = function(){
	alert("There is Error in processing the report.Please try after some time.");
}

function reloadeditors(){
	var selectedtype='';
		options=document.forms['createcampaign'].elements['campaignData.campDescType'];
	for(i=0;i<options.length;i++){
	opt=options[i];
	if(opt.checked) selectedtype=opt.value;
	}
	if(selectedtype=='html'){
		
		document.getElementById('fckdesccontent').style.display = 'none';
		document.getElementById('fckdesctxtcontent').style.display = 'block';
	    document.getElementById('contentDetails').style.display='none';
	}else{
		    document.getElementById('fckdesccontent').style.display='block';
			document.getElementById('contentDetails').style.display='block';
            document.getElementById('fckdesctxtcontent').style.display='none';
	}
	
}
function setRoundCorners(){
	if(!NiftyCheck())
	    return;
	Rounded("div#helpcontent");

	}
function hidestatusmessage(){
 $('statusMessageBox').hide();
}
function deleteCampaign(event, jsonobj){
	YAHOO.ebee.popup.wait.setHeader("Deleting...");
	YAHOO.ebee.popup.wait.show();
	var jsondata=eval(jsonobj);
	var count=jsondata.count;
	var agree=true;
	if(count>0)
		agree=confirm("This campaign has scheduled blast(s). Do you want to continue?");
	else
	agree=confirm("This operation cannot revert back. Do you want to continue?");
	var url="campaignlistmanage!deleteCampaign?campId="+jsondata.campId; 
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
function deleteEmailBlast(emailCampId){
	var campid=document.getElementById('campId').value;
	YAHOO.ebee.popup.wait.setHeader("Deleting...");
	YAHOO.ebee.popup.wait.show();
	agree=confirm("This operation cannot revert back. Do you want to continue?");
	if(agree){
		url="campaignlistmanage!deleteEmailBlast?emailCampId="+emailCampId;
		new Ajax.Request(url, {
			  method: 'get',
			  onSuccess: function(transport) {
				var result=transport.responseText;
				YAHOO.ebee.popup.wait.hide();
				if(!isValidActionMessage(transport.responseText)){
    				return;
    			}	
    			//window.location.reload();
    			window.location.href="../myemailmarketing/campaignlistmanage!campaignInfo?campId="+campid;		
		      }
		});
	}
	else{
		YAHOO.ebee.popup.wait.hide();
		return false;
	}
}