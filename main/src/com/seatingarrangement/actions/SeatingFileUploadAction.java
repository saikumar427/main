package com.seatingarrangement.actions;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import com.eventbee.general.EbeeConstantsF;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;
import com.seatingarrangement.dbhelpers.SeatingFileDataHelper;;

public class SeatingFileUploadAction extends ActionSupport implements Preparable,ValidationAware{
	
	private File upload;//The actual file
    private String uploadContentType; //The content type of the file
    private String uploadFileName; //The uploaded file name
    private String fileCaption;//The caption of the file entered by user
    private String imgName;
    private String fullWebPath="";
    private String venue_id="";
    private String section_id="";
    private String venuename="";
    private String noofrows="";
    private String noofcols="";
    private  ArrayList<String> Fields=new ArrayList<String>();
    public ArrayList<String> getFields() {
		return Fields;
	}
	public void setFields(ArrayList<String> fields) {
		Fields = fields;
	}
	private ArrayList<HashMap<String,String>> rowmap=new ArrayList<HashMap<String,String>>();
    
    
	public ArrayList<HashMap<String, String>> getRowmap() {
		return rowmap;
	}
	public void setRowmap(ArrayList<HashMap<String, String>> rowmap) {
		this.rowmap = rowmap;
	}
	public String getVenuename() {
		return venuename;
	}
	public void setVenuename(String venuename) {
		this.venuename = venuename;
	}
	public String getNoofrows() {
		return noofrows;
	}
	public void setNoofrows(String noofrows) {
		this.noofrows = noofrows;
	}
	public String getNoofcols() {
		return noofcols;
	}
	public void setNoofcols(String noofcols) {
		this.noofcols = noofcols;
	}
	private ArrayList<String> fileData=new ArrayList<String>();
    
    public ArrayList<String> getFileData() {
		return fileData;
	}
	public void setFileData(ArrayList<String> fileData) {
		this.fileData = fileData;
	}
	private static final long serialVersionUID = -2442188849518238282L;
	public void prepare() throws Exception {
		
	}
	public String execute(){
		System.out.println("In execute method");
		return "input";
		
	}
	public String save(){
		try {
			
			System.out.println("In savemethod sectionid , venueid val is"+venue_id+".."+section_id);
			String filepath=EbeeConstantsF.get("temp.upload.filepath","C:\\uploads");
			
			/*fullWebPath=EbeeConstantsF.get("temp.upload.filepath","http://localhost:8080/home/images/");
			fullWebPath+="/"+imgName;*/
			
			File theFile = new File(uploadFileName);
			String ext=uploadFileName.substring(uploadFileName.lastIndexOf("."));
			System.out.println("ext"+ext);
			/*if(!(".csv".equals(ext))){
				System.out.println("error");
				//return "input";
			}*/
			String modifiedName="section_"+venue_id+"_"+section_id+".csv";
			System.out.println("upload filename:: "+uploadFileName+" ,upload:: "+upload+" ,modifiedname:: "+modifiedName);
			File fileToCreate = new File(filepath, modifiedName);
			FileUtils.copyFile(this.upload, fileToCreate);
			
			//File nefile=new File(filepath,modifiedName);
			
			fullWebPath=fileToCreate.getAbsolutePath();
			System.out.println("full path:: "+fullWebPath);
			read();
			
			} 
		catch (Exception e) {
			System.out.println("Exception in saving file"+e.getMessage());
			addActionError(e.getMessage());
			}
			return "success";
		}
	public void read(){
		SeatingFileDataHelper.readAndInsertSeatsData(fullWebPath,venue_id,section_id);
		
	}
	
	public String exportfile(){
		
		System.out.println("we are in export method");
		Fields=SeatingFileDataHelper.populateSeatFields(venue_id, section_id);
		rowmap=SeatingFileDataHelper.getVenueSeats(venue_id,section_id,venuename);
		
		return "successofexport";
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getFileCaption() {
		return fileCaption;
	}
	public void setFileCaption(String fileCaption) {
		this.fileCaption = fileCaption;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getFullWebPath() {
		return fullWebPath;
	}
	public void setFullWebPath(String fullWebPath) {
		this.fullWebPath = fullWebPath;
	}
	public String getVenue_id() {
		return venue_id;
	}
	public void setVenue_id(String venue_id) {
		this.venue_id = venue_id;
	}
	public String getSection_id() {
		return section_id;
	}
	public void setSection_id(String section_id) {
		this.section_id = section_id;
	}
}
