<script>
var datatimezones={"Europe/London":"Europe/London","Pacific/Honolulu":"SystemV/HST10 Hawaii","America/Anchorage":"SystemV/YST9 Alaska","America/Los_Angeles":"SystemV/PST8 Pacific Time",
		"America/Phoenix":"SystemV/MST7 Arizona","America/La_Paz":"SystemV/MST7 La Paz","America/Managua":"SystemV/CST6 Central America","America/New_York":"SystemV/CST6 Central Time",
		"America/Monterrey":"SystemV/CST6 Mexico City, Monterrey","America/Toronto":"SystemV/EST5 Eastern Time","Pacific/Kwajalein":"Etc/GMT+12","Pacific/Apia":"Pacific/Apia",
		"America/Regina":"America/Regina","America/Bogota":"America/Bogota","America/Indiana/Indianapolis":"Etc/GMT+5","America/Halifax":"America/Halifax","America/Caracas":"America/Caracas",
		"America/Santiago":"America/Santiago","America/St_Johns":"America/St_Johns","America/Sao_Paulo":"America/Sao_Paulo","America/Argentina/Buenos_Aires":"America/Argentina/Buenos_Aires",
		"America/Godthab":"America/Godthab","Atlantic/South_Georgia":"Atlantic/South_Georgia","Atlantic/Azores":"Atlantic/Azores","Atlantic/Cape_Verde":"Atlantic/Cape_Verde",
		"Africa/Casablanca":"Africa/Casablanca","Europe/Berlin":"Europe/Berlin","Europe/Budapest":"Europe/Budapest","Europe/Paris":"Europe/Paris","Europe/Warsaw":"Europe/Warsaw",
		"Africa/Lagos":"Africa/Lagos","Europe/Istanbul":"Europe/Istanbul","Africa/Cairo":"Africa/Cairo","Africa/Johannesburg":"Africa/Johannesburg","Europe/Kiev":"Europe/Kiev",
		"Asia/Jerusalem":"Asia/Jerusalem","Asia/Baghdad":"Asia/Baghdad","Asia/Riyadh":"Asia/Riyadh","Europe/Moscow":"Europe/Moscow","Africa/Nairobi":"Africa/Nairobi",
		"Asia/Tehran":"Asia/Tehran","Asia/Dubai":"Asia/Dubai","Asia/Baku":"Asia/Baku","Asia/Kabul":"Asia/Kabul","Asia/Yekaterinburg":"Asia/Yekaterinburg","Asia/Karachi":"Asia/Karachi",
		"Asia/Calcutta":"Asia/Calcutta","Asia/Katmandu":"Asia/Katmandu","Asia/Novosibirsk":"Asia/Novosibirsk","Asia/Dhaka":"Asia/Dhaka","Asia/Colombo":"Asia/Colombo",
		"Asia/Rangoon":"Asia/Rangoon","Asia/Bangkok":"Asia/Bangkok","Asia/Krasnoyarsk":"Asia/Krasnoyarsk","Asia/Shanghai":"Asia/Shanghai","Asia/Singapore":"Asia/Singapore",
		"Australia/Perth":"Australia/Perth","Asia/Taipei":"Asia/Taipei","Asia/Tokyo":"Asia/Tokyo","Asia/Seoul":"Asia/Seoul","Asia/Yakutsk":"Asia/Yakutsk","Australia/Adelaide":"Australia/Adelaide",
		"Australia/Darwin":"Australia/Darwin","Australia/Brisbane":"Australia/Brisbane","Australia/Sydney":"Australia/Sydney","Pacific/Port_Moresby":"Pacific/Port_Moresby","Australia/Hobart":"Australia/Hobart",
		"Asia/Vladivostok":"Asia/Vladivostok","Pacific/Guadalcanal":"Pacific/Guadalcanal","Pacific/Auckland":"Pacific/Auckland","Pacific/Fiji":"Pacific/Fiji","Pacific/Tongatapu":"Pacific/Tongatapu",
		"America/Iqaluit":"SystemV/EST5 Eastern Time","America/Jamaica":"SystemV/EST5 Eastern Time","America/Juneau":"SystemV/YST9 Alaska","America/Kentucky/Louisville":"America/Bogota",
		"America/Kentucky/Monticello":"America/Bogota","America/La_Paz":"America/Halifax","America/Lima":"America/Bogota",
		"America/Los_Angeles":"SystemV/PST8 Pacific Time","America/Maceio":"America/Sao_Paulo","America/Managua":"America/Regina",
		"America/Manaus":"America/Halifax","America/Marigot":"America/Halifax","America/Martinique":"America/Halifax","America/Matamoros":"America/Regina",
		"America/Mazatlan":"SystemV/MST7 Arizona","America/Menominee":"SystemV/CST6 Central Time","America/Merida":"SystemV/CST6 Central Time",
		"America/Metlakatla":"SystemV/PST8 Pacific Time","Africa/Abidjan":"Africa/Casablanca","Africa/Accra":"Africa/Casablanca","Africa/Addis_Ababa":"Africa/Nairobi",
		"Africa/Algiers":"Africa/Lagos","Africa/Asmara":"Africa/Nairobi","Africa/Bamako":"Africa/Casablanca","Africa/Bangui":"Africa/Lagos",
		"Africa/Banjul":"Africa/Casablanca","Africa/Bissau":"Africa/Casablanca","Africa/Blantyre":"Africa/Cairo","Africa/Brazzaville":"Africa/Lagos",
		"Africa/Bujumbura":"Africa/Johannesburg","Africa/Ceuta":"Africa/Lagos","Africa/Conakry":"Africa/Casablanca","Africa/Dakar":"Africa/Casablanca",
		"Africa/Dar_es_Salaam":"Africa/Nairobi","Africa/Djibouti":"Africa/Nairobi","Africa/Douala":"Africa/Lagos","Africa/El_Aaiun":"Africa/Casablanca",
		"Africa/Freetown":"Africa/Casablanca","Africa/Gaborone":"Africa/Cairo","Africa/Harare":"Africa/Cairo","Africa/Kampala":"Africa/Nairobi",
		"Africa/Khartoum":"Africa/Nairobi","Africa/Kigali":"Africa/Cairo","Africa/Kinshasa":"Africa/Lagos","Africa/Libreville":"Africa/Lagos",
		"Africa/Lome":"Africa/Casablanca","Africa/Luanda":"Africa/Lagos","Africa/Lubumbashi":"Africa/Johannesburg","Africa/Lusaka":"Africa/Johannesburg",
		"Africa/Malabo":"Africa/Lagos","Africa/Maputo":"Africa/Cairo","Africa/Maseru":"Africa/Cairo","Africa/Mbabane":"Africa/Cairo","Africa/Mogadishu":"Africa/Nairobi",
		"Africa/Monrovia":"Africa/Casablanca","Africa/Ndjamena":"Africa/Lagos","Africa/Niamey":"Africa/Lagos","Africa/Nouakchott":"Africa/Casablanca",
		"Africa/Ouagadougou":"Africa/Casablanca","Africa/Porto-Novo":"Africa/Lagos","Africa/Sao_Tome":"Africa/Casablanca","Africa/Tripoli":"Africa/Cairo",
		"Africa/Tunis":"Africa/Lagos","Africa/Windhoek":"Africa/Lagos",
		"America/Adak":"SystemV/HST10Hawaii",
		"America/Anchorage":"SystemV/YST9YDT Alaska (DST)",
				"America/Anguilla":"America/Santiago",
				"America/Antigua":"America/Santiago",
				"America/Araguaina":"America/Buenos_Aires",
				"America/Argentina/Buenos_Aires":"America/Buenos_Aires",
				"America/Argentina/Catamarca":"America/Buenos_Aires",
				"America/Argentina/Cordoba":"America/Buenos_Aires",
				"America/Argentina/Jujuy":"America/Buenos_Aires",
				"America/Argentina/La_Rioja":"America/Buenos_Aires",
				"America/Argentina/Mendoza":"America/Buenos_Aires",
				"America/Argentina/Rio Gallegos":"America/Buenos_Aires",
				"America/Argentina/Salta":"America/Buenos_Aires",
				"America/Argentina/San_Juan":"America/Buenos_Aires",
				"America/Argentina/San_Luis":"America/Buenos_Aires",
				"America/Argentina/Tucuman":"America/Buenos_Aires",
				"America/Argentina/Ushuaia":"America/Buenos_Aires",
				"America/Aruba":"America/Santiago",
				"America/Asuncion":"America/Santiago",
				"America/Atikokan":"America/Bogota",
				"America/Bahia_Banderas":"SystemV/CST6 Mexico City, Monterrey",
				"America/Bahia":"America/Sao_Paulo",
					"America/Barbados":"America/Santiago",
					"America/Belem":"America/Buenos_Aires",
					"America/Belize":"America/Regina",
					"America/Blanc-Sablon":"America/Santiago",
					"America/Boa_Vista":"America/Santiago",
					"America/Boise":"SystemV/MST7 Mountain Time",
					"America/Cambridge_Bay":"SystemV/MST7 Mountain Time",
					"America/Campo_Grande":"America/Santiago",
					"America/Cancun":"SystemV/CST6 Central Time",
					"America/Cayenne":"America/Sao_Paulo",
					"America/Cayman":"America/Bogota",
					"America/Chicago":"SystemV/CST6 Central Time",
					"America/Chihuahua":"SystemV/MST7 Mountain Time",
					"America/Costa_Rica":"SystemV/CST6 Central America",
					"America/Cuiaba":"America/Santiago",
					"America/Curacao":"America/Santiago",
					"America/Danmarkshavn":"Atlantic/Azores",
					"America/Dawson_Creek":"SystemV/MST7 Arizona",
					"America/Dawson":"SystemV/PST8 Pacific Time",
					"America/Denver":"SystemV/MST7 Mountain Time",
					"America/Detroit":"SystemV/EST5EDT Eastern Time (DST)",
					"America/Dominica":"America/Santiago",
					"America/Edmonton":"SystemV/MST7MDT La Paz (DST)",
					"America/Eirunepe":"America/Bogota",
					"America/El_Salvador":"America/Regina",
					"America/Fortaleza":"America/Sao_Paulo",
					"America/Glace_Bay":"America/Halifax",
					"America/Goose_Bay":"America/Halifax",
					"America/Grand_Turk":"SystemV/EST5EDT Eastern Time (DST)",
					"America/Grenada":"America/Santiago",
					"America/Guadeloupe":"America/Santiago",
					"America/Guatemala":"America/Regina",
					"America/Guayaquil":"America/Bogota",
					"America/Guyana":"America/Santiago",
					"America/Havana":"SystemV/EST5EDT Eastern Time (DST)",
					"America/Hermosillo":"SystemV/MST7 Mountain Time",
					"America/Indiana/Indianapolis":"SystemV/EST5EDT Eastern Time (DST)",
					"America/Indiana/Knox":"SystemV/CST6 Mexico City, Monterrey",
					"America/Indiana/Marengo":"SystemV/EST5EDT Eastern Time (DST)",
					"America/Indiana/Petersburg":"SystemV/EST5EDT Eastern Time (DST)",
					"America/Indiana/Tell_City":"SystemV/CST6CDT Central Time (DST)",
					"America/Indiana/Vevay":"SystemV/EST5EDT Eastern Time (DST)",
					"America/Indiana/Vincennes":"SystemV/EST5EDT Eastern Time (DST)",
					"America/Indiana/Winamac":"SystemV/EST5EDT Eastern Time (DST)",
					"America/Inuvik":"SystemV/MST7MDT Mountain Time (DST)",
			"Asia/Bahrain": "Asia/Baghdad",
		    "Asia/Baku": "Baku",
		    "Asia/Beirut": "Europe/Istanbul",
		    "Asia/Bishkek": "Asia/Novosibirsk",
		    "Asia/Brunei": "Asia/Singapore",
		    "Asia/Choibalsan": "Asia/Shanghai",
		    "Asia/Chongqing": "Asia/Shanghai",
		    "Asia/Damascus": "Europe/Kiev",
		    "Asia/Dili": "Asia/Seoul",
		    "Asia/Dushanbe": "Asia/Yekaterinburg",
		    "Asia/Gaza": "Africa/Johannesburg",
		    "Asia/Harbin": "Asia/Taipei",
		    "Asia/Ho_Chi_Minh": "Asia/Bangkok",
		    "Asia/Hong_Kong": "Asia/Shanghai",
		    "Asia/Hovd": "Asia/Bangkok",
		    "Asia/Jakarta": "Asia/Bangkok",
		    "Asia/Jayapura": "Asia/Yakutsk",
		    "Asia/Kamchatka": "Pacific/Fiji",
		    "Asia/Kashgar": "Asia/Singapore",
		    "Asia/Kathmandu": "Asia/Katmandu",
		    "Asia/Kolkata": "Asia/Calcutta",
		    "Asia/Kuala_Lumpur": "Asia/Singapore",
		    "Asia/Kuching": "Asia/Irkutsk",
		    "Asia/Kuwait": "Asia/Riyadh",
		    "Asia/Macau": "Asia/Irkutsk",
		    "Asia/Magadan": "Pacific/Fiji",
		    "Asia/Makassar": "Asia/Irkutsk",
		    "Asia/Manila": "Asia/Singapore",
		    "Asia/Muscat": "Asia/Dubai",
		    "Asia/Nicosia": "Africa/Johannesburg",
		    "Asia/Novokuznetsk": "Asia/Krasnoyarsk",
		    "Asia/Omsk": "Asia/Krasnoyarsk",
		    "Asia/Oral": "Asia/Karachi",
		    "Asia/Phnom_Penh": "Asia/Bangkok",
		    "Asia/Pontianak": "Asia/Bangkok",
		    "Asia/Pyongyang": "Asia/Tokyo",
		    "Asia/Qatar": "Asia/Riyadh",
		    "Asia/Qyzylorda": "Asia/Novosibirsk",
		    "Asia/Sakhalin": "Pacific/Guadalcanal",
		    "Asia/Samarkand": "Asia/Karachi",
		    "Asia/Tashkent": "Asia/Karachi",
		    "Asia/Tbilisi": "Baku",
		    "Asia/Thimphu": "Asia/Dhaka",
		    "Asia/Ulaanbaatar": "Asia/Irkutsk",
		    "Asia/Urumqi": "Asia/Irkutsk",
		    "Asia/Vientiane": "Asia/Krasnoyarsk",
		    "Asia/Yerevan": "Baku",
		    "Atlantic/Bermuda": "America/Halifax",
		    "Atlantic/Canary": "Atlantic/Cape_Verde",
		    "Atlantic/Faroe": "Atlantic/Cape_Verde",
		    "Atlantic/Madeira": "Atlantic/Cape_Verde",
		    "Atlantic/Reykjavik": "Atlantic/Cape_Verde",
		    "Atlantic/St_Helena": "Atlantic/Cape_Verde",
		    "Atlantic/Stanley": "Atlantic/Cape_Verde",
		    "Australia/Broken_Hill": "Australia/Adelaide",
		    "Australia/Currie": "Pacific/Guadalcanal",
		    "Australia/Eucla": "Australia/Perth",
		    "Australia/Lindeman": "Australia/Sydney",
		    "Australia/Lord_Howe": "Australia/Hobart",
		    "Australia/Melbourne": "Australia/Sydney",
		    "Europe/Amsterdam": "Europe/Berlin",
		    "Europe/Andorra": "Europe/Berlin",
		    "Europe/Athens": "Europe/Istanbul",
		    "Europe/Belgrade": "Europe/Budapest",
		    "Europe/Bratislava": "Europe/Budapest",
		    "Europe/Brussels": "Europe/Paris",
		    "Europe/Bucharest": "Europe/Istanbul",
		    "Europe/Chisinau": "Europe/Kiev",
		    "Europe/Copenhagen": "Europe/Paris",
		    "Europe/Dublin": "Europe/London",
		    "Europe/Gibraltar": "Europe/Warsaw",
		    "Europe/Guernsey": "Europe/London",
		    "Europe/Helsinki": "Europe/Kiev",
		    "Europe/Isle_of_Man": "Europe/London",
		    "Europe/Jersey": "Europe/London",
		    "Europe/Kaliningrad": "Europe/Moscow",
		    "Europe/Lisbon": "Europe/London",
		    "Europe/Ljubljana": "Europe/Budapest",
		    "Europe/Luxembourg": "Europe/Budapest",
		    "Europe/Madrid": "Europe/Paris",
		    "Europe/Malta": "Europe/Paris",
		    "Europe/Mariehamn": "Europe/Kiev",
		    "Europe/Minsk": "Europe/Moscow",
		    "Europe/Monaco": "Europe/Paris",
		    "Europe/Oslo": "Europe/Paris",
		    "Europe/Podgorica": "Europe/Paris",
		    "Europe/Prague": "Europe/Budapest",
		    "Europe/Riga": "Europe/Kiev",
		    "Europe/Rome": "Europe/Berlin",
		    "Europe/Samara": "Asia/Dubai",
		    "Europe/San_Marino": "Europe/Berlin",
		    "Europe/Sarajevo": "Europe/Warsaw",
		    "Europe/Simferopol": "Asia/Dubai",
		    "Europe/Skopje": "Europe/Warsaw",
		    "Europe/Sofia": "Europe/Kiev",
		    "Europe/Stockholm": "Europe/Berlin",
		    "Europe/Tallinn": "Europe/Kiev",
		    "Europe/Tirane": "Europe/Berlin",
		    "Europe/Uzhgorod": "Europe/Istanbul",
		    "Europe/Vaduz": "Europe/Warsaw",
		    "Europe/Vatican": "Europe/Warsaw",
		    "Europe/Vienna": "Europe/Berlin",
		    "Europe/Vilnius": "Europe/Kiev",
		    "Europe/Volgograd": "Asia/Dubai",
		    "Europe/Zagreb": "Europe/Warsaw",
		    "Europe/Zaporozhye": "Europe/Istanbul",
		    "Europe/Zurich": "Europe/Paris",
		    "Indian/Antananarivo": "Asia/Baghdad",
		    "Indian/Chagos": "Asia/Dhaka",
		    "Indian/Christmas": "Asia/Bangkok",
		    "Indian/Cocos": "Asia/Rangoon",
		    "Indian/Comoro": "Asia/Riyadh",
		    "Indian/Kerguelen": "Asia/Karachi",
		    "Indian/Mahe": "Asia/Dubai",		   
		    "Indian/Maldives": "Asia/Karachi",
		    "Indian/Mauritius": "Asia/Dubai",
		    "Indian/Mayotte": "Asia/Riyadh",
		    "Indian/Reunion": "Asia/Dubai",
		    "Pacific/Chatham": "Pacific/Tongatapu",
		    "Pacific/Chuuk": "Pacific/Port_Moresby",
		    "Pacific/Easter": "SystemV/CST6 Central Time",
		    "Pacific/Efate": "Pacific/Guadalcanal",
		    "Pacific/Enderbury": "Pacific/Tongatapu",
		    "Pacific/Fakaofo": "Pacific/Tongatapu",
		    "Pacific/Funafuti": "Pacific/Fiji",
		    "Pacific/Galapagos": "SystemV/CST6 Central Time",
		    "Pacific/Gambier": "SystemV/YST9 Alaska",
		    "Pacific/Guam": "Pacific/Port_Moresby",
		    "Pacific/Honolulu": "SystemV/HST10 Hawaii",
		    "Pacific/Johnston": "SystemV/HST10 Hawaii",
		    "Pacific/Kiritimati": "Pacific/Tongatapu",
		    "Pacific/Kosrae": "Pacific/Guadalcanal",
		    "Pacific/Kwajalein": "Pacific/Auckland",
		    "Pacific/Majuro": "Pacific/Auckland",
		    "Pacific/Marquesas": "SystemV/YST9 Alaska",
		    "Pacific/Nauru": "Pacific/Auckland",
		    "Pacific/Midway": "SystemV/HST10 Hawaii",
		    "Pacific/Niue": "SystemV/HST10 Hawaii",
		    "Pacific/Norfolk": "Pacific/Guadalcanal",
		    "Pacific/Noumea": "Pacific/Guadalcanal",
		    "Pacific/Pago_Pago": "SystemV/HST10 Hawaii",
		    "Pacific/Palau": "Asia/Tokyo",
		    "Pacific/Pitcairn": "SystemV/PST8 Pacific Time",
		    "Pacific/Pohnpe": "Pacific/Guadalcanal",
		    "Pacific/Rarotonga": "SystemV/PST8 Pacific Time",
		    "Pacific/Saipan": "Pacific/Port_Moresby",
		    "Pacific/Tahiti": "SystemV/HST10 Hawaii",
		    "Pacific/Tarawa": "Pacific/Fiji",
		    "Pacific/Wake": "Pacific/Fiji",
		    "Pacific/Wallis": "Pacific/Fiji",
		    "America/Mexico_City":"SystemV/CST6CDT Central Time (DST)",
		    "America/Miquelon":"America/Godthab",
		    "America/Moncton":"America/Halifax",
		    "America/Monterrey":"SystemV/CST6 Mexico City, Monterrey",
		    "America/Montevideo":"America/Buenos_Aires",
		    "America/Montreal":"SystemV/EST5EDT Eastern Time (DST)",
		    "America/Montserrat":"America/Santiago",
		    "America/Nassau":"SystemV/EST5EDT Eastern Time (DST)",
		    "America/New_York":"SystemV/EST5EDT Eastern Time (DST)",
		    "America/Nipigon":"SystemV/EST5EDT Eastern Time (DST)",
		    "America/Nome":"SystemV/YST9YDT Alaska (DST)",
		    "America/Noronha":"Atlantic/South_Georgia",
		    "America/North_Dakota/Beulah":"SystemV/CST6CDT Central Time (DST)",
		    "America/North_Dakota/Center":"SystemV/CST6CDT Central Time (DST)",
		    "America/North_Dakota/New_Salem":"SystemV/CST6CDT Central Time (DST)",
		    "America/Ojinaga":"SystemV/MST7MDT Mountain Time (DST)",
		    "America/Panama":"SystemV/EST5 Eastern Time",
		    "America/Pangnirtung":"SystemV/EST5EDT Eastern Time (DST)",
		    "America/Paramaribo":"America/Buenos_Aires",
		    "America/Phoenix":"SystemV/MST7 Arizona",
		    "America/Port_of_Spain":"America/Santiago",
		    "America/Port-au-Prince":"SystemV/EST5EDT Eastern Time (DST)",
		    "America/Porto_Velho":"America/Santiago",
		    "America/Puerto_Rico":"America/Santiago",
		    "America/Rainy_River":"SystemV/CST6CDT Central Time (DST)",
		    "America/Rankin_Inlet":"SystemV/CST6CDT Central Time (DST)",
		    "America/Recife":"America/Sao_Paulo",
		    "America/Resolute":"SystemV/CST6CDT Central Time (DST)",
		    "America/Rio_Branco":"SystemV/EST5 Eastern Time",
		    "America/Santa_Isabel":"SystemV/PST8 Pacific Time",
		    "America/Santarem":"America/Sao_Paulo",
		    "America/Santo_Domingo":"America/Santiago",
		    "America/Scoresbysund":"Atlantic/Azores",
		    "America/Shiprock":"SystemV/MST7MDT Mountain Time (DST)",
		    "America/Sitka":"SystemV/YST9 Alaska",
		    "America/St_Barthelemy":"America/Santiago",
		    "America/St_Kitts":"America/Santiago",
		    "America/St_Lucia":"America/Santiago",
		    "America/St_Thomas":"America/Santiago",
		    "America/St_Vincent":"America/Santiago",
		    "America/Swift_Current":"SystemV/CST6 Central Time",
		    "America/Tegucigalpa":"America/Regina",
		    "America/Thule":"America/Halifax",
		    "America/Thunder_Bay":"SystemV/EST5EDT Eastern Time (DST)",
		    "America/Tijuana":"SystemV/PST8 Pacific Time",
		    "America/Toronto":"SystemV/EST5EDT Eastern Time (DST)",
		    "America/Tortola":"America/Santiago",
		    "America/Vancouver":"SystemV/PST8 Pacific Time",
		    "America/Whitehorse":"SystemV/PST8 Pacific Time",
		    "America/Winnipeg":"SystemV/CST6CDT Central Time (DST)",
		    "America/Yakutat":"SystemV/YST9 Alaska",
		    "America/Yellowknife":"SystemV/MST7MDT Mountain Time (DST)",
		    "Antarctica/Casey":"Pacific/Guadalcanal",
		    "Antarctica/Davis":"Asia/Yekaterinburg",
		    "Antarctica/DumontDUrville":"Australia/Hobart",
		    "Antarctica/Macquarie":"Pacific/Guadalcanal",
		    "Antarctica/Mawson":"Asia/Karachi",
		    "Antarctica/McMurdo":"Pacific/Auckland",
		    "Antarctica/Palmer":"America/Santiago",
		    "Antarctica/Rothera":"America/Sao_Paulo",
		    "Antarctica/South_Pole":"Pacific/Fiji",
		    "Antarctica/Syowa":"Asia/Baghdad",
		    "Antarctica/Vostok":"Asia/Dhaka",
		    "Arctic/Longyearbyen":"Europe/Warsaw",
		    "Asia/Aden":"Asia/Riyadh",
		    "Asia/Almaty":"Asia/Dhaka",
		    "Asia/Amman":"Asia/Jerusalem",
		    "Asia/Anadyr":"Pacific/Fiji",
		    "Asia/Aqtau":"Asia/Karachi",
		    "Asia/Aqtobe":"Asia/Karachi",
		    "Asia/Ashgabat":"Asia/Karachi",
		    "UTC": "Europe/London"};

var calGettimzone=function(lat,lng){
	//alert(lat);
    jQuery('#gettimezone').load('https://maps.googleapis.com/maps/api/timezone/json?location='+lat+','+lng+'&timestamp='+new Date().getTime()/1000,
            function(response, status, xhr){
        
       // alert(response);
            var res=JSON.parse(response);
   // alert("timejone::"+    datatimezones[res['timeZoneId']]);
   try{
    if(datatimezones[res['timeZoneId']]!=undefined && datatimezones[res['timeZoneId']]!='')
            jQuery('#timezone').val(datatimezones[res['timeZoneId']]);
    else {
    	if(res['timeZoneId']!=undefined)
    	jQuery('#timezone').val(res['timeZoneId']);
    }
    var timezone=$('#timezone').find('option:selected').text();
    // alert(timezone);
     if(timezone=='-- Select Time Zone --')
     	timezone='(GMT-08:00) Pacific Time';
    // alert(timezone);
     $('#timezoneval').html(timezone);
     $('#timezoneval').attr('data-timezone',$('#timezone').val());
    //$('#timezoneval').['data-timezone']=$('#timezone').val();
    }catch(err){}
          //  alert(status);alert( xhr)
          });
    
}

</script>
<ol id="gettimezone" style="display:none"></ol> 
