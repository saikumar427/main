<%@taglib uri="/struts-tags" prefix="s"%>
<style>
#listfiles > tbody > tr > td > input{
	margin:0px 0px !important;
	border-radius:0px !important;
	border-style: solid solid none !important;
    border-width: 1px 1px medium !important;
}
#listfiles > tbody > tr:last-child td > input{
  border-bottom: 1px solid #cccccc !important;
}
</style>
<s:form name="prilistrec" action="PriorityRegistration!savePriorityListRecords" id="prilistrec" cssClass="form-horizontal">
	<s:hidden name="eid" ></s:hidden>
	<s:hidden name="listId" id="listid"></s:hidden>
<div class="addRows">
	<div class="row">
		<div class="col-md-12" style="padding:0px !important;" align="center" >
			<div class="well well-no-margin">
			<div id="priorityerrors" style="display:none;"class="alert alert-danger"></div>
				<table id="listfiles">
					<tr><td><input type="text" id="readonlyfield1" value="" readonly placeholder="<s:text name="pr.code.plc.lbl"/>" class="form-control" onkeyup="nospaces(this)"></td>
						<s:if test="%{noOfFields==2}">
							<td><input type="text" value="" id="readonlyfield2" readonly placeholder="<s:text name="pr.pass.code.plc.lbl"/>" class="form-control" style="border-left:none !important;display: block" onkeyup="nospaces(this)"></td>
						</s:if>
					</tr>
					<tr><td><input type="text" value="" class="firstfield form-control"onkeyup="nospaces(this)"></td>
						<s:if test="%{noOfFields==2}">
							<td><input type="text" value=""  class="secondfield form-control" style="border-left:none !important;display: block " onkeyup="nospaces(this)"></td>
						</s:if>
						<td><a style="cursor: pointer" class="removelink">&nbsp;<s:text name="global.delete.lnk.lbl"/></a></td>
					</tr>
					<tr>
						<td><input type="text" value="" class="firstfield form-control" onkeyup="nospaces(this)"></td>
							<s:if test="%{noOfFields==2}">
								<td><input type="text" value=""  class="secondfield form-control" style="border-left:none !important;display: block" onkeyup="nospaces(this)"></td>
							</s:if>
						<td><a style="cursor: pointer" class="removelink">&nbsp;<s:text name="global.delete.lnk.lbl"/></a></td>
					</tr>
					<tr><td><input type="text" value="" class="firstfield form-control" onkeyup="nospaces(this)"></td>
						<s:if test="%{noOfFields==2}">
							<td><input type="text" value="" class="secondfield form-control" style="border-left:none !important;display: block" onkeyup="nospaces(this)"></td>
						</s:if>
						<td><a style="cursor: pointer" class="removelink">&nbsp;<s:text name="global.delete.lnk.lbl"/></a></td>
					</tr>
				</table>
				<div class="row sm-font bottom-top-gap" style="margin-bottom:8px;margin-top:5px;">
						<table>
							<tbody>
								<tr>
									<s:if test="%{noOfFields==2}">
										<td>
											<a style=" left: 100px;position: relative;" class="addrow" href="javascript:;"><s:text name="qn.addrow.lnk.lbl"/></a>
										</td>
									</s:if>
									<s:else>
										<td>
											<a style="left: 26px;position: relative;" class="addrow" href="javascript:;"><s:text name="qn.addrow.lnk.lbl"/></a>
										</td>
									</s:else>
								</tr>
							</tbody>
						</table>
				</div>
				<div class="center">
						<button type="button" class="btn btn-primary" id="addrowsdata" data-loading-text="Saving..."><s:text name="global.add.btn.lbl"/></button>
						<button type="button" class="btn btn-cancel" id="closepriority"><i class="fa fa-times"></i></button>
				</div>
			</div>
		</div>
	</div>
</div>
</s:form>
<script>
var jsondata=${jsonData};
var priListRec=jsondata.data;
var fieldLen='${noOfFields}';
if(priListRec.length>0){
	$('#listfiles').html('');
	var filed1=jsondata.field1;
	var filed2=jsondata.field2;
	var html='<tr><td><input type="text" id="readonlyfield1" value="'+filed1+'" readonly="true" class="form-control" onkeyup="nospaces(this)"></td>';
	if(Number(fieldLen)==2)	
	html+='<td><input type="text" value="'+filed2+'" id="readonlyfield2" readonly="true"  class="form-control" style="border-left:none !important;display: block" onkeyup="nospaces(this)"></td>';
	html+='</tr>';
	$('#listfiles').append(html);
	for(var i=0;i<priListRec.length;i++)
	{
		var htmldata='<tr><td><input type="text"  value="'+priListRec[i].userid+'" autofocus="autofocus" class="firstfield form-control" onkeyup="nospaces(this)"></td>';
		if(Number(fieldLen)==2)
			htmldata+='<td><input type="text"  value="'+priListRec[i].password+'" class="secondfield form-control" onkeyup="nospaces(this)"></td>';
		htmldata+='<td><a style="cursor: pointer" class="removelink">&nbsp;'+props.global_delete_lnk_lbl+'</a></td></tr>';//Delete
		$('#listfiles').append(htmldata);
		$('#addrowsdata').html(props.global_save_btn);
	}
}
function nospaces(t){
	if(t.value.match(/\s/g)){
	t.value=t.value.replace(/\s/g,'');
	}
	}
$(document).ready(function () {
	var count = $('.removelink').length;
	if(count == 1)
		$('.removelink').css('visibility','hidden');
	$(document).on('click','.addrow',function(e){
		if(e.handled !== true)
		{console.log(e.handled+"::myhandle");
			var htmldata='<tr><td><input type="text" value=""  autofocus="autofocus" class="firstfield form-control" onkeyup="nospaces(this)"></td>';
			if(fieldLen==2)
				htmldata+='<td><input type="text" value=""  class="secondfield form-control" style="border-left:none !important;display: block" onkeyup="nospaces(this)"></td>';
				htmldata+='<td><a style="cursor: pointer" class="removelink">&nbsp;'+props.global_delete_lnk_lbl+'</a></td></tr>';
				$('#listfiles').append(htmldata);
	 	e.handled = true;
	 	$('.removelink').show();
	 	$('.removelink').css('visibility','visible');
		}
		
    });
});



</script>