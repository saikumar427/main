<%@taglib uri="/struts-tags" prefix="s"%>
<!doctype html>
<html>
<head>
<script type="text/javascript"
	src="/main/datetimepicker/jquery.datetimepicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="/main/datetimepicker/jquery.datetimepicker.css" />
<style>


ul.errorMessage li {
	display: list-item;
}
</style>
</head>
	<div>
		<div class="row">
			<div class="col-xs-11">
				<div id="scheduleerrorMsg" class="alert alert-danger"
					style="display: none"></div>
			</div>
		</div>
		<form theme="simple" action="marketingmanage!saveSchedule"
			name="scheduleform" id="scheduleform" method="post">
			<s:hidden name="userId" />
			<s:hidden name="templateId" />
			<s:hidden name="schId" />
			<s:hidden name="tempName" />
			<s:hidden name="scheduleData.month" id="start_month_field" />
			<s:hidden name="scheduleData.day" id="start_day_field" />
			<s:hidden name="scheduleData.year" id="start_year_field" />
			<s:hidden name="scheduleData.hour" id="start_hour" />
			<s:hidden name="scheduleData.time" id="start_time" />
			<br />
			<div class="taskbox">
				<span class="section-main-header">Schedule</span><br />
				<div class="row">
					<div class="form-group">
						<div class="col-xs-1 col-sm-4" style="padding-top: 5px;">
							Name *
							<s:property value="%{tempName}" />
						</div>
						<div class="col-xs-10 col-sm-4">
							<input type="text" name="scheduleData.name" class="form-control"
								id="subject" value="<s:property value='%{scheduleData.name}'/>" />
						</div>
					</div>
				</div>
				<br />
				<div class="row">
					<div class="form-group">
						<div class="col-xs-3 col-sm-4" style="padding-top: 100px;">
							To *<br />
							<span class="smallestfont">Select mailing lists</span>
						</div>
						<div class="col-xs-9 col-sm-4">
							<div
								STYLE="height: 250px; width: 650px; font-size: 12px; overflow: auto; border-style: outset; padding-left: 10px;">
								<s:iterator value="%{allMailLists}" var="mailListData">
									<div class="row">
										<div class="col-xs-12">
											<s:checkbox name="selectedMailLists" fieldValue="%{listId}"
												value="%{selectedMailLists.contains(listId)}"
												cssClass="listcheck" />
											&nbsp;${listName}
											<s:set name="temp" value='listId' />
											<s:set name="aa" value='listMembersCount[#temp]' />
											<s:if test="%{#aa == ''}">
												<s:set name="aa" value="0" />
											</s:if>
											(Member Count:
											<s:property value='#aa' />
											)&nbsp;(List Id:
											<s:property value='#temp' />
											)
										</div>
									</div>
									<br />
								</s:iterator>
							</div>
						</div>
					</div>
				</div>
				<br />
				<div class="row">
					<div class="form-group">
						<div class="col-xs-2 col-sm-4" style="padding-top: 5px;">When</div>
						<s:set name="type" value="scheduleData.schtimeType"></s:set>
						<s:if test="%{#type != 'date'}">
							<div class="col-xs-9 col-sm-4">
								<div class="row">
									<div class="col-xs-12">
										<input type='radio' id='schType'
											name='scheduleData.schtimeType' class="listcheck" value='now'
											<s:if test="%{#type == 'now'}">checked="checked"</s:if> />
										Blast Now <span class="smallestfont">(Actual blast
											happens within an hour)</span>
									</div>
								</div>
							</div>
					</div>
				</div>
				</s:if>
				<br>
				<div class="row">
					<div class="form-group">
						<div class="col-xs-2 col-sm-4"></div>
						<div class="col-xs-9 col-sm-4">
							<div class="row">
								<div class="col-xs-4 col-sm-4">
									<input type='radio' id='schType'
										name='scheduleData.schtimeType' class="listcheck" value='date'
										<s:if test="%{#type == 'date'}">checked="checked"</s:if> />
									Blast At
								</div>
								<div class="col-xs-7 col-sm-7">
									<span id="startdate"> <input class="form-control"
										name="scheduleData.startDate" id="start" type="text"
										name="start"
										value="<s:property value='%{scheduleData.startDate}'/>" /><span
										class="smallestfont">(MM/DD/YYYY)</span>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group">
						<div class="col-xs-2 col-sm-4"></div>
						<div class="col-xs-9 col-sm-4">
							<div class="row">
								<div class="col-xs-12">
									<div>
										<input type='radio' id='schType'
											name='scheduleData.schtimeType' value='later'
											class="listcheck"
											<s:if test="%{#type == 'later'}">checked="checked"</s:if> />
										Schedule Later
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="row">
			<div class="form-group">
				<div class="col-xs-12"
					style="line-height: 54px; text-align: center;">
					<button type="button" class="btn btn-primary" id="submitBtn">Submit</button>
					<button type="button" class="btn btn-active" id="cancel">Cancel</button>
				</div>
			</div>
		</div>
	</div>
	</div>
		</div>
	<script>
var j=0;
$(function(){
	if($("input[name='scheduleData.schtimeType']:checked").val()=='date'){	
		var time=convertTime24to12($('#start_time').val());
		var dateString=$('#start_month_field').val()+"/"+$('#start_day_field').val()+"/"+$('#start_year_field').val()+" "+time;
		console.log(dateString);
		$("#start").val(dateString);
	}
	
	 $('input.listcheck').iCheck({  
         checkboxClass: 'icheckbox_square-grey', 
         radioClass: 'iradio_square-grey'});
	 
	 $('#startdate #start').datetimepicker({
	        //mask:true,
	        format:'m/d/Y g:i A',
	        formatTime:'g:i A',
	        onSelectTime:function(dp,$input){
	            //$('.nonrecurringtimes #end').datetimepicker('show');
	        }
	    });
	    
	
	$('#cancel').click(function(){
		window.location.href="../mymarketing/marketingmanage!templateInfo?templateId=${templateId}";
    });
	
	
	$('#submitBtn').click(function(){
		$('#scheduleerrorMsg').hide();	
		
		if($("input[name='scheduleData.schtimeType']:checked").val()=='date'){
			var startDate=$("#start").val();		
			if(startDate!='' && startDate!='undefined'){
				var array = startDate.split(startDate.charAt(startDate.length-3));
				var date = array[0].split('/');
				var time = array[1].split(':');
				var AMPM=array[2];
				$('#start_month_field').val(date[0]);
				$('#start_day_field').val(date[1]);
				$('#start_year_field').val(date[2]);
				$('#start_time').val(convertTime12to24(array[1]+" "+AMPM).split(":")[0]);
			}	
			else{
				alert('Please select date');
				return;
			}
		}
		
		
		$.ajax({
		  	   type: "POST",
		  	   url: 'marketingmanage!saveSchedule',
		  	   data: $("#scheduleform").serialize(),
		  	   success: function(response) {
		  		if(!isValidActionMessage(response)) return;
		  		if(response.indexOf('errorMessage')>-1){
		  			$('#scheduleerrorMsg').show();
		  			$('#scheduleerrorMsg').html(response);
			    }else{
			    	window.location.href="../mymarketing/marketingmanage!templateInfo?templateId=${templateId}";
			   } 
		  	   },failure:function(){
		  		  alert("fail");
		  	   }});
	});
});
</script>