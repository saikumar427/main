package com.eventmanage.actions.ticketing;
import com.event.beans.ticketing.GroupData;
import com.event.dbhelpers.EventHelperDB;
import com.event.dbhelpers.TicketsDB;
import com.eventbee.general.StatusObj;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;
public class GroupDetailsAction extends ActionSupport implements Preparable,ValidationAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8976345566747233238L;
	private String eid="";
	private String msgKey="";
	private String groupId="";
	private GroupData groupData;
	public void prepare(){
	
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public GroupData getGroupData() {
		return groupData;
	}
	public void setGroupData(GroupData groupData) {
		this.groupData = groupData;
	}
	public String save(){
		System.out.println("GroupDetailsAction in save method , groupname" +groupData.getGroupName());
		if("".equals(groupData.getGroupId()))
					msgKey="group.added";
			else
				msgKey="group.updated";
		
		StatusObj st=TicketsDB.saveGroupData(groupData, eid);
		try {
			msgKey=(new JSONObject().put("status","success").put("id",st.getData())).toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		System.out.println("removeGLobalEventCache: while adding group ticket");
		EventHelperDB.removeGLobalEventCache(eid, "remove", "ticketsinfo");
			return "success";
		}
	public String edit(){
		System.out.println("Group Edit Method");
		groupData=TicketsDB.getGroupInfo(groupId,eid);
		setGroupId(groupId);
		return "input";
	}
	
	public String createGroup(){
		return "creategroup";
		}
		
	
	
}
