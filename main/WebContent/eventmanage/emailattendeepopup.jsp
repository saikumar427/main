 <%@taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<style>
label{
	margin-left: 5px;
}
ul.errorMessage li {
display: list-item;
}
</style>


 <div class="well"  style="margin-top:5px;">
 
 <div class="row">
       	<div class="alert alert-danger" id="errormsg" style="display: none" ></div>
 </div>
 
 <div class="row">           
	 <div class="col-md-12 col-sm-12">
	                        
	                        <s:form cssClass="form-horizontal" id="emailattendeepopupform" name="emailattendeepopupform" action="EmailAttendees!saveMail" method="post" theme="simple" >
							<s:hidden name="eid" id="eid"></s:hidden>
							<s:hidden name="blastid" id="blastid"></s:hidden>
							<s:hidden name="powertype" id="powertype"></s:hidden>
							<s:hidden name="emailAttendeesData.email"></s:hidden>
							<s:set name="buyer"  value="emailAttendeesData.buyer"></s:set>
							<s:set name="selectAttendeeType"  value="emailAttendeesData.selectAttendeeType"></s:set>
							<s:set name="sendTo" value="emailAttendeesData.sendTo"></s:set>
							<s:set name="attendee" value="emailAttendeesData.attendee"></s:set>
							<s:set name="clist" value="emailAttendeesData.contentlist"></s:set>
							<s:set name="rlist" value="emailAttendeesData.recurringlist"></s:set>
							<s:hidden id="recurring" value="%{isrecurring}"></s:hidden>
							 <s:hidden id="monthPart" name="emailAttendeesData.dateTimeBeanObj.monthPart"></s:hidden>
							<s:hidden id="ddPart" name="emailAttendeesData.dateTimeBeanObj.ddPart"></s:hidden>
							<s:hidden id="yyPart" name="emailAttendeesData.dateTimeBeanObj.yyPart"></s:hidden>
							<s:hidden id="hhPart" name="emailAttendeesData.dateTimeBeanObj.hhPart"></s:hidden>
							<s:hidden id="mmPart" name="emailAttendeesData.dateTimeBeanObj.mmPart"></s:hidden>
							<s:hidden id="ampm" name="emailAttendeesData.dateTimeBeanObj.ampm"></s:hidden> 
							<s:hidden id="desctypedb" value="%{emailAttendeesData.descriptiontype}"></s:hidden> 
							<s:hidden id="desccontent" value="%{emailAttendeesData.content}"></s:hidden> 
							<s:hidden id="wysiwygcontent" name="emailAttendeesData.description"></s:hidden>
	                       
	                       
	                      <%--  <s:hidden id="sendtypee" name="emailAttendeesData.sendAt"></s:hidden> --%>
	                       <s:hidden id="notidsid" value="%{ticketsList.size}">  </s:hidden>
	                       
	                       
	                       
	                       
	                        <s:if test="%{emailAttendeesData.contentlist.size>0}"> 
     								 <div class="row">
                                    	<div class="col-md-2 col-sm-3 control-label"><s:text name="ea.copy.from.lbl"></s:text></div>
                                    	<div class="col-md-6 col-sm-6"><s:select list="#clist" listKey="key" cssClass="form-control" listValue="value" headerKey="" headerValue="%{getText('ea.select.schedule.dropdown')}" id="scheduleblast" onchange="getBlast()"></s:select></div>
                               		 	<div class="col-md-4 col-sm-3"></div>
                               		 </div>
                               		 <br>
                            </s:if>
                            
                            <div class="row">
                                <div class="col-md-2 col-sm-3 control-label"><s:text name="ea.send.to.rb.lbl"/><span class="required">*</span>     </div>                                   
                                <div class="col-md-6 col-sm-6">
                                	<input type="checkbox" class="btnstyles" name="emailAttendeesData.buyer" id="buyer" <s:if test='%{#buyer == "Y"}'>checked='checked'</s:if>/>&nbsp;   <s:text name="ea.send.to.buyers.rb.lbl"></s:text> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 							<input type="checkbox" class="btnstyles" name="emailAttendeesData.attendee" id="attendee"<s:if test='%{#attendee == "Y"}'>checked='checked'</s:if> />&nbsp;<s:text name="ea.send.to.attendee.rb.lbl"></s:text>
		 						</div>
		 						<div class="col-md-4 col-sm-3"></div>
                            </div>
                            <br>
                            
                            <div style="clear:both;"></div>
                            
                            <div class="row" id="sendto_attendees" style="<s:if test='%{#attendee == "Y" && powertype=="Ticketing"}'>display: block</s:if><s:else>display: none</s:else>">
                            <div class="col-md-5 col-md-offset-3">
                           		
									<div class="col-md-6" >
										<input type="radio" id="ALL" class="btnstyles radiotype" name="emailAttendeesData.sendTo" value="ALL" 
										<s:if test="%{#sendTo=='ALL'}">checked=true</s:if>>&nbsp;<s:text name="ea.attendee.type.all"></s:text>
                              		</div>
                              		<div class="col-md-6">
	                              		<input type="radio" id="selatt" class="btnstyles radiotype" name="emailAttendeesData.sendTo" value="selatt"  
										<s:if test="%{#sendTo!='ALL'}">checked=true</s:if>>&nbsp;<s:text name="ea.attendee.type.selected"></s:text>					  		
                           			</div>
                           		
                           	</div>
                           	</div>
                           	
                           	
                           	<div id="notickets" class="row" style="display:none;">
                           	<div class="col-md-3 col-sm-3" ></div>
                           	<div class="col-md-5  col-sm-6 background-options  sm-font" > <s:text name="ea.no.tkts.avail.lbl"/> </div>
                           	</div>
                           	
                                                    
                            <div class="row" id="attendeecheck1" style=" padding-right: 30px;   <s:if test='%{#attendee == "Y" && powertype=="Ticketing" && #sendTo!="ALL"}'>display: block</s:if><s:else>display: none</s:else>">
                           		<div class="col-md-3 col-sm-3" ></div>
                           		<div class="col-md-4  col-sm-6 background-options  sm-font" >
                           		
									<div class="row">
									 <div class="col-md-6" > 
										<input type="radio" id="alltickets" class="btnstyles attendees-type" name="emailAttendeesData.selectAttendeeType" value="TICKET" <s:if test='%{#selectAttendeeType == "TICKET"}'>checked='checked'</s:if> />&nbsp;<s:text name="ea.emailattendee.ticket.type.tickettype"></s:text>
                              		 </div> 
                              		
                              		 <div class="col-md-6"> 
                              			<input type="radio" id="tids_type" class="btnstyles attendees-type" name="emailAttendeesData.selectAttendeeType" value="TID" <s:if test='%{#selectAttendeeType == "TID"}'>checked='checked'</s:if> />&nbsp;<s:text name="ea.emailattendee.ticket.type.transactionids"></s:text>					  		
                           			 </div> 
                           			<br>
                           			<div style="<s:if test="%{#selectAttendeeType == 'TICKET'}">display: block;</s:if><s:else>display: none;</s:else>margin-left: 10px;" id="tktlist">
                           				<div style="padding-left:20px;">
                           					<s:iterator value="%{ticketsList}" var="attributes" status="stat">
                             					<!-- <div style="height:10px;"></div>&nbsp; -->
												<s:checkbox id="tlist" cssClass="tkts" name="chkedTickets"  fieldValue="%{key}"  value="%{selectedTkts.contains(key)}" />&nbsp;${value}<br/>
											</s:iterator>
										</div>
                           			</div>
                           			
                           			<div style="<s:if test="%{#selectAttendeeType == 'TID'}">display: block;</s:if><s:else>display: none;</s:else>padding:12px 10px 0px 10px;" id="tidslist">
									   		<div class="alert alert-danger" id="invalidTIDs" style="display: none" ></div>
									   		<s:textarea name="emailAttendeesData.tidList" cssClass="form-control" rows="8" cols="50" id="tid_list"></s:textarea>
                           					<div class="xsm-font"><s:text name="ea.comma.sep.lbl"/></div>
                           			</div>
                           		</div>
								</div>
                           		<div class="col-md-5 col-sm-3"></div>
                           	</div>
                            
                           
                           
                           
                            <div class="row">
                            <br>
                                <div class="col-md-2 col-sm-3 control-label">   <s:text name="ea.subject.header"/><span class="required">*</span>  </div>
                                <div class="col-md-6 col-sm-6"><s:textfield name="emailAttendeesData.subject" id="subject" cssClass="form-control" theme="simple" size="60" /></div>
                                <div class="col-md-4 col-sm-3"></div>
                            </div>
                            <br>
                          
                                
                                
         					<div class="row">
								<div  class="col-md-2 col-sm-3 control-label"><s:text name="event.description.lbl"/></div>
								<div class="col-md-6 col-sm-8">
					   
								<div class="btn-group pull-right"> 
									<s:set name="desctype" value="emailAttendeesData.descriptiontype"></s:set>  
										<div class="center btn-group btn-toggle" data-toggle="buttons">
											<label  id="selection_block" class="optiontype btn <s:if test="%{#desctype=='wysiwyg' || #desctype==''}">btn-default </s:if><s:else>btn-active</s:else>  ">
												<input class="radiotype"  id="wysiwyg"  name="emailAttendeesData.descriptiontype" value="wysiwyg"  type="radio" <s:if test="%{#desctype=='wysiwyg'}">checked='checked' </s:if> >&nbsp;
													<s:text name="event.description.btn.wysiwyg.lbl"/>
											</label>  
											<label   id="textarea_block" class="optiontype btn <s:if test="%{#desctype=='html'}">btn-default </s:if><s:else>btn-active</s:else>"  >
												<input  class="radiotype" id="html" name="emailAttendeesData.descriptiontype" value="html"  type="radio" <s:if test="%{#desctype=='html'}">checked='checked' </s:if>>&nbsp;
													<s:text name="event.description.btn.html.lbl"/>
											</label>                                        
											<label  id="text_block" class="optiontype btn <s:if test="%{#desctype=='text'}">btn-default </s:if><s:else>btn-active</s:else>">
												<input  class="radiotype" id="text" name="emailAttendeesData.descriptiontype" value="text" type="radio" <s:if test="%{#desctype=='text'  || #desctype==''}">checked='checked'</s:if>  >&nbsp;
													<s:text name="event.description.btn.text.lbl"/>
											</label>
										</div>
					  			</div> 
								</div>
								
								<div class="col-md-4 col-sm-3"></div>
							</div> 
        					<br/>                       
                                  
                                  <div class="row">
                                  <div  class="col-md-2 col-sm-3 control-label">   </div>
 						 		  <div class="col-md-6 col-sm-6">	
                            			<div id="textboxx" style="display:none;">                                                   		
											<s:textarea name="emailAttendeesData.content" rows="15" cols="101" id="textmsg" cssClass="form-control"></s:textarea>									
                            				<br>
                            			</div>
                            			<div id="wysiwygboxx">                          
									 		<%@include file="/eventmanage/emailatteendeesdescription.jsp" %>	 
                                 	 		<br>
                                		</div>                            
 						    	  </div>
 						    	  <div class="col-md-4 col-sm-3"></div>
 								  </div>                               
                                  <br>
                                 
                                <s:if test="%{emailAttendeesData.recurringlist.size>0}">     
                                 	
                                	<div class="row">
                                    	<div class="col-md-2 col-sm-3 control-label"><s:text name="global.sel.rec.date.lbl"></s:text>  </div>   
                                    	<div class="col-md-6 col-sm-6"><s:select  cssClass="form-control" name="emailAttendeesData.seldate" list="#rlist" listKey="key" listValue="value" headerKey="" headerValue="%{getText('ea.recc.date.sel')}" id="rlist"></s:select></div>
                                		<div class="col-md-4 col-sm-3"></div>
                                	</div>
                                	<br>  
                                </s:if>
                                
                                <s:set name="type" value="emailAttendeesData.sendAt"></s:set>
                                 
                                
                                <div class="row">                             
                                    <div class="col-md-2 col-sm-3 control-label"></div>                                 
                                    <div class="col-md-5 col-sm-6">
                                    	 <label>	<input type="checkbox" id="sendicheck"   <s:if test="%{#type == 'date' || #type == 'later'}">checked="checked" </s:if>    />  <span class="checkbox-space"> <s:text name="ea.shedul.later.lbl"/> </span> </label> 
                                    </div>	
                                    <div class="col-md-5 col-sm-3"></div>
                               </div>
                               <div style="clear:both;"></div>
                               
                               <div class="row">
                               			<div class="col-md-2 col-sm-3 control-label"></div> 
                               			<div style="display:<s:if test='%{#type=="later" || #type=="date"}'>block</s:if><s:else>none</s:else>;"  id="orangebox"    class="background-options  sm-font row col-md-6 col-sm-6">
                                    		<div class="col-md-12 col-xs-12">
                                    			<div class="col-xs-6 ">
													<input type="radio" class=" sm-font"  id="dateat" name="emailAttendeesData.sendAt" value="date" id="dateat"  <s:if test="%{#type == 'date'}">checked="checked" </s:if> />&nbsp;<s:text name="ea.send.at.rb.lbl"></s:text> 
												</div>
												<div class="col-xs-6 ">
													<input type="radio" class="sm-font"  id="laterchkid"  name="emailAttendeesData.sendAt" value="later" <s:if test="%{#type == 'later'}">checked="checked" </s:if>/>&nbsp;<s:text name="ea.send.later.rb.lbl"></s:text>
												</div>
                                    		</div>
                                    	 	<div class="col-md-5 col-xs-12" style="display:<s:if test='%{#type=="date"}'>block;</s:if><s:else>none;</s:else>  padding-left:35px;" id="senddate">
                                    			<s:textfield cssClass="form-control" name="emailAttendeesData.startDate" id="startDate" theme="simple" size="60"  placeholder="%{getText('ea.send.at.ph.lbl')}"   />  
                                
                  <div class="sub-text">   <s:text name="global.mm.dd.yyyy.lbl"/> </div>		</div> 
                                    	</div>
                               		
                               </div>
                               <br>
                               <div class="center row ">
                              		<input type="button" value="<s:if test='%{#type=="later" || #type=="date"}'> <s:text name="global.save.btn.lbl"/> </s:if><s:else><s:text name="ea.send.now.rb.lbl"/></s:else>" class="btn btn-primary" id="attendeesubmit"/>
         							<button class="btn btn-cancel" id="attendeecancel">   <i class="fa fa-times"></i>   </button>
                               </div>  <%-- <s:property   value="%{#type}"/> --%>
                           </s:form>
           </div>  
        </div> 
   </div> 
                                <script>
                               
                              
                               $('input.radiotype').change(function() {
                					var selected=$(this).attr('id');
                					activatedesc(selected);		
                				});
                                
                              function   activatedesc(selected){
                            	  if("wysiwyg"==selected)
                            	  {
                            		$('#selection_block').addClass("btn-default").removeClass("btn-active");
              				        $('#textarea_block').addClass('btn-active').removeClass("btn-default");
              				        $('#text_block').addClass('btn-active').removeClass("btn-default");
              				        $('#wysiwygboxx').show();
              				        $('#textboxx').hide(); 
              				        $('#wysiwyg').attr('checked',true);
              				        $('#html').attr('checked',false);
              				        $('#text').attr('checked',false); 
                            	  }
                                	
                            	if("html"==selected)
                        		{
                        		$('#selection_block').addClass("btn-active").removeClass("btn-default");
          				        $('#textarea_block').addClass('btn-default').removeClass("btn-active");
          				        $('#text_block').addClass('btn-active').removeClass("btn-default");
          				        $('#wysiwygboxx').hide();
        				        $('#textboxx').show();
        				        $('#wysiwyg').attr('checked',false);
        				        $('#html').attr('checked',true);
        				        $('#text').attr('checked',false);
                        		}
                            	  
                            	if("text"==selected)
                        		{
                        		$('#selection_block').addClass('btn-active').removeClass("btn-default");
          				        $('#textarea_block').addClass('btn-active').removeClass("btn-default");
          				        $('#text_block').addClass("btn-default").removeClass("btn-active");
          				      	$('#wysiwygboxx').hide();
        				        $('#textboxx').show();
        				        $('#wysiwyg').attr('checked',false);
        				        $('#html').attr('checked',false);
        				        $('#text').attr('checked',true);
                        		}
                            	  
                                }
                                
                                $(document).ready(function() {
                                	var dbdesctypee=$('#desctypedb').val();
                                	 /* alert(dbdesctypee); */
                                	if("wysiwyg"==dbdesctypee)
                                		{
                                		 $('#wysiwygboxx').show();
                   				         $('#textboxx').hide();
                   				         var content=$('#desccontent').val();
                   				         $('#editor').html(content);
                                		}
                                	else
                                		{
                                		 $('#wysiwygboxx').hide();
                				         $('#textboxx').show();
                                		}
                                });   /* ready closed */
                                	
                                	 
                                
                                	
                               
                                $(document).ready(function() {
                                	/* $('#createemail').prop("disabled",true); */

                                	$('input.btnstyles').iCheck({  
                                		checkboxClass: 'icheckbox_square-grey', 
                                		radioClass: 'iradio_square-grey'
                                		});
                                	$('.tkts').iCheck({
                                		checkboxClass: 'icheckbox_square-grey', 
                                		radioClass: 'iradio_square-grey'
                                	});
                                	
                                	$('#sendicheck').iCheck({  
                                		 checkboxClass: 'icheckbox_square-grey', 
                                		 radioClass: 'iradio_square-grey'});
                                	
                                	$('#laterchkid').iCheck({  
                                		checkboxClass: 'icheckbox_square-grey', 
                                		radioClass: 'iradio_square-grey'
                                		});
                                		
                                	$('#dateat').iCheck({  
                                		checkboxClass: 'icheckbox_square-grey', 
                                		radioClass: 'iradio_square-grey'
                                		});
                                	

                                	 $('#startDate, #endDate').datetimepicker({
                                		 format:'m/d/Y g:i A',
                                	     formatTime:'g:i A',
                                         timepicker: true
                                     });
                                	 
                                	
                                      });      /* ready closed */
                                
                                
                                      $('#attendeecancel').click(function(e){ 
                                   		 var blastid = $('#blastid').val();
                                   		 if(blastid=='') 
                                   			 {/* alert("in new"); */
                                   			 $('#createemail').prop("disabled",false);
                                   			 $('#newEmailBox').html('');
                                   			 $('html, body').animate({ scrollTop: $("#createemail").height()}, 1000);
                                   			 return false;
                                   			 } 
                                   		 else
                                   		 closeBox(blastid);
                                   		e.stopPropagation();
                                   		e.preventDefault();
                                   		
                                        });
                                	 
                                      
                                $('input#attendee').on('ifChecked', function(event){
                        			var powertype=document.getElementById('powertype').value;
                        			if(powertype=='Ticketing')
                        			{    
                        				$('#ALL').iCheck('check');
                        					$("#sendto_attendees").slideDown('slow');
                        					var val = $('input[name="emailAttendeesData.sendTo"]:checked').val();
    										if(val=='selatt') 
    											$('#attendeecheck1').show();
                        			}
                        		});
                                
                                      
                                      
                                $('input#attendee').on('ifUnchecked', function(event){
                        			var powertype=document.getElementById('powertype').value;
                        			if(powertype=='Ticketing')
                        			{   
                        				$('#selatt').iCheck('uncheck');
                        				$("#sendto_attendees").slideUp('slow');
                        				/* $("#attendeecheck1").slideUp('slow'); */
                        				$('#notickets').hide();
                        				$('#attendeecheck1').hide();
                        				
                        			}
                        		});
                                      
                                $('input#ALL').on('ifChecked', function(event){
						      		$('#attendeecheck1').slideUp('slow');
						      		$('#notickets').hide();
						    	});   
                                      
                                	  		
                                $('input#selatt').on('ifChecked', function(event){
									 var tidlen=$('#notidsid').val();
									 
									if(tidlen>0)
									{
						      		$('#attendeecheck1').slideDown('slow');
						      		$('#alltickets').iCheck('check');
									}
									else
										$('#notickets').show();
						    	});
                                
                                
                                
                                
                                
                                
                                $('input.attendees-type').on('ifChecked', function(event){
                        			var type=$(this).val();
                        			if(type=='TICKET'){
                        				
                        				$('#tidslist').hide();
                        				$('#tktlist').show();
                        			}
                        			if(type=='TID'){
                        				
                        				$('#tktlist').hide();
                        				$('#tidslist').show();
                        				
                        			}
                        		});
                                
                                
                                
                                
                                
                                
                                
                                $('#sendicheck').on('ifChecked', function(event){ 
                            		$('#orangebox').show();
                            		$('#attendeesubmit').val(props.global_save_btn);
                            	});
                            	
                            	$('#sendicheck').on('ifUnchecked', function(event){ 
                            		$('#orangebox').hide();
                            		$('#attendeesubmit').val(props.ea_send_now_btn_lbl);
                            	}); 
                            	
                            	$('#dateat').on('ifUnchecked', function(event){  
                            		$('#senddate').hide();
                            		$('#startDate').val('');
                            	});	
                            	
                            	$('#dateat').on('ifChecked', function(event){ 
                            		var dateonload=$('#monthPart').val()+'/'+$('#ddPart').val()+'/'+$('#yyPart').val()+" "+$('#hhPart').val()+":"+$('#mmPart').val()+" "+$('#ampm').val();
                            		$('#startDate').val(dateonload);
                            		$('#senddate').show();
                            	});
                                
    var header;  
    var Content="";
    $('#attendeesubmit').click(function(){
	
	$('#attendeesubmit').attr('disabled','disabled');
	alertMessage= $('#attendeesubmit').val();
	showProcessing('forLoad');
	
	if( $('#laterchkid').is(':checked') )
		header="later";
	else
		header="scheduled";
	
	var selecteddecktype=typedtext=$('input[class=radiotype]:checked').val(); 
	if("wysiwyg"==selecteddecktype )
	{
		typedtext=$('#editor').html();
		$('#wysiwygcontent').val(typedtext);
	}
	
	var valid=false;
	$(errormsg).html('');
	$(errormsg).hide();
	 
	 var subject=document.getElementById("subject").value;
	 if(subject==''){
		 $('#attendeesubmit').removeAttr('disabled');
		 valid=true;
		 hideProcessing();
		 $(errormsg).append('<ul class="errorMessage">  <li><span>'+props.ea_email_subject_empty+'</span></li> </ul>');
	}
	 
	 
	 if("wysiwyg"==selecteddecktype)
		 Content=$('#editor').html();
	 else
		 Content=document.getElementById("textmsg").value;
		 if(Content=='') 
		 {
			 $('#attendeesubmit').removeAttr('disabled');
			 valid=true;
			 hideProcessing();
			 $(errormsg).append('<ul class="errorMessage">  <li><span> '+props.ea_email_content_empty+'</span></li> </ul>');
		 }
		
			 if(  $('#sendicheck').is(':checked')  )
			 {
				if( $('#dateat').is(':checked')   ||    $('#laterchkid').is(':checked') ) 
					{
					if($('#dateat').is(':checked'))
						{
						if($('#startDate').val()=='')
							{
							$('#attendeesubmit').removeAttr('disabled');
							 valid=true;
							 hideProcessing();
							 $(errormsg).append('<ul class="errorMessage">  <li><span>'+props.ea_email_date_empty+'</span></li> </ul>');
							}
						}
					}
				else
					{
					$('#attendeesubmit').removeAttr('disabled');
					 valid=true;
					 hideProcessing();
					 $(errormsg).append('<ul class="errorMessage">  <li><span>'+props.ea_schedule_type+'</span></li> </ul>');
					
					}
			}      
			 
	if(valid)
	{
		 $(errormsg).show(); 
		 /* $('html, body').animate({ scrollTop: $("#errormsg").height()}, 1000); */
		  $('html, body').animate({ scrollTop: $("#errormsg").offset().top-200 },1000); 
	}
	else
	{
		dateformter(); 
		addemail();
	}	
	
    });
												
										function dateformter()
										{	 
										
											if(document.getElementById('dateat').checked)    
												{
												var str=document.getElementById("startDate").value;
												
                              			  	var result = str.split(" ");
                                         	var datepart=result[0];
                                         	var timepart=result[1];
                                         	var ampm=result[2];
                                         	
                                         	var date=datepart.split("/");                                   	
                                         	var time=timepart.split(":");
                                         	
                                         	document.getElementById('monthPart').value=date[0];
                                         	document.getElementById("ddPart").value =date[1];                                   	
                                         	document.getElementById("yyPart").value =date[2];
                                         	
                                         	document.getElementById("hhPart").value =time[0];
                                         	document.getElementById("mmPart").value =time[1];
                                         	document.getElementById("ampm").value =ampm; 
											}
										}
											
										function addemail(){
										
											var isrecur=document.getElementById('recurring').value;
											var powertype=document.getElementById('powertype').value;
											if(document.getElementById('rlist'))
											var rlist=document.getElementById('rlist').value;
											
											if(isrecur)
											{
											if(rlist==''){
												hideProcessing();
												$('#attendeesubmit').removeAttr('disabled');
												bootbox.confirm(props.ea_sel_event_dste,function(result){	});
												return;
												}
											}
												
											var count=0;
											if(powertype=='Ticketing'){
												
												var alltickets=document.getElementById('alltickets').checked;
												var attchk=document.getElementById('attendee').checked;
												
												if(document.getElementById('ALL').checked){  
													attchk=false;   
													$('#alltickets').iCheck('uncheck'); 
											 		$('#tids_type').iCheck('uncheck');	}						 	
												
												
												var tids_type=document.getElementById('tids_type').checked;
												if(document.getElementById('tlist'))
													{
													var obj=document.emailattendeepopupform.chkedTickets;
												if(obj.length==undefined)
												{
													if(obj.checked==true)
														count++;
												}else
												{
												for(var i=0;i<obj.length;i++)
												{
													if(obj[i].checked==true)
														count++;
												}
												}
												
												if(attchk==true){
													if(alltickets==true && count==0){
														hideProcessing();
														$('#attendeesubmit').removeAttr('disabled');
														bootbox.confirm(props.ea_Sel_atleast_one_ticket,function(result){
														});
														return;
													}
													if(tids_type==true){
														var TIDList=$("#tid_list").val().trim();
														if(TIDList==''){
															hideProcessing();
															$('#attendeesubmit').removeAttr('disabled');
															bootbox.confirm(props.ea_enter_comma_sep_tids,function(result){
															});
															return;
														}else{
															var invalidTIDlist='',validbulkTIDlist='',validcount='0',invalidcount='0';
															TIDList=TIDList.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
															TIDList=processingTIDList(TIDList);
															var tidslist=TIDList.split(",");
															var nonduplicatelist=removeDuplicates(tidslist);
															if(nonduplicatelist.length>1000){
																bootbox.alert(props.global_tids_list_should_be_less_than_1000_errmsg);
																return false;
															}
															for(var i=0;i<nonduplicatelist.length;i++){
																if(!nonduplicatelist[i].match(regExpForTID)){
																	if(invalidTIDlist!='' && nonduplicatelist[i]!=''){
																		invalidTIDlist=invalidTIDlist+', '+nonduplicatelist[i];
																	    invalidcount++;
																   	}else{
																   		invalidTIDlist=invalidTIDlist+nonduplicatelist[i];
																	   	invalidcount++;
															   		}
															   }else{
																   if(validbulkTIDlist!='' && nonduplicatelist[i]!=''){
																	   validbulkTIDlist=validbulkTIDlist+','+nonduplicatelist[i];
																	   validcount++;
																	}else{
																		validbulkTIDlist=validbulkTIDlist+nonduplicatelist[i];
																	    validcount++;
																	} 
																}
															}
															$('#tid_list').val(validbulkTIDlist);
															if(invalidcount>0){
																
																/* $("#invalidTIDs").html(props.global_invalid_tids_errmsg+": "+invalidTIDlist);
																$("#invalidTIDs").show(); */
																$(errormsg).append('<ul class="errorMessage">  <li><span> '+props.global_invalid_tids_errmsg+":"+invalidTIDlist  +  '</span></li> </ul>');
																$(errormsg).show(); 
																/* $('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000); */
																$('html, body').animate({ scrollTop: $("#errormsg").offset().top-200 },1000);
																$('#attendeesubmit').removeAttr('disabled');
																 
																 hideProcessing(); 
																
																
																return false;
															}
														}
													}
												}
											}
											}

											/* var content=$('#textmsg').val(); */
											var content=Content;   /* declared above */
											var description=replaceSpecialChars(content);
											var finaldescription=convert(description);
											document.getElementById('textmsg').value=finaldescription;
											var eid=document.getElementById('eid').value;

 											$.ajax({
													type:"POST",
													data:$('#emailattendeepopupform').serialize(),
													url: 'EmailAttendees!saveMail',
													success: function( result ){
															if(!isValidActionMessage(result)) 
																return;

															if(result.indexOf("errorMessage")>-1)
															{
																 $('#attendeesubmit').removeAttr('disabled');
																 hideProcessing();
																 $('.alert').show();
																 $('.alert').html(result);
																		
															}
															else
															{
																var statusJson=eval('('+result+')');
 	       														var success=statusJson.success;
 	           													if(success.indexOf("success")>-1)
 	           													{
 	           														//closeBox();
 	           														$('#createemail').prop("disabled",false);
                           			 								$('#newEmailBox').html('');
 	           														
 	           														var url = 'EmailAttendees!reloadData?eid='+eid;
 	           														$.ajax({
 	           															url : url,
 	           															type : 'POST',
 	           															success : function(response)
 	           															{	
 	           															hideProcessing();
 	           															//alert(JSON.stringify(response));
 	           															//alert(JSON.stringify(JSON.parse(result)));
 	           															if(alertMessage=='Save')
 	           															{
	 	           															$('#notification-holder').html('');
	 	           															$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
																			notification(props.ea_email_attendee_save,"success");
 	           															}
 	           															else
 	           															{
	 	           															$('#notification-holder').html('');
	 	           															$('html, body').animate({ scrollTop: $("#notification-holder").height()}, 1000);
																			notification(props.ea_sent_inhout_msg,"success");
 	           															}
 	           															
 	           														/* $('#tabl1').dataTable().fnDestroy(false);
 	           													$('#tabl2').dataTable().fnDestroy(false);
 	           													$('#tabl3').dataTable().fnDestroy(false); */


    															
 	           															generateEmailAtteendeeTable(JSON.parse(response));
 	           															}
 	           															});
 	           											
 	           														if(header=="later")
 	           															activate("draftsid");
 	           														else
 	           															activate("scheduledid");
 	           												
	        	 												}   /* save ajax  success*/
															}   /* else case */
														}  /* first ajax success */
													});
												}	
										
                               

                                function convert(text){
                                	 var chars = ["ò","δ","σ","Σ","ρ","ω","Ω","Ψ","Ο","ξ","Ξ","ν","μ","Λ","κ","ι","θ","Θ","η","ζ","π","☻","☺","♠","♣","♦","♥","©","Û","®","ž","Ü","Ÿ","Ý","Þ","%","¡","ß","¢","à","£","á","À","¤","â","Á","¥","ã","Â","¦","ä","Ã","§","å","Ä","¨","æ","Å","©","ç","Æ","ª","è","Ç","«","é","È","¬","ê","É","­","ë","Ê","®","ì","Ë","¯","í","Ì","°","î","Í","±","ï","Î","²","ð","Ï","³","ñ","Ð","´","ò","Ñ","µ","ó","Õ","¶","ô","Ö","·","õ","Ø","¸","ö","Ù","¹","÷","Ú","º","ø","Û","»","ù","Ü","@","¼","ú","Ý","½","û","Þ","€","¾","ü","ß","¿","ý","à","‚","À","þ","á","ƒ","Á","ÿ","å","„","Â","æ","…","Ã","ç","†","Ä","è","‡","Å","é","ˆ","Æ","ê","‰","Ç","ë","Š","È","ì","‹","É","í","Œ","Ê","î","Ë","ï","Ž","Ì","ð","Í","ñ","Î","ò","‘","Ï","ó","’","Ð","ô","“","Ñ","õ","”","Ò","ö","•","Ó","ø","–","Ô","ù","—","Õ","ú","˜","Ö","û","™","×","ý","š","Ø","þ","›","Ù","ÿ","œ","Ú"];
                                	  var codes = ["&ograve;","&delta;","&sigma;","&Sigma;","&rho;","&omega;","&Omega;","&Psi;","&Omicron;","&xi","&Xi;","&nu","&mu;","&Lambda;","&kappa","&iota;","&theta;","&Theta;","&eta;","&zeta;","&pi;","&#9787;","&#9785;","&spades;","&clubs;","&diams;","&hearts;","&copy;","&#219;","&reg;","&#158;","&#220;","&#159;","&#221;","&#222;","&#37;","&#161;","&#223;","&#162;","&#224;","&#163;","&#225;","&Agrave;","&#164;","&#226;","&Aacute;","&#165;","&#227;","&Acirc;","&#166;","&#228;","&Atilde;","&#167;","&#229;","&Auml;","&#168;","&#230;","&Aring;","&#169;","&#231;","&AElig;","&#170;","&#232;","&Ccedil;","&#171;","&#233;","&Egrave;","&#172;","&#234;","&Eacute;","&#173;","&#235;","&Ecirc;","&#174;","&#236;","&Euml;","&#175;","&#237;","&Igrave;","&#176;","&#238;","&Iacute;","&#177;","&#239;","&Icirc;","&#178;","&#240;","&Iuml;","&#179;","&#241;","&ETH;","&#180;","&#242;","&Ntilde;","&#181;","&#243;","&Otilde;","&#182;","&#244;","&Ouml;","&#183;","&#245;","&Oslash;","&#184;","&#246;","&Ugrave;","&#185;","&#247;","&Uacute;","&#186;","&#248;","&Ucirc;","&#187;","&#249;","&Uuml;","&#64;","&#188;","&#250;","&Yacute;","&#189;","&#251;","&THORN;","&#128;","&#190;","&#252","&szlig;","&#191;","&#253;","&agrave;","&#130;","&#192;","&#254;","&aacute;","&#131;","&#193;","&#255;","&aring;","&#132;","&#194;","&aelig;","&#133;","&#195;","&ccedil;","&#134;","&#196;","&egrave;","&#135;","&#197;","&eacute;","&#136;","&#198;","&ecirc;","&#137;","&#199;","&euml;","&#138;","&#200;","&igrave;","&#139;","&#201;","&iacute;","&#140;","&#202;","&icirc;","&#203;","&iuml;","&#142;","&#204;","&eth;","&#205;","&ntilde;","&#206;","&ograve;","&#145;","&#207;","&oacute;","&#146;","&#208;","&ocirc;","&#147;","&#209;","&otilde;","&#148;","&#210;","&ouml;","&#149;","&#211;","&oslash;","&#150;","&#212;","&ugrave;","&#151;","&#213;","&uacute;","&#152;","&#214;","&ucirc;","&#153;","&#215;","&yacute;","&#154;","&#216;","&thorn;","&#155;","&#217;","&yuml;","&#156;","&#218;"];
                                	  for(x=0; x<chars.length; x++){
                                	  text=text.replace(new RegExp(chars[x], 'g'),codes[x]);
                                	  }
                                	  return text;
                               	}
                               
                              
                                </script>
