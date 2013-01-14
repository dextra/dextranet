var idUltimo = "";

dextranet.comment = {

	abreTelaComentario : function(idDaDivDoPost, idDoPost) {
		var LIs = $("#relacao_dos_posts").children();
		var deuCerto = false;
		LIs.each(function() {
			if ($(this).attr("class") == idDaDivDoPost) {
				dextranet.comment.carregaComentario(idDoPost);
				deuCerto = true;
			}
		});

		return deuCerto;
	},

	chamaAbreTelaComentario : function() {
		var jaTemTextArea = false;
		$(".link").click(function() {
			var idDoPost = $(this).attr("id").substring(9);
			if (!jaTemTextArea) {
				if (dextranet.comment.abreTelaComentario($(this).attr("id"), idDoPost)) {
					jaTemTextArea = true;
				}
			} else {
				$("div.esteAqui").empty();
				$("div.esteAqui").removeClass("esteAqui");
				if (dextranet.comment.abreTelaComentario($(this).attr("id"), idDoPost)) {
					jaTemTextArea = true;
				}
			}
		});
	},

	carregaComentario : function(idDoPost) {
		$("div." + idDoPost).addClass("esteAqui");

		$.holy("../template/dinamico/post/abre_pagina_novo_comment.xml", {"idDoPost" : idDoPost});
//		$.ajax( {
//			type : 'GET',
//			url : '/s/comment',
//			data : {
//				"idReference" : idDoPost
//				},
//			success : function(comments) {
//					if(!isEmpty(comments))
//						$.holy("../template/comment.xml", {"jsonArrayComment" : comments});
//					$.holy("../template/dinamico/post/abre_pagina_novo_comment.xml", {"idDoPost" : idDoPost});
//			}
//		});
		idUltimo = idDoPost;
	},

	comentar : function() {

		var conteudo = CKEDITOR.instances.textarea_comment.getData();
		var autor = $("#user_login").text();

		$.ajax( {
			type : 'POST',
			url : '/s/comment',
			data : {
				"text" : conteudo,
				"author" : autor,
				"idReference" : "6535adc4-c9a9-4665-a118-ed36281ec001"
				},
			success : function() {
					alert("COMENTOU!!!!");
//					$.holy("../template/dinamico/post/comment.xml", {"jsonArrayComment" : comments});
			}
		});
	}
}

function isEmpty(obj) {
    for(var prop in obj) {
        if(obj.hasOwnProperty(prop))
            return false;
    }

    return true;
}