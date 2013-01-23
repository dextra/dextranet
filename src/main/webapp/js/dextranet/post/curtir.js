dextranet.curtir = {
		
		curte : function() {
			$(".linkCurtir").click(function() {
				var id = $(this).attr("id").substring(9);
				if (dextranet.curtir.voceJaCurtiu(this)) {
					var classe = $(this).attr("class").substring(11);
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
			});
		},
		
		voceJaCurtiu : function(tip) {
			var curtidas = $(tip).attr("original-title");
			if (curtidas.indexOf("você") == -1) {
				return true;
			} else {
				return false;
			}
		}
}