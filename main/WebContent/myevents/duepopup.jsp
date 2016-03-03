<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" /> 
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script type="text/javascript" src="/main/js/eventmanage/specialfee.js"></script>
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
</head>
<body>
<s:form name="dueform" id="dueform" action="" method="post">
<div class="container">
<div class="row">
<div class="col-md-12 col-xs-12 col-sm-12">
Your service fee dues exceed $25, enter your credit card information or
buy Bee Credits to continue using our service. 
</div>

</div>
</div>
</br>
</s:form>
<div class="container">
<div class="row" align="center">

<button class="btn btn-primary" id="proceed">Proceed</button>

</div></div>
</body>
</html>
<script>
$(document).ready(function(){
	$('#proceed').click(function(e){
		parent.window.location.href='/main/myaccount/home';
		});
});
</script>


