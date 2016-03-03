package com.eventbee.namedquery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class NQDbUtil {
	private static Logger log = Logger.getLogger(NQDbUtil.class);
	public static List getValues(String Query) {
		Connection con = null;
		return getValues(Query, null, con, null,null);
	}

	public static List getValues(String Query, String[] inputparams,String[] inputpramtypes) {
		Connection con = null;
		return getValues(Query, inputparams, con, null,inputpramtypes);
	}

	public static List getValues(String Query, String[] inputparams,
			String dsname,String[] inputpramtypes) {
		Connection con = null;
		return getValues(Query, inputparams, con, dsname,inputpramtypes);
	}

	public static List getValues(String Query, String[] inputparams,
			Connection con, String dsname,String[] inputpramtypes) {
		List list = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean conclose = false;
		boolean fortypes = false;
		try {
			if (con == null) {
				if(dsname!=null && dsname.length()!=0)
					con=NQEventbeeConnection.getConnection(dsname);
				else
					con=NQEventbeeConnection.getConnection();
				conclose = true;
			}
			pstmt = con.prepareStatement(Query);
			if(inputpramtypes!=null && inputparams.length== inputpramtypes.length)fortypes=true;
			for (int i = 0; inputparams != null && i < inputparams.length; i++) {
				if(fortypes){
					if(NQMasterDB.INT.equals(inputpramtypes[i]) )pstmt.setInt( i+1, Integer.parseInt(inputparams[i]));
					if(NQMasterDB.STR.equals(inputpramtypes[i]) )pstmt.setString(i+1,(String)inputparams[i]);
					if(NQMasterDB.DOUBLE.equals(inputpramtypes[i]) )pstmt.setDouble(i+1,Double.parseDouble((String) inputparams[i]));
					if(NQMasterDB.DT.equals(inputpramtypes[i]) )pstmt.setDate(i+1,(java.sql.Date) new Date(inputparams[i]));
				}else{
					pstmt.setString(i+1,(String)inputparams[i]);
				}
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (Exception ex) {
			log.error("Exception : " + ex);
			list = null;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (conclose) {
					if (con != null) {
						con.close();
						con = null;
					}
				}
			} catch (Exception ex) {
				log.error("Exception : " + ex);
			}
		}
		return list;
	}

	public static String getVal(String query, String[] params,String[] inputpramtypes) {
		return getVal(query, params, null,inputpramtypes);
	}

	public static String getVal(String query, String[] params, String dsname,String[] inputpramtypes) {
		Connection con = null;
		java.sql.PreparedStatement pstmt = null;
		String maxval = null;
		boolean fortypes = false;
		try {
			if(dsname!=null && dsname.length()!=0)
				con = NQEventbeeConnection.getConnection(dsname);
			else
				con = NQEventbeeConnection.getConnection();
			pstmt = con.prepareStatement(query);
			if(inputpramtypes!=null && params.length== inputpramtypes.length)fortypes=true;
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					if(fortypes){
						if(NQMasterDB.INT.equals(inputpramtypes[i]) )pstmt.setInt( i+1, Integer.parseInt(params[i]));
						if(NQMasterDB.STR.equals(inputpramtypes[i]) )pstmt.setString(i+1,(String)params[i]);
						if(NQMasterDB.DOUBLE.equals(inputpramtypes[i]) )pstmt.setDouble(i+1,Double.parseDouble((String) params[i]));
						if(NQMasterDB.DT.equals(inputpramtypes[i]) )pstmt.setDate(i+1,(java.sql.Date) new Date(params[i]));
					}else{
						pstmt.setString(i+1,(String)params[i]);
					}
				}

			}
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				maxval = rs.getString(1);
			}
			pstmt.close();
			pstmt = null;
			con.close();
			con = null;
		} catch (Exception ex) {
			log.error("Exception : " + ex);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return maxval;
	}

	public static NQStatusObj executeUpdateQuery(String query,
			String[] inputparams,String[] inputpramtypes) {
		Connection con = null;
		return executeUpdateQuery(query, inputparams, con, null,inputpramtypes);
	}

	public static NQStatusObj executeUpdateQuery(String query,
			String[] inputparams, String dsname,String[] inputpramtypes) {
		Connection con = null;
		return executeUpdateQuery(query, inputparams, con, dsname, inputpramtypes);
	}

	public static NQStatusObj executeUpdateQuery(String query,
			String[] inputparams, Connection con, String dsname,String[] inputpramtypes) {
		NQStatusObj statobj = new NQStatusObj(false, "", "0", 0);
		try{
		boolean conclose = false;
		boolean fortypes = false;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			if (con == null) {
				if(dsname!=null && dsname.length()!=0)
					con = NQEventbeeConnection.getConnection(dsname);
				else
					con = NQEventbeeConnection.getConnection();
				conclose = true;
			}
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			if(inputpramtypes!=null && inputparams.length== inputpramtypes.length)fortypes=true;			
			for (int i = 0; inputparams != null && i < inputparams.length; i++) {
				if(fortypes){
					try{					
					if(NQMasterDB.INT.equals(inputpramtypes[i]) )pstmt.setInt( i+1, Integer.parseInt(inputparams[i]));
									
					if(NQMasterDB.STR.equals(inputpramtypes[i]) )pstmt.setString(i+1,(String)inputparams[i]);
					
					if(NQMasterDB.DOUBLE.equals(inputpramtypes[i]) )pstmt.setDouble(i+1,Double.parseDouble((String) inputparams[i]));
							
					if(NQMasterDB.DT.equals(inputpramtypes[i]) ){						
				         DateFormat formatter ; 
				         Date date ; 
				          //formatter = new SimpleDateFormat("mm-dd-yyyy");
				        formatter = new SimpleDateFormat("yyyy-MM-dd");
				        date = (Date)formatter.parse(inputparams[i]);
				        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				      	pstmt.setDate(i+1, sqlDate);
						
					}
					}catch(Exception ex){
						log.error("Exception in db insert:"+ex);
					}
				}else{					
					pstmt.setString(i+1,(String)inputparams[i]);
				}
				
			}
			result = pstmt.executeUpdate();
			con.commit();
			log.info("result: "+result);			
			pstmt.close();
			pstmt = null;
			statobj.setStatus(true);
			statobj.setCount(result);
		} catch (Exception ex) {
			result = 0;
			System.out.println("Exception in NQDbUtil.java: " + ex.getLocalizedMessage()+"/n query: "+query);
			log.error("Exception in : " + ex.getLocalizedMessage());
			log.error("stacktrace"+ex.getStackTrace());
			log.error("stacktrace"+ex.getCause());
			statobj.setData(ex);
			statobj.setErrorMsg(ex.getMessage());
			statobj.setStatus(false);
			try{
				if(pstmt!=null && con!=null)
					con.rollback();
				}catch (Exception exc) {
					// TODO: handle exception
				}
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (conclose) {
					if (con != null) {
						con.close();
						con = null;
					}
				}
			} catch (Exception e) {
			}
		}
		statobj.setCount(result);
		return statobj;
		}catch(Exception ex){
			log.error("Exception in executeUpdateQuery:"+ex);
			System.out.println("Exception in executeUpdateQuery:"+ex);
		}
		return statobj;
	}
	/**
	 * 
	 * @param qname
	 * @param pInparams
	 * @return
	 */
	public static NQStatusObj executeQuery(String qname, HashMap pInparams) {
		Connection con = null;
		return executeQuery(qname, pInparams, con, null);
	}
	/**
	 * 
	 * @param qname
	 * @param pInparams
	 * @param dsname
	 * @return
	 */
	public static NQStatusObj executeQuery(String qname, HashMap pInparams,String dsname) {
		Connection con = null;
		return executeQuery(qname, pInparams, con, dsname);
	}
	/**
	 * 
	 * @param qname
	 * @param pInparams
	 * @param con
	 * @return
	 */
	public static NQStatusObj executeQuery(String qname, HashMap pInparams,
			Connection con) {
		return executeQuery(qname, pInparams, con, null);
	}
	/**
	 * 
	 * @param qname
	 * @param pInparams
	 * @param con
	 * @param dsname
	 * @return
	 */
	public static NQStatusObj executeQuery(String qname, HashMap pInparams,
			Connection con, String dsname) {
		NQStatusObj statobj = new NQStatusObj(false, "", "0", 0);
		String paramVals[] = null;
		try {
			
			HashMap hmap = getQueryData(qname, pInparams);
			if (hmap.size() != 0) {
				if(dsname==null){
					dsname = hmap.get("dsn").toString();
				}
				
				statobj = executeUpdateQuery(hmap.get("sql").toString(),
						(String[]) hmap.get("inputparams"),dsname,(String[]) hmap.get("inputparamstype"));
			}else{				
				statobj.setErrorMsg("There is no query with query name: "+qname);
			}
		} catch (Exception ex) {
			log.error("stacktrace"+ex.getStackTrace());
			log.error("stacktrace"+ex.getCause());
			
			log.error("Exception in executeQuery: "+ex);
		}
		return statobj;
	}
	/**
	 * 
	 * @param qname
	 * @param pInparams
	 * @return
	 */
	public static HashMap getQueryData(String qname, HashMap pInparams) {
		HashMap hmap = new HashMap();
		try {
			String paramVals[] = null;
			String[] paramTypes = null;
			if (qname != null) {
				if(!NQDbManager.queriesHash.containsKey(qname)){
					NQDbManager.loadAllQueries();
				}
				if(NQDbManager.queriesHash.containsKey(qname)){
					Query query = (Query) NQDbManager.queriesHash.get(qname);					
					String sql = query.getSql();
					String dsn = query.getDsn();
					List<InParam> inparamlist = query.getInparams();
					List<OutParam> outparamlist = query.getOutparams();
					/*paramVals = new String[pInparams.size()];
					for (int i = 0; i < inparamlist.size(); i++) {
						if(pInparams.containsKey(inparamlist.get(i).getKey())){
							String val = inparamlist.get(i).getDefaultValues();
							try {
								val = pInparams.get(inparamlist.get(i).getKey())
								.toString();
							} catch (Exception ex) {
							}
							paramVals[i] = val;
						}					
					}*/
					paramVals = new String[inparamlist.size()];
					paramTypes = new String[inparamlist.size()];
					
					for (int i = 0; i < inparamlist.size(); i++) {
						String val = inparamlist.get(i).getDefaultValues();
						try {
							val = pInparams.get(inparamlist.get(i).getKey())
							.toString();							
						} catch (Exception ex) {
						}
						paramVals[i] = val;
						paramTypes[i] = inparamlist.get(i).getType();
					}
					hmap.put("inputparams", paramVals);
					hmap.put("inputparamstype", paramTypes);
					hmap.put("inparamobj", inparamlist);
					
					hmap.put("sql", sql);
					hmap.put("dsn", dsn);
					hmap.put("outparamobj", outparamlist);
					//log.info("HMAP:"+hmap);
					return hmap;
				}else{
					log.info(new Date()+" Info : DbUtil: getQueryData : There is no query with query name: "+qname);
					return hmap;
				}
			}
		} catch (Exception ex) {
			log.error("Exception in getQueryData : "+ex);
		}
		return hmap;
	}
}
