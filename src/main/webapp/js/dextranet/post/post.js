dextranet.post = {

	inicializa : function() {
		dextranet.post.verConteudoPost();
		dextranet.comment.inicializa();
		$('.linkCurtir').tipsy({html:true});
	},

	listaPost : function(query, pagina, oncomplete) {
		var url = "/s/post";
		var quantidadePostsSolicitados = "20";

		var busca = {
				"max-results" : quantidadePostsSolicitados,
				"page" : pagina,
				"q" : query
		};

		$.ajax( {
			type : "GET",
			url : url,
			data : busca,
			success : function(posts) {
				if(posts.length > 0){
					postObjectArray = postObject.getpostObjectArrayFromPostJsonArray(posts);
					$(postObjectArray).each(function(){
						this.postObjectJson.userLike = dextranet.curtir.replaceDoTipsy(this.postObjectJson.userLike);
					});

					dextranet.post.carregaTemplatePost(postObjectArray);
					
				}
				if(posts.length < 20) {
					dextranet.paginacao.acabouOsPosts = true;
				}
				if (oncomplete) {
					oncomplete();
				}
			}
		});
	},

	buscaPost : function() {
		$("#container_mensagem").empty();
		var pagina = 0;
		consulta.setText($('#form_search_input').val());

		if(consulta.getText() != ""){
			$("#relacao_dos_posts").empty();
			dextranet.paginacao.resetPaginacao();
			dextranet.post.listaPost(consulta.getText(), pagina);
		}
		return false;
	},

	criaNovoPost : function() {

		var titulo = dextranet.strip.tagHTML($("#form_input_title").val());
		var conteudo = CKEDITOR.instances.form_input_content.getData();
		var autor = $("#user_login").text();

		dextranet.home.limparAvisoPreenchaCampos();

		if (dextranet.strip.allElem(titulo) == "" || dextranet.strip.allElem(conteudo) == "") {
			$("#container_message_warning_post").addClass("container_message_warning");
			$.holy("../template/dinamico/post/mensagem_preencha_campos.xml", {});
		} else {
			$.ajax( {
				type : "POST",
				url : "/s/post",
				data : {
					"title" : titulo.replace(/  /g, " &nbsp;"), //.replace serve pro browser reconhecer os espaços digitados pelo usuario
					"content" : dextranet.strip.lineBreak(conteudo),
					"author" : autor
				},
				success : function() {
					dextranet.post.limpaTelaPost();
					$("#container_mensagem").empty();
					$.holy("../template/dinamico/post/mensagem_sucesso.xml", {});
					$("#relacao_dos_posts").empty();
					dextranet.paginacao.resetPaginacao();
					dextranet.post.listaPost("", 0);
				}
			});
		}
		return false;
	},
	
	removeTodosOsPosts:function() {
		$.ajax( {
			type : "GET",
			url : "http://dextranet-desenvolvimento.appspot.com/s/post?max-results=70&page=0&q=",
			success : function(posts) {
				for (i=0; i < posts.length; i++) {
					$.ajax( {
						type: "DELETE",
						url : "http://dextranet-desenvolvimento.appspot.com/s/post/" + posts[i].id
					} );
				}
				alert("Removido");
			}
		} );
	},

	carregaTemplatePost : function(postObjectArray){
		var template = "../template/dinamico/post/post.xml";
		return $.holy(template, {"jsonArrayPost" : postObjectArray});
	},

	removeParagrafosVazios : function(posts){
		posts.each(function(){
			var paragrafos = $(this).children();
			$(paragrafos[0]).remove();
			$(paragrafos[$(paragrafos).size()-1]).remove();
		});
	},

	verConteudoPost : function() {
		$("h2.titulo").click(function() {
			var idDoPost = $(this).attr("class").substring(7);
			$("div#" + idDoPost + "_post").slideToggle("fast");
		});
	},

	atualizaPost : function(idDoPost) {
		$.ajax( {
			type : 'GET',
			url : '/s/post',
			data: {
				"max-results" : 1,
				"page" : 0,
				"q" : idDoPost
				},
			success : function(post) {
				postObjectArray = postObject.getpostObjectArrayFromPostJsonArray(post);
				$(postObjectArray).each(function(){
					$("li." + idDoPost + " .numero_comentario").text(this.postObjectJson.comentarios);
					$("li." + idDoPost + " .numero_curtida").text(this.postObjectJson.likes);
					$("li." + idDoPost + " .post").attr("original-title", dextranet.curtir.replaceDoTipsy(this.postObjectJson.userLike));
				});
			}
		});
	},

	limpaTelaPost : function() {
		dextranet.home.abrePopUpNovoPost();
		$("#form_input_title").val("");
		$("#form_search_input").val("");
		CKEDITOR.instances.form_input_content.setData("");
	}
};