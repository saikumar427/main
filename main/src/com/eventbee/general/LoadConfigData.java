package com.eventbee.general;

import java.util.HashMap;

public class LoadConfigData {
	 public static HashMap<String,String> getConfig(String configid,HashMap<String,String> hm){
		    String CONFIG_INFO_GET="select * from config where config_id=to_number(?,'999999999999999')";
			DBManager dbmanager=new DBManager();
			StatusObj statobj=dbmanager.executeSelectQuery(CONFIG_INFO_GET,new String []{configid});
			if(hm==null) hm=new HashMap<String,String>();
			int count=statobj.getCount();
			if(statobj.getStatus()&&count>0){
				for(int k=0;k<count;k++){
					hm.put(dbmanager.getValue(k,"name",""),dbmanager.getValue(k,"value",""));
				}
			}
				return hm;
		    }

}
