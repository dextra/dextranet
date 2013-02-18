dextranet.perfil = {

		existeCampoIncompleto : function(dados) {
			return (dados.name == "" || dados.phoneMobile == "");
		},

		init : function(id) {
			dextranet.perfil.obter(id, true);
		},

		obter : function(id, validar) {
			$.ajax( {
				type : "GET",
				url : "/s/perfil/obter/" + id,
				success : function(dados) {
					if (validar !== true || dextranet.perfil.existeCampoIncompleto(dados)) {
						$.holy("../template/dinamico/abre_pagina_perfil.xml", dados);
					}
				}
			});
		},

		inserir : function() {

			//TODO Validar dados preenchidos via JS aqui..

			var validar = true;

			$('.required').each(function (){
				if(this.value == ""){

					$("#container_mensagem").empty();
					$.holy("../template/estatico/menssagem_falta_campo_required.xml", {});
					validar = false;
					return;
				}
			});

			if(validar){
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
						phoneMobile : celularVal,

					};

				$.ajax( {
					type : "POST",
					url : "/s/perfil/inserir",
					data : dados,
					success : function(dados) {

						console.info(dados);
						$("#container_mensagem").empty();
						$.holy("../template/estatico/mensagem_sucesso.xml", {});
					}
				});
			}
		}
}
