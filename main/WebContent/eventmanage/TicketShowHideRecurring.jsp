<%@taglib uri="/struts-tags" prefix="s" %>

 <div class="row">
                <div class="col-md-12">      
                 <s:set value="%{alltickets.size()}" name="size"></s:set>
				<s:if test="%{#size==0}">
				Currently there are no tickets added to the event.
				</s:if>
				<s:else>  
				<div class="row">
                          <div class="col-md-6">
                       			<s:select name="eventdate" id='recurringdate' headerKey="1"	headerValue="-- Select Date --" 
		             theme="simple" cssClass='form-control'	list="eventdates" listKey="key" listValue="value"  onchange="getRecurringDateTickets()"/>
	                 </div>
                    </div>   
                    <br/>
                    
			<s:if test="%{eventdate !=''}">	      
				   <div class="row">
                      <label class="col-md-7"> Select Tickets to show (Unselect to hide) </label>
                    </div>  
                 <div style="overflow-y:scroll;max-height:150px;overflow-x:hidden;width:468px;">          
                 <s:form name="ticketshowhideform" action="TicketShowHide!update" id="ticketshowhideform"  cssClass='form-horizontal' theme="simple" method="post">
                 <s:hidden name="eid"></s:hidden>
                 <s:iterator value="%{alltickets}" var="ticket" >
                  
                  <div class="form-group">
                   <div class="col-md-1"></div>
                   <div class="col-md-11">
                <s:checkbox name="seltickets"  cssClass="tktshowhide" fieldValue="%{key}" value="%{seltickets.contains(key)}" onclick="updaterecurrshowhide(this)"/>&nbsp;${value}
                </div>
                  
                   </div>                    
                        
       </s:iterator>      
                </s:form>
                </div>
                </s:if>
                 </s:else>
                 <div id="recurimgload" style="display:none"><center>Please Wait<br/><img src="../images/ajax-spinner.gif"></img></center></div>
                 </div></div>
 
