<%@taglib uri="/struts-tags" prefix="s" %>
<script>
var eid = '${eid}';
</script>


<div class="alert alert-info">
	<s:text name="sid.help.msg"/>
	
</div>
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
<script type="text/javascript" src="ScanIDs!populateScanIdsList?eid=${eid}"></script>
<script type="text/javascript" language="javascript" src="/main/js/dataTables.js"></script>
<script type="text/javascript" src="/main/js/scanids.js"></script>
<script>


var json = data;

</script>
<style type="text/css" title="currentStyle">
            
            @import "/main/bootstrap/css/demo_table.css";
             .dataTables_filter input { 
            @import '.form-control'; 
            }
            .popover-content {
   color:#000;
   font-size:0.9em
}
        </style>