package com.eventbee.creditcard;

import com.eventbee.general.formatting.CurrencyFormat;
import com.event.beans.ProfileData;
import com.eventbee.general.StatusObj;

import java.util.Vector;
import java.util.Date;

public class CreditCardInfo {

	public String cc_number=null;
	public String cvv_code="";
	public String ccexp_month=null;
	public String ccexp_year=null;
	public String cardtype=null;
	public String currency_code=null;
	public ProfileData profiledata=null;
	public static int currentyear=0;
	public static int currentmonth=0;
	public String grand_total=null;
	public String errormessage=null;
	public String internalref=null;
	private Vector errorvector=new Vector();

	public static Vector permittedno=new Vector();
	public static Vector permittedmonth=new Vector();
	public static Vector permittedyear=new Vector();
	public static Vector permittedcards=new Vector();

	public static String[] month={"01","02","03","04","05","06","07","08","09","10","11","12"};
	public static String[] cards={"Visa","Mastercard","Amex","Discover","Diners Club","Other"};


	CurrencyFormat cf=null;

	static{

		Date date=new Date();
		currentyear=date.getYear()+1900;
                for(int i=0;i<10;i++){
			permittedno.add(Integer.toString(i));
		}

		for(int j=0;j<month.length;j++){
			permittedmonth.add(month[j]);

		}

		for(int k=currentyear;k<currentyear+50;k++){
			permittedyear.add(Integer.toString(k));
		}

		for(int j=0;j<cards.length;j++){
			permittedcards.add(cards[j]);

		}


	}

		public CreditCardInfo(String cc_number,String ccexp_month,String ccexp_year,String cardtype,String grand_total,String currency_code,String internalref){
			    Date date=new Date();
				this.cc_number=cc_number;
				this.ccexp_month=ccexp_month;
				this.ccexp_year=ccexp_year;
				this.cardtype=cardtype;
				this.grand_total=grand_total;
                this.currency_code=currency_code;
                this.internalref=internalref;
				currentyear=date.getYear()+1900;
				currentmonth=date.getMonth()+1;

		}

		public CreditCardInfo(String cc_number,String ccexp_month,String ccexp_year,String cardtype,String grand_total,String currency_code,ProfileData profiledata,String internalref){
			    Date date=new Date();
				this.cc_number=cc_number;
				this.ccexp_month=ccexp_month;
				this.ccexp_year=ccexp_year;
				this.cardtype=cardtype;
				this.grand_total=grand_total;
				this.profiledata=profiledata;
				this.currency_code=currency_code;
                this.internalref=internalref;
				currentyear=date.getYear()+1900;
				currentmonth=date.getMonth()+1;

		}

		public CreditCardInfo(String cc_number,String cvv_code,String ccexp_month,String ccexp_year,String cardtype,String grand_total,String currency_code,String internalref){

				Date date=new Date();
				this.cc_number=cc_number;
				this.cvv_code=cvv_code;
				this.ccexp_month=ccexp_month;
				this.ccexp_year=ccexp_year;
				this.cardtype=cardtype;
				this.grand_total=grand_total;
                this.currency_code=currency_code;
                 this.internalref=internalref;

				currentyear=date.getYear()+1900;
				currentmonth=date.getMonth()+1;

		}

		public CreditCardInfo(String cc_number,String cvv_code,String ccexp_month,String ccexp_year,String cardtype,String grand_total,String currency_code,ProfileData profiledata,String internalref){

				Date date=new Date();
				this.cc_number=cc_number;
				this.cvv_code=cvv_code;
				this.ccexp_month=ccexp_month;
				this.ccexp_year=ccexp_year;
				this.cardtype=cardtype;
				this.grand_total=grand_total;
				this.profiledata=profiledata;
                this.currency_code=currency_code;
                 this.internalref=internalref;

				currentyear=date.getYear()+1900;
				currentmonth=date.getMonth()+1;
		}


      public String getInternalRef(String vendor){
	  			   return internalref;
		}

		public String getCC_number(String vendor){
			   return cc_number;
		}

        public String getCurrencyCode(String vendor){
					   return currency_code;
				}


		public String getCCVcode(String vendor){
					   return cvv_code;
		}

		public String getCC_expmonth(String vendor){
			   return ccexp_month;
		}

		public String getCC_expyear(String vendor){
				return ccexp_year;
		}

		public String getcardtype(String vendor){
			return cardtype;
		}

		public String getgrand_total(String vendor){
			return grand_total;
		}

		public ProfileData getProfileData(String vendor){
			return profiledata;
		}

		public boolean checkCardtype(){
			boolean flag=false;
				if(permittedcards.contains(cardtype)){
							flag=true;
				}else{
							flag=false;
				}
			   if(!flag){
					errormessage="Invalid Card Type";
					errorvector.add("Invalid Card Type");
				}

			return flag;
		}


		public boolean checkCC_number(){
			boolean flag=false;
			try{
				int len=cc_number.length();

				if((len>7)&&(len<17)){
					for(int i=0;i<len;i++){
						if(permittedno.contains(cc_number.substring(i,i+1))){
								flag=true;
						}else{
								flag=false;
								break;
						}
					}

				}
				if(!flag){
					errormessage="Invalid Card Number";
					errorvector.add("Invalid Card Number");
				}
			}catch(Exception e){
				flag=false;
				errormessage="Invalid Card Number";
				//errorvector.add("Invalid Card Number");
			}

			return flag;
		}

		public boolean checkCC_expiry(){
			boolean flag=false;
			if((permittedmonth.contains(ccexp_month))&&(permittedyear.contains(ccexp_year))){
					if(currentyear<Integer.parseInt(ccexp_year)){
						flag=true;
					}else if(currentyear==Integer.parseInt(ccexp_year)){
						if(currentmonth<=Integer.parseInt(ccexp_month)){
							flag=true;
						}
					}

			}else{
			}
			if(!flag){
				errormessage="Invalid Expiration Date";
				errorvector.add("Invalid Expiration Date");
			}
			return flag;
		}

		public StatusObj checkFormat(){
			errorvector.clear();

			cf=new CurrencyFormat();
			grand_total=cf.getCurrency2decimal(grand_total);



			if((checkCardtype())&(checkCC_number())&(checkCC_expiry())){
				return new StatusObj(true,"","");
			}else{
				return new StatusObj(false,errormessage,errorvector);

			}
		}
		//String cc_number,String ccexp_month,String ccexp_year,String cardtype,String grand_total
		public static void main(String args[]){
			CreditCardInfo cci=new CreditCardInfo(args[0],args[1],args[2],args[3],args[4],args[5],args[6]);
			StatusObj sobj=cci.checkFormat();
			if(sobj.getStatus()){

			}else{

			}


		}

}
