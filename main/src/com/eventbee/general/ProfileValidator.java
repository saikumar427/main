package com.eventbee.general;

import com.eventbee.general.*;

import com.eventbee.general.EbeeConstantsF;
import java.sql.*;
import java.util.*;

public class ProfileValidator{


   public boolean checkNameValidity(String username){
  	  return checkNameValidity(username,false);
    }

    public boolean checkNameValidity(String username,boolean flag){

		String a[]={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
		"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
		"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

		String b[]={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9","-","_",
			"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
			"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};


		Vector v=new Vector();

		if(flag){
			for(int i=0;i<b.length;i++){
				v.add(b[i]);
			}
	     }

	    else{
				for(int i=0;i<a.length;i++){
							v.add(a[i]);
				}
			}

		for(int i=0;i<username.length();i++){
			String c=username.charAt(i)+"";
			if(!v.contains(c)){
				return false;
			}
          	}
		return true;
       }

 
}

