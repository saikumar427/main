package com.event.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.StatusObj;

public class NoticeboardDB {
public static int saveNoticeData(HashMap<String,String> notice,String groupId){
	System.out.println("noticehash in DB Method"+notice);
		String INSERT_NOTICE_QUERY="insert into notice(noticeid,notice,noticetype,groupid,grouptype,owner,postedat)" +
				" values(nextval('seq_notice')," +
				"?,?,to_number(?,'99999999999999'),?,to_number(?,'99999999999999'),current_timestamp)";
        int count=0;
  		try{
  			String mgrId=DbUtil.getVal("select mgr_id from eventinfo where eventid=CAST(? AS BIGINT)", new String[] {groupId});
  			String noticeId=notice.get("noticeid");
  			if("".equals(noticeId)){
			StatusObj stob1=DbUtil.executeUpdateQuery(INSERT_NOTICE_QUERY,new String[]{notice.get("notice"),notice.get("noticetype"),groupId,"",mgrId});
			if(stob1.getStatus())
  				count=1;
			}
  			else{
  				String UPDATE_NOTICE_QUERY="update notice set notice=?,postedat=current_timestamp," +
				"noticetype=? where noticeid=to_number(?,'99999999999999')";
  				String noticeDesc=notice.get("notice");
  				String noticeType=notice.get("noticetype");
  				DbUtil.executeUpdateQuery(UPDATE_NOTICE_QUERY,new String[]{noticeDesc,noticeType,noticeId});
  			}
  			
  		}catch(Exception e){
  			
  		}
  		return count;
  }//End of insertNotice

public static ArrayList<HashMap<String,String>> getAllNotices(String groupId){
		String GET_NOTICES_QUERY="select noticeid,notice,noticetype,to_char(postedat,'Mon dd, HH:MI AM')" +
				" as postedat from notice where groupid=to_number(?,'99999999999999') order by postedat desc";
		ArrayList<HashMap<String,String>> listObj=new ArrayList<HashMap<String,String>>();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=null;
		try{
		statobj=dbmanager.executeSelectQuery(GET_NOTICES_QUERY,new String []{groupId});
		int count=statobj.getCount();
		if(statobj.getStatus() && count>0){
			for(int k=0;k<count;k++){
				HashMap<String,String> notice=new HashMap<String,String>();
				notice.put("notice",dbmanager.getValue(k,"notice",""));
				notice.put("noticetype",dbmanager.getValue(k,"noticetype",""));
				notice.put("postedat",dbmanager.getValue(k,"postedat",""));
                notice.put("noticeid",dbmanager.getValue(k,"noticeid",""));
                listObj.add(notice);
			}//End of for loop.
		}//End of if block.
		}
		catch(Exception e){
			//Exception log
		}
	return listObj;
	}//End of getAllNotices
	
public static HashMap<String,String> getNoticeInfo(String noticeid){
		String GET_NOTICE_QUERY="select notice,noticetype from notice where noticeid=to_number(?,'99999999999999')";
  	    HashMap<String,String> notice=new HashMap<String,String>();
  		try{
  			DBManager dbmanager=new DBManager();
  			StatusObj statobj=dbmanager.executeSelectQuery(GET_NOTICE_QUERY,new String []{noticeid});
  			if(statobj.getStatus()){
  			notice.put("notice",dbmanager.getValue(0,"notice",""));
			notice.put("noticetype",dbmanager.getValue(0,"noticetype",""));
			notice.put("noticeid",noticeid);
  			}
  		}
  		catch(Exception e){
  		}
  		return notice;
	}//End of getNoticeInfo

 public static int updateNotice(String noticeid, String notice){
		String UPDATE_NOTICE_QUERY="update notice set notice=?,postedat=current_timestamp" +
				" where noticeid=to_number(?,'99999999999999')";
       int count=0;
 		try{
 			DbUtil.executeUpdateQuery(UPDATE_NOTICE_QUERY,new String[]{notice,noticeid});
 		}catch(Exception e){
 			//Exception log.
 		}
 		return count;
	 }//End of updateNotice

public static int deleteNotices(String noticeid){
		String DELETE_NOTICE_QUERY="delete from notice where noticeid=to_number(?,'99999999999999')";
        int count=0;
 		try{ 	
 			DbUtil.executeUpdateQuery(DELETE_NOTICE_QUERY, new String[]{noticeid});
 		}
 		catch(Exception e){
 			//Exception log.
 		}
 		return count;
	}//End of deleteNotices
}
