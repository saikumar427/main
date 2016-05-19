<%@page import="com.event.helpers.I18n"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<% String lang=I18n.getHyphenSessionLang(); %>
<html>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
 <script type="text/javascript" src="/main/js/creditcardpopupscreen.js"></script>
<script type="text/javascript" src="eventslist!populateEventsList"></script>
<s:set name="lasteventExists" value="eventsSummary['LAST_EVENT_EXISTS']"></s:set>
<s:set name="lastEventChartExists" value="eventsSummary['LAST_EVENT_CHART_EXISTS']"></s:set>
<s:set name="recentClosedEventExists"
	value="eventsSummary['RECENT_CLOSED_EVENT_EXISTS']"></s:set>
<s:set name="recentClosedEventChartExists"
	value="eventsSummary['RECENT_CLOSED_EVENT_CHART_EXISTS']"></s:set>	
<s:set name="I18N_ACTUAL_CODE" value="I18N_ACTUAL_CODE"></s:set>
	
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
.open
 {
  width: 35%;
  border-radius: 0px;
}

.dataTables_filter span.search-result {
  position: absolute;
  top: -41px;
  color: #ccc;
  #left: 125px;
  left: 250px;
  z-index: 2;
  display: block;
  width: 34px;	
  height: 34px;
  line-height: 34px;
  text-align: center;
  font-size: 16px;
  
}
.section-main-header:hover{
	font-weight: 600;
}

.circle { list-style-type:disc }
	
	</style>

<body>
          <!-- container
        ===========================-->
        <!-- <div class="container" > -->
           <div class="row">
                <div class="col-md-8" style="margin-bottom:30px">             
							<div class="row sticky-out-button pull-right">
								 <div class="col-md-12"> 
									 <div class="pull-right">
										   <!--  <button style ="visibility:hidden" id="allEventReports" class="btn btn-primary" onclick="getRecords();">Event Reports</button> -->
								            <button   style="margin-left:15px" class="btn btn-primary" onclick="newEventClick()"><s:text name="mes.create.event.btn.lbl"/></button>
			                                <ul class="dropdown-menu" role="menu" aria-labelledby="eventOptions" id="eventOptions">
											  <li role="presentation"><a role="menuitem" tabindex="-1" href="#" onclick="listNewEvent();"><s:text name="mes.create.new.lbl"/></a></li>
										      <li role="presentation"><a role="menuitem" tabindex="-1" href="#" onclick="copyEvent1('')"><s:text name="mes.copy.exits.lbl"/></a></li>
											</ul>
									 </div> 
								 </div>
							</div>
                            <!-- tab sections -->
                            <div class="tab-content no-button"  >
                              
                               <div class="row">
	                            <div class="col-md-5 col-sm-4">
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
                                <div class="tab-pane active" role="tabpanel" id="tab1">

                                    <table class="table table-hover" id='tabl1' >
                                        <tbody>
                                         <tr class='nodata'> 
                                             <td  class="load"><s:text name="global.loading.msg"/></td>
                                             <td></td>              
                                              <td></td> 
                                              <td></td> 
                                             </tr>                                            
                                        </tbody>
                                        
                                    </table>    
                                     <br id="tabl1_br">                               
                                </div>
                                
                                <div class="tab-pane" id="tab2">
                                    <table class="table table-hover" id='tabl2'>
                                        <tbody>   <tr class='nodata'>                                                                                 
                                             <td class="load"><s:text name="global.loading.msg"/></td> 
                                              <td></td> 
                                              <td></td>
                                              <td></td> 
                                             </tr>
                                        </tbody>
                                    </table>
                                    <br id="tabl2_br"> 
                                </div>
                               
                            </div>
                       
                </div>
                
                              <div class="col-md-4 whats-new">
                              
                            
                              
                              
                    <div>
		                 <div><label class="icon"><span class="section-main-header" style="cursor:pointer"><s:text name="mes.whats.new.lbl"/></span > <span style="cursor:pointer" class="glyphicon glyphicon-menu-right down"></span></label></div>
		                 <div class="content" >
		                     <p>
		                     <%-- <s:text name="mes.we.exicted.lbl"/> <span> <a href="javascript:;" id="uiFeedback"><s:text name='mes.we.excited.lnk'/></a></span> --%>
		                        <s:text name="mes.we.exicted.lbl"/> <span> <a href="/main/eventbee-for-business/<s:property value="I18N_ACTUAL_CODE"/>" target="_blank"><s:text name='mes.we.excited.lnk'/></a></span>
		                     </p>
		                  		                 
		                 </div>	                
	                 </div>
	                 <hr>
	                 <div>
		                 <div><label class="icon"><span class="section-main-header" style="cursor:pointer"><s:text name="mes.howto.lbl"/></span > <span style="cursor:pointer" class="glyphicon glyphicon-menu-right original"></span></label></div>
		                  <div  class="content" style="display:none">    
		                 	
		                 	<ul>
		                 	<li class="li_disc"><a href='/main/venue-reserved-seating/<s:property value="I18N_ACTUAL_CODE"/>'><s:text name="mes.venue.seating.lbl"/></a></li>
		                 	
					        <li class="li_disc"> <a href='/main/sell-tickets-on-facebook/<s:property value="I18N_ACTUAL_CODE"/>'><s:text name="mes.sell.tickets.lbl"/></a> </li>
					         
						    <li class="li_disc"> <a href='/main/custom-online-registration-form/<s:property value="I18N_ACTUAL_CODE"/>'><s:text name="mes.custom.registration.lbl"/></a> </li>
						     
						    <li class="li_disc"> <a href='/main/social-media-event-marketing/<s:property value="I18N_ACTUAL_CODE"/>'><s:text name="mes.social.media.marketing.lbl"/>	</a> </li>
						    
						    <li class="li_disc"> <a href='/main/attendee-event-management-at-the-door/<s:property value="I18N_ACTUAL_CODE"/>'><s:text name="mes.attendee.event.mng.lbl"/>	</a> </li>
						    
						    <li class="li_disc"> <a href='/main/free-online-event-registration-software/<s:property value="I18N_ACTUAL_CODE"/>'><s:text name="mes.free.reg.lbl"/></a> </li>
						    
						    <li class="li_disc"> <a href='/main/free-event-ticketing-software/<s:property value="I18N_ACTUAL_CODE"/>'><s:text name="mes.free.tkt.lbl"/></a> </li>
						    </ul>
		                 
		                 </div>
	                 </div>
	                 <hr>
	                 <%-- <div>
		                 <div><label class="icon"><span class="section-main-header">Tutorial videos</span > <span class="glyphicon glyphicon-menu-right original"></span></label></div>
		                 <div  class="content" style="display:none">    
		                 	<p>
		                        <a href="http://www.youtube.com/user/eventbee/videos" style="word-wrap:break-word">http://www.youtube.com/user/eventbee/videos</a>
		                    </p>
		                 </div>
	                 </div>
	                 <hr> --%>
	                <div>
		                <div><label class="icon"><span class="section-main-header" style="cursor:pointer"><s:text name="mes.connect.us.lbl"/></span > <span style="cursor:pointer" class="glyphicon glyphicon-menu-right original"></span></label></div>
		                 <div class="content" style="display:none">                 		
		                     <p>
		                     <% if(lang.equals("en-us")) {%>
								<a target="new" href="http://www.facebook.com/eventbeeinc"><img border="0" src="http://www.eventbee.com/main/images/home/icon_facebook.png"> <s:text name="mes.click.to.fan.fb.lbl"/></a>
								<%} else{%>
							    	<a target="new" href="http://www.facebook.com/EventbeeSpanish"><img border="0" src="http://www.eventbee.com/main/images/home/icon_facebook.png"> <s:text name="mes.click.to.fan.fb.lbl"/></a>
							    <%}%>
							</p>
							<p>
							 <% if (lang.equals("en-us")) {%>
								<a target="new" href="http://www.twitter.com/eventbee"><img border="0" src="http://www.eventbee.com/main/images/home/icon_twitter.png"> <s:text name="mes.click.to.fan.twitter.lbl"/></a>
						    	<%}else{ %>
							     <a target="new" href="http://www.twitter.com/eventbeespanish"><img border="0" src="http://www.eventbee.com/main/images/home/icon_twitter.png"> <s:text name="mes.click.to.fan.twitter.lbl"/></a> 
							      <%} %>
							</p>
		                    <p><s:text name="mes.any.qno.ask.msg1.lbl"/> <a href="javascript:;" id="emailus"><s:text name="mes.any.qno.ask.msg2.lbl"/></a></p>
		                 </div>
	                 </div>                         
              </div>
        </div><!-- row close -->
    </body>  
    <script>
	    var mgrid='${mgrId}';
	    var eventid='${eid}';
    </script>
<script src="/main/js/home_myevents.js"></script>
<script type="text/javascript" language="javascript" src="/main/js/dataTables.js"></script>
<script type="text/javascript" charset="utf-8">

var dTable;
var ptype='';
            $(document).ready(function() {  
            	$(".icon").click(function(){
            		$(this).find(".glyphicon").toggleClass("original down");
            		$(this).parent().parent().find(".content").slideToggle();  
            	});
            	
            	
            	$("#allLabel").click(function(){
            		$("#tab2").show();
            		$("#tab1").hide();
            		$("#allLabel").addClass("btn-default").removeClass("btn-active");
            		$("#activeLabel").removeClass("btn-default").addClass("btn-active");
            		 $("#allEventReports").css('visibility','visible');
            	 });
 					$("#activeLabel").click(function(){ 	
 	            		$("#tab1").show();
 	            		$("#tab2").hide();
 	            		$("#activeLabel").addClass("btn-default").removeClass("btn-active");
 	            		$("#allLabel").removeClass("btn-default").addClass("btn-active");
 					 $("#allEventReports").css('visibility','hidden');            		 
            	 });
 					
            	
            	$.each(data,function(outkey,resObj){	
           		 var tab=outkey=="active"?"tab1":"tab2";
           		 if(resObj.length==0)$('#'+tab+' .table .load').html(props.mes_no_events_todisplay);
           		$.each( resObj, function( inkey, valueobj ) {      			
           				
           			var tempentry=$('<tr><td   class="eventmng each-row" data-eventid="'+valueobj.id+'"  data-mgrid="'+valueobj.mgrid+'"><div ><div  style="float:left;width:80%"><span style="color:#428bca">'+valueobj.title+'</span><br><span class="sub-text">'+removePresentYear(valueobj.startdate)+', '+convertTime24to12(valueobj.start_time)+'</span></div><span  class="i-right-link pull-right"><a href="javascript:;"><span class="glyphicon  glyphicon-menu-right hidden-eb" ></span></a></span><div style="clear:both"></div></div></td></tr>');
                    var tempentry2=$('<tr><td class="eventmng each-row" data-eventid="'+valueobj.id+'"  data-mgrid="'+valueobj.mgrid+'"><div ><div  style="float:left;width:80%"><span  style="color:#428bca" >'+valueobj.title+' </span><br><span class="sub-text">'+removePresentYear(valueobj.startdate)+', '+convertTime24to12(valueobj.start_time)+'</span></div><span  class="i-right-link pull-right"><span class="status">'+getStatusFormat(outkey)+'&nbsp;&nbsp;&nbsp;</span ><a href="javascript:;"><span class="glyphicon glyphicon-menu-right hidden-eb" ></span></a></span><div style="clear:both"></div></div></td></tr>');
           			$('#'+tab+' .table .nodata').remove();
                      if(tab=="tab1")    
                    	 $('#tab1 .table').append(tempentry);                    	
           		    
                     $('#tab2 .table').append(tempentry2);
                     $('#tab2 .table .nodata').remove();                   
           			
           			
           		});
           	});
            	  
            /*show arrow icon on hover*/          
            	$('#tab1 .table tr ,#tab2 .table tr ').hover(function() {
            		$(this).prev().find('td  div  .dummy-row-div').css('border-bottom','0px');
					    $(this).find('td').find('.glyphicon').addClass('visible-eb').removeClass('hidden-eb');
					},function() {
						$(this).find('td').find('.glyphicon').addClass('hidden-eb').removeClass('visible-eb');
						$(this).prev().find('td  div .dummy-row-div').css('border-bottom','1px solid #f4f4f4');
					});
            	/*show arrow icon on hover end*/	
            	
           		$('.dataTables_filter input').attr('class','form-control');
            
           	
            
        	prepareActiveEventsTable();
        	prepareAllEventsTable();
            	
            	
            	if(data.active.length<=5)
            		removePagination('tabl1');  
               	if((data.closed.length+data.suspended.length+data.active.length)<=5)
               		removePagination('tabl2'); 
               	
              
               /*remove table header because taking some height above the table*/
                $('#tab1 thead').remove();
                $('#tab2 thead').remove();

                    $('#emailus').click(function() {
                    	$('.modal-title').html(props.la_supp_popup_hdr);
                    	$('#myModal').on('show.bs.modal', function () {
                    	$('iframe#popup').attr("src",'/main/user/homepagesupportemail.jsp');
                    	$('iframe#popup').css("height","440px"); 
                    	});
                    	$('#myModal').modal('show');
                    	});
                    $("#tab1 .dataTables_filter span, #tab2  .dataTables_filter span").click(function(){
                    	  	$(".dataTables_filter").toggleClass("open closeClass");
                    	  	$(".dataTables_filter").find("[name='search']").val('').focus().keyup();
                    });
            });

           /*  google.load("visualization", "1", {packages:["corechart"]});
            google.setOnLoadCallback(drawPieCharts); */              
                      
            function listNewEvent(){            	
            	parent.loadingPopup();
            	window.location.href='../myevents/createevent';
            }
          function newEventClick(){
        		if((data.closed.length+data.suspended.length+data.active.length)<=0){
        			listNewEvent();
        		}
        		else{
	        	  if($("#eventOptions").is(':hidden'))
	        	 	 $("#eventOptions").show();
	        	  else
	        		  $("#eventOptions").hide();
        		}
          }
          function removePagination(id){
        	  $('#'+id+'_paginate').hide();
         		$('#'+id+'_info').hide();
         		$('#'+id+'_br').remove();
         		$('#'+id).addClass('no-margin-bottom');
          }
          function prepareAllEventsTable(){
      		if(data.closed.length>0 || data.suspended.length>0 || data.active.length>0){
                  dTable=$('#tabl2').dataTable( {
                   "sPaginationType": "full_numbers",
                   "aaSorting": [],
                   "iDisplayLength":5,
                   "bLengthChange": false,
                   "aoColumns": [null
                               ] ,
                   "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');
                   $('.dataTables_filter').addClass('searchResponsive'); 
                   }                    
               } );           
           	}
      	}
          
          function prepareActiveEventsTable(){
	          if(data.active.length>0){
	      		//alert(data.active.length);
	               dTable=$('#tabl1').dataTable( {
	                  "sPaginationType": "full_numbers",
	                  "iDisplayLength":5,
	                  "aaSorting": [],
	                  "bLengthChange": false,
	                  "aoColumns": [null
	                              ] ,
	                              "fnInitComplete": function(oSettings, json) {$('.dataTables_filter input').attr('class','form-control');
	                              $('.dataTables_filter').addClass('searchResponsive'); 
	                              $(".dataTables_filter").addClass("closeClass");
	                              }
	              } );
	          	}
          }
            
        </script>
        <style type="text/css" title="currentStyle">      		        
           @import "";
            @import "/main/bootstrap/css/demo_table.css";
             .dataTables_filter input { 
            @import '.form-control'; 
            }           
        </style>
</html>
