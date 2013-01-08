
dextranet = {
	configuraLoading : function() {
		$.loading( {
			text : 'Carregando...',
			overlay : '#23557E',
			opacity : '60'
		});

        jQuery.ajaxSetup( {
            loading : true
        });
	},
};

dextranet.home = {

	carregaDadosSemParametro : function() {
		dextranet.home.carregaDados(false);
	},

	carregaDados : function(novoPost) {
		dextranet.home.loadMessages();
		consulta.setText("");
		dextranet.home.carregueOsTemplates();
		busquePosts("", novoPost, 0);
	},

	loadMessages : function() {
		if (!$.i18n.loaded) {
			setTimeout("$.loadMessages()", 100);
		}
	},

	carregueOsTemplates : function() {
		$.holy("../template/carrega_menu_principal.xml", {});
		$.holy("../template/carrega_menu_lateral.xml", {});

		$.ajax( {
			type : "GET",
			url : "/s/usuario",
			success : function(usuario) {
				$.holy("../template/carrega_dados_usuario.xml", usuario);
			}
		});
	},

	setActiveMenuLateral : function(id) {
		$("#sidebar_left_home").attr("class", "");
		$("#sidebar_left_new_post").attr("class", "");
		$("#sidebar_left_category").attr("class", "");
		$("#sidebar_left_profile").attr("class", "");
		$("#sidebar_left_team").attr("class", "");
		$(id).attr("class", "active");
	},

	abrePaginaNovoPost : function() {
		$.holy("../template/abre_pagina_novo_post.xml", {});
		dextranet.home.setActiveMenuLateral("#sidebar_left_new_post");
	},

	abrePaginaCategoria : function() {
		$.holy("../template/abre_pagina_categoria.xml", {});
		dextranet.home.setActiveMenuLateral("#sidebar_left_category");
	},

	abrePaginaPerfil : function() {
		$.holy("../template/abre_pagina_perfil.xml", {});
		dextranet.home.setActiveMenuLateral("#sidebar_left_profile");
	},

	abrePaginaEquipe : function() {
		$.holy("../template/abre_pagina_equipe.xml", {});
		dextranet.home.setActiveMenuLateral("#sidebar_left_team");
	},

	deslogarUsuario : function() {
		$.holy("../template/pagina_login.xml", {});
	},

	abrirOuFecharTelaUsuario : function() {
		if (dextranet.home.EhVisivel('#box_user_profile')){
			dextranet.home.abrirTelaUsuario();
		} else {
			dextranet.home.fecharTelaUsuario();
		}
	},

	abrirTelaUsuario : function() {
		$("#box_user_profile").css("display", "none");
		$("#box_user_info .shape_arrow_down").css("display", "none");
		$("#box_user_info .shape_arrow_right").css("display", "inline-block");
	},

	fecharTelaUsuario : function() {
		$("#box_user_profile").css("display", "block");
		$("#box_user_info .shape_arrow_down").css("display", "inline-block");
		$("#box_user_info .shape_arrow_right").css("display", "none");

		if ($('#box_user_notifications_full').is(':visible'))
			$("#box_user_notifications_full").css("display", "none");
	},

	abrirOuFecharTelaNotificacoes : function() {
		if (dextranet.home.EhVisivel('#box_user_notifications_full')){
			dextranet.home.someODisplayDeNotificacao();
			}
		else {
			dextranet.home.apareceODisplayDeNotificacao();
		}
	},

	EhVisivel : function(element){
		return $(element).is(':visible');
	},

	someODisplayDeNotificacao : function(){
		$("#box_user_notifications_full").css("display", "none");
	},

	apareceODisplayDeNotificacao : function(){
		$("#box_user_notifications_full").css("display", "block");

		if ($('#box_user_profile').is(':visible')) {
			dextranet.home.abrirTelaUsuario();
		}
	}

};

