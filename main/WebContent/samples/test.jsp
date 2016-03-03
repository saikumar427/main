<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">

<head profile="http://gmpg.org/xfn/1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Language" content="en">
<meta name="robots" content="index, follow">
<meta name="Keywords" content="online registration, registration software, online ticketing, event ticketing, eventbee" />

<title>Eventbee - $eventName</title>



<link rel="icon" href="/home/images/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="/home/images/favicon.ico" type="image/x-icon">

#if($scriptTag)
$scriptTag
#end

<style type="text/css">$customcss</style>
<link rel="stylesheet" type="text/css" href="http://www.eventbee.com/home/index.css" />

<link rel="stylesheet" href="http://blowupsf.com/wp-content/themes/starter/960/960.css" /> <!-- GRID. DO NOT MODIFY -->
    <link rel="stylesheet" href="http://blowupsf.com/wp-content/themes/starter/style.css" /> <!-- YOUR CUSTOM STYLESHEET -->
</head>

<body>
<div align="center">
<div id="navigation_bg">


<div id="site_container" class="container_16 clearfix" >
  <div id="sites_navigation" class="grid_16">
    <ol>
      <li id="jeffrey_and_ava"><a href="http://www.jeffreyandava.com"><span class="invisible">Jeffrey & Ava</span></a></li>
      <li id="blow_up"><a href="http://www.blowupsf.com"><span class="invisible">Blow Up</span></a></li>
      <li id="club_1994"><a href="http://www.club1994.com"><span class="invisible">Club 1994</span></a></li>
    </ol>
  </div>
  <div id="top_header" class="grid_16">
    <div id="blow_up_logo"> </div>
  </div>
  <div id="main_navigation_container" class="grid_16">
    <ol id="main_navigation">

      <li id="home"><a href="http://blowupsf.com/">home</a></li>
      <li id="twitter"><a href="http://twitter.com/blowupsf" target="_blank">twitter</a></li>
      <li id="blog"><a href="http://blowupsf.com/blog/">blog</a></li>
      <li id="photos"><a href="http://blowupsf.com/photos/">photos</a></li>
      <li id="videos"><a href="http://blowupsf.com/videos">videos</a></li>
      <li id="contact"><a href="http://blowupsf.com/contact">contact/booking</a></li>

      <li id="newsletter"><a href="http://blowupsf.com/newsletter/">subscribe to newsletter</a></li>

    </ol>
  </div>

<!-- end header -->





<div id="container" >
<br/>
<div>
<div style="float:left;">

#if ($groupUrl)<a href="$groupUrl">$groupName</a> > #end <b>$eventName</b><br/>
    <!-- AddThis Bookmark Button BEGIN -->
    <div>
    <script type="text/javascript">
    addthis_url    = location.href;
    addthis_title  = document.title;
    addthis_pub    = 'eventbee';
    </script><script type="text/javascript"
    src="http://s7.addthis.com/js/addthis_widget.php?v=12"></script>
    </div>
 </div>
<div style="float:right;">
 #if($trackPartnerPhoto)
    $trackPartnerPhoto
    <br/>
 #end
 #if($trackPartnerMessage)
    <span class="smallestfont">$trackPartnerMessage</span>
 #end
</div>
</div>

<div style="float:left;">
<table width="100%" valign="top" >
<tr>

<!-- start: left column -->

<td id="content" valign="top">

<!-- start: registration block -->
#if ($displayTickets)
 <div id="box">
  <table width="100%" colspan='5'>
  <tr><td width="100%">
   <div class="medium">Registration $creditcardLogos</div>
  </td></tr>
  #foreach( $entry in $displayTickets )

     #if($entry.get("ticketGroupName"))
     <tr>
    <td  colspan='2'><b>
     $entry.get("ticketGroupName")</b>
       #if($entry.get("ticketGroupDesc"))
      <br/>$entry.get("ticketGroupDesc")
      #end
      </td></tr>
    #end

  #set ($tickets = $entry.get("tickets"))
  #foreach( $ticketEntry in $tickets )
  <tr><td colspan='2'>
  #if($ticketEntry.get("isLooseTicket"))
    <b>$ticketEntry.get("ticketname"):</b>
    #if($ticketEntry.get("splprice"))
    <font color="red"><strike><b>$currencyFormat$ticketEntry.get("price")</b></strike>
    </font> $currencyFormat$ticketEntry.get("splprice") Discount Applied
    #else
    <b> $currencyFormat$ticketEntry.get("price")</b>
    #end
    #if($ticketEntry.get("status")=="CLOSED")
    <span class="statusmsg"> $ticketEntry.get("statusMsg")</span>
    #end
    #if($ticketEntry.get("status")=="SOLDOUT")
    <span class="statusmsg"> $ticketEntry.get("statusMsg")</span>
    #end
    #if ($ticketEntry.get("startdate") || $ticketEntry.get("enddate"))
     <br/><span class="smallestfont">Available </span>
      #if($ticketEntry.get("startdate"))
      <span class="smallestfont">$ticketEntry.get("startdate")</span>
      #end
      #if($ticketEntry.get("start_time"))
      <span class="smallestfont">
      $ticketEntry.get("start_time")</span> - #end
      #if($ticketEntry.get("enddate"))
      <span class="smallestfont">$ticketEntry.get("enddate")</span>
      #end
      #if($ticketEntry.get("end_time"))
      <span class="smallestfont">
      $ticketEntry.get("end_time")</span>
      #end
    #end
    #if($ticketEntry.get("ticketDescription"))
    <br/>$ticketEntry.get("ticketDescription")
    #end
  #else
    <table cellpadding="0" cellspacing="0"><tr><td width="15"></td>
    <td cellpadding="5"><b>$ticketEntry.get("ticketname"): </b>
    #if($ticketEntry.get("splprice"))
      <font color="red"><strike><b>$currencyFormat$ticketEntry.get("price")</b></strike>
      </font> $currencyFormat$ticketEntry.get("splprice") Discount Applied
    #else
    <b> $currencyFormat$ticketEntry.get("price")</b>
    #end
    #if($ticketEntry.get("status")=="CLOSED")
    <span class="statusmsg"> $ticketEntry.get("statusMsg")</span>
    #end
    #if($ticketEntry.get("status")=="SOLDOUT")
    <span class="statusmsg">$ticketEntry.get("statusMsg")</span>
    #end
    #if ($ticketEntry.get("startdate") || $ticketEntry.get("enddate"))
     <br/><span class="smallestfont">Available </span>
      #if($ticketEntry.get("startdate"))
      <span class="smallestfont">$ticketEntry.get("startdate")</span>
      #end
      #if($ticketEntry.get("start_time"))
      <span class="smallestfont">
      $ticketEntry.get("start_time")</span> - #end
      #if($ticketEntry.get("enddate"))
      <span class="smallestfont">$ticketEntry.get("enddate")</span>
      #end
      #if($ticketEntry.get("end_time"))
      <span class="smallestfont">
      $ticketEntry.get("end_time")</span>
      #end
    #end
    #if($ticketEntry.get("ticketDescription"))
    <br/>$ticketEntry.get("ticketDescription")
    #end
    </td>
    </tr>
    </table>
  #end

  </td></tr>
  #end
  #end
  <tr><td height="5"></td></tr>
  <tr><td align="left">
  #if($registerButton)
  $registerButton
  #end
  </td></tr>
  </table>
 </div>
 #end
 <br/>


<!-- end: registration block -->

<!-- start: description block -->

<div id="box" align="left">
   <table width="100%">
      <tr><td>
      $eventPhoto
      </td></tr>
      <tr><td>
      $description
      </td></tr></table>
</div>
</td>

<!-- end: description block -->

<!-- start: right column -->

<td id="navi" valign="top">

<!-- start: when and where -->

   <div id="box" align="left">
      <table width="100%">
      <tr>
      <td width="100%" valign="top">
      <div class="medium">When</div>
                        $startDate <span class="smallestfont">Start</span><br/>
	 	        $endDate <span class="smallestfont">End</span>
      #if( $addCalendarLink)
         <br/><img src="http://www.eventbee.com/home/images/addcal.png" border="0"/> $addCalendarLink
      #end

      </td></tr>
      <tr>
      <td width="100%" valign="top">
      <div class="medium">Where</div>
         #foreach( $address in $fullAddress ) $address<br/>
	 #end
         #if($googleMap)
	    <div><img src="http://www.eventbee.com/home/images/viewmap.png" border="0"/> $mapString</div>
	   $googleMap
	   #end

      </td></tr></table>
   </div>
   <br/>

<!-- end: when and where -->

<!-- start: network ticket selling -->

#if($networkTktEnabled)
   <div id="ntsbox" align="left">
      $networkSellingMsg
   </div>
   <br/>


#end

<!-- end: network ticket selling -->

<!-- start: event links -->

<div id="box" align="left" >
   <table width="100%"><tr><td>
      #if( $rsvpButton)
	  <div>$rsvpButton</div>
      #end
     <!-- #if( $eventListedBy)
         <li>$eventListedBy</li>
      #end
      #if( $mgrEventsLink)
         <li>$mgrEventsLink</li>
      #end
      -->
      #if( $contactMgrlink)
         <li>$contactMgrlink</li>
      #end
      #if( $emailToFriendLink)
         <li>$emailToFriendLink</li>
      #end
       #if( $eventURL)
         <li>$eventURL</li>
      #end
      #if( $eventPrintableVersionLink)
         <li>$eventPrintableVersionLink</li>
      #end
      #if( $eventLink)
         <li>$eventLink</li>
      #end

       </td></tr>
   </table>
</div>

<!-- end: event links -->


<!--start: attendee list-->


#if($viewAttendeeList)
   <br/>
   <div id="box" align="left">
      <table width="100%"><tr><td>
      <div class="medium">Who's Attending</div>
      #set($rlistentry =$viewAttendeeList.get("registeredattendees"))
         #if($rlistentry)
            #foreach($xentry in $rlistentry)
               <li>$xentry.get("name") </li>
	        $xentry.get("comments")
	    #end

	 #end
      #set($listentry =$viewAttendeeList.get("yesattending"))
	 #if($listentry)
	 <b>Yes ($viewAttendeeList.get("yesattendingcount"))</b>
	    #foreach($xentry in $listentry)
               <li>$xentry.get("name") - $xentry.get("attendeecount")</li>
               $xentry.get("comments")
            #end
	 #end
      #set($listentry1 =$viewAttendeeList.get("notsureattending"))
         #if($listentry1)
            <b>Not Sure ($viewAttendeeList.get("notsureattendingcount"))</b>
               #foreach($xentry in $listentry1)
                  <li>$xentry.get("name") - $xentry.get("attendeecount")
                  </li>
                  $xentry.get("comments")
               #end
         #end
      #set($listentry2 =$viewAttendeeList.get("notattending"))
         #if($listentry2)
            <b>No ($viewAttendeeList.get("notattendingcount"))</b>
               #foreach($xentry in $listentry2)
                  <li>$xentry.get("name") - $xentry.get("attendeecount")</li>
                  $xentry.get("comments")
               #end
         #end
      </td></tr></table>
   </div>
#end

<!--end: attendee list-->

<!-- Start: noticeboard -->

#if ($notices)
   <br/>
   <div id="box" align="left">
   <div class="medium">Notice Board</div>
      <table width="100%">
      #foreach( $entry in $notices)
      <tr>
      <td width="100%">
         <table><tr>
         <td width="20%">$entry.get("noticetype")</td><td width="10%"></td><td width="70%">$entry.get("postedDate")</td>
         </tr></table>
      </td></tr>
      <tr><td width="100%">
         <table>
         <tr><td width="100%">$entry.get("notice")</td></tr>
         </table>
      </td></tr>
      #end
      </table>
   </div>

#end

<!--end: notice board-->

<!--start: recommended events-->

 #if($partnerStreamerShow)
   #if($partnerStreamer)
   <br/>
   <div id="box">
      <table width="100%" align="center"><tr><td>
         $partnerStreamer
      </td></tr></table>
   </div>
   #end
#end

<!--end: recommended events-->

</td>

<!-- end:right column -->

</tr>
</table>
</div>
</div>

<div style="float:left;">
   $eventbeeFooter
</div>
</div>
</div>
</div>
</body>
</html>
