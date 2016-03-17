<%@taglib uri="/struts-tags" prefix="s"%>
<%@include file="../getresourcespath.jsp"%>
<style>
.edit-section{
  margin-top:10px !important;
}
</style>
<script src="/main/angular/angular.min.js "></script>
<script src="/main/angular/conditionalticketing/app.js"></script>
<script>
var existingRules = '${existingRules}';
var tktdata = ${jsonData};
var eventid = '${eid}';
var currentEventLevel='<s:property value="currentLevel"></s:property>';
var requireLevel="400";
</script>

<div id="conditionalticketing" ng-app="CT" ng-controller='ctController'  ng-cloak>
<!-- <div class="section-main-header">
				Ticketing Rules
			</div> -->
			
			
			
			<div id="rule_btn" class="row sticky-out-button pull-right">				
				 <button ng-if=" activeTab=='Required' " type="button" ng-disabled="scopes.requireController.requireRuleAddState()"
					class="btn btn-primary  pull-right "
					ng-click="requireClick(-1)">
					+<s:text name="tickt.rule.lbl"/>
				</button>
				
				<button ng-if=" activeTab=='Must' " type="button" ng-disabled="scopes.conditionalController.mustRulesAddState()"
					class="btn btn-primary  pull-right "
					ng-click="mustClick(-1)">
					+<s:text name="tickt.rule.lbl"/>
				</button>
				
				<button ng-if=" activeTab=='Block' " ng-disabled="scopes.blockController.blockRulesAddState()" type="button"
					class="btn btn-primary pull-right"
					ng-click="blockClick(-1)" style="">
					+<s:text name="tickt.rule.lbl"/>
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
<s:text name="ticket.no.ticketing.rules.lbl"/>
</div>
<div  ng-show="isMsgShow" class="not-found">
<s:text name="min.two.tickts.create.tktrules.msg"/>
</div>  
<div id ="ticketingRules" class="row"  ng-show="isTabShow">		
	<div class="col-md-12">		
			<div class="center btn-group btn-toggle" data-toggle="buttons">
				<label ng-click="tabChange('Required')"	ng-class="{'btn-default': activeTab=='Required','btn-active': activeTab!='Required'}"  class="optiontype btn  no-radius">
					<input type="radio"> <s:text name="tickt.must.buy"/>
				</label>
				<label ng-click="tabChange('Must')"	ng-class="{'btn-default': activeTab=='Must','btn-active': activeTab!='Must'}" class="optiontype btn  no-radius">
					<input class="datesel" id="all" name="events" value="2" type="radio"><s:text name="tickt.conditional.buy"/>
				</label>
				<label  ng-click="tabChange('Block')"	ng-class="{'btn-default': activeTab=='Block','btn-active': activeTab!='Block'}" class="optiontype btn  no-radius">
					<input  type="radio"> <s:text name="tickt.cant.buy"/>
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
										<span> {{$index+1}}.&nbsp;&nbsp;&nbsp;<s:text name="tickt.must.buy"/>
											<span ng-if="eachRule.trg.length > 1"> 
												<span ng-if="eachRule.condition=='requireOR'"> <s:text name="tickt.atleast.one.of"/></span>
												<span ng-if="eachRule.condition=='requireAND'"> <s:text name="tickt.all.of"/></span> 
											</span> 
											<span ng-repeat="childRule in eachRule.trg"> 
												<span ng-if="!$first">, </span> 
												<%-- <span ng-if="$last && $index > 0">and </span>  --%>
												 <b>{{getNameById(childRule.id)}}</b> 
												 <span ng-if="$last"> 
												   <s:text name="tickt.ticket.lbl"/><span ng-if="eachRule.trg.length > 1 ">s</span>
												  <s:text name="tickt.to.cmplte.registration.lbl"/>. </span> 
											</span>
										</span>
									</div>
									<div class="col-md-4 sm-font"   ng-show="hoverEffect && !status.isEditing" ><span class="temp pull-right"><a href="javascript:;"  ng-click="requireScope.clickedIndex=$index;copy(eachRule);status.isEditing=true"><s:text name="global.edit.lnk.lbl"/></a>   &nbsp;<a href="javascript:;" ng-click="deleteRule($index,requireRules)"> <s:text name="global.delete.lnk.lbl"/></a>&nbsp;</span></div>
								</div>
								<div ng-if="requireScope.clickedIndex==$index && require" class="row" >
									<div class="col-md-12 col-sm-12"> 
									  <div class="well well-no-margin edit-section">
										 	<div class="col-md-12 col-sm-12" style="margin-top: 10px !important">
												<div class="col-md-4 col-sm-6"><s:text name="tickt.to.cmplte.registration"/>, <s:text name="tickt.must.buy.lbl1"/></div>
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
													<button class="btn btn-primary" ng-disabled="!isValid()" ng-click="requireScope.clickedIndex=-2;saveClick($index);"><s:text name="global.save.btn.lbl"/></button>
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
											<div class="col-md-4 col-sm-6"><s:text name="tickt.to.cmplte.registration"/>, <s:text name="tickt.must.buy.lbl1"/></div>
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
												<button class="btn btn-primary" ng-disabled="!isValid()" ng-click="requireScope.clickedIndex=-2;saveClick('new');"><s:text name="global.save.btn.lbl"/></button>
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
										<span> {{$index+1}}.&nbsp;&nbsp;&nbsp;<s:text name="tickt.to.buy.lbl"/>  
											 <b>{{getNameById(eachRule.src.id)}}</b> 
											 <s:text name="tickt.ticket.lbl"/>, <s:text name="tickt.must.buy.lbl1"/>
											  <span ng-if="eachRule.trg.length > 1">
											  	 <span ng-if="eachRule.condition=='MustByOR'"> <s:text name="tickt.atleast.one.of"/>  </span>
											  	 <span ng-if="eachRule.condition=='MustByAND'"> <s:text name="tickt.all.of"/> </span> 
											  </span> 
											  <span ng-repeat="childRule in eachRule.trg">
											  		<span ng-if="!$first">, </span>
											  		<%-- <span ng-if="$last && $index > 0 "> and </span> --%>
											  		<b>{{getNameById(childRule.id)}} </b>
											  		<span ng-if="$last"> <s:text name="tickt.ticket.lbl"/><span ng-if="eachRule.trg.length > 1 ">s</span>.</span>
											   </span>
										</span>
									</div>
									<div class="col-md-4 sm-font"   ng-show="hoverEffect && !status.isEditing"><span class="temp pull-right"><a href="javascript:;"  ng-click="mustScope.clickedIndex=$index;copy(eachRule);status.isEditing=true"><s:text name="global.edit.lnk.lbl"/></a>   &nbsp;<a href="javascript:;" ng-click="deleteRule($index,mustRules)"> <s:text name="global.delete.lnk.lbl"/></a>&nbsp;</span></div>
								</div>
									<div ng-if="mustScope.clickedIndex==$index && must" class="row">										
											<div class="col-md-12 col-sm-12">
												<div class="well edit-section well-no-margin" ng-init="must.show=false">								
												    	<form class="form-horizontal" role="form">
														<div class="form-group" style="margin-bottom: 0px;">
																					
															 <div class="col-md-12 col-sm-12" style="margin-top: 10px !important">
																	<div class="col-md-2 col-sm-3" style="margin-top:7px;"> <div class="text-center"><s:text name="tickt.to.buy.lbl"/></div></div>																	
																	<div class="col-md-3 col-sm-3" style="padding-right:5px;" ng-init="must.src.id= must.src.id==null ? dupAllTickets[0].id : must.src.id ">
																		 <select class="col-md-9 col-sm-9 form-control" ng-options="item.id as item.label for item in dupAllTickets"  ng-model="must.src.id" ng-change="refreshDup()"></select>													
																			&nbsp;
																	</div>
																	<div class="col-md-2 col-sm-3" style="margin-top:7px;"><div class="text-center">, <s:text name="tickt.must.buy.lbl1"/></div></div>
																	<div class="col-md-3 col-sm-3">																   
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
																								
																								ng-class="{'opacityClass': obj.type=='LIMIT'}">
																								 <s:text name="tickt.for.any.quntity.lbl"/> <b>{{getNameById(obj.id)}},</b> <s:text name="tickt.any.quntity.lbl"/>
																								<b>{{getNameById(must.src.id)}}</b> <s:text name="tickt.is.allowed.lbl"/></span>
											
																						</label>
																					</div>																			
																					
																					<div ng-if="!ticket.is_donation" class="col-md-12 col-sm-12 radio" style="z-index: 1;"
																						ng-class="{'opacityClass': obj.type=='NOLIMIT'}">
																						<label>
																							<input type="radio"   style="margin-top: 10px;" 
																								ng-model="obj.type" value="LIMIT"
																								ng-click="obj.type='LIMIT'" /> <span> <s:text name="tickt.for.every.lbl"/>
																									<select ng-disabled="obj.type=='NOLIMIT'" class="qty-sel form-control" ng-init="minMax=getMINMAX(obj.id);obj.qty=obj.qty==null?minMax.min_qty:obj.qty" ng-options="n for n in [] | range:minMax.min_qty:minMax.max_qty" ng-model="obj.qty"></select>
																								<span>
																									<s:text name="tickt.quantity.of.lbl"/> <b>{{getNameById(obj.id)}}</b></span>, <s:text name="tickt.allow.mini.lbl"/>
																								<select ng-disabled="obj.type=='NOLIMIT'" class="qty-sel form-control" ng-init="minMax=getMINMAX(must.src.id); obj.min= obj.min==null || parseInt(obj.min) < parseInt(minMax.min_qty) ? minMax.min_qty : obj.min" ng-options="n for n in [] | range:minMax.min_qty:minMax.max_qty" ng-model="obj.min"></select>
																								<s:text name="tickt.and.max.lbl"/>
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
															<button class="btn btn-primary" ng-disabled="(!must.src.id || !isSelected())"   ng-click="addClick($index)"><s:text name="global.save.btn.lbl"/></button>
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
																	<div class="col-md-2 col-sm-3" style="margin-top:7px;"> <s:text name="tickt.to.buy.lbl"/></div>																	
																	<div class="col-md-3 col-sm-3" ng-init="must.src.id= must.src.id==null ? dupAllTickets[0].id : must.src.id ">
																		 <select class=" col-md-9 col-sm-9 form-control" ng-options="item.id as item.label for item in dupAllTickets"  ng-model="must.src.id" ng-change="refreshDup()"></select>														
																			&nbsp;
																	</div>
																	
																	<div class="col-md-2 col-sm-3" style="margin-top:7px;">, <s:text name="tickt.must.buy.lbl1"/></div>
																	<div class="col-md-3 col-sm-3">																   
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
																							
																							ng-class="{'opacityClass': obj.type=='LIMIT'}"> 
																							<s:text name="tickt.for.any.quntity.lbl"/> <b>{{getNameById(obj.id)}},</b> <s:text name="tickt.any.quntity.lbl"/>
																							<b>{{getNameById(must.src.id)}}</b> <s:text name="tickt.is.allowed.lbl"/></span>
										
																					</label>
																				 </div>																			
																				
																				<div ng-if="!ticket.is_donation" class="col-md-12 col-sm-12 radio" style="z-index: 1;"
																					ng-class="{'opacityClass': obj.type=='NOLIMIT'}">
																					<label>
																						<input type="radio"   style="margin-top: 10px;" 
																							ng-model="obj.type" value="LIMIT"
																							ng-click="obj.type='LIMIT'" /> <span> <s:text name="tickt.for.every.lbl"/>
																							<select ng-disabled="obj.type=='NOLIMIT'" class="qty-sel form-control" ng-init="minMax=getMINMAX(obj.id);obj.qty=obj.qty==null?minMax.min_qty:obj.qty" ng-options="n for n in [] | range:minMax.min_qty:minMax.max_qty" ng-model="obj.qty"></select>
																							<span>
																								<s:text name="tickt.quantity.of.lbl"/> <b> {{getNameById(obj.id)}} </b></span>, <s:text name="tickt.allow.mini.lbl"/>
																							<select ng-disabled="obj.type=='NOLIMIT'" class="qty-sel form-control" ng-init="minMax=getMINMAX(must.src.id);obj.min=obj.min==null ||  parseInt(obj.min) <  parseInt(minMax.min_qty) ? minMax.min_qty:obj.min" ng-options="n for n in [] | range:minMax.min_qty:minMax.max_qty" ng-model="obj.min"></select>
																							<s:text name="tickt.and.max.lbl"/> 
																							<select ng-disabled="obj.type=='NOLIMIT'" class="qty-sel form-control" ng-init="minMax=getMINMAX(must.src.id);obj.max=obj.max==null ||   parseInt(obj.max) >   parseInt(minMax.max_qty) ? minMax.max_qty:obj.max" ng-options="n for n in [] | range:minMax.min_qty:minMax.max_qty" ng-model="obj.max"></select>
																							<s:text name="tickt.of.lbl"/>
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
															<button class="btn btn-primary" ng-disabled="(!must.src.id || !isSelected())" ng-click="addClick('new')"><s:text name="global.add.btn.lbl"/></button>
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
										<span> {{$index+1}}.&nbsp;&nbsp;&nbsp;<s:text name="tickt.if.lbl"/> 
											 <b>{{getNameById(eachRule.src.id)}}</b>
											  <s:text name="tickt.is.bought.lbl"/>, <s:text name="tickt.cant.buy.lbl1"/>  
											   <span ng-repeat="childRule in eachRule.trg"> 
											   		 <span ng-if="!$first && !$last">, </span> 
											   		 <span ng-if="$last && $index > 0 "><s:text name="tickt.and.lbl"/> </span> 
											   		 <b>{{getNameById(childRule.id)}}</b>
											   		 <span ng-if="$last"> <s:text name="tickt.ticket.lbl"/><span ng-if="eachRule.trg.length > 1 ">s</span>.</span> 
											    </span>
										</span>
									</div>
									<div class="col-md-4 sm-font"   ng-show="hoverEffect && !status.isEditing"><span class="temp pull-right"><a href="javascript:;"  ng-click="blockScope.clickedIndex=$index;copy(eachRule);status.isEditing=true"><s:text name="global.edit.lnk.lbl"/></a>   &nbsp;<a href="javascript:;" ng-click="deleteRule($index,blockRules)"> <s:text name="global.delete.lnk.lbl"/></a>&nbsp;</span></div>
								</div>
									<div ng-if="blockScope.clickedIndex==$index && rule " class="row">
										<div class="col-md-12">
											<div class="well  edit-section well-no-margin" 
												ng-class="{'errorMSG':(!rule.src.id || rule.trg.length <= 0)}">
												
												<div class="row col-md-12"  ng-init="rule.src.id= rule.src.id==null ? dupAllTickets[0].id : rule.src.id " >	
												     <div class="col-md-4 col-sm-4">	
												        <span class="col-md-2 col-sm-4"><s:text name="tickt.if.lbl"/></span>	
												        <span class="col-md-10 col-sm-8">																						
															 <select class=" form-control" ng-options="item.id as item.label for item in dupAllTickets"  ng-model="rule.src.id" ng-change="refreshDup()"></select>														
															&nbsp;
														</span>
													 </div>
													 <div class="col-md-8 col-sm-8">
													<span ><s:text name="tickt.is.bought.lbl"/>, <s:text name="tickt.cant.buy.lbl1"/>  </span>
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
													<button class="btn btn-primary" ng-disabled="!isValid()" ng-click="addClick($index)"><s:text name="global.save.btn.lbl"/></button>
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
												         <span class="col-md-2 col-sm-4"><s:text name="tickt.if.lbl"/></span>	
													        <span class="col-md-10 col-sm-8">																						
															 <select class="form-control" ng-options="item.id as item.label for item in dupAllTickets"  ng-model="rule.src.id" ng-change="refreshDup()"></select>														
															&nbsp;
														  </span>
													 </div>
													 <div class="col-md-8 col-sm-8">
													<span ><s:text name="tickt.is.bought.lbl"/>, <s:text name="tickt.cant.buy.lbl1"/> </span>
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
														<button class="btn btn-primary" ng-disabled="!isValid()" ng-click="addClick('new')"><s:text name="global.add.btn.lbl"/></button>
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
	
</div>
<%-- <script src="/main/js/eventmanage/ticketing.js?id=1"></script> --%>