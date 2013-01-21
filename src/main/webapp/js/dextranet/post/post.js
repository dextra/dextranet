dextranet.post = {

	fazPesquisa : function() {
		var pagina = 0;
		consulta.setText($('#form_search_input').val());

		if(consulta.getText() != ""){
			dextranet.post.listaPost(consulta.getText(), pagina);
		}
		return false;
	},

	listaPost : function(query, pagina)
	{
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
						this.postObjectJson.userLike = dextranet.post.replaceDoTipsy(this.postObjectJson.userLike);
					});

					dextranet.post.carregaTemplatePost(postObjectArray);
				}
			}
		});

		if (pagina == 0){
			$.holy("../template/dinamico/carrega_miolo_home_page.xml",{});

		}
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
					dextranet.home.carregaDados();
					$.holy("../template/dinamico/post/mensagem_sucesso.xml", {});
				}
			});
		}
		return false;
	},

	curtePost : function() {
		$(".linkCurtir").click(function() {
			var idDoPost = $(this).attr("id").substring(9);
			var user = $("#user_login").text();
			var classe = $(this).attr("class").substring(11);
			var ehPost = true;
			if(classe == "comentario ttip") {
				ehPost = false;
			}

			$.ajax( {
				type : 'POST',
				url : '/s/curtida',
				data : {
					"usuario" : user,
					"id" : idDoPost,
					"isPost" : ehPost
					},
				success : function() {
					if(ehPost) {
						dextranet.post.atualizaPost(idDoPost);
					} else {
						dextranet.comment.atualizaComentario(idDoPost);
					}
				}
			});
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
						console.log(dextranet.post.replaceDoTipsy(this.postObjectJson.userLike));
						$(".post").attr("original-title", dextranet.post.replaceDoTipsy(this.postObjectJson.userLike));
					});
				}
		});
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

	replaceDoTipsy : function(conteudo) {
		conteudo = conteudo.replace(/ /,'');
		conteudo = conteudo.replace(/ /g,'&lt;br/&gt;');
		return conteudo.replace(dextranet.usuario, 'você');
	}
};