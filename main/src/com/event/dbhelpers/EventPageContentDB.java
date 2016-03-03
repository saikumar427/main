package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;

import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.StatusObj;
import com.eventbee.util.CoreConnector;
public class EventPageContentDB {
	public static void insertlogoandmessage(String eid,String message,String imageurl){
		String DELETE_PHOTO="delete from config where name='eventpage.logo.url' and config_id=CAST(? AS INTEGER) ";
		String DELETE_MESSAGE="delete from config where name='eventpage.logo.message' and config_id=CAST(? AS INTEGER) ";
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)", new String[]{eid});
		
		DbUtil.executeUpdateQuery(DELETE_PHOTO,new String[]{configid});
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) values (CAST(? AS INTEGER),'eventpage.logo.url',?)",new String[]{configid,imageurl});
		
		DbUtil.executeUpdateQuery(DELETE_MESSAGE,new String[]{configid});
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) values (CAST(? AS INTEGER),'eventpage.logo.message',?)",new String[]{configid,message});
	
	}
	
	
	public static void updateEventLogoURL(String eid,String eventLogoURL){
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?",new String[]{eid,"eventpage.logo.url"});
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"eventpage.logo.url",eventLogoURL,eid});
	}
	
	public static void deleteLogoURL(String eid){
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?",new String[]{eid,"eventpage.logo.url"});
	}
	
	public static void updateshowattendeeinfo(String eid,String val){
		String CONFIG_INFO_INSERT="insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)";
		String CONFIG_KEY_DELETE="delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?";
		
		DbUtil.executeUpdateQuery(CONFIG_KEY_DELETE,new String[]{eid,"eventpage.attendee.show"});
		DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{"eventpage.attendee.show",val,eid});
	}
	
	public static void updateshowsocialpromotions(String eid,String val){
		String CONFIG_INFO_INSERT="insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)";
		String CONFIG_KEY_DELETE="delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?";
		
		DbUtil.executeUpdateQuery(CONFIG_KEY_DELETE,new String[]{eid,"event.socialmediapromotions.show"});
		DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{"event.socialmediapromotions.show",val,eid});
	}
	
	public static void updateshowquestioninfo(String eid,String val){
		String CONFIG_INFO_INSERT="insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)";
		String CONFIG_KEY_DELETE="delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?";
		
		DbUtil.executeUpdateQuery(CONFIG_KEY_DELETE,new String[]{eid,"event.confirmationscreen.questions.show"});
		DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{"event.confirmationscreen.questions.show",val,eid});
	}
	public static void updateshowgooglemap(String eid,String val){
		String CONFIG_INFO_INSERT="insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)";
		String CONFIG_KEY_DELETE="delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?";
		
		DbUtil.executeUpdateQuery(CONFIG_KEY_DELETE,new String[]{eid,"eventpage.map.show"});
		DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{"eventpage.map.show",val,eid});
	}
	public static void updateshowpromotions(String eid,String val){
		String CONFIG_INFO_INSERT="insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)";
		String CONFIG_KEY_DELETE="delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?";
		
		DbUtil.executeUpdateQuery(CONFIG_KEY_DELETE,new String[]{eid,"show.ebee.promotions"});
		DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{"show.ebee.promotions",val,eid});
	}
	public static void insertEventPwd(String eid,String password){
		if("".equals(password.trim())){
			DbUtil.executeUpdateQuery("delete from view_security where eventid=?",new String[]{eid});
		}else{
			DbUtil.executeUpdateQuery("delete from view_security where eventid=?",new String[]{eid});
			DbUtil.executeUpdateQuery("insert into view_security(eventid,password) values(?,?)",new String[]{eid,password});
		}		
	}
	
	public static void updateeventphotoURL(String eid,String eventphotoURL,String uploadurl){
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?",new String[]{eid,"event.eventphotoURL"});
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.eventphotoURL",eventphotoURL,eid});
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?",new String[]{eid,"event.eventphotoURL.width"});
		DbUtil.executeUpdateQuery("update eventinfo set photourl=? where eventid=CAST(? AS BIGINT)",new String[]{uploadurl,eid});
	}
	public static String getPhotourl(String eid){
		String photourl=DbUtil.getVal("select photourl from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
		if(photourl==null) photourl="";
		return photourl;
	}
	public static void updateexternaleventphotoURL(String eid,String eventexternalphotoURL,String photowidth){
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?",new String[]{eid,"event.eventphotoURL"});
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.eventphotoURL",eventexternalphotoURL,eid});
		
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?",new String[]{eid,"event.eventphotoURL.width"});
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.eventphotoURL.width",photowidth,eid});

	}
	
	public static void deletePhoto(String eid){
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?",new String[]{eid,"event.eventphotoURL"});
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?",new String[]{eid,"event.eventphotoURL.width"});
		DbUtil.executeUpdateQuery("update eventinfo set photourl='' where eventid=CAST(? AS BIGINT)",new String[]{eid});
	}
	
	public static void updateSocialNetwork(String eid,ArrayList socialnwchkbox, String facebookeventid,String fanpageid){
		System.out.println("updateSocialNetwork");
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) " +
				"and name in ('event.fbshare.show','event.twitter.show','event.fblike.show','event.googleplusone.show','event.fbcomment.show','event.fbsend.show'," +
				"'event.FBRSVPList.show','event.FBRSVPList.gendercount.show','event.FBRSVPList.eventid','event.fbfanpagecomments.show','event.confirmationpage.fbsharepopup.show'," +
				"'event.fbiboughtbutton.gender','event.fbiboughtbutton.show','event.fbfanpagecomments.pageid','event.reg.loginpopup.show')",new String[]{eid});
			
		
		if(!socialnwchkbox.contains("twitter")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.twitter.show","N",eid});
		}else if(socialnwchkbox.contains("twitter")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.twitter.show","Y",eid});
		}
		if(!socialnwchkbox.contains("fbshare")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbshare.show","N",eid});
		}else if(socialnwchkbox.contains("fbshare")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbshare.show","Y",eid});
		}
		if(!socialnwchkbox.contains("fblike")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fblike.show","N",eid});
		}else if(socialnwchkbox.contains("fblike")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fblike.show","Y",eid});
		}
		if(!socialnwchkbox.contains("googleplusone")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.googleplusone.show","N",eid});
		}else if(socialnwchkbox.contains("googleplusone")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.googleplusone.show","Y",eid});
		}
	   /*if(!socialnwchkbox.contains("twittercomment")){
			DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=to_number(?,'9999999999999999')) and name=?",new String[]{eid,"event.twittercomment.show"});
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=to_number(?,'9999999999999999')",new String[]{"event.twittercomment.show","N",eid});
		}else if(socialnwchkbox.contains("twittercomment")){
			DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=to_number(?,'9999999999999999')) and name=?",new String[]{eid,"event.twittercomment.show"});
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=to_number(?,'9999999999999999')",new String[]{"event.twittercomment.show","Y",eid});
		}*/
		if(!socialnwchkbox.contains("fbcomment")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbcomment.show","N",eid});
		}else if(socialnwchkbox.contains("fbcomment")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbcomment.show","Y",eid});
		}
		if(!socialnwchkbox.contains("fbsend")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbsend.show","N",eid});
		}else if(socialnwchkbox.contains("fbsend")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbsend.show","Y",eid});
		}
		if(!socialnwchkbox.contains("fbfan")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbfanpagecomments.show","N",eid});
		}else if(socialnwchkbox.contains("fbfan")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbfanpagecomments.show","Y",eid});
		}
		if(!socialnwchkbox.contains("fbattending")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.FBRSVPList.show","N",eid});
		}else if(socialnwchkbox.contains("fbattending")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.FBRSVPList.show","Y",eid});
		}
		if(!socialnwchkbox.contains("gendercount")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.FBRSVPList.gendercount.show","N",eid});
		}else if(socialnwchkbox.contains("gendercount")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.FBRSVPList.gendercount.show","Y",eid});
		}
		if(!socialnwchkbox.contains("fbibought")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbiboughtbutton.show","N",eid});
		}else if(socialnwchkbox.contains("fbibought")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbiboughtbutton.show","Y",eid});
		}
		if(!socialnwchkbox.contains("ibgendercount")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbiboughtbutton.gender","N",eid});
		}else if(socialnwchkbox.contains("ibgendercount")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbiboughtbutton.gender","Y",eid});
		}
		if(!socialnwchkbox.contains("sharepopup")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.confirmationpage.fbsharepopup.show","N",eid});
		}else if(socialnwchkbox.contains("sharepopup")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.confirmationpage.fbsharepopup.show","Y",eid});
		}
		if(!socialnwchkbox.contains("fblogin")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.reg.loginpopup.show","N",eid});
		}else if(socialnwchkbox.contains("fblogin")){
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.reg.loginpopup.show","Y",eid});
		}
		
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.FBRSVPList.eventid",facebookeventid.trim(),eid});
		DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.fbfanpagecomments.pageid",fanpageid.trim(),eid});
	}
	
	
	
	public static void updatePromotionSettings(String eid,ArrayList promosettings)
	{
		String configid=DbUtil.getVal("select config_id from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{eid});
		
		DbUtil.executeUpdateQuery("delete from config where config_id=? and name in('eventpage.attendee.show','eventpage.socialmediapromotions.show','eventpage.map.show','show.ebee.promotions','event.confirmationscreen.questions.show')", new String[]{configid});
		
			if(!promosettings.contains("attendee"))
			{						 
				String CONFIG_INFO_INSERT="insert into config(config_id, name, value) values(?,?,?)";
				DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{configid,"eventpage.attendee.show","No"});
			}
			 else
			{
				String CONFIG_INFO_INSERT="insert into config(config_id, name, value) values(?,?,?)";
				DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{configid,"eventpage.attendee.show","Yes"});
			}
				 
			if(!promosettings.contains("socialmediapromotions"))
			{
				String CONFIG_INFO_INSERT="insert into config(config_id, name, value) values(?,?,?)";
			 	DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{configid,"eventpage.socialmediapromotions.show","N"});
		 	}
			else
		 	{
				String CONFIG_INFO_INSERT="insert into config(config_id, name, value) values(?,?,?)";
			 	DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{configid,"eventpage.socialmediapromotions.show", "Y"});
			}
			
			if(!promosettings.contains("maps"))
			{
				String CONFIG_INFO_INSERT="insert into config(config_id, name, value) values(?,?,?)";
			 	DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{configid,"eventpage.map.show","No"});
			}
			else
			{
				String CONFIG_INFO_INSERT="insert into config(config_id, name, value) values(?,?,?)";
			 	DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{configid,"eventpage.map.show","Yes"});
			}
			
			
			if(!promosettings.contains("promotions"))
			{
				String CONFIG_INFO_INSERT="insert into config(config_id, name, value) values(?,?,?)";
			 	DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{configid,"show.ebee.promotions","No"});
			}
			else
			{
				String CONFIG_INFO_INSERT="insert into config(config_id, name, value) values(?,?,?)";
			 	DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{configid,"show.ebee.promotions","Yes"});
			}
			
			if(!promosettings.contains("confirmationqno"))
			{
				String CONFIG_INFO_INSERT="insert into config(config_id, name, value) values(?,?,?)";
			 	DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{configid,"event.confirmationscreen.questions.show","No"});
			}
			else
			{
				String CONFIG_INFO_INSERT="insert into config(config_id, name, value) values(?,?,?)";
			 	DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{configid,"event.confirmationscreen.questions.show","Yes"});
			}
	}
	
	public static ArrayList<Entity> getConfigValues(String eid){
		ArrayList<Entity> list = new ArrayList<Entity>();
		//list = DbUtil.getValues("select name, value from config a,eventinfo b where a.config_id= b.config_id and b.eventid=to_number(?,'99999999999999') and a.name in ('event.fbshare.show','event.twitter.show','event.fblike.show','event.fbcomment.show','event.fbsend.show','event.fbattending.show') and a.value='N'",new String[]{eid});
		String query = "select name, value from config a,eventinfo b where a.config_id= b.config_id and b.eventid=CAST(? AS BIGINT) and a.name in ('event.fbshare.show','event.twitter.show','event.fblike.show','event.googleplusone.show','event.fbcomment.show','event.fbsend.show','event.FBRSVPList.show','event.FBRSVPList.gendercount.show','event.FBRSVPList.eventid','event.fbfanpagecomments.show','event.confirmationpage.fbsharepopup.show','event.fbiboughtbutton.gender','event.fbiboughtbutton.show','event.fbfanpagecomments.pageid','event.reg.loginpopup.show')";
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;

		try{
			statobj=dbmanager.executeSelectQuery(query,new String []{eid});
			int count1=statobj.getCount();
			if(statobj.getStatus()&&count1>0){
				for(int k=0;k<count1;k++){
					String name=dbmanager.getValue(k,"name","");
					String value=dbmanager.getValue(k,"value","");
					list.add(new Entity(name,value));
				}
			}
			
		}//End of try block
		catch(Exception e){
			System.out.println("There is an Exception while executing the query" +
					"for the eventid"+eid+" "+e.getMessage());
		}
		return list;		
	}	
	public static ArrayList<Entity> getconfigValuesofpromotions(String eid){ 
		ArrayList<Entity> list = new ArrayList<Entity>();
		String query = "select name, value from config a,eventinfo b where a.config_id= b.config_id and b.eventid=CAST(? AS BIGINT) and a.name in ('event.confirmationscreen.questions.show','show.ebee.promotions','eventpage.map.show','eventpage.socialmediapromotions.show','eventpage.attendee.show')";
		
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		
		try
		{
			statobj=dbmanager.executeSelectQuery(query,new String []{eid});
			int count1=statobj.getCount();
			if(statobj.getStatus()&&count1>0)
			{
				for(int k=0;k<count1;k++)
				{
					String name=dbmanager.getValue(k,"name","");
					String value=dbmanager.getValue(k,"value","");
					list.add(new Entity(name,value));
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("There is an Exception while executing the query for the eventid"+eid+" "+e.getMessage());
		}
		return list;
	}
	
	public static String getRegLoginPopupStatus(String eventid){
		String loginpopupstatus=DbUtil.getVal("select 'Y' from config where name='event.reg.loginpopup.show' and config_id in(select config_id from eventinfo where eventid=CAST(? AS BIGINT))", new String[]{eventid});
	    return loginpopupstatus;
	}
	
	public static void updateShowVolumeTickets(String eid,String val){
		String CONFIG_INFO_INSERT="insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)";
		String CONFIG_KEY_DELETE="delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name=?";
		
		DbUtil.executeUpdateQuery(CONFIG_KEY_DELETE,new String[]{eid,"event.volumetickets.show"});
		DbUtil.executeUpdateQuery(CONFIG_INFO_INSERT,new String[]{"event.volumetickets.show",val,eid});
	}
	
	public static void removeGLobalEventCache(String eid){
		String s1=EbeeConstantsF.get("s1","www.eventbee.com");
		String s2=EbeeConstantsF.get("s2","www.eventbee.com");
		System.out.println("in removeGLobalEventCache::"+eid);
		System.out.println("s1: server address :"+s1+":s2: server address:"+s2);
		HashMap<String,String> inputparams=new HashMap<String,String>();		
		inputparams.put("mode","removeevent");
		inputparams.put("rmkey",eid);
		inputparams.put("action","allow");
		String data="";
		try{
			CoreConnector cc1=new CoreConnector("http://"+s1+"/utworks/eventglobaldata.jsp");
			cc1.setArguments(inputparams);
			cc1.setTimeout(50000);
			data=cc1.MGet();
			CoreConnector cc2=new CoreConnector("http://"+s2+"/utworks/eventglobaldata.jsp");
			cc2.setArguments(inputparams);
			cc2.setTimeout(50000);
			data=cc2.MGet();
			}catch(Exception e){
			System.out.println("Error While Processing Request::"+e.getMessage());		
			}	
		
	}
	
	public static void updateFBconfirmationPromoSettings(String eid,String fbShareConfirmation,String fbLoginduringRegistration,String promotions){
		DbUtil.executeUpdateQuery("delete from config where config_id=(select config_id from eventinfo where eventid=CAST(? AS BIGINT)) and name in('event.confirmationpage.fbsharepopup.show','event.reg.loginpopup.show','show.ebee.promotions')",new String[]{eid});
		
		if(fbShareConfirmation.equals("Y"))
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.confirmationpage.fbsharepopup.show","Y",eid});
		else
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.confirmationpage.fbsharepopup.show","N",eid});
		
		if(fbLoginduringRegistration.equals("Y"))
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.reg.loginpopup.show","Y",eid});
		else
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"event.reg.loginpopup.show","N",eid});
		
		if(promotions.equals("Y"))
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"show.ebee.promotions","Yes",eid});
		else
			DbUtil.executeUpdateQuery("insert into config(config_id, name, value) select config_id,?,? from eventinfo where eventid=CAST(? AS BIGINT)",new String[]{"show.ebee.promotions","No",eid});
	}
	
	
	
	
}
