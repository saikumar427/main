<script type="text/javascript" src="../js/popup.js">
function popupdummy(){}
</script>

<%@taglib uri="/struts-tags" prefix="s"%>
<link type="text/css" rel="stylesheet" href="/main/bootstrap/css/bootstrap-colorpicker.min.css" />
<script src="/main/bootstrap/js/colorpicker.js"></script>
<script src="/main/js/newcolorpicker.js"></script>
<script type="text/javascript" src="../js/eventmanage/looknfeel.js"></script> 
<div id="lookFeelStatusMsg"></div>
<div id="break" style="display: none;"><br/></div>

<s:form theme="simple" id="sform" name="sform" action="EventCustomization!save" method="post">
	<s:hidden name="eid"></s:hidden>
	<div style="height:8px;"></div>
	<table width="100%">
		<tr><td valign="top" align="right">Preview&nbsp;<a href="#" ><img align="top" id="colorPreviewImg" src="../images/arrow_cycle.png" onclick="colorsTextPreview();" height="20px" width="20px"/></a>
			<!--input type="button" name="preview" value="Preview" id="colorPreviewBtn" /-->
			</td>
		</tr>
		<tr>
			<td colspan='2' align='center' valign="middle">
			<div id="colorprevid"></div>
			</td>
		</tr>
	</table>
	<br />
	
				<div class="overflowWrapper" style="margin-bottom:0px;">
					<div id="dataDiv" style="display:block;">
						<div id="borderRow" class="panel-body">

<div class="col-md-12 col-sm-12 questktLabel firstrow">
								<div class="col-md-3 col-sm-2 extramargin leftPadding">	
									<div>
									  <span class="section-header">Backgrounds</span>
									</div>
								</div>								
							</div>
				
				<div class="col-md-12 col-sm-12 questktLabel">
				<div style="height:20px"></div>
								<div class="col-md-3 col-sm-3 extramargin  leftPadding">	
									Page
								</div>
								<div class="col-md-3 col-sm-5 extramargin leftPadding">
								<s:set name="type" value="themeAttribs['BG_TYPE']"></s:set>
								
																	
									<!-- <input type="radio"  name="radioPage" id="radioPageColor" class="css-checkbox"/> -->
									
									<input class="paypal" type='radio' id="radioPageColor" data-head="Page"
				name="themeAttribs['BG_TYPE']" value="COLOR" data-type="Color"
				<s:if test="%{#type == 'COLOR'}">checked="checked"</s:if>>
									
									<label for="radioPageColor" class="css-label radGroup2" style="margin-right:15px;">Color</label>
									
									<div class="extramargin" id="ShowPageColor" style="<s:if test="%{#type=='COLOR'}">display:block</s:if><s:else>display:none</s:else>;width:50%;">
										<div class="input-group demo2">
											<s:textfield cssClass="form-control" name="themeAttribs['BG_COLOR']" id="bgcolor" data-image="BG_COLOR" data-radio="radioPageColor" onclick="checkcp('radioPageColor','BG_COLOR','bgcolor');"></s:textfield>
											<span class="input-group-addon">
												<i></i>
											</span>
										</div>
									</div>
								</div>
								<div class="col-md-3 col-sm-3 extramargin leftPadding">	
									<!-- <input type="radio" onclick="getDiv('Page','Image');" name="radioPage" id="radioPageImage" class="css-checkbox"/> -->
									
									<input class="paypal" type='radio' id="radioPageImage" data-head="Page" data-type="Image"
				name="themeAttribs['BG_TYPE']" value="IMAGE" 
				<s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>>
									
									<label for="radioPageImage" class="css-label radGroup2" style="margin-right:15px;">Image</label>
									<div class="extramargin" id="ShowPageImage" style="<s:if test="%{#type=='IMAGE'}">display:block</s:if><s:else>display:none</s:else>">
										<div class="input-group">
											<s:textfield id="BG_IMAGE" cssClass="form-control" name="themeAttribs['BG_IMAGE']" class="form-control" size="45" ></s:textfield>
											<span class="input-group-addon">
											<input id="pgimg" type="image" src="../images/image.gif" alt="" onclick='getImageURL("BG_IMAGE","radioPageImage");return false;' >
											</span>
										</div>
									</div>
								</div>								
							</div>	
							
							
							
							<div class="col-md-12 col-sm-12 questktLabel">
							<div style="height:20px"></div>
								<div class="col-md-3 col-sm-3 extramargin  leftPadding">	
								 <s:set name="type" value="themeAttribs['CONTAINERBG_TYPE']"></s:set>
									Container
								</div>
								<div class="col-md-3 col-sm-5  extramargin leftPadding">									
									<!-- <input type="radio" onclick="getDiv('Container','Color');" name="radioContainer" id="radioContainerColor" class="css-checkbox"/> -->
									
									<input type='radio' data-head="Container" data-type="Color" class="paypal" id="radioContainerColor" name="themeAttribs['CONTAINERBG_TYPE']" value='COLOR' <s:if test="%{#type == 'COLOR'}">checked="checked"</s:if>/>
									
									<label for="radioContainerColor" class="css-label radGroup2" style="margin-right:15px;">Color</label>
									<div class="extramargin" id="ShowContainerColor" style="<s:if test="%{#type=='COLOR'}">display:block</s:if><s:else>display:none</s:else>;width:50%;">
										<div class="input-group demo2">
											<!-- <input type="text" class="form-control" placeholder="Color"> -->
											 <s:textfield cssClass="form-control"  data-image="CONTAINERBG_COLOR" data-radio="radioContainerColor" name="themeAttribs['CONTAINERBG_COLOR']" size='8' id="containerbgcolor" onchange="checkcp('radioContainerColor','CONTAINERBG_COLOR','containerbgcolor');"></s:textfield>
											<span class="input-group-addon">
												<i></i>
											</span>
										</div>
									</div>
								</div>
								<div class="col-md-3 col-sm-3 extramargin  leftPadding">	
									<!-- <input type="radio" onclick="getDiv('Container','Image');" name="radioContainer" id="radioContainerImage" class="css-checkbox"/> -->
									<input type='radio'  data-head="Container" data-type="Image" class="paypal" id="radioContainerImage" name="themeAttribs['CONTAINERBG_TYPE']" value="IMAGE" <s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>>
									
									<label for="radioContainerImage" class="css-label radGroup2" style="margin-right:15px;">Image</label>
									<div class="extramargin" id="ShowContainerImage" style="<s:if test="%{#type=='IMAGE'}">display:block</s:if><s:else>display:none</s:else>">
										<div class="input-group ">
											<!-- <input type="text" class="form-control" placeholder="Image Path"> -->
											<s:textfield name="themeAttribs['CONTAINERBG_IMAGE']" size='45' id="CONTAINERBG_IMAGE" cssClass="form-control"></s:textfield> 
											<span class="input-group-addon">
												<input id="contimg" type="image" src="../images/image.gif" alt="" '  align="top" onclick='getImageURL("CONTAINERBG_IMAGE","radioContainerImage");return false;'/>
											</span>
										</div>
									</div>
								</div>								
							</div>
							
						<div class="col-md-12 col-sm-12 questktLabel">
						<div style="height:20px"></div>
								<div class="col-md-3 col-sm-3 extramargin  leftPadding">	
								<s:set name="type" value="themeAttribs['BOXBG_TYPE']"></s:set>
									Box
								</div>
								<div class="col-md-3 col-sm-5  extramargin leftPadding">	
									<!-- <input type="radio" onclick="getDiv('Box','Color');" name="radioBox" id="radioBoxColor" class="css-checkbox"/> -->
									<input class="paypal"  data-head="Box" data-type="Color" type='radio' id="radioBoxColor" name="themeAttribs['BOXBG_TYPE']" value='COLOR' <s:if test="%{#type == 'COLOR'}">checked="checked"</s:if>/>
									<label for="radioBoxColor" class="css-label radGroup2" style="margin-right:15px;">Color</label>
									<div class="extramargin" id="ShowBoxColor" style="<s:if test="%{#type=='COLOR'}">display:block</s:if><s:else>display:none</s:else>;width:50%;">
										<div class="input-group demo2">
											<!-- <input type="text" class="form-control" placeholder="Color"> -->
											 <s:textfield cssClass="form-control" data-image="BOXBG_COLOR" data-radio="radioBoxColor" name="themeAttribs['BOXBG_COLOR']" size='8' id="boxbgcolor" onchange="checkcp('radioBoxColor','BOXBG_COLOR','boxbgcolor');"></s:textfield>
											<span class="input-group-addon">
												<i></i>
											</span>
										</div>
									</div>
								</div>
								<div class="col-md-3 col-sm-3 extramargin  leftPadding">	
									<!-- <input type="radio" onclick="getDiv('Box','Image');" name="radioBox" id="radioBoxImage" class="css-checkbox"/> -->
									
									 <input type='radio' class="paypal" id="radioBoxImage"  data-head="Box" data-type="Image"
				name="themeAttribs['BOXBG_TYPE']" value="IMAGE" 
				<s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>>
									
									<label for="radioBoxImage" class="css-label radGroup2" style="margin-right:15px;">Image</label>
									<div class="extramargin" id="ShowBoxImage" style="<s:if test="%{#type=='IMAGE'}">display:block</s:if><s:else>display:none</s:else>">
										<div class="input-group">
											<!-- <input type="text" class="form-control" placeholder="Image Path"> -->
											
											<s:textfield name="themeAttribs['BOXBG_IMAGE']" size='45'
				id="BOXBG_IMAGE" cssClass="form-control"></s:textfield>
											<span class="input-group-addon">
												<!-- <input id="pgimg" type="image" align="top" style="height:20px;" src="image.gif"> -->
												<input id="bximg" type="image" src="../images/image.gif"  align="top" onclick='getImageURL("BOXBG_IMAGE","radioBoxImage");return false;'/>
											</span>
										</div>
									</div>
								</div>								
							</div>	
							
						<div class="col-md-12 col-sm-12 questktLabel">
						<div style="height:20px"></div>
								<div class="col-md-3 col-sm-3 extramargin  leftPadding">
								 <s:set name="type" value="themeAttribs['BOXHEADER_TYPE']"></s:set>
									Box Header
								</div>
								<div class="col-md-3 col-sm-5  extramargin leftPadding">	
									<!-- <input type="radio" onclick="getDiv('BoxHeader','Color');" name="radioBoxHeader" id="radioBoxHeaderColor" class="css-checkbox"/> -->
									
									<input class="paypal" type='radio' id="radioBoxHeaderColor" data-head="BoxHeader" data-type="Color"
				name="themeAttribs['BOXHEADER_TYPE']" value='COLOR' 
				<s:if test="%{#type == 'COLOR'}">checked="checked"</s:if>/>
									
									<label for="radioBoxHeaderColor" class="css-label radGroup2" style="margin-right:15px;">Color</label>
									<div class="extramargin" id="ShowBoxHeaderColor" style="<s:if test="%{#type=='COLOR'}">display:block</s:if><s:else>display:none</s:else>;width:50%;">
										<div class="input-group demo2">
											<!-- <input type="text" class="form-control" placeholder="Color"> -->
											
											 <s:textfield cssClass="form-control" data-image="BOXHEADER_COLOR" data-radio="radioBoxHeaderColor" name="themeAttribs['BOXHEADER_COLOR']" size='8' id="boxheaderColor" onchange="checkcp('radioBoxHeaderColor','BOXHEADER_COLOR','boxheaderColor');"></s:textfield>
											
											<span class="input-group-addon">
												<i></i>
											</span>
										</div>
									</div>
								</div>
								<div class="col-md-3 col-sm-3 extramargin  leftPadding">	
									<!-- <input type="radio" onclick="getDiv('BoxHeader','Image');" name="radioBoxHeader" id="radioBoxHeaderImage" class="css-checkbox"/> -->
									 <input type='radio' class="paypal"  data-head="BoxHeader" id="radioBoxHeaderImage" data-type="Image"
				name="themeAttribs['BOXHEADER_TYPE']" value="IMAGE" 
				<s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>>
									<label for="radioBoxHeaderImage" class="css-label radGroup2" style="margin-right:15px;">Image</label>
									<div class="extramargin" id="ShowBoxHeaderImage" style="<s:if test="%{#type=='IMAGE'}">display:block</s:if><s:else>display:none</s:else>">
										<div class="input-group">
											<!-- <input type="text" class="form-control" placeholder="Image Path"> -->
											
											<s:textfield cssClass="form-control" name="themeAttribs['BOXHEADER_IMAGE']" size='45' id="BOXHEADER_IMAGE"></s:textfield> 
											
											<span class="input-group-addon">
												<input id="bxhimg" type="image" src="../images/image.gif" alt="" align="top" onclick='getImageURL("BOXHEADER_IMAGE","radioBoxHeaderImage");return false;'/>
											</span>
										</div>
									</div>
								</div>								
							</div>		
				<div class="col-md-12 col-sm-12 questktLabel">
				<div style="height:20px"></div>
								<div class="col-md-3 col-sm-3  leftPadding">
								 <s:set name="type" value="themeAttribs['HEADER_TYPE']"></s:set>	
									Header
								</div>
								<div class="col-md-3 col-sm-5 leftPadding">	
									<!-- <input type="radio" onclick="getDiv('Header','Color');" name="radioHeader" id="radioHeaderColor" class="css-checkbox"/> -->
									
									<input class="paypal" data-head="Header" data-type="Color" type='radio' name="themeAttribs['HEADER_TYPE']" value="Default"  id="radioHeaderColor"
		<s:if test="%{#type == 'Default'}">checked="checked"</s:if>>
									
									<label for="radioHeaderColor" class="css-label radGroup2" style="margin-right:15px;">Default</label>									
								</div>
								<div class="col-md-3 col-sm-3  leftPadding">	
									<!-- <input type="radio" onclick="getDiv('Header','Image');" name="radioHeader" id="radioHeaderImage" class="css-checkbox"/> -->
									
									<input type='radio' class="paypal" data-head="Header" data-type="Image" id="radioHeaderImage" name="themeAttribs['HEADER_TYPE']" value="IMAGE"  
			<s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>>
									
									<label for="radioHeaderImage" class="css-label radGroup2" style="margin-right:15px;">Image</label>
									<div class="extramargin" id="ShowHeaderImage" style="<s:if test="%{#type=='IMAGE'}">display:block</s:if><s:else>display:none</s:else>">
										<div class="input-group">
											<!-- <input type="text" class="form-control" placeholder="Image Path"> -->
											
											<s:textfield name="themeAttribs['HEADER_IMAGE']" size='45'
				id="HEADER_IMAGE" cssClass="form-control"></s:textfield> 
											
											<span class="input-group-addon">
												<!-- <input id="pgimg" type="image" align="top" style="height:20px;" src="image.gif"> -->
												<input id="hdrimg" type="image" src="../images/image.gif" alt="" align="top" onclick='getImageURL("HEADER_IMAGE","radioHeaderImage");return false;'/>
											</span>
										</div>
									</div>									
								</div>
								<div class="col-md-3 col-sm-3 leftPadding">	
									<!-- <input type="radio" onclick="getDiv('Header','Html');" name="radioHeader" id="radioHeaderHtml" class="css-checkbox"/> -->
									
									<!-- <input class="paypal" type='radio' id="hd_html" name="themeAttribs['HEADER_TYPE']" value="HTML" onclick="htmlBlock('headerhtml');"
				<s:if test="%{#type == 'HTML'}">checked="checked"</s:if>> -->
				
				 <input class="paypal" data-head="Header" data-type="Html" type='radio' id="radioHeaderHtml" name="themeAttribs['HEADER_TYPE']" value="HTML" 
				<s:if test="%{#type == 'HTML'}">checked="checked"</s:if>>
				
									
									<label for="radioHeaderHtml" class="css-label radGroup2" style="margin-right:15px;">Html</label>
									<div class="extramargin" id="ShowHeaderHtml" style='<s:if test="%{#type == 'HTML'}">display:block</s:if><s:else>display:none</s:else>'>
										<div class="input-group">
											<!-- <textarea class="form-control" rows="3"></textarea> -->
											<s:textarea cssClass="form-control" id="hdrhtmltext" name="themeAttribs['HEADER_HTML']" rows="8" cols="56" ></s:textarea>
										</div>
									</div>									
								</div>															
							</div>
				
				<div class="col-md-12 col-sm-12 questktLabel">
				<div style="height:20px"></div>
								<div class="col-md-3 col-sm-3 leftPadding">
								<s:set name="type" value="themeAttribs['FOOTER_TYPE']"></s:set>
									Footer
								</div>
								<div class="col-md-3 col-sm-5 leftPadding">	
									<!-- <input type="radio" onclick="getDiv('Header','Color');" name="radioHeader" id="radioHeaderColor" class="css-checkbox"/> -->
									
									<!-- <input class="paypal" data-head="Header" data-type="Color" type='radio' name="themeAttribs['HEADER_TYPE']" value="Default"  id="radioHeaderColor"
		<s:if test="%{#type == 'Default'}">checked="checked"</s:if>> -->
		
		  <input type='radio' data-head="Footer" data-type="Color" class="paypal" name="themeAttribs['FOOTER_TYPE']" value="Default"  
		id="radioFooterColor" <s:if test="%{#type == 'Default'}">checked="checked"</s:if>>
		
		
									
									<label for="radioFooterColor" class="css-label radGroup2" style="margin-right:15px;">Default</label>									
								</div>
								<div class="col-md-3 col-sm-3 leftPadding">	
			
			 <input class="paypal" type='radio' data-head="Footer" data-type="Image" id="radioFooterImage" name="themeAttribs['FOOTER_TYPE']" value="IMAGE" 
				<s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>>
			
									
									<label for="radioFooterImage" class="css-label radGroup2" style="margin-right:15px;">Image</label>
									<div class="extramargin" id="ShowFooterImage" style="<s:if test="%{#type=='IMAGE'}">display:block</s:if><s:else>display:none</s:else>">
										<div class="input-group">
				
				 <s:textfield cssClass="form-control" name="themeAttribs['FOOTER_IMAGE']" size='45' id="FOOTER_IMAGE"></s:textfield>
											
											<span class="input-group-addon">
												<!-- <input id="pgimg" type="image" align="top" style="height:20px;" src="image.gif"> -->
												<input id="hdrimg" type="image" src="../images/image.gif" alt="" align="top" onclick='getImageURL("FOOTER_IMAGE","radioFooterImage");return false;'/>
											</span>
										</div>
									</div>									
								</div>
								<div class="col-md-3 col-sm-3 leftPadding">	
									<!-- <input type="radio" onclick="getDiv('Header','Html');" name="radioHeader" id="radioHeaderHtml" class="css-checkbox"/> -->
									
									<!-- <input class="paypal" type='radio' id="hd_html" name="themeAttribs['HEADER_TYPE']" value="HTML" onclick="htmlBlock('headerhtml');"
				<s:if test="%{#type == 'HTML'}">checked="checked"</s:if>> -->
				
				<!--  <input class="paypal" data-head="Header" data-type="Html" type='radio' id="radioHeaderHtml" name="themeAttribs['HEADER_TYPE']" value="HTML" 
				<s:if test="%{#type == 'HTML'}">checked="checked"</s:if>> -->
				
				 <input class="paypal" data-head="Footer" data-type="Html" type='radio'id="radioFooterHtml" name="themeAttribs['FOOTER_TYPE']" value="HTML" <s:if test="%{#type == 'HTML'}">checked="checked"</s:if>/>
				
									
									<label for="radioHeaderHtml" class="css-label radGroup2" style="margin-right:15px;">Html</label>
									<div class="extramargin" id="ShowFooterHtml" style='<s:if test="%{#type == 'HTML'}">display:block</s:if><s:else>display:none</s:else>'>
										<div class="input-group">
											<!-- <textarea class="form-control" rows="3"></textarea> -->
											<%-- <s:textarea cssClass="form-control" id="hdrhtmltext" name="themeAttribs['HEADER_HTML']" rows="8" cols="56" ></s:textarea> --%>
										<s:textarea cssClass="form-control" id="ftrhtmltext" name="themeAttribs['FOOTER_HTML']" rows="8" cols="56"></s:textarea>
										</div>
									</div>									
								</div>															
							</div>
							
							
							<div class="col-md-12 col-sm-12 questktLabel firstrow">
							<div style="height:20px"></div>
								<div class="col-md-3 col-sm-2 extramargin leftPadding">	
									<div>
									 <span class="section-header">Text</span>
									  <s:set name="bodyfonttype" value="themeAttribs['BODYTEXTFONTTYPE']"></s:set>
									  <s:set name="bodyfontsize" value="themeAttribs['BODYTEXTFONTSIZE']"></s:set>
									</div>
								</div>								
							</div>
							
						<div class="col-md-12 col-sm-12 questktLabel">
						<div style="height:20px"></div>
								<div class="col-md-3 col-sm-3 leftPadding">	
									Body  <i class="fa fa-info-circle info" title="This text style is applied to Body."></i>
								</div>
								<div class="col-md-3 col-sm-5  leftPadding">									
									<label for="radioBodyColor" class="radGroup2" style="margin-right:15px;">Color</label>
									<div class="extramargin" id="ShowBodyColor" style="display:block;width:50%;">
										<div class="input-group demo2">
											<!-- <input type="text" class="form-control" placeholder="Color"> -->
											<s:textfield  cssClass="form-control" name="themeAttribs['BODYTEXTCOLOR']" size='8'  data-image="BODYTEXTCOLOR" data-radio="radioBodyColor" onchange="checkcp('','BODYTEXTCOLOR','bodytextcolor');"
				id="bodytextcolor"></s:textfield>
											<span class="input-group-addon">
												<i style="background-color: rgb(238, 238, 238);"></i>
											</span>
										</div>
									</div>
								</div>
								<div class="col-md-3 col-sm-3  leftPadding">	
									<label for="radioBodyType" class="radGroup2" style="margin-right:15px;">Font Type</label>
									<div class="extramargin" id="ShowBodyType" style="display:block;">
										<div class="input-group">
											<%-- <select class="form-control" id="sel1">
												<option>Verdana</option>
												<option>Times New Roman</option>
												<option>Arial</option>
											</select> --%>
											
											<select style="width:170px" class="form-control" id="BODYTEXTFONTTYPE" name="themeAttribs['BODYTEXTFONTTYPE']" onchange="onchangeFonts('BODYTEXTFONTTYPE');">
				<option value="Lucida Grande,Lucida Sans Unicode,sans-serif">Lucida Grande,Lucida Sans Unicode,sans-serif</option>
				<s:iterator value="fontTypes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>"
						<s:if test="%{#bodyfonttype==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select>
											
											
										</div>
									</div>
								</div>
								<div class="col-md-3 col-sm-3 leftPadding">	
									<label for="radioBodySize" class="radGroup2" style="margin-right:15px;">Font Size</label>
									<div class="extramargin" id="ShowBodySize" style="display:block;">
										<div class="input-group">
											<%-- <select class="form-control" id="sel1">
												<option>12</option>
												<option>14</option>
												<option>16</option>
											</select> --%>
											
											<select class="form-control" id="BODYTEXTFONTSIZE" name="themeAttribs['BODYTEXTFONTSIZE']" onchange="onchangeFonts('BODYTEXTFONTSIZE');">
				<s:iterator value="fontSizes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>"
						<s:if test="%{#bodyfontsize==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select>
											
										</div>
									</div>
								</div>								
							</div>
							<div class="col-md-12 col-sm-12 questktLabel">
							<div style="height:20px"></div>
								<div class="col-md-3 col-sm-3  leftPadding">	
								 <s:set name="largefonttype" value="themeAttribs['LARGETEXTFONTTYPE']"></s:set>
								<s:set name="largefontsize" value="themeAttribs['LARGETEXTFONTSIZE']"></s:set>
									Large <i class="fa fa-info-circle info" title="This text style is applied to Event headers."></i>
								</div>
								<div class="col-md-3 col-sm-5 leftPadding">									
									<label for="radioLargeColor" class="radGroup2" style="margin-right:15px;">Color</label>
									<div class="extramargin" id="ShowLargeColor" style="display:block;width:50%;">
										<div class="input-group demo2">
											<!-- <input type="text" class="form-control" placeholder="Color"> -->
											 <s:textfield cssClass="form-control" name="themeAttribs['LARGETEXTCOLOR']" size='8' data-image="LARGETEXTCOLOR" data-radio="radioLargeColor" onchange="checkcp('','LARGETEXTCOLOR','largetextcolor');"
				id="largetextcolor"></s:textfield>
											<span class="input-group-addon">
												<i></i>
											</span>
										</div>
									</div>
								</div>
								<div class="col-md-3 col-sm-3 leftPadding">	
									<label for="radioLargeType" class="radGroup2" style="margin-right:15px;">Font Type</label>
									<div class="extramargin" id="ShowLargeType" style="display:block;">
										<div class="input-group">
											<%-- <select class="form-control" id="sel1">
												<option>Verdana</option>
												<option>Times New Roman</option>
												<option>Arial</option>
											</select> --%>
											
											<select style="width:170px" class="form-control" id="LARGETEXTFONTTYPE" name="themeAttribs['LARGETEXTFONTTYPE']" onchange="onchangeFonts('LARGETEXTFONTTYPE');">
				<option value="Verdana, sans-serif">Verdana, sans-serif</option>
				<s:iterator value="fontTypes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>"
						<s:if test="%{#largefonttype==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select>
											
										</div>
									</div>
								</div>
								<div class="col-md-3 col-sm-3 leftPadding">	
									<label for="radioLargeSize" class="radGroup2" style="margin-right:15px;">Font Size</label>
									<div class="extramargin" id="ShowLargeSize" style="display:block;">
										<div class="input-group">
											<%-- <select class="form-control" id="sel1">
												<option>12</option>
												<option>14</option>
												<option>16</option>
											</select> --%>
											
											<select class="form-control" id="LARGETEXTFONTSIZE" name="themeAttribs['LARGETEXTFONTSIZE']" onchange="onchangeFonts('LARGETEXTFONTSIZE');">
				<s:iterator value="fontSizes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>"
						<s:if test="%{#largefontsize==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select>
											
										</div>
									</div>
								</div>								
							</div>
						<div class="col-md-12 col-sm-12 questktLabel">
						<div style="height:20px"></div>
								<div class="col-md-3 col-sm-3 leftPadding">	
								   <s:set name="mediumfonttype" value="themeAttribs['MEDIUMTEXTFONTYPE']"></s:set>
		<s:set name="mediumfontsize" value="themeAttribs['MEDIUMTEXTFONTSIZE']"></s:set>
									Medium <i class="fa fa-info-circle info" title="This text style is applied to Box headers."></i>
								</div>
								<div class="col-md-3 col-sm-5 leftPadding">									
									<label for="radioMediumColor" class="radGroup2" style="margin-right:15px;">Color</label>
									<div class="extramargin" id="ShowMediumColor" style="display:block;width:50%;">
										<div class="input-group demo2">
											<!-- <input type="text" class="form-control" placeholder="Color"> -->
											 <s:textfield cssClass="form-control" name="themeAttribs['MEDIUMTEXTCOLOR']" data-image="MEDIUMTEXTCOLOR" data-radio="radioMediumColor" size='8' onchange="checkcp('','MEDIUMTEXTCOLOR','medtextcolor');"
				id="medtextcolor"></s:textfield>
											<span class="input-group-addon">
												<i></i>
											</span>
										</div>
									</div>
								</div>
								<div class="col-md-3 col-sm-3 leftPadding">	
									<label for="radioMediumType" class="radGroup2" style="margin-right:15px;">Font Type</label>
									<div class="extramargin" id="ShowMediumType" style="display:block;">
										<div class="input-group">
											<%-- <select class="form-control" id="sel1">
												<option>Verdana</option>
												<option>Times New Roman</option>
												<option>Arial</option>
											</select> --%>
											
											<select  style="width:170px" class="form-control" id="MEDIUMTEXTFONTYPE" name="themeAttribs['MEDIUMTEXTFONTYPE']" onchange="onchangeFonts('MEDIUMTEXTFONTYPE');">
				<option value="Verdana, sans-serif">Verdana, sans-serif</option>
				<s:iterator value="fontTypes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>"
						<s:if test="%{#mediumfonttype==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select>
											
										</div>
									</div>
								</div>
								<div class="col-md-3 col-sm-3 leftPadding">	
									<label for="radioMediumSize" class="radGroup2" style="margin-right:15px;">Font Size</label>
									<div class="extramargin" id="ShowMediumSize" style="display:block;">
										<div class="input-group">
											<%-- <select class="form-control" id="sel1">
												<option>12</option>
												<option>14</option>
												<option>16</option>
											</select> --%>
											
											<select  class="form-control" id="MEDIUMTEXTFONTSIZE" name="themeAttribs['MEDIUMTEXTFONTSIZE']" onchange="onchangeFonts('MEDIUMTEXTFONTSIZE');">
				<s:iterator value="fontSizes">
					<s:set name="cval" value="key" />
					<s:property value="%{mediumfontsize}"/>
					<option value="<s:property value="key"/>"
						<s:if test="%{#mediumfontsize==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select>
											
										</div>
									</div>
								</div>								
							</div>	
							
<div class="col-md-12 col-sm-12 questktLabel">
<div style="height:20px"></div>
								<div class="col-md-3 col-sm-3 leftPadding">	
								 <s:set name="smallfonttype" value="themeAttribs['SMALLTEXTFONTTYPE']"></s:set>
								 <s:set name="smallfontsize"	value="themeAttribs['SMALLTEXTFONTSIZE']"></s:set>
									Small <i class="fa fa-info-circle info" title="This text style is applied to all other content."></i>
								</div> 
								<div class="col-md-3 col-sm-5 leftPadding">									
									<label for="radioSmallColor" class="radGroup2" style="margin-right:15px;">Color</label>
									<div class="extramargin" id="ShowSmallColor" style="display:block;width:50%;">
										<div class="input-group demo2">
											<!-- <input type="text" class="form-control" placeholder="Color"> -->
											<s:textfield cssClass="form-control" name="themeAttribs['SMALLTEXTCOLOR']" size='8' data-image="SMALLTEXTCOLOR" data-radio="radioSmallColor" onchange="checkcp('','SMALLTEXTCOLOR','smalltextcolor');"
				id="smalltextcolor"></s:textfield>
											<span class="input-group-addon">
												<i></i>
											</span>
										</div>
									</div>
								</div>
								<div class="col-md-3 col-sm-3 leftPadding">	
									<label for="radioSmallType" class="radGroup2" style="margin-right:15px;">Font Type</label>
									<div class="extramargin" id="ShowSmallType" style="display:block;">
										<div class="input-group">
											<%-- <select class="form-control" id="sel1">
												<option>Verdana</option>
												<option>Times New Roman</option>
												<option>Arial</option>
											</select> --%>
											<select style="width:170px" class="form-control" id="SMALLTEXTFONTTYPE" name="themeAttribs['SMALLTEXTFONTTYPE']" onchange="onchangeFonts('SMALLTEXTFONTTYPE');">
				<option value="Verdana, Arial, Helvetica, sans-serif">Verdana, Arial, Helvetica, sans-serif</option>
				<s:iterator value="fontTypes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>"
						<s:if test="%{#smallfonttype==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select>
										</div>
									</div>
								</div>
								<div class="col-md-3 col-sm-3 leftPadding">	
									<label for="radioSmallSize" class="radGroup2" style="margin-right:15px;">Font Size</label>
									<div class="extramargin" id="ShowSmallSize" style="display:block;">
										<div class="input-group">
											<%-- <select class="form-control" id="sel1">
												<option>12</option>
												<option>14</option>
												<option>16</option>
											</select> --%>
											<s:select cssClass="form-control"  list="fontSizes" listKey="key" listValue="value" name="themeAttribs['SMALLTEXTFONTSIZE']"></s:select>
										</div>
									</div>
								</div>								
							</div>
							
<div class="col-md-12 col-sm-12 questktLabel">
<div style="height:20px"></div>
								<div class="col-md-3 col-sm-3 leftPadding">	
									Link <i class="fa fa-info-circle info" title="This text style is applied to Links."></i>
									 <s:set name="bodyfonttype" value="themeAttribs['LINKTEXTFONTTYPE']"></s:set>
		<s:set name="bodyfontsize" value="themeAttribs['LINKTEXTFONTSIZE']"></s:set>
								</div>
								<div class="col-md-3 col-sm-5 leftPadding">									
									<label for="radioLinkColor" class="radGroup2" style="margin-right:15px;">Color</label>
									<div class="extramargin" id="ShowLinkColor" style="display:block;width:50%;">
										<div class="input-group demo2">
											<!-- <input type="text" class="form-control" placeholder="Color"> -->
											<s:textfield  cssClass="form-control" name="themeAttribs['LINKTEXTCOLOR']" size='8'
				id="linktextcolor" data-image="LINKTEXTCOLOR" data-radio="radioLinkColor" onchange="checkcp('','LINKTEXTCOLOR','linktextcolor');"></s:textfield>
											<span class="input-group-addon">
												<i></i>
											</span>
										</div>
									</div>
								</div>
								<div class="col-md-3 col-sm-3 leftPadding">	
									<label for="radioLinkType" class="radGroup2" style="margin-right:15px;">Font Type</label>
									<div class="extramargin" id="ShowLinkType" style="display:block;">
										<div class="input-group">
											<%-- <select class="form-control" id="sel1">
												<option>Verdana</option>
												<option>Times New Roman</option>
												<option>Arial</option>
											</select> --%>
											<select style="width:170px" class="form-control" id="LINKTEXTFONTTYPE" name="themeAttribs['LINKTEXTFONTTYPE']" onchange="onchangeFonts('LINKTEXTFONTTYPE');">
				<option value="Lucida Grande,Lucida Sans Unicode,sans-serif">Lucida Grande,Lucida Sans Unicode,sans-serif</option>
				<s:iterator value="fontTypes">
					<s:set name="cval" value="key"
					 />
					 
					<option value="<s:property value="key"/>"
						<s:if test="%{#linkfonttype==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select>
										</div>
									</div>
								</div>
								<div class="col-md-3 col-sm-3 leftPadding">	
									<label for="radioLinkSize" class="radGroup2" style="margin-right:15px;">Font Size</label>
									<div class="extramargin" id="ShowLinkSize" style="display:block;">
										<div class="input-group">
											<%-- <select class="form-control" id="sel1">
												<option>12</option>
												<option>14</option>
												<option>16</option>
											</select> --%>
											
											
											<s:set name="linkfontsize" value="themeAttribs['LINKTEXTFONTSIZE']"></s:set>
											<select class="form-control" id="LINKTEXTFONTSIZE" name="themeAttribs['LINKTEXTFONTSIZE']" onchange="onchangeFonts('LINKTEXTFONTSIZE');">
				<s:iterator value="fontSizes">
					<s:set name="cval" value="key" />
					<option value="<s:property value="key"/>"
						<s:if test="%{#linkfontsize==#cval}">selected='selected'</s:if>>${value}</option>
				</s:iterator>
			</select>
											
										</div>
									</div>
								</div>								
							</div>



</div></div></div>	
	
	<br />
</s:form>

<div class="row">
  <div class="form-group">
	 <div class="col-sm-5"></div>
	 <div class="col-sm-5">
	   <button type="submit" class="btn btn-primary" id="colorsntextsubmit">Submit</button>
	   </div>
	</div>
  </div>

<script>

/* function htmlBlock(radcheck){
	if(radcheck=='hdrdef'){
		document.getElementById("hdrhtml").style.display='none';
	}
	if(radcheck=='hd_img'){
		document.getElementById("hdrhtml").style.display='none';
	}
	if(radcheck=='hd_html'){
		document.getElementById("hdrhtml").style.display='block';
	}
	if(radcheck=='ftrdef'){
		document.getElementById("ftrhtml").style.display='none';
	}
	if(radcheck=='ftr_img'){
		document.getElementById("ftrhtml").style.display='none';
	}
	if(radcheck=='ft_html'){
		document.getElementById("ftrhtml").style.display='block';
	}
} */

function getDivs(divid,cssLabel){   		
	var chkid='radio'+divid+cssLabel;
	var showDivid = 'Show'+divid+cssLabel;
	if(document.getElementById(chkid).checked){
		if(cssLabel=='Html'){
			$('#'+showDivid).show();
			if(divid=='Header')
			$('#ShowHeaderImage').hide();
			else
			$('#ShowFooterImage').hide();
		}else{
			if(divid=='Header')
			$('#ShowHeaderHtml').hide();
			else
			$('#ShowFooterHtml').hide();
			$('#'+showDivid).show();
			var cssLabel = (cssLabel == "Image" )?"Color":"Image";	
			var hideDivid = 'Show'+divid+cssLabel;					
			$('#'+hideDivid).hide();					
		}				
	}			
}

$(function(){

	$('input.paypal').on('ifChecked', function(event){
		getDivs($(this).attr('data-head'),$(this).attr('data-type'));
		});
	

	$('.info').tooltipster({
    	theme: 'my-custom-theme',
    	maxWidth:'100px',
    	position:'right'
    });
	

$('#colorsntextsubmit').click(function(){
		$.ajax({
			type:'get',
			data:$('#sform').serialize(),
			url:'EventCustomization!save',
			success:function(result){
				if(!isValidActionMessage(result)) return;
				window.location.reload();
	}
			});
});
	
$('input.paypal').iCheck({  
	checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey'});
	
/* $('input.paypal').on('ifChecked', function(event){
	//htmlBlock($(this).attr('id'));
	console.log($(this).attr('data-head')+"::"+$(this).attr('data-type'));
	getDiv($(this).attr('data-head'),$(this).attr('data-type'));
	}); */

$('.demo2').colorpicker();


$('.demo2').colorpicker().on('changeColor', function(ev){
var cprad=$(this).children('input').attr('data-radio');
var imgid=$(this).children('input').attr('data-image');
var textfieldid=$(this).children('input').attr('id');
var cpheader='';
	loadColorPicker(textfieldid, imgid, cpheader, cprad,ev.color.toHex());
    });

});
</script>


