<%@taglib uri="/struts-tags" prefix="s"%>
<script src="../js/eventmanage/eventdetails.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>
<script type="text/javascript" src="/main/copy-text-to-clipboard/jquery.zclip.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>

<s:hidden value="%{eventData.powertype}"  id="powertype"></s:hidden>

<%-- <s:property value="%{eventData.powertype}"></s:property> --%>


<%-- <div class="section-main-header">
<s:text name="global.event.url.section.header"/>
</div> --%>
<div class="panel-primary">
	<div class="panel-body">
	<div class="row">
		
		<div class="tab-pane active" role="tabpanel" id="tab1">

			<div class="col-md-12 col-sm-12 bottom-gap row">
				<div class="col-md-6 col-sm-6">
				  		<s:hidden id="customname" value="%{eventData.userurl}"></s:hidden>
						<s:hidden id="mgrid" value="%{eventData.mgrId}"></s:hidden>
						<%-- <s:hidden id="powertype" value="%{powertype}"></s:hidden> --%>
	
				<span id="customurlval"><a href="<s:text name="eventData.eventURL"></s:text>" target="_blank"><s:text name="eventData.eventURL"></s:text></a>
				</span>

				</div>
				<div class="col-md-3 col-sm-3 sm-font">
					 <s:if
						test="%{subMgr==null || submgr_permissions['IntegrationLinks']=='yes'}"> 
						<a onclick="openEdit()" id="" style="cursor:pointer"><s:text
								name="global.customize.lnk.lbl" /></a>
					 </s:if> 
					&nbsp;
					<a  onclick="triggerCopy()" id="copylink" style="cursor:pointer"  data-link="<s:text name="eventData.eventURL"></s:text>"><s:text
								name="global.copylink.lnk.lbl" /></a>
					
				</div>
				
				<div class="col-md-3 col-sm-3"><s:text name="sum.event.page.visits.lbl"/>: <s:property value="%{visitcount}"></s:property>   </div>
			</div>
			
			<div style="clear: both"></div>
			
       <div class="col-md-12">
			<div class="well" ID="showEdit" STYLE="DISPLAY: NONE;">								
				
					 <div class="col-md-12 row" id="existError"></div>
					 	 <div class="col-md-7 col-sm-8 row bottom-gap">
					  		 <!-- <div class="row col-md-1" style="margin-top:8px;width:11%"></div> -->
					   		
					   		<span>http://</span>
					   		<span><input type="text" class="form-control" style="display:inline;width:60% !important" id="enteredValue" placeholder='<s:text name="es.event.url.name"/>'>
									</span>
									<span>.eventbee.com</span>
						
									
									<%-- <div class="col-md-3" style="margin-top:8px;margin-left:-25px;"><span>.eventbee.com</span></div> --%>
					  </div>
					  <div class="col-md-4 col-sm-4">
							  <span>
										<button type="button" class="btn btn-primary" onclick="success()"><s:text name="epc.save.notice.btn.lbl"/></button>
								</span>
								 <span>
										<button class="btn btn-cancel" onclick="openEdit()">
											<i class="fa fa-times"></i>
										</button>
								</span>
					  </div>				
					
				 <div  style="clear:both"></div>			
				

				<script>

				 triggerCopy();

				 function triggerCopy(){
						
						
						$("#copylink").zclip({
							path:"/main/copy-text-to-clipboard/ZeroClipboard.swf",
					        copy:function(){ return $(this).data('link')}
						});	
					   $("#copylink").trigger('mouseenter');
					}


				 
				
					function openEdit() {
						var _powertype = $('#powertype').val();

						if($('#showEdit').is(':visible')){
							$('#showEdit').slideToggle();
							return;
							}
						
						if(_powertype=='RSVP')
						specialFee("${eid}","150","EventCustomURL","Ticketing");
						else
						specialFee("${eid}","200","EventCustomURL","Ticketing");	
							}
					
					var eid = '${eid}';
					var success = function() {
						$('#existError').html('');
						var _powertype = $('#powertype').val();
						var userurl = $('#customname').val();
						var userid = $('#mgrid').val();
						var value=$('#enteredValue').val();
						var url = "Snapshot!setCustomURL?eid=" + eid
								+ "&mgrId=" + userid + "&userurl=" + userurl
								+ "&newurl=" + value + "&powertype="
								+ _powertype;
						$.ajax({
									url : url,
									type : 'POST',
									success : function(transport) {
										var result = transport;
										$('#existError').show();
										if (result.indexOf("URL Exists") > -1)
											$('#existError').html('<font color="red">'+ props.global_this_url_not_avail_errmsg+ '</font>');
										else if (result.indexOf("Invalid") > -1)
											$('#existError').html('<font color="red">'+ props.global_enter_valid_text_msg+ '</font>');
										else if (result.indexOf("spacesInUrl") > -1)
											$('#existError').html('<font color="red">'+ props.global_use_alphanumeric_chars_msg+ '</font>');
										else
											openEdit();

										if (result.indexOf('newurl') > -1) {
											var tempObj = JSON.parse(result);
											$('#customname')
													.val(tempObj.newurl);
											//alert(tempObj.url);
											$('#customurlval')
													.html('<a href="'+tempObj.url+'" target="_blank">'+tempObj.url+'</a>');
											$('#copylink').attr('data-link',tempObj.url);
										}
									}
								});
					};
					var cancel = function() {
					};
				</script>
			</div>
		</div>	

</div>
		
		
		
</div>
	</div>
</div>




<div class="section-main-header box-top-gap">
<s:if test="%{eventData.powertype=='Ticketing'}">
<s:text name="spt.tkt.sale.lbl"/>
</s:if><s:else>
<s:text name="spt.rsvp.rep.lbl"/>
</s:else>
</div>


<div class="row sticky-out-button pull-right">
	<div class="col-md-12"> 
		 <div class="pull-right">
		 <s:if test="%{subMgr==null}">
		<%--  <h1><s:property value="%{subMgr}"></s:property></h1> --%>
		  <button style="margin-left: 15px;display:none" class="btn btn-primary" id="deleteEvent"
				onclick="deleteEvent(${eid})" ><s:text name="sum.delete.event.lnk.lbl"></s:text></button>
				</s:if>
		 <s:if test="%{isrecurring==true}">
			<ul class="dropdown-menu" role="menu" aria-labelledby="eventOptions" style="max-height: 200px;overflow: auto;"
				id="eventOptions">
			</ul> 

			<button style="margin-left: 15px" class="btn btn-primary"
				onclick="dropDownClick()" ><span id="recurevent"> <s:text name="spt.all.dates.lbl"/> </span>&nbsp; <span style="cursor: pointer;  margin-top: 3px;" id="toggling-menu" class="glyphicon glyphicon-menu-down"></span></button>
            </s:if>

		 </div> 
	 </div> 
	<div style="clear:both"></div>
</div>


<div class="panel-primary">
	<div class="panel-body">
	<div class="row" style="overflow-y: hidden;">
		<div id="donutchart" class="table-responsive" style="margin: -51px auto;text-align: center;width: 500px;"></div>
	<div class="col-md-2">
	</div>
</div>
	</div>
</div>



<div class="section-main-header box-top-gap">
<s:text name="spt.recent.labl"/>
<span id="transactioncount">
 
<s:if test="%{eventData.powertype=='Ticketing'}">
<s:text name="spt.transactions.lbl"/></s:if>
<s:else>
<s:text name="spt.reponses.lbl"/></s:else>

</span>

 <s:if test="%{subMgr==null || submgr_permissions['Reports']=='yes'}"> 
&nbsp;<span id="viewall"></span>
</s:if>

</div>
<div class="panel-primary">
	<div class="panel-body">
	      <table class="table table-hover" id="ticketsales">
				<tbody id="tktsales_body">	</tbody>
		</table>
	</div>
</div>


<script>
var powertype=$('#powertype').val();
var jsonData=${jsondata};
var recenttrnsactions=${msg};
var isrecurring=${isrecurring};
var recurtktDetails=${recTicketDetails};
var rsvpjsonData=${rsvpjsondata};
var eid=${eid};
var totalTicketSold=0;
var chartData=new Array();

fillRecentTransactionData();
google.load("visualization", "1", {
	packages : [ "corechart" ]
}); 

google.setOnLoadCallback(doStats);

</script>
