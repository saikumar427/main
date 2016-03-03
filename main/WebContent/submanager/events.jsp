<%@taglib uri="/struts-tags" prefix="s"%>
  <style>

.dataTables_filter {
	float: left !important;
	text-align: right;
}
.dataTables_filter,.closeClass {
  transition: all 0.35s, border-radius 0s;
  width: 34px;
  height: 34px;
  background-color: #fff;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
  border-radius: 0px;
  border: 1px solid #ccc;
  cursor:pointer !important;
  
}

.searchResponsive{
        margin-top: -42px;
		margin-left: 215px;
}

@media ( max-width : 768px) {
	.searchResponsive {
		 margin-top: -42px;
		margin-left: 127px;
	}
}
.open
 {
  width: 35%;
  border-radius: 0px;
}
.dataTables_filter span.search-result {
   display: block;
    font-size: 16px;
    height: 34px;
    left: 215px !important;
    line-height: 34px;
    position: absolute;
    text-align: center;
    top: -42px !important;
    width: 34px;
    z-index: 2;
    color:#ccc;
  
}
@media ( max-width : 768px) {
.dataTables_filter span.search-result {
 display: block;
    font-size: 16px;
    height: 34px;
    left: 127px !important;
    line-height: 34px;
    position: absolute;
    text-align: center;
    top: -42px !important;
    width: 34px;
    z-index: 2;
}
}


  
  </style>
  
  
     <div class="row" >
      <div class="col-md-12"> 
      <div class="pull-right"><a id="changepwd" style="cursor: pointer" ><s:text name="la.sub.mgr.change.password"/></a></div>
          <div style="clear:both"></div>
          <br/>
         <div class="panel-default"> 
          
                        <div>                           
                            <!-- tab sections -->
                            <div class="tab-content">                           
                               <div class="row">
	                            <div class="col-md-4 col-sm-4">
	                               <div class="tab-bottom-gap">
										<div class="center btn-group btn-toggle" data-toggle="buttons">
												<label id="activeLabel" class="optiontype btn btn-default no-radius">
													<input class="datesel" id="active" name="events" value="1" type="radio"><s:text name="mes.event.active.lbl"/>
												</label>
												<label id="allLabel" class="optiontype btn btn-active no-radius">
													<input class="datesel" id="all" name="events" value="2" type="radio"><s:text name="mes.event.all.lbl"/>
												</label>
										</div>
								   </div>
	                             </div>
                             </div>
                                <div class="tab-pane active" id="tab1" >
                                    <table class="table table-hover" id='tabl1'>
                                      
                                        <tbody>
                                         <tr class='nodata'> 
                                           <td></td>                                           
                                             <td colspan='2' ><s:text name="global.loading.msg"/></td> 
                                             </tr>                                            
                                        </tbody>
                                    </table>
                                     <br id="tabl1_br"/>
                                </div>
                                <div class="tab-pane" id="tab2">
                                    <table class="table table-hover" id='tabl2'>
                                    
                                        <tbody>   <tr class='nodata'>   
                                          <td></td>                                         
                                             <td colspan='2' ><s:text name="global.loading.msg"/></td> 
                                             </tr>
                                        </tbody>
                                    </table>
                                    
                                    <br id="tabl2_br"/>
                                </div>
                             
                            </div>
                        </div>
                    </div>
     </div>
     </div>
     <script type="text/javascript" src="events!populateEventsList"></script>
     <script src="/main/bootstrap/js/custom.js""></script>
     <script src="/main/js/events.js"></script>
     <%-- <script type="text/javascript" language="javascript" src="//www.eventbee.com/home/js/blockManage/jquery.dataTables.js"></script> --%>
     <script type="text/javascript" language="javascript" src="/main/js/dataTables.js"></script>
	<script type="text/javascript" charset="utf-8">
            $(document).ready(function() {

            	$("#allLabel").click(function(){
            		$("#tab2").show();
            		$("#tab1").hide();
            		$("#allLabel").addClass("btn-default").removeClass("btn-active");
            		$("#activeLabel").removeClass("btn-default").addClass("btn-active");
            	 });
 					$("#activeLabel").click(function(){ 	
 	            		$("#tab1").show();
 	            		$("#tab2").hide();
 	            		$("#activeLabel").addClass("btn-default").removeClass("btn-active");
 	            		$("#allLabel").removeClass("btn-default").addClass("btn-active");
            	 });
			
            
            	$('#changepwd').click(function(){
           		 $('.modal-title').html(props.la_sub_mgr_change_password);
           		 //  $('#myModal').on('show.bs.modal', function () {
           		   $('iframe#popup').attr("src",'changepassword!subMgrChangepwd?t=a');
           		   $('iframe#popup').css("height","300px"); 
           		//   });
           		   $('#myModal').modal('show');
           		}); 
            } );
            
            function submanagerLogout(){
            	$.ajax( {
               	   type: "POST",
               	   url: '/main/submanager/logout',
               	   success: function( id ) {
               	   }
            	   });
            	$('#myModal .close').bind('click',function(){
            		window.location.href="/main/submanager/logout";
            	});
            }
        </script>
         <style type="text/css" title="currentStyle">      		        
           @import "";
            @import "/main/bootstrap/css/demo_table.css";
             .dataTables_filter input { 
            @import '.form-control'; 
            }           
        </style>