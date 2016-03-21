<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.event.helpers.I18n"%>
<html>
<head>
<title></title>
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" /> 
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/grey.css" />
<script src="/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js" ></script>
<script>
function closeThis(){
	parent.closeFileUploadWindow();
}
$(document).ready(function(){
	var errorData = $('.alert-danger').html();
	if(errorData.indexOf('Request exceeded allowed size limit!')>-1)
		$('.alert-danger').html('<ul><li>'+props.pg_image_too_large_error+'</li></ul>');
});
</script>
<style>
.smallestfont {
    color: #666666;
    font-family: Verdana,Arial,Helvetica,sans-serif;
    font-size: 10px;
    font-weight: lighter;
}
</style>
</head>
<body>
<s:actionerror cssClass="alert alert-danger"/>
<s:fielderror />
<!--  <s:fielderror theme="simple"/>-->
 <form  action="FileUpload!saveFile" method="POST" enctype="multipart/form-data" id="fileUploadForm" name="fileUploadForm" class="form-horizontal" >
<s:hidden name="eid" ></s:hidden>
<s:hidden name="purpose" ></s:hidden>
<div class="col-xs-12">
	<div class="row">
		<div class="col-xs-12 row">
			<div class="col-md-8 col-sm-8 col-xs-8">
			<s:file name="upload" style="background-color: #f5f5f5;border-color: #6c6c6c;border-radius: 4px;color: #333;padding: 6px 12px;" label="File" id="upload" /></div>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<button type="submit" class="btn btn-primary"><s:text name="global.upload.btn.lbl" /></button>
    			<button class="btn btn-cancel" onclick="parent.closepopup();"> <i class="fa fa-times"></i></button>
			</div>
		</div>
	</div>
	<br>
	<div class="row">
		<div class="col-xs-12"><span id="message" class="smallestfont"><s:text name="buyer.file.upload.msg" /></span></div>
	</div>
</div>
</form> 

</body>
</html>