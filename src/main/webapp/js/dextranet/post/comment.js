var idUltimo = "";

dextranet.comment = {

	chamaAbreTelaComentario : function() {
		var jaTemTextArea = false;
		$(".link").click(function() {
			var idDoPost = $(this).attr("id").substring(9);
			if (!jaTemTextArea) {
				if (dextranet.comment.abreTelaComentario($(this).attr("id"), idDoPost)) {
					idUltimo = idDoPost;
					jaTemTextArea = true;
				}
			} else {
				$("div.esteAqui").empty();
				$("div.esteAqui").removeClass("esteAqui");
				if(idUltimo != idDoPost) {
					if (dextranet.comment.abreTelaComentario($(this).attr("id"), idDoPost)) {
						jaTemTextArea = true;
						idUltimo = idDoPost;
					}
				} else {
					jaTemTextArea = false;
				}
			}
		});
	},

	abreTelaComentario : function(idDaDivDoPost, idDoPost) {
		var LIs = $("#relacao_dos_posts").children();
		var deuCerto = false;
		LIs.each(function() {
			if ($(this).attr("class") == idDaDivDoPost) {
				dextranet.comment.carregaComentario(idDoPost);
				$.holy("../template/dinamico/post/abre_pagina_novo_comment.xml", {"idDoPost" : idDoPost});

				deuCerto = true;
			}
		});

		return deuCerto;
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
			}
		});
		idUltimo = idDoPost;
	},

	comentar : function() {
		$("#form_comment").submit(function() {
			var idDoPost = $(this).attr("class");
			var conteudo = CKEDITOR.instances.textarea_comment.getData();
			var autor = $("#user_login").text();

			if (dextranet.strip.allElem(conteudo) == "") {
				if(!dextranet.home.EhVisivel("#message-warning-comment"))
					$("#form_comment").before('<ul class="message" id="message-warning-comment"><li class="warning">Preencha</li></ul>');
			}else {
				$.ajax( {
					type : 'POST',
					url : '/s/comment',
					data : {
						"text" : conteudo,
						"author" : autor,
						"idReference" : idDoPost
						},
					success : function(comments) {
							dextranet.comment.limpaTelaComentario();
							dextranet.comment.carregaComentario(idDoPost);
							dextranet.post.atualizaPost(idDoPost);
						}
				});
			}
			return false;
		});
	},

	limpaTelaComentario : function()
	{
		if(dextranet.home.EhVisivel("#message-warning-comment"))
			$(".message").remove();
		$("#list_comments_fromPost").empty();
		CKEDITOR.instances.textarea_comment.setData(null);
	}
}