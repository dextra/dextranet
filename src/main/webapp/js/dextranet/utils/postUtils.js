function conteudoDeExibicao(texto) {
	
	texto = dextranet.strip.tagHTML(texto);
	
	return texto.length > 150 ? texto.substring(0, 150) + "..." : texto;
	
}