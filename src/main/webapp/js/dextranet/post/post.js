dextranet.post = {

//FIXME carregaDadosHomePage() -> usar hide() do javascript ou não
//FIXME postObject

	fazPesquisa:function() {
		var ehUmNovoPost = false;
		var pagina = 0;
		consulta.setText($('#form_search_input').val());

		if(consulta.getText() != ""){
			dextranet.post.listarPosts(consulta.getText(), ehUmNovoPost, pagina);
		}
		return false;
	},

	listarPosts:function(query, ehUmNovoPost, pagina) {
		var url = "/s/post";
		var quantidadePostsSolicitados = "20";
		var template = "../template/post.xml";

		$.ajax( {
			type : "GET",
			url : url,
			data : "max-results=" + quantidadePostsSolicitados + "&page=" + pagina + "&q=" + query,
			success : function(posts) {
				if(posts.length > 0){
					var postObjectArray = postObject.getpostObjectArrayFromPostJsonArray(posts);

					$(postObjectArray).each(function(){
						this.setHiddenText();
					});

					$.when(dextranet.post.carregaTemplatePost(template,postObjectArray,ehUmNovoPost))
						.done(dextranet.post.adicionaBotaoVerMais(postObjectArray));
				}
			}
		});

		if (pagina == 0){
			$.holy("../template/carrega_miolo_home_page.xml",{});
		}
	},

	carregaTemplatePost:function(template, postObjectArray, ehUmNovoPost){
		return $.holy(template, {"jsonArrayPost" : postObjectArray,"sucesso" : ehUmNovoPost});
	},

	adicionaBotaoVerMais:function(postObjectArray){
		readMoreButton.addButtonEvent($(".list_stories_footer_call"),postObjectArray);
	},

	criaNovoPost:function() {
		if (($("#form_input_title").val() == "")
				|| (CKEDITOR.instances.form_input_content.getData() == "")) {
			$("li.warning").css("display", "list-item");
		} else {

			var post = {
				"title" : $("#form_input_title").val(),
				"content" : CKEDITOR.instances.form_input_content.getData(),
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
	}

};