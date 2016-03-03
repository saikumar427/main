<%@taglib uri="/struts-tags" prefix="s" %>

            <div class="row">
                <div class="col-md-12">                    
                    <form class="form-horizontal" id='DonationAddForm' >
                        <s:hidden name="ticketType" value="Donation"></s:hidden>
                        <s:hidden name="eid"></s:hidden>
                        <s:hidden name="ticketData.ticketId"></s:hidden>
                        <div class="alert alert-danger" id="donationerrors" style="display:none"></div>
                        <s:set name="groupsSize" value ="allgroups.size()" ></s:set>
                       
			                <s:set name="grplen" value="groupId.length()"></s:set>
			                <s:if test="%{#grplen ==0}"><s:hidden name="groupId" value="0"></s:hidden></s:if>
			                <s:else><s:hidden name="groupId" ></s:hidden></s:else>			
                     
                        <div class="form-group">
                            <label for="name" class="col-md-4 control-label">Donation Name *</label>
                            <div class="col-md-8">
                                <s:textfield name="ticketData.ticketName"  cssClass='form-control' theme="simple" size="52" id="donationname"></s:textfield>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="email" class="col-md-4 control-label">Donation Description</label>
                            <div class="col-md-8">
                               <s:textarea rows="3" cols="49" name="ticketData.ticketDescription" cssClass='form-control' theme="simple"></s:textarea>
                            </div>
                        </div>
                        <s:set name="scancoderequired" value="ticketData.isScanCode"></s:set>
                       
                        
                        <div class="form-group">
                        <label for="message" class="col-md-4 control-label">Scan Codes Required<br><span class="smallestfont">(Includes barcode & QRcode)</span></label>
                         
                            <div class="col-md-8">
                                <label class="checkbox-inline">
                                   <input type="radio" class="scancode" name="ticketData.isScanCode" id="yscancode" value="Yes" <s:if test="%{#scancoderequired == 'Yes'}">checked="checked"</s:if>>  Yes
                                </label>
                                <label class="checkbox-inline">
                                    <input type="radio" class="scancode" name="ticketData.isScanCode" id="nscancode" value="No" <s:if test="%{#scancoderequired != 'Yes'}">checked="checked"</s:if>> No
                                </label>
                               
                            </div>
                        </div>
                        
                        <div class="form-group">
                          <label  class="col-md-4 control-label">Sale Date & Time *</label>
                            <div class="col-md-8">
                              <div class='row'>
                              
                                <div class="col-md-12">
                                    
							     	<s:if test="%{isrecurring==true}">
							     	<div class='col-md-2' style="padding-left:0px;">
                               <label class="col-md-3 control-label">Starts</label> 
                               </div>
								    <s:if test="%{ticketData.ticketId==''}"><s:set name="startdays" value="100"></s:set></s:if>
								    <s:else><s:set name="startdays" value="ticketData.startBefore"></s:set></s:else>								 
								   <div class="col-md-3">
								    <s:textfield name="ticketData.startBefore" size="2" theme="simple" value="%{#startdays}" cssClass="form-control"></s:textfield>
                                    day(s)
                                    </div>
                                    <s:hidden name="ticketData.stdateTimeBeanObj.monthPart"></s:hidden>
                                    <s:hidden name="ticketData.stdateTimeBeanObj.ddPart"></s:hidden>
                                    <s:hidden name="ticketData.stdateTimeBeanObj.yyPart"></s:hidden>
                                    <div class="col-md-3">
                                    <s:textfield name="ticketData.startHoursBefore" size="2" theme="simple" cssClass="form-control"></s:textfield>hour(s)
                                    <s:hidden name="ticketData.stdateTimeBeanObj.hhPart"></s:hidden>
                                    </div>
                                    <div class="col-md-4">
                                    <s:textfield name="ticketData.startMinutesBefore" size="2" theme="simple" cssClass="form-control"></s:textfield>minute(s)&nbsp;before the event
                                    <s:hidden name="ticketData.stdateTimeBeanObj.mmPart"></s:hidden>
                                    <s:hidden name="ticketData.stdateTimeBeanObj.ampm"></s:hidden>
                                    </div>                                    
                                    </s:if>
                                    <s:else>
                                 <%--    <div class="col-md-4">
							<s:textfield  cssClass="form-control" name="ticketData.stdateTimeBeanObj.monthPart" theme="simple" size="2" maxlength="2" id="start_month_field"></s:textfield>
							</div>
							<div class="col-md-4">
							<s:textfield cssClass="form-control" name="ticketData.stdateTimeBeanObj.ddPart" theme="simple" size="2" maxlength="2" id="start_day_field"></s:textfield>
							</div>
							<div class="col-md-4">
							<s:textfield  cssClass="form-control" name="ticketData.stdateTimeBeanObj.yyPart" theme="simple" size="4" maxlength="4" id="start_year_field"></s:textfield>
							<s:set name="hhpart" value="ticketData.stdateTimeBeanObj.hhPart"/>
							</div><div class="col-md-4">
							<select   Class='form-control' name="ticketData.stdateTimeBeanObj.hhPart" id="ticketData.stdateTimeBeanObj.hhPart" >
							<s:iterator value="%{hours}" var="type">
							<s:set name="cval" value="key"/>
							<option value="<s:property value="key"/>" <s:if test="%{#hhpart==#cval}">selected='selected'</s:if>>${value}</option>
							</s:iterator>
							</select>
							</div>
							<s:set name="minspart" value="ticketData.stdateTimeBeanObj.mmPart"/>
							<div class="col-md-4">
							<select  Class='form-control' name="ticketData.stdateTimeBeanObj.mmPart" id="ticketData.stdateTimeBeanObj.mmPart" >
							<s:iterator value="%{minutes}" var="type">
							<s:set name="cval" value="key"/>
							<option value="<s:property value="key"/>" <s:if test="%{#minspart==#cval}">selected='selected'</s:if>>${value}</option>
							</s:iterator>
							</select>
							</div>
							<div class="col-md-4">
							<s:set name="ampm" value="ticketData.stdateTimeBeanObj.ampm"/>
							<select  Class='form-control' name="ticketData.stdateTimeBeanObj.ampm">
							<option value="AM" <s:if test="%{#ampm == 'AM'}">selected='selected'</s:if>>AM</option>
							<option value="PM" <s:if test="%{#ampm == 'PM'}">selected='selected'</s:if>>PM</option>
							</select>
							</div> --%>
							
							
							 <div class="row">
                                  		
                                  		<!-- <div class="col-xs-1"  style="padding-top: 7px;  width: 12.333%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;from </div> -->
										<div class="col-md-12" >
											 <s:textfield cssClass="form-control" id="donationstart" type="text" name="ticketData.newStartDate" />
										</div><div class="col-md-12" style="padding-left: 141px;padding-top: 12px;">&nbsp;&nbsp;to</div>
										<div class="col-md-12" style="padding-top:10px">
										<s:textfield cssClass="form-control" id="donationend" type="text" name="ticketData.newEndDate" />
										</div>
									
									</div>
							
							
							
							
							</s:else>
														
                           </div>  
                            
                            <div class="col-md-12">
                           	<s:if test="%{isrecurring==true}">
                           	 <div class="col-md-2" style="padding-left:0px;">
                             <label class="col-md-3 control-label">Ends</label>  
                             </div>
                           	<div class="col-md-3">
							<s:textfield   cssClass='form-control' name="ticketData.endBefore" size="2" theme="simple"></s:textfield>day(s)
							</div>
							<s:hidden name="ticketData.enddateTimeBeanObj.monthPart"></s:hidden>
							<s:hidden name="ticketData.enddateTimeBeanObj.ddPart"></s:hidden>
							<s:hidden name="ticketData.enddateTimeBeanObj.yyPart"></s:hidden>
							<div class="col-md-3">
							<s:textfield  cssClass='form-control' name="ticketData.endHoursBefore" size="2" theme="simple"></s:textfield>hour(s)
							<s:hidden name="ticketData.enddateTimeBeanObj.hhPart"></s:hidden>
							</div>
							<div class="col-md-4">
							<s:textfield  cssClass='form-control' name="ticketData.endMinutesBefore" size="2" theme="simple"></s:textfield>before the event
							<s:hidden name="ticketData.enddateTimeBeanObj.mmPart"></s:hidden>
							<s:hidden name="ticketData.enddateTimeBeanObj.ampm"></s:hidden>
							</div>
							</s:if> 
                             <s:else>
                             <%-- <div class="col-md-4">
						<s:textfield  cssClass='form-control' name="ticketData.enddateTimeBeanObj.monthPart" theme="simple" size="2" maxlength="2" id="end_month_field"></s:textfield>
						</div><div class="col-md-4">
						<s:textfield  cssClass='form-control' name="ticketData.enddateTimeBeanObj.ddPart" theme="simple" size="2" maxlength="2" id="end_day_field"></s:textfield>
						</div><div class="col-md-4">
						<s:textfield cssClass='form-control' name="ticketData.enddateTimeBeanObj.yyPart" theme="simple" size="4" maxlength="4" id="end_year_field"></s:textfield>
						</div>
						<div class="col-md-4">
						<s:set name="hhpart" value="ticketData.enddateTimeBeanObj.hhPart"/>
						<select  name="ticketData.enddateTimeBeanObj.hhPart" Class='form-control' id="ticketData.enddateTimeBeanObj.hhPart" >
						<s:iterator value="%{hours}" var="type">
						<s:set name="cval" value="key"/>
						<option value="<s:property value="key"/>" <s:if test="%{#hhpart==#cval}">selected='selected'</s:if>>${value}</option>
						</s:iterator>
						</select>
						</div>
						<div class="col-md-4">
						<s:set name="minspart" value="ticketData.enddateTimeBeanObj.mmPart"/>
						<select  Class='form-control' name="ticketData.enddateTimeBeanObj.mmPart" id="ticketData.enddateTimeBeanObj.mmPart" >
						<s:iterator value="%{minutes}" var="type">
						<s:set name="cval" value="key"/>
						<option value="<s:property value="key"/>" <s:if test="%{#minspart==#cval}">selected='selected'</s:if>>${value}</option>
						</s:iterator>
						</select>
						</div>
						<div class="col-md-4">
						<s:set name="ampm1" value="ticketData.enddateTimeBeanObj.ampm"/>
						<select  Class='form-control' name="ticketData.enddateTimeBeanObj.ampm">
						<option value="AM" <s:if test="%{#ampm1 == 'AM'}">selected='selected'</s:if>>AM</option>
						<option value="PM" <s:if test="%{#ampm1 == 'PM'}">selected='selected'</s:if>>PM</option>
						</select>
						</div> --%>
</s:else>
                             
                             
                             </div>                            
                                    
                         </div>           
                                    
               		   </div>
				   
					 </div>                         
                 
                    </form>
                    
                 </div>
            </div>
       