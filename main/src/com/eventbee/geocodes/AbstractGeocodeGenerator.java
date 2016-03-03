package com.eventbee.geocodes;
import java.util.*;
import com.eventbee.util.Address;
import com.eventbee.util.CoreConnector;
abstract class AbstractGeocodeGenerator implements GeocodeGenerator{
	public com.eventbee.util.Address address=null;

public void setAddress(com.eventbee.util.Address address){
	this.address=address;
}
public com.eventbee.util.Address getAddress(){
	return address;
}
abstract public Geocode getGeocodes();

public String getRawResponseData(String url,String[] params,String [] values,String method){
	CoreConnector conector=new CoreConnector(url);
	conector.setArguments(values,params);
	conector.setTimeout(30000);
	if("get".equals(method))
	return conector.MGet();
	else
	return conector.MPost();
}

}