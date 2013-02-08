dextranet.curtir = {
		
		curte : function(botao) {
			var id = $(botao).attr("id");
			if (dextranet.curtir.voceJaCurtiu(botao)) {
				var classe = $(botao).attr("class").substring(11);
				var ehPost = true;
				if(classe == "comentario ttip") {
					ehPost = false;
				}
	
				$.ajax( {
					type : 'POST',
					url : '/s/curtida',
					data : {
						"usuario" : dextranet.usuario.nickName,
						"id" : id,
						"isPost" : ehPost
						},
					success : function() {
						if(ehPost) {
							dextranet.post.atualizaPost(id);
						} else {
							dextranet.comment.atualizaComentario(id);
						} 
					}
				});
			}
		},
		
		voceJaCurtiu : function(tip) {
			var curtidas = $(tip).attr("original-title");
			if (curtidas.indexOf("você") == -1) {
				return true;
			} else {
				return false;
			}
		},
		
		replaceDoTipsy : function(conteudo) {
			conteudo = conteudo.replace(/ /,'');
			conteudo = conteudo.replace(/ /g,'<br>');
			return conteudo.replace(dextranet.usuario.nickName, 'você');
		}
}