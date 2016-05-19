<%@taglib uri="/struts-tags" prefix="s"%>
<%@include file="../getresourcespath.jsp"%>

<s:set name="menucontextH" value="#attr['menucontext']" />
  <s:set name="I18N_CODE" value="I18N_CODE"></s:set>
  <s:set name="I18N_CODE_PATH" value="I18N_CODE_PATH"></s:set>
  <s:set name="I18N_ACTUAL_CODE" value="I18N_ACTUAL_CODE"></s:set>
  
   
 

<!-- responsive navbar
===============================-->
<style>
#countries li.active a,#languages li.active a{
	color:#FF9900 !important;
}
</style>
<script src="<%=resourceaddress%>/main/js/i18n/<s:property value="I18N_CODE"/>/properties.js"></script>
<link type="text/css" rel="stylesheet" href="/main/css/select2.css" />

	<div
		style="background: none repeat scroll 0px 0px rgb(243, 246, 250); min-height: 91px;"
		class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button data-target=".navbar-collapse" data-toggle="collapse"
					class="navbar-toggle">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<br /> 
				<s:if test="%{#I18N_CODE_PATH != ''}">
				<a style="margin-bottom: -16px; margin-top: -10px;"
					class="navbar-brand" href="/<s:property value="I18N_ACTUAL_CODE"/>"><img alt="Eventbee"
					src="http://www.eventbee.com/main/images/logo.png" /></a>
				</s:if>
				<s:else>
				<a style="margin-bottom: -16px; margin-top: -10px;"
					class="navbar-brand" href="/"><img alt="Eventbee"
					src="http://www.eventbee.com/main/images/logo.png" /></a>
				</s:else>
				
				
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<s:if test="%{#I18N_ACTUAL_CODE != 'en-co' && #I18N_ACTUAL_CODE!='es-co' }">
					<li><a href="/main/pricing/<s:property value="I18N_ACTUAL_CODE"></s:property>"> <s:text name="la.pricing.features.link.lbl"></s:text>    </a></li>
					</s:if>
					<li><a href="/main/how-it-works/<s:property value="I18N_ACTUAL_CODE"></s:property>"> <s:text name="la.howitworks.link.lbl"></s:text>     </a></li>
					<!--<li><a href="/main/event-creation"> <s:text name="la.faq.link.txt"></s:text>   </a></li>-->
					<li><a href="/main/faq/<s:property value="I18N_ACTUAL_CODE"></s:property>">  <s:text name="global.faq.link.lbl"></s:text>   </a></li>
					<li><a id="contact" href="javascript:;">     <s:text name="la.contact.header.link"></s:text></a></li>
				</ul>
			  <ul class="nav navbar-nav navbar-right">
				 <li style="display:none">	
				  <div id="i18nLang" style="display:none;width:330px;position: absolute;top: 16px; right: 2px;  background-color: white;   padding: 20px 30px 20px 20px;   border: 1px solid #EEE;">
				     <div> 
				       <div style="float:left;padding-right:20px;border-right: 1px solid #EEE;">
				         <span class="sub-text"><s:text name="la.i18ncountries.lbl"/></span>
				         <ul style="padding:0px" id="countries" class="sm-font">
				          
				         </ul>				       
				       </div>
				        <div style="float:left;padding-left:20px">
				         <span class="sub-text"><s:text name="la.i18nlanguages.lbl"/></span>
					         <ul style="padding:0px;font-size:12px" class="sm-font" id="languages">
					           
					         </ul>					        
				        </div>   
				      </div>    
			       </div>		
				  <a href="javascript:;" id="i18nLangToggle" ><span style="margin-right:3px"><i class="fa fa-globe" style="color:#5388c4 !important; font-size:20px !important"></i></span><i class="fa fa-sort-down" style="font-size:14px !important;position: relative;top: -4px;"></i></a>
				  </li>
				  <s:if test="%{#I18N_ACTUAL_CODE != 'en-co' && #I18N_ACTUAL_CODE!='es-co' && #I18N_ACTUAL_CODE!='es-mx' && #I18N_ACTUAL_CODE!='en-mx'}">			  
				<li><a href="javascript:;" id="getTickets">   <s:text name="la.getmytickets.link.lbl"></s:text></a></li>
				</s:if>
				<%
					if (session.getAttribute("SESSION_USER") == null) {
				%>
                     
				<%
					if (session.getAttribute("SESSION_SUBMGR") == null) {
				%>
				<li><a href="/main/user/login?lang=<s:property value="I18N_ACTUAL_CODE"></s:property>">  <s:text name="global.login.link.lbl"></s:text>     </a></li>
				<li class="nav-btn" ><a href="/main/user/signup?lang=<s:property value="I18N_ACTUAL_CODE"></s:property>"><button
							class="btn btn-primary">    <s:text name="global.signup.btn.lbl"></s:text></button></a></li>
				
				<%
					} else {
				%>
				<li class="nav-btn"><a href="/main/submanager/logout?lang=<s:property value="I18N_ACTUAL_CODE"></s:property>"><button
							class="btn btn-primary"><s:text name="global.logout.btn.lbl"></s:text></button></a></li>
				<%
					}
				%>

				<%
					} else {
				%>
				<s:if test="%{#attr['basecontent']!='submgrlogin'}">
					<li><a href="/main/myevents/home"><s:text name="la.myaccount.link.lbl"></s:text></a></li>
					<li class="nav-btn"><a href="/main/user/logout?lang=<s:property value="I18N_ACTUAL_CODE"></s:property>"><button
								class="btn btn-primary"><s:text name="global.logout.btn.lbl"></s:text></button></a></li>
				</s:if>
				<%
					}
				%>
			</ul>
			</div>
		</div>
	</div>


<!-- modal dialog
===========================-->
<div class="col-md-12">
	<!-- Modal -->
	<div class="modal" id="myModal" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body">
					<iframe id="popup" src="" width="100%" style="height: 430px"
						frameborder="0"></iframe>
				</div>
			</div>
		</div>
	</div>
</div>
<script
	src="/main/bootstrap/js/jquery-1.11.2.min.js"></script>
	 <script src="/main/js/select2.js"></script>
<script>
/* var i18nLangJSON=[{"country":"USA","code":"US","languages":[{"label":"English","code":"en_US"}]},
                  {"country":"Colombia","code":"CO","languages":[{"label":"Español","code":"es_CO"},{"label":"Ingles","code":"en_CO"}]}	                 
                  ];

var I18N_ACTUAL_CODE='<s:property value="I18N_ACTUAL_CODE"></s:property>';
var lang=I18N_ACTUAL_CODE.split("_")[0];
var country=I18N_ACTUAL_CODE.split("_")[1]; */
	$(function() {			
		/*  var countryHTML="";
		var languagesHTML="";
		$.each(i18nLangJSON,function(index,value){
			if(country==value.code)
			 	 countryHTML+='<li class="active" id="index_'+index+'"><a title="'+value.country+'" href="javascript:;">'+value.country+'</a></li>';			
			 else
				 countryHTML+='<li  id="index_'+index+'"><a title="'+value.country+'" href="javascript:;">'+value.country+'</a></li>';
		}); */
		//$("#countries").html(countryHTML);
		
		//$(document).on("click","[id^='index']",function(){
			//countryClick($(this),false);
		//});
		/*$.each(i18nLangJSON,function(index,value){
			if(value.code==country)	
				countryClick($("#index_"+index),true);				
		}); */
		
		
		/* $("#i18nLangToggle").click(function(){
			if($("#i18nLang").is(":visible"))
				$("#i18nLang").hide('slow');
			else
				$("#i18nLang").show('slow');
		}); */
		
		$('#contact').click(function() {
							$('.modal-title').html(props.la_supp_popup_hdr);
							$('#myModal').on('show.bs.modal',
											function() {
												$('iframe#popup')
														.attr("src",
																'/main/user/homepagesupportemail.jsp');
												$('iframe#popup').css("height",
														"440px");
											});
							$('#myModal').modal('show');
						});
		$('#getTickets').click(	function() {
							$('.modal-title').html(props.la_get_tkts_popup_hdr);
							$('#myModal').on('show.bs.modal',
											function() {
												$('iframe#popup')
														.attr("src",
																'/main/user/homepagemytickets.jsp');
												$('iframe#popup').css("height",
														"435px");
											});
							$('#myModal').modal('show');
						});
		$('#myModal').modal({
			backdrop : 'static',
			keyboard : false,
			show : false
		});
		$('#myModal').on('hide.bs.modal',
						function() {
							$('iframe#popup').attr("src", '');
							$('#myModal .modal-body')
									.html(
											'<iframe id="popup" src="" width="100%" style="height:440px" frameborder="0"></iframe>');
						});
	});
	
	/* function countryClick(el,programClick){
		var index=el.attr('id').split("_")[1];	
		var languages=i18nLangJSON[parseInt(index)]['languages'];
		if(!programClick){
			languageClick(languages[0].code);
			return false;
		}
		el.addClass('active');
		el.siblings().removeClass('active');		
		var languagesHTML="";
		$.each(languages,function(index,value){
				if(I18N_ACTUAL_CODE==value.code)
					 languagesHTML+='<li class="active" style="cursor:pointer"><a title="'+value.label+'" onclick="languageClick(\''+value.code+'\')">'+value.label+'</a></li>';						
			    else
					 languagesHTML+='<li style="cursor:pointer"><a title="'+value.label+'" onclick="languageClick(\''+value.code+'\')">'+value.label+'</a></li>';							 
			});
		$("#languages").html(languagesHTML);
	} */
	
	function languageClick(code){		
		  var _url = location.href;
		  _url =removeURLParameter(_url,"lang");
		  _url += (_url.split('?')[1] ? '&':'?') + "lang="+code;
		  window.location.href=_url;
	} 
	
	function removeURLParameter(url, parameter) {
	    //prefer to use l.search if you have a location/link object
	    var urlparts= url.split('?');   
	    if (urlparts.length>=2) {

	        var prefix= encodeURIComponent(parameter)+'=';
	        var pars= urlparts[1].split(/[&;]/g);

	        //reverse iteration as may be destructive
	        for (var i= pars.length; i-- > 0;) {    
	            //idiom for string.startsWith
	            if (pars[i].lastIndexOf(prefix, 0) !== -1) {  
	                pars.splice(i, 1);
	            }
	        }

	        url= urlparts[0]+'?'+pars.join('&');
	        if(url.slice(-1)=="?")//remove if last character has ?
	        	url=url.substring(0, url.length - 1);
	        return url;
	    } else {
	    	 if(url.slice(-1)=="?")//remove if last character has ?
		        	url=url.substring(0, url.length - 1);
	        return url;
	    }
	}
</script>