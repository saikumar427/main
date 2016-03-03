package com.myemailmarketing.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.json.JSONObject;

import com.eventbee.beans.Entity;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.helpers.FileWriterHelper;
import com.membertasks.actions.MemberTasksWrapper;
import com.myemailmarketing.beans.CampaignDetails;
import com.myemailmarketing.dbhelpers.CampaignDesignDB;
import com.myemailmarketing.helpers.CampaignHelper;

public class CampaignAddAction extends MemberTasksWrapper{

	private static final long serialVersionUID = -2941084779242802890L;
	private CampaignDetails campaignData=new CampaignDetails();
	private ArrayList<Entity> widthSizes = new ArrayList<Entity>();
	private String campaddeventsinfo="";
	private String campId="";
	private String priviewContent="";
	private ArrayList<Entity> descriptiontype=new ArrayList<Entity>();
	private ArrayList<HashMap<String,String>> userEventList=new ArrayList<HashMap<String,String>>();
	private String previewFileName="";
	private String content="";
	private String mytextarea;
	private String jsonData="";
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getMytextarea() {
		return mytextarea;
	}

	public void setMytextarea(String mytextarea) {
		this.mytextarea = mytextarea;
	}

	public String getPreviewFileName() {
		return previewFileName;
	}

	public void setPreviewFileName(String previewFileName) {
		this.previewFileName = previewFileName;
	}
	public ArrayList<Entity> getDescriptiontype() {
		return descriptiontype;
	}
	public void setDescriptiontype(ArrayList<Entity> descriptiontype) {
		this.descriptiontype = descriptiontype;
	}
	public String getPriviewContent() {
		return priviewContent;
	}
	public void setPriviewContent(String priviewContent) {
		this.priviewContent = priviewContent;
	}
	public String getCampId() {
		return campId;
	}
	public void setCampId(String campId) {
		this.campId = campId;
	}
	public ArrayList<HashMap<String, String>> getUserEventList() {
		return userEventList;
	}
	public void setUserEventList(ArrayList<HashMap<String, String>> userEventList) {
		this.userEventList = userEventList;
	}
	public String getCampaddeventsinfo() {
		return campaddeventsinfo;
	}
	public void setCampaddeventsinfo(String campaddeventsinfo) {
		this.campaddeventsinfo = campaddeventsinfo;
	}
	public ArrayList<Entity> getWidthSizes() {
		return widthSizes;
	}
	public void setWidthSizes(ArrayList<Entity> widthSizes) {
		this.widthSizes = widthSizes;
	}
	public CampaignDetails getCampaignData() {
		return campaignData;
	}
	public void setCampaignData(CampaignDetails campaignData) {
		this.campaignData = campaignData;
	}
	public void prepare() throws Exception {
		try {
			super.prepare();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	public String execute(){
		populateDescriptionTypes();
		campaddeventsinfo=EbeeConstantsF.get("camp.add.events.info","Copy and Paste following tokens within your content as required. Please don't alter tokens, they will be replaced with real values in the actual email campaign that goes out to your mailing lists!");
		Long start=System.currentTimeMillis();
		System.out.println("before calling usereventslist"+System.currentTimeMillis());
		/**Uncomment the below  line and jsp:include of campuserevents.jsp entry in camphelpercontent.jsp
		 * to display the user events list in create campaign screen**/
		userEventList=CampaignDesignDB.getUserEventsList(userId);
		Long end=System.currentTimeMillis();
		System.out.println("Time taken"+(end-start));
		widthSizes=fillWidthSizes();
		return SUCCESS;
	}
	public ArrayList<Entity> fillWidthSizes(){
		ArrayList<Entity> widthsize=new ArrayList<Entity>();
		for (int i=1; i<61;i++){
			widthsize.add(new Entity(Integer.toString(i)+"px",Integer.toString(i)+"px"));	
		}
		return widthsize;
	}
	public String saveCampaign(){
		campaignData=CampaignHelper.processCampaignData(campaignData);
		campId=CampaignDesignDB.saveCampaignData(userId, campaignData,campId,mytextarea);
		JSONObject json=new JSONObject();
		try
		{
			json.put("campid",campId);
		jsonData=json.toString();
		}
		catch(Exception e)
		{
			System.out.println("the exception is"+e);
		}
		return "created";
	}
	public String previewCampaign(){
		 campaignData=CampaignHelper.processCampaignData(campaignData);
		 priviewContent=CampaignHelper.getPreviewData(campaignData,mytextarea);
		 return "previewCampaign";
	}
	public String editCampaign(){
		populateDescriptionTypes();
		campaddeventsinfo=EbeeConstantsF.get("camp.add.events.info","Copy and Paste following information to your HTML and Text content as required.<br/> Please Note: Do not alter URL links");
		userEventList=CampaignDesignDB.getUserEventsList(userId);
		widthSizes=fillWidthSizes();
		campaignData=CampaignDesignDB.getCampaignData(userId, campId);
		actionTitle=campaignData.getCampName()+" > Edit Campaign";
		return SUCCESS;
	}
	public String previewExistingCampaign(){
		campaignData=CampaignDesignDB.getCampaignData(userId, campId);
		priviewContent=CampaignHelper.getPreviewData(campaignData,"");
		return "previewCampaign";
	}
	public void populateDescriptionTypes(){	   
		descriptiontype=getDescriptionTypes();
	}
	public ArrayList<Entity> getDescriptionTypes(){
		ArrayList<Entity> descriptiontype=new ArrayList<Entity>();
		descriptiontype.add(new Entity("html","HTML"));
		descriptiontype.add(new Entity("wysiwyg","WYSIWYG Editor"));
		return descriptiontype;
	}
		public String campaignPreview(){
		System.out.println("campaignData in Action:::::"+campaignData);
		System.out.println("mytextarea:::::"+mytextarea);
		//String  content="";
		//..content=CampaignHelper.getPreviewData(campaignData,mytextarea);
		//System.out.println("content from the method is"+content);
		//campaignData.setPreviewcontent(content);
		return "success";
	}
	public String contentShow()	{
		content=CampaignHelper.getPreviewData(campaignData,mytextarea);
		campaignData.setPreviewcontent(content);
        return "preview";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
