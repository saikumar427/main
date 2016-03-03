<%@taglib uri="/struts-tags" prefix="s"%>
	<div class="col-md-12">
		 <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><s:text name="global.event.url.section.header"/></h3>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                    <div class="col-md-10"><s:text name="global.event.url.msg"/>
                                     <br/> <br/> 
                                    
                                    <s:hidden id="customname" value="%{eventData.userurl}"></s:hidden>
	<s:hidden id="mgrid" value="%{eventData.mgrId}"></s:hidden>
	<s:hidden id="powertype" value="%{powertype}"></s:hidden>
                                    
                                   <%--  <s:textfield name="eventData.eventURL" cssClass="form-control"  onclick="this.select()"></s:textfield>  --%>   
                                  <span id="customurlval"> <s:text name="eventData.eventURL"></s:text></span>&nbsp;
	<s:if test="%{subMgr==null || submgr_permissions['IntegrationLinks']=='yes'}">
	<a href="javascript:;" id="customurl"><s:text name="global.edit.lnk.lbl"/></a>
                                                              </s:if>
                                    </div>
                                    </div>
                                </div>
                                <div class="panel-footer">
                                    <a id="previewlink"	href="/event?eid=${eid}" target="_blank" class="btn btn-primary"><s:text name="global.preview.lnk.lbl"/></a> &nbsp;
                                     <%-- <s:if test="%{subMgr==null || submgr_permissions['IntegrationLinks']=='yes'}"><input
										type="button" id="customurlbtn" name="customurlbutton"	value="Set Custom URL"  class="btn btn-primary"/></s:if> --%>
                                </div>
                            </div>
	</div>
	
<script>
var eid='${eid}';

$('#customurlbtn').click(function(){
	setCustomUrl();
});

$('#customurl').click(function(){
	 openSinglePopUp($(this).offset(),success,cancel);
});



function setCustomUrl(){
	var url='IntegrationLinks!customURL?eid=${eid}';
			    	      $('#myModal').on('show.bs.modal', function () {	
		   	       			   $('#myModal .modal-title').html('<h3><span style="padding-left:10px">Custom URL</span></h3>');	
			                   $('iframe#popup').attr("src",url);
			                 });		
			                      $('#myModal').modal('show');  
							        $('#myModal').on('hide.bs.modal', function () { 
							        $('iframe#popup').attr("src",'');			        
							        $('#myModal .modal-body').html('<iframe id="popup" src="" width="100%" height="10" onload="javascript:resizeIframe(this)" frameborder="0"></iframe>');
							    });						    
							
							
	}


function openSinglePopUp(position,okFunction,cancelFunction){
	$('#singleerror').html('');
	$('#onefieldtext').val($('#customname').val());
     $('#singleValuePopUp').css({ top: position.top-20, left:  position.left-20}).fadeIn(); 
	 $('#singleValuePopUp').find("[key='OK']").off();
	  $('#singleValuePopUp').find("[key='CANCEL']").off();
    if(!okFunction)
        okFunction=function(){};
    if(!cancelFunction)
        cancelFunction=function(){};
    $('#singleValuePopUp').find("[key='OK']").click(function(){okFunction($('#singleValuePopUp').find("[key='VALUE']").val());});
    $('#singleValuePopUp').find("[key='CANCEL']").click(function(){ $('#singleValuePopUp').hide();cancelFunction();});
    
}    
    var success=function(value){
    	$('#singleerror').html('');
    	  var _powertype=$('#powertype').val();
        var userurl=$('#customname').val();
        var userid=$('#mgrid').val();
        var url="Snapshot!setCustomURL?eid="+eid+"&mgrId="+userid+"&userurl="+userurl+"&newurl="+value+"&powertype="+_powertype;
        $.ajax({
  		  url : url,
  		  type: 'POST',
  		  success: function(transport) {		  	
  			var result=transport;
  			$('#singleerror').show();
  			if(result.indexOf("URL Exists")>-1)
  				$('#singleerror').html('<font color="red">'+props.global_this_url_not_avail_errmsg+'</font>');
  			else if(result.indexOf("Invalid")>-1)	
  				$('#singleerror').html('<font color="red">'+props.global_enter_valid_text_msg+'</font>');		
  			else if(result.indexOf("spacesInUrl")>-1)
  				$('#singleerror').html('<font color="red">'+props.global_use_alphanumeric_chars_msg+'</font>');
  			else
  				$('#singleValuePopUp').hide();

if(result.indexOf('newurl')>-1){
var tempObj=JSON.parse(result);
$('#customname').val(tempObj.newurl);
$('#customurlval').html(tempObj.url);
}
	  	      }
  	});	
    };
      var cancel=function(){
    };


</script>	