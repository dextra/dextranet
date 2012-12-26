function carregaDadosHomePage() {
	carregueOsTemplates();
	busquePosts("", false,0);
	$(document).delay(1000);
}

function carregaDadosHomePageAposInclusao() {
	carregueOsTemplates();
	busquePosts("", true,0);
	$(document).delay(1000);
}

function carregueOsTemplates() {
	$.holy("../template/carrega-menu-principal.xml", {});
	$.holy("../template/carrega-menu-lateral.xml", {});
	$.holy("../template/carrega-dados-usuario.xml", {});
}

function busquePosts(query, ehUmNovoPost, pagina) {
	var tipo = 'GET';
	var url = "/s/post";
	var quantidadePostsRecuperados = "20";

	$.ajax( {
		type : tipo,
		url : url,
		data : "max-results=" + quantidadePostsRecuperados + "&page=" + pagina +"&q=" + query,
		success : function(posts) {
			//console.log(posts);
			$.holy("../template/carrega-miolo-home-page.xml", {
				"jsonArrayPost" : posts,
				"sucesso" : ehUmNovoPost
			});
		}
	});
}

function maisPosts(query, ehUmNovoPost, pagina){

	var tipo = 'GET';
	var url = "/s/post";
	var quantidadePostsRecuperados = "20";

	$.ajax( {
		type : tipo,
		url : url,
		data : "max-results=" + quantidadePostsRecuperados + "&page=" + pagina +"&q=" + query,
		success : function(posts) {
			//console.log(posts);
		$.holy("../template/carrega-mais-posts.xml", {
				"jsonArrayPost" : posts,
				"sucesso" : ehUmNovoPost
			});
		}
	});

}

function setActiveMenuLateral(id) {
	// limpa o active atual
	$("#sidebar-left-home").attr("class", "");
	$("#sidebar-left-new-post").attr("class", "");
	$("#sidebar-left-category").attr("class", "");
	$("#sidebar-left-profile").attr("class", "");
	$("#sidebar-left-team").attr("class", "");

	// adiciona o active na li desejada
	$(id).attr("class", "active");
}
function carregaOpcaoVerMais() {
	$(".list-stories-lead").readmore( {
		substr_len : 300,
		substr_lines : 5,
		split_word : true,
		ellipses : '...'
	});
}