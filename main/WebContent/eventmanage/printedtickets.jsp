<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" language="JavaScript" src="../js/eventmanage/printedtickets.js"></script>
<style>
    #layoutpopup{
    background-color: rgb(237, 245, 255);border-color: #c0c0c0;
    border-style: solid;
    border-width: 0px 1px 0px 1px;
    }
    #innerlayoutpopup{
    padding:0px 10px 10px 10px;
    }
    
    .yui-content{
    border-bottom-width: 0px!important;
    }
    
    .boxfooter {
    border: 1px solid #c0c0c0;
    }
    
    .panel-footer{
    padding: 26px 15px !important;
    }
    
    .panel-footer-link {
    margin-top: -7px !important;
}
    
</style>


<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title"><s:text name="pt.section.header"/></h3>
				</div>
				<div class="panel-body" style="padding-left:5px;">
				
				
				<s:if test="%{isrecur==true}">
				
				 <div class="row form-horizontal"><div class="col-md-2 col-sm-2 control-label "><s:text name="pt.for.date.lbl"/></div>
				     <div class="col-md-4">
					<s:select name="selecteddate" id="selecteddate" list="datesList"  listKey="key" listValue="value" onchange="generateTickets();" cssClass="form-control"></s:select>
					</div>
					
					</div> 
					<br/>
				                 </s:if>
				                 
				
				<div class="col-xs-12 col-sm-12">
                    <div class="row">
                        <div class="col-md-12">      
                        
                             
                        <div id="printedtickets" style="display:block;"> </div>              
                          
                        
                        
                        
                        
                        
                        <%-- <div id="recurcontent" style="<s:if test="%{isrecur==true}">display:none;</s:if><s:else>>display:block;</s:else>">
                        
                         <ul class="nav nav-tabs">
                                <li class="active"><a href="#tab1" data-toggle="tab">Not Sold (<label class='tab1Count'>0</label>)</a></li>
                                <li><a href="#tab2" data-toggle="tab">Sold (<label class='tab2Count'>0</label>)</a></li>
                            </ul>
                        
                        
                        <div class="tab-content" id="tabcontent"> 
                                <div class="tab-pane active table-responsive" id="tab1">
                                    <table class="table table-hover" id='tabl1' width="100%">
                                        <thead id="notSoldHead" >
                                            <th></th>
                                            <th>Ticket Name</th>                                            
                                            <th>Serial No</th>
                                             <th>Agent Code</th>
                                        </thead>
                                        <tbody id="tab1body">
                                         <tr class='nodata' id="tab1nodata"> 
                                             <td></td>    
                                             <td></td>                                      
                                             <td>No Data</td> 
                                             <td></td>
                                             </tr>                                            
                                        </tbody>
                                    </table>

                                </div>
                                <div class="tab-pane table-responsive" id="tab2">
                                    <table class="table table-hover" id='tabl2' width="100%">
                                        <thead id="soldHead" >
                                            <th></th>
                                            <th>Ticket Name</th>                                            
                                            <th>Serial No</th>
                                             <th>Agent Code</th>
                                        </thead>
                                        <tbody id="tab2body">   
                                        <tr class='nodata' id="tab2nodata">   
                                          <td></td>    
                                          <td></td>                                      
                                             <td colspan='2'>No Data</td> 
                                             <td></td>
                                             </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            </div> --%>
                            
                            <div id="loadingimage" style="display:none;"><center><img src="../images/ajax-loader.gif"/></center></div>
                           <div id="dataDiv" class="table-responsive" style="display:block;"></div>               <!-- discount settings -->
                        </div>
                        
                    </div>
                </div>
  				</div>
  				
  				<div class="panel-footer">
  				<%-- <span class="buttons-div">
			<button class="btn btn-primary" id="markassold">Mark As Sold</button>
			<button class="btn btn-primary" id="delete">Delete</button>
			 </span>  --%>
		
			<span class="pull-right panel-footer-link">
			<a href="javascript:;" id="createprintedtickets" style="a:{text-decoration:underline}" ><s:text name="pt.generate.tickets.lnk.lbl"/></a>
			</span> 
  				</div></div>

<script>
var eid='${eid}';
var dataarray='';
var jsondata=${jsonData};
var isrecur=${isrecur};





 if(isrecur==true){
//document.getElementById('printedtkttable').style.display='none';
}else
	generatePrintedTicketsTable(jsondata);

 
/* var download='${download}';
if(download=='yes')
	window.location.href='PrintedTickets!downloadPdf?eid='+eid;  */


</script>
