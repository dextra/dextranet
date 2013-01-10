this.postObjectArray = "",

dextranet.post = {

	fazPesquisa : function() {
		var ehUmNovoPost = false;
		var pagina = 0;
		consulta.setText($('#form_search_input').val());

		if(consulta.getText() != ""){
			dextranet.post.listarPosts(consulta.getText(), ehUmNovoPost, pagina);
		}
		return false;
	},

	listarPosts : function(query, ehUmNovoPost, pagina) {
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

					dextranet.post.carregaTemplatePost(postObjectArray,ehUmNovoPost);
				}
			}
		});

		if (pagina == 0){
			$.holy("../template/dinamico/carrega_miolo_home_page.xml",{});
		}
	},

	carregaTemplatePost : function(postObjectArray, ehUmNovoPost){
		var template = "../template/dinamico/post/post.xml";
		return $.holy(template, {"jsonArrayPost" : postObjectArray,"sucesso" : ehUmNovoPost});
	},

	adicionaBotaoVerMais:function(){
		dextranet.readMoreButton.addButtonEvent($(".list_stories_footer_call"),postObjectArray);
	},


	criaNovoPost:function() {

		var contentComparacao = CKEDITOR.instances.form_input_content.getData();
		contentComparacao = dextranet.stripHTML(contentComparacao,1);

		if (($("#form_input_title").val() == "") || (contentComparacao == "")) {
			$("li.warning").css("display", "list-item");
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