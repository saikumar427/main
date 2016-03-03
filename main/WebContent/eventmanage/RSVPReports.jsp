<%@taglib uri="/struts-tags" prefix="s"%>
<script>
var eid='${eid}';
var recattndjson = ${recurringAttendeeCountJson};
</script>
<style>
table tr.nowell td{
border-top:0px !important;
}
</style>
<script type="text/javascript" src="../js/eventmanage/rsvpreports.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>
<script type="text/javascript" src="../js/eventmanage/rsvptransactiondetails.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>
<link type="text/css" rel="stylesheet" href="../css/reportscustom.css" />
<script type="text/javascript">
function dashboard(id){
	if(id=='rsvpAttendeeRepTab'){
		$("#rsvpAttendeeRepBlock").show();
		$("#rsvpCheckInRepBlock").hide();
		$('#rsvpresponsecontentreports').show();
		$('#rsvpcheckinreport').hide();
		reptype='rsvpresponsecontentreports';
	}else{
		$("#rsvpCheckInRepBlock").show();
		$("#rsvpAttendeeRepBlock").hide();
		$('#rsvpresponsecontentreports').hide();
		$('#rsvpcheckinreport').show();
		reptype='rsvpcheckinreport';
	}
}
</script>
 <div class="row sticky-out-button">				
   <div class="col-md-12">
    <div class="pull-right">
    
    <div style="position: relative;" id="att_rep_genbtn">
    	<input type="button" class="btn btn-primary" id="rsvpresponsesubmitbtn" value="<s:text name='global.generate.btn.lbl'/>" />
    </div>
    <div style="display:none;position: relative;" id="check_rep_genbtn" >
   		<input type="button" class="btn btn-primary" id="rsvpcheckinsubmitbtn" value="<s:text name='global.generate.btn.lbl'/>" />
    </div>
    </div>                            
    </div>
    <div style="clear:both"></div>
</div>
<div class="white-box no-button">
<div class="row">
  <div class="col-md-12">
<s:form method="post" name="rsvpreportsform" action="RSVPReports!getRSVPResponseInfo" id="rsvpreportsform">
	<s:hidden name="eid" id="eid" ></s:hidden>
	<s:hidden id="currentlevel" value="%{currentLevel}"></s:hidden>
	<s:hidden name="export" value="false" id="export_id"></s:hidden>
	<s:hidden type="hidden"  id="isrecur" name="isrecurring"></s:hidden>
	<s:hidden id="rsvprepsearchid" name="rsvpReportsData.isSearch"></s:hidden>

  <s:set value="%{isrecurring}" name="isrecurring"></s:set>
  <s:if test="%{isrecurring==true}">
  
  <div class="bottom-gap">
					<div class="form-inline">
						<div class="form-group" style="margin-right:15px;">
							<label for="title" class="control-label"><s:text name='global.sel.rec.date.lbl'/></label>
						</div>
						<div class="form-group">
							<s:select cssClass="form-control" name="rsvpReportsData.date"
								id='recurringdate' headerKey="all" headerValue="All Dates"
								list="recurringDates" listKey="key" listValue="value"
								onchange="getDateCount();" />
						</div>
					</div>
				</div>
  </s:if>
  
  <div class="form-inline">
<div class="form-group" style="margin-bottom:10px !important;margin-right:10px;">
<div class="btn-group btn-group sm-font">
				<label id="att_rep" class="sel-reports-lbl btn btn-default active">
					<input class="rep-radiotype" name="reports"
					value="Attendee" type="radio" checked style="visibility: hidden !important; margin: 0 !important;">
					<span style="margin-left: -18px !important"><s:text name='rep.attendee.report.lbl'/></span>
				</label> 
				<label id="check_rep" class="sel-reports-lbl btn btn-active">
					<input class="rep-radiotype" name="reports"
					value="Check-In" type="radio" style="visibility: hidden !important; margin: 0 !important;">
					<span style="margin-left: -18px !important"><s:text name='rep.checkin.report.lbl'/></span>
				</label>
</div>
</div>

<div class="form-group" id="search_list" style="display:none;margin-right:10px;margin-bottom:10px !important;">
					<%-- <s:hidden id="trackcodessize" value="%{trackCodes.size()}"></s:hidden> --%>
						<select id="searchlist" name="rsvpReportsData.searchOn" class="form-control" onchange="searchRSVPReports();">
							<option value="transactionId"><s:text name='global.transaction.id.lbl'/></option>
							<option value="email"><s:text name='global.email.lbl'/></option>
							<option value="name"><s:text name='global.name.lbl'/></option>
							<%-- <s:if test="%{#trackurls!=0}">
								<option value="transactionSource">Transaction Source</option>
							</s:if> --%>
						</select>
</div>
<div class="form-group bottom-gap">
<div class=" dataTables_filter closeClass">
<label for="search" class="sr-only"><s:text name='global.search.lbl'/></label> 
<input type="text" class="form-control" name="rsvpReportsData.searchContent" id="search_filter" placeholder="<s:text name='global.search.placeholder.lbl'/>">	
<span class="glyphicon glyphicon-search search-result"></span>
</div>				
</div>
</div>

  <div class="">
		<div id="rsvpAttendeeRepBlock">
			<jsp:include page="rsvpresponsereports.jsp"></jsp:include>
		</div>
		<div id="rsvpCheckInRepBlock" style="display:none">
            <jsp:include page="rsvpcheckinreports.jsp"></jsp:include>
        </div>
	</div>

</s:form>

 <div class="" style="margin-bottom: 0px;">
 	<!--<div id="offsetdiv"></div>-->
	<div class="row"><br>
	<div class="col-md-12">
 	<div class="report-content" id="rsvpresponsecontentreports"></div>
	</div></div>
	<div class="row">
	<div class="col-md-12">
	<div class="report-content" id="rsvpcheckinreport"></div>
	</div>
	</div>
</div>
  </div>
  </div>
  </div>
<style type="text/css" title="currentStyle">

 .table > tbody{
  font-size:12px !important;
 }
 
/*  .table > thead > tr > th{
 	cursor: pointer;
 } */
 
@import "../bootstrap/css/demo_table.css";
.dataTables_filter input {
@import '.form-control';
}
</style>
<script>
$(document).ready(function() {
	
	$("#search_filter").keyup(function(event){
		if($.trim($('input[name="rsvpReportsData.searchContent"]').val()).length>0)
			$('#search_filter').tooltipster('hide');
		   if(event.keyCode == 13){
			   searchRSVPReports();
		   }
		});
	
	$(".dataTables_filter span").click(function(){
	  	$(".dataTables_filter").toggleClass("open closeClass");
	  	$(".dataTables_filter").find("[name='reportsData.searchContent']").focus();
	  	if($("#search_list").is(":visible")){
			$("#search_list").hide();
		}else $("#search_list").show();
	});
	
	$('input.rep-radiotype').change(function() {
		var selectedRadio=$(this).val();
	    $(".sel-reports-lbl").each(function() {
	        $(this).removeClass('active'); 
	        $(this).removeClass('btn-active'); 
	        $(this).removeClass('btn-default'); 
	    });
		if(selectedRadio=='Attendee'){
			$('#att_rep').addClass('btn-default').addClass('active');
	        $('#check_rep').addClass('btn-active');
	        dashboard('rsvpAttendeeRepTab');
	        $('#att_rep_genbtn').show();
			$('#check_rep_genbtn').hide();
		}else{
			$('#att_rep').addClass('btn-active');
	        $('#check_rep').addClass('btn-default').addClass('active');
	        dashboard('rsvpCheckInRepTab');
	        $('#check_rep_genbtn').show();
			$('#att_rep_genbtn').hide();
		}
	});
        
	$('[data-toggle=offcanvas]').click(function() {
		$('.row-offcanvas').toggleClass('active');
	});
	
	$('[data-type=date]').datetimepicker({
		format: 'm/d/Y',
		timepicker: false
	});
	   
	$('input[name="rsvpReportsData.attselindex"]').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'
	});
	   
	$('input[name="rsvpReportsData.attendeeType"]').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'
	});
	
	$('input[name="rsvpReportsData.bookingSourceType"]').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'
	});
	
	$('input[name="rsvpReportsData.checkinreportsindex"]').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'
	});
	
	$('input[name="checkinFields"]').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'
	});
	
	$('input[name="resFields"]').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'
	});

});
      
</script>
