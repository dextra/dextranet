dextranet.microblog = {

	foundMicroPosts : [],

	listar : function(pagina) {
		if (pagina == 1) {
			dextranet.microblog.foundMicroPosts = [];
			dextranet.paginacao.paginaCorrenteMicroBlog = 1;
		}
		if (!pagina) {
			var pagina = 1;
		}

		$.ajax( {
			type : "GET",
			url : "/s/microblog/post?p="+pagina,
			contentType : dextranet.application_json,
			success : function(microposts) {
				if(microposts != null && microposts.length > 0){
					dextranet.microblog.foundMicroPosts = dextranet.microblog.foundMicroPosts.concat(microposts);
				}
				$.holy("../template/dinamico/microblog/lista_microposts.xml", { paginar : microposts.length > 0,
																				posts : dextranet.microblog.foundMicroPosts,
																       			gravatar : dextranet.gravatarUrl });
			},
			error: function(jqXHR, textStatus, errorThrown) {
				dextranet.processaErroNaRequisicao(jqXHR);
			}
		});
	},

	postar : function() {
		var conteudo = $('div#novo_micropost textarea#conteudo_novo_micropost').val();
		if (conteudo != "") {
			$.ajax({
				type : 'POST',
				url : '/s/microblog/post',
				data : {
					"texto" : conteudo
				},
				success : function() {
					dextranet.microblog.listar(1);
				},
				error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		} else {
			$('.message').message($.i18n.messages.erro_campos_obrigatorios, 'error', true);
		}
		return false;
	},

	verificaNovos : function(data) {
		$.ajax( {
			type : "GET",
			url : "/s/microblog/count?d="+data,
			contentType : dextranet.application_json,
			loading : false,
			success : function(resultado) {
				if(resultado > 0){
					$('div#novos_microPosts').show();
					$('span#qtd_novos_microPosts').text(resultado);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				dextranet.processaErroNaRequisicao(jqXHR);
			}
		});
	},

	remover : function(microPostId) {
		$.ajax( {
			type : "DELETE",
			url : "/s/microblog/"+microPostId,
			contentType : dextranet.application_json,
			success : function() {
				$('li#'+microPostId).slideUp(function(){
					$(this).remove();
					dextranet.microblog.listar(1);
				});
			},
			error: function(jqXHR, textStatus, errorThrown) {
				dextranet.processaErroNaRequisicao(jqXHR);
			}
		});
	}
}