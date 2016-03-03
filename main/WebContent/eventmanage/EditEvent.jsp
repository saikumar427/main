<%@taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="/main/css/listing.css" />
<script type="text/javascript" src='http://maps.google.com/maps/api/js?sensor=false&libraries=places'></script>
<script src="../bootstrap/js/locationpicker.jquery.js"></script>
<script src="../bootstrap/js/jquery.timezone-picker.min.js"></script>
<script src="../js/eventmanage/editlisting.js?timestamp=<%=(new java.util.Date()).getTime()%>"></script>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<script>
var seeRecurring = '${seeRecurring}';
var eventid = '${eid}';
</script>

<script>
var stWeekDay=getWeekDay('<s:property value="%{editEventData.startyear}"/>/<s:property value="%{editEventData.startmonth}"/>/<s:property value="%{editEventData.startday}"/>')+", "+getDateFormat('<s:property value="%{editEventData.startyear}"/>/<s:property value="%{editEventData.startmonth}"/>/<s:property value="%{editEventData.startday}"/>');
var endWeekDay=getWeekDay('<s:property value="%{editEventData.endyear}"/>/<s:property value="%{editEventData.endmonth}"/>/<s:property value="%{editEventData.endday}"/>')+", "+getDateFormat('<s:property value="%{editEventData.endyear}"/>/<s:property value="%{editEventData.endmonth}"/>/<s:property value="%{editEventData.endday}"/>');
</script>

<%-- <div class="row" style="margin-left:0px;">
    <div style="float:left"><a href="Snapshot?eid=${eid}"> <i class="glyphicon glyphicon-chevron-left"></i><s:text name="ee.back.to.summary.lbl"/></a></div>
</div> --%>
<s:if test="%{subMgr==null || (subMgr!=null &&  (submgr_permissions['EditEvent']=='yes') || submgr_permissions['EventPageContent']=='yes')}">
<s:if test="%{subMgr==null || (subMgr!=null &&  submgr_permissions['EditEvent']=='yes')}">

<div id="editEventDIV">

<div id="eventerrors"  class='alert alert-danger' style='display:none'>
<s:if test="%{errorsExists==true}">
<s:fielderror/>
</s:if>
</div>
 <s:form name="editeventform"  id="editeventform" cssClass="form-horizontal" action="EditEvent!update" method="post">
	<s:hidden name="eid" id="eventid"></s:hidden>
	<s:hidden name="editEventData.mgrId" id="managerid"></s:hidden>
	<s:hidden name="subcategory" id="hsub"></s:hidden>
	<s:hidden name="editEventData.region" id="hregion"></s:hidden>
	<!--<s:hidden name="editEventData.description" id="descmsg"></s:hidden>-->

<div class="section-main-header"><s:text name="global.event.lbl"/></div>
<div class="row sticky-out-button pull-right">
	<div class="col-md-12"><button type="button" class="btn btn-primary" id="edit_title_section"><s:text name="global.edit.btn.lbl"/></button></div>
</div>
<div class="white-box">
	<div id="titleSectionStatic" class="toggleTitleSection">
	
		<div class="form-group">
            <div class="col-md-10 col-sm-7"><span id="titleStatic" style="font-size: 18px;"><s:property value="%{editEventData.eventTitle}"/></span></div>
        </div>
        <div class="form-group">
            <div class="col-md-10 col-sm-7 table-responsive"><span id="descStatic"></span></div>
        </div>
	</div>
	<div style="display:block;" id="titleSection" class="toggleTitleSection">
		<s:hidden name="editEventData.descriptiontype" id="descriptiontype"></s:hidden>
		<s:hidden name="editEventData.description" id="msg"></s:hidden>
		<div class="form-group">
	        <label for="title" class="col-sm-3 control-label"><s:text name="event.title.lbl"/><span class="required">*</span></label>
	        <div class="col-sm-7">
	        <s:textfield name="editEventData.eventTitle" id="title" cssClass="form-control"  />
	        </div>
    	</div>
         
		<div class="form-group" style="display: none;">
			<label for="description" class="col-sm-3 control-label"><s:text name="event.description.lbl"/></label>
			<div class="col-sm-7">
				<div class="row">
					<div class="col-md-12">
						<div class="btn-group pull-right"> 
							<s:set name="desctype" value="%{editEventData.descriptiontype}"></s:set>
							<label  id="selection_block" class="qtntype btn <s:if test="%{#desctype=='wysiwyg' || #desctype==''}">btn-default active</s:if><s:else>btn-active</s:else>">
								<input class="radiotype combo-radio-btn"  id="wysiwyg"  name="type" value="wysiwyg"  type="radio" <s:if test="%{#desctype=='wysiwyg'}">checked=true</s:if>>&nbsp;
								<span class="combo-radio-label"><s:text name="event.description.btn.wysiwyg.lbl"/>&nbsp;</span>
							</label>  
							<label   id="textarea_block" class="qtntype btn <s:if test="%{#desctype=='html'}">btn-default active</s:if><s:else>btn-active</s:else>">
								<input class="radiotype combo-radio-btn" id="html" name="type" value="html"  type="radio" <s:if test="%{#desctype=='html'}">checked=true</s:if>>&nbsp;
								<span class="combo-radio-label"><s:text name="event.description.btn.html.lbl"/>&nbsp;</span>
							</label>                                        
							<label  id="text_block" class="qtntype btn <s:if test="%{#desctype=='text'}">btn-default active</s:if><s:else>btn-active</s:else>">
								<input class="radiotype combo-radio-btn" id="text" name="type" value="text" type="radio" <s:if test="%{#desctype=='text'}">checked=true</s:if>>&nbsp;
								<span class="combo-radio-label"><s:text name="event.description.btn.text.lbl"/>&nbsp;</span> 
							</label>
					  	</div> 
					  </div>
                </div>
			</div>
		</div> 		
 						
 						
 						<div class="form-group">
 						 <label for="title" class="col-sm-3 control-label"><s:text name="event.description.lbl"/></label>
                            <div class="col-sm-7">
                             <div id="fckdesctxtcontent" >                                                   		
									<s:textarea name="editEventData.textcontent" rows="15" cols="101" id="textmsg" cssClass="form-control"></s:textarea>									
                            	<br>
                            </div>
                            <div id="fckdesccontent">                          
									 <%@include file="/myevents/description.jsp" %>							
                                 <br>
                               </div>                            
 						</div></div> 
                        
                        <div class="center">
                            <span>
                                <input type="button"  class="btn btn-primary submitBtn" value="<s:text name="global.save.btn.lbl"/>" /> 
                                <button type="button" class="btn btn-cancel cancel_title_section"><i class="fa fa-times"></i></button>                        
                            </span>
                        </div>
	</div>                        
</div>
                       
<div class="section-main-header box-top-gap"><s:text name="event.when.section.header"/></div>
<div class="row sticky-out-button pull-right">
	<div class="col-md-12"><button type="button" class="btn btn-primary" id="edit_when_section"><s:text name="global.edit.btn.lbl"/></button></div>
</div>

<div class="white-box">
<div id="whenSectionStatic" class="toggleWhenSection">
   	<div class="form-group">
		<div class="col-md-10">
			 <span id="startStatic">
			 	<span id="stWeekDay"></span>,
			 	<span id="stWeekDayTm">
					 <s:property value="%{editEventData.startHour}"/>:
					 <s:property value="%{editEventData.startminute}"/>
					 <s:property value="%{editEventData.startampm}"/>
				</span>
			 </span>&nbsp;<s:text name="event.when.start.lbl"/>
		</div>
	</div>
	<div class="form-group">
		<div class="col-md-10">
			<span id="endStatic">
			<span id="endWeekDay"></span>,
			 <s:property value="%{editEventData.endHour}"/>:
			 <s:property value="%{editEventData.endminute}"/>
			 <s:property value="%{editEventData.endampm}"/>
			</span>&nbsp;<s:text name="event.when.end.lbl"/>
		</div>
	</div>
</div>
	<div id="whenSection" style="display:none;" class="toggleWhenSection">
	
		<s:hidden name="editEventData.startmonth" id="startmonth"></s:hidden>
		<s:hidden name="editEventData.startyear" id="startyear"></s:hidden>
		<s:hidden name="editEventData.startday" id="startday"></s:hidden>
		<s:hidden name="editEventData.endmonth" id="endmonth"></s:hidden>
		<s:hidden name="editEventData.endyear" id="endyear"></s:hidden>
		<s:hidden name="editEventData.endday" id="endday"></s:hidden>
		
		<s:hidden name="editEventData.endampm" id="endampm1"></s:hidden>
		<s:hidden name="editEventData.endHour" id="endhour"></s:hidden>
		<s:hidden name="editEventData.endminute" id="endminute"></s:hidden>
		<s:hidden name="editEventData.startampm" id="stampm1"></s:hidden>
		<s:hidden name="editEventData.startminute" id="startminute"></s:hidden>
		<s:hidden name="editEventData.startHour" id="starthour"></s:hidden>
		
		<s:hidden name="seeRecurring" id="isRecurring"></s:hidden>
		<div id="optlist"></div>
		<div class="form-group">
		    <label for="hostname" class="col-sm-2 control-label"></label>
		    <div class="col-md-2"></div>
		    <div class="col-md-10">
		        <span class="nonrecurringtimes">
                      <div class="col-sm-8">
                         <div class="row">
	                         <div class="form-group">
								<div class="col-sm-5">
									 <input class="form-control" id="start" type="text" name="start" value='${editEventData.startmonth}/${editEventData.startday}/${editEventData.startyear} ${editEventData.startHour}:${editEventData.startminute} ${editEventData.startampm}'/>
								<div class="sub-text">&nbsp;<s:text name="global.mm.dd.yyyy.lbl"/></div>
								</div>
								<div class="col-sm-1 label-to" ><s:text name="global.to.lbl"/></div>
								<div class="col-sm-5">
									<input class="form-control" id="end" type="text" name="end" value='${editEventData.endmonth}/${editEventData.endday}/${editEventData.endyear} ${editEventData.endHour}:${editEventData.endminute} ${editEventData.endampm}' />
								<div class="sub-text">&nbsp;<s:text name="global.mm.dd.yyyy.lbl"/></div>
								</div>
							</div>
						</div>
					</div>
				</span>
		
		<span class="recurringtimes" style="display:none">
			<div class="col-md-5">
			  <div class="row">
			   <div class="form-group">
				<div class="recurring-time">
					<div class="col-sm-5" >
						   <input class="form-control" id="fromtime" type="text" name="fromtime" value="${editEventData.startHour}:${editEventData.startminute} ${editEventData.startampm}" />
					</div>
					<div class="col-sm-1 label-to"><s:text name="global.to.lbl"/></div>
					<div class="col-sm-5">
						<input class="form-control" id="totime" type="text" name="totime" value="${editEventData.endHour}:${editEventData.endminute} ${editEventData.endampm}" />
					</div>
				</div>
				</div>
				</div>
				<div class="form-group">
					<div class="recurring-date">
					     <input class="form-control recurring-date-picker" id="date" type="text" name="date" value="${editEventData.startmonth}/${editEventData.startday}/${editEventData.startyear}" />
					</div>
				</div>
			</div> 
            <div class="col-md-7 recurring-schedule">
	            <div class="row"><div  class="col-md-6">									
				<div id="rectitle"><s:text name="event.schedule.lbl"/></div>
				</div></div>
				<div style="border:1px solid #ccc;height:100px" id="recheight">
			        <table class="table">
			            <thead></thead>
			            <tbody id="recurringdates"></tbody>
			        </table>
			    </div>
			</div> 
		</span>
	</div>
	</div>
    <div class="form-group">
    	<div class="col-md-2"></div>
      	<div class="col-md-10" id="recurchk" data-recur="no"><a id="recurlink" style="cursor:pointer;text-decoration: none"><s:text name="recurring.event.lbl"/></a></div> 
    </div>

    <div class="center">
        <span>
            <input type="button"  class="btn btn-primary submitBtn" value="<s:text name="global.save.btn.lbl"/>" />  
            <button type="button" class="btn btn-cancel cancel_when_section"><i class="fa fa-times"></i></button>                        
        </span>
    </div>
          
	</div>                        
</div>       

<!-- <hr id="wherescroll"> -->
<div class="section-main-header box-top-gap"><s:text name="event.where.section.header"/></div>
<div class="row sticky-out-button pull-right">
	<div class="col-md-12"><button type="button" class="btn btn-primary" id="edit_where_section"><s:text name="global.edit.btn.lbl"/></button></div>
</div>

<div class="white-box">
	<div id="whereSectionStatic" class="toggleWhereSection">
		
		 <div class="form-group" id="staticVenDiv" style="<s:if test="%{editEventData.venue!=''}">display:block</s:if><s:else>display:none</s:else>">
               <div class="col-md-10"><span id="venueStatic"><s:property value="%{editEventData.venue}"/></span></div>
           </div>
           <div class="form-group" id="staticAddrDiv" style="<s:if test="%{editEventData.address1!=''}">display:block</s:if><s:else>display:none</s:else>">
               <div class="col-md-10"><span id="addrStatic"><s:property value="%{editEventData.address1}"/></span></div>
           </div>
           <div class="form-group">
           
           		<div class="col-md-12">
           			<span id="city_state_country">
           			<s:property value="%{editEventData.city}"/>,&nbsp;
           			<s:if test="%{editEventData.state!=''}"><s:property value="%{editEventData.state}"/>,&nbsp;</s:if>
           			<s:property value="%{editEventData.country}"/>
           			</span>
           		</div>
           		
               <%-- <div class="col-md-1"><span id="cityStatic">c</span>,</div>
               
               <div class="col-md-1" id="staticStateDiv" style="<s:if test="%{editEventData.state!=''}">display:block</s:if><s:else>display:none</s:else>"><span id="stateStatic"><s:property value="%{editEventData.state}"/></span>,</div>
               
               <div class="col-md-1"><span id="countryStatic"><s:property value="%{editEventData.country}"/></span></div> --%>
           </div>
	</div>

	<div id="whereSection" style="display:none;" class="toggleWhereSection">
		<s:hidden name="state" id="hstate"></s:hidden>
		<s:hidden name="editEventData.latitude" id="latitude"></s:hidden>
		<s:hidden name="editEventData.longitude" id="longitude"></s:hidden>
		
        <div class="form-group">
            <div class="col-md-1"></div>
			<div class="col-md-6">
				<div id="searchblock">                       
                     <div class="form-group">
                          <div class="col-md-2"></div>
                          <div class="col-md-10">
                             <s:textfield name="search" id="search" cssClass="form-control" placeholder="%{getText('event.search.venue.or.address.ph')}"/>
                         </div>
                     </div>
                </div>
                <div style="display:none">
                <div class="form-group">
                    <label for="map" class="col-sm-3 control-label"></label>
                    <div class="col-sm-7">
                        <div id="map" class="google-map-control"></div>
                    </div>
                </div> 
                </div>                      
                <div class="form-group">
                  <div class="col-md-2"></div>
                    <div class="col-md-10">
                        <s:textfield name="editEventData.venue"  id="venue"  cssClass="form-control" placeholder="%{getText('event.venue.ph')}"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-2"></div>
                    <div class="col-md-10">
                        <s:textfield name="editEventData.address1" id="addr"  cssClass="form-control" placeholder="%{getText('event.address.ph')}"/>
                    </div>
                </div>
                <div class="form-group">
                   <div class="col-md-2"></div>
                    <div class="col-md-10">
                  <s:textfield name="editEventData.city" id="city"  cssClass="form-control" placeholder="%{getText('event.city.ph')}*"/>
                    </div>
                </div>
                <div class="form-group">
                     <div class="col-md-2"></div>
                     <div class="col-md-10">
                              <s:select label="Select Country" name="editEventData.country" id="country" headerKey="1"
								headerValue="%{getText('event.select.country.lbl')}" cssClass="form-control country"
								list="countrylist" listKey="key" listValue="value" onchange="getCountry();"/>
                    </div>
                </div>                       
                <div class="form-group">
                    <div class="col-md-2"></div>
                    <div class="col-md-10">
                      <div class="form-group state-control">
                	 	 <div class="col-md-12">
                	 	 	 <s:textfield name="editEventData.state" id="state"  cssClass="form-control state" placeholder="%{getText('event.state.ph')}"/>
                	 	 </div>
                	 	 </div>
                	 	 <div class="form-group zip-control">
                	 	 <div class="col-md-12">
                	 	 	 <s:textfield name="editEventData.zip" id="zip"  cssClass="form-control zip" placeholder="%{getText('event.zip.ph')}"/>
                	 	 </div>
                	 	 </div>
                	 	 <div style="clear:both;"></div>
                    </div>
                </div>
                <div class="form-group">
	               <div class="col-md-2"></div>
	                     <div class="col-md-10">
	                <a href="javascript:;"  id="clearaddessr"><s:text name="event.clear.address"/></a>
	                </div>
                </div>                      
            </div>
            <div class="col-md-5">
             	<iframe id="googlemap" src="/main/myevents/googlemap.jsp?lat=<s:property value="%{editEventData.latitude}"/>&lng=<s:property value="%{editEventData.longitude}"/>" class="google-map-iframe"></iframe>
            </div>  
          </div>
          <input id="lat" type="hidden" name="lat" />
          <input id="lon" type="hidden" name="lon" />
          
          <div id="timezoneselect">
	        <div class="form-group" style="display:none;" id="timezonedata">
	            <label for="timezone" class="col-sm-3 control-label"><s:text name="event.time.zone.lbl"/><span class="required">*</span></label>
	            <div class="col-sm-7">
	            <s:select name="editEventData.timezones"  cssClass="form-control" id='timezone' headerKey="1" headerValue="-- Select Time Zone --" list="timezones" listKey="key" listValue="value"  />
	            </div>
	            <%@include file="/myevents/timezone.jsp" %>
	        </div>
		  </div>
                        
          <div class="center">
              <span>
                  <input type="button"  class="btn btn-primary submitBtn" value="<s:text name="global.save.btn.lbl"/>" />   
                  <button type="button" class="btn btn-cancel cancel_where_section"><i class="fa fa-times"></i></button>                        
              </span>
          </div>
	</div>                        
</div>





                     
<div class="section-main-header box-top-gap"><s:text name="event.listing.options.section.header"/></div>
<div class="row sticky-out-button pull-right">
	<div class="col-md-12"><button type="button" class="btn btn-primary" id="edit_list_option_section"><s:text name="global.edit.btn.lbl"/></button></div>
</div>                   
<div class="white-box">
<div id="listOptionSectionStatic" class="toggleListOptSection">
 		 <%-- <div class="form-group">
 		         <span class="col-md-2 col-sm-4"><s:text name="ce.i18nlang.lbl"/></span>
				 <div class="col-md-7 col-sm-7">
				   <span class="col-md-1 col-sm-1">:</span>
			 		<span id="i18nLangStatic"><s:property value="%{editEventData.i18nLang}"/></span>
			 	 </div>               
          </div> --%>

		<div class="form-group">
           <span class="col-md-2 col-sm-4"><s:text name="event.host.lbl"/></span>
           <div class="col-md-7 col-sm-7">
       		 <span class="col-md-1 col-sm-1">:</span>
       		 <span id="hostNmStatic"><s:property value="%{editEventData.name}"/></span>,&nbsp;
       		 <span id="hostEmailStatic"><s:property value="%{editEventData.email}"/></span>
           </div>
       </div>
	<div class="form-group">
		<span class="col-md-2 col-sm-4"><s:text name="event.privacy.lbl"/></span>
		<div class="col-md-7 col-sm-7">
		    <span class="col-md-1 col-sm-1">:</span>
			<span id="listPrivacyStatic">
				<s:if test="%{editEventData.listingprivacy=='PVT'}"><s:text name="event.private.lbl"/></s:if>
				<s:else><s:text name="event.public.lbl"/></s:else>
			</span>
		</div>
	</div> 
</div>
<div id="listOptionSection" style="display:none;" class="toggleListOptSection">       
                         <s:hidden name="editEventData.i18nLang" id="i18nLang"></s:hidden>
                         <%-- <div class="form-group">
                            <label for="hostname" class="col-sm-3 control-label"  style="font-weight: normal !important; margin-top: -12px;"><s:text name="ce.i18nlang.lbl"/><span class="required">*</span></label>
                            <div class="col-sm-7">
                                   <s:select  name="editEventData.i18nLang" id="i18nLang" cssClass="form-control country"
								list="i18nLangList" listKey="key" listValue="value" />
                                
                            </div>
                        </div> --%>
                            
                         <div class="form-group">
                            <label for="hostname" class="col-sm-3 control-label"><s:text name="event.host.lbl"/><span class="required">*</span></label>
                            <div class="col-sm-7">
                                <s:textfield name="editEventData.name" cssClass="form-control"  id="hostname"  placeholder="%{getText('event.host.name.ph')}"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="hostemail" class="col-sm-3 control-label"></label>
                            <div class="col-sm-7">
                                  <s:textfield name="editEventData.email" cssClass="form-control"  id="hostemail"  placeholder="%{getText('event.host.email.ph')}"/>
                            </div>
                        </div>   
                            
                        <div class="form-group">
                            <label for="listingprivacy" class="col-sm-3 control-label"><s:text name="event.listing.privacy.lbl"/>&nbsp;
                            <i id="listingprivacyinfo" style="cursor:pointer" class="fa fa-info-circle"></i></label>
                            <div class="col-sm-7 listing-options">
                            <s:iterator value="%{listingprivacy}" var="type">
								<s:radio name="editEventData.listingprivacy" list="#{key: value}"
									value="%{editEventData.listingprivacy}"  cssClass="listprivacy"/>&nbsp;								
								</s:iterator>
                            </div>
                            </div>
                            
                        	<div class="center">
						        <span>
						            <input type="button"  class="btn btn-primary submitBtn" value="<s:text name="global.save.btn.lbl"/>" />  
						            <button type="button" class="btn btn-cancel cancel_list_option_section"><i class="fa fa-times"></i></button>                        
						        </span>
						    </div>
	</div>                        
</div>
</s:form>

<s:set name="recc" value="editEventData.recurringDates"></s:set>
<s:iterator value="editEventData.recurringDates" var="recurdate" status="recstat">


<script>

<s:if test="%{#recstat.index==0}">
$('a#recurlink').html('Regular Event');
$('.nonrecurringtimes #start').datetimepicker('hide');
$('.nonrecurringtimes #end').datetimepicker('hide');    	
$('.nonrecurringtimes').fadeOut(200);        
$('.recurringtimes').delay(200).fadeIn(function(){
$('.recurringtimes #date').datetimepicker('show');
$('#recurchk').attr('data-recur','yes');
$('#isRecurring').val("on");
});

$("#whenSectionStatic #startStatic #stWeekDayTm").html("${value}".split(" ")[3]+" "+"${value}".split(" ")[4].split("-")[0]);
</s:if>
//selectDate("${value}","${key}");
prevSaveRecDates["${value}"]="${key}";
</script>
</s:iterator>

</div>


<script>
removereccdate();
var selectedtype='';
var powertype='<s:text name="powertype"/>';
$('.submitBtn').click(function(){
	
	var subform=$(this).parent().parent().parent().attr('id');
	var selectedtype = $('input[name="type"]:checked').val();
	selectedtype='wysiwyg';
	editlistEvent('editevent',$('#isRecurring').val(),selectedtype,subform);	
});
 
 function convert(text){
  var chars = ["ò","δ","σ","Σ","ρ","ω","Ω","Ψ","Ο","ξ","Ξ","ν","μ","Λ","κ","ι","θ","Θ","η","ζ","π","☻","☺","♠","♣","♦","♥","©","Û","®","ž","Ü","Ÿ","Ý","Þ","%","¡","ß","¢","à","£","á","À","¤","â","Á","¥","ã","Â","¦","ä","Ã","§","å","Ä","¨","æ","Å","©","ç","Æ","ª","è","Ç","«","é","È","¬","ê","É","­","ë","Ê","®","ì","Ë","¯","í","Ì","°","î","Í","±","ï","Î","²","ð","Ï","³","ñ","Ð","´","ò","Ñ","µ","ó","Õ","¶","ô","Ö","·","õ","Ø","¸","ö","Ù","¹","÷","Ú","º","ø","Û","»","ù","Ü","@","¼","ú","Ý","½","û","Þ","€","¾","ü","ß","¿","ý","à","‚","À","þ","á","ƒ","Á","ÿ","å","„","Â","æ","…","Ã","ç","†","Ä","è","‡","Å","é","ˆ","Æ","ê","‰","Ç","ë","Š","È","ì","‹","É","í","Œ","Ê","î","Ë","ï","Ž","Ì","ð","Í","ñ","Î","ò","‘","Ï","ó","’","Ð","ô","“","Ñ","õ","”","Ò","ö","•","Ó","ø","–","Ô","ù","—","Õ","ú","˜","Ö","û","™","×","ý","š","Ø","þ","›","Ù","ÿ","œ","Ú"];
  var codes = ["&ograve;","&delta;","&sigma;","&Sigma;","&rho;","&omega;","&Omega;","&Psi;","&Omicron;","&xi","&Xi;","&nu","&mu;","&Lambda;","&kappa","&iota;","&theta;","&Theta;","&eta;","&zeta;","&pi;","&#9787;","&#9785;","&spades;","&clubs;","&diams;","&hearts;","&copy;","&#219;","&reg;","&#158;","&#220;","&#159;","&#221;","&#222;","&#37;","&#161;","&#223;","&#162;","&#224;","&#163;","&#225;","&Agrave;","&#164;","&#226;","&Aacute;","&#165;","&#227;","&Acirc;","&#166;","&#228;","&Atilde;","&#167;","&#229;","&Auml;","&#168;","&#230;","&Aring;","&#169;","&#231;","&AElig;","&#170;","&#232;","&Ccedil;","&#171;","&#233;","&Egrave;","&#172;","&#234;","&Eacute;","&#173;","&#235;","&Ecirc;","&#174;","&#236;","&Euml;","&#175;","&#237;","&Igrave;","&#176;","&#238;","&Iacute;","&#177;","&#239;","&Icirc;","&#178;","&#240;","&Iuml;","&#179;","&#241;","&ETH;","&#180;","&#242;","&Ntilde;","&#181;","&#243;","&Otilde;","&#182;","&#244;","&Ouml;","&#183;","&#245;","&Oslash;","&#184;","&#246;","&Ugrave;","&#185;","&#247;","&Uacute;","&#186;","&#248;","&Ucirc;","&#187;","&#249;","&Uuml;","&#64;","&#188;","&#250;","&Yacute;","&#189;","&#251;","&THORN;","&#128;","&#190;","&#252","&szlig;","&#191;","&#253;","&agrave;","&#130;","&#192;","&#254;","&aacute;","&#131;","&#193;","&#255;","&aring;","&#132;","&#194;","&aelig;","&#133;","&#195;","&ccedil;","&#134;","&#196;","&egrave;","&#135;","&#197;","&eacute;","&#136;","&#198;","&ecirc;","&#137;","&#199;","&euml;","&#138;","&#200;","&igrave;","&#139;","&#201;","&iacute;","&#140;","&#202;","&icirc;","&#203;","&iuml;","&#142;","&#204;","&eth;","&#205;","&ntilde;","&#206;","&ograve;","&#145;","&#207;","&oacute;","&#146;","&#208;","&ocirc;","&#147;","&#209;","&otilde;","&#148;","&#210;","&ouml;","&#149;","&#211;","&oslash;","&#150;","&#212;","&ugrave;","&#151;","&#213;","&uacute;","&#152;","&#214;","&ucirc;","&#153;","&#215;","&yacute;","&#154;","&#216;","&thorn;","&#155;","&#217;","&yuml;","&#156;","&#218;"];
  for(x=0; x<chars.length; x++){
  text=text.replace(new RegExp(chars[x], 'g'),codes[x]);
  }
  return text;
  }
 
 $(function(){
	 
	 formOnLoadData=$('#editeventform').serializeObject();
	 
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
	 
	 var valu = props.ee_listing_options_help_msg;	
	 $('#listingprivacyinfo').tooltipster({
		 position: 'right',
		    fixedWidth:'100px',
		        content: $('<span>'+valu+'</span>'),
		    });
	 
		 $('input.listingtype').iCheck({  
		 checkboxClass: 'icheckbox_square-grey', 
		 radioClass: 'iradio_square-grey'});
		 $('input.listprivacy').iCheck({  
			 checkboxClass: 'icheckbox_square-grey', 
			 radioClass: 'iradio_square-grey'});
		
		 
		 
		 $('input.radiotype').change(function() {
			 var selectedRadio=$(this).attr('id');
             $('input[name="type"][value="'+selectedRadio+'"]').prop("checked",true);
			$(".qtntype").each(function() {
				$(this).removeClass('active'); 
				$(this).removeClass('btn-active'); 
				$(this).removeClass('btn-default'); 
			});
			

    		if(selectedRadio=='text'){
    			$('#text_block').addClass('btn-default').addClass('active');
    			$('#textarea_block').addClass('btn-active');
    			$('#selection_block').addClass('btn-active');
   			 }else if(selectedRadio=='html'){
    			$('#text_block').addClass('btn-active');
    			$('#textarea_block').addClass('btn-default').addClass('active');
    			$('#selection_block').addClass('btn-active');
   			 }else{
    			$('#text_block').addClass('btn-active');
    			$('#textarea_block').addClass('btn-active');
    			$('#selection_block').addClass('btn-default').addClass('active');
    		}
    
    		var selectedtype = $('input[name="type"]:checked').val();
			document.getElementById('descriptiontype').value=selectedtype;
			if (selectedtype == 'text' || selectedtype == 'html') {
				if (document.getElementById('fckdesccontent'))
			    	document.getElementById('fckdesccontent').style.display = 'none';
				document.getElementById('fckdesctxtcontent').style.display = 'block';
			}else{
				document.getElementById('fckdesccontent').style.display = 'block';
				document.getElementById('fckdesctxtcontent').style.display = 'none';
			}
			
		 });
		 
		 $("#titleSection").hide();
});
 
function reloadeditors(){
		var selectedtype = $('input[name="type"]:checked').val();
		document.getElementById('descriptiontype').value=selectedtype;
		document.getElementById('fckdesccontent').style.display = 'block';
		document.getElementById('fckdesctxtcontent').style.display = 'none';
		/* if (selectedtype == 'text' || selectedtype == 'html') {
			if (document.getElementById('fckdesccontent'))
		    document.getElementById('fckdesccontent').style.display = 'none';
			document.getElementById('fckdesctxtcontent').style.display = 'block';
		} else {
			document.getElementById('fckdesccontent').style.display = 'block';
			document.getElementById('fckdesctxtcontent').style.display = 'none';
		} */
		var niceeditorhtml = document.getElementById('msg').value;    			
		//document.getElementById('descmsg').value=niceeditorhtml;
		document.getElementById('editor').innerHTML=niceeditorhtml;
		document.getElementById('descStatic').innerHTML=niceeditorhtml;
}

function getCountry(){	
	//alert("getcountry");
		 address=[];
		 country= $("#country").val();	
		 address.push( $("#city").val());			
		 address.push(country);
		 if($("#addr").val()!='')
		 	address.push($("#addr").val());
		 if($("#venue").val()!='')
		 	address.push($("#venue").val());
		 if(address.length>0)
		 	getLatLong($("#city").val(),country,$("#venue").val(),$("#addr").val());
	
}


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
		        $("#latitude").val(res.results[0].geometry.location.lat);
		    	$("#longitude").val(res.results[0].geometry.location.lng);
	            $('#googlemap').attr('src','/main/myevents/googlemap.jsp?lat=' +res.results[0].geometry.location.lat+'&lng='+res.results[0].geometry.location.lng);
	            calGettimzone(res.results[0].geometry.location.lat,res.results[0].geometry.location.lng);
	  }); 
	  
	}else{	  
	   jQuery.ajax({
		  url : '/main/myevents/populateMap.jsp',
	      type : 'POST',
	      data : {address:$("#addr").val(),venue:$("#venue").val(),city:$("#city").val(),state:$("#state").val(),country:$("#country").val()},
	      success : function(t){
	    	  $("#latitude").val(t.lat);
	    	  $("#longitude").val(t.lng);
	    	  $('#googlemap').attr('src','/main/myevents/googlemap.jsp?lat='+t.lat+'&lng='+t.lng);
	    	  calGettimzone(t.lat,t.lng);
	      }
	  }); 
	}
}

 function latlongDefault(){
	var ip = '<%=request.getHeader("x-forwarded-for")==null?request.getRemoteAddr():request.getHeader("x-forwarded-for")%>';
	 jQuery('#getlatlng').load('http://freegeoip.net/json/'+ip,function(response, status, xhr){
		 try{
			  var res=JSON.parse(response);
			  $('#googlemap').attr('src','/main/myevents/googlemap.jsp?lat='+res.latitude+'&lng='+res.longitude);
		 }catch(err){}
	 });
} 

reloadeditors();
$("#stWeekDay").html(stWeekDay);
$("#endWeekDay").html(endWeekDay);
</script>
</s:if>


<s:if test="%{subMgr==null || (subMgr!=null &&  submgr_permissions['EventPageContent']=='yes')}">
<div id="contentDIV">
<div class="section-main-header box-top-gap"><s:text name="display.options"/></div>
<div id="con_data"><div id="loading"><center><img src="../images/ajax-loader.gif"></center></div></div>
<script>
$(document).ready(function(){
	var url = 'EventPageContent?eid='+eventid;
	$.ajax({
		type:'POST',
		url:url,
		success:function(result){
			if(!isValidActionMessage(result)) return;
			$('#con_data').html(result);
		},
		error:function(){
			alert('error');
		}
	});
});
</script>
</div>
</s:if>

</s:if>





