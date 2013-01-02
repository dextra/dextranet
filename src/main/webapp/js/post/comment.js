function abreTelaComentario(idDaDivDoPost) {
	var LIs = $("#relacao_dos_posts").children();
	var deuCerto = false;
	LIs.each(function() {
		if ($(this).attr("class") == idDaDivDoPost) {
			$(this).append("<div id='list_stories_form_comment'></div>");
			$.holy("../template/carrega_form_comentario.xml", {});
			deuCerto = true;
		}
	});

	return deuCerto;
}

function chamaAbreTelaComentario() {
	var jaTemTextArea = false;
	$(".link").click(function(){
		if(!jaTemTextArea){
			if(abreTelaComentario($(this).attr("id"))){
				jaTemTextArea = true;
			}
		} else {
			$("#list_stories_form_comment").remove();
			if(abreTelaComentario($(this).attr("id"))){
				jaTemTextArea = true;
			}
		}
	});
}

function criaComentario() {
	//esperando serviço de criação de comentario
	carregaDadosHomePageAposInclusao();
}