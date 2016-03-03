<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
 <link rel="stylesheet" type="text/css" href="../bootstrap/css/demo_table.css" /> 

</head>
<body>
<script type="text/javascript" language="javascript" src="/main/js/reportsDatatable.js"></script>	 
<script type="text/javascript" language="JavaScript" src="/main/js/accountreports.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>
<%-- <div style="padding-left:16px">			
<h1><small>All Event Reports</small></h1>
</div>
<hr> --%>
<s:fielderror cssClass="statusMessageBox alert alert-danger" theme="simple">
</s:fielderror>
<div id="accounterrormsg" class="alert alert-danger statusMessageBox" style="display:none;"></div>

            <div class="row white-box">
                <div class="col-md-12"> 
                   <span id="showdate" style="display:block;">	
                	<form name="getevents" action="accountreports" id="getevents" method="post">
					<s:hidden name="chkditem" id="chked"></s:hidden>
					<s:set name="startdate"  value="%{mgrSelectedDates['startdate']}"></s:set>
					<s:set name="enddate"  value="%{mgrSelectedDates['enddate']}"></s:set>
					
					
					
					
					
                	<div class="row">               		
                		
                		<!-- <div class="col-md-2">
                		<div class="gap"><a href="javascript:;" onclick="displayAllEvents();">All Events</a>&nbsp;|&nbsp;Date Range </div>
                		</div> -->
                		
                		
                		<div class="col-md-2" style="margin-bottom:5px;">
                		<div class="form-group extramarginreportin">
                		<div class="center btn-group btn-toggle" data-toggle="buttons">
                		
                		<label id="alldateslbl" class="optiontype btn btn-default no-radius" >
                			<input class="datesel" id="alldates" name="" value="1" type="radio">All
                		</label>
                		
                		<label id="daterangelbl" class="optiontype btn btn-active no-radius">
							<input class="datesel" id="daterange" name="" value="2"  type="radio">Date Range
						</label>
						
                		</div>
                		</div>
                		</div>
                		
                		
						<div class="col-md-8" id="datepicker" style="display:none;">
							<div class="row">
								<div class="col-md-4 col-sm-4">
								    <input type="text" name="reportsData.startdate" value="" id="from" class="form-control"  placeholder="mm/dd/yyyy"> 
								</div>
								<div class="col-md-1 col-sm-1" style="padding-top:5px"><center>to</center></div>
								<div class="col-md-4 col-sm-4">
								    <input type="text" name="reportsData.enddate" value="" id="to" class="form-control"  placeholder="mm/dd/yyyy"> 
								</div>
								<div class="col-md-3 col-sm-3">
								   <input type="button" onclick="getEvents();" class="btn btn-primary" value="Update" id="geteventsdata">
								</div>	<br>							
							</div>
						</div><div class="col-md-2"></div>
						
                	</div> <!-- row 1-->
                	<div class="row">
                	
                		<div class="col-md-12 ">
                		<input type="hidden" name="" value="62" id="eventlistsize">
                		<span class="pull-right">
                			<a href="javascript:;" id="select">Select All</a>&nbsp; &nbsp;  
                			<a href="javascript:;" id="clear">Clear All</a>
                		</span>
							
                		</div>  
                	</div>
                	<br>
                	
                	<div class="row">
	                	<div class=" row col-md-12">
		                	<!-- <div STYLE="height : 250px; font-size: 12px; overflow: auto; border:2px solid #ccc;padding:10px"> -->					
							
							<div STYLE="height : 250px;  overflow: auto;">	
							<%-- <s:iterator value="%{eventList}" var="attributes" status="stat">
								<div  id="allevtchckbox" class="col-md-6 col-sm-12">
								<s:checkbox cssClass="eventslist" id="clist" name="chekedeventslist"  fieldValue="%{key}"  value="%{selectedEventsList.contains(key)}" theme="simple"/>&nbsp;&nbsp;&nbsp;${value}<br/><br>
								</div>
							</s:iterator> --%>
							<div  id="allevtchckbox" class="col-md-6 col-sm-12">  </div>
							
							
							</div>
						</div>
                	</div><!-- row 2-->
                	<br>                
                	</form>
                	</span> 
                	<form method="post" action="Reports!getAttendeeInfo" name="reportsform"
					id="reportsform"><s:hidden name="eid" id="eid"></s:hidden>
					<s:hidden name="eventlist" id="chekedlist"></s:hidden>
					<s:hidden name="eventstartdate" id="eventstartdate"></s:hidden>
					<s:hidden name="eventenddate" id="eventenddate"></s:hidden>
					<s:set name="reportscheck" value="reportsData.reportsradio"></s:set>
					
                	<!--  <div class="row">
                		<div class="col-md-3">
                			<input type="radio" name="reportsradio" value="attendee" id="reportsattradio" class="attendee"
                			<s:if test="%{#reportscheck == 'attendee'}">checked='checked'</s:if> onclick="getfields('attendee');" />&nbsp; Attendee/Ticket Report 
                		</div>
                		<div class="col-md-4">
                			<input type="radio" name="reportsradio" value="registration" class="sales"
							id="reportstrnsradio" onclick="getfields('registration');"
							<s:if test="%{#reportscheck == 'registration'}">checked='checked'</s:if> />&nbsp;	Sales Report 
                		</div>
                	</div> -->  <!-- row 3--> 
                	
                	<div class="row">
                	<div class="col-md-4 col-sm-5">
                	<div class="form-group extramarginreportin">
                	<div class="center btn-group btn-toggle" data-toggle="buttons">
                	
                		<label id="attendeetype" class="optionselectedtype btn btn-default no-radius" >
                			<input class="reports" id="reportsattradio" name="radioreports" value="1" type="radio" <s:if test="%{#reportscheck == 'attendee'}">checked='checked'</s:if> />Attendee/Ticket Report
                		</label>
                		
                		<label id="salestype" class="optionselectedtype btn btn-active no-radius">
							<input class="reports" id="reportstrnsradio" name="radioreports" value="2"  type="radio" <s:if test="%{#reportscheck == 'registration'}">checked='checked'</s:if> />Sales Report
						</label>
                	
                	</div>
                	</div>
                	</div>
                	
                	
                	<div class="col-md-2 col-sm-3">
                		<span class="add-on"> 
                			<a onclick="" href="javascript:;" id="filter">  Filters </a>
                			<a onclick="" href="javascript:;" id="fields">   &nbsp;&nbsp; Fields</a>
						</span>
                	</div>
                	
                	<div class="col-md-2 col-sm-4">
                	<input type="button" value="Submit" class="btn  btn-primary showatt" onclick="getreports();">
                	</div>
                	<div class="col-md-4"></div>
                	
                	</div>  
                	
                	
                	
                	<br/>
                	<div class="">
                		<div class="col-md-12">
		                	<div id="attendeecontent" style="display: none">
								<jsp:include page="accountattendeereports.jsp"></jsp:include>
							</div>
							<div id="registrationcontent" style="display: none">
								<jsp:include page="accounttransactionreports.jsp"></jsp:include>
							</div>
						</div>						
					</div>
					<s:set name="reptype" value="reportsData.reportType" />
					
             </form>  	
      </div> <!-- col-md-12 -->
          </div><!-- row -->

<div class="row" id="accountattendeerep" style="display:none;">
	<div class="col-md-12">
		<div id="attreportcontent" class="loadingbar"></div>
	</div>
</div>    
<br>    <br><br>  
          
     <div class="row" id="accountsalesrep" style="display:none;">
		<div class="col-md-12">
			<div id="tranreportcontent"  class="loadingbar"></div>
		</div>
	</div>     <br>    <br><br>
          
</body>
<script>

$(function(){
var resultjson=${eventList};
fillallevents(resultjson);
	});



function getreports()
{
	var radioattchecked=document.getElementById('reportsattradio').checked;
	if(radioattchecked){
		$( "#attendeereportsfilters" ).hide('slow');
		$( "#attendeeReportsDisplayFields" ).hide('slow');
		showAttendeeResults();
	}
	else{
		$( "#salesreportfilter" ).hide('slow');
		$( "#salesreportdisplayfields" ).hide('slow');
		showTransResults();
	}
	}


$( "#filter" ).click(function() {
	var radioattchecked=document.getElementById('reportsattradio').checked;
	if(radioattchecked){
	  $( "#attendeereportsfilters" ).toggle( "slow", function(){});
	}
	else
		{
		 $( "#salesreportfilter" ).toggle( "slow", function(){});
		}
	});

$( "#fields" ).click(function() {
	var radioattchecked=document.getElementById('reportsattradio').checked;
	if(radioattchecked){
	  $( "#attendeeReportsDisplayFields" ).toggle( "slow", function(){});
	}
	else
		{
		 $( "#salesreportdisplayfields" ).toggle( "slow", function(){});
		}
	});



/* function getFilter()
	{
	var radioattchecked=document.getElementById('reportsattradio').checked;
	if(radioattchecked)
		document.getElementById("attendeereportsfilters").style.display='block';
	else
		document.getElementById("salesreportfilter").style.display='block';
	} 
	
	function getDisplayFields()
	{
		var radioattchecked=document.getElementById('reportsattradio').checked;
		if(radioattchecked)
			document.getElementById("attendeeReportsDisplayFields").style.display='block';
		else
			document.getElementById("salesreportdisplayfields").style.display='block';
	}*/



$('input.reports').change(function() {
	var selectedreport=$(this).attr('id');
	
    $(".optionselectedtype").each(function() {
        $(this).removeClass('active'); 
        $(this).removeClass('btn-active'); 
        $(this).removeClass('btn-default'); 
    });
	if(selectedreport=='reportsattradio'){
		$('#attendeetype').addClass('btn-default');
        $('#salestype').addClass('btn-active');
        
        $('#accountattendeerep').show();
        $('#accountsalesrep').hide();
        
        document.getElementById("reportstrnsradio").checked = false;
        document.getElementById("reportsattradio").checked = true;
        getfields('in attendee');
	}else{
		$('#salestype').addClass('btn-default');
        $('#attendeetype').addClass('btn-active');
        
        $('#accountattendeerep').hide();
        $('#accountsalesrep').show();
        
        document.getElementById("reportsattradio").checked = false;
        document.getElementById("reportstrnsradio").checked = true;
        getfields('registration');
	}
});


$('input.datesel').change(function() {
	var selectedRadio=$(this).attr('id');
    $(".optiontype").each(function() {
        $(this).removeClass('active'); 
        $(this).removeClass('btn-active'); 
        $(this).removeClass('btn-default'); 
    });
	if(selectedRadio=='daterange'){
		$('#daterangelbl').addClass(' btn-default');
		//$('#daterangelbl').addClass('active');
        $('#alldateslbl').addClass('btn-active');
        $('#datepicker').show();
	}else{
		$('#alldateslbl').addClass('btn-default');
		//$('#alldateslbl').addClass('active');
        $('#daterangelbl').addClass('btn-active');
        $('#datepicker').hide();
        displayAllEvents();
	}
});



function showAttendeeResults(){
	 $('#attendeetid').iCheck('check');
formaction('attendee');
}



    $('#select').click(function(event) {  //on click
        $('.allevtchk').iCheck('check');
    });
    
     $('#clear').click(function(event) { 
     	 $('.allevtchk').iCheck('uncheck')  ;    
    });

$(function(){
 $('input.attendee').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
  $('input.sales').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
   $('input.eventslist').iCheck({  
 checkboxClass: 'icheckbox_square-grey', 
 radioClass: 'iradio_square-grey'});
   
 $('input.attendee').on('ifChecked', function(event){
	 $('#accountattendeerep').show();
	 $('#accountsalesrep').hide();
	 		$('#accounterrormsg').hide();
      		$('#registrationcontent').fadeOut(200);
   		    $('#attendeecontent').delay(200).fadeIn();    		 
    });
     $('input.sales').on('ifChecked', function(event){
    	 $('#accountattendeerep').hide();
    	 $('#accountsalesrep').show();
    	 	$('#accounterrormsg').hide();
      		$('#attendeecontent').fadeOut(200);
   		    $('#registrationcontent').delay(200).fadeIn();   		    
    });
    
    
         $('#from').datetimepicker({
         format:'m/d/Y',
        timepicker:false,
        step:5
        
    });
    
    $('#to').datetimepicker({
         format:'m/d/Y',
        timepicker:false,
        step:5
    });
 
 });
 
 
 
 
getfields();
</script>
