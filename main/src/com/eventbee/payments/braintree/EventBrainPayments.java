package com.eventbee.payments.braintree;
import java.math.BigDecimal;
import java.util.HashMap;
import com.braintreegateway.*;
public class EventBrainPayments 
{
	private String CreditCardNumber="";
	private String CreditCardType="";
	private String CVV2="";
	private String ExpMonth="0";
	private String ExpYear="0";
	private String PostalCode="";
	private String StateOrProvince="";
	private String Street1="";
	private String CountryName="";
	private String CityName="";
	private String PayerFirstName="";
	private String PayerLastName="";
	private String PayerEmail="";
	private String amt="";
	private String AuthorizationID="";
	private String AuthorizationAmt="";
	private String expirationDate="";
	private String orderid="";
	private boolean vaultflag=false;
	
private  BraintreeGateway intialize(HashMap<String,String> paydet)
	{
	System.out.println("Intializing..");
	Environment environment=Environment.SANDBOX;
	String mid="";
	String mpubkey="";
    String mprikey="";
    String env="";
    if(paydet!=null)
    {
    	try{
    		mid=(String) paydet.get("merchant_id");
    		mpubkey=(String) paydet.get("public_key");
    		mprikey=(String) paydet.get("private_key");
    		env=(String) paydet.get("env");
    		if(!"SANDBOX".equals(env))
    		environment=Environment.PRODUCTION;
    		
    	return new BraintreeGateway(environment,mid,mpubkey,mprikey);
    	}catch(Exception e)
        { System.out.println("Exception in intialize:"+e.getMessage());}
    }
    System.out.println("In sufficent values to create gateway");
    	return null;
}
public void setPaymentDetails(HashMap<String,String> paydet)
{
	if(paydet!=null){
						
		                     CreditCardNumber=paydet.get("CreditCardNumber");if(CreditCardNumber==null){CreditCardNumber="";}
							 CreditCardType=paydet.get("CreditCardType");if(CreditCardType==null){CreditCardType="";}
							 CVV2=paydet.get("CVV2");if(CVV2==null){CVV2="";}
							 ExpMonth=paydet.get("ExpMonth");if(ExpMonth==null){ExpMonth="0";}
							 ExpYear=paydet.get("ExpYear");if(ExpYear==null){ExpYear="0";}
							 PostalCode=paydet.get("PostalCode");if(PostalCode==null){PostalCode="";}
							 StateOrProvince=paydet.get("StateOrProvince");if(StateOrProvince==null){StateOrProvince="";}
							 Street1=paydet.get("Street1");if(Street1==null){Street1="";}
							 CountryName=paydet.get("CountryName");if(CountryName==null){CountryName="";}
							 CityName=paydet.get("CityName");if(CityName==null){CityName="";}
							 PayerFirstName=paydet.get("PayerFirstName");if(PayerFirstName==null){PayerFirstName="";}
							 PayerLastName=paydet.get("PayerLastName");if(CreditCardNumber==null){CreditCardNumber="";}
							 PayerEmail=paydet.get("PayerEmail");if(PayerEmail==null){PayerEmail="";}
							 amt=paydet.get("amt");if(amt==null){amt="0.00";}
							 orderid=paydet.get("orderid");if(orderid==null){orderid="";}
							 expirationDate=paydet.get("expirationDate");
						

                      }

	
}
       
public Result<Transaction> makeADirectPayment(HashMap<String,String> paydet) 
    {System.out.println(" Start processing DirectPayment");
			   	Result<Transaction> result=null;
			
			    BraintreeGateway gateway =  intialize(paydet);
			    setPaymentDetails(paydet);
			     TransactionRequest request = new TransactionRequest().
			     amount(new BigDecimal(amt)).
			             orderId(orderid).
			      creditCard().
			         number(CreditCardNumber).
			         expirationDate(expirationDate).
			         cvv(CVV2).
			         done().
			     customer().
			         firstName(PayerFirstName).
			         lastName(PayerLastName).
			         email(PayerEmail).
			         done().
			     billingAddress().
			         firstName(PayerFirstName).
			         lastName(PayerLastName).
			         streetAddress(Street1).
			         locality(CityName).
			         postalCode(PostalCode).
			         countryName(CountryName).
			         done().
			         options().
			         submitForSettlement(true).
			         done();

			     try{
			    	 if(gateway!=null)
			         result = gateway.transaction().sale(request);
			    	 else
			    	System.out.println("unable to create gateway for this directpayment");
			    		 
			        }catch(Exception e)
			        { System.out.println("Exception in makeADirectPayment:"+e.getMessage());}
			        
			return result;
			        
} 
 
public Result<Transaction> makeAAuthorizePayment(HashMap<String,String> paydet) 
   {System.out.println(" Start processing AuthorizePayment");
		      Result<Transaction> result=null;
		   if(!"false".equals(paydet.get("vaultflag"))){vaultflag=true;}
		   System.out.println(" vaultflag at authroize:"+vaultflag);
		    BraintreeGateway gateway =  intialize(paydet);
		    setPaymentDetails(paydet);
		    TransactionRequest request = new TransactionRequest().
		     amount(new BigDecimal(amt)).
		             orderId(orderid).
		      creditCard().
		         number(CreditCardNumber).
		         expirationDate(expirationDate).
		         cvv(CVV2).
		         done().
		     customer().
		         firstName(PayerFirstName).
		         lastName(PayerLastName).
		         email(PayerEmail).
		         done().
		     billingAddress().
		         firstName(PayerFirstName).
		         lastName(PayerLastName).
		         streetAddress(Street1).
		         locality(CityName).
		         postalCode(PostalCode).
		         countryName(CountryName).		    
		         done().options().storeInVaultOnSuccess(vaultflag).done();
		               
		     try{
		    	 if(gateway!=null)
		    	 result = gateway.transaction().sale(request);	
		    	 else
				 System.out.println("unable to create gateway for this Authorization");
		        }catch(Exception e)
		     {System.out.println("Exception in makeAAuthorizePayment:"+e.getMessage());}
		
		        return result;
}

public Result<Transaction> makeASettlementPayment(HashMap<String,String> paydet) 
     {System.out.println(" Start processing SettlementPayment");
		    Result<Transaction> result=null;
		    BraintreeGateway gateway =  intialize(paydet);		     
		     try{
		    	 String tid=paydet.get("tid");
		    	 amt=paydet.get("amt");if(amt==null){amt="0.00";}
		    	 if(gateway!=null)
	                 if(!"".equals(amt))		    		
		    		   result = gateway.transaction().submitForSettlement(tid,new BigDecimal(amt));
	                else
			           result = gateway.transaction().submitForSettlement(tid);
		    	 else
				System.out.println("unable to create gateway for this SettlementPayment");
		        }catch(Exception e)
		     {System.out.println("Exception in makeASettlementPayment:"+e.getMessage());}		return result;
}

public Result<Transaction> makeAVoidPayment(HashMap<String,String> paydet) 
         {System.out.println(" Start processing VoidPayment");
		    Result<Transaction> result=null;
		    BraintreeGateway gateway =  intialize(paydet);	
		     
		     try{	
		    	 String tid=paydet.get("tid");
		    	 if(gateway!=null)
		    	 result = gateway.transaction().voidTransaction(tid);
		    	 else
				 System.out.println("unable to create gateway for this SettlementPayment");
		    	 
		     }catch(Exception e)
		         {System.out.println("Exception in makeAVoidPayment:"+e.getMessage());}
		return result;
}

public Result<Transaction> makeARefundPayment(HashMap<String,String> paydet) 
     {
	System.out.println(" Start processing RefundPayment");
		    Result<Transaction> result=null;
		    BraintreeGateway gateway =  intialize(paydet);			     
		     try{ 
		    	 String tid=paydet.get("tid");
		    	 String amt=paydet.get("amt");if(amt==null){amt="0.00";}
		    	 if(gateway!=null)
	                 if(!"".equals(amt))		    		
	                	result = gateway.transaction().refund(tid,new BigDecimal(amt));
	                else
	                	result = gateway.transaction().refund(tid);
		    	 
		    	 else
				System.out.println("unable to create gateway for this RefundPayment");
		    	}catch(Exception e)
		     {System.out.println("Exception in makeARefundPayment:"+e.getMessage()); }
		return result;
}
public Transaction getReport(HashMap<String,String> paydet) 
     {
	System.out.println(" Start processing Reports");
		    Transaction transaction=null;
		    BraintreeGateway gateway =  intialize(paydet);
		    String tid=paydet.get("tid");
		     
		     try{
		    	 if(gateway!=null) 
		    	 transaction = gateway.transaction().find(tid);
		    	 else
			    System.out.println("unable to create gateway for this Reports");
		        }catch(Exception e)
		     {System.out.println("Exception in getReport:"+e.getMessage());}
		      
		return transaction;
}
public Result<Transaction> valutTransaciton(HashMap<String,String> paydet)
{
	 
		 System.out.println("Start Processing VaultTransaction");
		BraintreeGateway gateway =  intialize(paydet);
		TransactionRequest request=null;
		Result<Transaction> result=null;
        String cid=paydet.get("coustmerid");
        String tokenid=paydet.get("tokenid");
        String amt=paydet.get("amt");if(amt==null){amt="0.00";}
        try{
       
        if(gateway!=null)
        {
        	 if(!"".equals(cid)){
            	 request = new TransactionRequest().
                amount(new BigDecimal(amt)).
                customerId(cid).options().submitForSettlement(true).done();
            	 result = gateway.transaction().sale(request);

            
            }
            else{
            	
            	request = new TransactionRequest().
    	         paymentMethodToken(tokenid).
    	     amount(new BigDecimal(amt)).
    	           creditCard().
    		        done().options().submitForSettlement(true).done();
            	result = gateway.transaction().sale(request);
            }
        }
        else
		System.out.println("unable to create gateway for this ValutTransacitonPayment");
   	}catch(Exception e)
    {System.out.println("Exception in valutTransaciton:"+e.getMessage()); }
   
	return result;
}
public Result<Customer> validatecreditcard(HashMap<String,String> paydet)
{
	
	System.out.println("Start Processing validation");
	BraintreeGateway gateway = intialize(paydet);
	try{
		 setPaymentDetails(paydet);
		
         System.out.println(PayerFirstName+"  "+PayerLastName+"  "+CreditCardNumber+"  "+CVV2);
		 CustomerRequest request = new CustomerRequest().
		 firstName(PayerFirstName).
         lastName(PayerLastName).
	     creditCard().
	     number(CreditCardNumber).
         expirationDate(expirationDate).
         cvv(CVV2). billingAddress().
         firstName(PayerFirstName).
         lastName(PayerLastName).
         streetAddress(Street1).
         locality(CityName).
         postalCode(PostalCode).
         countryName(CountryName).
         done().
         options().
         verifyCard(true).
         done().
         done();
	Result<Customer> result = gateway.customer().create(request);

	return result;
	}catch(Exception e){System.out.println("error in card validation:"+e.getMessage());
	return null;
	}
	}
}