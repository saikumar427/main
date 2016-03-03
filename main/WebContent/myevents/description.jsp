<script src="/main/js/tinymce/tinymce.min.js"></script>
<script>
	var upload_url ="http://images.eventbee.com/eventdesc/index.php?upload/english";
	//var upload_url ="http://www.citypartytix.com:8080/eventdesc/index.php?upload/english";
    tinymce.init({
      selector:'textarea#editor',
      menubar: false,
      statusbar: false,
      resize: false,
      height: "250",
      plugins: [
      "advlist autolink lists link image charmap print preview anchor",
      "searchreplace visualblocks",
      "insertdatetime media table contextmenu paste jbimages textcolor colorpicker code"
      ],
      toolbar: "fontselect fontsizeselect | styleselect | forecolor backcolor | bold italic underline | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link jbimages | code",
      relative_urls: false
    });
</script>
<textarea id="editor"></textarea>
<style>
    #editor_ifr {
      height: 250px !important;
      min-height: 250px !important;
    }
    #editor {
      overflow: auto !important;
    }
    .mce-window {
      border-radius: 8px;
      padding: 10px;
    }
</style>