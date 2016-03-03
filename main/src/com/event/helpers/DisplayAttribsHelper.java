package com.event.helpers;

import java.util.HashMap;

import com.eventbee.general.EbeeConstantsF;

public class DisplayAttribsHelper {
	
public static void processLookandFeelData(HashMap<String,String> attribsMap){
	if("COLOR".equalsIgnoreCase(attribsMap.get("BG_TYPE"))){
		attribsMap.put("BG_IMAGE","");
	}
	else{
		attribsMap.put("BG_COLOR","");
	}
	if("COLOR".equalsIgnoreCase(attribsMap.get("BOXBG_TYPE"))){
		attribsMap.put("BOXBG_IMAGE","");
	}
	else{
		attribsMap.put("BOXBG_COLOR","");
	}
	if("COLOR".equalsIgnoreCase(attribsMap.get("BOXHEADER_TYPE"))){
		attribsMap.put("BOXHEADER_IMAGE","");
	}
	else{
		attribsMap.put("BOXHEADER_COLOR","");
	}
	if("COLOR".equalsIgnoreCase(attribsMap.get("CONTAINERBG_TYPE"))){
		attribsMap.put("CONTAINERBG_IMAGE","");
	}
	else{
		attribsMap.put("CONTAINERBG_COLOR","");
	}
	if("HTML".equalsIgnoreCase(attribsMap.get("HEADER_TYPE"))){
		attribsMap.put("HEADER_IMAGE","");
	}
	else{
		attribsMap.put("HEADER_HTML","");
	}
	if("HTML".equalsIgnoreCase(attribsMap.get("FOOTER_TYPE"))){
		attribsMap.put("FOOTER_IMAGE","");
	}
	else{
		attribsMap.put("FOOTER_HTML","");
	}
}
public static HashMap<String,String> processLandFDataForCSS(HashMap<String,String> attribMap){
	HashMap<String,String> hm=new HashMap<String,String>();
	String background=attribMap.get("BG_TYPE");
	if("COLOR".equals(background))
		hm.put("#**BACKGROUND**#",attribMap.get("BG_COLOR"));
	else{
		String bgimg=attribMap.get("BG_IMAGE");
		hm.put("#**BACKGROUND**#","url("+bgimg+")");
		}
	
	if("COLOR".equals(attribMap.get("BOXBG_TYPE")))
		
		hm.put("#**BOXBACKGROUND**#",attribMap.get("BOXBG_COLOR"));
	else{
		String bgimg=attribMap.get("BOXBG_IMAGE");
		hm.put("#**BOXBACKGROUND**#","url("+bgimg+")");
		}
	if("COLOR".equals(attribMap.get("BOXHEADER_TYPE")))
		hm.put("#**BOXHEADERBACKGROUND**#",attribMap.get("BOXHEADER_COLOR"));
	else{
		String bgimg=attribMap.get("BOXHEADER_IMAGE");
		hm.put("#**BOXHEADERBACKGROUND**#","url("+bgimg+")");
		}
	if("COLOR".equals(attribMap.get("CONTAINERBG_TYPE")))
		hm.put("#**CONTAINERBACKGROUND**#",attribMap.get("CONTAINERBG_COLOR"));
	else{
		String bgimg=attribMap.get("CONTAINERBG_IMAGE");
		hm.put("#**CONTAINERBACKGROUND**#","url("+bgimg+")");
		}
	
	hm.put("#**BODY_FONT_SIZE**#",attribMap.get("BODYTEXTFONTSIZE"));
	hm.put("#**BODY_FONT_TYPE**#",attribMap.get("BODYTEXTFONTTYPE"));
	hm.put("#**BODY_TEXT_COLOR**#",attribMap.get("BODYTEXTCOLOR"));
	
	hm.put("#**BIGGER_FONT_SIZE**#",attribMap.get("LARGETEXTFONTSIZE"));
	hm.put("#**BIGGER_FONT_TYPE**#",attribMap.get("LARGETEXTFONTTYPE"));
	hm.put("#**BIGGER_TEXT_COLOR**#",attribMap.get("LARGETEXTCOLOR"));

	hm.put("#**MEDIUM_FONT_SIZE**#",attribMap.get("MEDIUMTEXTFONTSIZE"));
	hm.put("#**MEDIUM_FONT_TYPE**#",attribMap.get("MEDIUMTEXTFONTYPE"));
	hm.put("#**MEDIUM_TEXT_COLOR**#",attribMap.get("MEDIUMTEXTCOLOR"));

	hm.put("#**SMALL_FONT_SIZE**#",attribMap.get("SMALLTEXTFONTSIZE"));
	hm.put("#**SMALL_FONT_TYPE**#",attribMap.get("SMALLTEXTFONTTYPE"));
	hm.put("#**SMALL_TEXT_COLOR**#",attribMap.get("SMALLTEXTCOLOR"));
	
	hm.put("#**LINK_FONT_SIZE**#",attribMap.get("LINKTEXTFONTSIZE"));
	hm.put("#**LINK_FONT_TYPE**#",attribMap.get("LINKTEXTFONTTYPE"));
	hm.put("#**LINK_TEXT_COLOR**#",attribMap.get("LINKTEXTCOLOR"));

	return hm;
	
}
public static HashMap<String,String> processHandFooterData(HashMap<String,String> attribMap){
	HashMap<String,String> hm=new HashMap<String,String>();
	if("IMAGE".equalsIgnoreCase(attribMap.get("HEADER_TYPE"))){
		String headerImageHTML="<img src=\""+attribMap.get("HEADER_IMAGE")+"\" />";
		hm.put("HEADER_HTML",headerImageHTML);
	}
	else if("HTML".equalsIgnoreCase(attribMap.get("HEADER_TYPE"))){
		hm.put("HEADER_HTML",attribMap.get("HEADER_HTML"));
	}else{
		hm.put("HEADER_HTML","Default");
	}
	if("IMAGE".equalsIgnoreCase(attribMap.get("FOOTER_TYPE"))){
		String footerImageHTML="<img src=\""+attribMap.get("FOOTER_IMAGE")+"\" />";
		hm.put("FOOTER_HTML",footerImageHTML);
	}
	else if("HTML".equalsIgnoreCase(attribMap.get("FOOTER_TYPE"))){
		hm.put("FOOTER_HTML",attribMap.get("FOOTER_HTML"));
	}else{
		hm.put("FOOTER_HTML","Default");
	}

	return hm;
}
private static String urlencodeString(String data){
	try{
	//return java.net.URLEncoder.encode(data,"UTF-8");
	}catch(Exception ex){
		
	}
	return data;
}
@SuppressWarnings("deprecation")
public static String getStreamerCodeString(String mgrId,HashMap<String,String> streamerData){

	
	try{
	StringBuffer sData=new StringBuffer();
	String title=streamerData.get("TITLE");
	//String streamevents=streamerData.get("STREAMEVENTS");
	String location=streamerData.get("LOCATION");
	String category=streamerData.get("CATEGORY");
	String no_of_items=streamerData.get("NO_OF_ITEMS");
	String streamsize=streamerData.get("STREAMERSIZE");
	String bgtype=streamerData.get("BG_TYPE");
	if(bgtype==null) bgtype="COLOR";
	String bgcolor=streamerData.get("BACKGROUND_COLOR");
	String bgimage=streamerData.get("BACKGROUND_IMAGE");
	if(bgcolor==null) bgcolor="#FFFFFF";
	
	String bigtextcolor=streamerData.get("BIGGER_TEXT_COLOR");
	
	String bigfonttype=streamerData.get("BIGGER_FONT_TYPE");
	String bigfontsize=streamerData.get("BIGGER_FONT_SIZE");
	String medtextcolor=streamerData.get("MEDIUM_TEXT_COLOR");
	String medfonttype=streamerData.get("MEDIUM_FONT_TYPE");
	String medfontsize=streamerData.get("MEDIUM_FONT_SIZE");
	String smalltextcolor=streamerData.get("SMALL_TEXT_COLOR");
	String smallfonttype=streamerData.get("SMALL_FONT_TYPE");
	String smallfontsize=streamerData.get("SMALL_FONT_SIZE");
	String bordercolor=streamerData.get("BORDERCOLOR");
	String linkcolor=streamerData.get("LINKCOLOR");
	String displaypowerlink=streamerData.get("DISPLAYEBEELINK");

	String str="";
	
	str=str+"refid="+mgrId;
	/*str=str+"&retrievecount="+no_of_items;
	str=str+"&streamsize="+streamsize;
	if(location!=null&&!"".equals(location))
	str=str+"&location="+location;
	if(category!=null&&!"".equals(category))
	str=str+"&category="+category;
	if(title!=null&&!"".equals(title))
	str=str+"&title="+title;	
	if("IMAGE".equalsIgnoreCase(bgtype))
		str=str+"&background=url("+bgimage+")";
	else
		str=str+"&background="+urlencodeString(bgcolor);

	str=str+"&bordercolor="+urlencodeString(bordercolor);

	str=str+"&linkcolor="+urlencodeString(linkcolor);
	
	str=str+"&bigtextcolor="+urlencodeString(bigtextcolor);
	
	str=str+"&bigfonttype="+urlencodeString(bigfonttype);
	
	str=str+"&bigfontsize="+urlencodeString(bigfontsize);
	str=str+"&medtextcolor="+urlencodeString(medtextcolor);
	str=str+"&medfonttype="+urlencodeString(medfonttype);
	str=str+"&medfontsize="+urlencodeString(medfontsize);
	str=str+"&smalltextcolor="+urlencodeString(smalltextcolor);
	str=str+"&smallfonttype="+urlencodeString(smallfonttype);
	str=str+"&smallfontsize="+urlencodeString(smallfontsize);
	str=str+"&displaypowerlink="+displaypowerlink;
	*/
	String serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/";
	String resultString="<script type=\"text/javascript\" language=\"javascript\" src="+serveraddress+"portal/streaming/eventstreaming_js.jsp?"+str+"></script>";
	
	return resultString;
	}catch(Exception ex){
		System.out.println("Error in DisplayAttribsHelper processing straem attributes "+ex.getMessage());
		return "";
	}
}
}
