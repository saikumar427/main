package com.eventbee.geocodes;
import java.util.*;
import com.eventbee.general.*;
import com.eventbee.util.Address;
public class GeocodeAmericaCSV extends AbstractGeocodeGenerator{

	private String Latitude="";
	private String Longitude="";
	private String Status="";

public Geocode getGeocodes(){
	Geocode geocode=new Geocode();
	String addressstring="";
	Address address=getAddress();
	if(address!=null){
		addressstring=address.getStreet()+","+address.getCity()+","+address.getState()+","+address.getZip()+"";
	}
	System.out.println("addressstring: "+addressstring);
	if(!addressstring.equals("")){
		String urlstring=EbeeConstantsF.get("GEOCODES_SERVER_URL","http://rpc.geocoder.us/service/csv");
	String response=getRawResponseData(urlstring,new String[]{"address"},new String[] {addressstring},"get");

	System.out.println("response    "+response);
	if(response!=null&&!"".equals(response)){
		String[] result = response.split(",");
		if(result!=null&&result.length>=2){
		 geocode.setLatitude(result[0]);
		 geocode.setLongitude(result[1]);
		 geocode.setStatus(true);
         System.out.println(result[0]);
         System.out.println(result[1]);

	 	}
	}
}
	return geocode;
}

}