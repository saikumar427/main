<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" language="JavaScript"
	src="../js/eventmanage/looknfeel.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>
	<script type="text/javascript" src="../js/newcolorpicker.js"></script>
	
	
	<style>
	 #myCarouselPhone {
    display:block;
  }
   #myCarousel{
    display:none;
  }
	@media (min-width: 768px) {
  #myCarouselPhone {
    display:block;
  }
   #myCarousel{
    display:none;
  }
}
@media (min-width: 992px) {
  #myCarouselPhone {
    display:none;
  }
   #myCarousel{
    display:block;
  }
}
.extra-row-margin{
margin:20px 0px;
}
.extra-row-margin:first-child{
	margin:0px 0px 20px 0px;
}
.extra-row-margin:last-child{
	margin:20px 0px 0px 0px;
}
div.looknfeel .btn-active,div.looknfeel .btn-default{
width:136px !important;
}
.extra{
 margin-left: 3px;
    margin-top: 4px;
}

.button{
height: 20px; 
width: 20px; 
border: 1px solid grey;
cursor: pointer;
background-color: #ddd;
}

.image img{
width:28px;
cursor:pointer;
}

.previewbtn{
/* background-color: orange;
    border: 5px solid white;
    color: white;
    cursor: pointer;
    font-size: 30px;
    font-weight: bold;
    height: 56px;
    margin: 0 auto;
    margin-top: 0;
    text-align: center;
    width: 20%; */
    
     background-color: orange;
    border: 5px solid white;
    bottom: 10px;
    color: white;
    cursor: pointer;
    font-size: 30px;
    font-weight: bold;
    height: 56px;
    left: 45%;
    margin: 0 auto;
    position: fixed;
    text-align: center;
    width: 16%;
    z-index:99999;
    
}

.header-margins{
margin-top:8px;
}



	
	</style>
	
	
<script type="text/javascript">
var _eid = ${eid};
var serveraddress = '${serverAddress}';
//serveraddress='http://localhost';
</script>
<%-- <div class="row">
<div class="col-md-4">
<input type="radio" name="looknfeelradio" value="" class="bsradiochk" id="colorntextradio" onclick="getfields();" <s:if test="%{looknfeel=='LooknFeel'}">checked='checked'</s:if>/>&nbsp;Colors & Text 
 <i class="fa fa-info-circle info" title="If you customized your event page by editing HTML & CSS or selecting Theme, those changes will be lost when you change Colors & Text here. "></i>
</div>

<div class="col-md-4">
<input type="radio" name="looknfeelradio" value="" class="bsradiochk" id="themesradio" onclick="getfields();" <s:if test="%{looknfeel=='Themes'}">checked='checked'</s:if>/>&nbsp;Themes 
 <i class="fa fa-info-circle info" title="If you customized your event page by editing HTML & CSS or Colors & Text, those changes will be lost when you change the event Theme here. "></i>
</div>

<div class="col-md-4">
<input type="radio" name="looknfeelradio" value="" class="bsradiochk" id="htmlcssradio" onclick="getfields();" <s:if test="%{looknfeel=='HTMLnCSS'}">checked='checked'</s:if>/>&nbsp;HTML & CSS
  <s:if test="%{powertype=='RSVP'}">
		<img title="Pro Ticketing" alt="Pro Ticketing" src="/main/images/pro.jpg">
		 </s:if><s:else>
		  <img title="Advanced Ticketing" alt="Advanced Ticketing" src="/main/images/advanced.jpg">
		 </s:else>
 <i class="fa fa-info-circle info" title="If you customized your event page by selecting Theme or Colors & Text, those changes will be lost when you change HTML & CSS here. "></i>
</div>
</div> --%>

<%-- <div class="center btn-group btn-toggle" data-toggle="buttons">
				<label id="colorslbl" class="optiontype btn <s:if test="%{looknfeel=='LooknFeel'}">btn-active</s:if><s:else>btn-default</s:else>" >
					<input type="radio" name="looknfeelradio" value="" class="bsradiochk" id="colorntextradio" onclick="getfields();" <s:if test="%{looknfeel=='LooknFeel'}">checked='checked'</s:if>/>&nbsp;Colors & Text 
                     <i class="fa fa-info-circle info" title="If you customized your event page by editing HTML & CSS or selecting Theme, those changes will be lost when you change Colors & Text here. "></i>
				</label>
				<label id="themelbl" class="optiontype btn <s:if test="%{looknfeel=='Themes'}">btn-active</s:if><s:else>btn-default</s:else>">
					<input type="radio" name="looknfeelradio" value="" class="bsradiochk" id="themesradio" onclick="getfields();" <s:if test="%{looknfeel=='Themes'}">checked='checked'</s:if>/>&nbsp;Themes 
 <i class="fa fa-info-circle info" title="If you customized your event page by editing HTML & CSS or Colors & Text, those changes will be lost when you change the event Theme here. "></i>
				</label>
				
				<label id="htmlcsslbl" class="optiontype btn <s:if test="%{looknfeel=='HTMLnCSS'}">btn-active</s:if><s:else>btn-default</s:else>">
					<input type="radio" name="looknfeelradio" value="" class="bsradiochk" id="htmlcssradio" onclick="getfields();" <s:if test="%{looknfeel=='HTMLnCSS'}">checked='checked'</s:if>/>&nbsp;HTML & CSS
  <s:if test="%{powertype=='RSVP'}">
		<img title="Pro Ticketing" alt="Pro Ticketing" src="/main/images/pro.jpg">
		 </s:if><s:else>
		  <img title="Advanced Ticketing" alt="Advanced Ticketing" src="/main/images/advanced.jpg">
		 </s:else>
 <i class="fa fa-info-circle info" title="If you customized your event page by selecting Theme or Colors & Text, those changes will be lost when you change HTML & CSS here. "></i>
				</label>
				
			</div> --%>




<%-- <div id="colorntextcontent" style="display: none">

 <jsp:include page="themepage.jsp"></jsp:include> 
</div>
<div id="themescontent" style="display: none">

 <jsp:include page="EventThemes.jsp"></jsp:include> 
</div>
<div id="htmlcsscontent" style="display: none">
 <jsp:include page="htmlncsscustomization.jsp"></jsp:include> 
</div> --%>
<div style="margin: 0 -30px;" ><div class="col-lg-12" style="background:#f3f6fa;margin: -24px 0; padding-bottom: 20px; padding-top: 18px;"><div class="col-md-3" style="padding:0px">
<s:text name="themePage.eventbeeThemes" ></s:text>
</div><div style="clear:both;"></div></div></div>
<div style="clear:both;"></div>
<div class="row" style="margin-top:20px;padding-top: 25px;background-color:white;padding-bottom: 20px;">
<div class="col-md-3" id="colorsntext">
 <div data-toggle="buttons" class="center btn-group btn-toggle looknfeel" id="taggling-menu">
												<label class="btn-active optiontype btn no-radius labels looknfeellbl" id="widgetslbl">
													<input type="radio" value="1" name="events" id="active" class="datesel"><span>Widgets</span>
												</label>
												<br>
										<div style="height: 40px;"></div>

												<label class="btn-active optiontype btn no-radius looknfeellbl" id="fontslbl">
													<input type="radio" value="2" name="events" id="all" class="datesel">Font
												</label>
												<br>
												<div style="height: 40px;"></div>
												<label class="btn-default optiontype btn no-radius labels looknfeellbl" id="colorslbl">
													<input type="radio" value="2" name="events" id="all" class="datesel">Colors
												</label>
												
												<br/><div style="height: 40px;"></div>
												<label class="btn-active optiontype btn no-radius labels looknfeellbl" id="htmlcsslbl">
													<input type="radio" value="2" name="events" id="all" class="datesel">HTML/CSS
												</label>
												
												
										</div>


</div>
<div class="col-md-9" id="colorsntextdata">
	<div class="row extra-row-margin">
		<div class="">
		<div class="col-md-3">
			Page:
		</div>
		<div class="col-md-3 extra">
			<div class="button"  onclick="getType('BG');" id="BG_COLOR"></div>
		</div>
		</div>
	</div>
	<div class="row extra-row-margin">
		<div class="">
		<div class="col-md-3">
			Container:
		</div>
		<div class="col-md-3 extra">
		<div class="button"  onclick="getType('CONTAINERBG');" id="CONTAINERBG_COLOR"></div>
		</div>
		</div>
	</div>
	<div class="row extra-row-margin">
		<div class="">
		<div class="col-md-3">
			Box:
		</div>
		<div class="col-md-3 extra">
		<div class="button"  onclick="getType('BOXBG');" id="BOXBG_COLOR"></div>
		</div>
		</div>
	</div>	
	<div class="row extra-row-margin">
		<div class="">
		<div class="col-md-3">
			Box header:
		</div>
		<div class="col-md-3 extra">
		<div class="button"  onclick="getType('BOXHEADER');" id="BOXHEADER_COLOR"></div>
		</div>
		</div>
	</div>	
	<div class="row extra-row-margin">
		<div class="">
		<div class="col-md-3">
			Header:
		</div>
		<div class="col-md-3 image">
		<img src="../images/image.gif" onclick="getType('HEADER');"/>
		</div>
		</div>
	</div>	
	<div class="row extra-row-margin" id="footertype">
		<div class="">
		<div class="col-md-3">
			Footer:
		</div>
		<div class="col-md-3 image">
		<img src="../images/image.gif" onclick="getType('FOOTER');"/>
		</div>
		</div>
	</div>
</div>



<div id="fonts" class="col-md-9" style="display:none;">
<div class="row extra-row-margin">
		<div class="">
		<div class="col-md-3">
			Body:
		</div>
		<div class="col-md-3 extra">
			<div class="button"  onclick="getFontStyles('BODYTEXT');" id="BODYTEXTCOLOR"></div>
		</div>
		</div>
	</div>
	<div class="row extra-row-margin">
		<div class="">
		<div class="col-md-3">
			Large:
		</div>
		<div class="col-md-3 extra">
		<div class="button"  onclick="getFontStyles('LARGETEXT');" id="LARGETEXTCOLOR"></div>
		</div>
		</div>
	</div>
	<div class="row extra-row-margin">
		<div class="">
		<div class="col-md-3">
			Medium:
		</div>
		<div class="col-md-3 extra">
		<div class="button"  onclick="getFontStyles('MEDIUMTEXT');" id="MEDIUMTEXTCOLOR"></div>
		</div>
		</div>
	</div>	
	<div class="row extra-row-margin">
		<div class="">
		<div class="col-md-3">
			Small:
		</div>
		<div class="col-md-3 extra">
		<div class="button"  onclick="getFontStyles('SMALLTEXT');" id="SMALLTEXTCOLOR"></div>
		</div>
		</div>
	</div>	
	<div class="row extra-row-margin">
		<div class="">
		<div class="col-md-3">
			Link:
		</div>
		<div class="col-md-3 extra">
		<div class="button"  onclick="getFontStyles('LINKTEXT');" id="LINKTEXTCOLOR"></div>
		</div>
		</div>
	</div>	

</div>

<div id="widgets" class="col-md-9" style="display:none;">


	<form name="socialsettings" id="socialsettings" action="EventPageContent!updatePromoSettings">
	<s:hidden name="eid" id="eid"></s:hidden> 
	<s:hidden name="mgrId"></s:hidden> 
	<s:hidden name="powertype" id="ptype"> </s:hidden>	
	
	<div class="section-header">Promotions & Other information</div>
	
	<s:if test="%{powertype=='Ticketing'}">
	<div class="row">
		<div class="col-md-4"><input type="checkbox" class="showsopromo content" name="promosettings" id="socialmediapromotions" value="socialmediapromotions" 
			<s:if test="%{promosettings.contains('socialmediapromotions')  }">checked="checked"</s:if> />&nbsp; Social Promotions &nbsp; <i class="fa fa-info-circle"  style="cursor:pointer" id="spromoinfo" title="This will display the information about who has promoted the event, in your event page"></i>
		</div>
	<div class="col-md-4"><input type="checkbox" class="promotion content" name="promosettings" id="promotions" value="promotions"
			<s:if test="%{promosettings.contains('promotions') ||  configValuesofpromotions.isEmpty }">checked="checked"</s:if> />&nbsp;Promotions &nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="promoinfo" title="This will display a checkbox which is selected by default for the buyers to recieve promotions and discounts from eventbee and its partners."></i>
		</div>
	</div>	
	</s:if>
	
	<div class="row">
		<div class="col-md-4"><input type="checkbox" class="maps content" name="promosettings" id="maps" value="maps"
			<s:if test="%{promosettings.contains('when') || configValuesofpromotions.isEmpty}">checked="checked"</s:if> />  &nbsp;When
		</div>
	<div class="col-md-4"><input type="checkbox" class="attendee content" name="promosettings" id="attendee" value="attendee"
			<s:if test="%{promosettings.contains('where') || configValuesofpromotions.isEmpty}">checked="checked"</s:if> /> Where
		</div>
		
	</div>
	
	
	
	
	<div class="row">
		<div class="col-md-4"><input type="checkbox" class="maps content" name="promosettings" id="maps" value="maps"
			<s:if test="%{promosettings.contains('maps') || configValuesofpromotions.isEmpty}">checked="checked"</s:if> />  &nbsp;Google Maps &nbsp; <i class="fa fa-info-circle"  style="cursor:pointer" id="googleinfo" title="Show or hide google map on your event page, by default google map is displayed."></i>
		</div>
	<div class="col-md-4"><input type="checkbox" class="attendee content" name="promosettings" id="attendee" value="attendee"
			<s:if test="%{promosettings.contains('attendee') || configValuesofpromotions.isEmpty}">checked="checked"</s:if> /> Who's Attending &nbsp; <i class="fa fa-info-circle"  style="cursor:pointer" id="whosinfo" title="Display attendee list on your event page by selecting Show option. You can further select what information of the attendee you want to display on the event page. By default attendee list is not displayed on event page. You can choose to display Attendees in Event Page by clicking on Show radio button"></i>
		</div>
		<a  style="top-margin:10px;"  id="editdisplaybtn">Edit Display Fields</a>
	</div>
	
<div class="section-header header-margins">Share Buttons</div>
	
	<div class="row">
	<div class="col-md-4">
	<input type="checkbox" class="twitter content" name="socialnwchkbox" id="twitter" value="twitter"
				<s:if test="%{socialnwchkbox.contains('twitter') || configValues.isEmpty}">checked='checked'</s:if> />&nbsp;Twitter
	</div>
	<div class="col-md-4">
	<input type="checkbox" class="facebook content" name="socialnwchkbox" id="fblike" value="fblike"
				<s:if test="%{socialnwchkbox.contains('fblike') || configValues.isEmpty}">checked='checked'</s:if> />&nbsp;Facebook Like
	</div>
	</div>
	
	<div class="row">
	<div class="col-md-4">
	<input type="checkbox" class="google content" name="socialnwchkbox" id="googleplusone" value="googleplusone"
				<s:if test="%{socialnwchkbox.contains('googleplusone') || configValues.isEmpty}">checked='checked'</s:if> />&nbsp;Google +1
	</div>
	</div>
	
	<div class="section-header header-margins">Commenting</div>
	
	<div class="row">
	<div class="col-md-4">
	<input type="checkbox" class="fbcomment content" 	name="socialnwchkbox" id="fbcomment" value="fbcomment"
			<s:if test="%{socialnwchkbox.contains('fbcomment') || configValues.isEmpty}">checked='checked'</s:if> />&nbsp;Facebook Commenting
	</div>
	<div class="col-md-4">
	<input type="checkbox" class="fbsend content" 	name="socialnwchkbox" value="fbsend"
			<s:if test="%{socialnwchkbox.contains('fbsend') || configValues.isEmpty}">checked='checked'</s:if> />&nbsp;Facebook Send
	</div>
	</div>
	
	
	<div class="section-header header-margins">Attending</div>
	
	<div class="row">
	<div class="col-md-7">
	<input type="checkbox" class="fbattending content" id="fbattending" name="socialnwchkbox" value="fbattending"
				<s:if test="%{socialnwchkbox.contains('fbattending')}">checked='checked'</s:if> />&nbsp;Facebook Attending, enter your Facebook event id&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="fbattendinginfo" title="Go to your Facebook event page, enter the number displayed after eid= in the browser URL box."></i>
	</div>
<div class="col-md-5">
<input type="text" class="form-control" name="facebookeventid" id="facebookeventid" value="<s:property value="facebookeventid"/>" />
</div>
<div id="fbeventidvalidationmessage"  style="display:none"></div>
	</div>
	
	
	<div class="row">
	<div class="col-md-4">
	<input type="checkbox" class="morfcount content" name="socialnwchkbox" value="gendercount"
				<s:if test="%{socialnwchkbox.contains('gendercount')}">checked='checked'</s:if> />&nbsp;Male/Female Count&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="genderinfo" title="One can see who is attending your event on gender basis with this option."></i>
	</div>
	<div class="col-md-6" id="fbiderrormsg" style="color:Red; display:none;">
	</div>
	</div>
	
	<div class="section-header header-margins">Share Box</div>
	<div class="row">
	<div class="col-md-12">
	<input type="checkbox" class="fbsharebox content" name="socialnwchkbox" value="sharepopup"
				<s:if test="%{socialnwchkbox.contains('sharepopup') || configValues.isEmpty}">checked='checked'</s:if> />&nbsp;Facebook Share Box in confirmation page&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="shareboxinfo" title="This box is displayed as soon as attendee completes the registration process, makes it easy for the attendee to share the event he/she attending on Facebook wall."></i>
	</div>
	</div>
	
	<div class="section-header header-margins">Facebook Login during Registration:</div>
	<div class="row">
	<div class="col-md-4">
	<input type="checkbox" class="fbloginbox content"	name="socialnwchkbox" value="fblogin"
				<s:if test='%{ntsEnable=="Y"}'>disabled="disabled"</s:if>
				<s:if test='%{socialnwchkbox.contains("fblogin") || configValues.isEmpty || ntsEnable=="Y" || regloginpopup=="N" }'>checked='checked'</s:if> />&nbsp;Facebook Login Box&nbsp;<i class="fa fa-info-circle"  style="cursor:pointer" id="fbloginboxinfo" title="This is mandatory for Network Ticket Selling events. "></i>
	</div>
	</div>
	
	</form>
	</div>
</div>
<br/>
<div id="previewbtn" class="previewbtn" style="display: none;">Preview</div>

			<div class="row" id="previewcontent">
			<br/><br/>
			<div id="colorprevid" style="text-align:center;height:auto !important;"></div>
			
			</div>


<script>
	$('.info').tooltipster({
    	maxWidth:'100px',
    	position:'right'
    });

	$('#previewbtn').on('click',function(){
if($(this).text()=='Preview'){
	 $('html, body').animate({scrollTop: $("#colorsntext").offset().top}, 1000);
	// $(this).text('Return');
}else{
	$('html, body').animate({scrollTop: $("#maincontent").offset().top}, 1000);
	//$(this).text('Preview');
}

		});

	

	$(window).scroll(function(){
		
		if( $('#colorsntext').length > 0 ) {

			if( $('#colorsntext').is_on_screen() ) { 
				$('#previewbtn').html('Preview');
			}

if($('#coloreventpreview').is_on_screen()){
	if($('div').offset().top<200)
	$('#previewbtn').html('Preview');
	else	
	$('#previewbtn').html('Return');
}
			
		}
		
	});



	$.fn.is_on_screen = function(){
	    var win = $(window);
	    var viewport = {
	        top : win.scrollTop(),
	        left : win.scrollLeft()
	    };
	    viewport.right = viewport.left + win.width();
	    viewport.bottom = viewport.top + win.height();
	     
	    var bounds = this.offset();
	    bounds.right = bounds.left + this.outerWidth();
	    bounds.bottom = bounds.top + this.outerHeight();
	     
	    return (!(viewport.right < bounds.left || viewport.left > bounds.right || viewport.bottom < bounds.top || viewport.top > bounds.bottom));
	     
	};
	

	
$('input#ebeethemeid').iCheck({  
	checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey'});

	$('input.themes').iCheck({  
		checkboxClass: 'icheckbox_square-grey', 
		radioClass: 'iradio_square-grey'});

$('input#mythemeid').iCheck({  
	checkboxClass: 'icheckbox_square-grey', 
	radioClass: 'iradio_square-grey'});

$('input.content').iCheck({
    checkboxClass: 'icheckbox_square-grey',
    radioClass: 'iradio_square-grey'
});
/* $('input.promotion').iCheck({
    checkboxClass: 'icheckbox_square-grey',
    radioClass: 'iradio_square-grey'
});
$('input.maps').iCheck({
    checkboxClass: 'icheckbox_square-grey',
    radioClass: 'iradio_square-grey'
});
$('input.attendee').iCheck({
    checkboxClass: 'icheckbox_square-grey',
    radioClass: 'iradio_square-grey'
}); */


$("select[name='theme']").addClass('form-control');
$("select[name='mytheme']").addClass('form-control');

getfields();

$('input.bsradiochk').on('ifChecked', function(event){
	getfields();
	});


$(".looknfeellbl").click(function(){
		$('.looknfeellbl').each(function(){
	           $(this).removeClass('btn-default').removeClass('btn-active');
	           $(this).addClass('btn-active');
			});
		$('#colorprevid').show();
		$('#previewbtn').show();
		 $('#'+$(this).attr('id')).addClass('btn-default').removeClass('btn-active');
		 if($(this).attr('id')=='fontslbl'){
			 $('#colorsntextdata').hide();
			 $('#fonts').show();
			 $('#widgets').hide();
			 }else if($(this).attr('id')=='colorslbl'){
				 $('#colorsntextdata').show();
				 $('#fonts').hide();
				 $('#widgets').hide();
				 }else if($(this).attr('id')=='widgetslbl'){
					 $('#colorprevid').hide();
						$('#previewbtn').hide();
					 $('#colorsntextdata').hide();
					 $('#fonts').hide();
					 $('#widgets').show();
					 }
});


$('input.bsradiochk').change(function() {
	var selectedRadio=$(this).attr('id');
    $(".optiontype").each(function() {
        $(this).removeClass('active'); 
        $(this).removeClass('btn-active'); 
        $(this).removeClass('btn-default'); 
    });
	if(selectedRadio=='themesradio'){
		$('#colorslbl').addClass('btn-default');
        $('#themelbl').addClass('btn-active');
        $('#htmlcsslbl').addClass('btn-default');
	}else if(selectedRadio=='colorntextradio'){
		$('#colorslbl').addClass('btn-active');
        $('#themelbl').addClass('btn-default');
        $('#htmlcsslbl').addClass('btn-default');
	}else{
		$('#colorslbl').addClass('btn-default');
        $('#themelbl').addClass('btn-default');
        $('#htmlcsslbl').addClass('btn-active');
		}
	getfields();
});


function getType(type){
	currentDivId=type;
	var	url='../membertasks/ImageUpload?purpose=looknfeel&type='+type;
		$('.modal-title').html('Image Upload');
		
		$('iframe#popup').attr("src",url);
		$('iframe#popup').css("height","250px"); 
		
		$('#myModal').modal('show');
	}


function getFontStyles(type){
	currentDivId=type;
	var url='LooknFeel!getFontStyles?eid='+_eid+'&purpose=looknfeel&type='+type;
	$('.modal-title').html('Font Styles');
	
	$('iframe#popup').attr("src",url);
	$('iframe#popup').css("height","250px"); 
	
	$('#myModal').modal('show');
}



function resizeIframe() {
	var element=document.getElementById("coloreventpreview");
	  element.style.height = element.contentWindow.document.body.scrollHeight + 'px';
	}



function getfields(){
	/* var colorntextchecked=document.getElementById('colorntextradio').checked;
	var themeschecked=document.getElementById('themesradio').checked;
	var htmlncsschecked=document.getElementById('htmlcssradio').checked; */
	var colorntextchecked=true;
	var themeschecked=false;
	var htmlncsschecked=false;
	//alert(htmlncsschecked);
		if(colorntextchecked){
			/* document.getElementById("colorntextcontent").style.display='block';
			document.getElementById("themescontent").style.display='none';
			document.getElementById("htmlcsscontent").style.display='none'; */
			//colorsTextPreview();
			loadEventPreview('color');     
		}else if(themeschecked){
			
			document.getElementById("colorntextcontent").style.display='none';
			document.getElementById("themescontent").style.display='block';
			document.getElementById("htmlcsscontent").style.display='none';
		//	getThemesContentPreview();
			loadEventPreview('themes');
		}else{

			$('#htmlcssradio').iCheck('check');
			HTMLnCSSblock();
			if('RSVP'=='${powertype}'){
				//getCCStatus('${eid}','LooknFeel_HtmlCssLoad','${powertype}','${currentlevel}','${currentfee}');
				specialFee('${eid}','150','LooknFeel_HtmlCssLoad','RSVP');
			}else 
				specialFee('${eid}','300','LooknFeel_HtmlCssLoad','Ticketing');
			
		}
}

</script>