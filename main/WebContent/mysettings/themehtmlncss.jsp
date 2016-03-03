<%@taglib uri="/struts-tags" prefix="s"%>
  <div class="row">
    <div class="form-group">
   <div class="col-md-12">
   <s:form theme="simple" action="eventslist!save" id="htmlncss" name="htmlncss">
<input type="hidden" name="themeid" value='<%=request.getParameter("themeid")%>' ></input>
<span class="section-header">   <s:text name="global.html.lbl"/>   </span><br/>
<s:textarea rows="20" cols="105" name="userThemes.HTMLContent" cssClass="form-control"></s:textarea>
<br/><span class="section-header"><s:text name="global.css.lbl"/></span><br/>
<s:textarea rows="20" cols="105" name="userThemes.CSScontent" cssClass="form-control"></s:textarea>
</s:form>
   </div>
  </div>
  </div>
  <br/>
  <div class="row">
  <div class="form-group">
	 <div class="col-sm-4"></div>
	 <div class="col-sm-4" align="center">
	   <button type="submit" class="btn btn-primary" id="htmlncsssubmit"><s:text name="global.submit.btn.lbl"/></button>
	   <button type="submit" class="btn btn-primary" id="htmlncsspreview"><s:text name="global.preview.btn.lbl"/></button>
	   <button type="submit" class="btn btn-primary" id="htmlncsscancel"><s:text name="global.cancel.btn.lbl"/></button>
	   </div>
	</div>
  </div>
  <script>

  $('#htmlncsssubmit').click(function(){
		document.htmlncss.submit();
        });

  $('#htmlncsspreview').click(function(){
	  $.ajax({
			 type: "POST",
		 	   url: 'eventslist!preview',
		 	   data: $("#htmlncss").serialize(),
		 	  success: function( result ) {
		 		 var previewFileName=result;	
		 		 var url='../membertasks/FileRead?readFileName='+''+previewFileName+'';
		 		 $('.modal-title').html('Preview');
		 		 $('#myModal').on('show.bs.modal', function () {
			 			$('iframe#popup').attr("src",url);
			 			$('iframe#popup').css("height","250px"); 
			 			});
			 			$('#myModal').modal('show');
		 	  }	
		}); 
	  });

  $('#htmlncsscancel').click(function(){
		window.location.href='../mysettings/home';
	  });

  </script>
