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
		dextranet.comment.listaComentarios(idDoPost);
		idUltimo = idDoPost;
	},

	listaComentarios : function(idDoPost) {
		$.ajax( {
			type : 'GET',
			url : '/s/comment',
			data : {
				"idReference" : idDoPost
				},
			success : function(comments) {
					commentObjectArray = postObject.getpostObjectArrayFromPostJsonArray(comments);
					$(commentObjectArray).each(function(){
						dextranet.post.replaceDoTipsy(this.postObjectJson.userLikes);
					});
					if(commentObjectArray.length > 0)
						$.holy("../template/dinamico/post/comment.xml", {"jsonArrayComment" : commentObjectArray});
			}
		});
		return false;
	},

	comentar : function() {
		$("#form_comment").submit(function() {
			var idDoPost = $(this).attr("class");
			var conteudo = CKEDITOR.instances.textarea_comment.getData();
			var autor = $("#user_login").text();

			dextranet.home.limparAvisoPreenchaCampos();

			if (dextranet.strip.allElem(conteudo) == "") {
				if(!dextranet.home.EhVisivel("#message-warning"))
				{
					$("#container_message_warning_comment").addClass("container_message_warning");
					$.holy("../template/dinamico/post/mensagem_preencha_campos.xml", {"seletor":"#container_message_warning_comment"});
				}
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

	atualizaComentario : function(idComentario) {
		$.ajax({
			type : 'GET',
			url : '/s/comment',
			data : {
				"idComment" : idComentario
				},
			success : function(comment) {
					commentObjectArray = postObject.getpostObjectArrayFromPostJsonArray(comment);
					$(commentObjectArray).each(function() {
						$("span#" + idComentario).text(this.postObjectJson.likes);
						$(".comentario").attr("original-title", dextranet.post.replaceDoTipsy(this.postObjectJson.userLikes));
					});
				}
		});
	},

	limpaTelaComentario : function() {
		if(dextranet.home.EhVisivel("#message-warning"))
			$(".message").remove();
		$("#list_comments_fromPost").empty();
		CKEDITOR.instances.textarea_comment.setData("");
	}
}