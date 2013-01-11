
dextranet.paginacao = {}

dextranet.paginacao.paginacaoDosPosts=function() {

	$(window).scroll(function (){
		var ehUmNovoPost = false;
		var posicaoMinimaParaNovaPagina = posicaoDoScrollBuscarMaisPosts();
		var posicaoDoScroll = $(document).scrollTop();

		console.info("scroll: " + posicaoDoScroll + "\n posicaoMinimaParaNovaPagina: " + posicaoMinimaParaNovaPagina);
		if (posicaoDoScroll > posicaoMinimaParaNovaPagina) {
				dextranet.post.listarPosts(consulta.getText(), ehUmNovoPost, numeroDaPagina.getPagina());
				numeroDaPagina.next();
				console.info(numeroDaPagina.getPagina());
		}
	})

	function posicaoDoScrollBuscarMaisPosts()
	{
		var maximoValorDoScroll = document.documentElement.scrollHeight;
		var porcentagemDaPaginaDisparaNovaBusca = 0.65;

		return (maximoValorDoScroll * (porcentagemDaPaginaDisparaNovaBusca));
	}
};




