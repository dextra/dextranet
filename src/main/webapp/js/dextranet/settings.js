dextranet.settings = {

		inicializa : function() {
			dextranet.settings.configuraLoading();
			dextranet.settings.carregaInternacionalizacao();
			dextranet.settings.inicializaMensagens();
			dextranet.settings.carregaPaginacao();
			dextranet.settings.configuraDatePicker();
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

		carregaInternacionalizacao : function() {
			if (!$.i18n.loaded) {
				setTimeout("$.carregaInternacionalizacao()", 100);
			}
		},

		inicializaMensagens : function() {
			$(".message").messageMonitor();
		},

		configuraDatePicker : function() {
			$.datepicker.setDefaults({
			    dateFormat: 'dd/mm/yy',
			    dayNames: ['Domingo','Segunda','Ter�a','Quarta','Quinta','Sexta','S�bado'],
			    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
			    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S�b','Dom'],
			    monthNames: ['Janeiro','Fevereiro','Mar�o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
			    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
			    nextText: 'Pr�ximo',
			    prevText: 'Anterior'
			});
		},

		// FIXME: a paginacao deveria estar relacionada ao post e nao ao settings
		carregaPaginacao : function() {
			numeroDaPagina = new dextranet.paginacao.pagina();
			consulta = new dextranet.paginacao.query();
			numeroDaPagina.setPaginaInicial();
			dextranet.paginacao.paginacaoDosPosts();
		}
};