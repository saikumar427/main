<%@ page import="java.util.*,org.json.*,com.eventbee.namedquery.*"%>
<%
	String inpcols[] = {"key","index","defaultvalue","type"};
	String outpcols[] = {"key","column","defaultvalue"};
	
	JSONObject jsonobj = new JSONObject();
	JSONArray jsonarray =new JSONArray();
	
	String queryname = request.getParameter("queryname");
	String sql = request.getParameter("query");
	String module = request.getParameter("modulename");
	String dsname = request.getParameter("dsn");
	String incount = request.getParameter("inpnumber");
	String outcount = request.getParameter("outpnumber");
	int inpcount = Integer.parseInt((incount==null) ? "0" : incount);
	int outpcount = Integer.parseInt((outcount==null) ? "0" : outcount);
	
	String inparray[][] = new String[inpcount][3];
	String outparray[][] = new String[outpcount][3];
	String values[];
	String nextval = "select nextval('qid_seq')";
	String dyna_query = "insert into dynaqueries(qid,module,queryname,sql,dsname) values(?,?,?,?,?)";
	String inparams_query = "insert into queryinparams(paramkey,paramindex,paramdefault,paramtype,qid) values(?,?,?,?,?)";
	String outparams_query = "insert into queryoutparams(paramkey,paramcolumn,paramdefault,qid) values(?,?,?,?)";
	
	if(queryname==null || queryname.length()==0){
		jsonarray.put("Please enter query name.");				
	}
	if(sql==null || sql.length()==0){
		jsonarray.put("Please enter query.");
	}
	if(module==null || module.length()==0){
		jsonarray.put("Please enter module name.");
	}
	if(jsonarray.length()!=0){
		jsonobj.put("status","error");
		jsonobj.put("errors",jsonarray);
	}else{
		String qid=NQDbUtil.getVal(nextval,null,null);
		NQStatusObj statobj = NQDbUtil.executeUpdateQuery(dyna_query,new String[]{qid,module,queryname,sql,dsname},new String[]{"INT","STR","STR","STR","STR"});
		jsonobj.put("status","success");
		jsonobj.put("statobjstatus",statobj.getStatus());
		if(!statobj.getStatus()){
			jsonobj.put("errormsg","Exception Occurred at dynaquery: "+statobj.getErrorMsg());
		}else{
			if(inpcount!=0){
				for(int i=0;i<inpcount;i++){
					values = new String[inpcols.length+1];
					for(int j=0;j<inpcols.length;j++){
						values[j] = request.getParameter("inp"+inpcols[j]+i).trim();
						//inparray[i][j] =request.getParameter("inp"+inpcols[j]+i);					
					}
					values[inpcols.length] = qid;					
					statobj = NQDbUtil.executeUpdateQuery(inparams_query,values,new String[]{"STR","INT","STR","STR","INT"});
					jsonobj.put("statobjstatus",statobj.getStatus());
					if(!statobj.getStatus()){
						jsonobj.put("errormsg","Exception Occurred at queryinparams: "+statobj.getErrorMsg());
					}
				}
			}
			if(outpcount!=0){
				for(int i=0;i<outpcount;i++){
					values = new String[outpcols.length+1];
					for(int j=0;j<outpcols.length;j++){
						values[j] = request.getParameter("outp"+outpcols[j]+i).trim();
						//outparray[i][j] =request.getParameter("outp"+outpcols[j]+i);					
					}
					values[outpcols.length] = qid;
					statobj = NQDbUtil.executeUpdateQuery(outparams_query,values,new String[]{"STR","STR","STR","INT"});
					jsonobj.put("statobjstatus",statobj.getStatus());
					if(!statobj.getStatus()){
						jsonobj.put("errormsg","Exception Occurred at queryoutparams: "+statobj.getErrorMsg());
					}
				}
			}
		}
	}
	out.println(jsonobj);
%>
<%!
public void insertIntoInParams(String inp[][]){
	for(int i=0;i<inp.length;i++){
		for(int j=0;j<inp[i].length;j++){
			System.out.println("inp["+i+"]["+j+"]: "+inp[i][j]);
		}
	}
}
%>