var dextranet = {

		processaErroNaRequisicao : function(jqXHR) {
			messageError = '(' + jqXHR.status + ' ' + jqXHR.statusText + ') ' + jqXHR.responseText;
			$('.message').message(messageError, 'error', true);
		}

};