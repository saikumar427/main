
<%@page import="com.event.helpers.I18n"%>
<html>
<head>
<%@taglib uri="/struts-tags" prefix="s"%>
<!-- <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/grey.css" />
<script src="/main/bootstrap/js/icheck.min.js"></script>
</head>
<body>
<div class="container">
<div class="row"><div class="col-md-12"><div id="tktcontent"></div></div>
</div>
</div>
<div>
<br/><br/>
<div class="text-right">
<button id="tktsubmit" class="btn btn-primary"><%=I18n.getString("sea.sel.tkt.lbl")%></button>     
<button id="tktcancel" class="btn btn-active"><%=I18n.getString("global.cancel.btn.lbl")%></button>
 
</div>
 </div>

</body>

<script>
onload();
function onload(){
	$('#tktcontent').html(parent.$('#tktdetails').val());
	$('input[name="selticketid"]').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});
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