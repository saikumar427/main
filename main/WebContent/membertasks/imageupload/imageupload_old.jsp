<%@ taglib prefix="s" uri="/struts-tags" %>

<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
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
    	background-color: #d8d8d8;
    	border-color: #6c6c6c;
    	color: #fff;
    	 padding: 6px 12px;
    	  border-radius: 4px;
    }
</STYLE>
<script>
function closeThis(){
	parent.$('#myModal').modal('hide');
	parent.closeFileUploadWindow();
}
</script>
</head>
<body>
<s:actionerror cssClass="alert alert-danger"/>
<s:fielderror />
<s:form action="ImageUpload!save" method="POST" enctype="multipart/form-data" theme="simple" id="imageupload" name="imageupload">
<s:hidden name="eid" ></s:hidden>
<s:hidden name="purpose"></s:hidden>

<s:if test="%{purpose!='looknfeel'}">
<div class="col-xs-12">
<div class="row">
<div class="col-xs-12">
   <s:file name="upload" label="File" size="40"  cssClass="btn-bt"/> </div>
</div>
<div class="row">
<div class="col-xs-12"><span class="smallestfont">
<s:text name="epc.upload.help.message"></s:text>
</span>
</div>
</div>
<br>
<div class="row">
	<div class="col-xs-offset-3 col-xs-8">
		<div class="pull-right">
			<input type="submit" name="submit" value="<s:text name="global.submit.btn.lbl"></s:text>" class="btn btn-primary">&nbsp;
			<input type="button" value="<s:text name="global.cancel.btn.lbl"></s:text>" onclick="closeThis();" class="btn btn-active"/>
		</div>
	</div>
</div>
<br>
</div>
</s:if>
<s:else>

<div>
<div class="col-xs-10 col-sm-10">
<input type="radio" name="uploadtype" value="image" class="upload" checked="checked"> Upload Image
 <s:file name="upload" label="File" size="40"  cssClass="btn btn-cancel"/> </div>
</div>

<div style="clear:both;"></div><br/>
<div>
<div class="col-xs-10 col-sm-10">

<s:if test="%{type=='FOOTER' || type=='HEADER' }">

<input type="radio" name="uploadtype" value="html" class="upload">&nbsp;HTML

<s:textarea cols="5" rows="4" cssClass="form-control"></s:textarea>
<br/>
</s:if>
<s:else>

<input type="radio" name="uploadtype" value="color" class="upload">&nbsp;Color
<div class="extramargin" id="ShowLargeColor" style="display:block;width:50%;">
										<div class="input-group demo2">
											<!-- <input type="text" class="form-control" placeholder="Color"> -->
											 <s:textfield cssClass="form-control"  size='8' id="colorval"></s:textfield>
											<span class="input-group-addon">
												<i></i>
											</span>
										</div>
									</div>
									</s:else>

</div>
</div>


	<div class="col-xs-offset-3 col-xs-8">
		<div class="pull-right">
		    <span id="imagebtn"><input type="submit" name="submit" value="Upload" class="btn btn-primary">&nbsp;</span>
			<span id="colorbtn" style="display:none;"><input type="button" name="submit" value="Upload" class="btn btn-primary" onclick="uploadThis();">&nbsp;</span>
			<input type="button" value="Cancel" onclick="closeThis();" class="btn btn-active"/>
		</div>
</div>

</s:else>
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


function closeThis(){
	parent.$('#myModal').modal('hide');
}

function uploadThis(){

	if($("#imageupload input[name='uploadtype']:checked").val()=='color'){
		parent.$('#'+type+'_COLOR').css("background-color",$('#colorval').val());
		parent.onchangeCP(type+'_COLOR', $('#colorval').val());
		parent.$('#myModal').modal('hide');
	}
}


</script>