package com.eventmanage.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.json.JSONObject;




import com.event.beans.EditEventData;
import com.event.dbhelpers.EditEventDB;
import com.event.dbhelpers.EventDB;
import com.event.dbhelpers.EventHelperDB;
import com.event.helpers.DataPopulator;
import com.event.helpers.I18n;
import com.eventbee.beans.Entity;
import com.eventbee.general.EventBeeValidations;
import com.eventbee.general.formatting.EventbeeStrings;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class EditEventAction extends ActionSupport implements Preparable,
		ValidationAware {
	private static final long serialVersionUID = -3773514102198232135L;
	private static final Logger log = Logger.getLogger(EditEventAction.class);
	private String eid = "";
	private EditEventData editEventData;
	private ArrayList listingtype = new ArrayList();
	private ArrayList listingprivacy = new ArrayList();
	private ArrayList descriptiontype = new ArrayList();
	private ArrayList categorytype = new ArrayList();
	private List<Entity> countrylist = new ArrayList();
	private ArrayList timezones = new ArrayList();
	private List<Entity> communities = new ArrayList<Entity>();
	private String country;
	private String category;
	private String subcategory;
	private String state;
	private String region;
	private String mytextarea;
	private List<Entity> optionsList;
	private String sthours="";
	private String endhours="";
	private String startampm="";
	private String endampm="";
	private String seeRecurring="";
	private ArrayList<Entity> hours=new ArrayList<Entity>();
	private ArrayList<Entity> minutes=new ArrayList<Entity>();
	private boolean errorsExists;
    private String jsonData="";
    private String msgType="editeventpage";
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

	public String getStartampm() {
		return startampm;
	}

	public String getEndampm() {
		return endampm;
	}

	public void setEndampm(String endampm) {
		this.endampm = endampm;
	}

	public void setStartampm(String startampm) {
		this.startampm = startampm;
	}	

	public String getEndhours() {
		return endhours;
	}

	public void setEndhours(String endhours) {
		this.endhours = endhours;
	}

	public String getSthours() {
		return sthours;
	}

	public void setSthours(String sthours) {
		this.sthours = sthours;
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

	public String getMytextarea() {
		return mytextarea;
	}

	public void setMytextarea(String mytextarea) {
		this.mytextarea = mytextarea;
	}

	public List<Entity> getCommunities() {
		return communities;
	}

	public void setCommunities(List<Entity> communities) {
		this.communities = communities;
	}

	public String getState() {
		return state;
	}

	public String getSeeRecurring() {
		return seeRecurring;
	}

	public void setSeeRecurring(String seeRecurring) {
		this.seeRecurring = seeRecurring;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList getCategorytype() {
		return categorytype;
	}

	public void setCategorytype(ArrayList categorytype) {
		this.categorytype = categorytype;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Entity> getCountrylist() {
		return countrylist;
	}

	public void setCountrylist(List countrylist) {
		this.countrylist = countrylist;
	}

	public void setListingtype(ArrayList listingtype) {
		this.listingtype = listingtype;
	}

	public void setListingprivacy(ArrayList listingprivacy) {
		this.listingprivacy = listingprivacy;
	}

	public void setDescriptiontype(ArrayList descriptiontype) {
		this.descriptiontype = descriptiontype;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public EditEventData getEditEventData() {
		return editEventData;
	}

	public void setEditEventData(EditEventData editEventData) {
		this.editEventData = editEventData;
	}

	public void populateListingTypes() {
		listingtype.clear();
		listingtype.add(new Entity("event", "&nbspEvent"));
		listingtype.add(new Entity("class", "&nbspClass"));
	}

	public void populateListingPrivacyTypes() {
		listingprivacy.clear();
		listingprivacy.add(new Entity("PBL", I18n.getString("event.public.lbl")));
		listingprivacy.add(new Entity("PVT", I18n.getString("event.private.lbl")));
	}

	public void populateDescriptionTypes() {
		descriptiontype.clear();
		descriptiontype.add(new Entity("text", "Text"));
		descriptiontype.add(new Entity("html", "HTML"));
		descriptiontype.add(new Entity("wysiwyg", "WYSIWYG Editor"));
	}

	public void populateCountrylist() {
		countrylist = EventDB.getCountries();
	}

	public void prepare() {
		editEventData = new EditEventData();
	}

	public void populateEventInfo() {
		editEventData = EditEventDB.getEventinfo(eid);
		country = editEventData.getCountry();
		category = editEventData.getCategory();
		subcategory = editEventData.getSubcategory();
		state = editEventData.getState();
		region = editEventData.getRegion();
		seeRecurring=new Boolean(EventDB.isRecurringEvent(eid)).toString();
		editEventData.setRecurringcheck(seeRecurring);
		
	}

	public void populateCategoryList() {
		categorytype = DataPopulator.populateCategoryList();
	}

	public void populateTimeZones() {
		timezones = DataPopulator.populateTimeZones();
	}

	public void populateData() {
		
		populateListingTypes();
		populateListingPrivacyTypes();
		populateDescriptionTypes();
		populateCountrylist();
		populateCategoryList();
		populateTimeZones();
		String AM_PM=editEventData.getStartampm();
		String disphr=editEventData.getStartHour();
		int hr=1;
		try{
			hr=Integer.parseInt(disphr.trim());
		}catch(Exception e){hr=1;}
		if(hr==12) AM_PM="PM";
		if(hr==0){
			System.out.println("we are in populate data if(hr==0)");
			hr=12;
			AM_PM="AM";
		}
		if(hr>12){
			System.out.println("we are in populate data if(hr>12)");
			hr=hr-12;
			AM_PM="PM";
			if(hr<10)
				editEventData.setStartHour("0"+hr);
			else
				editEventData.setStartHour(""+hr);
			}
		editEventData.setStartampm(AM_PM);
		
		disphr=""+hr;
		if(disphr.length()<2)
			disphr="0"+disphr;
		sthours=EventbeeStrings.getHH12Html(disphr,"editEventData.startHour");
		editEventData.setHours(sthours);
		
		startampm=EventbeeStrings.getTimeMHtml(AM_PM,"editEventData.startampm");
		//editEventData.setStartampm(startampm);
		
		hours=DataPopulator.getHours();
		minutes=DataPopulator.getMinutes();
		AM_PM=editEventData.getEndampm();
		disphr=editEventData.getEndHour();
		try{
			hr=Integer.parseInt(disphr.trim());
		}catch(Exception e){hr=1;}
		if(hr==12) AM_PM="PM";
		if(hr==0){
			System.out.println("we are in if(hr==0)second loop"+hr);
			hr=12;
			AM_PM="AM";
		}

		if(hr>12){
			hr=hr-12;
			AM_PM="PM";
			if(hr<10)
				editEventData.setEndHour("0"+hr);
			else
				editEventData.setEndHour(""+hr);
			}
		editEventData.setEndampm(AM_PM);
		
		disphr=""+hr;
		if(disphr.length()<2)
			disphr="0"+disphr;
		endhours=EventbeeStrings.getHH12Html(disphr,"editEventData.endHour");
		editEventData.setEndhours(endhours);
		endampm=EventbeeStrings.getTimeMHtml(AM_PM,"editEventData.endampm");
		//editEventData.setEndampm(endampm);
		
	}

	public boolean validateData() {
		String numbervalidatestatus="";
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		if ("".equals(editEventData.getEventTitle().trim())) {
			addFieldError("editEventData.eventTitle", resourceBundle.getString("event.title.is.empty.errmsg"));
		}
		/*if ("1".equals(editEventData.getCategory())) {
			addFieldError("editEventData.category", "Category is not selected");
		}*/
		
		if ("".equals(editEventData.getSubcategory())) {
			// addFieldError("editEventData.subcategory", "* Subcategory is not
			// selected");
		}
		if ("".equals(editEventData.getDescription())) {
			// addFieldError("editEventData.description", "* Description is
			// empty");
		}
		if ("1".equals(editEventData.getTimezones())) {
			addFieldError("editEventData.timezones",
					"Time Zone is not selected");
		}
		if("on".equalsIgnoreCase(seeRecurring))
			editEventData.setRecurringcheck("true");
		else
			editEventData.setRecurringcheck("false");
		if(!"true".equals(editEventData.getRecurringcheck())){
		if(editEventData.getStartmonth().trim().length()==0)
			 addFieldError("editEventData.getStartmonth", resourceBundle.getString("start.time.month.fld.is.empty.errmsg"));
			 else{
			  numbervalidatestatus=validateNumber(editEventData.getStartmonth().trim());
			    if("notvalidnumber".equals(numbervalidatestatus))
			     addFieldError("editEventData.getStartmonth", resourceBundle.getString("start.time.month.fld.should.be.numeric.errmsg")); 
			 }
			 if(editEventData.getStartday().trim().length()==0)
				  addFieldError("editEventData.getStartday", resourceBundle.getString("start.time.date.fld.is.empty.errmsg")); 
			 else{
				 numbervalidatestatus=validateNumber(editEventData.getStartday().trim());
				   if("notvalidnumber".equals(numbervalidatestatus))
				   addFieldError("editEventData.getStartday", resourceBundle.getString("start.time.date.fld.should.be.numeric.errmsg"));
			 }
			 if(editEventData.getStartyear().trim().length()==0)
				  addFieldError("editEventData.getStartyear", resourceBundle.getString("start.time.year.fld.is.empty.errmsg"));
			 else{
				 numbervalidatestatus=validateNumber(editEventData.getStartyear().trim());
				   if("notvalidnumber".equals(numbervalidatestatus))
				   addFieldError("editEventData.getStartyear", resourceBundle.getString("start.time.year.fld.should.be.numeric.errmsg"));
			 }
			 if(editEventData.getEndmonth().trim().length()==0)
				  addFieldError("editEventData.getEndmonth", resourceBundle.getString("end.time.month.fld.is.empty.errmsg"));
			 else{
				 numbervalidatestatus=validateNumber(editEventData.getEndmonth().trim());
				   if("notvalidnumber".equals(numbervalidatestatus))
				   addFieldError("editEventData.getEndmonth", resourceBundle.getString("end.time.month.fld.should.be.numeric.errmsg")); 
			 }
			 if(editEventData.getEndday().trim().length()==0)
				  addFieldError("editEventData.getEndday", resourceBundle.getString("end.time.date.fld.is.empty.errmsg"));
			 else{
			   numbervalidatestatus=validateNumber(editEventData.getEndday().trim());
				  if("notvalidnumber".equals(numbervalidatestatus))
				  addFieldError("editEventData.getEndday", resourceBundle.getString("end.time.date.fld.should.be.numeric.errmsg")); 
			 }
			 if(editEventData.getEndyear().trim().length()==0)
				 addFieldError("editEventData.getEndyear", resourceBundle.getString("end.time.year.fld.is.empty.errmsg"));
			 else{
				numbervalidatestatus=validateNumber(editEventData.getEndyear().trim());
				 if("notvalidnumber".equals(numbervalidatestatus))
				 addFieldError("editEventData.getEndyear", resourceBundle.getString("end.time.year.fld.should.be.numeric.errmsg"));  
			 }
		}	 
		if ("".equals(editEventData.getName().trim())) {
			addFieldError("editEventData.name", resourceBundle.getString("host.name.is.empty.errmsg"));
		}
				
		if ("".equals(editEventData.getEmail().trim()))
			addFieldError("addEventData.email", resourceBundle.getString("global.email.is.empty.errmsg"));
		else if(!EventBeeValidations.isValidFromEmail(editEventData.getEmail().trim()))
			addFieldError("addEventData.email", resourceBundle.getString("global.invalid.format.for.email.errmsg" ));
		if ("".equals(editEventData.getCity().trim())) {
			addFieldError("editEventData.city", resourceBundle.getString("city.is.empty"));
		}
		if ("1".equals(editEventData.getCountry())) {
			addFieldError("editEventData.country", resourceBundle.getString("country.is.not.selected.errmsg"));
		}
		//String mgrcardchkstatus=AddEventDB.getMgrEventStaus(editEventData.getMgrId());
		String mgrcardchkstatus="don't askcard";/*For Disable Card ask feature at eventn level we comment above statement
        and hard code mgrcardchkstatus=don't askcard.If you want that logic enable just
        uncomment above statement remove this hardcode statement*/

		if("askcard".equalsIgnoreCase(mgrcardchkstatus))
			addFieldError("mgrcardchkstatus","askcard");
		if (!getFieldErrors().isEmpty()) {
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

	public String execute() {
		
		populateEventInfo();
		populateData();
		
		editEventData.setName(EventDB.getConfigVal(eid, "event.hostname", ""));
		editEventData.setTimezones(EventDB.getConfigVal(eid, "event.timezone",""));
		editEventData.setRecurringDates(EditEventDB.getRecurringEventDates(eid));
		return "success";
	}

	public String update() {
		boolean status = validateData();
		if (optionsList != null) {			
			editEventData.setRecurringDates(optionsList);
			if(optionsList!=null){
				if(optionsList.get(0)!=null){
					log.info("Recurring Event Startdate as per selected dates:"+optionsList.get(0).getKey().toString());
					log.info("Recurring Event Enddate as per selected dates:"+optionsList.get(optionsList.size()-1).getKey().toString());
					/*String[] startdate = optionsList.get(0).getKey().toString().split("/");
					String[] enddate = optionsList.get(optionsList.size()-1).getKey().toString().split("/");
					editEventData.setStartmonth(startdate[0]);
					editEventData.setStartday(startdate[1]);
					editEventData.setStartyear(startdate[2]);
					
					editEventData.setEndmonth(enddate[0]);
					editEventData.setEndday(enddate[1]);
					editEventData.setEndyear(enddate[2]);*/
					String[] startdate = optionsList.get(0).getKey().toString().split("/");
					String[] enddate = optionsList.get(optionsList.size()-1).getKey().toString().split("/");
					String[] eventStartDate=startdate[0].split("-");
					String[] eventEndDate=enddate[0].split("-");
					editEventData.setStartmonth(eventStartDate[1]);
					editEventData.setStartday(eventStartDate[2]);
					editEventData.setStartyear(eventStartDate[0]);
					editEventData.setEndmonth(eventEndDate[1]);
					editEventData.setEndday(eventEndDate[2]);
					editEventData.setEndyear(eventEndDate[0]);
				}
			}
		}
		
		if (status) {
			EditEventDB.updateEventData(editEventData, eid, mytextarea);
			JSONObject json=new JSONObject();
			try{
				json.put("status","success");
			}catch(Exception e){}
			jsonData=json.toString();
			EventHelperDB.removeGLobalEventCache(eid, "remove", "eventinfo");
			return "updated";
		} else {
			populateData();
			subcategory = editEventData.getSubcategory();
			String description = editEventData.getDescription();
			String descriptiontype = editEventData.getDescriptiontype();
			//if ("wysiwyg".equals(descriptiontype))	description = mytextarea;
			editEventData.setDescription(description);
			//editEventData.setName(EventDB.getConfigVal(eid, "event.hostname",""));
			//editEventData.setTimezones(EventDB.getConfigVal(eid,"event.timezone", ""));
			if (optionsList != null)		
				editEventData.setRecurringDates(optionsList);
			return "fail";
		}
	}

	public ArrayList getListingtype() {
		return listingtype;
	}

	public ArrayList getListingprivacy() {
		return listingprivacy;
	}

	public ArrayList getDescriptiontype() {
		return descriptiontype;
	}

	public ArrayList getTimezones() {
		return timezones;
	}

	public void setTimezones(ArrayList timezones) {
		this.timezones = timezones;
	}

	public boolean isErrorsExists() {
		return errorsExists;
	}

	public void setErrorsExists(boolean errorsExists) {
		this.errorsExists = errorsExists;
	}

	public List<Entity> getOptionsList() {
		return optionsList;
	}

	public void setOptionsList(List<Entity> optionsList) {
		this.optionsList = optionsList;
	}
}
