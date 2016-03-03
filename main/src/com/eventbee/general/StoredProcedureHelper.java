package com.eventbee.general;

public class  StoredProcedureHelper {
	
	
	public static void creditmailquota(String uid ,String qty,String userid,String quota,String role,String type ,String tid)
	{
		
		String result="";
	        
	     try{   if ("MEM".equals(role))	        	
	        result=DbUtil.getVal("select quotaval from user_quota  where userid=? and quotatype=?",new String[]{userid,quota});
	          else
	       result=DbUtil.getVal("select quotaval from user_quota  where unitid=? and quotatype=?and role=?",new String[]{uid,quota,role});

	        
	        DbUtil.executeUpdateQuery("insert into mail_quota(unitid,userid,refid,type,value,tdate) values(?,?,?,?,cast(? as numeric),now())",new String[]{uid,userid,tid,type,qty});
	      
	       
	        if ("".equals(result)||result==null)	        
	        { 
	        	DbUtil.executeUpdateQuery("insert into user_quota(unitid,quotaval,userid,quotatype,role) values(?,cast(? as numeric)+100,?,?,?)",new String[]{uid,qty,userid,quota,role});
	          result="100";
	        }
	    	   else if (Integer.parseInt(result)<0 )	result="0";
	
	     if ("MEM".equals(role))
			
	        DbUtil.executeUpdateQuery("update user_quota set quotaval=cast(? as numeric)+cast(? as numeric) where userid=? and quotatype=?",new String[]{result,qty,userid,quota});
			else
			DbUtil.executeUpdateQuery("update user_quota set quotaval=cast(? as numeric)+cast(? as numeric) where unitid=? and quotatype=?and role=?",new String[]{result,qty,uid,quota,role});
	     }catch(Exception e){System.out.println("errror in creditmailquota "+e.getMessage());}
	        
	        
		
	}
	
	

}
