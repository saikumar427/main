
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<link type="text/css" rel="stylesheet" href="/main/css/bootstrap/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="/main/font-awesome-4.0.3/css/font-awesome.min.css" />
</head>
<body>
 <div class="container">
<s:form name="mailsuccessform"  method="post" theme="simple" id="mailsuccess"> 
<div style="height:30px"></div>
<div class="row">
<div class="col-xs-4"></div>
<div class="col-xs-8">
<b>Mail sent successfully.</b>
</div>
</div>
<br/>
 <hr/>
 </s:form>
                  <div class="row">
                     
                        <div class="col-xs-12" align="center">
                            <button class="btn btn-primary" id="okbtn">
                                <i class="fa fa-check"></i> OK</button>
                        </div>
                    </div>

</div>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script>
parent.resizeIframe();

$(document).ready(function(){
	$('#okbtn').click(function(){
		 parent.closepopup();
		});
});



</script>

</body>
</html>
