<%@taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript">
function Reports(eid,trackcode,scode){
	var url="TrackURL!trackURLReport?eid="+eid+"&trackcode="+trackcode+"&scode="+scode;
	window.location.href=url;
}
</script>
<div>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
<td align="right"> <a href="TrackURL?eid=${eid}" ><s:text name="turl.back.to.all.turl.lnk.lbl"/></a> </td>			
</tr>
</table>
</div><br/>

<div class="box" align="left">
<div class="boxheader"><s:text name="turl.all.turl.lbl"/></div>
<div class="boxcontent">
<jsp:include page="trackurlssummaryreport.jsp"></jsp:include>
</div>
</div>