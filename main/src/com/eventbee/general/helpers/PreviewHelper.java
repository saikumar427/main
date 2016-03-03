package com.eventbee.general.helpers;

import com.eventbee.general.dbhelpers.PreviewDBHelper;

public class PreviewHelper {

	public static String processPreviewData(String cssContent){
		
		String HTMLContent=PreviewDBHelper.getDefaultHTML("event");
		StringBuffer sb= new StringBuffer(HTMLContent);
		String searchFor="$customcss";
		sb.replace(sb.indexOf(searchFor),sb.indexOf(searchFor)+searchFor.length(),cssContent);
		return sb.toString();
	}
	public static String eventspageProcessPreviewData(String cssContent){
		
		String HTMLContent=PreviewDBHelper.getDefaultHTML("eventspage");
		StringBuffer sb= new StringBuffer(HTMLContent);
		String searchFor="$customcss";
		sb.replace(sb.indexOf(searchFor),sb.indexOf(searchFor)+searchFor.length(),cssContent);
		return sb.toString();
	}
}
