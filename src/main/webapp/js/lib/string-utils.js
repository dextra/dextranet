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

	trocaQuebraDeLinha : function(texto) {
		return texto.replace(/\n/g,"<br>");
	},

	removeTagHTML : function(texto) {
		return texto.replace(/<.*?>/g, '');
	},

	resolveCaracteresHTML : function(texto) {
		var toRemove = $('body').append(
				'<div style="display: none;">'+texto+'</div>').children().last();
		var res = toRemove.text();
		toRemove.remove();
		return res;
	},

	validaEmail : function(email) {
	  var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	  return regex.test(email);
	}
}