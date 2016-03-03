<%@taglib uri="/struts-tags" prefix="s"%>
<s:form action="unsubscribe!doneUnsubscribe" method="post">
<input type="hidden" name="cid" value="${cid}" />
<input type="hidden" name="pid" value="${pid}" />


<div style="padding-top:20px" class="row">
<div class="col-md-12 col-md-offset-1">
<div class="col-md-2"></div>
<div class="col-md-7">${temptext}</div>
<div class="col-md-2"></div>
</div>
</div>

<br>
<div class="row">
<div class="col-md-12 col-md-offset-1">
<div class="col-md-3"></div>
<div class="col-md-6">
<div class="col-md-1">Email</div><div class="col-md-8"><input type="text" name="decodetoken" class="form-control" value="${decodetoken}" size="50" READONLY="READONLY"/></div></div>
<div class="col-md-3"></div>
</div>
</div>
<br>
<div class="row">
<div class="col-md-12 col-md-offset-1 form-group">
<div class="col-md-2"></div>
<div class="col-md-5"><center><button type="submit" class="btn btn-primary" id="submitbtn">Unsubscribe</button>
<div class="col-md-2"></div>
</center></div>
</div>
</s:form>

