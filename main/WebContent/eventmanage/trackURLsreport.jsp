<%@taglib uri="/struts-tags" prefix="s" %>
<style>
#tabl1_paginate{
	  margin-top: 10px;
}
</style>

<script>
function getRecurringTrackReport(){
	removeHiddenEle();
	document.recurringTrackURL.submit();
}
</script>

<form name="tempform" action="TrackUrlManage!getManagePage" id="tempform" method="get">
<s:hidden name="eid"></s:hidden>
<s:hidden name="trackcode"></s:hidden>
<s:hidden name="secretcode"></s:hidden>
</form>

<div  class="col-md-12">
<div class="row">
<div class="pull-left">
<a href="<s:property value='TrackURLData.eventURL' /><s:property value='trackcode' />" target="_blank"><s:property value='TrackURLData.eventURL' /><s:property value='trackcode' /></a>
</div>
<div class="pull-right">
 <a href="IntegrationLinks?eid=${eid}"><s:text name="turl.back.to.all.turl.lnk.lbl"/></a><br>
 <a href="IntegrationLinks!manageTrackURL?eid=${eid}&trackcode=${trackcode}&scode=${scode}" ><s:text name="turl.back.to.manage.lnk.lbl"/></a>
</div>
</div>
<br>
<div class="row">
<div class="col-md-12">
<s:form id="recurringTrackURL" name="recurringTrackURL" action="IntegrationLinks!trackURLReport" method="get">
<s:hidden name="eid"></s:hidden>
<s:hidden name="powertype" id="powertype"></s:hidden>
<s:hidden name="trackcode"></s:hidden>
<s:hidden name="secretcode"></s:hidden>
<s:hidden name="isrecurring" id="isrecurring"></s:hidden>
<s:set value="%{isrecurring}" name="isrecurring"></s:set>
<s:if test="%{isrecurring==true}">
<s:set value="%{dates.size()}" name="datesize"></s:set>
<s:if test="%{#datesize!=0}">
<div class="col-md-2 col-sm-3 col-xs-6 row">
<s:text name="turl.event.date.lbl"/></div>
<div class="col-md-10 col-sm-9 col-xs-6 row">  <s:select name="TrackURLData.date" class="form-control" id='source' headerKey="1"
    		headerValue="-- Select Date --" 
    		list="dates" listKey="key" listValue="value" onchange="getRecurringTrackReport()" /></div>
    		<br/><br/>
</s:if></s:if>
<s:else>
</s:else>
</s:form>

<div class="tab-content">
<div class="tab-pane active" id="tab1">
<div class="table-responsive">
<table class="table table-hover" id='tabl1'>
<thead>
<tr id="thead">
</tr>
</thead>
<tbody id="tbody">
<tr class='nodata'> 
<td colspan='2' ><s:text name="turl.noreport.msg"/></td> 
<td></td>                                           

<td></td>    
<td></td>    
<td></td>    
<td></td>    
<td></td>    
<td></td>    
<td></td>    
</tr>                                            
</tbody>
</table>
</div>
<div id="trackcodeexport" style="    margin-top: 30px;"></div>
</div>
</div>
</div></div>
</div>
<script>
var abc='';
var visitcount='${visitcount}';
 var msg='${msg}';
 var jdata=${msg};
 // alert("the json data is::"+JSON.stringify(jdata));
 if(visitcount==null || visitcount=='undefined')
	 vistitcount=0;
 $('#visits').html("Visits&nbsp;&nbsp;&nbsp;"+visitcount);
 var mul = 15*jdata.fields.length;
for(var i=0;i<jdata.fields.length;i++){
	$("#thead").append("<th><div style='width:"+mul+"px;'>"+jdata.fields[i]+"</div></th>");
}
	 $.each(jdata.TrackCodeReportMap,function(inkey,valueobj){ 
	var tempentry='<tr>';
		 for(var i=0;i<jdata.fields.length;i++){  
		   if(valueobj[jdata.fields[i]]==undefined){
			   valueobj[jdata.fields[i]]=" ";
		   }
		    tempentry+='<td>'+valueobj[jdata.fields[i]]+'</td>';  
		   }  
		 tempentry+="</tr>";    
         $(' .table .nodata').remove();
          $('#tabl1').append(tempentry);		 
	 });


	 if(jdata.TrackCodeReportMap.length!=0){
	      document.getElementById("trackcodeexport").innerHTML=props.global_export_to_lbl+": <a href='javascript:reportexport(\"excel\")'>"+props.global_excel_lbl+"</a>&nbsp;|&nbsp;<a href='javascript:reportexport(\"csv\")'>"+props.global_csv_lbl+"</a>";
	       }else{
		   document.getElementById('trackcodeexport').innerHTML='';
	       }
	 

function goBack(){
document.tempform.submit();
}


function removeHiddenEle(){
	if(document.getElementById('exportreport')){
	var a=document.getElementById('exportreport');
		if(a) document.recurringTrackURL.removeChild(a);
	}
	if(document.getElementById('sortdir')){
	var b=document.getElementById('sortdir');
		if(b) document.recurringTrackURL.removeChild(b);
	}
	if(document.getElementById('sortfield')){
	var c=document.getElementById('sortfield');
		if(c) document.recurringTrackURL.removeChild(c);
	}
	if(document.getElementById('exptyp')){
	var d=document.getElementById('exptyp');
		if(d) document.recurringTrackURL.removeChild(d);
	}
}


function reportexport(exporttype){
	var dir="asc";
	var colno=JSON.stringify((dTable.fnSettings().aaSorting[0])[0]);
	var dirtype=JSON.stringify((dTable.fnSettings().aaSorting[0])[1]);
	if(dirtype.indexOf('desc')>-1)
	dir="desc";
	var sortfield=jdata.fields[colno];
	var formele=document.recurringTrackURL;
	removeHiddenEle();
	var input=document.createElement("input");
	input.setAttribute("type","hidden");
	input.setAttribute("name","export");
	input.setAttribute("id","exportreport");
	input.setAttribute("value","true");
	formele.appendChild(input);
	var input1=document.createElement("input");
	input1.setAttribute("type","hidden");
	input1.setAttribute("name","sortDirection");
	input1.setAttribute("id","sortdir");
	input1.setAttribute("value",dir);
	formele.appendChild(input1);
	var input2=document.createElement("input");
	input2.setAttribute("type","hidden");
	input2.setAttribute("name","sortField");
	input2.setAttribute("id","sortfield");
	input2.setAttribute("value",sortfield);
	formele.appendChild(input2);
	var input3=document.createElement("input");
	input3.setAttribute("type","hidden");
	input3.setAttribute("name","exporttype");
	input3.setAttribute("id","exptyp");
	input3.setAttribute("value",exporttype);
	formele.appendChild(input3);
	formele.action="IntegrationLinks!trackURLReport";
	formele.submit();
}
</script>   
<script type="text/javascript" src="/main/js/reportsDatatable.js"></script>
<script type="text/javascript" src="/main/js/dataTables.js"></script>	
 <script>

/*  var dTable;
 $(document).ready(function() {	
     dTable= $('#tabl1').dataTable({    	
    	 "bFilter": false,
    	 "scrollX": true,
    	 "bInfo": false,
    	 "bPaginate": false,
    	 "bLengthChange": false,
    	 "aoColumns": [null,
                       null,                                 
                       null,
                       null,null,{ "bSortable": false },null,{ "bSortable": false },{ "bSortable": false }
                     ] 
               });
     dTable.fnSort( [ [0,'desc'] ] );
    });
 
 */
 var dTable;
 $(document).ready(function(){
	 var count =$("#tabl1 > tbody > tr").length;
	 
	 var powerType = $('#powertype').val();
	if('RSVP'==powerType){ 
		dTable = $('#tabl1').dataTable( {
	        "sPaginationType": "full_numbers",
	        "bFilter": false,
	        "iDisplayLength":10,
	        "scrollX": true,
	        "bLengthChange": false,
	        "aoColumns": [null,
	                       null,                                 
	                       null,
	                       null,null,{ "bSortable": false }
							]
	                    });
		}else{
			dTable = $('#tabl1').dataTable( {
		        "sPaginationType": "full_numbers",
		        "bFilter": false,
		        "iDisplayLength":10,
		        "scrollX": true,
		        "bLengthChange": false,
		        "aoColumns": [null,
		                       null,                                 
		                       null,
		                       null,null,{ "bSortable": false },null,{ "bSortable": false },{ "bSortable": false }]
		                    });
			}
	 	
	    
	            /*  "bFilter":false,
	             aaSorting: [] */
	      if(count<=10)
	    	  removePagination('tabl1');
	    
		});
		 /* if(data.citems.length<=5)
			 removePagination('trackdatatable'); */
 </script>
  
 <style type="text/css" title="currentStyle">
 @import "/main/bootstrap/css/demo_table.css";
            /* @import "//www.eventbee.com/home/css/blockManage/demo_page.css";
            @import "//www.eventbee.com/home/css/blockManage/demo_table.css"; */
             .dataTables_filter input { 
            @import '.form-control'; 
            }
           
        </style>
