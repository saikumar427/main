
package com.eventbee.geocodes;
import java.util.*;
import com.eventbee.util.Address;
public class Geocode{

	private String Latitude="";
	private String Longitude="";
	private boolean Status=false;


	public void setStatus(boolean Status){
				 this.Status=Status;
			}

	public boolean getStatus(){
				return Status;
}

public void setLatitude(String Latitude){
			 this.Latitude=Latitude;
		}

public String getLatitude(){
			return Latitude;
}


public void setLongitude(String Longitude){
			 this.Longitude=Longitude;
		}

public String getLongitude(){
			return Longitude;
}

}