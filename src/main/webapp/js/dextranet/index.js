var dextranet = {

		application_json : "application/json;charset=UTF-8",

		gravatarUrl : "http://www.gravatar.com/avatar/",

		processaErroNaRequisicao : function(jqXHR) {
			messageError = '(' + jqXHR.status + ' ' + jqXHR.statusText + ') ' + jqXHR.responseText;
			$('.message').message(messageError, 'error', true);
		}

};