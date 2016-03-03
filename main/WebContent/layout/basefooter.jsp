<%@taglib uri="/struts-tags" prefix="s"%>
<%@include file="../getresourcespath.jsp"%>
<style>
.footertabheader{
       color: #B0B0B0;
       font-family: 'Open Sans', sans-serif;
}
.footerlinks a, .footertab a{
         color: #ccc;
         font-size: 12px;
         line-height: 200%;
}
.top-gap{
	margin-top:7px;
}
.select-active{
	background:#ddd !important;
	color:#000 !important;
}
.select-active:hover{
	background:#ddd !important;
	color:#000 !important;
}
#rootDiv a:hover {
   text-decoration: underline !important;
}

.footerlinks a:hover {
   text-decoration: underline !important;
}
</style>

<s:set name="I18N_CODE" value="I18N_CODE"></s:set>
<s:set name="I18N_CODE_PATH" value="I18N_CODE_PATH"></s:set>
<s:set name="I18N_ACTUAL_CODE" value="I18N_ACTUAL_CODE"></s:set>
<script src="<%=resourceaddress%>/main/js/i18n/<s:property value="I18N_ACTUAL_CODE"/>/footerjson.js?id=1"></script>

<script>

function fillHTML(){
	var html="<br>";
	for(var i=0;i<i18nFooter.sections.length;i++){
		var eachSection=i18nFooter.sections[i];
		html+='<div class="col-md-'+eachSection.grids+'">';
		
		
		
		for(var j=0;j<eachSection.subsections.length;j++){
			var eachSubSection=eachSection.subsections[j];
			
			if(eachSubSection.title=='search'){
				html+='<span id="dropDown"></span>';
			}else{

				html+='<span class="footertabheader">'+ 
							'<h4>'+ 
									'<strong>'+eachSubSection.title+'</strong>'+
							
							'</h4>'+
						'</span>';
				for(var k=0;k<eachSubSection.sublinks.length;k++){
					var eachSubLink=eachSubSection.sublinks[k];
					
					if(eachSubLink.type=="text"){
						
						 if(eachSubLink.strong)
		  	                  html+=' <strong>';
						  if(eachSubLink.i18n_include==false)
							  html+='<span class="footertab"><a href="'+eachSubLink.href+'"';
						else	  
						   	 html+='<span class="footertab"><a href="'+eachSubLink.href+'/<s:property value="I18N_ACTUAL_CODE"/>"';
						     if(eachSubLink.target)
			  	                  html+=' target="'+eachSubLink.target+'"';
					    	   html+='>';
						   	if(eachSubLink.limg)
			  	                  html+=eachSubLink.limg;
						   	html+=eachSubLink.label+'</a></span>';
						   	
						    if(eachSubLink.strong)
			  	                  html+=' </strong>';
						   	
						    html+='<br/>';
			  	                
					}
				    else if(eachSubLink.type=="img"){
				    	html+='<a href="'+eachSubLink.href+'"'; 
						    	if(eachSubLink.target)
			  	                  html+=' target="'+eachSubLink.target+'"';
					    	  html+='>'+
				    				'<img src="'+eachSubLink.src+'"';
					    					if(eachSubLink.width)
					    	                  html+=' width="'+eachSubLink.width+'"';
					    					if(eachSubLink.class_name)
						    	                  html+=' class="'+eachSubLink.class_name+'"';
				    	            html+='/>'+
				    		        '</a> <br/>';
				    }
				}
					
			}	
	
			}		
		html+='</div><!--grid div close -->';
		document.getElementById("rootDiv").innerHTML=html;
	}
}
</script>

<!-- full width container footer -->
<div class="container" style="background-color: #474747; width: 100%;">
	<div class="container footer">
		<s:if test="%{I18N_ACTUAL_CODE=='en-us'}">
		
			<div class="row" style="margin: 0 auto; padding-bottom: 10px;">
		 </s:if>
		
		 <%if(session.getAttribute("SESSION_USER")==null && session.getAttribute("SESSION_SUBMGR")==null) { %>
		 
		<s:if test="%{#I18N_ACTUAL_CODE=='es-mx' || #I18N_ACTUAL_CODE=='es-co'}">
		
		<div class="col-md-9 col-sm-9 " style="margin: 0 auto; padding-bottom: 10px;">
		
		
		 </s:if>
		 <% } else{
			 
		 } %>
			<div class="row" id="rootDiv">
				
				
			</div>
			
		</div>
		 <%if(session.getAttribute("SESSION_USER")==null && session.getAttribute("SESSION_SUBMGR")==null) { %>
		<s:if test="%{#I18N_ACTUAL_CODE=='es-mx' || #I18N_ACTUAL_CODE=='es-co'}">
		<div class="col-md-3 col-sm-3 " style="margin-top:16px;">
			<a><select style="margin-bottom: 25px;width:200px;height:36px !important;line-height:35px !important;margin-top:10px !important;padding: 0px !important;" id="states" onchange="languageClick(value)"> 
           <!-- <option value="en-co" <s:if test="%{I18N_ACTUAL_CODE=='en-co'}">selected='selected' class="select-active" </s:if>>Colombia - English</option>   -->
		   <option value="es-co" <s:if test="%{I18N_ACTUAL_CODE=='es-co'}">selected='selected' class="select-active" </s:if>>Colombia - Spanish</option>               
		   <!--  <option value="en-mx" <s:if test="%{I18N_ACTUAL_CODE=='en-mx'}">selected='selected' class="select-active"</s:if>>Mexico - English</option> -->
		   <option value="es-mx" <s:if test="%{I18N_ACTUAL_CODE=='es-mx'}">selected='selected' class="select-active"</s:if>>Mexico - Spanish</option>   
		    <option value="en-us" <s:if test="%{I18N_ACTUAL_CODE=='en-us'}">selected='selected' class="select-active"</s:if>>United States - English</option>  
		   <!--  </optgroup> -->
        </select> 
           </a> 
		</div>
		</s:if>
			 
	  <script>
        $(document).ready(function() {
            $("#states").select2();   
        });
    </script>
    <style>
   .select2-container .select2-choice > .select2-chosen { text-align: center;
    padding-left: 25px;}
    </style>
    <s:if test="%{I18N_ACTUAL_CODE=='es-co'}">
      <style>
   .select2-chosen{
   	 background-image: url("<%=resourceaddress%>/main/images/flags/flag_colombia.png");
   	 background-repeat:no-repeat;
   	 background-size:25px 27px;
   	 background-position:center left;
   }
    </style>
    </s:if>
    <s:if test="%{I18N_ACTUAL_CODE=='es-mx'}">
    <style>
   .select2-chosen{
   	 background-image: url("<%=resourceaddress%>/main/images/flags/flag_mexico.png");
   	 background-repeat:no-repeat;
   	 background-size:25px 27px;
   	 background-position:center left;
   }
    </style>
    </s:if>
    
    <s:if test="%{I18N_ACTUAL_CODE=='en-us'}">
    <style>
   .select2-chosen{
   	 background-image: url("<%=resourceaddress%>/main/images/flags/flag_united_states.png");
   	 background-repeat:no-repeat;
   	 background-size:25px 27px;
   	 background-position:center left;
   }
    </style>
    </s:if>
		<div class="row" style="margin: 0 auto; padding-bottom: 0px;">
          
                 
                   	<div class="col-md-2">&nbsp; </div>
					<div class="col-md-4"> &nbsp;</div>
					<div class="col-md-3">&nbsp; </div>
					<div class="col-md-3">
					<span style="display:none;">
						<s:if test="%{I18N_ACTUAL_CODE=='en-us'}">
						<a style="position:relative;top:-100px;left: 8px;"><select style="margin-bottom: 25px;width:200px;height:36px !important;line-height:35px !important;margin-top:10px !important;padding: 0px !important;" id="states" onchange="languageClick(value)"> 
						<!-- <option value="en-co" <s:if test="%{I18N_ACTUAL_CODE=='en-co'}">selected='selected' class="select-active" </s:if>>Colombia - English</option>   -->
						<option value="es-co" <s:if test="%{I18N_ACTUAL_CODE=='es-co'}">selected='selected' class="select-active" </s:if>>Colombia - Spanish</option>               
						<!--  <option value="en-mx" <s:if test="%{I18N_ACTUAL_CODE=='en-mx'}">selected='selected' class="select-active"</s:if>>Mexico - English</option> -->
						<option value="es-mx" <s:if test="%{I18N_ACTUAL_CODE=='es-mx'}">selected='selected' class="select-active"</s:if>>Mexico - Spanish</option>   
						 <option value="en-us" <s:if test="%{I18N_ACTUAL_CODE=='en-us'}">selected='selected' class="select-active"</s:if>>United States - English</option>  
						<!--  </optgroup> -->
						</select> 
						</a> 
						</s:if>
          		 </span>
        </div>
        </div>
	<% } else{} %>
	</div>
</div>
<hr
	style="margin: 0; background-color: #606060; height: 1px; border-top: 1px solid #282828;">
<div class="container" style="background-color: #474747; width: 100%;">
	<div class="container footer">
		<div class="row" style="margin: 0 auto; padding-top: 15px;">
			<div class="row">
				<center>
					<span style="font-size: 12px; color: #ccc"> <s:text name="la.copyrisght.lbl"></s:text> </span>
				</center>
				<span class="footerlinks" style="font-size: 0.7em">
					<center>
						<a href="/main/privacystatement/<s:property value="I18N_ACTUAL_CODE"/>"><s:text name="la.privacy.link.lbl"></s:text></a> <span style="color: #ccc"> |</span>   <a
							href="/main/termsofservice/<s:property value="I18N_ACTUAL_CODE"/>"><s:text name="la.termsofservice.link.lbl"></s:text></a>
					</center>
				</span>
				<center>
					<span style="font-size: 12px; color: #ccc"><s:text name="la.trustedby.lbl"></s:text></span>
				</center>
			</div>
			<br />
		</div>
	</div>
</div>
<script>
$('#states').click(function(){
	$('#disled').css('pointer-events','none');
	});
fillHTML();
</script>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-60215903-1', 'auto');
  ga('send', 'pageview');
</script>





