package com.event.dbhelpers;

import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import com.event.beans.ListingOptionsData;
import com.event.beans.ProfileData;
import com.event.helpers.XffunctionHelper;
import com.eventbee.creditcard.CardConstants;
import com.eventbee.creditcard.CreditCardModel;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventbee.general.formatting.CurrencyFormat;
import com.eventbee.general.formatting.EventbeeStrings;

public class UpgradeEventDB {
	public static String getPremiumLevel(String eid){
		String premiumlevel=DbUtil.getVal("select premiumlevel from eventinfo where eventid=CAST(? AS BIGINT)", new String[] {eid});
		if(premiumlevel==null)	premiumlevel="";
		if("EVENT_PREMIUM_LISTING".equals(premiumlevel)) premiumlevel="Premium";
		if("EVENT_FEATURED_LISTING".equals(premiumlevel)) premiumlevel="Featured";
		if("EVENT_CUSTOM_LISTING".equals(premiumlevel))  premiumlevel="Custom";
		return premiumlevel;
	}
	public static void updatePremiumLevel(String eid,String premiumType){
		System.out.println("premiumType::::::"+premiumType);
		if("premium".equals(premiumType)) premiumType="EVENT_PREMIUM_LISTING";
		else if("featured".equals(premiumType)) premiumType="EVENT_FEATURED_LISTING";
		else premiumType="";
		StatusObj obj=DbUtil.executeUpdateQuery("update eventinfo set premiumlevel=? where eventid=CAST(? AS BIGINT)",new String[]{premiumType, eid});

	}
	public static void updateEventInfo(String eid,ListingOptionsData listingOptionsData){
		String premiumlevel=listingOptionsData.getPremiumlisttype();
		String level="";
		if("premium".equals(premiumlevel)){
			level="EVENT_PREMIUM_LISTING";
		}
		else if("featured".equals(premiumlevel)){
			level="EVENT_FEATURED_LISTING";
		}
		else if("custom".equals(premiumlevel)){
			level="EVENT_CUSTOM_LISTING";
		}
		DbUtil.executeUpdateQuery("update eventinfo set status='ACTIVE',premiumlevel=?  where eventid=CAST(? AS BIGINT)",new String[]{level, eid});
	}
	public static String getAccountType(String mgrId){
		String accounttype=DbUtil.getVal("select accounttype from authentication where user_id=?", new String[] {mgrId});
		if(accounttype==null)
			accounttype="basic";
		return accounttype;
	}
	public static String insertUpgradeData(String eid, String premiumlevel){
		if("Premium".equals(premiumlevel)) premiumlevel="EVENT_PREMIUM_LISTING";
		if("Featured".equals(premiumlevel)) premiumlevel="EVENT_FEATURED_LISTING";
		if("Custom".equals(premiumlevel))  premiumlevel="EVENT_CUSTOM_LISTING";
		String upgradeEventSeqId=DbUtil.getVal("select nextval('CCProcessing_Sequence')",new String[]{});
		DbUtil.executeUpdateQuery("insert into CCProcessing_details(refid,purpose,regid,created_at) " +
				"values(CAST(? AS BIGINT),?,to_number(?,'99999999999999'),now())",
				new String[] {eid, premiumlevel, upgradeEventSeqId});
		return upgradeEventSeqId;
	}

	public static void update(String eid,String premiumlevel, String totalAmount,String upgradeEventSeqId){
		if("premium".equals(premiumlevel)) premiumlevel="EVENT_PREMIUM_LISTING";
		if("featured".equals(premiumlevel)) premiumlevel="EVENT_FEATURED_LISTING";
		if("custom".equals(premiumlevel))  premiumlevel="EVENT_CUSTOM_LISTING";
		DbUtil.executeUpdateQuery("update CCProcessing_details set amount=to_number(?,'9999999999999.99'),purpose=?,created_at='now()'" +
				"  where refid=CAST(? AS BIGINT) and regid=to_number(?,'99999999999999')",new String[]{totalAmount,premiumlevel, eid,upgradeEventSeqId});

	}
	public static String getTotalAmount(String eid,String upgradeEventSeqId){
		String totalAmount=DbUtil.getVal("select amount from CCProcessing_details where refid=CAST(? AS BIGINT) and regid=to_number(?,'9999999999999')", new String[]{eid,upgradeEventSeqId});
	return totalAmount;
	}
	public static String creditCardScreenData(String eid,String upgradeEventSeqId,String totalAmount){
		String paymentData="";
		ProfileData m_ProfileData=new ProfileData();
		CreditCardModel m_card=new CreditCardModel();
		HashMap<String,String> hm=new HashMap<String,String>();
		hm.put(CardConstants.REQUEST_APP,"UPGRADE_EVENT");
		hm.put(CardConstants.TRANSACTION_TYPE,CardConstants.TRANS_ONE_TIME);
		hm.put(CardConstants.BASE_REF,"/card");
		hm.put(CardConstants.LOGO_URL,"");
		hm.put(CardConstants.AUTH_POLICY,"");
		hm.put(CardConstants.AUTH_URL,"");
		hm.put(CardConstants.AMOUNT,""+totalAmount);
		m_card.setParams(hm);
		m_card.setProfiledata(m_ProfileData);
		paymentData=getCardScreen(m_card,eid);
		return paymentData;
	}
	static String YEARS[]=new String[]{"2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020"};
	public static String getCardScreen(CreditCardModel ccm,String eventid){
		StringBuffer sb=new StringBuffer("");
		String BASE_REF=ccm.BASE_REF;
		String currencyformat=DbUtil.getVal("select currency_symbol from currency_symbols where currency_code=(select currency_code from event_currency where eventid=?)",new String[]{eventid});
	    if(currencyformat==null)   currencyformat="$";
		String grandtotal=ccm.getGrandtotal();
		CurrencyFormat cf=new CurrencyFormat();
		grandtotal=cf.getCurrency2decimal(grandtotal);
		grandtotal=currencyformat+"<span id='grandtotal'>"+grandtotal+"</span>";
		sb.append("<div style='background:#DCE8FF;border:0 none;margin:0;padding:5px;'>");
		sb.append("<table width='100%' cellpading='0'><tr><td>Amount: ");
		sb.append(grandtotal);
		sb.append("<br/><span style='color:#666666;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;font-weight:lighter;'>NOTE: CC processing is done by Eventbee. Eventbee appears on your credit card statement</span></td></tr>");
		sb.append("</table>");
	    sb.append("<br/>");
		sb.append("<table width='100%' cellpading='0'><tr><td><table ><tr><td  width='110px'  class='inputlabel'>Card Type *</td><td  class='inputvalue'>");
		sb.append(XffunctionHelper.getXfSelectOneCombo(BASE_REF+"/cardtype",EventbeeStrings.cardtypes,EventbeeStrings.carddisplaytypes, ccm.getCardtype() ));
		sb.append("</td></tr></table>");
		sb.append("</td></tr><tr><td><table width='100%' cellpading='0'><tr><td  width='110px'  class='inputlabel'>Card Number *</td><td  class='inputvalue'>");
		sb.append(XffunctionHelper.getXfTextBox(BASE_REF+"/cardnumber",GenUtil.getEncodedXML(ccm.getCardnumber() ),"25"));
		sb.append("</td><td  class='inputlabel'>Expiration Month</td><td  class='inputvalue'>");
		sb.append(XffunctionHelper.getXfSelectOneCombo(BASE_REF+"/expmonth",EventbeeStrings.monthvals,EventbeeStrings.months, GenUtil.getEncodedXML(ccm.getExpmonth() )    ));
		sb.append("</td><td  class='inputlabel'>Expiration Year</td><td  class='inputvalue'>");
		sb.append(XffunctionHelper.getXfSelectOneCombo(BASE_REF+"/expyear",YEARS,YEARS, GenUtil.getEncodedXML(ccm.getExpyear() )    ));
		sb.append("</td></tr></table></td></tr>");
		String CVV2_ENABLE=EbeeConstantsF.get("CVV2_ENABLE1","yes");
		String cardvendor=EbeeConstantsF.get("cardvendor","PAYPALPRO");

		if("yes".equals(CVV2_ENABLE)&&"PAYPALPRO".equals(cardvendor)){
		String linkpath="http://"+EbeeConstantsF.get("serveraddress","www.beeport.com")+"/home/links";
		String helplink="(<a href=\"javascript:popupwindow('"+linkpath+"/cvvcodehelp.html','Tags','600','400')\">Where is my CVV Code?</a>)";

		sb.append("<tr><td><table width='100%' cellpading='0'><tr><td  width='110px' height='30' class='inputlabel'>CVV Code *<br/>"+helplink+"</td><td height='30' valign='top' class='inputvalue'>");
		sb.append(XffunctionHelper.getXfTextBox(BASE_REF+"/cvvcode",GenUtil.getEncodedXML(ccm.getCvvcode() ),"25"));
		sb.append("</td></tr></table></td></tr>");
		}
		/*sb.append("<tr><td height='30' class='inputlabel'>Expiration Month</td><td height='30' class='inputvalue'>");
		sb.append(XffunctionHelper.getXfSelectOneCombo(BASE_REF+"/expmonth",EventbeeStrings.monthvals,EventbeeStrings.months, GenUtil.getEncodedXML(ccm.getExpmonth() )    ));
		sb.append("</td></tr><tr><td height='30' class='inputlabel'>Expiration Year</td><td height='30' class='inputvalue'>");
		sb.append(XffunctionHelper.getXfSelectOneCombo(BASE_REF+"/expyear",YEARS,YEARS, GenUtil.getEncodedXML(ccm.getExpyear() )    ));
		sb.append("</td></tr>");*/
		sb.append("</table>");
	   
			if(ccm.getProfiledata()!=null){
				sb.append("<table class='block'>");
				sb.append("<tr><td colspan='2' style='font-family:Verdana,sans-serif;font-size:20px;font-weight:bold;'>Credit Card Billing Address</td></tr><tr><td width='110px' height='30' class='inputlabel'>First Name *</td><td   width='240' height='30' class='inputvalue'>");
				sb.append(XffunctionHelper.getXfTextBox(BASE_REF+"/profiledata/firstName",GenUtil.getEncodedXML(ccm.getProfiledata().getFirstName() ),"25"));
				sb.append("</td></tr><tr><td  class='inputlabel'>Last Name *</td><td height='30' class='inputvalue'>");
				sb.append(XffunctionHelper.getXfTextBox(BASE_REF+"/profiledata/lastName",GenUtil.getEncodedXML(ccm.getProfiledata().getLastName() ),"25"));
				sb.append("</td></tr><tr><td  class='inputlabel'>Email *</td><td height='30' class='inputvalue'>");
				sb.append(XffunctionHelper.getXfTextBox(BASE_REF+"/profiledata/email",GenUtil.getEncodedXML(ccm.getProfiledata().getEmail() ),"30"));
				sb.append("</td></tr><tr><td   class='inputlabel'>Organization</td><td height='30' class='inputvalue'>");
				sb.append(XffunctionHelper.getXfTextBox(BASE_REF+"/profiledata/company",GenUtil.getEncodedXML(ccm.getProfiledata().getCompany() ),"35"));
				sb.append("</td></tr><tr><td   class='inputlabel'>Street *</td><td height='30' class='inputvalue'>");
				sb.append(XffunctionHelper.getXfTextBox(BASE_REF+"/profiledata/street1",GenUtil.getEncodedXML(ccm.getProfiledata().getStreet1() ),"35"));
				sb.append("</td></tr><tr><td   class='inputlabel'>Apt/Suite</td><td height='30' class='inputvalue'>");
				sb.append(XffunctionHelper.getXfTextBox(BASE_REF+"/profiledata/street2",GenUtil.getEncodedXML(ccm.getProfiledata().getStreet2() ),"35"));
				sb.append("</td></tr><tr><td  class='inputlabel'>City *</td><td height='30' class='inputvalue'>");
				sb.append(XffunctionHelper.getXfTextBox(BASE_REF+"/profiledata/city",GenUtil.getEncodedXML(ccm.getProfiledata().getCity() ),"35"));
				sb.append("</td></tr><tr><td  class='inputlabel'>State *</td><td height='30' class='inputvalue'>");
				sb.append(XffunctionHelper.getXfSelectListBox(BASE_REF+"/profiledata/state",EventbeeStrings.getUSStateCodes(),EventbeeStrings.getUSStateNames(),  GenUtil.getEncodedXML(ccm.getProfiledata().getState() )    ));
	            sb.append("</td></tr><tr><td  class='inputlabel'>Country</td><td height='30' class='inputvalue'>");
				sb.append(XffunctionHelper.getXfSelectListBox(BASE_REF+"/profiledata/country",new String[]{"US"},new String[]{"USA"},  GenUtil.getEncodedXML(ccm.getProfiledata().getCountry() )      ));
				sb.append("</td></tr><tr><td  class='inputlabel'>Zip *</td><td height='30' class='inputvalue'>");
				sb.append(XffunctionHelper.getXfTextBox(BASE_REF+"/profiledata/zip",GenUtil.getEncodedXML(ccm.getProfiledata().getZip() ),"10"));
				sb.append("</td></tr><tr><td   class='inputlabel'>Phone * <br/><span style='color:#666666;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;font-weight:lighter;'>10 digits</span></td><td height='30' class='inputvalue'>");
				sb.append(XffunctionHelper.getXfTextBox(BASE_REF+"/profiledata/phone",GenUtil.getEncodedXML(ccm.getProfiledata().getPhone() ),"10"));
				sb.append("</td></tr><tr><td>");
				sb.append("</td></tr><tr><td>");
				sb.append("</td></tr></table>");
				sb.append("<input type='hidden' name='BASE_REF' value='"+BASE_REF+"' />");
				sb.append("</div>");
			}
		return sb.toString();

}
	public static String validatePayment(ListingOptionsData listingOptionsData, String eid,String BASE_REF){
		String result="";
		JSONObject object=new JSONObject();
		CreditCardModel ccm=new CreditCardModel();
		ProfileData m_ProfileData=new ProfileData();
		HashMap<String, String> hm=new HashMap<String, String>();
		String seqID=listingOptionsData.getSeqId();
		String m_cardamount=listingOptionsData.getTotalAmount();
		hm.put(CardConstants.REQUEST_APP,"UPGRADE_EVENT");
		hm.put(CardConstants.TRANSACTION_TYPE,CardConstants.TRANS_ONE_TIME);
		hm.put(CardConstants.BASE_REF,"/card");
		hm.put(CardConstants.LOGO_URL,"");
		hm.put(CardConstants.AUTH_POLICY,"");
		hm.put(CardConstants.AUTH_URL,"");
		hm.put(CardConstants.AMOUNT,""+m_cardamount);
		ccm.setParams(hm);
		ccm.setProfiledata(m_ProfileData);
		/*String BASE_REF=request.getParameter("BASE_REF");

		ccm.setCardtype(request.getParameter(BASE_REF+"/cardtype"));
		ccm.setCardnumber(request.getParameter(BASE_REF+"/cardnumber"));
		ccm.setCvvcode(request.getParameter(BASE_REF+"/cvvcode"));
		ccm.setExpmonth(request.getParameter(BASE_REF+"/expmonth"));
		ccm.setExpyear(request.getParameter(BASE_REF+"/expyear"));
		ccm.getProfiledata().setFirstName(request.getParameter(BASE_REF+"/profiledata/firstName"));
		ccm.getProfiledata().setLastName(request.getParameter(BASE_REF+"/profiledata/lastName"));
		ccm.getProfiledata().setEmail(request.getParameter(BASE_REF+"/profiledata/email"));
		ccm.getProfiledata().setCompany(request.getParameter(BASE_REF+"/profiledata/company"));
		ccm.getProfiledata().setStreet1(request.getParameter(BASE_REF+"/profiledata/street1"));
		ccm.getProfiledata().setStreet2(request.getParameter(BASE_REF+"/profiledata/street2"));
		ccm.getProfiledata().setCity(request.getParameter(BASE_REF+"/profiledata/city"));
		ccm.getProfiledata().setState(request.getParameter(BASE_REF+"/profiledata/state"));
		ccm.getProfiledata().setCountry(request.getParameter(BASE_REF+"/profiledata/country"));
		ccm.getProfiledata().setZip(request.getParameter(BASE_REF+"/profiledata/zip"));
		ccm.getProfiledata().setPhone(request.getParameter(BASE_REF+"/profiledata/phone"));

		StatusObj sobj= ccm.localValidate();

		Vector v=new Vector();
		String error="";
		if(sobj.getStatus()){
			v=(Vector)(sobj.getData());
		}

		if(v==null || v.size()==0){
		sobj= ccm.validate();
		if(sobj.getStatus()){
		if(sobj.getData() instanceof Vector)
		v =(Vector)(sobj.getData());
		else if(sobj.getData() instanceof String){
		error=(String)(sobj.getData());
		v.add("please try back later");

		}
		}
		}
		if(v==null || v.size()==0){
		DbUtil.executeUpdateQuery("update CCProcessing_details set status='Success', created_at='now()'" +
				"  where refid=to_number(?,'9999999999999') and regid=to_number(?,'99999999999999')",new String[]{eid,seqID});
		String premiumlisttype=DbUtil.getVal("select purpose from CCProcessing_details where refid=to_number(?,'9999999999999') and regid=to_number(?,'99999999999999')",new String[]{eid,seqID});
		DbUtil.executeUpdateQuery("update eventinfo set premiumlevel=?  where eventid=to_number(?,'9999999999999')",new String[]{premiumlisttype, eid});

		object.put("status","success");
		}else{
		JSONArray errorsArray=new JSONArray();
		if(v!=null&&v.size()>0){
		for(int i=0;i<v.size();i++){
		errorsArray.put(v.elementAt(i));
		}
		}
		object.put("status","error");
		object.put("errors",errorsArray);
		}

		object.put("eid",eid);	*/
		result=object.toString();
		return result;
	}

}
