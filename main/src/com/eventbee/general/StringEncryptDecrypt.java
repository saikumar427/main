package com.eventbee.general;

import java.util.Arrays;
import java.util.List;

public class StringEncryptDecrypt {

	static String[] vals=new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",".","@","_","-","0","1","2","3","4","5",
			"6","7","8","9"};
	static String[] mappedVals=new String[]{ "Z","Y","0","X","U","1","A","2","K","M","W","S","B","3","C","N","a","O","b","4","z",
			"x","E","d","5","L","6","Q","9","7","D","R","G","I","c","F","J","H","V","m"};

	static	List valsList=Arrays.asList(vals);
    static	List mappedList=Arrays.asList(mappedVals);

	public static String getEncryptedString(String email){   
	   if(email==null) return "";  
		   String encryptedemail="";   
		   email=email.toLowerCase();
			 for(int i=0;i<email.length();i++){    
			       if(valsList.indexOf(String.valueOf(email.charAt(i)))>-1)
			        encryptedemail+=mappedVals[valsList.indexOf(String.valueOf(email.charAt(i)))];
			       else
			        encryptedemail+=String.valueOf(email.charAt(i));
			      }
			      return encryptedemail;
   }

	public static String getDecryptedString(String email){   
	  if(email==null) return "";  
		  String decryptedemail="";   
		  for(int i=0;i<email.length();i++){    
			 if(mappedList.indexOf(String.valueOf(email.charAt(i)))>-1)
			     decryptedemail+=vals[mappedList.indexOf(String.valueOf(email.charAt(i)))];
			 else
			     decryptedemail+=String.valueOf(email.charAt(i));
		   }
		  return decryptedemail;
  }
	
	
}
	

