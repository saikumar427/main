<%@taglib uri="/struts-tags" prefix="s" %>

<!-- <link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap.css" /> -->
<%-- <style>
.importblock{
	background-color: #FFFFFF;
    background-image: none;
    border: 1px solid grey;
    border-radius: 4px;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
    color: #555555;
    display: block;
    font-size: 14px;
    height: 200px;
    line-height: 1.42857;
    padding: 6px 12px;
    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
    vertical-align: middle;
    width: 520px;
    overflow:auto;
}
</style> --%>
<div id="showID"></div>
<form name="importURLfrm" action="TrackURL!insertImportTrackURLs" id="importURLfrm" method="post">
<s:hidden name="eid"></s:hidden>
<s:set name="size1"  value="globalTrackUrlList.size()" ></s:set>
<div class="col-sm-12 col-xs-12 xol-md-12 well" style="margin-top: 10px;">
<div id="errormsg"  class="alert alert-danger" style="display:none"></div>

	<div class="col-sm-12 col-xs-12 xol-md-12"><div id="error"></div></div>
	<div class="col-sm-12 col-xs-12 xol-md-12"><div id="errormsg" ></div></div>
	
	<div class="col-sm-12 col-xs-12">
		<s:if test="%{#size1==0}">
		<div class="col-sm-11" style="margin-top: 7px;"><s:text name="turl.no.global.track.partners.msg"/></div>
		<div class="col-sm-1"><button type="button" class="btn btn-active " id="cancelImport"><i class="fa fa-times"></i></button></div>
		</s:if>
		<s:else>
		
		<div class="col-sm-12 col-xs-12 xol-md-12 row" style="margin-bottom: 10px;">
		<div class="col-sm-12 "><s:text name="turl.import.track.partners.lbl"/></div>
		<div class="col-sm-12"><a  href="javascript:;" id="selectall"><s:text name="global.select.all.lbl"/></a> &nbsp; <a  href="javascript:;" id="clearall"><s:text name="global.clear.all.lbl"/></a></div>
		
	</div>
		
		<s:iterator value="%{globalTrackUrlList}" var="trackpartner" >
		<div class="col-sm-6 col-xs-12"><s:checkbox name="globalUrls" id="globalurls" fieldValue="%{trackId}" value="true" cssClass="checktrack"/>&nbsp;${trackName}</div>
		</s:iterator>
		
		<div class="col-sm-12 col-md-12 col-xs-12 center">
			<input type="button" class="btn btn-primary" value="<s:text name="global.add.btn.lbl"/>" id="submitbtn" />
			<button type="button" class="btn btn-cancel " id="cancelImport"><i class="fa fa-times"></i></button>
		</div>
		</s:else>
	</div>
</div>

<%-- <div class="col-xs-12 col-msm-12">
	<div class="row">
		<div class="col-xs-12">
			<div id="error"></div>
			<div id="errormsg" ></div>
		</div>
	</div>	
	<s:if test="%{#size1>0}">
	<div class="col-sm-12">
		<div class="pull-right">
			<a  href="javascript:;" id="selectall">Select All</a> &nbsp; <a  href="javascript:;" id="clearall">Clear All</a>
		</div>
	</div>
	</s:if>
	<br>
	<div class="row">
		<div class="col-xs-12">
						<div class="importblock">
						<br/>
					
						<s:if test="%{#size1==0}">
						Currently you have no global track partners or you have added all of your global track partners to this event.
						</s:if>
						<s:else>
						<s:iterator value="%{globalTrackUrlList}" var="trackpartner" >
						<s:checkbox name="globalUrls" id="globalurls" fieldValue="%{trackId}" value="true" theme="simple"  cssClass="checktrack"/>&nbsp;${trackName}<br/><br/>
						</s:iterator>
						</s:else>
						</div>			
		</div>
	</div>
	<s:if test="%{#size1!=0}">
	<br>
	<div class="col-sm-12">
		<div class="pull-right">
			<input type="button" value="Submit" class="btn btn-primary" id="submitbtn">&nbsp;<input type="button" value="Cancel" class="btn btn-active" onclick="parent.closepopup();">
		</div>
	</div>
	
	</s:if>
</div> --%><!-- col-md-12 final div -->
</form>
<%-- <script src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
<script src="/main/bootstrap/js/icheck.min.js"></script>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/grey.css" /> --%>
<script>
$('#cancelImport').click(function(){
	closeWell();
});
$(document).ready(function(){
	$('#createTrack').html('');
	$('#importTracking').prop("disabled",true);
});
$('#selectall').click(function(event) {	
    $('.checktrack').iCheck('check');
});

 $('#clearall').click(function(event) { 
 	 $('.checktrack').iCheck('uncheck');      
});
 
 $(function(){
	 $('input.checktrack').iCheck({  
	 checkboxClass: 'icheckbox_square-grey', 
	 radioClass: 'iradio_square-grey'});
 });
 $('#submitbtn').click(function(){
	 submitimporttrackurl();
 });
	function submitimporttrackurl(){	
		showProcessing('forload');
		
	 var checkCount = $("input:checkbox:checked").length;
		if(checkCount>0){
			$.ajax({
	    		url: 'TrackURL!insertImportTrackURLs',
	    		type: 'GET',
	    		data: $("#importURLfrm").serialize(),
	    		 success: function( t ) { 
	    			 parent.getReloadedTrackURLData();
	    			 //parent.closepopup();
	    			 hideProcessing();
	    			 closeWell();
	    			//parent.window.location.reload(true);					
	    		 },
	    		 error: function(){
	    			 alert("in failure"); $('#errormsg').html(props.global_failed_get_results_errmsg);
	    			 hideProcessing();
	    		 }
	    	});
		}else{
			document.getElementById("errormsg").innerHTML=props.turl_import_one_track_partner_errmsg;
			document.getElementById("errormsg").style.display="block";
			 hideProcessing();
		}
		 
	}
</script>