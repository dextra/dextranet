function carregaDadosHomePage()
{
	$.holy("../template/carrega-menu-principal.xml", {});
	$.holy("../template/carrega-menu-lateral.xml", {});
	$.holy("../template/carrega-dados-usuario.xml", {});
	$.holy("../template/carrega-campo-pesquisa.xml", {});

	$.ajax( {
		type : 'GET',
		url : "/s/posts",
		data : "max-results=20&q=",
		success : function(jsonArrayPost) {
			$.holy("../template/carrega-ultimos-posts.xml",  {"jsonArrayPost" : jsonArrayPost});
		}
	});

	$(document).delay(1000);
}