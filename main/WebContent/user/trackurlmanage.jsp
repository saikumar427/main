<%@taglib uri="/struts-tags" prefix="s"%>

<style>
.line-space {
	margin-bottom:10px;
}
</style>

<form id="trackurlmanageform" name="trackurlmanageform" action="TrackUrlManage!validatePassword" onsubmit="continuefunc();">
<s:if test="%{eid!=''}"><input type="hidden" name="eid" value="${eid}"></s:if>
<s:if test="%{beeid!=''}"><input type="hidden" name="beeid" id="beeid" value="${beeid}"></s:if>
<input type="hidden" name="trackcode" id="trackcode" value="${trackcode}">
<input type="hidden" name="secretcode" id="secretcode" value="${secretcode}">

<div class="row" style="padding: 20px">
	<div class="col-sm-12 col-md-12 col-xs-12 line-space">
		<div class="text-center">
			${trackcode} - <s:text name="turl.manage.track.url"/>
		</div>
	</div>
	<div class="col-sm-12 col-md-12 col-xs-12 line-space">
		<div class="text-center">
			<s:text name="trul.manage.password.protect.visit"/>
		</div>
	</div>
	<s:if test="%{errormsg!=''}">
		<div class="col-sm-12 col-md-12 col-xs-12 line-space">
			<div class="text-center">
				<font color="red"><s:text name="trul.manage.invalid.password.forget"/></font>
			</div>
		</div>
	</s:if>
	<div class="col-md-5 col-sm-4 col-xs-4"></div>
	<div class="col-md-2 col-sm-4 col-xs-4 line-space">
		<div class="text-center">
			<input id="password" length="10" type="password" name="password" class="form-control"/>
		</div>
	</div>
	<div class="col-md-5 col-sm-4 col-xs-4  "></div>
	
	<div class="col-sm-12 col-md-12 col-xs-12">
		<div class="text-center">
			<input value=" <s:text name="trul.continue.lbl"/>" id="continue" type="submit" class="btn btn-primary"/>
		</div>
	</div>
</div>


            <%-- <div style="padding:30px">
            
            <div class="row" style="text-align:center">
            	<div class="col-md-12">
            		${trackcode} - Tracking URL             			
            </div>
         </div>
         <br/>
        <div class="row" style="text-align:center">
            	<div class="col-md-12">
            		This page is password protected, enter password to visit the page           			
            </div>
         </div>
              <br/>
              
              <s:if test="%{errormsg!=''}">              
              <div class="row" style="text-align:center">
              	<div class="col-md-12">
              	<font color="red">Invalid password, enter valid password. Forgot your Tracking URL password? Please contact your Event Manager.</font>
              	</div>
              </div>
              <br/>
              </s:if>
              
          <div class="row" >
            	<div class="col-md-12">
            	<div class="col-md-5">
            	</div>
            	<div class="col-md-2">
            		<input id="password" length="10" type="password" name="password" class="form-control"/>
            		</div>         			
            </div>
         </div>   
           <br/>
         <div class="row" style="text-align:center">
            	<div class="col-md-12">
            		<input value="Continue" id="continue" type="submit" class="btn btn-primary"/>            		
            </div>
         </div>    --%>
            
            
            
            
            
            
            
            
            
            
            
            
            
<!--<table cellspacing="0" cellpadding="0" class="inputvalue" valign="top" border="0" align="center" id="container">
		<tr>
			<td height="20"></td>
		</tr>
		<tr>
			<td height="30" align="center" class="large">
				${trackcode} - <h1><small>Tracking URL </small></h1>
			</td>
		</tr>
		<tr>
			<td class="inputlabel" width="36%" height="30" align="center">This
			page is password protected, enter password to visit the page
		</td>
		</tr>
		<s:if test="%{errormsg!=''}">
		
		<tr>
			<td class="inputlabel" width="36%" height="30" align="center">
			<font color="red">Invalid password, enter valid password. Forgot your Tracking URL password? Please contact your Event Manager.</font>
		</td>
		</tr>		
		</s:if>
		<tr>
			<td id="pwdprotect" align="center"></td>
		</tr>
		<tr>
			<td class="inputvalue" align="center">
				<input id="password" length="10" type="password" name="password" />
			</td>
		</tr>
		<tr>
			<td height="5"></td>
		</tr>
		<tr>
			<td align="center"><input value="Continue" id="continue" type="submit" /></td>
		</tr>
		<tr>
			<td height="110"></td>
		</tr>
	</table>
	
	--></div>
	
	
</form>

<script>

//var btn = new YAHOO.widget.Button("continue", { onclick: { fn: continuefunc} });
var btn = new YAHOO.widget.Button("continue");
function continuefunc(){
if(document.getElementById('beeid')){
var beeid=document.getElementById('beeid').value;
beeid=beeid.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
if(beeid!='')
document.trackurlmanageform.action='TrackUrlManage!validateGlobalPassword';
}
//document.trackurlmanageform.submit();
}


</script>