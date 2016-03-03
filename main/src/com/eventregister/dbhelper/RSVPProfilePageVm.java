package com.eventregister.dbhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.velocity.VelocityContext;

import com.eventregister.customquestions.beans.CustomAttribute;

public class RSVPProfilePageVm {
	
	
	
	
	HashMap getfieldMap(String field){
		HashMap map=new HashMap();
		map.put("qId",field);
		return map;
	}

	Vector getAttendeeObject(){
		Vector v=new Vector();
		ArrayList list=new ArrayList();
		list.add("fname");
		list.add("lname");
		list.add("email");
		list.add("phone");
		for(int i=0;i<list.size();i++){
			HashMap hm=getfieldMap((String)list.get(i));
			v.add(hm);
		}
		return v;
	}
	
	
	public Vector gettransactionprofile(String eventid,CustomAttribute[] attributeSet){
		Vector questions=new Vector();
		ArrayList ticketspecificAttributeIds=null;
		Vector p=getAttendeeObject();
		questions.addAll(p);
		RegistrationRSVPManager regRSVpMgr= new RegistrationRSVPManager();
	if(attributeSet!=null&&attributeSet.length>0){
		ticketspecificAttributeIds=regRSVpMgr.getQuestionsFortransactionlevel(eventid);
		if(ticketspecificAttributeIds!=null)
		{
			if(attributeSet!=null&&attributeSet.length>0){
				
				for(int k=0;k<attributeSet.length;k++){
				boolean noattribs=false;
				ArrayList al=null;
				HashMap customMap=new HashMap();
				CustomAttribute cb=(CustomAttribute)attributeSet[k];
				customMap.put("qType",cb.getAttributeType());
				customMap.put("qId",cb.getAttribId());
				System.out.println("ticketspecificAttributeIds--"+ticketspecificAttributeIds);
				if((ticketspecificAttributeIds!=null&&ticketspecificAttributeIds.contains(cb.getAttribId()))){
				questions.add(customMap);
	    	  }
			}
			}
		  }
		}
		return questions;
	}


	public Vector getattendprofile(String eventid,String selectedOption,CustomAttribute[] attributeSet, VelocityContext context,HashMap profilePageLabels){
		Vector questions=new Vector();
		ArrayList ticketspecificAttributeIds=null;
		String option="101";
		if("notsure".equals(selectedOption)){
			option="102";
		}
		Vector p=getAttendeeObject();
		questions.addAll(p);
		RegistrationRSVPManager regRSVpMgr= new RegistrationRSVPManager();
	if(attributeSet!=null&&attributeSet.length>0){
		ticketspecificAttributeIds=regRSVpMgr.getQuestionsFortheSelectedOption(option,eventid);
		if(ticketspecificAttributeIds!=null)
		{
			if(attributeSet!=null&&attributeSet.length>0){
				
				for(int k=0;k<attributeSet.length;k++){
					
					boolean noattribs=false;
					ArrayList al=null;
					HashMap customMap=new HashMap();
					CustomAttribute cb=(CustomAttribute)attributeSet[k];
					customMap.put("qType",cb.getAttributeType());
					customMap.put("qId",cb.getAttribId());
					System.out.println("ticketspecificAttributeIds--"+ticketspecificAttributeIds);
					if((ticketspecificAttributeIds!=null&&ticketspecificAttributeIds.contains(cb.getAttribId()))){
						questions.add(customMap);
	    			}
				}
			}
		 }
	}
		
	return questions;
	}

	
}
