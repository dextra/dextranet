var dextranet = {

		gravatarUrl : "http://www.gravatar.com/avatar/",

		processaErroNaRequisicao : function(jqXHR) {
			messageError = '(' + jqXHR.status + ' ' + jqXHR.statusText + ') ' + jqXHR.responseText;
			$('.message').message(messageError, 'error', true);
		},

		carregaMenus : function() {
			$.holy("../template/estatico/carrega_menu_principal.xml", {});
			$.holy("../template/estatico/carrega_menu_lateral.xml", {});
			$.holy("../template/estatico/carrega_miolo.xml", {});
		}

};