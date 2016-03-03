package com.mymarketing.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.EventbeeMail;
import com.eventbee.general.GenUtil;
import com.eventbee.general.SendMailStatus;
import com.eventbee.general.StatusObj;
import com.eventbee.general.StringEncryptDecrypt;
import com.eventbee.general.TemplateConverter;
import com.mymarketing.beans.MailListData;
import com.mymarketing.beans.TemplateData;

public class MarketingDB {
	
	public static void saveTemplate(TemplateData templateData,String mgrId){
		System.out.println("in saveTemplate userId: "+mgrId+" templateId: "+templateData.getTemplateId());
		String name=templateData.getName();
		String subject=templateData.getSubject();
		String from=templateData.getFrom().trim().toLowerCase();
		//String description=templateData.getContent();
		String notes=templateData.getNotes();
		String templateId=templateData.getTemplateId();
		
		String content=templateData.getContent();
		String footercontent=templateData.getFootercontent();
	    if("yes".equals(footercontent)){
			String server_address="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
			if(content.indexOf("#**EMAILID**#")>-1){
			}else{
			content=content+"<br/>"
			+"<br/><div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' color='#666666' size='-3'>If you wish to remove yourself from this mailing list, please unsubscribe by</font></div>"
			+"<div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' color='#666666' size='-3'>"
			+"<a href='"+server_address+"/main/unsubscribe?token=#**EMAILID**#&tid=#**TEMPID**#&pid=mm'>clicking here</a></font></div>"
			+"<div><img src='"+server_address+"/main/emailtrack?token=#**EMAILID**#&tid=#**TEMPID**#&bid=#**BLASTID**#&purpose=openemail' border='0px'></div>";
			}
		}
		
		String insertQry="insert into marketing_templates (templateid,name,subject,fromemail,content,notes,created_at,lastupdate,owner_id)"+
		         " values(?,?,?,?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),CAST(? as integer))";
		
		String updateQry="update marketing_templates set name=?,subject=?,fromemail=?,content=?,notes=?," +
				"lastupdate=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') where templateid=? and owner_id=CAST(? as integer)";
		
		if(templateId.equals("")){
			templateId=DbUtil.getVal("select nextval('seq_campdesignid')",null);
			DbUtil.executeUpdateQuery(insertQry,new String[]{templateId,name,subject,from,content,notes,DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate(),mgrId});
		}else
			DbUtil.executeUpdateQuery(updateQry, new String[]{name,subject,from,content,notes,DateUtil.getCurrDBFormatDate(),templateId,mgrId});
		
	}
	
	public static ArrayList<TemplateData> getTemplates(String mgrId){
		System.out.println("In getTemplates ");
		ArrayList<TemplateData> templateList = new ArrayList<TemplateData>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery("select templateid,CAST(templateid as integer) as tempid,name,subject,fromemail,description,notes, to_char(created_at, 'Dy, Mon DD ,YYYY, HH:MM PM') as createdat from marketing_templates where owner_id=CAST(? as integer) order by tempid desc",new String[]{mgrId});
		int count = statobj.getCount();
		
		if(statobj.getStatus() && count>0){
			for(int k=0;k<count;k++){
				TemplateData templateData = new TemplateData();
				templateData.setTemplateId(dbmanager.getValue(k,"templateid",""));
				templateData.setName(dbmanager.getValue(k,"name",""));
				templateData.setSubject(dbmanager.getValue(k,"subject",""));
				templateData.setFrom(dbmanager.getValue(k,"fromemail",""));
				templateData.setContent(dbmanager.getValue(k,"description",""));
				templateData.setNotes(dbmanager.getValue(k,"notes",""));
				templateData.setCreatedAt(dbmanager.getValue(k,"createdat",""));
				templateList.add(templateData);
			}
		}
		return templateList;
		
	}
	
	public static TemplateData getTemplateData(String templateid){
		System.out.println("In getTemplateData templateid: "+templateid);
		TemplateData templateData = new TemplateData();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		statobj=dbmanager.executeSelectQuery("select name,subject,fromemail,content,notes, to_char(created_at, 'Dy, Mon DD ,YYYY, HH:MM PM') as createdat from marketing_templates where templateid=?",new String[]{templateid});
		int count = statobj.getCount();
		if(statobj.getStatus()){
			templateData.setTemplateId(templateid);
			templateData.setName(dbmanager.getValue(0,"name",""));
			templateData.setSubject(dbmanager.getValue(0,"subject",""));
			templateData.setFrom(dbmanager.getValue(0,"fromemail",""));
			templateData.setContent(dbmanager.getValue(0,"content",""));
			templateData.setNotes(dbmanager.getValue(0,"notes",""));
			templateData.setCreatedAt(dbmanager.getValue(0,"createdat",""));
			String content=dbmanager.getValue(0,"content","");
			if(content.indexOf("#**EMAILID**#")>-1)
				templateData.setFootercontent("yes");
			else
				templateData.setFootercontent("no");
		}
		return templateData;
	}
	
	public static void getDetailsForTestMail(String templateid,TemplateData templateData){
		System.out.println("In getDetailsForTestMail templateid: "+templateid);
		DBManager dbmanager=new DBManager();
		String query="select subject,fromemail,content from marketing_templates where templateid=?";
		StatusObj statobj=dbmanager.executeSelectQuery(query,new String[]{templateid});
		int count=statobj.getCount();
		if(statobj.getStatus()&&count>0){
			templateData.setFrom(dbmanager.getValue(0,"fromemail",""));
			templateData.setSubject(dbmanager.getValue(0,"subject",""));
			templateData.setContent(dbmanager.getValue(0,"content",""));
			templateData.setTemplateId(templateid);
		}
	}
	
	public static String sendTestMail(TemplateData templateData, String mgrId){
		System.out.println("In sendTestMail: "+templateData.getMailTo());
		String msg="";
		
		String content=templateData.getContent();
		String server_address="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
	    if(content.indexOf("#**EMAILID**#")>-1){
		}else{
		content=content+"<br/>"
		+"<br/><div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' color='#666666' size='-3'>If you wish to remove yourself from this mailing list, please unsubscribe by</font></div>"
		+"<div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' color='#666666' size='-3'>"
		+"<a href='"+server_address+"/main/unsubscribe?token=#**EMAILID**#&tid=#**TEMPID**#&pid=mm'>clicking here</a></font></div>"
		+"<div><img src='"+server_address+"/main/emailtrack?token=#**EMAILID**#&tid=#**TEMPID**#&bid=#**BLASTID**#&purpose=openemail' border='0px'></div>";
		}
		
		EmailObj emailobj=null;
			try{				
				emailobj=new EmailObj();				
				emailobj.setTo(templateData.getMailTo().trim().toLowerCase());
				//String fromemail=EbeeConstantsF.get("MARKETING_TEST_FROM_EMAIL","marketing@eventbee.com");
				emailobj.setFrom(templateData.getFrom().trim().toLowerCase());				
				emailobj.setReplyTo(templateData.getFrom().trim().toLowerCase());
				emailobj.setSendMailStatus(new SendMailStatus("13579","Marketing_Test_Mail","","1"));
				if(content!=null&&!"".equals(content.trim())){
					emailobj.setSubject(templateData.getSubject()+" (Test mail)");
					String encodeemailid=StringEncryptDecrypt.getEncryptedString(templateData.getMailTo().trim().toLowerCase());
					String encodetempid=EncodeNum.encodeNum(templateData.getTemplateId().trim());
					Map message=fillData(encodeemailid,encodetempid);
					emailobj.setHtmlMessage(TemplateConverter.getMessage(message,content));		
					EventbeeMail.sendHtmlMailPlain(emailobj);
				}
				return "Success";	
			}catch(Exception exp){				
				System.out.println("Exception in My Marketing sendTestMail Error: "+exp.getMessage());
			}
			return msg;
	}
	
	public static Map fillData(String emailid,String tempid){
		Map mp=new HashMap();
		mp.put("#**EMAILID**#",emailid);
		mp.put("#**TEMPID**#",tempid);
		mp.put("#**BLASTID**#","1234");
        return mp;
	}  
	
	public static ArrayList<HashMap<String,String>> getMailingList(String mgrId){  
		System.out.println("In getMailingList: ");
		ArrayList<HashMap<String,String>> listObj=new ArrayList<HashMap<String,String>>();
		String query="select name,description,list_id  from marketing_mail_list where owner_id=CAST(? as integer) order by list_id desc";
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(query, new String[]{mgrId});
		if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++){
				HashMap<String,String> mapObj=new HashMap<String,String>();
				mapObj.put("name",db.getValue(i, "name", ""));
				mapObj.put("description",db.getValue(i, "description", ""));
				mapObj.put("list_id",db.getValue(i, "list_id", ""));
				listObj.add(mapObj);
			}
		}
		return listObj;
	}
	
	public static void getMailListData(String listid, MailListData maildata){
		System.out.println("In getMailListData ");
		StringBuffer emails= new StringBuffer();
		String query="select name,description,exist_managers  from marketing_mail_list where list_id=?";
		String query2="select email from marketing_list_emails where list_id=CAST(? AS INTEGER)";
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(query,new String[]{listid});
		if(sb.getStatus()){
			maildata.setName(db.getValue(0, "name", ""));
			maildata.setDescription(db.getValue(0, "description", ""));
			maildata.setExistmanagerstatus(db.getValue(0, "exist_managers", "no"));
		}
		StatusObj sb2=db.executeSelectQuery(query2,new String[]{listid});
		if(sb2.getStatus()){
			for(int i=0;i<sb2.getCount();i++)
				emails.append(db.getValue(i,"email","")+"\n");
			int a= emails.lastIndexOf("\n"); 
			emails.deleteCharAt(a);
			maildata.setEmail(emails.toString());
		}
	}
	
	
	public static void bulkUploadinsert(String listId,String  bulkmails,List<String> filterdomains,String existmanager){
		System.out.println("bulkUploadinsert listId: "+listId);
		try{
		String deletemailsquery="delete from marketing_list_emails where list_id=CAST(? AS INTEGER)";
		DbUtil.executeUpdateQuery(deletemailsquery,new String[]{listId});
		System.out.println("emails are successfully deleted for listid:"+listId);
		String insertmailsquery="insert into marketing_list_emails(list_id,email) values(CAST(? AS INTEGER),?)";
		String[] bulkmailsarr=GenUtil.strToArrayStr(bulkmails, "\n");
		List<String> unsubscribeemails=DbUtil.getValues("select distinct lower(email) from marketing_unsubscribe_emails", null);
		List<String> manageremails=DbUtil.getValues("select distinct lower(email) from user_profile",null);
 		for(int i=0;i<bulkmailsarr.length;i++){
		String domainpart=bulkmailsarr[i].trim().substring(bulkmailsarr[i].trim().indexOf("@")+1, bulkmailsarr[i].trim().length());
		if("yes".equalsIgnoreCase(existmanager)){
			if(!unsubscribeemails.contains(bulkmailsarr[i].trim().toLowerCase()) && !(filterdomains.get(0).contains(domainpart)))
				DbUtil.executeUpdateQuery(insertmailsquery,new String[]{listId,bulkmailsarr[i].trim().toLowerCase()});
		}else{
			if(!unsubscribeemails.contains(bulkmailsarr[i].trim().toLowerCase()) && !(filterdomains.get(0).contains(domainpart)) && !(manageremails.contains(bulkmailsarr[i].trim().toLowerCase())))
				  DbUtil.executeUpdateQuery(insertmailsquery,new String[]{listId,bulkmailsarr[i].trim().toLowerCase()});   
		 }	  
		}
		System.out.println("emails are successfully inserted for listid:"+listId);
		/*ArrayList<String> currentMembers=getCurrentMembers(listId);
		ArrayList<String> insertMembers=new ArrayList<String>();
		int count=0;
		for(int i=0;i<maildata.getEmails().size();i++){
		String memail=maildata.getEmails().get(i);
		if(currentMembers.contains(memail)){ //adding duplicate emails to separate list
		count++;
		}else
		insertMembers.add(memail);
		}
		//Here we are inserting non-duplicates
		for(int j=0;j<insertMembers.size();j++){
		String query2="insert into marketing_list_emails(list_id,email) values(CAST(? AS INTEGER),?)";
		StatusObj sb2=DbUtil.executeUpdateQuery(query2,new String[]{listId,insertMembers.get(j)});
		}*/
		//return count;//finally returning duplicates count
		}catch(Exception e){
		   System.out.println("Exception occured in bulkUploadinsert for listid:"+listId);	
		 }
		}
	
	public static ArrayList<String> getCurrentMembers(String listId){
		ArrayList<String> currentMembers=new ArrayList<String>();
		String query="select lower(email) as email from marketing_list_emails where list_id=CAST(? AS INTEGER)";
		DBManager db=new DBManager();
		StatusObj sb=db.executeSelectQuery(query, new String[]{listId});
		if(sb.getStatus()){
			for(int i=0;i<sb.getCount();i++){
				currentMembers.add(db.getValue(i, "email", ""));
			}
		}
		return currentMembers;
	}
	
	
	
	public static String saveMailListData(MailListData maildata,String mgrId){
		System.out.println("In saveMailListData ");
		String listid = maildata.getListid();
		String existsmanagerstatus=maildata.getExistmanagerstatus();
		if("".equals(listid)){
			String description=maildata.getDescription();
			String name=maildata.getName();
			
			listid=DbUtil.getVal("select  nextval('seq_list') as seqlist",null);
			String query="insert into marketing_mail_list(name,description,list_id,created_at,owner_id,exist_managers)  values (?,?,?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),CAST(? as integer),?)";
			StatusObj sb=DbUtil.executeUpdateQuery(query,new String[]{name,description,listid,DateUtil.getCurrDBFormatDate(),mgrId,existsmanagerstatus});
			
		}else{
			String description=maildata.getDescription();
			String name=maildata.getName();
			String query="update marketing_mail_list set name=?,description=?,exist_managers=? where list_id=? and owner_id=CAST(? as integer)";
			StatusObj sb=DbUtil.executeUpdateQuery(query, new String[]{name,description,existsmanagerstatus,maildata.getListid(),mgrId});
			
		}
		return listid;
	}
	
	public static String unsubscribeMailListCount(){
		String count  = DbUtil.getVal("select count(*) from marketing_unsubscribe_emails", null);
		return count;
	}
	
	public static void saveUnsubscribeEmail(String email){
		System.out.println("email is:"+email);
		String[] subscribemail=GenUtil.strToArrayStr(email, ",");
		for(int i=0;i<subscribemail.length;i++){
		DbUtil.executeUpdateQuery("insert into marketing_unsubscribe_emails(email,date) values(?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))",new String[]{subscribemail[i].trim().toLowerCase(),DateUtil.getCurrDBFormatDate()});
		}
	}
	
	public static void deleteTemplate(String templateid){
		String deleteSchedules = "delete from marketing_schedules where campid=?";
		DbUtil.executeUpdateQuery(deleteSchedules,new String[]{templateid});
		String deleteTemplate="delete from marketing_templates where templateid=?";
		DbUtil.executeUpdateQuery(deleteTemplate,new String[]{templateid});
		System.out.println("template deleted successfully");
	}
	
	public static void deleteMailList(String listid){
		String query1="delete from marketing_list_emails where list_id=CAST(? AS INTEGER)";
		DbUtil.executeUpdateQuery(query1,new String[]{listid});
		
		String query2="delete from marketing_mail_list where list_id=?";
		DbUtil.executeUpdateQuery(query2,new String[]{listid});
		System.out.println("maillist deleted successfully");
		
	}
	
	public static ArrayList<String> getFilterDomains(){
		List<String> filterdomains=new ArrayList<String>();
		try{
		filterdomains=DbUtil.getValues("select domain from filter_domains",null);
		}catch(Exception e){
			System.out.println("Exception occured in getFilterDomains"+e.getMessage());
		}
		return (ArrayList)filterdomains;
	}

}
