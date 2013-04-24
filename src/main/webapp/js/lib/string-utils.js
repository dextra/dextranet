stringUtils = {

	removeCaracteresEspeciais : function(texto) {

		texto = texto.replace(/<.*?>/g, '');
		texto = texto.replace(/\n/g, '');
		texto = texto.replace(/ /g, ''); //replace espaco
		texto = texto.replace(/	/g, ''); //replace tab
		texto = texto.replace(/&nbsp;/g, '');

		return texto;
	},

	removeQuebraDeLinha : function(texto) {
		return texto.replace(/\n/g,"");
	},

	removeTagHTML : function(texto) {
		return texto.replace(/<.*?>/g, '');
	}
}