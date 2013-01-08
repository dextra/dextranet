
dextranet.post = {};

//FIXME carregaDadosHomePage() -> usar hide() do javascript
//FIXME tirar alert do "Preencha todos os campos"
//FIXME postObject

dextranet.post.fazPesquisa=function() {
	var cons = $('#form_search_input').val();
	var ehUmNovoPost = false;
	var pagina = 0;

	consulta.setText(cons);

	if(consulta.getText() != ""){
		$("#form_gif_loading").css("display", "inline");
		dextranet.post.listarPosts(consulta.getText(), ehUmNovoPost, pagina);
	}
	return false;
};

dextranet.post.listarPosts=function(query, ehUmNovoPost, pagina) {
	var url = "/s/post";
	var quantidadePostsSolicitados = "20";
	var template = "../template/post.xml";

	$("#form_gif_loading").css("display", "inline");

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
};

dextranet.post.carregaTemplatePost=function(template, postObjectArray, ehUmNovoPost){
	return $.holy(template, {"jsonArrayPost" : postObjectArray,"sucesso" : ehUmNovoPost});
};

dextranet.post.adicionaBotaoVerMais=function(postObjectArray){
	readMoreButton.addButtonEvent($(".list_stories_footer_call"),postObjectArray);
};

dextranet.post.criaNovoPost=function() {
	if (($("#form_input_title").val() == "")
			|| (CKEDITOR.instances.form_input_content.getData() == "")) {
		alert("Preencha todos os campos.");
	} else {
		$("#form_post_submit").css("display", "none");
		$("#form_gif_loading").css("display", "inline");

		var post = {
			"title" : $("#form_input_title").val(),
			"content" : CKEDITOR.instances.form_input_content.getData(),
			"author" : $("#user_login").text()
		}

		$.ajax( {
			type : "POST",
			url : "/s/post",
			data : post,
			success : function() {
				dextranet.home.carregaDadosAposInclusaoDeUmPost();
			}
		});
	}
	return false;
};

