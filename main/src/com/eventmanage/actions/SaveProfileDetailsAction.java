package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.eventregister.customquestions.beans.CustomAttribSet;
import com.eventregister.customquestions.beans.CustomAttribute;
import com.eventregister.customquestions.dbhelper.CustomAttribsDB;
import com.eventregister.dbhelper.ProfileActionDB;
import com.eventregister.dbhelper.RegistrationDBHelper;
import com.eventregister.dbhelper.RegistrationTiketingManager;
import com.opensymphony.xwork2.ActionSupport;

public class SaveProfileDetailsAction  extends ActionSupport{
	String eid="";
	String tid="";
	String profilejsondata="";
	String buyerjsondata="";
	String custom_setid=null;
	String paytype="";
	String seatingenabled="";
	String eventdate="";
	String ticketids="";
	HashMap seatingtickets=null;
	public String getTicketids() {
		return ticketids;
	}
	public void setTicketids(String ticketids) {
		this.ticketids = ticketids;
	}
	public String getEventdate() {
		return eventdate;
	}
	public void setEventdate(String eventdate) {
		this.eventdate = eventdate;
	}
	public String getSeatingenabled() {
		return seatingenabled;
	}
	public void setSeatingenabled(String seatingenabled) {
		this.seatingenabled = seatingenabled;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getCustom_setid() {
		return custom_setid;
	}
	public void setCustom_setid(String custom_setid) {
		this.custom_setid = custom_setid;
	}

	ProfileActionDB profiledb=new ProfileActionDB();
	RegistrationTiketingManager regTktMgr=new RegistrationTiketingManager();
	CustomAttribsDB ticketcustomattribs=new CustomAttribsDB();
	
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getProfilejsondata() {
		return profilejsondata;
	}
	public void setProfilejsondata(String profilejsondata) {
		this.profilejsondata = profilejsondata;
	}
	public String getBuyerjsondata() {
		return buyerjsondata;
	}
	public void setBuyerjsondata(String buyerjsondata) {
		this.buyerjsondata = buyerjsondata;
	}
	HashMap <String,String>buyerMap=new HashMap(); 	
	public String execute(){
		System.out.println("SaveProfileDetailsAction eid: "+eid+" tid: "+tid);
		profiledb.deletePreviousData(tid);
		ArrayList<String> qlist=new ArrayList();
		 qlist.add("fname");
		 qlist.add("lname");
		 qlist.add("email");
		 qlist.add("phone");
		 CustomAttribSet customattribs=(CustomAttribSet)ticketcustomattribs.getCustomAttribSet(eid,"EVENT" );
		 CustomAttribute[]  attributeSet=customattribs.getAttributes();
		 custom_setid=DbUtil.getVal("select attrib_setid  from custom_attrib_master where groupid=CAST(? AS BIGINT) and purpose=?",new String []{eid,"EVENT"});
		 ProcessBuyerResponses(attributeSet,qlist);
		 ProcessProfileResponses(attributeSet,qlist);
		 regTktMgr.updatePaytype(tid, "payment section");
		 updateProfilekeysForNonAttendeeTickets();
		return "Success";
	}
	
	
	void ProcessBuyerResponses(CustomAttribute[] attributeSet,ArrayList qlist){
		try{
		ArrayList buyerAttribs=null; 
		String bResponseId=null;
		String	shortresponse=null;
		String bigresponse=null;
		HashMap buyerResponseMasterMap=null;
		String profileid=DbUtil.getVal("select nextval('SEQ_attendeeid')",new String[]{});
		String profilekey="AK"+EncodeNum.encodeNum(profileid).toUpperCase();
		if(attributeSet!=null){
         buyerAttribs=regTktMgr.getBuyerSpecificAttribs(eid);
	     }
		//HashMap <String,String>buyerMap=new HashMap();
		HashMap <String,String>buyerCustomMap=new HashMap();
		if(buyerAttribs!=null&&buyerAttribs.size()>0){
			buyerResponseMasterMap=new HashMap();
			bResponseId=DbUtil.getVal("select nextval('attributes_survey_responseid')",null);
			buyerResponseMasterMap.put("responseid",bResponseId);
			buyerResponseMasterMap.put("profileid",profileid);
			buyerResponseMasterMap.put("profilekey",profilekey);
			buyerResponseMasterMap.put("ticketid","0");
			buyerResponseMasterMap.put("eventid",eid);
			buyerResponseMasterMap.put("tid",tid);
			buyerResponseMasterMap.put("custom_setid",custom_setid);
			//profiledb.InsertResponseMaster(buyerResponseMasterMap);
		}
		
		JSONArray buyerjsonarray=new JSONArray(buyerjsondata);
		for(int index=0;index<buyerjsonarray.length();index++){
			JSONObject buyerobj=(JSONObject)buyerjsonarray.get(index);
			String question=(String)buyerobj.get("qid");
			
			
			if(qlist.contains(question))
				buyerMap.put((String)buyerobj.get("qid"),(String)buyerobj.get("response"));
			else{
				String qid=(String)buyerobj.get("qid");
				for(int j=0;j<attributeSet.length;j++){
					CustomAttribute cb=(CustomAttribute)attributeSet[j];
					if(buyerAttribs.contains(cb.getAttribId())){
						String questionid=cb.getAttribId();
						if(qid.equals(cb.getAttribId())){
							String question1=cb.getAttributeName();
							String type=cb.getAttributeType();
							if("checkbox".equals(type)||"radio".equals(type)||"select".equals(type)){
								ArrayList options=cb.getAttriboptions();
								try{
									JSONArray responsesarray=(JSONArray)buyerobj.get("response");
									String responses[]=new String[responsesarray.length()];
									for(int p=0;p<responsesarray.length();p++){
										responses[p]=(String)responsesarray.get(p);	
									}
									String responsesVal[]=profiledb.getOptionVal(options,responses);
									shortresponse=GenUtil.stringArrayToStr(responses,",");
									bigresponse=GenUtil.stringArrayToStr(responsesVal,",");
								}catch(Exception e){
									System.out.println("::::: Exception in AddAttendee SaveProfileDetails ProcessBuyerResponses()---->(JSONArray)buyerobj.get(response) :::: "+e.getMessage());
									System.out.println("::::: eid: "+eid+" tid: "+tid+" buyerjsondata: "+buyerjsondata);
								}
							}else{
								shortresponse=(String)buyerobj.get("response");
								bigresponse=(String)buyerobj.get("response");
							}
							HashMap buyerResponse=new HashMap();
							buyerResponse.put("question",question1);
							buyerResponse.put("questionid",questionid);
							buyerResponse.put("shortresponse",shortresponse);
							buyerResponse.put("bigresponse",bigresponse);
							buyerResponse.put("responseid",bResponseId);
							//System.out.println("buyerResponse"+buyerResponse);
							profiledb.insertResponse(buyerResponse);
						}
					}
				}
			}
		}
		if(buyerResponseMasterMap!=null)
			profiledb.InsertResponseMaster(buyerResponseMasterMap);
		
		buyerMap.put("profileid", profileid);
		buyerMap.put("profilekey", profilekey);
		buyerMap.put("tid",tid);
		buyerMap.put("eventid",eid);
		profiledb.InserBuyerInfo(buyerMap);
		}
		catch(Exception e){
			System.out.println("::::: Exception occured in buyer insertion: "+e.getMessage());
			System.out.println("::::: eid: "+eid+" tid: "+tid+" buyerjsondata: "+buyerjsondata);
		}
		
	}

	void ProcessProfileResponses(CustomAttribute[] attributeSet,ArrayList qlist){
		
		try{
		HashMap profilebasinfo=null;
		String seatcode="";
		String seatindex="";
		//System.out.println("::::: eid: "+eid+" tid: "+tid+" profilejsondata: "+profilejsondata);
		 JSONArray profileArray=new JSONArray(profilejsondata);	
		 HashMap ticketspecificAttributeIds=ticketcustomattribs.getTicketLevelAttributes(eid);
		 if("YES".equals(seatingenabled)){
		  	seatingtickets=regTktMgr.getSeatingCodeDetails(eid,tid,eventdate,ticketids);
		  }
		 for(int pindex=0;pindex<profileArray.length();pindex++){
		 JSONObject profileobj=(JSONObject)profileArray.get(pindex);
		 String ticketid=(String)profileobj.get("ticketid");
		 String tickettype=(String)profileobj.get("tickettype");
		  //JSONArray questions=(JSONArray)profileobj.get("questions");
		 profilebasinfo=new HashMap();
		 HashMap profileresponseMaster=null;
		 String pResponseid=null;
		 String profileid=DbUtil.getVal("select nextval('SEQ_attendeeid')",new String[]{});
		 String profilekey="AK"+EncodeNum.encodeNum(profileid).toUpperCase();
		 if(ticketspecificAttributeIds.containsKey(ticketid)){
		 pResponseid=DbUtil.getVal("select nextval('attributes_survey_responseid')",null);	 
		 profileresponseMaster=new HashMap();
		 profileresponseMaster.put("tid", tid);
		 profileresponseMaster.put("profileid",profileid);
		 profileresponseMaster.put("profilekey",profilekey);
		 profileresponseMaster.put("responseid",pResponseid);
		 profileresponseMaster.put("ticketid",ticketid);
		 profileresponseMaster.put("eventid",eid);
		 profileresponseMaster.put("custom_setid",custom_setid);
		 //System.out.println("profileresponseMaster"+profileresponseMaster);
		 profiledb.InsertResponseMaster(profileresponseMaster);
		 }
		 try{
			 JSONArray questions=(JSONArray)profileobj.get("questions");
			 for(int q=0;q<questions.length();q++){
				 JSONObject qobj=(JSONObject)questions.get(q);
				 String qid=(String)qobj.get("qid");
				 if(qlist.contains(qid)){
					 profilebasinfo.put(qid, (String)qobj.get("response"));	
				 }else{
					for(int j=0;j<attributeSet.length;j++){
						CustomAttribute cb=(CustomAttribute)attributeSet[j];
						String questionid=cb.getAttribId();
						String bigresponse=null;
						String shortresponse=null;
						if(qid.equals(cb.getAttribId())){
							String question1=cb.getAttributeName();
							String type=cb.getAttributeType();
							if("checkbox".equals(type)||"radio".equals(type)||"select".equals(type)){
								ArrayList options=cb.getAttriboptions();
								try{	
									JSONArray responsesarray=(JSONArray)qobj.get("response");
									String responses[]=new String[responsesarray.length()];
									for(int p=0;p<responsesarray.length();p++){
										responses[p]=(String)responsesarray.get(p);	
									}
									String responsesVal[]=profiledb.getOptionVal(options,responses);
									shortresponse=GenUtil.stringArrayToStr(responses,",");
									bigresponse=GenUtil.stringArrayToStr(responsesVal,",");
								}catch(Exception e){
									System.out.println("::::::: Exception in AddAttendee SaveProfileDetails ProcessProfileResponses()---> JSONArray responsesarray=(JSONArray)qobj.get(response):::: "+e.getMessage());
									System.out.println("::::: eid: "+eid+" tid: "+tid+" profilejsondata: "+profilejsondata);
								}
							}else{
									shortresponse=(String)qobj.get("response");
									bigresponse=(String)qobj.get("response");
							}
							HashMap profileResponseMap=new HashMap();
							profileResponseMap.put("question",question1);
							profileResponseMap.put("questionid",questionid);
							profileResponseMap.put("shortresponse",shortresponse);
							profileResponseMap.put("bigresponse",bigresponse);
							profileResponseMap.put("responseid",pResponseid);
							//System.out.println("profileResponseMap"+profileResponseMap);
							profiledb.insertResponse(profileResponseMap);
					       }	
					}
			}
		}
		 }catch(Exception e){
				System.out.println("::::::: Exception in AddAttendee SaveProfileDetails ProcessProfileResponses()--->(JSONArray)profileobj.get(questions) ::::::: "+e.getMessage());
				System.out.println("::::::: eid: "+eid+" tid: "+tid+" profilejsondata: "+profilejsondata);
		}
		if(profilebasinfo.size()==0&&ticketspecificAttributeIds.containsKey(ticketid)){
			profilebasinfo.put("fname","");	
			profilebasinfo.put("lname","");	
			profilebasinfo.put("email","");	
			profilebasinfo.put("phone","");	
			}
		if(profilebasinfo!=null&&profilebasinfo.size()>0){
			try{
				ArrayList seatcodes=(ArrayList) seatingtickets.get(ticketid);
				ArrayList seatindeces=(ArrayList) seatingtickets.get(ticketid+"_index");
				seatcode=(String)seatcodes.get(0);
				seatindex=(String)seatindeces.get(0);
				seatcodes.remove(0);
				seatindeces.remove(0);
			
			}catch(Exception e){
				System.out.println(":::: caught exception in ProcessProfileResponses(): "+e.getMessage());
				seatcode="";
				seatindex="";
			}
		profilebasinfo.put("eventid",eid);
		profilebasinfo.put("tid",tid);
		profilebasinfo.put("tickettype",tickettype);
		profilebasinfo.put("tickettype",tickettype);
		profilebasinfo.put("profileid",profileid);
		profilebasinfo.put("profilekey",profilekey);
		profilebasinfo.put("ticketid",ticketid);
		profilebasinfo.put("seatcode", seatcode);
		profilebasinfo.put("seatindex", seatindex);
		}
		/*for(int i=0;i<qlist.size();i++){
			String q=(String)profilebasinfo.get(qlist.get(i));
			if(q==null || "".equals(q)){
				profilebasinfo.put(qlist.get(i),buyerMap.get(qlist.get(i)));
			}
		}*/
		if ((profilebasinfo.get("fname") == null || "".equals(profilebasinfo.get("fname"))) && (profilebasinfo.get("lname") == null || "".equals(profilebasinfo.get("lname")))){
			profilebasinfo.put("fname", buyerMap.get("fname"));
			profilebasinfo.put("lname", buyerMap.get("lname"));
		}
		if(profilebasinfo.get("email") == null || "".equals(profilebasinfo.get("email"))){
			profilebasinfo.put("email", buyerMap.get("email"));
		}
		if(profilebasinfo.get("phone") == null || "".equals(profilebasinfo.get("phone"))){
			profilebasinfo.put("phone", buyerMap.get("phone"));
		}
		 profiledb.updateBaseProfile(profilebasinfo);
		 }
		}
		 
		 
		
		catch(Exception e){
			System.out.println("::::::: Exception in profile insertion: "+e.getMessage());
			System.out.println("::::::: eid: "+eid+" tid: "+tid+" profilejsondata: "+profilejsondata);
		}
		
	
	}
	
	/*void updatePaytype(){
	StatusObj sb=DbUtil.executeUpdateQuery("update event_reg_details_temp set current_action=? where tid=?",new String[]{"payment section",tid});	
		
	}*/
	
	void updateProfilekeysForNonAttendeeTickets(){
		profiledb.updateNonAttendeeTicketsprofiles(tid,eid,seatingtickets);
		
	}
	
	
}
	


