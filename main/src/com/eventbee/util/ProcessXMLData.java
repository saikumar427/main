package com.eventbee.util;
import java.io.*;
import java.util.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ProcessXMLData{


   public static Document getDocument (InputStream ipstrm){
		Document doc=null;
    	try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.parse (ipstrm);
            // normalize text representation
            doc.getDocumentElement ().normalize ();
		}//try
		catch (SAXParseException err) {
        }
        catch (SAXException e) {
        }
        catch (Throwable t) {
        }
        return doc;
    }//end of getDocument




	public static Map getProcessedXMLData(Document doc,String tagName,String [] tagElements){
		String nvalue="";
		Map resMap=new HashMap();

		try{
			NodeList listOfNodes = doc.getElementsByTagName(tagName);

			for(int s=0; s<listOfNodes.getLength() ; s++){
				Node firstNode = listOfNodes.item(s);
				if(firstNode.getNodeType() == Node.ELEMENT_NODE){

					Element firstElement = (Element)firstNode;
					for(int i=0;i<tagElements.length;i++){
						NodeList nList = firstElement.getElementsByTagName(tagElements[i]);
						Element nElement = (Element)nList.item(0);
						NodeList textnList = nElement.getChildNodes();
						nvalue=((Node)textnList.item(0)).getNodeValue().trim();
						resMap.put((String)tagElements[i],nvalue);
					}
				}//if
			}//for
		}//try
		catch(Exception e){
		}

		return resMap;
	}//end of getProcessedXMLData












public static Vector getProcessedXMLList(Document doc,String tagName,String [] tagElements){
		String nvalue="";
		Vector vec=new Vector();
		Map resMap=new HashMap();

		try{
			NodeList listOfNodes = doc.getElementsByTagName(tagName);

			for(int s=0; s<listOfNodes.getLength() ; s++){
				Node firstNode = listOfNodes.item(s);
				if(firstNode.getNodeType() == Node.ELEMENT_NODE){
resMap=new HashMap();
					Element firstElement = (Element)firstNode;
					for(int i=0;i<tagElements.length;i++){
						NodeList nList = firstElement.getElementsByTagName(tagElements[i]);
						Element nElement = (Element)nList.item(0);
						NodeList textnList = nElement.getChildNodes();
						nvalue=((Node)textnList.item(0)).getNodeValue().trim();
						resMap.put((String)tagElements[i],nvalue);
					}
				}//if
				vec.add(resMap);
			}//for
		}//try
		catch(Exception e){
		}

		return vec;
	}//end of getP










}//class