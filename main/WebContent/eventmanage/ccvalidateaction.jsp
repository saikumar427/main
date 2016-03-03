<%@ page import="org.json.*"%>
<%@ page import="java.util.*,com.eventbee.general.*" %>
<%@ page import="com.eventbee.creditcard.*" %>
<%@ page import="com.event.beans.ProfileData"%>
<%@ page import="com.eventbee.util.*" %>
<%!
 final String TRANSACTION_SEQ_GET="select nextval('seq_transactionid') as transactionid ";
%>
<%
JSONObject object=new JSONObject();
CreditCardModel ccm=new CreditCardModel();
ProfileData m_ProfileData=new ProfileData();
HashMap hm=new HashMap();
String eid=request.getParameter("eid");
String seqID=request.getParameter("listingOptionsData.seqId");
String m_cardamount=request.getParameter("listingOptionsData.totalAmount");
String tranid=DbUtil.getVal(TRANSACTION_SEQ_GET,null);
String tid=EncodeNum.encodeNum(tranid);
hm.put(CardConstants.INTERNAL_REF,tid);
hm.put(CardConstants.REQUEST_APP,"UPGRADE_EVENT");
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
DbUtil.executeUpdateQuery("update CCProcessing_details set status='Success', created_at='now()'" +
		"  where refid=to_number(?,'9999999999999') and regid=to_number(?,'99999999999999')",new String[]{eid,seqID});
String premiumlisttype=DbUtil.getVal("select purpose from CCProcessing_details where refid=to_number(?,'9999999999999') and regid=to_number(?,'99999999999999')",new String[]{eid,seqID});
DbUtil.executeUpdateQuery("update eventinfo set premiumlevel=?  where eventid=CAST(? AS BIGINT)",new String[]{premiumlisttype, eid});

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

object.put("eid",eid);
out.println(object.toString());

%>



