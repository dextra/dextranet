dextranet.paginacao = {

	paginaCorrente : 1,
	complete : true,

	paginar : function() {
		$(window).scroll(function(e) {
			e.preventDefault();
			if(dextranet.paginacao.complete && ($(window).scrollTop() + $(window).height()) == ($(document).height()) && $('li#sidebar_left_home').hasClass('active')) {
				dextranet.paginacao.complete = false;
				dextranet.post.listar(dextranet.paginacao.paginaCorrente+= 1);
			}
			return false;
		});
	}
};



