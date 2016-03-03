package com.eventbee.namedquery;

import java.util.*;
import java.text.*;
import java.sql.*;

import org.apache.log4j.Logger;

public class NQDbHelper {
	private static Logger log = Logger.getLogger(NQDbHelper.class);
	private Map columnMap = null;
	private String[] columnnames = null;
	private List records = null;
	List columnlist = null;
	private String dsnname = null;

	public NQDbHelper(String name) {
		dsnname = name;
	}

	public NQDbHelper() {
	}

	public NQStatusObj executeSelectQuery(String query, String[] inputparams) {

		HashMap recordsmap = new NQMasterDB(dsnname).getRecords(query,
				inputparams, null);
		NQStatusObj statobj = new NQStatusObj(false, "", "0", 0);
		if (recordsmap != null) {

			columnMap = (Map) recordsmap.get(NQMasterDB.COLUMN_MAPPING);
			columnnames = (String[]) recordsmap.get(NQMasterDB.COLUMN_NAMES);
			columnlist = Arrays.asList(columnnames);
			records = (List) recordsmap.get(NQMasterDB.RECORDS);
			statobj.setStatus(true);
			statobj.setData(recordsmap);
			statobj.setCount(records.size());
		}
		return statobj;

	}

	public String[] getColumnNames() {
		return columnnames;
	}

	public Object getValueFromRecord(int index, String columnname1,
			String defval) {
		Object retval = null;
		String columnname = (columnname1 != null) ? columnname1.toLowerCase()
				: " ";
		try {
			Object[] record = (Object[]) records.get(index);
			retval = (columnlist.indexOf(columnname) != -1) ? (record[columnlist
			                                                          .indexOf(columnname)])
			                                                          : defval;
			if (retval == null)
				retval = defval;
		} catch (Exception exp) {
			System.out.println("Error in getValueFromRecord()"
					+ exp.getMessage());
		}
		return retval;
	}

	public String getValue(int index, String columnname1, String defval) {
		Object retval = null;
		String columnname = (columnname1 != null) ? columnname1.toLowerCase()
				: " ";
		try {
			Object[] record = (Object[]) records.get(index);
			retval = (columnlist.indexOf(columnname) != -1) ? (record[columnlist
			                                                          .indexOf(columnname)])
			                                                          : defval;
			if (retval == null)
				retval = defval;
		} catch (Exception exp) {
			System.out.println("Error in getValueFrom Record()"
					+ exp.getMessage());
		}

		if (retval != null)
			return retval.toString();
		return null;
	}
	public boolean getStatus(String key,List poutparam){		
		try{
			for(int i=0;i<poutparam.size();i++){
				if(key.equals(poutparam.get(i))){					
					return true;					
				}					
			}
			return false;
		}catch(Exception ex){
			log.error("Exception: "+ex);
		}
		return false;
	}

	/**
	 * 
	 * @param qname
	 * @param pInParams
	 * @return
	 */
	public List getDataList(String qname, HashMap pInParams) {
		String paramVals[] = null;
		NQStatusObj statobj = new NQStatusObj(false, "", "0", 0);
		List list = new ArrayList();
		try {
			HashMap hmap = NQDbUtil.getQueryData(qname, pInParams);
			if (hmap.size() != 0) {
				List<OutParam> outparamlist = (List<OutParam>) hmap
				.get("outparamobj");
				if (dsnname == null || dsnname.length() == 0)
					dsnname = hmap.get("dsn").toString();
				HashMap recordsmap = new NQMasterDB(dsnname).getRecords(hmap.get(
				"sql").toString(), (String[]) hmap.get("inputparams"),(String[])hmap.get("inputparamstype"));
				if (recordsmap != null) {
					columnMap = (Map) recordsmap.get(NQMasterDB.COLUMN_MAPPING);
					columnnames = (String[]) recordsmap
					.get(NQMasterDB.COLUMN_NAMES);
					columnlist = Arrays.asList(columnnames);
					records = (List) recordsmap.get(NQMasterDB.RECORDS);
					statobj.setStatus(true);
					statobj.setData(recordsmap);
					statobj.setCount(records.size());
				}
				if (statobj.getStatus()) {
					for (int k = 0; k < statobj.getCount(); k++) {
						Map map = new HashMap();
						for (int i = 0; i < outparamlist.size(); i++) {
								String value = getValue(k, outparamlist.get(i)
										.getColumnName(), "");								
								map.put(outparamlist.get(i).getKey(), value);
								log.info(outparamlist.get(i).getKey()+":->"+value);
						}
						list.add(map);
					}

				}
			}else{
				statobj.setErrorMsg("There is no query with query name: "+qname);
			}
			return list;
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		return list;
	}

	/**
	 * 
	 * @param qname
	 * @param pInParams
	 * @return
	 */
	public String getDataString(String qname, HashMap pInParams) {
		String str = null;
		try {
			HashMap hmap = NQDbUtil.getQueryData(qname, pInParams);
			if (hmap.size() != 0) {
				if (dsnname == null || dsnname.length() == 0)
					dsnname = hmap.get("dsn").toString();
				str = NQDbUtil.getVal(hmap.get("sql").toString(), (String[]) hmap
						.get("inputparams"), dsnname, (String[])hmap.get("inputparamstype"));
			}
			return str;
		} catch (Exception ex) {
			System.out.println("Exception: " + ex);
		}
		return str;
	}

	/**
	 * 
	 * @param qname
	 * @param pInParams
	 * @return
	 */
	public List getDataColumnList(String qname, HashMap pInParams) {
		List list = null;
		try {
			HashMap hmap = NQDbUtil.getQueryData(qname, pInParams);
			if (hmap.size() != 0) {
				if (dsnname == null || dsnname.length() == 0)
					dsnname = hmap.get("dsn").toString();
				list = NQDbUtil.getValues(hmap.get("sql").toString(),
						(String[]) hmap.get("inputparams"), dsnname, (String[])hmap.get("inputparamstype"));
			}
			return list;
		} catch (Exception ex) {
			System.out.println("Exception: " + ex);
		}
		return list;
	}

	/**
	 * 
	 * @param qname
	 * @param pInParams
	 * @return
	 */
	public HashMap getDataHash(String qname, HashMap pInParams) {
		HashMap hmap = null;
		try {
			List list = getDataList(qname, pInParams);
			if (list.size() != 0) {
				hmap = (HashMap) list.get(0);
			}
			return hmap;
		} catch (Exception ex) {
			System.out.println("Exception: " + ex);
		}
		return hmap;
	}
}
