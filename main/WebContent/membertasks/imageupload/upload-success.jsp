<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
var fullPath='<s:property value="fullWebPath"/>';
var uploadurl='<s:property value="imgName" />';
var photoname='<s:property value="uploadFileName" />';
//alert("in full path::"+fullPath);
parent.setWebPath(fullPath,uploadurl,photoname);
//window.close();
</script>

