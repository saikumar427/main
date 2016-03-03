<%@taglib uri="/struts-tags" prefix="s"%>
    <link rel="stylesheet" type="text/css"	href="../assets/bootstrap/css/bootstrap.css" />

<div class="col-xs-12 col-sm-12">
	<div class="row">
		<div class="col-md-12">
			<h4>Remove Sub Manager</h4>
		</div>
	</div>
	<hr>
	<s:form name="submanagerremoveform" id="submanagerremoveform" action="SubManager!removeSubManager" method="post">
	<s:hidden name="eid"></s:hidden>
	<s:hidden name="subMgrId"></s:hidden>
	<div class="row">
	<div class="col-md-12">
		Sub Manager will be removed from the list for ever. This operation cannot be revert back.
		</div>
	</div>
	<hr>
	<div class="row">
	<div class="pull-right">
		<input type="button" value="Ok" class="btn btn-primary" onclick="removeSubManagerForm();">&nbsp;<input type="button" value="Cancel" onclick="parent.closepopup();" class="btn btn-primary">
	</div>
	</div>
	</s:form>		
</div>
