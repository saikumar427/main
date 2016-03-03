<%@taglib uri="/struts-tags" prefix="s"%>

<div id="htmlncssStatusMsg" ></div>
<div id="break" style="display: none;"><br/></div>
<s:form theme="simple" action="HTMLnCSSCustomization!save" id="htmlncss" name="htmlncss">
<s:hidden name="eid"></s:hidden>
<s:hidden id="powertypeid" name="powertype"></s:hidden>
<s:hidden id="currlevelid" name="currentLevel"></s:hidden>
<s:hidden id="currfeeid" name="currentFee"></s:hidden>

<div style="height:8px;"></div>

	<table width="100%">
		<tr><td valign="top" align="right">Preview&nbsp;<a href="#"><img id="htmlPreviewImg" src="../images/arrow_cycle.png" onclick="getHtmlnCssContentPreview();" height="20px" width="20px" align="top"/></a>
			<!-- input type="button" name="preview" value="Preview" id="htmlPreviewBtn" /-->
			</td>
		</tr>
		
		<tr>
			<td colspan='2' align='center'>
			<div id="htmlcssprevid"></div>
			</td>
		</tr>
	</table>
	<br />
<div class="taskbox">
<span class="section-header">HTML</span><br/>
<div class="row"><div class="col-md-12">
<s:textarea cssClass="form-control" rows="20" cols="110" name="attribMap['HTML']"></s:textarea>
</div>
</div>
</div>
<br/>
<div class="taskbox">
<span class="section-header">CSS</span><br/>
<div class="row"><div class="col-md-12">
<s:textarea cssClass="form-control" rows="20" cols="110" name="attribMap['CSS']"></s:textarea>
</div></div>
</div>
<p/>


</s:form>

<div class="row" align="center">
<div style="height:30px"></div>
<input type="button" name="submit" value="Submit" id="htmlcssSubmitBtn" class="btn btn-primary" />

</div>

<script>

$('#htmlcssSubmitBtn').click(function(){
	checkProTicketing();

	/* $.ajax({
		type:'GET',
		data:$('#htmlncss').serialize(),
		url:'HTMLnCSSCustomization!save',
		success:function(result){
	if(!isValidActionMessage(result))
		return;
	window.location.reload();
}
		}); */
	
});

</script>