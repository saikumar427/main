function getAllWidgets(widgetId, obj, idArray,eid){ 
	var html = '';
	jQuery.each(idArray, function(key, object){
		for(var mKey in object){
			//if(mKey!='tickets'){			
				var date1 = new Date();
				console.log("before each widget starts::"+mKey);			
				html = '<div id="'+mKey+'">'+'</div>'; 			
				jQuery('#'+widgetId).append(html);
				var widgetHtml = obj[mKey];
				if(mKey=='tickets'){
						jQuery('#'+mKey).html("<script type='text/javascript' language='JavaScript' src='/home/js/widget/eventregistration.js'></script><iframe  id='_EbeeIFrame_ticketwidget_"+eid+"' name='_EbeeIFrame_ticketwidget_"+eid+"'  src='http://www.citypartytix.com/eregister?eid="+eid+"&viewType=iframe;resizeIFrame=true&context=web'  height='0' width='100%'></iframe>");					
				}
				else
					jQuery('#'+mKey).html(widgetHtml);
				var date2 = new Date();
				console.log("after each widget completes::"+(date2-date1));
			//}
			
		}
	});
}

/*function getAllWidgets(widgetId, obj, idArray){ 
	var html = '';
	$.each(idArray, function(key, object){
		for(mKey in object){
			html = '<div id="'+mKey+'">'+'</div>'; 
			document.getElementById(widgetId).innerHTML=document.getElementById(widgetId).innerHTML+html;
			document.getElementById(mKey).innerHTML=obj[mKey];
			
		}
	});
}
*/

