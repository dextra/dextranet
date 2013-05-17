dextranet.post = {

		foundPosts : [],

		novo : function() {
			$.holy("../template/dinamico/post/novo_post.xml", {});
			dextranet.ativaMenu("sidebar_left_new_post");
		},

		postar : function() {
			// validacao feita manualmente em funcao do CKEDITOR
			var titulo = $('#form_new_post input#form_input_title').val();
			var tituloLimpo = stringUtils.removeCaracteresEspeciais(titulo);
			var conteudo = CKEDITOR.instances.form_input_content.getData();
			var conteudoLimpo = stringUtils.removeCaracteresEspeciais(conteudo);

			if (tituloLimpo != "" && conteudoLimpo != "") {
				$.ajax( {
					type : "POST",
					url : "/s/post/",
					data : { 'titulo' : titulo, 'conteudo' : conteudo },
					success : function() {
						$('.message').message($.i18n.messages.post_mensagem_postagem_sucesso, 'success', true);
						dextranet.post.listar(null, 1);
					},
	    			error: function(jqXHR, textStatus, errorThrown) {
	    				dextranet.processaErroNaRequisicao(jqXHR);
	    			}
				});
			} else {
				$('.message').message($.i18n.messages.post_erro_campos_obrigatorios, 'error', true);
			}
		},

		listar : function(idPost, pagina) {
			console.info('Pagina antes: ' + pagina);
			console.info('Pagina corrente antes: ' + dextranet.paginacao.paginaCorrente);
			if (pagina == 1) {
				dextranet.post.foundPosts = [];
				dextranet.paginacao.paginaCorrente = 1;
			}
			if (!pagina) {
				var pagina = 1;
			}


			$.ajax( {
				type : "GET",
				url : "/s/post?p="+pagina,
				contentType : dextranet.application_json,
				success : function(posts) {
					if(posts != null && posts.length > 0){
						dextranet.post.foundPosts = dextranet.post.foundPosts.concat(posts);
						$.holy("../template/dinamico/post/lista_posts.xml", { posts : dextranet.post.foundPosts,
							  												  gravatar : dextranet.gravatarUrl,
							  												  idPost : idPost});
						dextranet.ativaMenu("sidebar_left_home");
					}
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		},

		conteudoParaExibicao : function(conteudo) {
			var conteudoLimpo = stringUtils.removeTagHTML(conteudo);

			if (conteudoLimpo.length > 200) {
				conteudoLimpo = conteudoLimpo.substring(0, 199) + " (...)"
			}

			return conteudoLimpo;
		},

		curtir : function(postId) {
			$.ajax( {
				type : "POST",
				url : "/s/post/"+postId+"/curtida",
				contentType : dextranet.application_json,
				success : function(qtdCurtidas) {
					$("div.list_stories_data a#showLikes_"+postId+" span.numero_curtida").text(qtdCurtidas);
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		},

		listarCurtidas : function(postId) {
			$.ajax( {
				type : "GET",
				url : "/s/post/"+postId+"/curtida",
				contentType : dextranet.application_json,
				success : function(curtidas) {
					$.holy("../template/dinamico/post/lista_curtidas.xml", { curtidas : curtidas, gravatar : dextranet.gravatarUrl });
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		},

		remover : function(postId) {
			$.ajax( {
				type : "DELETE",
				url : "/s/post/"+postId,
				contentType : dextranet.application_json,
				success : function() {
					dextranet.post.listar(null, 1);
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		},

		// Comentarios
		comentar : function(idPost) {
			var idDoPost = idPost;
			var conteudo = $('#' + idPost + ' .idConteudoComentario').val();
			if (conteudo == "") {
				// FIXME: nao daria pra usar o proprio componente de mensagens?
				if (!dextranet.home.EhVisivel("#message-warning")) {
					$("#container_message_warning_comment").addClass(
							"container_message_warning");
					$.holy("../template/dinamico/post/mensagem_preencha_campos.xml",{
										"seletor" : "#container_message_warning_comment"
					});
				}
			} else {
				$.ajax({
					type : 'POST',
					url : '/s/post/' + idDoPost + '/comentario',
					data : {
						"conteudo" : conteudo
					},
					success : function(comments) {
						dextranet.post.limpaCampoComentario();
						dextranet.post.listar(idDoPost);
					}
				});
			}
			return false;
		},

		limpaCampoComentario : function() {
			if ($('#idConteudoComentario').val() != "") {
				$('#idConteudoComentario').val("");
			}
		},


		removerComentario : function(postId, comentarioId) {
			$.ajax( {
				type : "DELETE",
				url : "/s/post/"+comentarioId+"/comentario",
				contentType : dextranet.application_json,
				success : function() {
					dextranet.post.listar(postId);
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		},

		curtirComentario : function(postId, comentarioId) {
			$.ajax( {
				type : "POST",
				url : "/s/post/"+postId+"/"+comentarioId+"/curtida",
				contentType : dextranet.application_json,
				success : function(qtdCurtidas) {
					$("a#showLikes_"+comentarioId+" span.numero_curtida").text(qtdCurtidas.quantidadeDeCurtidas);
				}
			});
		},

		listarCurtidasComentario : function(postId, comentarioId) {
			$.ajax( {
				type : "GET",
				url : "/s/post/"+postId+"/"+comentarioId+"/curtida",
				contentType : dextranet.application_json,
				success : function(curtidas) {
					$.holy("../template/dinamico/post/lista_curtidas.xml", { curtidas : curtidas, gravatar : dextranet.gravatarUrl });
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		}
	}