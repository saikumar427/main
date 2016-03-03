<%@taglib uri="/struts-tags" prefix="s" %>



	<s:form name="noticedataform" id="noticedataform"  theme="simple">
	<s:hidden name="eid" id="eid"></s:hidden>
	<s:hidden name="noticeData.noticeid" />
	<s:set name="type" value="noticeData.noticetype"/> 
	
							<div class="row">
	                        	<div class="alert alert-danger" id="noticedataformerrormsg" style="display: none" >   </div>
	                        </div>
	
		 <!-- <div id="noticedataformerrormsg" style="display:none;"> </div> -->
		 <%-- <div> <s:property value='%{noticeData}'/> </div> --%>
		 
		 <div class="well" >
			<div class="row">
				<div class="col-sm-6 col-md-6 col-xs-12">
					<s:textarea id="noticeid" cssClass="form-control" name="noticeData.notice" class="form-control"  rows="1" cols="50" value="%{noticeData.notice} " />
				</div>
				
				
				
				
				<div class="col-sm-4 col-md-4 col-xs-12 "  id="buttongroup" >
					 <div class="center btn-group btn-toggle" data-toggle="buttons">
					
						<label id="alertlbl" class="noticeboardlbl optiontype btn btn-default no-radius">                             
							<input type="radio" name="noticeData.noticetype" value="alert" class="noticeboard" id="alert"  <s:if test="%{type == 'alert'}">checked='checked'</s:if>  />&nbsp; Alert 
						</label>
						<label id="messagelbl" class="noticeboardlbl optiontype btn btn-active no-radius">
							<input type="radio" name="noticeData.noticetype" value="message" class="noticeboard" id="message"  <s:if test="%{type == 'message'}">checked='checked'</s:if> />&nbsp;Message
						</label>
						<label id="infolbl" class="noticeboardlbl optiontype btn btn-active no-radius">
							<input type="radio" name="noticeData.noticetype" value="info" class="noticeboard" id="info" <s:if test="%{type == 'info'}">checked='checked'</s:if> />&nbsp;Info
						</label>
					</div>
				</div>
				
				<div class="col-sm-2 col-md-2 col-xs-12" >
         			<%-- <span class="pull-right"> --%>
                		<button id="noticeadd" type="button" class="btn btn-primary" data-loading-text="Add"  onclick="handleAddNoteSubmit();">  <s:if test="%{noticeData.noticeid == '' ||  noticeData.noticeid == null}"> <b>Add</b> </s:if> <s:else> <b>Save</b></s:else>       </button>    
                		<button type="button" class="btn btn-cancel" id="cancel" type="button"><i class="fa fa-times"></i></button>
            		<%-- </span> --%>
         		</div>  <%-- <s:property value='%{noticeData.noticeid}'/> --%> 
		 	</div>
		 	</div>
		 </s:form>
		 
		 
		 
	<script>
	
	
	$('#noticeadd').click(function(){
		  $('#noticeadd').button('loading');
		  setTimeout(function() {
		       $('#noticeadd').button('reset');
		  }, 1000);
		});
	
	
	
	
	$(document).ready(function() {
		var selectedreport='${type}';
			
		if(selectedreport=='')
		document.getElementById("alert").checked = true;
		else
			activate(selectedreport);
	});
	
	
	$('input.noticeboard').change(function() {
		var selectedreport=$(this).attr('id');
		activate(selectedreport);		
	});
	
	
	function activate(selectedreport)
	{
		
		$(".noticeboardlbl").each(function() {
	        $(this).removeClass('active'); 
	        $(this).removeClass('btn-active'); 
	        $(this).removeClass('btn-default'); 
	    });
		
		if(selectedreport=='alert'){
			$('#alertlbl').addClass('btn-default');
	        $('#messagelbl').addClass('btn-active');
	        $('#infolbl').addClass('btn-active');
	        document.getElementById("alert").checked = true;
	        document.getElementById("message").checked = false;
	        document.getElementById("info").checked = false;
		}
		if(selectedreport=='message'){
			$('#alertlbl').addClass('btn-active');
	        $('#messagelbl').addClass('btn-default');
	        $('#infolbl').addClass('btn-active');
	        document.getElementById("alert").checked = false;
	        document.getElementById("message").checked = true;
	        document.getElementById("info").checked = false;
		}
		
		if(selectedreport=='info'){
			$('#alertlbl').addClass('btn-active');
	        $('#messagelbl').addClass('btn-active');
	        $('#infolbl').addClass('btn-default');
	        document.getElementById("alert").checked = false;
	        document.getElementById("message").checked = false;
	        document.getElementById("info").checked = true;
		}
	}
	
	
	
	
	
	</script>
