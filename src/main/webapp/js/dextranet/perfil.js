dextranet.perfil = {

		getId : function() {
			var tipo = "GET";
			var path = "/s/perfil";
			var operacao = "1";
			//var dados = {};
			dextranet.perfil.executar(tipo,path,operacao);
		},

		validar : function(id) {
			var tipo = "GET";
			var path = "/s/perfil/obter/" + id;
			var operacao = "4";
			dextranet.perfil.executar(tipo,path,operacao);
		},

		getPerfil : function(id) {
			var tipo = "GET";
			var path =  "/s/perfil/obter/" + id;
			var operacao = "2";
			//var dados = {};
			console.info(id);
			dextranet.perfil.executar(tipo,path,operacao);
		},

		upload : function() {
			var tipo = "POST";
			var path =  "/s/perfil/inserir";
			var operacao = "3";

			var nameVal = $("#nameVal").val();
			var areaVal = $("#areaVal").val();
			var unitVal = $("#unitVal").val();
			var ramalVal = $("#ramalVal").val();
			var skypeVal = $("#skypeVal").val();
			var gTalkVal = $("#gTalkVal").val();
			var residencialVal = $("#residencialVal").val();
			var celularVal = $("#celularVal").val();

			var dados = {
					name : nameVal,
					area : areaVal,
					unit : unitVal,
					branch : ramalVal,
					skype : skypeVal,
					gTalk : gTalkVal,
					phoneResidence : residencialVal,
					phoneMobile : celularVal
			};
			dextranet.perfil.executar(tipo,path,operacao,dados);
		},

		executar : function(tipo,path,operacao,dados) {

			$.ajax( {
				type : tipo,
				url : path,
				data : dados,
				success : function(retorno) {
					if(operacao == "1") {
						console.info(retorno.id);
						dextranet.perfil.getPerfil(retorno.id);
					} else if(operacao == "2") {
						console.info(retorno.userId);
						console.info(retorno.name);
						$.holy("../template/dinamico/abre_pagina_perfil.xml", retorno);
					} else if(operacao == "3") {
						dextranet.perfil.getPerfil(retorno.userId);
						//$.holy("../template/dinamico/abre_pagina_perfil.xml", retorno);
					} else if(operacao == "4") {
						if (retorno.name == "" || retorno.phoneMobile == "") {
								$.holy("../template/dinamico/abre_pagina_perfil.xml",retorno);
						}
					}
				}
			});
		}
}