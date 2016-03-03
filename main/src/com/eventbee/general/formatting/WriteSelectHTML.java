package com.eventbee.general.formatting;

       
import java.util.*;


public class WriteSelectHTML {
	
public static String getSelectHtml(String[] optionDisp, String[] optionVal, String nameofsel, 
		String defaultVal, String firstdisp, String firstval){
	return 	getSelectHtml(optionDisp, optionVal, nameofsel,defaultVal,firstdisp,firstval,"");
}	
	
public static String getSelectHtml(String[] optionDisp, String[] optionVal, String nameofsel, 
		String defaultVal, String firstdisp, String firstval,String attr){

//todo: validate input
/*
optionDisp, optionVal arrays not null and of same length
*/	

//issue: what to do if validate fail?

	StringBuffer sb=new StringBuffer();
	sb.append("<select name='"+nameofsel+"' "+attr+">");
        if(firstdisp!=null)
		sb.append("<option value='"+firstval+"'>"+firstdisp+"</option>");
	for(int i=0;i<optionDisp.length;i++){
	
	 if(optionVal[i].equals(defaultVal)){
	  sb.append("<option value='"+optionVal[i]+"' selected='selected'>"+optionDisp[i]+"</option>");
	 }else{
		  sb.append("<option value='"+optionVal[i]+"'>"+optionDisp[i]+"</option>");
	 }
	}
	
	sb.append("</select>");
	
	return sb.toString();

}

public static String getSelectHtml(HashMap optionSet, String nameofsel){
	String[] optionDisp=(String[])optionSet.get("OPTION_DISP_ARRAY"); 
	String[] optionVal=(String[])optionSet.get("OPTION_VAL_ARRAY"); 
	String defaultVal=(String)optionSet.get("OPTION_DEFAULT"); 
	String firstdisp=(String)optionSet.get("OPTION_FIRST_DISP"); 
	String firstval=(String)optionSet.get("OPTION_FIRST_VAL");
	return getSelectHtml(optionDisp, optionVal, nameofsel, 
		defaultVal, firstdisp, firstval);
}

public static String isRadioChecked(String value, String pValue){
      if(value.equals(pValue))
		return "checked='checked'";
	else
		return "";
 }

public static String getXfHeader(String[] vals){
        StringBuffer sb=new StringBuffer();
      	sb.append("<xf:tr>");
        for (int i=0; i<vals.length; i++){ 
                sb.append("<xf:td align='center'>"+ vals[i] +"</xf:td>");
       }
       sb.append("</xf:tr>");
       return sb.toString(); 

 }
 public static String getXfBoolean(String ref, String caption){
        StringBuffer sb=new StringBuffer();
	sb.append("<xf:table><xf:tr><xf:td><xf:selectBoolean ref='"+ref+"' selectUIType='checkbox'>");
        sb.append("</xf:selectBoolean></xf:td>");
	sb.append("<xf:td>"+caption+"</xf:td></xf:tr></xf:table>");
	return sb.toString();
   }

   
 public static String getXfSelectOneRadio(String ref, String[] values, String[] captions){
        StringBuffer sb=new StringBuffer();
	sb.append("<xf:selectOne ref='"+ref+"' selectUIType='radio'>");
	for(int i=0;i<values.length;i++){
        	sb.append("<xf:item>");
		if(captions!=null)
        		sb.append("<xf:caption>"+captions[i]+"</xf:caption>");
        	sb.append("<xf:value>"+values[i]+"</xf:value>");
        	sb.append("</xf:item>");
	}
        sb.append("</xf:selectOne>");   
	return sb.toString();
   }
  public static String getXfSelectMany(String ref, String[] values, String[] captions){
        StringBuffer sb=new StringBuffer();
	sb.append("<xf:selectMany selectUIType='checkbox' ref='"+ref+"'>");
	for(int i=0;i<values.length;i++){
        	sb.append("<xf:item>");
		if(captions!=null)
        		sb.append("<xf:caption>"+captions[i]+"</xf:caption>");
        	sb.append("<xf:value>"+values[i]+"</xf:value>");
        	sb.append("</xf:item>");
	}
        sb.append("</xf:selectMany>");   
	return sb.toString();
   }
  public static String getXfOutput(String ref, String caption){
        StringBuffer sb=new StringBuffer();
      	sb.append("<xf:output ref='"+ref+"'>");
        if(caption!=null)
		sb.append("<xf:caption>"+caption +"</xf:caption>");
    	sb.append("</xf:output>");
    	return sb.toString(); 

   }
   public static String getXfTextBox(String ref, String caption, String size){
	StringBuffer sb=new StringBuffer();
      	sb.append("<xf:textbox size='"+size+"' ref='"+ref+"'>");
	if(caption!=null)
		sb.append("<xf:caption>"+caption +"</xf:caption>");
    	sb.append("</xf:textbox>");
    	return sb.toString(); 
   }
   public static String getXfHidden(String ref, String val){
         StringBuffer sb=new StringBuffer();
         sb.append("<xf:hidden ref='"+ref+"'>");
         sb.append("<xf:value>"+val+"</xf:value>");
         sb.append("</xf:hidden>");
         return sb.toString();
   }
   public static String getXFButton(String id, String caption, String hint){
 	StringBuffer sb=new StringBuffer();
	sb.append("<xf:submit id='"+id+"' class='button'>");
        sb.append("<xf:caption>"+caption+"</xf:caption>");
        sb.append("<xf:hint>"+hint+"</xf:hint>");    
        sb.append("</xf:submit>");   
        return sb.toString();
   }

public static void main(String[] arg){
	System.out.println("todo");
}


  public static String getSelectedChoiceBoxs(String ref, String[] values, String[] captions,String[] dbvals){
	if(values==null) return "";
	if(captions==null) captions=values;
	if(dbvals==null) dbvals=new String[0];
	
        StringBuffer sb=new StringBuffer();
	List al=java.util.Arrays.asList(dbvals);
	String str="<input type='checkbox' name='"+ref+"'";
	for(int i=0;i<values.length;i++){
		if(al.contains(values[i]))
			sb.append(str+" value='"+values[i]+"' checked='true'>"+captions[i]+"</br>");	
		else
			sb.append(str+" value='"+values[i]+"' >"+captions[i]+"<br/>");	
	}
	   
	return sb.toString();
   }
   public static String getSelectOneRadio(String ref,String[] values,String[] captions,String defvalue){
	   return getSelectOneRadio(ref,values,captions,defvalue,"");
   }
   public static String getSelectOneRadio(String ref,String[] values,String[] captions,String defvalue,String attr){
	   return getSelectOneRadio(ref,values,captions,defvalue,attr,"");
   }
   public static String getSelectOneRadio(String ref,String[] values,String[] captions,String defvalue,String attr,String br){
	   StringBuffer sb=new StringBuffer();
	   for(int i=0;i<values.length;i++){
		   sb.append("<input type='radio' value='"+values[i]+"' name='"+ref+"' "+isRadioChecked(values[i],defvalue)+" "+attr+" /> "+captions[i]+" "+br);
	   }
	  return sb.toString();
   }

}

