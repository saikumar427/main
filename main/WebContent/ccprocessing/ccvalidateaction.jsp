<%@ page import="org.json.*"%>
<%@ page import="java.util.*,com.eventbee.general.*" %>
<%@ page import="com.eventbee.creditcard.*" %>
<%@ page import="com.event.beans.ProfileData"%>
<%@ page import="com.eventbee.util.*" %>

<%
JSONObject object=new JSONObject();
CreditCardModel ccm=new CreditCardModel();
ProfileData m_ProfileData=new ProfileData();
HashMap hm=new HashMap();
String userid=request.getParameter("userId");
String seqID=request.getParameter("creditCardData.seqId");
String m_cardamount=request.getParameter("creditCardData.amount");
String purpose=request.getParameter("purpose");
System.out.println("purpose:;;;;"+purpose);
System.out.println("m_cardamount::::::::"+m_cardamount);
hm.put(CardConstants.REQUEST_APP,"BUY_MAIL_QUOTA");
hm.put(CardConstants.TRANSACTION_TYPE,CardConstants.TRANS_ONE_TIME);
hm.put(CardConstants.BASE_REF,"/card");
hm.put(CardConstants.LOGO_URL,"");
hm.put(CardConstants.AUTH_POLICY,"");
hm.put(CardConstants.AUTH_URL,"");
hm.put(CardConstants.AMOUNT,""+m_cardamount);
ccm.setParams(hm);
ccm.setProfiledata(m_ProfileData); 

String BASE_REF=request.getParameter("BASE_REF");

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
	System.out.println("enteres in v::::::::"+v.size());

	if("buymailquota".equals(purpose)){
	String qty="0";
	if("25".equals(m_cardamount)){
		qty="1000";
	}else if("40".equals(m_cardamount)){
		qty="2500";
	}else if("60".equals(m_cardamount)){
		qty="5000";
	}else{
		qty="10000";
	}
	String transactionid=DbUtil.getVal("select nextval('seq_transactionid') as transactionid", new String[] {});
	transactionid=EncodeNum.encodeNum(transactionid);
	String status=DbUtil.getVal("select creditMailQuota('13579',"+qty+",'"+userid+"','mail.quota.buy','MEM','C','"+transactionid+"')",null);
	}
DbUtil.executeUpdateQuery("update CCProcessing_details set status='Success', amount=?, created_at='now()'" +
		"  where refid=CAST(? AS BIGINT) and regid=to_number(?,'99999999999999')",new String[]{m_cardamount,userid,seqID});
String premiumlisttype=DbUtil.getVal("select purpose from CCProcessing_details where refid=CAST(? AS BIGINT) and regid=to_number(?,'99999999999999')",new String[]{userid,seqID});
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

object.put("userid",userid);
out.println(object.toString());

%>



