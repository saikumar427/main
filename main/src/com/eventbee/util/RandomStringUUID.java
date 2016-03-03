package com.eventbee.util;
import java.util.UUID;
public class RandomStringUUID {
	
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		return randomUUIDString;
	}
	
}
