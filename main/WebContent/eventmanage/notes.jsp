<%@taglib uri="/struts-tags" prefix="s" %>
<s:form name="notesform" action="TransactionDetails!saveNotes" method="post" theme="simple" id="notesform"> 
<div id="noteserrormessages"></div>
<s:hidden name="eid" id="evtid"></s:hidden>
<s:hidden name="tid" id="transactionid"></s:hidden>
<s:hidden name="transactionNotesData.notes_id"></s:hidden>
<div class="row">
<div class="col-md-1"></div>
<div class="col-md-11"><s:textarea name="transactionNotesData.notes"  cssClass="form-control" rows="2" cols="45" theme="simple"></s:textarea></div>
</div>
</s:form>
<br/>
<div class="form-group">
                        <div class="pull-right">
                            <button type="button" class="btn btn-primary" id="notessubmit" onclick="updateNotes();">Save</button>
                            <button class="btn btn-active" id="cancelnotes"><i class="fa fa-times" ></i></button>
                        </div>
                    </div>
<br/>                   
