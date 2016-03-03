<%@taglib uri="/struts-tags" prefix="s" %>
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
						<link type="text/css" rel="stylesheet"
							href="<%=resourceaddress%>/main/bootstrap/css/grey.css" />
     </head>
<body>
<div class="container">
<div class="row">
<div class="col-xs-11">
<div id="templateErrorMsg" class="alert alert-danger" style="display:none"></div></div></div>
<form action="home!saveTemplate" name="templateForm" id="templateForm" method="post">
<input type="hidden" name="userId" value="${userId}"/>
<input type="hidden" name="templateData.templateId" value="<s:property value='%{templateData.templateId}'/>"/>
<div class="row">
<div class="form-group">
<div class="col-xs-2 col-sm-4" style="padding-top:5px;">
<label for="tname" class="col-sm-2 control-label"><span class="pull-left">Template Name *</span></label>
</div>
<div class="col-xs-10 col-sm-4">
<s:textfield name="templateData.name" theme="simple" size="20" id="tempname" cssClass="form-control"></s:textfield>
</div></div></div>
<div class="row">
<div class="form-group">
<div class="col-xs-2 col-sm-4" style="padding-top:5px;">
<label for="tsubject" class="col-sm-2 control-label"><span class="pull-left">Subject&nbsp;*</span></label>
</div>
<div class="col-xs-10 col-sm-4">
<s:textfield name="templateData.subject" theme="simple" size="50" id="subject" cssClass="form-control"></s:textfield><br>
</div></div></div>
<div class="row">
<div class="form-group">
<div class="col-xs-2 col-sm-4" style="padding-top:5px;">
<label for="tfrom" class="col-sm-2 control-label"><span class="pull-left">From&nbsp;*</span></label>
</div>
<div class="col-xs-10 col-sm-4">
<s:textfield name="templateData.from" theme="simple" size="50" id="frommail" cssClass="form-control"></s:textfield><br>
</div></div></div>
<div class="row">
<div class="form-group">
<div class="col-xs-2 col-sm-4 " style="padding-top:200px;">
<label for="tcontent" class="col-sm-2 control-label"><span class="pull-left">Content&nbsp;*</span></label>
</div>
<div class="col-xs-10 col-sm-4">
<s:textarea rows="20" cols="90" name="templateData.content" theme="simple" cssClass="form-control"></s:textarea><br>
</div></div></div>
<div class="row">
<div class="form-group">
<div class="col-xs-2 col-sm-4" style="padding-top:5px;">
<label for="tnotes" class="col-sm-2 control-label"><span class="pull-left">Notes</span></label>
</div>
<div class="col-xs-10 col-sm-4">
<s:textarea rows="2" cols="90" name="templateData.notes" theme="simple" cssClass="form-control"></s:textarea><br>
</div></div></div>
<div class="row">
<div class="form-group">
<div class="col-xs-2 col-sm-4" style="padding-top:5px;">
<label for="rfcontent" class="col-sm-2 control-label"><span class="pull-left">Require Footer Content</span></label>
</div>
<div class="col-xs-10 col-sm-4">
<s:set name="footercontentshow" value="templateData.footercontent"></s:set>
<input type="radio" name="templateData.footercontent" id="footercontentyes" value="yes" class="tempfooter" <s:if test="%{#footercontentshow == 'yes'}">checked="checked"</s:if>>&nbsp;Yes&nbsp;
<input type="radio" name="templateData.footercontent"  id="footercontentno" value="no" class="tempfooter" <s:if test="%{#footercontentshow == 'no'}">checked="checked"</s:if>>&nbsp;No
</div></div></div>
</form>
<hr>
<div class="form-group">
<div class="col-sm-offset-2 col-sm-6 pull-right">
<button type="submit" class="btn btn-primary"  id="sbmt">Submit</button>
<button class="btn btn-active" id="cncl"> Cancel</button>
</div>

</div>
</div>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

  	<script src="<%=resourceaddress%>/main/bootstrap/js/icheck.min.js"></script>
<script>
$(function(){
	$('#cncl').click(function(){
    	parent.closediv();
    });
	
	$('input.tempfooter').iCheck({  
        checkboxClass: 'icheckbox_square-grey', 
        radioClass: 'iradio_square-grey'});
	
	$('#sbmt').click(function(){
		$('#templateErrorMsg').hide();
		$.ajax({
		  	   type: "POST",
		  	   url: 'home!saveTemplate',
		  	   data: $("#templateForm").serialize(),
		  	   success: function(response) {
		  		if(!parent.isValidActionMessage(response)) return; 
			    if(response.indexOf('errorMessage')>-1){
			    	parent.$('iframe#popup').css("height","1000px");
			    	$('#templateErrorMsg').html(response);
			    	$('#templateErrorMsg').show();
			    	
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
</body></html>

