<%@page import="java.util.*,java.net.*,java.text.*,java.io.*,org.json.*"%>
<%!
   private static final long MAX_PROCESS_RUNNING_TIME = 30 * 1000;   
   String conv2Html(int i) {
		if (i == '&') return "&amp;";
		else if (i == '<') return "&lt;";
		else if (i == '>') return "&gt;";
		else if (i == '"') return "&quot;";
		else return "" + (char) i;
	}

      String conv2Html(String st) {
	StringBuffer buf = new StringBuffer();
	for (int i = 0; i < st.length(); i++) {
	  buf.append(conv2Html(st.charAt(i)));
	}
      return buf.toString();
    }
%>
<%
  String wantedfile=request.getParameter("filepath");
  String filter=request.getParameter("filter");
  String linescount=request.getParameter("lcount");
  String pattern=request.getParameter("pattern");
  String disptype=request.getParameter("disptype");
  String linesfrom=request.getParameter("linesfrom");
  if(linescount==null)linescount="";
  if(linesfrom==null)linesfrom="";
  StringBuffer sb = new StringBuffer();
  ArrayList arr=new ArrayList();
  long start = System.currentTimeMillis();
  Process p=null;
  try{
   if("lines".equals(filter)){
     if("top".equals(disptype)) 
       p= Runtime.getRuntime().exec("/bin/sh /mnt/jboss/runner/standalone/active/main.war/utiltools/adminLogScripts/srchFromTop.sh "+linescount+" "+wantedfile);
     else if("bottom".equals(disptype))
       p= Runtime.getRuntime().exec("/bin/sh /mnt/jboss/runner/standalone/active/main.war/utiltools/adminLogScripts/srchFromBottom.sh "+linescount+" "+wantedfile);
     else if("middle".equals(disptype)){
      int lcount=0;
      if(!"".equals(linescount) && !"".equals(linesfrom))
      try{
      lcount=Integer.parseInt(linescount)+Integer.parseInt(linesfrom);
      }catch(Exception e){System.out.println("Exception occured at parsing integer");}
      p= Runtime.getRuntime().exec("/bin/sh /mnt/jboss/runner/standalone/active/main.war/utiltools/adminLogScripts/srchFromLineNo.sh "+lcount+" "+wantedfile+" "+linescount);
    }
   }else if("pattern".equals(filter))
     p= Runtime.getRuntime().exec("/bin/sh /mnt/jboss/runner/standalone/active/main.war/utiltools/adminLogScripts/srchByPattern.sh "+pattern+" "+wantedfile);
   
  
   if(p!=null) p.waitFor();
   BufferedInputStream ls_in = new BufferedInputStream(p.getInputStream());
   BufferedInputStream ls_err = new BufferedInputStream(p.getErrorStream());
   boolean end = false;
   while (!end) {
        int c = 0;
	while ((ls_err.available() > 0) && (++c <= 1000)) {
          sb.append(conv2Html(ls_err.read()));
        }
	c = 0;
	while ((ls_in.available() > 0) && (++c <= 1000)) {
	 sb.append(conv2Html(ls_in.read()));
         }
       try {
        	
                p.exitValue();
	        while (ls_err.available() > 0){
		 sb.append(conv2Html(ls_err.read()));
                }		
                while (ls_in.available() > 0){
		sb.append(conv2Html(ls_in.read()));
               }
                
             end = true;
	  }catch (IllegalThreadStateException ex) {
	}
    if (System.currentTimeMillis() - start > MAX_PROCESS_RUNNING_TIME) {
	p.destroy();
	end = true;
	sb.append("!!!! Process has timed out, destroyed !!!!!");
     }
   try {
	Thread.sleep(50);
	}
  catch (InterruptedException ie) {}
    arr.add(sb.toString());
    }
   }catch(Exception e){
   System.out.println("Exception :"+e.getMessage());
  }
  out.println(arr.toString());	  
  
  
 		
%>

