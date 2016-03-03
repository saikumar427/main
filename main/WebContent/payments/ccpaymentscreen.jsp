<%@taglib uri="/struts-tags" prefix="s"%>
<script src="/main/js/prototype.js" language="JavaScript" type="text/javascript"></script>
<script src="/main/js/ajaxjson.js" language="JavaScript" type="text/javascript"></script>
<div> 
<table width="600px"><tr><td id='ccpaymenterror'></td></tr></table>
</div>
<div id='ccpaymentstatus'>
<s:form name="ccpaymentscreen" id="ccpaymentscreen" method="post" action="payments!processPayment">
<s:hidden name="seqId" id="seqId"/>
<s:hidden name="state" id="bhstate"></s:hidden>
<table width="600px">
  <tr><td id="headermsg" class="smallestfont" style="padding-left:5px;"></td><td></td></tr>
</table>
<table>
  <tr><td>   
   <table>
    <tr><td height='30'>Card Type *</td>
         <td height='30'><s:select name="carddata.creditcardtype" id="cardtype"
				headerKey="1" headerValue="-- Select Card Type --" list="cardtype"  listKey="key" listValue="value"/></td> 
         
	</tr>	
	<tr><td height='30'>Card Number *</td>
        <td height='30'><s:textfield name="carddata.cardnumber" id="cardnumber" theme="simple" size="25" /></td>
    </tr>
    <tr><td height='30'>CVV Code *</td>
        <td height='30'><s:textfield name="carddata.cvvcode" id="cvvcode" theme="simple" size="25" /></td>
    </tr>
    
    <tr>
       <td height='30'>Expiration Month/Year</td>
       <td height='30'><s:select name="carddata.expiremonth" id='expiremonth'
				headerKey="0"  list="months"
				listKey="key" listValue="value" />&nbsp;<s:select name="carddata.expireyear" id='expireyear'
				headerKey="0"  list="years"
				listKey="key" listValue="value"/></td>
    </tr>
    </table></td>
    <td align="right" valign="top"><table align="right">
    <tr><td><img src="/main/images/amex.png" border="0" width="40px">&nbsp;<img src="/main/images/mastercard.png" border="0" width="40px" >&nbsp;<img src="/main/images/discover.png" border="0" width="40px" >&nbsp;<img src="/main/images/visa.png" border="0" width="40px" ></td></tr>
    </table></td>
    </tr>
    </table>
    <table>
    <tr>
    <td height='30'><b>Credit Card Billing Address</b></td>
    </tr></table>
    <table>
    <tr>
       <td height='25'>Cardholder *</td>
       <td height='25' colspan="2"><table align="left"><tr>&nbsp;<td style="padding-bottom:10px;"><font class="smallestfont">First Name</font><br/><s:textfield name="carddata.firstname" id="firstname" theme="simple" size="25" /></td><td height='30' style="padding-bottom:10px;"><font class="smallestfont">Last Name</font><br/><s:textfield name="carddata.lastname" id="lastname" theme="simple" size="25" /></td></tr></table></td>
   </tr>
      
   <tr>
     <td height='30'>Street *</td>
     <td height='30'>&nbsp;<s:textfield name="carddata.address" id="address" theme="simple" size="25" /></td>
   </tr>
   <tr>
     <td height='30'>Apt/Suite</td>
     <td>&nbsp;<s:textfield name="carddata.aptsuite" id="aptsuite" theme="simple" size="25" /></td>
   </tr>
   <tr>
     <td height='30'>City *</td>
     <td height='30'>&nbsp;<s:textfield name="carddata.city" id="city" theme="simple" size="25" /></td>
   </tr>
    <tr>
     
     <td height='30'>Country *</td>
     <td height='30'>&nbsp;<select name="carddata.country" id="bhcountry">
                        <option value="1">-- Select Country --</option>
                        <option value="United States of America">United States of America</option>
                        <option value="Afghanistan">Afghanistan</option>
                        <option value="American Samoa">American Samoa</option>
                        <option value="Andorra">Andorra</option>
                        <option value="Australia">Australia</option>
                        <option value="Austria">Austria</option>
                        <option value="Bahrain">Bahrain</option>
                        <option value="Belgium">Belgium</option>
                        <option value="Brazil">Brazil</option>
                        <option value="British Indian Ocean Territory">British Indian Ocean Territory</option>
                        <option value="Brunei Darussalam">Brunei Darussalam</option>
                        <option value="Canada">Canada</option>
                        <option value="China">China</option>
                        <option value="Christmas Island">Christmas Island</option>
                        <option value="Cocos (Keeling) Islands">Cocos (Keeling) Islands</option>
                        <option value="Congo">Congo</option>
                        <option value="Cook Islands">Cook Islands</option>
                        <option value="Costa Rica">Costa Rica</option>
                        <option value="Cyprus">Cyprus</option>
                        <option value="Czech Republic">Czech Republic</option>
                        <option value="Denmark">Denmark</option>
                        <option value="Dominican Republic">Dominican Republic</option>
                        <option value="Ecuador">Ecuador</option>
                        <option value="El Salvador">El Salvador</option>
                        <option value="Estonia">Estonia</option>
                        <option value="Faroe Islands">Faroe Islands</option>
                        <option value="Finland">Finland</option>
                        <option value="France">France</option>
                        <option value="French Guiana">French Guiana</option>
                        <option value="French Southern Lands">French Southern Lands</option>
                        <option value="Germany">Germany</option>
                        <option value="Greece">Greece</option>
                        <option value="Greenland">Greenland</option>
                        <option value="Guadeloupe">Guadeloupe</option>
                        <option value="Guam">Guam</option>
                        <option value="Guatemala">Guatemala</option>
                        <option value="Heard and McDonald Islands">Heard and McDonald Islands</option>
                        <option value="Hong Kong">Hong Kong</option>
                        <option value="Hungary">Hungary</option>
                        <option value="India">India</option>
                        <option value="Indonesia">Indonesia</option>
                        <option value="Iran">Iran</option>
                        <option value=Ireland>Ireland</option>
                        <option value="Israel">Israel</option>
                        <option value="Italy">Italy</option>
                        <option value="Jamaica">Jamaica</option>
                        <option value="Japan">Japan</option>
                        <option value="Kiribati">Kiribati</option>
                        <option value="Luxembourg">Luxembourg</option>
                        <option value="Malaysia">Malaysia</option>
                        <option value="Malta">Malta</option>
                        <option value="Martinique">Martinique</option>
                        <option value="Mayotte">Mayotte</option>
                        <option value="Mexico">Mexico</option>
                        <option value="Micronesia">Micronesia</option>
                        <option value="Monaco">Monaco</option>
                        <option value="Montenegro">Montenegro</option>
                        <option value="Morocco">Morocco</option>
                        <option value="Nauru">Nauru</option>
                        <option value="Netherlands">Netherlands</option>
                        <option value="New Zealand">New Zealand</option>
                        <option value="Niue">Niue</option>
                        <option value="Norfolk Island">Norfolk Island</option>
                        <option value="Norway">Norway</option>
                        <option value="Pakistan">Pakistan</option>
                        <option value="Peru">Peru</option>
                        <option value="Philippines">Philippines</option>
                        <option value="Pitcairn">Pitcairn</option>
                        <option value="Portugal">Portugal</option>
                        <option value="Poland">Poland</option>
                        <option value="Puerto Rico">Puerto Rico</option>
                        <option value="Reunion">Reunion</option>
                        <option value="Saint Kitts and Nevis">Saint Kitts and Nevis</option>
                        <option value="Saint Pierre and Miquelon">Saint Pierre and Miquelon</option>
                        <option value="San Marino">San Marino</option>
                        <option value="Singapore">Singapore</option>
                        <option value="Slovakia">Slovakia</option>
                        <option value="Slovenia">Slovenia</option>
                        <option value="South Africa">South Africa</option>
                        <option value="Spain">Spain</option>
                        <option value="Swaziland">Swaziland</option>
                        <option value="Sweden">Sweden</option>
                        <option value="Switzerland">Switzerland</option>
                        <option value="Taiwan">Taiwan</option>
                        <option value="Thailand">Thailand</option>
                        <option value="Tokelau">Tokelau</option>
                        <option value="Turkey">Turkey</option>
                        <option value="Turks and Caicos Islands">Turks and Caicos Islands</option>
                        <option value="Tuvalu">Tuvalu</option>
                        <option value="United Arab Emirates">United Arab Emirates</option>
                        <option value="United Kingdom">United Kingdom</option>
                        <option value="United States Minor Outlying Islands">United States Minor Outlying Islands</option>
                        <option value="Vatican City">Vatican City</option>
                        <option value="Zimbabwe">Zimbabwe</option>
                </select></td>
    </tr> 
    
    <tr>
     <td height='30'>Zip *</td>
     <td height='30'>&nbsp;<s:textfield name="carddata.zip" id="zip" theme="simple" size="10" /><br><span class="smallestfont">&nbsp;(letters, numbers, spaces and hyphens only.)</span></td>
   </tr>
   </table>
   <div style="position:absolute;bottom:-25px;left:0px;right:0px;text-align:center;background-color:#F2F2F2;margin:0px;padding-top:0px;padding-left:5px;padding-right:5px;padding-bottom:11px;">
 <span class="smallestfont">By clicking on Submit button, I agree to make all Eventbee service fee payments using this card.</span></div>
 </s:form>
 
 </div>
 
 