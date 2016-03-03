package com.eventbee.actions;

import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.eventbee.general.DateUtil;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EncodeNum;
import com.eventbee.general.StringEncryptDecrypt;
import com.opensymphony.xwork2.ActionSupport;

public class EmailTrackAction extends ActionSupport{
    public String token="";
    public String cid="";
	public String tid="";
	public String purpose="";
	public String bid="";
	public String redirecturl="";
	

	public String getRedirecturl() {
		return redirecturl;
	}

	public void setRedirecturl(String redirecturl) {
		this.redirecturl = redirecturl;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String execute(){
		String campaignid="",emailid="",blastid="";
		HttpServletRequest request=ServletActionContext.getRequest();
		String fromip=request.getHeader("x-forwarded-for");
		if(fromip==null)fromip="";
		if("".equals(fromip))fromip=request.getRemoteAddr();
		try{
		campaignid=EncodeNum.decodeNum(tid);
		blastid=EncodeNum.decodeNum(bid);
		}catch(Exception e){
			System.out.println("Exception Occured in decode campainid in CampaignEmailsOpenAction"+tid+"::"+bid);
		}
	    try{
	    	if(token.trim().indexOf("@")>-1)
		    	emailid=token.trim();
			else
	    	    emailid=StringEncryptDecrypt.getDecryptedString(token.trim());
		  
	    }catch(Exception e){System.out.println("Exception occured in decode email in CampaignEmailsOpenAction"+token);}
		String currenttime=DateUtil.getCurrDBFormatDate();
	    if("openemail".equals(purpose)){
	        String serveraddress="http://"+EbeeConstantsF.get("serveraddress","www.eventbee.com");
	    	DbUtil.executeUpdateQuery("insert into marketing_emails_track(campid,email,blastid,purpose,datetime,ipcall)"+
	    	"values(?,?,?,?,to_timestamp(?,'YYYY-MM-dd HH24:MI:ss.S'),?)", new String[]{campaignid.trim(),emailid.trim(),blastid.trim(),purpose,currenttime,fromip});
	    	redirecturl=serveraddress+"/home/images/spacer.gif";
	    }else{
	    	DbUtil.executeUpdateQuery("insert into marketing_emails_track(campid,email,blastid,purpose,link_url,datetime,ipcall)"+
	    	    	"values(?,?,?,?,?,to_timestamp(?,'YYYY-MM-dd HH24:MI:ss.S'),?)", new String[]{campaignid.trim(),emailid.trim(),blastid.trim(),purpose,redirecturl,currenttime,fromip});
	    }
		return "loadspacer";
	}
	
}
