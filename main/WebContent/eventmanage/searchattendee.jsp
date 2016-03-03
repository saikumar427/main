<%@taglib uri="/struts-tags" prefix="s"%>
<style>
.form-inline .form-control{
width:auto
}
</style>
			<div class="col-xs-12 col-sm-12">
                <p class="visible-xs">
                    <button type="button" class="btn btn-block btn-primary" data-toggle="offcanvas">Menu
                        <i class="fa fa-arrows-h"></i>
                    </button>
                </p>
				<div class="row">
                    <div id="search">               
                        <div class="col-md-12">
                            <h2>Search</h2>
                            <s:form action='TransactionDetails!getTransactionInfo'    	id='transactionform' name='transactionform' method="post" 	theme="simple" cssClass="form-inline" role="form" >
	<s:hidden name="eid" id="eid"/>
	<s:hidden name="powertype"/>
	<s:set name="trancheck" value="traninfoindex"/>
	
	<%-- <s:hidden name="reportsData.attsDay" id="attsdate"/>
	<s:hidden name="reportsData.attsMonth" id="attsmonth"/>
	<s:hidden name="reportsData.attsYear" id="attsyear"/>
	
	<s:hidden name="reportsData.atteDay" id="attedate"/>
	<s:hidden name="reportsData.atteMonth" id="attemonth"/>
	<s:hidden name="reportsData.atteYear" id="atteyear"/> --%>
	
                                <input type="radio" name="traninfoindex" value="1" id="tranradio" <s:if test="%{#trancheck==1 || #trancheck==''}">checked='checked'</s:if>>
                                <s:textfield name="tid" id="tid" theme="simple" cssClass="form-control" placeholder="Transaction ID" ></s:textfield>
                                <br>
                                <br>
                               <input type="radio" name="traninfoindex" value="2" id=""<s:if test="%{#trancheck==2}">checked='checked'</s:if>/>
                               <s:textfield name="attendeename"  cssClass="form-control" id="attendeename"  theme="simple" placeholder="First Name"></s:textfield>
	    						<s:textfield name="attendeelastname" cssClass="form-control" id="attendeelname" theme="simple" placeholder="Last Name" ></s:textfield>
                                <br>
                                <br>
                                <s:if test="%{powertype=='Ticketing'}">
                                <input type="radio" name="traninfoindex" value="3" <s:if test="%{#trancheck==3}">checked='checked'</s:if> />
                                <s:textfield cssClass="form-control" name="ordernumber" placeholder="Order Number" id="ordernumber" theme="simple"></s:textfield>
                                <br>
                                <br>
                                </s:if>
                                <input type="radio" name="traninfoindex" value="5" <s:if test="%{#trancheck==5}">checked='checked'</s:if>/>
                                <s:textfield name="emailid" id="emailid" placeholder="Email" cssClass="form-control" theme="simple"></s:textfield>
                                <br>
                                <br>
                                <input id="searchdate"  type="radio" name="traninfoindex" value="4" <s:if test="%{#trancheck==4}">checked='checked'</s:if> />
                                <input type="text" class="form-control" placeholder="Start date" id="startDate" name="startDate" />
                                <input type="text" class="form-control" placeholder="End date" id="endDate" name="endDate"/>
                                <br>
                            </s:form>
                            <br/><br/>
                            <p class="text-center">
                                    <button class="btn btn-primary" id="attendeedetails">Submit</button>
                            </p>
                        </div>
						<br/>
                        <div class="col-md-12">
                            <div class="table  table-responsive" style="overflow-x: visible;">                              
                            </div>
                            <br/>
                            <div id="error" style="text-align: center;"></div>
                        </div>
                    </div>

                </div>
                <!--/row-->
                 <div style="height:50px;"></div>
          	</div>
            <!--/span-->
            <script type="text/javascript" language="javascript" src="/main/js/dataTables.js"></script>
<script>
var eid=${eid};
var attendeedata='';
var salesdata='';
var checkindata='';
var filteronchange=false;
var actionType='';
var buyerProfileId='';
var attendeeProfileId='';


    $(document).ready(function() {  			
    	
    	 $('#tabl1').dataTable( {
             "sPaginationType": "full_numbers",
             "iDisplayLength":5,
             "bLengthChange": false,
             bFilter:false,
             "aoColumns": [null,
                           null,                                 
                           { "bSortable": false }
                         ] 
                         
         } );
    	 
    	 

    	 $('input[name="traninfoindex"]').iCheck({  
    			checkboxClass: 'icheckbox_square-grey', 
    			radioClass: 'iradio_square-grey'});
        

        $('[data-toggle=offcanvas]').click(function() {
            $('.row-offcanvas').toggleClass('active');
        });

        $('#startDate, #endDate').datetimepicker({
            format: 'm/d/Y',
            timepicker: false
        });

      

        $('#tid').on('focus', function() {
            $('input[name=traninfoindex][value=1]').iCheck('check');
        });
        
        $('#attendeename,#attendeelname').on('focus', function() {
            $('input[name=traninfoindex][value=2]').iCheck('check');
        });
        
        $('#ordernumber').on('focus', function() {
            $('input[name=traninfoindex][value=3]').iCheck('check');
        });
        
        $('#emailid').on('focus', function() {
            $('input[name=traninfoindex][value=5]').iCheck('check');
        });
        
        $('#startDate,#endDate').on('focus', function() {
            $('input[name=traninfoindex][value=4]').iCheck('check');
        });

        
        $('#attendeedetails').click(function(event){
        	var flag=true;
        	var selectedoption = $('input[name=traninfoindex]:checked').val();
        	//alert(selectedoption);
        	switch (selectedoption) {
        	
            case '1': 
            	var value=document.getElementById('tid').value;
            	if(value==''){ flag=false;
            	$('#error').show();
        		$('.table-responsive').hide();
        		$('#error').html("Transaction ID is empty");
        		}
                break;
            case '2':
            	var value1=document.getElementById('attendeename').value;  
            	var value2=document.getElementById('attendeelname').value;
            	if(value1=='' && value2==''){ flag=false;
            	$('#error').show();
        		$('.table-responsive').hide();
        		$('#error').html("Attendee Name is empty");
        	}
                break;
            case '3':
            	var value=document.getElementById('ordernumber').value;
            	if(value==''){ flag=false;
            	$('#error').show();
        		$('.table-responsive').hide();
        		$('#error').html("Order Number is empty");
        	}
                break;
            case '4':
            	var value1=document.getElementById('startDate').value;  
            	var value2=document.getElementById('endDate').value;
            	if(value1=='' || value2==''){ flag=false;
            	$('#error').show();
        		$('.table-responsive').hide();
        		$('#error').html("Date is empty");
        	}
                break;
            case '5':
            	var value=document.getElementById('emailid').value;
            	if(value==''){ flag=false;
            	$('#error').show();
        		$('.table-responsive').hide();
        		$('#error').html("Email is empty");
        	}
                break;
        } 
        	
        	
        	/* if(document.getElementById('searchdate').checked)
        	{
        	 var str1=document.getElementById("startDate").value;   
         	var date=str1.split("/");         	
         	document.getElementById("attsmonth").value =date[0];
         	document.getElementById("attsdate").value =date[1];
         	document.getElementById("attsyear").value =date[2];       	
         	
         	var str2=document.getElementById("endDate").value;   
         	var date2=str2.split("/");
         	document.getElementById("attemonth").value =date2[0];
         	document.getElementById("attedate").value =date2[1];
         	document.getElementById("atteyear").value =date2[2];  
        	}  */
        	
        	if(flag){
        	 loadhtml='<center><img src="../images/ajax-spinner.gif"></center>';
        	$('.table-responsive').html(loadhtml);
        	$('#error').hide();
        	//$('.table-responsive').hide();
        	 $('#thead').hide(); 
        	var url='';
        	 var option = $('input[name=traninfoindex]:checked').val(); 
        	
            switch (option) {
                case '1':
                	url='TransactionDetails!getTransactionIdInfo';
                    break;
                case '2':
                	url='TransactionDetails!getTranInfoByAttendeeName';
                    break;
                case '3':
                   url='TransactionDetails!getOrderNumTransactionInfo';
                    break;
                case '4':
                   url='TransactionDetails!getTranInfoByDates';
                    break;
                case '5':
                   url='TransactionDetails!getTranInfoByEmail';
                    break;
            }            
                      
            	
				
  		   
           
            
            
         	
        	$.ajax({
                type: "POST",
                url: url,
                data: $("#transactionform").serialize(), 
                success: function(data){
                	

                	 if(!isValidActionMessage(data)) return;
                	if(data.indexOf('Not a valid Transaction ID')>-1){
                		$('#error').show();
                		$('.table-responsive').hide();
                		$('#error').html(data);
                		
                	}else if(data.indexOf('Not a valid Attendee Name')>-1){
                		$('#error').show();
                		$('.table-responsive').hide();
                		$('#error').html('None Found');
                		
                	}else if(data.indexOf('Not a valid Order Number')>-1){
                		$('#error').show();
                		$('.table-responsive').hide();
                		$('#error').html('Not a valid Order Number');
                		
                	}else if(data.indexOf('AttendeeTranReport')>-1){ 
                	     var json=eval('('+data+')');
                	     
                	     getReport(json);     
                	           	     
                	}else if(data.indexOf('Not a valid Email')>-1){
                		$('#error').show();
                		$('.table-responsive').hide();
                		$('#error').html('None Found'); 
                		
                	}else if(data.indexOf('No Records')>-1){
                		$('#error').show();
                		$('.table-responsive').hide();
                		$('#error').html('None Found');
                		
                	}else if(data.indexOf('None Found')>-1){                		
                		$('#error').show();
                		$('.table-responsive').hide();
                		$('#error').html('None Found');
                		
                	}else if(data.indexOf('Date fileds are Empty')>-1){
                		
                		$('#error').show();
                		$('.table-responsive').hide();
                		$('#error').html('Date fileds are Empty');
                	}else if(data.indexOf('Order Number is Empty')>-1){
                		
                		$('#error').show();
                		$('.table-responsive').hide();
                		$('#error').html('Order Number is Empty');
                	}
                	
                	
                	
                }
                }); 
        	}//if close
        	});
        

        var divOpen = false;

        $(document).on( "click",".clickk",function(e) {
        	$('.closeTransactionDetails').trigger("click");
        	divOpen=false;
        	loadingPopup();
        	var tidObj;
        	var chkboxlen;
        	if(actionType=='attendee'){
        		chkboxlen=$('input[name="attFields"]:checked').length;
        	}else if(actionType=='registration'){
        		chkboxlen=$('input[name="Fields"]:checked').length;
        	}else{
        		chkboxlen=$('input[name="checkinFields"]:checked').length;
        	}
        	
        	var isrecurring=$('#isrecur').val();
        	var powertype='Tickeing';
        	var paymenttype='${paymentType}';
        	
        	var tid=$(this).attr('data-tid');
        	var url='TransactionDetails!getTransactionInfo?eid='+eid+'&tid='+tid;
        	$.ajax({
        		type:"POST",
        		url:url,
        		async:false,
        		success:function(result){
        			if(!isValidActionMessage(result)) return;
        		   tidObj=JSON.parse(result);
        		}
        	});
        	
        	var len=$.map(tidObj.transdetails, function(n, i) { return i; }).length;
        	var tktlen=$.map(tidObj.tktdetails, function(n, i) { return i; }).length;
        	var buyerlen=$.map(tidObj.buyerdetails, function(n, i) { return i; }).length;
        	var attlen=$.map(tidObj.attendeedetails, function(n, i) { return i; }).length;
        	var paymentstatus=tidObj.transdetails.paymentstatus;
        	var bookingdomain=tidObj.transdetails.bookingdomain;
        	var refund=tidObj.transdetails.refund;
        	var profilecount=tidObj.profilecount;
        	var currencySymbol=tidObj.currencySymbol;
        	var currentTransactionDate=tidObj.currentTransactionDate;
        	e.preventDefault();
            if (divOpen) return;
            chkboxlen=2;
            var html = '<tr class="well">' +
                '<td colspan="5" >' +
                '<div >' +
                '<span class="pull-right">' +
                '<button class="btn btn-active btn-xs closeTransactionDetails">' +
                '<i class="fa fa-times"></i>' +
                '</button>' +
                '</span>' ;
               if(tidObj!=undefined && len>0){

            	  
                   	html+= '<h4 class="section-header">Transaction Info</h4><hr>';
                  
                if(powertype!='RSVP' && isrecurring=='true')
               html+='Event Date: '+tidObj.transdetails.eventdate;
              
                html+= '<span class="pull-right">';

                if(paymentstatus=='Cancelled' && bookingdomain!='VBEE'){
                	html+='[<a href="javascript:;" onclick="reactivatetransaction(\''+eid+'\',\''+tid+'\',\''+tidObj.transdetails.paymenttype+'\')">Undelete Transaction</a>]<br/><br/>';
                    }else{
                if(powertype!='RSVP'){
                	if(paymentstatus!='Deleted')
                html+= '[<a href="javascript:;" id="resend_'+tid+'" onclick="resendMail(\''+eid+'\',\''+tid+'\')">Resend email</a>]<br/><br/>' ;
                }else{
                	if(bookingdomain!='VBEE')
                		html+='[<a href="javascript:;" onclick="deletersvptransaction(\''+eid+'\',\''+tid+'\')">Delete Transaction</a>]';
                html+='[<a href="javascript:;" id="resend_'+tid+'" onclick="resendMail(\''+eid+'\',\''+tid+'\')">Resend email</a>]<br/><br/>';	
                }
                    }
                html+='</span>';
                
                html+='<table class="table table-responsive">' +
                '<thead>' +
                '<th>Order Number</th>' +
                '<th>Transaction ID</th>' +
                '<th>Purchase Date & Time (EST)</th>' +
                '<th>Transaction Source</th>' +
                '<th>Transaction Status</th>' +
                '</thead>' +
                '<tbody>' +
                '<tr>' +
                '<td>'+tidObj.transdetails.orderNumber+'</td>' +
                '<td>'+tid+'</td>' +
                '<td>'+tidObj.transdetails.transaction_date+'</td>' +
                '<td>'+tidObj.transdetails.bookingsource+'</td>' +
                '<td>'+tidObj.transdetails.paymentstatus;
               

                if(powertype!='RSVP' && bookingdomain!='VBEE' && (paymentstatus=='Completed' || paymentstatus=='Pending Approval')){
                html+='&nbsp;[<a href="javascript:;" onclick="changeStatus(\''+eid+'\',\''+tid+'\',\''+tidObj.transdetails.paymenttype+'\')">Change Status</a>]' ;
                }

                html+='</td></tr></tbody></table>'; 

               html+='<div>&nbsp;&nbsp; <b>Notes </b>[<a href="javascript:;" onclick="addNotes(\''+eid+'\',\''+tid+'\');">Edit</a>]&nbsp;&nbsp;&nbsp;'+tidObj.notes+'</div><br/>';
               html+='<div id="notesData"></div>'; 
                if(powertype!='RSVP'){
                html+='<h4 class="section-header">Payment Details</h4>';
                if(powertype!='RSVP' && bookingdomain!='VBEE' && refund=='Yes' && paymentstatus=='Completed')
                	html+='<span class="pull-right">[<a href="javascript:;" onclick="refund(\''+eid+'\',\''+tid+'\',\''+tidObj.transdetails.paymenttype+'\')">Refund</a>]</span>' ;
                
                
                html+='<hr><table class="table table-responsive">'+
                '<thead><th>Discount Code</th>'+
                '<th>Total Discount ('+currencySymbol+')</th>' +
                '<th>Credit Card Processing Fee/Tax ('+currencySymbol+')</th>' +
                '<th>Total Amount ('+currencySymbol+')</th>' +
                '<th>Payment Method</th>' +
                '</thead>' +
                '<tbody>' +
                '<tr>' +
                '<td>'+tidObj.transdetails.discountcode+'</td>' +
                '<td>'+tidObj.transdetails.discount+'</td>' +
                '<td>'+tidObj.transdetails.tax+'</td>' +
                '<td>'+tidObj.transdetails.totalAmount+'</td>' +
                '<td>'+tidObj.transdetails.paymenttype+'</td>' +
                '</tr></tbody></table>' ;


        		if(tidObj.transdetails.refund_amt>0){                
                html+='<hr><table><tr>';
                html+='<td><b>Refunded Amount&nbsp; ('+currencySymbol+')</b></tr><tr>';
                html+='<td><div style="height: 8px;"></div>'+tidObj.transdetails.refund_amt+'</td></tr></table><br/>';
        		}
               }
                
                html+='<div id="tktData">';
                if(powertype!='RSVP'){
                	
                	if(tktlen!=undefined && tktlen>0){
                		html+='<h4 class="section-header">Ticket Details</h4><hr>' ;

                   if(paymentstatus!='Deleted' &&  bookingdomain!='VBEE' )
                           html+='<span class="pull-right">[<a href="javascript:;" onclick="ticketdetails(\''+eid+'\',\''+tid+'\')";>Edit</a>]</span>' ;
                        
                        html+='<table class="table table-responsive">'+
                        '<thead><th>Ticket Name</th>'+
                        '<th>Price ('+currencySymbol+')</th>' +
                        '<th>Count</th>' +
                        '<th>Discount ('+currencySymbol+')</th>' +
                        '<th>Total ('+currencySymbol+')</th>' +
                        '</thead>' +
                        '<tbody>' ;
                        
                        for(var i=0;i<tktlen;i++){
                        html+='<tr>' +
                        '<td>'+tidObj.tktdetails[i].ticketName+'</td>' +
                        '<td>'+tidObj.tktdetails[i].ticketprice+'</td>' +
                        '<td>'+tidObj.tktdetails[i].qty+'</td>' +
                        '<td>'+tidObj.tktdetails[i].discount+'</td>' +
                        '<td>'+tidObj.tktdetails[i].ticketstotal+'</td>' +
                        '</tr>' ;
                        }
                        html+='<tr><td colspan="5">';
                        html+='<div id="ticketDetailsData"></div></td></tr>';
                        html+='</tbody>'+ 
                        '</table>';
                	}

                }else{

                	if(tktlen!=undefined && tktlen>0){
                		html+='<h4 class="section-header">Response Details</h4><hr>' ;
                		if(isrecurring=='true'){
                    		html+='<form action="TransactionDetails!changeDate" id="transactiondateform" name="transactiondateform" method="post" theme="simple">';
                    		html+='<span><span id="rsvprecurring">For Event Date : '+tidObj.currentTransactionDate+'</span></br><br/>';
                    		html+='ChangeDate <select id="source" name="reportsData.date">';
                    		html+='<option selected="selected" value="">--Select Date--</option>';
                    		for(var i=0;i<tidObj.recurdates.length;i++)
                    			html+='<option value="'+tidObj.recurdates[i]+'">'+tidObj.recurdates[i]+'</option>';
                    		html+='<input type="hidden" name="eid" value="'+eid+'" /><input type="hidden" name="tid" value="'+tid+'" />';
                    		html+='</select>&nbsp;<input type="button" id="rsvprecursubmit" value="Submit" class="btn btn-primary btn-xs"/></span></form>';
                    		}

                		if(tidObj.response1=="Y" && bookingdomain!='VBEE'){
                			 html+='<span class="pull-right">[<a href="javascript:;" onclick="changeToNotAttending(\''+eid+'\',\''+tid+'\')">Change To Not Attending</a> |'+
                    			  '<a href="javascript:;" onclick="addRSVPResponses(\''+eid+'\',\''+tid+'\')">Add More Attendees</a>]</span>'; 
                    		}else if(bookingdomain!='VBEE'){
                        		html+='<span class="pull-right">[<a href="javascript:;" onclick="addRSVPResponses(\''+eid+'\',\''+tid+'\')">Change To Attending</a>]</span>'
                        		}

                		html+='<br/><table class="table table-responsive">'+
                        '<thead><th>Response Type</th>'+
                        '<th>Count</th>'+
                         '</thead>' +
                        '<tbody>' ;
                		for(var i=0;i<tktlen;i++){
                            html+='<tr>' +
                            '<td>'+tidObj.tktdetails[i].ticketName+'</td>' +
                            '<td>'+tidObj.tktdetails[i].qty+'</td>' +
                            '</tr>' ;
                            }
                            html+='</tbody>'+ 
                            '</table>';
                            
                    	}
                    }
                html+='</div>';

                html+='<div id="buyerData">';
                if(tidObj.buyerdetails!=null && tidObj.buyerdetails!=undefined && buyerlen>0){
                	html+='<h4 class="section-header">Registrant Details</h4>' +
                    '<table class="table table-responsive" id="'+tidObj.buyerdetails.profilekey+'">';
                    html+='<tr><td><span><b>Profile&nbsp;</b></span>';
                   
                    if(paymentstatus!='Deleted' &&  bookingdomain!='VBEE' )
                    html+='[<a href="javascript:;" onclick="profilefunc(\''+eid+'\',\''+tid+'\',\''+tidObj.buyerdetails.profilekey+'\');">Edit</a>]';
                    html+='</td></tr>';
                    if(tidObj.buyerdetails.fname!='')
                    html+='<tr><td>First Name : '+tidObj.buyerdetails.fname+'</td></tr>';
                    if(tidObj.buyerdetails.lname!='')
                    html+='<tr><td>Last Name : '+tidObj.buyerdetails.lname+'</td></tr>';
                    if(tidObj.buyerdetails.email!='')
                    html+='<tr><td>Email : '+tidObj.buyerdetails.email+'</td></tr>';
                    if(tidObj.buyerdetails.phone!='')
                    html+='<tr><td>Phone : '+tidObj.buyerdetails.phone+'</td></tr>';

                    var buyerkey=tidObj.buyerdetails.profilekey;
                    var customattribs=tidObj.attribmap[buyerkey];
                    if(customattribs!=undefined && customattribs!='undefined' && customattribs.length>0 ){
                    for(var att=0;att<customattribs.length;att++){
                    	if(customattribs[att]['response']!='')
                       html+='<tr><td>'+customattribs[att]['question']+' : '+customattribs[att]['response']+'</td></tr>';
                        }
                    }
                    html+='</table><table class="table table-responsive" id="edit_'+tidObj.buyerdetails.profilekey+'" style="display:none;">';
                    html+='<tr><td>';
                    html+='<div id="buyerProfileData"></div></td></tr>';
                    html+='</table>';
                
                }
                html+='</div>';
               /*  html+='<div id="buyerloading" style="display:none;"><center><img src="../images/ajax-loader.gif"></center></div>';
                html+='<div id="buyerdetailsdata" class="profiles" style="display:none;"></div><br/>'; */
                
                if(attlen>0){
                	html+='<h4 class="section-header">Attendee Details</h4>' ;
                    
                
                    for(var i=0;i<tktlen;i++){
                    	try{
                    		var tktdata=tidObj.tktdetails[i];
                        	var tktid=tktdata.tktnumber;
                        	var tktname=tktdata.ticketName;
                        	var attendeedetailsObj=tidObj.attendeedetails; 
                        	var tktattendeedetails=attendeedetailsObj[tktid];
                        	for(var att=0;att<tktattendeedetails.length;att++){
                        		var c=att+1;
                        		var eachtktdetails=tktattendeedetails[att];
                        		var pk=eachtktdetails.profilekey;
                        		html+='<table class="table table-responsive" id="'+pk+'">';
                        		if(powertype!='RSVP'){
                        			 if(paymentstatus!='Deleted' &&  bookingdomain!='VBEE' ){
                              		 html+='<tr><td><span><b>'+tktname+' - Profile   # '+c+'</b></span> [<a href="javascript:;" onclick="editattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Edit<a/> '+ 
                              		 '<a href="javascript:;" onclick="deleteattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\',\''+profilecount+'\');">Delete<a/>]</td></tr>';
                        			 }
                        		}else{
                                       if(tktname!='Not Sure' &&  bookingdomain!='VBEE'){
                                    	   html+='<tr><td><b>'+tktname+' - Profile   # '+c+'</b>'; 
                                    	   html+='[<a href="javascript:;" onclick="editattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Edit</a> '; 
                                    	   html+= '&nbsp;&nbsp;<a href="javascript:;" onclick="deleteattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\',\''+profilecount+'\');">Delete</a>';
                                    	   html+= '&nbsp;&nbsp;<a href="javascript:;" onclick="changeToNotsure(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Not Sure</a>';
                                    	   html+='&nbsp;&nbsp;<a href="javascript:;" onclick="changeToNotattendingProfile(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Not Attending</a>]</td></tr>';
                                        }else if(bookingdomain!='VBEE'){
                                           html+='<tr><td><b>'+tktname+' - Profile   # '+c+'</b>'; 
                                           html+='[<a href="javascript:;" onclick="editattendeenotes(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Edit</a> '; 
                                      	   html+= '&nbsp;&nbsp;<a href="javascript:;" onclick="deleteattendeenotes('+eid+','+pk+','+tktid+','+tid+','+profilecount+');">Delete</a>';
                                      	   html+= '&nbsp;&nbsp;<a href="javascript:;" onclick="changeToSure(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Sure</a>';
                                      	   html+='&nbsp;&nbsp;<a href="javascript:;" onclick="changeToNotattendingProfile(\''+eid+'\',\''+pk+'\',\''+tktid+'\',\''+tid+'\');">Not Attending</a>]</td></tr>';
                                        }
                            		}


                        		 if(eachtktdetails.fname!='')
                                 html+='<tr><td>First Name : '+eachtktdetails.fname+'</td></tr>';
                                 if(eachtktdetails.lname!='')
                                 html+='<tr><td>Last Name : '+eachtktdetails.lname+'</td></tr>';
                                 if(eachtktdetails.email!='')
                                 html+='<tr><td>Email : '+eachtktdetails.email+'</td></tr>';
                                 var pkey=eachtktdetails.profilekey;
                                 var seatevt=tidObj.seatingevt;
                                 
                                 if(seatevt=='Y'){
                                     if(eachtktdetails.seatcode!=''){
                                     html+='<tr><td>Seat Code : '+eachtktdetails.seatcode;
                                     if(bookingdomain!='VBEE')   
                                     html+=' [<a href="javascript:;" onclick="getSeatingChart(\''+eid+'\',\''+tid+'\',\''+pk+'\',\''+currentTransactionDate+'\',\'changeseat\')">Change Seat</a>]';
                                     html+='</td></tr>';   
                                     }else{
                                    	 if(paymentstatus!='Deleted' && eachtktdetails.tickettype!='donationType'){
                                        	 html+='<tr><td>Seat Code : ';
                                        	    if(bookingdomain!='VBEE')
                                        	    	 html+=' [<a href="javascript:;" onclick="getSeatingChart(\''+eid+'\',\''+tid+'\',\''+pk+'\',\''+currentTransactionDate+'\',\'addseat\')">Add Seat</a>]';
                                        	    html+='</td></tr>';  
                                        	 }
                                         }
                                     }
                                 
                                 
                                 
                                 var attcustomattribs=tidObj.attribmap[pkey];
                                 if(attcustomattribs!=undefined && attcustomattribs!='undefined' && attcustomattribs.length>0 ){
                                 for(var atts=0;atts<attcustomattribs.length;atts++){
        							if(attcustomattribs[atts]['response']!='')
                                    html+='<tr><td>'+attcustomattribs[atts]['question']+' : '+attcustomattribs[atts]['response']+'</td></tr>';
                                     }
                                 }
                                 html+='</table><table class="table table-responsive" id="edit_'+pk+'" style="display:none;">';
                                 html+='<tr><td>';
                                 html+='<div id="attedeedData_'+pk+'"></div></td></tr><table>';
                        	}
                        	
                        	
                    	}catch(err){
                    		//alert(err);
                    	}
                    	
                    	
                    }
                    
                	 html+='</table>';	
                }
               }
                html+='</div>' +
                '</td>' +
                '</tr>';
            $('.closeTransactionDetails').trigger("click");
            var parent = $(this).parents('tr');
            parent.addClass('info');
           
            $("html, body").animate({
                scrollTop: $(parent).offset().top - 60
            }, "slow",function(){
            	 hidePopup();
                });
            $(html).insertAfter(parent);
            divOpen = true;
            $( "div.dataTables_scrollBody" ).scrollLeft( 0 );
        });
 
       

        $(document).on('click', '.closeTransactionDetails', function() {
            divOpen = false;
            $(this).parents('tr').prev().removeClass('info');
            $(this).parents('tr').remove();
        });


$(document).on('click','#rsvprecursubmit',function(){
	$('#rsvprecursubmit').attr('disabled','disabled');
$.ajax({
	type:'POST',
	data:$('#transactiondateform').serialize(),
    url:'TransactionDetails!changeDate',
	success:function(result){
		if(result.indexOf('success')>-1){
			$('#rsvprecursubmit').removeAttr('disabled');
			$('#rsvprecurring').html('For Event Date : '+$('#source').val());
			}
		}
});
});


$(document).on('click', '.closeTransactionDetails', function() {
    divOpen = false;
    $(this).parents('tr').prev().removeClass('info');
    $(this).parents('tr').remove();
});


$(document).on('click','#canceltktdetails',function(){
	$("div.loadedData").remove();	
});

$(document).on('click','#cancelregdetails',function(){
	$('#'+buyerProfileId).show();
	$('#edit_'+buyerProfileId).hide();
	$('#'+attendeeProfileId).show();
	$('#edit_'+attendeeProfileId).hide();
	$("div.loadedData").remove();	
});


$(document).on('click','#canceltktdetails',function(){
	$("div.loadedData").remove();	
});
        

    });
    
    function getReport(data){
      
	$('#attendeeinfo').html('')	;
	var attendeeinfo='<table class="table" id="attendeedata" ><thead><th>Transaction Date</th><th>Transaction ID</th><th>Attendee Name</th></thead><tbody id="attendeetableData">';
		$.each(data.AttendeeTranReport,function(inkey,valueobj){
		
		attendeeinfo+='<tr class="hover-item"><td><a href="#" class="clickk '+valueobj["Transaction ID"]+'" data-tid="'+valueobj["Transaction ID"]+'">'+valueobj["Transaction Date"]+'</a></td><td><a href="#" class="clickk" data-tid="'+valueobj["Transaction ID"]+'">'+valueobj["Transaction ID"]+'</a></td><td><a href="#" class="clickk" data-tid="'+valueobj["Transaction ID"]+'">'+valueobj["Attendee Name"]+'</a></td></tr>';
	});	
	attendeeinfo+='</tbody></table>';
	$('.table-responsive').html(attendeeinfo).show();  
	sortTable();

    }
    
   
    function sortTable(){
    	 var rowCount = $('#attendeetableData tr').length;
    	 
    	$('#attendeedata').dataTable( {
    	    "sPaginationType": "full_numbers",
    	    "iDisplayLength":10,
    	    "bLengthChange": false,
    	    "aoColumns": [null,null,null],
    	        "bFilter": false                  
    	});
    	
    	if(rowCount <= 10)
			removePagination('attendeedata');
        }
    


   /*  function isValidActionMessage(messageText){

    	if(messageText.indexOf('nologin')>-1||messageText.indexOf('login!authenticate')>-1){
    	    	alert('You need to login to perform this action');
    	    	window.location.href="../user/login";
    	    	return false;
    	    }
    	 else if(messageText.indexOf('Something went wrong')>-1){
    	 		alert('Sorry! You do not have permission to perform this action');
    	 		YAHOO.ebee.popup.wait.hide();
    	 		return false;
    	 	} 
    	    return true;

    	} */

    function changeStatus(eid,tid,paymentType){
    	var url='TransactionDetails!changeStatus?eid='+eid+'&tid='+tid+'&payType='+paymentType;
    	var dynatimestamp = new Date();
    	url += '&dynatimestamp='+dynatimestamp.getTime();
    	$('.modal-title').html('Transaction Status');
    	$('#myModal').on('show.bs.modal', function () {
    	$('iframe#popup').attr("src",url);
    	});
    	$('#myModal').modal('show');



    	    	/* YAHOO.ebee.popup.wait.show();
    	var dynatimestamp = new Date();
    	url += '&dynatimestamp='+dynatimestamp.getTime();
    	var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:gettransactionstatus,failure:getFailure});
    	
 */
    }
    	$(document).on('click','#cancelnotes',function(){
    		 $("div.loadedData").remove();
    	});


   /*  function resendMail(eid,tid){
    	var url='TransactionDetails!resendMail?eid='+eid+'&tid='+tid+'&powerType='+'${powertype}';
    	var dynatimestamp = new Date();
    	url += '&dynatimestamp='+dynatimestamp.getTime();
    	$('.modal-title').html('Mail Success');
    	$('#myModal').on('show.bs.modal', function () {
    	$('iframe#popup').attr("src",url);
    	$('iframe#popup').css("height","100px"); 
    	});
    	$('#myModal').modal('show');
    	//var request=YAHOO.util.Connect.asyncRequest('GET',url,{success:getMailSuccess,failure:getFailure});
    } */


    function resendMail(eid,tid){
    	var url='TransactionDetails!resendMail?eid='+eid+'&tid='+tid+'&powerType=Ticketing';
    	var dynatimestamp = new Date();
    	url += '&dynatimestamp='+dynatimestamp.getTime();
    	$.ajax({
    		type:'get',
    		url:url,
    		success:function(result){
    			if(!isValidActionMessage(result)) return;
    			openSinglePopUpWithOptions($('#resend_'+tid).offset(),resendsuccess,resendcancel,'','Mail Sent Successfully','');
    			$('#onefieldtextoption').hide();
    			$('#singlecancel').hide();
    			$('#singlesubmit').attr('value','OK');
    			}
    	});
    }


    var resendsuccess = function(result){
    	$('#singleValuePopUpOptions').hide();
    };

    var resendcancel = function(){
    	$('#singleValuePopUpOptions').hide();
    };
    

    

    function showDetails(tid){
    	// closepopup();
    	//hidePopup();
    	$('.closeTransactionDetails').trigger("click");
    	//alert("in show details method::"+tid);
    	loadingPopup();
    	$("."+tid).each(function() {
    		 $(this).trigger("click",function(){
   	 });
    	   	 
    		 return false;
        });
    	 //hidePopup();
    }

     

    function addNotes(eid,tid){
    	$("div.loadedData").remove();	
    	var url='TransactionDetails!addNotes?eid='+eid+'&tid='+tid;
    	var dynatimestamp = new Date();
    	url += '&dynatimestamp='+dynatimestamp.getTime();
    		var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
    	$('#notesData').append(temp);
    	  $('#filledData').load(url,function(){
    		 $('.imgload').hide();
    		 $('#filledData').show("slide", {direction: "top" },"slow");
        	 });
    }

    function ticketdetails(eid,tid){
    	$("div.loadedData").remove();	
        $('#tktloading').show();	
    	var url='EditTransactionTickets!editTickets?eid='+eid+'&tid='+tid;
    	var dynatimestamp = new Date();
    	url += '&dynatimestamp='+dynatimestamp.getTime();
    		var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
    	$('#ticketDetailsData').append(temp);
    	  $('#filledData').load(url,function(){
    		 $('.imgload').hide();
    		 $('#filledData').show("slide", {direction: "top" },"slow");
        	 }); 
    	}

    function profilefunc(eid,tid,pkey){
    	buyerProfileId=pkey;
    	$('#edit_'+pkey).show();
    	$('#'+pkey).hide();
    	$("div.loadedData").remove();
    	$('#buyerloading').show();
    	var url='EditAttendee!editBuyerInfo?eid='+eid+'&tid='+tid+'&pid='+pkey;
    	var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
    	var dynatimestamp = new Date();
    	url += '&dynatimestamp='+dynatimestamp.getTime();
    	$('#buyerProfileData').append(temp);
        	$('#filledData').load(url,function(result){
        		$('.taskheader').hide();
       		 $('.imgload').hide();
       		$('#filledData').show();
       		$('#profile_header').show("slide", {direction: "bottom" },"slow");
       		$('.taskheader').show();
       		setTimeout(function(){
       		$('input.radiochk').iCheck({  
       			checkboxClass: 'icheckbox_square-grey', 
       			radioClass: 'iradio_square-grey'});
       		},500);
           	 }); 
    	}

    function editattendeenotes(eventid,profileid,ticketid,transactionid){
    	attendeeProfileId=profileid;
    	$('#edit_'+profileid).show();
    	$('#'+profileid).hide();
    	$("div.loadedData").remove();
    	//$('#attendeeloading_'+profileid).show();
    	//YAHOO.ebee.popup.wait.show();
    	var url='EditAttendee?eid='+eventid+'&pid='+profileid+'&ticketid='+ticketid+'&tid='+transactionid;
    	var dynatimestamp = new Date();
    	url += '&dynatimestamp='+dynatimestamp.getTime();
    	//alert("the url is::"+url);
    	var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-loader.gif"></div><div id="filledData"></div></div>';
    	$('#attedeedData_'+profileid).append(temp);
    	$('#filledData').load(url,function(){
       		 $('.imgload').hide();
       		$('#filledData').show();
       		$('#profile_header').show("slide", {direction: "bottom" },"slow");
    		setTimeout(function(){
       	   		$('input.radiochk').iCheck({  
       	   			checkboxClass: 'icheckbox_square-grey', 
       	   			radioClass: 'iradio_square-grey'});
       	   		},500);
    	});
    }

    	function addRSVPResponses(eid,tid){
    		$("div.loadedData").remove();
    		var url='EditTransactionTickets!rsvpEditResponses?eid='+eid+'&tid='+tid;
    		var dynatimestamp = new Date();
        	url += '&dynatimestamp='+dynatimestamp.getTime();
        	var temp='<div class="loadedData"><div class="imgload"><center><img src="../images/ajax-spinner.gif"></div><div id="filledData"></div></div>';
        	$('#tktData').append(temp);
        	$('#filledData').load(url,function(){
	       		 $('.imgload').hide();
	       		$('#filledData').show("slide", {direction: "top" },"slow");
    		});
    	}


    	function deleteattendeenotes(eventid,profileid,ticketid,transactionid,profilecount){
    	    var url='EditAttendee!deleteattendeeNotes?eid='+eventid+'&tid='+transactionid+'&ticketid='+ticketid+'&pid='+profileid+'&profilecount='+profilecount;
    		var dynatimestamp = new Date();
    		url += '&dynatimestamp='+dynatimestamp.getTime();
    		$('.modal-title').html('Delete Attendee Notes');
    		$('#myModal').on('show.bs.modal', function () {
    		$('iframe#popup').attr("src",url);
    		$('iframe#popup').css("height","100px"); 
    		});
    		$('#myModal').modal('show');
    	}

    	function changeToNotAttending(eid,tid){
    		loadingPopup();
        	var url='EditTransactionTickets!changeToNotAttending?eid='+eid+'&tid='+tid;
        	$.ajax({
        		type:'POST',
        	    url:url,
        		success:function(result){
        			showDetails(tid);
        			}
        	});

        	}

function changeToNotsure(eid,pk,tktid,tid){
	loadingPopup();
		var url='EditAttendee!changeToNotsure?eid='+eid+'&pid='+pk+'&ticketid='+tktid+'&tid='+tid;
		$.ajax({
    		type:'POST',
    	    url:url,
    		success:function(result){

    			showDetails(tid);
    			}
    	});
}

function changeToSure(eid,pk,tktid,tid){
	loadingPopup();
	var url='EditAttendee!changeToSure?eid='+eid+'&pid='+pk+'&ticketid='+tktid+'&tid='+tid;
	$.ajax({
		type:'POST',
	    url:url,
		success:function(result){
			showDetails(tid);
			}
	});
}

function changeToNotattendingProfile(eid,pk,tktid,tid){
	loadingPopup();
	var url='EditAttendee!changeToNotattending?eid='+eid+'&pid='+pk+'&ticketid='+tktid+'&tid='+tid;
	$.ajax({
		type:'POST',
	    url:url,
		success:function(result){
			showDetails(tid);
			}
	});
}

function deletersvptransaction(eid,tid){
	loadingPopup();
	var url='TransactionDetails!deleteRSVPTransaction?eid='+eid+'&tid='+tid;
	$.ajax({
		type:'POST',
	    url:url,
		success:function(result){
    		window.location.reload();
			}
	});
}

function refund(eid,tid,paymentType){
	var url='PaymentsRefund?eid='+eid+'&tid='+tid+'&paymentType='+paymentType;
	var dynatimestamp = new Date();
	url += '&dynatimestamp='+dynatimestamp.getTime();
	$('.modal-title').html('Refund');
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","100px"); 
	});
	$('#myModal').modal('show');

}

function getSeatingChart(eventid,tranid,pk,edate,seataction){
	if(pk!='')
		profilekey=pk;
		var dynatimestamp = new Date();
		var url='ManageAttendeeSeats!getSeatingChart?eid='+eventid+'&profilekey='+pk+'&tid='+tranid+'&seataction='+seataction;
		url += '&dynatimestamp='+dynatimestamp.getTime();
		if(seataction=='changeseat')
		$('.modal-title').html('Change Seat');
		else
		$('.modal-title').html('Add Seat');
		$('#myModal').on('show.bs.modal', function () {
		$('iframe#popup').attr("src",url);
		$('iframe#popup').css("height","100px"); 
		});
		$('#myModal').modal('show');
}


function updateNotes(){
	$.ajax({
		type:'get',
		data:$('#notesform').serialize(),
		url:'TransactionDetails!saveNotes',
		success:function(result){
			if(!isValidActionMessage(result)) return;
			var tid=$('#transactionid').val();
			showDetails(tid);
			}
	});
}


    	
    
</script>
 <style type="text/css" title="currentStyle">
           /*  @import "//www.eventbee.com/home/css/blockManage/demo_page.css"; */
             @import "/main/bootstrap/css/demo_table.css";
             .dataTables_filter input { 
            @import '.form-control'; 
            }
           
        </style>
        