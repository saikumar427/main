<%@taglib uri="/struts-tags" prefix="s"%>

<s:form theme="simple" id="themesform" name="themesform" action="EventThemes!save" method="post">
<div id="ThemeStatusMsg"></div>
<div id="break" style="display: none;"><br/></div>
<s:hidden name="eid"></s:hidden>
<s:hidden name="themePage.selectedTheme"></s:hidden>
<s:hidden name="theme" id="selectedthemetype" value="%{themePage.selectedTheme}"></s:hidden>

<div style="height:8px;"></div>

	<table width="100%">
		<tr><td valign="top" align="right">Preview&nbsp;<a href="#"><img id="themesPreviewImg" src="../images/arrow_cycle.png" onclick="getThemesContentPreview();" height="20px" width="20px" align="top"/></a>
			<!--input type="button" name="preview" value="Preview" id="themesPreviewBtn" /-->
			</td>
		</tr>
		
		<tr>
			<td colspan='2' align='center'>
			<div id="themesprevid" ></div>
			</td>
		</tr>
	</table>
	<br />
<div class="taskbox"><span class="section-header">Change Theme</span><br />
<hr/>

<s:set name="themetyperadio" value="themePage.themetype"></s:set>
<s:set name="themessize" value="themePage.namesArrayLength"></s:set>
<%-- <div class="row">
<div class="col-md-2">Current Theme : </div><div class="col-md-10"><s:text name="themePage.currentTheme"></s:text></div>
</div>
<div style="height:20px"></div>
<div class="row">
<div class="col-md-2" style="width:16.5%;padding-top:10px;">Change To:</div>

<div class="col-md-3" style="width:21%;padding-top:10px;"><input type="radio" id="ebeethemeid" name='themetype' value='DEFAULT' <s:if test="%{#themetyperadio == 'DEFAULT'}">checked='checked'</s:if>>&nbsp;Eventbee Themes</div>

<div class="col-md-3"><s:text name="themePage.eventbeeThemes"></s:text></div>

 <s:if test="%{#themessize > 0}">
<div class="col-md-2" style="padding-top:9px;">
<input type="radio" id="mythemeid" name='themetype' value='PERSONAL' <s:if test="%{#themetyperadio == 'PERSONAL'}">checked='checked'</s:if>>&nbsp;My Themes
</div>
<div class="col-md-2">
<s:text name="themePage.userThemes"></s:text>
</div>
</s:if> 
</div> --%>



<div id="borderRow" class="panel-body">
					        <!--<div class="form-horizontal" style="margin-bottom:0px;">
								<div class="form-group">
									<div class="col-sm-12 col-md-12form-inline">
										<div  class="moveDown">
											<div class="btn-group"> 
												<button id="multilineTab" class="btn btn-default">Colors & Text <i class="fa fa-info-circle info tooltipstered" style="cursor: pointer"></i></button>
												<button id="textTab" class="btn btn-primary active">Themes</button>
												<button id="multipleTab" class="btn btn-default">HTML and CSS <img src="advanced.jpg" alt="Advanced Ticketing" title="Advanced Ticketing"><i class="fa fa-info-circle info tooltipstered" style="cursor: pointer"></i></button>
											</div>
										</div>									
									</div>
								</div>
							</div>-->
							<div class="col-md-12 col-sm-12 leftPadding">
								<div class="col-md-2 col-sm-4 extramargin  leftPadding">	
									Current Theme:
								</div>
								<div class="col-md-5 col-sm-8 extramargin leftPadding">									
									<label for="radioPageColor" class="radGroup2" style="white-space: normal !important; margin-right:15px;"><s:text name="themePage.currentTheme"></s:text></label>
								</div>															
							</div>
							<div class="col-md-12 col-sm-12 leftPadding">
								<div class="col-md-2 col-sm-4 extramargin  leftPadding">	
									Change To:
								</div>
								<div class="col-md-3 col-sm-5 extramargin leftPadding">									
									<!-- <input type="radio" onclick="getDiv('Page','Color');" name="radioPage" id="radioPageColor" class="css-checkbox"/> -->
									
									<input type="radio" class="themes" id="ebeethemeid" name='themetype' data-page="Page" data-type="Colors" value='DEFAULT' <s:if test="%{#themetyperadio == 'DEFAULT'}">checked='checked'</s:if>>
									<label for="radioPageColor" class="css-label radGroup2" style="margin-right:15px;">Eventbee Themes</label>
								</div>
								
								<%-- <s:if test="%{#themessize > 0}">

&nbsp;My Themes


<s:text name="themePage.userThemes"></s:text>

</s:if> --%>
								<s:if test="%{#themessize > 0}">
								<div class="col-md-3 col-sm-3 extramargin leftPadding">	
									<!--  <input type="radio" onclick="getDiv('Page','Image');" name="radioPage" id="radioPageImage" class="css-checkbox"/>  -->
									 <input type="radio" class="themes" id="mythemeid" data-page="Page" data-type="Images" name='themetype' value='PERSONAL' <s:if test="%{#themetyperadio == 'PERSONAL'}">checked='checked'</s:if>> 
									<label for="radioPageImage" class="css-label radGroup2" style="margin-right:15px;">My Themes</label>
									<div class="extramargin" id="ShowPageImages" style="<s:if test="%{#themetyperadio == 'PERSONAL'}">display:block;</s:if><s:else>display:none;</s:else>">
										<div class="input-group">
											<%-- <select class="form-control" id="sel1">
												<option>Verdana</option>
												<option>Times New Roman</option>
												<option>Arial</option>
											</select> --%>
											
											<s:text name="themePage.userThemes"></s:text>
											
										</div>	
									</div>									
								</div>
								</s:if>								
							</div>
							<div class="col-md-12 col-sm-12">
								<div class="col-md-2 col-sm-2 extramargin  leftPadding">	
									&nbsp;
								</div>								
								<div class="col-md-8 col-sm-8 extramargin leftPadding"  id="ShowPageColors" style="<s:if test="%{#themetyperadio == 'DEFAULT'}">display:block;</s:if><s:else>display:none;</s:else>">
									<div id="myCarousel" class="carousel slide hidden-phone">
										<div class="carousel-inner">
											<div class="item active">
												<div class="row">
													<div class="col-md-3">
														<a href="javascript:;" class="thumbnail" data-id="ebee_basic_seating">
															<img title="ebee_basic_seating" src="/main/bootstrap/images/themes/ebee_basic_seating1.jpg" alt="Image" style="max-width:100%;">
														</a>
													</div>
													<div class="col-md-3">
														<a href="javascript:;" class="thumbnail" data-id="ebee-3d-black">
															<img title="ebee-3d-black" src="/main/bootstrap/images/themes/ebee-3d-black1.jpg" alt="Image" style="max-width:100%;">
														</a>
													</div>
													<div class="col-md-3">
														<a href="javascript:;" class="thumbnail" data-id="ebee-3d-blue">
															<img title="ebee-3d-blue" src="/main/bootstrap/images/themes/ebee-3d-blue1.jpg" alt="Image" style="max-width:100%;">
														</a>
													</div>
													<div class="col-md-3">
														<a href="javascript:;" class="thumbnail" data-id="ebee-3d-grey">
															<img title="ebee-3d-grey" src="/main/bootstrap/images/themes/ebee-3d-grey1.jpg" alt="Image" style="max-width:100%;">
														</a>
													</div>
												</div>
											</div>
											<div class="item">
												<div class="row">
													<div class="col-md-3">
														<a href="javascript:;" class="thumbnail" data-id="ebee-basic-aqua">
															<img title="ebee-basic-aqua" src="/main/bootstrap/images/themes/ebee-basic-aqua1.jpg" alt="Image" style="max-width:100%;">
														</a>
													</div>
													<div class="col-md-3">
														<a href="javascript:;" class="thumbnail" data-id="ebee-basic-black">
															<img title="ebee-basic-black" src="/main/bootstrap/images/themes/ebee-basic-black1.jpg" alt="Image" style="max-width:100%;">
														</a>
													</div>
													<div class="col-md-3">
														<a href="javascript:;" class="thumbnail" data-id="ebee-basic-grey">
															<img title="ebee-basic-grey" src="/main/bootstrap/images/themes/ebee-basic-grey1.jpg" alt="Image" style="max-width:100%;">
														</a>
													</div>
													<div class="col-md-3">
														<a href="javascript:;" class="thumbnail" data-id="ebee-IEEE">
															<img title="ebee-IEEE" src="/main/bootstrap/images/themes/ebee-IEEE1.jpg" alt="Image" style="max-width:100%;">
														</a>
													</div>
												</div>
											</div>
											<div class="item">
												<div class="row">
													<div class="col-md-3">
														<a href="javascript:;" class="thumbnail" data-id="ebee-PMI">
															<img title="ebee-PMI" src="/main/bootstrap/images/themes/ebee-PMI1.jpg" alt="Image" style="max-width:100%;">
														</a>
													</div>
													<div class="col-md-3">
														<a href="javascript:;" class="thumbnail" data-id="basic">
															<img title="ebee-responsive" src="/main/bootstrap/images/themes/ebee-responsive1.jpg" alt="Image" style="max-width:100%;">
														</a>
													</div>
													<div class="col-md-3">
														<a href="javascript:;" class="thumbnail" data-id="ebee-TED">
															<img title="ebee-TED" src="/main/bootstrap/images/themes/ebee-TED1.jpg" alt="Image" style="max-width:100%;">
														</a>
													</div>
													<!-- <div class="col-md-3">
														<a href="#x" class="thumbnail">
															<img src="/main/bootstrap/images/themes/ebee-TED1.jpg" alt="Image" style="max-width:100%;">
														</a>
													</div> -->
												</div>
											</div>
										</div>										
										<a class="left carousel-control" data-slide="prev" role="button" style="background-image: none !important;" href="#myCarousel">
											<span class="glyphicon glyphicon-chevron-left fa fa -3x fa-chevron-left" aria-hidden="true" style="font-size: 36px !important;"></span>
											<span class="sr-only">Previous</span>
										</a>
										<a class="right carousel-control" data-slide="next" role="button" style="background-image: none !important;" href="#myCarousel">
											<span class="glyphicon glyphicon-chevron-right fa fa-3x fa-chevron-right" aria-hidden="true" style="font-size: 36px !important;"></span>
											<span class="sr-only">Next</span>
										</a>
									</div>
									<div id="myCarouselPhone" class="carousel slide visible-phone">
										<div class="carousel-inner">
											<div class="item active">
												<a href="javascript:;" class="thumbnail" data-id="ebee_basic_seating">
													<img title="ebee_basic_seating" src="/main/bootstrap/images/themes/ebee_basic_seating1.jpg" alt="Image" style="max-width:100%;">
												</a>
											</div>
											<div class="item">
												<a href="javascript:;" class="thumbnail" data-id="ebee-3d-black">
													<img title="ebee-3d-black" src="/main/bootstrap/images/themes/ebee-3d-black1.jpg" alt="Image" style="max-width:100%;">
												</a>											
											</div>
											<div class="item">
												<a href="javascript:;" class="thumbnail" data-id="ebee-3d-blue">
													<img title="ebee-3d-blue" src="/main/bootstrap/images/themes/ebee-3d-blue1.jpg" alt="Image" style="max-width:100%;">
												</a>											
											</div>
											<div class="item">
												<a href="javascript:;" class="thumbnail" data-id="ebee-3d-grey">
													<img title="ebee-3d-grey" src="/main/bootstrap/images/themes/ebee-3d-grey1.jpg" alt="Image" style="max-width:100%;">
												</a>											
											</div>
											<div class="item">
												<a href="javascript:;" class="thumbnail" data-id="ebee-basic-aqua">
													<img title="ebee-basic-aqua" src="/main/bootstrap/images/themes/ebee-basic-aqua1.jpg" alt="Image" style="max-width:100%;">
												</a>											
											</div>
											<div class="item">
												<a href="javascript:;" class="thumbnail" data-id="ebee-basic-black">
													<img title="ebee-basic-black" src="/main/bootstrap/images/themes/ebee-basic-black1.jpg" alt="Image" style="max-width:100%;">
												</a>											
											</div>
											<div class="item">
												<a href="javascript:;" class="thumbnail" data-id="ebee-basic-grey">
													<img title="ebee-basic-grey" src="/main/bootstrap/images/themes/ebee-basic-grey1.jpg" alt="Image" style="max-width:100%;">
												</a>											
											</div>
											<div class="item">
												<a href="javascript:;" class="thumbnail" data-id="ebee-IEEE">
													<img title="ebee-IEEE" src="/main/bootstrap/images/themes/ebee-IEEE1.jpg" alt="Image" style="max-width:100%;">
												</a>											
											</div>
											<div class="item">
												<a href="javascript:;" class="thumbnail" data-id="ebee-PMI">
													<img title="ebee-PMI" src="/main/bootstrap/images/themes/ebee-PMI1.jpg" alt="Image" style="max-width:100%;">
												</a>											
											</div>
											<div class="item">
												<a href="javascript:;" class="thumbnail" data-id="basic">
													<img title="ebee-responsive" src="/main/bootstrap/images/themes/ebee-responsive1.jpg" alt="Image" style="max-width:100%;">
												</a>											
											</div>
											<div class="item">
												<a href="javascript:;" class="thumbnail" data-id="ebee-TED">
													<img title="ebee-TED" src="/main/bootstrap/images/themes/ebee-TED1.jpg" alt="Image" style="max-width:100%;">
												</a>											
											</div>
											<a class="left carousel-control" data-slide="prev" role="button" style="background-image: none !important;left:0 !important;" href="#myCarouselPhone">
												<span class="glyphicon glyphicon-chevron-left fa fa -3x fa-chevron-left" aria-hidden="true" style="font-size: 24px !important;"></span>
												<span class="sr-only">Previous</span>
											</a>
											<a class="right carousel-control" data-slide="next" role="button" style="background-image: none !important;right:0 !important;" href="#myCarouselPhone">
												<span class="glyphicon glyphicon-chevron-right fa fa-3x fa-chevron-right" aria-hidden="true" style="font-size: 24px !important;"></span>
												<span class="sr-only">Next</span>
											</a>										
									</div>
								</div>																
							</div>
						</div>
					</div>
<div style="height:10px"></div>
</div>
</s:form>

<div class="row" align="center">
<input type="button" name="submit" value="Submit" id="themesSubmitBtn" class="btn btn-primary" />

</div>
<script>

$(document).ready(function () {
	$(".carousel").carousel();

	$(window).resize(function () {
		$(".carousel").carousel();
	});


	$('input.themes').on('ifChecked', function(event){
		getDiv($(this).attr('data-page'),$(this).attr('data-type'));
		});

	$('.thumbnail').click(function(){
		$('#selectedthemetype').val($(this).attr('data-id'));
		getThemesContentPreview();
		});
	
});


$('#themesSubmitBtn').click(function(){
	$.ajax({
		type:'GET',
		data:$('#themesform').serialize(),
		url:'EventThemes!save',
		success:function(result){
	if(!isValidActionMessage(result))
		return;
		window.location.reload();
}
		});
	
});


function getDiv(divid,cssLabel)
{   		
	var chkid = (cssLabel == "Colors" )?"ebeethemeid":"mythemeid";
	var showDivid = 'Show'+divid+cssLabel;
	if(document.getElementById(chkid).checked){
	    cssLabel = (cssLabel == "Images" )?"Colors":"Images";	
		var hideDivid = 'Show'+divid+cssLabel;
			$('#'+showDivid).show();
			$('#'+hideDivid).hide();
	}			
}

</script>
