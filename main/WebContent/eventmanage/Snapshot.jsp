<%@taglib uri="/struts-tags" prefix="s"%>
<script src="../js/eventmanage/snapshot.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>
<script type="text/javascript" src="../js/dataTables.js"></script>
<script>
var eventdate='${eventDate}';
var isrecurring=${isrecurring};
var ntsenable='${ntsEnable}';
var feeshowstatus='${feeshowstatus}';
var eid = '${eid}';
</script>
<s:hidden name="eventData.isNonProfit" id="isnonprofit"></s:hidden>
<s:hidden name="eventData.currentLevel" id="evt_cur_lvl"></s:hidden>
<s:hidden id="current_fee" value="%{currentFee}"></s:hidden>
<s:hidden name="isrecurring"  id="isrecurring"></s:hidden>


<div class="section-main-header">
	<!--panel header goes here-->
	<s:text name="sum.section.header"/>
</div>
<div class="row sticky-out-button pull-right">				
   <!--panel footer goes here-->
   <span class="buttons-div">
			<s:if test="%{subMgr==null || submgr_permissions['EditEvent']=='yes'}"><button class="btn btn-primary" onclick="editEvent();"><s:text name="sum.edit.event.btn.lbl"/></button></s:if>
			<s:if test="%{subMgr==null}"><button class="btn btn-primary" onclick="copyThisEvent();"><s:text name="sum.copy.event.btn.lbl"/></button></s:if>
		</span> 
</div>
<div class="panel panel-primary">
	<div class="panel-body white-box" >
	<div class="row">
		<div class="col-md-6">
			<div class="row">
				<div class="col-md-3"><s:text name="sum.starts.lbl"/>:</div>
				<div class="col-md-7">
					<s:text name="eventData.evt_start_date"></s:text>
					,
					<s:text name="eventData.starttime"></s:text>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-3"><s:text name="sum.ends.lbl"/>:</div>
				<div class="col-md-7">
					<s:text name="eventData.evt_end_date"></s:text>
					,
					<s:text name="eventData.endtime"></s:text>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-3"><s:text name="sum.location.lbl"/>:</div>
				<div class="col-md-7">
					<s:text name="eventData.location"></s:text>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-3"><s:text name="sum.manager.lbl"/>:</div>
				<div class="col-md-7">
					<s:text name="eventData.hostName"></s:text>
					<a href="mailto:<s:text name="eventData.email"></s:text>"><s:text
							name="eventData.email"></s:text></a>
				</div>
			</div>
		</div>
		<div class="col-md-6">

			<div class="row">
				<div class="col-md-5"><s:text name="sum.status.lbl"/>:</div>
				<div class="col-md-7">
					<span id="statusid"><s:text name="eventData.status"></s:text></span>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-5"><s:text name="global.registration.type.lbl"/>:</div>
				<div class="col-md-7">
					<s:text name="eventData.powertype"></s:text>
					<s:set name="powertype" value="eventData.powertype"></s:set>
					<s:set name="poweredbyEB" value="poweredbyEB"></s:set>
					<s:set name="eventstatus" value="eventData.status"></s:set>
				</div>
			</div>
			<br>
			<s:if test="%{isrecurring==true}">
				<div class="row">
					<div class="col-md-5"><s:text name="sum.recurring.dates.count"/>:</div>
					<div class="col-md-7">
						<s:property value="recurDates.size()" />
					</div>
				</div>
				<br>
			</s:if>

			<s:elseif test="%{eventData.eventPageViews!='NA'}">
				<div class="row">
					<div class="col-md-5"><s:text name="sum.event.page.visits.lbl"/>:</div>
					<div class="col-md-7">
						<s:text name="eventData.eventPageViews"></s:text>
					</div>
				</div>
				<br>
			</s:elseif>

				<div class="row">
					<div class="col-md-5"><s:if test="%{#powertype=='RSVP'}"><s:text name="sum.rsvp.level.lbl"/>:</s:if><s:else><s:text name="sum.ticketing.level.lbl"/>:</s:else></div>
					<div class="col-md-7">
						<s:if test="%{eventData.premiumlevel=='Seating' || eventData.premiumlevel== 'Seating-NonProfit'}">
							<s:if test="%{eventData.premiumlevel=='Seating'}"><s:text name="sum.advanced.key"/></s:if>
							<s:else><s:text name="sum.advanced.nonprofit.key"/></s:else>
				
						</s:if>
						<s:else>
							<span id="upgrade_level"><s:text name="eventData.premiumlevel"></s:text></span>
							<s:if test="%{eventData.currentLevel !=150}">
							<button class="btn btn-primary" id="upgradebtn" onclick="upgradeLevel(${eid});"><s:text name="global.upgrade.btn.lbl"/></button>
							</s:if>
						</s:else>
					</div>
				</div>
			<br>

		</div>
		</div>
		<div style="clear:both"></div>
			
		<span class="pull-right panel-footer-link">
		 <s:set value="eventData.total" name="totalsize"></s:set> 
		 <s:set value="eventData.status" name="status"></s:set> 
		 <s:hidden name="eventData.status" id="eventstsid"/>
		 
		 <s:if 	test="%{subMgr==null}">
				<s:if test="%{#totalsize > 0}"><!--a href="javascript:;" class="element">Delete Event</a--></s:if>
				<s:else>
					<a href="javascript:;"
						onclick="chanageEventStatus('delete',${eid});" class="element"><s:text name="sum.delete.event.lnk.lbl"/></a>&nbsp;&nbsp;
                </s:else>
				
					<a href="javascript:;" onclick="chanageEventStatus('actorspnd',${eid})" id="activate_suspend" <s:if test="%{#powertype == 'RSVP'}">class="element"</s:if>>
					<s:if test="%{#status == 'Suspended'}"><s:text name="sum.activate.event.lnk.lbl"/></s:if><s:else><s:text name="sum.suspend.event.lnk.lbl"/></s:else></a>
				
				
					<!-- <a href="javascript:;"
						onclick="chanageEventStatus('suspend',${eid})"></a>-->
				
				<s:if test="%{#powertype == 'RSVP'}">&nbsp;&nbsp;<a
						href="javascript:;"
						onclick="UpdateRSVPtoTicketing({'eid':'${eid}'});"><s:text name="sum.change.to.ticketing.lnk.lbl"/></a>
				</s:if>
			</s:if>

		</span>
		
	</div>
</div>
<%-- 
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title"><s:text name="global.event.url.section.header"/>
		<img src="../images/pro.jpg" title="Pro&nbsp;<s:property value="%{powertype}"/>" alt="Pro&nbsp;<s:property value="%{powertype}"/>" />
		</h3>
	</div>
	<div class="panel-body">
	<s:hidden id="customname" value="%{eventData.userurl}"></s:hidden>
	<s:hidden id="mgrid" value="%{eventData.mgrId}"></s:hidden>
	<s:hidden id="powertype" value="%{powertype}"></s:hidden>
	<span id="customurlval"><s:text name="eventData.eventURL"></s:text></span>&nbsp
	<s:if test="%{subMgr==null || submgr_permissions['IntegrationLinks']=='yes'}">
	<a href="javascript:;" id="customurl" onclick="getSinglePopup();" ><s:text name="global.edit.lnk.lbl"/></a>
	</s:if>
		<s:textfield name="eventData.eventURL" size="60"
			onclick="this.select()" cssClass="form-control"></s:textfield>
	</div>
	<div class="panel-footer">
		<span class="buttons-div"> <a href="/event?eid=${eid}"
			class="btn btn-primary" target="_blank"><s:text name="global.preview.lnk.lbl"/></a> </span>
	</div>
</div>
<s:if test="%{powertype=='Ticketing'}">
	<s:if test="%{subMgr==null}">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title"><s:text name="global.nts.section.header"/>&nbsp;
				<i class="fa fa-info-circle tooltipstered" style="cursor: pointer" 
				id="ntsinfo"  title="<s:text name="global.nts.help.msg1"/><a href='#' onclick='loadNTSVideo();'>Click here</a>&nbsp;<s:text name="global.nts.help.msg2"/>" ></i>
				</h3>
			</div>
			<div class="panel-body table-responsive" style="padding-top: 0px;">
						<table id="mypromo" class='table table-hover'>
								<thead>
											<tr>
												<th align="center"><s:text name="global.nts.who.lbl"/></th>
												<th align="center"><s:text name="global.nts.when.lbl"/></th>
												<th align="center"><s:text name="global.nts.event.page.visits.lbl"/></th>
												<th align="center"><s:text name="global.nts.ticket.sales.lbl"/></th>
											</tr>
										</thead>
							<s:if test="%{promotedMembers.size>0}">
							</s:if>
							<s:else>
								<tbody>
									<tr class="nontsdata">
										<td colspan="4"><s:text name="global.nts.nodata.msg"/></td>
									</tr>
								</tbody>
						</s:else>
						</table>
			</div>
				<div class="panel-footer" id="ntsfooter">
					<div id="ntsdisbtn" style="<s:if test='%{ntsEnable=="N"}'>display:none;</s:if>"><input id="disable" type="button" onclick="disableNTS();"
						value="<s:text name="global.nts.disable.btn.lbl"/>" class="btn btn-primary" />
					</div>
					<div id="ntsenbbtn" style="<s:if test='%{ntsEnable=="Y"}'>display:none;</s:if>"><input id="enable" type="button" onclick="updatentsstatus();"
						value="<s:text name="global.nts.enable.btn.lbl"/>" class="btn btn-primary" />
					</div>
				</div>
		</div>
	</s:if>
</s:if> --%>
<s:if test="%{#powertype == 'Ticketing' && #poweredbyEB == 'yes'}">
<br/>
<br/>
	<div class="section-main-header">
	<!--panel header goes here-->
	<s:text name="sum.ticket.sales.section.header"/>
</div>
<div class="row sticky-out-button pull-right">				
   <!--panel footer goes here-->
       <s:if test="%{subMgr==null || submgr_permissions['Reports']=='yes'}">			
				<button class="btn btn-primary" onclick="detailedReport();"><s:text name="sum.detailed.reports.btn.lbl"/></button>
	</s:if>
</div>
	<div class="panel panel-primary">
		<div class="panel-body white-box">
			<div class="row">
				<div class="col-md-12">
					<s:if test="%{isrecurring==true}">
						<form id="recurringEventDateChartForm" action="Snapshot" method="post">
							<s:hidden name="eid"></s:hidden>
							<div id="scroll"></div>
							<div class="row">
								<div class="col-md-3"><s:text name="global.recurring.event.dates.lbl"/></div>
								<div class="col-md-5">
									<s:select list="recurDates" listKey="key" listValue="value"
										name="eventDate" headerKey="all" cssClass="form-control"
										headerValue="All Dates" 
										onchange="getRecurringEventChart()" id="datelist" />
								</div>
							</div>
						</form>
					</s:if>
					<s:if test="%{isrecurring==false}">
						<br />
                                <s:text name="sum.total.ticket.sold.lbl"/>: <b><s:property
								value="totalSales.total" /> </b>(<s:text name="global.online.lbl"/> - <b><s:property
								value="totalSales.online" /></b>, <s:text name="global.manual.lbl"/> - <b><s:property
								value="totalSales.manual" /></b>)
                                	</s:if>
					<s:else>
						<br />								
									<s:text name="sum.total.ticket.sold.lbl"/>: <b><span id="ttotal"><s:property
									value="totalSales.total" /></span><span id="total"></span> </b> (<s:text name="global.online.lbl"/> - <b><span
							id="tonline"><s:property value="totalSales.online" /></span><span
							id="online"></span></b>, <s:text name="global.manual.lbl"/> - <b><span id="tmanual"><s:property
									value="totalSales.manual" /></span><span id="manual"></span></b>)									
									</s:else>

				</div>
			</div>
			<table class="table table-hover" id="ticketsales">
				<thead id="tktsales_head"></thead>
				<tbody id="tktsales_body">
					<tr class="nodata">
						<td colspan="2"><s:text name="global.nts.nodata.ticket.msg"/></td>
					</tr>
				</tbody>
			</table>
		</div>
		<script>
		var data = ${jsondata};
		var recTicketDetails = ${recTicketDetails};
		var fbpromodata=${promotionsJson};                    	
		$(document).ready(function(){	
                            		
                            		$.each(fbpromodata,function(outkey,resObj){
                            			$.each(resObj, function( inkey, valueobj ) {
                            				if(valueobj.name==undefined)valueobj.name='';
                            				if(valueobj.day==undefined)valueobj.day='';
                            				if(valueobj.visits==undefined)valueobj.visits='0';
                            				if(valueobj.visitstotal==undefined)valueobj.visitstotal='0';
                            				if(valueobj.sales==undefined)valueobj.sales='0';
                            				if(valueobj.salestotal==undefined)valueobj.salestotal='0';
                            				image = "<a href='https://www.facebook.com/profile.php?id="+valueobj.fbuid+"' target='_blank'><img title='"+valueobj.name+"' src='https://graph.facebook.com/"+valueobj.fbuid+"/picture' />";
                            			    var content='<tr><td>'+image+'<span style="vertical-align: middle !important;">'+valueobj.name+'</span></a></td><td style="vertical-align: middle !important;">'+valueobj.day+'&nbsp;<img src="/main/images/home/icon_facebook.png"></td><td style="vertical-align: middle !important;">'+props.global_total_lbl+':'+valueobj.visits+'&nbsp;'+props.global_fees_lbl+':'+valueobj.visitstotal+'</td><td style="vertical-align: middle !important;">'+props.global_total_lbl+':'+valueobj.sales+'&nbsp;'+props.global_fees_lbl+':'+valueobj.salestotal+'</td></tr>';
                            			    $('#mypromo .nontsdata').remove();
                            			    $("#mypromo").append(content);
                            			});
                            		});
                            		
                            		if(fbpromodata.promotions.length>0)
                                        $('#mypromo').dataTable( {
                                            "sPaginationType": "full_numbers",
                                            "iDisplayLength":5,
                                            "bLengthChange": false,
                                            "aoColumns": [null,null,null,null],                                                                                        
                                        	"bFilter": false  
                                        } );
									
									if(isrecurring){
                            			//$("#rec_tktsales_head").show();
                            			var aocol=[null];
                            			var html='<tr><th>'+props.global_ticket_name_lbl+'</th>';
                            			$.each(recTicketDetails.dates,function(outkey,resObj){	
                            				aocol.push(null);
    	                            		html+='<th>'+resObj+'</th>';
                                		});
                            			html+='</tr>';
                            			$('#ticketsales #tktsales_head').append($(html));
                            			$.each(data,function(outkey,resObj){
                            				var html='<tr><td>'+recTicketDetails[outkey].ticket_name+'</td>';
                            				$.each(recTicketDetails.dates,function(dateskey,datesObj){		
                            					var offlinesales=0,onlinesales=0,sold_qty=0;
                            					if(resObj[datesObj]!=undefined){
                            						console.log(resObj[datesObj]);
                            						if(resObj[datesObj].Manager!=undefined) offlinesales=resObj[datesObj].Manager;
                            						if(resObj[datesObj].online!=undefined) onlinesales=resObj[datesObj].online;
                            						if(resObj[datesObj].soldqty!=undefined) sold_qty=resObj[datesObj].soldqty;
                            						console.log(recTicketDetails[outkey].ticket_name);
                            						html+='<td>'+sold_qty+'/'+recTicketDetails[outkey].total_qty+'<br/><span class="sub-text">'+props.global_online_lbl+': '+onlinesales+'</span>,&nbsp;<span class="sub-text">'+props.global_manual_lbl+': '+offlinesales+'</span></td>';			
                            					}else{
                            						html+='<td>'+sold_qty+'/'+recTicketDetails[outkey].total_qty+'<br/><span class="sub-text">'+props.global_online_lbl+': '+onlinesales+'</span>,&nbsp;<span class="sub-text">'+props.global_manual_lbl+': '+offlinesales+'</span></td>';			
                            					}
                                    		});
                            				html+='</tr>';
                            				$('#ticketsales .nodata').remove();
                            				$('#ticketsales #tktsales_body').append($(html));
                                		});
                            			
                            			 $('#ticketsales').dataTable({
                                             "sPaginationType": "full_numbers",
                                             "iDisplayLength":5,
                                             "bLengthChange": false,
                                             "aoColumns": aocol,                                                                                        
                                         	"bFilter": false  
                                         });
                            			
                            		}else{
									var aocol=[null,null,null];
	                            		var tableHead='<tr><th>'+props.sum_ticket_status_lbl+'</th><th>'+props.global_ticket_name_lbl+'</th><th>'+props.sum_total_sold_lbl+'</th></tr>';
	                            		$('#ticketsales #tktsales_head').append($(tableHead));
                            		$.each(data,function(outkey,resObj){		
                            			$.each( resObj, function( inkey, valueobj ) {
	                            			var html='<tr>';
	                            			if(!isrecurring) html+='<td>'+valueobj.status+'</td>';
	                            			html+='<td>'+valueobj.ticket_name+'</td>'+
	                            				'<td>'+valueobj.sold_qty+'/'+valueobj.total_qty+'<br/><span class="sub-text">'+props.global_online_lbl+': '+valueobj.onlinesales+'</span>,&nbsp;<span class="sub-text">'+props.global_manual_lbl+': '+valueobj.offlinesales+'</span></td></tr>';			
	                            	        $('#ticketsales .nodata').remove();
	                            			$('#ticketsales #tktsales_body').append($(html));
                            			});
                            		});
                            		if(data.ticketssummary.length>5)
                                        $('#ticketsales').dataTable( {
                                            "sPaginationType": "full_numbers",
                                            "iDisplayLength":5,
                                            "bLengthChange": false,
                                            "aoColumns": aocol,                                                                                        
                                        "bFilter": false  
                                        } );

									}
                                    /*$('#customurl').click(function(){
											 openSinglePopUp($(this).offset(),success,cancel,$('#customname').val());
                                        });*/
                            	});
                            	
                                </script>
<style type="text/css" title="currentStyle">
@import "../bootstrap/css/demo_table.css";
.dataTables_filter input {
@import '.form-control';
}
</style>
		
	</div>
</s:if>
<s:elseif test="%{#powertype == 'Ticketing' && #eventstatus == 'Active'}">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"><s:text name="global.payment.settings.lbl"/></h3>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-12"><s:text name="sum.update.ps.help.msg"/></div>
			</div>
		</div>
		<div class="panel-footer">
			<button class="btn btn-primary" onclick="paymentSettings();"
				style="text-decoration: none"><s:text name="global.payment.settings.lbl"/></button>
		</div>
	</div>
</s:elseif>
<s:elseif test="%{#powertype == 'RSVP'}">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"><s:text name="sum.rsvp.responses.lbl"/></h3>
		</div>
		<div class="panel-body">
			<s:if test="%{isrecurring==true}">
				<div class="row">
					<div class="col-md-12">
						<form id="rsvpRecurringEventDateChartForm"
							action="Snapshot!getRSVPChartData" method="post">
							<div id="scroll"></div>
							<div class="row">
							<div class="col-md-3"><s:text name="global.recurring.event.dates.lbl"/></div>
							<div class="col-md-5">
								<s:select list="recurDates" listKey="key" listValue="value"
									name="eventDate" headerKey="all" headerValue="All Dates"
									cssClass="form-control" id="rsvprecdate"
									onchange="getRSVPRecurringEventChart()" />
							</div>
							</div>
						</form>
					</div>
				</div>
				<br/>
			</s:if>
			<div class="row">
			<div class="col-md-12">
						<div class="row" id="rsvp_response_none" style="display:block">
							<div class="col-md-2"><s:text name="global.none.lbl"/></div>
						</div>
						<div class="row" id="rsvp_att" style="display:none">
						<div class="col-md-2"><s:text name="sum.rsvp.attending.key"/></div>
							<div class="col-md-1">
								<span id="attending"></span>
							</div>
						</div>
						
						<div class="row" id="rsvp_notsur" style="display:none">
						<div class="col-md-2"><s:text name="sum.rsvp.not.sure.key"/></div>
						<div class="col-md-1">
							<span id="notsure"></span>
							</div>
						</div>
						
						<div class="row" id="rsvp_notatt" style="display:none">
						<div class="col-md-2"><s:text name="sum.rsvp.not.attending.key"/></div>
						<div class="col-md-1">
							<span id="notattending"></span>
							</div>
						</div>
			</div>
		</div>
		</div>
	</div>
	
	<script>
		var rsvpdata = ${rsvpjsondata};
		if(rsvpdata.Attending){
			$("#rsvp_response_none").hide();
			$("#attending").html(rsvpdata.Attending);
			$("#rsvp_att").show();
		}
		if(rsvpdata.NotSure){
			$("#rsvp_response_none").hide();
			$("#notsure").html(rsvpdata.NotSure+"<br/>");
			$("#rsvp_notsur").show();
			$("#rsvp_notsur").before("<br/>");
		}
		if(rsvpdata.NotAttending){
			$("#rsvp_response_none").hide();
			$("#notattending").html(rsvpdata.NotAttending);
			$("#rsvp_notatt").show();
			$("#rsvp_notatt").before("<br/>");
		}
	</script>
	
</s:elseif>
<s:if test="%{#powertype == 'RSVP'}">
	<script>
var eid = '${eid}';

function UpdateRSVPtoTicketing(){		
  	UpdateRSVPtoTicketingEvent(eid,'{eventData.total}');
}
</script>
</s:if>
<script>
    var _eventid='';
	var _purpose='';
	var j=0;
	
function getSinglePopup(){
var powertype='${powertype}';
var curlevel='${currentLevel}';
var curfee='${currentFee}';

var cardrequired='';
if(powertype=='RSVP' && curlevel == 150 && curfee == 0)
  cardrequired='no';

var url='SpecialFee!getCCStatus?eid='+eid;
if(powertype=='RSVP' && curlevel==90){
	specialFee(eid,'150','EventCustomURL',powertype);
}else if(powertype=='RSVP' && cardrequired!='no'){
	$.ajax({
		url : url,
		type : "post",
		error: function(){parent.hidePopup();alert(props.global_sys_cant_process_errmsg);},
		success : function(t){
			data = t;
			var ccjson=eval('('+data+')');
	        var cardstatus=ccjson.cardstatus;
	        var userid=ccjson.userid;
	        if(cardstatus=='Y'){
	        	var url='IntegrationLinks!customURL?eid='+eid;
	 //       	callPopUp(url);
openSinglePopUp($('#customurl').offset(),eventurlsuccess,eventurlcancel,$('#customname').val());
	        }else{
	          var url='../payments!getPaymentScreen?processType=vault&amount=1&refId='+userid+'&purpose=invoice_hold_customURL';	               
                getccSnap(userid,'invoice_hold_customURL');
		    }
		}
	});

}else{
	specialFee(eid,'200','EventCustomURL','Ticketing');		
}



/*	 var _powertype=$('#powertype').val();
	 var curlevel=$('#evt_cur_lvl').val();
	 var curfee=$('#current_fee').val();
	var cardrequired='';
	if(_powertype=='RSVP' && curlevel == 150 && curfee == 0)
	  cardrequired='no';
		var url='SpecialFee!getCCStatus?eid='+eid;
	if(_powertype=='RSVP' && curlevel==90){
		specialFee(eid,'150','EventCustomURL',_powertype);
	//}else if(_powertype=='RSVP' && cardrequired!='no'){
	}else if(_powertype=='RSVP' && curlevel==150){
		openSinglePopUp($('#customurl').offset(),success,cancel,$('#customname').val());
       new Ajax.Request(url, {
    	       method: 'post',
    	        onSuccess: function(t){
			    data=t.responseText;
			    var ccjson=eval('('+data+')');
		        var cardstatus=ccjson.cardstatus;
		        var userid=ccjson.userid;
		        if(cardstatus=='Y'){
		        	var url='IntegrationLinks!customURL?eid='+eid;
					loadURLBasedWindow(url, handlecustomURLSuccess);
		        }else{
		          var url='/main/payments!getPaymentScreen?processType=vault&amount=1&refId='+userid+'&purpose=invoice_hold_customURL';
                //  loadURLBasedWindow(url,handlesuccessccform);
                    getccSnap(userid,'invoice_hold_customURL');
			    }
			 } 
    	}); 
	}else{
		specialFee(eid,'200','EventCustomURL','Ticketing');
	}*/
}

	function previewFunc(){
		window.location.href="../event?eid=${eid}";
	}
	
	function paymentSettings(){
		window.location.href="PaymentSettings?eid=${eid}";
	}
	
	function editEvent(){
		  window.location.href='EditEvent?eid=${eid}';
	  }
   
	function copyThisEvent(){    		
		copyEventSnapshot('true',eid);
}
    function closePopup(){
    			 $('#myModal').modal('hide');    
    			 }		

	function enableNetwork(){
		updatentsstatus();
	}

	  
	    var eventurlsuccess=function(value){
	    	$('#singleerror').html('');
	        var _powertype=$('#powertype').val();
	        var userurl=$('#customname').val();
var userurl=$('#customname').val();
	        userurl=userurl.replace(/[^a-z0-9]|\s+|\r?\n|\r/gmi, "");
	        $('#customname').val(userurl);	       
 var userid=$('#mgrid').val();
	        var url="Snapshot!setCustomURL?eid="+eid+"&mgrId="+userid+"&userurl="+userurl+"&newurl="+value+"&powertype="+_powertype;
	        $.ajax({
	  		  url : url,
	  		  type: 'POST',
	  		  success: function(transport) {		  	
	  			var result=transport;
	  			$('#singleerror').show();
	  			if(result.indexOf("URL Exists")>-1)
	  				$('#singleerror').html(props.global_this_url_not_avail_errmsg);
	  			else if(result.indexOf("Invalid")>-1)	
	  				$('#singleerror').html(props.global_enter_valid_text_msg);		
	  			else if(result.indexOf("spacesInUrl")>-1)
	  				$('#singleerror').html(props.global_use_alphanumeric_chars_msg);
	  			else
	  				$('#singleValuePopUp').hide();

if(result.indexOf('newurl')>-1){
	var tempObj=JSON.parse(result);
	$('#customname').val(tempObj.newurl);
	$('#customurlval').html(tempObj.url);
	}
		  	      }
	  	});	
	    };
	      var eventurlcancel=function(){
	    };

	
	
//data = {"ticketssummary":[{"total_qty":"20", "ticket_name": "First Tkt", "status": "Available", "alldates": "no", "sold_qty": "1", "offlinesales": "0", "onlinesales": "1"}]};
// var value="Our patent pending technology encourages users to share your event on the web <br>and social media. We generate unique URLs for users to track page visits and ticket <br>sales. The fees to use this feature are 10 cents per visit + 10% of total ticket sale <br>proceeds sold through unique URLs. Click here to watch brief introduction video. ";
  $(function(){
	  $('#ntsinfo').tooltipster({
		  interactive:'true',
		  
		   contentAsHTML:'true',
		    fixedWidth:'100px',
		    position: 'right'
		    });
  });
  
  
  function loadNTSVideo(){
	   var url='/main/eventmanage/NetworkSelling!loadNtsVideo?eid=${eid}';	 
		   $('iframe#popup').attr("src",url);		 
		   $('#myModal .modal-header').html('<button type="button" class="close" data-dismiss="modal">&times;</button><h3>Network Ticket Selling</h3>');
		   
		   $('#myModal').modal('show');
	}
  
  
  
</script>
