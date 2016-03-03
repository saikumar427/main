package com.myevents.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

//import org.apache.http.HttpRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;


import com.event.beans.AddEventData;
import com.event.dbhelpers.AddEventDB;
import com.event.dbhelpers.EditEventDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.UpgradeEventDB;
import com.event.helpers.DataPopulator;
import com.event.helpers.EventHelper;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EventBeeValidations;
import com.eventbee.general.EventListingKeywords;
import com.eventbee.general.GenUtil;
import com.eventbee.general.StatusObj;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class AddEventAction extends MyEventsActionWrapper implements Preparable,ValidationAware{
	private static final long serialVersionUID = -3773514102198232135L;
	private static final Logger log = Logger.getLogger(AddEventAction.class);
	private AddEventData addEventData=new AddEventData();
	private ArrayList<Entity> listingtype=new ArrayList<Entity>();
	private ArrayList<Entity> listingprivacy=new ArrayList<Entity>();
	private ArrayList<Entity> descriptiontype=new ArrayList<Entity>();
	private ArrayList<Entity> categorytype=new ArrayList<Entity>();      
	private List<Entity> countrylist=new ArrayList<Entity>();
	private ArrayList<Entity> timezones=new ArrayList<Entity>();
	private String eid="";
	private ArrayList<Entity> hours=new ArrayList<Entity>();
	private ArrayList<Entity> minutes=new ArrayList<Entity>();
	private String mytextarea;	
	private List<Entity> recurringDates;
	private List<Entity> optionsList;
	private String isRecurring="";
	private boolean errorsExists;
	private String subcategory="";
	private String jsonData="";
	private String msgType="listeventpage";
	private String eventStatus="";
	//private ArrayList<Entity> ntsEnable=new ArrayList<Entity>();
	private ArrayList<Entity> i18nLangList=new ArrayList<Entity>();
	
	public ArrayList<Entity> getI18nLangList() {
		ArrayList<Entity> list=new ArrayList<Entity>();
		list.add(new Entity("en_US","en_US"));
		list.add(new Entity("es_US","es_US"));
		i18nLangList=list;
		return i18nLangList;
	}

	public void setI18nLangList(ArrayList<Entity> i18nLangList) {
		this.i18nLangList = i18nLangList;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getIsRecurring() {
		return isRecurring;
	}

	public void setIsRecurring(String isRecurring) {
		this.isRecurring = isRecurring;
	}
	
	public List<Entity> getOptionsList() {
		return optionsList;
	}

	public void setOptionsList(List<Entity> optionsList) {
		this.optionsList = optionsList;
	}
	

	public boolean isErrorsExists() {
		return errorsExists;
	}

	public void setErrorsExists(boolean errorsExists) {
		this.errorsExists = errorsExists;
	}

	public List<Entity> getRecurringDates() {
		return recurringDates;
	}

	public void setRecurringDates(List<Entity> recurringDates) {
		this.recurringDates = recurringDates;
	}

	public String getMytextarea() {
		return mytextarea;
	}

	public void setMytextarea(String mytextarea) {
		this.mytextarea = mytextarea;
	}
	public ArrayList<Entity> getHours() {
		return hours;
	}

	public void setHours(ArrayList<Entity> hours) {
		this.hours = hours;
	}

	public ArrayList<Entity> getMinutes() {
		return minutes;
	}

	public void setMinutes(ArrayList<Entity> minutes) {
		this.minutes = minutes;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public AddEventData getAddEventData() {
		return addEventData;
	}

	public void setAddEventData(AddEventData addEventData) {
		this.addEventData = addEventData;
	}
	
	public ArrayList<Entity> getListingtype() {
		return listingtype;
	}

	public void setListingtype(ArrayList<Entity> listingtype) {
		this.listingtype = listingtype;
	}

	public ArrayList<Entity> getListingprivacy() {
		return listingprivacy;
	}

	public void setListingprivacy(ArrayList<Entity> listingprivacy) {
		this.listingprivacy = listingprivacy;
	}

	public ArrayList<Entity> getDescriptiontype() {
		return descriptiontype;
	}

	public void setDescriptiontype(ArrayList<Entity> descriptiontype) {
		this.descriptiontype = descriptiontype;
	}

	public ArrayList<Entity> getCategorytype() {
		return categorytype;
	}

	public void setCategorytype(ArrayList<Entity> categorytype) {
		this.categorytype = categorytype;
	}

	public List<Entity> getCountrylist() {
		return countrylist;
	}

	public void setCountrylist(List<Entity> countrylist) {
		this.countrylist = countrylist;
	}

	public ArrayList<Entity> getTimezones() {
		return timezones;
	}

	public void setTimezones(ArrayList<Entity> timezones) {
		this.timezones = timezones;
	}

	/*public ArrayList<Entity> getNtsEnable() {
		return ntsEnable;
	}

	public void setNtsEnable(ArrayList<Entity> ntsEnable) {
		this.ntsEnable = ntsEnable;
	}*/

	public void prepare(){	
		System.out.println("Mgr ID before super prepare:"+mgrId);
		try {
			super.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		System.out.println("Mgr ID after super prepare:"+mgrId);
		//eventData=new EventData();
	}
	public void populateListingTypes(){		
		listingtype=DataPopulator.populateListingTypes();
	}
	public void populateListingPrivacyTypes(){
		listingprivacy=DataPopulator.populateListingPrivacyTypes();						
	}
	public void populateDescriptionTypes(){	
		descriptiontype=DataPopulator.populateDescriptionTypes();
	}
	public void populateCountrylist(){
		countrylist=EventDB.getCountries();
	}
	public void populateCategoryList(){
		categorytype=DataPopulator.populateCategoryList();
	}
	
	public void populateTimeZones(){
		timezones=DataPopulator.populateTimeZones();
				
	}
	
	/*public void populateNTSEnable(){		
		ntsEnable=DataPopulator.populateNTSEnable();
	}*/
	
	public void populateListingData(){
		populateListingTypes();
		populateListingPrivacyTypes();
		populateDescriptionTypes();
		populateCountrylist();
		populateTimeZones();
		populateCategoryList();
		//populateNTSEnable();
		hours=DataPopulator.getHours();
		minutes=DataPopulator.getMinutes();
	}
	public String execute(){
		recurringDates = new ArrayList<Entity>();
		optionsList = new ArrayList<Entity>();
		DataPopulator.defaultDateDisplay(addEventData);
		populateListingData();		
		return "success";
		
	}
	
	public static String getIpFromRequest(HttpServletRequest request) {
	    String ip="";
	    ip=request.getHeader("x-forwarded-for");
	    ip=ip==null?request.getRemoteAddr():ip;
	    return ip;
	}
	
	public static void blockIp(String ip){
		System.out.println("blockIP::"+ip);
		com.eventbee.util.CoreConnector cc1=null;
		Map resMap=null;
		String data="";
		JSONObject json=new JSONObject();
		HashMap<String,String> inputparams=new HashMap<String,String>();		
		inputparams.put("mode","add");
		inputparams.put("ip",ip);
		inputparams.put("allow","allow");
		try{
		cc1=new com.eventbee.util.CoreConnector("http://"+com.eventbee.general.EbeeConstantsF.get("serveraddress","www.eventbee.com")+"/main/utiltools/blockedManager.jsp");
		cc1.setArguments(inputparams);
		cc1.setTimeout(50000);
		data=cc1.MGet();		
		}catch(Exception e){
		System.out.println("Error While Processing Request::"+e.getMessage());		
		}		
	}
	
	public String getEventsCount(){
		System.out.println("mgrid in getEventsCount::"+mgrId);
		String listingStatus="";
		
		
		String isexists = DbUtil.getVal("select value from mgr_config where name='mgr.eventlisting.allow'  and userid=cast(? as integer)",new String[]{mgrId});
		if("N".equals(isexists)){
			listingStatus = "donot_allow";			
		}
		else if("Y".equals(isexists)){
			listingStatus = "allow";
		}
		else {
			String count = AddEventDB.getEvtsCount(mgrId);	
			System.out.println("count in getEventsCount:::"+count);
			try{
			if(Integer.parseInt(count)<10){
				listingStatus = "allow";
			
				} 
				else{
					String evtcnt = AddEventDB.getTicketingEvtsCount(mgrId);				
					boolean flag = false;
					System.out.println("events count::"+evtcnt);
					if(Integer.parseInt(evtcnt)==0){					
						flag = true;
					}/*else if(Integer.parseInt(count)>=8){
						flag = true;
					}*/
					if(flag){
						String query = "insert into mgr_config(name,userid,value,created_at) values('mgr.eventlisting.allow',cast(? as integer),'N',now())";
						 DbUtil.executeUpdateQuery(query, new String[]{mgrId});
						 HttpServletRequest request = ServletActionContext.getRequest();
						 String ip=getIpFromRequest(request);
						 System.out.println("ip address:::"+ip);		 
						 // DbUtil.executeUpdateQuery("insert into iptable(status,created_at,ip) values('block',now(),?)", new String[]{ip});
						// blockIp(ip);					 
						 query="insert into suspected_user_activities(userid,ip,action,created_at) values(?,?,'event_listing_action',now())";
						 DbUtil.executeUpdateQuery(query,new String[]{mgrId,ip});
						 if(Integer.parseInt(evtcnt)==0)
							 listingStatus = "donot_allow";
						 /*else if(Integer.parseInt(count)>=8)
							 listingStatus = "donot_allow_events";*/
					}
					
			}
				
			}catch(Exception e){System.out.println("Exception in getEventsCount::"+e.getMessage());}		
		}	
		
		
		JSONObject jsonObj = new JSONObject();		
		try{
			jsonObj.put("listingStatus", listingStatus);
			jsonObj.put("mgrid", mgrId);
		}catch(Exception e){System.out.println("");}
		jsonData = jsonObj.toString();
		
		return "jsondata";
	}
	
	
	public void validateDescriptionkeywords(){
		String isexits="",description="",query="";
		String descriptiontype=addEventData.getDescriptiontype();
		if("wysiwyg".equals(descriptiontype)) {
			description=addEventData.getDescription();
		}
		else{
			description=addEventData.getTextcontent();
		}
		isexits=DbUtil.getVal("select 'Y' from mgr_config where name='mgr.eventlisting.allow' and userid=cast(? as integer) and value='Y'", new String[]{mgrId});
		if(isexits==null)isexits = "N";
		HttpServletRequest request = ServletActionContext.getRequest();
		String ip=getIpFromRequest(request);
		if(!"Y".equals(isexits)){			
		if(EventListingKeywords.keywords.size()>0){		
			System.out.println("description:::"+description+"::ip:::"+ip);
			for(int key=0;key<EventListingKeywords.keywords.size();key++){	
				
				
				if(description.indexOf(EventListingKeywords.keywords.get(key))>-1)
				
				{							
					
					query="insert into suspected_user_activities(userid,ip,action,created_at) values(?,?,'event_listing_action',now())";
					DbUtil.executeUpdateQuery(query,new String[]{mgrId,ip});
					DbUtil.executeUpdateQuery("insert into emptied_description_events(eventid,description,created_at,ip) values(cast(? as bigint),?,now(),?)",new String[]{eid,description,ip});
					DbUtil.executeUpdateQuery("update authentication set accounttype='blocked' where user_id=?", new String[]{mgrId});
					String email = DbUtil.getVal("select email from user_profile where user_id=?",new String[]{mgrId});							
					DbUtil.executeUpdateQuery("update user_profile set email='"+email+"_inactive_eb ' where user_id=?",new String[]{mgrId});
				
					break;
				}			
			}}
				else{
					System.out.println("from db::mgrid::"+mgrId+"and ip:::"+ip);
					ArrayList<String> keywords = new ArrayList<String>();
					keywords = AddEventDB.getKeywords();
					EventListingKeywords.keywords.addAll(keywords);					
					for(int i=0;i<keywords.size();i++){
						if(description.indexOf(keywords.get(i))>-1)
						{					
							query="insert into suspected_user_activities(userid,ip,action,created_at) values(?,?,'event_listing_action',now())";
							DbUtil.executeUpdateQuery(query,new String[]{mgrId,ip});
							DbUtil.executeUpdateQuery("insert into emptied_description_events(eventid,description,created_at,ip) values(cast(? as bigint),?,now(),?)",new String[]{eid,description,ip});
							DbUtil.executeUpdateQuery("update authentication set accounttype='blocked' where user_id=?", new String[]{mgrId});
							String email = DbUtil.getVal("select email from user_profile where user_id=?",new String[]{mgrId});							
							DbUtil.executeUpdateQuery("update user_profile set email='"+email+"_inactive_eb ' where user_id=?",new String[]{mgrId});
													
							break;
						}
					}
				} 
		
		}
	}
	
	public String getListingPopup(){
		System.out.println("get listing popup");
		return "listingpopup";
		
	}
	
	
	
	public boolean validateData(){
        String numbervalidatestatus="";
        ResourceBundle resourceBundle=I18n.getResourceBundle();
        if("".equals(addEventData.getEventTitle().trim())){
			addFieldError("addEventData.eventTitle", resourceBundle.getString("event.title.is.empty.errmsg"));			
		}
		if("1".equals(addEventData.getCategory())){
			addFieldError("addEventData.category", resourceBundle.getString("category.is.not.selected.errmsg"));			
		}
		if("1".equals(addEventData.getTimezones())){
			addFieldError("addEventData.timezones", resourceBundle.getString("time.zone.is.not.selected.errmsg"));			
		}
        if(!"true".equals(isRecurring)){
		if(addEventData.getStartmonth().trim().length()==0)
			 addFieldError("addEventData.getStartmonth", resourceBundle.getString("start.time.month.fld.is.empty.errmsg"));
			 else{
			  numbervalidatestatus=validateNumber(addEventData.getStartmonth().trim());
			    if("notvalidnumber".equals(numbervalidatestatus))
			     addFieldError("addEventData.getStartmonth", resourceBundle.getString("start.time.month.fld.should.be.numeric.errmsg")); 
			 }
			 if(addEventData.getStartday().trim().length()==0)
				  addFieldError("addEventData.getStartday", resourceBundle.getString("start.time.date.fld.is.empty.errmsg")); 
			 else{
				 numbervalidatestatus=validateNumber(addEventData.getStartday().trim());
				   if("notvalidnumber".equals(numbervalidatestatus))
				   addFieldError("addEventData.getStartday", resourceBundle.getString("start.time.date.fld.should.be.numeric.errmsg"));
			 }
			 if(addEventData.getStartyear().trim().length()==0)
				  addFieldError("addEventData.getStartyear", resourceBundle.getString("start.time.year.fld.is.empty.errmsg"));
			 else{
				 numbervalidatestatus=validateNumber(addEventData.getStartyear().trim());
				   if("notvalidnumber".equals(numbervalidatestatus))
				   addFieldError("addEventData.getStartyear", resourceBundle.getString("start.time.year.fld.should.be.numeric.errmsg"));
			 }
			 if(addEventData.getEndmonth().trim().length()==0)
				  addFieldError("addEventData.getEndmonth", resourceBundle.getString("end.time.month.fld.is.empty.errmsg"));
			 else{
				 numbervalidatestatus=validateNumber(addEventData.getEndmonth().trim());
				   if("notvalidnumber".equals(numbervalidatestatus))
				   addFieldError("addEventData.getEndmonth", resourceBundle.getString("end.time.month.fld.should.be.numeric.errmsg")); 
			 }
			 if(addEventData.getEndday().trim().length()==0)
				  addFieldError("addEventData.getEndday", resourceBundle.getString("end.time.date.fld.is.empty.errmsg"));
			 else{
			   numbervalidatestatus=validateNumber(addEventData.getEndday().trim());
				  if("notvalidnumber".equals(numbervalidatestatus))
				  addFieldError("addEventData.getEndday", resourceBundle.getString("end.time.date.fld.should.be.numeric.errmsg")); 
			 }
			 if(addEventData.getEndyear().trim().length()==0)
				 addFieldError("addEventData.getEndyear", resourceBundle.getString("end.time.year.fld.is.empty.errmsg"));
			 else{
				numbervalidatestatus=validateNumber(addEventData.getEndyear().trim());
				 if("notvalidnumber".equals(numbervalidatestatus))
				 addFieldError("addEventData.getEndyear", resourceBundle.getString("end.time.year.fld.should.be.numeric.errmsg"));  
			 }
        } 
            if("".equals(addEventData.getCity().trim())){
			addFieldError("addEventData.city", resourceBundle.getString("city.is.empty"));			
		   }
		  if("1".equals(addEventData.getCountry())){
			addFieldError("addEventData.country", resourceBundle.getString("country.is.not.selected.errmsg"));			
		}
		if("".equals(addEventData.getName().trim())){
			addFieldError("addEventData.name", resourceBundle.getString("host.name.is.empty.errmsg"));			
		}
		StatusObj obj=null;
		
		if ("".equals(addEventData.getEmail().trim()))
			addFieldError("addEventData.email", resourceBundle.getString("global.email.is.empty.errmsg"));
		else if(!EventBeeValidations.isValidFromEmail(addEventData.getEmail().trim()))
			addFieldError("addEventData.email", resourceBundle.getString("global.invalid.format.for.email.errmsg"));
		else if(addEventData.getEmail().trim().indexOf(';')>-1 ||addEventData.getEmail().trim().indexOf(',')>-1)
			addFieldError("addEventData.email", resourceBundle.getString("only.one.email.is.allowed.errmsg"));
		
		/*if(addEventData.getPowerWithType().equals("ticketing") && addEventData.getNtsEnable().equals("Y") && "".equals(addEventData.getNtsCommission().trim())){
				addFieldError("addEventData.ntsCommission", "Network Ticket Selling Commission is empty");			
			}
			if(addEventData.getPowerWithType().equals("ticketing") && addEventData.getNtsEnable().equals("Y") && addEventData.getNtsCommission().contains("+"))
				addFieldError("addEventData.ntsCommission", "Network Ticket Selling Commission should be numeric");
			else if(addEventData.getPowerWithType().equals("ticketing") && addEventData.getNtsEnable().equals("Y") && !addEventData.getNtsCommission().trim().equals("")){
				
				try {
				Double commission = Double.parseDouble(addEventData.getNtsCommission());
				if (commission < 0 || commission > 100) {
					addFieldError("addEventData.ntsCommission", "Invalid Network Ticket Selling Commission");
				}else if (commission < 5 ) {
					addFieldError("addEventData.ntsCommission", "Network Ticket Selling Commission should be greater than or equal to 5%");
				}
				}catch (Exception e) {
					addFieldError("addEventData.ntsCommission", "Network Ticket Selling Commission should be numeric");
				}
			}*/
		
		//String mgrcardchkstatus=AddEventDB.getMgrEventStaus(mgrId);
		String mgrcardchkstatus="don't askcard";/*For Disable Card ask feature at eventn level we comment above statement
		                                       and hard code mgrcardchkstatus=don't askcard.If you want that logic enable just
		                                       uncomment above statement remove this hardcode statement*/
		                                       
		if("askcard".equalsIgnoreCase(mgrcardchkstatus))
			addFieldError("mgrcardchkstatus","askcard");
		
		if(!getFieldErrors().isEmpty()){
			errorsExists = true;
			return false;
		}
	return true;
	}
    public String validateNumber(String number){
		if (number==null || number.length()==0)return "notvalidnumber";
        for (int i = 0; i < number.length(); i++) {
          if (!Character.isDigit(number.charAt(i)))
          return "notvalidnumber";
        }
		return "validnumber";
	}
    
   public String insertEventListTrack(){
	  AddEventDB.insertEventListTrack(mgrId,addEventData,isRecurring); 
	 return "inserteventlistrack";  
   }
    
    public String listevent(){
    	addEventData.setI18nLang(I18n.getLanguageFromSession());
    	addEventData.setI18nActulaLang(I18n.getActualLangFromSession());
		recurringDates = new ArrayList<Entity>();		
		if(optionsList!=null)
			recurringDates = optionsList;
		else
			optionsList = new ArrayList<Entity>();
		
		boolean status=validateData();	
		if(status){
			if(isRecurring!=null){
				if(isRecurring.equals("true"))
					if(optionsList!=null){Collections.sort(optionsList, new Comparator<Entity>() {
                        
                        @Override
                        public int compare(Entity  date1, Entity  date2)
                        {
                                try{
                       Date dtempa=new Date(date1.getValue().split("-")[0]);
                       Date dtempb=new Date(date2.getValue().split("-")[0]);
                       return  dtempa.compareTo(dtempb);
                                }catch(Exception e){System.out.println("error:::"+e.getMessage());}
                           return 1;
                        }
                    });
						if(optionsList.get(0)!=null){
							
							
							
							log.info("Recurring Event Startdate as per selected dates:"+optionsList.get(0).getKey().toString());
							log.info("Recurring Event Enddate as per selected dates:"+optionsList.get(optionsList.size()-1).getKey().toString());
							
							String[] startdate = optionsList.get(0).getKey().toString().split("/");
							String[] enddate = optionsList.get(optionsList.size()-1).getKey().toString().split("/");
							String[] eventStartDate=startdate[0].split("-");
							String[] eventEndDate=enddate[0].split("-");
							addEventData.setStartmonth(eventStartDate[1]);
							addEventData.setStartday(eventStartDate[2]);
							addEventData.setStartyear(eventStartDate[0]);
							addEventData.setEndmonth(eventEndDate[1]);
							addEventData.setEndday(eventEndDate[2]);
							addEventData.setEndyear(eventEndDate[0]);
						}
					}
			}
			String accounttype=UpgradeEventDB.getAccountType(mgrId);
			if("".equals(eid)){
			HashMap geteventidhm=AddEventDB.getEvtSeqID();
			eid=GenUtil.getUniqueId((String)geteventidhm.get("eventid"));
			AddEventDB.mapEventSeqIdToEncoded((String)geteventidhm.get("eventid"), eid);
			AddEventDB.createEvent(addEventData,eid,mgrId,mytextarea,isRecurring,accounttype);			
			if(isRecurring!=null){
				if(isRecurring.equals("true"))
					AddEventDB.createRecurringEvent(eid,optionsList);
			}
			//EventHelperDB.setUserTheme(eid,mgrId,addEventData.getPowerWithType());
			String premiumType="";
			if("Gold".equals(accounttype)){
				premiumType="premium";
				UpgradeEventDB.updatePremiumLevel(eid,premiumType);
			}else if("Platinum".equals(accounttype)){
				premiumType="featured";
				UpgradeEventDB.updatePremiumLevel(eid,premiumType);
			}else{				
			}
			System.out.println("powertype: "+addEventData.getPowerWithType());
			// inserting eventpagecontent socialnetwork entries into confing for rsvp event. 
			if(addEventData.getPowerWithType().equals("rsvp"))
				EventDB.insertRSVPSocialNetwork(eid);
			System.out.println("16)inserting confirmation stray email");			
			if(!AddEventDB.checkeventid(eid)){
			EventHelper.sendListingConfirmationEmail(mgrId,eid);
			
			System.out.println("16)inserted confirmation stray email");
			AddEventDB.insertEventHitSummary(eid);
			}}else{
				AddEventDB.updateEventData(addEventData, eid,isRecurring,recurringDates,addEventData.getPowerWithType());
			}
			/*
			if(("Yes".equalsIgnoreCase(EventDB.getConfigVal(eid,"event.poweredbyEB","")))){
				try{
				EventHelper.SendEmail(eid,"13579");
				}
				catch(Exception e){
					log.info("Exception in send Email in listing flow:"+e.getMessage());
						
				}
			}*/
			
			
			/*if("ticketing".equals(addEventData.getPowerWithType()))
				return "ticketingeventlisted";
			else
				return "rsvpeventlisted";*/
			JSONObject json=new JSONObject();
			try{
				json.put("powertype",addEventData.getPowerWithType());
				json.put("eventid", eid);
			}catch(Exception e){
				System.out.println("Exception occured in preparing jsondata in listevent :"+eid+""+e.getMessage());
			}
			jsonData=json.toString();
			return "jsondata";
		}
		else{
			subcategory=addEventData.getSubcategory();
			populateListingData();
			String description="";
			String descriptiontype = addEventData.getDescriptiontype();
			if ("wysiwyg".equals(descriptiontype)){	
				description = addEventData.getDescription();
				addEventData.setDescription(description);
				addEventData.setDescriptiontype("wysiwyg");}
			else
			{	description=addEventData.getTextcontent();
			addEventData.setTextcontent(description);
			if("text".equals(descriptiontype))
				{addEventData.setDescriptiontype("text");
				}
				else
				addEventData.setDescriptiontype("html");
			}
			return "fail";
		}		
	}
     
    /*public String getManagerStatus(String mgrid){
    	String mgrstatus=AddEventDB.getMgrEventStaus(mgrid);
    	if("askcard".equals(mgrstatus)){
    		String accounttype=UpgradeEventDB.getAccountType(mgrid);
    		if("basic".equalsIgnoreCase(accounttype)){
			String cardstatus=CardProcessorDB.getManagerCCStatus(mgrid);
			if(!"Y".equalsIgnoreCase(cardstatus))
				return "askcard";
			else return "don't askcard";
        	}else return "don't askcard";
    	}else return "don't askcard";	
			
		}*/

    public String eventListRecurringDates(){
    	List<Entity> recurdates=EditEventDB.getRecurringEventDates(eid);
    	ArrayList recurringdateskeys=new ArrayList();
    	HashMap<String,String> recurringdates=new HashMap<String,String>();
    	for(int i=0;i<recurdates.size();i++){
    		recurringdateskeys.add(recurdates.get(i).getKey());
    		recurringdates.put(recurdates.get(i).getKey(),recurdates.get(i).getValue());
    	}
        JSONObject json=new JSONObject();
		try{
			json.put("recurringdatekeys",recurringdateskeys);
			json.put("recurringdates",recurringdates);
			}catch(Exception e){
			System.out.println("Exception occured in preparing jsondata for recurringdates in listevent :"+eid+""+e.getMessage());
		}
		jsonData=json.toString();
    	return "jsondata";
    }
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
