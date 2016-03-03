<%@taglib uri="/struts-tags" prefix="s"%>
<style>
.input-group-addon {
	padding: 5px 12px !important;
}
.text {
	margin-top: 8px !important;
}
</style>
<link type="text/css" rel="stylesheet"
	href="/main/bootstrap/css/bootstrap-colorpicker.min.css" />
<script src="/main/bootstrap/js/colorpicker.js"></script>
<div class="row" style="margin-bottom:10px; margin-left:10px;">
    	<div style="float:left"><a href="home"> <i class="glyphicon glyphicon-chevron-left"></i><s:text name="cept.back.to.settings.lbl"></s:text></a></div>
    </div>

<div class="panel panel-primary">
	<div class="panel-heading ">
		<h3 class="panel-title"><s:text name="ms.event.streamer.settings.lbl"/></h3>
	</div>

	<div class="panel-body">
		<form  name="streamerdataform"
			id="streamerdataform" method="POST"
			action="ManageStreamer!saveStreamerData" class="form-horizontal">
			<div class="form-group">
				<div class="col-md-12">
					<h4 class="section-header"><s:text name="ms.settings.th.lbl"/></h4>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-2 control-label"><s:text name="ms.title.lbl"/></div>
				<div class="col-sm-2">
					<s:textfield cssClass="form-control" name="streamerData['TITLE']"
						size='60' id="title" theme="simple"></s:textfield>
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-sm-2 control-label"><s:text name="ms.event.count.lbl"/></div>
				<div class="col-sm-2">

					<s:set name="cnt" value="streamerData['NO_OF_ITEMS']"></s:set>
					<select class="form-control" name="streamerData['NO_OF_ITEMS']">
						<s:iterator value="count">
							<s:set name="cval" value="key" />
							<option value="<s:property value="key"/>"
								<s:if test="%{#cnt==#cval}">selected='selected'</s:if>>${value}</option>
						</s:iterator>
					</select>
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-2 control-label"><s:text name="ms.streamer.width.lbl"/></div>
				<div class="col-md-2">
					<s:textfield cssClass="form-control"
						name="streamerData['STREAMERSIZE']" size='8' id="STREAMERSIZE"
						theme="simple"></s:textfield>
				</div>
				<div class="col-md-2"  style="padding-top:5px;"><s:text  name="epc.photo.pixels.txt.ibl"/></div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-2 control-label"><s:text name="ms.display.link.lbl"/></div>
				<div class="col-md-2">
					<s:set name="type" value="streamerData['DISPLAYEBEELINK']"></s:set>
					<!-- <input class="radiobtn" type="radio"
						name="streamerData['DISPLAYEBEELINK']" value="yes"
						<s:if test="%{#type == 'yes'}">checked="checked"</s:if>>&nbsp;Yes
					&nbsp;&nbsp;&nbsp; <input class="radiobtn" type="radio"
						name="streamerData['DISPLAYEBEELINK']" value="no"
						<s:if test="%{#type != 'yes'}">checked="checked"</s:if>>&nbsp;No -->
					
						<div class="center btn-group btn-toggle" data-toggle="buttons">
				<label id="linkyeslbl" class="optiontype btn no-radius <s:if test="%{#type=='yes'}">btn-default</s:if><s:else>btn-active</s:else>" >
					<!-- <input class="datesel" id="alldates" name="reportsData.attdatetype" value="1" type="radio">All -->
					<input  type="radio" name="streamerData['DISPLAYEBEELINK']" value="yes" <s:if test="%{#type == 'yes'}">checked="checked"</s:if>>&nbsp;<s:text name="gllobal.yes.lbl"/>
					
				</label>
				<label id="linknolbl" class="optiontype btn no-radius <s:if test="%{#type!='yes'}">btn-default</s:if><s:else>btn-active</s:else>">
					<input  type="radio"
						name="streamerData['DISPLAYEBEELINK']" value="no" <s:if test="%{#type != 'yes'}">checked="checked"</s:if>>&nbsp;<s:text name="global.no.lbl"/>
				</label>
			</div>
						
						
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-12">
					<h4 class="section-header"><s:text name="ms.look.feel.th.lbl"/></h4>
				</div>
			</div>

			<div class="row">
				<div class="col-md-2 control-label text"><s:text name="ms.background.header.lbl"/></div>

				<div class="col-md-1 text">
					<s:set name="type" value="streamerData['BG_TYPE']"></s:set>
					<input class="radiobtn" type='radio' name="streamerData['BG_TYPE']"
						value="COLOR"
						<s:if test="%{#type == 'COLOR'}">checked="checked"</s:if>>&nbsp;<s:text name="ms.background.color.rb.lbl"/>
				</div>
				<div class="col-md-2">
					<div class="input-group demo2">
						<s:textfield cssClass="form-control "
							name="streamerData['BACKGROUND_COLOR']" size='8' id="bgcolor"
							theme="simple"></s:textfield>
						<span class="input-group-addon"><i></i></span>
					</div>
				</div>

				<div class="col-md-1 text" style="width:8.5%;">
					<input class="radiobtn" type='radio' name="streamerData['BG_TYPE']"
						value="IMAGE"
						<s:if test="%{#type == 'IMAGE'}">checked="checked"</s:if>>&nbsp;<s:text name="ms.background.image.rb.lbl"/>
				</div>
				<div class="col-md-5">
					<%-- <s:textfield cssClass="form-control" name="streamerData['BACKGROUND_IMAGE']"  size='45' id="BACKGROUND_IMAGE" theme="simple"></s:textfield> --%>

					 <%-- <div class="input-group demo2">
						<s:textfield cssClass="form-control"
							name="streamerData['BACKGROUND_IMAGE']" size='45'
							id="BACKGROUND_IMAGE" theme="simple"></s:textfield>
						<input id="pgimg" type="image" src="../images/image.gif" alt=""
							onclick='getImageURL("BG_IMAGE","radioPageImage");return false;'>
					</div>  --%>
					
					
					 <div class="input-group">
											<s:textfield cssClass="form-control" name="streamerData['BACKGROUND_IMAGE']" size='45' id="BACKGROUND_IMAGE" theme="simple"></s:textfield>
											<span class="input-group-addon">
											<img src="../images/image.gif" alt="" onclick='getImageURL("BACKGROUND_IMAGE");' />
											</span>
										</div> 

				</div>


			</div>
			<br />
			
			
			<div class="row">
				<div class="col-md-2 text"></div>
				<div class="col-md-1 text text-right">
					<s:text name="ms.border.lbl"/>
				</div>
				<div class="col-md-2">
					<div class="input-group demo2">
							<s:textfield cssClass="form-control" name="streamerData['BORDERCOLOR']"  size='8' id="sborder" theme="simple"></s:textfield>
						<span class="input-group-addon"><i></i></span>
					</div>
				</div>

				<div class="col-md-1 text text-right">
					<s:text name="ms.links.lbl"/>
				</div>
				<div class="col-md-2">
					 <div class="input-group demo2">
											<s:textfield cssClass="form-control" name="streamerData['LINKCOLOR']"  size='8' id="slinks" theme="simple"></s:textfield>
											<span class="input-group-addon"><i></i></span>
										</div> 

				</div>


			</div>
			<br />

			<div class="row">
				<div class="col-md-2 control-label"><s:text name="ms.large.text.font.header.lbl"/> <i class="fa fa-info-circle info" id="largeinfo" style="cursor:pointer"></i></div>

				<div class="col-md-1 text text-right"><s:text name="ms.color.lbl"/></div>

				<div class="col-md-2">
					<div class="input-group demo2">
						<s:textfield cssClass="form-control"
							name="streamerData['BIGGER_TEXT_COLOR']" theme="simple"
							id="largetextcolor"></s:textfield>
						<span class="input-group-addon"><i></i></span>
					</div>
				</div>

				<div class="col-md-1 text text-right">
					<s:set name="largefonttype"
						value="streamerData['BIGGER_FONT_TYPE']"></s:set>
					<s:text name="ms.type.lbl"/>
				</div>

				<div class="col-md-2">
					<select class="form-control"
						name="streamerData['BIGGER_FONT_TYPE']">
						<s:iterator value="fontTypes">
							<s:set name="cval" value="key" />
							<option value="<s:property value="key"/>"
								<s:if test="%{#largefonttype==#cval}">selected='selected'</s:if>>${value}</option>
						</s:iterator>
					</select>
				</div>


				<div class="col-md-1 text text-right"><s:text name="ms.size.lbl"/></div>

				<div class="col-md-2">
					<s:set name="largefontsize"
						value="streamerData['BIGGER_FONT_SIZE']"></s:set>
					<select class="form-control"
						name="streamerData['BIGGER_FONT_SIZE']">
						<s:iterator value="fontSizes">
							<s:set name="cval" value="key" />
							<option value="<s:property value="key"/>"
								<s:if test="%{#largefontsize==#cval}">selected='selected'</s:if>>${value}</option>
						</s:iterator>
					</select>
				</div>
			</div>
			<br />


			<div class="row">
				<!-- <div class="col-lg-2 text">Medium Text Font</div> -->

				<div class="col-md-2 control-label"><s:text name="ms.medium.text.font.header.lbl"/> <i class="fa fa-info-circle info"  style="cursor:pointer" id="mediuminfo"></i></div>
				<div class="col-md-1 text text-right"><s:text name="ms.color.lbl"/></div>

				<div class="col-md-2">
					<div class="input-group demo2">
						<s:textfield cssClass="form-control" theme="simple"
							name="streamerData['MEDIUM_TEXT_COLOR']" size='8'
							id="medtextcolor"></s:textfield>
						<span class="input-group-addon"><i></i></span>
					</div>
				</div>

				<div class="col-md-1 text text-right">
					<s:set name="mediumfonttype"
						value="streamerData['MEDIUM_FONT_TYPE']"></s:set>
					<s:text name="ms.type.lbl"/>
				</div>

				<div class="col-md-2 ">

					<select class="form-control"
						name="streamerData['MEDIUM_FONT_TYPE']">
						<s:iterator value="fontTypes">
							<s:set name="cval" value="key" />
							<option value="<s:property value="key"/>"
								<s:if test="%{#mediumfonttype==#cval}">selected='selected'</s:if>>${value}</option>
						</s:iterator>
					</select>
				</div>


				<div class="col-md-1 text text-right"><s:text name="ms.size.lbl"/></div>

				<div class="col-md-2">
					<s:set name="mediumfontsize"
						value="streamerData['MEDIUM_FONT_SIZE']"></s:set>
					<select class="form-control"
						name="streamerData['MEDIUM_FONT_SIZE']">
						<s:iterator value="fontSizes">
							<s:set name="cval" value="key" />
							<option value="<s:property value="key"/>"
								<s:if test="%{#mediumfontsize==#cval}">selected='selected'</s:if>>${value}</option>
						</s:iterator>
					</select>
				</div>
			</div>
			<br />


			<div class="row">
				<div class="col-md-2 control-label"><s:text name="ms.small.text.font.header.lbl"/> <i style="cursor:pointer" class="fa fa-info-circle info" id="smallinfo"></i></div>
				<div class="col-md-1 text text-right"><s:text name="ms.color.lbl"/></div>
				<div class="col-md-2">
					<div class="input-group demo2">
						<s:textfield cssClass="form-control"
							name="streamerData['SMALL_TEXT_COLOR']" size='8'
							id="smalltextcolor" theme="simple"></s:textfield>
						<span class="input-group-addon"><i></i></span>
					</div>
				</div>
				<div class="col-md-1 text text-right">
					<s:set name="smallfonttype" value="streamerData['SMALL_FONT_TYPE']"></s:set>
					<s:text name="ms.type.lbl"/>
				</div>

				<div class="col-md-2">
					<select class="form-control" name="streamerData['SMALL_FONT_TYPE']">
						<s:iterator value="fontTypes">
							<s:set name="cval" value="key" />
							<option value="<s:property value="key"/>"
								<s:if test="%{#smallfonttype==#cval}">selected='selected'</s:if>>${value}</option>
						</s:iterator>
					</select>
				</div>

				<div class="col-md-1 text text-right"><s:text name="ms.size.lbl"/></div>

				<div class="col-md-2">
					<s:set name="smallfontsize" value="streamerData['SMALL_FONT_SIZE']"></s:set>
					<select class="form-control" name="streamerData['SMALL_FONT_SIZE']">
						<s:iterator value="fontSizes">
							<s:set name="cval" value="key" />
							<option value="<s:property value="key"/>"
								<s:if test="%{#smallfontsize==#cval}">selected='selected'</s:if>>${value}</option>
						</s:iterator>
					</select>
				</div>
			</div>
		</form>
	</div>
	<div class="panel-footer center">
		<button type="submit" class="btn btn-primary" id="streamersubmit"><s:text name="global.submit.btn.lbl"/></button>
		<button type="submit" class="btn btn-primary" id="streamerpreview"><s:text name="global.preview.btn.lbl"/></button>
		<%-- <button type="submit" class="btn btn-primary" id="cancel"><s:text name="global.cancel.btn.lbl"/></button> --%>

	</div>
</div>


<script>

var currentDivId="";
var previewFileName="";



	$(function() { 
		$('#largeinfo').attr('title',props.ms_large_text_font_lbl);
	$('#mediuminfo').attr('title',props.ms_medium_text_format_lbl);
	$('#smallinfo').attr('title',props.ms_small_text_font_lbl);
	
	
		$('input.radiobtn').iCheck({  
			checkboxClass: 'icheckbox_square-grey', 
			radioClass: 'iradio_square-grey'});
			

		

		$('.info').tooltipster({
	    	maxWidth:'100px',
	    	position:'right'
	    });

$('.optiontype').click(function(){
	if($(this).attr('id')=='linkyeslbl'){
		$('#linkyeslbl').removeClass('btn-active').addClass('btn-default');
		$('#linknolbl').removeClass('btn-default').addClass('btn-active');
		$('#linkyeslbl').prop("checked", true);
		}else{
		$('#linknolbl').removeClass('btn-active').addClass('btn-default');
		$('#linkyeslbl').removeClass('btn-default').addClass('btn-active');
		$('#linknolbl').prop("checked", true);
		}
		
});
	    
		
		/* $('input.radiobtn').iCheck({
			checkboxClass : 'icheckbox_square-grey',
			radioClass : 'iradio_square-grey'
		}); */
	});

	$(function() {
		$('.demo2').colorpicker();
	});

	$('#streamersubmit').click(function() {
		$.ajax({
			type : "GET",
			url : 'ManageStreamer!saveStreamerData',
			data : $("#streamerdataform").serialize(),
			success : function(result) {
				window.location.href = 'home';
			}
		});
	});

	$('#cancel').click(function() {
		window.location.href = '/main/mysettings/home';
	});

	$('#streamerpreview').click(function() {
		$('#streamerpreview').attr('disabled', 'disabled');
		$.ajax({
			type : "GET",
			url : 'ManageStreamer!saveStreamerPreviewData',
			data : $("#streamerdataform").serialize(),
			success : function(result) {
				$('#streamerpreview').removeAttr('disabled');
				$('.modal-title').html('Streamer Preview');
				$('#myModal').on('show.bs.modal', function() {
					var url = 'ManageStreamer!preview';
					$('iframe#popup').attr("src", url);
					$('iframe#popup').css("height", "350px");
				});
				$('#myModal').modal('show');
			}
		});
	});

	function setWebPath(fullPath){
		document.getElementById(currentDivId).value=fullPath;
		$('#myModal').modal('hide');
	}

	
	function getImageURL(divId){
		currentDivId=divId;
		url='../membertasks/ImageUpload';
		$('.modal-title').html('Image Upload');
		$('#myModal').on('show.bs.modal', function () {
		$('iframe#popup').attr("src",url);
		$('iframe#popup').css("height","220px"); 
		});
		$('#myModal').modal('show');
	}
	
</script>