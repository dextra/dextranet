timeAgo = {

	data : null,

	serverDate : function (){
		$.ajax( {
			type : "GET",
			loading : false,
			url : "/s/timeMachine/",
			contentType : dextranet.application_json,
			async : false,
			complete : function(timeMachine) {
				data_servidor = timeMachine.responseText.replace(/"/g,'');
				d = data_servidor.split(" ");
				dia = d[0].split("/");
				if(!d[1]){
					d[1] = "00:00";
				}
				hora = d[1].split(":");

				//Date(yyyy, mm, dd, hh, mm, ss)
				timeAgo.data = new Date(dia[2],dia[1] - 1,dia[0],hora[0],hora[1],00);
			},
   			error: function(jqXHR, textStatus, errorThrown) {
   				dextranet.processaErroNaRequisicao(jqXHR);
   			}
		});
	},

	calcular : function (data){
		if(!data)
			return $.i18n.messages.data_invalida;

		d = data.split(" ");
		dia = d[0].split("/");
		if(!d[1]){
			d[1] = "00:00";
		}
		hora = d[1].split(":");

		//Date(yyyy, mm, dd, hh, mm, ss)
		var date = new Date(dia[2],dia[1] - 1,dia[0],hora[0],hora[1],00);


		diff = ((timeAgo.data.getTime() - date.getTime()) / 1000),
		day_diff = Math.floor(diff / 86400);

		if ( isNaN(day_diff) || day_diff < 0 )
			return;
		return  day_diff == 0 && (
				diff < 60 && $.i18n.messages.segundos_atras ||
				diff < 120 && $.i18n.messages.minuto_atras ||
				diff < 3600 && Math.floor( diff / 60 ) + $.i18n.messages.minutos_atras ||
				diff < 7200 && $.i18n.messages.hora_atras ||
				diff < 86400 && Math.floor( diff / 3600 ) + $.i18n.messages.horas_atras) ||
				day_diff == 1 && $.i18n.messages.ontem ||
				day_diff < 7 && day_diff + $.i18n.messages.dias_atras ||
				day_diff < 31 && Math.ceil( day_diff / 7 ) + $.i18n.messages.semanas_atras ||
				day_diff > 31 && date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear();

	}
}