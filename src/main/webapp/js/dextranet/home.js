
var dextranet = {

	configuraLoading : function() {
		$.loading({
			text : 'Carregando...',
			overlay : '#23557E',
			opacity: '60'
		});
	},

	setLoading : function() {
		jQuery.ajaxSetup({
			loading : true
		});
	},
};