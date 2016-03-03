package com.eventbee.general;

import java.text.*;
import java.util.*;

public class TemplateConverter {
public static final String Mgr_SignUp=
    "Dear #**firstname**# #**lastname**#,\n"
  	+"\n"
  	+"\n"
  	+"Thank you for signing up Manager Account at Beeport. Your account is being processed for approval. You will be receiving Approval confirmation email along with login details shortly.\n"
  	+"\n"
  	+"\n"
  	+"If you have any questions, please send email to support@beeport.com \n"
  	+"\n"
  	+"\n"
  	+"\n"
  	+"Cheers,\n"
  	+"Beeport Team";


public static final String BeeID_SignUp=
    "Dear #**firstname**# #**lastname**#,\n"
	+"\n"
	+"Thank you for Signing up at Eventbee.Your account is now activated.\n"
	+"\n"
	+"Your login details -\n"
	+"\n"
	+"Bee ID: #**userid**# \n"
	+"Password: #**password**# \n"
	+"\n"
	+"To Login, please visit http://www.eventbee.com,  and enter Bee ID and Password.\n"
	+"\n"
/*	+"- Build your home page with Photo and Content to attract more visitors \n"
	+"- Share My page URL with your friends and contacts \n"
	+"- Update your profile with current information\n" */
	+"Attendee: Find a local event, and start event networking!\n"
	+"Host: List a public or private event, and increase your attendee count!\n"
	+"\n"
	+"Once again, we welcome you to Eventbee community.\n"
	+"\n"
	+"Sincerely,\n"
	+"\n"
	+"Bala Musrif \n"
	+"Founder & President \n"
	+"http://www.eventbee.com";

 public static final String EventView=
    "#**personalmessage**# \n"
 	+"\n"
 	+"Visit event page by clicking on the following URL, #**link**#\n";

 public static final String EventRegister=
    "Dear #**firstname**# #**lastname**#,\n"
 	+"\n"
    +"Your registration for #**eventname**# is confirmed. Your Transacation ID is #**transactionkey**#.\n"
    +"\n"
	+"When - Starts #**startdate**#, Ends #**enddate**#\n"	
	+"Where - #**fulladdress**#\n\n"	
 	+"Your Attendee Key: #**key**#\n"
 	+"\n"
    +"EVENT PAGE:\n"
    +"Visit event page by clicking on the following link #**transactionurl**#.\n"
    +"Network with other Attendees, view up to date event information by visiting event page.\n"
    +"Thank you for registering #**eventname**# event. We look forward to seeing you there!\n"
    +"\n"
    +"\n"
    +"Event Manager:\n"
    +"#**mgrfirstname**# #**mgrlastname**# \n"
    +"#**mgremail**#\n"
    +"#**mgrphone**#\n";
    
    /*public static final String EventRegisterHTML=
    "Dear #**firstname**# #**lastname**#,\n<br />"
 	+"\n<br />"
    +"Your registration for #**eventname**# is confirmed.\n<br />"
    +"\n<br />"
 	+"Your Attendee Key: #**key**#\n<br />"
 	+"\n<br />"
    +"EVENT PAGE:\n<br />"
    +"Visit  <a href='#**transactionurl**#'> event page </a>.\n<br />"
    +"Network with other Attendees, view up to date event information by visiting event page.\n<br />"
    +"Thank you for registering #**eventname**# event. We look forward to seeing you there!\n<br />"
    +"\n<br />"
    +"\n<br />"
    +"Event Manager:\n<br />"
    +"#**mgrfirstname**# #**mgrlastname**# \n<br />"
    +"#**mgremail**#\n<br />"
    +"#**mgrphone**#\n<br />";
 */
    public static final String EventRegisterHTML=
    "Dear #**firstname**# #**lastname**#,\n<br />"
 	+"\n<br />"
    +"Your registration for #**eventname**# is confirmed. Your Transacation ID is #**transactionkey**#.\n<br />"
    +"\n<br /><div align='left'>"
 	+"#**htmlticketcont**#"
 	+"\n</div><br />"
    +"Thank you for registering #**eventname**# event. We look forward to seeing you there!\n<br />"
    +"\n<br />"
    +"\n<br />"
    +"Event Manager:\n<br />"
    +"#**mgrfirstname**# #**mgrlastname**# \n<br />"
    +"#**mgremail**#\n<br />"
    +"#**mgrphone**#\n<br />";
    
    

  public static final String Sponsor=
    "Dear #**firstname**# #**lastname**#,\n"
    +"\n"
    +"Your Booking for #**sponsorname**# at #**groupname**# is confirmed.\n"
    +"Your Sponsor Key: #**key**#\n"
    +"\n"
    +"Link URLs:\n"
    +"Booking Details: #**bookdetails**#\n"
    +"#**sponsorname**# Details: #**sponsordetails**#\n"
    +"#**groupname**# Details: #**groupdetails**#\n"
    +"\n"
    +"Thank you for Booking #**sponsorname**# at #**groupname**#.\n"
    +"\n"
    +"Event Manager:\n"
    +"#**mgrfirstname**# #**mgrlastname**#\n"
    +"#**mgremail**#\n"
    +"#**mgrphone**#\n";

  public static final String FriendToJoin=
    "#**name**# invited you to visit eventbee.com and join Eventbee community.\n"
    +"\n"
  	+"#**personalmessage**# \n"
    +"\n"
    +"Experience a whole new way of Event Networking! Join the all-new exciting events community. It's FREE!\n"
    +"\n"
	+":: Build your own home page to share with other Attendees.\n"
    +"\n"
  	+":: Social network with other attendees, make business contacts.\n"
    +"\n"
  	+":: Get members-only event discounts.\n"
    +"\n"
  	+":: Keep track of your events, contacts, sponsorships.\n"
    +"\n"
  	+"Visit http://www.eventbee.com to join Eventbee community";


  public static final String EvtMgrToJoin=
    "#**name**# invited you to list your events at eventbee.com.\n"
    +"\n"
  	+"#**personalmessage**# \n"
    +"\n"
  	+"List your events at Eventbee, the all-new exciting events community portal. Eventbee is committed to provide best event experience to all its member community through state-of the-art technology. By listing at Eventbee you are reaching highly sought after events community. Moreover take advantage of Eventbee's tight integration with Beeport software to effectively manage your events.\n"
    +"\n"
  	+"Visit http://www.eventbee.com to learn more about Eventbee community.\n"
    +"\n"
  	+"Power your events with Beeport (http://www.beeport.com) technology to list events at Eventbee.";

  public static final String Member_SignUp=
    "Dear #**firstname**# #**lastname**#,\n"
  	+"\n"
  	+"Welcome to #**unitname**#\n"
  	+"You have signed up for a #**memshipname**# membership. Your User Name and Password are below.\n"
  	+"User Name: #**userid**#\n"
  	+"Password: #**password**#\n"
  	+"\n"
    +"To Login, visit #**uniturl**#\n"
  	+"\n"
  	+"Thank you,\n"
  	+"#**mgrfirstname**# #**mgrlastname**#\n"
  	+"#**mgremail**#";

  public static final String Manager_Add_manual=
   	"Dear #**firstname**# #**lastname**#,\n"
  	+"\n"
  	+"Welcome to #**unitname**#\n"
  	+"You have signed up for a #**memshipname**# membership."
    +"Your User Name and Password are below.\n"
  	+"User Name: #**userid**#\n"
  	+"Password: #**password**#\n"
  	+"\n"
    +"To Login, visit #**uniturl**#\n"
  	+"\n"
    +"Please reset your User Name and Password on first login\n"
  	+"Thank you,\n"
  	+"#**mgrfirstname**# #**mgrlastname**#\n"
  	+"#**mgremail**#";

  public static final String Manager_File_Upload=Manager_Add_manual;

  public static final String ForgotPassword=
    "Thank you for contacting #**portname**# regarding your account information.\n"
	+"Your #**portname**# Password is #**password**#\n"
	+"To change your login information, click on My Profile link on your home page.\n"
	+"Thank you for visiting #**portname**#\n"
	+"#**mgremail**#";

public static final String ForgotLogin=
	"Thank you for contacting #**portname**# regarding your account information.\n"
	+"Your #**portname**# Login information is \n #**loginname**#\n"
	+"To change your login information, click on My Profile link on your home page.\n"
	+"Thank you for visiting #**portname**#\n"
	+"#**mgremail**#";

public static final String ForgotLoginMultiple=
	"Thank you for contacting #**portname**# regarding your account information.\n"
	+"You seem to have following accounts with this email ID \n"
	+"#**loginname**#\n"
	+"To change your login information, click on My Profile link on your home page.\n"
	+"Thank you for visiting #**portname**#\n"
	+"#**mgremail**#";

 public static final String ChangePassword="\t\t\t\t\t\t\t\t\t\t\t#**date**#\n"
                 +"Dear #**firstname**# #**lastname**#,\n"
                 +"Your account password changed to '#**password**#'.\n"
                 +"Thanks,\n"
                 +"with regards,\n"
                 +"Management\n";

 public static final String ChangeEvtStatus="\t\t\t\t\t\t\t\t\t\t\t#**date**#\n "
                 +"Dear #**firstname**# #**lastname**#,\n "
                 +"Your event #**eventname**#'s status changed to '#**status**#'.\n"
                 +"Thanks,\n "
                 +"with regards,\n "
                 +"Management\n ";

public static final String EVENTFEEDBACK="\n "
							+" #**name**# 's feedback for #**eventname**#:\n"
							+" ** Feedback ** \n"
							+" #**feedback**# \n";

public static final String ChangeAccStatus="\t\t\t\t\t\t\t\t\t\t\t#**date**#\n "
                  +"Dear #**firstname**# #**lastname**#,\n "
                  +"\t\t\t\t Your account status changed to '#**status**#'.\n"
                  +"\t\t\t\t   Thanks,\n "
                  +"\t\t\t\t\t\t\t\t\t\t with regards,\n "
                  +"\t\t\t\t\t\t\t\t\t\t Management\n ";

 static final String teststring="\t\t\t\t\t\t\t\t\t\t\t\t #**date**#\n"
 		+"\t\t\tThis is testing for #**name**# template\n"
 		+"\t\t\tThis templates users the following attributtes\n"
 		+"\t\t\t#**1**#\n"
 		+"\t\t\t#**2**#\n"
 		+"\t\t\t\t\t\t\t\t\t\t\t\t #**sub**#\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t#**date**#";

public static final String invitetoJoin="#**firstname**# #**lastname**# has invited you to join #**name**#. \n \n"
					+"To Join this Community, visit  #**url**# \n \n"
					+"#**firstname**#'s Personal Message: \n\n"
					+"#**personalmessage**# \n\n"
					+"Cheers, \n"
					+"#**app_name**#";

			
 public static void main(String args[]) throws Exception {
         Map wmap=new HashMap();
	 wmap.put("#**date**#",new Date().toString());
	 wmap.put("#**1**#","1.table");
	 wmap.put("#**2**#","2.row");
	 wmap.put("#**sub**#","thank u");
	 wmap.put("#**sub1**#","thank u");
	 wmap.put("#**name**#","Testing");
	 Vector tablev=new Vector();
	 String[] data1= new String[]{"col1","col2"};
	 String[] data2= new String[]{"col2","col3"};
	 tablev.add(data1);
	 tablev.add(data2);
	 wmap.put("#**table1**#",tablev);

        // sop(Mgr_SignUp);
	sop(getMessage( wmap, Mgr_SignUp));
  }

  public static void  sop(String s){
        System.out.println(s);
  }

  private static void processString(StringBuffer sb,String searchFor, String replaceWith){
	while(sb.indexOf(searchFor) !=-1){
		sb.replace(sb.indexOf(searchFor),sb.indexOf(searchFor)+searchFor.length(),
   replaceWith);
	}
  }

  public static String getMessage(Map wordReplaceMap,String templateMessage){
	StringBuffer sb= new StringBuffer(templateMessage);
	Set set=wordReplaceMap.entrySet();
	Iterator iter=set.iterator();
	while(iter.hasNext()){
		Map.Entry me=(Map.Entry)iter.next();
		if(me.getValue() instanceof String){
			processString(sb,me.getKey().toString(),me.getValue().toString());
		}
		if(me.getValue() instanceof Vector){
			StringBuffer sbf1=new StringBuffer();
			Vector tempvec=(Vector)me.getValue();
			if(tempvec !=null){
				for(int i=0;i<tempvec.size();i++){
					String[] stra=(String[])tempvec.elementAt(i);
					sbf1.append("\n");
					for(int j=0;j<stra.length;j++){
						sbf1.append(stra[j]+"\t");
					}

				}//end for tempvec
				sbf1.append("\n");
				processString(sb,me.getKey().toString(),sbf1.toString());
			}//ed if ve != null
		}//end if instance of vector
	}

	return sb.toString();

    }//END OF GET MESSAGE








  }//END OF FILE
