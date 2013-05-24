dextranet.microblog = {

	listar : function() {
		$.ajax( {
			type : "GET",
			url : "/s/microblog/post",
			contentType : dextranet.application_json,
			success : function(microposts) {
				$.holy("../template/dinamico/microblog/lista_microposts.xml", { posts : microposts,
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
					dextranet.microblog.listar();
				},
				error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		} else {
			$('.message').message($.i18n.messages.erro_campos_obrigatorios, 'error', true);
		}
		return false;
	}
}

//(function($) {
//
//    $('#new_tweet').submit(function() {
//        var text = $('#tweet_text').val();
//        $('.tweets').prepend('<li>' + text + '</li>');
//
//        var post = {};
//        post.text = text;
//
//        $.post("/s/microblog/post", post).done(function(data) {
//            console.log($.get("/s/microblog/post"));
//        });
//        return false;
//    });
//
//}(jQuery));