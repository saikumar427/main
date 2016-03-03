var save="";
function TicketWedgetControl(tktID)
{  
   var eventId='';
	this.tktID=tktID;
	var ticketName='';
	var ticketPrice='';
	var ticketFee='';
	var ticketStatusMsg='';
	var ticketActualPrice='';
	var ticketChargingPrice='';
	var ticketActualFee='';
	var ticketChargingFee='';
	var ticketProcessFee='';
	var ticketfinalProcessFee='';
	var ticketSelQty='';
	var ticketMinQty='';
	var ticketMaxQty='';
	var ticketIsAvailable='';
	var ticketDesc='';
	var ticketType='';
	var ticketGroupId='';
	var ticketGroupType='';
	var ticketIsMemberTicket='';
	var ticketDonationAmount='';
	var ticketErrorMessage='';
	var ticketIsEnabled='';
	var ticketPositionIndex='';
	var ticketClassName='';
	var ticketGroupId='';
	var ticketSmallDesc='';
	
	var elementDisplayStatusArray=new Array();
	//var ticketDonationQty=0
	this.SetTicketName = function(tktName){
		ticketName=tktName;
		_labeltktName.innerHTML	= ticketName;
	}
	this.GetTicketName = function(){return ticketName;}
	/*****************************************************************************************************************/
		this.SetActualPrice = function(tktActualPrice){
			ticketActualPrice = tktActualPrice
			//ticketChargingPrice = ticketActualPrice;
			this.SetTicketPrice(ticketActualPrice,ticketChargingPrice);
		}
		this.SetChargingPrice = function(tktChargingPrice){
			ticketChargingPrice = tktChargingPrice;
			this.SetTicketPrice(ticketActualPrice,ticketChargingPrice);
		}
	
		this.SetActualFee = function(tktActualFee){
		 	ticketActualFee = tktActualFee;
			this.SetTicketFee(ticketActualFee,ticketChargingFee);
		}
		this.SetChargingFee = function(tktChargingFee){
			ticketChargingFee = tktChargingFee;
			this.SetTicketFee(ticketActualFee,ticketChargingFee);
		}
	/*****************************************************************************************************************/
	this.SetTicketPrice	= function(tktActualPrice,tktChargingPrice){
		if(tktChargingPrice == '')
		
			tktChargingPrice = tktActualPrice;
		ticketActualPrice = tktActualPrice;
		ticketChargingPrice = tktChargingPrice;
		if(ticketActualPrice == ticketChargingPrice)
		{
             
		    _labeltktPrice.innerHTML = priceshow;
			_ticketHiddenPrice.value = ticketActualPrice;
			_ticketHiddenFinalPrice.value=ticketActualPrice;
			
			
		}
		else
		{
			_labeltktPrice.innerHTML =  "<strike>"+ticketActualPrice+"</strike><br/>"+ticketChargingPrice;
			_ticketHiddenFinalPrice.value = ticketChargingPrice;
		}
	}
	
	this.setDonationTicketQty	= function(qty){
	 _donationHiddenQty.value=qty;
	}
	
	this.setDonationPrice	= function(donationAmount){
		 _ticketHiddenPrice.value=donationAmount;
		 _ticketHiddenFinalPrice.value=donationAmount;
	}
	
	this.GetDonationTicketQty	= function(){return _donationHiddenQty.value;}
	
	this.GetTicketPrice	= function(){return ticketActualPrice;}
	this.SetTicketFee = function(tktActualFee,tktChargingFee){
			ticketActualFee = tktActualFee;
			ticketChargingFee = tktChargingFee
			if(ticketActualFee == ticketChargingFee)
			{
				_labeltktFee.innerHTML = ticketActualFee;
				_ticketHiddenFee.value = ticketActualFee;
			}
			else
			{
				_labeltktFee.innerHTML = "<strike>"+ticketActualFee+"</strike><br/>"+tktChargingFee;
				_ticketHiddenFee.value = tktChargingFee;
			}
	}
	this.GetTicketFee = function(){return 	_labeltktFee.innerHTML;}

	this.SetTicketChargingPrice = function(tktChargingPrice){
		ticketChargingPrice = tktChargingPrice;
	}
	this.GetTicketChargingPrice = function(){return ticketChargingPrice;}

	this.SetTicketStatusMsg = function(tktMsg){
		ticketStatusMsg = tktMsg;
		_labeltktStatus.innerHTML = ticketStatusMsg;
	}
	this.GetTicketStatusMsg = function(){return ticketStatusMsg;}
	
	this.SetTicketType = function(tktType){
		ticketType = tktType;
		_ticketHiddenType.value=tktType;
	}
	this.GetTicketType = function(){return ticketType;}
	this.SetTicketSmallDesc = function(txtDesc){
		_spansmallDesc.innerHTML = txtDesc;
	}
	this.SetMemberTicketLogin = function(){
		ticketIsMemberTicket = 'Y';
		ticketSmallDesc = _spansmallDesc.innerHTML;
		if(ticketSmallDesc!="")
			_spansmallDesc.innerHTML = _spansmallDesc.innerHTML + '<br\>* Need Member <a href="#" onclick="javascript:funMemberTicketLogin('+tktID+','+eventId+');">login</a> Login  to buy';
		else
			_spansmallDesc.innerHTML = '* Need Member <a href="#" onclick="javascript:funMemberTicketLogin('+tktID+','+eventId+');">login</a> Login  to buy';
	}
	this.ClearMemberTicketLogin = function(){
		if(ticketIsMemberTicket=='Y')
		{
			_spansmallDesc.innerHTML = ticketSmallDesc;
			_select.disabled	= false;
		}
	}
	this.SetTicketDesc = function(tktDesc,Id){
		if(tktDesc!='undefined')
		{
			ticketDesc = tktDesc
			_labeltktName.innerHTML = _labeltktName.innerHTML + ' <img style="cursor:pointer;" id=imgShowHide'+Id+' src="/home/images/expand.gif" onClick=showDescription('+tktID+');>';
			_ticketDescLabel.innerHTML = ticketDesc;
		}
		//_ticketTable.appendChild(_ticketDescTr);
	}
	this.toggleDescription = function(ticketID){
		var currentExpandstatus=false;
		if(elementDisplayStatusArray['descTR'+ticketID]){
		currentExpandstatus=elementDisplayStatusArray['descTR'+ticketID];
		}
		if(currentExpandstatus)
		{
			if(document.getElementById('imgShowHide'+ticketID))
				document.getElementById('imgShowHide'+ticketID).src="/home/images/expand.gif";
			if(document.getElementById('descTR'+ticketID))
				document.getElementById('descTR'+ticketID).style.display = 'none';
		}
		else
		{
			if(document.getElementById('imgShowHide'+ticketID))
				document.getElementById('imgShowHide'+ticketID).src="/home/images/collapse.gif";
			if(document.getElementById('descTR'+ticketID))
				document.getElementById('descTR'+ticketID).style.display = 'block';
		}
		//if(elementDisplayStatusArray['descTR'+ticketID])
			elementDisplayStatusArray['descTR'+ticketID]=!currentExpandstatus;
	}
	
	this.GetTicketDesc = function(){return ticketDesc;}

	this.SetTicketClassName = function(tktClassName){
		
		ticketClassName		= tktClassName;
		_ticketTable.className	= ticketClassName;
	}
	this.GetTicketClassName = function(){return tktClassName;}
	
	this.SetTicketQuantity = function(minQty,maxQty,selectedQty,IsMemberTicket){	
		_select.name	= "qty_"+tktID;
		_select.id="qty_"+tktID;
		
		if(IsMemberTicket == 'Y')	_select.disabled	= true;
		_option			= document.createElement("option");
		_option.text	= 0;
		_option.value	= 0;
		_select.options.add(_option); 
		for(var i=parseFloat(minQty);i<=parseFloat(maxQty);i++)
		{	
			_option		= document.createElement("option");
			_option.text	= i;
			_option.value	= i;
			if(i == selectedQty)
			{
				_option.selected = true;
			}
			_select.options.add(_option); 
		}
	}

	this.SetTicketActualFee = function(tktActualFee){ticketActualFee = tktActualFee;}
	this.GetTicketActualFee = function(){return ticketActualFee;}
	this.SetTicketChargingFee = function(tktChargingFee){ticketChargingFee = tktChargingFee;}
	this.GetTicketChargingFee = function(){return ticketChargingFee;}
	this.SetTicketProcessFee = function(tktProcessFee){ticketProcessFee = tktProcessFee;}
	this.GetTicketProcessFee = function(){return ticketProcessFee;}
	this.getTicketGroupId=function(){return ticketGroupId;}
	this.SetTicketGroupId = function(tktGroupId){ticketGroupId = tktGroupId;_ticketHiddenGroupid.value=tktGroupId;}
	this.SetEventId=function(EventId){eventId = EventId;}
	//_container.style.height =	"40px";
	//_container.style.width =	"100%"
	var _container			= document.createElement("DIV");	
	var _ticketTable		= document.createElement("TABLE");
	var _ticketTbody		= document.createElement("tbody");
	var _ticketTr			= document.createElement("TR");
	var _ticketDescImgTd	= document.createElement("TD");
	var _ticketDescTR		= document.createElement("TR");
	var _ticketDescTd		= document.createElement("TD");
	var _tktDummyTd			= document.createElement("TD");
	var _tktDummyTd1			= document.createElement("TD");
	var _ticketDescLabel	= document.createElement("label");
	_ticketDescTR.id = "descTR"+tktID;
	_ticketDescTR.style.display = "none";
	var _tktsmallDescTR		= document.createElement("TR");
	var _tktsmallDescTD		= document.createElement("TD");
	var _spansmallmessage		= document.createElement("DIV");
	var _spansmallDesc		= document.createElement("DIV");
	_container.setAttribute("class","widgetcontainer");
	var vbdiv=document.createElement("DIV");
	_spansmallDesc.setAttribute("class","small");
	_spansmallmessage.id="divmsg_"+tktID;
	/********************Hidden Fields*********************/
	var _ticketHiddenPrice	= document.createElement("INPUT");
	_ticketHiddenPrice.type="hidden";
	_ticketHiddenPrice.id="originalprice_"+tktID;
	_ticketHiddenPrice.name="originalprice_"+tktID;	
	_container.appendChild(_ticketHiddenPrice);

	var _ticketHiddenFee= document.createElement("INPUT");
	_ticketHiddenFee.type="hidden";
	_ticketHiddenFee.id="processfee_"+tktID;
	_ticketHiddenFee.name="processfee_"+tktID;	
	_container.appendChild(_ticketHiddenFee);
	
	var _ticketHiddenFinalPrice= document.createElement("INPUT");
	_ticketHiddenFinalPrice.type="hidden";
	_ticketHiddenFinalPrice.id="finalprice_"+tktID;	
	_ticketHiddenFinalPrice.name="finalprice_"+tktID;	
	
	_container.appendChild(_ticketHiddenFinalPrice);

	var _ticketHiddenFinalFee= document.createElement("INPUT");
	_ticketHiddenFinalFee.type="hidden";
	_ticketHiddenFinalFee.id="finalprocessfee_"+tktID;
	_ticketHiddenFinalFee.name="finalprocessfee_"+tktID;	
	
	_container.appendChild(_ticketHiddenFinalFee);
	
	var _ticketHiddenGroupid= document.createElement("INPUT");
		_ticketHiddenGroupid.type="hidden";
		_ticketHiddenGroupid.id="ticketGroup_"+tktID;
		_ticketHiddenGroupid.name="ticketGroup_"+tktID;	
		_container.appendChild(_ticketHiddenGroupid);

	var _ticketHiddenType= document.createElement("INPUT");
			_ticketHiddenType.type="hidden";
			_ticketHiddenType.id="ticketType_"+tktID;
			_ticketHiddenType.name="ticketType_"+tktID;	
			_container.appendChild(_ticketHiddenType);
	
	
	
	

	 var _donationHiddenQty= document.createElement("INPUT");
	_donationHiddenQty.type="hidden";
	_donationHiddenQty.id="qty_"+tktID;
	_donationHiddenQty.name="qty_"+tktID;
	_donationHiddenQty.value="0";
	
	/**vb hidden*/
	var _tickettriggerprice	= document.createElement("INPUT");
	_tickettriggerprice.type="hidden";
	_tickettriggerprice.id="triggerprice_"+tktID;
	_tickettriggerprice.name="triggerprice_"+tktID;	
	
	var _ticketnoTriggerDiscountValue	= document.createElement("INPUT");
	_ticketnoTriggerDiscountValue.type="hidden";
	_ticketnoTriggerDiscountValue.id="triggerprice_"+tktID;
	_ticketnoTriggerDiscountValue.name="triggerprice_"+tktID;	
	
	
	/**vb hidden*/
	

	
	    
	/********************************************************/
	
	_ticketTable.style.width	=	"100%";

	var _labeltktName	= document.createElement("label");
	var _labeltktStatus	= document.createElement("label");
	var _select			= document.createElement("select");	
	var _labeltktPrice	= document.createElement("label");
	var _labeltktFee	= document.createElement("label");
	var _imgtktDesc		= document.createElement("a");
	var _txtktDonate	= document.createElement("INPUT");
	var _txtktvbmsg=document.createElement("label");
	
	_txtktDonate.type = 'text';
	_txtktDonate.setAttribute("size","4");
	_ticketTbody.id="tktbodyimg_"+tktID;
	_ticketTbody.appendChild(_ticketTr);
	//_ticketTbody.appendChild(_tktsmallDescTR);
	_ticketTbody.appendChild(_ticketDescTR);
	
	_ticketTable.appendChild(_ticketTbody);
	_container.appendChild(_ticketTable);

	this.GetContainer = function()
	{ 
		return _container; 
	}

	this.initializeControls = function(tktData)
	{   
		var _ticketGroupTd	= document.createElement("TD");
		var _ticketNameTd	= document.createElement("TD");

		if(tktData['isLooseTicket']=='N')
		{
			_ticketGroupTd.style.width	=	"5%";
			_ticketGroupTd.setAttribute("rowSpan",3);
			_ticketGroupTd.id="groupname_"+tktID;
			_ticketNameTd.style.width	=	"48%";
			
			_ticketNameTd.appendChild(_labeltktName);
			_ticketTr.appendChild(_ticketGroupTd);
			_ticketTr.appendChild(_ticketNameTd);
			/******************** More Option related**************************/
			_tktDummyTd1.setAttribute("width","5%");
			_ticketDescTd.setAttribute("colSpan",4);
			_ticketDescTd.appendChild(_ticketDescLabel);
			_ticketDescTd.setAttribute("colSpan",4);
			_ticketDescTR.appendChild(_ticketDescTd);
			
			/*******************************************************************/
			_ticketNameTd.appendChild(_spansmallmessage);
			_ticketNameTd.appendChild(_spansmallDesc);
			//_tktsmallDescTR.appendChild(_tktsmallDescTD);
		}
		else
		{
			_ticketGroupTd.style.width	=	"0%";
			_ticketNameTd.style.width	=	"46%";
			
			_ticketNameTd.appendChild(_labeltktName);
			_ticketTr.appendChild(_ticketNameTd);
			/******************** More Option related*************************/
			_ticketDescTd.appendChild(_ticketDescLabel);
			_ticketDescTR.appendChild(_ticketDescTd);
			_ticketDescTd.setAttribute("colSpan",4);
			_ticketDescTd.setAttribute("width","100%");
			/*****************************************************************/
			_ticketNameTd.appendChild(_spansmallmessage);
			_ticketNameTd.appendChild(_spansmallDesc);
			//_tktsmallDescTR.appendChild(_tktsmallDescTD);
		}
		if(tktData['Available']=='Y')
		{
		
			if(tktData['DonateType']=='Y')
			{
				var _ticketDonateTd	= document.createElement("TD");
				_ticketDonateTd.setAttribute("align","right");
				_ticketDonateTd.setAttribute("valign","top");
				_ticketDonateTd.style.width		=	"15%";
				_ticketDonateTd.appendChild(_txtktDonate);
				_ticketTr.appendChild(_ticketDonateTd);
				var _ticketDonateDummyTd=document.createElement("TD");
				_ticketDonateDummyTd.setAttribute("align","right");
				
				_ticketDonateDummyTd.style.width="35%";
				_ticketTr.appendChild(_ticketDonateDummyTd);
				_container.appendChild(_donationHiddenQty);

				if(tktData['Msg']=='NA'){
				 		      _txtktDonate.setAttribute("disabled","true");
							  _ticketDonateDummyTd.style.width="25%";
							  var _ticketHiddenquantity= document.createElement("INPUT");
								_ticketHiddenquantity.type="hidden";
								_ticketHiddenquantity.id="qty_"+tktID;
								_ticketHiddenquantity.name="qty_"+tktID;
								_ticketHiddenquantity.value="0";	

								_container.appendChild(_ticketHiddenquantity);

								var _ticketStatusTd	= document.createElement("TD");
								_ticketStatusTd.setAttribute("align","right");
								_ticketStatusTd.style.width	=	"50%";
								_ticketStatusTd.appendChild(_labeltktStatus);
								_ticketTr.appendChild(_ticketStatusTd);
								
					}
			}
			else
			{
				
				/*****************************************************/	
				_labeltktPrice.name		= "lblPrice"+tktID;
				_labeltktPrice.id		= "lblPrice"+tktID;	
				var _ticketPriceTd		= document.createElement("TD");
				_ticketPriceTd.setAttribute("align","right");
				_ticketPriceTd.setAttribute("valign","top");
				_ticketPriceTd.style.width	=	"25%";
				_ticketPriceTd.appendChild(_labeltktPrice);
				_ticketTr.appendChild(_ticketPriceTd);
				/*****************************************************/
				_labeltktFee.name		= "lblFee"+tktID;
				_labeltktFee.id			= "lblFee"+tktID;	
				var _ticketFeeTd		= document.createElement("TD");
				_ticketFeeTd.setAttribute("align","right");
				_ticketFeeTd.setAttribute("valign","top");
				_ticketFeeTd.style.width	=	"13%";
				_ticketFeeTd.appendChild(_labeltktFee);
				_ticketTr.appendChild(_ticketFeeTd);
				/*****************************************************/
				if(tktData['Msg']!='NA'){
				 var _ticketVolumeTd		= document.createElement("TD");
				_ticketVolumeTd.setAttribute("align","right");
				_ticketVolumeTd.setAttribute("valign","top");
				_ticketVolumeTd.style.width	=	"24%";
				_ticketVolumeTd.id="td_ticketid_"+tktID;
				_ticketVolumeTd.appendChild(_select);
				_ticketTr.appendChild(_ticketVolumeTd);
				}
				else{
				var _ticketHiddenquantity	= document.createElement("INPUT");
				_ticketHiddenquantity.type="hidden";
				_ticketHiddenquantity.id="qty_"+tktID;
				_ticketHiddenquantity.name="qty_"+tktID;
				_ticketHiddenquantity.value="0";	

				_container.appendChild(_ticketHiddenquantity);

				var _ticketStatusTd		= document.createElement("TD");
				_ticketStatusTd.setAttribute("align","right");
				_ticketStatusTd.style.width	=	"50%";
				_ticketStatusTd.appendChild(_labeltktStatus);
				_ticketTr.appendChild(_ticketStatusTd);
				
				}
				/*****************************************************/
			}
		
			
			
		}
		else
		{
				
			var _ticketHiddenquantity	= document.createElement("INPUT");
			_ticketHiddenquantity.type="hidden";
			_ticketHiddenquantity.id="qty_"+tktID;
			_ticketHiddenquantity.name="qty_"+tktID;
			_ticketHiddenquantity.value="0";	
			
			_container.appendChild(_ticketHiddenquantity);

			var _ticketStatusTd		= document.createElement("TD");
			_ticketStatusTd.setAttribute("align","center");
			_ticketStatusTd.style.width	=	"50%";
			_ticketStatusTd.appendChild(_labeltktStatus);
			_ticketTr.appendChild(_ticketStatusTd);
		}
       ////vbb///////////
	   	/**vb**/
			{
			 priceshow="<div>"+volumewording['dispricemsg']+": $"+tktData['ticketDiscountPrice']+"</div><div>"+volumewording['pricemsg']+": $"+tktData['ActualPrice']+"</div>";
		   var _ticketvb= document.createElement("div");
		         _ticketvb.setAttribute("margin-top","50px");
				  _ticketvb.setAttribute("id","vbsavemsg");
				_ticketvb.setAttribute("align","right");
				_ticketvb.setAttribute("valign","top");
				_ticketvb.style.width="100%";
				_ticketvb.style.weight="bold";
				_ticketvb.appendChild(_txtktvbmsg);
				_container.appendChild(_ticketvb);
							//hidden setting
				_ticketvb.appendChild(_tickettriggerprice);
				//	alert("yes end td of vb");
		}
	}
		/**vb**/
	/**vb**/
	this.SetVBdata=function(msg)
	{
//	alert("setvb");
//	_txtktvbmsg.innerHTML =msg;
	//	alert("end setvb");
		}
	
	/**vb**/	
	
	this.SetTicketData = function(tktData)
	{		this.initializeControls(tktData);
			this.SetTicketStatusMsg(tktData['Msg']);
			this.SetTicketGroupId(tktData['GroupId']);
			var tckname=tktData['Name'];
			if(tktData['Strike']!=undefined && tktData['Strike']=='Y')
			tckname='<strike>'+tckname+'</strike>';
			this.SetTicketName(tckname);
			this.SetActualPrice(tktData['ActualPrice']);
			this.ticketIsAvailable=tktData['Available'];
			this.ticketStatusMsg=tktData['Msg'];
			this.SetEventId(tktData['eventId']);
			if(typeof(tktData['ChargingPrice'])!="undefined")
			{
				this.SetChargingPrice(tktData['ChargingPrice']);
			}
			this.SetActualFee(tktData['ActualFee']);
			if (typeof(tktData['ChargingFee'])!="undefined")
			{
				this.SetChargingFee(tktData['ChargingFee']);
			}
			//this.SetTicketPrice(tktData['ActualPrice'],tktData['ChargingPrice']);
			//this.SetTicketFee(tktData['ActualFee'],tktData['ChargingFee']);
			this.SetTicketQuantity(tktData['Min'],tktData['Max'],tktData['Selected'],tktData['IsMemberTicket']);
			this.SetTicketClassName(tktData['Style']);
			this.SetTicketType(tktData['ticketType']);
			if(typeof(tktData['Desc'])!="undefined")
			{
				this.SetTicketDesc(tktData['Desc'],tktData['Id']);
			}	
			if(typeof(tktData['smallDesc'])!="undefined")
			{
				this.SetTicketSmallDesc(tktData['smallDesc']);
			}
			if((typeof(tktData['IsMemberTicket'])!="undefined") && (tktData['IsMemberTicket']=="Y"))
			{
				this.SetMemberTicketLogin();
			}
		/**vb**/	
		//if(vb=="true")
			{
		
			var vbmsg="You Save: $"+tktData['ticketSave']+"</span>";
			//alert("vbmsg"+vbmsg);
			save=tktData['ticketSave'];
			_tickettriggerprice.value=tktData['ticketDiscountPrice'];
	          
	         // this.SetTicketPrice(tktData['ActualPrice'],'');			
			_ticketVB="True";
			this.SetVBdata(vbmsg);
			}
			
			
			
		/**vb**/
			
	}
	_select.onchange = function()
	{
		eventOnSelectChanged(tktID);
		//if(vb=="true")
		{  
		//yousave(tktID);
		}
	}
	_txtktDonate.onblur = function()
	{
		eventOnblurDonate(tktID,_txtktDonate.value);
	}
	/*_txtktDonate.onkeyup = function()
	{
		eventOnblurDonate(tktID,_txtktDonate.value);
	}
	*/
}

function yousave(tktID)
{ var qid='qty_'+tktID;
var selqty=document.getElementById(qid).value;

var msgg="You Save: $"+formatCurrency(save*selqty);
document.getElementById('vbsavemsg').innerHTML=msgg;
}
function formatCurrency(num) {
    num = isNaN(num) || num === '' || num === null ? 0.00 : num;
    return parseFloat(num).toFixed(2);
}

var ticketWidgets=[];
var priceshow="";
 			
function addTicketControl(parentElmId,tktData)
{   var tktCtrl=new TicketWedgetControl(tktData['Id']);
	tktCtrl.SetTicketData(tktData);
	document.getElementById(parentElmId).appendChild(tktCtrl.GetContainer());
	ticketsArray.push(parentElmId);
	ticketWidgets[parentElmId]=tktCtrl;
	if(tktsData['tktDescMode'] && tktsData['tktDescMode']=='expand' && typeof(tktData['Desc'])!="undefined"){
	tktCtrl.toggleDescription(tktData['Id']);
	}
}
var totalAmount=0;

function Initialize(elmName)
{ 
var profeecount=0;
	elm = document.getElementById(elmName);
	var tags =elm.getElementsByTagName('div');
	var searchClass='tktWedgetClass';
	for(i=0; i<tags.length; i++) {
		var test = tags[i].className;
		if (test.indexOf(searchClass) != -1)
		{	
		
if(tktsData[tags[i].id].ChargingFee!=0.00 && (tktsData[tags[i].id].Available=="Y"))
		{
	
		profeecount++;
	
		}
			addTicketControl(tags[i].id,tktsData[tags[i].id]);
		}
		else if(test.indexOf("tckgrpname") != -1){
                setTicketGroupDescription(tags[i].id);
        }
	}

	getTicketsAvailabilityMsg();

	if($('seatingsection')){
		seatingticketresponse();
		getseatingsection();
	}
	
	else{
		getBuyButtonStatus();
 
	     }
	checkprofee(profeecount,tags);	 
}function checkprofee(profeecount,tags)
			{
					if(profeecount==0)
					{
					
					if($('feelabel'))
					$('feelabel').innerHTML="";
									
				var searchClass='tktWedgetClass';
				for(i=0; i<tags.length; i++) {
					var test = tags[i].className;
					if (test.indexOf(searchClass) != -1)
					{		
									var  lblid=	"lblFee"+tags[i].id;
								if($(lblid))$(lblid).hide();
						}
								
								}
	}


}
function funSet(){
	var newVal	=	document.getElementById('txtBOXchange').value;
	var id		=	document.getElementById('txtBOX').value;
	ticketWidgets[id].SetTicketName(newVal);
}
function funGet(){
	var id		=	document.getElementById('txtBOX').value;
	var result = ticketWidgets[id].GetTicketName();
}
function eventOnSelectChanged(ticketID)
{
	if(document.getElementById('totalamount'))
	document.getElementById('totalamount').style.display='none';
}
function eventOnblurDonate(ticketID,text)
{ 
	if(document.getElementById('totalamount'))
	document.getElementById('totalamount').style.display='none';

	if(text!=''&&parseFloat(text)>0){
		ticketWidgets[ticketID].setDonationTicketQty(1);
		ticketWidgets[ticketID].setDonationPrice(parseFloat(text));
   }
   else{
	   ticketWidgets[ticketID].setDonationTicketQty(0);
	   ticketWidgets[ticketID].setDonationPrice(0);
	   }
}
function showDescription(ticketID)
{
	ticketWidgets[ticketID].toggleDescription(ticketID);
}
function funMemberTicketLogin(ticketID,eventId)
{
	getMemeberLoginPopUp(eventId);
}
function funMemberLoggedIn()
{
	var id		=	document.getElementById('txtBOX').value;
	var result = ticketWidgets[id].ClearMemberTicketLogin();
}
