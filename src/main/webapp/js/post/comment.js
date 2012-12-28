function abreTelaComentario(idDaDivDoPost) {
	var LIs = $("#relacao_dos_posts").children();
	var deuCerto = false;
	LIs.each(function() {
		if ($(this).attr("class") == idDaDivDoPost) {
			$(this).append("<br><div align='center'><form id='form_comment'><textarea id='textarea_comment' placeholder='Digite seu comentario' style='resize:none;'  />" +
					"<br><input type='submit' value='Comentar' /></form></div>");
			deuCerto = true;
		}
	});

	return deuCerto;
}

//Erros com o comentario:
//	quando ele abrir outro textarea (nao na mesmo post)
//	quando der um submit, ele deve ocultar

function chamaAbreTela() {
	var jaTemTextArea = false;
	$(".link").click(function(){
		if(!jaTemTextArea){
			if(abreTelaComentario($(this).attr("id"))){
				CKEDITOR.replace('textarea_comment');
				jaTemTextArea = true;
			}
		}
	});
}