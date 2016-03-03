<%@taglib uri="/struts-tags" prefix="s"%>

<s:form action="emailcamp!unsubscribeMarketingEmail" method="post">
<input type="hidden" name="tempid" value="${tempid}" />
<div  style="padding-bottom:30px;">
<div style="padding-top:40px;float:right;padding-right:350px;padding-bottom:10px;">${temptext}</div>
<div class="row">
<div style="float:right;width:820px;">
<div class="input-group">
<div class="row">
<div class="col-sm-1">Email&nbsp;&nbsp;</div>
<div class="col-sm-9"><input type="text" name="decodeemail" class="form-control" value="${decodeemail}" size="50" READONLY="READONLY"/></div>
</div></div></div></div><br/>
<div class="form-group">
<div class="col-sm-6" style="float:right;padding-right:650px;">
<button type="submit" class="btn btn-primary" id="submitbtn">Unsubscribe</button>
</div></div></div>
</s:form>
