<%@taglib uri="/struts-tags" prefix="s"%>
 
            <div class="row" style="height: 385px;padding-top: 83px">
                    
                    
                    
                    <div class="col-md-12 text-center">
                   			<div class="form-group">
                           		 <div class="col-sm-12"> 
                         <s:text name="act.lnk.msg.lbl"/> 
                        		 </div>
                        	 </div>
                        </div>
                        <div class="col-sm-12 text-center">
                       			 <div class="form-group">
                       			 <div class="col-sm-12"> 
                     <s:text name="act.lnk.you.msg.lbl"/>
                   			  </div>
                   			  </div>
                   			  </div><br/>
                 <div class="col-sm-12 text-center">
                 <div class="form-group">
                       			 <div class="col-sm-12"> <br/>
<s:text name="act.lnk.questns.lbl"/> <a href="/main/faq/<s:property value="I18N_ACTUAL_CODE"/>"><s:text name="global.faq.link.lbl"/> </a> <s:text name="act.lnk.page.lbl"/> <a style="cursor:pointer;" id="activationsupport"><s:text name="act.lnk.clck.lbl"/></a> <s:text name="act.lnk.cntct.lbl"/>
                            </div>
                        </div>
                    </div>
                   
            </div>
            
            <script>
            $(document).ready(function(){
            	$("input.form-control").ezpz_hint();
$('#activationsupport').click(function(){
	//$('.modal-title').html('Contact Support');
	$('.modal-title').html(props.la_supp_popup_hdr);
	$('#myModal').on('show.bs.modal', function () {
	$('iframe#popup').attr("src",'/main/user/homepagesupportemail.jsp');
	$('iframe#popup').css("height","440px"); 
	});
	$('#myModal').modal('show');
});
                });

            </script>