package com.eventbee.util;

import com.eventbee.general.EbeeConstantsF;

public class LinksGenerator{
	static String defaultprotocol="http",sslprotocol="http";
	static String slash="://",slink="/",qmark="?";


	public static String getServerAddress(String unitid,int isproxy){
				String serveraddress=null;

				if(isproxy==0){
						serveraddress=EbeeConstantsF.get("serveraddress","localhost:8080");
				}if(isproxy==2){
					 	 serveraddress=EbeeConstantsF.get("serveraddress","localhost:8080");
				}else{
						 serveraddress=serveraddress;
				}

				return serveraddress;

	}

		public static String getManagerHome(String unitid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{
						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"home"+qmark+"unitid="+unitid;
				}catch(Exception e){
					System.out.println("Exception in getManagerHome() LinksGenerator"+e);
				}

				return url;
		}

		public static String getEvents(String unitid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{
						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"events"+qmark+"unitid="+unitid;
				}catch(Exception e){
					System.out.println("Exception in getEvents() LinksGenerator"+e);
				}

				return url;
		}

		public static String getEvent(String unitid,String eventid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"event"+qmark+"unitid="+unitid+"&id="+eventid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}

		public static String getEventRegister(String unitid,String eventid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"eventregister"+qmark+"unitid="+unitid+"&id="+eventid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}

		public static String getMyEvent(String unitid,String transactionid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"myevent"+qmark+"unitid="+unitid+"&id="+transactionid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}


		public static String getTransaction(String unitid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"transaction"+qmark+"unitid="+unitid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}

		public static String getEventSponsorships(String unitid,String eventid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"eventsponsorships"+qmark+"unitid="+unitid+"&id="+eventid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}


		public static String getClubSponsorships(String unitid,String clubid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"clubsponsorships"+qmark+"unitid="+unitid+"&id="+clubid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}

		public static String getSponsorship(String unitid,String opportunityid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"sponsorship"+qmark+"unitid="+unitid+"&id="+opportunityid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}


		public static String getSponsorshipRegister(String unitid,String opportunityid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"sponsorshipregister"+qmark+"unitid="+unitid+"&id="+opportunityid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}

		public static String getMySponsorship(String unitid,String transactionid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"mysponsorship"+qmark+"unitid="+unitid+"&id="+transactionid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}


		public static String getClub(String unitid,String clubid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"club"+qmark+"unitid="+unitid+"&id="+clubid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}


		public static String getSignup(String unitid,String clubid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"signup"+qmark+"unitid="+unitid+"&id="+clubid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}


		public static String getMemberLogin(String unitid,String clubid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"login"+qmark+"unitid="+unitid+"&id="+clubid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}

		public static String getMyTransaction(String unitid,String transactionid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"mytransaction"+qmark+"unitid="+unitid+"&id="+transactionid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}


		public static String getEmailLink(String purpose,String campid,String unitid,String itemid,String mcode,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"emailrespond"+qmark+"unitid="+unitid+"&id="+itemid+"&purpose="+purpose+"&campid="+campid+"&mcode="+mcode+"&id="+itemid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}


		public static String getUnsubscribe(String unitid,String memberid,String listid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"unsubscribe"+qmark+"unitid="+unitid+"&memberid="+memberid+"&listid="+listid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}

		//for upcoming sponsorships of a unit
		public static String getSponsorships(String unitid,int isproxy){
				String url=null,appname=null;
				String serveraddress=null;
				appname=EbeeConstantsF.get("appname","portal");
				try{

						serveraddress=getServerAddress(unitid,isproxy);
						url=defaultprotocol+slash+serveraddress+slink+appname+slink+"sponsors"+qmark+"unitid="+unitid;

				}catch(Exception e){
					System.out.println("Exception in getEvent() LinksGenerator"+e);
				}

				return url;
		}


		public static void main(String args[]){
				System.out.println("MANAGER HOME : "+LinksGenerator.getManagerHome("13579",0));
				System.out.println("UPCOMING EVENTS : "+LinksGenerator.getEvents("13581",0));
				System.out.println("EVENT DETAILS : "+LinksGenerator.getEvent("13581","13600",0));
				System.out.println("EVENT REGISTER : "+LinksGenerator.getEventRegister("13581","13600",0));
				System.out.println("MY EVENT : "+LinksGenerator.getMyEvent("13581","jHvvlGMM",0));
				System.out.println("TRANSACTION : "+LinksGenerator.getTransaction("13581",0));
				System.out.println("EVENT SPONSORSHIPS : "+LinksGenerator.getEventSponsorships("13735","13737",0));
				System.out.println("CLUB SPONSORSHIPS : "+LinksGenerator.getClubSponsorships("13735","13736",0));
				System.out.println(" SPONSORSHIP : "+LinksGenerator.getSponsorship("13581","4",0));
				System.out.println(" SPONSORSHIP REGISTER: "+LinksGenerator.getSponsorshipRegister("13587","6",0));
				System.out.println("MY SPONSORSHIP : "+LinksGenerator.getMySponsorship("13581","AAAAAAAA",0));
				System.out.println("CLUB DETAILS : "+LinksGenerator.getClub("13735","13736",0));
				System.out.println("SIGN UP : "+LinksGenerator.getSignup("13735","13736",0));
				System.out.println("MEMBER LOGIN : "+LinksGenerator.getMemberLogin("13735","13736",0));
		}
}
