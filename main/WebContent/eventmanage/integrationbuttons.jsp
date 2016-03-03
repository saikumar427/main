
<%@taglib uri="/struts-tags" prefix="s"%>
<s:set name="powertype" value="powertype"></s:set>
  <div class="col-md-12">
  	           <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><s:text name="bc.sections.header"/></h3>
                                </div><!-- pannel heading -->
                                <div class="panel-body" style="background-color: #F5F5F5;">
                                    <div class="row">
                                    <div class="col-md-11">
                                    <a href='<s:text name="eventData.eventURL"></s:text>'>                                   
                                    <s:if test="%{#powertype == 'Ticketing'}"><img border='0' src='../images/register.jpg'/>
                                    </s:if>
                                    <s:elseif test="%{#powertype == 'RSVP'}"><img border='0' src='/home/images/rsvp.jpg' width="100" height="38"/>
                                    </s:elseif></a>
                                    </div>
                                    </div><!-- row 1 --> 
                                    <div class="row">
                                    <div class="col-md-11"><s:text name="bc.copy.and.paste.code"/>
                                   	</div> 
                                    </div><!-- row 2 --> 
                                    <div class="row">
                                    <div class="col-md-11">
                                    <textarea readonly="readonly" style="background-color: #FFFFFF" class="form-control" cols="80" rows="4" onClick="this.select()"><s:if test="%{#powertype == 'Ticketing'}"><a href="<s:text name='eventData.eventURL'></s:text>" ><img border="0" src="<s:text name='eventData.serverAddress'></s:text>/home/images/register.jpg"/></a></s:if>
									<s:elseif test="%{#powertype == 'RSVP'}"><a href="<s:text name='eventData.eventURL'></s:text>" ><img border="0" src="<s:text name='eventData.serverAddress'></s:text>/home/images/rsvp.jpg"/></a>
									</s:elseif>
									</textarea>
									</div>
                                    </div><!-- row 3 --> 
                                    
                                    <br/>
                                    
                                    <s:if test="%{#powertype == 'Ticketing'}">
                                    <div class="row">
                                    <div class="col-md-11">
									<a href='<s:text name="eventData.eventURL"></s:text>' ><img border='0' src='../images/buyticket.jpg'/></a>
									</div> </div><!-- row 4 -->
									<div class="row">
                                    <div class="col-md-11"><s:text name="bc.copy.and.paste.code"/>
                                   	</div> 
                                    </div><!-- row 5 --> 
									 <div class="row">
		 							 <div class="col-md-11">
	 								  <textarea readonly="readonly" style="background-color: #FFFFFF" class="form-control" cols="80" rows="2" onClick="this.select()"><a href="<s:text name='eventData.eventURL'></s:text>" ><img border="0" src="<s:text name='eventData.serverAddress'></s:text>/home/images/buyticket.jpg"/></a></textarea>
	 								  </div> </div><!-- row 6 -->
</s:if>
                                    
                                    
                                   
                                </div> <!-- pannel body -->                              
                            </div>
  </div><!-- col - 9-->
  
  