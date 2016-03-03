package com.eventregister.dbhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.json.*;
import com.eventregister.customquestions.beans.CustomAttribute;
import com.event.dbhelpers.DisplayAttribsDB;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.I18NCacheData;
import com.eventbee.general.StatusObj;
import com.eventbee.general.GenUtil;
import com.eventregister.customquestions.beans.AttribOption;




public class RegistrationRSVPManager {
	

	private  String eid="";
	public  String getEid() {
		return eid;
	}
	public  void setEid(String eid) {
		this.eid = eid;
	}
	
	public Vector getRSVPRecurringDates(String eventid){
		 Vector vec=new Vector();
		 String query="select date_display as evt_start_date from event_dates where eventid=CAST(? AS BIGINT) order by (zone_startdate+cast(cast(to_timestamp(COALESCE(zone_start_time,'00'),'HH24:MI:SS aaa') as text) as time ))";
		 DBManager db=new DBManager();  
		   String str=null;
		   StatusObj stob=db.executeSelectQuery(query,new String[]{eventid} );
		   if(stob.getStatus()){
		   for(int i=0;i<stob.getCount();i++){
		   vec.add(db.getValue(i,"evt_start_date",""));
		   }
		   }
		 return vec;
	
	}
	public String attendeeLabel(String eid){
		HashMap profilelabel=DisplayAttribsDB.getAttribValues(eid,"RSVPFlowWordings");
		String attendingLabel=GenUtil.getHMvalue(profilelabel,"event.reg.response.attending.label","Attending");
		return attendingLabel;
	}
	
	 public  String getVelocityTemplate(String eid,String purpose){
	    	String template="";
	    	try{
	    	template=DbUtil.getVal("select content from reg_flow_templates where eid=CAST(? AS BIGINT) and purpose=?",new String[]{eid,purpose});

	    	if(template==null)
	    	template=DbUtil.getVal("select content from reg_flow_templates where  purpose=?",new String[]{purpose});
	    	}
	    	catch(Exception e){

	    	System.out.println("Exception in getVelocityTemplate"+e.getMessage());
	    	}

	    	return template;

	    	}
	 public static ArrayList getQuestionsFortheSelectedOption(String option,String eventid){

		 String query="select attribid from subgroupattribs where groupid=CAST(? AS BIGINT) and subgroupid=to_number(?,'999999999')";
		 ArrayList attribsList=new ArrayList();
		 DBManager db=new DBManager();
		 StatusObj sb=db.executeSelectQuery(query,new String[]{eventid,option});
		 if(sb.getStatus()){
		 for(int i=0;i<sb.getCount();i++){
		 attribsList.add(db.getValue(i,"attribid",""));
		 }
		 }
		 return attribsList;
		 }

	 

		 public static ArrayList getQuestionsFortransactionlevel(String eventid){
		 	
		 	String query="select attribid from buyer_custom_questions where eventid=CAST(? AS BIGINT)";
		 	ArrayList attribsList=new ArrayList();
		 	DBManager db=new DBManager();
		 	StatusObj sb=db.executeSelectQuery(query,new String[]{eventid});
		 	if(sb.getStatus()){
		 		for(int i=0;i<sb.getCount();i++){
		 			attribsList.add(db.getValue(i,"attribid",""));
		 		}
		 	}
		 	
		 	return attribsList;


		 }
		 
		 void fillCustomQuestions(CustomAttribute[] attributeSet,ArrayList customQuestions,JSONArray questionsarray,JSONObject profilesetObject,String pattern){
			 HashMap<String, String> regFlowWordings=new HashMap<String, String>();	
			 try{
					HashMap<String, String> hm= new HashMap<String,String>();
					hm.put("module", "RSVPFlowWordings");
					regFlowWordings=DisplayAttribsDB.getDisplayAttribs(hm, I18NCacheData.getI18NLanguage(eid), eid);
					//System.out.println("regFlowWordings : "+regFlowWordings);
			    if(attributeSet!=null&&attributeSet.length>0){
				if(customQuestions!=null&&customQuestions.size()>0){
					for(int k=0;k<attributeSet.length;k++){
						CustomAttribute cb=(CustomAttribute)attributeSet[k];
                        JSONObject attributesObj=new JSONObject();
					    if(customQuestions.contains(cb.getAttribId())){
							attributesObj.put("qType",cb.getAttributeType());
							attributesObj.put("qId",cb.getAttribId());
							attributesObj.put("lblText",cb.getAttributeName());
							attributesObj.put("type",cb.getAttributeType());
							attributesObj.put("Required","Required".equals(cb.getIsRequired())?"Y":"N");
							attributesObj.put("ErrorMsg",GenUtil.getHMvalue(regFlowWordings,"event.reg.profile.error.message","Required"));
							attributesObj.put("StarColor","red");
							attributesObj.put("Id",cb.getAttribId());
							String attrib_setid=cb.getAttribSetid();
							if("text".equals(cb.getAttributeType()))
							attributesObj.put("textboxsize",cb.getTextboxSize());
							else if("textarea".equals(cb.getAttributeType())){
							attributesObj.put("rows",cb.getRows());
							attributesObj.put("cols",cb.getCols());
						}
						else{
							ArrayList  customattribOptions=cb.getAttriboptions();
							if(customattribOptions!=null && customattribOptions.size()>0){
								JSONArray optionsArrayObj=new JSONArray();
								for(int p=0;p<customattribOptions.size();p++){
									AttribOption option=(AttribOption)customattribOptions.get(p);
									JSONObject optionObj=new JSONObject();

									optionObj.put("Display",option.getOptionValue());
									optionObj.put("Value",option.getOptionid());
									optionsArrayObj.put(optionObj);
								}
								if("select".equals(cb.getAttributeType()))
									attributesObj.put("Validate","N");

								attributesObj.put("Options",optionsArrayObj);
								
							}
						}
						questionsarray.put(cb.getAttribId());
						
						profilesetObject.put(pattern+cb.getAttribId(),attributesObj);
							
						}
					}
				}
			}
                }
				catch(Exception e){
				
				}
			}
		 
}
