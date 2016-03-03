package com.eventbee.geocodes;
import java.util.*;
import com.eventbee.general.*;
import com.eventbee.util.Address;
import com.eventbee.util.ProcessXMLData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import java.io.*;
public class GeocodeAmericaREST extends AbstractGeocodeGenerator{

	private String Latitude="";
	private String Longitude="";
	private String Status="";

public Geocode getGeocodes(){
	Geocode geocode=new Geocode();
	Map resMap=null;
	String addressstring="";
	Address address=getAddress();
	//EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"GeocodeAmericaREST.java","in getGeocodes()","address is---------> :"+address,null);
	if(address!=null){
		addressstring=address.getStreet()+","+address.getCity()+","+address.getState()+","+address.getZip()+"";
	}
	if(!addressstring.equals("")){
		//String urlstring=EbeeConstantsF.get("GEOCODES_SERVER_URL","http://maps.google.com/maps/api/geocode/json");
		String urlstring="http://maps.google.com/maps/api/geocode/json";
		//EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"GeocodeAmericaREST.java","in getGeocodes()","urlstring is---------> :"+urlstring,null);
		//EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"GeocodeAmericaREST.java","in getGeocodes()","addressstring is---------> :"+addressstring,null);
		String response=getRawResponseData(urlstring,new String[]{"address","sensor"},new String[] {addressstring,"false"},"get");
		//EventbeeLogger.log(EventbeeLogger.LOGGER_MAIN,EventbeeLogger.INFO,"GeocodeAmericaREST.java","in getGeocodes()","response is---------> :"+response,null);
		//System.out.println("\n response: "+response);
		if(response!=null){
			try{
				JSONObject obj=new JSONObject();
				obj=(JSONObject)new JSONTokener(response).nextValue();
				JSONArray ja=obj.getJSONArray("results");
				System.out.println("\n jasonarray length: "+ja.length());
				if(ja.length() > 0){
					String lang =ja.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng");
					String lat =ja.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat");
					System.out.println("\n lang: "+lang+" lat: "+lat);
					if(lang != null && lat != null){
						geocode.setLatitude(lat);
						geocode.setLongitude(lang);
						geocode.setStatus(true);
					}
				}
			}catch (Exception e) {
				System.out.println("\n Exception in GeocodeAmericaREST getGeocodes: "+e.getMessage());
			}
		}
		/*if(response!=null&& (response.indexOf("rdf:RDF")>0) ){
			try {
				Document doc=ProcessXMLData.getDocument(new StringBufferInputStream(response));
				if(doc!=null){
					doc.getDocumentElement ().normalize ();
					resMap=ProcessXMLData.getProcessedXMLData(doc,"geo:Point",new String [] {"geo:long","geo:lat"});
					if(resMap!=null&&!resMap.isEmpty())
						 geocode.setLatitude((String)resMap.get("geo:lat"));
						 geocode.setLongitude((String)resMap.get("geo:long"));
						 geocode.setStatus(true);
				}
			} catch (Exception e) {
					//EventbeeLogger.logException(EventbeeLogger.LOGGER_EXCEPTION,EventbeeLogger.ERROR, "GeocodeAmericaREST.java", "getGeocodes() ", e.getMessage(), null) ;
			}
		}*/
	}

	return geocode;
}

}