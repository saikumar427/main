package com.mycommunities.beans;
import java.util.*;
public class Config {
	
	    
		   private String configid  = null;   
		   private String refid=null;
		   private String purpose=null;	
		   HashMap confighm=new HashMap();
		   
		   public String getConfigID() {return configid;}
		   public void setConfigID(String p_configid) {
			configid = p_configid;
		   }

		   public String getRefID() {return refid;}
		   public void setRefID(String p_refid){
			  refid = p_refid;
		  }

		   public String getPurpose() {return purpose;}
		   public void setPurpose(String p_purpose) {
			  purpose = p_purpose;
		  }

		  public HashMap getConfigHash() {return confighm;}
		  public void setConfigHash(HashMap p_confighm) {
			  confighm = p_confighm;
		  }

		  public String getKeyValue(String key,String defaultVal) {
			 if(confighm==null) return defaultVal;
		         if(confighm.get(key)!=null) return (String)confighm.get(key);
			 return defaultVal;	
		  }

		  public void setKeyValue(String key,String value) {
			if(confighm!=null){
				confighm.put(key,value);
			}
		  }
		 
		  public void removeKey(String key){
			 confighm.remove(key);
		  }

	


}
