<%-- <%@taglib uri="/struts-tags" prefix="s"%>

<div id="errordisplay"></div>
<s:form id="sendmailpopupform" name="sendmailpopupform" action="EmailAttendees!sendTest" method="post" theme="simple">
<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="blastid" id="blastid"></s:hidden>
<div style="height:3px"></div>
	<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
	<table border="0" cellpadding="3" cellspacing="0" width="100%">
		<tr>
			<td colspan='2'></td>
		</tr>
		<tr>
	<td colspan="2" >Review your email content by sending test mail to yourself. This optional step helps you <br>to correct any mistakes in the email before sending out to attendees. </td>
		</tr>
		<tr>
        <td colspan="2" align="center" valign="top">
			<table align="left">
		<tr>
		<td >Emails&nbsp;&nbsp;&nbsp;&nbsp; <input type='text' name='emailAttendeesData.mailtotest' id="mailtotest" size="70" /></td>
		<td><span class="smallestfont" style="align:left">(comma separated)</span></td>
		</tr>
</table>
</td>
</tr></table>
</s:form>	
					
 --%>
 
 
 <%@taglib uri="/struts-tags" prefix="s"%>
<!doctype html>
<html>
    <head>
        <title>Bootstrap</title>
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
     <style>
            .btn-default{background-color: #D1D0CE}
			 
     </style>
    </head>
    <body>
    
    
    
    
    
    <div class="container">
    <div class="row"> 
    <div id="errormsg" class="alert alert-danger" style="display:none;width:95%;margin-left:4px;" ></div>
        
        <s:form id="sendmailpopupform" name="sendmailpopupform" action="EmailAttendees!sendTest" method="post" theme="simple">
<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="blastid" id="blastid"></s:hidden>
<div class="col-md-12">
		<s:text name="ea.test.mail.msg"/>
</div>
<br/>
 	<div class="form-group">
  			<label for="inputPassword3" class="col-xs-2">  <s:text name="ea.test.mail.mails"/>  </label>
    		<div class="col-xs-10">
      			<input type='text' class="form-control" name='emailAttendeesData.mailtotest' id="mailtotest" size="70" />
    		</div>
  	</div>
</s:form>
<br/><br/>

					 <div class="form-group">
						<div class="col-xs-5"></div>
						
                            <div class="center">
                                 <button id="testmailsubmit" class="btn btn-primary"><s:text name="global.continue.btn.lbl"/></button>
                                        <button onclick="parent.closePopup();" class="btn btn-cancel">   <i class="fa fa-times"></i>  </button>
                           </div>
                        
                     </div> 
</div>
</div>
    </body>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
     <%-- <script src="/main/js/bootstrap.min.js"></script>
	<script src="/main/js/jquery.tooltipster.js"></script>
	<script src="/main/js/jquery.validate.min.js"></script>
	<script src="/main/js/jquery.ezpz_hint.js"></script>  --%>
	<script>
	
	$('#testmailclose').click(function(){
		
		parent.hidePopup();
	});
	
	
	$('#testmailsubmit').click(function(){
	
	
	 $('#testmailsubmit').attr('disabled','disabled');
	 $.ajax( {
   	   type: "GET",
   	   url: 'EmailAttendees!sendTest',
   	   data: $("#sendmailpopupform").serialize(),
   	   success: function( result ) {
   		   
   	   	   if(result.indexOf('success')>-1){
   	   		 if(!parent.isValidActionMessage(result)) return;
   	   	parent.$('#myModal').modal('hide');
   	   	   	   /* parent.window.location.reload(); */
   	   	   }else{
   	   	 $('#testmailsubmit').removeAttr('disabled');
   			$('.alert').show();
       	   $('.alert').html(result);
       	parent.window.resizeIframe();
   	   	   }
   	   }});
});
	</script>
</html>
 
 
 