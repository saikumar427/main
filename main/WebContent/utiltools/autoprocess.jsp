<%@ page import="org.json.*,java.util.regex.*,java.util.*"%>
<%
String type = request.getParameter("qtype");	
String query = request.getParameter("query");
String noofinp = request.getParameter("inpnumber");
String noofoutp = request.getParameter("outpnumber");
%>
<%!
String inpcols[] = {"Key","Index","DefaultValue","Type"};
String outpcols[] = {"Key","Column","DefaultValue"};

/*bild html elements to input parameters and output parameters based upon the count*/
public String getHtmlElements(int count,String name,String[] cols){
	StringBuilder sb = new StringBuilder();	
	sb.append("<table align='center'>");
	if(count!=0){		
		sb.append("<tr>");
		sb.append("<td class='tablehead' align='center'>"+cols[0]+"</td>");
		sb.append("<td class='tablehead' align='center'>"+cols[1]+"</td>");
		sb.append("<td class='tablehead' align='center'>"+cols[2]+"</td>");
		if(name.equals("inp")){
			sb.append("<td class='tablehead' align='center'>"+cols[3]+"</td>");
		}
		sb.append("</tr>");
		for(int i=0;i<count;i++){			
			int index = 0;
			String paramtype ="";
			String icval = "";
			String readonly = "";			
			sb.append("<tr>");
			if(name.equals("inp")){
				readonly = "readonly='readonly'";
				index = i + 1;
				icval = ""+index;				
				paramtype = "<td><select name='"+name+cols[3].trim().toLowerCase()+i+"'><option selected='selected' value='STR'>VARCHAR</option><option value='INT'>INTEGER</option><option value='DOUBLE'>DOUBLE</option><option value='DT'>DATE</option></select></td>";
			}
			sb.append("<td><input type='text' name='"+name+cols[0].trim().toLowerCase()+i+"' ></td>");
			sb.append("<td><input type='text' name='"+name+cols[1].trim().toLowerCase()+i+"' value='"+icval+"' "+readonly+"></td>");
			sb.append("<td><input type='text' name='"+name+cols[2].trim().toLowerCase()+i+"'></td>");
			sb.append(paramtype);
			sb.append("</tr>");
		}
	}else{
		sb.append("<tr>");
		sb.append("<td class='tabledata'></td>");
		sb.append("<td class='tabledata'>No Parameters</td>");
		sb.append("</tr>");
	}
	sb.append("</table>");
	return sb.toString();
}
%>
<%
	JSONObject jsonobj = new JSONObject();
	JSONArray jsonarray =new JSONArray();	
	int inpcount=0;
	int outpcount=0;
	String name = "";
	String htmlelm = "";
	String outphtml = "";
	if(type==null){
		jsonarray.put("select radio button.");	
	}
	if(query==null || query.length()==0){
		jsonarray.put("Please enter query.");
	}	
	if(jsonarray.length()==0){
		if(noofinp!=null)
			inpcount = Integer.parseInt(noofinp);
		if(noofoutp!=null)
			outpcount = Integer.parseInt(noofoutp);
		htmlelm = getHtmlElements(inpcount,"inp",inpcols);
		if(type.equals("select")){			
			outphtml = getHtmlElements(outpcount,"outp",outpcols); 
			jsonobj.put("outphtml",outphtml);
		}	
		jsonobj.put("inphtml",htmlelm);
	}else{
		jsonobj.put("status","error");
		jsonobj.put("errors",jsonarray);
	}
	out.println(jsonobj);
%>
