
package com.eventbee.geocodes;
import java.util.*;
import com.eventbee.util.Address;
public interface GeocodeGenerator{
public void setAddress(com.eventbee.util.Address address);
	Geocode getGeocodes();
}
