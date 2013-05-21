timeAgo = {

	calcular : function (data){
		if(!data)
			return "Data inválida";

		console.info("Data: " + data);
		d = data.split(" ");
		console.info("d: " + d);
		dia = d[0].split("/");
		console.info("dia: " + dia);
		hora = d[1].split(":");
		console.info("hora: " + hora);

		//Date(yyyy, mm, dd, hh, mm, ss)
		var date = new Date(dia[2],dia[1] - 1,dia[0],hora[0],hora[1],00),

	    diff = (((new Date()).getTime() - date.getTime()) / 1000),
	    day_diff = Math.floor(diff / 86400);

		if ( isNaN(day_diff) || day_diff < 0 )
			return;

		return day_diff == 0 && (
		        diff < 60 && "segundos atrás" ||
		        diff < 120 && "1 minuto atrás" ||
		        diff < 3600 && Math.floor( diff / 60 ) + " minutos atrás" ||
		        diff < 7200 && "1 hora atrás" ||
		        diff < 86400 && Math.floor( diff / 3600 ) + " horas atrás") ||
		    day_diff == 1 && "Ontem" ||
		    day_diff < 7 && day_diff + " dias atrás" ||
		    day_diff < 31 && Math.ceil( day_diff / 7 ) + " semanas atrás" ||
		    day_diff > 31 && date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear();
		}
}