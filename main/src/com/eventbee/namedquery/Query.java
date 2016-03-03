package com.eventbee.namedquery;

import java.util.ArrayList;

public class Query {
	private String qid = "";
	private String sql = "";
	private String dsn = "";
	private ArrayList<InParam> inparams;
	private ArrayList<OutParam> outparams;	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getDsn() {
		return dsn;
	}
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	public ArrayList<InParam> getInparams() {
		return inparams;
	}
	public void setInparams(ArrayList<InParam> inparams) {
		this.inparams = inparams;
	}
	public ArrayList<OutParam> getOutparams() {
		return outparams;
	}
	public void setOutparams(ArrayList<OutParam> outparams) {
		this.outparams = outparams;
	}
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	
}
