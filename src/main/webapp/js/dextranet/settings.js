dextranet.settings = {

		inicializa : function() {
			dextranet.settings.configuraLoading();
			dextranet.settings.loadMessages();
			dextranet.settings.carregaPaginacao();
		},

		configuraLoading : function() {
			$.loading( {
				text : 'Carregando...',
				overlay : '#23557E',
				opacity : '60'
			});

	        jQuery.ajaxSetup( {
	            loading : true
	        });

		},

		loadMessages : function() {
			if (!$.i18n.loaded) {
				setTimeout("$.loadMessages()", 100);
			}
		},

		// FIXME: a paginacao deveria estar relacionada ao post e nao ao settings
		carregaPaginacao : function() {
			numeroDaPagina = new dextranet.paginacao.pagina();
			consulta = new dextranet.paginacao.query();
			numeroDaPagina.setPaginaInicial();
			dextranet.paginacao.paginacaoDosPosts();
		}
};