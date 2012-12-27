function abrePaginaNovoPost() {
	$.holy("../template/abre_pagina_novo_post.xml", {});
	setActiveMenuLateral("#sidebar_left_new_post");
}

function fazPesquisa() {
	var textSearch = $('#form_search_input').val();
	var dados = "max-results=20";
	if (textSearch != "")
		dados += "&q=\"" + textSearch + "\"";
	$.ajax( {
		type : 'GET',
		url : "/s/post",
		data : dados,
		success : function(jsonArrayPost) {
			$.holy("../template/carrega_miolo_home_page.xml", {
				"jsonArrayPost" : jsonArrayPost,
				"sucesso" : false
			});
		}
	});
	return false;
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
	var horario = minhaData.slice(13, 16);

	//Para corrigir erro estranho, gambiarra
	var hora = minhaData.slice(10, 13);
	if (hora == "00") {hora = "22"}
	else if (hora == "01") {hora = "23"}
	else{
		hora = hora -2;
	}

	switch (mes) {
	case "Jan":
		mes = "01";
		break;
	case "Fer":
		mes = "02";
		break;
	case "Mar":
		mes = "03";
		break;
	case "Apr":
		mes = "04";
		break;
	case "May":
		mes = "05";
		break;
	case "Jun":
		mes = "06";
		break;
	case "Jul":
		mes = "07";
		break;
	case "Aug":
		mes = "08";
		break;
	case "Sep":
		mes = "09";
		break;
	case "Oct":
		mes = "10";
		break;
	case "Nov":
		mes = "11";
		break;
	case "Dec":
		mes = "12";
		break;
	}

	switch (diaS) {
	case "Sun":
		diaS = "dom";
		break;
	case "Mon":
		diaS = "seg";
		break;
	case "Tue":
		diaS = "ter";
		break;
	case "Wed":
		diaS = "qua";
		break;
	case "Thu":
		diaS = "qui";
		break;
	case "Fri":
		diaS = "sex";
		break;
	case "Sat":
		diaS = "sab";
		break;
	}

	return diaS + ", " + dia + "/" + mes + "/" + ano + " - " + hora + horario;
}

function paginacaoDosPost() {

	var pagina = 0;
	var query = "";
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
	var maximoValorDoScroll =  document.documentElement.scrollHeight;
	var porcentagemDaPaginaDisparaNovaBusca = 0.80;

	return (maximoValorDoScroll * (porcentagemDaPaginaDisparaNovaBusca));
}
