// Variáveis globais
query = "";

function abrePaginaNovoPost() {
	$.holy("../template/abre_pagina_novo_post.xml", {});
	setActiveMenuLateral("#sidebar_left_new_post");
}

function fazPesquisa() {
	query = $('#form_search_input').val();
	var ehUmNovoPost = false;
	var pagina = 0;

	if(query != ""){
		query = "\"" + query + "\"";
		busquePosts(query, ehUmNovoPost, pagina);
	}

	return false; //O retorno falso faz com que a página de pesquisa não sofra reload para index
}

function criaNovoPost() {
	if (($("#form_input_title").val() == "")
			|| (CKEDITOR.instances.form_input_content.getData() == "")) {
		alert("Preencha todos os campos.");
		return false;
	} else {

		console.log(post);
		var post =  {
						"title" : $("#form_input_title").val(),
						"content" : CKEDITOR.instances.form_input_content.getData(),
						"author" : $.cookie("userLogin")
					}

		$.ajax( {
			type : "POST",
			url : "/s/post",
			data : post,
			success : function() {
				carregaDadosHomePageAposInclusao();
			}
		});
		return false;
	}
}

function converteData(minhaData) {

	var dateFormat = "E, dd/MM/yyyy hh:mm";
	var date;

	var dateParse = Date.parse(minhaData);

	//feito para não afetar execução enquanto não é feita a mudança na data enviada pelo serviço.
	if(isNaN(dateParse)){
		date = convertToDate(minhaData);
	}else{
		date = new Date(minhaData);
	}

	return formatDate(date, dateFormat);
}

function paginacaoDosPost() {

	var pagina = 1;
	var ehUmNovoPost = false;

	$(window)
			.scroll(
					function() {

						var posicaoMinimaParaNovaPagina = posicaoDoScrollBuscarMaisPosts();
						var margemParaNovaBusca = (document.documentElement.scrollHeight*0.95);
						var posicaoDoScroll = $(document).scrollTop();

						console.log("Posição mínima nova página : " + posicaoMinimaParaNovaPagina +
								" Margem adicionada : " + margemParaNovaBusca +
								" posição do Scroll: " + posicaoDoScroll);

						if (posicaoDoScroll > posicaoMinimaParaNovaPagina) {
								busquePosts(query, ehUmNovoPost, pagina);
								console.log("mais páginas");
								posicaoMinimaParaNovaPagina = (posicaoDoScroll + margemParaNovaBusca);
								pagina = pagina + 1;
						}
					});
}

function posicaoDoScrollBuscarMaisPosts() {
	var maximoValorDoScroll = document.documentElement.scrollHeight;
	var porcentagemDaPaginaDisparaNovaBusca = 0.80;

	return (maximoValorDoScroll * (porcentagemDaPaginaDisparaNovaBusca));
}
