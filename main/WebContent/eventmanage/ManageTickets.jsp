<%-- <%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.eventbee.general.StatusObj"%>
<%@page import="com.eventbee.general.DBManager"%> --%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="/main/copy-text-to-clipboard/jquery.zclip.js"></script>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@include file="../getresourcespath.jsp"%>
<link rel="stylesheet" href="/main/bootstrap/css/bootstrap-multiselect.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="/main/css/ticketing.css" />


<style>
.qty-sel{
	width:70px !important;
	display:inline !important;
}
.edit-section{
  margin-top:10px !important;
}
ul.categorytickets li:last-child {
	border-bottom: 0px !important
}

.backgroundclr {
	background-color: #f5f5f5 !important;
}

 tr#edited:hover {
 background-color: #FFFFFF !important;
}
 .tktname{
 padding: 0px;
 margin-left: 15px;
 margin-top:-4px;
 }
 
</style>
<s:set name="venue" value="%{venueenable}"></s:set>
<s:set name="tickets" value="%{submgr_permissions['ManageTickets']}"></s:set>
<s:set name="ticketdisplay" value="%{submgr_permissions['DisplayTickets']}"></s:set>
<s:set name="ticketurls" value="%{submgr_permissions['TicketURLs']}"></s:set>
<s:hidden name="currencySymbol" id="currsym"></s:hidden>

<s:if test="%{subMgr==null || (subMgr!=null &&  submgr_permissions['ManageTickets']=='yes')}">
<div id="ticketsblock">

<div class="row sticky-out-button pull-right">
	<div class="col-md-12"> 
	<div class="pull-right">
			<button class="btn btn-primary" id="addticket"><s:text name="mt.create.ticket.lbl"/></button>&nbsp;
			<button class="btn btn-primary" id="adddonation"> <s:text name="mt.create.donation.lbl"/> </button>&nbsp;
			<button class="btn btn-primary" id="createticketcategory">  <s:text name="mt.create.group.lbl"/> </button>
	</div>
	</div> 
	<div style="clear:both"></div>
</div>

<div class="white-box no-button">
   <s:if test="%{isrecurring==true}">
	  <div class="form-inline" style="margin-left:10px;">
           <div class="form-group" style="margin-right:15px;"> <s:text name="global.sel.rec.date.lbl"/>  </div>
           <div class="form-group">
                <s:select name="eventDate" id='source' list="dates" listKey="key" listValue="value" cssClass="form-control" onchange="changeDate();"/> 
           </div>
      </div>
	  <br/>
   </s:if>
	
	<div class="row">
		<div class="col-md-12">
		    <div class="not-found" id="noTicketsDiv" style="display:none"> <s:text name="mt.no.tkts.todisp.lbl"/> </div>
			<s:hidden name="isrecurring" id="isrecurring"></s:hidden>
			<ul class="list-group" id="tickets"></ul>
			<div id='tktbox' style="display: none;"></div>
			<div id="donationbox" style="display: none;"></div>
			<div id="groupbox" style="display: none;"></div>
		</div>
	
	
	<%-- <span class="pull-right" style="margin-top: 8px; margin-bottom: 15px;">
		<s:if test="%{#venue!='YES'}">
			<s:hidden id="eventcapacity" name="eventleveltktcount"></s:hidden>
			<a href="javascript:;" id='seteventcap' title="Pro Ticketing">&nbsp;Event Capacity  :<span id="capacity">
			<s:property value="%{eventleveltktcount}" /></span></a> 	&nbsp;&nbsp;	
		</s:if>
	</span> --%>
	<div style="clear: both;"></div>
	
	
				
	</div>   <!-- row end -->
</div>   <!-- white box -->
	


</div>   <!-- tickets block -->
</s:if>



<div style="clear: both;" class="bottom-gap"></div> 
<%-- <div id="privateStatusMsg"></div>
<div id="subManagerTicketUrls">
	<div class="section-main-header box-top-gap"> <s:text name="mt.prt.tkt.url.lbl"/>&nbsp;
		<i id="ticketurlinfo" style="cursor: pointer" class="fa fa-info-circle info tooltipstered" ></i>
	</div>
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

<s:if test="%{isrecurring==false && subMgr==null}">
<div id="tickets_waitlist"  >
	<div class="section-main-header box-top-gap"> <s:text name="mt.waitlist.lbl"/></div>
	<div id="tickets_waitlist_data"></div>
</div>
</s:if>

<div style="clear: both;" class="bottom-gap" id="gapid"></div> --%>
<!-- <div id="successmsg"></div> -->


<%-- <s:if test="%{subMgr==null || (subMgr!=null &&  submgr_permissions['DisplayTickets']=='yes')}">
	<div id="displaytktdiv">
	<div class="section-main-header box-top-gap">Ticket Display</div>
	<div class="white-box bottom-gap"> 
		<div id="ticketformatdiv">   <s:text name="global.loading.msg"/>        </div>
	</div>
	</div>
</s:if>

<s:if test="%{subMgr!=null && submgr_permissions['ManageTickets']!='yes' && submgr_permissions['DisplayTickets']=='yes'}">
<div style="clear: both;" ></div>
<div id="displaytktdiv">
	<div class="section-main-header box-top-gap">Ticket Display</div>
	<div class="white-box bottom-gap"> 
		<div id="ticketformatdiv"   >   <s:text name="ea.loading.table.lbl"/>     </div>
	</div>
</div>
</s:if> --%>




<%-- <s:if test="%{subMgr==null || (subMgr!=null &&  (submgr_permissions['DisplayTickets']=='yes' || venueenable!='YES' || submgr_permissions['ManageTickets']=='yes'))}">


<div class="section-main-header box-top-gap"> <s:text name="mt.tkt.settingg.lbl"/> </div>

	<div class="white-box bottom-gap"> 
		
		
		<s:if test="%{#venue!='YES'}">
		<div class="row bottom-gap" id="eventcapacityDiv">
			<div class="col-md-12 col-sm-12 col-xs-12">
			<s:hidden id="eventcapacity" name="eventleveltktcount"></s:hidden>
			<label id='seteventcap' style="cursor: pointer;">
			<span title="<s:text name="up.pro.ticketing.lbl"/>">&nbsp;<s:text name="mt.event.capacity.lbl"/>  <span id="capacity">
			<s:property value="%{eventleveltktcount}" /></span>
			</span>
			<span id="evtCapacitySpin" class="sm-font arrow-gap glyphicon glyphicon-menu-right" data-toggle="tickets_poiuytr" style="cursor: pointer;  margin-top: 3px;"></span>
			 		</label>
			</div>
			</div>
			
	<div class="row background-options" id="showCapacity" style="display:none;">
	
	<div class="col-md-12 col-sm-12">
		<div class="col-md-12 row sm-font" id="existError"></div>
		<div class="col-md-6 col-sm-8 row bottom-gap">
			<input type="text" class="form-control" id="enteredValue" placeholder='<s:text name="mt.event.capacity.lbl" />'>
		</div>
		<div class="col-md-4 col-sm-4">
			<span>
				<button type="button" class="btn btn-primary" onclick="success()"> <s:text name="global.save.btn.lbl"/> </button>
			</span>
			<span>
			<button class="btn btn-cancel" onclick="closeCapacity()">
				<i class="fa fa-times"></i>
			</button>
			</span>
		</div>
				
	</div>
	</div>		
			
		</s:if>
		
		
		<s:if test="%{subMgr==null || (subMgr!=null &&  submgr_permissions['DisplayTickets']=='yes')}">
		<s:if test="%{#venue!='YES'}">
		 <hr/> 
		
		<!-- <div class="row bottom-gap">
		<div class="col-md-12 col-sm-12 col-xs-12" style="background-color: #EEEEEE;border: 1px solid #000000;"></div>
		</div> -->
		
		</s:if>
		<div class="row">
		<div class="col-md-12 col-xs-12 col-xs-12">
		
		<label style="cursor: pointer;" id="ticketDisplay">
		<span title="<s:text name="up.pro.ticketing.lbl"/>">&nbsp;<s:text name="mt.display.tkt.lbl"/> </span>
		
		<span class="arrow-gap glyphicon glyphicon-menu-right sm-font" id="spinner" style="cursor: pointer;  margin-top: 3px;"></span>
		</label>
		
		</div>
		
		</div>
		<div class="row">
		<div class="background-options" id="displaytktdiv" style="display:none;"></div>
		</div>
		
		</s:if>
		
	</div>
</s:if>
 --%>
<!-- conditional ticketing -->
<script>

/* $(document).ready(function(){
	$('#ticketurlinfo').attr('title',props.mt_pvt_urls_msgs);
});

var subMgr='${subMgr}'; */

var subTickets='${tickets}';
/* var subTicketDisplay='${ticketdisplay}';
var subTicketUrls='${ticketurls}';

var eventlevelcount = '${eventleveltktcount}'; */
var tktdata = ${jsonData};
var groupTickets = new Array();
var emptyGrps = new Array();
var tktGrps = new Array();
var isAdded = true;
var eventid = '${eid}';
var existingRules = '${existingRules}';
var existingTicketingRules=JSON.parse(existingRules);

/* var currentEventLevel='<s:property value="currentLevel"></s:property>';
var requireLevel="300"; */


/* var loaded=false;
$('#ticketDisplayAtttribs').click(function(e){
	specialFee(eventid,'200','DisplayTickets','Ticketing');
});

$('#ticketDisplay').click(function(){
	DisplayTicketsData();
})
 */

/* DisplayTickets();
function DisplayTickets(){
if(document.getElementById('displaytktdiv')){
	var url="DisplayTickets?eid="+${eid};
    $('#ticketformatdiv').load(url,function(){});
}
} */

/* var loaded=false;


function DisplayTicketsData(){

    loadingPopup();

     
       if(!loaded){
           url="DisplayTickets?eid="+${eid};
           $('#displaytktdiv').load(url,function(){
        	   if($('#displaytktdiv').is(':hidden'))
                   $('#spinner').removeClass('original').addClass('down');   
               	 else
               	 $('#spinner').removeClass('down').addClass('original');
                $('#displaytktdiv').slideDown();
                hidePopup();
             });
            loaded=true;
           
            
       }
       else{
           //alert("in loadin")
           if($('#displaytktdiv').is(':visible')){       
               $('#displaytktdiv').slideUp();
               $('#spinner').removeClass('down').addClass('original');
           }else{
               $('#displaytktdiv').slideDown();
               $('#spinner').removeClass('original').addClass('down');   
           }
           hidePopup();
           }
      
       
} */

</script>





<%-- <div id="conditionalticketing" ng-app="CT" ng-controller='ctController'  ng-cloak>
<div class="section-main-header" style="margin-top:50px">
				Ticketing Rules
			</div>
			<div class="row sticky-out-button pull-right">				
				 <button ng-if=" activeTab=='Required' " type="button" ng-disabled="scopes.requireController.requireRuleAddState()"
					class="btn btn-primary  pull-right "
					ng-click="requireClick(-1)">
					+Rule
				</button>
				
				<button ng-if=" activeTab=='Must' " type="button" ng-disabled="scopes.conditionalController.mustRulesAddState()"
					class="btn btn-primary  pull-right "
					ng-click="mustClick(-1)">
					+Rule
				</button>
				
				<button ng-if=" activeTab=='Block' " ng-disabled="scopes.blockController.blockRulesAddState()" type="button"
					class="btn btn-primary pull-right"
					ng-click="blockClick(-1)" style="">
					+Rule
				</button>  			
			</div>
			
<div class="white-box" >
<!-- <div class="boxcontent" id="conditionalHint">
			Does your event has complex ticketing conditions such as
			<br> 1. Buying certain tickets is required to complete
			registration<br> 2. To buy a ticket, one has to buy certain
			quantity of another dependant ticket<br> 3. Buying a ticket type prohibits buying
			another type<br>
			<br>
</div>  -->
<div ng-show="!isTabShow" class="not-found">
 No ticketing rules to display.
</div>   
<div id ="ticketingRules" class="row"  ng-show="isTabShow">		
	<div class="col-md-12">		
			<div class="center btn-group btn-toggle" data-toggle="buttons">
				<label ng-click="tabChange('Required')"	ng-class="{'btn-default': activeTab=='Required','btn-active': activeTab!='Required'}"  class="optiontype btn  no-radius">
					<input type="radio"> Must Buy
				</label>
				<label ng-click="tabChange('Must')"	ng-class="{'btn-default': activeTab=='Must','btn-active': activeTab!='Must'}" class="optiontype btn  no-radius">
					<input class="datesel" id="all" name="events" value="2" type="radio">Conditional Buy
				</label>
				<label  ng-click="tabChange('Block')"	ng-class="{'btn-default': activeTab=='Block','btn-active': activeTab!='Block'}" class="optiontype btn  no-radius">
					<input  type="radio"> Can't Buy
				</label>												
				
			</div>
	
	</div>
   
	<div class="col-md-12">
		<br />
		<div ng-show="activeTab=='Required'" ng-controller="requireController as requireScope">
		    <div ng-show="requireRules.length<=0 && !status.isEditing" class="not-found">{{noRule}}</div>
			<table class="table table-hover" ng-init="thisScope = requireScope">
					<tbody>
						<tr ng-repeat="eachRule in requireRules track by $index"  ng-init="hoverEffect=false;" ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()">	
							<td>
								<div class="row"  ng-show="eachRule.trg.length > 0">
									<div   class="col-md-8">
										<span> {{$index+1}}.&nbsp;&nbsp;&nbsp;Must buy 
											<span ng-if="eachRule.trg.length > 1"> 
												<span ng-if="eachRule.condition=='requireOR'"> atleast one of </span>
												<span ng-if="eachRule.condition=='requireAND'">all of </span> 
											</span> 
											<span ng-repeat="childRule in eachRule.trg"> 
												<span ng-if="!$first">, </span> 
												<span ng-if="$last && $index > 0">and </span> 
												 <b>{{getNameById(childRule.id)}}</b> 
												 <span ng-if="$last"> 
												   ticket<span ng-if="eachRule.trg.length > 1 ">s</span>
												  to complete registration. </span> 
											</span>
										</span>
									</div>
									<div class="col-md-4 sm-font"   ng-show="hoverEffect && !status.isEditing" ><span class="temp pull-right"><a href="javascript:;"  ng-click="requireScope.clickedIndex=$index;copy(eachRule);status.isEditing=true">Edit</a>   &nbsp;<a href="javascript:;" ng-click="deleteRule($index,requireRules)"> Delete</a>&nbsp;</span></div>
								</div>
								<div ng-if="requireScope.clickedIndex==$index && require" class="row" >
									<div class="col-md-12 col-sm-12"> 
									  <div class="well well-no-margin edit-section">
										 	<div class="col-md-12 col-sm-12" style="margin-top: 10px !important">
												<div class="col-md-4 col-sm-6">To complete registration, must buy</div>
												<div class="col-md-2 col-sm-4">
													<select class="form-control"  ng-options="o.value as o.label for o in ruleTypes"
															      ng-model="require.condition"></select>
													<div style="clear:both"></div>
												</div>
										   </div>
											
											<div class="row col-md-12 col-sm-12" >
												<div class="col-md-10 col-sm-10" style="margin-top: 10px !important;">
														<div class="col-md-12 col-sm-12">
															<div ng-repeat="ticket in dupAllTickets track by $index" >														
															      <label  class="sm-font" > <input  icheck type="checkbox" ng-model="ticket.check" value="{{ticket.id}}"/>&nbsp;&nbsp;{{ticket.label}}
																</label>														
															</div>
														</div> 
															
																					
												</div>					
											</div>
										
										   <div class="center">
											    <span>
													<button class="btn btn-primary" ng-disabled="!isValid()" ng-click="requireScope.clickedIndex=-2;saveClick($index);">Save</button>
													<button class="btn btn-cancel"   ng-click="cancelClick(requireScope);"><i class="fa fa-times" ></i></button>
											   </span><br>
										  </div>
										<div style="clear:both"></div>	
										</div>
									
									
									
										<!--  well close -->
									</div>										
								</div><!-- row close -->
							</td>
						</tr>
					</tbody>
			</table>		
		   
			<!-- for new entry -->
				<div ng-if="requireScope.clickedIndex==-1 && require" class="row edit-section">
									<div class="col-md-12 col-sm-12">
										<div class="well edit-section">
										 <div class="col-md-12 col-sm-12" style="margin-top: 10px !important">
											<div class="col-md-4 col-sm-6">To complete registration, must buy</div>
											<div class="col-md-2 col-sm-4">
												<select class="form-control" ng-disabled=" existedRule()!='' " ng-init=" require.condition= (existedRule() == 'requireAND') ? 'requireOR' : 'requireAND' "
														      ng-options="o.value as o.label for o in ruleTypes"
														      ng-model="require.condition"></select>
												<div style="clear:both"></div>
											</div>
										</div>
											
										<div class="row col-md-12 col-sm-12" >											
											<div class="col-md-10 col-sm-10" style="margin-top: 10px !important;">
												<div class="col-md-12 col-sm-12">
													<div ng-repeat="ticket in dupAllTickets track by $index" >														
													      <label  class="sm-font" > <input  icheck type="checkbox" ng-model="ticket.check" value="{{ticket.id}}"/>&nbsp;&nbsp;{{ticket.label}}
														</label>														
													</div>
												</div> 						
											</div>					
										</div>	
									
										 <div class="center">
										    <span >
												<button class="btn btn-primary" ng-disabled="!isValid()" ng-click="requireScope.clickedIndex=-2;saveClick('new');">Save</button>
												<button class="btn btn-cancel"   ng-click="cancelClick(requireScope);"><i class="fa fa-times" ></i></button>
											</span><br>
										 </div>
										<div style="clear:both"></div>	
									</div><!--  well close -->
								</div>										
					</div><!-- row close -->
			<!-- for new entry end -->
			
		</div>

		<div ng-show="activeTab=='Must'"
			ng-controller="mustController as mustScope">
			 <div ng-show="mustRules.length<=0 && !status.isEditing" class="not-found">{{noRule}}</div>
			 <table class="table table-hover" ng-init="thisScope = mustScope">
					<tbody>
						<tr ng-repeat="eachRule in mustRules track by $index"  ng-init="hoverEffect=false;" ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()">	
							<td>
								<div class="row"  ng-show="eachRule.src.id &&  eachRule.trg.length > 0">
									<div class="col-md-8">
										<span> {{$index+1}}.&nbsp;&nbsp;&nbsp;To buy  
											 <b>{{getNameById(eachRule.src.id)}}</b> 
											 ticket, must buy
											  <span ng-if="eachRule.trg.length > 1">
											  	 <span ng-if="eachRule.condition=='MustByOR'"> atleast one of  </span>
											  	 <span ng-if="eachRule.condition=='MustByAND'"> all of </span> 
											  </span> 
											  <span ng-repeat="childRule in eachRule.trg">
											  		<span ng-if="!$first">, </span>
											  		<span ng-if="$last && $index > 0 "> and </span>
											  		<b>{{getNameById(childRule.id)}} </b>
											  		<span ng-if="$last"> ticket<span ng-if="eachRule.trg.length > 1 ">s</span>.</span>
											   </span>
										</span>
									</div>
									<div class="col-md-4 sm-font"   ng-show="hoverEffect && !status.isEditing"><span class="temp pull-right"><a href="javascript:;"  ng-click="mustScope.clickedIndex=$index;copy(eachRule);status.isEditing=true">Edit</a>   &nbsp;<a href="javascript:;" ng-click="deleteRule($index,mustRules)"> Delete</a>&nbsp;</span></div>
								</div>
									<div ng-if="mustScope.clickedIndex==$index && must" class="row">										
											<div class="col-md-12 col-sm-12">
												<div class="well edit-section well-no-margin" ng-init="must.show=false">								
												    	<form class="form-horizontal" role="form">
														<div class="form-group" style="margin-bottom: 0px;">
																					
															 <div class="col-md-12 col-sm-12" style="margin-top: 10px !important">
																	<div class="col-md-2 col-sm-2"> To buy</div>																	
																	<div class="col-md-4 col-sm-6" ng-init="must.src.id= must.src.id==null ? dupAllTickets[0].id : must.src.id ">
																		 <select class="col-md-9 col-sm-9 form-control" ng-options="item.id as item.label for item in dupAllTickets"  ng-model="must.src.id" ng-change="refreshDup()"></select>														
																			&nbsp;
																	</div>																	
																	<div class="col-md-2 col-sm-2">must buy</div>
																	<div class="col-md-4 col-sm-6">																   
																			<select  class="col-md-9 col-sm-9 form-control" 
																	      ng-options="o.value as o.label for o in ruleTypes"
																	      ng-model="must.condition"></select>
																		
																	</div>
																	<div style="clear:both"></div>
												              </div>	
												              													
																<div class="col-md-12 col-sm-12">
																	<div class="col-md-12 col-sm-12">
																		<div ng-repeat="ticket in dupAllTickets track by $index" >	
																		<div  ng-if="must.src.id && ticket.id != must.src.id ">													
																		       <label  class="sm-font" > <input  icheck type="checkbox" ng-model="ticket.check" value="{{ticket.id}}"/>&nbsp;&nbsp;{{ticket.label}}
																			   </label>		
																			   <div class="background-options sm-font" ng-if="ticket.check" ng-init="obj=ticket;init(obj);obj.type==null ? 'NOLIMIT' : obj.type">
																					<div class="radio col-md-12 col-sm-12">
																						<label>
																							<input type="radio" 
																								ng-model="obj.type" value="NOLIMIT"
																								ng-click="obj.type='NOLIMIT'" /><span
																								
																								ng-class="{'opacityClass': obj.type=='LIMIT'}"> For
																								any quantity of <b>{{getNameById(obj.id)}},</b> any quantity of
																								<b>{{getNameById(must.src.id)}}</b> is allowed.</span>
											
																						</label>
																					</div>																			
																					
																					<div ng-if="!ticket.is_donation" class="col-md-12 col-sm-12 radio" style="z-index: 1;"
																						ng-class="{'opacityClass': obj.type=='NOLIMIT'}">
																						<label>
																							<input type="radio"   style="margin-top: 10px;" 
																								ng-model="obj.type" value="LIMIT"
																								ng-click="obj.type='LIMIT'" /> <span> For every
																									<select ng-disabled="obj.type=='NOLIMIT'" class="qty-sel form-control" ng-init="minMax=getMINMAX(obj.id);obj.qty=obj.qty==null?minMax.min_qty:obj.qty" ng-options="n for n in [] | range:minMax.min_qty:minMax.max_qty" ng-model="obj.qty"></select>
																								<span>
																									quantity  of <b>{{getNameById(obj.id)}}</b></span>, allow
																								minimum
																								<select ng-disabled="obj.type=='NOLIMIT'" class="qty-sel form-control" ng-init="minMax=getMINMAX(must.src.id); obj.min= obj.min==null || parseInt(obj.min) < parseInt(minMax.min_qty) ? minMax.min_qty : obj.min" ng-options="n for n in [] | range:minMax.min_qty:minMax.max_qty" ng-model="obj.min"></select>
																								and maximum
																								<select ng-disabled="obj.type=='NOLIMIT'" class="qty-sel form-control"  ng-init="minMax=getMINMAX(must.src.id);obj.max=obj.max==null ||  parseInt(obj.max) >  parseInt(minMax.max_qty) ? minMax.max_qty : obj.max" ng-options="n for n in [] | range:minMax.min_qty:minMax.max_qty" ng-model="obj.max"></select>
																								of
																								<b>{{getNameById(must.src.id)}}</b>.
																							</span>
																						</label>
																					</div>
																					<div style="clear: both"></div>
																				</div>		
																			</div>							
																	   </div><!-- ng repeat tickets -->												
																	</div>
																</div>
														</div><!-- form group -->		
													</form>		
													<div  class="center">											
														<span >
															<button class="btn btn-primary" ng-disabled="(!must.src.id || !isSelected())"   ng-click="addClick($index)">Save</button>
															<button class="btn btn-cancel"   ng-click="cancelClick(mustScope);"><i class="fa fa-times" ></i></button>
														</span><br>
													</div>
													<div style="clear:both"></div>													
												</div><!-- well close -->
											</div>
									</div><!-- ng-if end -->								
							</td>											
						</tr>
					</tbody>
				</table>		
				<!-- for new entry -->
					<div ng-if="mustScope.clickedIndex==-1 && must" class="row">		
											<div class="col-md-12 col-sm-12">
												<div class="well" ng-init="must.show=false">								
													<form class="form-horizontal" role="form">
														<div class="form-group" style="margin-bottom: 0px;">																					
															 <div class="col-md-12 col-sm-12" style="margin-top: 10px !important">
																	<div class="col-md-2 col-sm-2"> To buy</div>																	
																	<div class="col-md-4 col-sm-6" ng-init="must.src.id= must.src.id==null ? dupAllTickets[0].id : must.src.id ">
																		 <select class="form-control" ng-options="item.id as item.label for item in dupAllTickets"  ng-model="must.src.id" ng-change="refreshDup()"></select>														
																			&nbsp;
																	</div>
																	
																	<div class="col-md-2 col-sm-2">must buy</div>
																	<div class="col-md-4 col-sm-6">																   
																			<select  class="col-md-9 col-sm-9 form-control" 
																	      ng-options="o.value as o.label for o in ruleTypes"
																	      ng-model="must.condition"></select>
																		
																	</div>
																	
																	<div style="clear:both"></div>
												               </div>
															
															<div class="col-md-12 col-sm-12">
																<div class="col-md-12 col-sm-12">
																	<div ng-repeat="ticket in dupAllTickets track by $index" >														
																	   <div  ng-if="must.src.id && ticket.id != must.src.id ">	   
																	       <label  class="sm-font"><input icheck type="checkbox" ng-model="ticket.check" value="{{ticket.id}}"/>&nbsp;&nbsp;{{ticket.label}}
																	      </label>	
																	   
																		    <div class="background-options sm-font" ng-if="ticket.check" ng-init="obj = ticket;init(obj);obj.type==null ? 'NOLIMIT' : obj.type">
																				<div class="radio col-md-12 col-sm-12">
																					<label>
																						<input type="radio"
																							ng-model="obj.type" value="NOLIMIT"
																							ng-click="obj.type='NOLIMIT'" /><span
																							
																							ng-class="{'opacityClass': obj.type=='LIMIT'}"> For
																							any quantity of <b>{{getNameById(obj.id)}},</b> any quantity of
																							<b>{{getNameById(must.src.id)}}</b> is allowed.</span>
										
																					</label>
																				 </div>																			
																				
																				<div ng-if="!ticket.is_donation" class="col-md-12 col-sm-12 radio" style="z-index: 1;"
																					ng-class="{'opacityClass': obj.type=='NOLIMIT'}">
																					<label>
																						<input type="radio"   style="margin-top: 10px;" 
																							ng-model="obj.type" value="LIMIT"
																							ng-click="obj.type='LIMIT'" /> <span> For every
																							<select ng-disabled="obj.type=='NOLIMIT'" class="qty-sel form-control" ng-init="minMax=getMINMAX(obj.id);obj.qty=obj.qty==null?minMax.min_qty:obj.qty" ng-options="n for n in [] | range:minMax.min_qty:minMax.max_qty" ng-model="obj.qty"></select>
																							<span>
																								quantity  of <b> {{getNameById(obj.id)}} </b></span>, allow
																							 minimum
																							<select ng-disabled="obj.type=='NOLIMIT'" class="qty-sel form-control" ng-init="minMax=getMINMAX(must.src.id);obj.min=obj.min==null ||  parseInt(obj.min) <  parseInt(minMax.min_qty) ? minMax.min_qty:obj.min" ng-options="n for n in [] | range:minMax.min_qty:minMax.max_qty" ng-model="obj.min"></select>
																							and maximum 
																							<select ng-disabled="obj.type=='NOLIMIT'" class="qty-sel form-control" ng-init="minMax=getMINMAX(must.src.id);obj.max=obj.max==null ||   parseInt(obj.max) >   parseInt(minMax.max_qty) ? minMax.max_qty:obj.max" ng-options="n for n in [] | range:minMax.min_qty:minMax.max_qty" ng-model="obj.max"></select>
																							of
																							<b>{{getNameById(must.src.id)}}</b>.
																						</span>
																					</label>
																				</div>
																				<div style="clear: both"></div>
																			</div>	
																	  </div>													
																   </div><!-- ng repeat tickets -->															
																
																	<div style="clear: both"></div>								
																</div>
															</div>								
														</div>
													</form>		
													<div  class="center">											
														<span>
															<button class="btn btn-primary" ng-disabled="(!must.src.id || !isSelected())" ng-click="addClick('new')">Add</button>
															<button class="btn btn-cancel"   ng-click="cancelClick(mustScope);"><i class="fa fa-times" ></i></button>
														</span><br>
													</div>
													<div style="clear:both"></div>													
												</div><!-- well close -->
											</div>
									</div><!-- ng-if end -->	
				<!-- for new entry end -->				
		</div>
		<div ng-show="activeTab=='Block' " ng-controller="blockController as blockScope">
			 <div ng-show="blockRules.length<=0 && !status.isEditing" class="not-found">{{noRule}}</div>
				<table class="table table-hover" ng-init="thisScope = blockScope">
					<tbody>
						<tr ng-repeat="eachRule in blockRules track by $index"  ng-init="hoverEffect=false;" ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()">	
							<td>
								<div class="row"  ng-show="eachRule.src.id &&  eachRule.trg.length > 0">
									<div   class="col-md-8">
										<span> {{$index+1}}.&nbsp;&nbsp;&nbsp;If 
											 <b>{{getNameById(eachRule.src.id)}}</b>
											  ticket is bought, can't buy  
											   <span ng-repeat="childRule in eachRule.trg"> 
											   		 <span ng-if="!$first && !$last">, </span> 
											   		 <span ng-if="$last && $index > 0 ">and </span> 
											   		 <b>{{getNameById(childRule.id)}}</b>
											   		 <span ng-if="$last"> ticket<span ng-if="eachRule.trg.length > 1 ">s</span>.</span> 
											    </span>
										</span>
									</div>
									<div class="col-md-4 sm-font"   ng-show="hoverEffect && !status.isEditing"><span class="temp pull-right"><a href="javascript:;"  ng-click="blockScope.clickedIndex=$index;copy(eachRule);status.isEditing=true">Edit</a>   &nbsp;<a href="javascript:;" ng-click="deleteRule($index,blockRules)"> Delete</a>&nbsp;</span></div>
								</div>
									<div ng-if="blockScope.clickedIndex==$index && rule " class="row">
										<div class="col-md-12">
											<div class="well  edit-section well-no-margin" 
												ng-class="{'errorMSG':(!rule.src.id || rule.trg.length <= 0)}">
												
												<div class="row col-md-12"  ng-init="rule.src.id= rule.src.id==null ? dupAllTickets[0].id : rule.src.id " >	
												     <div class="col-md-4 col-sm-4">	
												        <span class="col-md-2 col-sm-4">If</span>	
												        <span class="col-md-10 col-sm-8">																						
															 <select class=" form-control" ng-options="item.id as item.label for item in dupAllTickets"  ng-model="rule.src.id" ng-change="refreshDup()"></select>														
															&nbsp;
														</span>
													 </div>
													 <div class="col-md-8 col-sm-8">
													<span >is bought, can't buy </span>
													</div>						
												</div>										
												<div class="row col-md-12">														
														<div class="col-md-12 col-sm-12">
														   <div class="col-md-12 col-sm-12">	
																<div ng-repeat="ticket in dupAllTickets track by $index" >														
																      <label  class="sm-font"  ng-if="rule.src.id && ticket.id != rule.src.id"> <input icheck type="checkbox" ng-model="ticket.check" value="{{ticket.id}}"/>&nbsp;&nbsp;{{ticket.label}}
																	</label>														
																</div>
															</div>
														</div>				
												</div>
												<div class="center">
													<span>
													<button class="btn btn-primary" ng-disabled="!isValid()" ng-click="addClick($index)">Save</button>
													<button class="btn btn-cancel"   ng-click="cancelClick(blockScope);"><i class="fa fa-times" ></i></button>
												   </span><br>
												</div>
												<div style="clear:both"></div>
											</div>
										</div>
									</div>
								
							</td>											
						</tr>
					</tbody>
				</table>
				
				<!-- for new entry -->
							<div ng-if="blockScope.clickedIndex==-1 && rule" class="row">
										<div class="col-md-12">
											<div class="well" 
												ng-class="{'errorMSG':(!rule.src.id || rule.trg.length <= 0)}">
												
												<div class="row col-md-12"  ng-init="rule.src.id=dupAllTickets[0].id" >	
												     <div class="col-md-4 col-sm-4">	
												         <span class="col-md-2 col-sm-4">If</span>	
													        <span class="col-md-10 col-sm-8">																						
															 <select class="form-control" ng-options="item.id as item.label for item in dupAllTickets"  ng-model="rule.src.id" ng-change="refreshDup()"></select>														
															&nbsp;
														  </span>
													 </div>
													 <div class="col-md-8 col-sm-8">
													<span >is bought, can't buy </span>
													</div>						
												</div>										
												<div class="row col-md-12">														
														<div class="col-md-12 col-sm-12">
														   <div class="col-md-12 col-sm-12">	
																<div ng-repeat="ticket in dupAllTickets track by $index" >														
																      <label  class="sm-font" ng-if="rule.src.id && ticket.id != rule.src.id" > <input icheck type="checkbox" ng-model="ticket.check" value="{{ticket.id}}"/>&nbsp;&nbsp;{{ticket.label}}
																	</label>														
																</div>
															</div>
														</div>				
												</div>
												<div class="center">
													<span>
														<button class="btn btn-primary" ng-disabled="!isValid()" ng-click="addClick('new')">Add</button>
														<button class="btn btn-cancel"  ng-click="cancelClick(blockScope);"><i class="fa fa-times" ></i></button>
													</span><br>
												</div>
												<div style="clear:both"></div>
											</div>
										</div>
								</div>		
				
				<!-- for new entry end -->
		</div>
	</div>

	</div><!-- CT controller end -->
	
	</div>
	
</div><!-- conditional ticketing section close -->	 

  <!-- conditional ticketing scripts  -->
	<script src="/main/angular/angular.min.js "></script>
	<script src="/main/angular/conditionalticketing/app.js"></script> 
	<!-- conditional ticketing end -->
  --%>

	<!-- Modal -->
	<div class="modal" id="myModalhtmlin" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" style="z-index: 9999;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3>
						<label class='headlbl'></label>
					</h3>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer"></div>
			</div>

		</div>
		<div class="modal-backdrop fade in"></div>
	</div>
	<script>
		var eid = '${eid}';
		var isrecuring = '${isrecurring}';
		var currenySymbol = '${currenySymbol}';
		var totalSoldCount=0;
	</script>
	
	
	<%-- <script type="text/javascript" src="TicketURLs!populateTicketurlsList?eid=${eid}"></script> --%>
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script src="/main/js/eventmanage/ticketing.js?id=1"></script>
	<%-- <script src="/main/js/eventmanage/ticketurls.js"></script> --%>
	
	<%-- <script src="/main/bootstrap/js/jquery.twbsPagination.min.js"></script> --%>
	<script>
		window.resizeIframe = function() {
			var obj = document.getElementById('popup');
			obj.style.height = obj.contentWindow.document.body.scrollHeight
					+ 'px';
		}
	</script>
	<style>

</style>

	

	<script>
		$(function() {		
				
			$('#contact').click(
					function() {
						$('.modal-title').html(props.la_supp_popup_hdr);
						$('iframe#popup').attr("src",
								'/main/user/homepagesupportemail.jsp');
						$('iframe#popup').css("height", "440px");
						$('#myModal').modal('show');
					});

			$('#getTickets').click(
					function() {
						$('.modal-title').html(props.la_get_tkts_popup_hdr);
						$('iframe#popup').attr("src", '/main/user/homepagemytickets.jsp');
						$('iframe#popup').css("height", "435px");
						$('#myModal').modal('show');
					});

			$('#myModal').modal({
				backdrop : 'static',
				keyboard : false,
				show : false
			});
			$('#myModal').on('hide.bs.modal',function() {
								$('iframe#popup').attr("src", '');
								$('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" style="height:440px" frameborder="0" onload="modalOnLoad()"></iframe>');
							});
			/* if((subMgr==null || subMgr==undefined || subMgr=='') && isrecuring=='false'){
				$.ajax({
					   url:'WaitList',
					   data:{eid:eventid},
					   success:function(result){
						   $("#tickets_waitlist_data").html(result);
						}
					});
			} */


			 //$('#ticketurlinfo').tooltipster({fixedWidth:'100px',position: 'right'});

			
			});

		/* var isValidActionMessage = function(messageText) {
			if (messageText.indexOf('nologin') > -1
					|| messageText.indexOf('login!authenticate') > -1) {
				alert('You need to login to perform this action');
				window.location.href = "/main/user/login";
				return false;
			} else if (messageText.indexOf('Something went wrong') > -1) {
				alert('Sorry! You do not have permission to perform this action');
				closediv();
				return false;
			}
			return true;
		} */

		function modalOnLoad() {
			hidePopup();
		}

		if(isrecuring=='true'){
			//$('#addticket').html('+ Ticket (All dates)');
			changeDate();
		}else
            drawChart();		


/* if(subMgr==null || subMgr==undefined || subMgr==''){
	getURLsReloadedData();
	$('#subManagerTicketUrls').show();
}else{
	if(subTicketUrls=='yes'){
		$('#subManagerTicketUrls').show();
		 getURLsReloadedData();
	}else
		$('#subManagerTicketUrls').hide();
} */

function changeDate(){
	
	loadingPopup();
	var eventDate='';
	if(document.getElementById('source'))
		eventDate=document.getElementById('source').value;
	$.ajax({
		   url:'ManageTickets!populateTicketJson',
		   data:{eid:eventid,eventDate:eventDate},
		   success:function(result){
			   enableTicketDonationButtons();
			   $('#tktbox').insertAfter($('#tickets'));
			   $('#donationbox').insertAfter($('#tickets'));
			   $('#groupbox').insertAfter($('#donationbox'));
			   tktdata=new Object();
			   totalSoldCount=0;
			   totalTktCount=0;
			tktdata=JSON.parse(result);
			$('#tickets').html('');
			drawChart();
			hidePopup();
			   }
		});
	
}
	
		$.each(tktdata.TicketDetails, function(inkey, data) {
			if (data.ttype == 'Ticket Group')
				groupTickets.push(data.gid);
			else
				tktGrps.push(data.gid);
		});

		var isAdded = false;
		if (groupTickets.length > 0) {
			$.each(groupTickets, function(i, dat) {
				$.each(tktdata.TicketDetails, function(inkey, indata) {
					if (indata.ttype != 'Ticket Group' && dat != indata.gid) {
						if ($.inArray(dat, emptyGrps) < 0
								&& $.inArray(dat, tktGrps) < 0) {
							emptyGrps.push(dat);
						}
					}
				});

			});
		}
	</script>
