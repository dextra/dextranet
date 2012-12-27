function abrePaginaNovoPost() {
	$.holy("../template/abre_pagina_novo_post.xml", {});
	setActiveMenuLateral("#sidebar_left_new_post");
}

function fazPesquisa() {
	var query = $('#form_search_input').val();
	var ehUmNovoPost = false;
	var pagina = 0

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

		var post = form2js('form_new_post', '.', true, function(node) {
			if (node.id && node.id.match(/form_input_content/)) {
				return {
					name : "content",
					value : CKEDITOR.instances.form_input_content.getData()
				};
			}
		});

		console.log(post);

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

	var diaS = minhaData.slice(0, 3);
	var dia = minhaData.slice(8, 10);
	var mes = minhaData.slice(4, 7);
	var ano = minhaData.slice(24, 28);
	var hora = minhaData.slice(10, 16);

	switch (mes) {
	case "Jan":	mes = "01";
		break;
	case "Fer":	mes = "02";
		break;
	case "Mar":	mes = "03";
		break;
	case "Apr":	mes = "04";
		break;
	case "May":	mes = "05";
		break;
	case "Jun":	mes = "06";
		break;
	case "Jul":	mes = "07";
		break;
	case "Aug":	mes = "08";
		break;
	case "Sep":	mes = "09";
		break;
	case "Oct": mes = "10";
		break;
	case "Nov":	mes = "11";
		break;
	case "Dec":	mes = "12";
		break;
	}

	switch (diaS) {
	case "Sun":	diaS = "dom";
		break;
	case "Mon":	diaS = "seg";
		break;
	case "Tue":	diaS = "ter";
		break;
	case "Wed":	diaS = "qua";
		break;
	case "Thu":	diaS = "qui";
		break;
	case "Fri":	diaS = "sex";
		break;
	case "Sat":	diaS = "sab";
		break;
	}

	return diaS + ", " + dia + "/" + mes + "/" + ano + " - " + hora;
}

function paginacaoDosPost(query) {

	var pagina = 0;
	var ehUmNovoPost = false;
	var posicaoMinimaParaNovaPagina = posicaoDoScrollBuscarMaisPosts();
	var margemParaNovaBusca = (document.documentElement.scrollHeight);

	$(window)
			.scroll(
					function() {

						var posicaoDoScroll = $(document).scrollTop();

						if (posicaoDoScroll > posicaoMinimaParaNovaPagina) {
							posicaoMinimaParaNovaPagina = (posicaoDoScroll + margemParaNovaBusca);
							pagina = pagina + 1;
							busquePosts(query, ehUmNovoPost, pagina);
						}

					});
}

function posicaoDoScrollBuscarMaisPosts() {
	var maximoValorDoScroll = document.documentElement.scrollHeight;
	var porcentagemDaPaginaDisparaNovaBusca = 0.80;

	return (maximoValorDoScroll * (porcentagemDaPaginaDisparaNovaBusca));
}
