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
  //System.out.println("wantedfile is:"+wantedfile);
  String filter=request.getParameter("filter");
  //System.out.println("filter"+filter);
  String hrvalue=request.getParameter("hrvalue");
  String hr2=request.getParameter("hr2");
  String hr3=request.getParameter("hr3");
  String newdate=request.getParameter("newdate");
  String mns21=request.getParameter("mns21");
  String mns22=request.getParameter("mns22");
  String mns31=request.getParameter("mns31");
  String mns32=request.getParameter("mns32");
 
  StringBuffer sb = new StringBuffer();
  ArrayList arr=new ArrayList();
  long start = System.currentTimeMillis();
  System.out.println("start is:"+start);
  Process p=null;
  try{
        System.out.println("in try");
		if("fullfile".equals(filter)){
         p= Runtime.getRuntime().exec("/bin/sh /mnt/jboss/runner/standalone/active/main.war/utiltools/adminLogScripts/hitsTotHours.sh "+wantedfile);
		}else if("latestfiles".equals(filter)){
		 
		 p= Runtime.getRuntime().exec("/bin/sh /mnt/jboss/runner/standalone/active/main.war/utiltools/adminLogScripts/hitsWithinHour.sh "+newdate+" "+hrvalue+" "+wantedfile);
		
		}else if("perticulartime".equals(filter)){

		p= Runtime.getRuntime().exec("/bin/sh /mnt/jboss/runner/standalone/active/main.war/utiltools/adminLogScripts/hitsCountAtTime.sh "+newdate+" "+hr2+" "+mns21+" "+mns22+" "+mns22+" "+wantedfile);
		
		
		}else if("countlist".equals(filter)){
		p= Runtime.getRuntime().exec("/bin/sh /mnt/jboss/runner/standalone/active/main.war/utiltools/adminLogScripts/hitsInMiutes.sh "+newdate+" "+hr3+" "+mns31+" "+mns32+" "+mns32+" "+wantedfile);
		}else{
			System.out.println("Failed");
		}
		 System.out.println("after execute command");
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

