dextranet.paginacao.query = function() {

	var text = "";

	this.setText = function(novoTexto) {
		text = novoTexto;
	}

	this.getText = function() {
		return text;
	}
}