
dextranet.paginacao = {}

dextranet.paginacao.paginacaoDosPosts=function() {

	$(window).scroll(function (){
		var posicaoMinimaParaNovaPagina = posicaoDoScrollBuscarMaisPosts();
		var margemParaNovaBusca = (document.documentElement.scrollHeight*0.95);
		var posicaoDoScroll = $(document).scrollTop();

		if (posicaoDoScroll > posicaoMinimaParaNovaPagina) {
				dextranet.post.listaPost(consulta.getText(), numeroDaPagina.getPagina());
				posicaoMinimaParaNovaPagina = (posicaoDoScroll + margemParaNovaBusca);
				numeroDaPagina.next();
		}
	})

	function posicaoDoScrollBuscarMaisPosts()
	{
		var maximoValorDoScroll = document.documentElement.scrollHeight;
		var porcentagemDaPaginaDisparaNovaBusca = 0.80;

		return (maximoValorDoScroll * (porcentagemDaPaginaDisparaNovaBusca));
	}
};




