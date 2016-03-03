package com.eventbee.general.formatting;

import java.util.*;
import com.eventbee.general.*;
public class WriteXFTags {

public static String DEFAULT_LABEL_TD_WIDTH="240";
public static String DEFAULT_VALUE_TD_WIDTH="240";
public static String DEFAULT_XMLNS_XF="http://apache.org/cocoon/xmlform/1.0";

public static String getDocumentTag(String title, String subtitle){
  StringBuffer sb=new StringBuffer("");
  sb.append("<document xmlns:xf='");
  sb.append(DEFAULT_XMLNS_XF);
  sb.append("'");
  if(title!=null)
	 sb.append(" title='"+GenUtil.getEncodedXML(title)+"'" );
  if(subtitle!=null) 
	sb.append(" sub-title='"+GenUtil.getEncodedXML(subtitle)+"'");
  sb.append(">");
  return sb.toString();
}

public static String getXFHintTD(String content){
  StringBuffer sb=new StringBuffer("");
  sb.append("<xf:td class='hint'>");
  sb.append(GenUtil.getEncodedXML(content));
  sb.append("</xf:td>");
  return sb.toString();
}

public static String getXFLabelTD(String width, String labelname, boolean isMandatory){
  StringBuffer sb=new StringBuffer("");
  sb.append("<xf:td height='30' width='");
  if(width==null) 
  	sb.append(DEFAULT_LABEL_TD_WIDTH);
  else
	sb.append(width);
  sb.append("' class='inputlabel'>");
  
  sb.append(labelname);
  if (isMandatory)
	sb.append(" *");
  sb.append("</xf:td>");
 return sb.toString();
}

public static String getXFValueTD(String width, String content, boolean isMandatory){
  StringBuffer sb=new StringBuffer("");
  sb.append("<xf:td height='30' width='");
  if(width==null) 
  	sb.append(DEFAULT_VALUE_TD_WIDTH);
  else
	sb.append(width);
  sb.append("' class='inputvalue'>");
  
  sb.append(content);
  sb.append("</xf:td>");
 return sb.toString();
}

public static String getXFHeaderTD(String colspan, String content){
  StringBuffer sb=new StringBuffer("");
  sb.append("<xf:td colspan='");
  sb.append(GenUtil.getEncodedXML(colspan));
  sb.append("' class='subheader'>");
  sb.append(GenUtil.getEncodedXML(content));
  sb.append("</xf:td>");
 return sb.toString();
}

public static String getXfHeader(String[] vals){
        StringBuffer sb=new StringBuffer();
      	sb.append("<xf:tr>");
        for (int i=0; i<vals.length; i++){ 
                sb.append("<xf:td class='subheader' align='center'>"+GenUtil.getEncodedXML( vals[i]) +"</xf:td>");
       }
       sb.append("</xf:tr>");
       return sb.toString(); 

 }

public static String getXfHeaderR(String[] vals){
        StringBuffer sb=new StringBuffer();
      	sb.append("<xf:tr>");
        for (int i=0; i<vals.length; i++){ 
                sb.append("<xf:td class='colheader'>"+ GenUtil.getEncodedXML(vals[i] )+"</xf:td>");
        }
       sb.append("</xf:tr>");
       return sb.toString(); 

 }

 public static String getXfBoolean(String ref, String caption){
        StringBuffer sb=new StringBuffer();
	sb.append("<xf:table><xf:tr><xf:td><xf:selectBoolean ref='"+GenUtil.getEncodedXML(ref)+"' selectUIType='checkbox'>");
        sb.append("</xf:selectBoolean></xf:td>");
	sb.append("<xf:td>"+GenUtil.getEncodedXML(caption)+"</xf:td></xf:tr></xf:table>");
	return sb.toString();
   }

 public static String getXfBoolean(String ref){
        StringBuffer sb=new StringBuffer();
	sb.append("<xf:table><xf:tr><xf:td><xf:selectBoolean ref='"+GenUtil.getEncodedXML(ref)+"' selectUIType='checkbox'>");
        sb.append("</xf:selectBoolean></xf:td>");
	sb.append("</xf:tr></xf:table>");
	return sb.toString();
   }
 
 public static String getXfSelectListBox(String ref, String[] values, String[] captions){  
        StringBuffer sb=new StringBuffer();
        sb.append("<xf:selectOne ref='"+GenUtil.getEncodedXML(ref)+"' selectUIType='listbox'>");
        for(int i=0;i<values.length;i++){
        	sb.append("<xf:item>");
		if(captions!=null)
        		sb.append("<xf:caption>"+GenUtil.getEncodedXML(captions[i])+"</xf:caption>");
        	sb.append("<xf:value>"+GenUtil.getEncodedXML(values[i])+"</xf:value>");
        	sb.append("</xf:item>");
	}
        sb.append("</xf:selectOne>");   
	return sb.toString();
   }

 public static String getXfSelectOneRadio(String ref, String[] values, String[] captions){
        StringBuffer sb=new StringBuffer();
	sb.append("<xf:selectOne ref='"+GenUtil.getEncodedXML(ref)+"' selectUIType='radio'>");
	for(int i=0;i<values.length;i++){
        	sb.append("<xf:item>");
		if(captions!=null)
        		sb.append("<xf:caption>"+GenUtil.getEncodedXML(captions[i])+"</xf:caption>");
        	sb.append("<xf:value>"+GenUtil.getEncodedXML(values[i])+"</xf:value>");
        	sb.append("</xf:item>");
	}
        sb.append("</xf:selectOne>");   
	return sb.toString();
   }
  
 public static String getXfSelectOneCombo(String ref, String[] values, String[] captions){
       StringBuffer sb=new StringBuffer();
	sb.append("<xf:selectOne ref='"+GenUtil.getEncodedXML(ref)+"' selectUIType='checkbox'>");
	for(int i=0;i<values.length;i++){
        	sb.append("<xf:item>");
		if(captions!=null)
        		sb.append("<xf:caption>"+GenUtil.getEncodedXML(captions[i])+"</xf:caption>");
        	sb.append("<xf:value>"+GenUtil.getEncodedXML(values[i])+"</xf:value>");
        	sb.append("</xf:item>");
	}
        sb.append("</xf:selectOne>");   
	return sb.toString();
   }
 public static String getXfSelectMany(String ref, String[] values, String[] captions){
        StringBuffer sb=new StringBuffer();
	sb.append("<xf:selectMany selectUIType='checkbox' ref='"+GenUtil.getEncodedXML(ref)+"'>");
	for(int i=0;i<values.length;i++){
        	sb.append("<xf:item>");
		if(captions!=null)
        		sb.append("<xf:caption>"+GenUtil.getEncodedXML(captions[i])+"</xf:caption>");
        	sb.append("<xf:value>"+GenUtil.getEncodedXML(values[i])+"</xf:value>");
        	sb.append("</xf:item>");
	}
        sb.append("</xf:selectMany>");   
	return sb.toString();
   }

 public static String getXfWriteOneRow(String[] values){
	StringBuffer sb=new StringBuffer();
	for(int i=0;i<values.length;i++){
        	sb.append("<xf:td class='subheader'>");
		sb.append(GenUtil.getEncodedXML(values[i]));
        	sb.append("</xf:td>");
	}

	return sb.toString();
  }

 public static String getXfWriteOneRowData(String[] values){
	StringBuffer sb=new StringBuffer();
	for(int i=0;i<values.length;i++){
        	sb.append("<xf:td class='data'>");
		sb.append(GenUtil.getEncodedXML(values[i]));
        	sb.append("</xf:td>");
	}

	return sb.toString();
  }

 public static String getXfOutput(String ref, String caption){
        StringBuffer sb=new StringBuffer();
      	sb.append("<xf:output ref='"+GenUtil.getEncodedXML(ref)+"'>");
        if(caption!=null)
		sb.append("<xf:caption>"+GenUtil.getEncodedXML(caption) +"</xf:caption>");
    	sb.append("</xf:output>");
    	return sb.toString(); 
  }

  public static String getXfOutput(String caption){
        StringBuffer sb=new StringBuffer();
        sb.append("<xf:table><xf:tr><xf:td>");
        if(caption!=null)
          	sb.append(GenUtil.getEncodedXML(caption));
        sb.append("</xf:td></xf:tr></xf:table>");
	return sb.toString(); 
   }

    public static String getXfTextArea(String ref, String caption, String rows, String cols){
	StringBuffer sb=new StringBuffer();
      	sb.append("<xf:textarea rows='"+GenUtil.getEncodedXML(rows)+"' cols='"+GenUtil.getEncodedXML(cols)+"' ref='"+GenUtil.getEncodedXML(ref)+"'>");
	if(caption!=null)
		sb.append("<xf:caption>"+GenUtil.getEncodedXML(caption) +"</xf:caption>");
    	sb.append("</xf:textarea>");
    	return sb.toString(); 
   }

   public static String getXfTextAreaRO(String ref, String caption, String rows, String cols){
	StringBuffer sb=new StringBuffer();
      	sb.append("<xf:textarea rows='"+GenUtil.getEncodedXML(rows)+"' cols='"+GenUtil.getEncodedXML(cols)+"' ref='"+GenUtil.getEncodedXML(ref)+"' readonly='true' >");
	if(caption!=null)
		sb.append("<xf:caption>"+GenUtil.getEncodedXML(caption) +"</xf:caption>");
    	sb.append("</xf:textarea>");
    	return sb.toString(); 
   }

   public static String getXfTextBox(String ref, String caption, String size){
	StringBuffer sb=new StringBuffer();
      	sb.append("<xf:textbox size='"+GenUtil.getEncodedXML(size)+"' ref='"+GenUtil.getEncodedXML(ref)+"'>");
	if(caption!=null)
		sb.append("<xf:caption>"+GenUtil.getEncodedXML(caption) +"</xf:caption>");
    	sb.append("</xf:textbox>");
    	return sb.toString(); 
   }
   public static String getXfTextBox(String ref, String caption, String size, String Attributes)
   {
	StringBuffer sb=new StringBuffer();
      	sb.append("<xf:textbox  size='"+size+"'  ref='"+GenUtil.getEncodedXML(ref)+"' ");
	if(Attributes!=null)
		sb.append(Attributes);
    	sb.append(" >");
	if(caption!=null)
		sb.append("<xf:caption>"+GenUtil.getEncodedXML(caption) +"</xf:caption>");
    	sb.append("</xf:textbox>");
    	return sb.toString(); 
   }

   public static String getXfPassWord(String ref, String caption, String size){
	StringBuffer sb=new StringBuffer();
      	sb.append("<xf:password size='"+GenUtil.getEncodedXML(size)+"' ref='"+GenUtil.getEncodedXML(ref)+"'>");
	if(caption!=null)
		sb.append("<xf:caption>"+GenUtil.getEncodedXML(caption) +"</xf:caption>");
    	sb.append("</xf:password>");
    	return sb.toString(); 
   }

   public static String getXfHidden(String ref, String val){
         StringBuffer sb=new StringBuffer();
         sb.append("<xf:hidden ref='"+GenUtil.getEncodedXML(ref)+"'>");
	 if(val!=null)
		 sb.append("<xf:value>"+GenUtil.getEncodedXML(val)+"</xf:value>");
         sb.append("</xf:hidden>");
         return sb.toString();
   }
   public static String getXFButton(String id, String caption, String hint){
 	StringBuffer sb=new StringBuffer();
	sb.append("<xf:submit id='"+GenUtil.getEncodedXML(id)+"' class='button'>");
        sb.append("<xf:caption>"+GenUtil.getEncodedXML(caption)+"</xf:caption>");
        sb.append("<xf:hint>"+GenUtil.getEncodedXML(hint)+"</xf:hint>");    
        sb.append("</xf:submit>");   
        return sb.toString();
   }
}

