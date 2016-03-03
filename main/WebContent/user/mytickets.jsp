<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<script type="text/javascript" src="/main/js/prototype.js"></script>
<script type="text/javascript" src="/main/js/advajax.js"></script>
<script type="text/javascript" src="http://www.eventbee.com/home/js/customfonts/cufon-yui.js"></script>
<script src="http://www.eventbee.com/home/js/customfonts/Myriad_Pro_400.font.js" type="text/javascript"></script>
<script>Cufon.replace('.featuredeventsheader h2');</script> 
<link href="/main/css/newhomepage.css" type="text/css" rel="stylesheet">
<link href="/main/css/style.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/main/build/container/assets/skins/sam/container.css" />
<link href="/main/css/yuioverrides.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/main/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="/main/build/container/container-min.js"></script>
<script type="text/javascript" src="/main/js/common/common.js"></script>
<head>
<style>
  body {
  background: #F5F5F5;
  }
</style>
</head>
<body>
<%
String tid=request.getParameter("tid");
if(tid == null)tid="";
%>

<div id="backgroundPopup" style="display:none;"></div>
<div id="mytickets">
<table width="100%">
<tr><td height="20">
<!--<form action="mytickets!getTicketsInformation" name="transactionid" id="transactionid" >-->
<table width="100%">
<tr><td align="left"><div id="statusmsg"></div></td></tr>
<tr><td align="left"><div id="statusmsg1"  valign="middle" style="display:none;"></div></td></tr> 


<tr>
   <td height="40" class="featuredeventsheader">
   <h2>Get Your Tickets</h2></td>
</tr>
<tr>
   <td height="40" width="100%">Enter Transaction ID to receive your registration/tickets confirmation email.</td>
</tr>  
<tr><td height="40">
<table  width="100%" >
<tr><td  width="100%">Transaction ID <input type="text" name="transaction_id" id="transaction_id" size="40" value="<%=tid%>"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onClick="return processTransaction();" name="gettickets" id="gettickets" value="Email Me Tickets"/></td>
</tr>
</table>
</td></tr> 
<tr>
   <td height="40" width="100%">If you don't have Transaction ID, enter your email to receive Transaction ID.</td>
</tr>

<tr><td height="40">Enter Email <input type="text" name="email_id" id="email_id" size="40">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onClick="return processEmail();" name="submit" id="submit" value="Email Me Transaction ID"/></td></tr>

</table>
</td></tr>

<tr><td height="20"></td></tr>
<tr><td height="40">NOTE: If you don't see registration/tickets confirmation email in your Inbox, please do check your Bulk/Spam folder, and update your spam
 filter settings to allow Eventbee emails. For further help, please email support@eventbee.com</td></tr>
</table>
</div>
<script type="text/javascript">
function processEmail()
  {
     document.getElementById("backgroundPopup").style.display="block";
     YAHOO.ebee.popup.wait.show();
   	//var msg="Ticket(s) information sent to your mail";
	var msg1='<ul class="errorMessage"><li align="left"><font color="red" >Please enter your Email</font></li></ul>';
	var msg2='<ul class="errorMessage"><li align="left"><font color="red">Please enter valid Email</font></li></ul>';
	var emailid=document.getElementById('email_id').value;
	var x=document.getElementById('email_id').value;
	var url="";
	var emailExp = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-Z0-9]{2,4}$/;
	
	if(x!="")
	{
    document.getElementById("backgroundPopup").style.display="block";
	YAHOO.ebee.popup.wait.show();
	emailid=emailid.replace(/[ \t\r]+/g,"");
	emailid=emailid.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
	document.getElementById('email_id').value=emailid;
	emailid=emailid.toLowerCase();
	if(emailid.match(emailExp))
	{
	  url="mytickets!getEmailId?email_id="+emailid;
      new Ajax.Request(url, {
    	       method: 'post',
    	       onSuccess: function(t){
			   var msg3=t.responseText;
			   
			   if(msg3.indexOf("success")>-1)
			   {
			   document.getElementById('statusmsg1').style.display='none';
			   document.getElementById('statusmsg').style.display='none';
			   YAHOO.ebee.popup.wait.hide();
			   document.getElementById("backgroundPopup").style.display="none";
			   document.getElementById('statusmsg').show();
			   document.getElementById('statusmsg').update(t.responseText);
			   }
			   else
			   {
			    t.responseText='<ul class=errorMessage><li>'+t.responseText+'</li></ul>';
			    document.getElementById('statusmsg').style.display='none';
				document.getElementById('statusmsg1').style.display='none';
		        YAHOO.ebee.popup.wait.hide();
				document.getElementById("backgroundPopup").style.display="none";
				document.getElementById('statusmsg1').show();
				document.getElementById('statusmsg1').update(t.responseText);
			   }
             }
    });
	 //document.getElementById('statusmsg').innerHTML=msg;
      document.getElementById('transaction_id').value="";
	 }
	else
	    {
		 document.getElementById('statusmsg').style.display='none';
		 document.getElementById('statusmsg1').style.display='none';
	    	YAHOO.ebee.popup.wait.hide();
			document.getElementById("backgroundPopup").style.display="none";
	    	document.getElementById('statusmsg1').show();
		 document.getElementById('statusmsg1').innerHTML=msg2;
		 document.getElementById('transaction_id').value="";
	    }
	}
	 else
	{
       document.getElementById('statusmsg').style.display='none';
	   document.getElementById('statusmsg1').style.display='none';
	  	YAHOO.ebee.popup.wait.hide();
		document.getElementById("backgroundPopup").style.display="none";
	   document.getElementById('statusmsg1').show();
	  document.getElementById('statusmsg1').innerHTML=msg1;
	  document.getElementById('transaction_id').value="";
	}  
  }
  
 function processTransaction()
{
	 document.getElementById("backgroundPopup").style.display="block";
	 YAHOO.ebee.popup.wait.show();
	//var msg="Ticket(s) information sent to your mail";
	var msg1='<ul class="errorMessage"><li align="left"><font color="red">Please enter your Transaction ID</font></li></ul>';
	var transactionid=document.getElementById('transaction_id').value;
	var y=document.getElementById('transaction_id').value;
	var url="";
	if(y!="")
	{
	 
	transactionid=transactionid.replace(/[ \t\r]+/g,"");
	transactionid=transactionid.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,''); 
	document.getElementById('transaction_id').value=transactionid;
    url="mytickets!getTransactionId?transaction_id="+transactionid;
	     new Ajax.Request(url, {
	    	       method: 'post',
	    	       onSuccess: function(t){
				var msg=t.responseText;
				//alert(msg);
				if(msg.indexOf("success")>-1)
				{
				 document.getElementById('statusmsg1').style.display='none';
				 document.getElementById('statusmsg').style.display='none';
				 YAHOO.ebee.popup.wait.hide();
				 document.getElementById("backgroundPopup").style.display="none";
				 document.getElementById('statusmsg').show();
				 document.getElementById('statusmsg').update(t.responseText); 
                }
               else
			   {
			    t.responseText='<ul class=errorMessage><li>'+t.responseText+'</li></ul>';
				document.getElementById('statusmsg').style.display='none';
				document.getElementById('statusmsg1').style.display='none';
	            YAHOO.ebee.popup.wait.hide();
				document.getElementById("backgroundPopup").style.display="none";
				document.getElementById('statusmsg1').show();
				document.getElementById('statusmsg1').update(t.responseText); 
			   }
			 }
	      }); 
   // document.getElementById('statusmsg').innerHTML=msg;
	document.getElementById('email_id').value="";	
	}
	else
	{
     document.getElementById('statusmsg').style.display='none';
	 document.getElementById('statusmsg1').style.display='none';
	 YAHOO.ebee.popup.wait.hide();
	 document.getElementById("backgroundPopup").style.display="none";
	 document.getElementById('statusmsg1').show();
	 document.getElementById('statusmsg1').innerHTML=msg1;
	 document.getElementById('email_id').value="";
	 }
	
}
</script>
</body>
</html>
