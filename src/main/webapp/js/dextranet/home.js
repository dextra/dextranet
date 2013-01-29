dextranet.home = {

	inicializa : function() {
		consulta.setText("");
		dextranet.paginacao.resetPaginacao();
		dextranet.home.carregueOsTemplates();
		dextranet.usuario.autenticacao();
	},

	carregueOsTemplates : function() {
		$.holy("../template/estatico/carrega_menu_principal.xml", {});
		$.holy("../template/estatico/carrega_menu_lateral.xml", {});
		$.holy("../template/dinamico/carrega_miolo_home_page.xml", {});
	},

	setActiveMenuLateral : function(id) {
		$("#sidebar_left_home").attr("class", "");
		$("#sidebar_left_new_post").attr("class", "");
		$("#sidebar_left_category").attr("class", "");
		$("#sidebar_left_profile").attr("class", "");
		$("#sidebar_left_team").attr("class", "");
		$(id).attr("class", "active");
	},

	abrePopUpNovoPost : function() {
		if (dextranet.home.EhVisivel('.sidebar_show_right')){
			$(".sidebar_show_right").css("display","none");
			dextranet.home.setActiveMenuLateral("#sidebar_left_home");
		} else {
			dextranet.home.limparAvisoPreenchaCampos();
			$(".sidebar_show_right").css("display","block");
			dextranet.home.setActiveMenuLateral("#sidebar_left_new_post");
		}
	},

	abrePaginaCategoria : function() {
		$.holy("../template/dinamico/abre_pagina_categoria.xml", {});
		dextranet.home.setActiveMenuLateral("#sidebar_left_category");
	},

	abrePaginaPerfil : function() {
		if(dextranet.usuario.nickName == "webster.lima") {	
			$.holy("../template/dinamico/abre_pagina_novo_banner.xml", {});
			dextranet.home.setActiveMenuLateral("#sidebar_left_profile");
		}
	},

	abrePaginaEquipe : function() {
		$.holy("../template/dinamico/abre_pagina_equipe.xml", {});
		dextranet.home.setActiveMenuLateral("#sidebar_left_team");
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
		if (dextranet.home.EhVisivel('#box_user_notifications_full')) {
			dextranet.home.someODisplayDeNotificacao();
		} else {
			dextranet.home.apareceODisplayDeNotificacao();
		}
	},

	EhVisivel : function(element){
		return $(element).is(':visible');
	},

	someODisplayDeNotificacao : function() {
		$("#box_user_notifications_full").css("display", "none");
	},

	apareceODisplayDeNotificacao : function() {
		$("#box_user_notifications_full").css("display", "block");

		if ($('#box_user_profile').is(':visible')) {
			dextranet.home.abrirTelaUsuario();
		}
	},

	limparAvisoPreenchaCampos : function() {
		if(dextranet.home.EhVisivel("div.container_message_warning")) {
			$("div.container_message_warning").empty();
			$("div.container_message_warning").removeClass("container_message_warning");
		}
	}
};
