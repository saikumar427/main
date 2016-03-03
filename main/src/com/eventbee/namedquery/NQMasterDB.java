package com.eventbee.namedquery;

import java.util.*;
import java.text.*;
import java.sql.*;
import java.sql.Date;

import org.apache.log4j.Logger;

public class  NQMasterDB{
	private static Logger log = Logger.getLogger(NQMasterDB.class);

	public static final String INT="INT";
	public static final String STR="STR";
	public static final String DOUBLE="DOUBLE";
	public static final String DT="DT";
    public String dsname=null;

	public static final String COLUMN_NAMES="COLUMN_NAMES";
	public static final String COLUMN_MAPPING="COLUMN_MAPPING";
	public static final String RECORDS="RECORDS";

   public NQMasterDB(String name){
	 dsname=name;
   }
public NQMasterDB(){}
	@SuppressWarnings({ "deprecation", "unchecked" })
	public HashMap getRecords(String query,Object[] inputparams,String[] inputpramtypes){
			java.sql.Connection con=null;
			java.sql.PreparedStatement pstmt=null;
			java.sql.ResultSet rs=null;
			Map columnMap=new HashMap();
			List records=null;
			HashMap resultMap=null;
			try{
				if(dsname!=null && dsname.length()!=0)
					con=NQEventbeeConnection.getConnection(dsname);
				else
					con=NQEventbeeConnection.getConnection();
				pstmt=con.prepareStatement(query);
				if(inputparams != null){
					boolean fortypes=false;
					if(inputpramtypes!=null && inputparams.length== inputpramtypes.length)fortypes=true;
					for(int i=0;i<inputparams.length;i++){
						if(fortypes){
							if(INT.equals(inputpramtypes[i]) ){pstmt.setInt( i+1, Integer.parseInt((String) inputparams[i]));}
							if(STR.equals(inputpramtypes[i]) )pstmt.setString(i+1,(String)inputparams[i]);
							if(DOUBLE.equals(inputpramtypes[i]) )pstmt.setDouble(i+1,Double.parseDouble((String) inputparams[i]));
							if(DT.equals(inputpramtypes[i]) )pstmt.setDate(i+1,(java.sql.Date) new java.util.Date((String)inputparams[i]));
							
						}else{
							pstmt.setString(i+1,(String)inputparams[i]);
						}
					}
				}


				rs=pstmt.executeQuery();
				if(rs.next()){

					records=new ArrayList();
					resultMap=new HashMap();
					ResultSetMetaData rsmd=rs.getMetaData();
					int colcount=rsmd.getColumnCount();
					String colnames[]=new String[colcount];
					for(int i=0;i<colcount;i++){
						colnames[i]=rsmd.getColumnName(i+1);
						columnMap.put( rsmd.getColumnName(i+1),new Integer( rsmd.getColumnType(i+1)) );
					 }

					resultMap.put(COLUMN_NAMES,colnames);//adding coulmn names[] to reqult
					resultMap.put(COLUMN_MAPPING,columnMap);//adding mappin to sql
					 //Set keyset=columnMap.keySet() ;
					 do{

						String key=null;
						Object[] record=new Object[colnames.length];
						for(int i=0;i<colnames.length;i++){
							 key=colnames[i];
							 int coltype=((Integer)columnMap.get(key)).intValue();
							 Object val=null;
							/* if(coltype==93 ||coltype==91 )	val=rs.getDate(key,Calendar.getInstance());
							 else val=rs.getString(key);
							 */
							 if(coltype==93 ||coltype==91 )	val=rs.getObject(key);
							 else val=rs.getString(key);
							record[i]=val;
						}
						records.add(record);

					 }while(rs.next());
					 resultMap.put(RECORDS,records);
				}
				rs.close();
				pstmt.close();
				con.close();
			}catch(Exception e){
				System.out.println("Error in NQMasterDB getrecords(): "+e.getMessage()+"\n query: "+query);
				if(resultMap !=null)resultMap=null;
				
				}finally{
				try{
					if(pstmt!=null)pstmt.close();
					if(con!=null)con.close();
				}catch(Exception ex){}
			}

			return resultMap;
	}






}
