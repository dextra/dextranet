dextranet.strip = {

	allElem : function(conteudo) {

		conteudo = conteudo.replace(/<.*?>/g, '');
		conteudo = conteudo.replace(/\n/g, '');
		conteudo = conteudo.replace(/ /g, '');
		conteudo = conteudo.replace(/&nbsp;/g, '');

		return conteudo;
	},

	lineBreak : function(conteudo) {
		return conteudo.replace(/\n/g,"");
	},

	tagHTML : function(conteudo) {
		return conteudo.replace(/<.*?>/g, '');
	}
}