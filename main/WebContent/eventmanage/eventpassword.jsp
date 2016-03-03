<%@taglib uri="/struts-tags" prefix="s"%>
<link type="text/css" rel="stylesheet" href="/main/css/bootstrap/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="/main/font-awesome-4.0.3/css/font-awesome.min.css" />



<div class="col-md-12">
<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="powertype" id="powertype"></s:hidden>
<div class="row">
<div class="col-md-8">
Note: Leave blank to remove password protection
</div>
</div>
<br>
<div class="row">
<div class="col-xs-2">Password:</div>
<div class="col-xs-10">
<s:textfield name="" id="password" theme="simple" size="27" cssClass="form-control"></s:textfield>
</div>
</div>
<br><br>
<div class="row">
<div class="col-xs-offset-4">
<input type="button" value="Submit" class="btn btn-primary" onclick="submitFunc();">&nbsp;<input type="button" value="Cancel" class="btn btn-primary" onclick="parent.closePopup();">
</div>
</div>
</div>

<script>
function submitFunc(){
	var password=document.getElementById('password').value;
	var powertype=document.getElementById('powertype').value;
	var eid=document.getElementById('eid').value;
	insertpwd(password,powertype,eid);
}


function insertpwd(password,powertype,eid){	
	var url="EventPageContent!insertEventPassword?eid="+eid;
	var params={password:password,powertype:powertype};
	parent.submitContentFormAndReload(url, params);	
}
</script>