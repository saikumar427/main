package com.mycommunities.helpers;

import java.util.ArrayList;

import com.eventbee.beans.Entity;

public class CommunityManageHelper {

	public static ArrayList<Entity> fillTermTypes(){
		ArrayList<Entity> entityObj=new ArrayList<Entity>();
		entityObj.add(new Entity("Monthly","Monthly"));
		entityObj.add(new Entity("Quarterly","Quarterly"));
		entityObj.add(new Entity("Half yearly","Half yearly"));
		entityObj.add(new Entity("Annual","Annual"));
		entityObj.add(new Entity("Bi-Annual","Bi-Annual"));
		return entityObj;
	}
}
