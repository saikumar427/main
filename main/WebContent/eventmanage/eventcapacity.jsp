

<%@taglib uri="/struts-tags" prefix="s" %>


 <div class="row">
                <div class="col-md-12">   
                <div class="alert alert-danger" id="setcapacityerror" style="display: none;"></div>         
                    <s:form name="EventCapacityForm" action="TicketDetails!saveEventCapacity" id="EventCapacityForm" cssClass='form-horizontal'>
                    <s:hidden name="eid" />
                    <s:set name="tkttyp" value="%{tkttype}"></s:set>
                        
                        <div class="form-group">
                        <div class="col-md-1" style="width: 6.333%;">
                        <input type="radio" class='evtcapacity' name="tkttype" value="limited" <s:if test="%{#tkttyp=='limited'}">checked="checked"</s:if>/>
                        </div>
                        
                        
                            <div class="col-md-11">
                            <div class='row'>
                              <div class="col-md-4">
                               Event level limit to  &nbsp;
                               </div>
                               <div class="col-md-4 col-md-pull-1" style="margin-top: -7px;right: 11.333%;">
                               <s:textfield  cssClass='form-control'name="tktcount" size="12" placeholder="Enter Count"/> 
                               </div>
                                <div class="col-md-3 col-md-pull-1" style="right: 15.333%;">
                                  tickets
                                </div>
                            </div></div>
                        </div>
                            <div class="form-group">
                            <div class="col-md-1" style="width: 6.333%;">
                             <input type="radio"  class='evtcapacity' name="tkttype" value="unlimited" <s:if test="%{#tkttyp=='unlimited'}">checked="checked"</s:if>/> 
                            </div>
                            <div class="col-md-11">
                              No event level limit, follow individual ticket level settings  
                              </div>
                        </div>
   </s:form>
   </div></div>             