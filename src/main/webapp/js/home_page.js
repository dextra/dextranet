function carregaDadosHomePage() {
	crieOsCookies();
	carregueOsTemplates();
	busquePosts("", false, 0);
	$(document).delay(1000);
}

function crieOsCookies()
{
	$.cookie("userName","Arara azul");
	$.cookie("userLogin", "arara");
	$.cookie("userEmail","arara@dextra-sw.com");
}

function carregaDadosHomePageAposInclusao() {
	carregueOsTemplates();
	busquePosts("", true, 0);
	$(document).delay(1000);
}

function carregueOsTemplates() {
	$.holy("../template/carrega_menu_principal.xml", {});
	$.holy("../template/carrega_menu_lateral.xml", {});
	var user = {"login" : $.cookie("userLogin"), "name" : $.cookie("userName"), "email" : $.cookie("userEmail")}
	$.holy("../template/carrega_dados_usuario.xml", user);
}

function busquePosts(query, ehUmNovoPost, pagina) {

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
				$.holy(template, {"jsonArrayPost" : posts,"sucesso" : ehUmNovoPost});
			}
		}
	});

	if (pagina == 0){
		$.holy("../template/carrega_miolo_home_page.xml",{});
	}
}

function busqueDocuments(query, ehUmNovoPost, pagina) {
	var tipo = 'GET';
	var url = "/s/document";
	var quantidadePostsRecuperados = "20";
	var template = "../template/post.xml";

	$.ajax( {
		type : tipo,
		url : url,
		data : "max-results=" + quantidadePostsRecuperados + "&page=" + pagina + "&q=" + query,
		success : function(posts) {
			if(posts.length > 0){
				$.holy(template, {"jsonArrayPost" : posts,"sucesso" : ehUmNovoPost});
			}
		}
	});

	if (pagina == 0){
		$.holy("../template/carrega_miolo_home_page.xml",{});
	}
}

function setActiveMenuLateral(id) {
	// limpa o active atual
	$("#sidebar_left_home").attr("class", "");
	$("#sidebar_left_new_post").attr("class", "");
	$("#sidebar_left_category").attr("class", "");
	$("#sidebar_left_profile").attr("class", "");
	$("#sidebar_left_team").attr("class", "");

	// adiciona o active na li desejada
	$(id).attr("class", "active");
}
function carregaOpcaoVerMais() {
	$(".list_stories_lead").readmore( {
		substr_len : 300,
		substr_lines : 5,
		split_word : true,
		ellipses : '...'
	});
}

function abrePaginaCategoria() {
	$.holy("../template/abre_pagina_categoria.xml", {});
	setActiveMenuLateral("#sidebar_left_category");
}

function abrePaginaPerfil() {
	$.holy("../template/abre_pagina_perfil.xml", {});
	setActiveMenuLateral("#sidebar_left_profile");
}

function abrePaginaEquipe() {
	$.holy("../template/abre_pagina_equipe.xml", {});
	setActiveMenuLateral("#sidebar_left_team");
}

function deslogarUsuario()
{
	$.cookie("userName","");
	$.cookie("userLogin", "");
	$.cookie("userEmail","");
	$.holy("../template/pagina_login.xml", {});
}

function abrirOuFecharTelaUsuario()
{
	if($('#box_user_profile').is(':visible')){
		$("#box_user_profile").css("display","none");
		$(".shape_arrow_down").css("display","none");
		$(".shape_arrow_right").css("display","inline-block");
	}
	else {
		$("#box_user_profile").css("display","block");
		$(".shape_arrow_down").css("display","inline-block");
		$(".shape_arrow_right").css("display","none");
	}
}

function abrirOuFecharTelaNotificacoes()
{
	if($('#box_user_notifications_full').is(':visible'))
		$("#box_user_notifications_full").css("display","none");
	else
		$("#box_user_notifications_full").css("display","block");
}
