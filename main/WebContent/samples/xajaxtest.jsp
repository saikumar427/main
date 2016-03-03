<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="../js/prototype.js"	></script>
<script>
function validateEmail(){
//$("#paypalresult").load("/portal/utiltools/paypalxtest.jsp?email=abc");
//$("#paypalresult").load("/main/samples/xajaxtest.jsp");
}
function submitContent(){
var url='/portal/utiltools/paypalxtest.jsp';
	new Ajax.Request(url, {
		  method: 'post',
		  parameters: {email:document.getElementById("email").value},
		  onSuccess: function(transport) {
			var result=transport.responseText;
				alert(result);		
	}
	});
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div id="paypalresult"></div>
<input type="button" onclick="submitContent();" value="Test">
<input type="text" name="email" id="email">
</body>
</html>