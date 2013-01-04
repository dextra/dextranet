function carregaDadosHomePage() {
	consulta.setText("");
	carregueOsTemplates();
	busquePosts("", false, 0);
	$(document).delay(1000);
}

function carregaDadosHomePageAposInclusao() {
	consulta.setText("");
	carregueOsTemplates();
	busquePosts("", true, 0);
	$(document).delay(1000);
}

function carregueOsTemplates() {
	$.holy("../template/carrega_menu_principal.xml", {});
	$.holy("../template/carrega_menu_lateral.xml", {});

	$.ajax({
		type : "GET",
		url : "/s/usuario",
		success : function(usuario) {
			$.holy("../template/carrega_dados_usuario.xml", usuario);
		}
	});

}

function busquePosts(query, ehUmNovoPost, pagina) {
	$("#form_gif_loading").css("display", "inline");
	var tipo = 'GET';
	var url = "/s/post";
	var quantidadePostsSolicitados = "20";
	var template = "../template/post.xml";
	//var template = $(this).closest('template').find('div[id="#container_post"]').val();

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

/*function busqueDocuments(query, ehUmNovoPost, pagina) {
	var tipo = 'GET';
	var url = "/s/document";
	var quantidadePostsRecuperados = "20";
	var template = "../template/post.xml";

	try{
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
	}catch(err)
	{
		console.log("erro ao solicitar nova página");
	}

	if (pagina == 0){
		$.holy("../template/carrega_miolo_home_page.xml",{});
	}
}
*/
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
	$.holy("../template/pagina_login.xml", {});
}

function abrirOuFecharTelaUsuario()
{
	//mostra ou oculta a tela de inf. do usuário
	if($('#box_user_profile').is(':visible')){
		$("#box_user_profile").css("display","none");
		$("#box_user_info .shape_arrow_down").css("display","none");
		$("#box_user_info .shape_arrow_right").css("display","inline-block");
	}
	else {
		$("#box_user_profile").css("display","block");
		$("#box_user_info .shape_arrow_down").css("display","inline-block");
		$("#box_user_info .shape_arrow_right").css("display","none");

		//se a tela de notificações estiver visível, a esconde
		if($('#box_user_notifications_full').is(':visible'))
			$("#box_user_notifications_full").css("display","none");
	}
}

function abrirOuFecharTelaNotificacoes()
{
	//mostra ou oculta a tela de notificações
	if($('#box_user_notifications_full').is(':visible'))
		$("#box_user_notifications_full").css("display","none");
	else {
			$("#box_user_notifications_full").css("display","block");

			//se a tela de inf. do usuário estiver vísivel, a esconde
			if($('#box_user_profile').is(':visible')){
				$("#box_user_profile").css("display","none");
				$("#box_user_info .shape_arrow_down").css("display","none");
				$("#box_user_info .shape_arrow_right").css("display","inline-block");
			}
	}
}

function removeExtraParagraphs(paragraphs){
	var firstPosition = 0;
	var lastPosition = paragraphs.size()-1;

	$(paragraphs[firstPosition]).remove();
	$(paragraphs[lastPosition]).remove();

	paragraphs.splice(firstPosition,1);
	paragraphs.splice(lastPosition,1);

	return paragraphs;
}
