package com.eventbee.general;

import org.jsoup.Jsoup;

public class JsoupHtml2Text {
	
	public static String getPlainText(String finalContents){
		return Jsoup.parse(finalContents).text();    
	}
	
}
