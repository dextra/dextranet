
dextranet = {};
dextranet.home = {};


dextranet.home.abrePaginaNovoPost=function() {
	$.holy("../template/abre_pagina_novo_post.xml", {});
	setActiveMenuLateral("#sidebar_left_new_post");
};

dextranet.home.carregaDadosHomePage=function() {
	consulta.setText("");
	dextranet.home.carregueOsTemplates();
	dextranet.post.listarPosts("", false, 0);
	$(document).delay(1000);
};

dextranet.home.carregaDadosHomePageAposInclusao=function() {
	consulta.setText("");
	dextranet.home.carregueOsTemplates();
	dextranet.post.listarPosts("", true, 0);
	$(document).delay(1000);
};


dextranet.home.carregueOsTemplates=function() {
	$.holy("../template/carrega_menu_principal.xml", {});
	$.holy("../template/carrega_menu_lateral.xml", {});

	$.ajax({
		type : "GET",
		url : "/s/usuario",
		success : function(usuario) {
			$.holy("../template/carrega_dados_usuario.xml", usuario);
		}
	});
};
