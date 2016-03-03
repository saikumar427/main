package com.myemailmarketing.actions;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;

import com.eventbee.beans.CreditCardData;
import com.eventbee.beans.Entity;
import com.eventbee.creditcard.CreditCardDBHelper;
import com.eventbee.general.DbUtil;
import com.membertasks.actions.MemberTasksWrapper;
import com.myemailmarketing.beans.CampaignDetails;
import com.myemailmarketing.beans.MailListDetails;
import com.myemailmarketing.beans.MailQuotaDetails;
import com.myemailmarketing.dbhelpers.CampaignDesignDB;
import com.myemailmarketing.dbhelpers.MailQuotaDB;
import com.myemailmarketing.dbhelpers.MailingListDB;
import com.myemailmarketing.helpers.JsonBuilderHelper;

public class EmailMarketingSummaryAction extends MemberTasksWrapper{

	private static final long serialVersionUID = -7351202608334624032L;
	private ArrayList<CampaignDetails> campaignList=new ArrayList<CampaignDetails>();
	private ArrayList<MailListDetails> mailingList=new ArrayList<MailListDetails>();
	private ArrayList<Entity> mailQuotaList=new ArrayList<Entity>();
	private CreditCardData creditCardData=new CreditCardData();
	private MailQuotaDetails mailQuotaDetails=new MailQuotaDetails();
	private String mailQuota="0";
	private String jsonData="";
	private HashMap<String,String> emailBlastsSummaryCount=new HashMap<String,String>();
	private String SeqId="";
	private String amount="";
	private String configentry="yes";
	
	public String getConfigentry() {
		return configentry;
	}
	public void setConfigentry(String configentry) {
		this.configentry = configentry;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public MailQuotaDetails getMailQuotaDetails() {
		return mailQuotaDetails;
	}
	public void setMailQuotaDetails(MailQuotaDetails mailQuotaDetails) {
		this.mailQuotaDetails = mailQuotaDetails;
	}
	public CreditCardData getCreditCardData() {
		return creditCardData;
	}
	public void setCreditCardData(CreditCardData creditCardData) {
		this.creditCardData = creditCardData;
	}
	public String getSeqId() {
		return SeqId;
	}
	public void setSeqId(String seqId) {
		SeqId = seqId;
	}
	public ArrayList<Entity> getMailQuotaList() {
		return mailQuotaList;
	}
	public void setMailQuotaList(ArrayList<Entity> mailQuotaList) {
		this.mailQuotaList = mailQuotaList;
	}
	public HashMap<String, String> getEmailBlastsSummaryCount() {
		return emailBlastsSummaryCount;
	}
	public void setEmailBlastsSummaryCount(
			HashMap<String, String> emailBlastsSummaryCount) {
		this.emailBlastsSummaryCount = emailBlastsSummaryCount;
	}
	public void prepare() throws Exception {
		try {
			super.prepare();
			//emailBlastsSummaryCount=CampaignDesignDB.getemailBlastsSummaryCount(userId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	public String execute(){
		
		if("".equals(userId) || userId==null){
			System.out.println("--- mgrId EMPTY or NULL case --- in EmailMarketingSummaryAction execute mgrId: "+userId);
			return "error";
		}
		System.out.println("the user id is"+userId);
		String entry=DbUtil.getVal("select 'Y' from config where config_id=cast(? as integer) and name=? and value=?", new String[]{userId,"mgr.emailmarketing.enable","Y"});
		System.out.println("the entry is::"+entry);
		if(entry==null || !"Y".equals(entry))
		{
		configentry="no";
		}
		emailBlastsSummaryCount=CampaignDesignDB.getemailBlastsSummaryCount(userId);
		mailQuota=MailQuotaDB.getMailQuota(userId);
		System.out.println("the avalible mail quota in home page"+mailQuota);
		campaignList=MailingListDB.getCamplist(userId);
		mailingList=MailingListDB.getListdetails(userId);
		mailQuotaList=MailQuotaDB.mailQuotaListDetails();
		SeqId=CreditCardDBHelper.getSeqID(userId);
		creditCardData.setSeqId(SeqId);		
		return SUCCESS;
	}
	
	/**
	 * @param campaignList the campaignList to set
	 */
	public void setCampaignList(ArrayList<CampaignDetails> campaignList) {
		this.campaignList = campaignList;
	}
	/**
	 * @return the campaignList
	 */
	public ArrayList<CampaignDetails> getCampaignList() {
		return campaignList;
	}
	public String getMailQuota() {
		return mailQuota;
	}
	public void setMailQuota(String mailQuota) {
		this.mailQuota = mailQuota;
	}
	public ArrayList<MailListDetails> getMailingList() {
		return mailingList;
	}
	public void setMailingList(ArrayList<MailListDetails> mailingList) {
		this.mailingList = mailingList;
	}
	public String populateMailLists(){
		mailingList=MailingListDB.getListdetails(userId);
		JSONObject js=JsonBuilderHelper.getMailListJson(mailingList);
		jsonData=js.toString();
		return "maillistJson";
	}
	public String populateCampaignLists(){
		campaignList=MailingListDB.getCamplist(userId);
		JSONObject js=JsonBuilderHelper.getCampListJson(campaignList);
		jsonData=js.toString();
		return "camplistJson";
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String getJsonData() {
		return jsonData;
	}
	public String getPaymentScreen(){		
		creditCardData.setAmount(amount);
		String creditCardScreenData=CreditCardDBHelper.creditCardScreenData(userId,SeqId,amount);
		creditCardData.setCreditCardScreenData(creditCardScreenData);
		creditCardData.setSeqId(SeqId);
		return "paymentScreen";
	}
}
