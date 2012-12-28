// ===================================================================
// Author: Matt Kruse <matt@mattkruse.com>
// WWW: http://www.mattkruse.com/
// ------------------------------------------------------------------
// These functions use the same 'format' strings as the
// java.text.SimpleDateFormat class, with minor exceptions.
// The format string consists of the following abbreviations:
//
// Field        | Full Form          | Short Form
// -------------+--------------------+-----------------------
// Year         | yyyy (4 digits)    | yy (2 digits), y (2 or 4 digits)
// Month        | MMM (name or abbr.)| MM (2 digits), M (1 or 2 digits)
//              | NNN (abbr.)        |
// Day of Month | dd (2 digits)      | d (1 or 2 digits)
// Day of Week  | EE (name)          | E (abbr)
// Hour (1-12)  | hh (2 digits)      | h (1 or 2 digits)
// Hour (0-23)  | HH (2 digits)      | H (1 or 2 digits)
// Hour (0-11)  | KK (2 digits)      | K (1 or 2 digits)
// Hour (1-24)  | kk (2 digits)      | k (1 or 2 digits)
// Minute       | mm (2 digits)      | m (1 or 2 digits)
// Second       | ss (2 digits)      | s (1 or 2 digits)
// AM/PM        | a                  |
// ------------------------------------------------------------------

var MONTH_NAMES=new Array('Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro','Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez');
var DAY_NAMES=new Array('Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado','Dom','Seg','Ter','Qua','Qui','Sex','Sab');
function LZ(x) {
	return (x<0||x>9?"":"0")+x;
}

// ------------------------------------------------------------------
// formatDate (date_object, format)
// Returns a date in the output format specified.
// The format string uses the same abbreviations as in getDateFromFormat()
// ------------------------------------------------------------------
function formatDate(date,format) {
	format=format+"";

	var yyyy,yy,MMM,MM,dd,hh,h,mm,ss,ampm,HH,H,KK,K,kk,k;

	var year = date.getYear()+"";
	var month = date.getMonth()+1;
	var dayOfMonth = date.getDate();
	var dayOfWeek = date.getDay();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();

	if (year.length < 4) {
		year=""+(year-0+1900);
	}


	var value=new Object();

	value = setPossibleFormatsValues(year, month, dayOfMonth, dayOfWeek, hour, minute, second);


	var result="";

	result = getApplyFormat(value,format);

	return result;
}

function getApplyFormat(value, format){

	var formatCharacterIndex=0;
	var formatCharacter="";
	var token="";

	var result = "";

	while (formatCharacterIndex < format.length) {
		formatCharacter=format.charAt(formatCharacterIndex);
		token="";
		while ((format.charAt(formatCharacterIndex)==formatCharacter) &&
				(formatCharacterIndex < format.length)) {
			token += format.charAt(formatCharacterIndex++);
		}
		if (value[token] != null) {
			result=result + value[token];
		} else {
			result=result + token;
		}
	}
	return result;
}

function setPossibleFormatsValues(year, month, dayOfMonth, dayOfWeek, hour, minute, second){
	var value=new Object();

	value["y"]=""+year;
	value["yyyy"]=year;
	value["yy"]=year.substring(2,4);

	value["M"]=month;
	value["MM"]=LZ(month);
	value["MMM"]=MONTH_NAMES[month-1];
	value["NNN"]=MONTH_NAMES[month+11];

	value["d"]=dayOfMonth;
	value["dd"]=LZ(dayOfMonth);

	value["E"]=DAY_NAMES[dayOfWeek+7];
	value["EE"]=DAY_NAMES[dayOfWeek];

	value["H"]=hour;
	value["HH"]=LZ(hour);

	if (hour==0){
		value["h"]=12;
	} else if (hour>12){
		value["h"]=hour-12;
	} else {
		value["h"]=hour;
	}

	value["hh"]=LZ(value["h"]);

	if (hour>11){
		value["K"]=hour-12;
	} else {
		value["K"]=hour;
	}

	value["k"]=hour+1;
	value["KK"]=LZ(value["K"]);
	value["kk"]=LZ(value["k"]);
	if (hour > 11) {
		value["a"]="PM";
	} else {
		value["a"]="AM";
	}


	value["m"]=minute;
	value["mm"]=LZ(minute);

	value["s"]=second;
	value["ss"]=LZ(second);

	return value;

}