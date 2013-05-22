dextranet.paginacao = {

	paginaCorrente : 1,
	complete : true,

	paginar : function() {
		console.info("Chamada da função de paginar");
		$(window).scroll(function(e) {
			e.preventDefault();
			console.info("Chamada da função de scroll");
			if(dextranet.paginacao.complete && ($(window).scrollTop() + $(window).height()) == ($(document).height()) && $('li#sidebar_left_home').hasClass('active')) {
				dextranet.paginacao.complete = false;
				dextranet.post.listar(null, dextranet.paginacao.paginaCorrente+= 1);
			}
			return false;
		});
	}
};



