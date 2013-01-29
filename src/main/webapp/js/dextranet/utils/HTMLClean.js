dextranet.strip = {

	allElem : function(texto) {

		texto = texto.replace(/<.*?>/g, '');
		texto = texto.replace(/\n/g, '');
		texto = texto.replace(/ /g, ''); //replace espaço
		texto = texto.replace(/	/g, ''); //replace tab
		texto = texto.replace(/&nbsp;/g, '');

		return texto;
	},

	lineBreak : function(texto) {
		return texto.replace(/\n/g,"");
	},

	tagHTML : function(texto) {
		return texto.replace(/<.*?>/g, '');
	}
}