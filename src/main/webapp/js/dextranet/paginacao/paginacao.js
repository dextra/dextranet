
dextranet.paginacao = {}

dextranet.paginacao.paginacaoDosPosts = function() {

	var scroll = new dextranet.paginacao.scroll();

	$(window).scroll(function (){
		if (scroll.solicitarNovaPagina() == true) {
			dextranet.post.listaPost(consulta.getText(),numeroDaPagina.getPagina());
			numeroDaPagina.next();
		}
	})
};

dextranet.paginacao.scroll = function() {

	var chegouAteOFinal = true;

	getPosicao = function(){
		return posicaoDoScroll = $(document).scrollTop();
	}

	proximaPosicaoScrollParaRecarregar = function(){
		var maximoValorDoScroll = document.documentElement.scrollHeight;
		var porcentagemDaPaginaDisparaNovaBusca = 0.65;
		return (maximoValorDoScroll * (porcentagemDaPaginaDisparaNovaBusca));
	}

	this.solicitarNovaPagina = function(){
		var r = false;

		if (getPosicao() > proximaPosicaoScrollParaRecarregar()){
			verificaSeOScrollChegouAteOFinal();
			if(getChegouAteOFinal() == false){
				r = true;
			}
		}
		return r;
	}

	verificaSeOScrollChegouAteOFinal = function(){
		var url = "/s/post";
		var quantidadePostsSolicitados = "20";
		var busca = {
				"max-results" : quantidadePostsSolicitados,
				"page" : numeroDaPagina.getPagina(),
				"q" : consulta.getText()};

		$.ajax( {
			type : "GET",
			url : url,
			data : busca,
			success : function(posts) {
				if(posts.length > 0){
					setChegouAteOFinal(false);
				}
				else{
					setChegouAteOFinal(true);
				}
			}
		});
	}

	setChegouAteOFinal = function(status){
		chegouAteOFinal = status;
	}

	getChegouAteOFinal = function(){
		return chegouAteOFinal;
	}
}



