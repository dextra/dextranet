dextranet.paginacao = {

	paginaCorrente : 1,

	complete : true,

	paginar : function() {
		$(window).scroll(function(e) {
			e.preventDefault();
			if(dextranet.paginacao.complete && ($(window).scrollTop() + $(window).height()) == ($(document).height()) && $('li#sidebar_left_home').hasClass('active')) {
				dextranet.paginacao.complete = false;
//				ultimoRegistro = $('ul#relacao_dos_posts li.listaPost:last div.list_stories_data div#time_ago').text();
//				dextranet.post.paginar(setIntervalUtils.formataData(ultimoRegistro));
				dextranet.post.listar(dextranet.paginacao.paginaCorrente + 1);
				dextranet.paginacao.paginaCorrente = dextranet.paginacao.paginaCorrente + 1;
			}
			return false;
		});
	},

	paginaCorrenteMicroBlog : 1,

	indiceUltimoMicroPost : 1,

	paginarMicroPost : false,

	novosPosts : [],

	novosMicroPosts : []

};