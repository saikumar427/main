<%@taglib uri="/struts-tags" prefix="s" %>

<style>
table{
border-collapse: unset !important;
border-spacing: 2px !important;
}

</style>

<script type="text/javascript">
 var powertype='${powertype}';
 var isrecurring='${isrecurring}';
 var colorjson=${seatColorData};
 //alert(JSON.stringify(colorjson));
</script>
<script type="text/javascript" src="../js/seatingaddmanual.js?timestamp=(new Date()).getTime()"></script>
<div class="col-md-12">
	<div class="row">
	<div class="pull-right"><a href="Seating?eid=${eid}"><b><s:text name="sea.back.lbl"/></b></a></div>
	</div><br/>
	<s:hidden id="eid" name="eid"></s:hidden>
    <s:hidden id="venueid" name="venueid"></s:hidden>
	<div class="row">
		<div class="col-md-2"><s:text name="sea.event.date"/></div>
		<div class="col-md-5">		
		<s:select name="eventdate" id='eventdate' theme="simple" list="eventdates" listKey="key" listValue="value"  cssClass="form-control" onchange="getSeating()"/></div>
	</div>
	<br/>
	<s:if test="sectionsList.size>1">
	<div class="row">
		<div class="col-md-2"><s:text name="sea.section.lbl"/></div>
		<div class="col-md-5">
		<s:select list="sectionsList" name="section" id="section" listKey="key" listValue="value"   cssClass="form-control"  onchange="getSeating()"/>
		</div>
	</div>
	</s:if>
	<s:else>
			<s:hidden id="section" name="sectionid" value="%{sectionsList[0].key}"></s:hidden>
	</s:else>
	<br/>
	
	<div class="row">
		<div id="loading" align="center" class="load">
			<table>
				<tr><td align="center"><s:text name="global.loading.msg"/></td></tr>
				<tr><td align="center"><img src="../images/ajax-loader.gif"></td></tr>
			</table>
			</div>	
	</div>
	
	<div class="section-main-header">
 	 	<s:text name="sea.seat.layout.th.lbl"/>
		</div>
	
			 <div id="seatsdiv"  align="left">
		 		<div class="white-box">
                                
                               
                                    <div class="selstyle" >
										<table id="seltable" >
										</table>
									</div><br/>
									<div class="bottom-gap" style="overflow:auto;width:100%;height: 100%;border: 1px solid #336699;padding: 10px;background-color: white;border-radius: 4px;box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;">	
										<table bgcolor="#C0C0C0" align="center">
											<tr>
												<td>
													<div id="seatcell" style="padding:4px 4px 4px 4px" ></div>
												</td>
											</tr>	
										</table>
									</div>
                              
                               <div align="center">
                                    <button class="btn btn-primary"  type="button"  onclick="saveseats()"><s:text name="global.save.btn.lbl"/></button> &nbsp;                                                                     
                                </div> 
                            </div>
		 </div>
		 
</div>

<script>
getSeating();
</script>