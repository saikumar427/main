<%@taglib uri="/struts-tags" prefix="s"%>
<!--begin custom header content for this example-->
<div id="settings_tab"></div>
<script type="text/javascript">
YAHOO.namespace("settings.tab");
var tabView;
(function() {
    tabView = new YAHOO.widget.TabView();    
    tabView.addTab( new YAHOO.widget.Tab({
        label: 'Profile',
        dataSrc: 'accountinfo',
        cacheData: false,     
        active: true        
    }));   
    tabView.appendTo('settings_tab');
})();

YAHOO.util.Event.onContentReady("settings_update",function(){
var settings_update_Btn = new YAHOO.widget.Button("settings_update",{onclick:{fn:updateAccountsInfo}});
});
function updateAccountsInfo(){
	$('accInfoForm').request({
  		onComplete: function(o){
  		tabView.getTab(0).set('content',o.responseText);
  		var settings_update_Btn = new YAHOO.widget.Button("settings_update",{onclick:{fn:updateAccountsInfo}});
  		$('updateInfo').scrollTo();
  		}
	})
}
</script>