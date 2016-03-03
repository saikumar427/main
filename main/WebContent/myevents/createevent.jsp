<%@taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<link rel="stylesheet" type="text/css" href="/main/css/listing.css" />
          <script type="text/javascript" src='http://maps.google.com/maps/api/js?sensor=false&libraries=places'></script>
    <script src="/main/bootstrap/js/locationpicker.jquery.js"></script>
    <script src="/main/bootstrap/js/jquery.timezone-picker.min.js"></script>
    <script src="/main/js/myevents/listing.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>
        <!-- Form
        ===========================-->
<style>
.main-content-body{
  background-color:white;
  margin-bottom:48px;
}
.my-custom-theme {
	border-radius: 5px; 
	border: 2px solid #5388C4;
	background: #fbfcfd;
	color: #000;
}
.my-custom-theme .tooltipster-content {
    /*font-family: Tahoma, sans-serif;*/
    width:100%;
	font-size: 14px;
	line-height: 16px;
	padding: 8px 10px;
	#opacity:0;
}

/* tooltip custom theme */
.form-validate-theme {
	border-radius: 5px; 
	border: 2px solid #ff4747;
	background: #ffffff;
	color: #000;
}
.form-validate-theme .tooltipster-content {
    /*font-family: Tahoma, sans-serif;*/
    width:100%;
	font-size: 14px;
	line-height: 16px;
	padding: 8px 10px;
}


.xdsoft_datetimepicker{
z-index: 800;
}

.xdsoft_datetimepicker .xdsoft_label
{z-index:700}


</style>
        
        <div class="container">
        <div id='eventerrors' class='alert alert-danger' style='display:none'></div>
         <s:form name="addeventform" cssClass="form-horizontal" id="myform" action="createevent!listevent" method="post" theme="simple" >
            <div class="row">
                <div class="col-md-11">
                   <h1><span class="section-main-header"><s:text name="ce.page.header"/></span></h1>
                   

<s:hidden name="state" id="hstate"></s:hidden>
<s:hidden name="eid" id="eventid"></s:hidden>
<s:hidden name="mgrId" id="managerid"></s:hidden>
<s:hidden name="addEventData.region" id="hregion"></s:hidden>

<s:hidden name="addEventData.startmonth" id="startmonth"></s:hidden>
<s:hidden name="addEventData.startyear" id="startyear"></s:hidden>
<s:hidden name="addEventData.startday" id="startday"></s:hidden>
<s:hidden name="addEventData.endmonth" id="endmonth"></s:hidden>
<s:hidden name="addEventData.endyear" id="endyear"></s:hidden>
<s:hidden name="addEventData.endday" id="endday"></s:hidden>


<s:hidden name="addEventData.endampm" id="endampm1"></s:hidden>
<s:hidden name="addEventData.endhour" id="endhour"></s:hidden>
<s:hidden name="addEventData.endminute" id="endminute"></s:hidden>
<s:hidden name="addEventData.stampm" id="stampm1"></s:hidden>
<s:hidden name="addEventData.startminute" id="startminute"></s:hidden>
<s:hidden name="addEventData.starthour" id="starthour"></s:hidden>
<s:hidden name="addEventData.description" id="msg"></s:hidden>
<s:hidden name="addEventData.descriptiontype" id="descriptiontype"></s:hidden> 

<s:set name="endate" value="paymentData.paypal_merchant_status"></s:set>
<div id="optlist"></div>

<s:hidden name="subcategory" id="hsub"></s:hidden>
                        <hr>
                        <div class="form-group">
                            <label for="title" class="col-sm-3 control-label" style="font-weight: normal !important"><s:text name="event.title.lbl"/><span class="required">*</span></label>
                            <div class="col-sm-7">
                            <s:textfield name="addEventData.eventTitle" id="title" cssClass="form-control"  />
                            </div>
                        </div>

                       <div class="form-group" style="display: none;">
 							<label for="description" class="col-sm-3 control-label"   style="font-weight: normal !important"><s:text name="event.description.lbl"/></label>
 							<div class="col-sm-7">								
                           
  								
                          <div class="btn-group  pull-right"> 
                          <s:set name="desctype" value="%{addEventData.descriptiontype}"></s:set>      
                            <label  id="selection_block" class="qtntype btn <s:if test="%{#desctype=='wysiwyg' || #desctype==''}">btn-default</s:if><s:else>btn-active</s:else>">
						      <input class="radiotype"  id="wysiwyg"  name="type" value="wysiwyg"  type="radio" <s:if test="%{#desctype=='wysiwyg'}">checked=true</s:if> style="visibility:hidden !important;margin: 0 !important;">&nbsp;
						      <span style="margin-left:-18px !important"><s:text name="event.description.btn.wysiwyg.lbl"/>&nbsp;</span>
						    </label>      
						    <label   id="textarea_block" class="qtntype btn <s:if test="%{#desctype=='html'}">btn-default</s:if><s:else>btn-active</s:else>">
						      <input class="radiotype" id="html" name="type" value="html"  type="radio" <s:if test="%{#desctype=='html'}">checked=true</s:if> style="visibility:hidden !important;margin: 0 !important;">&nbsp;
						      <span style="margin-left:-18px !important"><s:text name="event.description.btn.html.lbl"/>&nbsp;</span>
						    </label>                               
						    <label  id="text_block" class="qtntype btn <s:if test="%{#desctype=='text'}">btn-default</s:if><s:else>btn-active</s:else>">
						      <input class="radiotype" id="text" name="type" value="text" type="radio" <s:if test="%{#desctype=='text'}">checked=true</s:if> style="visibility:hidden !important;margin: 0 !important;">&nbsp;
						      <span style="margin-left:-18px !important"><s:text name="event.description.btn.text.lbl"/>&nbsp;</span> 
						    </label>			    
						   </div> 
						   
						  
                                                  
 							</div> 							
 						</div> 		
 						   
 						<div class="form-group">
 							<label for="description" class="col-sm-3 control-label"   style="font-weight: normal !important"><s:text name="event.description.lbl"/></label>
                            <div class="col-sm-7">
	                             <div id="fckdesctxtcontent" >                                                   		
										<s:textarea name="addEventData.textcontent" rows="15" cols="101" id="textmsg" cssClass="form-control"></s:textarea>									
	                            	<br>
	                            </div>
                               <div id="fckdesccontent">                          
									 <%@include file="description.jsp" %>						
                                 <br>
                               </div>                            
 						    </div>
 						</div>
 
 
                        <s:hidden name="addEventData.i18nLang" id="i18nLang"></s:hidden>
 						<%-- <div class="form-group">
                            <label for="hostname" class="col-sm-3 control-label"  style="font-weight: normal !important; margin-top: -12px;"><s:text name="ce.i18nlang.lbl"/><span class="required">*</span></label>
                            <div class="col-sm-7">
                                   <s:select  name="addEventData.i18nLang" id="i18nLang" cssClass="form-control country"
								list="i18nLangList" listKey="key" listValue="value" />
                                
                            </div>
                        </div> --%>

                       <div class="form-group">
                            <label for="hostname" class="col-sm-3 control-label"  style="font-weight: normal !important; margin-top: -12px;"><s:text name="event.host.lbl"/><span class="required">*</span></label>
                            <div class="col-sm-7">
                                
                                <s:textfield name="addEventData.name" cssClass="form-control"  id="hostname"  placeholder="%{getText('event.host.name.ph')}" />
                            </div>
                        </div>



                        <div class="form-group">
                            <label for="hostemail" class="col-sm-3 control-label"></label>
                            <div class="col-sm-7">
                                
                                  <s:textfield name="addEventData.email" cssClass="form-control"  id="hostemail"  placeholder="%{getText('event.host.email.ph')}"/>
                            </div>
                        </div>
                         
                        <div class="form-group">
                            <label class="col-sm-3 control-label"  style="font-weight: normal !important"><s:text name="global.registration.type.lbl"/><!-- &nbsp;<i class="fa fa-info-circle info" id="reginfo"></i> --></label>
                            <div class="col-sm-7">
                                <label class="radio-inline" style="margin-left: -19px;">
                                    <input type="radio" name='addEventData.powerWithType' id="tickets" value="ticketing" checked class="regtype" />
                                    <%-- <span  data-tooltiptext="Collect payment from attendee"> --%><s:text name="ce.registration.type.tickets.lbl"/>
                                </label>
                                <br>
                                <label class="radio-inline" style="margin-left: -19px;">
                                    <input type="radio" name="addEventData.powerWithType" id="rsvp" value="rsvp" class="regtypersvp" />
                                   <%--  <span  data-tooltiptext="No payment required from attendee"> --%><s:text name="ce.registration.type.rsvp.lbl"/>
                                </label>
                            </div>
                        </div>
                        
                        <br><br>
                        <h1><span class="section-main-header"><s:text name="event.where.section.header"/></span></h1>
                        <hr id="wherescroll">
                        
                        <!-- div form group -->                       
                        <div class="form-group">
                                            
                        <div class="col-md-1"></div>
                        
                        <div class="col-md-6"> 
                        <div id="searchblock">                       
                        <div class="form-group">
                          <!--   <label for="search" class="col-sm-3 control-label">Search (city, neighbourhood or zip)</label> -->
                             <div class="col-md-2"></div>
                             <div class="col-md-10">
                             <input type="text" name="search" id="search" class="form-control" id="title"  placeholder="<s:text name='event.search.venue.or.address.ph'/>"/>
                            </div>
                        </div>
                        </div>
                        <div style="display:none">
                        <div class="form-group">
                            <label for="map" class="col-sm-3 control-label"></label>
                            <div class="col-sm-7">
                                <div id="map" style="width:100%;height: 250px;"></div>
                            </div>
                        </div> 
                        </div>
                        <div class="form-group">
                            <!-- <label for="venue" class="col-sm-3 control-label">Venue</label> -->
                            <div class="col-md-2"></div>
                             <div class="col-md-10">                            
                                <s:textfield name="addEventData.venue"  id="venue"  cssClass="form-control venue" placeholder="%{getText('event.venue.ph')}"/>
                            </div>
                        </div>
                        <div class="form-group">
                           <!--  <label for="addr" class="col-sm-3 control-label">Address</label> -->
                            <div class="col-md-2"></div>
                             <div class="col-md-10">                            
                                <s:textfield name="addEventData.address1" id="addr"  cssClass="form-control addr" placeholder="%{getText('event.address.ph')}"/>
                            </div>
                        </div>
                        <div class="form-group">
                           <!--  <label for="city" class="col-sm-3 control-label">City&nbsp;*</label> -->
                           <div class="col-md-2"></div>
                             <div class="col-md-10">                         
                          <s:textfield name="addEventData.city" id="city"  cssClass="form-control city" placeholder="%{getText('event.city.ph')}*"/>
                            </div>
                        </div>
                        <div class="form-group">
                           <!--  <label for="country" class="col-sm-3 control-label">Country&nbsp;*</label> -->
                             <div class="col-md-2"></div>
                             <div class="col-md-10">
                                      <s:select label="Select Country" name="addEventData.country" id="country" headerKey="1"
										headerValue="%{getText('event.select.country.lbl')}" cssClass="form-control country"
										list="countrylist" listKey="key" listValue="value" onchange="getCountry();"/>
                            </div>
                        </div>
                        <div class="form-group">
                        	<div class="col-md-2"></div>
                             <div class="col-md-10">
                        	   <div class="form-group" style="float:left;width:55%;">
                        	 	 <div class="col-md-12">
                        	 	 	 <s:textfield name="addEventData.state" id="state"  cssClass="form-control state" placeholder="%{getText('event.state.ph')}"/>
                        	 	 </div>
                        	 	 </div>
                        	 	 <div class="form-group" style="float:right;width:55%;">
                        	 	 <div class="col-md-12">
                        	 	 	 <s:textfield name="addEventData.zip" id="zip"  cssClass="form-control zip" placeholder="%{getText('event.zip.ph')}"/>
                        	 	 </div>
                        	 	 </div>
                        	 	 <div style="clear:both;"></div>
                        	 </div>
                        </div>
                   <%--      <div class="row" style="margin-left: 76px;">                        
                        <div class="col-md-6">
                         
                         
                            <div style="padding-right: 5px;" class="col-sm-13">
                              <s:textfield name="addEventData.state" id="state"  cssClass="form-control state" placeholder="State"/>
                            </div>
                       
                        </div>
                         <div class="col-md-6">
                         
                        
                            <div class="col-sm-13">
                              
                               <s:textfield name="addEventData.zip" id="zip"  cssClass="form-control zip" placeholder="ZIP"/>
                            </div>
                        </div>
                                             
                        </div> --%>
                       <div class="form-group">
                       <div class="col-md-2"></div>
                             <div class="col-md-10">
                        <a href="javascript:;"  id="clearaddessr"><s:text name="event.clear.address"/></a>
                        </div>
                        </div>                        
                        </div> 
                        <div class="col-md-5">     
                        	  <iframe src="googlemap.jsp?lat=37.77655770352863&lng=-122.41147616131593" id="googlemap" style="height: 280px; width: 100%;border:0px;"></iframe>
                        	  <%--  <%@include file="googlemap.jsp" %>  --%>
                        </div>       
                       
                        </div>
                        
                        <!-- end form group -->
                        <input id="lat" type="hidden" name="lat" />
                        <input id="lon" type="hidden" name="lon" />
                            
                        <br><br>
                        <h1><span class="section-main-header"><s:text name="event.when.section.header"/></span></h1>
                        <hr>
                        <div id="timezoneselect">
                        
                        <div class="form-group" style="display:none;" id="timezonedata">
                            <label for="timezone" class="col-sm-3 control-label"><s:text name="event.time.zone.lbl"/><span class="required">*</span></label>
                            <div class="col-sm-7">
                            <s:select name="addEventData.timezones"  cssClass="form-control" id='timezone' headerKey="1"  list="timezones" listKey="key" listValue="value"  />

                            </div>
                            <%@include file="timezone.jsp" %>
                        </div>
                        </div>
                       

                        <div class="form-group">
                            <label for="hostname" class="col-sm-2 control-label"></label>
                             <input type='hidden' name="isRecurring" id="isRecurring" value="false"/>
                             <div class="col-md-2"></div>
                             <div class="col-md-10">
                              <span class="recurringtimes" style="display:none">
                              <div class="col-md-5">
									       <div class="row">
									               
		                               <div class="form-group">
		                                       <div style="padding-bottom:20px">
		                                               <div class="col-sm-5" >
		                                                       <input class="form-control" id="fromtime" type="text" name="fromtime" value="${addEventData.starthour}:${addEventData.startminute} AM"/>
		                                               </div>
		                                               <div class="col-sm-1" style="padding-top: 7px;"><s:text name="global.to.lbl"/></div>
		                                               <div class="col-sm-5">
		                                                      <input class="form-control" id="totime" type="text" name="totime" value="${addEventData.endhour}:${addEventData.endminute} AM"/>
		                                               </div>
		                                       </div>
									                              
									               </div>
									       </div>
									       
									       <div class="form-group">
									       <div style="padding-left:0px;">
									               <input class="form-control" id="date"  style="margin-left: 65px;width: 250px;" type="text" name="date" value='${addEventData.startmonth}/${addEventData.startday}/${addEventData.startyear}'/>
									       </div>
									       </div>
									        <!-- <div class='row'><div class="col-sm-10" id="recurchk" data-recur="yes"><a id="recurlink" style="cursor:pointer;text-decoration: none">Regular Event</a></div></div> -->
									</div>
									<div class="col-md-7"  style="padding-left: 0;">
									<div class="row"><div  class="col-md-6">									
										<div id="rectitle"><s:text name="event.schedule.lbl"/></div>
									</div></div>
									<div style="border:1px solid #ccc;height:100px" id="recheight">
									     <table  class="table">
									                       <thead>
									                       </thead> 
									                       <tbody id="recurringdates"></tbody>
									               </table>
									               </div>
									</div>
							             
								 </span>  	
								  <span class="nonrecurringtimes">
								  <div class="col-sm-8">
                                   <div class="row" >
                                   <div class="form-group">                                  		
										<div class="col-sm-5">
											 <input class="form-control" id="start" type="text" name="start" value='${addEventData.startmonth}/${addEventData.startday}/${addEventData.startyear} ${addEventData.starthour}:${addEventData.startminute} AM' />
										<div class="sub-text">&nbsp;<s:text name="global.mm.dd.yyyy.lbl"/></div>
										</div>
										<div class="col-sm-1" style="padding-top: 7px;"><s:text name="global.to.lbl"/></div>
										<div class="col-sm-5">
										<input class="form-control" id="end" type="text" name="end" value='${addEventData.endmonth}/${addEventData.endday}/${addEventData.endyear} ${addEventData.endhour}:${addEventData.endminute} AM' />
										<div class="sub-text">&nbsp;<s:text name="global.mm.dd.yyyy.lbl"/></div>
									</div>
									</div>
									</div>
								</div>
                                </span>
                                </div>
                        </div>
                         
                               <div class="form-group">
                                      <div class="col-md-2"></div>
				                            <div class="col-md-10" id="recurchk" data-recur="no"><a id="recurlink" style="cursor:pointer;text-decoration: none;"><s:text name="recurring.event.lbl"/></a></div> 
				                         </div>   
				                           
                         
                        <br><br>   
                </div> 
                           
            </div>
            <div class="row">
            <div class="col-md-11"> <hr></div></div><br>
            <div class="row">
            <div class="col-md-12">
                        <div >
                          <center>
                                <button type="submit" class="btn   btn-primary continue"><s:text name="global.continue.btn.lbl"/></button>                               
                           </center>
                        </div>
            </div>
            </div>
            </s:form>
            
            
        </div>
        <ol id="getlatlng" style="display:none"></ol> 
        <div class="modal" id="myModalhtmlin" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h3><label class='headlbl'></label></h3>
                        </div>
                        <div class="modal-body" id="timezonepopup">
                         </div>
                        <div class="modal-footer">
                           
                        </div>
                    </div>
                   
                </div>
                 <div class="modal-backdrop fade in"></div>
            </div>
        
        
    </body>
    
<script>
var address=[];
var venue='',addr='',city='',state='',country='',zip='';
$('#clearaddessr').click(function(){	
	$('#search').val('');
	 $('#searchblock').show();
	$('#venue').val('');
    $('#addr').val('');
    $('#city').val('');
    $('#state').val('');
    $('#zip').val('');
    $('#country').val('1');
   
});


function convert(text){
	 var chars = ["ò","δ","σ","Σ","ρ","ω","Ω","Ψ","Ο","ξ","Ξ","ν","μ","Λ","κ","ι","θ","Θ","η","ζ","π","☻","☺","♠","♣","♦","♥","©","Û","®","ž","Ü","Ÿ","Ý","Þ","%","¡","ß","¢","à","£","á","À","¤","â","Á","¥","ã","Â","¦","ä","Ã","§","å","Ä","¨","æ","Å","©","ç","Æ","ª","è","Ç","«","é","È","¬","ê","É","­","ë","Ê","®","ì","Ë","¯","í","Ì","°","î","Í","±","ï","Î","²","ð","Ï","³","ñ","Ð","´","ò","Ñ","µ","ó","Õ","¶","ô","Ö","·","õ","Ø","¸","ö","Ù","¹","÷","Ú","º","ø","Û","»","ù","Ü","@","¼","ú","Ý","½","û","Þ","€","¾","ü","ß","¿","ý","à","‚","À","þ","á","ƒ","Á","ÿ","å","„","Â","æ","…","Ã","ç","†","Ä","è","‡","Å","é","ˆ","Æ","ê","‰","Ç","ë","Š","È","ì","‹","É","í","Œ","Ê","î","Ë","ï","Ž","Ì","ð","Í","ñ","Î","ò","‘","Ï","ó","’","Ð","ô","“","Ñ","õ","”","Ò","ö","•","Ó","ø","–","Ô","ù","—","Õ","ú","˜","Ö","û","™","×","ý","š","Ø","þ","›","Ù","ÿ","œ","Ú"];
	  var codes = ["&ograve;","&delta;","&sigma;","&Sigma;","&rho;","&omega;","&Omega;","&Psi;","&Omicron;","&xi","&Xi;","&nu","&mu;","&Lambda;","&kappa","&iota;","&theta;","&Theta;","&eta;","&zeta;","&pi;","&#9787;","&#9785;","&spades;","&clubs;","&diams;","&hearts;","&copy;","&#219;","&reg;","&#158;","&#220;","&#159;","&#221;","&#222;","&#37;","&#161;","&#223;","&#162;","&#224;","&#163;","&#225;","&Agrave;","&#164;","&#226;","&Aacute;","&#165;","&#227;","&Acirc;","&#166;","&#228;","&Atilde;","&#167;","&#229;","&Auml;","&#168;","&#230;","&Aring;","&#169;","&#231;","&AElig;","&#170;","&#232;","&Ccedil;","&#171;","&#233;","&Egrave;","&#172;","&#234;","&Eacute;","&#173;","&#235;","&Ecirc;","&#174;","&#236;","&Euml;","&#175;","&#237;","&Igrave;","&#176;","&#238;","&Iacute;","&#177;","&#239;","&Icirc;","&#178;","&#240;","&Iuml;","&#179;","&#241;","&ETH;","&#180;","&#242;","&Ntilde;","&#181;","&#243;","&Otilde;","&#182;","&#244;","&Ouml;","&#183;","&#245;","&Oslash;","&#184;","&#246;","&Ugrave;","&#185;","&#247;","&Uacute;","&#186;","&#248;","&Ucirc;","&#187;","&#249;","&Uuml;","&#64;","&#188;","&#250;","&Yacute;","&#189;","&#251;","&THORN;","&#128;","&#190;","&#252","&szlig;","&#191;","&#253;","&agrave;","&#130;","&#192;","&#254;","&aacute;","&#131;","&#193;","&#255;","&aring;","&#132;","&#194;","&aelig;","&#133;","&#195;","&ccedil;","&#134;","&#196;","&egrave;","&#135;","&#197;","&eacute;","&#136;","&#198;","&ecirc;","&#137;","&#199;","&euml;","&#138;","&#200;","&igrave;","&#139;","&#201;","&iacute;","&#140;","&#202;","&icirc;","&#203;","&iuml;","&#142;","&#204;","&eth;","&#205;","&ntilde;","&#206;","&ograve;","&#145;","&#207;","&oacute;","&#146;","&#208;","&ocirc;","&#147;","&#209;","&otilde;","&#148;","&#210;","&ouml;","&#149;","&#211;","&oslash;","&#150;","&#212;","&ugrave;","&#151;","&#213;","&uacute;","&#152;","&#214;","&ucirc;","&#153;","&#215;","&yacute;","&#154;","&#216;","&thorn;","&#155;","&#217;","&yuml;","&#156;","&#218;"];
	  for(x=0; x<chars.length; x++){
	  text=text.replace(new RegExp(chars[x], 'g'),codes[x]);
	  }
	  return text;
	  }

var selectedtype='';

function reloadeditors(){
	var selectedtype = $('input[name="type"]:checked').val();
	//alert(selectedtype);
	 document.getElementById('descriptiontype').value=selectedtype;
	 
	 $(".qtntype").each(function() {
			$(this).removeClass('active'); 
			$(this).removeClass('btn-default'); 
			$(this).removeClass('btn-active'); 
		});
		if(selectedtype=='text'){
			$('#text_block').addClass('btn-default');
			$('#textarea_block').addClass('btn-active');
			$('#selection_block').addClass('btn-active');
		}else if(selectedtype=='html'){
			$('#text_block').addClass('btn-active');
			$('#textarea_block').addClass('btn-default');
			$('#selection_block').addClass('btn-active');
		}else{
			$('#text_block').addClass('btn-active');
			$('#textarea_block').addClass('btn-active');
			$('#selection_block').addClass('btn-default');
		}
		
	 if (selectedtype == 'text' || selectedtype == 'html') {
		//if (document.getElementById('fckdesccontent'))
	    document.getElementById('fckdesccontent').style.display = 'none';
		document.getElementById('fckdesctxtcontent').style.display = 'block';
	} else {
		document.getElementById('fckdesccontent').style.display = 'block';
		document.getElementById('fckdesctxtcontent').style.display = 'none';
		/* var niceeditorhtml = document.getElementById('msg').value;    			
		document.getElementById('editor').innerHTML=niceeditorhtml; */
	} 	
}

$(function(){
	 var value = props.ce_registration_type_help_msg;
		   $('#reginfo').tooltipster({
	            theme: 'my-custom-theme',
			    fixedWidth:'100px',
			        content: $('<span>'+value+'</span>'),
			    });
	var val = props.ce_recurring_help_msg;	
	 $('#reccinfo').tooltipster({
         theme: 'my-custom-theme',
		    fixedWidth:'100px',
		        content: $('<span>'+val+'</span>'),
		    });
	 
	 $('input.desctype').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
	 $('input.desctype').on('ifChecked', function(event){
		 reloadeditors1("addevent");
	    });
	 
	 
	 $('input.radiotype').change(function() {
		 reloadeditors();		
	 });
	   
			
});



function getCountry(){	
	//alert("getcountry");
		 address=[];
		 country= $( "#country" ).val();	
		 address.push( $( "#city" ).val());			
		 address.push(country);
		 if($("#addr").val()!='')
		 address.push($("#addr").val());
		 if($("#venue").val()!='')
		 address.push($("#venue").val());
		 if(address.length>0)
		 getLatLong($( "#city" ).val(),country,$("#venue").val(),$("#addr").val());
	
}

//

function getLatLong(city,country,venue,addr){	
	var res='',fulladdress='',venue1='',addr1='';
	if(venue!='' || addr!=''){
		if(venue!=''){
			if(venue.indexOf(',')>-1){
				var v=venue.split(',');				
				for(var i=0;i<v.length;i++){
					venue1+=v[i]+',';
				}
			}else{
				venue1=venue;
			}
			fulladdress = city+","+country+","+venue1+","+addr;
			fulladdress=fulladdress.replace(/ /g, "+");			
		}else{
			if(addr.indexOf(',')>-1){
				var v=addr.split(',');
				for(var j=0;j<v.length;j++){
					addr1+=v[j]+',';
				}
			}else{
				addr1=addr;
			}
			fulladdress = city+","+country+","+addr1+","+venue;
			fulladdress=fulladdress.replace(/ /g, "+");				
		}
	}else {
		fulladdress = city+","+country;		
	}
	
    
	if($( "#city" ).val()=='' || $( "#country" ).val()==''){
	jQuery('#gettimezone').load('https://maps.googleapis.com/maps/api/geocode/json?address='+fulladdress,
	            function(response, status, xhr){
		        res=JSON.parse(response);	            
	            $('#googlemap').attr('src','googlemap.jsp?lat=' +res.results[0].geometry.location.lat+'&lng='+res.results[0].geometry.location.lng);
	           
	            calGettimzone(res.results[0].geometry.location.lat,res.results[0].geometry.location.lng);
	  }); 
	  
	}else{	  
	   jQuery.ajax({
		  url : '/main/myevents/populateMap.jsp',
	      type : 'POST',
	      data : {address:$("#addr").val(),venue:$("#venue").val(),city:$("#city").val(),state:$("#state").val(),country:$("#country").val()},
	      success : function(t){
	    	$('#googlemap').attr('src','googlemap.jsp?lat='+t.lat+'&lng='+t.lng);
	    	  calGettimzone(t.lat,t.lng);
	      }
	  }); 
	}
}



 function latlongDefault(){
	var ip = '<%=request.getHeader("x-forwarded-for")==null?request.getRemoteAddr():request.getHeader("x-forwarded-for")%>';
	 jQuery('#getlatlng').load('http://freegeoip.net/json/'+ip,
	            function(response, status, xhr){
		 try{
						  var res=JSON.parse(response);
						  $('#googlemap').attr('src','googlemap.jsp?lat='+res.latitude+'&lng='+res.longitude);
		 }catch(err){}
		 
	 });
} 

latlongDefault();
reloadeditors();
</script>
</html>