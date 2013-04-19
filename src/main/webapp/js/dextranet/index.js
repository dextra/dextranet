var dextranet = {

		application_json : "application/json;charset=UTF-8",

		processaErroNaRequisicao : function(jqXHR) {
			messageError = '(' + jqXHR.status + ' ' + jqXHR.statusText + ') ' + jqXHR.responseText;
			$('.message').message(messageError, 'error', true);
		}

};