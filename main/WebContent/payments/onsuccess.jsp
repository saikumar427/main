<%@taglib uri="/struts-tags" prefix="s"%>
<%-- <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/css/bootstrap/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="/main/font-awesome-4.0.3/css/font-awesome.min.css" />
<script src="/main/js/bootstrap.min.js"></script> --%>
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
<script src="/main/bootstrap/js/bootstrap.min.js"></script>
<style>

.submitbtn-style {
    height:21px;
	margin: 5px;
    cursor: pointer;
	background: transparent url(/main/images/sprite.png);
	background-repeat:repeat-x;
	border:#808080 1px solid;
   	padding-left: 10px;
	padding-right: 10px;
	font: 12px/1.4 verdana,arial,helvetica,clean,sans-serif;
	valign: bottom;
}

.submitbtn-style a {
	text-decoration: none;
	color: #000;
}

.submitbtn-style:hover {
	border-color:#7D98B8;
	background-position:0 -1300px;
	text-decoration: none;
	color: #000;
}

</style>

<script>

var url='${successurl}';
/* new Ajax.Request(url, {
	method: 'post',
	    onSuccess:showMsg,
		onFailure:failureMsg
		}); */

		$.ajax({
			url : url,
			type:'POST',
			success:function(){
				showMsg();
			}
		});
		
function showMsg()
{
	if(url.indexOf("upgrade")!=-1)
	{
	    parent.onsucesscloseupgrade();
	}
	else{
	  parent.onsucessclose();
	}

}
function failureMsg()
{
alert("update failure");
}

</script>
<body>
<table align="center">

<tr><td >Card Submitted Sucessfully.</td></tr>
<tr>
<td ><center><input type='button' value='Close' class="btn btn-success" onclick='parent.onsucessclose();'></center></td>
</tr>
<table>

</body>
<script>


</script>