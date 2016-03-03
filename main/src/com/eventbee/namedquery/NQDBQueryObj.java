package com.eventbee.namedquery;

public class NQDBQueryObj {
	private String m_query=null;
	private String [] m_inputs=null;
	private boolean m_forcedCommit=false;

	public NQDBQueryObj(String query, String [] inputs,boolean forcedCommit) {
		this(query,inputs);
		setForcedCommitStatus(forcedCommit);
		}
	public NQDBQueryObj(String query, String [] inputs) {
			setQuery(query);
			setQueryInputs(inputs);
		}
	public NQDBQueryObj() {

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
