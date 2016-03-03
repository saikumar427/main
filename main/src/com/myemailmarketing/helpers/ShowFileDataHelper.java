package com.myemailmarketing.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.csvreader.CsvReader;
import com.eventbee.beans.Entity;
import com.eventbee.general.EbeeConstantsF;
import com.eventbee.general.EventBeeValidations;
import com.myemailmarketing.beans.MemberData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ShowFileDataHelper {

	private int validCount;
	private ArrayList<String> inValidData=new ArrayList<String>();
	
	public int getValidCount() {
		return validCount;
	}
	public void setValidCount(int validCount) {
		this.validCount = validCount;
	}
	public ArrayList<String> getInValidData() {
		return inValidData;
	}
	public void setInValidData(ArrayList<String> inValidData) {
		this.inValidData = inValidData;
	}
	
	public static void deleteFile(String userId,String listId,String ext)
	{
		String filepath=EbeeConstantsF.get("temp.upload.filepath","C:\\uploads");
		String fileName="maillist_"+userId+"_"+listId+".csv";
		File f1=new File(filepath+"/"+fileName);
		f1.delete();
	}

	public static ArrayList<MemberData> getEntireFileData(String filePath,String userId,String listId,String action,String ext){
		
		ArrayList<MemberData> memberDataList=new ArrayList<MemberData>();
		try{
			String filepath=EbeeConstantsF.get("temp.upload.filepath","C:\\uploads");
			String fileName="maillist_"+userId+"_"+listId+".csv";
			FileInputStream fileInputStream = new FileInputStream(filepath+"/"+fileName);
			fileName=filepath+"/"+fileName;
			ArrayList<String> filecontent=new ArrayList<String>();
			MemberData memberDataObj=null;
			int i=ext.indexOf(".xls");
			if(i!=-1){
			Row row = null;
			Cell cell = null;
			Iterator<Row> rowIterator = null;
			Iterator<Cell> cellIterator = null;
			int numSheets = 0;
			Workbook workbook = WorkbookFactory.create(fileInputStream);
			numSheets = workbook.getNumberOfSheets();
			for(int x = 0; x < numSheets; x++) {
			Sheet sheet = workbook.getSheetAt(x);
			rowIterator = sheet.iterator();
			while (rowIterator.hasNext())
				{
			row = rowIterator.next();
			cellIterator = row.iterator();
			while (cellIterator.hasNext())
				{
			memberDataObj=new MemberData();
			cell = cellIterator.next();
			String valid="";
				boolean value=EventBeeValidations.isValidFromEmail(cell.toString().trim());
				if(value)
					valid="yes";
				else
					valid="no";
			if("yes".equals(valid)){
				if(filecontent.contains(cell.toString()) && filecontent.size()!=0){}
				else{
					filecontent.add(cell.toString());   
					memberDataObj.setEmail(cell.toString());
					memberDataList.add(memberDataObj);
				}}
				}
		}
			}}
				else
				{ 
					CsvReader products = new CsvReader(fileName);
					products.readHeaders();
					String headers[]=products.getHeaders();
					for(int x=0;x<headers.length;x++)
					{
						memberDataObj=new MemberData();
						String valid="";
						boolean value=EventBeeValidations.isValidFromEmail(headers[x]);
						if(value)
							valid="yes";
						else
							valid="no";
						if("yes".equals(valid)){
							if(filecontent.contains(headers[x]) && filecontent.size()!=0){}
							else{
							filecontent.add(headers[x]);   
							memberDataObj.setEmail(headers[x]);
							memberDataList.add(memberDataObj);
							}}
					}	
					while (products.readRecord())
					{
						String array[]=products.getValues();
						for(int k=0;k<array.length;k++)
						{
							memberDataObj=new MemberData();
							String valid="";
								boolean value=EventBeeValidations.isValidFromEmail(array[k]);
								if(value)
									valid="yes";
								else
									valid="no";
							if("yes".equals(valid)){
								if(filecontent.contains(array[k]) && filecontent.size()!=0){}
								else{
									filecontent.add(array[k]);   
									memberDataObj.setEmail(array[k]);
									memberDataList.add(memberDataObj);
								}}
						}
					}
					products.close();
				
				}
		if("delete".equals(action)){
		File f1=new File(filepath+"/"+fileName);
		f1.delete();
		}
		}
		catch(Exception e){
			System.out.println("Exception while processing the file data in File Upload in Mail Lists"+e.getMessage());
		}
		return memberDataList;
	}
	
	public static ArrayList<String> getFileData(String filePath,String userId,String listId,String ext){
		ArrayList<String> fileData=new ArrayList<String>();
		try{
			System.out.println("thte extension is"+ext);
			String filepath=EbeeConstantsF.get("temp.upload.filepath","C:\\uploads");
			String fileName="maillist_"+userId+"_"+listId+".csv";
			FileInputStream fileInputStream = new FileInputStream(filepath+"/"+fileName);
			fileName=filepath+"/"+fileName;
			int i=ext.indexOf(".xls");
			if(i!=-1){
			Row row = null;
			Cell cell = null;
			Iterator<Row> rowIterator = null;
			Iterator<Cell> cellIterator = null;
			int numSheets = 0;
			Workbook workbook = WorkbookFactory.create(fileInputStream);
			numSheets = workbook.getNumberOfSheets();
			for(int x = 0; x < numSheets; x++) {
			Sheet sheet = workbook.getSheetAt(x);
			rowIterator = sheet.iterator();
			while (rowIterator.hasNext())
				{
				row = rowIterator.next();
				cellIterator = row.iterator();
			while (cellIterator.hasNext())
				{
			cell = cellIterator.next();
			fileData.add(cell.toString());
				}}}}
			else
			{
				CsvReader products = new CsvReader(fileName);
				products.readHeaders();
				String headers[]=products.getHeaders();
				for(int j=0;j<headers.length;j++)
					fileData.add(headers[j]);
				while (products.readRecord())
				{
					String array[]=products.getValues();
					for(int k=0;k<array.length;k++)
						fileData.add(array[k]);
				}
				products.close();
					}}
			catch(Exception e){
			System.out.println("Exception"+e.getMessage());
			}
		return fileData;
		}

	public static int isValidEmail(String email){
		try{
			if(email==null||"".equals(email.trim()))
			return 1;
			else
			{
				int s1=email.indexOf('@');
				int s2=email.indexOf('.');
				if ((s1==-1)||(s2==-1))
					return 2;
			}
			}
			catch(Exception e){
			System.out.println("the exception occeured in isValidEmail Method");
			}
		return 0;
			}
	

	public static int getValidCount(ArrayList fileData){
	    int validcount=0;
	    for(int i=0;i<fileData.size();i++){
	    	String email=fileData.get(i).toString().trim();
	    	Pattern p = Pattern.compile("^[a-zA-Z0-9!#$%&'*+/=?^_`():{|}<>~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^:_`(){|}<>~-]+)*@(?:[a-zA-Z0-9!#$%&'*+/=?:^_`(){|}<>~-](?:[_a-zA-Z0-9-'!#$%&'*+/=?^:_`(){|}<>~-]*[_a-zA-Z0-9-'!#$%&'*+/=?:^_`(){|}<>~-])?\\.)+[_a-zA-Z0-9-'!#$%&'*+/=?:^_`(){|}<>~-](?:[_a-zA-Z0-9-'!#$%&'*+/=?^:_`(){|}<>~-]*[_a-zA-Z0-9-'!#$%&'*+/=?:^_`(){|}<>~-])?$");
		    Matcher m = p.matcher(email);
		    boolean matchFound = m.matches();
		    if (matchFound)
		    validcount++;  
		}
	    return validcount;
	}
	    
		
}
	
