
dextranet.paginacao.query = function() {

	var text = "";

	this.setText = function(novoTexto) {
		if(novoTexto == "") {
			text = "";
		} else {
			text= "\"" + novoTexto + "\"";
		}
	}

	this.getText = function() {
		return text;
	}
}