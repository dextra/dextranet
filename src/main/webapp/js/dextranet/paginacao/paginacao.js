
dextranet.paginacao = {}

dextranet.paginacao.paginacaoDosPosts=function() {

	var ehUmNovoPost = false;
	var scroll = new dextranet.paginacao.scroll();

	$(window).scroll(function (){

		if (scroll.solicitarNovaPagina()) {
			dextranet.post.listarPosts(consulta.getText(), ehUmNovoPost, numeroDaPagina.getPagina());
			numeroDaPagina.next();
			console.info(numeroDaPagina.getPagina());
		}
	})
};

dextranet.paginacao.scroll = function() {

	var posicaoMaximaQueOScrollChegou = 0;
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
		if (getPosicao() > proximaPosicaoScrollParaRecarregar()){
			scrollChegouAteOFinal();
			return true;
		}
		else{
			return false;
		}
	}

	scrollChegouAteOFinal = function(){
		var url = "/s/post";
		var quantidadePostsSolicitados = "20";
		var busca = {
				"max-results" : quantidadePostsSolicitados,
				"page" : numeroDaPagina.getPagina(),
				"q" : consulta.getText()
		};

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
		console.info(status);
		chegouAteOFinal = status;
	}

	this.getChetouAteOFinal = function(){
		return chegouAteOFinal;
	}
}



