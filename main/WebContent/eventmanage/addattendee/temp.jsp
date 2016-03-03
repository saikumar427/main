
<html>
<head>

<title>Insert title here</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/css/bootstrap/bootstrap.min.css" />
</head>
<body>
<div id="tktcontent"></div>
<div>
<br/><br/>
<div  align="center">
<button id="tktsubmit" class="btn btn-primary">Select tickets</button>
<button id="tktcancel" class="btn btn-primary">Cancel</button>
</div>
 </div>

</body>

<script>
onload();
function onload(){
	$('#tktcontent').html(parent.$('#tktdetails').val());
}

$('#tktsubmit').click(function(){
	 var val = $('input:radio[name=selticketid]:checked').val();
	 parent.getSelectedTicket(val);
});

$('#tktcancel').click(function(){
	 parent.$('#myModal').modal('hide');
});


</script>

</html>