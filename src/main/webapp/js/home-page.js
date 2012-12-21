function carregaDadosHomePage(temNovoPost)
{
	$.holy("../template/carrega-menu-principal.xml", {});
	$.holy("../template/carrega-menu-lateral.xml", {});
	$.holy("../template/carrega-dados-usuario.xml", {});

	$.ajax( {
		type : 'GET',
		url : "/s/post",
		data : "max-results=20&q=",
		success : function(jsonArrayPost) {
			$.holy("../template/carrega-miolo-home-page.xml",
					{
						"jsonArrayPost" : jsonArrayPost,
						"sucesso" : temNovoPost
					});
		}
	});
	$(document).delay(1000);
}

function chamaMetodoCarregaDadosHomePage()
{
	carregaDadosHomePage(false);
}

function setActiveMenuLateral(id)
{
	//limpa o active atual
	$("#sidebar-left-home").attr("class","");
	$("#sidebar-left-new-post").attr("class","");
	$("#sidebar-left-category").attr("class","");
	$("#sidebar-left-profile").attr("class","");
	$("#sidebar-left-team").attr("class","");

	//adiciona o active na li desejada
	$(id).attr("class","active");
}