package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.velocity.VelocityContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.event.dbhelpers.SpecialFeeDB;
import com.event.dbhelpers.TransactionDB;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.GenUtil;
import com.eventregister.customquestions.beans.AttribOption;
import com.eventregister.customquestions.beans.CustomAttribSet;
import com.eventregister.customquestions.beans.CustomAttribute;
import com.eventregister.customquestions.dbhelper.CustomAttribsDB;
import com.eventregister.dbhelper.ProfileActionDB;
import com.eventregister.dbhelper.RSVPProfileActionDB;
import com.eventregister.dbhelper.RSVPRegistrationConfirmationEmail;
import com.eventregister.dbhelper.RegistrationDBHelper;
import com.eventregister.dbhelper.RegistrationRSVPManager;
import com.opensymphony.xwork2.ActionContext;

public class SaveRSVPProfileDetailsAction {
	String eid="";
	String eventdate="";
	String transactionjsondata="";
	String responsejsondata="";
	String sure="";
	String custom_setid=null;
	String tid="";
	private String msgType = "addrsvpmanualattendee";
	ProfileActionDB profiledb=new ProfileActionDB();
	RegistrationRSVPManager regRsvpMgr=new RegistrationRSVPManager();
	CustomAttribsDB ticketcustomattribs=new CustomAttribsDB();
	
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getCustom_setid() {
		return custom_setid;
	}
	public void setCustom_setid(String custom_setid) {
		this.custom_setid = custom_setid;
	}
	
	public String getSure() {
		return sure;
	}
	public void setSure(String sure) {
		this.sure = sure;
	}
	
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	
	public String getEventdate(){
		return eventdate;
	}
	public void setEventdate(String eventdate){
		this.eventdate=eventdate;
	}
	
	public String getResponsejsondata() {
		return responsejsondata;
	}
	public void setResponsejsondata(String responsejsondata) {
		this.responsejsondata = responsejsondata;
	}
	public String getTransactionjsondata() {
		return transactionjsondata;
	}
	public void setTransactionjsondata(String transactionjsondata) {
		this.transactionjsondata = transactionjsondata;
	}
	
	public String execute(){
		System.out.println("save rsvp profiledata");
		ArrayList<String> qlist=new ArrayList();
		 qlist.add("fname");
		 qlist.add("lname");
		 qlist.add("email");
		 qlist.add("phone");
		try{
			// adding for special fee pro feature start
			String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
			SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP AddAttendee");
			// special fee end.
			CustomAttribSet customattribs=(CustomAttribSet)ticketcustomattribs.getCustomAttribSet(eid,"EVENT" );
			CustomAttribute[]  attributeSet=customattribs.getAttributes();
			tid=RSVPProfileActionDB.getTransactionId("RK");	
			custom_setid=DbUtil.getVal("select attrib_setid  from custom_attrib_master where groupid=CAST(? AS BIGINT) and purpose=?",new String []{eid,"EVENT"});
			ProcessTransactionLevelResponses(attributeSet,qlist,tid);
			ProcessResponseLevelResponses(attributeSet,qlist,tid);
			updateRSVPDb();
		}
		catch(Exception e){
			System.out.println("Exception in excute method SaveRSVPProfileDetailsAction-"+e.getCause());
		}
		 return "Success";
	}
	
	private void ProcessTransactionLevelResponses(CustomAttribute[] attributeSet,ArrayList qlist,String tid) {
		try{
			ArrayList transactionAttribs=null; 
			String bResponseId=null;
			String	shortresponse=null;
			String bigresponse=null;
			HashMap buyerResponseMasterMap=null;
			String profileid=DbUtil.getVal("select nextval('SEQ_attendeeid')",new String[]{});
			String profilekey="AK"+EncodeNum.encodeNum(profileid).toUpperCase();
			if(attributeSet!=null){
				transactionAttribs=RegistrationRSVPManager.getQuestionsFortransactionlevel(eid);
			}
			
			HashMap <String,String>translevelMap=new HashMap();
			HashMap <String,String>buyerCustomMap=new HashMap();
			if(transactionAttribs!=null&&transactionAttribs.size()>0){
				  buyerResponseMasterMap=new HashMap();
				  bResponseId=DbUtil.getVal("select nextval('attributes_survey_responseid')",null);
				  buyerResponseMasterMap.put("responseid",bResponseId);
				  buyerResponseMasterMap.put("profileid",profileid);
				  buyerResponseMasterMap.put("profilekey",profilekey);
				  buyerResponseMasterMap.put("ticketid","101");
				  buyerResponseMasterMap.put("eventid",eid);
				  buyerResponseMasterMap.put("tid",tid);
				  buyerResponseMasterMap.put("custom_setid",custom_setid);
				
			}
			
			JSONArray buyerjsonarray=new JSONArray(transactionjsondata);
		   for(int index=0;index<buyerjsonarray.length();index++){
				JSONObject buyerobj=(JSONObject)buyerjsonarray.get(index);
				String question=(String)buyerobj.get("qid");
				if(qlist.contains(question))
					translevelMap.put((String)buyerobj.get("qid"),(String)buyerobj.get("response"));
				else{
					String qid=(String)buyerobj.get("qid");
					for(int j=0;j<attributeSet.length;j++){
						CustomAttribute cb=(CustomAttribute)attributeSet[j];
						if(transactionAttribs.contains(cb.getAttribId())){
							String questionid=cb.getAttribId();
							if(qid.equals(cb.getAttribId())){
								String question1=cb.getAttributeName();
								String type=cb.getAttributeType();
								if("checkbox".equals(type)||"radio".equals(type)||"select".equals(type)){
									ArrayList options=cb.getAttriboptions();
									JSONArray responsesarray=(JSONArray)buyerobj.get("response");
									String responses[]=new String[responsesarray.length()];
									for(int p=0;p<responsesarray.length();p++){
										responses[p]=(String)responsesarray.get(p);	
									}
									String responsesVal[]=profiledb.getOptionVal(options,responses);
									shortresponse=GenUtil.stringArrayToStr(responses,",");
									bigresponse=GenUtil.stringArrayToStr(responsesVal,",");
								}
								else{
									shortresponse=(String)buyerobj.get("response");
									bigresponse=(String)buyerobj.get("response");
								}
								HashMap transResponse=new HashMap();
								transResponse.put("question",question1);
								transResponse.put("questionid",questionid);
								transResponse.put("shortresponse",shortresponse);
								transResponse.put("bigresponse",bigresponse);
								transResponse.put("responseid",bResponseId);
								profiledb.insertResponse(transResponse);
							}
						}
					}
				}
		}
			if(buyerResponseMasterMap!=null)
				profiledb.InsertResponseMaster(buyerResponseMasterMap);
			translevelMap.put("sure", sure);
			translevelMap.put("eventdate", eventdate);
			translevelMap.put("profileid", profileid);
			translevelMap.put("profilekey", profilekey);
			translevelMap.put("tid",tid);
			translevelMap.put("eventid",eid);
			profiledb.InserBuyerInfo(translevelMap);
			RSVPProfileActionDB.FillTransactionLevel(translevelMap);
			translevelMap.put("ticketid","101");
			translevelMap.put("tickettype","attendeeType");
			
			}
			catch(Exception e){
			System.out.println("Exception Occured is ----"+e.getMessage());	
			}
		
	}

	
	
	private void ProcessResponseLevelResponses(CustomAttribute[] attributeSet,ArrayList qlist,String tid) {
        
try {
	
	ArrayList responseAttribs=null; 
	HashMap profilebasinfo=null;
	profilebasinfo=new HashMap();;
	JSONObject profileobj=null;
	JSONArray responsejsonarray=new JSONArray(responsejsondata);
	//System.out.println("responsejsondata is:"+responsejsondata);
	//System.out.println("responsejsonarray is:"+responsejsonarray.toString());  
	if(attributeSet!=null){
		responseAttribs=RegistrationRSVPManager.getQuestionsFortheSelectedOption("101", eid);	
		}
	for(int pindex=0;pindex<responsejsonarray.length();pindex++){
		profileobj=(JSONObject)responsejsonarray.get(pindex);
		String qid=(String)profileobj.get("qid");
	    profilebasinfo.put(qid, profileobj.get("response"));	
	}
	
	HashMap responseprofile = new HashMap();
	HashMap profileid_keyMap =new HashMap();
	int surecount=Integer.parseInt(sure);
	responseprofile.put("eventid", eid);
	responseprofile.put("tid", tid);
	responseprofile.put("ticketid", "101");
	responseprofile.put("tickettype", "attendeeType");
	ArrayList questionslist=new ArrayList();
	
	for(int i=1;i<=surecount;i++){
		
		String  responseId=DbUtil.getVal("select nextval('attributes_survey_responseid')",null);
		String profileid=DbUtil.getVal("select nextval('SEQ_attendeeid')",new String[]{});
		String profilekey="AK"+EncodeNum.encodeNum(profileid).toUpperCase();
		profileid_keyMap.put("pid_"+i, profileid);
		profileid_keyMap.put("pkey_"+i, profilekey);
		profileid_keyMap.put("responseid_"+i, responseId);
		responseprofile.put("fname", profilebasinfo.get("fname_"+i));
		responseprofile.put("lname", profilebasinfo.get("lname_"+i));
		responseprofile.put("email", profilebasinfo.get("email_"+i));
		responseprofile.put("profileid",profileid);
		responseprofile.put("profilekey",profilekey);
		responseprofile.put("responseid", responseId);
		HashMap questionoriginal=new HashMap();
		questionoriginal=RSVPProfileActionDB.getQuestionOriginal(custom_setid);
		if(questionoriginal!=null){
			for(int k=0;k<responseAttribs.size();k++){
				CustomAttribute cb=null;
				String questionid=(String) responseAttribs.get(k);
				for(int kw=0;kw<attributeSet.length;kw++)
                { 
				  cb=(CustomAttribute)attributeSet[kw];
                  // System.out.println(kw+"***  attt  "+((CustomAttribute)attributeSet[kw]).getAttribId()+" questionid:"+questionid);
                   if(!questionid.equals(cb.getAttribId()+""))
                    {
                    //   System.out.println(kw+"***con  attt  "+((CustomAttribute)attributeSet[kw]).getAttribId()+" questionid:"+questionid);
                     continue;
                    }
                   else
                   {//   System.out.println(kw+"***break  attt  "+((CustomAttribute)attributeSet[kw]).getAttribId()+" questionid:"+questionid);
                     
                       break;                  
                   }
                }
				String qname=(String)questionoriginal.get(questionid);
				String type=DbUtil.getVal("select attribtype from custom_attribs where attrib_id=to_number(?,'999999999') and attrib_setid=(select attrib_setid  from custom_attrib_master where groupid=CAST(? AS BIGINT) and purpose='EVENT')", new String[]{questionid,eid});
				String shortresponse="";
				String bigresponse="";
				
				if("checkbox".equals(type)||"radio".equals(type)||"select".equals(type)){
					ArrayList options=cb.getAttriboptions();
					JSONArray responsesarray=(JSONArray)profilebasinfo.get(questionid+"_"+i);
					String responses[]=new String[responsesarray.length()];
					for(int p=0;p<responsesarray.length();p++){
						responses[p]=(String)responsesarray.get(p);	
					}
					String responsesVal[]=profiledb.getOptionVal(options,responses);
					shortresponse=GenUtil.stringArrayToStr(responses,",");
					bigresponse=GenUtil.stringArrayToStr(responsesVal,",");
					//shortresponse=bigresponse;
				}
				else{
					shortresponse=profilebasinfo.get(questionid+"_"+i).toString();
					bigresponse=profilebasinfo.get(questionid+"_"+i).toString();
				}
				
				
				responseprofile.put("questionid", questionid);
				//String res=profilebasinfo.get(questionid+"_"+i).toString();
				responseprofile.put("shortresponse",shortresponse);
				responseprofile.put("bigresponse",bigresponse);
				responseprofile.put("question",questionoriginal.get(questionid));
				profiledb.insertResponse(responseprofile);
				
			}
			
			responseprofile.put("ticketid","101");
			responseprofile.put("custom_setid",custom_setid);
			profiledb.InsertResponseMaster(responseprofile);
		}
		RSVPProfileActionDB.insertBaseProfile(responseprofile);
		RegistrationDBHelper regdbhelper=new RegistrationDBHelper();
		regdbhelper.InserMailingList(eid,responseprofile);
	}
	
		
}
catch (JSONException e) {
		// TODO Auto-generated catch block
	System.out.println("Exception in processing the responseprofile"+e.getMessage());	
	//e.printStackTrace();
	}
}//end of method

void updateRSVPDb(){
	
	/*String emailid_trac=DbUtil.getVal("select email from event_reg_transactions where tid=? and eventid=?", new String[]{tid,eid});
	VelocityContext context=new VelocityContext();
	context.put("attendee",sure);
	context.put("notSure","0");
	context.put("transactionKey",tid);
	context.put("eventdate",eventdate);
	context.put("eventreglink","");
	context.put("eventid",eid);
	context.put("attendingLabel","Attending");
	context.put("mayBeLabel","MayBe");
	RSVPProfileActionDB.getdetails(tid,eid,context,sure,"0");
	HashMap rsvpattendee=new HashMap();
	rsvpattendee.put("m_email",context.get("mgrEmail"));
	rsvpattendee.put("GROUPID",eid);
	rsvpattendee.put("emailid",emailid_trac);
	rsvpattendee.put("email",emailid_trac);*/
	
	
	//int r=RSVPRegistrationConfirmationEmail.sendRsvpEmail(rsvpattendee,context);
	TransactionDB.resendingMail(eid,tid,"RSVP");
}


}//end of class
