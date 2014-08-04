dextranet.settings = {

		inicializa : function() {
			dextranet.settings.configuraLoading();
			dextranet.settings.carregaInternacionalizacao();
			dextranet.settings.inicializaMensagens();
			dextranet.settings.configuraDatePicker();
			dextranet.settings.configuraMascaras();
		},
	
		intervaloBuscaNovosPosts : 60000,//1 minuto

		alfabeto : ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"],

		configuraLoading : function() {
			$.loading( {
				text : 'Carregando...',
				overlay : '#23557E',
				opacity : '60'
			});

	        $.ajaxSetup( {
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
			    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
			    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
			    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
			    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
			    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
			    nextText: 'Próximo',
			    prevText: 'Anterior'
			});
		},

		configuraMascaras : function() {
			$.mask.masks = {
                'telefone'  : { mask : '(99) 9999-99999' },
                'data'      : { mask : '39/19/9999' },
                'ramal'     : { mask : '9999' }
			};
		},
};