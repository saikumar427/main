package com.membertasks.actions;

import com.eventbee.general.helpers.FileReaderHelper;



public class FileReadAction extends MemberTasksWrapper{
	
	private static final long serialVersionUID = -7068125312248102125L;
	private String readFileName="";
	private String fileContent="";
	public String getReadFileName() {
		return readFileName;
	}
	public void setReadFileName(String readFileName) {
		this.readFileName = readFileName;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	public void prepare() throws Exception {
		try {
			super.prepare();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	public String execute(){
		System.out.println("FileReadActionExecute Method"+readFileName);
		fileContent=FileReaderHelper.getContents(readFileName);
		return SUCCESS;
	}
}
