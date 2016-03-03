package com.eventbee.general.formatting;
import java.util.*;
import com.eventbee.general.DbUtil;
import com.eventbee.general.EbeeConstantsF;
public class EventbeeStrings  {
public static String Email_Allowed_Chars[]={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
		"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","_","-"};

public static String Allowed_Chars_for_wiki_filename[]={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
		"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","_"};

public static String[] beeportCategoryCodes=new String[]{"Home","Tasks","Contacts","Community","Events","Email Marketing","Surveys & Polls","Collaboration","Integration","Storage","Reports"};
public static String[] beeportCategoryValues=new String[]{"portal","Tasks","Contacts","unit","events","emailmarketing","surveys","collaboration","integration","storage","reports"};
public static String[] beeportCategoryURLS=new String[]{"/manager/portalhome/myhome/myhome-portal",
		 	"/manager/customtables/tabledata?.tableid=2&conf=Tasks&fromhome=y","/manager/customtables/tabledata?.tableid=1&conf=Contacts&fromhome=y",
		 	"/manager/portalhome/myhome/unitpage","/manager/portalhome/myhome/myhome-events",
		 	"/manager/portalhome/myhome/myhome-emailmarketing","/manager/portalhome/myhome/myhome-surveys",
			"/manager/portalhome/myhome/myhome-collaboration","/manager/portalhome/myhome/myhome-integration",
			"/manager/portalhome/myhome/myhome-storage","/manager/portalhome/myhome/myhome-reports"};


public static String[] ebeeCategoryCodes=new String[]{"My Home","My Profile","My Page","My Preferences","My Photos","My  Alerts","My Messages","My Network","My Guestbook","My "+getDisplayName(EbeeConstantsF.get("club.label","Bee Hive"),"Beehive")+"s","My  Events","My Classifieds","My Invitations","My Surveys","Logout"};
public static String[] ebeeCategoryValues=new String[]{"portal","profile",   "page",   "preferences",   "photos",   "alerts",    "messages",   "network",   "guestbook",   "clubs","events","postings","invitation","reports","logout"};
public static String[] portalCategoryValues=new String[]{"portal","profile","page","preferences","messages","logout"};

// Predefined PATTERNS,FOLDERS here   NOTE: This String array must contain only small values
public static String[] DEFAULTS_CODES=new String[]{"beeport","eventbee","desihub","manager","portal","home","club","zone","member","event","sponsors",
	"activateaccountmember","admin","ads","alerts","classifieds","createevent","customconfig","customtables","CVS","directory","discussionforums",
	"dynamiccontent","editevent","editprofiles","maillistbeelet","myevents","noticeboard","photos","portalhome","resources","search","sms","stylesheets",
	"attendeesignup","auth","clubmembersignnp","clubpayment","contentbeelet","editattendeeprofile","editeventprice","eventregister","guestbook","hub",
	"invitation","networking","nuser","pagecontent","printdetails","sponsorship","survey",
	"accountupgrade","activateaccount","agendas","attendeelist","beereach","billing","bulkregistration",
	"camp","campaign","clone","clubs","configurelnf","contentbeelet","coupon","editeventprice","eventadd",
	"eventPageLNF","eventupgrade","listreport","lists","membermgmt","mgrlinks","opportunity","promosales",
	 "proxysignup","rolemanagement","scheduling","signup","unitmanagement"};

static String[] USStateCodes=new String[]{"","AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","HI","IA","ID",
"IL","IN","KS","KY","LA","MA","MD","ME","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ",
"NM","NV","NY","OH","OK","OR","PA","RI","SC","SD","TN","TX","UT","VA","VT","WA","WI",
"WV","WY"};


static String[] USStateNameValues=new String[]{"","Alaska","Alabama","Arkansas","Arizona","California","Colorado",
"Connecticut","District of Columbia","Delaware","Florida","Georgia","Hawaii","Iowa",
"Idaho","Illinois","Indiana","Kansas","Kentucky","Louisiana","Massachusetts","Maryland","Maine","Michigan",
"Minnesota","Missouri","Mississippi","Montana","North Carolina","North Dakota","Nebraska","New Hampshire",
"New Jersey","New Mexico","Nevada","New York","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina",
"South Dakota","Tennessee","Texas","Utah","Virginia","Vermont","Washington","Wisconsin","West Virginia","Wyoming"};

static String[] USStateNames=new String[]{"-- Select State --","Alaska","Alabama","Arkansas","Arizona","California","Colorado",
"Connecticut","District of Columbia","Delaware","Florida","Georgia","Hawaii","Iowa",
"Idaho","Illinois","Indiana","Kansas","Kentucky","Louisiana","Massachusetts","Maryland","Maine","Michigan",
"Minnesota","Missouri","Mississippi","Montana","North Carolina","North Dakota","Nebraska","New Hampshire",
"New Jersey","New Mexico","Nevada","New York","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina",
"South Dakota","Tennessee","Texas","Utah","Virginia","Vermont","Washington","Wisconsin","West Virginia","Wyoming"};


public static String[] NUMBERS=new String[]{"One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten",
			"Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Ninteen",
			"Twenty"};



public static String [] INDIA_STATES_NAME={"Andaman and Nicobar Islands","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chandigarh",
		"Chattisgarh","Dadra and Nagar Haveli","Daman and Diu","Delhi","Goa","Gujarat","Haryana","Himachal Pradesh",
		"Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Lakshadweep","Madhya Pradesh","Maharashtra","Manipur",
		"Meghalaya","Mizoram","Nagaland","Orissa","Pondicherry","Punjab","Rajasthan","Sikkim","Tamil Nadu","Tripura",
"Uttarakhand","Uttaranchal","Uttar Pradesh","West Bengal"};

public static String [] INDIA_STATES_CODE={"Andaman and Nicobar Islands","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chandigarh",
		"Chattisgarh","Dadra and Nagar Haveli","Daman and Diu","Delhi","Goa","Gujarat","Haryana","Himachal Pradesh",
		"Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Lakshadweep","Madhya Pradesh","Maharashtra","Manipur",
		"Meghalaya","Mizoram","Nagaland","Orissa","Pondicherry","Punjab","Rajasthan","Sikkim","Tamil Nadu","Tripura",
"Uttarakhand","Uttaranchal","Uttar Pradesh","West Bengal"};

public static String [] US_STATES_NAME={"Alaska","Alabama","Arkansas","Arizona","California","Colorado",
"Connecticut","District of Columbia","Delaware","Florida","Georgia","Hawaii","Iowa",
"Idaho","Illinois","Indiana","Kansas","Kentucky","Louisiana","Massachusetts","Maryland","Maine","Michigan",
"Minnesota","Missouri","Mississippi","Montana","North Carolina","North Dakota","Nebraska","New Hampshire",
"New Jersey","New Mexico","Nevada","New York","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina",
"South Dakota","Tennessee","Texas","Utah","Virginia","Vermont","Washington","Wisconsin","West Virginia","Wyoming"};

public static String [] US_STATES_CODE={"AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","HI","IA","ID",
				"IL","IN","KS","KY","LA","MA","MD","ME","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ",
				"NM","NV","NY","OH","OK","OR","PA","RI","SC","SD","TN","TX","UT","VA","VT","WA","WI",
"WV","WY"};
public static String [] UK_STATES_NAME={"East Midlands","Eastern","London","North East","North West","South East","South West","West Midlands","Yorkshire and the Humber"};
public static String [] UK_STATES_CODE={"East Midlands","Eastern","London","North East","North West","South East","South West","West Midlands","Yorkshire and the Humber"};




static String[] CountryCodes=new String[]{"USA"};
static String[] CountryNames=new String[]{"USA"};

//static String[] AllCountryCodes=new String[]{"USA"};
//static String[] AllCountryNames=new String[]{"USA"};

/*static String[] AllCountryCodes=new String[]{"India","UK","USA","Afghanistan","Albania","Algeria","American Samoa","Andorra","Angola","Anguilla","Antarctica",
"Antigua & Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas",
"Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia & Herzegovina",
"Botswana","Bouvet Island","Brazil","Brunei Darussalam","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada",
"Cape Verde","Cayman Islands","Central African Republic","Chad","Chile","China","Christmas Island","Colombia","Comoros",
"Congo","Congo (DROC)","Cook Islands","Costa Rica","Cote D'Ivoire","Croatia","Cuba","Cyprus","Czech Republic","Denmark",
"Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Estonia","Equatorial Guinea","Eritrea",
"Ethiopia","Falkland Islands","Faroe Islands","Fiji","Finland","France","French Guiana","French Polynesia","Gabon","Gambia",
"Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guadaloupe","Guam","Guatemala","Guinea","Guinea-Bissau",
"Guyana","Haiti","Vatican City","Honduras","Hong Kong","Hungary","Iceland","Indonesia","Iran","Iraq","Ireland","Israel",
"Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kiribati","Korea, North","Korea, Republic of","Kuwait","Kyrgyzstan",
"Laos","Latvia","Lebanon","Lesotho","Liberia"," Libya","Liechtenstein","Lithuania","Luxembourg","Macao","Macedonia","Madagascar",
"Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Martinique","Mauritania","Mauritius","Mayotte","Mexico","Micronesia",
"Moldova, Republic of","Monaco"," Mongolia","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nauru","Nepal","Netherlands",
"Netherlands Antilles",
"New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","Niue","Norfolk Island","Norway","Oman","Pakistan",
"Palau","Palestine","Panama",
"Papua New Guinea","Paraguay","Peru","Philippines","Pitcairn","Poland","Portugal","Puerto Rico","Qatar","Reunion","Romania",
"Russian Federation","Rwanda","Saint Helena","Saint Kitts & Nevis","Saint Lucia","Saint Vincent & the Grenadines","Samoa",
"San Marino","Sao Tome & Principe","Saudi Arabia","Senegal","Serbia & Montenegro","Seychelles","Sierra Leone","Singapore",
"Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","Spain","Sri Lanka","Sudan"," Suriname","Svalbard & Jan Mayen",
"Swaziland","Sweden","Switzerland","Syrian Arab Republic","Taiwan","Tajikistan","Tanzania","Thailand","Timor-Leste","Togo",
"Tokelau","Tonga","Trinidad & Tobago","Tunisia","Turkey","Turkmenistan","Turks & Caicos Islands","Tuvalu","Uganda","Ukraine",
"United Arab Emirates","Uruguay","Uzbekistan","Vanuatu","Venezuela","Viet Nam","Virgin Islands, British","Virgin Islands, U.S.",
"Wallis & Futuna","Western Sahara","Yemen","Zambia","Zimbabwe"};*/
static String[] AllCountryCodes=null;

static String[] AllCountryNames=null;


static {
	populateCountriesInfo();
}

static String[] CategoryCodes=new String[]{"Technology","Entrepreneur","Business","Corporate","Career","Sports","Health","Entertainment","Social","Community","Non-profit","Other"};
static String[] CategoryNames=new String[]{"Technology","Entrepreneur","Business","Corporate","Career","Sports","Health","Entertainment","Social","Community","Non-profit","Other"};

static String[] SubCategoryCodes=new String[]{"Activity","Baseball","Basketball","Breakfast","Camp","Coffee","Conference","Cricket","Dinner","Fair","Football","Fund-Raising",
	"Job Fair","Lunch","Meeting","Mixer","Movie","Outdoors","Running","Show","Soccer","Swimming","Tennis","Trade Show","Training","Travel","Webcast","Other"};

//static String[] CategoryZoneCodes=new String[]{"association","business","club","company","group","professional","other"};
//static String[] CategoryZoneNames=new String[]{"Association","Business","Club","Company","Group","Professional","Other"};

//static String[] CategoryZoneCodes=new String[]{"association","business","club","group"};
//static String[] CategoryZoneNames=new String[]{"Association","Business","Club","Group"};

static String[] CategoryZoneNames=new String [] {"Corporation","Association","Non-Profit","Other"};
static String[] CategoryZoneCodes=new String [] {"Corporation","Association","Non-Profit","Other"};

static String[] EducationNames=new String[]{"Bachelors Degree"," Masters Degree"};

static String[] EducationCodes=new String[]{"Bachelors Degree"," Masters Degree"};

public static String[] months=new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

public static String[] monthvals=new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};

public static String[] days=new String[]{"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};

static String[] hh24=new String[]{"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};

static String[] hh12=new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};

static String[] timeM=new String[]{"AM","PM"};

String[] eventstatus=new String[]{"Active","Hold","Close","Cancel"};
 String[] eventstatusvalues=new String[]{"ACTIVE","HOLD","CLOSE","CANCEL"};

public static String[] cardtypes=new String[]{"","Visa","Mastercard","Amex","Discover"};
public static String[] carddisplaytypes=new String[]{"-- Select Card Type --","Visa","Mastercard","Amex","Discover"};


public static String graphColors[]=	{"#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#00FFFF", "#FF00FF",
		"#CCFF66", "#66FF66", "#6666CC", "#FF9933", "#990099"};
public static String [] classifiedtypes={"Activity Partner", "Coach", "Housing", "Instructor","Professional", "Rideshare","Services", "Travel","For Sale", "Wanted", "Other"};
public static String [] volunteertypes={"Arts","Children","Community","Education","Events","Health","Housing","Immigration","Legal","Media","Religion","Technology","Seniors","Sports","Youth","Other"};
public static String [] desiclassifiedtypes={"Jobs", "Housing", "Autos", "Real Estate", "Travel", "Services","For Sale", "Wanted","Matrimonial", "Announcements", "Other"};
public static String [] desivolunteertypes={"Arts","Children","Community","Education","Events","Health","Housing","Immigration","Legal","Media","Religion","Technology","Seniors","Sports","Youth","Other"};

public static String [] movies={"Hindi","Telugu","Tamil","Punjabi","Kannada","Malayalam","Marati","English","Other"};
public static String [] travel={"Business","Recreation","Other"};
public static String [] reviews={"Phone cards","Books","Doctors","Restaurents","Recreation","Stores","Websites","Airlines","Other"};
public static String [] recipies={"Vegetarian","Non-vegetarian","Other"};
public static String [] deals={"Electronics","Travel","Other"};
public static String [] humor={"India","Foreign"};
public static String [] experiences={"India","Abroad"};
//public static String [] logtypes={"Movies","Travel","Recipes","Reviews & Recommendations","Deals","Humor","Experiences","Music","Articles","Immigration","Interviews","Other"};
//public static String [] logtypesval={"Movies","Travel","Recipes","Reviews","Deals","Humor","Experiences","Music","Articles","Immigration","Interviews","Other"};
//public static String [] logtypes={"Articles","Deals","Experiences","Humor","Immigration","Interviews","Movies","Music","Recipes","Reviews & Recommendations","Travel","Other"};
//public static String [] logtypesval={"Articles","Deals","Experiences","Humor","Immigration","Interviews","Movies","Music","Recipes","Reviews","Travel","Other"};
public static String [] logtypes={"Airlines","Books","Deals","Doctors","Media","Movies","Music","Phone Cards","Recipes","Recreation","Restaurants","Stores","Travel","Websites","Other"};
public static String [] logtypesval={"Airlines","Books","Deals","Doctors","Media","Movies","Music","Phone Cards","Recipes","Recreation","Restaurants","Stores","Travel","Websites","Other"};

//public static String [] photologvalues={"Nature","Travel","Places","Movies","Events","News","Sports","People"};
public static String [] photologvalues={"Nature","Travel","Places","Movies","Events","News","Sports"};

//public static String [] photologtypes={"Nature","Travel","Places","Movies","Events","News","Sports","People"};
public static String [] photologtypes={"Nature","Travel","Places","Movies","Events","News","Sports"};
public static String [] ebeephotologvalues={"Event","Activity","Class"};
public static String [] ebeephotologtypes={"Event","Activity","Class"};
public static String[] gePhototLogTypes(){
String [] logtypes=new String [0];
String appname=EbeeConstantsF.get("application.name","Eventbee");
if("Desihub".equals(appname.trim()))
logtypes=photologtypes;
else
logtypes=ebeephotologtypes;
return logtypes;
}

public static String[] gePhototLogValues(){
String [] logtypes=new String [0];
String appname=EbeeConstantsF.get("application.name","Eventbee");
if("Desihub".equals(appname.trim()))
logtypes=photologvalues;
else
logtypes=ebeephotologvalues;
return logtypes;
}
public static String[] getLogTypes(){
return logtypes;
}
public static String[] getLogTypeVals(){
return logtypesval;
}
public static String[] getLogCategory(String type){
	String [] cat=null;
	if("Movies".equalsIgnoreCase(type))
	return movies;
	else if("Travel".equalsIgnoreCase(type))
	return travel;
	else if("Recipes".equalsIgnoreCase(type))
	return recipies;
	else if("Reviews".equalsIgnoreCase(type))
	return reviews;
	else if("Deals".equalsIgnoreCase(type))
	return deals;
	else if("Humor".equalsIgnoreCase(type))
	return humor;
	else if("Experiences".equalsIgnoreCase(type))
	return experiences;
	else
	return cat;
}

public static String[] getClassifiedTypesForDesi(){
	  return desiclassifiedtypes;
  }
  public static String[] getVolunteerTypesForDesi(){
	  return desivolunteertypes;
  }
  public static String[] getClassifiedTypes(){
	  return classifiedtypes;
  }
  public static String[] getVolunteerTypes(){
	  return volunteertypes;
  }
  public static String[] getRequiredPurposeTypes(String purpose){
  String [] types=new String[]{};
  if("classified".equals(purpose))
	types=getClassifiedTypes();
  if("volunteer".equals(purpose))
	types=getVolunteerTypes();
    return types;
  }
  public static String[] getRequiredPurposeTypesForDesihub(String purpose){
  String [] types=new String[]{};
  if("classified".equals(purpose))
	types=getClassifiedTypesForDesi();
  if("volunteer".equals(purpose))
	types=getVolunteerTypesForDesi();
    return types;
  }
  public static String[] getRequiredPurposeTypes(String purpose,String appname){
  if("Desihub".equalsIgnoreCase(appname))
		return getRequiredPurposeTypesForDesihub(purpose);
  else
		return getRequiredPurposeTypes(purpose);
  }

public static String getStateName(String state_code){

	if(state_code==null) return "";
	String state_name=state_code;
	for(int i=0;i<USStateCodes.length;i++){
		if(state_code.equals(USStateCodes[i])){
			state_name=getStateName(i);
			break;
		}
	}
	return state_name;
}
public static String getStateName(int i){
	if(i<USStateNameValues.length)
		return USStateNameValues[i];
	else
		return "";
}

public static String getDayHtml(String toselect, String nameofsel,
		String firstdisp,  String firstval){
	return WriteSelectHTML.getSelectHtml(days, days, nameofsel,
		toselect, firstdisp, firstval);
}
public static String getDayHtml(String toselect,String nameofsel){
	return getDayHtml(toselect, nameofsel, null, null);
}
public static String getDayHtml(int toselect, String nameofsel,
		String firstdisp,  String firstval){
	return getDayHtml(days[toselect-1], nameofsel, firstdisp, firstval);
}
public static String getDayHtml(int toselect,String nameofsel){
	return getDayHtml(toselect, nameofsel, null, null);
}

public static String getMonthHtml(String toselect, String nameofsel,
		String firstdisp,  String firstval){
	return WriteSelectHTML.getSelectHtml(months, monthvals, nameofsel,
		toselect, firstdisp, firstval);
}
public static String getMonthHtml(String toselect,String nameofsel){
	return getMonthHtml(toselect, nameofsel, null, null);
}
public static String getMonthHtml(int toselect, String nameofsel,
		String firstdisp,  String firstval){
	return getMonthHtml(monthvals[toselect], nameofsel, firstdisp, firstval);
}
public static String getMonthHtml(int toselect,String nameofsel){
	return getMonthHtml(toselect, nameofsel, null, null);
}

public static String getYearHtml(int startyear, int noofyears, int toselect, String nameofsel,
		String firstdisp, String firstval){
	String[] years=new String[noofyears];
	if(noofyears>0){
		for(int i=0;i<noofyears;i++){
			years[i]=""+(startyear+i);
		}
	}else{
		for(int i=0;i<-1*noofyears;i++){
			years[i]=""+(startyear-i);
		}
	}
	return WriteSelectHTML.getSelectHtml(years, years, nameofsel,
		""+toselect, firstdisp, firstval);
}
public static String getYearHtml(int startyear,int noofyears,int toselect,String nameofsel){
	return getYearHtml(startyear, noofyears, toselect, nameofsel, null, null);
}

public static String getHH24Html(String toselect, String nameofsel,
		String firstdisp,  String firstval){
	return WriteSelectHTML.getSelectHtml(hh24, hh24, nameofsel,
		toselect, firstdisp, firstval);
}
public static String getHH24Html(String toselect,String nameofsel){
	return getHH24Html(toselect, nameofsel, null, null);
}

public static String getHH12Html(String toselect, String nameofsel,
		String firstdisp,  String firstval){
	return WriteSelectHTML.getSelectHtml(hh12, hh12, nameofsel,
		toselect, firstdisp, firstval);
}
public static String getHH12Html(String toselect,String nameofsel){
	return getHH12Html(toselect, nameofsel, null, null);
}

public static String getMinuteHtml(int startMinute, int interval, int steps, String toselect, 		String nameofsel, String firstdisp, String firstval){
	String[] minutes=new String[steps];
	for(int i=0;i<steps;i++){
		minutes[i]=""+(startMinute+(i*interval));
		if(minutes[i].length()==1) minutes[i]="0"+minutes[i];
	}
	return WriteSelectHTML.getSelectHtml(minutes, minutes, nameofsel,
		toselect, firstdisp, firstval);
}
public static String getMinuteHtml(int startMinute, int interval, int steps, String toselect, 		String nameofsel){
	return getMinuteHtml(startMinute, interval, steps, toselect, nameofsel, null, null);
}

public static String getTimeMHtml(String toselect, String nameofsel,
		String firstdisp,  String firstval){
	return WriteSelectHTML.getSelectHtml(timeM, timeM, nameofsel,
		toselect, firstdisp, firstval);
}
public static String getTimeMHtml(String toselect,String nameofsel){
	return getTimeMHtml(toselect, nameofsel, null, null);
}

public static String[] getEducationCodes(){
	return EducationCodes;
}
public static String[] getEducationNames(){
	return EducationNames;
}

public static String[] getCategoryCodes(){
	return CategoryCodes;
}
public static String[] getCategoryNames(){
	return CategoryNames;
}
public static String[] getSubCategoryNames(){
	return SubCategoryCodes;
}
public static String[] getCategoryZoneCodes(){
	return CategoryZoneCodes;
}

public static String[] getCategoryZoneNames(){
	return CategoryZoneNames;
}

public static String[] getCountryCodes(){
	//return CountryCodes;
	return AllCountryCodes;
}
public static String[] getCountryNames(){
	//return CountryNames;
	return AllCountryNames;
}


public static String[] getAllCountryCodes(){
	return AllCountryCodes;
}
public static String[] getAllCountryNames(){
	return AllCountryNames;
}

public static String[] selectCountrys(){
	ArrayList al=new ArrayList();
	al.add(" -- Select Country -- ");
	for(int i=0;i<AllCountryNames.length;i++){
		al.add(AllCountryNames[i]);
	}
	return (String[])al.toArray(new String[0]);
}
public static String[] selectCountryCodes(){
	ArrayList al=new ArrayList();
	al.add("");
	for(int i=0;i<AllCountryCodes.length;i++){
		al.add(AllCountryCodes[i]);
	}
	return (String[])al.toArray(new String[0]);
}

public static String[] selectStates(){
	ArrayList al=new ArrayList();
	al.add(" -- Select State -- ");
	for(int i=0;i<USStateNames.length;i++){
		al.add(USStateNames[i]);
	}
	return (String[])al.toArray(new String[0]);
}
public static String[] selectStateCodes(){
	ArrayList al=new ArrayList();
	al.add("");
	for(int i=0;i<USStateCodes.length;i++){
		al.add(USStateCodes[i]);
	}
	return (String[])al.toArray(new String[0]);
}

public static String[] getUSStateCodes(){
	return USStateCodes;
}
public static String[] getUSStateNames(){
	return USStateNames;
}
public static String makeFirstCharCaps(String arg){
	if(arg==null || "".equals(arg.trim())){
			return arg;
	}else{
		String temp=arg.substring(0,1);
		temp=temp.toUpperCase();
		temp=temp+arg.substring(1);
		return temp;
	}
}

public static void main(String[] arg){
	try{
	System.out.println("String is:"+makeFirstCharCaps("uday"));
	}catch(Exception e){System.out.println("Exception is:"+e);}
}

   public static String processString(String str,String searchFor, String replaceWith){
              StringBuffer sb=new StringBuffer();
              sb.append(str);
	while(sb.indexOf(searchFor) !=-1){
		sb.replace(sb.indexOf(searchFor),sb.indexOf(searchFor)+searchFor.length(),
   replaceWith);
	}
    return sb.toString();
  }
  public static String[] getBeeportCategoryCodes(){
	  return beeportCategoryCodes;
  }
  public static String[] getBeeportCategoryValues(){
	  return beeportCategoryValues;
  }
  public static String[] getBeeportCategoryURLS(){
	  return beeportCategoryURLS;
  }

  private static void populateCountriesInfo(){
	 // List countryCodes=DbUtil.getValues("select  countrycode from country_states where statecode is null order by disposition");
	  List countryNames=DbUtil.getValues("select countryname from country_states where statecode is null  order by disposition");
	  AllCountryNames=(String[])countryNames.toArray(new String[0]);
	  AllCountryCodes=(String[])countryNames.toArray(new String[0]);
	    ebeeCategoryCodes=new String[]{"My Home","My Profile","My Page","My Preferences","My Photos","My Alerts","My Messages","My Network","My Guestbook","My "+getDisplayName(EbeeConstantsF.get("club.label","Bee Hive"),"Beehive")+"s","My  Events","My Classifieds","My Invitations","My Surveys","Logout"};

  }
  public static void reloadCountriesInfo(){
	  populateCountriesInfo();
  }

  public static String[] getMemberMenuCategoryCodes(){
	  return ebeeCategoryCodes;
  }
  public static String[] getMemberMenuCategoryValues(){
	  return ebeeCategoryValues;
  }
  public static List getMemberMenuCategories(String unitid){
	  if("13579".equals(unitid))
		return Arrays.asList(ebeeCategoryValues);
	  else
	        return Arrays.asList(portalCategoryValues);
  }
  public static String[] getMemberMenuURLS(String authid){
	  return getMemberMenuURLS(authid,"13579");
  }
  public static String[] getMemberMenuURLS(String authid,String unitid){
  String groupid=DbUtil.getVal("select  clubid from clubinfo where unitid=?",new String [] {unitid});
	  //"/portal/portalhome/myhome/homeportal?UNITID="+unitid,
	  //"/portal/portalhome/myhome-member-logout?UNITID="+unitid -- rplaced with logout
       return  new String[]{"/portal/clubpage/clubpage.jsp?UNITID="+unitid+"&GROUPID="+groupid,
	       	  	    "/portal/editprofiles/memberprofile.jsp?isnew=yes&UNITID="+unitid+"&GROUPID="+groupid,
			    "/portal/editprofiles/managemypage.jsp?UNITID="+unitid+"&GROUPID="+groupid,
			    "/portal/preferences/editpref.jsp?UNITID="+unitid+"&GROUPID="+groupid,
			    "/portal/photoupload/photosmanage.jsp?UNITID="+unitid+"&GROUPID="+groupid,
			    "/portal/alerts/myalert.jsp?UNITID="+unitid+"&GROUPID="+groupid,
			    "/portal/club/ClubMessagingBeelet.jsp?UNITID="+unitid+"&GROUPID="+groupid,
			    "/portal/nuser/NuserFriends.jsp?UNITID="+unitid+"&GROUPID="+groupid,
			    "/portal/guestbook/GBookManage.jsp?UNITID="+unitid+"&GROUPID="+groupid,
			    "/portal/club/myhubs.jsp?UNITID="+unitid+"&GROUPID="+groupid,
			    "/portal/myevents/myevents.jsp?UNITID="+unitid+"&GROUPID="+groupid,
			    "/portal/classifieds/mypostings.jsp?UNITID="+unitid+"&GROUPID="+groupid,
			    "/portal/invitation/invitation.jsp?UNITID="+unitid+"&GROUPID="+groupid,
			    "/portal/club/mysurveyspolls.jsp?UNITID="+unitid+"&GROUPID="+groupid,
			    "/portal/clubdetails/clubdetails.jsp?logout=y&UNITID="+unitid+"&GROUPID="+groupid
                  	};
  }
  public static String getTabAlignment(String unitid){
	  if(unitid==null || "".equals(unitid.trim())) return unitid;
	  if("13579".equals(unitid) || "13578".equals(unitid)){
		  return "left";
	  }else{
		String st=DbUtil.getVal("select value from config where name='member.tab.alignment' and config_id in (select config_id from clubinfo where unitid=?)",new String[]{unitid});
		if(st==null || "".equals(st.trim()))
			return "left";
		else
			return st;
	  }
  }
  public static String getDisplayName(String code,String defval){
	  if("Bee Hive".equals(code))
		  return "Beehive";
	  else if("HUB".equals(code))
		  return "Hub";
	  else
		  return defval;
  }
  public static List getDefaultCodesAsList(){
	  return java.util.Arrays.asList(DEFAULTS_CODES);
  }
  public static String[] getDefaultCodesAsArray(){
	  return DEFAULTS_CODES;
  }
  public static List getEmailPermittedChar(){
  List list=new ArrayList();
  list=Arrays.asList(Email_Allowed_Chars);
  return list;
  }
   public static List getWikiFilePermittedChar(){
  List list=new ArrayList();
  list=Arrays.asList(Allowed_Chars_for_wiki_filename);
  return list;
  }

   public static String []  getStatesCodes(String country){
	   if("USA".equals(country)) return US_STATES_CODE;
	   else
	   if("India".equals(country)) return INDIA_STATES_CODE;
	   else
	   if("UK".equals(country)) return UK_STATES_CODE;
	   else
		  return new String[0];
  }
  public static String []  getStatesNames(String country){
	   if("USA".equals(country)) return US_STATES_NAME;
	   else
	   if("India".equals(country)) return INDIA_STATES_NAME;
	   else
	   if("UK".equals(country)) return UK_STATES_NAME;
	   else
		  return new String[0];
  }

}

