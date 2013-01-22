
dextranet.paginacao.pagina = function() {

	var numero;

	this.next = function() {
		numero = numero + 1;
	}

	this.back = function() {
		if(numero > 1) {
			numero = numero - 1;
		}
	}

	this.setPaginaInicial = function() {
		numero  = 1;
	}

	this.getPagina = function() {
		return numero;
	}

}