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

		$.ajax( {
			type : 'GET',
			url : '/s/comment',
			data : {
				"idReference" : idDoPost
				},
			success : function(comments) {
					if(comments.length > 0)
						$.holy("../template/dinamico/post/comment.xml", {"jsonArrayComment" : comments});
					$.holy("../template/dinamico/post/abre_pagina_novo_comment.xml", {"idDoPost" : idDoPost});
			}
		});
		idUltimo = idDoPost;
	},

	comentar : function() {
		$("#form_comment").submit(function() {
			var idDoPost = $(this).attr("class");
			var conteudo = CKEDITOR.instances.textarea_comment.getData();
			var autor = $("#user_login").text();
			console.log(idDoPost);
			$.ajax( {
				type : 'POST',
				url : '/s/comment',
				data : {
					"text" : conteudo,
					"author" : autor,
					"idReference" : idDoPost
					},
				success : function(comments) {
						console.log(idDoPost);
				}
			});
		});
	}
}