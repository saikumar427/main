<%@ taglib prefix="s" uri="/struts-tags" %>

<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap-colorpicker.min.css" />
<link type="text/css" rel="stylesheet" href="/mainboot/bootstrap/css/custom.css">
<html>
<head>
<link type="text/css" rel="stylesheet"
							href="/mainboot/bootstrap/css/grey.css" />
<style>
.marginclass{
margin-top:8px;
}


</style>


							</head>
							<body>

<div>
<div class="col-xs-10 col-sm-10">
<div class="col-xs-3 marginclass">Color</div>
<div class="col-xs-7" id="ShowLargeColor" style="display:block;width:50%;">
										<div class="input-group demo2">
											<!-- <input type="text" class="form-control" placeholder="Color"> -->
											 <s:textfield cssClass="form-control"  size='8' id="colorval"></s:textfield>
											<span class="input-group-addon">
												<i></i>
											</span>
										</div>
									</div>
									

</div>
</div>
<div style="clear:both;"></div><br/>

<div>
<div class="col-xs-10 col-sm-10">
<div class="col-xs-3 marginclass"> 
Font Type</div>
 <div class="col-xs-7">
 <select style="width:230px" class="form-control" id="fonttype">
				<option value="Lucida Grande,Lucida Sans Unicode,sans-serif">Lucida Grande,Lucida Sans Unicode,sans-serif</option>
				<s:iterator value="fontTypes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>"
						<s:if test="%{#bodyfonttype==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select>
 </div>
 
 </div>
</div>


<div style="clear:both;"></div><br/>

<div>
<div class="col-xs-10 col-sm-10">
<div class="col-xs-3 marginclass"> Font Size</div>
<div class="col-xs-7">
<select style="width:100px" class="form-control" id="fontsize">
				<s:iterator value="fontSizes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>">${value}</option>
				</s:iterator>
			</select>
					</div>				

</div>
</div>

<div class="col-xs-offset-3 col-xs-8">

		<div class="pull-right">
<br/>
		    <span><input type="button" name="submit" value="Upload" class="btn btn-primary" id="submitbtn">&nbsp;</span>
			<input type="button" value="Cancel" onclick="closeThis();" class="btn btn-active"/>
		</div>
</div>
							
							
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

$('#submitbtn').click(function(){
	parent.$('#'+type+'COLOR').css("background-color",$('#colorval').val());
	parent.onchangeCP(type+'COLOR', $('#colorval').val());
	parent.onchangeFonts(type+'FONTYPE',$('#fonttype').val());
	parent.onchangeFonts(type+'FONTSIZE',$('#fontsize').val()); 
	parent.closepopup();	
});


function closeThis(){
parent.closepopup();	
}

</script>

