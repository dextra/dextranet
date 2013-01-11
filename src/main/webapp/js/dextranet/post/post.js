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
						this.setHiddenText();
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

	adicionaBotaoVerMais:function(postObjectArray){
		dextranet.post.removeParagrafosVazios($(".list_stories_lead"));
		dextranet.readMoreButton.addButtonEvent($(".list_stories_footer_call"),postObjectArray);
	},

	removeParagrafosVazios:function(posts){
		posts.each(function(){
			var paragrafos = $(this).children();
			$(paragrafos[0]).remove();
			$(paragrafos[$(paragrafos).size()-1]).remove();
		});
	},


	criaNovoPost:function() {
		var contentComparacao = CKEDITOR.instances.form_input_content.getData();
		contentComparacao = dextranet.stripHTML(contentComparacao,1);

		if (($("#form_input_title").val() == "") || (contentComparacao == "")) {
			if(!dextranet.home.EhVisivel("#message-warning"))
				$("#form_new_post").before('<ul class="message" id="message-warning"><li class="warning">Preencha</li></ul>');
		} else {

			var conteudo = dextranet.post.removeLinebreak(CKEDITOR.instances.form_input_content.getData());

			console.info(conteudo);

			var post = {
				"title" : dextranet.stripHTML($("#form_input_title").val()),
				"content" : conteudo,
				"author" : $("#user_login").text()
			};

			$.ajax( {
				type : "POST",
				url : "/s/post",
				data : post,
				success : function() {
					dextranet.home.carregaDados(true);
				}
			});
		}
		return false;
	},

	removeLinebreak : function(CKEditorText){
		return CKEditorText.replace(/\n/g,"");
	}
};