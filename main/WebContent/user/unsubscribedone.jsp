<%@taglib uri="/struts-tags" prefix="s"%>
<div class="row">
<div class="col-md-12">
<div style="float:right;padding-top:40px;padding-right:350px;">${message}</div>
<s:if test="%{message=='Unsubscribe failed'}">
<div class="form-group">
<div class="col-sm-6">
<button class="btn btn-default"  style="background-color: #D1D0CE;" id="cancel">Back</button>
</div></div>
</s:if>
</div></div>

