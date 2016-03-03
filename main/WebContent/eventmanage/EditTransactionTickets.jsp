<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" language="JavaScript" src="../js/eventmanage/edittransactiontickets.js">
</script>
<div id="statusMessageBox" class="statusMessageBox row" style="display: none">
<div style="float: left; valign: middle;">
<div id="EditTransactionTicketsStatusMsg"></div>
</div>
</div>
<br />  

<s:form name="ticketedit" id="ticketedit"
	action="EditTransactionTickets!updateTickets" method="post"
	theme="simple">
	<s:hidden name="eid"></s:hidden>
	<s:hidden name="tid"></s:hidden>
 <s:set value="%{isrecurring}" name="isrecurring"></s:set>
<s:if test="%{isrecurring==true}">
				<div class="alert">
                            <div class="row">
                                <div class="col-md-1" style="width: 17%;"><s:text name="rep.for.event.date.lbl"/>&nbsp;&nbsp;</div>
                  				 <div class="col-md-6"><s:property value="currentTransactionDate"></s:property></div>
                  		  </div>
                  		  <div style="height:10px;"></div>
                  		  <div class="row">
                                <div class="col-md-1" style="width: 17%; padding-top: 8px;"><s:text name="rep.chng.date.lbl"/></div>
                  				 <div class="col-md-6"><s:select name="reportsData.date" id='source' headerKey=""
    		      headerValue="%{getText('rep.change.date.lbl')}" 
    		      list="dates" listKey="key" listValue="value" cssClass="form-control"/></div>
                  		  </div>
                    </div>
                      </s:if>
     <div class="row"><div class="col-md-6"></div>
     <div class="col-md-6 sm-font"><s:text name="rep.make.sure.clck.cntn.lbl"/>&nbsp;&nbsp;</div></div>
     <div style="height:30px"></div>
			<div class="row"><div class="col-md-11 alert alert-danger" id="error" style="display:none;margin-left:25px;"></div></div>
			<div class="row">
			<div class="col-md-6" ><b><s:text name="rep.tckt.name.lbl"/></b></div>
			<div class="col-md-1"><b><s:text name="rep.price.lbl"/>&nbsp;(${currencySymbol})</b></div>
			<div class="col-md-1" style="width:10.333%;"><b><s:text name="rep.qnty.lbl"/></b></div>
			<div class="col-md-2" style="width: 12.667%;"><b><s:text name="rep.dscnt.lbl"/>&nbsp;(${currencySymbol})</b></div>
			<div class="col-md-1 pull-right" style="width:15%"><b><s:text name="rep.total.lbl"/>&nbsp;(${currencySymbol})</b></div>
			</div>
    <div style="height:10px"></div>
		<s:set value="%{ticketdetails.size()}" name="tsize"></s:set>
		<s:iterator value='ticketdetails' var='ticketdet'
			status="ticketStatus">
			<input type="hidden" name="ticketprices[<s:property value='#ticketStatus.count' />]" id="ticketprices<s:property value='#ticketStatus.count' />" value="<s:property value='#ticketdet.price' />">
			<input type="hidden" name="ticketfees[<s:property value='#ticketStatus.count' />]" id="ticketfees<s:property value='#ticketStatus.count' />" value="<s:property value='#ticketdet.fee' />">
				<div class="row">
				<s:if test="%{#ticketdet.isDonation=='yes'}">
				<div class="col-md-6" style="margin-top: 8px;">
				<span id="ticketname<s:property value='#ticketStatus.count' />">
						<s:property	value='#ticketdet.ticketname' />
					</span>
				</div>
				<div class="col-md-1"  style="margin-top: 8px;">
				<span id="ticketprice<s:property value='#ticketStatus.count' />"></span>
				</div>
				<div class="col-md-1" style="width:10.333%;">
				<input type="hidden" size="3"
					id="qty<s:property value='#ticketStatus.count'/>"
					style="text-align: right"
					name="qty[<s:property value='#ticketStatus.count' />]"
					value="<s:property value='#ticketdet.qty'/>"/>
					<input type="hidden" size="3"
					id="qtyoriginal<s:property value='#ticketStatus.count'/>"
					style="text-align: right"
					name="qtyoriginal[<s:property value='#ticketStatus.count' />]"
					value="<s:property value='#ticketdet.qty'/>"/>
				</div>				
				<div class="col-md-1" style="width: 12.667%;">
				<input type="hidden" size="5"
					style="text-align: right"
					id="discount<s:property value='#ticketStatus.count' />"
					name="discount[<s:property value='#ticketStatus.count' />]"
					value="<s:property value='#ticketdet.discount' />"
					 />
				</div>
				<div class="col-md-1" style="width:15%">
				<div id="total<s:property value='#ticketStatus.count' />"><input
					type="text" size="7" class="form-control" style="text-align: right"
					id="totalval<s:property value='#ticketStatus.count' />"
					name="total[<s:property value='#ticketStatus.count' />]"
					value="<s:property value='#ticketdet.ticketstotal' />"
					onChange="changedonationtotal('<s:property value='#ticketStatus.count' />',<s:property value='#tsize' />);" />
				</div>
				</div>
				</s:if>
				<s:else>
				<div class="col-md-6" style="margin-top: 8px;">
				<span id="ticketname<s:property value='#ticketStatus.count' />">
						<s:property	value='#ticketdet.ticketname' />
					</span>
					<s:if test="%{#ticketdet.newName!=null}" ><br><s:text name="rep.name.prce.chng.lbl"/>(<s:property value='#ticketdet.newName' />)</s:if>
				</div>
				<div class="col-md-1"  style="margin-top: 8px;">
					<span id="ticketprice<s:property value='#ticketStatus.count' />">
					<script>document.getElementById("ticketprice<s:property value='#ticketStatus.count'/>").innerHTML=numberWithCommas(<s:property value='#ticketdet.price' />);</script>
					</span>
				</div>
				<div class="col-md-1" style="width:10.333%;">
				<input type="text" class="form-control" size="3"
					id="qty<s:property value='#ticketStatus.count'/>"
					style="text-align: right"
					name="qty[<s:property value='#ticketStatus.count'/>]"
					value="<s:property value='#ticketdet.qty' />"
					onChange="calculateamount('<s:property value='#ticketStatus.count' />',<s:property value='#tsize' />);" />
					<input type="hidden" size="3"
					id="qtyoriginal<s:property value='#ticketStatus.count'/>"
					style="text-align: right"
					name="qtyoriginal[<s:property value='#ticketStatus.count' />]"
					value="<s:property value='#ticketdet.qty'/>"/>
				</div>
				<div class="col-md-1" style="width: 12.667%;">
				<input type="text" class="form-control" size="5"
					style="text-align: right"
					id="discount<s:property value='#ticketStatus.count' />"
					name="discount[<s:property value='#ticketStatus.count' />]"
					value="<s:property value='#ticketdet.discount' />"
					onChange="calculateamount('<s:property value='#ticketStatus.count' />',<s:property value='#tsize' />);" />
				</div>
				<div class="col-md-1" style="width:15%">
				<div id="total<s:property value='#ticketStatus.count' />"><input
					type="text" class="form-control" size="7" style="text-align: right"
					id="totalval<s:property value='#ticketStatus.count' />"
					name="total[<s:property value='#ticketStatus.count' />]"
					value="<s:property value='#ticketdet.ticketstotal' />"
					onChange="changetotal('<s:property value='#ticketStatus.count' />',<s:property value='#tsize' />);" />
				</div>
				</div>
				</s:else>
			</div>
			<div class="row">
			<div class="col-md-5"><b><s:property value='#ticketdet.groupname' /></b></div>
			</div>
			<input type="hidden"
				name="ticketid[<s:property value='#ticketStatus.count' />]"
				value="<s:property value='#ticketdet.ticketid' />">
			<input type="hidden"
				name="ticketgroupid[<s:property value='#ticketStatus.count' />]"
				value="<s:property value='#ticketdet.tktgroupid' />">
			<input type="hidden"
				name="ticketname[<s:property value='#ticketStatus.count' />]"
				value="<s:property value='#ticketdet.ticketname' />">
			<input type="hidden"
				name="groupname[<s:property value='#ticketStatus.count' />]"
				value="<s:property value='#ticketdet.groupname' />">
				<div style="height:5px"></div>
		</s:iterator>
		<div class="row">
		<div class="col-md-6"></div>
		<div class="col-md-3" style="width:24.5%"><b><s:text name="rep.total.discount.lbl"/>&nbsp;(${currencySymbol})</b></div>
		<div class="col-md-2">
		<span id="totaldiscount"> 
			<script>
			document.getElementById("totaldiscount").innerHTML=numberWithCommas(<s:text name="reportsData.currentDiscount"></s:text>);</script>
			</span>
		</div>
		<div class="col-md-1"><input type="hidden" name="totaldiscount1"
				id="totaldiscount1" value=""> <input type="hidden"
				name="totalamount1" id="totalamount1" value=""><input type="hidden"
				name="totalnetamount" id="totalnetamount" value=""><input type="hidden"
				name="remqty" id="remqty" value=""><input
				type="hidden" name="finali" value="<s:property value='#tsize' />"></div>
		</div>
		<div style="height:14px"></div>
		<div class="row">
		<div class="col-md-8" style="width: 64.667%;"></div>
		<div class="col-md-3" style="width:24%"><b><s:text name="rep.total.amnt.lbl"/>&nbsp;(${currencySymbol})</b></div>
		<div class="col-md-1 pull-left" >
			<span id="totalamount">
			<script>document.getElementById("totalamount").innerHTML=numberWithCommas(<s:text name="reportsData.originalAmount"></s:text>);</script>
			</span>
				</div>
		</div>
<div style="height:14px"></div>
		<div class="row">
		<div class="col-md-8" style="width: 64.667%;"></div>
		<div class="col-md-3" style="width:19%"><b><s:text name="rep.tax.lbl"/>&nbsp;(${currencySymbol})</b></div>
		<div class="col-md-1 pull-left"  style="width:12.5%"><input class="form-control" type="text" size="7" name="tax"
				id="tax" style="text-align: right"
				value="<s:text name="reportsData.currentTax"></s:text>"
				onChange="changetax1()"></div>
		</div>
	<div style="height:14px"></div>	
<div class="row">
		<div class="col-md-8" style="width: 64.667%;"></div>
		<div class="col-md-3" style="width:24%"><b><s:text name="rep.amnt.Net.lbl"/>&nbsp;(${currencySymbol})</b></div>
		<div class="col-md-1">
				<span id="net">
			<script>

			document.getElementById("net").innerHTML=numberWithCommas(<s:text name="reportsData.netAmount"></s:text>);</script>
			</span>
				
				</div>
		</div>
</s:form>
<br/>
<div id="processingbar" style="display:none;"></div>

 <div class="form-group" id="buttons">
                        <div class="col-sm-offset-2 col-sm-6 pull-right">
                            <button type="submit" class="btn btn-primary" onclick="finalSubmit('${eid}','<s:property value='#tsize'/>','${tid}')"><s:text name="global.submit.btn.lbl"></s:text></button>
                            <button class="btn btn-cancel" id="canceltktdetails" onclick="closeTransactionTickets();"><i class="fa fa-times"></i></button>
                        </div>
                    </div>
 <br/>
