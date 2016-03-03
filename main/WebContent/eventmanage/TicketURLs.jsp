<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="/main/copy-text-to-clipboard/jquery.zclip.js"></script>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@include file="../getresourcespath.jsp"%>
<script src="/main/js/eventmanage/ticketurls.js"></script>
<s:set name="ticketurls" value="%{submgr_permissions['TicketURLs']}"></s:set>
<style>
tr.editTktRow:hover{
	background-color: transparent !important;
}
</style>
<div id="privateStatusMsg"></div>
<div id="subManagerTicketUrls">
	<%-- <div class="section-main-header"> <s:text name="mt.prt.tkt.url.lbl"/>&nbsp;
		<i id="ticketurlinfo" style="cursor: pointer" class="fa fa-info-circle info tooltipstered" ></i>
	</div> --%>
	<div class="row sticky-out-button pull-right">
		<div class="col-md-12"> 
			 <div class="pull-right">
				<button class="btn btn-primary" id="createprivateurls" title="<s:text name="up.pro.ticketing.lbl"/>"> <s:text name="mt.url.btn.lbl"/> </button>
			 </div> 
		 </div> 
		<div style="clear:both"></div>
	</div>

	<div  class="white-box no-button">
		<div id="ticketurls"></div>
		<div id="ticketurlsData_br" style="height:15px;"></div>
		<div id="ticketurlsloading"></div>
		<div id="ticketurlsbox" class="row" style="padding:0px 20px"></div>
	</div>
</div>
<script>
$(document).ready(function(){
	$('#ticketurlinfo').attr('title',props.mt_pvt_urls_msgs);
});
$('#ticketurlinfo').tooltipster({fixedWidth:'100px',position: 'right'});
var data = ${jsonData};
var subMgr='${subMgr}';
var eid = '${eid}';
console.log(subMgr);
var subTicketUrls='${ticketurls}';
if(subMgr==null || subMgr==undefined || subMgr==''){
	onLoadTicketURLs();
	$('#subManagerTicketUrls').show();
}else{
	if(subTicketUrls=='yes'){
		$('#subManagerTicketUrls').show();
		onLoadTicketURLs();
	}else
		$('#subManagerTicketUrls').hide();
}

function onLoadTicketURLs(){
	if(data.citems.length<=0){
		//var htmldata='<table class="table table-responsive table-hover" id="ticketurlsData"><tr class="nodata"><td><div class="row"><div class="col-md-2"></div><div class="col-md-4">No private tickets page URLs to display</div></div></td></tr></table>';
		var htmldata='<div class="not-found" id="noTicketsDiv" style="">'+props.ptu_no_urls_to_diplay_msg+'</div>';
		$('#ticketurls').html(htmldata);
		$('#ticketurlsData_br').remove();
	}else{
		buildPrivateTicketURLData();
		callDataTable();
	}
}


</script>