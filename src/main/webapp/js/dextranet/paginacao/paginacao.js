dextranet.paginacao = {

	paginaCorrente : 1,
	complete : true,

	paginar : function() {

		console.info('1');
		$(window).scroll(function(e) {
			e.preventDefault();
			console.info('2');
			if(dextranet.paginacao.complete && ($(window).scrollTop() + $(window).height()) == ($(document).height()) && $('li#sidebar_left_home').hasClass('active')) {
				console.info('3');
				dextranet.paginacao.complete = false;
				dextranet.post.listar(null, dextranet.paginacao.paginaCorrente+= 1);
			}
			return false;
		});
	}
};



