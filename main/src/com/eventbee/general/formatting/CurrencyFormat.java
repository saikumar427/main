package com.eventbee.general.formatting;
import java.text.*;
import java.util.Locale;

import com.eventbee.general.*;

public class CurrencyFormat{

	public String getCurrency2decimal(String amount){
	 try {
  			if(amount.indexOf(".")==-1){
		   		amount=amount+".00";
			}else{
				int l=amount.length()-1;
				int k=amount.indexOf(".");
				if((l-k)==1){
					amount=amount+"0";
				}else if((l-k)>=2){
					amount=amount.substring(0,k+3);
				}
			}
	 	} catch (Exception e) {
	            EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR,"CurrencyFormat", "getCurrency2decimal(String amount)", e.getMessage(), e);
       	}
       	return amount;
	}

	public static String getCurrencyFormat(String spec,String amount,boolean includeCS){
		double num=0;

		if(amount.startsWith(spec)){
			amount=amount.substring(spec.length(),amount.length());
		}
                try{
			num=Double.parseDouble(amount);
		}catch(NumberFormatException ne){
			return amount;
		}
                if (includeCS)
		     return spec+getValiNum(amount);
                else
                     return getValiNum(amount);
	}

  	public static String getValiNum(String str ){
		String s="";
		try{
			DecimalFormat df=new DecimalFormat("0.00");
			df.setMaximumFractionDigits(2);
			Number n=df.parse(str.trim(),new ParsePosition(0) );
			s= df.format(n.doubleValue());
		}catch(Exception e){
		     EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR,"CurrencyFormat", "getValiNum(String str )", e.getMessage(), e);
		}
		return s;
	}
  	
  	public static String newCurrencyFormat(Double doubleValue){//if 10.00 --> 10; 10.5-->10.50; 10.234 -->10.23
  	   try{
	  		boolean isWholeNumber=(doubleValue == Math.round(doubleValue));
	  	    DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.US);
	  	    formatSymbols.setDecimalSeparator('.');
	
	  	    String pattern= isWholeNumber ? "#.##" : "#.00";    
	  	    DecimalFormat df = new DecimalFormat(pattern, formatSymbols);
	  	    return df.format(doubleValue);
  	   }catch(Exception e){
  		   return String.valueOf(doubleValue);
  	   }
  	  }

	public static void main(String args[]){
		CurrencyFormat cf=new CurrencyFormat();
		String s=cf.getCurrencyFormat("$","$567.897",true);

	}


}

