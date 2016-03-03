<%@taglib uri="/struts-tags" prefix="s"%>
<!doctype html>
<html>
<body>
	<s:form name="priorityfields" action="PriorityRegistration!savePriorityFields" id="priorityfields" cssClass="form-horizontal">
	<s:hidden name="eid"></s:hidden>
	<div class="well">
		
			<div class="row">
<div class="col-md-3 col-sm-3 control-label"><s:text name="pr.fields.req.for.authentication.lbl"/></div>
<div class="col-md-3 col-md-3">
<select name="fieldcount" id="fieldlevel" class="form-control" onchange="changeLabel();">
<option value="1">One</option><option value="2">Two</option></select>
</div>

<div class="col-md-3 col-sm-3">
								<span class="pull-right">
									<button type="button" class="btn btn-primary" id="savepriorityfields" data-loading-text="Saving...">
									<s:text name="global.save.btn.lbl"/>
									</button>
									<button type="button" class="btn btn-active" id="closepriority">
										<i class="fa fa-times"></i>
									</button>
								</span>
							</div>

</div> 


<div class="row">
<div class="col-md-3 col-sm-3 control-label"><s:text name="pr.field.labels.lbl"/> <s:property value="%{firstlbl}"/>   </div>
<div class="col-md-5 col-md-5">
<div class="row"> 
<div id="firstlabel" class="col-md-4 col-sm-4 extramargin"><s:text name="pr.first.label.lbl"/></div>
<div class="col-md-4 col-sm-4"><s:textfield  id="first" name="firstlbl" size="10" onkeyup="changefirstLabel()" cssClass="form-control" placeholder="%{getText('pr.userid.ph')}"></s:textfield></div>
</div>
</div>
</div>

<div class="row">
<div class="col-md-3 col-sm-3 control-label"></div>
<div class="col-md-5 col-md-5">
  <div class="row" id="secondlabel" style="display:none;"> 
<div  class="col-md-4 col-sm-4 extramargin"><s:text name="pr.second.label.lbl"/></div>
<div class="col-md-4 col-sm-4"><s:textfield cssClass="form-control" id="second" type="text" name="secondlbl" size="10" onkeyup="changesecondLabel()" placeholder="<s:text name='pr.pwd.ph'/>"></s:textfield></div>
</div>
</div>
</div>
</div>
</s:form>
			<script>
				var nooffields=${fieldcount};
				if(nooffields==2){
					document.getElementById('fieldlevel').value=nooffields;
					changeLabel();
				}
			</script>
			
			</body></html>