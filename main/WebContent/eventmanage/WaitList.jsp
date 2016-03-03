<%@taglib uri="/struts-tags" prefix="s"%>
<script>
var waitlistdata = ${jsonData};
var eid=${eid};
var serveraddress = '${serverAddress}';
</script>
<style>
.dataTables_paginate{
margin-bottom:10px;
}
.control-label{
	padding-top:0px !important;
}
</style>
<div class="row sticky-out-button">				
    <div class="pull-right" style="position: relative;">
    <ul class="dropdown-menu" id="ticketTypes"></ul> 
    <button style="margin-left: 15px" class="btn btn-primary" id='waitlistticket' onclick="allTicketsClick();" value="0" >
    	<span id="ticketNm" ><s:text name='global.all.tickets.lbl'/></span>&nbsp;<span style="cursor: pointer;  margin-top: 3px;" id="toggling-menu" class="glyphicon glyphicon-menu-down"></span>
    </button>
    </div>
</div>
<div class="white-box">
			<div class="btn-group btn-group-sm sm-font bottom-gap">
				<label id="wl_status_all" class="wl-status-lbl btn btn-default active">
					<input class="wl-st-radiotype" id="tkttext" name="status"
					value="All" type="radio" checked style="visibility: hidden !important; margin: 0 !important;">
					<span style="margin-left: -18px !important"><s:text name="wl.all.lnk.lbl"/>&nbsp;</span>
				</label> 
				<label id="wl_status_wait" class="wl-status-lbl btn btn-active">
					<input class="wl-st-radiotype" id="tkttextarea" name="status"
					value="Waiting" type="radio" style="visibility: hidden !important; margin: 0 !important;">
					<span style="margin-left: -18px !important"><s:text name="wl.waiting.lnk.lbl"/>&nbsp;</span>
				</label> 
				<label id="wl_status_inpro" class="wl-status-lbl btn btn-active">
					<input class="wl-st-radiotype" id="tktselection" name="status"
					value="In Process" type="radio" style="visibility: hidden !important; margin: 0 !important;">
					<span style="margin-left: -18px !important"><s:text name="wl.in.process.lnk.lbl"/>&nbsp;</span>
				</label>
				<label id="wl_status_comp" class="wl-status-lbl btn btn-active">
					<input class="wl-st-radiotype" id="tktselection" name="status"
					value="Completed" type="radio" style="visibility: hidden !important; margin: 0 !important;">
					<span style="margin-left: -18px !important"><s:text name="wl.completed.lnk.lbl"/>&nbsp;</span>
				</label>
				<label id="wl_status_exp" class="wl-status-lbl btn btn-active">
					<input class="wl-st-radiotype" id="tktselection" name="status"
					value="Expired" type="radio" style="visibility: hidden !important; margin: 0 !important;">
					<span style="margin-left: -18px !important"><s:text name="wl.expired.lnk.lbl"/>&nbsp;</span>
				</label>
			</div>
			<br/>
		
		<div class="links-div bottom-gap" id="sel_clear_all">
			<span class="sm-font">
				<a href="javascript:;" id="wl_sel_all"><s:text name="global.select.all.lbl"/></a>
				<a href="javascript:;" id="wl_sel_none"><s:text name="global.clear.all.lbl"/></a>
			</span>
		</div>
		<div class="row col-md-12 table-responsive"> <!-- style="overflow-y:hidden;height:auto;" -->
		<table class="table table-hover" id="wait_list_table">
				<tbody>
					<tr class="nodata">
						<td><div class="not-found"><s:text name="wl.no.data.display.msg"/></div></td>
					</tr>
				</tbody>
		</table>
		<div style="clear:both"></div>
		</div>
		<div style="clear:both"></div>
			<div class="center" style="margin-top:10px;">
     			<span>
					<input id="mulActLink" type="button" value="<s:text name="wl.send.act.lnk.btn.lbl"/>" class="btn btn-primary hidden"/>
				</span>
   			</div>
	
</div>
<script type="text/javascript" src="/main/copy-text-to-clipboard/jquery.zclip.js"></script>
<script type="text/javascript" src="../js/eventmanage/waitlist.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>
<style type="text/css" title="currentStyle">
@import "../bootstrap/css/demo_table.css";
.dataTables_filter input {
@import '.form-control';
}

.modal-body table{
width:100% !important;
}
</style>
<script>
loadTicketTypes(waitlistdata.ticketTypes);
$("#mulActLink").click(function(e){
	if(e.handled !== true){
		sendMultipleActivationLink();
		e.handled = true;
	}
});
</script>
