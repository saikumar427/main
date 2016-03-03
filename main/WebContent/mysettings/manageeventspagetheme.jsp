<%@taglib uri="/struts-tags" prefix="s"%>
    <style>
    .panel-footer{
    background: url(/main/images/buttons_bg.png) repeat-x;
    }
    
    </style> 
    <div class="row" style="margin-bottom:10px; margin-left:10px;">
    	<div style="float:left"><a href="home"> <i class="glyphicon glyphicon-chevron-left"></i><s:text name="cept.back.to.settings.lbl"></s:text></a></div>
    </div>
	<div class="row">
  		<div class="col-md-12">
   			<div class="panel panel-primary">
			<div class="panel-heading"><h3 class="panel-title"><s:text name="cept.title.header.lbl"></s:text>&nbsp;<s:text name="cept.title.header.and.lbl"></s:text>&nbsp;<s:text name="cept.description.header.lbl"></s:text>&nbsp; <i class="fa fa-info-circle"  style="cursor:pointer" id="boxofficeinfo11"></i></h3></div>
				<div class="panel-body">
   	 				<s:form theme="simple" id="EventsPageCustomize" name="EventsPageCustomize" action="CustomizeEventsPageTheme!customizeSettings" method="post">
	   					<s:hidden name="mgrId" id="managerid"/>
	   					<div id="titledesc" class="alert alert-danger" style="display:none"></div>
	   					<div class="form-group">
  							<label class="col-md-2 col-sm-2 col-xs-2"><s:text name="cept.title.header.lbl"></s:text>&nbsp;*</label>
    						<div class="col-md-10 col-sm-10 col-xs-10">
      							<s:textfield cssClass="form-control" id="boxofftitle" name="boxoffice.title" placeholder="Box Office Title" />
    						</div>
    					</div>
    					<br/><br/>
    					<div class="form-group">
    						<label class="col-md-2 col-sm-2 col-xs-2"><s:text name="cept.description.header.lbl"></s:text></label>
    						<div class="col-md-10 col-sm-10 col-xs-10">
      							<s:textarea cssClass="form-control" id="boxoffdesc" name="boxoffice.description" />
    						</div>
  	 					 </div>
        			</s:form>
    			</div>
    			<div class="panel-footer">
    				<div class="row">
     					<div class="col-md-1">
	 							<input type="button" value="<s:text name="global.update.link.lbl"></s:text>" class="btn btn-primary" id="updateCustom"/>
	 					</div>
	 					<div class="col-md-1" >
     								<div id="updatedCustomMsg" style="padding:7px; margin-bottom: -15px;"></div>
     					</div>
     				</div>
				</div>                               
   			</div> <!-- panel primary -->
   
   <div class="panel panel-primary">
		<div class="panel-heading"><h3 class="panel-title"><s:text name="cept.photo.th.lbl"></s:text> <i class="fa fa-info-circle"  style="cursor:pointer" id="boxofficephotomsg"></i>       </h3></div>
		<div class="panel-body">
   	 		<s:form theme="simple" id="EventsPagePhoto" name="EventsPagePhoto" action="CustomizeEventsPageTheme!updatePreferences" method="post">
	  			<s:hidden name="userThemes.photoname" id="photoname" theme="simple"></s:hidden>
	  			<s:hidden name="userThemes.uploadurl" id="uploadurl" theme="simple"></s:hidden>
	  			<s:hidden name="userThemes.photo" id="Image" theme="simple"></s:hidden>
				<s:if test="%{userThemes.photo != null && userThemes.photo != ''}">
						<img src='<s:text name="userThemes.photo"></s:text>' style="max-width: 100px;  max-height:100px" id="uploadedimgurl" />
				</s:if>    	
     		</s:form>
    	</div>
    	<div class="panel-footer">
        	<input class="btn btn-primary" type="button" id="photobtn" value="<s:text name="cept.add.change.photo.btn.lbl"></s:text>"/>
        	<s:if test="%{userThemes.photo != null && userThemes.photo != ''}"> &nbsp;
        	<input class="btn btn-primary" type="button" id="deletephotobtn" value="Delete Photo"/>
        	</s:if>
		</div>                               
   </div>
   
   <div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"><s:text name="cept.events.th.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="boxofficeeventsmsg"></i></h3>
			
		</div>
		<div class="panel-body">
			<div id="boxoffevents" class="alert alert-danger" style="display:none"></div>
   	 		<s:form theme="simple" id="EventsPageContent" name="EventsPageContent" action="CustomizeEventsPageTheme!updatePreferences" method="post">
	   		<div class="row">
	   			<div class="form-group">
  					<label class="col-md-3" style="width: 18%;"><s:text name="cept.upcoming.events.lbl"></s:text></label>
    				<div class="col-md-9">
      					<div class="col-md-2">
      						<input type="radio"  name="userThemes.upcomingEvents" class="paypal upcomingevts" value="Yes" <s:if test="%{userThemes.upcomingEvents == 'Yes' || userThemes.upcomingEvents == '' }">checked='checked'</s:if>>&nbsp;<s:text name="cept.upcoming.events.show.all.rb.lbl"></s:text>
      					</div>
      					<div class="col-md-3">
				      		<input type="radio"  name="userThemes.upcomingEvents" class="paypal upcomingevts" value="Selected" <s:if test="%{userThemes.upcomingEvents=='Selected'}">checked='checked'</s:if>>&nbsp;<s:text name="cept.upcoming.events.show.selected.rb.lbl"></s:text>
    					</div>   
    				</div>
       			</div>
       		</div>
       
       	<div id="did"  style="<s:if test="%{userThemes.upcomingEvents=='Selected'}">display:block</s:if><s:else>display:none</s:else>">
       		<div class="row">
       			<div class="form-group">
        			<div class="col-md-1" style="width:6.333%;"></div>
  						<label class="col-md-4"><s:text name="cept.all.events.lbl"></s:text></label>
    				<div class="col-md-1" style="width: 6.333%;"></div>
    					<label class="col-md-4"><s:text name="cept.selected.events"></s:text></label>
    				<div class="col-md-1"></div>
       			</div>
       		</div>
       		
  
     		<div class="row">
       			<div class="form-group">
      	 			<div class="col-md-1" style="width:2.333%;"></div>
    				 <div class="col-md-4 col-sm-4">
    	   				<s:select name="allevents" list="allEvents" listKey="key" listValue="value" multiple="true" size="10" cssClass="form-control"/>
    	 			</div>
    	
    	 			<div class="col-md-1" style="width: 4.333%; padding-left: 4px;"><br/><br/><br/>
    	   				<i style="cursor:pointer" id="moveright" title="Move Right" class="fa fa-arrow-right moves-arrow"  onclick="moveOptions(document.EventsPageContent.allevents, document.EventsPageContent.selevents);"> </i>
    	   				<i style="cursor:pointer" id="moveleft"  title="Move Left"  class="fa fa-arrow-left moves-arrow"  onclick="moveOptions(document.EventsPageContent.selevents, document.EventsPageContent.allevents);" > </i>
    				</div>
    	
    				<div class="col-md-4 col-sm-4">
    					<s:select name="selevents" id="selevents" list="selEvents" listKey="key" listValue="value" multiple="true" size="10" cssClass="form-control" />
					</div>
    				<div class="col-md-1" style="padding-left: 0px;"><br/><br/><br/><br/>
    	 				<i style="cursor:pointer" class="fa fa-arrow-up moves-arrow" title="Move Up" onclick="javascript:ManageQuestion_mvUpOption(document.getElementById('selevents'))"  ></i><br>
    	 				<i style="cursor:pointer" class="fa fa-arrow-down moves-arrow " title="Move Down" onclick="ManageQuestion_mvDnOption(document.getElementById('selevents'))" ></i>
    				</div>   	
     			</div>
   			</div>   
       </div>
       <br/>
       
       
       <div class="row">
       		<div class="form-group">
  				<label class="col-md-4 col-sm-4 col-xs-4" style="width:18%"><s:text name="cept.past.events.lbl"></s:text></label>
    			<div class="col-md-9">
      				<div class="col-md-2 col-sm-2 col-xs-2">
      					<input type="radio" name="userThemes.pastEvents" value="Yes" class="paypal pastevts" <s:if test="%{userThemes.pastEvents == 'Yes'}">checked='checked'</s:if>>&nbsp;<s:text name="cept.past.events.show.rb.lbl"></s:text>
      				</div>
      				<div class="col-md-2 col-sm-2 col-xs-2">
      					<input type="radio" name="userThemes.pastEvents" value="No" class="paypal pastevts" <s:if test="%{userThemes.pastEvents == 'No'}">checked='checked'</s:if>>&nbsp;<s:text name="cept.past.events.hide.rb.lbl"></s:text>
    				</div>
       			</div>
       		</div>
       	</div>
	 </s:form>
    </div> <!-- body close -->
   
    <div class="panel-footer">
    	<div class="row">
     		<div class="col-md-1">
	 			<input class="btn btn-primary" type="button" id="eventsupdatebtn" value="<s:text name="global.update.link.lbl"></s:text>"/>
	 		</div>
	 		<div class="col-md-1" >
     			<div id="updatedCustomevtMsg" style="padding:7px; margin-bottom: -15px;"></div>
     		</div>
     	</div>   
	</div>     <!-- footer close  -->                         
   </div> <!-- panel close -->
   

   
   <div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"><s:text name="cept.look.feel.th.lbl"></s:text>&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="boxofficelookfeel"></i></h3>
		</div>
		<div class="panel-body">
   	 		<s:form theme="simple" id="EventsPageThemeContent" name="EventsPageThemeContent" action="CustomizeEventsPageTheme!save" method="post">
	  			<s:hidden name="userThemes.selectedTheme"></s:hidden>
	  			<s:hidden name="userThemes.myeventpagethemename"></s:hidden>
	  			<s:hidden name="attribMap['HTML']"></s:hidden>
	  			<s:hidden name="attribMap['CSS']"></s:hidden>
	  			<s:set name="themetyperadio" value="userThemes.themetype"></s:set>
	  			<div class="alert alert-danger" id="looknfeelstats" style="display:none;width: 820px;margin-left: 18px;"></div>
				
				<div class="form-group">
					<div class="col-md-12">
						<div class="col-md-3 col-xs-4 col-sm-4">
							<label class="radio-inline">
								<input class="paypal" type="radio" name='themetype' value='DEFAULT' id="evethm" <s:if test="%{#themetyperadio == 'DEFAULT'}">checked='checked'</s:if> > 
									<s:text name="cept.eventbee.theme.rb.lbl"></s:text>
							</label>
						</div>
						<div class="col-md-3 col-sm-4 col-xs-4">
							<s:text name="userThemes.eventbeeThemes"></s:text>
						</div>
						<div class="col-md-3 col-sm-4 col-xs-4">
							<div style="padding-top:4px">[ <a href="javascript:;" onclick="edithtmlcss('DEFAULT');submitform('evethm');"><s:text name="global.save.btn.lbl"></s:text></a>&nbsp;&nbsp;<a href="javascript:eventbeethemePreview('evethm')" ><s:text name="global.preview.lnk.lbl"></s:text> </a>]
							</div>
						</div>
					</div>
					<br/><br/>
					<div class="col-md-12">
						<div class="col-md-3 col-sm-3 col-xs-3">
							<label class="radio-inline">
								<input class="paypal" type="radio" name='themetype' value='PERSONAL' id="custthm" <s:if test="%{#themetyperadio == 'PERSONAL'}">checked='checked'</s:if> > 
									<s:text name="cept.custom.html.css.rb.lbl"></s:text>
							</label>
						</div>
						<div class="col-md-5 col-sm-5 col-xs-5">
							[ <a href="javascript:;" onClick="edithtmlcss('PERSONAL');customizeEventsPage('custthm');"> <s:text name="global.edit.lnk.lbl"></s:text>  </a> &nbsp;&nbsp;<a href="javascript:customthemePreview('custthm')" ><s:text name="global.preview.lnk.lbl"></s:text></a> ]
						</div>
						<div class="col-md-4 col-sm-4 col-xs-4"></div>
					</div>
				</div>
     		</s:form>
    	</div>
   </div>
   
   
   <s:if test="%{isfbinstalled==true}">
     <div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"><s:text name="cept.facebook.header.title"></s:text></h3>
		</div>
			<div class="panel-body">
   	 			<s:form theme="simple" id="fbsettings" name="FacebookSettings" >
	   			<s:hidden name="mgrId" id="mgrid"/>
	   			
	   			<div id="fberrors" class="alert alert-danger" style="display:none;width: 822px;padding-left: 40px;margin-left: 16px;"></div>
	  			
	  			<div class="form-group">
  					<label class="col-md-2 col-sm-3 col-xs-3"><s:text name="cept.title.header.lbl"></s:text>&nbsp;*</label>
    				<div class="col-md-10 col-sm-9 col-xs-9">
      					<s:textfield cssClass="form-control" id="fbtitile" name="boxoffice.fbtitle"  />
    				</div>
    			</div>
    			<br/><br/>
    			<div class="form-group">
    				<label class="col-md-2 col-sm-3 col-xs-3"><s:text name="cept.description.header.lbl"></s:text></label>
    				<div class="col-md-10 col-sm-9 col-xs-9">
      					<s:textarea cssClass="form-control" id="boxoffdesc" name="boxoffice.fbdescription" />
    				</div>
  	  			</div>
  	  			<br/><br/>
  	  			<br/><br/>
       			
       			<div class="form-group">
  					<label class="col-md-4" style="width:18%"><s:text name="cept.pagination.lbl"></s:text></label>
    				<div class="col-md-9">      		
      					<div class="col-md-2 ">
      						<input type="radio"   name="boxoffice.fbpagination" value="yes" class="paypal fbpastevts" id="Yes" <s:if test="%{boxoffice.fbpagination=='yes' || boxoffice.fbpagination=='' || boxoffice.fbpagination=='Yes' }">checked='checked'</s:if>>&nbsp;<s:text name="cept.pagination.yes.rb.lbl"></s:text> 
      					</div> 
      					<div class="col-md-5 col-sm-5 col-xs-5">
      						<input type="radio"  name="boxoffice.fbpagination" value="no" class="paypal fbpastevts" id="No" <s:if test="%{boxoffice.fbpagination == 'no'}">checked='checked'</s:if>>&nbsp;<s:text name="cept.pagination.no.rb.lbl"></s:text>
      					</div>
    				</div> 
       			</div>   
       			<div style="clear: both;"></div>     
       			<div class="form-group" id="pagediv" style=" <s:if test="%{boxoffice.fbpagination=='yes' || boxoffice.fbpagination=='' || boxoffice.fbpagination=='Yes'}">display: block</s:if> <s:else>display: none</s:else> ">  		
    				
    				<div class="col-md-2 col-md-offset-2 col-sm-2 col-sm-offset-2 col-xs-4">
      					<s:textfield cssClass="form-control" id="fbpagesize" name="boxoffice.fbpaginationsize"  />  
    				</div>
    				
    				<div id="pagesize" class="col-md-4 col-sm-4 col-xs-4" style="margin-top: 5px;"><s:text name="cept.pagination.per.page"></s:text></div>
    				<div id="fbpageerror" style="color:Red; display:none;"></div>
    				<div class="col-md-4 col-sm-4 col-xs-4"></div>
    			</div> 
    			   
       				<br/><br/>
     				<div style="clear: both;"></div>
  	  					<div class="form-group"><br/>
  							<label class="col-md-2 col-sm-4 col-xs-4"><s:text name="cept.buy.button.lbl"></s:text>&nbsp;*</label>
    						<div class="col-md-10 col-sm-8 col-xs-8">
      							<s:textfield cssClass="form-control" id="fbbuybtn" name="boxoffice.fbbuybutton"  />
    						</div>
        				</div>
  	  				<br/><br/>
  	  				<div class="form-group">
  						<label class="col-md-2 col-sm-4 col-xs-4"><s:text name="cept.message.events.lbl"></s:text> &nbsp;*</label>
    					<div class="col-md-10 col-sm-8 col-xs-8">
      						<s:textfield cssClass="form-control" id="fbnoevts" name="boxoffice.fbnoevtsmsg"  />
    					</div>
    				</div>
    				<br/><br/>
    			</s:form>
  			</div>
    	
			<div class="panel-footer">
    			<div class="row">
     				<div class="col-md-1">
	 					<input type="button" value="Update" class="btn btn-primary" id="fbsave"/>
	 				</div>
	 				<div class="col-md-1" >
     					<div id="fbstatus" style="padding:7px; margin-bottom: -15px;"></div>
     				</div>
    		 	</div>
			</div>		
  	 	</div><!-- panel ends -->
  	 </s:if>
  	 
  	 
  </div>
</div>


   

<script>
$( document ).ready(function() {
	$('#boxofficeinfo11').attr('title',props.cept_title_desc_msg_lbl);
	$('#boxofficephotomsg').attr('title',props.cept_upload_photo_msg_lbl);
    $('#boxofficeeventsmsg').attr('title',props.cept_events_help_msg_lbl);
    $('#boxofficelookfeel').attr('title',props.cept_look_feel_msg_lbl);
});


/* $('#moveright').click(function(){	
	moveOptions(document.EventsPageCustomize.allevents, document.EventsPageCustomize.selevents); 
});
  
$('#moveleft').click(function(){
	moveOptions(document.EventsPageCustomize.selevents, document.EventsPageCustomize.allevents);
}); */


/* function fbpagination(val){
	
	alert(val);
	if(val=='yes')
		document.getElementById('pagediv').style.display='block';
	else
		document.getElementById('pagediv').style.display='none';
} */



$('#fbsave').click(function(){   
	$('#fbpageerror').hide();
	/* var size=$('#fbpagesize').val();
	if(isNaN(size)){
		$('#fbpageerror').show();
		$('#fbpageerror').html(props.cpet_fb_error_msg);		return;
	} */
	$.ajax( {
	   	   type: "GET",
	   	   url: 'CustomizeEventsPageTheme!fbSave',
	   	   data: $("#fbsettings").serialize(),   	   
	   		success: function(data) {      	
			        if(data.indexOf("errorMessage")>-1)
			        {
			        	document.getElementById('fberrors').style.display='block';
			        	document.getElementById('fberrors').innerHTML=data;
			        }  
			        else
			        {			        	
			        	document.getElementById('fbstatus').innerHTML=props.global_update_msg;
				    }   
	   		
	   	   }});
});

$('.fbpastevts').on('ifChecked', function(event){
	var id = $(this).attr('id');
    	if(id=='Yes')
		document.getElementById('pagediv').style.display='block';
	else
		document.getElementById('pagediv').style.display='none';
    });

$("select[name='theme']").addClass("form-control" );
$(function(){
	$('input.paypal').iCheck({  
	checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey'});
	});

$('input.upcomingevts').on('ifChecked', function(event){
if($(this).val()=='Selected'){
	document.getElementById('did').style.display='block';
}else {
	document.getElementById('did').style.display='none';
} 
});

$('input.pastevts').on('ifChecked', function(event){
	});
	
$('#updateCustom').click(function(){
    var title = $('#boxofftitle').val();
	if (title == "") {
		$("#updatedCustomMsg").html('');
		$('#titledesc').show();
		$('#titledesc').html(props.cpet_box_office_title_msg);
		hidePopup();
		return false;
	}
	$('#titledesc').hide();
	$.ajax( {
	   	   type: "GET",
	   	   url: 'CustomizeEventsPageTheme!customizeSettings',
	   	   data: $("#EventsPageCustomize").serialize(),
	   	   success: function( result ) {
	   		if(!isValidActionMessage(result)) return;
	   		if(result.indexOf("don't askcard")>-1){
	   			hidePopup();
	             $("#updatedCustomMsg").html(props.global_update_msg);
	             
	        }else{
	              var mgrid=document.getElementById('managerid').value;
	              _purpose='BoxOffice Settings';
	              _powertype='';
	               getcc(mgrid,'boxoffice settings');   
	             }
	   	   }});
});

$('#photobtn').click(function(){
		url = '../membertasks/ImageUpload';
		$('.modal-title').html(props.global_img_upload_hdr);
		$('#myModal').on('show.bs.modal', function () {
		$('iframe#popup').attr("src",url);
		$('iframe#popup').css("height","220px"); 
		});
		$('#myModal').modal('show');
});


$('#deletephotobtn').click(function(){
	bootbox.confirm(props.cpet_del_pic_msg, function (result){
		if(result)
		{
	var url = "CustomizeEventsPageTheme!deletePhoto";
    $.ajax( {
	   	   type: "GET",
	   	   url: url,
	   	   success: function( result ) {
	   		/* window.location.href="/main/mysettings/CustomizeEventsPageTheme"; */
	   		
	   		
	   		
	   		$('#uploadedimgurl').hide();
	   		
	   		$('#deletephotobtn').hide();
	   		
	   	   }});
		}
	});
});

function closeFileUploadWindow(){
	$('#myModal').modal('hide');
}

function addOption(theSel, theText, theValue)
{
  var newOpt = new Option(theText, theValue);
  var selLength = theSel.length;
  theSel.options[selLength] = newOpt;
}

function deleteOption(theSel, theIndex)
{ 
  var selLength = theSel.length;
  if(selLength>0)
  {
    theSel.options[theIndex] = null;
  }
}

function moveOptions(theSelFrom, theSelTo)
{
  var selLength = theSelFrom.length;
  var selectedText = new Array();
  var selectedValues = new Array();
  var selectedCount = 0;
  var i;
  // Find the selected Options in reverse order
  // and delete them from the 'from' Select.
  for(i=selLength-1; i>=0; i--)
  {
    if(theSelFrom.options[i].selected)
    {
      if(theSelFrom.options[i].text=="Attendee Name"){
    	  return;
      }
      selectedText[selectedCount] = theSelFrom.options[i].text;
      selectedValues[selectedCount] = theSelFrom.options[i].value;
      deleteOption(theSelFrom, i);
      selectedCount++;
    }
  }
  
  // Add the selected text/values in reverse order.
  // This will add the Options to the 'to' Select
  // in the same order as they were in the 'from' Select.
  for(i=selectedCount-1; i>=0; i--)
  {
    addOption(theSelTo, selectedText[i], selectedValues[i]);
  }
  
  //if(NS4) history.go(0);
}


$('#eventsupdatebtn').click(function(){
	var l = document.getElementById('selevents');
	var upCEvent=document.getElementsByName("userThemes.upcomingEvents");
	for (var i = 0; i < upCEvent.length; i++) {
		if(upCEvent[i].value=='Selected' && upCEvent[i].checked){
			if (l.length==0) {
				$('#boxoffevents').show();
				$('#boxoffevents').html(props.cpet_box_office_events_error_msg);
				/* $('#boxoffevents').delay(2000).fadeOut('slow'); */
				return false;
			}		
		}
	}
	addselEventstoForm();
	$.ajax({
		method:'POST',
		url:'CustomizeEventsPageTheme!updatePreferences',
		data:$('#EventsPageContent').serialize(),
		success: function( result ) {
			if(!isValidActionMessage(result)) return;
			 if(result.indexOf("success")>-1){
				 $('#updatedCustomevtMsg').show();
		        	$("#updatedCustomevtMsg").html(props.global_update_msg);
		        	/* $('#updatedCustomevtMsg').delay(2000).fadeOut('slow'); */
		        } 
				}
					});
});
//init();

function init(){
	var x=document.getElementsByName("userThemes.upcomingEvents");
	
	for (var i=0;i<=x.length;i++){
		if(x[i].checked && x[i].value=='Selected'){
			document.getElementById('did').style.display='block';
		}else{
			document.getElementById('did').style.display='none';
			}
	}
}

function showdiv(obj){
	//alert(obj);
	/* if(obj.value=='Selected'){
		document.getElementById('did').style.display='block';
	}else {
		document.getElementById('did').style.display='none';
	} */
}
function addselEventstoForm() {
	var selectBox = document.getElementById("selevents");
	for (var i = 0; i < selectBox.options.length; i++) {
		var obj1 = createHiddenTextElement(i,"value",selectBox.options[i].text);			
		var obj2 = createHiddenTextElement(i,"key",selectBox.options[i].value);
		$('#EventsPageContent').append(obj1);
		$('#EventsPageContent').append(obj2);
	}
}
function createHiddenTextElement(index,etype,val){
	var pname=document.createElement("input");
	pname.type="hidden";
	pname.name="selEvents["+index+"]."+etype;	
	pname.value=val;
	return pname;
}


function ManageQuestion_mvUpOption(elem) {

	selindex = elem.selectedIndex;
	if (selindex <= 0) {
		return;
	}
	temp = elem.options[selindex].text;
	val = elem.options[selindex].value;
	elem.options[selindex].text = elem.options[selindex - 1].text;
	elem.options[selindex].value = elem.options[selindex - 1].value;
	
	elem.options[selindex - 1].text = temp;
	elem.options[selindex - 1].value = val;
	elem.selectedIndex = selindex - 1;
}

function ManageQuestion_mvDnOption(elem) {

	selindex = elem.selectedIndex;
	if (selindex == elem.length - 1) {
		return;
	}
	temp = elem.options[selindex].text;
	val = elem.options[selindex].value;
	elem.options[selindex].text = elem.options[selindex + 1].text;
	elem.options[selindex].value = elem.options[selindex + 1].value;
	
	elem.options[selindex + 1].text = temp;
	elem.options[selindex + 1].value = val;
	elem.selectedIndex = selindex + 1;
}

function edithtmlcss(type){
	var themetype="<s:text name='userThemes.themetype'></s:text>";
	if(type==undefined){
		type=themetype;
	}	
}

function submitform(id){
if(document.getElementById(id).checked){
	$.ajax({
		method:'POST',
		url:'CustomizeEventsPageTheme!save',
		data:$('#EventsPageThemeContent').serialize(),
		success: function( result ) {
			if(!isValidActionMessage(result)) return;
			 if(result.indexOf("success")>-1){
				 window.location.reload( true );	     
		        } 
				}
					});
}else {
	$('#looknfeelstats').show();
	$('#looknfeelstats').html(props.cpet_select_theme.lbl);
	$('#looknfeelstats').delay(2000).fadeOut('slow');
	    	//alert('');
	    } 
}


var previewFileName="";

function eventbeethemePreview(id)
{
	    if(document.getElementById(id).checked){   
	       var theme = document.forms['EventsPageThemeContent'].elements['theme'].value;
	       var url='${shortUrl}/events?preview=show&pretheme='+theme;
				$('.modal-title').html(props.globa_preview);
				$('#myModal').on('show.bs.modal', function () {
				$('iframe#popup').attr("src",url);
				$('iframe#popup').css("height","500px"); 
				});
				$('#myModal').modal('show');
	    }else {
	    	$('#looknfeelstats').show();
	    	$('#looknfeelstats').html(props.cpet_select_theme_lbl);
	    	$('#looknfeelstats').delay(2000).fadeOut('slow');
	    }   
}
function customthemePreview(id){
	    if(document.getElementById(id).checked){
	       var url='${shortUrl}/events';
	       $('.modal-title').html(props.globa_preview);
			$('#myModal').on('show.bs.modal', function () {
			$('iframe#popup').attr("src",url);
			$('iframe#popup').css("height","400px"); 
			});
			$('#myModal').modal('show');
	     }else {
	    	$('#looknfeelstats').show();
	    	$('#looknfeelstats').html(props.cpet_select_html_lbl);
	    	$('#looknfeelstats').delay(2000).fadeOut('slow');
	    }   
}

function customizeEventsPage(id) {
	if(document.getElementById(id).checked){
		var url = 'CustomizeEventsPageTheme!edit?purpose=eventspage';
		$('.modal-title').html(props.cpet_html_css_popup);
		$('#myModal').on('show.bs.modal', function () {
		$('iframe#popup').attr("src",url);
		$('iframe#popup').css("height","450px"); 
		});
		$('#myModal').modal('show');
	}else {
		$('#looknfeelstats').show();
    	$('#looknfeelstats').html(props.cpet_select_html_lbl);
    	$('#looknfeelstats').delay(2000).fadeOut('slow');
	    }  
}

function showdiv(obj){
	if(obj.value=='Selected'){
		document.getElementById('did').style.display='block';
	}else {
		document.getElementById('did').style.display='none';
	}
}


function setWebPath(fullPath, uploadurl, photoname) {
	document.getElementById('Image').value = fullPath;
	if (document.getElementById("uploadurl"))
		if (document.getElementById("uploadurl")) {
			document.getElementById('uploadurl').value = uploadurl;
		}

	if (document.getElementById("photoname"))
		if (document.getElementById("photoname")) {
			document.getElementById('photoname').value = photoname.substring(0,
					photoname.lastIndexOf("."));
		}
	inserteventsPublicPagephotoURL();
}
function inserteventsPublicPagephotoURL() {
	var eventphotoURL = document.getElementById('Image').value;
	var uploadurl = document.getElementById('uploadurl').value;
	var photoname = document.getElementById('photoname').value;
	var url = "CustomizeEventsPageTheme!updateeventPublicPagePhotoURL?purpose=update";
	url+='&eventphotoURL='+eventphotoURL+'&uploadurl='+uploadurl+'&photoname='+photoname;
	
	submitContentFormAndReload(url);
}
function submitContentFormAndReload(url) {
	var dynatimestamp = new Date();
	url += '&dynatimestamp=' + dynatimestamp.getTime();
	$.ajax({
		method:'POST',
		url:url,
		success: function( result ) {
			if(!isValidActionMessage(result)) return;
			 if(result.indexOf("done")>-1){
				 parent.window.location.reload( true );	     
		        } 
				}
					});
}

$(document).ready(function(){
	$('#boxofficeinfo11').tooltipster({
		 
	    fixedWidth:'100px',
	    position: 'right'
	    });
	
	$('#boxofficephotomsg').tooltipster({
	    fixedWidth:'100px',
	    position: 'right'
	    });
	
	$('#boxofficeeventsmsg').tooltipster({
	    fixedWidth:'100px',
	    position: 'right'
	    });
	
	$('#boxofficelookfeel').tooltipster({
	    fixedWidth:'100px',
	    position: 'right'
	    });
});




</script>
