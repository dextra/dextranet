function conteudoDeExibicao(texto) {
	
	texto = dextranet.strip.tagHTML(texto);
	
	return texto.length > 200 ? texto.substring(0, 200) + "..." : texto;
	
}