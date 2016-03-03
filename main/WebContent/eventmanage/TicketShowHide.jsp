<%@taglib uri="/struts-tags" prefix="s" %>




 <div class="row">
                <div class="col-md-12">      
                 <s:set value="%{alltickets.size()}" name="size"></s:set>
				<s:if test="%{#size==0}">
				Currently there are no tickets added to the event.
				</s:if>
				<s:else>         
				   <div class="row">
                      <label class="col-md-7"> Select Tickets to show (Unselect to hide)</label>
                        <div class="col-md-5">
                        <a href="javascript:;" name="CheckAll" id="tktshowhidechk" >Select All</a>&nbsp;<a href="javascript:;" name="UnCheckAll" id="tktshowhideunchk" >Clear All</a>
                   
                         </div>
                    </div> 
                    <br/>  
                    <div style="overflow-y:scroll;max-height:150px;overflow-x:hidden;width:468px;">   
                 <s:form name="ticketshowhideform" action="TicketShowHide!update" id="ticketshowhideform"  cssClass='form-horizontal' theme="simple" method="post">
                 <s:hidden name="eid"></s:hidden>
                 <s:iterator value="%{alltickets}" var="ticket" >
                  
                  <div class="form-group">
                  <div class="col-md-1"></div>
                   <div class="col-md-11"><s:checkbox name="seltickets" cssClass="tktshowhide" id="seltickets" fieldValue="%{key}" value="%{seltickets.contains(key)}" />  ${value}
                     </div>         
                   </div>                    
		       </s:iterator>      
                </s:form>
                </div>
                 </s:else>       
 
   </div></div>                 