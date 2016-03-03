<%@taglib uri="/struts-tags" prefix="s"%>
<s:if test="%{paymentSettingsErrorsExists=='true'}">
<div>
<s:fielderror theme="simple" />
</div>
</s:if>
<s:if test="%{paymentSettingsErrorsExists=='false'}">
<div id="updatedMsg" class="errorMessage">
Payment Settings Updated.
</div>
</s:if>