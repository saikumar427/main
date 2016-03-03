	var COUNTRIES=new Array("USA","Albania","Algeria","Andorra","Angola","Anguilla","Antigua and Barbuda","Armenia","Aruba",
"Argentina","Australia","Austria","Azerbaijan Republic","Bahamas","Bahrain","Barbados","Belgium",
"Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia and Herzegovina","Botswana","Brazil","British Virgin Islands",
"Brunei","Bulgaria","Burkina Faso","Burundi","Canada","Cambodia","Cape Verde","Cayman Islands","Chad",
"China","Chile","Colombia","Comoros","Costa Rica","Cook Islands","Croatia","Cyprus","Czech Republic",
"Denmark","Democratic Republic of the Congo","Djibouti","Dominica","Dominican Republic","Ecuador",
"El Salvador","Eritrea","Estonia","Ethiopia","Falkland Islands","Faroe Islands","Federated States of Micronesia",
"Fiji","Finland","France","French Guiana","French Polynesia","Gabon Republic","Gambia","Germany",
"Gibraltar","Greenland","Greece","Grenada","Guadeloupe","Guatemala","Guinea","Guinea Bissau","Guyana",
"Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Ireland","Israel","Italy","Japan",
"Jamaica","Jordan","Kazakhstan","Kenya","Kiribati","Kuwait","Kyrgyzstan","Laos","Latvia","Lesotho",
"Liechtenstein","Lithuania","Luxembourg","Madagascar","Malaysia","Malawi","Maldives","Mali","Malta",
"Marshall Islands","Martinique","Mauritania","Mauritius","Mayotte","Mexico","Mongolia","Montserrat",
"Morocco","Mozambique","Namibia","Nauru","Nepal","Netherlands","Netherlands Antilles","New Zealand",
"New Caledonia","Nicaragua","Niger","Niue","Norfolk Island","Norway","Oman","Palau","Panama","Papua New Guinea",
"Peru","Philippines","Pitcairn Islands","Poland","Portugal","Qatar","Republic of the Congo","Reunion",
"Romania","Russia","Rwanda","Saint Vincent and the Grenadines","San Marino","Samoa","São Tomé and Príncipe",
"Saudi Arabia","Senegal","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands",
"Somalia","South Africa","South Korea","Spain","Sri Lanka","St. Helena","St. Kitts and Nevis",
"St. Lucia","St. Pierre and Miquelon","Suriname","Svalbard and Jan Mayen Islands","Swaziland","Sweden",
"Switzerland","Taiwan","Tajikistan","Tanzania","Thailand","Togo","Tonga","Trinidad and Tobago","Tunisia",
"Turkey","Turkmenistan","Turks and Caicos Islands","Tuvalu","Uganda","Ukraine","United Arab Emirates",
"United Kingdom","Uruguay","Vanuatu","Vatican City State","Venezuela","Vietnam","Wallis and Futuna Islands",
"Yemen","Zambia");
	
	function BrainCallCountries(){
		var SelObj=document.getElementById("country");
		var defval=document.getElementById("bhcountry");
		var oOption=document.createElement("OPTION");

		var innTxt=document.createTextNode(" -- Select Country -- ");

		oOption.setAttribute('value','');
		oOption.appendChild(innTxt);
		SelObj.appendChild(oOption);
		for(var i=0;i<COUNTRIES.length;i++){
			var txt = document.createTextNode(COUNTRIES[i]);
			var idval=document.createTextNode(COUNTRIES[i]);
			oOption=document.createElement("OPTION");
			if(defval.value==COUNTRIES[i]){
				oOption.setAttribute('selected','true');
			}
			oOption.setAttribute('value',COUNTRIES[i]);
			oOption.setAttribute('id',COUNTRIES[i]);
			oOption.appendChild(txt);
			SelObj.appendChild(oOption);
		}	
	}



