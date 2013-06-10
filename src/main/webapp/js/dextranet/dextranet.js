var dextranet = {

		gravatarUrl : "http://www.gravatar.com/avatar/",

		processaErroNaRequisicao : function(jqXHR) {
			$('.message').message(jqXHR.responseText, 'error', true);
		},

		carregaMenus : function() {
			$.holy("../template/estatico/carrega_menu_principal.xml", {});
			$.holy("../template/estatico/carrega_menu_lateral.xml", {});
			$.holy("../template/estatico/carrega_miolo.xml", {});
		},

		indexacao : function() {
			var query = $('form#form_search input#form_search_input').val();
			if (!query) {
				var messageError = $.i18n.messages.post_mensagem_busca_campo_vazio;
				$('.message').message(messageError, 'warning', true);
				return false;
			}

			$.ajax({
				type : "GET",
				url : "/s/indexacao/",
				data : { 'query' : query },
				contentType : dextranet.application_json,
				success : function(posts) {
					if (posts.length == 0) {
						var messageError = $.i18n.messages.mensagem_nenhum_registro;
						$('.message').message(messageError, 'warning', true);
						return false;
					}
					clearInterval(setIntervalUtils.timerPosts);
					$.holy("../template/dinamico/post/lista_posts.xml", { paginar : false,
																		  posts : posts,
																		  gravatar : dextranet.gravatarUrl });
					dextranet.ativaMenu("sidebar_left_home");

				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});

			return false;
		},

		ativaMenu : function (menuClass) {
			$('div#sidebar_left ul#sidebar_left_menu li').removeClass("active");
			$('div#sidebar_left ul#sidebar_left_menu li#' + menuClass).addClass("active");
		}
};