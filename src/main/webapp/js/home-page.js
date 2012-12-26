function carregaDadosHomePage() {
	carregueOsTemplates();
	busquePosts(0, "", false);
	$(document).delay(1000);
}

function carregaDadosHomePageAposInclusao() {
	carregueOsTemplates();
	busquePosts(0, "", true);
	$(document).delay(1000);
}

function carregueOsTemplates() {
	$.holy("../template/carrega-menu-principal.xml", {});
	$.holy("../template/carrega-menu-lateral.xml", {});
	$.holy("../template/carrega-dados-usuario.xml", {});
}

function busquePosts(menorPostSolicitado, query, ehUmNovoPost) {
	var tipo = 'GET';
	var url = "/s/post";
	var quantidadePostsRecuperados = "20";

	$.ajax( {
		type : tipo,
		url : url,
		data : "max-results=" + quantidadePostsRecuperados + "&q=" + query,
		success : function(posts) {
			console.log(posts);
			$.holy("../template/carrega-miolo-home-page.xml", {
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