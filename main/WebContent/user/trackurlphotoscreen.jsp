<%@taglib uri="/struts-tags" prefix="s"%>
<!doctype html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <link type="text/css" rel="stylesheet" href="/main/css/bootstrap/bootstrap.min.css" />
        <link href="/main/font-awesome-4.0.3/css/font-awesome.min.css" rel="stylesheet">
     </head>
<body>
<div class="container">
<s:form name="trackmanagephotoform" id="trackmanagephotoform" action="TrackUrlManage!savePhotoUrl">
<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="trackcode" id="trackcode"></s:hidden>
<s:hidden name="secretcode" id="secretcode"></s:hidden>
<s:hidden name="userid"></s:hidden>
<div class="row">
<div class="form-group">
<span>Enter photo URL here, for best results make sure photo width is 200px</span><p/>
<div class="col-sm-4"><input type="text" name="photo" id="photo" value="<s:property value="photo"/>" size="69" class="form-control"/><p/></div></div></div>
<div class="form-group">
<div class="col-sm-6" style="line-height: 54px; text-align: center;">
<button type="submit" class="btn btn-primary" id="sbmt">Submit</button>
<button class="btn btn-primary" id="cncl">Cancel</button>
</div>
</div>
</s:form></div>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script>
$(function(){
	$('#cncl').click(function(){
    	parent.closediv();
    });
	
	$('#sbmt').click(function(){
		parent.submitform($("#trackmanagephotoform").serialize(),'TrackUrlManage!savePhotoUrl');
	});
});
</script>
</body></html>
