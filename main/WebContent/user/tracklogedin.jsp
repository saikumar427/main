<script>
var eid='${eid}';
var trackcode='${trackcode}';
var scode='${secretcode}';
var serveradd='${serveraddress}';
var userid='${userid}';
userid=userid.replace(/^(?:\s)*/g,'').replace(/(?:\s)*$/g,'');
if(userid=='')
window.location.href=serveradd+"/main/TrackUrlManage!getManagePage?eid="+eid+"&trackcode="+trackcode+"&secretcode="+scode;
else
window.location.href=serveradd+"/main/TrackUrlManage!getGlobalTrackePage?userid="+userid+"&trackcode="+trackcode+"&secretcode="+scode;
</script>
