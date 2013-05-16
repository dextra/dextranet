dextranet.paginacao = {

	paginaCorrente : 1,

	paginar : function() {

		var complete = true;
		$(window).scroll(function() {
			if(complete && ($(window).scrollTop() + $(window).height()) == ($(document).height()) && $('li#sidebar_left_home').hasClass('active')) {
				complete = false;
				dextranet.post.listar(null, dextranet.paginacao.paginaCorrente+= 1);
			}
			return false;
		});
	}
};



