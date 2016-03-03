<%@taglib uri="/struts-tags" prefix="s"%>


 <script type="text/javascript" src="../js/home_myevents.js"></script>


<style>

.eventsdata{
 #background: #F1F1F2;
  position:absolute;   
   # top:0;
   # background-color:rgba(255, 255, 255, 0.5);
    width: 100%;
   z-index:  800; 
}

#tablevent1_filter{
	margin-right:22px
}
</style>
 <s:set name="actionName" value="menuMap[#attr['currentaction']]"/>
<div class="container">
<div class="row">
<div class="col-md-7">
<div class="row">
<div style="padding:15px;background:#EEEEEE;" class="col-md-11">
<div class="panel panel-default"  style="padding:12px;height: 520px;">                        
                        <div class="panel-body">                            
                            <!-- tabs -->                          
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#tabevent1" data-toggle="tab">Active (<label class='tabevent1Count'>0</label>)</a></li>
                                <li><a href="#tabevent2" data-toggle="tab">Closed (<label class='tabevent2Count'>0</label>)</a></li>
                                <li><a href="#tabevent3" data-toggle="tab">Suspended (<label class='tabevent3Count'>0</label>)</a></li>                                
                                <li><a href="../myevents/home"> <i class="fa fa-home"></i></a></li>
                                
                            </ul>      
                            </div>
                            <!-- tab sections -->
                            <div class="tab-content">
                                <div class="tab-pane active" id="tabevent1">

                                    <table class="table" id='tablevent1'>
                                        <thead>
                                            <th>Name</th>                                                                                  
                                            <th></th>
                                        </thead>
                                        <tbody>
                                         <tr class='nodata'> 
                                           <td></td>                                           
                                             <td  class="load">Loading...</td>                                             
                                             </tr>                                            
                                        </tbody>
                                    </table>
                                </div>
                                
                                <div class="tab-pane" id="tabevent2">
                                    <table class="table" id='tablevent2'>
                                        <thead>
                                            <th>Name</th>                                                                                  
                                            <th></th>
                                        </thead>
                                        <tbody>   <tr class='nodata'>   
                                          <td></td>                                         
                                             <td class="load">Loading...</td> 
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                
                                <div class="tab-pane" id="tabevent3">
                                   <table class="table" id='tablevent3'>
                                        <thead>
                                            <th>Name</th>                                                                              
                                            <th></th>
                                        </thead>
                                        <tbody>                                           
                                           <tr class='nodata'> 
                                           <td></td>                                       
                                             <td class="load">Loading...</td>                                              
                                             </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        
                        </div>                        
                    </div>
		</div>	
</div>
</div>
</div>
</div>
<s:if test="%{#actionName=='Reports' || #actionName=='TrackURL'}">
<script type="text/javascript" language="javascript" src="/main/js/reportsDatatable.js"></script>
<link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.0/css/jquery.dataTables.css" />
<script>
var pagination = 'simple_numbers';
</script>
</s:if>
<s:else>
<script type="text/javascript" language="javascript" src="/main/js/dataTables.js"></script>
<script>
var pagination = 'full_numbers';
</script>
</s:else>
  
<script type="text/javascript" charset="utf-8">
function getEventsData(){
var eventsjson = data;

                       	
            	$.each(eventsjson,function(outkey,resObj){	
           		 var tab=outkey=="active"?"tabevent1":outkey=="closed"?"tabevent2":"tabevent3";
           		 $('.'+tab+'Count').text(resObj.length);	
           		 if(resObj.length==0)$('#'+tab+' .table .load').html('No Data');
           		$.each( resObj, function( inkey, valueobj ) {           			
           			var tempentry=$('<tr><td style="max-width: 100px;">'+valueobj.title+'</td><td style="text-align:center"><button class="btn btn-warning eventmng" data-eventid="'+valueobj.id+'"  data-mgrid="'+valueobj.mgrid+'">Manage</button></td></tr>');			
                      $('#'+tab+' .table .nodata').remove();
           			$('#'+tab+' .table').append(tempentry);
           			
           		});
           	});
           	$('.dataTables_filter input').attr('class','form-control');
           	
            	if(eventsjson.active.length>0){
             $('#tablevent1').dataTable( {
                    "sPaginationType": pagination,
                    "iDisplayLength":5,
                    "bLengthChange": false,
                    "aoColumns": [null,                                
                                  { "bSortable": false }
                                ] ,
                                aaSorting: [],                                 
                                "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');}
                } );            
            	}
            	
            	if(eventsjson.closed.length>0){
                  $('#tablevent2').dataTable( {
                    "sPaginationType": pagination,
                    "iDisplayLength":5,
                    "bLengthChange": false,
                    "aoColumns": [null,                                  
                                  { "bSortable": false }
                                ] ,
                                aaSorting: [],
                    "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');}                    
                } );            	
            	}
            	
            	if(eventsjson.suspended.length>0){
            		$('#tablevent3').dataTable( {
                        "sPaginationType": pagination,
                        "iDisplayLength":5,
                        "bLengthChange": false,
                        "aoColumns": [null,                                     
                                      { "bSortable": false }
                                    ] ,
                                    aaSorting: [],
                        "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');}
                    } );
               	}
          
}           	
</script>   
<s:if test="%{#actionName=='Reports'}">         	
<style>
 th, td { white-space: nowrap; }
   div.dataTables_wrapper {    
       margin: 0 auto;
        
   }
   table.dataTable {
 border-collapse: collapse;
}
table.dataTable.stripe tbody tr.odd, table.dataTable.display tbody tr.odd {
   background-color: #e2e4ff;
}
  .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
  color: white !important;
  border: 1px solid #FFFFFF;
  
background: rgba(73,155,234,1);
background: -moz-linear-gradient(top, rgba(73,155,234,1) 0%, rgba(32,124,229,1) 69%, rgba(32,124,229,1) 84%);
background: -webkit-gradient(left top, left bottom, color-stop(0%, rgba(73,155,234,1)), color-stop(69%, rgba(32,124,229,1)), color-stop(84%, rgba(32,124,229,1)));
background: -webkit-linear-gradient(top, rgba(73,155,234,1) 0%, rgba(32,124,229,1) 69%, rgba(32,124,229,1) 84%);
background: -o-linear-gradient(top, rgba(73,155,234,1) 0%, rgba(32,124,229,1) 69%, rgba(32,124,229,1) 84%);
background: -ms-linear-gradient(top, rgba(73,155,234,1) 0%, rgba(32,124,229,1) 69%, rgba(32,124,229,1) 84%);
background: linear-gradient(to bottom, rgba(73,155,234,1) 0%, rgba(32,124,229,1) 69%, rgba(32,124,229,1) 84%);
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#499bea', endColorstr='#207ce5', GradientType=0 );
}
</style>
</s:if>
<s:else>
 <style type="text/css" title="currentStyle">
					            @import "//www.eventbee.com/home/css/blockManage/demo_page.css";
					            @import "//www.eventbee.com/home/css/blockManage/demo_table.css";
					             .dataTables_filter input { 
					            @import '.form-control'; 
					            }
					           
					        </style>
</s:else>