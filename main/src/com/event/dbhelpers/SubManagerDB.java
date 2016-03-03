package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.event.beans.SubManagerData;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
import com.eventbee.general.EventbeeMail;
import com.eventbee.general.I18NCacheData;
import com.eventbee.general.SendMailStatus;
import com.eventbee.general.StatusObj;
import com.eventbee.general.StringEncrypter;
import com.eventbee.general.TemplateConstants;
import com.eventbee.general.TemplateConverter;
import com.eventbee.general.StringEncrypter.EncryptionException;
import com.user.dbhelpers.UserDB;

public class SubManagerDB {
	private static Logger log = Logger.getLogger(SubManagerDB.class);

	public static ArrayList<SubManagerData> getSubManagerList(String eid) {
		log.info("In SubManagerDB getSubManagerList");

		ArrayList<SubManagerData> subManagerList = new ArrayList<SubManagerData>();
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;

		String subManagerListQuery = "select email,sub_mgr_id,status,name from event_submanager where eventid=CAST(? AS BIGINT) order by created_at desc";
		statobj = dbmanager.executeSelectQuery(subManagerListQuery,
				new String[] { eid });
		int count = statobj.getCount();
		if (statobj.getStatus() && count > 0) {
			for (int k = 0; k < count; k++) {
				SubManagerData subManagerData = new SubManagerData();
				subManagerData.setEmail(dbmanager.getValue(k, "email", ""));
				subManagerData.setName(dbmanager.getValue(k, "name", ""));
				subManagerData.setSubMgrId(dbmanager.getValue(k, "sub_mgr_id",""));
				String status=dbmanager.getValue(k, "status", "");
				String currentstatus="";
				if("Active".equals(status))
					currentstatus=I18n.getString("global.status.active.lbl");
				else
					currentstatus=I18n.getString("global.status.inactive.lbl");
				subManagerData.setStatus(currentstatus);
				
				
				subManagerData.setEid(dbmanager.getValue(k, eid, ""));

				subManagerList.add(subManagerData);

			}
		}
		return subManagerList;
	}

	public static void saveSubManager(String eid, SubManagerData subMgrData) {
		System.out.println("::::: in saveSubManager ::::: ");
		try {
			StatusObj statobj = null;
			String[] inputParams=null;
			if (!"".equals(subMgrData.getSubMgrId())) {
				String updateSubMgrQry = "update event_submanager set name=?,permission_codes=?," +
						"updated_at=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') " +
						"where eventid=CAST(? AS BIGINT) and sub_mgr_id=CAST(? AS BIGINT) ";
				
				inputParams=new String[]{subMgrData.getName().trim(),
						subMgrData.getPermCodes(),DateUtil.getCurrDBFormatDate(),eid,subMgrData.getSubMgrId()};
				statobj = DbUtil.executeUpdateQuery(updateSubMgrQry, inputParams);
			
			} else {
				String password="";
				String submgrid = DbUtil.getVal("select sub_mgr_id from submanager where email=?",new String []{subMgrData.getEmail().trim().toLowerCase()});
				
				if(submgrid==null){
				
					submgrid = DbUtil.getVal("select nextval('seq_submgr_id') as submgrid", null);
					password=generatePassword();
					String encriptpwd = (new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME)).encrypt(password);
					String createSubMgrQry = "insert into submanager(email,sub_mgr_id,password,created_at,updated_at) " +
							"values(?,CAST(? AS BIGINT),?,to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))";
					statobj = DbUtil.executeUpdateQuery(createSubMgrQry,
							new String[] {subMgrData.getEmail().trim().toLowerCase(),
										submgrid,encriptpwd,
										DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate() });
					subMgrData.setEmailTemplate("EVENT_SUBMANAGER_NEW");
				}
				String createSubMgrQry = "insert into event_submanager (eventid,email,name,mgr_id,sub_mgr_id,status,"
					+ "permission_codes,created_at,updated_at) values(CAST(? AS BIGINT),?,?,CAST(? AS INTEGER),CAST(? AS BIGINT),?,?,"
					+ "to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'),to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS'))";
				statobj = DbUtil.executeUpdateQuery(createSubMgrQry,
						new String[] { eid,
								subMgrData.getEmail().trim().toLowerCase(),subMgrData.getName().trim(),
								subMgrData.getMgrId(), submgrid,"Active",subMgrData.getPermCodes(),
								DateUtil.getCurrDBFormatDate(),DateUtil.getCurrDBFormatDate() });
				
				subMgrData.setPassword(password);
				subMgrData.setSubMgrId(submgrid);
				if (statobj.getStatus() && "on".equals(subMgrData.getSendEmail()))
					processEmail(eid, subMgrData);
			}
			
		} catch (Exception e) {
			System.out.println("exception in createSubManager: "+ e.getMessage());
		}
	}

	static void processEmail(String eid, SubManagerData subMgrData) {
		System.out.println("submanager processEmail:::: ");
		String password = "";
		String subject = "";
		if ("activation".equals(subMgrData.getManage())){
			subject = "Sub-Manager Activation Email";
			subMgrData.setEmailTemplate("EVENT_SUBMANAGER_EXISTING");
		}else{
			subject = "Sub-Manager Confirmation Email";
			subMgrData.setEmailTemplate("EVENT_SUBMANAGER_NEW");
		}
		
		if (!"".equals(subMgrData.getPassword()))
			password = subMgrData.getPassword();
		else {
			try {
				password = DbUtil.getVal("select password from submanager where sub_mgr_id=CAST(? AS BIGINT)",
						new String[] {subMgrData.getSubMgrId()});
				password =(new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME) ).decrypt(password);
    		} catch (EncryptionException e) {
    			System.out.println("Exception in SubManagerDB processEmail: "+e.getMessage());
    		}
		}
		
		String permissions = subMgrData.getPermissions().toString();
		HashMap evtmap=new HashMap();
		EventInfoDB edb=new EventInfoDB();
		StatusObj sobj=edb.getEventInfo(eid,evtmap);
		String  serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		String submgrurl=serveraddress+"/main/submanager/login";

		HashMap hm = new HashMap();
		hm.put("submgrname", subMgrData.getName());
		hm.put("toemail", subMgrData.getEmail());
		hm.put("eventname", (String)evtmap.get("EVENTNAME"));
		hm.put("submgrurl", submgrurl);
		hm.put("password", password);
		hm.put("mgrname", (String)evtmap.get("FIRSTNAME")+" "+(String)evtmap.get("LASTNAME"));
		hm.put("mgremail", (String)evtmap.get("EMAIL"));
		hm.put("subject", subject);
		hm.put("emailtemplate", subMgrData.getEmailTemplate());
		hm.put("purpose", subMgrData.getEmailTemplate());
		hm.put("isCustomFeature", "N");
		String dbi18nLang=I18NCacheData.getI18NLanguage(eid);
		hm.put("emailtemplate", RegEmailSettingsDB.getEmailTemplate(hm, dbi18nLang, eid));
		SendEmail(hm);
	}

	static void SendEmail(HashMap hm) {
		try {
			//EmailTemplate emailtemplate = new EmailTemplate("13579", (String) hm.get("emailtemplate"));
			HashMap<String, String> emailTemplate =(HashMap<String, String>)hm.get("emailtemplate");
			EmailObj obj = EventbeeMail.getEmailObj();
			Map mp = new HashMap();
			
			mp.put(TemplateConstants.TO_EMAIL, (String) hm.get("toemail"));
			mp.put(TemplateConstants.USERID, (String) hm.get("toemail"));
			mp.put(TemplateConstants.SUB_MANAGER_NAME, (String) hm.get("submgrname"));
			mp.put(TemplateConstants.EVENT_NAME, (String) hm.get("eventname"));
			mp.put(TemplateConstants.SUB_MANAGER_URL, (String) hm.get("submgrurl"));
			mp.put(TemplateConstants.PASSWORD, (String) hm.get("password"));
			mp.put(TemplateConstants.MANAGER_NAME, (String) hm.get("mgrname"));
			//mp.put(TemplateConstants.MGRLASTNAME, (String) hm.get("mgrlastname"));
			
			String fromemail=DbUtil.getVal("select email from from_email_permissions where email=?", new String[]{(String) hm.get("mgremail")});
			if(fromemail==null || "".equals(fromemail))
				fromemail=EbeeConstantsF.get("MESSAGES_FROM_EMAIL","messages@eventbee.com");
			String HTMLcontent = TemplateConverter.getMessage(mp, emailTemplate.get("htmlFormat"));
			obj.setTo((String) hm.get("toemail"));
			//obj.setFrom((String) hm.get("mgremail"));
			obj.setFrom(fromemail);
			obj.setReplyTo((String) hm.get("mgremail"));
			obj.setBcc((String) hm.get("mgremail")+",bala@eventbee.org");
			obj.setSubject((String) emailTemplate.get("subjectFormat"));
			obj.setHtmlMessage(HTMLcontent);
			obj.setSendMailStatus(new SendMailStatus("0","EVENT_SUBMANAGER_CONFIRMATION","13579","1"));
			EventbeeMail.sendHtmlMailPlain(obj);
			EventbeeMail.insertStrayEmail(obj,"html","S");
		} catch (Exception e) {
			System.out.println("Exception in Sub Manager sendEmail: "+e.getMessage());
		}
	}

	public static HashMap validateSubManager(String eid,
			SubManagerData subMgrData) {
		HashMap hm = new HashMap();

		String exist = isSubManagerExist(eid, subMgrData.getEmail(), subMgrData.getSubMgrId());
		if ("yes".equals(exist))
			hm.put("subMgrExist", I18n.getString("sub.already.exists.msg"));
		/*if ("".equals(subMgrData.getSubMgrId())) {
			hm = UserDB.validateEquals(subMgrData.getPassword().toString(),
					subMgrData.getRetypePassword().toString(),
					"Passwords should match", "pwdMatch", hm);
			hm = UserDB.validateString(subMgrData.getPassword().toString(),
					"Password", "pwdLength", 200, 4, hm);
		} else {
			if (!"".equals(subMgrData.getPassword())
					|| !"".equals(subMgrData.getRetypePassword())) {
				hm = UserDB.validateEquals(subMgrData.getPassword().toString(),
						subMgrData.getRetypePassword().toString(),
						"Passwords should match", "pwdMatch", hm);
				hm = UserDB.validateString(subMgrData.getPassword().toString(),
						"Password", "pwdLength", 200, 4, hm);
			}
		}*/
		return hm;
	}

	public static String isSubManagerExist(String eid, String email, String submgrid) {
		String existQry = "select 'yes' from event_submanager where lower(email)=? and eventid=CAST(? AS BIGINT)";
		String exist = "";
		if (!"".equals(submgrid)) {
			existQry += "and sub_mgr_id != CAST(? AS BIGINT)";
			exist = DbUtil.getVal(existQry, new String[] {
					email.trim().toLowerCase(), eid, submgrid });
		} else {
			exist = DbUtil.getVal(existQry, new String[] {
					email.trim().toLowerCase(), eid });
		}
		if ("yes".equals(exist))
			return "yes";
		else
			return "no";
	}

	public static SubManagerData getSubManager(String eid, String subMgrId) {
		SubManagerData subManager = new SubManagerData();
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		String subManagerListQuery = "select permission_codes,email,status,name from event_submanager where eventid=CAST(? AS BIGINT) and sub_mgr_id=CAST(? AS BIGINT)";
		statobj = dbmanager.executeSelectQuery(subManagerListQuery,
				new String[] { eid, subMgrId });
		int count = statobj.getCount();
		if (statobj.getStatus() && count > 0) {
			subManager.setEmail(dbmanager.getValue(0, "email", ""));
			subManager.setStatus(dbmanager.getValue(0, "status", ""));
			subManager.setName(dbmanager.getValue(0, "name", ""));
			subManager.setSubMgrId(subMgrId);
			String permissionCodes = dbmanager.getValue(0, "permission_codes","");
			try{
				List<String> permissions = Arrays.asList(permissionCodes.split(","));
				for (int i = 0; i < permissions.size(); i++) {
					subManager.getSubMgrPermissionList().add(permissions.get(i).toString());
				}
			}catch(Exception e){
				System.out.println("exception in getSubManager SubManagerDB.java::::: "+e.getMessage());
			}
		}
		return subManager;
	}

	public static void manageSubManager(String eid, String subMgrId,String status) {
		DbUtil.executeUpdateQuery("update event_submanager set status=?,updated_at=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') " +
				"where eventid=CAST(? AS BIGINT) and sub_mgr_id=CAST(? AS BIGINT)",
						new String[] { status, DateUtil.getCurrDBFormatDate(),eid, subMgrId });
		
		if("Active".equals(status)){
			SubManagerData subMgrData = new SubManagerData();
			StringBuffer permissions= new StringBuffer();
			//List<String> subMgrPermissionList = getSubManagerPermissionList(eid, subMgrId);
			subMgrData=getSubManager(eid, subMgrId);
			HashMap<String, String> actionPermissionMap = getActionPermissions();
			if(subMgrData.getSubMgrPermissionList().size()>0){
				for(int i=0;i<subMgrData.getSubMgrPermissionList().size();i++){
					permissions.append(actionPermissionMap.get(subMgrData.getSubMgrPermissionList().get(i))+"<br/>");
				}
				subMgrData.setPermissions(permissions);
			}
			subMgrData.setManage("activation");
			subMgrData.setEmailTemplate("EVENT_SUBMANAGER_NEW");
			processEmail(eid, subMgrData);
		}
		
	}

	public static ArrayList<Entity> getActionPermissionList(String powertype) {
		ArrayList<Entity> actionPermissionList = new ArrayList<Entity>();
		try {
			DBManager db = new DBManager();
			StatusObj sb = db.executeSelectQuery("select permission,code,action from action_permission where powertype='Common' or powertype=?", new String[]{powertype});
			if (sb.getStatus()) {
				for (int p = 0; p < sb.getCount(); p++) {
					/*actionPermissionList.add(new Entity(db.getValue(p, "code", ""), 
							db.getValue(p, "permission", "")));*/
					String dbpermession=db.getValue(p, "action", "");
					String key=I18n.getString("sm.permession."+dbpermession);
					actionPermissionList.add(new Entity(db.getValue(p, "code", ""),  key));
				}
			}
		} catch (Exception e) {
			System.out.println("Exception ocuured in getActionPermissionList "+ e.getMessage());
		}
		return actionPermissionList;
	}
	
	public static HashMap<String, String> getActionPermissions() {
		HashMap<String, String> actionPermissionMap = new HashMap<String, String>();
		try {
			DBManager db = new DBManager();
			StatusObj sb = db.executeSelectQuery("select permission,code from action_permission", null);
			if (sb.getStatus()) {
				for (int p = 0; p < sb.getCount(); p++) {
					actionPermissionMap.put(db.getValue(p, "code", ""), db.getValue(p, "permission", ""));
				}
			}
		} catch (Exception e) {
			System.out.println("Exception ocuured in getActionPermissions "+ e.getMessage());
		}
		return actionPermissionMap;
	}

	/*public static ArrayList<String> getSubManagerPermissionList(String eid, String submgrid) {
		DBManager dbmanager = new DBManager();
		StatusObj statobj = null;
		ArrayList<String> subMgrPermissionList = new ArrayList<String>();
		String QUERY = "select permission_codes from submanager_permissions"
				+ "  where eventid=CAST(? AS BIGINT) and sub_mgr_id=CAST(? AS BIGINT)";
		statobj = dbmanager.executeSelectQuery(QUERY, new String[] { eid,submgrid });
		if (statobj.getStatus()) {
			String permission_codes = dbmanager.getValue(0, "permission_codes","");
			List<String> permissions = Arrays.asList(permission_codes.split(","));
			for (int i = 0; i < permissions.size(); i++) {
				subMgrPermissionList.add(permissions.get(i).toString());
			}
		}
		return subMgrPermissionList;
	}*/
	 
		public static void removeSubManager(String eid, String subMgrId) {
			DbUtil.executeUpdateQuery("delete from event_submanager where eventid=CAST(? AS BIGINT) and sub_mgr_id=CAST(? AS BIGINT)",
							new String[] {eid, subMgrId });
			
		}
		
		public static String generatePassword(){
			   String ALPHA_NUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			   /*int lens[]={8,9,10,11,12};
			   Random randomGenerator = new Random();
			   int rand=randomGenerator.nextInt(5);
			   int len=lens[rand];*/
			   int len=5;
			   StringBuffer sb = new StringBuffer(len);
			   for (int i = 0; i < len; i++) {
			   int ndx = (int) (Math.random() * ALPHA_NUM.length());
			   sb.append(ALPHA_NUM.charAt(ndx));
			   }
			   return sb.toString();
		   }
		   static public SubManagerData validateSubManager(String email,String password){
		 SubManagerData smd=null;
		String validate_query="select sub_mgr_id from submanager  where lower(email)=?  and password=?";
		String sub_mgr_id=null;
		try{
			String encPword=(new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME) ).encrypt(password);
			sub_mgr_id=DbUtil.getVal(validate_query, new String[]{email.trim().toLowerCase(),encPword});
			if(sub_mgr_id!=null){
			smd=new SubManagerData();
			smd.setSubMgrId(sub_mgr_id);
			smd.setEmail(email);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception occured while validating Sub manager with email="+email);
		}
		System.out.println("returning "+smd);
		return smd; 
	}
	 static public HashMap<String, String> getPermittedActions(String sub_mgr_id,String eid){
		 String PermissionQuery="select action from action_permission where code in" +
		 		"(select regexp_split_to_table(permission_codes, ',') from event_submanager where sub_mgr_id =?::BIGINT and eventid=?::BIGINT and status='Active') " +
		 		"union select subaction as action from subactions where code in" +
		 		"(select regexp_split_to_table(permission_codes, ',') from event_submanager where sub_mgr_id =?::BIGINT and eventid=?::BIGINT and status='Active') "; 
		 DBManager db=new DBManager();
		 HashMap<String, String> hm=new HashMap<String, String>();
		 StatusObj sob=db.executeSelectQuery(PermissionQuery, new String[]{sub_mgr_id,eid,sub_mgr_id,eid});
		 if(sob.getStatus() && sob.getCount()>0){
			 hm.put("Snapshot", "yes");
			 hm.put("SpecialFee", "yes");
			 for(int i=0;i<sob.getCount();i++){
				 hm.put(db.getValue(i, "action", ""), "yes");
			 }
		 }
		 return hm;
	 }
	 static public HashMap<String, String> getNonPermittedActions(String sub_mgr_id,String eid){
		 String PermissionQuery="select action from action_permission where code not in" +
		 		"(select regexp_split_to_table(permission_codes, ',') from event_submanager where sub_mgr_id =?::BIGINT and eventid=?::BIGINT and status='Active') ";
		 DBManager db=new DBManager();
		 HashMap<String, String> hm=new HashMap<String, String>();
		 StatusObj sob=db.executeSelectQuery(PermissionQuery, new String[]{sub_mgr_id,eid});
		 if(sob.getStatus() && sob.getCount()>0){
			 for(int i=0;i<sob.getCount();i++){
				 hm.put(db.getValue(i, "action", ""), "yes");
			 }
		 }
		 return hm;
	 }
	 
		public static HashMap validateSubMgrChangePwd(String email,String oldPwd,String newPwd,String confirmNewPwd) {
			HashMap hm = new HashMap();
			try{
				String password = DbUtil.getVal("select password from submanager where email=?", new String[] {email.toLowerCase().trim()});
				if(password==null){
					hm.put("invalidemail", I18n.getString("sub.no.account.found.msg"));
				}else{
					password =(new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME) ).decrypt(password);
					System.out.println("password: "+password+" oldPwd: "+oldPwd);
					if(!oldPwd.equals(password)){
						hm.put("invalidoldpwd", I18n.getString("sub.invalid.old.pwd.msg"));
					}
					
					hm = UserDB.validateEquals(newPwd,confirmNewPwd,I18n.getString("sub.password.should.match.lbl"), "pwdMatch", hm);
					hm = UserDB.validateString(newPwd,"Password", "pwdLength", 200, 4, hm);
				}
			}catch(Exception e){
				
			}
			return hm;
		}
		
		public static void updatePassword(String password,String email){
			try {
				String encriptpwd = (new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME)).encrypt(password);
				DbUtil.executeUpdateQuery("update submanager set password=?,updated_at=to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.MS') where email=?",
								new String[] {encriptpwd, DateUtil.getCurrDBFormatDate(),email});
			} catch (EncryptionException e) {
				System.out.println("Exception in updatePassword ERROR: "+e.getMessage());
			}
		}
}
