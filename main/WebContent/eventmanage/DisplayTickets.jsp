<%@taglib uri="/struts-tags" prefix="s"%>

<s:form id="DisplayTicketsform" theme="simple" name="DisplayTicketsform"
	action="DisplayTickets!save">
	<s:hidden name="eid"></s:hidden>
	<s:hidden name="formatId"></s:hidden>

	 
		<!-- <div class="col-md-12">
			<div id="successmsg"></div>
		</div> --> 
	
	
	<!-- <div class="row"> -->
		<!-- <div class="col-md-12"> -->
			<div style="clear: both;" class="bottom-gap" id="gapid"></div>
				<div id="successmsg"></div>
				


					<div class="row">
						<div class="col-md-5">
							<s:text name="dt.sold.out.tickets.section.header.lbl"></s:text>
						</div>
					</div>
					<br>

					<div class="row">
						<div class="col-md-4 col-sm-4  "><s:text name="dt.ticket.display.lbl"></s:text></div>
						<s:set name="soldouttktmsg" value="ticketDisplayOptionsMap['event.soldouttickets.status']"></s:set>
						<div class="col-md-4 col-sm-6 col-xs-12">
							<div data-toggle="buttons" class="center btn-group btn-toggle ">
								<label
									onclick="showDiv('soldouttickets');makeActive('soldouthide','soldoutshow')"
									id="soldoutshow"
									class="optiontype btn btn-xs no-radius <s:if test='%{#soldouttktmsg == "yes"}'>btn-default</s:if><s:else>btn-active</s:else>">
									<input type="radio" id="soldoutshow_radio" value="yes"
									name="ticketDisplayOptionsMap['event.soldouttickets.status']"
									class="datesel" /> <s:text name="dt.display.btngroup.show.lbl"></s:text>
								</label> <label
									onclick="hideDiv('soldouttickets');makeActive('soldoutshow','soldouthide')"
									id="soldouthide"
									class="optiontype btn btn-xs no-radius <s:if test='%{#soldouttktmsg == "no"}'>btn-default</s:if><s:else>btn-active</s:else>">
									<input type="radio" id="soldouthide_radio" value="no"
									name="ticketDisplayOptionsMap['event.soldouttickets.status']"
									class="datesel" /><s:text name="dt.display.btngroup.hide.lbl"></s:text>
								</label>
							</div>
						</div>
						<div class="col-md-4 col-sm-2 "></div>
					</div>
					<br>

					<div id="soldouttickets"
						style="display:<s:if test='%{#soldouttktmsg == "yes"}'>block</s:if><s:else>none</s:else>">
						<div class="row">
							<div class="col-md-4 col-sm-4"><s:text name="dt.ticket.status.message.tb.lbl"></s:text></div>
							<div class="col-md-4 col-sm-6 col-xs-12" style="width: 140px">
								<s:textfield
									name="ticketDisplayOptionsMap['event.soldouttickets.statusmessage']"
									theme="simple" cssClass="form-control"></s:textfield>
							</div>
							<div class="col-md-4 col-sm-2 "></div>
						</div>
						<br>

						<div class="row">
							<div class="col-md-4 col-sm-4"><s:text name="dt.ticket.strikethrough.text.lbl"></s:text></div>
							<s:set name="strikethrough"
								value="ticketDisplayOptionsMap['event.soldouttickets.strikethrough']"></s:set>


							<div class="col-md-4 col-sm-6 col-xs-12">
								<div data-toggle="buttons" class="center btn-group btn-toggle">
									<label
										onclick="makeActive('soldoutticketstrikeno','soldoutticketstrikeyes'  )"
										id="soldoutticketstrikeyes"
										class="optiontype btn btn-xs no-radius <s:if test='%{#strikethrough == "yes"}'> btn-default </s:if><s:else> btn-active </s:else>">
										<input type="radio" id="soldoutticketstrikeyes_radio"
										value="yes"
										name="ticketDisplayOptionsMap['event.soldouttickets.strikethrough']"
										<s:if test="%{#strikethrough == 'yes'}">checked="checked"</s:if>
										class="datesel" /> <s:text name="gllobal.yes.lbl"></s:text>&nbsp;&nbsp;
									</label> <label
										onclick="makeActive('soldoutticketstrikeyes','soldoutticketstrikeno')"
										id="soldoutticketstrikeno"
										class="optiontype btn btn-xs no-radius <s:if test='%{#strikethrough == "no"}'>btn-default  </s:if><s:else> btn-active </s:else>">
										<input type="radio" id="soldoutticketstrikeno_radio"
										value="no"
										name="ticketDisplayOptionsMap['event.soldouttickets.strikethrough']"
										<s:if test="%{#strikethrough == 'no'}">checked="checked"</s:if>
										class="datesel" /><s:text name="global.no.lbl"></s:text>&nbsp;&nbsp;&nbsp;&nbsp;
									</label>
								</div>
							</div>
							<div class="col-md-4 col-sm-2 "></div>

							<%-- <div class="col-md-2">
		  	   		<s:set name="strikethrough" value="ticketDisplayOptionsMap['event.soldouttickets.strikethrough']"></s:set>
					<input type="radio" class="radiobtn" name="ticketDisplayOptionsMap['event.soldouttickets.strikethrough']" value="yes" <s:if test="%{#strikethrough == 'yes'}">checked="checked"</s:if>/>&nbsp;Yes
		  	   		</div>
		  	   		<div class="col-md-4">
		  	   		<input type="radio" class="radiobtn" name="ticketDisplayOptionsMap['event.soldouttickets.strikethrough']" value="no" <s:if test="%{#strikethrough == 'no'}">checked="checked"</s:if>/>&nbsp;No		  	   		
		  	   		</div> --%>


						</div>
						<br>
					</div>
					<!-- soldout tickets  -->
					<br>

					<div class="row">
						<div class="col-md-5">
							<s:text name="dt.not.yet.started.tickets.section.header.lbl"></s:text>
						</div>
					</div>
					<br>

					<div class="row">
						<div class="col-md-4 col-sm-4"><s:text name="dt.ticket.display.lbl"></s:text></div>
						<div class="col-md-4 col-sm-6 col-xs-12">
							<s:set name="notyetstartedtickets"
								value="ticketDisplayOptionsMap['event.notyetstartedtickets.status']"></s:set>

							<div data-toggle="buttons" class="center btn-group btn-toggle">
								<label
									onclick="showDiv('notyetstarted');makeActive('notyethide','notyetshow')"
									id="notyetshow"
									class="optiontype btn btn-xs no-radius <s:if test='%{#notyetstartedtickets == "yes"}'>btn-default</s:if><s:else>btn-active</s:else>">
									<input id="notyetshow_radio" type="radio" value="yes"
									name="ticketDisplayOptionsMap['event.notyetstartedtickets.status']"
									class="datesel" /> <s:text name="dt.display.btngroup.show.lbl"></s:text>
								</label> <label
									onclick="hideDiv('notyetstarted');makeActive('notyetshow','notyethide')"
									id="notyethide"
									class="optiontype btn btn-xs no-radius <s:if test='%{#notyetstartedtickets == "no"}'>btn-default</s:if><s:else>btn-active</s:else>">
									<input id="notyethide_radio" type="radio" value="no"
									name="ticketDisplayOptionsMap['event.notyetstartedtickets.status']"
									class="datesel" /><s:text name="dt.display.btngroup.hide.lbl"></s:text>
								</label>
							</div>
						</div>
						<div class="col-md-4 col-sm-2 "></div>


					</div>
					<br>
					
					<div id="notyetstarted"
						style="display:<s:if test='%{#notyetstartedtickets == "yes"}'>block</s:if><s:else>none</s:else>">

						<div class="row">
							<div class="col-md-4 col-sm-4"><s:text name="dt.display.start.date.lbl"></s:text></div>
							<div class="col-md-4 col-sm-6 col-xs-12">
								<s:set name="notyetstarttime"
									value="ticketDisplayOptionsMap['event.notyetstartedtickets.showstartdate']"></s:set>
								<div data-toggle="buttons" class="center btn-group btn-toggle">
									<label
										onclick="makeActive('notyetstarttimehide','notyetstarttimeshow')"
										id="notyetstarttimeshow"
										class="optiontype btn btn-xs no-radius <s:if test='%{#notyetstarttime == "yes"}'> btn-default </s:if><s:else> btn-active</s:else>">
										<input id="notyetstarttimeshow_radio" type="radio" value="yes"
										name="ticketDisplayOptionsMap['event.notyetstartedtickets.showstartdate']"
										<s:if test="%{#notyetstarttime == 'yes'}">checked="checked"</s:if>
										class="datesel" /> <s:text name="gllobal.yes.lbl"></s:text>
									</label> <label
										onclick="makeActive('notyetstarttimeshow','notyetstarttimehide')"
										id="notyetstarttimehide"
										class="optiontype btn btn-xs no-radius <s:if test='%{#notyetstarttime == "no"}'>btn-default  </s:if><s:else> btn-active </s:else>">
										<input id="notyetstarttimehide_radio" type="radio" value="no"
										name="ticketDisplayOptionsMap['event.notyetstartedtickets.showstartdate']"
										<s:if test="%{#notyetstarttime == 'no'}">checked="checked"</s:if>
										class="datesel" /><s:text name="global.no.lbl"></s:text>
									</label>
								</div>
							</div>
							<div class="col-md-4 col-sm-2 "></div>
							<!-- <input type="radio"  class="radiobtn" name="ticketDisplayOptionsMap['event.notyetstartedtickets.showstartdate']" value="yes" <s:if test="%{#notyetstarttime == 'yes'}">checked="checked"</s:if>/>&nbsp;Yes		  	   		
		  	   		</div>	
		  	   		<div class="col-md-2">
					<input type="radio"  class="radiobtn" name="ticketDisplayOptionsMap['event.notyetstartedtickets.showstartdate']" value="no" <s:if test="%{#notyetstarttime == 'no'}">checked="checked"</s:if>/>&nbsp;No&nbsp;
		  	   		</div> -->
						</div>
						<br>

						<div class="row">
							<div class="col-md-4 col-sm-4"><s:text name="dt.display.end.date.lbl"></s:text></div>
							<div class="col-md-4 col-sm-6 col-xs-12">
								<s:set name="notyetendtime"
									value="ticketDisplayOptionsMap['event.notyetstartedtickets.showenddate']"></s:set>
								<div data-toggle="buttons" class="center btn-group btn-toggle">
									<label
										onclick="makeActive('notyetendtimehide','notyetendtimeshow')"
										id="notyetendtimeshow"
										class="optiontype btn btn-xs no-radius <s:if test='%{#notyetendtime == "yes"}'> btn-default </s:if><s:else> btn-active </s:else>">
										<input id="notyetendtimeshow_radio" type="radio" value="yes"
										name="ticketDisplayOptionsMap['event.notyetstartedtickets.showenddate']"
										<s:if test="%{#notyetendtime == 'yes'}">checked="checked"</s:if>
										class="datesel" /> <s:text name="gllobal.yes.lbl"></s:text>
									</label> <label
										onclick="makeActive('notyetendtimeshow','notyetendtimehide')"
										id="notyetendtimehide"
										class="optiontype btn btn-xs no-radius <s:if test='%{#notyetendtime == "no"}'>btn-default  </s:if><s:else> btn-active  </s:else>">
										<input id="notyetendtimehide_radio" type="radio" value="no"
										name="ticketDisplayOptionsMap['event.notyetstartedtickets.showenddate']"
										<s:if test="%{#notyetendtime == 'no'}">checked="checked"</s:if>
										class="datesel" /><s:text name="global.no.lbl"></s:text>
									</label>
								</div>
							</div>
							<div class="col-md-4 col-sm-2 "></div>

							<!-- <input type="radio"  class="radiobtn" name="ticketDisplayOptionsMap['event.notyetstartedtickets.showenddate']" value="yes" <s:if test="%{#notyetendtime == 'yes'}">checked="checked"</s:if>/>&nbsp;Yes
		  	   		</div>
		  	   		<div class="col-md-4">
                    <input type="radio" class="radiobtn" name="ticketDisplayOptionsMap['event.notyetstartedtickets.showenddate']" value="no" <s:if test="%{#notyetendtime == 'no'}">checked="checked"</s:if>/>&nbsp;No
		  	   		</div> -->
						</div>
						<br>

						<div class="row">
							<div class="col-md-4 col-sm-4"><s:text name="dt.nyst.display.time.lbl"></s:text></div>
							<div class="col-md-4 col-sm-6 col-xs-12">
								<s:set name="notyetshowtime"
									value="ticketDisplayOptionsMap['event.notyetstartedtickets.showtime']"></s:set>
								<div data-toggle="buttons" class="center btn-group btn-toggle">
									<label
										onclick="makeActive('notyettimehide2','notyettimeshow1')"
										id="notyettimeshow1"
										class="optiontype btn btn-xs no-radius <s:if test='%{#notyetshowtime == "yes"}'> btn-default </s:if><s:else> btn-active  </s:else>">
										<input id="notyettimeshow1_radio" type="radio" value="yes"
										name="ticketDisplayOptionsMap['event.notyetstartedtickets.showtime']"
										<s:if test="%{#notyetshowtime == 'yes'}">checked="checked"</s:if>
										class="datesel" /> <s:text name="gllobal.yes.lbl"></s:text>
									</label> <label
										onclick="makeActive('notyettimeshow1','notyettimehide2')"
										id="notyettimehide2"
										class="optiontype btn btn-xs no-radius <s:if test='%{#notyetshowtime == "no"}'>btn-default  </s:if><s:else> btn-active  </s:else>">
										<input id="notyettimehide2_radio" type="radio" value="no"
										name="ticketDisplayOptionsMap['event.notyetstartedtickets.showtime']"
										<s:if test="%{#notyetshowtime == 'no'}">checked="checked"</s:if>
										class="datesel" /><s:text name="global.no.lbl"></s:text>
									</label>
								</div>
							</div>
							<div class="col-md-4 col-sm-2 "></div>


							<!--  <input type="radio"  class="radiobtn" name="ticketDisplayOptionsMap['event.notyetstartedtickets.showtime']" value="yes" <s:if test="%{#notyetshowtime == 'yes'}">checked="checked"</s:if>/>&nbsp;Yes
		  	   		</div>
		  	   		<div class="col-md-4">
                    <input type="radio" class="radiobtn" name="ticketDisplayOptionsMap['event.notyetstartedtickets.showtime']" value="no" <s:if test="%{#notyetshowtime == 'no'}">checked="checked"</s:if>/>&nbsp;No
		  	   		</div> -->
						</div>
					</div>
					<!-- not yet started  -->
					<br>


					<div class="row">
						<div class="col-md-5">
							<s:text name="dt.active.tkts.section.header.lbl"></s:text>
						</div>
					</div>
					<br>

					<div class="row">
						<div class="col-md-4 col-sm-4"><s:text name="dt.ticket.display.lbl"></s:text></div>
						<div class="col-md-4 col-sm-6 col-xs-12">
							<s:set name="activetktsstatus"
								value="ticketDisplayOptionsMap['event.activetickets.status']"></s:set>
							<div data-toggle="buttons" class="center btn-group btn-toggle">
								<label id="activetktstatusshow"
									class="optiontype btn btn-xs btn-default no-radius" disabled
								> <input
									type="radio" value="yes"
									name="ticketDisplayOptionsMap['event.activetickets.status']"
									class="datesel" checked="checked" /> <s:text name="dt.display.btngroup.show.lbl"></s:text>
								</label> <label id="activetktstatushide"
									class="optiontype btn btn-xs btn-active no-radius" disabled>
									<input type="radio" value="no"
									name="ticketDisplayOptionsMap['event.activetickets.status']"
									class="datesel" /><s:text name="dt.display.btngroup.hide.lbl"></s:text>
								</label>
							</div>



							<!-- <input type="radio" class="radiobtn"  disabled="disabled" name="ticketDisplayOptionsMap['event.activetickets.status']" value="yes" onclick="showDiv('activetickets')" <s:if test="%{#activetktsstatus == 'yes'}">checked="checked"</s:if>/>&nbsp;Show -->
						</div>
						<div class="col-md-4 col-sm-2 "></div>
					</div>
					<br>

					<div id="activetickets"
						<s:if test="%{#activetktsstatus == 'yes'}"></s:if>
						<s:else>style="display:none;"</s:else>>
						<div class="row">
							<div class="col-md-4 col-sm-4"><s:text name="dt.display.start.date.lbl"></s:text></div>
							<div class="col-md-4 col-sm-6 col-xs-12">
								<s:set name="activetktsstartdate"
									value="ticketDisplayOptionsMap['event.activetickets.showstartdate']"></s:set>
								<div data-toggle="buttons" class="center btn-group btn-toggle">
									<label
										onclick="makeActive('activetktsdatehide','activetktsdateshow')"
										id="activetktsdateshow"
										class="optiontype btn btn-xs no-radius <s:if test='%{#activetktsstartdate == "yes"}'> btn-default </s:if><s:else> btn-active </s:else>">
										<input id="activetktsdateshow_radio" type="radio" value="yes"
										name="ticketDisplayOptionsMap['event.activetickets.showstartdate']"
										<s:if test="%{#activetktsstartdate == 'yes'}">checked="checked"</s:if>
										class="datesel" /> <s:text name="gllobal.yes.lbl"></s:text>
									</label> <label
										onclick="makeActive( 'activetktsdateshow','activetktsdatehide')"
										id="activetktsdatehide"
										class="optiontype btn btn-xs no-radius <s:if test='%{#activetktsstartdate == "no"}'>btn-default  </s:if><s:else> btn-active </s:else>">
										<input id="activetktsdatehide_radio" type="radio" value="no"
										name="ticketDisplayOptionsMap['event.activetickets.showstartdate']"
										<s:if test="%{#activetktsstartdate == 'no'}">checked="checked"</s:if>
										class="datesel" /><s:text name="global.no.lbl"></s:text>
									</label>
								</div>
							</div>
							<div class="col-md-4 col-sm-2 "></div>

							<!-- <input type="radio"  class="radiobtn" name="ticketDisplayOptionsMap['event.activetickets.showstartdate']" value="yes" <s:if test="%{#activetktsstartdate == 'yes'}">checked="checked"</s:if>/>&nbsp;Yes		  	   		
		  	   		</div>	
		  	   		<div class="col-md-4">
                    <input type="radio" class="radiobtn" name="ticketDisplayOptionsMap['event.activetickets.showstartdate']" value="no" <s:if test="%{#activetktsstartdate == 'no'}">checked="checked"</s:if>/>&nbsp;No
		  	   		</div> -->
						</div>
						<br>

						<div class="row">
							<div class="col-md-4 col-sm-4"><s:text name="dt.display.end.date.lbl"></s:text></div>
							<div class="col-md-4 col-sm-6 col-xs-12">
								<s:set name="activetktsenddate"
									value="ticketDisplayOptionsMap['event.activetickets.showenddate']"></s:set>
								<div data-toggle="buttons" class="center btn-group btn-toggle">
									<label
										onclick="makeActive('activetktedatehide','activetktedateshow')"
										id="activetktedateshow"
										class="optiontype btn btn-xs no-radius <s:if test='%{#activetktsenddate == "yes"}'> btn-default </s:if><s:else> btn-active </s:else>">
										<input id="activetktedateshow_radio" type="radio" value="yes"
										name="ticketDisplayOptionsMap['event.activetickets.showenddate']"
										<s:if test="%{#activetktsenddate == 'yes'}">checked="checked"</s:if>
										class="datesel" /> <s:text name="gllobal.yes.lbl"></s:text>
									</label> <label
										onclick="makeActive('activetktedateshow','activetktedatehide')"
										id="activetktedatehide"
										class="optiontype btn btn-xs no-radius <s:if test='%{#activetktsenddate == "no"}'>btn-default  </s:if><s:else> btn-active </s:else>">
										<input id="activetktedatehide_radio" type="radio" value="no"
										name="ticketDisplayOptionsMap['event.activetickets.showenddate']"
										<s:if test="%{#activetktsenddate == 'no'}">checked="checked"</s:if>
										class="datesel" /><s:text name="global.no.lbl"></s:text>
									</label>
								</div>
							</div>
							<div class="col-md-4 col-sm-2 "></div>
							<!-- <input type="radio" class="radiobtn"  name="ticketDisplayOptionsMap['event.activetickets.showenddate']" value="yes" <s:if test="%{#activetktsenddate == 'yes'}">checked="checked"</s:if>/>&nbsp;Yes
		  	   		</div>
		  	   		<div class="col-md-4">
					<input type="radio" class="radiobtn"  name="ticketDisplayOptionsMap['event.activetickets.showenddate']" value="no" <s:if test="%{#activetktsenddate == 'no'}">checked="checked"</s:if>/>&nbsp;No
		  	   		</div> -->
						</div>
						<br>

						<div class="row">
							<div class="col-md-4 col-sm-4"><s:text name="dt.nyst.display.time.lbl"></s:text></div>
							<div class="col-md-4 col-sm-6 col-xs-12">
								<s:set name="activetktsshowtime"
									value="ticketDisplayOptionsMap['event.activetickets.showtime']"></s:set>
								<div data-toggle="buttons" class="center btn-group btn-toggle">
									<label
										onclick="makeActive('activetkttimehide','activetkttimeshow'   )"
										id="activetkttimeshow"
										class="optiontype btn btn-xs no-radius <s:if test='%{#activetktsshowtime == "yes"}'> btn-default </s:if><s:else> btn-active </s:else>">
										<input id="activetkttimeshow_radio" type="radio" value="yes"
										name="ticketDisplayOptionsMap['event.activetickets.showtime']"
										<s:if test="%{#activetktsshowtime == 'yes'}">checked="checked"</s:if>
										class="datesel" /> <s:text name="gllobal.yes.lbl"></s:text>
									</label> <label
										onclick="makeActive('activetkttimeshow','activetkttimehide')"
										id="activetkttimehide"
										class="optiontype btn btn-xs no-radius <s:if test='%{#activetktsshowtime == "no"}'>btn-default  </s:if><s:else> btn-active </s:else>">
										<input id="activetkttimehide_radio" type="radio" value="no"
										name="ticketDisplayOptionsMap['event.activetickets.showtime']"
										<s:if test="%{#activetktsshowtime == 'no'}">checked="checked"</s:if>
										class="datesel" /><s:text name="global.no.lbl"></s:text>
									</label>
								</div>
							</div>
							<div class="col-md-4 col-sm-2 "></div>

							<!-- <input type="radio"   class="radiobtn"  name="ticketDisplayOptionsMap['event.activetickets.showtime']" value="yes" <s:if test="%{#activetktsshowtime == 'yes'}">checked="checked"</s:if>/>&nbsp;Yes
		  	   		</div>
		  	   		<div class="col-md-4">
                    <input type="radio"  class="radiobtn"  name="ticketDisplayOptionsMap['event.activetickets.showtime']" value="no" <s:if test="%{#activetktsshowtime == 'no'}">checked="checked"</s:if>/>&nbsp;No
		  	   		</div> -->
						</div>
					</div>
					<!-- not yet started  -->
					<br>


					<div class="row">
						<div class="col-md-5">
							<s:text name="dt.closed.tickets.section.header.lbl"></s:text>
						</div>
					</div>
					<br>

					<div class="row">
						<div class="col-md-4 col-sm-4"><s:text name="dt.ticket.display.lbl"></s:text></div>
						<div class="col-md-4 col-sm-6 col-xs-12">
							<s:set name="closedtktsstatus"
								value="ticketDisplayOptionsMap['event.closedtickets.status']"></s:set>

							<div data-toggle="buttons" class="center btn-group btn-toggle">
								<label
									onclick="showDiv('closedtickets');makeActive('closedticketshide','closedticketsshow'  )"
									id="closedticketsshow"
									class="optiontype btn btn-xs no-radius <s:if test='%{#closedtktsstatus == "yes"}'>btn-default  </s:if><s:else>btn-active</s:else>">
									<input id="closedticketsshow_radio" type="radio" value="yes"
									name="ticketDisplayOptionsMap['event.closedtickets.status']"
									<s:if test="%{#closedtktsstatus == 'yes'}">checked="checked"</s:if>
									class="datesel" /> <s:text name="dt.display.btngroup.show.lbl"></s:text>
								</label> <label
									onclick="hideDiv('closedtickets');makeActive('closedticketsshow','closedticketshide')"
									id="closedticketshide"
									class="optiontype btn btn-xs no-radius <s:if test='%{#closedtktsstatus == "no"}'>btn-default  </s:if><s:else>btn-active</s:else>">
									<input id="closedticketshide_radio" type="radio" value="no"
									name="ticketDisplayOptionsMap['event.closedtickets.status']"
									<s:if test="%{#closedtktsstatus == 'no'}">checked="checked"</s:if>
									class="datesel" /><s:text name="dt.display.btngroup.hide.lbl"></s:text>
								</label>
							</div>
						</div>
						<div class="col-md-4 col-sm-2 "></div>


						<!-- <input type="radio" class="radiobtn" id="closedshow" name="ticketDisplayOptionsMap['event.closedtickets.status']" value="yes" onclick="showDiv('closedtickets')" <s:if test="%{#closedtktsstatus == 'yes'}">checked="checked"</s:if>/>&nbsp;Show
		  	   		</div>
		  	   		<div class="col-md-4">
                    <input type="radio"  class="radiobtn"  id="closedhide" name="ticketDisplayOptionsMap['event.closedtickets.status']" value="no" onclick="hideDiv('closedtickets')" <s:if test="%{#closedtktsstatus == 'no'}">checked="checked"</s:if>/>&nbsp;Hide
		  	   		</div> -->
					</div>
					<br>
					<div id="closedtickets"
						style="display:<s:if test='%{#closedtktsstatus == "yes"}'>block</s:if><s:else>none</s:else>">
						<!-- <div id="closedtickets" <s:if test="%{#closedtktsstatus == 'yes'}"></s:if> <s:else>style="display:none;"</s:else>> -->
						<div class="row">
							<div class="col-md-4 col-sm-4"><s:text name="dt.ticket.status.message.tb.lbl"></s:text></div>
							<div class="col-md-4 col-sm-6 col-xs-12" style="width: 140px">
								<s:textfield
									name="ticketDisplayOptionsMap['event.closedtickets.statusmessage']"
									theme="simple" cssClass="form-control"></s:textfield>
							</div>
							<div class="col-md-4 col-sm-2 "></div>
						</div>
						<br>

						<div class="row">
							<div class="col-md-4 col-sm-4"><s:text name="dt.ticket.strikethrough.text.lbl"></s:text></div>
							<div class="col-md-4 col-sm-6 col-xs-12">
								<s:set name="closedtktstrike"
									value="ticketDisplayOptionsMap['event.closedtickets.strikethrough']"></s:set>
								<div data-toggle="buttons" class="center btn-group btn-toggle">
									<label
										onclick="makeActive('closedticketstrikeno','closedticketstrikeyes' )"
										id="closedticketstrikeyes"
										class="optiontype btn btn-xs no-radius <s:if test='%{#closedtktstrike == "yes"}'>btn-default</s:if><s:else>btn-active</s:else>">
										<input id="closedticketstrikeyes_radio" type="radio"
										value="yes"
										name="ticketDisplayOptionsMap['event.closedtickets.strikethrough']"
										<s:if test="%{#closedtktstrike == 'yes'}">checked="checked"</s:if>
										class="datesel" /> <s:text name="gllobal.yes.lbl"></s:text>&nbsp;&nbsp;
									</label> <label
										onclick="makeActive('closedticketstrikeyes','closedticketstrikeno')"
										id="closedticketstrikeno"
										class="optiontype btn btn-xs no-radius <s:if test='%{#closedtktstrike == "no"}'>btn-default</s:if><s:else>btn-active</s:else>">
										<input id="closedticketstrikeno_radio" type="radio" value="no"
										name="ticketDisplayOptionsMap['event.closedtickets.strikethrough']"
										<s:if test="%{#closedtktstrike == 'no'}">checked="checked"</s:if>
										class="datesel" /><s:text name="global.no.lbl"></s:text>&nbsp;&nbsp;&nbsp;&nbsp;
									</label>
								</div>
							</div>
							<div class="col-md-4 col-sm-2 "></div>


							<!-- <input type="radio"  class="radiobtn"  name="ticketDisplayOptionsMap['event.closedtickets.strikethrough']" value="yes" <s:if test="%{#closedtktstrike == 'yes'}">checked="checked"</s:if>/>&nbsp;Yes
		  	   		</div>
		  	   		<div class="col-md-4">
                    <input type="radio"  class="radiobtn"  name="ticketDisplayOptionsMap['event.closedtickets.strikethrough']" value="no" <s:if test="%{#closedtktstrike == 'no'}">checked="checked"</s:if>/>&nbsp;No
		  	   		</div> -->
						</div>
						<br>
					</div>
					<!-- closed tickets  -->
					
<s:if test="%{isrecurring==false}">
<br>
					<div class="row">
						<div class="col-md-5">
							<s:text name="dt.waitlisted.tickets.section.header.lbl"></s:text>
						</div>
					</div>
					<br>

					<div class="row">
						<div class="col-md-4 col-sm-4"><s:text name="dt.waitlisted.button.label.lbl"></s:text></div>
						<div class="col-md-4 col-sm-6 col-xs-12">
							<s:textfield
								name="ticketDisplayOptionsMap['event.waitlisttickets.buttonname']"
								theme="simple" cssClass="form-control" style="width:145px;"></s:textfield>
						</div>
						<div class="col-md-4 col-sm-2 "></div>
					</div>
</s:if>

					<div class="row">
						<div class="col-md-5">
							<s:text name="dt.general.section.header.lbl"></s:text>
						</div>
					</div>
					<br>

					<div class="row">
						<div class="col-md-4 col-sm-4"><s:text name="dt.ticket.description.mode.lbl"></s:text>
							</div>
						<s:set name="tktDescMode"
							value="ticketDisplayOptionsMap['event.general.tktDescMode']"></s:set>
						<div class="col-md-4 col-sm-6 col-xs-12">
							<div data-toggle="buttons" class="center btn-group btn-toggle">
								<label onclick="makeActive('tktdescmodehide','tktdescmodeshow')"
									id="tktdescmodeshow"
									class="optiontype btn btn-xs no-radius <s:if test='%{#tktDescMode == "collapse" || #tktDescMode != "expand" }'>btn-default</s:if><s:else>btn-active</s:else>">
									<input type="radio" id="tktdescmodeshow_radio" value="collapse"
									name="ticketDisplayOptionsMap['event.general.tktDescMode']"
									<s:if test="%{#tktDescMode == 'collapse' || #tktDescMode == ''}">checked="checked"</s:if> />
									<s:text name="dt.ticket.description.mode.btngroup.collapse"></s:text>
								</label> <label
									onclick="makeActive('tktdescmodeshow','tktdescmodehide')"
									id="tktdescmodehide"
									class="optiontype btn btn-xs no-radius <s:if test='%{#tktDescMode == "expand"}'>btn-default</s:if><s:else>btn-active</s:else>">
									<input id="tktdescmodehide_radio" type="radio" value="expand"
									name="ticketDisplayOptionsMap['event.general.tktDescMode']"
									<s:if test="%{#tktDescMode == 'expand' }">checked="checked"</s:if> /><s:text name="dt.ticket.description.mode.btngroup.expand"></s:text>
								</label>
							</div>
						</div>
						<div class="col-md-4 col-sm-2 "></div>



						<!-- <input type="radio"  class="radiobtn" name="ticketDisplayOptionsMap['event.general.tktDescMode']" value="collapse" <s:if test="%{#tktDescMode == 'collapse' || #tktDescMode == ''}">checked="checked"</s:if>/>&nbsp;Collapse
		  	   		</div>
		  	   		<div class="col-md-4">
		  	   		<input type="radio"  class="radiobtn" name="ticketDisplayOptionsMap['event.general.tktDescMode']" value="expand"  <s:if test="%{#tktDescMode == 'expand'}">checked="checked"</s:if>/>&nbsp;Expand		  	   		
		  	   		</div> -->
					</div>
					<br>

					<div class="row">
						<div class="col-md-4 col-sm-4"><s:text name="dt.group.description.mode.lbl"></s:text>
							</div>

						<div class="col-md-4 col-sm-6 col-xs-12">
							<s:set name="grpDescMode"
								value="ticketDisplayOptionsMap['event.group.tktDescMode']"></s:set>
							<div data-toggle="buttons" class="center btn-group btn-toggle">
								<label
									onclick="makeActive('groupdescmodeexpand','groupdescmodecolapse')"
									id="groupdescmodecolapse"
									class="optiontype btn btn-xs no-radius <s:if test='%{#grpDescMode == "collapse" || #grpDescMode != "expand" }'>btn-default</s:if><s:else>btn-active</s:else>">
									<input id="groupdescmodecolapse_radio" type="radio"
									value="collapse"
									name="ticketDisplayOptionsMap['event.group.tktDescMode']"
									<s:if test="%{#grpDescMode == 'collapse' || #grpDescMode == ''}">checked="checked"</s:if>
									class="datesel" /> <s:text name="dt.ticket.description.mode.btngroup.collapse"></s:text>
								</label> <label
									onclick="makeActive( 'groupdescmodecolapse','groupdescmodeexpand')"
									id="groupdescmodeexpand"
									class="optiontype btn btn-xs no-radius <s:if test='%{#grpDescMode == "expand"}'>btn-default</s:if><s:else>btn-active</s:else>">
									<input id="groupdescmodeexpand_radio" type="radio"
									value="expand"
									name="ticketDisplayOptionsMap['event.group.tktDescMode']"
									<s:if test="%{#grpDescMode == 'expand'}">checked="checked"</s:if>
									class="datesel" /><s:text name="dt.ticket.description.mode.btngroup.expand"></s:text>
								</label>
							</div>
						</div>
						<div class="col-md-4 col-sm-2 "></div>
						<!-- <input type="radio" class="radiobtn" name="ticketDisplayOptionsMap['event.group.tktDescMode']" value="collapse" <s:if test="%{#grpDescMode == 'collapse' || #grpDescMode == ''}">checked="checked"</s:if>/>&nbsp;Collapse
		  	   		</div>
		  	   		<div class="col-md-4">
                    <input type="radio"  class="radiobtn" name="ticketDisplayOptionsMap['event.group.tktDescMode']" value="expand"  <s:if test="%{#grpDescMode == 'expand'}">checked="checked"</s:if>/>&nbsp;Expand
		  	   		</div>  -->
					</div>
					<br>

					<%-- <div class="row">
		  	   		<div class="col-md-4">Registration Time Out</div>		  	   		
      				<div class="input-group">      
      				<!-- <input type="text" class="form-control" id="exampleInputAmount" > -->
      				<s:textfield name="regtimeout" id="" size="2" maxlength="2" class="form-control"></s:textfield>
      				<div class="input-group-addon">minutes</div>
    				</div>	    							
		  	   		</div>  --%>


					<div class="row bottom-gap">
						<div class="col-md-4 col-sm-4"><s:text name="dt.registration.time.out.tb.lbl"></s:text></div>
						<div class=" col-md-4 col-sm-6 col-xs-12 " style="width: 147px;">

							<%-- <s:textfield cssClass='form-control' name="regtimeout"
								id="regtimeout" theme="simple" size="4" placeholder="Time"
								style="margin-left: 15px;"></s:textfield> --%>
								
								<s:textfield cssClass="form-control" name="regtimeout" placeholder="%{getText('trans.time.lbl')}" id="regtimeout" size="4" ></s:textfield>
								<div class="sub-text"><s:text name="dt.reg.timeout.min.lbl"/></div>
								
							<%-- <span class="input-group-addon" style="padding-left: 0px; margin-left: 25px";> 
								<label class="radio-inline" style="padding-top: 0px;"> <s:text name="dt.reg.timeout.min.lbl"></s:text></label>
							</span> --%>
						</div>
						<div class="col-md-4 col-sm-2 "></div>
					</div>


					
					

				
				<!-- panel body   -->
				
				<div class="center">
					<input type="button" name="submit" value="<s:text name="global.save.btn.lbl"/>" id="submitBtn" 	class="btn btn-primary dtsavebtn" />
					<button class="btn btn-cancel" id="displayTicketClose"><i class="fa fa-times"></i></button>
			    </div>
				

			
			
		<!-- </div> -->
	<!-- </div> -->
	           
	<!-- close of display -->
	      <div style="display:none;" id="formatshow">   
	      <div style="clear: both;"></div>      
						 <div  id="popuplink" onClick="creatDisplay('',<s:property value='%{eid}'/>)">
							<a style="cursor: pointer"> <s:text name="dt.create.frmat.lbl"></s:text> </a>
						</div> 
						<div class=" col-md-12">
								<div class="panel-body  table-responsive background-options sm-font" >
									<table class="table" id='formatlisttable'>
										<thead>
											<tr>
												<th><b><s:text name="dt.table.header.format"></s:text></b></th>
												<th><%-- <s:text name="dt.table.header.manage"></s:text> --%></th>
												<th></th>
											</tr>
										</thead>
										<tbody id="formatlist">
											<tr id="nodata">
												<td><s:text name="dt.no.data.lbl"></s:text></td>
												<td></td>
												<td></td>
											</tr>
										</tbody>
									</table>
								</div>
						
						<!-- panel   -->

					</div>
					<!-- row end   -->
	       </div>
	
	
	
	
	
	
</s:form>
<%-- <script type="text/javascript" language="JavaScript" src="/main/bootstrap/js/bootbox.min.js"> --%>
<script>

function statusMessageForRules(message, type) {
    var html = '<div style="margin-bottom:0px;" class="alert alert-' + type + ' alert-dismissable page-alert">';    
    html += '<button type="button" class="close close-notification"><span aria-hidden="true">x</span><span class="sr-only">Close</span></button>';
    html += message;
    html += '</div>';   
    $("#successmsg").html('');
    $("#successmsg").show();
    $(html).hide().prependTo('#successmsg').slideDown();
};



/* $(document).ready(function(){  
	$('#submitBtn').attr('data-loading-text',props.global_processing_lbl);
}); */

/* function rebuildtable(JsonData)
{
	
	 $('#formatlisttable').dataTable().fnDestroy(false);
	fillFormats(JSON.parse(JsonData));
	
} */


	$(document).ready(function() {
		///var formatresult = '${jsonData}';
		/*  alert(JSON.stringify(formatresult));  */
		//fillFormats(JSON.parse(formatresult));
	});
	
	
	/* function reconstructtable()
	{      
	
	var rowCount = $('#formatlist tr').length;
		 $('#formatlisttable').dataTable({
	        "sPaginationType": "full_numbers",
	        "iDisplayLength":5,
	        "bLengthChange": false,
	        "aoColumns": [{ "bSortable": false },
	                      { "bSortable": false },
	                      { "bSortable": false }
	                    ] ,
	         "bFilter": false,
	    }); 
	    
	    
			if(rowCount <= 5)
				removePagination('formatlisttable');
		
	} */
	
	
	

	/* function fillFormats(formats) {
		var eid = "${eid}";
		var jsondata=formats.formats;
		  alert(JSON.stringify(jsondata));
		alert(formats.formats[0].fm);    
		var html = "";
				$.each(jsondata, function(key, Obj) {
					var id="fmId_"+Obj.fmId;  
					var edit="edit_fmId_"+Obj.fmId;
					var del="del_fmId_"+Obj.fmId;
							//	html += '<tr id="'+id+'" class="trhover">    <td >'+Obj.fm+'</td>     <td id="'+edit+'" style="display:none;" >  <a href="javascript:;" onclick=editFormat(\''+Obj.fmId+'\','+eid+')>'+props.global_edit_lnk_lbl+'</a></td>     <td id="'+del+'" style="display:none;"> <a href="javascript:;" onclick=deleteFormat(\''+Obj.fmId+'\','+eid+')>'+props.global_delete_lnk_lbl+'</a></td>   </tr>';
					html += '<tr class="trhover" id="'+id+'" >    <td ><div class="col-md-12" >       <div class="col-md-10"> '+Obj.fm+' </div>      <div style="display:none;" class="col-md-1 hideThis" id="'+edit+'" ><a href="javascript:;" onclick=editFormat(\''+Obj.fmId+'\','+eid+')>'+props.global_edit_lnk_lbl+'</a></div>   <div style="display:none;" class="col-md-1 hideThis" id="'+del+'"> <a href="javascript:;" onclick=deleteFormat(\''+Obj.fmId+'\','+eid+')>'+props.global_delete_lnk_lbl+'</a></div>       </div></td> </tr>';		
				
				});
				$('#formatlist').html('');
		$('#formatlist').html(html);
		showhide();
		reconstructtable();
	} */
	 
	function showhide()
	{

		 $(document).on('mouseover','tr',function() {
	         $(this).find('.hideThis').show();
	         });
	 $(document).on('mouseout','tr',function() {
	         $(this).find('.hideThis').hide();
	         }); 
		/* $('#formatlisttable tr').mouseover(function(){     
			alert("hii");
			 var editlink="edit_"+this.id;
			 var dellink="del_"+this.id;
			 
			      $('#'+editlink).css("display","block");
			     $('#'+dellink).css("display","block");    
			   
			}); 
		 
		 
		 $('.trhover').mouseout(function(){
			 alert(this.id);
			 var editlink="edit_"+this.id;
			 var dellink="del_"+this.id;
			     $('#'+editlink).css("display","none");
			     $('#'+dellink).css("display","none");
			});  */
	}
		 
	
		 
	
	
	
	
	/* function editFormat(fid,eid)
	{
		creatDisplay(fid,eid);
	} */
	
	
	
	/* function deleteFormat(fid,eid)
	{  
		bootbox.confirm(props.dt_del_format_lbl, function (result) {
			if(result){
		url="DisplayTickets!deleteDisplayFormat?formatId="+fid+"&eid="+eid;
		 $.ajax({
				type:"GET",
				url: url,
				success: function( ajaxresult ) 
				{
					if(!isValidActionMessage(ajaxresult)) return;
					$('#formatlisttable').dataTable().fnDestroy(false);
					 
					fillFormats(JSON.parse(ajaxresult));
					 
					
				}
		 });
			}  
		});
	}  */ 
	
	
	/* function closePopup() {
		parent.$('#myModal').modal('hide');
	} */

	/* function creatDisplay(fid,eid) {
		
		var url="";
		if(fid=='' || fid==null)
			url= "DisplayTickets!displayFormatPopupContent?eid="+eid;
		else
			url="DisplayTickets!populateFormatData?formatId="+fid+"&eid="+eid; 

		$('#myModal').on('show.bs.modal', function() 
		{
		$('#myModal .modal-title').html('<h3><span style="padding-left:10px">'+props.dt_table_header_format+'</span></h3>');
		$('iframe#popup').attr("src", url);
		});
		parent.window.resizeIframe();
		$('#myModal').modal('show');

		$('#myModal').on('hide.bs.modal', function() 
				{
		$('iframe#popup').attr("src", '');
		$('#myModal .modal-body')
		.html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:resizeIframe(this)" frameborder="0"></iframe>');
				});
	} */

	function makeActive(activeElement, inactiveElement) {
		//alert(activeElement+"::"+inactiveElement);
		$('#' + activeElement).addClass('btn-active').removeClass('btn-default');
		
		$('#' + inactiveElement).addClass('btn-default').removeClass('btn-active');
		document.getElementById(activeElement + '_radio').checked = true;
		//$('#'+inactiveElement+'_radio').iCheck('uncheck');
	}

	function showDiv(id) {
		if (document.getElementById(id).style.display == 'none') {
			document.getElementById(id).style.display = 'block';
		}
	}
	function hideDiv(id) {
		if (document.getElementById(id).style.display == 'block') {
			document.getElementById(id).style.display = 'none';
		}
	}
	
	function closepopup(){
		document.getElementById("submitBtn").disabled = false;
		 parent.$('#myModal').modal('hide');    
		 
	}
	
	$('#submitBtn').click(function() {
		var eid = "${eid}";
		$('#submitBtn').attr("disabled", "disabled");
		$('#successmsg').html('');
		$('#successmsg').hide();
		
		
		specialFee(eid,'200','DisplayTickets','Ticketing');
	});

	$('#displayTicketClose').click(function(e){
			e.preventDefault();
			$('#displaytktdiv').slideUp();
			$('#spinner').removeClass('down').addClass('original');
			$('html, body').animate({ scrollTop: $("#spinner").offset().top-200 },1000);
		});

	
		function DisplayTickets(){
		var errormsg="";
		var timeoutmin=$('#regtimeout').val().trim();
		
		if(timeoutmin=="")
			errormsg=props.dt_reg_time_empty_lbl;
		else if(isNaN(timeoutmin))
			errormsg=props.dt_reg_time_numeric_lbl;
		else if(timeoutmin>30 || timeoutmin<5)
			errormsg=props.dt_reg_time_range_lbl;
		else if(!Number.isInteger(Number(timeoutmin))){
			errormsg=props.dt_reg_time_numeric_lbl;
		}
		
		if(errormsg=="")
			savechanges();
		else
			{
			$('html, body').animate({ scrollTop: $('#gapid').offset().top-scrollTo}, 3000);
			$('#successmsg').removeClass('alert-success'); 
			$('#successmsg').addClass('alert alert-danger');
			$('#successmsg').show().html(errormsg); 
			document.getElementById("submitBtn").disabled = false;
			}
	}
	
	

	function savechanges()
	{
		/* $('#submitBtn').button('loading'); */
		/* perform processing then reset button when done */
		setTimeout(function() {	$('#submitBtn').button('reset');}, 1000);
		$.ajax({
			type : "POST",
			url : 'DisplayTickets!save',
			data : $('#DisplayTicketsform').serialize(),
			success : function(t) {
				
				if (!isValidActionMessage(t))
					return;
				
				document.getElementById("submitBtn").disabled = false;
				/* window.scrollTo(0, 0); */
				/* statusMessageForRules("Updated Sucessfully", "success"); */
				//$('html, body').animate({ scrollTop: $('#seteventcap').offset().top-scrollTo}, 1000);
				 notification(props.dt_disp_tkts_updtd_succ,"success");
				//$('#successmsg').show().html(t);
				//$('#successmsg').removeClass('alert-danger');
				//$('#successmsg').addClass('alert-success'); 
				/* $('#gap').html('<br/>'); */
			}
		});
	}

	$(function() {
		$('input.radiobtn').iCheck({
			checkboxClass : 'icheckbox_square-grey',
			radioClass : 'iradio_square-grey'
		});

		$('input#notyetshow').on('ifChecked', function(event) {
			showDiv('notyetstarted');
		});

		$('input#notyethide').on('ifChecked', function(event) {
			hideDiv('notyetstarted');
		});

		$('input#closedshow').on('ifChecked', function(event) {
			showDiv('closedtickets');
		});

		$('input#closedhide').on('ifChecked', function(event) {
			hideDiv('closedtickets');
		});
	});

	
</script>
