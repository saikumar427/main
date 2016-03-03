package com.eventregister.customquestions.dbhelper;

import java.util.ArrayList;
import java.util.HashMap;

import com.eventregister.customquestions.beans.CustomAttribute;
import com.eventbee.general.DBManager;
import com.eventbee.general.DBQueryObj;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.EventbeeLogger;
import com.eventbee.general.StatusObj;
import com.eventregister.customquestions.beans.AttribOption;
import com.eventregister.customquestions.beans.CustomAttribResponse;
import com.eventregister.customquestions.beans.CustomAttribSet;

@SuppressWarnings("unchecked")
public class CustomAttribsDB {
	
	private static final String INSERT_CUSTOM_ATTRIB_MASTER="insert into custom_attrib_master"
		+" (attribsetid,groupid ,purpose ,created) values(?,?,?,now()) ";

	private static final String INSERT_ATTRIBUTES="insert into custom_attribs"
		+" (attrib_setid,attrib_id ,attribname,attribtype,isrequired,textboxsize,rows,cols,"
		+"  position,lastupdated)"
		+" values(?,?,?,?,?,?,?,?,?,now())";

	private static final String GET_EVENT_ATTRIB_SET="select b.attrib_setid,a.* from custom_attribs a,"
		+"custom_attrib_master b"
		+" where a.attrib_setid=b.attrib_setid and b.groupid=to_number(?,'9999999999') and b.purpose=? order by position ";
	private static final String attribIdSelectQuery="select b.attrib_setid,a.* from custom_attribs a,"
		+"custom_attrib_master b,subgroupattribs c"
		+" where a.attrib_setid=b.attrib_setid and b.groupid=to_number(?,'9999999999') and b.purpose=? and "
		+" a.attribsetid=c.attribsetid and b.groupid=c.groupid and c.subgroupid=to_number(?,'9999999999') order by position";

	private static final String GET_ATTRIB_DETAILS="select * from custom_attribs where attrib_id=?";

	private static final String GET_ATTRIB_OPTIONS="select * from custom_attrib_options"
		+" where attrib_setid=to_number(?,'9999999999') and attrib_id=to_number(?,'9999999999') order by position";

	private static final String GET_ATTRIB_RESPONSE="select a.* from custom_questions_response a,"
		+"custom_questions_response_master b where b.groupid=? and b.subgroupid=? and b.transactionid=?"
		+" and b.profileid=? and b.purpose=? and a.ref_id=b.ref_id";

	private static final String INSERT_RESPONSE_MASTER="insert into custom_questions_response"
		+"(groupid,subgroupid,transactionid,profileid,attribsetid,ref_id,status) values "
		+"(?,?,?,?,?,?,1)";
	private static final String DELETE_RESPONSE="delete from custom_questions_response where ref_id=?";

	private static final String DELETE_RESPONSE_MASTER="delete from custom_questions_response_master where ref_id=?";

	private static final String INSERT_RESPONSE="insert into custom_questions_response"
		+" (ref_id,attribid,option_id,shortresponse,bigresponse,question_shortform,question_original,"
		+" optionval,optiondisplay) values (?,?,?,?,?,?,?,?,?)";

	private final static String GETATTR_FOR_SETID="select question_original from custom_questions_response a,custom_questions_response_master b"
		+" where a.ref_id =b.ref_id  and b.attribsetid=? ";

	private final static String RESPONSE_QUERY="select a.* from custom_questions_response a,"
		+" custom_questions_response_master b where b.attribsetid=? and a.ref_id=b.ref_id order by ref_id";

	private static final String DELETE_ATTRIBUTES="delete from custom_attribs where attrib_setid=?";


	private static final String DELETE_OPTIONS="delete from custom_attrib_options where attrib_setid=?";

	private static final String INSERT_OPTIONS="insert into custom_attrib_options"
		+" (attrib_setid ,attrib_id ,option_id,position,"
		+ "option_val,created,lastupdated)"
		+" values(?,?,?,?,?,now(),now())";


	public  HashMap getCustomAttribSetOptions(String attribsetid,String attribid){
	HashMap attribOptionsMap=new HashMap();
	DBManager dbmanager=new DBManager();
		HashMap hm=new HashMap();
		StatusObj statobj=null;
		ArrayList options=new ArrayList();


		try{
		statobj=dbmanager.executeSelectQuery(GET_ATTRIB_OPTIONS,new String []{attribsetid,attribid});
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"CustomAttribsDB.java","getCustomAttribSetOptions()","statobj.getStatus()-->"+statobj.getStatus(),null);
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
			} //End for()
			} //End if()
		else{
			//There is an error in query execution
			EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"CustomAttribsDB.java","getCustomAttribSetOptions()","statobj.getStatus() Error in selection of records"+statobj.getStatus(),null);

		}
		}//End of try
		catch(Exception e){
		EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "CustomAttribsDB.java", "getCustomAttribSetOptions()", e.getMessage(), e ) ;
		}//End of catch
	return attribOptionsMap;
	} //End of getCustomAttribSetOptions()

	public  CustomAttribSet getCustomAttribSet(String groupid,String purpose){

	     return getCustomAttribSet(groupid,null,purpose);
	}




	public  CustomAttribSet getCustomAttribSet(String groupid,String subgroupid,String purpose){

		ArrayList attribids=new ArrayList();
		ArrayList params=new ArrayList();
		CustomAttribSet custattset=new CustomAttribSet();
		DBManager dbmanager=new DBManager();
		HashMap hm=new HashMap();
		StatusObj statobj=null;
		String query=null;
		params.add(groupid);
		params.add(purpose);
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"CustomAttribsDB.java","getCustomAttribSet()","Entered the method getCustomAttribSet()",null);
		try{

		if(subgroupid==null){
		query=GET_EVENT_ATTRIB_SET;
		}
		else{
		params.add(subgroupid);
		query=attribIdSelectQuery;
		}

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
			HashMap optionsOfAttribs=getCustomAttribSetOptions(attribsetid,attribid);

			if(optionsOfAttribs.containsKey((Object)attribid)){
				attrib.setAttriboptions( (ArrayList) optionsOfAttribs.get(attribid));
			}// End if()

			attribs[k]=attrib;

			}//End for()
			custattset.setAttributes(attribs);
		}//End if()
		else{
		//There is an error in query execution
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"CustomAttribsDB.java","getCustomAttribSet()","statobj.getStatus() Error in selection of records"+statobj.getStatus(),null);
		}//End else
		}//End of try
		catch(Exception e){
		EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "TicketCustomAttribsDB.java", "getCustomAttribSet()", e.getMessage(), e ) ;
		}//End of catch
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"CustomAttribsDB.java","getCustomAttribSet()","Exited from method getCustomAttribSet()",null);
		return custattset;
	}//End of getCustomAttribSet()

	public  HashMap getAttribResponseSet(String trnid,String groupid,String subgroupid,String profileid,String purpose)
	{
		HashMap custatresponsemap=new HashMap();
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"CustomAttribsDB.java","getAttribResponseSet()","Entered the method getAttribResponseSet()",null);
		try{
		DBManager dbmanager=new DBManager();
		HashMap hm=new HashMap();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery(GET_ATTRIB_RESPONSE,new String []{groupid,subgroupid,trnid,profileid,purpose});
		int count=statobj.getCount();
		//ArrayList
		CustomAttribResponse custattresp=new CustomAttribResponse();
		if(statobj.getStatus()&&count>0){
		for (int k=0;k<count;k++){
		String attribid=dbmanager.getValue(k,"attribid","");
		String optionid=dbmanager.getValue(k,"optionid","");
		String shortresp=dbmanager.getValue(k,"shortresponse","");
		String bigresp=dbmanager.getValue(k,"bigresponse","");
		String quest_shortform=dbmanager.getValue(k,"question_shortform","");
		String question_original=dbmanager.getValue(k,"question_original","");
		String optvalue=dbmanager.getValue(k,"optionval","");
		String optiondisp=dbmanager.getValue(k,"option_display","");
		custattresp.setattribId(attribid);
		custattresp.setShortResponse(shortresp);
		custattresp.setResponse(bigresp);
		custattresp.setQuestionShortform(quest_shortform);
		custattresp.setQuestionOriginal(question_original);
		custattresp.setOptionVal(optvalue);
		custattresp.setOptionDisplay(optiondisp);
		custattresp.setOptionid(optionid);
		custatresponsemap.put(attribid,custattresp);
		} //End of for() loop
		}//End of if()
		else{

		//There is an error in query execution
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"CustomAttribsDB.java","getAttribResponseSet()","statobj.getStatus() Error in selection of records"+statobj.getStatus(),null);
		} //End else
		}//End of try
		catch(Exception e){
		EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "TicketCustomAttribsDB.java", "getAttribResponseSet()", e.getMessage(), e ) ;
		}//End of catch
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"CustomAttribsDB.java","getAttribResponseSet()","Exited from method getAttribResponseSet()",null);
		return custatresponsemap;
	}//End of getAttribResponseSet()
	public StatusObj  setAttributeResponsekey(CustomAttribResponse[] custattrbresp,String trnid,String groupid,String profileid,String subgroupid,String attribsetid)
	{
		DBManager dbmanager=new DBManager();
		HashMap hm=new HashMap();
		StatusObj statobj=null;
		ArrayList queries=new ArrayList();
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"CustomAttribsDB.java","setAttributeResponsekey()","Entered the method getAttribResponseSet()",null);
		try{
		String refseq=DbUtil.getVal("select nextval('refidseq')",new String[]{});
		String ref_id=EncodeNum.encodeNum(refseq);
		queries.add(new DBQueryObj(INSERT_RESPONSE_MASTER,new String [] {groupid,subgroupid,trnid,profileid,attribsetid,ref_id}));

		for(int i=0;i<custattrbresp.length;i++){
		CustomAttribResponse custresp=custattrbresp[i];
		String attribid=custresp.getattribId();
		String optionid=custresp.getOptionid();
		String shortresp=custresp.getShortResponse();
		String bigresp=custresp.getResponse();
		String ques_shortform=custresp.getQuestionShortform();
		String ques_orig=custresp.getQuestionOriginal();
		String optval=custresp.getOptionVal();
		String optdisp=custresp.getOptionDisplay();
		queries.add(new DBQueryObj(INSERT_RESPONSE,new String []{ref_id,attribid,optionid,shortresp,bigresp,ques_shortform,ques_orig,optval,optdisp}));
		}//End for
		StatusObj statobj1=DbUtil.executeUpdateQueries((DBQueryObj [])queries.toArray(new DBQueryObj [0]));
		}//End try
		catch(Exception e){
		EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "CustomAttribsDB.java", "setAttributeResponsekey()", e.getMessage(), e ) ;

		} //End catch
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"CustomAttribsDB.java","setAttributeResponsekey()","Exited from the method getAttribResponseSet()",null);
		return statobj;

	} //End setAttributeResponsekey()
	public  ArrayList getAttributes(String setid){

			DBManager dbmanager=new DBManager();
			ArrayList attribs_list=new ArrayList();
			StatusObj statobj=null;
			try{
			statobj=dbmanager.executeSelectQuery(GETATTR_FOR_SETID,new String []{setid});
			int count=statobj.getCount();
			if(statobj.getStatus()&&count>0){
				for(int k=0;k<count;k++){
					attribs_list.add(dbmanager.getValue(k,"question_original",""));
				} //End for()
			} // End if()
			else{
				EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"CustomAttribsDB.java","getAttributes()","statobj.getStatus() Error in selection of records"+statobj.getStatus(),null);
			}
			}//End try
			catch(Exception e){
			EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "CustomAttribsDB.java", "getAttributes()", e.getMessage(), e ) ;
			} //End catch
			return attribs_list;
	} //End getAttributes()
	public  HashMap getResponses(String setid){
			DBManager dbmanager=new DBManager();
			StatusObj statobj=null;
			HashMap hm=null;
			HashMap custatresponsemap=new HashMap();
			CustomAttribResponse custattresp=new CustomAttribResponse();
			try{
			statobj=dbmanager.executeSelectQuery(RESPONSE_QUERY,new String []{setid});
			int count=statobj.getCount();
			if(statobj.getStatus()&&count>0){

			for(int k=0;k<count;k++){
				String attribid=dbmanager.getValue(k,"attribid","");
				String optionid=dbmanager.getValue(k,"optionid","");
				String shortresp=dbmanager.getValue(k,"shortresponse","");
				String bigresp=dbmanager.getValue(k,"bigresponse","");
				String quest_shortform=dbmanager.getValue(k,"question_shortform","");
				String question_original=dbmanager.getValue(k,"question_original","");
				String optvalue=dbmanager.getValue(k,"optionval","");
				String optiondisp=dbmanager.getValue(k,"option_display","");
				custattresp.setattribId(attribid);
				custattresp.setShortResponse(shortresp);
				custattresp.setResponse(bigresp);
				custattresp.setQuestionShortform(quest_shortform);
				custattresp.setQuestionOriginal(question_original);
				custattresp.setOptionVal(optvalue);
				custattresp.setOptionDisplay(optiondisp);
				custattresp.setOptionid(optionid);
				custatresponsemap.put(attribid,custattresp);

			} //End for

			} //End if
			else{
				//There is a problem in query execution. Log Entry
			}
			}//End try
			catch (Exception e){
			EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "CustomAttribsDB.java", "getResponses()", e.getMessage(), e ) ;
			} //End catch
		return custatresponsemap;
	} //End getResponses()
	public  StatusObj insertIntoCustomAttribMaster(String[] params){

			StatusObj stobj=new StatusObj(false,"",null,0);
			try{
				stobj=DbUtil.executeUpdateQuery(INSERT_CUSTOM_ATTRIB_MASTER,params,null);
				//Log entry
			} // End try
			catch(Exception e){
			EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "CustomAttribsDB.java", "insertIntoCustomAttribMaster()", e.getMessage(), e ) ;
			} //End catch
		return stobj;
	} //End insertIntoCustomAttribMaster()
	public  void deleteAttribs(String setid){
	        StatusObj statusobj = null;
	        try{
	        statusobj = DbUtil.executeUpdateQuery(DELETE_OPTIONS, new String[] {setid}, null);
	        //Log
	        statusobj = DbUtil.executeUpdateQuery(DELETE_ATTRIBUTES, new String[] {setid}, null);
	        //Log
	        } //End try
	        catch(Exception e){
		EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "CustomAttribsDB.java", "deleteAttribs()", e.getMessage(), e ) ;
	        } //End catch
	} //End deleteAttribs()

	public  StatusObj insertCustomAttributes(CustomAttribute [] customattribs,String groupid,String purpose,String setid){
			StatusObj statobj=new StatusObj(false,"",null,0);
			EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"CustomAttribsDB.java","insertCustomAttributes()","Entered the method insertCustomAttributes()",null);
			try{
				if(customattribs==null) return statobj;
				String id=DbUtil.getVal("select attribsetid from custom_attrib_master where purpose=? and groupid=? ",new String[]{purpose,groupid});

				if(id == null || "".equals(id)){
				    id=setid;
				    statobj = insertIntoCustomAttribMaster(new String[] {id, groupid, purpose});
				    //Log entry

			    }
				//Log entry
			deleteAttribs(id);
			ArrayList dbquery=new ArrayList();
			ArrayList optdbquery=new ArrayList();
			ArrayList <String>queryParams=null;

			for(int i=0;i<customattribs.length;i++){
				queryParams=new ArrayList();
				queryParams.add(id);
				queryParams.add((i+1)+"");
				queryParams.add((String)customattribs[i].getAttributeName());
				queryParams.add((String)customattribs[i].getAttributeType());
				queryParams.add((String)customattribs[i].getIsRequired());
				queryParams.add((String)customattribs[i].getTextboxSize());
				queryParams.add((String)customattribs[i].getRows());
				queryParams.add((String)customattribs[i].getCols());
				queryParams.add((String)customattribs[i].getPosition());

				dbquery.add(new DBQueryObj(INSERT_ATTRIBUTES,(String[])queryParams.toArray(new String[0])));
				ArrayList options =(ArrayList) customattribs[i].getAttriboptions();
				if(options!=null && options.size()>0){
				for(int j=0;j<options.size();j++){
					ArrayList opt=new ArrayList();
					AttribOption attopt=(AttribOption) options.get(j);
					opt.add(id);
					opt.add((j+1)+"");
					opt.add((String) attopt.getOptionid());
					opt.add((String) attopt.getPosition());
					opt.add((String) attopt.getOptionValue());
					optdbquery.add(new DBQueryObj(INSERT_OPTIONS,(String [] )opt.toArray(new String[0])));
					} //End for
				} //End if


				} //End of outer for()
				if(dbquery!=null&&dbquery.size()>0)
					statobj=DbUtil.executeUpdateQueries((DBQueryObj [])dbquery.toArray(new DBQueryObj [dbquery.size()]));

				if(optdbquery!=null&&optdbquery.size()>0&&statobj.getStatus())
					statobj=DbUtil.executeUpdateQueries((DBQueryObj [])optdbquery.toArray(new DBQueryObj [optdbquery.size()]));


			}//End try
			catch(Exception e){
			EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "CustomAttribsDB.java", "insertCustomAttributes()", e.getMessage(), e ) ;
			}//End catch
			EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"CustomAttribsDB.java","insertCustomAttributes()","Exited from method insertCustomAttributes()",null);
			return statobj;

		} //End insertCustomAttributes()
	public  void deleteResponseFromDB(String refid){
			DBQueryObj [] queries=new DBQueryObj [2];
			queries[0]=new DBQueryObj(DELETE_RESPONSE,new String [] {refid});
			queries[1]=new DBQueryObj(DELETE_RESPONSE_MASTER,new String [] {refid});
			try{
			StatusObj statobj=DbUtil.executeUpdateQueries(queries);
			} //End try
			catch(Exception e){
			EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "CustomAttribsDB.java", "deleteResponseFromDB()", e.getMessage(), e ) ;
			} //End catch
		} //End deleteResponseFromDB()

	public String extendsTestMethod(String arg1,String arg2){
		return "args"+arg1+arg2;
	}




	public HashMap getTicketLevelAttributes(String groupid){

	DBManager db=new DBManager();
	HashMap ticketsAttribs=new HashMap();
	StatusObj sb=db.executeSelectQuery("select subgroupid,a.attribid,a.position as x,b.position as y from subgroupattribs a,custom_attribs b where a.attribsetid=b.attrib_setid and groupid=to_number(?,'9999999999') and a.attribid=b.attrib_id order by subgroupid,a.position,b.position",new String []{groupid});
	if(sb.getStatus()){
	ArrayList attribList=null;
	for(int i=0;i<sb.getCount();i++){
	if(ticketsAttribs.containsKey(db.getValue(i,"subgroupid","")))
	attribList.add(db.getValue(i,"attribid",""));
	else{
	attribList=new ArrayList();
	attribList.add(db.getValue(i,"attribid",""));
	}
	ticketsAttribs.put(db.getValue(i,"subgroupid",""),attribList);
	}

	}

	return ticketsAttribs;
	}

}
