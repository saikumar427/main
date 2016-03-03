package com.eventregister.dbhelper;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VelocityHelper {
	
	public static String getVelocityOutPut(VelocityContext vc,String Template,String templatetype){
		StringBuffer str=new StringBuffer();
		java.io.StringWriter out1=new java.io.StringWriter();
		VelocityEngine ve= new VelocityEngine();
		try{
		ve.init();
		boolean abletopares=ve.evaluate(vc,out1,templatetype,Template );
		str=out1.getBuffer();
		}
		catch(Exception e){

		System.out.println(e.getMessage());

		}

		return str.toString();

		}

}
