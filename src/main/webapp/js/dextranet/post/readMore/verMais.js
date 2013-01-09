
verMais = function(){

	var limiteDeCaracteres = 300;
	var textoSemFormatacao = null;
	var textoFormatado = null;

	this.input = function(textoNaoFormatado){
		textoSemFormatacao = textoNaoFormatado;
		formataTexto();
	}

	this.output = function(){
		return textoFormatado;
	}

	formataTexto = function(){
		textoFormatado = limpaTexto(textoSemFormatacao);
	}

	limpaTexto = function(string){
		return string.substring(0,limiteDeCaracteres);
	}

}