package com.eventregister.customquestions.dbhelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.eventbee.general.DBManager;
import com.eventbee.general.EventbeeLogger;
import com.eventbee.general.StatusObj;
import com.eventregister.customquestions.beans.AttribOption;
import com.eventregister.customquestions.beans.CustomAttribSet;
import com.eventregister.customquestions.beans.CustomAttribute;

public class CustomAttribsDBDup {
	
	private static final String GET_EVENT_ATTRIB_SET="select b.attrib_setid,a.* from custom_attribs a,"
		+"custom_attrib_master b"
		+" where a.attrib_setid=b.attrib_setid and b.groupid=CAST(? AS BIGINT) and b.purpose=? order by position ";
	
	private static final String attribIdSelectQuery="select b.attrib_setid,a.* from custom_attribs a,"
		+"custom_attrib_master b,subgroupattribs c"
		+" where a.attrib_setid=b.attrib_setid and b.groupid=CAST(? AS BIGINT) and b.purpose=? and "
		+" a.attribsetid=c.attribsetid and b.groupid=c.groupid and c.subgroupid=CAST(? AS INTEGER) order by position";

	private static final String GET_ATTRIB_OPTIONS="select * from custom_attrib_options"
		+" where attrib_setid=CAST(? AS INTEGER) and attrib_id=CAST(? AS INTEGER) order by position";
	

	public  CustomAttribSet getCustomAttribSet(String groupid,String purpose,String pid){
		//System.out.println("In CustomAttribsDBDup getCustomAttribSet profilekey: "+pid);
		ArrayList attribids=new ArrayList();
		ArrayList params=new ArrayList();
		CustomAttribSet custattset=new CustomAttribSet();
		DBManager dbmanager=new DBManager();
		HashMap hm=new HashMap();
		StatusObj statobj=null;
		String query=null;
		params.add(groupid);
		params.add(purpose);
		try{

			//if(subgroupid==null){
				query=GET_EVENT_ATTRIB_SET;
			/*}else{
				params.add(subgroupid);
				query=attribIdSelectQuery;
			}*/
	
			statobj=dbmanager.executeSelectQuery(query,(String[])params.toArray(new String [params.size()]));
			int count=statobj.getCount();
			if(statobj.getStatus()&&count>0){
				CustomAttribute[] attribs=new CustomAttribute[count];
				String attribsetid=dbmanager.getValue(0,"attrib_setid","");
		
				custattset.setAttribSetid(attribsetid);
				for (int k=0;k<count;k++){
					CustomAttribute attrib= new CustomAttribute();
					String attribid=dbmanager.getValue(k,"attrib_id","");
					String rows=dbmanager.getValue(k,"rows","");
					String cols=dbmanager.getValue(k,"cols","");
					String textboxsize=dbmanager.getValue(k,"textboxsize","");
					String attribname=dbmanager.getValue(k,"attribname","");
					String attribtype=dbmanager.getValue(k,"attribtype","");
					String isreq=dbmanager.getValue(k,"isrequired","");
					String position=dbmanager.getValue(k,"position","");
					String lastupdated=dbmanager.getValue(k,"lastupdated","");
					attrib.setAttribId(attribid);
					attrib.setIsRequired(isreq);
					attrib.setTextboxSize(textboxsize);
					attrib.setRows(rows);
					attrib.setCols(cols);
					attrib.setPosition(position);
					attrib.setAttributeName(attribname);
					attrib.setAttributeType(attribtype);
					attrib.setLastUpdated(lastupdated);
					
					if("select".equals(attribtype) || "checkbox".equals(attribtype) || "radio".equals(attribtype)){
						HashMap optionsOfAttribs=getCustomAttribSetOptions(attribsetid,attribid,attribtype,pid);
						if(optionsOfAttribs.containsKey((Object)attribid)){
							attrib.setAttriboptions( (ArrayList) optionsOfAttribs.get(attribid));
						}
					}
					
					attribs[k]=attrib;
				}
				custattset.setAttributes(attribs);
			}else{
				//There is an error in query execution
				EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"CustomAttribsDBDup.java","getCustomAttribSet()","statobj.getStatus() Error in selection of records"+statobj.getStatus(),null);
			}
		}catch(Exception e){
			EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "CustomAttribsDBDup.java", "getCustomAttribSet()", e.getMessage(), e ) ;
		}
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"CustomAttribsDBDup.java","getCustomAttribSet()","Exited from method getCustomAttribSet()",null);
		
		return custattset;
	}//End of getCustomAttribSet()

	public  HashMap getCustomAttribSetOptions(String attribsetid,String attribid,String attribtype,String pid){
		HashMap attribOptionsMap=new HashMap();
		DBManager dbmanager=new DBManager();
			HashMap hm=new HashMap();
			StatusObj statobj=null;
			StatusObj statobj2=null;
			ArrayList options=new ArrayList();
			ArrayList attriboptions=new ArrayList();

			try{
				statobj=dbmanager.executeSelectQuery(GET_ATTRIB_OPTIONS,new String []{attribsetid,attribid});
				EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"CustomAttribsDBDup.java","getCustomAttribSetOptions()","statobj.getStatus()-->"+statobj.getStatus(),null);
				int count=statobj.getCount();
				if(statobj.getStatus()&&count>0){
					for (int k=0;k<count;k++){
						//String attribid=dbmanager.getValue(k,"attrib_id","");
						String position=dbmanager.getValue(k,"position","");
						String optionid=dbmanager.getValue(k,"option","");
						String optvalue=dbmanager.getValue(k,"option_val","");
						AttribOption cop=new AttribOption();
						cop.setPosition(position);
						cop.setOptionid(optionid);
						cop.setOptionValue(optvalue);
					    options.add(cop);
						attribOptionsMap.put(attribid, options);
						attriboptions.add(optionid);
					} 
				}	
					//if("select".equals(attribtype)){
						//System.out.println("attribid: "+attribid+" attribtype: "+attribtype+" options first size: "+options.size());
						String query = "select shortresponse,bigresponse from custom_questions_response cr, " +
						"custom_questions_response_master cm where cm.ref_id=cr.ref_id and shortresponse<>'' " +
						"and cr.attribid=CAST(? AS INTEGER) and cm.attribsetid=? and cm.profilekey=?";
						statobj2=dbmanager.executeSelectQuery(query,new String []{attribid,attribsetid,pid});
						if(statobj2.getStatus()&&statobj2.getCount()>0){
							//for (int i=0;i<statobj2.getCount();i++){
								String shortresponse=dbmanager.getValue(0,"shortresponse","");
								String bigresponse=dbmanager.getValue(0,"bigresponse","");
								
								
								if("checkbox".equals(attribtype)){
									List<String> checkboxshortres = Arrays.asList( shortresponse.split(",") );
									List<String> checkboxbigres = Arrays.asList( bigresponse.split(",") );
									for(int i=0;i<checkboxshortres.size();i++){
										if(!attriboptions.contains(checkboxshortres.get(i))){
											AttribOption cop=new AttribOption();
											cop.setPosition("");
											cop.setOptionid(checkboxshortres.get(i));
											cop.setOptionValue(checkboxbigres.get(i));
											options.add(cop);
											attribOptionsMap.put(attribid, options);
										}
									}
									
								}else{
									if(!attriboptions.contains(shortresponse)){
										AttribOption cop=new AttribOption();
										cop.setPosition("");
										cop.setOptionid(shortresponse);
										cop.setOptionValue(dbmanager.getValue(0,"bigresponse",""));
										options.add(cop);
										attribOptionsMap.put(attribid, options);
									}
								}
									
							//}
						
						}
						//System.out.println("attribid: "+attribid+" attribtype: "+attribtype+" options final size: "+options.size());
					//}
					
				/*}else{
					//There is an error in query execution
					EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"CustomAttribsDB.java","getCustomAttribSetOptions()","statobj.getStatus() Error in selection of records"+statobj.getStatus(),null);
				}*/
			}catch(Exception e){
				EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "CustomAttribsDBDup.java", "getCustomAttribSetOptions()", e.getMessage(), e ) ;
			}
		return attribOptionsMap;
		} //End of getCustomAttribSetOptions()

}
