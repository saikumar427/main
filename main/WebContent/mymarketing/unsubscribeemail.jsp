<%@taglib uri="/struts-tags" prefix="s" %>
<!doctype html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <link type="text/css" rel="stylesheet" href="/main/css/bootstrap/bootstrap.min.css" />
        <link href="/main/font-awesome-4.0.3/css/font-awesome.min.css" rel="stylesheet">
        <script type="text/javascript" src="/main/js/marketing/marketingmaillist.js"></script>
     </head>
<body>
<div class="container">
<div class="row">
<div class="col-xs-12">
<div id="unsubscribeErrorMsg" class="alert alert-danger" style="display:none;"></div></div></div>
<form action="home!saveUnsubscribeEmail" name="unsubscribeEmailForm" id="unsubscribeEmailForm" method="post">
<s:hidden name="userId" ></s:hidden>
<div class="row">
<div class="form-group">
<div class="col-xs-6 col-sm-8" style="padding-top:5px;">
<label for="lcount" class="col-sm-2 control-label"><span class="pull-left">Unsubscribed List Count</span></label>
</div>
<div class="col-xs-5 col-sm-1">
<label for="lcount" class="col-sm-2 control-label">(<s:property value="%{unsubscribeCount}"/>)</label>
</div></div></div>
<div class="row">
<div class="form-group">
<div class="col-xs-5 col-sm-7" style="padding-top:100px;">
<label for="anmail" class="col-sm-2 control-label"><span class="pull-left">Add New Email&nbsp;*</span></label></div>
<div class="col-xs-6 col-sm-8">
<s:textarea rows="10" cols="50" name="email"  id="email" cssClass="form-control"></s:textarea></div>
</div></div>
</form>
<hr>
<div class="form-group">
<div class="col-sm-offset-2 col-sm-6 pull-right">
<button type="submit" class="btn btn-primary"  id="sbmt">
<i class="fa fa-pencil-square-o"></i> Submit</button>
<button class="btn btn-danger" id="cncl">
<i class="fa fa-times"></i> Cancel</button>
</div>
</div>
</div>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script>
$(function(){
	$('#cncl').click(function(){
    	parent.closediv();
    });
	
	
	$('#sbmt').click(function(){
		$('#unsubscribeErrorMsg').hide();
		var status=getUnsubscribeEmailsList();
	    if(status){
		$.ajax({
		  	   type: "POST",
		  	   url: 'home!saveUnsubscribeEmail',
		  	   data: $("#unsubscribeEmailForm").serialize(),
		  	   success: function(response) {
		  		if(!parent.isValidActionMessage(response)) return;
		  	   if(response.indexOf('errorMessage')>-1){
			    	parent.$('iframe#popup').css("height","500px");
			    	$('#unsubscribeErrorMsg').html(response);
			    	$('#unsubscribeErrorMsg').show();
			    	
			    }else{
			    $('#myModal').hide();
			    parent.window.location.reload();
			    }
		  	   },failure:function(){
		  		  alert("fail");
		  	   }});
		}
});

});


function getUnsubscribeEmailsList(){
	 var unsubscribelist=$('#email').val();
     unsubscribelist=unsubscribelist.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	 unsubscribelist=unsubscribelist.trim();
	 var totalmaillist=processingUnsubscribeMailList(unsubscribelist);
	 var maillist=totalmaillist.split(",");
	 var nonduplicatelist=removeunsubscribeduplicates(maillist);
	 var finalunsubscribelist='';
	 var emailExp = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-Z0-9]{2,4}$/;
	 for(var i=0;i<nonduplicatelist.length;i++){
		 
	  if(nonduplicatelist[i].match(emailExp)){
		 if(finalunsubscribelist!='')
	       finalunsubscribelist=finalunsubscribelist+','+nonduplicatelist[i];
	     else
	       finalunsubscribelist=nonduplicatelist[i];
	 }
	  }
	  $('#email').val(finalunsubscribelist);
	  return true;
	}

	function removeunsubscribeduplicates(maillist){
	  var newmaillist=new Array(); 
	  label:for(var i=0;i<maillist.length;i++){
	   maillist[i]=maillist[i].toLowerCase().replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	  for(var j=0;j<newmaillist.length;j++)
	  {
	   if(newmaillist[j]==maillist[i]) 
	   continue label;
	  }
	  newmaillist[newmaillist.length] = maillist[i];
	  }
	  return newmaillist;
	}

	function processingUnsubscribeMailList(emaillist){
	emailsarray=emaillist.split("\n");
	var emailadd='';
	for(var i=0;i<emailsarray.length;i++){
	 var temp=emailsarray[i];
	 columnsarry=temp.split(","); 
	 if(columnsarry.length>1){
	 for(var j=0;j<columnsarry.length;j++){
	  if(emailadd!='')
	  emailadd=emailadd+','+columnsarry[j];
	  else
	  emailadd=columnsarry[j];
	 }
	 }else{
	 if(emailadd!='')
	 emailadd=emailadd+','+temp;
	 else
	 emailadd=temp;
	 }
	}
	return emailadd;
	}
</script>
</body></html>

