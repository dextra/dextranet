dextranet.paginacao = {
		acabouOsPosts : false,
		
		resetPaginacao : function() {
			acabouOsPosts = false;
		}
}

dextranet.paginacao.paginacaoDosPosts = function() {

	var complete = true;
	$(window).scroll(function() {
		if(complete && ($(window).scrollTop() + $(window).height()) > ($('body').height()) && !dextranet.paginacao.acabouOsPosts) {
			console.log(consulta.getText());
			complete = false;
			dextranet.post.listaPost(consulta.getText(), numeroDaPagina.getPagina(), function() {complete = true;});			
			numeroDaPagina.next(); 
		}
		return false;
	});
};



