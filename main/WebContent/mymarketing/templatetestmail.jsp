<%@taglib uri="/struts-tags" prefix="s"%>
<%@include file="../getresourcespath.jsp"%>
<!doctype html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <link type="text/css" rel="stylesheet"
					href="<%=resourceaddress%>/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
				<link
					href="<%=resourceaddress%>/main/bootstrap/css/bootstrap.css"
					rel="stylesheet" />
					<link href="<%=resourceaddress%>/main/bootstrap/css/custom.css"
						rel="stylesheet">
     </head>
 <style>
  .smallestfont {
    font-family: Verdana, Arial, Helvetica, sans-serif;
    font-size: 10px;
    font-weight: lighter;
    color: #666666;
  }
 </style>    
<body>
<div class="container">
<div class="row">
<div class="col-xs-12">
<div id="errordisplay" class="alert alert-danger" style="display:none"></div></div></div>
<s:form id="testEmailForm" name="testEmailForm" action="home!sendTestEmail" method="post" theme="simple">
<s:hidden name="templateid" ></s:hidden>
<div class="row">
<div class="col-xs-12 col-sm-12">
Review your email content by sending test mail to yourself. This optional step helps you </br>to correct any mistakes in the email before sending out. 
</div></div>
<div class="row">
<div class="form-group">
<div class="col-xs-4 col-sm-5" style="padding-top:5px;">
<label for="emails" class="col-sm-4 control-label"><span class="pull-left">Emails</span><br><span class="smallestfont">comma separated</span></label>
</div>
<div class="form-group">
<div class="col-xs-8 col-sm-7" style="padding-top:5px;">
<textarea name='templateData.mailTo' class="form-control" size="70" ></textarea> 
</div></div></div>
</div>
</s:form>
<hr>
<div class="form-group">
<div class="col-sm-offset-2 col-sm-6 pull-right">
<button type="submit" class="btn btn-primary"  id="sbmt"> Submit</button>
<button class="btn btn-active" id="cncl"> Cancel</button>
</div>
</div></div>
</body></html>					
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script>
$(function(){
	$('#cncl').click(function(){
    	parent.closediv();
    });
	
	$('#sbmt').click(function(){
		$('#errordisplay').hide();
		$.ajax({
		  	   type: "POST",
		  	   url: 'home!sendTestEmail',
		  	   data: $("#testEmailForm").serialize(),
		  	   success: function(response) {
		  		if(!parent.isValidActionMessage(response)) return; 
			    if(response.indexOf('errorMessage')>-1){
			    	parent.$('iframe#popup').css("height","300px");
			    	$('#errordisplay').html(response);
			    	$('#errordisplay').show();
			    	
			    }else{
			    $('#myModal').hide();
			    parent.window.location.reload();	 
			    }
		  	   },failure:function(){
		  		  alert("fail");
		  	   }});
	});
});	
</script>

