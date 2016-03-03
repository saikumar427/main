package com.eventbee.util;

import java.util.*;

public class Address{

	private String Street="";
	private String City="";
	private String State="";
	private String Zip="";
	private String Country="";

	public Address(String Street,String City,String State,String Zip,String Country){
		this.Street=Street;
		this.City=City;
		this.State=State;
		this.Zip=Zip;
		this.Country=Country;
	}
	public void setStreet(String Street){
			 this.Street=Street;
		}

		public String getStreet(){
			return Street;
	}

	public void setCity(String City){
				 this.City=City;
			}

			public String getCity(){
				return City;
		}

public void setState(String State){
			 this.State=State;
		}

		public String getState(){
			return State;
	}

public void setZip(String Zip){
			 this.Zip=Zip;
		}

		public String getZip(){
			return Zip;
	}

public void setCountry(String Country){
			 this.Country=Country;
		}

		public String getCountry(){
			return Country;
	}




	}
