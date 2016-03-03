<%@taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/custom.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/font-awesome-4.3.0/css/font-awesome.css" />
</head>
<body>
 <div class="container">
<div id="deleteattendeenoteserrormessages"></div>
<s:form name="deleteattendeenotes" id="deleteattendeenotes" action="EditAttendee!deleteAttendee">
<s:hidden name="pid"></s:hidden>
<s:hidden name="eid" id="eventid"></s:hidden>
<s:hidden name="ticketid"></s:hidden>
<s:hidden name="tid" id="transactionid"></s:hidden>
<s:hidden name="profilecount" id="profilecount"></s:hidden>

<div class="row">
<div class="col-xs-3"><s:text name="del.nte.lbl"/>&nbsp;</div>
<div class="col-xs-9">
<s:if test="%{profilecount==1}"><s:text name="del.total.trans.del.lbl"/></s:if><s:else>
 <s:text name="del.attnde.info.del.cmplty.lbl"/>
 </s:else>
</div>
</div>
 <div style="height:10px"></div>
 <div class="row">
<div class="col-xs-3"><s:text name="del.rsn.lbl"/>&nbsp;</div>
<div class="col-xs-9"><s:textarea name="deleteattendeenotes" id="deleteattendeenotes" cols="55" rows="7" cssClass="form-control"></s:textarea></div>

</div>
</s:form>
<hr>
<div class="form-group">
                        <div class="center">
                            <button type="submit" class="btn btn-primary" id="deleteattendeesubmit">
                                 <s:text name="del.cntn.lbl"/></button>
                            <button class="btn btn-cancel" id="cancel">
                                 <i class="fa fa-times"></i></button>
                        </div>
                    </div>
</div>
</body></html>
<script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
 <script>
 $(document).ready(function(){
	 $('#deleteattendeesubmit').click(function(){
var reportstype=parent.$('input[name="reports"]:checked').val();
		 //alert(parent.$('input[name="reports"]:checked').val());
		 parent.loadingPopup();
$.ajax({
	type:'get',
	data:$('#deleteattendeenotes').serialize(),
	url:'EditAttendee!deleteAttendee',
	success:function(result){
		var tid=$('#transactionid').val();
		if($('#profilecount').val()==1)
		parent.window.location.reload();
		else{	
		parent.$('tr.nowell').remove();
		parent.$('tr.info').removeClass('info');
		if(reportstype=='Attendee'){
			parent.$('#attendeesubmitbtn').trigger('click');
		}else if(reportstype=='Sales')
			parent.$('#transactionsubmitbtn').trigger('click');
		else if(reportstype=='Check-In')
			parent.$('#checkinsubmitbtn').trigger('click');

		$('html, body').animate({ scrollTop: parent.$("#transactionsubmitbtn").offset().top-100}, 1000);
		
		parent.$('#myModal').modal('hide');
		}
	}
}); 
	 });

	 $('#cancel').click(function(){
		 parent.$('#myModal').modal('hide');
		});
	 
	 });
 </script>
