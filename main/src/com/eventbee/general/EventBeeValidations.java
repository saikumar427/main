package com.eventbee.general;

import java.text.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.event.helpers.I18n;

public class  EventBeeValidations{

	 public static final SimpleDateFormat SDF = new SimpleDateFormat("MM-dd-yyyy");
	 public static final SimpleDateFormat SDF1 = new SimpleDateFormat("MM/dd/yy");

	public static StatusObj isValidNumber(String num,String label,String type){
		StatusObj stob=new StatusObj(false,null,num);
		if(type==null)type="integer";
		if(num==null||"".equals(num)){
			stob.setErrorMsg(label+" "+I18n.getString("sub.is.empty.seterrormsg.lbl"));
			return stob;
		}else if("integer".equalsIgnoreCase(type)){

			EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"EventbeeValidations.java",null,"type of numaric  is------"+type,null);
				try{
					String data=""+Integer.parseInt(num);
					stob.setData(data);
					stob.setStatus(true);
				}catch(Exception e){
					stob.setErrorMsg(I18n.getString("la.invalid.lbl")+" "+label);
					return stob;
				}
		}
		else if("double".equalsIgnoreCase(type)){
				try{
					String data=""+Double.parseDouble(num);
					stob.setData(data);
					stob.setStatus(true);
				}catch(Exception e){
					stob.setErrorMsg(I18n.getString("la.invalid.lbl")+" "+label);
					return stob;
				}
		}
		else if("float".equalsIgnoreCase(type)){
				try{
					String data=""+Float.parseFloat(num);
					stob.setData(data);
					stob.setStatus(true);
				}catch(Exception e){
					stob.setErrorMsg(I18n.getString("la.invalid.lbl")+" "+label);
					return stob;
				}
		}

		return stob;
	}



public static StatusObj isValidNumber(String num,String label,String type,double checkwith){


		StatusObj stob=new StatusObj(false,null,num);
		if(type==null)type="integer";
		if(num==null||"".equals(num)){
			stob.setErrorMsg(label+" "+I18n.getString("sub.is.empty.seterrormsg.lbl"));
			return stob;
		}else if("integer".equalsIgnoreCase(type)){
				try{
					int temp=Integer.parseInt(num);
					String data=""+temp;
					if(temp<checkwith){
						stob.setErrorMsg(I18n.getString("la.invalid.lbl")+" "+label);
					}else{
					stob.setData(data);
					stob.setStatus(true);
					}
				}catch(Exception e){
					stob.setErrorMsg(I18n.getString("la.invalid.lbl")+" "+label);
					return stob;
				}
		}
		else if("double".equalsIgnoreCase(type)){
				try{
					double temp=Double.parseDouble(num);
					String data=""+temp;
					if(temp<checkwith){
						stob.setErrorMsg(I18n.getString("la.invalid.lbl")+" "+label);
					}else{
					stob.setData(data);
					stob.setStatus(true);
					}
				}catch(Exception e){
					stob.setErrorMsg(I18n.getString("la.invalid.lbl")+" "+label);
					return stob;
				}
		}
		else if("float".equalsIgnoreCase(type)){
				try{

					float temp=Float.parseFloat(num);
					String data=""+temp;
					if(temp<checkwith){
						stob.setErrorMsg(I18n.getString("la.invalid.lbl")+" "+label);
					}else{
					stob.setData(data);
					stob.setStatus(true);
					}
				}catch(Exception e){
					stob.setErrorMsg(I18n.getString("la.invalid.lbl")+" "+label);
					return stob;
				}
		}

		return stob;
	}

	public static boolean isValidFromEmail(String email){
	Pattern p = Pattern.compile("^[a-zA-Z0-9!#$%&'*+/=?^_`():{|}<>~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^:_`(){|}<>~-]+)*@(?:[a-zA-Z0-9!#$%&'*+/=?:^_`(){|}<>~-](?:[_a-zA-Z0-9-'!#$%&'*+/=?^:_`(){|}<>~-]*[_a-zA-Z0-9-'!#$%&'*+/=?:^_`(){|}<>~-])?\\.)+[_a-zA-Z0-9-'!#$%&'*+/=?:^_`(){|}<>~-](?:[_a-zA-Z0-9-'!#$%&'*+/=?^:_`(){|}<>~-]*[_a-zA-Z0-9-'!#$%&'*+/=?:^_`(){|}<>~-])?$");
    Matcher m = p.matcher(email);
    boolean matchFound = m.matches();
    if (matchFound)
      return true;
    else
      return false;
    }

	public static StatusObj isValidEmail(String email,String label){
		StatusObj stob=new StatusObj(false,null,email);
		if(email==null||"".equals(email.trim())){
			stob.setErrorMsg(label+" "+I18n.getString("sub.is.empty.seterrormsg.lbl"));
			return stob;
		}
		int s1=email.indexOf('@');
		int s2=email.indexOf('.');
           if ((s1==-1)||(s2==-1)){
                 //stob.setErrorMsg(email+" is invalid ");
		 stob.setErrorMsg(I18n.getString("sub.invalid.format.lbl")+" "+label);
		 return stob;
	   }else if(email.indexOf(';')>-1 || email.indexOf(',')>-1){
			stob.setErrorMsg(I18n.getString("only.one.email.is.allowed.errmsg"));
			return stob;
		}
	   else{
		   stob.setStatus(true);
	   }   
		return stob;
	}

	public static boolean isStringValidForEmail(String email,String label){
		/*
		List list=EventbeeStrings.getEmailPermittedChar();
		for(int i=0;i<email.length();i++){
			String c=email.charAt(i)+"";
			if(!list.contains(c)){
				return false;
			}
		}*/
		return true;
	}

	public static StatusObj isValidStr(String str,String label){
		return isValidStr( str,label,0,0);
	}

	public static StatusObj isValidStr(String str,String label,int maxlength,int minlength){
		StatusObj stob=new StatusObj(false,null,str);
		if(str==null||"".equals(str.trim())){
			if("Password".equals(label))
				label=I18n.getString("global.pwd.ph");
			stob.setErrorMsg(label+" "+I18n.getString("sub.is.empty.seterrormsg.lbl"));
			return stob;
		}
		String lbl=EbeeConstantsF.get("login.label","User Name");
		 if(maxlength>0){
			int temp=str.length();
			if(!(temp<=maxlength)){
				if(lbl.equalsIgnoreCase(label) || "Password".equalsIgnoreCase(label))
					stob.setErrorMsg(label+" "+I18n.getString("sub.pwd.max.siz.msg")+" "+maxlength);
					else{
						if("Password".equals(label))
							label=I18n.getString("global.pwd.ph");
						stob.setErrorMsg(I18n.getString("sub.pwd.permetted.msg")+" "+label+ " "+I18n.getString("sub.is.lbl")+" "+maxlength);
					}
				return stob;
			}
		}
		 if(minlength>0){
			int temp=str.length();
			if(!(temp>=minlength)){
				if("Password".equals(label))
					label=I18n.getString("global.pwd.ph");
			stob.setErrorMsg(I18n.getString("sub.min.size.msg")+" "+label+ " "+I18n.getString("sub.is.lbl")+" "+minlength);
				return stob;
			}
		}

		stob.setStatus(true);
		return stob;
	}

	public static StatusObj getValiNum(String str ){
		StatusObj statobj=new StatusObj(false,"Not a Valid Number",str.trim());
		try{
			DecimalFormat df=new DecimalFormat("0.00");
			df.setMaximumFractionDigits(2);
			Number n=df.parse(statobj.getData().toString(),new ParsePosition(0) );

			statobj.setData( df.format(n.doubleValue()) );
			statobj.setStatus(true);

		}catch(Exception e){
		      System.out.println("Error in EventBeeValidations getValiNum():="+e.getMessage());
		}
		return statobj;

	}

      public static StatusObj isValidDate(String dateString,String label){
		StatusObj statobj=null;
		statobj=isValidStr(dateString,label);
		if(!statobj.getStatus())return statobj;
		statobj.setStatus(false);
		statobj.setErrorMsg(label+" "+I18n.getString("la.is.not.a.valid.date"));
		try{
			SDF.setLenient(false);
			Date date=SDF.parse(dateString.trim());
			statobj.setData(date);
			statobj.setStatus(true);
		}catch(Exception ex){
			//System.out.println(dateString+"exception= "+ex.getMessage());
		}
		return statobj;
	}


      public static StatusObj isValidDate1(String dateString,String label){
		//checks the dates which are in MM-dd-yyyy format;
		StatusObj statobj=null;
		statobj=isValidStr(dateString,label);
		if(!statobj.getStatus())return statobj;
		statobj.setStatus(false);
		statobj.setErrorMsg(label+" is  not a valid date");
		try{
			SDF1.setLenient(false);
			Date date=SDF1.parse(dateString.trim());
			statobj.setData(date);
			statobj.setStatus(true);
		}catch(Exception ex){
			//System.out.println(dateString+"exception= "+ex.getMessage());
		}
		return statobj;
	}






	public static StatusObj isValidDate(String month,String dayofmonth,String year,String label,boolean validateWithCurrent){
		//month index for jan is 1
		String dateString=month+"-"+dayofmonth+"-"+year;
		StatusObj stobj= isValidDate(dateString,label);

		if(!stobj.getStatus())return stobj;
		stobj.setStatus(false);
		stobj.setErrorMsg(label +" "+I18n.getString("la.is.before.current.date"));
		if(validateWithCurrent ){
			StatusObj stobjcurrent= isValidDate(getCurrentDateString(),"current");
			Date querydate=(Date)stobj.getData();
			Date currdate=(Date)stobjcurrent.getData();
			if( querydate.equals(currdate) || querydate.after(currdate) ){
				stobj.setStatus(true);
			}
		}
		return stobj;
	}



	public static StatusObj isValidDate1(String month,String dayofmonth,String year,String label,boolean validateWithCurrent){
		//month index for jan is 1
		String dateString=month+"/"+dayofmonth+"/"+year;
		StatusObj stobj= isValidDate1(dateString,label);

		if(!stobj.getStatus())return stobj;
		stobj.setStatus(false);
		stobj.setErrorMsg(label +" "+I18n.getString("la.is.before.current.date"));
		if(validateWithCurrent ){
			StatusObj stobjcurrent= isValidDate1(getCurrentDateString1(),"current");
			Date querydate=(Date)stobj.getData();
			Date currdate=(Date)stobjcurrent.getData();
			if( querydate.equals(currdate) || querydate.after(currdate) ){
				stobj.setStatus(true);
			}
		}
		return stobj;
	}






	public static StatusObj isValidDate(int month,int dayofmonth,int year,String label,boolean validateWithCurrent){
		//month index for jan is 1
		return isValidDate(""+month,dayofmonth+"",year+"",label,validateWithCurrent);
	}



	public static String getCurrentDateString(){
		//mm-dd-yyyy
		return SDF.format(new Date());
	}

	public static String getCurrentDateString1(){
		//mm/dd/yy
		return SDF1.format(new Date());
	}

	public static String getDateString(Date date){
		return SDF.format(date);
	}

	public static String getDateString1(Date date){
		return SDF1.format(date);
	}

  	public static boolean checkValidZip(String zip){
		zip=zip.trim();
		String a[]={"1","2","3","4","5","6","7","8","9","0","-"};
		Vector v=new Vector();
		for(int i=0;i<a.length;i++){
			v.add(a[i]);
		}
		for(int i=0;i<zip.length();i++){
			String c=zip.charAt(i)+"";
			if(!v.contains(c)){
				return false;
			}
          	}
		return true;
       }

       public static boolean checkPhoneValidity(String phone){
		   if(phone.length()>10) return false;
	   		String a[]={"0","1","2","3","4","5","6","7","8","9"};
	   		ArrayList A=new ArrayList();
	   		for(int i=0;i<a.length;i++){
	   			A.add(a[i]);
	   		}
	   		for(int i=0;i<phone.length();i++){
	   			String c=phone.charAt(i)+"";
	   			if(!A.contains(c)){
	   				return false;
	   			}
	             	}
	   		return true;
       }

  public static boolean checkCodeValidity(String code){
		String a[]={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};
		Vector v=new Vector();
		for(int i=0;i<a.length;i++){
			v.add(a[i]);
		}
		for(int i=0;i<code.length();i++){
			String c=code.charAt(i)+"";
			if(!v.contains(c)){
				return false;
			}
          	}
		return true;
       }

   public static boolean isCodeAlreadyExists(String code,String query){
	java.sql.Connection con=null;
	boolean exists=false;
	java.sql.PreparedStatement pstmt=null;
	try{
		/*
		List al=EventbeeStrings.getDefaultCodesAsList();
		if(al.contains(code.toLowerCase()))
			exists=true;
		else{
			con=EventbeeConnection.getConnection();
			pstmt=con.prepareStatement(query);
			pstmt.setString(1,code);
			java.sql.ResultSet rs=pstmt.executeQuery();
			if(rs.next()){
				exists=true;
			}
			rs.close();
			pstmt.close();
			pstmt=null;
			con.close();
			con=null;
		}*/
	}catch(Exception e){
		System.out.println("Error in EventBeeValidations isCodeAlreadyExists():"+e.getMessage());
	}
	finally{
		try{
			if (pstmt!=null) pstmt.close();
			if(con!=null) con.close();
		}catch(Exception e){}
	}
	return exists;
   }
    public static HashMap getLocation(String location)
	{
		String GET_LOCATION="select lid,cid from locations where lower(location)=?";
		HashMap hm=new HashMap();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(GET_LOCATION,new String[]{location});
		if(statobj.getStatus()){
			for(int i=0;i<statobj.getCount();i++){
				hm.put("lid",dbmanager.getValue(i,"lid",""));
				hm.put("cid",dbmanager.getValue(i,"cid",""));
			}
		}
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"EventBeeValidations.java","in getLocation()","**location map is** :"+hm,null);

		return hm;
	}

	public static HashMap getFeatureUrl(String feature)
	{
		String GET_URL="select url from feature where lower(feature)=?";
		HashMap hm=new HashMap();
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(GET_URL,new String[]{feature});
		if(statobj.getStatus()){
			for(int i=0;i<statobj.getCount();i++){
				hm.put("url",dbmanager.getValue(i,"url",""));
			}
		}else{
			return null;
		}
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"EventBeeValidations.java","in getFeatureUrl()","**feature map is** :"+hm,null);
		return hm;
	}

	public static boolean isKeywordAlreadyExists(String code)
	{
		String GET_KEYWORD="select keyword from reservedwords where lower(keyword)=?";
		boolean exists=false;
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(GET_KEYWORD,new String[]{code});
		if(statobj.getStatus()&&statobj.getCount()>0){
			exists=true;
		}
		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"EventBeeValidations.java","in getFeatureUrl()","**exists  is** :"+exists,null);
		return exists;
	}

   public static boolean validateCode(String code){
		boolean exists=false;

		exists=isKeywordAlreadyExists(code);
/*
		if(!exists){
			HashMap hm=getLocation(code.toLowerCase());
			if(hm!=null&&!hm.isEmpty())
				exists=true;
		}
*/
		if(!exists){
			HashMap hm=getFeatureUrl(code.toLowerCase());
			if(hm!=null&&!hm.isEmpty())
				exists=true;
		}

		if(!exists){
			exists=isCodeAlreadyExists(code,"select clubid from clubinfo where lower(clublogo)=?");
		}

		

		EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.DEBUG,"EventBeeValidations.java","in validateCode()","**exists val is** :"+exists,null);
		return exists;

	}

	public static HashMap getRedirectUrl(String username,String type)
	{

		String query="select url,outofcontext from hardcoded_urls where shorturl=? and type=?";
		DBManager dbmanager=new DBManager();
		StatusObj statobj1=dbmanager.executeSelectQuery(query,new String[]{username,type});
		HashMap hm=new HashMap();
		if(statobj1.getStatus())
		{
			for(int i=0;i<statobj1.getCount();i++)
			{
				hm.put("url",dbmanager.getValue(i,"url",""));
				hm.put("outofcontext",dbmanager.getValue(i,"outofcontext",""));
			}
		}
		return hm;

	}

    public static String isValidBeeIDCustomUrl(String newidurlname){
    	String beeidexists=DbUtil.getVal("select 'yes' from authentication where lower(login_name)=?", new String[]{newidurlname.toLowerCase()});
    	if("yes".equals(beeidexists)) return "alreadyexists";
    	else{
    	 String urlexists=DbUtil.getVal("select 'yes' from hardcoded_urls where lower(shorturl)=?", new String[]{newidurlname.toLowerCase()});	
    	 if("yes".equals(urlexists)) return "alreadyexists";
    	 else{
    		String reservedkey=DbUtil.getVal("select 'yes' from reservedwords where lower(keyword)=?", new String[]{newidurlname.toLowerCase()});
    		if("yes".equalsIgnoreCase(reservedkey)) return "alreadyexists";
    		else return "notexists";
    	 }
    		 
    	}
    	 
    } 

	public static void main(String args[]){
		/*StatusObj obj=EventBeeValidations.isValidStr(args[0],"myemail");
		if(!obj.getStatus()){
			System.out.println(obj.getErrorMsg());
		}*/
		StatusObj statobj=EventBeeValidations.isValidDate( "08", "16", "2003","test",true);
		if(!statobj.getStatus()){
			System.out.println(statobj.getErrorMsg());
		}else{
			Date df=(Date)statobj.getData();
			System.out.println( df.toString());
		}


	}

}
