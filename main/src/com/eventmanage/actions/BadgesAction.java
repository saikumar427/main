package com.eventmanage.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Result;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import java.util.ResourceBundle;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.struts2.ServletActionContext;

import com.event.beans.BadgesData;
import com.event.dbhelpers.BadgesDB;
import com.event.dbhelpers.DisplayAttribsDB;
import com.event.dbhelpers.EmailAttendeesDB;
import com.event.dbhelpers.SpecialFeeDB;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.I18NCacheData;
import com.eventbee.general.formatting.EventbeeStrings;
import com.eventbee.general.formatting.WriteSelectHTML;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;


 public class BadgesAction extends ActionSupport implements Preparable,ValidationAware{

	private static final long serialVersionUID = 3638845334032069827L;
	private String eid="";
	private String setid="";
	private ArrayList attributes=new ArrayList();
	private String [] fonttypes={};
	private String [] fontsizes={};
	private String line1fontsize="";
	private String line2fontsize="";
	private String line1fontfamily="";
	private String line2fontfamily="";	
	private String result="";
	private String[] displayFields={};
	private BadgesData badgesData=new BadgesData();
	private String purpose="";
	private ArrayList dates=new ArrayList();
	private HashMap<String,String> badgeAttribs=new HashMap<String,String>();
	private String msgType = "badgesettings";
	private HashMap<String,String> defaultvalues=new HashMap<String,String>();
	private String configValue;
	private InputStream inputStream;
	private ArrayList attributeFields = new ArrayList();
	private boolean errorsExists;
	private String radioType="1";
	private HashMap<String,ArrayList> attribOptionMap=new HashMap<String,ArrayList>();
	private HashMap<String,String> optionLabelMap=new HashMap<String, String>();
	private HashMap<String,ArrayList<Entity>> subQuestionsMap=new HashMap<String, ArrayList<Entity>>();
	
	 
	public String getRadioType() {
		return radioType;
	}

	public void setRadioType(String radioType) {
		this.radioType = radioType;
	}

	public boolean isErrorsExists() {
		return errorsExists;
	}

	public void setErrorsExists(boolean errorsExists) {
		this.errorsExists = errorsExists;
	}

	public HashMap<String, String> getDefaultvalues() {
		return defaultvalues;
	}

	public void setDefaultvalues(HashMap<String, String> defaultvalues) {
		this.defaultvalues = defaultvalues;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ArrayList getAttributeFields() {
		return attributeFields;
	}

	public void setAttributeFields(ArrayList attributeFields) {
		this.attributeFields = attributeFields;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public HashMap<String, String> getBadgeAttribs() {
		return badgeAttribs;
	}

	public void setBadgeAttribs(HashMap<String, String> badgeAttribs) {
		this.badgeAttribs = badgeAttribs;
	}

	public ArrayList getDates() {
		return dates;
	}

	public void setDates(ArrayList dates) {
		this.dates = dates;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String[] getDisplayFields() {
		return displayFields;
	}

	public void setDisplayFields(String[] displayFields) {
		this.displayFields = displayFields;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getLine1fontsize() {
		return line1fontsize;
	}

	public void setLine1fontsize(String line1fontsize) {
		this.line1fontsize = line1fontsize;
	}

	public String getLine2fontsize() {
		return line2fontsize;
	}

	public void setLine2fontsize(String line2fontsize) {
		this.line2fontsize = line2fontsize;
	}

	public String getLine1fontfamily() {
		return line1fontfamily;
	}

	public void setLine1fontfamily(String line1fontfamily) {
		this.line1fontfamily = line1fontfamily;
	}

	public String getLine2fontfamily() {
		return line2fontfamily;
	}

	public void setLine2fontfamily(String line2fontfamily) {
		this.line2fontfamily = line2fontfamily;
	}

	public BadgesData getBadgesData() {
		return badgesData;
	}

	public void setBadgesData(BadgesData badgesData) {
		this.badgesData = badgesData;
	}

	public String[] getFontsizes() {
		return fontsizes;
	}

	public void setFontsizes(String[] fontsizes) {
		this.fontsizes = fontsizes;
	}

	public String[] getFonttypes() {
		return fonttypes;
	}

	public void setFonttypes(String[] fonttypes) {
		this.fonttypes = fonttypes;
	}

	public String getSetid() {
		return setid;
	}

	public void setSetid(String setid) {
		this.setid = setid;
	}

	public ArrayList getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList attributes) {
		this.attributes = attributes;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}
	
	public HashMap<String, ArrayList> getAttribOptionMap() {
		return attribOptionMap;
	}

	public void setAttribOptionMap(HashMap<String, ArrayList> attribOptionMap) {
		this.attribOptionMap = attribOptionMap;
	}

	public HashMap<String, String> getOptionLabelMap() {
		return optionLabelMap;
	}

	public void setOptionLabelMap(HashMap<String, String> optionLabelMap) {
		this.optionLabelMap = optionLabelMap;
	}

	public HashMap<String, ArrayList<Entity>> getSubQuestionsMap() {
		return subQuestionsMap;
	}

	public void setSubQuestionsMap(
			HashMap<String, ArrayList<Entity>> subQuestionsMap) {
		this.subQuestionsMap = subQuestionsMap;
	}

	public void populateData(){
		configValue= BadgesDB.getConfigValue(eid);
		//setid=ReportsDB.getAttribSetID(eid,"EVENT");	
		//attributes=BadgesDB.getCustomAttributes(eid);
		try{
			HashMap allmap=BadgesDB.getAllCustomAttributesMap(eid);
			attributes=(ArrayList)allmap.get("attriblist");
			subQuestionsMap=(HashMap<String,ArrayList<Entity>>)allmap.get("subquestionofmap");
			optionLabelMap=(HashMap)allmap.get("optionlabelmap");
			attribOptionMap=(HashMap<String,ArrayList>)allmap.get("attriboptionmap");
		}catch(Exception e){
			System.out.println("BadgesAction populateDataWithOutDBValues: "+e.getMessage());
		}
		HashMap<String, String> hm= new HashMap<String,String>();
		hm.put("module", "badge_settings");
		badgeAttribs= DisplayAttribsDB.getDisplayAttribs(hm, I18NCacheData.getI18NLanguage(eid), eid);//DisplayAttribsDB.getAttribValues(eid, "badge_settings");
		
		fonttypes=BadgesDB.getFontTypes();
		fontsizes=BadgesDB.getFontSizes();
		/*badgesData.setFontsize1(WriteSelectHTML.getSelectHtml(fontsizes,fontsizes,"line1fontsize","20",null,null));
		badgesData.setFonttype1(WriteSelectHTML.getSelectHtml(fonttypes,fonttypes,"line1fontfamily","",null,null));
		badgesData.setFontsize2(WriteSelectHTML.getSelectHtml(fontsizes,fontsizes,"line2fontsize","16",null,null));
		badgesData.setFonttype2(WriteSelectHTML.getSelectHtml(fonttypes,fonttypes,"line2fontfamily","",null,null));*/
		badgesData.setPageheight(badgeAttribs.get("event.badge.page_size_height"));
		badgesData.setPagewidth(badgeAttribs.get("event.badge.page_size_width"));
		badgesData.setLeftmargin(badgeAttribs.get("event.badge.page_margin_left"));
		badgesData.setRightmargin(badgeAttribs.get("event.badge.page_margin_right"));
		badgesData.setTopmargin(badgeAttribs.get("event.badge.page_margin_top"));
		badgesData.setBottommargin(badgeAttribs.get("event.badge.page_margin_bottom"));
		badgesData.setColwidth(badgeAttribs.get("event.badge.badge_size_width"));
		badgesData.setColheight(badgeAttribs.get("event.badge.badge_size_height"));
	    badgesData.setFontsize1(WriteSelectHTML.getSelectHtml(fontsizes,fontsizes,"line1fontsize",badgeAttribs.get("event.badge.namefield_font_size"),null,null));
		badgesData.setFonttype1(WriteSelectHTML.getSelectHtml(fonttypes,fonttypes,"line1fontfamily",badgeAttribs.get("event.badge.namefield_font_type"),null,null));
		badgesData.setFontsize2(WriteSelectHTML.getSelectHtml(fontsizes,fontsizes,"line2fontsize",badgeAttribs.get("event.badge.othertext_font_size"),null,null));
		badgesData.setFonttype2(WriteSelectHTML.getSelectHtml(fonttypes,fonttypes,"line2fontfamily",badgeAttribs.get("event.badge.othertext_font_type"),null,null));
		//System.out.println("badgesData.setFonttype1 is"+badgesData.getFonttype1);
		 
		
		int year=0;
		java.util.Date d=new java.util.Date();
	    year=d.getYear()+1900;
	    
	    String syear=badgesData.getSYear();
	    if("".equals(syear)) syear="1";
	    String eyear=badgesData.getEYear();
	    if("".equals(eyear)) eyear="1";
	    int displayStartYear=Integer.parseInt(syear);
	    int displayEndYear=Integer.parseInt(eyear); 	    
	    badgesData.setStartMonth(EventbeeStrings.getMonthHtml(badgesData.getSMonth(),"badgesData.sMonth"));
		badgesData.setStartDay(EventbeeStrings.getDayHtml(badgesData.getSDay(),"badgesData.sDay"));
		badgesData.setStartYear(EventbeeStrings.getYearHtml(year-1,3,displayStartYear,"badgesData.sYear"));
		badgesData.setEndDay(EventbeeStrings.getDayHtml(badgesData.getEDay(),"badgesData.eDay"));
		badgesData.setEndYear(EventbeeStrings.getYearHtml(year-1,3,displayEndYear,"badgesData.eYear"));
		badgesData.setEndMonth(EventbeeStrings.getMonthHtml(badgesData.getEMonth(),"badgesData.eMonth"));
		
		  java.util.Date today=new java.util.Date();
	        Format fm=new SimpleDateFormat("MM/dd/yyyy");
	        String timenow=fm.format(today);
		System.out.println("the time now is::"+timenow);
		badgesData.setStartDate(timenow);
		badgesData.setEndDate(timenow);
		badgesData.setTicketsList(EmailAttendeesDB.getTicketsList(eid));
		
	}
	public String execute(){
		String curLvl=ActionContext.getContext().getParameters().get("curLvl").toString();
		String pwrType=ActionContext.getContext().getParameters().get("pwrType").toString();
		int BadgesTkt = 300;
		int BadgesRsvp = 150;
		System.out.println("Current Level : "+curLvl+" , Power Type : "+pwrType+" & EventId : "+eid);
		if(Integer.parseInt(curLvl)==BadgesRsvp || Integer.parseInt(curLvl)>=BadgesTkt){
			getEventDates();
	        populateData();
			return "success";
		}else
			return "pageRedirect";
	}
	public void getEventDates(){
	    dates=BadgesDB.getEventDates(eid);

	}
	public String attendeeLabels(){
		ResourceBundle resourceBundle =I18n.getResourceBundle();
		boolean status=validateData();
		if(status){
			long badgestime=System.currentTimeMillis();
			Vector vc=BadgesDB.getAttendeeListInfo(eid,badgesData);
			if(vc==null){
				addFieldError("foperror", I18n.getString("global.none.found.lbl"));
				if(!getFieldErrors().isEmpty()){
					radioType=badgesData.getTransactionradio();
					errorsExists = true;
					getEventDates();
			        populateCustomBadgesData();
			        populateDataWithOutDBValues();
			        return "success";
				}
			}
			String mgrId=ActionContext.getContext().getParameters().get("mgrId").toString();
			if(purpose.equals("rsvpbadges"))
				SpecialFeeDB.chekingSpecialFee(eid,mgrId,"150","RSVP Attendee Badges");
			else if(purpose.equals("ticketingbadges")){
				if("Yes".equalsIgnoreCase(SpecialFeeDB.checkUpgradeStatus(eid, "Badges", "Ticketing", "300")))
				SpecialFeeDB.chekingSpecialFee(eid,mgrId,"300","Ticketing Attendee Badges");
			}
			badgeAttribs.put("event.badge.page_size_height", badgesData.getPageheight());
			badgeAttribs.put("event.badge.page_size_width", badgesData.getPagewidth());
			badgeAttribs.put("event.badge.page_margin_left", badgesData.getLeftmargin());
			badgeAttribs.put("event.badge.page_margin_right", badgesData.getRightmargin());
			badgeAttribs.put("event.badge.page_margin_top", badgesData.getTopmargin());
			badgeAttribs.put("event.badge.page_margin_bottom", badgesData.getBottommargin());
			badgeAttribs.put("event.badge.badge_size_width", badgesData.getColwidth());
			badgeAttribs.put("event.badge.badge_size_height", badgesData.getColheight());
			badgeAttribs.put("event.badge.namefield_font_size",line1fontsize);
			badgeAttribs.put("event.badge.namefield_font_type",line1fontfamily );
			badgeAttribs.put("event.badge.othertext_font_size",line2fontsize );
			badgeAttribs.put("event.badge.othertext_font_type",line2fontfamily );
			long badgesattribstime=System.currentTimeMillis();
			DisplayAttribsDB.insertDisplayAttribs(eid,"badge_settings",badgeAttribs , true);
			long badgesattribstotaltime=(System.currentTimeMillis())-badgesattribstime;
			System.out.println("Attendee_Badges_eid:"+eid+" time taken to insert badges attribs: "+badgesattribstotaltime+" MS");
			badgesData.setLine1fontfamily(line1fontfamily);
			badgesData.setLine1fontsize(line1fontsize);
			badgesData.setLine2fontfamily(line2fontfamily);
			badgesData.setLine2fontsize(line2fontsize);
			//long fopresulttime=System.currentTimeMillis();
			result=BadgesDB.getBadgesData(eid,badgesData,attributeFields,purpose,vc);
			/*long fopresulttotaltime=(System.currentTimeMillis())-fopresulttime;
			System.out.println("Attendee_Badges_eid:"+eid+" time taken to get badges foptags result data: "+fopresulttotaltime+" MS");*/			try{
				long pdftime=System.currentTimeMillis();
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpServletResponse httpservletresponse=ServletActionContext.getResponse();
				StringReader reader = new StringReader(result);
				FopFactory fopFactory = FopFactory.newInstance();
				FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
				ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
				Fop fop = fopFactory.newFop("application/pdf",foUserAgent, bytearrayoutputstream);
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory.newTransformer();
				Result res = new SAXResult(fop.getDefaultHandler());
				transformer.transform(new StreamSource(reader), res);
				byte[] pdfBytes = bytearrayoutputstream.toByteArray();
				if(pdfBytes!=null){
					inputStream = new ByteArrayInputStream ( pdfBytes );
			    }
				httpservletresponse.setContentLength(pdfBytes.length); 
				long pdftotaltime=(System.currentTimeMillis())-pdftime;
				System.out.println("Attendee_Badges_eid:"+eid+" time taken to construct pdf: "+pdftotaltime+" MS");

				  /*StringBufferInputStream stringbufferinputstream=new StringBufferInputStream(result);
				ByteArrayInputStream  stringbufferinputstream=new ByteArrayInputStream(result.toString().getBytes("UTF-8"));
		        Driver driver = new Driver(new InputSource(stringbufferinputstream), bytearrayoutputstream);
		       	driver.setRenderer(1);            
		        driver.run();
				if("".equals(fopval)){
		        byte abyte0[] = bytearrayoutputstream.toByteArray();
		        request.setAttribute("content", abyte0);
	            httpservletresponse.setContentLength(abyte0.length);
	            httpservletresponse.getOutputStream().write(abyte0);
	            httpservletresponse.getOutputStream().flush();
				   }*/
	    }catch(Exception exception){
	    	System.out.println("In Badges Action Exception: "+exception.getMessage());
	    	addFieldError("foperror", resourceBundle.getString("ba.inv.vals.entrd.crct.val"));
			if(!getFieldErrors().isEmpty()){
				errorsExists = true;
				getEventDates();
				populateDataWithOutDBValues();
				return "success";
			}
	    }
	    long badgestotaltime=(System.currentTimeMillis())-badgestime;
		System.out.println("Attendee_Badges_eid:"+eid+" Total time taken to *** generate pdf badges *** : "+badgestotaltime+" MS");
		return "badgespdf";
		}else{
		 if(!getFieldErrors().isEmpty()){
			 errorsExists = true;
			}
			getEventDates();
			populateDataWithOutDBValues();
			return "success";
		}
	}
	
	public boolean validateData(){
		ResourceBundle resourceBundle =I18n.getResourceBundle();

		if("".equals(badgesData.getLeftmargin().trim())) badgesData.setLeftmargin("0");
		if("".equals(badgesData.getRightmargin().trim())) badgesData.setRightmargin("0");
		if("".equals(badgesData.getTopmargin().trim())) badgesData.setTopmargin("0");
		if("".equals(badgesData.getBottommargin().trim())) badgesData.setBottommargin("0");
		double leftmargin=0;
		double rightmargin=0;
		double topmargin=0;
		double bottommargin=0;
		double pagewidth=0;
		double Colwidth=0;
		double Colheight=0;
		double pagehgt=0;
		try{
			 leftmargin=Double.parseDouble(badgesData.getLeftmargin());
		}catch(Exception e){
			addFieldError("badgesData.pagewidth",resourceBundle.getString("ba.inv.left.margn.lbl"));
		}
		try{
			rightmargin=Double.parseDouble(badgesData.getRightmargin());
		}catch(Exception e){
			addFieldError("badgesData.pagewidth",resourceBundle.getString("ba.inv.right.margn.lbl"));
		}
		try{
			topmargin=Double.parseDouble(badgesData.getTopmargin());
		}catch(Exception e){
			addFieldError("badgesData.pagewidth",resourceBundle.getString("ba.inv.top.margn.lbl"));
		}
		try{
			bottommargin=Double.parseDouble(badgesData.getBottommargin());
		}catch(Exception e){
			addFieldError("badgesData.pagewidth",resourceBundle.getString("ba.inv.btm.margn.lbl"));
		}
		
		double hormargin=leftmargin+rightmargin;
		double vermargin=topmargin+bottommargin;
		if("".equals(badgesData.getPagewidth().trim())){
			addFieldError("badgesData.pagewidth",resourceBundle.getString("ba.pg.size.wdth.emty.lbl"));
		}else if("0".equals(badgesData.getPagewidth().trim())){
			addFieldError("badgesData.pagewidth",resourceBundle.getString("ba.pg.width.grtr.zro.lbl"));
		}else{
			try{
				 pagewidth=Double.parseDouble(badgesData.getPagewidth());
			}catch(Exception e){
				addFieldError("badgesData.pagewidth",resourceBundle.getString("ba.inv.pg.wdth.lbl"));
			}
		}
		
		if("".equals(badgesData.getPageheight().trim())){
			addFieldError("badgesData.pageheight",resourceBundle.getString("ba.pg.sze.hght.emty.lbl"));
		}else if("0".equals(badgesData.getPageheight().trim())){
			addFieldError("badgesData.pagewidth",resourceBundle.getString("ba.pg.sze.hght.grt.zro.lbl"));
		}else{
			try{
				pagehgt=Double.parseDouble(badgesData.getPageheight());
			}catch(Exception e){
				addFieldError("badgesData.pagewidth",resourceBundle.getString("ba.inv.pg.sze.hght.lbl"));
			}
		}
		
		if("".equals(badgesData.getColwidth().trim())){
			addFieldError("badgesData.colwidth",resourceBundle.getString("ba.bdge.sze.wdth.emty.lbl"));
		}else if("0".equals(badgesData.getColwidth().trim())){
			addFieldError("badgesData.colwidth",resourceBundle.getString("ba.bdge.sze.wdth.grtr.zro.lbl"));
		}else{
			try{
				 Colwidth=Double.parseDouble(badgesData.getColwidth());
			}catch(Exception e){
				addFieldError("badgesData.pagewidth",resourceBundle.getString("ba.inv.bdge.sze.wdth.lbl"));
			}
		}
		
		if("".equals(badgesData.getColheight().trim())){
			addFieldError("badgesData.colheight",resourceBundle.getString("ba.bdg.sze.hght.emty.lbl"));
		}else if("0".equals(badgesData.getColheight().trim())){
			addFieldError("badgesData.colheight",resourceBundle.getString("ba.sze.hght.grt.zro.lbl"));
		}else{
			try{
				 Colheight=Double.parseDouble(badgesData.getColheight());
			}catch(Exception e){
				addFieldError("badgesData.pagewidth",resourceBundle.getString("ba.inv.bdg.sze.hght.lbl"));
			}
		}
		
		if(!"".equals(badgesData.getPagewidth().trim()) && !"0".equals(badgesData.getPagewidth().trim())){
			double tabwidth=pagewidth-(double)hormargin;
			if(!"".equals(badgesData.getColwidth().trim()) && !"0".equals(badgesData.getColwidth().trim())){
				if(Colwidth>tabwidth)
					addFieldError("badgesData.pagewidth",resourceBundle.getString("ba.bdge.sze.wdth.less.pgsze.lbl"));
			}
		}
		
		if(!"".equals(badgesData.getPageheight().trim()) && !"0".equals(badgesData.getPageheight().trim())){
			double tabheight=pagehgt-(double)vermargin;
			if(!"".equals(badgesData.getColheight().trim()) && !"0".equals(badgesData.getColheight().trim())){
				if(Colheight>tabheight)
					addFieldError("pageheighterror",resourceBundle.getString("ba.bdge.sze.wdth.less.pgsze.hght.lbl"));
			}
		}
		
		if(!getFieldErrors().isEmpty()){
			errorsExists = true;
			radioType=badgesData.getTransactionradio();
			return false;
		}
		
		return true;
	}
	
public void populateCustomBadgesData(){
		
		badgesData.setPageheight(badgesData.getPageheight());
		badgesData.setPagewidth(badgesData.getPagewidth().trim());
		badgesData.setLeftmargin(badgesData.getLeftmargin().trim());
		badgesData.setRightmargin(badgesData.getRightmargin().trim());
		badgesData.setTopmargin(badgesData.getTopmargin().trim());
		badgesData.setBottommargin(badgesData.getBottommargin().trim());
		badgesData.setColwidth(badgesData.getColwidth().trim());
		badgesData.setColheight(badgesData.getColheight().trim());
	    badgesData.setFontsize1(WriteSelectHTML.getSelectHtml(fontsizes,fontsizes,"line1fontsize",line1fontsize,null,null));
		badgesData.setFonttype1(WriteSelectHTML.getSelectHtml(fonttypes,fonttypes,"line1fontfamily",line1fontfamily,null,null));
		badgesData.setFontsize2(WriteSelectHTML.getSelectHtml(fontsizes,fontsizes,"line2fontsize",line2fontsize,null,null));
		badgesData.setFonttype2(WriteSelectHTML.getSelectHtml(fonttypes,fonttypes,"line2fontfamily",line2fontfamily,null,null));
		badgesData.setTransactionId(badgesData.getTransactionId().trim());
		setAttributeFields(attributeFields);
		}
	
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void populateDataWithOutDBValues(){
		configValue= BadgesDB.getConfigValue(eid);
		attributes=BadgesDB.getCustomAttributes(eid);
		//badgeAttribs= DisplayAttribsDB.getAttribValues(eid, "badge_settings");
		fonttypes=BadgesDB.getFontTypes();
		fontsizes=BadgesDB.getFontSizes();
		badgesData.setPageheight(badgesData.getPageheight());
		badgesData.setPagewidth(badgesData.getPagewidth().trim());
		badgesData.setLeftmargin(badgesData.getLeftmargin().trim());
		badgesData.setRightmargin(badgesData.getRightmargin().trim());
		badgesData.setTopmargin(badgesData.getTopmargin().trim());
		badgesData.setBottommargin(badgesData.getBottommargin().trim());
		badgesData.setColwidth(badgesData.getColwidth().trim());
		badgesData.setColheight(badgesData.getColheight().trim());
	    badgesData.setFontsize1(WriteSelectHTML.getSelectHtml(fontsizes,fontsizes,"line1fontsize",line1fontsize,null,null));
		badgesData.setFonttype1(WriteSelectHTML.getSelectHtml(fonttypes,fonttypes,"line1fontfamily",line1fontfamily,null,null));
		badgesData.setFontsize2(WriteSelectHTML.getSelectHtml(fontsizes,fontsizes,"line2fontsize",line2fontsize,null,null));
		badgesData.setFonttype2(WriteSelectHTML.getSelectHtml(fonttypes,fonttypes,"line2fontfamily",line2fontfamily,null,null));
		int year=0;
		java.util.Date d=new java.util.Date();
	    year=d.getYear()+1900;
	    
	    String syear=badgesData.getSYear();
	    if("".equals(syear)) syear="1";
	    String eyear=badgesData.getEYear();
	    if("".equals(eyear)) eyear="1";
	    int displayStartYear=Integer.parseInt(syear);
	    int displayEndYear=Integer.parseInt(eyear); 	    
	    badgesData.setStartMonth(EventbeeStrings.getMonthHtml(badgesData.getSMonth(),"badgesData.sMonth"));
		badgesData.setStartDay(EventbeeStrings.getDayHtml(badgesData.getSDay(),"badgesData.sDay"));
		badgesData.setStartYear(EventbeeStrings.getYearHtml(year-1,3,displayStartYear,"badgesData.sYear"));
		badgesData.setEndDay(EventbeeStrings.getDayHtml(badgesData.getEDay(),"badgesData.eDay"));
		badgesData.setEndYear(EventbeeStrings.getYearHtml(year-1,3,displayEndYear,"badgesData.eYear"));
		badgesData.setEndMonth(EventbeeStrings.getMonthHtml(badgesData.getEMonth(),"badgesData.eMonth"));
		badgesData.setTransactionId(badgesData.getTransactionId().trim());
		setAttributeFields(attributeFields);
		badgesData.setTicketsList(EmailAttendeesDB.getTicketsList(eid));
	}

}
