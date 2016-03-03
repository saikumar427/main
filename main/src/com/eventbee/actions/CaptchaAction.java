package com.eventbee.actions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

public class CaptchaAction  implements SessionAware {
	private String formid="";
	private Map session;

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}
	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}
	public void generate(){
		try{
		HttpServletResponse response=ServletActionContext.getResponse();
		BufferedImage image= new BufferedImage(80,40,BufferedImage.TYPE_BYTE_INDEXED);
		Font font=new Font("",Font.BOLD,15);
		Graphics g=image.createGraphics();
		g.setColor(Color.lightGray );
		g.fillRect(1,1,80,40);
		g.setColor(Color.black);
		String s=getRandomCaptchaWord();
		g.setFont(font);
		g.drawString(s,20,25);
		session.put("captcha_"+formid,s);
		ImageIO.write(image, "jpg", response.getOutputStream());
		}
		catch(Exception e)
		{
		}
	}
	public String getRandomCaptchaWord(){
		 char str1[]={'X','f','d','K','P','m','R','a','N','Y'};
		 char str2[]={'9','6','3','7','2'};

		 StringBuffer str=new StringBuffer();

		 Random randomGenerator = new Random();
		 for(int i=0;i<4;i++){
		 int randomInt = randomGenerator.nextInt(10);
		 str=str.append(str1[randomInt]);
		 }
		 int randomNum= randomGenerator.nextInt(5);

		  str.insert(randomNum,str2[randomNum]);

		return str.toString();
		}
}
