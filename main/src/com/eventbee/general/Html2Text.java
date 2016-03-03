package com.eventbee.general;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class Html2Text extends HTMLEditorKit.ParserCallback {
 StringBuffer s;

 public Html2Text() {}

 public void parse(Reader in) throws IOException {
   s = new StringBuffer();
   ParserDelegator delegator = new ParserDelegator();
   // the third parameter is TRUE to ignore charset directive
   delegator.parse(in, this, Boolean.TRUE);
 }

 public void handleText(char[] text, int pos) {
   s.append(text);
 }

 public String getText() {
   return s.toString();
 }

 public static String getPlainText(String finalContents) {
	 String textresult="";
   try {
   //  System.out.println("commming::");
     finalContents = finalContents.replaceAll("<br", "\n<br");
     InputStream is = new ByteArrayInputStream(finalContents.getBytes());
 	// read it with BufferedReader
   	 BufferedReader br = new BufferedReader(new InputStreamReader(is));
   	 Html2Text parser = new Html2Text();
     parser.parse(br);
     br.close();
     //System.out.println("res::"+parser.getText());
     textresult =parser.getText();     
   }
     catch (Exception e) {
    System.out.println("exception while parsing html 2 text::"+e.getMessage());
   }
     return textresult;
 }
}