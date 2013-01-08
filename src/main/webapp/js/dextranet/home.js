dextranet = {};
dextranet.home = {};

dextranet.home.abrePaginaNovoPost=function() {
	$.holy("../template/abre_pagina_novo_post.xml", {});
	setActiveMenuLateral("#sidebar_left_new_post");
};

dextranet.home.carregaDados = function() {
	dextranet.home.carregaDadosHome(false);
}

dextranet.home.carregaDadosAposInclusaoDeUmPost = function() {
	dextranet.home.carregaDadosHome(true);
}

dextranet.home.carregaDadosHome=function(ehUmNovoPost){
	dextranet.home.configuraLoading();
	consulta.setText("");
	dextranet.home.carregueOsTemplates();
	dextranet.post.listarPosts("", ehUmNovoPost, 0);
	$(document).delay(1000);
};

dextranet.home.carregueOsTemplates = function() {
	$.holy("../template/carrega_menu_principal.xml", {});
	$.holy("../template/carrega_menu_lateral.xml", {});

	$.ajax( {
		type : "GET",
		url : "/s/usuario",
		success : function(usuario) {
			$.holy("../template/carrega_dados_usuario.xml", usuario);
		}
	});
};

dextranet.home.setActiveMenuLateral = function(id) {
	$("#sidebar_left_home").attr("class", "");
	$("#sidebar_left_new_post").attr("class", "");
	$("#sidebar_left_category").attr("class", "");
	$("#sidebar_left_profile").attr("class", "");
	$("#sidebar_left_team").attr("class", "");
	$(id).attr("class", "active");
}

dextranet.home.abrePaginaNovoPost = function() {
	$.holy("../template/abre_pagina_novo_post.xml", {});
	dextranet.home.setActiveMenuLateral("#sidebar_left_new_post");
}

dextranet.home.abrePaginaCategoria = function() {
	$.holy("../template/abre_pagina_categoria.xml", {});
	dextranet.home.setActiveMenuLateral("#sidebar_left_category");
}

dextranet.home.abrePaginaPerfil = function() {
	$.holy("../template/abre_pagina_perfil.xml", {});
	dextranet.home.setActiveMenuLateral("#sidebar_left_profile");
}

dextranet.home.abrePaginaEquipe = function() {
	$.holy("../template/abre_pagina_equipe.xml", {});
	dextranet.home.setActiveMenuLateral("#sidebar_left_team");
}

dextranet.home.deslogarUsuario = function() {
	$.holy("../template/pagina_login.xml", {});
}

dextranet.home.abrirOuFecharTelaUsuario = function() {
	if ($('#box_user_profile').is(':visible')) {
		$("#box_user_profile").css("display", "none");
		$("#box_user_info .shape_arrow_down").css("display", "none");
		$("#box_user_info .shape_arrow_right").css("display", "inline-block");
	} else {
		$("#box_user_profile").css("display", "block");
		$("#box_user_info .shape_arrow_down").css("display", "inline-block");
		$("#box_user_info .shape_arrow_right").css("display", "none");

		// se a tela de notificações estiver visível, a esconde
		if ($('#box_user_notifications_full').is(':visible'))
			$("#box_user_notifications_full").css("display", "none");
	}
}

dextranet.home.abrirOuFecharTelaNotificacoes = function() {
	// mostra ou oculta a tela de notificações
	if ($('#box_user_notifications_full').is(':visible'))
		$("#box_user_notifications_full").css("display", "none");
	else {
		$("#box_user_notifications_full").css("display", "block");

		// se a tela de inf. do usuário estiver vísivel, a esconde
		if ($('#box_user_profile').is(':visible')) {
			$("#box_user_profile").css("display", "none");
			$("#box_user_info .shape_arrow_down").css("display", "none");
			$("#box_user_info .shape_arrow_right").css("display",
					"inline-block");
		}
	}
}

dextranet.home.configuraLoading = function() {
	$.loading( {
		text : 'Carregando...',
		overlay : '#23557E',
		opacity : '60'
	});
}

dextranet.home.setLoading = function() {
	jQuery.ajaxSetup( {
		loading : true
	});
}
