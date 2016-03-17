<%@taglib uri="/struts-tags" prefix="s"%>
<style>
tr.editRow:hover {
  background-color: #FFFFFF !important;
}

</style>
<s:hidden name="powertype" id="powertype"></s:hidden>
<s:hidden value="%{currentLevel}" id="currentLevel" />
<!--	<div class="alert alert-info hidden-xs">
		<i class="fa fa-info-circle"></i>&nbsp;<s:text name="sm.help.msg"/>
	</div> -->

<!-- row close -->
<!-- <h3 class="section-main-header"><s:text name="sm.section.header"/></h3> -->
<div class="row sticky-out-button pull-right">
	<button class="btn btn-primary" name="submgrid" id="submgrid"
				type="button">+ <s:text name="sm.create.sub.manager.btn.lbl"/></button>
</div>
<div class="white-box panel-body">
	<div id="reactiveload"></div>
	<div class="table-responsive">
		<table class="table table-hover" id="submanagerdata" class="submanagerdata">
		<thead style="display:none;">
					<th><s:text name="global.email.lbl"/></th>
					</thead>
			<tbody id="subBody">
				<tr class='nodata'><td class='dataMsg'><s:text name="global.loading.msg"/></td></tr>
			</tbody>
		</table>
	</div>
	<div class="col-md-12 col-xs-12 text-center">
		<div id="forload"></div>
	</div>
	<div id="mainSubMgrBox" style="display: none; margin-top: 15px;"></div>
</div>
	<%-- <div class="panel panel-primary">
		<div id="reactiveload"></div>
		<div class="panel-body">
		<div class="table-responsive">
			<table class="table table-hover" id="submnagerdata">
				<thead>
					<th><s:text name="global.email.lbl"/></th>
					<th><s:text name="global.status.lbl"/></th>
					<th></th>
				</thead>
				<tbody id="subBody">
					<tr class='nodata'>
						<td class='dataMsg'><s:text name="global.loading.msg"/></td>
						<td colspan='2'></td>
						<td></td>
					</tr>
				</tbody>
			</table>
			</div>
			<div class="col-md-12 col-xs-12 text-center">
				<div id="forload"></div>
			</div>
			
			<div id="mainSubMgrBox" style="display: none; margin-top: 15px;"></div> 
			
		</div>
		
	</div> --%>
	
<%-- <div class="box-top-gap">
<jsp:include page="/eventmanage/ScanIDs.jsp"></jsp:include>
</div>	 --%>
	
<div class="box-top-gap">
	
<s:form name="ScanIDsform" action="ScanIDs!createScanId" id="ScanIDsform">
<s:hidden name="eid"></s:hidden>
<div id="statusMsg"></div>
<div class="section-main-header"><s:text name="sid.section.header" /></div>
<div class="row sticky-out-button pull-right  box-top-gap">
	<div class="col-md-12"> 
		 <div class="pull-right">
			<button class="btn btn-primary" name="scanid"  id="scanids" type="button"><s:text name="sid.create.scanid.btn.lbl"/></button>   
		 </div> 
	 </div> 
</div>



<div class="white-box">

<div id="scandatadiv" class="bottom-gap" ></div>	
<div id="scandata_br" style="height:33px;"></div>
<div id="loading" class="box-top-gap" style="display:none;"><center><img src="../images/ajax-loader.gif"/></center></div>

<div id="loadedData"></div>

</div>                         

</s:form>
</div>

<script type="text/javascript" src="ScanIDs!populateScanIdsList?eid=${eid}"></script>
<script type="text/javascript" src="/main/js/scanids.js"></script>
<script type="text/javascript" language="javascript" src="/main/js/dataTables.js"></script>
<script type="text/javascript" language="javascript" src="/main/js/eventmanage/submanager.js"></script>
<script>

var scanIDjson = data;

var jsub = 0;
var eid = '${eid}';
var json = ${jsonData};

var oTable;
$(document).ready(function(){
 loadedData(json);	
 showHideLinks();
});


function rebuildData(message){
	
	var url='SubManager!reloadData?eid='+eid; 
		$.ajax({
			type:'POST',
			url:url,
			success:function(result){
				$('table#submanagerdata tbody').remove();
				$('#submgrid').prop("disabled",false);
				$('.editRow').remove();
				$('#submanagerdata').dataTable().fnDestroy(false);
				addData(JSON.parse(result));
				var count =$("#submanagerdata > tbody > tr").length;
				callDataTableReload();
			      if(count<=5)
			    	  removePagination('submanagerdata');
			      
			      if('Add'==message){
			    	  $('#notification-holder').html('');
						$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
						notification(props.sm_submgr_added_status_msg,"success");
						
			      }else{
			    	  $('#notification-holder').html('');
						$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
						notification(props.sm_submgr_updated_status_msg,"success");
						
			      }
			      showHideLinks();
			}
	}); 
		//$('.editRow').remove();
		
}
function addData(json){
	$('#mainSubMgrBox').html('');
	$('#subBody').remove();
	$.each(json,function(outkey,resObj){
		$.each( resObj, function( inkey, valueobj ) {
			createItem(jsub,valueobj);		
		});
	});	
	hideProcessing();
	
	}

function loadedData(json){
	
	$.each(json,function(outkey,resObj){
		$.each( resObj, function( inkey, valueobj ) {
			createItem(jsub,valueobj);		
		});
	});	
	if(json.SubManagerDetails.length>0)
		callDataTable();
	else{
		//$('#subBody').html('');
		$('.nodata .dataMsg').html('<div class="not-found"><s:text name="sm.nodata.msg" /></div>');
		//$('#submgrid').trigger('click');
	}
    }
function createItem(jsub,valueobj){
	
		++jsub;
		var namepart='';
	    if(valueobj.status=='Active')
	    	namepart +='<a  href="javascript:;" onclick=manageSubManager(\''+eid+'\',\''+valueobj.submgrid+'\',"Inactive",\''+jsub+'\');>'+props.sm_deactivate_lnk_lbl+'</a>';
	    else
	    	namepart +='<a  href="javascript:;" onclick=manageSubManager(\''+eid+'\',\''+valueobj.submgrid+'\',"Active",\''+jsub+'\');>'+props.sm_reactivate_lnk_lbl+'</a>'; 
	    	
	    	namepart +='&nbsp;&nbsp;&nbsp;<a  href="javascript:;" onclick=removeSubManager(\''+eid+'\',\''+valueobj.submgrid+'\',\''+jsub+'\');>'+props.global_delete_lnk_lbl+'</a>';
	    	namepart +='&nbsp;&nbsp;&nbsp;<a  href="javascript:;" onclick=editSubManager(\''+eid+'\',\''+valueobj.submgrid+'\'); id="disp_'+valueobj.submgrid+'">  ' +props.global_edit_lnk_lbl+ '  </a>';
	    				
	//var tempentry=$('<tr id='+valueobj.submgrid+'_row><td>'+valueobj.name+'<br><span class="sub-text">'+valueobj.email+'</span></td><td id="status_'+valueobj.submgrid+'">'+valueobj.status+'</td><td  id="manage_'+valueobj.submgrid+'"><span  id="displayManage_'+valueobj.submgrid+'" class="hideThis">'+namepart+'<span></td></tr>');
	
	var tempentry=$('<tr id='+valueobj.submgrid+'_row><td><div class="col-md-9 col-sm-9 col-xs-12 row"><div class="col-sm-6 col-md-6 col-xs-6 row">'+valueobj.name+'<br>'+
			'<span class="sub-text">'+valueobj.email+'</span></div><div class="col-sm-6 col-md-6 col-xs-6 sm-font" id="status_'+valueobj.submgrid+'">'+valueobj.status+'</div>'+
			'</div><div class="col-sm-3 col-md-3 col-xs-12 row"><div class="col-sm-12 col-md-12 col-xs-12 sm-font" id="manage_'+valueobj.submgrid+'">'+
			'<span  id="displayManage_'+valueobj.submgrid+'" class="hideThis" style="display:none;">'+namepart+'<span></div></div>'+
			'</td></tr>');
	
	$('.table .nodata').remove();
	$('#submanagerdata').append(tempentry);
	
}

function callDataTable(){	
	 $('#submanagerdata').dataTable({
	        "sPaginationType": "full_numbers",
	        "iDisplayLength":5,	        
	        "bLengthChange": false,
	        "bSort" : false,
	        "bFilter":false,
	        "aoColumns": [null],
	        "bFilter" : false   
	    });	
	 
	 if(json.SubManagerDetails.length<=5)
		 removePagination('submanagerdata');
}

function callDataTableReload(){	  
	 $('#submanagerdata').dataTable({
	        "sPaginationType": "full_numbers",
	        "iDisplayLength":5,	        
	        "bLengthChange": false,
	        "bSort" : false,
	        "bFilter":false,
	        "aoColumns": [null],
	        "bFilter" : false   
	    });	
	
}



function showHideLinks(){
	 $('.hideThis').hide();
	 $(document).on('mouseover','tr',function() {
			$(this).find('.hideThis').show();
			});
		$(document).on('mouseout','tr',function() {
			$(this).find('.hideThis').hide();
			});
}


	
</script>

<style type="text/css" title="currentStyle">
@import "/main/bootstrap/css/demo_table.css";

.dataTables_filter input {@import '.form-control';
	
}
.popover-content {
	color: #000;
	font-size: 0.9em
}
</style>
