package com.myemailmarketing.helpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EmailObj;
import com.eventbee.general.EmailTemplate;
import com.eventbee.general.EventbeeMail;
import com.eventbee.general.GenUtil;
import com.eventbee.general.SendMailStatus;
import com.eventbee.general.TemplateConverter;
import com.myemailmarketing.beans.CampaignDetails;
import com.myemailmarketing.beans.EmailScheduleData;
import com.myemailmarketing.dbhelpers.CampaignDesignDB;

public class CampaignHelper {    

	public static CampaignDetails processCampaignData(CampaignDetails campData){
		String bgType=campData.getBackgroundType();
		System.out.println("bgtype"+bgType);
		if("COLOR".equalsIgnoreCase(bgType)){
			String background=campData.getBackgroundColor();
			if(background==null)
			background="#FFFF";
			campData.setBackground(background);
		}
		else if("IMAGE".equalsIgnoreCase(bgType)){
			String background=campData.getBackgroungImage();
			background="url("+background+")";
			campData.setBackground(background);
		}
		return campData;
	}

	@SuppressWarnings("unchecked")
	public static String sendTestMail(String userId,String campId,EmailScheduleData schData){
		try {
			CampaignDetails campDetails = CampaignDesignDB.getCampaignData(userId, campId);
			String Htmlcont=campDetails.getCampContent();
			String background=campDetails.getBackground();
			String border=campDetails.getBordercolor();
			String borderwidth=campDetails.getBorderwidth();
			String server_address1="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
			String imgpattern=server_address1+"/home/images/poweredby.jpg";
			String imagetest=server_address1+"/campmailsopen?eid=#*EID*#";	
			String logodisp=schData.getDisplayEbeeLogo();
            if("No".equalsIgnoreCase(logodisp))
            imgpattern=server_address1+"/home/images/spacer.gif";
            String replyTo=schData.getCampReplyTo();
            String fulladdress="";
            ArrayList<String> addressList=CampaignDesignDB.getUserTotalInfo(userId);
    		if(addressList!=null) fulladdress=GenUtil.getCSVData((String [])addressList.toArray(new String[addressList.size()]));
            HashMap tobeReplaced=new HashMap();
           	tobeReplaced.put("#*server_address1*#", server_address1);
    		tobeReplaced.put("#*imgpattern*#", imgpattern);
    		tobeReplaced.put("#*fulladdress*#", fulladdress);
    		tobeReplaced.put("#*imagetest*#", imagetest);
    		tobeReplaced.put("#*replyTo*#", replyTo);
    		//EmailTemplate emailTemplate=new EmailTemplate("13579","CAMPAIGNFOOTER");
    		//String footerContent=TemplateConverter.getMessage(tobeReplaced,emailTemplate.getHtmlFormat());
    		String textunsuburl="If you wish to remove yourself from this mailing list, please unsubscribe by ";
    		String messagefrommgr="This email was sent by "+fulladdress;
    		
    	    String footerContent="<div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' color='#666666' size='-3'>"+messagefrommgr+"</font></div>"
    		                    +"<br/><div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' color='#666666' size='-3'>"+textunsuburl+"</font></div>"
    		                    +"<div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' color='#666666' size='-3'>"
			                    +"<a href='"+server_address1+"/main/user/emailcamp!getDecodeEmail'>clicking here</a> or send mail to <a href='mailto:"+replyTo+"'>"+replyTo+"</a></font></div>"
    		                    +"<br/><div align='center'><img src='"+server_address1+"'/home/images/poweredby.jpg' border='0'/></div>";
    		
    		String Htmlmailcont="This is your Email preview. Please check your Email preview for any errors before blasting your email campaign to real recipients."
				+"<br/><br/>Note:<br/>1. Eventbee reserved words such as #*FIRSTNAME*# etc. are repalaced with real data in Real email"
				+"<br/>2. Unsubscribe link is enabled in Real email<br/><br/>"
				+"---------------------------Real email content starts after this line------------------------<br/><br/>"
				+Htmlcont+"<br/>"+footerContent;

			
	 		EmailObj emailobj=null;
	 		emailobj=new EmailObj();		
	 		String conttype=campDetails.getCampDescType();
			emailobj.setTo(schData.getMailtotest().trim());
			String fromemail=EbeeConstantsF.get("MESSAGES_FROM_EMAIL", "messages@eventbee.com");
			emailobj.setFrom(fromemail);
			emailobj.setSubject(schData.getCampSubject()+" (Test mail)");
			emailobj.setSendMailStatus(new SendMailStatus("13579","EmailMarketing_Test_Mail","", "1"));
			if("wysiwyg".equalsIgnoreCase(conttype)){
				String finalhtmlcont="This is your Email preview. Please check your Email preview for any errors before blasting your email campaign to real recipients."
					+"<br/><br/>Note:<br/>1. Eventbee reserved words such as #*FIRSTNAME*# etc. are repalaced with real data in Real email"
					+"<br/>2. Unsubscribe link is enabled in Real email<br/><br/>"
					+"---------------------------Real email content starts after this line------------------------<br/><br/>"
					+"<DIV STYLE='background:"+background+";border: "+border+";border-width:"+borderwidth+" ; border-style: solid;'>"+Htmlcont+"</DIV>"
					+footerContent;
				emailobj.setHtmlMessage(finalhtmlcont);
				emailobj.setTextMessage(finalhtmlcont);
			}
			else{
				emailobj.setHtmlMessage(Htmlmailcont);
				emailobj.setTextMessage(Htmlmailcont);
			}
			emailobj.setReplyTo(replyTo);
			EventbeeMail.sendHtmlMailPlain(emailobj);
			return "Success";
		} catch (Exception e) {
			System.out.println("There is an exception in sending test email in Email Blast"+e.getMessage());
		}
		return "";
	}
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static EmailScheduleData processEmailBlastData(String userId,String campId,EmailScheduleData schData){
		try{
		CampaignDetails campDetails=CampaignDesignDB.getCampaignData(userId,campId);
		String htmlmessage=campDetails.getCampContent();
		String server_address1="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
		String imgpattern=server_address1+"/home/images/poweredby.jpg";
		if("No".equalsIgnoreCase(schData.getDisplayEbeeLogo()))
			imgpattern=server_address1+"/home/images/spacer.gif";
		String imagetest=server_address1+"/campmailsopen?eid=#*EID*#";	
		String fulladdress="";
		String replyTo=schData.getCampReplyTo();
		ArrayList<String> addressList=CampaignDesignDB.getUserTotalInfo(userId);
		if(addressList!=null) fulladdress=GenUtil.getCSVData((String [])addressList.toArray(new String[addressList.size()]));
		HashMap tobeReplaced=new HashMap();
		tobeReplaced.put("#*server_address1*#", server_address1);
		tobeReplaced.put("#*imgpattern*#", imgpattern);
		tobeReplaced.put("#*fulladdress*#", fulladdress);
		tobeReplaced.put("#*imagetest*#", imagetest);
		tobeReplaced.put("#*replyTo*#", replyTo);
		EmailTemplate emailTemplate=new EmailTemplate("13579","CAMPAIGNFOOTER");
		/*String footerContent="";
		try{
		footerContent=TemplateConverter.getMessage(tobeReplaced,emailTemplate.getHtmlFormat());
		}catch(Exception e){
			System.out.println("Exception occured in getting message from TemplateConverter:"+e.getMessage());
		}
		htmlmessage=htmlmessage+footerContent;*/
		String campid=CampaignDesignDB.getNewCampId();
		schData.setCampId(campid);
		schData.setCampStatus("P");
		String sel=schData.getSchtimeType();
		if(sel.equals("now")){
			java.sql.Timestamp tsp=new java.sql.Timestamp(Calendar.getInstance().get(Calendar.YEAR)-1900,Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH),Calendar.getInstance().get(Calendar.HOUR_OF_DAY),0,0,0);
			schData.setCampScheduledDate(tsp.toString());
			schData.setCampTimeZone(tsp.toString());
		}else if(sel.equals("date")){
			String day= schData.getDay();
			String month= schData.getMonth();
			String year= schData.getYear();
			String time=schData.getTime();
			java.sql.Timestamp tsp=new java.sql.Timestamp(Integer.parseInt(year)-1900,Integer.parseInt(month)-1,Integer.parseInt(day),Integer.parseInt(time),0,0,0);
			schData.setCampScheduledDate(tsp.toString());
			schData.setCampTimeZone(tsp.toString());
		}else{
			System.out.println("enter into schedule later case"); 
			schData.setCampScheduledDate(null);
			schData.setCampStatus("T");
			//schData.setCampTimeZone((new Date()).toString());
			java.sql.Timestamp tsp=new java.sql.Timestamp(Calendar.getInstance().get(Calendar.YEAR)-1900,Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH),Calendar.getInstance().get(Calendar.HOUR_OF_DAY),0,0,0);
			schData.setCampTimeZone(tsp.toString());
		}
		schData.setCampHtml(htmlmessage);
		}//End of try
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("There is an exception in processing Email Blast Data"+e.getMessage());
		}
		return schData;
	}
	public static String getPreviewData(CampaignDetails campaignData,String fckdesc){
		String htmlContent="";
		String contentType=campaignData.getCampDescType();
		if ("wysiwyg".equals(contentType)){
			htmlContent=campaignData.getDescription();
		}
		String priviewContent="";
		if("".equals(htmlContent)){
			htmlContent=campaignData.getTextcontent();
		}
		if("wysiwyg".equalsIgnoreCase(campaignData.getCampDescType())){
				String background=campaignData.getBackgroundColor(); 	
				String bordercolor=campaignData.getBordercolor();
				if(bordercolor==null)bordercolor="#FFFFFF";
				String borderwidth=campaignData.getBorderwidth();
				if(borderwidth==null)borderwidth="0px";
				priviewContent="<DIV STYLE='position: relative; top: 0px; left: -4 px;width: 563px; height: 461px; background:"+background+";border: "+bordercolor+";border-width:"+borderwidth+" ; border-style: solid; padding: 10px;'>";
				priviewContent+=htmlContent;
		 }
		 else
		 {
			 priviewContent="<DIV STYLE='width: 563px; height: 461px;'>";
				priviewContent+=htmlContent;
		 }
		return priviewContent;
	}
	
}
