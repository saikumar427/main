<%@page import="com.event.helpers.I18n"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String type = request.getParameter("type");
%>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<script src="/main/js/i18n/<%=I18n.getHyphenSessionLang()%>/properties.js" ></script>
<html>
<head>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/grey.css" />
<title>Photo Upload </title>
<STYLE type="text/css">
.smallestfont {
	font-family: Verdana, Arial, Helvetica, sans-serif; 
	font-size: 10px;
	font-weight: lighter; 
	color: #666666;
}
.errorMessage {
	background-color:#FFFFDE;
	color:red;
	background-position:20px 10px;
	background-repeat:no-repeat;
	border:1px solid #FFCF0F;
	margin: 5px;
	width:352px;
	min-height:20px;
	padding-top:10px;
	padding-left:28px;
	padding-bottom:10px;
}

    .btn-file {
    position: relative;
    overflow: hidden;
    }
    .btn-file input[type=file] {
    position: absolute;
    top: 0;
    right: 0;
    min-width: 100%;
    min-height: 100%;
    font-size: 999px;
    text-align: right;
    filter: alpha(opacity=0);
    opacity: 0;
    outline: none;
    background: white;
    cursor: inherit;
    display: block;
    }
    
    
    .btn-bt{
    	background-color: #F5F5F5;
    	border-color: #6c6c6c;
    	color: #333;
    	 padding: 6px 12px;
    	  border-radius: 4px;
    }
</STYLE>
<script>
$(document).ready(function(){
	var type = '<%=type%>';
	if('cover'==type){
		$('#message').html(props.pg_upload_cover_img);
	}else if('logo'==type){
		$('#message').html(props.pg_upload_logo_img);
	}else if('body'==type){
		$('#message').html(props.pg_upload_body_img);
	}else if('photoWidget'==type){
		$('.btn-cancel').hide();
	}
	
});

function closeThis(){
	parent.$('#myModalStyle').modal('hide');
}
</script>
</head>
<body>
<s:actionerror cssClass="alert alert-danger"/>
<s:fielderror />
<s:form action="ImageUpload!save" method="POST" enctype="multipart/form-data" theme="simple" id="imageupload" name="imageupload">
<s:hidden name="eid" ></s:hidden>
<s:hidden name="purpose"></s:hidden>

<div class="col-xs-12">
<div class="row">
<div class="col-xs-12 row">
	<div class="col-md-8 col-sm-8 col-xs-8"><s:file name="upload" label="File" size="40"  cssClass="btn-bt"/></div>
	<div class="col-md-4 col-sm-4 col-xs-4">
	<input type="submit" name="submit" value="<s:text name="global.upload.btn.lbl"></s:text>" class="btn btn-primary">
	<button type="button" onclick="closeThis();" class="btn btn-cancel"><i class="fa fa-times"></i></button>
	<%-- <input type="button" value="<s:text name="global.cancel.btn.lbl"></s:text>" onclick="closeThis();" class="btn btn-cancel"/> --%>
	</div>
</div>
</div><br>
<div class="row">
<div class="col-xs-12"><span class="smallestfont" id="message"></span>
</div>
</div>

</div>
</s:form>
</body>
</html>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="/main/bootstrap/js/icheck.min.js"></script>
<script src="/main/bootstrap/js/colorpicker.js"></script>
<script>
var type='${type}';

$('.demo2').colorpicker();
$('input.upload').iCheck({  
	checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey'});


$('.upload').on('ifChecked',function(){

	if($(this).attr('value')=='image'){
		$('#imagebtn').show();
		$('#colorbtn').hide();
		}else{
			$('#imagebtn').hide();
			$('#colorbtn').show();
			}
});


function uploadThis(){

	if($("#imageupload input[name='uploadtype']:checked").val()=='color'){
		parent.$('#'+type+'_COLOR').css("background-color",$('#colorval').val());
		parent.onchangeCP(type+'_COLOR', $('#colorval').val());
		parent.$('#myModal').modal('hide');
	}
}


</script>