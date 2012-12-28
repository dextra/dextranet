function abreTelaComentario(idDaDivDoPost) {
	var LIs = $("#relacao_dos_posts").children();
	var deuCerto = false;
	LIs.each(function() {
		if ($(this).attr("class") == idDaDivDoPost) {
			$(this).append("<br><div><form id='form_comment'><textarea id='textarea_comment' placeholder='Digite seu comentario' style='resize:none;'  /></form></div>");
			deuCerto = true;
		}
	});

	return deuCerto;
}