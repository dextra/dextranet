function carregaDadosHomePage() {
	carregueOsTemplates();
	busquePosts("", false, 0);
	$(document).delay(1000);
}

function carregaDadosHomePageAposInclusao() {
	carregueOsTemplates();
	busquePosts("", true, 0);
	$(document).delay(1000);
}

function carregueOsTemplates() {
	$.holy("../template/carrega_menu_principal.xml", {});
	$.holy("../template/carrega_menu_lateral.xml", {});
	$.holy("../template/carrega_dados_usuario.xml", {});
}

function busquePosts(query, ehUmNovoPost, pagina) {
	var tipo = 'GET';
	var url = "/s/post";
	var quantidadePostsRecuperados = "20";
	var template = escolheTemplateDosPosts(pagina);

	$.ajax( {
		type : tipo,
		url : url,
		data : "max-results=" + quantidadePostsRecuperados + "&page=" + pagina + "&q=" + query,
		success : function(posts) {
			$.holy(template, {"jsonArrayPost" : posts,"sucesso" : ehUmNovoPost});
		}
	});

}

function escolheTemplateDosPosts(pagina){
	var template;

	if(pagina == 0){
		template = "../template/carrega_miolo_home_page.xml";
	}
	else {
		template = "../template/carrega_mais_posts.xml";
	}
	return template;
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