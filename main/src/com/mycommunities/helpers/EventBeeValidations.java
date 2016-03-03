package com.mycommunities.helpers;


import java.text.*;
import java.util.*;
import com.eventbee.general.StatusObj;


public class  EventBeeValidations{

	
	 public static final SimpleDateFormat SDF1 = new SimpleDateFormat("yyyy-MM-dd");

	 public static StatusObj isValidDate1(String dateString,String label){
		 //checks the dates which are in yyyy-MM-dd format;
		 
         
		 StatusObj stob=new StatusObj(false,null,dateString);
			stob=isValidStr(dateString,label);
			
			if(!stob.getStatus())
				return stob;
			stob.setStatus(false);
			stob.setErrorMsg(label+" is  not a valid date");
			try{
				SDF1.setLenient(false);
				Date date=(Date)SDF1.parse(dateString.trim());
				stob.setData(date);
				stob.setStatus(true);
			}catch(Exception ex){
				//System.out.println(dateString+"exception= "+ex.getMessage());
			}
			return stob;
		}
      
	 public static StatusObj isValidDate(String payDueDate,String label){
		 //checks the dates which are in yyyy-MM-dd format;
		 
         
		 StatusObj stob=new StatusObj(false,null,payDueDate);
			stob=isValidString(payDueDate,label);
			
			if(!stob.getStatus())
				return stob;
			stob.setStatus(false);
			stob.setErrorMsg(label+" is  not a valid date");
			try{
				SDF1.setLenient(false);
				Date date=SDF1.parse(payDueDate.trim());
				stob.setData(date);
				stob.setStatus(true);
			}catch(Exception ex){
				//System.out.println(dateString+"exception= "+ex.getMessage());
			}
			return stob;
		}
      
	 
	 public static StatusObj isValidString(String str,String label){
			return isValidString( str,label,0,0);
		}
	 
		public static StatusObj isValidString(String str,String label,int maxlength,int minlength){
			StatusObj stob=new StatusObj(false,null,str);
		
			if(str==null||"".equals(str.trim())){
				
				return stob;
			}
			 if(maxlength>0){
			    int temp=str.length();
				 System.out.println("in maxlength"+temp);
				if(!(temp<=maxlength)){
					stob.setErrorMsg("Max size permitted for "+label+ " is "+maxlength);
					return stob;
				}
			}
			 if(minlength>0){
				int temp=str.length();
				System.out.println("in minlength"+temp);
				if(!(temp>=minlength)){
				stob.setErrorMsg("Minimum size for "+label+ " is "+minlength);
					return stob;
				}
			}

			stob.setStatus(true);
			return stob;
		}


	 
	 
	 
	 
	 public static StatusObj isValidStr(String str,String label){
			return isValidStr( str,label,0,0);
		}

		public static StatusObj isValidStr(String str,String label,int maxlength,int minlength){
			StatusObj stob=new StatusObj(false,null,str);
			if(str==null||"".equals(str.trim())){
				stob.setErrorMsg(label+" is empty");
				return stob;
			}
			 if(maxlength>0){
				int temp=str.length();
				System.out.println("in maxlength"+temp);
				if(!(temp<=maxlength)){
					stob.setErrorMsg("Max size permitted for "+label+ " is "+maxlength);
					return stob;
				}
			}
			 if(minlength>0){
				int temp=str.length();
				System.out.println("in minlength"+temp);
				if(!(temp>=minlength)){
				stob.setErrorMsg("Minimum size for "+label+ " is "+minlength);
					return stob;
				}
			}

			stob.setStatus(true);
			return stob;
		}
}
