package com.event.beans.ticketing;

import java.util.ArrayList;

public class GroupData {
private String groupName;
private String groupType="Loose";
private String groupDescription="";
private ArrayList groupTickets;
private String groupId;
private String position;
public String getPosition() {
	return position;
}
public void setPosition(String position) {
	this.position = position;
}
public String getGroupId() {
	return groupId;
}
public void setGroupId(String groupId) {
	this.groupId = groupId;
}
public String getGroupName() {
	return groupName;
}
public void setGroupName(String groupName) {
	this.groupName = groupName;
}
public String getGroupType() {
	return groupType;
}
public void setGroupType(String groupType) {
	this.groupType = groupType;
}
public String getGroupDescription() {
	return groupDescription;
}
public void setGroupDescription(String groupDescription) {
	this.groupDescription = groupDescription;
}
public ArrayList getGroupTickets() {
	return groupTickets;
}
public void setGroupTickets(ArrayList groupTickets) {
	this.groupTickets = groupTickets;
}

}
