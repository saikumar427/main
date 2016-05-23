<%@taglib uri="/struts-tags" prefix="s"%>
<script>
var eid=${eid};
var currencySymbol='${currencySymbol}';
</script>
<style>
table tr.nowell td{
border-top:0px !important;
}

</style>
<script type="text/javascript" src="../js/eventmanage/reports.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>
<script type="text/javascript" src="../js/eventmanage/transactiondetails.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>
<link type="text/css" rel="stylesheet" href="../css/reportscustom.css?id=1" />	
<script type="text/javascript">
var pajson = ${pendingApprovalJson};
var purpose='${purpose}';
</script>
 <div class="row sticky-out-button">				
   <div class="col-md-12">
    <div class="pull-right">
    
    <div style="position: relative;" id="att_rep_genbtn">
    	<input type="button" class="btn btn-primary" id="attendeesubmitbtn" value="<s:text name='global.generate.btn.lbl'/>" />
    </div>
    <div style="display:none;position: relative;" id="sales_rep_genbtn" >
    	<input  type="button" class="btn btn-primary" id="transactionsubmitbtn" value="<s:text name='global.generate.btn.lbl'/>" />
    </div>
    <div style="display:none;position: relative;" id="check_rep_genbtn" >
   		<input type="button" class="btn btn-primary" id="checkinsubmitbtn" value="<s:text name='global.generate.btn.lbl'/>" />
    </div>
    </div>                            
    </div>
    <div style="clear:both"></div>
</div> 

<div class="white-box no-button">
<div class="row">
  <div class="col-md-12">
<form method="post" action="Reports!getAttendeeInfo" name="reportsform" id="reportsform">
<s:hidden name="eid" id="eid"></s:hidden>
<s:hidden name="currencySymbol" id="currencysymbol"></s:hidden>
<s:hidden type="hidden"  id="isrecur" name="isrecurring"></s:hidden>
<s:hidden id="repsearchid" name="reportsData.isSearch"></s:hidden>
<s:set value="%{isrecurring}" name="isrecurring"></s:set>
			<s:if test="%{isrecurring==true}">
				<div class="bottom-gap">
					<div class="form-inline">
						<div class="form-group" style="margin-right:15px;">
							<label for="title" class="control-label"><s:text name='global.sel.rec.date.lbl'/></label>
						</div>
						<div class="form-group">
							<s:select cssClass="form-control" name="reportsData.date"
								id='recurringdate' headerKey="all" headerValue="%{getText('rep.all.recc.dates.lbl')}"
								list="dates" listKey="key" listValue="value"
								onchange="getDate();" />
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
				<label id="sales_rep" class="sel-reports-lbl btn btn-active">
					<input class="rep-radiotype" name="reports"
					value="Sales" type="radio" style="visibility: hidden !important; margin: 0 !important;">
					<span style="margin-left: -18px !important"><s:text name='rep.sales.report.lbl'/></span>
				</label> 
				<label id="check_rep" class="sel-reports-lbl btn btn-active">
					<input class="rep-radiotype" name="reports"
					value="Check-In" type="radio" style="visibility: hidden !important; margin: 0 !important;">
					<span style="margin-left: -18px !important"><s:text name='rep.checkin.report.lbl'/></span>
				</label>
</div>
</div>
<div class="form-group" id="search_list" style="display:none;margin-right:10px;margin-bottom:10px !important;">
<select id="searchlist" name="reportsData.searchOn" class="form-control" onchange="searchReports();">
							<option value="transactionId"><s:text name='global.transaction.id.lbl'/></option>
							<option value="orderNumber"><s:text name='global.order.number.lbl'/></option>
							<option value="email"><s:text name='global.email.lbl'/></option>
							<option value="name"><s:text name='global.name.lbl'/></option>
						</select>
</div>
<div class="form-group bottom-gap">
<div class="dataTables_filter closeClass">
<label for="search" class="sr-only"><s:text name='global.search.lbl'/></label> 
<input type="text" class="form-control" name="reportsData.searchContent" id="search_filter" placeholder="<s:text name='global.search.placeholder.lbl'/>">	
<span class="glyphicon glyphicon-search search-result"></span>
</div>	
</div>			
</div>
	
	<div id="pendingaprow" class="row bottom-gap">
						
                            <div id="showattdiv"  class="col-md-12" <s:if test="%{pendingApproval > 0 || recPendingAppSize > 0}">style="display:block;"</s:if><s:else>style="display:none;"</s:else>>
								<s:if test="%{pendingApproval == 1 && recPendingAppSize == 0}"><s:text name='rep.one.pending.approval.lbl'/></s:if>
								<s:elseif test="%{pendingApproval > 1 && recPendingAppSize == 0}"><s:property value="pendingApproval"/>&nbsp;<s:text name='rep.trans.pending.approval.lbl'/></s:elseif>
								<s:elseif test="%{pendingApproval == 0 && recPendingAppSize > 0}">
									<span id="alldatespacount">
										<s:if test="%{recPendingAppSize == 1}"><s:text name='rep.one.pending.approval.lbl'/></s:if>
										<s:elseif test="%{recPendingAppSize > 1}"><s:property value="recPendingAppSize"/>&nbsp;<s:text name='rep.trans.pending.approval.lbl'/></s:elseif>
									</span>
									<span id="attpacount"></span>
								</s:elseif>
								<input type="button" name="Submit" id="showattbtn" value="<s:text name='rep.pending.approval.show.btn.lbl'/>" class="btn btn-primary"/>
								<br/>
							</div>
					</div>
	
	<div class="">
		<div id="attendeeRepBlock">
			<jsp:include page="attendeereports.jsp"></jsp:include>
		</div>
		<div id="salesRepBlock" style="display:none">
            <jsp:include page="transactionreports.jsp"></jsp:include>
        </div>
        <div id="checkInRepBlock" style="display:none">
            <jsp:include page="checkinreports.jsp"></jsp:include>
        </div>
	</div>
</form>
 <div class="" style="margin-bottom: 0px;margin-top: -10px;">
 	<!--<div id="offsetdiv"></div>-->
	<div class="row"><br>
	<div class="col-md-12">
 	<div class="report-content" id="attreportcontent"></div>
	</div></div>
	<div class="row">
	<div class="col-md-12">
	<div class="report-content" id="tranreportcontent"></div>
	</div>
	</div>
	<div class="row">
	<div class="col-md-12">
	<div class="report-content" id="checkinreportcontent"></div>
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
 
/*   .table > thead > tr > th{
 	cursor: pointer;
 } */

@import "../bootstrap/css/demo_table.css";
.dataTables_filter input {
@import '.form-control';
}
</style>

<script>
if(purpose=='summary'){
	toggleReports('Sales');
	$('input[name="reports"][value="Sales"]').prop("checked",true);
	$('#repsearchid').val(false);
	actionType='registration';
	formaction('registration');
}


function dashboard(id){
	
	if(id=='attendeeRepTab'){
		$("#attendeeRepBlock").show();
		$("#salesRepBlock").hide();
		$("#checkInRepBlock").hide();
		$('#attreportcontent').show();
		$('#tranreportcontent').hide();
		$('#checkinreportcontent').hide();
		reptype='attreportcontent';
	}
	else if(id=='salesRepTab'){
		showSalesRep();
	}
	else{
		$("#checkInRepBlock").show();
		$("#salesRepBlock").hide();
		$("#attendeeRepBlock").hide();
		$('#attreportcontent').hide();
		$('#tranreportcontent').hide();
		$('#checkinreportcontent').show();
		reptype='checkinreportcontent';
	}
}

function showSalesRep(){
	$("#salesRepBlock").show();
	$("#attendeeRepBlock").hide();
	$("#checkInRepBlock").hide();
	$('#attreportcontent').hide();
	$('#tranreportcontent').show();
	$('#checkinreportcontent').hide();
	reptype='tranreportcontent';
}

function toggleReports(selectedRadio){
	$(".sel-reports-lbl").each(function() {
        $(this).removeClass('active'); 
        $(this).removeClass('btn-active'); 
        $(this).removeClass('btn-default'); 
    });
	if(selectedRadio=='Attendee'){
		$('#att_rep').addClass('btn-default').addClass('active');
        $('#sales_rep').addClass('btn-active');
        $('#check_rep').addClass('btn-active');
        dashboard('attendeeRepTab');
        $('#att_rep_genbtn').show();
		$('#sales_rep_genbtn').hide();
		$('#check_rep_genbtn').hide();
	}else if(selectedRadio=='Sales'){
		$('#att_rep').addClass('btn-active');
        $('#sales_rep').addClass('btn-default').addClass('active');
        $('#check_rep').addClass('btn-active');
        dashboard('salesRepTab');
        $('#sales_rep_genbtn').show();
		$('#att_rep_genbtn').hide();
		$('#check_rep_genbtn').hide();
	}else{
		$('#att_rep').addClass('btn-active');
        $('#sales_rep').addClass('btn-active');
        $('#check_rep').addClass('btn-default').addClass('active');
        dashboard('checkInRepTab');
        $('#check_rep_genbtn').show();
		$('#att_rep_genbtn').hide();
		$('#sales_rep_genbtn').hide();
	}
}

    $(document).ready(function() {
    	getDate();
    	
    	  $("#search_filter").keyup(function(event){
    		  if($.trim($('input[name="reportsData.searchContent"]').val()).length>0)
    				$('#search_filter').tooltipster('hide');
    		   if(event.keyCode == 13){
    			   searchReports();
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
    		toggleReports(selectedRadio);
    	});
    	
        $('[data-toggle=offcanvas]').click(function() {
            $('.row-offcanvas').toggleClass('active');
        });

        $('[data-type=date]').datetimepicker({
            format: 'm/d/Y',
            timepicker: false
        });

        $('#editReport').click(function() {
            $('#tabSection').slideToggle();
        });

        $('#updateReport').click(function() {
            $('#tabSection').slideUp();
        });

        $('input[name="attselindex"]').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'});

        $('input[name="selindex"]').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'});

        $('input[name="typeindex"]').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'});

        $('input[name="attFields"]').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'});
       
        $('input[name="Fields"]').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'});

        $('.srctype').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'});
        

        $('input[name="checkinreportsindex"]').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'});

        $('input[name="checkinFields"]').iCheck({  
    		checkboxClass: 'icheckbox_square-grey', 
    		radioClass: 'iradio_square-grey'});
    });
</script>



