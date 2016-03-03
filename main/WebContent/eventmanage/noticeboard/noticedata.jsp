<%@taglib uri="/struts-tags" prefix="s" %>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<div class="col-xs-12 col-sm-12">
<s:form name="noticedataform" id="noticedataform" action="NoticeBoard!saveNoticeData" theme="simple">
<s:hidden name="eid"/>
<s:hidden name="noticeData.noticeid"/>
<div class="row">
<div class="col-xs-12">
<div  id="noticedataformerrormsg" class="alert alert-danger" style="display:none"></div>
</div></div>
<div class="row">
<div class="col-xs-3">Type</div>
<div class="col-xs-8">
<s:set name="typeVal" value="noticeData.noticetype"/>
<select name="noticeData.noticetype" class="form-control" >
<s:iterator value="%{noticeTypeList}" var="type">
<s:set name="cval" value="key"/>
<option value="<s:property value="key"/>" <s:if test="%{#typeVal==#cval}">selected='selected'</s:if> >
<s:property value="value"/></option>
</s:iterator>
</select>
</div>
</div>
<br>
<div class="row">
<div class="col-xs-3">Notice&nbsp;*</div>
<div class="col-xs-8">
<s:textarea name="noticeData.notice" id="notice" rows="8" cols="30" theme="simple" cssClass="form-control"></s:textarea>
</div>
</div>
<br><br>
<div class="row">
<div class="col-xs-offset-4 pull-right">
<input type="submit" value="Submit" class="btn btn-primary" id="addnoticedata">&nbsp;
<input type="button" value="Cancel" class="btn btn-active" onclick="parent.closePopup();">
</div>
</div>
</s:form>
</div>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/tooltipster.css" />
  <script src="/main/js/jquery.tooltipster.js"></script>
<script src="/main/js/jquery.validate.min.js"></script>
  <style>
   /* tooltip custom theme */


/* tooltip custom theme */
.form-validate-theme {
	border-radius: 5px; 
	border: 2px solid #ff4747;
	background: #fff4f4;
	color: #000;
}
.form-validate-theme .tooltipster-content {
    /*font-family: Tahoma, sans-serif;*/
    width:100%;
	font-size: 14px;
	line-height: 16px;
	padding: 8px 10px;
}
   
   </style>
<script>
$('#addnoticedata').click(function(){
	// handleAddNoteSubmit();
});

function handleAddNoteSubmit(){
    var noticevalue=document.getElementById("notice").value;   
    if(noticevalue==""){       	
    document.getElementById("noticedataformerrormsg").style.display="block";    
    document.getElementById("noticedataformerrormsg").innerHTML="Notice should not be empty";
    parent.resizeIframe();
    } 
    else  {     
	submitFormAndReload('noticedataform', 'noticedataformerrormsg',true);	
}
}



$('#noticedataform input[type="text"], #noticedataform textarea, #noticedataform select').tooltipster({
    trigger: 'custom',
    onlyOne: false,
    fixedWidth:'300px',
    position: 'left',
    theme:'form-validate-theme'
});

$('#noticedataform').validate({
errorPlacement: function (error, element) {
                
    var lastError = $(element).data('lastError'),
        newError = $(error).text();
    
    $(element).data('lastError', newError);
                    
    if(newError !== '' && newError !== lastError){
        $(element).tooltipster('content', newError);
        $(element).tooltipster('show');
    }
},
success: function (label, element) {
    $(element).tooltipster('hide');
},
rules: {
	'noticeData.notice': {required: true}    
},
messages: {
	'noticeData.notice': 'Notice should not be empty'
},
 submitHandler: function (form) {
	 submitFormAndReload('noticedataform', 'noticedataformerrormsg',true);	
 }
 
 });








function submitFormAndReload(formId,errorDiv,reloadYN){
	var url = 'NoticeBoard!saveNoticeData';
	$.ajax({
		url : url,
		data : $('#noticedataform').serialize(),
		success : function(t){
			if(!isValidActionMessage(t)) return;
	        if(t.indexOf("success")>-1){	        	
	        	if(reloadYN)
	        		parent.window.location.reload( true );	
	        } 
	         else {
	        	
            	$('#'+errorDiv).html(result);            	
	        }
		}
	});
}

function isValidActionMessage(messageText){

	if(messageText.indexOf('nologin')>-1||messageText.indexOf('login!authenticate')>-1){
	        alert('You need to login to perform this action');
	        window.location.href="../user/login";
	        return false;
	    }
	 else if(messageText.indexOf('Something went wrong')>-1){
	         alert('Sorry! You do not have permission to perform this action');
	         YAHOO.ebee.popup.wait.hide();
	         return false;
	     }
	    return true;

	}
</script>