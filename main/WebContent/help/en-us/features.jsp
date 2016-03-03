<%@ include file="/getresourcespath.jsp" %>

<head>        
<link type="text/css" rel="stylesheet" href="/main/css/bootstrap/justified-nav.css" />
       
<style>
.nav-justified {
border-radius:0px;
border:0px;
}
.nav nav-justified:hover li{background:url(<%=resourceaddress%>/main/images/features/sitearea-nav.jpg) repeat-x 0 -100px;}

.nav > li > a {
display:block;
}
.nav-justified > li:first-child > a {
border-radius: 5px 0px 0px 0px;
}
.nav-justified > li:last-child > a {
border-radius: 0px 5px 0px 0px;
}
body {
padding-top:0px;
}
.footer {
border-top: 0px;
padding-top:0px;
padding-bottom:0px;
margin-top:0px;
}
</style>
 </head>
<%
String tabName=request.getParameter("tabName");
System.out.println("tabName"+ tabName);
if(tabName== null || "".equals(tabName)){
tabName="creation";
}
%>


	
		 
		 
   <div class="row" style="margin-left:-30px;">
   <div class="col-md-12">
            	 
            	 
            	 
            	 <ul class="nav nav-justified" style="margin:0px 0px -10px -20px;width:105.2%;">
            	 <li class="active" id="creationli">            	 
            	       <a href="javascript:showTab('creation')" rel="creation">
						<img src="<%=resourceaddress%>/main/images/features/creation.png"  border="0" width="50px"/>
						Creation</a>
            	 </li>
            	 
            	  
            	 <li id="customizationli">
            	  					<a href="javascript:showTab('customization')" rel="customization">
									<img src="<%=resourceaddress%>/main/images/features/customization.png" border="0" width="50px"/>Customization</a>
																
            	 </li>
            	
            	 
            	 <li id="integrationli">
            	  <a href="javascript:showTab('integration')"  rel="integration">
									<img src="<%=resourceaddress%>/main/images/features/integration.png" border="0" width="50px"/>
									Integration</a>
            	 </li>
            	 
            	 
            	 <li id="promotionli">
            	  <a href="javascript:showTab('promotion')"  rel="promotion">
									<img src="<%=resourceaddress%>/main/images/features/promotion.png" border="0" width="50px"/>Promotion</a>
            	 </li>
            	 
            	 <li id="managementli">
            	  <a href="javascript:showTab('management')"  rel="management">
										<img src="<%=resourceaddress%>/main/images/features/management.png" border="0" width="50px"/>Management</a>
            	 </li>
            	 </ul>       	            	 
            	          	         	 
            	 
          </div>
          </div> 
            	 
            	 
            	 
            	 
            	 
            	
            <div class="row" style="margin-left:-30px;">
            <div  class="col-md-12" id="integration">
		 	<%@ include file="../featurescontent/integration.jsp" %>
		 	</div>
		 	<div  class="col-md-12" id="promotion">
		 	<%@ include file="../featurescontent/promotion.jsp" %>
		 	</div>
		 	<div  class="col-md-12" id="management">
		 	<%@ include file="../featurescontent/management.jsp" %>
		 	</div>
		 
		 	<div  class="col-md-12" id="creation">
		 	<%@ include file="../featurescontent/creation.jsp" %>
		 	</div>
		 	<div  class="col-md-12" id="customization">
		    <%@ include file="../featurescontent/customization.jsp" %>
		 	</div>
		 	
		 </div>
			  
	          	          
	               
		 
<script>
function showTab(tabName) {
var tabnames=new Array("creation","customization","integration","promotion","management");
for(i=0;i<tabnames.length;i++){
if(tabName!=tabnames[i]){   
   if (document.getElementById (tabnames[i])) {   
	document.getElementById (tabnames[i]).style.display ='none';
	document.getElementById (tabnames[i]+"li").className="";
   }
 }else {
 document.getElementById (tabnames[i]).style.display ='block';
 document.getElementById (tabnames[i]+"li").className="active";
 }
}
}
showTab("<%=tabName%>");
</script>

