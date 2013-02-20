dextranet.equipe = {
//		var var tipo;
//		var var path;
//		var dados;

		// obterTodos
		get : function () {
			var tipo = "GET";
			var path = "/s/perfil/obterTodos";
			dextranet.equipe.executar(tipo,path);
		},

		//obterPorInicial
		getInicial : function (inicial) {
			var tipo = "GET";
			var path = "/s/perfil/obterPorInicial/" + inicial;
			dextranet.equipe.executar(tipo,path);
		},

		//obterPorNome
		getNome : function () {
			var tipo = "POST";
			var path = "/s/perfil/obterPorNome";
			var nameVal = $("#form_search_input_name").val();
			var dados = {
				name : nameVal
			};
			dextranet.equipe.executar(tipo,path,dados);
		},

		executar : function (tipo,path,dados) {
			var retorno = {};

			$.ajax( {
				type : tipo,
				url : path,
				data : dados,
//				async : false,
				success : function(retorno) {
					$.holy("../template/dinamico/abre_pagina_equipe.xml",{"todos" : retorno});
				}
			});
		}
}