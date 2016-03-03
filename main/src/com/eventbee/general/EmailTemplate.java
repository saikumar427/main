package com.eventbee.general;


public class  EmailTemplate{

	//SELECT fromemail,replytoemail,textformat,htmlformat,subjectformat FROM EMAIL_TEMPLATES WHERE unitid='13579' AND purpose='GUESTBOOK'
	static final String GET_TEMPLATE_DATA="SELECT fromemail,replytoemail,textformat,htmlformat,subjectformat FROM EMAIL_TEMPLATES WHERE unitid=? AND purpose=?";
static final String GET_CUSTOM_TEMPLATE_DATA="SELECT fromemail,replytoemail,textformat,htmlformat,subjectformat FROM EMAIL_TEMPLATES WHERE unitid=? AND groupid=? AND purpose=?";

	private String unitId = null;
	private String purpose = null;
    private String groupid = null;

	private String textFormat = null;
	private String  htmlFormat= null;

	private String subjectFormat = null;

	private String replyToMail = null;
	private String fromMail = null;

	public EmailTemplate(String unitId, String purpose){
		//System.out.println("in Email template");
		this.unitId=unitId;
		this.purpose=purpose;
		DBManager dbmanager=new DBManager();
		StatusObj statobj=dbmanager.executeSelectQuery(GET_TEMPLATE_DATA,new String[]{this.unitId,this.purpose});
		if(statobj != null && statobj.getStatus() && statobj.getCount()>0   ){

				fromMail=dbmanager.getValue(0,"fromemail","");
				replyToMail=dbmanager.getValue(0,"replytoemail","");
				textFormat=dbmanager.getValue(0,"textformat","");
				htmlFormat=dbmanager.getValue(0,"htmlformat","");
				subjectFormat=dbmanager.getValue(0,"subjectformat","");

		}else{
			statobj=dbmanager.executeSelectQuery(GET_TEMPLATE_DATA,new String[]{"0",this.purpose});
			if(statobj != null && statobj.getStatus() && statobj.getCount()>0   ){

					fromMail=dbmanager.getValue(0,"fromemail","");
					replyToMail=dbmanager.getValue(0,"replytoemail","");
					textFormat=dbmanager.getValue(0,"textformat","");
					htmlFormat=dbmanager.getValue(0,"htmlformat","");
					subjectFormat=dbmanager.getValue(0,"subjectformat","");

			}
		}

	}


	public EmailTemplate(String unitId, String purpose, String groupid){
				this.unitId=unitId;
				this.purpose=purpose;
				this.groupid=groupid;
				DBManager dbmanager=new DBManager();
				StatusObj statobj=dbmanager.executeSelectQuery(GET_CUSTOM_TEMPLATE_DATA,new String[]{this.unitId,this.groupid,this.purpose});
				if(statobj != null && statobj.getStatus() && statobj.getCount()>0   ){

						fromMail=dbmanager.getValue(0,"fromemail","");
						replyToMail=dbmanager.getValue(0,"replytoemail","");
						textFormat=dbmanager.getValue(0,"textformat","");
						htmlFormat=dbmanager.getValue(0,"htmlformat","");
						subjectFormat=dbmanager.getValue(0,"subjectformat","");

				}else{
					statobj=dbmanager.executeSelectQuery(GET_CUSTOM_TEMPLATE_DATA,new String[]{"0",this.groupid,this.purpose});
					if(statobj != null && statobj.getStatus() && statobj.getCount()>0   ){

							fromMail=dbmanager.getValue(0,"fromemail","");
							replyToMail=dbmanager.getValue(0,"replytoemail","");
							textFormat=dbmanager.getValue(0,"textformat","");
							htmlFormat=dbmanager.getValue(0,"htmlformat","");
							subjectFormat=dbmanager.getValue(0,"subjectformat","");

					}
				}

			}


	public String getFromMail(){
		return fromMail;
	}

	public String getReplyToMail(){
		return replyToMail;
	}

	public String getTextFormat(){
		return textFormat;
	}
	public String getHtmlFormat(){
		return htmlFormat;
	}

	public String getSubjectFormat(){
		return subjectFormat;
	}


}