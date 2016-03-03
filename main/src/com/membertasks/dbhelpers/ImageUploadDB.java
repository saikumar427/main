package com.membertasks.dbhelpers;
import com.eventbee.general.*;
public class ImageUploadDB {
	public static String getTempImgName()throws Exception{
		
		String imgid=null;
		String query="select nextval('seq_imageid') as imageid ";
		String imageId=DbUtil.getVal(query, null);
		imgid=EncodeNum.encodeNum(imageId);
		return imgid;

	}
}
