<%@taglib uri="/struts-tags" prefix="s"%>
<table border="0" cellpadding="3" cellspacing="0" width="100%">
	<tr>
		<td valign='top' width='50%'>
		<table border="0">
			<tr>
				<td align="left" colspan="3"><b>Search Filter</b></td>
			</tr>
			<tr>
				<td>
					<fieldset class="fieldsetstyle">
						<legend class="legendheader">Trasaction Date</legend>
						<table>
						<s:set name="tdateradiocheck" value="rsvpReportsData.tdateradio"></s:set>
							<tr>
								<td>
									<input type="radio" name="rsvpReportsData.tdateradio" value="1"
									<s:if test="%{#tdateradiocheck == 1}">checked='checked'</s:if> />
								</td>
								<td colspan="2">All</td>
							</tr>
							<tr>
								<td>
									<input type="radio" name="rsvpReportsData.tdateradio" value="2" 
									<s:if test="%{#tdateradiocheck == 2}">checked='checked'</s:if> />
								</td>
								<td>Start Date</td>
								<td>	<s:text name="rsvpReportsData.startMonth"></s:text>
									<s:text	name="rsvpReportsData.startDay"></s:text>
									<s:text name="rsvpReportsData.startYear"></s:text>
								</td>
							</tr>
							<tr>
								<td></td>
								<td>End Date</td>
									<td><s:text name="rsvpReportsData.endMonth"></s:text>
									<s:text	name="rsvpReportsData.endDay"></s:text>
									<s:text	name="rsvpReportsData.endYear"></s:text>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
		</td>
		<td valign='top' width='50%'>
		<table>
			<tr>
				<td align="left" colspan="2"><b>Display Fields Filter</b>&nbsp;&nbsp;
				<a href="#" name="CheckAll"
					onClick="checkAll(document.rsvpreportsform.transFields)">Select All</a> | <a
					href="#" name="UnCheckAll"
					onClick="uncheckAll(document.rsvpreportsform.transFields)">Clear All</a></td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td><input type="checkbox" name="transFields" value="Transaction ID"
					<s:if test="%{transFields.contains('Transaction ID')}">checked</s:if>></input>
					Transaction	ID</td>
				
				<td><input type="checkbox" name="transFields" value="Booking Date"
					<s:if test="%{transFields.contains('Booking Date')}">checked</s:if>></input>Transaction
				Date</td>

			</tr>
			<tr>
				<!--  <td><input type="checkbox" name="transFields" value="Response"
					<s:if test="%{transFields.contains('Response')}">checked</s:if>></input>Response</td>
				-->
				<td><input type="checkbox" name="transFields" value="First Name"
					<s:if test="%{transFields.contains('First Name')}">checked</s:if>></input>First
				Name</td>
				<td><input type="checkbox" name="transFields" value="Last Name"
					<s:if test="%{transFields.contains('Last Name')}">checked</s:if>></input>Last
				Name</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="transFields" value="Email"
					<s:if test="%{transFields.contains('Email')}">checked</s:if>></input>Email</td>
				<td><input type="checkbox" name="transFields" value="Phone"
					<s:if test="%{transFields.contains('Phone')}">checked</s:if>></input>Phone</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="transFields"
					value="Notes"
					<s:if test="%{transFields.contains('Notes')}">checked</s:if>></input>Notes</td>
			</tr>
			<!-- <tr>
				<td>
					<input type="checkbox" name="transFields" value="Booking Source"
					<s:if test="%{transFields.contains('Booking Source')}">checked</s:if>></input>Booking Source
				</td>
				<td>
					<input type="checkbox" name="transFields" value="Track Code"
					<s:if test="%{transFields.contains('Track Code')}">checked</s:if>></input>Tracking URL
				</td>
			</tr> -->
			<s:set value="%{transattributes.size()}" name="transattributes"></s:set>
			<s:if test="%{#transattributes!=0}">
			<tr>
				<td colspan="2">
					<input type="checkbox" name="transFields" value="transFieldsFilter" <s:if test="%{transFields.contains('transFieldsFilter')}">checked</s:if>></input>
					Include Registration Form Questions &nbsp;&nbsp;(<span id='transcount'>${transattribcount}</span>/<s:property value="%{transattributes.size()}"/> included)<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#" onclick="getTransactionAttributes('${eid}');">Click here</a> to select questions
				</td>
			</tr>
			</s:if>
		</table>
		</td>
	</tr>
</table>
<table border="0" cellpadding="3" cellspacing="0" width="100%">

	<tr>
		<td align="center" colspan="2"><input type="button" name="Submit"
			id="submitrsvptransbtn" value="Submit" /></td>
	</tr>

</table>