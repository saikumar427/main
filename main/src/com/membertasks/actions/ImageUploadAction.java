package com.membertasks.actions;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import sun.misc.BASE64Decoder;

import com.membertasks.dbhelpers.ImageUploadDB;
import com.event.helpers.I18n;
import com.eventbee.general.EbeeConstantsF;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUploadAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6298711523703424701L;
	private String uid="";
	private String imageURL="";
	private File upload;//The actual file
    private String uploadContentType; //The content type of the file
    private String uploadFileName; //The uploaded file name
    private String fileCaption;//The caption of the file entered by user
    private String imgName;
    private String fullWebPath="";
    private String type="";
    private String purpose="";
    private String imgId="";

	public String getImgId() {
		return imgId;
	}
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFullWebPath() {
		return fullWebPath;
	}
	public void setFullWebPath(String fullWebPath) {
		this.fullWebPath = fullWebPath;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
		System.out.println("in set upload"+upload.getName());
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String execute(){
		System.out.println("In execute method");
		//System.getProperties().list(System.out);
		return INPUT;
	}
	public String save(){
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		try {
			
			String filepath=EbeeConstantsF.get("photo.image.path","C:\\uploads");
			String filepath1=EbeeConstantsF.get("thumbnail.photo.image.path","C:\\uploads1"); 
			String filepath2=EbeeConstantsF.get("big.photo.image.path","C:\\uploads2"); 
			String filepath3=EbeeConstantsF.get("smallthumbnail.photo.image.path","C:\\uploads3");	
			fullWebPath=EbeeConstantsF.get("photo.image.webpath","http://localhost:8080/home/images/");
			File theFile = new File(uploadFileName);
			String ext=uploadFileName.substring(uploadFileName.lastIndexOf("."));
			String imgId=ImageUploadDB.getTempImgName();
			imgName=imgId+ext;
			fullWebPath+="/"+imgName;
			File fileToCreate = new File(filepath, imgName);
			File fileToCreate1 = new File(filepath1, imgName);
			File fileToCreate2 = new File(filepath2, imgName);
			File fileToCreate3 = new File(filepath3, imgName);
			FileUtils.copyFile(this.upload, fileToCreate);
			
			/*FileUtils.copyFile(this.upload, fileToCreate1);
			FileUtils.copyFile(this.upload, fileToCreate2);
			FileUtils.copyFile(this.upload, fileToCreate3);*/
			//FileUtils.copyFileToDirectory(upload,theFile);
			
			if(createImages(300,300,imgName,filepath,filepath1)==1){
				FileUtils.copyFile(this.upload, fileToCreate1);
			}
			if(createImages(600,600,imgName,filepath,filepath2)==1){
				FileUtils.copyFile(this.upload, fileToCreate2);
			}
			if(createImages(100,100,imgName,filepath,filepath3)==1){
				FileUtils.copyFile(this.upload, fileToCreate3);
			}
			
			
			} 
		
		
		
		catch (Exception e) {
			System.out.println("Exception in saving file"+e.getMessage());
			if(e.getMessage()==null)
			addActionError(resourceBundle.getString("iu.plz.select.valid.file.msg"));
				else
			addActionError(e.getMessage());

			return INPUT;

			}
			return SUCCESS;
	}
	
	
	public String saveImageWUSIWUG(){
		ResourceBundle resourceBundle=I18n.getResourceBundle();
		try {
			
			System.out.println("in save methos is::"+uploadFileName);
			
			String filepath=EbeeConstantsF.get("photo.image.path","C:\\uploads");
			String filepath1=EbeeConstantsF.get("thumbnail.photo.image.path","C:\\uploads1"); 
			String filepath2=EbeeConstantsF.get("big.photo.image.path","C:\\uploads2"); 
			String filepath3=EbeeConstantsF.get("smallthumbnail.photo.image.path","C:\\uploads3");	
			fullWebPath=EbeeConstantsF.get("photo.image.webpath","http://localhost:8080/home/images/");
			File theFile = new File(uploadFileName);
			String ext=uploadFileName.substring(uploadFileName.lastIndexOf("."));
			String imgId=ImageUploadDB.getTempImgName();
			imgName=imgId+ext;
			fullWebPath+="/"+imgName;
			File fileToCreate = new File(filepath, imgName);
			File fileToCreate1 = new File(filepath1, imgName);
			File fileToCreate2 = new File(filepath2, imgName);
			File fileToCreate3 = new File(filepath3, imgName);
			FileUtils.copyFile(this.upload, fileToCreate);
			
			/*FileUtils.copyFile(this.upload, fileToCreate1);
			FileUtils.copyFile(this.upload, fileToCreate2);
			FileUtils.copyFile(this.upload, fileToCreate3);*/
			//FileUtils.copyFileToDirectory(upload,theFile);
			
			if(createImages(300,300,imgName,filepath,filepath1)==1){
				FileUtils.copyFile(this.upload, fileToCreate1);
			}
			if(createImages(600,600,imgName,filepath,filepath2)==1){
				FileUtils.copyFile(this.upload, fileToCreate2);
			}
			if(createImages(100,100,imgName,filepath,filepath3)==1){
				FileUtils.copyFile(this.upload, fileToCreate3);
			}
			
			} 
		
		catch (Exception e) {
			System.out.println("Exception in saving file"+e.getMessage());
			if(e.getMessage()==null)
			addActionError(resourceBundle.getString("iu.plz.select.valid.file.msg"));
				else
			addActionError(e.getMessage());

			return INPUT;

			}
			return "wysiwyg";
	}
	
	
	int createImages(int width,int height,String imgname,String srcimagedirpath,String descimagedirpath){

     	int i=0;
		try{
			File file=new File(srcimagedirpath +"/"+imgname);
			if(file.exists()){
			
			Image image = Toolkit.getDefaultToolkit().getImage(srcimagedirpath +"/"+imgname );
			MediaTracker mediaTracker = new MediaTracker(new Panel());
			
			mediaTracker.addImage(image, 0);
			mediaTracker.waitForID(0);
			int imageWidth = image.getWidth(null);
			int imageHeight = image.getHeight(null);
			int smallWidth = width;
			int smallHeight = height;

			/* creates images if the width of the image is larger than our specified length*/
				if(smallWidth>imageWidth){
				i=1;
				}else{
				System.out.println("smallWidth width/height = " + smallWidth + "/" + smallHeight);
				double smallRatio = (double)smallWidth / (double)smallHeight;
				double imageRatio1 = (double)imageWidth / (double)imageHeight;
				if (smallRatio < imageRatio1) 
   				smallHeight = (int)(smallWidth / imageRatio1);
				else 
				smallWidth = (int)(smallHeight * imageRatio1);

				BufferedImage smallImage = new BufferedImage(smallWidth,smallHeight, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics2D_1 = smallImage.createGraphics();
				graphics2D_1.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				graphics2D_1.drawImage(image, 0, 0, smallWidth, smallHeight, null);

				BufferedOutputStream bfout1 = new BufferedOutputStream(new FileOutputStream(descimagedirpath+"/"+imgname));
				JPEGImageEncoder encoder1 = JPEGCodec.createJPEGEncoder(bfout1);
				JPEGEncodeParam param1 = encoder1.getDefaultJPEGEncodeParam(smallImage);

				int quality1 = 100;
				quality1 = Math.max(0, Math.min(quality1, 100));
				param1.setQuality((float)quality1 / 100.0f, false);
				encoder1.setJPEGEncodeParam(param1);
				encoder1.encode(smallImage);
				i=2;

				}

			}

			}catch(Exception e){
			System.out.println("exeception while creating images"+e.getMessage());
			}

	return i;

	}
	
	
	public static String Base64toImg(String dataUrl){
		String parts[] = dataUrl.split(",");
		String imageString = parts[1];
		String filepath=EbeeConstantsF.get("photo.image.path","C:\\uploads");
		String fullWebPath=EbeeConstantsF.get("photo.image.webpath","http://localhost:8080/home/images/");
		String imgId="";
		try{
			imgId=ImageUploadDB.getTempImgName();
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] imageByte = decoder.decodeBuffer(imageString);
			BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageByte));
			String modfiedName=filepath+"img_"+imgId+".png";
			java.io.File outputfile = new java.io.File(modfiedName);
			fullWebPath = fullWebPath+"/img_"+imgId+".png";
		}catch(Exception e){
			System.out.println("while converting data image to image : "+e);
		}
		return fullWebPath;
	}
	
}
	
	
	
	


