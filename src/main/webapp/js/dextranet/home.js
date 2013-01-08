dextranet = {};
dextranet.home = {};
dextranet.home.busquePosts = function busquePosts(query, ehUmNovoPost, pagina){

	$("#form_gif_loading").css("display", "inline");
	var tipo = 'GET';
	var url = "/s/post";
	var quantidadePostsSolicitados = "20";
	var template = "../template/post.xml";

	$.ajax( {
		type : tipo,
		url : url,
		data : "max-results=" + quantidadePostsSolicitados + "&page=" + pagina + "&q=" + query,
		success : function(posts) {
			if(posts.length > 0){
				var postObjectArray = postObject.getpostObjectArrayFromPostJsonArray(posts);
				$(postObjectArray).each(function(){
					this.setHiddenText();
				});
				$.when($.holy(template, {"jsonArrayPost" : postObjectArray,"sucesso" : ehUmNovoPost})).done(
						function(){
							readMoreButton.addButtonEvent($(".list_stories_footer_call"),postObjectArray);
						}
				);
			}
		}
	});
	if (pagina == 0){
		$.holy("../template/carrega_miolo_home_page.xml",{});
	}
}