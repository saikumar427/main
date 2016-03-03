<%@taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" type="text/css" href="../css/backgroundDisablePopup.css" />
<script type="text/javascript">
var previewFileName="";
function getContentPreview(){	
	
	$('htmlncss').action='eventslist!preview';
	$('htmlncss').request({
         onSuccess: function(t) {
	        var previewFileName=t.responseText;		
	        var url='../membertasks/FileRead?readFileName='+''+previewFileName+'';
	       showYUIinIframe("Preview",url,600,500);
	       
	          }
    });
	$('htmlncss').action='eventslist!save';
}
</script>
<div id="htmlncssStatusMsg"></div>
<div id="break" style="display: none;"><br/></div>
<div id="backgroundPopup" ></div>
<s:form theme="simple" action="eventslist!save" id="htmlncss" name="htmlncss">
<input type="hidden" name="themeid" value='<%=request.getParameter("themeid")%>' ></input>
<jsp:include page="/help/myevents_messages.jsp"></jsp:include>
<div class="taskcontent"><script>setHelpContent("mythemes_htmlncss_helpcontent")</script></div>
<div style="height:8px;"></div>
<div class="taskbox">
<span class="taskheader">HTML</span><br/>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr>
<td><s:textarea rows="20" cols="105" name="userThemes.HTMLContent"></s:textarea></td>
</tr>
</table>
</div>
<br/>
<div class="taskbox">
<span class="taskheader">CSS</span><br/>
<table  border="0" cellpadding="3" cellspacing="0" width="100%" >
<tr>
<td><s:textarea rows="20" cols="105" name="userThemes.CSScontent"></s:textarea></td>
</tr>
</table>
</div>
<p/>
<table width="100%" align="center">
<tr><td align="center"><input type="submit" value="Submit" id="submitBtn">&nbsp;&nbsp;
<input type="button" name="preview" value="Preview" id="previewBtn" />

</td></tr>
</table>
</s:form>
<div id="popupdialog"><div id="hd"></div><div id="bd"></div></div>
<div id="iframepopupdialog"><div id="hd"></div><div id="bd"></div></div>
<script>
var btn = new YAHOO.widget.Button("submitBtn", { onclick: {  fn: submitHtmlnCss} });
function submitHtmlnCss(){
	$('htmlncss').request({
  		onComplete: function(o){
  			$('htmlncssStatusMsg').update(o.responseText);
  			$('break').show();
	  		$('htmlncssStatusMsg').scrollTo();	  		
  		}
	});
}
var btn1 = new YAHOO.widget.Button("previewBtn", { onclick: { fn: getContentPreview } });
function getPreview(){
	javascript:getContentPreview();
}
</script>