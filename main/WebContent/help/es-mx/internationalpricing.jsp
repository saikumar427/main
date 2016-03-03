<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.eventbee.general.DBManager,com.eventbee.general.StatusObj,java.util.ArrayList,java.util.HashMap" %>    
<%!
   public static ArrayList<HashMap<String,String>> getPricingList(){
   ArrayList<HashMap<String,String>> pricelist=new ArrayList<HashMap<String,String>>();
   HashMap<String,String> currencymap=new HashMap<String,String>();
   DBManager dbm=new DBManager();
   StatusObj statobj=null;
   statobj=dbm.executeSelectQuery("select * from international_pricing order by currency", null);
   if(statobj.getStatus() && statobj.getCount()>0){
	   for(int i=0;i<statobj.getCount();i++){
		   HashMap<String,String> hmap=new HashMap<String,String>();
		   hmap.put("currency",dbm.getValue(i, "currency", ""));
		   hmap.put("currencycode",dbm.getValue(i, "currency_code", ""));
		   hmap.put("currencysymbol",dbm.getValue(i, "currency_symbol", ""));
		   hmap.put("basicfee",dbm.getValue(i, "l100", ""));
		   hmap.put("profee",dbm.getValue(i, "l200", ""));
		   hmap.put("advancedfee",dbm.getValue(i, "l300", ""));
		   hmap.put("paymentprocessor",dbm.getValue(i, "payment_processors", ""));
		   pricelist.add(hmap);
	   }
   }
   return pricelist;
 }

%>
<%
   ArrayList<HashMap<String,String>> pricinglist=new ArrayList<HashMap<String,String>>();
   pricinglist=getPricingList();
%>
<!doctype html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <link type="text/css" rel="stylesheet" href="/main/css/bootstrap/bootstrap.min.css" />
        <link href="/main/font-awesome-4.0.3/css/font-awesome.min.css" rel="stylesheet">
    <style>
            .btn-default{background-color: #D1D0CE}
            .my_featureRow {
              margin-top: 0.2em;
              margin-bottom: 0.2em;
              border: 0.1em solid rgb(163, 163, 163);
            } 
            
            .my_feature {
               line-height:2.8em;   
            }
             .my_plan1 {
               background: none;
               border-left:1px solid #A3A3A3;
             }
			 
			 .my_plan3 {
               background: none;
               border-left:1px solid #A3A3A3;
            }
			
            .my_plan2 {
             background: none;
             border-left:1px solid #A3A3A3;
           }
 
     .my_planFeature {
    text-align: center;
    /*font-size: 2em;*/
    height: 40px;
    padding-top: 5px;
    
} 
    .fstyle{
       font-weight:normal;
      }
    
     </style>
    </head>
    <body>
    
<div class="container">
<div class="row my_featureRow" style="background:#F3F6FA;height:60px;"> 
<div class="col-xs-12 col-sm-8">
<div class="col-xs-3 col-sm-3  my_feature"><label for="curr" class="text-muted">Currency</label></div>
<div class="col-xs-3 col-sm-3  my_planFeature my_paln1"><label for="basic" class="text-muted">Basic</label></div>
<div class="col-xs-3 col-sm-3 my_planFeature my_paln2"><label for="pro" class="text-muted">Pro</label></div>
<div class="col-xs-3 col-sm-3  my_planFeature my_paln3"><label for="adv" class="text-muted">Advanced</label></div></div>
<div class="col-xs-12 col-sm-4">
<div class="my_planFeature my_paln3"><label for="adv" class="text-muted">Payment Processors Supported</label></div></div>
</div>
<%for(int j=0;j<pricinglist.size();j++){
%>
<div class="row my_featureRow">
<div class="col-xs-12 col-sm-8">
<div class="col-xs-3 col-sm-3" style="padding-top:5px;"><label for="curr<%=j%>" class="fstyle"><%=pricinglist.get(j).get("currency")%>&nbsp;(<%=pricinglist.get(j).get("currencysymbol")%>)</label></div>
<div class="col-xs-3 col-sm-3  my_planFeature my_paln1"><label for="basic<%=j%>" class="fstyle"><%=pricinglist.get(j).get("currencysymbol")%><%=pricinglist.get(j).get("basicfee")%></label></div>
<div class="col-xs-3 col-sm-3  my_planFeature my_paln2"><label for="pro<%=j%>" class="fstyle"><%=pricinglist.get(j).get("currencysymbol")%><%=pricinglist.get(j).get("profee")%></label></div>
<div class="col-xs-3 col-sm-3  my_planFeature my_paln3"><label for="adv<%=j%>" class="fstyle"><%=pricinglist.get(j).get("currencysymbol")%><%=pricinglist.get(j).get("advancedfee")%></label></div></div>
<div class="col-xs-12 col-sm-4" style="padding-bottom:10px;">
<div class="my_planFeature my_paln3"><label for="payprocessor<%=j%>" class="fstyle"><%=pricinglist.get(j).get("paymentprocessor")%></label></div></div>
</div>
<%}%>
</div>
<script>
parent.resizeIframe();
</script>