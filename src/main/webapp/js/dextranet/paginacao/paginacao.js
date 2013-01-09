
dextranet.paginacao = {}

dextranet.paginacao.paginacaoDosPosts=function() {
	//$(window).scroll(onScroll());

	$(window).scroll(function (){
		var ehUmNovoPost = false;
		var posicaoMinimaParaNovaPagina = posicaoDoScrollBuscarMaisPosts();
		var margemParaNovaBusca = (document.documentElement.scrollHeight*0.95);
		var posicaoDoScroll = $(document).scrollTop();

		if (posicaoDoScroll > posicaoMinimaParaNovaPagina) {
				dextranet.post.listarPosts(consulta.getText(), ehUmNovoPost, numeroDaPagina.getPagina());
				posicaoMinimaParaNovaPagina = (posicaoDoScroll + margemParaNovaBusca);
				numeroDaPagina.next();
				console.info("Pagina + 1");
		}
	})

	function posicaoDoScrollBuscarMaisPosts()
	{
		var maximoValorDoScroll = document.documentElement.scrollHeight;
		var porcentagemDaPaginaDisparaNovaBusca = 0.80;

		return (maximoValorDoScroll * (porcentagemDaPaginaDisparaNovaBusca));
	}
};




