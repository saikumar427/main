<%@taglib uri="/struts-tags" prefix="s"%>
<s:set name="questionsVec" value="questionslist" />
<s:form name='editattendee' id='editattendee' action='EditProfileData' method='post' >
<input type='hidden' name='profileresponsejson'  id='profileresponsejson' value='' />
<input type='hidden' name='eid'  id='eid' value='${eid}' />
<input type='hidden' name='pid'  id='pid' value='${pid}' />
<s:iterator value="#questionsVec" status='stat'>
<div class="row">
<div class="col-md-1"></div>
<div class="col-md-6" style="margin-top: 8px;">
<s:set name='qid' value="#questionsVec[(#stat.count)-1]" />
<div id='<s:property value='#qid'/>'></div>
</div>
</div>
</s:iterator>
<br/>
 </s:form>
 
 <div class="form-group" id="buttons">
                       <div class="col-md-3 col-sm-3 col-xs-3" style=" margin-right:80px;"></div>
                        <div class="col-md-6 col-xs-6 col-sm-6">
                            <button type="button" name='update' id='updaprofile' class="btn btn-primary" onclick="updateProfileData('attendee');"><s:text name="global.submit.btn.lbl"></s:text></button>
                            <button class="btn btn-cancel" name="cancel" id="cancelregdetails" onclick="closeProfile()"><i class="fa fa-times"></i></button>
                        </div>
                    </div>
                    
                    
                    <div class="form-group row" id="buttonsloading" style="display:none;">   
 						<div class="col-md-3 col-sm-3 col-xs-3" style=" margin-right: 63px;"></div>
                        <div class="col-md-6 col-xs-6 col-sm-6">
                          <s:text name="global.processing.btn.lbl"/><br>&nbsp;&nbsp;&nbsp;&nbsp;<img src='../images/ajax-loader.gif'>
                        </div>
                    </div>
 