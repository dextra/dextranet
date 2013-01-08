
dextranet.paginacao = {}

dextranet.paginacao.paginacaoDosPosts=function() {
	$(window).scroll(onScroll());

	function onScroll(){
		var ehUmNovoPost = false;
		var posicaoMinimaParaNovaPagina = posicaoDoScrollBuscarMaisPosts();
		var margemParaNovaBusca = (document.documentElement.scrollHeight*0.95);
		var posicaoDoScroll = $(document).scrollTop();

		if (posicaoDoScroll > posicaoMinimaParaNovaPagina) {
				busquePosts(consulta.getText(), ehUmNovoPost, numeroDaPagina.getPagina());
				posicaoMinimaParaNovaPagina = (posicaoDoScroll + margemParaNovaBusca);
				numeroDaPagina.next();
		}
	}

	function posicaoDoScrollBuscarMaisPosts()
	{
		var maximoValorDoScroll = document.documentElement.scrollHeight;
		var porcentagemDaPaginaDisparaNovaBusca = 0.80;

		return (maximoValorDoScroll * (porcentagemDaPaginaDisparaNovaBusca));
	}
};




