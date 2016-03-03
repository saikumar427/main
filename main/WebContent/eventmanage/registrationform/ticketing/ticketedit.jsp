<%@taglib uri="/struts-tags" prefix="s" %>
  <link type="text/css" rel="stylesheet" href="/main/css/bootstrap/tooltipster.css" />
<link type="text/css" rel="stylesheet" href="/main/css/bootstrap/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="/main/font-awesome-4.0.3/css/font-awesome.min.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/jquery.datetimepicker.css" />
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/blue.css" />

<link rel="stylesheet" type="text/css" href="/main/css/listing.css" />
    
<div id="ticketformerrormessages"  class='alert alert-danger' style='display:none' ></div>
 <div class="container">
            <div class="row">
                <div class="col-md-8">
                   
                    <form class="form-horizontal" action="TicketDetails!save" name="ticketingForm" id="ticketingForm"  >
                        <s:hidden name="eid" id="eid"></s:hidden><s:hidden name="ticketData.ticketId" id="ticketid"></s:hidden>
                        
                    
                 
					<s:set name="grplen" value="groupId.length()"></s:set>
					<s:if test="%{#grplen ==0}"><s:hidden name="groupId" value="0"></s:hidden></s:if>
					<s:else><s:hidden name="groupId"></s:hidden></s:else>
				
                        
                        
                          <div class="form-group">
                            <label for="name" class="col-md-3 control-label">Ticket Name *</label>
                            <div class="col-md-5">
                               <s:textfield name="ticketData.ticketName" cssClass='form-control' theme="simple" size="52" placeholder="Enter Ticket Name"></s:textfield>
                            </div>
                        </div>
                          
                          <div class="form-group">
                            <label for="name" class="col-md-3 control-label">Ticket Price&nbsp;*</label>
                            
                             <%-- <div class="col-md-4">
                            <div class="input-group" id="amountfield">
                                    <span class="input-group-addon">${currency}</span>
                                    <s:textfield   cssClass="form-control" name="amount" id="amount"></s:textfield>
                                    
                                </div>
                            </div> --%>
                            
                              
                             
                            <div class="col-md-4">
                                 <div class="input-group" id="amountfield">
                                 <span class="input-group-addon"> <s:text name='ticketData.currency'></s:text></span>
                               <s:textfield cssClass='form-control' id="ticketprice" name="ticketData.ticketPrice" theme="simple" size="4" placeholder="Enter Number"></s:textfield>
                            </div>
                            </div> 
                        </div>
                             
                    
                             
                          <div class="form-group">
                            <label for="name" class="col-md-3 control-label">Tickets Available *</label>
                            <div class="col-md-5">
                             <s:textfield id="tktavailid" cssClass='form-control' name="ticketData.totalTicketCount" theme="simple" size="12" onkeyup="setTicketMaxQty()" placeholder="Enter Number"></s:textfield>
                            </div>
                        </div>
                             
                          <div class="form-group">
                            <label for="name" class="col-md-3 control-label">Ticket Description</label>
                            <div class="col-md-5">
                           <s:textarea rows="3" cols="49" cssClass='form-control' name="ticketData.ticketDescription" theme="simple" placeholder="Enter Ticket Description"></s:textarea>
                           </div>
                        </div>
                        
                            <div class="form-group">
                            <label for="name" class="col-md-3 control-label">Purchase Quantity *</label>
                            <div class="col-md-5">
                            <div class="input-group">
                             <span class="input-group-addon" style="padding-left: 0px;">
                                <label class="radio-inline" style="padding-top: 0px;">
                               Minimum 
                               </label></span>
                               <s:textfield cssClass='form-control' name= "ticketData.minQty" theme="simple" theme="simple" size="4" placeholder="Enter Number" style="margin-right:10px"></s:textfield>
                               </div>
                               <div style="height: 10px;"></div>
 							<div class="input-group">
                             <span class="input-group-addon" style="padding-left: 0px;">
                                <label class="radio-inline" style="padding-top: 0px;">
                               Maximum 
                               </label></span>                               
							<s:textfield cssClass='form-control' id="tktmaxid" name="ticketData.maxQty" theme="simple" theme="simple" size="4" placeholder="Enter Number"></s:textfield>

                            </div>
                            </div>
                        </div>
                        
                        
                        <div class="form-group">
                          <label  class="col-md-3 control-label">Ticket Sale Date & Time *</label>
                            <div class="col-md-5">
                              <div class='row'>
                              
                                <div class="col-md-12">
                                    
							     	<s:if test="%{isrecurring==true}">
							     	<div class='col-md-1'>
                               <label class="col-md-4 control-label">Starts</label> 
                               </div>
								    <s:if test="%{ticketData.ticketId==''}"><s:set name="startdays" value="100"></s:set></s:if>
								    <s:else><s:set name="startdays" value="ticketData.startBefore"></s:set></s:else>								 
								   <div class="col-md-4">
								    <s:textfield cssClass='form-control' name="ticketData.startBefore" size="2" theme="simple" value="%{#startdays}"></s:textfield>
                                    day(s)
                                    </div>
                                    <s:hidden name="ticketData.stdateTimeBeanObj.monthPart"></s:hidden>
                                    <s:hidden name="ticketData.stdateTimeBeanObj.ddPart"></s:hidden>
                                    <s:hidden name="ticketData.stdateTimeBeanObj.yyPart"></s:hidden>
                                    <div class="col-md-4">
                                    <s:textfield cssClass='form-control' name="ticketData.startHoursBefore" size="2" theme="simple"></s:textfield>hour(s)
                                    <s:hidden name="ticketData.stdateTimeBeanObj.hhPart"></s:hidden>
                                    </div>
                                    <div class="col-md-4">
                                    <s:textfield cssClass='form-control' name="ticketData.startMinutesBefore" size="2" theme="simple"></s:textfield>minute(s)&nbsp;before the event
                                    <s:hidden name="ticketData.stdateTimeBeanObj.mmPart"></s:hidden>
                                    <s:hidden name="ticketData.stdateTimeBeanObj.ampm"></s:hidden>
                                    </div>                                    
                                    </s:if>
                                    <s:else>
                                   <%--  <div class="col-md-4">
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
											 <s:textfield cssClass="form-control" id="tktstart" type="text" name="ticketData.newStartDate" />
										</div><div class="col-md-12" style="padding-left:250px;padding-top: 12px;">&nbsp;&nbsp;to</div>
										<div class="col-md-12" style="padding-top:10px">
										<s:textfield cssClass="form-control" id="tktend" type="text" name="ticketData.newEndDate" />
										</div>
									
									</div>
							
							
							</s:else>
														
                           </div>  
                             
                            <div class="col-md-12">
                           	<s:if test="%{isrecurring==true}">
                           	<div class="col-md-1">
                             <label class="col-md-3 control-label">Ends</label>  
                             </div>
                           	<div class="col-md-4">
							<s:textfield   cssClass='form-control' name="ticketData.endBefore" size="2" theme="simple"></s:textfield>day(s)
							</div>
							<s:hidden name="ticketData.enddateTimeBeanObj.monthPart"></s:hidden>
							<s:hidden name="ticketData.enddateTimeBeanObj.ddPart"></s:hidden>
							<s:hidden name="ticketData.enddateTimeBeanObj.yyPart"></s:hidden>
							<div class="col-md-4">
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
                        
                                 
                        
                          <div class="form-group">
                            <label for="name" class="col-md-3 control-label">Service Fee&nbsp;*</label>
                            <div class="col-md-5">
                                <s:set name="processfeepaid" value="ticketData.processFeePaidBy"></s:set>
                          <input type="radio" class="tktichk" name="ticketData.processFeePaidBy" id="processFeeAttendee" value="processFeeAttendee" <s:if test="%{#processfeepaid != 'processFeeMgr'}">checked="checked"</s:if>>&nbsp;Collect from Attendee&nbsp;
                       <s:text  name='ticketData.currency'></s:text><s:textfield  id="processingfee" name="ticketData.processingFee" theme="simple" size="12" placeholder="Enter Number"></s:textfield><br/>
                       <div style="height:5px;"></div>
                       <input type="radio" class="tktichk" name="ticketData.processFeePaidBy"  id="processFeeMgr" value="processFeeMgr" <s:if test="%{#processfeepaid == 'processFeeMgr'}">checked="checked"</s:if>>&nbsp;Paid by Manager

                            </div>
                        </div>
                                         
                        <s:set name="typeOfTicket" value="ticketAtt"></s:set>
                          <div class="form-group">
                            <label for="name" class="col-md-3 control-label">Collect Attendee Name</label>
                            <div class="col-md-5">
                     <input type="radio" class="tktichk" name="ticketData.isAttendee" id="aticketType" value="ATTENDEE" <s:if test="%{#typeOfTicket == 'ATTENDEE'}">checked="checked"</s:if>>&nbsp;Yes, on each ticket sale
	                 <div style="height:5px;"></div>
	                 <input type="radio" class="tktichk" name="ticketData.isAttendee" id="nticketType" value="NONATTENDEE" <s:if test="%{#typeOfTicket != 'ATTENDEE'}">checked="checked"</s:if>>&nbsp;No, buyer name collected at transaction level is sufficient

                            </div>
                        </div>
                        
                        <s:set name="scancoderequired" value="ticketData.isScanCode"></s:set>
                              <div class="form-group">
                            <label for="name" class="col-md-3 control-label">Scan Codes Required<br>(Includes barcode & QRcode)</label>
                            <div class="col-md-5">
                                <input type="radio" class="tktichk" name="ticketData.isScanCode" id="yscancode" value="Yes" <s:if test="%{#scancoderequired == 'Yes'}">checked="checked"</s:if>>&nbsp;Yes&nbsp;&nbsp;
	                             <input type="radio" class="tktichk" name="ticketData.isScanCode" id="nscancode" value="No" <s:if test="%{#scancoderequired != 'Yes'}">checked="checked"</s:if>>&nbsp;No

                            </div>
                        </div>
                        
                        

				   <s:set name="hubSize" value ="hubMap.size()" ></s:set>
				   <s:if test="%{#hubSize > 0}">
				            <div class="form-group">
                            <label for="name" class="col-md-3 control-label">Member Only Ticket *</label>
                            <div class="col-md-5">
                            <s:set name="memTicket" value="ticketData.memberTicket"></s:set>
                            <s:set name="memTicketlen" value="ticketData.memberTicket.length()"></s:set>                            
                               <input type="radio" class="tktichk" name="ticketData.memberTicket" value="No" <s:if test="%{#memTicket == 'No'}">checked="checked"</s:if> ></input>No&nbsp;&nbsp;
								<input type="radio" class="tktichk" name="ticketData.memberTicket" value="Yes" <s:if test="%{#memTicket == 'Yes'}">checked="checked"</s:if>></input>Yes&nbsp;&nbsp;
								<s:set name="hubid" value="ticketData.hubId"/>
								<select Class='form-control' name="ticketData.hubId" id="ticketData.hubId" >
								<s:iterator value="%{hubMap}" var="type">
								<s:set name="cval" value="key"/>
								<option value="<s:property value="key"/>" <s:if test="%{#hubid==#cval}">selected='selected'</s:if>>${value}</option>
								</s:iterator>
								</select>
                            </div>
                        </div>
                        </s:if>
                         <hr>
                          <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-6 pull-right">
                                <button id="tktsave" class="btn btn-primary" ><i class="fa fa-pencil-square-o"></i> Submit</button>
                                <button id="cancel" class="btn btn-danger"><i class="fa fa-times"></i> Cancel</button>
                            </div>
                        </div>
                       </form>
                       
                       
                </div>
            </div>
        </div>
    <script>var eid='${eid}';</script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="/main/js/bootstrap.min.js"></script>
    <script src="/main/js/jquery-sortable.js"></script>
     <script src="/main/js/eventmanage/ticketingvalidation.js"></script>
     <script src="/main/bootstrap/js/bootbox.min.js"></script>
     <script src="/main/js/jquery.validate.min.js"></script>
     <script src="/main/js/jquery.tooltipster.js"></script>
     <script src="/main/bootstrap/js/jquery.datetimepicker.js"></script>
     <script src="/main/bootstrap/js/icheck.min.js"></script>
    <script>
     $(document).ready(function(){
    	 $('#tktstart').datetimepicker({
		       format:'m/d/Y g:i A',
		       formatTime:'g:i A'
		   });
		 
		 $('#tktend').datetimepicker({
		       format:'m/d/Y g:i A',
		       formatTime:'g:i A'
		   });

		 $('input.tktichk').iCheck({  
				checkboxClass: 'icheckbox_square-blue', 
				radioClass: 'iradio_square-blue'});
     });


/* $('#tktsave').click(function(){

	$.ajax({
           type:'get',
           data:$('#ticketingForm').serialize(),
           url:'TicketDetails!save',
           success:function(result){
              if(result.indexOf('errorMessage')>-1){
				$('#ticketformerrormessages').show();
				$('#ticketformerrormessages').html(result);
				parent.resizeIframe();
                  }else{
						parent.$('#myModal').modal('hide');
                      }
               }
		});
}); */
     
         </script>
     
   
    
 
    <style>
            .form-validate-theme {
                border-radius: 5px; 
                border: 2px solid #ff4747;
                background: #fff4f4;
                color: #000;
            }
            .form-validate-theme .tooltipster-content {
                width:100%;
                font-size: 14px;
                line-height: 16px;
                padding: 8px 10px;
            }
        </style>
