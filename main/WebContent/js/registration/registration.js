var discountsjson;
var discountedprices;
var tktsData='';
var profileJsondata='';
var CtrlWidgets=[];
var responsesdata=[];
var etid="";
var loginuserid='';
var evtdate='';
var eventid='';
var ticketsArray=[];
var tranid='';
var paymenttype;
var oid='';
var context='';;
var domain='';;
var ticketurl='';
var discountcode='';
var track='';
var serveradd;
var code;
var headers=new Object();
var timestamp;
var bfname='';
var blname='';
var bemail='';
var bphone='';
var paymentmode='';
var promotionenable=true;
var isavailableseat="";
var selectTktMsg='Please select ticket(s) Quantity';
var fbsharepopup="";

//waitList level variable

var waitlistId='';

//seating level variables
var sectionid="";
var max_ticketid=new Object();
var min_ticketid=new Object();
var ticket_ids_seats=[];
var allsectionid=[];
var allsectionname=[];
var seatingsectionresponsedata=new Object();
var ticketseatcolor=new Object();
var mins_left = 14;
var s_left = 60;
var mins_remain=14;
var secs_remain=60;
var ticket_groupnames=new Object();
var notavailableticketids=new Array();
var memberseatticketids=new Array();
var seatingticketidresponse="";
var seating_enabled_tkt_wedget="NO";
var venueid="0";
var sel_seatcodes=new Array();
var sel_ticket=new Object();
var ticket_count=new Object();
var ids="";
var ticketnameids=new Object();
var seating_ticketids=new Array();
var seatposition="";
var section_sel_seats=new Object();
var seatinfo=new Object();
var button="<center><input type='button' value='"+props.sea_sel_tkt_btn_lbl+"' id='accept' onclick='closeit(\"Y\")'><input type='button' value='"+props.sea_cancel_btn_lbl+"' id='cancel' onclick='closeit(\"N\")'></center>";
var sel_select=new Object();	
var seatcode_ticid=new Object();
var sel=false;
var count=0;
var reg_timeout='';
var layoutcount=0;
var ntsdata=new Object();
var fblogindata=new Object();
var becamepartner=false;
var ntscode="";
var display_ntscode="";
var fname="",lname="",email="";

// Seating Group seats Seleted Process
var soldOutTickets={};
var GroupSeats=new Array();
var seattickets=new Array();
var seattables=new Array();
var seatingeid="";

//priority registration
var priorityListData='';
var priRegToken='';
var priRegType='';
var priListId='';
var priLimitType='';
var isPriority=false;

/*var seattickets=["73291","73236","73238"];



var table1=["1780_64_21","1780_64_24","1780_65_21","1780_65_24","1780_66_21","1780_66_24","1780_68_22","1780_68_23"];
var table2=["1780_64_14","1780_64_17","1780_65_14","1780_65_17","1780_66_14","1780_66_17","1780_68_15","1780_68_16"];
var table3=["1780_46_68","1780_46_71","1780_47_68","1780_47_71","1780_48_68","1780_48_71","1780_50_69","1780_50_70"];
var table4=["1780_73_21","1780_73_24","1780_74_21","1780_74_24","1780_75_21",
            
            "1780_75_24",
            "1780_77_22",
            "1780_77_23"];

var table5=["1780_55_82",
            "1780_55_85",
            "1780_56_82",
            "1780_56_85",
            "1780_57_82",
            "1780_57_85",
            "1780_59_83",
            "1780_59_84"];


var seatingeid="182401075";
GroupSeats.push.apply(GroupSeats, table1);
GroupSeats.push.apply(GroupSeats, table2);
GroupSeats.push.apply(GroupSeats, table3);
GroupSeats.push.apply(GroupSeats, table4);
GroupSeats.push.apply(GroupSeats, table5);*/






