var previewFileName="";

function closebtn(){
	document.getElementById('customurlblock').style.display="none";
	if(document.getElementById("errormsg"))
	document.getElementById("errormsg").innerHTML="";
}
function setCustomURL(eid){
	var userid=document.getElementById('mgrid').value;
	var userurl=document.getElementById('customURL').value;	
	var newurl=document.getElementById('shorturl').value;
	var eid=document.getElementById('gid').value;
	var url="ManageGroup!setCustomURL?gid="+eid+"&mgrId="+userid+"&userurl="+userurl+"&customURL="+newurl;
	new Ajax.Request(url, {
		  method: 'get',
		  onSuccess: function(transport) {
			var result=transport.responseText;
			if(!isValidActionMessage(result)){
    			return;
    		}
			else if(result.indexOf("URL Exists")>-1){
				document.getElementById("customurlerrormessages").style.display="block";
				document.getElementById("customurlerrormessages").innerHTML='This URL is not available, please enter new URL';
			}
			else if(result.indexOf("Invalid")>-1){				
				document.getElementById("customurlerrormessages").style.display="block";
				document.getElementById("customurlerrormessages").innerHTML='Enter Valid Text';
			}
			else if(result.indexOf("spacesInUrl")>-1){
				document.getElementById("customurlerrormessages").style.display="block";
				document.getElementById("customurlerrormessages").innerHTML='Use alphanumeric characters only';
			}else{
				document.getElementById('customurlblock').style.display="none";
				YAHOO.ebee.popup.dialog.cancel();
				window.location.reload(true);			
		}
			
	      }
	});	
	
}
function previewMethod(event,jsonObj){
	var url=jsonObj.url;
	showYUIinIframe("Preview",url,600,500);

}
function getContentPreview(){	
	
	$('GroupTheme').action='ManageGroup!GroupThemePreview';
	$('GroupTheme').request({
         onSuccess: function(t) {
	        var previewFileName=t.responseText;		
	        var url='../membertasks/FileRead?readFileName='+''+previewFileName+'';
	        showYUIinIframe("Streamer Preview",url,600,500);
    }
    });
	$('GroupTheme').action='ManageGroup!saveGroupTheme';
}
