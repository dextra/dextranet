var idUltimo = "";

function abreTelaComentario(idDaDivDoPost, idDoPost) {
	var LIs = $("#relacao_dos_posts").children();
	var deuCerto = false;
	LIs.each(function() {
		if ($(this).attr("class") == idDaDivDoPost) {
			carregaComentario(idDoPost);
			deuCerto = true;
		}
	});

	return deuCerto;
}

function chamaAbreTelaComentario() {
	var jaTemTextArea = false;
	$(".link").click(function(){
		var idDoPost = $(this).attr("id").substring(9);
		if(!jaTemTextArea){
			if(abreTelaComentario($(this).attr("id"),idDoPost)){
				jaTemTextArea = true;
			}
		} else {
			$("div.esteAqui").empty();
			$("div.esteAqui").removeClass("esteAqui");
			if(abreTelaComentario($(this).attr("id"),idDoPost)){
				jaTemTextArea = true;
			}
		}
	});
}

function carregaComentario(idDoPost) {
	$("div." + idDoPost).addClass("esteAqui");
	$.ajax( {
		type : 'GET',
		url : '/s/comment',
		data : '',
		success : function(comments) {
			if(comments.length > 0){
				$.holy("../template/comment.xml", {"jsonArrayComment" : comments}); //colocar seletor antes
			}
		}
	});
	idUltimo = idDoPost;
}

function criaComentario() {
	//esperando serviço de criação de comentario
	carregaDadosHomePageAposInclusao();
}