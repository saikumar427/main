package com.eventbee.general;
import java.util.*;
import java.text.*;
import java.sql.*;

public class DBQueryObj{
private String m_query=null;
private String [] m_inputs=null;
private boolean m_forcedCommit=false;

public DBQueryObj(String query, String [] inputs,boolean forcedCommit) {
	this(query,inputs);
	setForcedCommitStatus(forcedCommit);
	}
public DBQueryObj(String query, String [] inputs) {
		setQuery(query);
		setQueryInputs(inputs);
	}
public DBQueryObj() {

}
	public String getQuery(){
		return m_query;
	}
	public void setQuery(String query){
		m_query=query;
	}
	public String [] getQueryInputs(){
		return m_inputs;
	}
	public void setQueryInputs(String [] inputs){
		m_inputs=inputs;
	}
	public boolean getForcedCommitStatus(){
		return m_forcedCommit;
	}
	public void setForcedCommitStatus(boolean forcedCommit){
		m_forcedCommit=forcedCommit;
	}



}
