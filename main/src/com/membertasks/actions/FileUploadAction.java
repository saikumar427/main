package com.membertasks.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ResourceBundle;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.csvreader.CsvReader;
import com.eventbee.general.EbeeConstantsF;
import com.membertasks.dbhelpers.ImageUploadDB;
import com.event.helpers.I18n;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class FileUploadAction extends MemberTasksWrapper{

	private static final long serialVersionUID = -2442188849518238282L;
	public void prepare() throws Exception {
		try {
			super.prepare();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	private File upload;//The actual file
    private String uploadContentType; //The content type of the file
    private String uploadFileName; //The uploaded file name
    private String fileCaption;//The caption of the file entered by user
    private String imgName;
    private String fullWebPath="";
	private String listId="";
	private String ext="";
	private String purpose="";
	private String priFldCount="";//for priority reg fields count
	private String type="";
	private String imgId="";
	//private String fileWebPath="";
	
	public String getImgId() {
		return imgId;
	}
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getPriFldCount() {
		return priFldCount;
	}
	public void setPriFldCount(String priFldCount) {
		this.priFldCount = priFldCount;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
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
	public String execute(){
		if("filesupload".equals(type))
			return "fileinput";
		else
			return INPUT;
	}
	
	
	public String saveFile()
	{
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		try {
			
			
			String filepath=EbeeConstantsF.get("filewidget.upload.filepath","C:\\uploads");
			fullWebPath=EbeeConstantsF.get("filewidget.upload.webpath","http://localhost:8080/home/images/photos/tempupload/");
			File theFile = new File(uploadFileName);
			ext=uploadFileName.substring(uploadFileName.lastIndexOf("."));
			ext=ext.toLowerCase();
			ArrayList<String> list = new ArrayList<String>(Arrays.asList(".xls",".xlsx", ".doc", ".docx",".ppt",".pdf",".csv",".txt",".png",".gif",".jpg",".jpeg",".bmp"));
			
			if(list.contains(ext)){
				imgId=ImageUploadDB.getTempImgName();
				String modfiedName="buyer_"+imgId+ext;
				File fileToCreate = new File(filepath, modfiedName);
				FileUtils.copyFile(this.upload, fileToCreate);
				fullWebPath+= "/"+modfiedName;
			}
			else
			{
				addActionError("This file format is not supported.");
				return "fileinput";
			}
		}
		catch(Exception e)
		{
			if(e.getMessage()==null)
				addActionError(resourceBundle.getString("iu.plz.select.valid.file.msg"));
			else
				addActionError(e.getMessage());
			return "fileinput";
		}
		return "files";
	}
	
	public String save(){
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		try {
			System.out.println("In save method list id val is"+listId);
			String filepath=EbeeConstantsF.get("temp.upload.filepath","C:\\uploads");
			fullWebPath=EbeeConstantsF.get("temp.upload.filepath","http://localhost:8080/home/images/");
			File theFile = new File(uploadFileName);
			ext=uploadFileName.substring(uploadFileName.lastIndexOf("."));
			int i=ext.indexOf(".xls");
			int j=ext.indexOf(".csv");
			if(i!=-1 || j!=-1)
			{
				if("priority".equalsIgnoreCase(purpose) && i!=-1){
					Workbook workbook = WorkbookFactory.create(new FileInputStream(this.upload));
					int numSheets = 0;
					numSheets = workbook.getNumberOfSheets();
					Iterator<Row> rowIterator = null;
					Row row = null;
					for(int x = 0; x < numSheets; x++){
				        Sheet sheet = workbook.getSheetAt(x);
				        rowIterator = sheet.iterator();
				        while (rowIterator.hasNext()){	
				        	row = rowIterator.next();
				        	int cellcount=row.getLastCellNum();
				        	int priFldCountValue=Integer.parseInt(priFldCount);
				            if(priFldCountValue!=(cellcount)){
				            	if(cellcount==2)
								{
									//System.out.println("in count one"+cellcount);
									addActionError(resourceBundle.getString("pr.plz.up.err.lbl")+" "+priFldCount+" "+resourceBundle.getString("pr.xls.file.err.lbl"));
								}
								else
								{
									//System.out.println("in count two"+cellcount);
									addActionError(resourceBundle.getString("pr.plz.up.err.lbl")+" "+priFldCount+" "+resourceBundle.getString("pr.xls.flds.err.lbl"));
								}
				            	
								return INPUT;
				            }
				        }
				    }
				}
				else if("priority".equalsIgnoreCase(purpose) && j!=-1)
				{
					CsvReader products = new CsvReader(new FileReader(this.upload));
					products.readHeaders();
					String headers[]=products.getHeaders();
					int count=headers.length;
					int priFldCountValue=Integer.parseInt(priFldCount);
					if(priFldCountValue!=count){
						if(count==2)
						{
							//System.out.println("in count one"+count);
		            	addActionError(resourceBundle.getString("pr.plz.up.err.lbl")+" "+priFldCount+" "+resourceBundle.getString("pr.csv.file.err.lbl"));
						}
						else
						{
							//System.out.println("in count two"+count);
							addActionError(resourceBundle.getString("pr.plz.up.err.lbl")+" "+priFldCount+" "+resourceBundle.getString("pr.csv.flds.err.lbl"));	
						}
						return INPUT;
		            }
					
				}
			//String imgId=ImageUploadDB.getTempImgName();
			String modfiedName="maillist_"+userId+"_"+listId+".csv";
			fullWebPath+="/"+imgName;
			System.out.println("upload file name"+uploadFileName+"abc"+upload+"the File"+theFile);
			File fileToCreate = new File(filepath, modfiedName);
			FileUtils.copyFile(this.upload, fileToCreate);
			System.out.println("filepath"+filepath);
			//FileUtils.copyFileToDirectory(upload,theFile);
			File nefile=new File(filepath,modfiedName);
			//FileUtils.copyFileToDirectory(upload,theFile);
			fullWebPath=nefile.getAbsolutePath();
			} 
			else
			{
				addActionError(resourceBundle.getString("pr.suprt.file.frmt.msg.lbl"));
				return INPUT;
			}
		}
		catch (Exception e) {
			System.out.println("Exception in saving file"+e.getMessage());
			if(e.getMessage()==null)
				addActionError(resourceBundle.getString("iu.plz.select.valid.file.msg"));
					else
				//addActionError(e.getMessage());
			addActionError(e.getMessage());

			return INPUT;

			}
		return SUCCESS;
		}
}
