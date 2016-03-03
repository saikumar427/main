package com.user.dbhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.event.helpers.I18n;
import com.event.i18n.dbmanager.ConfirmationEmailDAO;
import com.eventbee.beans.Entity;
import com.eventbee.general.DBManager;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
//import com.eventbee.general.EmailTemplate;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.EventBeeValidations;
import com.eventbee.general.EventbeeLogger;
import com.eventbee.general.EventbeeMail;
import com.eventbee.general.ProfileValidator;
import com.eventbee.general.SendMailStatus;
import com.eventbee.general.StatusObj;
import com.eventbee.general.TemplateConstants;
import com.eventbee.general.TemplateConverter;
import com.user.beans.UserData;
import com.eventbee.general.StringEncrypter;
import com.eventbee.general.StringEncrypter.EncryptionException;
import com.mycommunities.beans.AddCommunityMemberData;
import com.myevents.actions.AddEventAction;
public class UserDB {
	
	private static final Logger log = Logger.getLogger(UserDB.class);  
	static String LOGIN_LABEL="User Name"; 
	  static{
		LOGIN_LABEL=EbeeConstantsF.get("login.label","User Name");
	  }
	
	public static String getUserId(String loginName, String password){
		String uid=DbUtil.getVal("select user_id from authentication where login_name=?",new String[]{loginName.trim()});
		return uid;
		
	}
	public static ArrayList<Entity> populateGender(){
		ArrayList<Entity> gendertype=new ArrayList<Entity>();
		gendertype.add(new Entity("Male","Male"));
		gendertype.add(new Entity("Female","Female"));
		return gendertype;
	}
	public static HashMap validateAttendeeSignUp(UserData userData){
		HashMap hm= new HashMap();
		ProfileValidator pv=new ProfileValidator();
	    int i=hm.size();
	    String loginLabel=LOGIN_LABEL;
	    
	   // System.out.println("loginLabel : "+loginLabel);
	    
	    if("Bee ID".equals(loginLabel)) 
	    	loginLabel=I18n.getString("login.label.bee.id");
	    else if("User Name".equals(loginLabel))
	    	loginLabel=I18n.getString("login.label.user.name");
	    	
	   hm=validateUserName("",userData.getBeeId().toString(),loginLabel+" "+I18n.getString("la.beeid.already.exists.msg"),"loginnameExist",hm,"signup",loginLabel,"13579");
	   if(userData.getBeeId().toString()!=null&& !"".equals( (userData.getBeeId().toString()).trim() )){
			  if(!pv.checkNameValidity(userData.getBeeId().toString().trim(),true)){
		   		hm.put("invalid_id",I18n.getString("la.invalid.lbl")+" "+loginLabel+". "+ I18n.getString("su.use.aplha.only.msg"));
			  }
		}
	   hm=validateEquals(userData.getPassword().toString(),userData.getRetypePassword().toString(),I18n.getString("ai.pwd.match.msg"),"pwdMatch",hm);
	   hm=validateString(userData.getBeeId().toString(),loginLabel,"loginLength",20,4,hm);
	   hm=validateString(userData.getPassword().toString(),"Password","pwdLength",20,4,hm);
	 //  hm=validateString(userData.getFirstName().toString(),"First Name","firstnameError",50,1,hm);
	//   hm=validateString(userData.getLastName().toString(),"Last Name","lastnameError",50,1,hm);
	   hm=validateEmail(userData.getEmail().toString(),"Email","emailError",hm);
	  // hm=validateString(userData.getPhone().toString(),"Phone","phoneError",50,1,hm);
	   
	        int k=hm.size()-i;
	        if(k==1)hm.put("generalError",I18n.getString("su.there.is.msg")+" "+k+" "+I18n.getString("su.error.msg"));
	        else if(k>1)hm.put("generalError",I18n.getString("su.ther.are.msg")+" "+k+" "+I18n.getString("su.error.msg"));

	       return hm;
	  }
	public static HashMap validateUserName(String str1,String str2,String msg,String path,HashMap hm,String func,String label){
		   return validateUserName(str1,str2,msg,path,hm,func,label,"");
	   }

	   public static HashMap validateUserName(String str1,String str2,String msg,String path,HashMap hm,String func,String label,String UNITID){
		  
		  if( !(str2 !=null && str2.trim().length()>1) ){
			 System.out.println("In if case func :: "+func+" for :: "+str2);
		  }else if((func.trim()).equals("signup")){		
			  System.out.println("In else if case func: "+func+" for :: "+str2);
			  //String exists=DbUtil.getVal("select 'yes' from AUTHENTICATION Where lower(Login_Name)=?", new String[]{str2.toLowerCase()});
				// if("yes".equals(exists)) hm.put(path,msg);
			  String exists=EventBeeValidations.isValidBeeIDCustomUrl(str2);
			  if("alreadyexists".equals(exists)) hm.put(path,msg);
		  }
		 
		return hm;
	    }
	   
	   public static HashMap validateUserName(String str1,String str2,String msg,String path,HashMap hm,String func,String label,String UNITID,String usrId){
			  
			  if( !(str2 !=null && str2.trim().length()>1) ){
				 
			  }else if((func.trim()).equals("signup")){			
				  String exists=DbUtil.getVal("select 'yes' from AUTHENTICATION Where lower(Login_Name)=? and user_id not in(?)", new String[]{str2.toLowerCase(),usrId});
					 if("yes".equals(exists)) hm.put(path,msg);
			  }
			 
			return hm;
		    }
	   
	   public static HashMap validateDiscountCode(String str1,AddCommunityMemberData memberdata,String msg,String path,HashMap hm,String coupontype,String usrId,String groupId){
		   	if( memberdata.getDiscountcode().trim().length()>0){
		   		
		   		String clubmembermaster = " select membership_id from club_membership_master "
					+ " where clubid=to_number(?,'999999999') and membership_name=? and status='ACTIVE'";
		   		String membership_id = DbUtil.getVal(clubmembermaster, new String[] {
					groupId, memberdata.getMembershiptype() });
		   		
		   		
				  String query = "select discount,discounttype from coupon_master where couponid in (select couponid from coupon_codes where couponcode=?) and groupid=? and coupontype=?";
				  DBManager dbmanager = new DBManager();
				  StatusObj statobj = dbmanager.executeSelectQuery(query,new String[] {memberdata.getDiscountcode().trim(),groupId,coupontype});
				  if(!statobj.getStatus()){
					  String existingMemShipIdQry = "select membership_id from club_member where userid=?";
					  String existingMemShipId = DbUtil.getVal(existingMemShipIdQry, new String[] {usrId});
					  if(existingMemShipId.equals(membership_id)){
						  String exists=DbUtil.getVal("select 'yes' from transaction where discountcode=? and refid=?", new String[]{memberdata.getDiscountcode().trim(),usrId});
							 if(!"yes".equals(exists))
								 hm.put(path,msg);
					  }else
						  hm.put(path,msg);
					  
				  }
				  if(statobj.getStatus()){
					  /*String clubmembermaster = " select membership_id from club_membership_master "
							+ " where clubid=to_number(?,'999999999') and membership_name=? and status='ACTIVE'";
					  String membership_id = DbUtil.getVal(clubmembermaster, new String[] {
							groupId, memberdata.getMembershiptype() });*/
					  String selquery="select membership_id from coupon_membership  where couponid in(select couponid from coupon_master where couponid in (select couponid from coupon_codes where couponcode=?) and groupid=?)";
					  HashMap hmp=new HashMap();
					  DBManager db=new DBManager();
					  StatusObj sb=db.executeSelectQuery(selquery,new String []{memberdata.getDiscountcode().trim(),groupId});
					  if(sb.getStatus()){
						  for(int i=0;i<sb.getCount();i++)
							  hmp.put(db.getValue(i,"membership_id",""),"");

					  }
					
					  if(!hmp.containsKey(membership_id))
						  hm.put(path,msg);
					  
						
				  }
				  
			  }
			 
			return hm;
		    }
	   
	   public static HashMap validateEquals(String str1,String str2,String msg,String path,HashMap hm){
	        if(!(str1.equals(str2))){
	       	    hm.put(path,msg);
		}
		return hm;
	   }
	   public static HashMap validateString(String str,String label,String path,int maxlen,int minlen,HashMap hm){
	        StatusObj stob=new StatusObj(false,"",null);
	        stob=EventBeeValidations.isValidStr(str,label,maxlen,minlen);
	        if(!stob.getStatus()){
	   	    hm.put(path,stob.getErrorMsg());
		}
		return hm;
	   }

	   public static HashMap validateRadio(String str,String label,String path,HashMap hm){
		if(("".equals(str))||(str==null)){
	   	    hm.put(path,"Select "+label);
		}
		return hm;
	   }
	   public static HashMap validateEmail(String str,String label,String path,HashMap hm){
	        StatusObj stob=new StatusObj(false,"",null);
	        stob=EventBeeValidations.isValidEmail(str,label);
	        if(!stob.getStatus()){
	    	    hm.put(path,stob.getErrorMsg());
		}
		return hm;
	   }
	   public static final String MEM_SEQ_GET="select org_id as orgid,unit_id as unitid, -100 as roleid,"
		   +" nextval('seq_userid') as userid, nextval('seq_transactionid') as transactionid"
		   +" from org_unit where unit_id=to_number(?,'9999999999')";
	   public static  HashMap getSequenceID(String unitid){		 
		   DBManager dbmanager=new DBManager();
			StatusObj statobj=dbmanager.executeSelectQuery(MEM_SEQ_GET,new String[]{unitid});
			HashMap hm=new HashMap();
			if(statobj.getStatus()){
				hm.put("orgid",dbmanager.getValue(0,"orgid",""));
				hm.put("unitid",dbmanager.getValue(0,"unitid",""));
				hm.put("roleid",dbmanager.getValue(0,"roleid",""));
				hm.put("userid",dbmanager.getValue(0,"userid",""));
				hm.put("transactionid",dbmanager.getValue(0,"transactionid",""));
			
			}
		   return hm;
	   }
	   public static void processSignUp(UserData userData){
		   
		   StatusObj stob=null;
			boolean flag=false;
			HashMap hm=new HashMap();
			hm.put("acctstatus","1");
			hm.put("loginname",(String)userData.getBeeId());
			hm.put("password",(String)userData.getPassword());
	        hm.put("firstname",(String)userData.getFirstName());
			hm.put("lastname",(String)userData.getLastName());
	        hm.put("email",(String)userData.getEmail());
	        hm.put("phone",(String)userData.getPhone());
			hm.put("scrname",(String)userData.getBeeId());
			//hm.put("gender",(String)userData.getGender());
			hm.put("refer_by",(String)userData.getReferBy());
			hm.put("category",(String)userData.getCategory());
			hm.put("refer_source",(String)userData.getReferType());
			hm.put("promotioncode",(String)userData.getPromotionCode());
			hm.put("twitterpage",(String)userData.getTwitterpage());
			hm.put("fbfanpage",(String)userData.getFbfanpage());
			
			HashMap seqHm=getSequenceID("13579");
			
			if(seqHm!=null){
				hm.put("orgid",(String)seqHm.get("orgid"));
				hm.put("unitid",(String)seqHm.get("unitid"));
				hm.put("roleid",(String)seqHm.get("roleid"));
				hm.put("userid",(String)seqHm.get("userid"));
				hm.put("transactionid",(String)seqHm.get("transactionid"));	
				hm.put("usertoken",EncodeNum.encodeNumUserId((String)hm.get("userid")));
			}
			stob=insertMemberData(hm);
			
			if(stob.getStatus()){
				/*String themecode=EbeeConstantsF.get("accounts.basic.deftheme","movablemanila");
				//Insert the themes of a signup user:
				
				StatusObj statobj=new StatusObj(false,"",null);
				String USERTHEMES_QUERY="insert into user_roller_themes (userid,module,themecode,cssurl) values(to_number(?,'999999999999999'),?,?,?)";
				String name=(String)hm.get("loginname");
				String ethemecode="basic";
				String pthemecode="basic";
				String nthemecode="basic";
				String hthemecode="basic";
				DBQueryObj [] dbquery=new DBQueryObj [3];
						dbquery[0]=new DBQueryObj();
						dbquery[0].setQuery(USERTHEMES_QUERY);
						dbquery[0].setQueryInputs(new String[] {(String)hm.get("userid"),"eventspage",ethemecode,ethemecode+".css"});
						dbquery[1]=new DBQueryObj();
						dbquery[1].setQuery(USERTHEMES_QUERY);
						dbquery[1].setQueryInputs(new String[] {(String)hm.get("userid"),"photo",pthemecode,pthemecode+".css"});
						dbquery[2]=new DBQueryObj();
						dbquery[2].setQuery(USERTHEMES_QUERY);
						dbquery[2].setQueryInputs(new String[] {(String)hm.get("userid"),"network",nthemecode,nthemecode+".css"});*///commented on 03-Mar-2016
						//statobj=DbUtil.executeUpdateQueries(dbquery);
				//Partner Signup
				 String INSERTQ="insert into group_partner (partnerid,title,message,userid,url,status,created_at) values (nextval('group_partnerid'),'','',?,'','Active',now())";
				 StatusObj stobjt=null;
				 stobjt=DbUtil.executeUpdateQuery(INSERTQ,new String [] {(String)hm.get("userid")});
				 //end of Partner Signup	
				 System.out.println("*** In processSignUp BeeId: "+userData.getBeeId()+" userid: "+hm.get("userid")+" usertoken: "+hm.get("usertoken"));
				 
				 userLangTrack((String) hm.get("userid"),I18n.getLanguageFromSession().toString(),"signup","0");//language specific track
				 
				 SendEmail(hm);
				 
			 }
	   }
	  
	   public static StatusObj insertMemberData(HashMap usrMap){
			StatusObj statusObj=null;
			int rcount=0;
			try{				 
				 rcount=InsertUserProfileEntry(usrMap);
				 if(rcount>0){
					 rcount=0;
				 	 rcount=InsertAuthenticationEntry(usrMap);
					 if(rcount>0){
						 updateUserPreference((String)usrMap.get("userid"),"pref:myurl",(String)usrMap.get("scrname"));
					 }
				}
				if(rcount>0){
					statusObj=new StatusObj(true, "success", usrMap);
				}else{					
					statusObj=new StatusObj(false, "member insertion failed",null);
				}
			}catch(Exception e1){
				statusObj=new StatusObj(false, e1.getMessage(), null);
			}
			return statusObj;
		   }
	   final static String USER_PROFILE_INSERT =" insert into user_profile(user_id,first_name, "
			+" last_name,email,phone,mobile,ref_source,ref_by,street,city,state,zip, "
			+" country,title,company,blogurl,companyurl,personalurl,shareprofile,industry,favorite_stars, "
			+" native_country,native_state,native_city,interests, "
			+" created_by,created_at,category,promotioncode,twitter_page,fb_fan_page,ip) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
			+" 'com.eventbee.useraccount.AccountDB',now(),?,?,?,?,?) ";
	   public static int InsertUserProfileEntry(HashMap DataHash){
		  
			int rcount=0;
			String user_id=(String)DataHash.get("userid");
			HttpServletRequest request = ServletActionContext.getRequest();
			String ip = AddEventAction.getIpFromRequest(request);
			StatusObj statusObj=DbUtil.executeUpdateQuery(USER_PROFILE_INSERT,new String[]{user_id,
					(String)DataHash.get("firstname"),(String)DataHash.get("lastname"),
					(String)DataHash.get("email"),(String)DataHash.get("phone"),
					(String)DataHash.get("mobile"),(String)DataHash.get("refer_source"),
					(String)DataHash.get("refer_by"),(String)DataHash.get("street"),
					(String)DataHash.get("city"),(String)DataHash.get("state"),(String)DataHash.get("zip"),
					(String)DataHash.get("country"),(String)DataHash.get("title"),(String)DataHash.get("company"),
					(String)DataHash.get("blogurl"),(String)DataHash.get("companyurl"),
					(String)DataHash.get("personalurl"),(String)DataHash.get("shareprofile"),
					(String)DataHash.get("industry"),(String)DataHash.get("favorite_stars"),
					(String)DataHash.get("natcountry"),(String)DataHash.get("natstate"),
					(String)DataHash.get("natcity"),(String)DataHash.get("interests"),
					(String)DataHash.get("category"),(String)DataHash.get("promotioncode"),
					(String)DataHash.get("twitterpage"),(String)DataHash.get("fbfanpage"),ip});					
			rcount=statusObj.getCount();
			
			return rcount;
	   }
	   
	   final static String AUTHENTICATION_INSERT =" insert into authentication(auth_id, "
		    +" org_id,unit_id,role_id,user_id, "
		    +" login_name,password,acct_status, "
		    +" created_by,created_at,description,membership_status,accounttype,usertoken) "
		    +" values (to_number(?,'9999999999'),to_number(?,'9999999999'),to_number(?,'9999999999'),to_number(?,'9999999999'),?,?,?,to_number(?,'9999999999'), "
		    +" 'com.eventbee.useraccount.AccountDB', now(),?,?,'Pending',?) ";

	   public static int InsertAuthenticationEntry(HashMap DataHash){
		   System.out.println("insert authentication method");
			int rcount=0;
			try{
			String encpass=(String)DataHash.get("password");	
			encpass=(new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME) ).encrypt(encpass);
			StatusObj statusObj=DbUtil.executeUpdateQuery(AUTHENTICATION_INSERT,
					new String[]{(String)DataHash.get("userid"),
					(String)DataHash.get("orgid"),(String)DataHash.get("unitid"),
					(String)DataHash.get("roleid"),(String)DataHash.get("userid"),
					(String)DataHash.get("loginname"),encpass,(String)DataHash.get("acctstatus"),
					(String)DataHash.get("nonprofitdesc"),(String)DataHash.get("membershipstatus"),
					(String)DataHash.get("usertoken")});		
			rcount=statusObj.getCount();
			}catch(Exception e){
				System.out.println("exception:::::"+e.getMessage());
			}
			return rcount;
	   }
	   static final String MEM_PREFERENCE_DELETE="delete from member_preference where pref_name like ? and user_id=?";
	   final static String MEM_PREFERENCE_INSERT =" insert into member_preference( "
		    +" user_id, pref_name, pref_value) values (?,?,?) ";
	   public static boolean updateUserPreference(String userid,String pref_name,String pref_value){	  
	   	int i=0;
	   	try{
			DbUtil.executeUpdateQuery(MEM_PREFERENCE_DELETE,new String [] {pref_name,userid});
			StatusObj statusObj=DbUtil.executeUpdateQuery(MEM_PREFERENCE_INSERT, new String[]{userid,pref_name,pref_value});
			i=statusObj.getCount();
	   	}catch(Exception e){
	   		System.out.println("excepttion::::"+e.getMessage());
	   	}
	   	if (i>0)
			return true;
		else
			return false;
	   	}
	  static void SendEmail(HashMap hm){
		    try{
		    	String mailbcc=EbeeConstantsF.get("mail_ebee_bcc","bala@eventbee.org");
		    	//EmailTemplate emailtemplate=new EmailTemplate("13579","EBEE_SIGNUP_ACTIVATION_EMAIL");
		    	String lang=I18n.getLanguageFromSession();
				HashMap<String, String> purposeHm= new HashMap<String,String>();
				purposeHm.put("purpose", "EBEE_SIGNUP_ACTIVATION_EMAIL");
				purposeHm.put("isCustomFeature", "N");
				HashMap<String, String> emailTemplate=new HashMap<String, String>();
				ConfirmationEmailDAO emailDao=new ConfirmationEmailDAO();
				emailTemplate=(HashMap<String, String>)emailDao.getData(purposeHm, lang, "").get("emailTemplate");
				
		        EmailObj obj=EventbeeMail.getEmailObj();
		        Map mp=new HashMap();
		        mp.put(TemplateConstants.TO_FIRST_NAME,(String)hm.get("firstname"));
		        mp.put(TemplateConstants.TO_LAST_NAME,(String)hm.get("lastname"));
		        mp.put(TemplateConstants.USERID,(String)hm.get("loginname"));
		        mp.put(TemplateConstants.MGRID,(String)hm.get("usertoken"));
		        String password=(String)hm.get("password");
		        if(password.indexOf("<")>-1 || password.indexOf(">")>-1){
		        	password=password.replace("<","&lt;");
		        	password=password.replace(">","&gt;");
		        }
		        mp.put(TemplateConstants.PASSWORD,password);
		        String HTMLcontent=TemplateConverter.getMessage(mp,emailTemplate.get("htmlFormat"));
		        String serverAddress=EbeeConstantsF.get("serveraddress","www.eventbee.com"); 
		        HTMLcontent=HTMLcontent.replaceAll("www.eventbee.com", serverAddress);
		        obj.setTo((String)hm.get("email"));
		        obj.setFrom(emailTemplate.get("fromMail"));
				//obj.setBcc("bala@beeport.com,newsignup@eventbee.com");
		        obj.setBcc(mailbcc);
		        obj.setSubject(TemplateConverter.getMessage(mp,emailTemplate.get("subjectFormat")));
		        obj.setHtmlMessage(HTMLcontent);
		        obj.setSendMailStatus(new SendMailStatus((String)hm.get("unitid"),"SIGNUP_CONFIRMATION",(String)hm.get("userid"),"1"));
		        //EventbeeMail.sendHtmlMail(obj);
		        EventbeeMail.sendHtmlMailPlain(obj);		        
		        EventbeeMail.insertStrayEmail(obj,"html","S");
		    }
		    catch(Exception e){
		       EventbeeLogger.logException("com.eventbee.exception",EventbeeLogger.INFO,"UserDB.java","SendMail()","ERROR in SendMail method",e);
		    }
		}
	  public static ArrayList populatereferTypeList(){
			ArrayList list=new ArrayList();
			list.add("Google");
			list.add("Facebook");
			list.add("Twitter");
			list.add("Email");
			list.add("Search");
			list.add("Media");
			list.add("Friend");
			list.add("Customer");
			list.add("Other");
			return list;
		}
	   final static String IsvalidMemberid="select a.login_name,a.password,b.email from authentication a,"
		  +" user_profile b where a.user_id=b.user_id  and lower(b.email)=? and a.unit_id=to_number(?,'99999999999999') ";

	  public static Vector isValidMember(String email,String unit_id,String forgottype){
		  DBManager dbmanager = new DBManager();
		  StatusObj statobj = null;
		  HashMap hm=null;
		  Vector v=new Vector();
		  if("submgr".equals(forgottype))
	    {String Issubmgr="select email as login_name,password,email from submanager where lower(email)=?";  
	     statobj=dbmanager.executeSelectQuery(Issubmgr,new String []{email.toLowerCase().trim()});
	    }
		else
		statobj=dbmanager.executeSelectQuery(IsvalidMemberid,new String []{email.toLowerCase().trim(),unit_id});
		   
	    if(statobj.getStatus()){
	    	for(int k=0;k<statobj.getCount();k++){	
	    		hm=new HashMap();
	    		hm.put("login_name",dbmanager.getValue(k,"login_name",""));
	    		String password=dbmanager.getValue(k,"password","");
	    		try {
	    			hm.put("password",(new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME) ).decrypt(password));
	    		} catch (EncryptionException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		hm.put("email",dbmanager.getValue(k,"email",""));
	    		v.add(hm);
	    	}
	    }
	    	return v;
	  }
	  
	  public static void SendEmail(HashMap emailcontent,int account_count,String unitid){
		 	try{
			String   purpose="FORGOT_LOGIN";
			String type=(String)emailcontent.get("type");
			if(account_count>1)
			purpose="FORGOT_MULTIPLE_LOGIN_PASSWORD";
			else if(account_count==-498)
				purpose="FORGOT_SUBMGR_LOGIN";
			
			String lang=I18n.getLanguageFromSession();
			HashMap<String, String> purposeHm= new HashMap<String,String>();
			purposeHm.put("purpose", purpose);
			purposeHm.put("isCustomFeature", "N");
			HashMap<String, String> emailTemplate=new HashMap<String, String>();
			ConfirmationEmailDAO emailDao=new ConfirmationEmailDAO();
			emailTemplate=(HashMap<String, String>)emailDao.getData(purposeHm, lang, "").get("emailTemplate");
			
			//EmailTemplate emailtemplate=new EmailTemplate("13579",purpose,"0");
				EmailObj emailobj=EventbeeMail.getEmailObj();
				emailobj.setFrom(emailTemplate.get("fromMail"));
				String replyto=emailTemplate.get("replyToMail");
				if(replyto==null||"".equals(replyto.trim()))
					replyto=emailTemplate.get("fromMail");
				emailobj.setReplyTo(replyto);
				emailobj.setTo((String)emailcontent.get("email"));
				Map message=fillData(emailcontent);
				//System.out.println("meesage:::"+message);
				emailobj.setSubject(TemplateConverter.getMessage(message,emailTemplate.get("subjectFormat")));
				//emailobj.setTextMessage(TemplateConverter.getMessage(message,emailtemplate.getTextFormat()));
				emailobj.setHtmlMessage(TemplateConverter.getMessage(message,emailTemplate.get("htmlFormat")));
				emailobj.setSendMailStatus(new SendMailStatus("0",purpose,unitid,"1"));
				//EventbeeMail.sendTextMailPlain(emailobj);
				EventbeeMail.sendHtmlMailPlain(emailobj);
			}catch(Exception e){
				 System.out.println(" There is an error in Login Problem send mail:"+ e.getMessage());
		   //	e.printStackTrace();
			}
		}
	  public static Map fillData(HashMap emailcontent){
			Map mp=new HashMap();
				mp.put("#**password**#",(String)emailcontent.get("dbpassword"));
				mp.put("#**loginname**#",(String)emailcontent.get("dbloginnames"));
				
			return mp;
	 }
	  
	  public static String authenticateUser(String uname, String pword){
		  String uid=authenticateUser(uname,pword,null,null);
		  return uid;
	  }
	  
	  public static String authenticateUser(String uname, String pword,String useragent,String userip){
			String uid=null,accounttype=null;
			String VALIDATE_USER_QUERY="select User_ID,accounttype from AUTHENTICATION " +
			" where lower(Login_Name)=? AND Password=? ";
			if(log.isDebugEnabled())
				log.debug("User attempt to login: "+uname);
			try{
				String encPword=(new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME) ).encrypt(pword);
				DBManager dbmanager=new DBManager();
				StatusObj statobj=dbmanager.executeSelectQuery(VALIDATE_USER_QUERY,new String[]{uname.toLowerCase(),encPword});
				
				if(statobj.getStatus()){
					uid=dbmanager.getValue(0,"User_ID","");
					accounttype=dbmanager.getValue(0,"accounttype","");
					if(!"blocked".equals(accounttype) && !"Pending".equals(accounttype)){						
							if(log.isInfoEnabled())
								log.info("User login for : "+uname+" is "+uid);
								String qry="insert into loggedinusers(uid, logintime, status,user_agent,user_ip) values(?,now(), 'P',?,?)";
								DbUtil.executeUpdateQuery(qry, new String[]{uid,useragent,userip});
							}else if("Pending".equals(accounttype)){
								uid="inactive";		
								log.info("Pending accounttype User attempted to login: "+uname);
							}else{							
								uid=null;
								log.info("Invalid User attempt to login: "+uname);
							}
			   }else{
				   System.out.println("Invalid user");
				   log.info("Invalid User attempt to login: "+uname);
			   }
			}catch(Exception ex){
				log.error("User attempt to login failed for: "+uname +" message: "+ex.getMessage());
			}
			return uid;
		}
	  public static String validateToken(String token){
	  String userexists=DbUtil.getVal("select 'Y' as userexists from loggedinusers where uid=? and status='P'", new String[]{token}); 
	  DbUtil.executeUpdateQuery("update loggedinusers set status='U' where uid=? and status='P'", new String[]{token});
	  return userexists;
	  }
	  
	  public static void removeDuplicates(String beeId){
		 
		   ArrayList userIdList=new ArrayList();
		   DBManager dbmanager=new DBManager();
		   StatusObj statobj=dbmanager.executeSelectQuery("select user_id from authentication where login_name=? order by user_id", new String[]{beeId});
		   for(int i=0;i<statobj.getCount();i++)
			   userIdList.add(dbmanager.getValueFromRecord(i, "user_id", ""));
		   if(userIdList.size()>1 ){
			   log.info("Duplicate entries found in authetication for Login: "+beeId);
			   log.info("Deleting duplicates..");
			   
			   for(int j=1;j<userIdList.size();j++){
				   String userid=(String)userIdList.get(j);
				   System.out.println("Deleting duplicates beeId: "+beeId+" userid: "+userid);
				  statobj= DbUtil.executeUpdateQuery("delete from authentication where user_id=? and login_name=?",new String[]{userid,beeId});
				  statobj= DbUtil.executeUpdateQuery("delete from member_preference where user_id=?",new String[]{userid});
				  }
		   }
	  }
	  
	  
	  
	  public static boolean CheckLoginDetails(String loginname,UserData userData){
		  
		System.out.println("we are in checklogindetails" + loginname);
		boolean status = false;
		DBManager db = new DBManager();
		StatusObj statobj = null;
		String login_name = "";
		String password = "";
		String email = "";
		String select_authentication = " select a.login_name,a.password,a.user_id,b.email from authentication a,user_profile b where a.user_id=b.user_id and lower(a.login_name)=? ";
		statobj = db.executeSelectQuery(select_authentication, new String[] { loginname.toLowerCase() });
	    /*login_name = db.getValue(0, "login_name", "");
		password = db.getValue(0, "password", "");
		email = db.getValue(0, "email", "");

		String userpwd = userData.getPassword();
		String userencpass = "";
		try {
			userencpass = (new StringEncrypter(
					StringEncrypter.DES_ENCRYPTION_SCHEME)).encrypt(userpwd);
		} catch (Exception e) {

		}

		String useremail = userData.getEmail();
		String userloginname = loginname;

		if (userloginname.equals(login_name) && userencpass.equals(password)
				&& useremail.equals(email))
			status = true;

		else
			status = false;*/
		if(statobj.getStatus() && statobj.getCount()>0)
			status = false;
		else status = true;
		return status;  
  }
	  
	  public static String getMarketingUser(String token){

		  String marketingUserExists=DbUtil.getVal("select 'Y' from marketing_users where refid=?", new String[]{token}); 
		  if(marketingUserExists==null)
			  marketingUserExists="N";
		  return marketingUserExists;
	  }
	  
	  public static void activateManager(UserData udata,String userid,String useragent,String userip){
		 // String decodetoken=EncodeNum.decodeUserId(token.trim());
		  String updateUserProfile="update user_profile set ref_by=?,first_name=?,last_name=?,phone=?,twitter_page=?,fb_fan_page=?,promotioncode=? where user_id=?";
		  String updateAuthentication="update authentication set accounttype=NULL where user_id=?";
		  String qry="insert into loggedinusers(uid, logintime, status,user_agent,user_ip) values(?,now(), 'P',?,?)";
		  DbUtil.executeUpdateQuery(updateUserProfile, new String[]{udata.getReferBy(),udata.getFirstName(),udata.getLastName(),udata.getPhone(),udata.getTwitterpage(),udata.getFbfanpage(),udata.getPromotionCode(),userid});
		  DbUtil.executeUpdateQuery(updateAuthentication, new String[]{userid});
		  DbUtil.executeUpdateQuery(qry, new String[]{userid,useragent,userip});
		  
		  String beeId=DbUtil.getVal("select login_name from authentication where user_id=?", new String[]{userid});
		  StatusObj stobj=DbUtil.executeUpdateQuery("delete from authentication where accounttype='Pending' and login_name=?",new String[]{beeId});
		  System.out.println("deleted duplicates for beeid: "+beeId+" count: "+stobj.getCount());
		  
		  userLangTrack(userid,I18n.getLanguageFromSession().toString(),"signup-activation","0");//language specific track
	  }
	  
	  public static boolean validateUser(String userId,String token){
		  String mgrId=DbUtil.getVal("select user_id from authentication where usertoken=? ",new String[]{token});
		  if(mgrId==null)mgrId="";
		  if(mgrId.equals(userId))
		  return true;
		  else
			  return false;
	  }
	  
	  public static HashMap<String,String> getUserToen(String token){
		  if(token==null)token="";
		  String query="select mgr_id,action_pattern,eventid from main_to_classic_token where status='ACTIVE' and token_id=?";
		  HashMap<String,String> userMap=new HashMap<String,String>();
		  DBManager dbm=new DBManager();
		  StatusObj sbj=null;
		  sbj=dbm.executeSelectQuery(query,new String[]{token.trim()});
		  System.out.println("getUserToen the count is::"+sbj.getCount());
		  if(sbj.getStatus() && sbj.getCount()>0){
			  String mgrId=dbm.getValue(0,"mgr_id","");
			  String action_pattern=dbm.getValue(0,"action_pattern","");
			  if(action_pattern==null || "".equals(action_pattern.trim()))action_pattern="";
			  String eventid=dbm.getValue(0,"eventid","");
			  userMap.put("mgrid",mgrId);
			  userMap.put("actionpattern",action_pattern);
			  userMap.put("eid",eventid);
		  }
		  return userMap;
	  }
	  
	  public static void makeTokenExpire(String tokenId){
		  String query="update main_to_classic_token set  status='EXPIRED' where token_id=?";
		  DbUtil.executeUpdateQuery(query,new String[]{tokenId});
	  }
	  
	  public static void makeEnrtyInLoggedInUsers(String mgrId,String userIp,String userAgent){
		  String qry="insert into loggedinusers(uid, logintime, status,user_agent,user_ip) values(?,now(), 'P',?,?)";
			DbUtil.executeUpdateQuery(qry, new String[]{mgrId,userAgent,userIp});
		  
	  }
	  
	  public static void userLangTrack(String userid, String lang, String action,String eventid){
		  String Query="insert into user_lang_track(userid,country,action,subid,date) values(?,?,?,CAST(? AS BIGINT),now())";
		  DbUtil.executeUpdateQuery(Query, new String[]{ userid,  lang,  action, eventid});
	  }
}
