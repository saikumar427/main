package com.eventbee.util;
import java.net.*;
import java.util.*;
import java.io.*;
import com.eventbee.general.*;

public class CoreConnector extends Thread {
	private int 		timeout = 10000;
	private int 		bufferLength = 10000;
	private String 		message;
	private String 		source_url;
	private String 		query;
	private String 		sBodyText=null;
	private boolean 	done = false;
	private boolean 	_transportMethodIsPost = false;
	private String      _contentType="application/x-www-form-urlencoded";
	private Exception 	_lastException = null;
	private DataOutputStream server=null;
	private BufferedReader in=null;
	public static final String CLASS_NAME="com.eventbee.util.CoreConnector";

	public CoreConnector() {
		source_url = null;
		query = "";
		String timeOutVal= System.getProperty("CORE_REQ_TIMEOUT");
		if(timeOutVal != null && timeOutVal.length() > 0) {
			try {
				timeout=Integer.parseInt(timeOutVal);
			} catch (NumberFormatException e) {
			}
		}
	}
	public CoreConnector(String p_url) {
		source_url = p_url;
		query = "";
		String timeOutVal= System.getProperty("CORE_REQ_TIMEOUT");
		if(timeOutVal != null && timeOutVal.length() > 0) {
			try {
				timeout=Integer.parseInt(timeOutVal);
			} catch (NumberFormatException e) {
			}
		}
	}
	public CoreConnector(String p_url, String p_query,  int p_length) {
		source_url = p_url;
		query = p_query;
	    bufferLength = p_length;
	}
	public void setTimeout(int p_timeout) {
		timeout = p_timeout;
	}
	public void setBufferInitLength(int p_length) {
		bufferLength = p_length;
	}
	public void setURL(String p_url) {
		source_url = p_url;
	}
	public void setQuery(String p_query) {
		query = p_query;
	}
	public String getQuery() {
		return query;
	}
	public Exception getLastException() {
		return _lastException;
	}
	public boolean setArgument(String arg, String fldName) {
	    if(arg == null) return false;
	    StringBuffer buf=new StringBuffer(query);
	    buf.append(fldName);
	    buf.append("=");
	    buf.append(URLEncoder.encode(arg.trim()));
	    buf.append("&");
	    query=buf.toString();
		return true;
	}

	public boolean setArguments(String args[], String fldNames[]) {
		if(args == null || args.length != fldNames.length) return false;
		StringBuffer buf=new StringBuffer(query);
		for(int i = 0; i < args.length; i++) {
			if(fldNames[i]!="" && fldNames[i]!=null)
			{
			    buf.append(fldNames[i]);
	   		 buf.append("=");
	   		 buf.append(URLEncoder.encode(args[i].trim()));
	   		 buf.append("&");
			}
		}
		query=buf.toString();
		return true;
	}

	public boolean setArguments(Map params) {
		if(params == null) return false;
		StringBuffer buf=new StringBuffer(query);
		String attribKey="";
		String attribVal="";
		for (Iterator keys = params.keySet().iterator(); keys.hasNext(); ) {
			attribKey = (String) keys.next();
			attribVal=(String)params.get(attribKey);
			if(attribVal!=null){
			    buf.append(attribKey);
	   		 buf.append("=");
	   		 buf.append(URLEncoder.encode(attribVal.trim()));
	   		 buf.append("&");
	   	 }
		}
		query=buf.toString();
		return true;
	}
	public String runTheThread() {
	    message = null;
	    _lastException = null;
	    setName(Thread.currentThread().getName());
	    done=false;
	    start();
	    try {
		    join(timeout);
	    } catch (InterruptedException e) { }
	    if (!done){
	   		//System.out.println("M_ERROR: Connection time out from URL:"+source_url+", for query:"+query+". Time out set to:"+timeout);
			Exception e=new Exception("M_ERROR: Connection time out from URL:"+source_url+", for query:"+query+". Time out set to:"+timeout);
	    }
	   	return message;
	}
	public String MPost() {
	    _transportMethodIsPost = true;
	    _contentType="application/x-www-form-urlencoded";
	    query += "submit=Submit\n";
		return runTheThread();
	}
	public String MBodyPost(String bodyText) {
	    _transportMethodIsPost = true;
	    _contentType="text/xml";
	    query=bodyText;
	    return runTheThread();
	}
	public String MGet() {
	    source_url += "?" + query;
		_transportMethodIsPost = false;
		_contentType="application/x-www-form-urlencoded";
	    return runTheThread();
	}
	public void run() {
	    try{
	   	    GetOrPostData();
			done = true;
		} catch (Exception e) {
			System.out.println("***** NETWORK ERROR FROM CORE CONNECTOR :" + e.toString());
			e.printStackTrace();
			_lastException = e;
			message = null;
		}
		finally{
		    try {
		      if(in!=null){
		  		  in.close();
		  		  in=null;
		  	  }
		  	  if(server!=null){
		  		  server.close();
		  	      server=null;
		  	  }
		    } catch (Exception e) {}
		}
	}
	private void readMessage(URLConnection urlc)throws Exception{
	    StringBuffer buf = new StringBuffer(bufferLength);
	    in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
		String line;
		message = "";
		while((line = in.readLine()) != null) {
			buf.append(line.trim() + "\n");
		}
		in.close();
		in=null;
		message = buf.toString();
	}
	private void GetOrPostData() throws Exception{
		URL u = new URL(source_url);
		URLConnection urlc = u.openConnection();
		urlc.setDoOutput(true);
		urlc.setDoInput(true);
		urlc.setAllowUserInteraction(false);
		urlc.setUseCaches(false);
		urlc.setRequestProperty("Content-Type", _contentType);
		if(_transportMethodIsPost)
		    WriteToServer(urlc);
		readMessage(urlc);
	}
	private void WriteToServer(URLConnection urlc) throws Exception{
		server = new DataOutputStream(urlc.getOutputStream());
		server.writeBytes(query);
		server.flush();
		server.close();
		server=null;
	}
}
