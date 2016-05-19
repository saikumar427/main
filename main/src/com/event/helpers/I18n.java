package com.event.helpers;

import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;


public class I18n {
	private static HashMap<String, String> mappingCodes=new HashMap<String, String>();
	static{
		/*mappingCodes.put("en", "en_US");
		mappingCodes.put("en_US", "en_US");
		mappingCodes.put("en_CO", "en_US");
		
		mappingCodes.put("es_US", "es_CO");	
		mappingCodes.put("es", "es_CO");		
		mappingCodes.put("es_CO", "es_CO");	*/
		
		mappingCodes.put("en", "en-us");
		mappingCodes.put("en-us", "en-us");
		mappingCodes.put("en-co", "en-us");
		
		mappingCodes.put("es-us", "es-us");	
		mappingCodes.put("es", "es-co");		
		mappingCodes.put("es-co", "es-co");
		
		mappingCodes.put("en-mx", "en-us");	
		mappingCodes.put("es-mx", "es-mx");
	}

	public static  void  putLocaleInSession(Map<String, Object> session,ActionInvocation invocation){
		HttpServletRequest request = ServletActionContext.getRequest();
		try{			
 			 /* getting i18n lang from domain
			  * URL aURL = new URL(request.getRequestURL()+"");
			   String domain = aURL.getHost();	   
			   String lang=I18n.getLanguageByDomain(domain);			   
			  */		
			
			String lang=I18n.getLanguageByRequestParam(request.getQueryString()+"");
			if("en_US".equals(lang)) lang="en-us";//this is for ticket purchases link in confirmation email which are already sent with en_US.
			if(lang==null || "".equals(lang)){//this logic work specially wrote for help pages 
				URL aURL = new URL(request.getRequestURL()+"");		
				try{
				 if(mappingCodes.containsKey(aURL.getPath().split("/")[3])) // for help pages at third index we have language
					 lang=aURL.getPath().split("/")[3];				 					 
				}catch(Exception e){
					
				}
			}
			
			Map<String, String>  context=new HashMap<String, String>();
			 context.put("I18N_CODE_PATH","");
			if(lang!=null && !"".equals(lang) ){
				session.put("I18N_ACTUAL_CODE",lang);
				context.put("I18N_ACTUAL_CODE", lang);
				
				String langNCountry=getMappingCode(lang)==null ? lang : getMappingCode(lang);
				System.out.println("language  and country in session ::"+langNCountry);
				String [] langNCoun=langNCountry.split("-");
					
				session.put("WW_TRANS_I18N_LOCALE", new Locale(langNCoun[0], langNCoun[1].toUpperCase()));
				//context.put("I18N_LANG",langNCoun[0]);
				//context.put("I18N_COUNTRY",langNCoun[1]);
				context.put("I18N_CODE",langNCountry);
				if(!"en-us".equals(langNCountry))
				 context.put("I18N_CODE_PATH","/"+langNCountry);
				
			}
			else{//if Request param language value is null
				if(session!=null && session.get("I18N_ACTUAL_CODE")!=null && session.get("WW_TRANS_I18N_LOCALE")!=null){//if values from session is not null
					context.put("I18N_ACTUAL_CODE", session.get("I18N_ACTUAL_CODE")+"");
					//String [] langNCoun=session.get("WW_TRANS_I18N_LOCALE").toString().split("_");
					//context.put("I18N_LANG",langNCoun[0]);
					//context.put("I18N_COUNTRY",langNCoun[1]);
					context.put("I18N_CODE",getHyphenURL(session.get("WW_TRANS_I18N_LOCALE").toString()));
					if(!"en-us".equals(getHyphenURL(session.get("WW_TRANS_I18N_LOCALE").toString())))
					 context.put("I18N_CODE_PATH","/"+getHyphenURL(session.get("WW_TRANS_I18N_LOCALE").toString()));
				}
				else{//if values I18N_ACTUAL_CODE session is  null
					session.put("I18N_ACTUAL_CODE","en-us");
					session.put("WW_TRANS_I18N_LOCALE", new Locale("en", "US"));
					
					context.put("I18N_ACTUAL_CODE", "en-us");					
					//context.put("I18N_LANG","en");
					//context.put("I18N_COUNTRY","US");
					context.put("I18N_CODE","en-us");
					context.put("I18N_CODE_PATH","");
				}
				
			}
			invocation.getInvocationContext().getValueStack().push(context);
  		  }catch(Exception e){
			 System.out.println("Exception while putting I18n language in session");			
		}
	}

	public static String getHyphenURL(String url){
		return url.toLowerCase().replace("_", "-");
	}
	
	public static String getHyphenSessionLang(){
		return getLanguageFromSession().toLowerCase().replace("_", "-");
	}
	
	public static String getLanguageByDomain(String domain){
		domain= domain.startsWith("www.") ? domain.substring(4) : domain;
		String[] splitted=domain.split("\\.");
		if(splitted.length==1) return "en-us";
		String lang=splitted[splitted.length-1];   
		lang="com".equalsIgnoreCase(lang) || "".equals(lang)?"en-us":lang;
		return lang;
	}
	public static String getLanguageByRequestParam(String query){
		String i18n=null;//making default language as english 					
		try{
			if(query!=null && !"".equals(query)){
				String[] pairs = query.split("&");
				for (String pair : pairs) {
					int idx = pair.indexOf("=");
					if("lang".equalsIgnoreCase(URLDecoder.decode(pair.substring(0, idx), "UTF-8")))
						i18n= URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
				}
			}
		}catch(Exception e){
			//System.out.println("Exception while splitting language from query parms::"+e.getMessage());
		}
		return i18n;
	}
	
	public static String getLanguageFromSession(){
		String lang=null;
		Map<String, Object> session=ActionContext.getContext().getSession();
		
		if(session.get("WW_TRANS_I18N_LOCALE")!=null)
          lang = session.get("WW_TRANS_I18N_LOCALE").toString();
		
        if("".equals(lang) || lang==null) lang="en_US";
        return lang;
    }
	
	public static String getActualLangFromSession(){
		String lang=null;
		Map<String, Object> session=ActionContext.getContext().getSession();
		
		if(session.get("I18N_ACTUAL_CODE")!=null)
          lang = session.get("I18N_ACTUAL_CODE").toString();
		
        if("".equals(lang) || lang==null) lang="en-us";
        return lang;
    }
	
	public static HashMap<String, String> prepareHashMapFromJSON(JSONObject jsonData){
		HashMap<String, String> hashmap=new HashMap<String, String>();
		Iterator<String> keysItr = jsonData.keys();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next();
	        String value="";
			try {
				value = jsonData.getString(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
	        hashmap.put(key, value);
	    }
	    return hashmap;
	}
	
	public static ResourceBundle getResourceBundle(){
		String i18nLocale="";
		/*if(ActionContext.getContext().getSession().get("WW_TRANS_I18N_LOCALE")!=null && !"".equals(ActionContext.getContext().getSession().get("WW_TRANS_I18N_LOCALE")))
			i18nLocale=ActionContext.getContext().getSession().get("WW_TRANS_I18N_LOCALE").toString();*/
		i18nLocale=getLanguageFromSession();
		String [] langNCoun=i18nLocale.split("_");
		return	ResourceBundle.getBundle("global", new Locale(langNCoun[0], langNCoun[1]));
	}
	
	public static String getString(String key){
		return I18n.getResourceBundle().getString(key);
	}
	
	public static String getMappingCode(String codeSource){
		String mappingCode=mappingCodes.get(codeSource);
		return mappingCode;
	}
	
	
	
}
