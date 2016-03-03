<%@taglib uri="/struts-tags" prefix="s"%>
<s:hidden id="eventid" name="eid"/>
<!-- <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/tooltipster.css" /> -->
<div class="col-md-12">
	<div class="panel panel-primary">
                                <div class="panel-heading">
                                <h3 class="panel-title"><s:text name="wc.sections.header"/></h3>
                                    <%-- <h3 class="panel-title"> <s:if test="%{powertype=='RSVP'}">RSVP</s:if>  <s:else>Tickets</s:else> Widget Code</h3> --%>
                                </div>
                                <div class="panel-body" style="background-color: #F5F5F5;">
                                <div><s:text name="wc.copy.and.paste.code"/></div>
                                <br>
                                
                                    <s:if test="%{powertype=='RSVP'}">
                                    	<div class="row">
                                    		<div class="col-md-12 ">
                                    			<h4 class="section-header"><s:text name="global.rsvp.lbl"/></h4>
                                    		</div>
                                   		 </div>	
                                    </s:if>
										<s:else>
											<div class="row">
											<div class="col-md-12 ">
												 <h4 class="section-header"><s:text name="wc.tkts.widget.code.lbl"/></h4>
												</div> 
											</div>
										</s:else>	
										
										<div class="row">
										<div class="col-md-12">
										<textarea cols="80" rows="5" id="twidget" class="form-control" readonly="readonly" style="background-color: #FFFFFF" onClick="this.select()"><script type='text/javascript' language='JavaScript' src='<s:text name='eventData.serverAddress'></s:text>/home/js/widget/eventregistration.js'></script><iframe  id='_EbeeIFrame_ticketwidget_${eid}' name='_EbeeIFrame_ticketwidget_${eid}'  src='<s:text name='eventData.serverAddress'></s:text>/eregister?eid=${eid}&viewType=iframe;resizeIFrame=true&context=web'  height='0' width='700'></iframe>
	                                     </textarea>
	                                     </div>
										</div>
										<br>
										
										<h4 class="section-header"><s:text name="wc.who.is.attending.lbl"/></h4>
										
										<div class="row">
										<div class="col-md-12">
										<textarea cols="80" rows="5" readonly="readonly"  class="form-control" style="background-color: #FFFFFF"  onClick="this.select()"><script type='text/javascript' language='JavaScript' src='<s:text name='eventData.serverAddress'></s:text>/home/js/widget/eventregistration.js'></script>
                                        <iframe id='_EbeeIFrame_Attendees_${eid}' name='_EbeeIFrame_Attendees_${eid}' src="<s:text name='eventData.serverAddress'></s:text>/attendees?eid=${eid}&viewType=iframe;resizeIFrame=true&context=web" width="275" height="0" scrolling="no"></iframe >
	                                    </textarea>
	                                    </div>
										</div>
										<br>
										
										<h4 class="section-header"><s:text name="wc.fb.commenting.lbl"/></h4>
										
										<div class="row">
										<div class="col-md-12">
										<textarea cols="80" rows="5"  class="form-control" readonly="readonly" style="background-color: #FFFFFF"  onClick="this.select()"><iframe  id='_EbeeIFrame_fbcomments_${eid}' name='_EbeeIFrame_fbcomments_${eid}'  src='<s:text name='eventData.serverAddress'></s:text>/fbcomments?eid=${eid}&viewType=iframe;resizeIFrame=true&context=web'  frameborder='0' height='500' width='625px' scrolling='auto'></iframe>
	   									</textarea>
	   									</div>
										</div>
										<br>
										
										<h4 class="section-header"><s:text name="wc.fb.rsvp.list.lbl"/></h4>
										
										<div class="panel panel-primary">
                                
                                <div class="panel-body">
                                     <form  action="IntegrationLinks!saveFbeid" name="rsvpForm" id="rsvpForm"  >
	                                 <s:hidden name="eid"></s:hidden>
	                                 <div class="row">
	                                 	<div class="col-md-3"><s:text name="wc.fb.event.id.lbl"/>&nbsp; <i class="fa fa-info-circle"  style="cursor:pointer" id="fbeidinfo"  title="<s:text name="wc.fb.event.id.msg"/>"></i></div>
	                                 	 <div class="col-md-4"><s:textfield name="fbeid" id="fbeventid" cssClass="form-control"></s:textfield></div>
	                                 	 <div class="col-md-5">	                                 	 
	                                 	<font color="red"><div id="fbeventidmessage" style="display:none"></div></font><font color="red" ><div id="fbeventidvalidationmessage"  style="display:none"></div></font>
	                                 	</div>
	                                 </div>
	                                 <div class="row">
	                                 	<div class="col-md-6">
	                                 	<input type="checkbox"  id="gendercount" name="gendercount" value="gendercount" class="gender">&nbsp;<s:text name="wc.fb.rsvp.male.female.count.lbl"/>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="genderinfo" title="<s:text name="wc.fb.rsvp.male.female.count.msg"/>"></i>
	                                 	</div>
	                                 </div>	     
	                                 <br>                            
	                                 <div class="row">
		                                 <div class="col-md-6">
		                                 <input type="button" id="getcode" value="Get Code" class="btn btn-primary" onclick="getCode();">
		                                 </div>
	                                 </div>	                                
	                                 <div class="row">
	                                 	<div class="col-md-12">
	                                 	<div id="textmsg"></div>
	                                 	</div>	                                 	
	                                 </div>	
	                                 <div class="row">
	                                 	<div class="col-md-12">
	                                 	<textarea cols="80" rows="5" class="form-control" id="fbtext" readonly="readonly" style="background-color: #FFFFFF;display:none"  onClick="this.select()"><iframe  id='_EbeeIFrame_fbAttendees_${fbeid}' name='_EbeeIFrame_fbAttendees_${fbeid}'  src='<s:text name='eventData.serverAddress'></s:text>/fbattendees?fbeid=${fbeid}&showgender=yes&viewType=iframe;resizeIFrame=true&context=web'  frameborder='0' height='295' width='290px' scrolling='no'></iframe>
										 </textarea>
	                                 	</div>
	                                 </div>
	                                 <div class="row">
	                                 	<div class="col-md-12">
	                                 	<div id="textmsg"></div>
	                                 	</div>	                                 	
	                                 </div>	
	                                 <div class="row">
	                                 	<div class="col-md-12">
	                                 	<textarea cols="80" rows="5" class="form-control" id="fbtext1" readonly="readonly" style="background-color: #FFFFFF;display:none"  onClick="this.select()"><iframe  id='_EbeeIFrame_fbAttendees_${fbeid}' name='_EbeeIFrame_fbAttendees_${fbeid}'  src='<s:text name='eventData.serverAddress'></s:text>/fbattendees?fbeid=${fbeid}&showgender=yes&viewType=iframe;resizeIFrame=true&context=web'  frameborder='0' height='295' width='290px' scrolling='no'></iframe>
										 </textarea>
	                                 	</div>
	                                 </div>
	                               </form>                             
                                </div>                                
                            </div>
								
                                </div>                                
                            </div>
                           
</div><!-- col-sm-9 -->

<script>
jQuery(function(){
	  $('input[name="gendercount"]').iCheck({  
 		checkboxClass: 'icheckbox_square-grey', 
 		radioClass: 'iradio_square-grey'});
	 
	 
	  $('#fbeidinfo').tooltipster({
	        fixedWidth:'100px',
		    position: 'top'
		    });
	  
	  $('#genderinfo').tooltipster({	        
		    fixedWidth:'100px',
		    position: 'right'
		    });
});

function getCode(){   
	$('#textmsg').hide();
	$('#fbtext').hide();
    $('#fbtext1').hide();
	var a="";
	var fbwcode="";	
	var msg=props.wc_fb_event_id_help_msg;
	var msg1=props.wc_copy_and_paste_wc_msg;
    var msg2=props.wc_pls_enter_valid_fb_event_id_errmsg;
    var fbid=document.getElementById("fbeventid").value; 
	var inpVal = Number(document.getElementById('fbeventid').value);
	var eid=document.getElementById("eventid").value;
	    if(isNaN(inpVal)) {
			  try{
		            var err = new Error(msg2);
		            if (!err.message) {
		                err.message = msg2;
		            }
		            throw err;
		        }
		       catch (e) {
			       
		    	 $("#fbeventidvalidationmessage").show();
		         $("#fbeventidvalidationmessage").html(e.message);
		         $('#fbeventidmessage').hide(); 
		         $('#textmsg').hide();
		         $('#fbtext1').hide();
		     	 $('#fbtext').hide();
		         fbeventid.focus();
		         fbeventid.select();
		         return;
	 }
	    }
	
		$.ajax({
	        url: 'IntegrationLinks!saveFbeid',
	        type: 'post',
	        data: $("#rsvpForm").serialize(),
	        success: function (t) {      
	        	fbid=fbid.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
			    $('#fbeventid').value=fbid;
			    if(fbid!=''){	
				   		if ($('#gendercount').is(":checked")){
                        a="yes";
                        fbwcode="<iframe  id='_EbeeIFrame_fbAttendees_"+fbid+"' name='_EbeeIFrame_fbAttendees_"+fbid+"'  src='<s:text name='eventData.serverAddress'></s:text>/fbattendees?eid="+eid+"&fbeid="+fbid+"&showgender="+a+"&viewType=iframe;resizeIFrame=true&context=web'  frameborder='0' height='295' width='290px' scrolling='no'></iframe>";			    		
			    	    $('#textmsg').show();
			            $('#textmsg').html(msg1); 
			            document.getElementById("fbtext").value=fbwcode;
			    		$('#fbtext').show();
			    		$('#fbtext1').hide();
			    		$('#fbeventidmessage').hide();
			    		$("#fbeventidvalidationmessage").hide();
			    		}
			    	else{
			    		a="no";
			    		fbwcode="<iframe  id='_EbeeIFrame_fbAttendees_"+fbid+"' name='_EbeeIFrame_fbAttendees_"+fbid+"'  src='<s:text name='eventData.serverAddress'></s:text>/fbattendees?eid="+eid+"&fbeid="+fbid+"&showgender="+a+"&viewType=iframe;resizeIFrame=true&context=web'  frameborder='0' height='295' width='290px' scrolling='no'></iframe>";
			    	    $('#textmsg').show();
			    		$('#textmsg').html(msg1);
			    		document.getElementById("fbtext1").value=fbwcode; 
			    		$('#fbtext1').show();
			    	    $('#fbtext').hide();
			    	    $('#fbeventidmessage').hide();
			    	    $("#fbeventidvalidationmessage").hide();
			        }
			  }else {
			       	$('#fbeventidmessage').show();
			    	$('#fbeventidmessage').html(msg);
			    	$('#fbeventid').show();
			    	$("#fbeventidvalidationmessage").hide();
			    	$('#textmsg').hide();
			    	$('#fbtext1').hide();
			    	$('#fbtext').hide();
			    }
	        	
	        }       	
	        		  
	        });
 
}  
</script>