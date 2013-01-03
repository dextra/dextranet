function abrePaginaNovoPost() {
	$.holy("../template/abre_pagina_novo_post.xml", {});
	setActiveMenuLateral("#sidebar_left_new_post");
}

function fazPesquisa() {
	var cons = $('#form_search_input').val();
	consulta.setText(cons);
	var ehUmNovoPost = false;
	var pagina = 0;

	if(consulta.getText() != ""){
		busquePosts(consulta.getText(), ehUmNovoPost, pagina);
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
						"author" : $("#user_login").text()
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

	var ehUmNovoPost = false;

	$(window)
			.scroll(
					function() {

						var posicaoMinimaParaNovaPagina = posicaoDoScrollBuscarMaisPosts();
						var margemParaNovaBusca = (document.documentElement.scrollHeight*0.95);
						var posicaoDoScroll = $(document).scrollTop();

						if (posicaoDoScroll > posicaoMinimaParaNovaPagina) {
								busquePosts(consulta.getText(), ehUmNovoPost, numeroDaPagina.getPagina());
								posicaoMinimaParaNovaPagina = (posicaoDoScroll + margemParaNovaBusca);
								numeroDaPagina.next();
						}
					});
}

function posicaoDoScrollBuscarMaisPosts() {
	var maximoValorDoScroll = document.documentElement.scrollHeight;
	var porcentagemDaPaginaDisparaNovaBusca = 0.80;

	return (maximoValorDoScroll * (porcentagemDaPaginaDisparaNovaBusca));
}


function pagina(){
	var numero;

	this.next = function(){
		numero = numero + 1;
	}
	this.back = function(){
		if(numero > 1){
			numero = numero - 1;
		}
	}
	this.setPaginaInicial = function(){
		numero  = 1;
	}
	this.getPagina = function(){
		return numero;
	}

}

function query(){
	var text = "";

	this.setText = function(novoTexto){
		if(novoTexto == ""){
			text = "";
		}
		else{
			text= "\"" + novoTexto + "\"";
		}
	}
	this.getText = function(){
		return text;
	}

}