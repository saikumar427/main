<%@taglib uri="/struts-tags" prefix="s"%>
<style>

.FB_ImgLogoContainer{position:relative;width:50px;height:45px;display:block;float:left;}
 img.FB_ImgLogo{height:15px;width:15px;bottom:0;right:0;position:absolute;margin:0;}

</style>
<link rel="stylesheet" type="text/css"  href="../css/webintegration.css" />
<script>
var eid='${eid}';
var appId='${facebookConnectData.fbAppId}';
</script>
 <div id="fb-root"></div>
 <script type="text/javascript" language="JavaScript" src="../js/fbconnect.js">
 </script>
 <div class="box" align="left">
<div class="boxheader">Publish To Facebook&nbsp;</div>
<div class="boxcontent">
<table  border="0" cellpadding="0" cellspacing="0" width="100%" >
<s:set name="isPublished" value="facebookConnectData.alreadyPublished"></s:set>

<s:if test="%{isPublished}">
<tr>
<td colspan="2">
<span class="smallestfont">Published: <a href="http://www.facebook.com/event.php?eid=<s:text name='facebookConnectData.fbeventid'></s:text>" target="_blank">http://www.facebook.com/event.php?eid=<s:text name='facebookConnectData.fbeventid'></s:text></a></span>
</td></tr>
</s:if>
<s:else>
<tr><td>
<span class="smallestfont" id="publishedtofb">If you are using this feature for the first time, you will see grant permission window after Facebook login. Please grant permission and click this link again to publish event in your Facebook account.</span>
</td>
</tr>
</s:else>
</table>
</div>
<div class="boxfooter">
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
<td>
<SPAN style='font-weight: bold;color: #0052A4'></SPAN>
<s:if test="%{isPublished}">
</s:if>
<s:else>
<input type="button" id="fbbtn1" value="Publish To Facebook" />  
</s:else>
</td>
</tr>
</table>
</div>
</div>
<div id="popupdialog">
	<div id="hd"></div>
	<div id="bd"></div>
</div>
<script>
 var fbbtn = new YAHOO.widget.Button("fbbtn1", { onclick: { fn: publish }}); 
</script>
