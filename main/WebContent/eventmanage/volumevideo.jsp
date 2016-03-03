<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<body>
<s:set name="vdo" value="videourl"/>
<div id="videoframe">
<iframe src='<s:property value="%{#vdo}"/>' id="ntsvideo" width="511px" style="padding-left: 0px" scrolling="no" height="399px" frameborder="no" webkitAllowFullScreen mozallowfullscreenallowFullScreen></iframe>
</div>
</body>
</html>


