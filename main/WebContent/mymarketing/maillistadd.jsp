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
        <script type="text/javascript" src="<%=resourceaddress%>/main/js/marketing/marketingmaillist.js"></script>
     </head>
<body>
<div class="container">
<div class="row">
<div class="col-xs-12">
<div id="summary" style="display:none"></div></div></div>
<s:form id="marketingmail" name="marketingmail" action="home!saveMailList" method="post" theme="simple">
<s:hidden name="listid" id="listid"></s:hidden>
<s:hidden name="userId"></s:hidden>
<s:hidden name="filterdomains" id="filterdomains"></s:hidden>
<div class="row">
<div class="form-group">
<div class="col-xs-4 col-sm-6" style="padding-top:5px;">
<label for="name" class="col-sm-2 control-label"><span class="pull-left">Name *</span></label>
</div>
<div class="col-xs-8 col-sm-2">
<s:textfield name="maildata.name"  size="60" id="listname" cssClass="form-control"></s:textfield>
</div></div></div>
<div class="row">
<div class="form-group">
<div class="col-xs-3 col-sm-5"></div></div></div>
<div class="row">
<div class="form-group">
<div class="col-xs-4 col-sm-6">
<label for="emails" class="col-sm-2 control-label"><span class="pull-left">Emails *</span></label><br/><span class="smallestfont">comma separated</span></div>
<div class="col-xs-4 col-sm-6">
<s:textarea rows="10" cols="90" name="bulkmails"  id="bulktextarealeft" cssClass="form-control"></s:textarea></div>
<div class="col-xs-4 col-sm-6">
<s:textarea rows="10" cols="90" name="bulkmailsright" id="bulktextarearight" cssClass="form-control"></s:textarea></div>
</div></div>
<s:hidden name="validcount" id="validcount"></s:hidden>
<s:hidden name="invalidcount" id="invalidcount"></s:hidden>
<s:hidden name="addedmailscount" id="addedmailscount"></s:hidden>
<s:hidden name="dupliactemailscount" id="dupliactemailscount"></s:hidden>
</s:form>
<hr>
<div class="form-group">
<div class="col-sm-offset-2 col-sm-6 pull-right">
<button type="submit" class="btn btn-primary"  id="sbmt">
Submit</button>
<button class="btn btn-active" id="cncl">
Cancel</button>
</div>
</div>
</div>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script>
var purpose='';
$(function(){
	$('#cncl').click(function(){
		parent.closepopup();
	});
	
	
	$('#sbmt').click(function(){
		$('#summary').hide();
		$('#bulktextarearight').html('');
		var status=getEmailsList();
		if(status){
		 $.ajax({
		  	   type: "POST",
		  	   url: 'home!saveMailList',
		  	   data: $("#marketingmail").serialize(),
		  	   success: function(response) {
		  		if(!parent.isValidActionMessage(response)) return; 
			    if(response.indexOf('is empty')>-1){
			    	$('#summary').html(response);
			    	parent.$('iframe#popup').css("height","500px");
			    	$('#summary').addClass('alert alert-danger');
			    	$('#summary').show();
			    	
			    }else{
			    	 var jsondata=eval('('+response+')');
				     if(!parent.isValidActionMessage(response)) return;
					 prepareSummary(jsondata,'summary');
					 $('#summary').removeClass('alert alert-danger');
					 $('#summary').show();
					 $('#bulktextarea').val(jsondata.invalidlines);
					}
			    },failure:function(){
		  		  alert("fail");
		  	   }});
		}
	});
	
	function closediv(){
		$('#myModal').modal('hide');
		window.location.reload();
		}
	
});




</script>
</body></html>