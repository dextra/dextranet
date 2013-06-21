dextranet.grupos = {

		usuariosSelecionados : [],

		novo : function() {
			$.holy("../template/dinamico/grupo/grupo.xml", {});
			dextranet.ativaMenu("sidebar_left_grupos");
		},

		listar : function() {
			$.ajax( {
				type : "GET",
				url : "/s/grupo",
				dataType : "json",
				success : function(grupos) {
					$.holy("../template/dinamico/grupo/lista_grupos.xml", { grupos : grupos});
					dextranet.ativaMenu("sidebar_left_team");
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		},

		listarPelaInicial : function (inicial) {
			var colaboradores = $('.list-grupo span.nome');
			$('.list-grupo-empty').show();
			$(colaboradores).each( function () {
				var nomeDoGrupo = $(this).text();
				var inicialDoNomeDoGrupo = nomeDoGrupo.substring(0, 1);
				if (inicialDoNomeDoGrupo.toLowerCase() == inicial.toLowerCase()) {
					$(this).closest('.grupo').show();
					$('.list-grupo-empty').hide();
				} else {
					$(this).closest('.grupo').hide();
				}
			});
		},

		salvar : function() {
			if ($('#frmGrupo').validate()) {

				var usuarios = JSON.stringify(dextranet.grupos.usuariosSelecionados);
				var grupo = form2js("frmGrupo");
				delete grupo["membros"];

				grupo.usuarios = jQuery.parseJSON(usuarios);

				$.ajax( {
					type : "PUT",
					url : "/s/grupo/",
					contentType : "application/json",
					dataType : "json",
					data : JSON.stringify(grupo),
					success : function(data) {
						$('.message').message($.i18n.messages.usuario_mensagem_edicao_sucesso, 'success', true);
						dextranet.grupos.listar();
					},
	    			error: function(jqXHR, textStatus, errorThrown) {
	    				dextranet.processaErroNaRequisicao(jqXHR);
	    			}
				});
			} else {
				$('.message').message($.i18n.messages.erro_campos_obrigatorios, 'error', true);
			}
		},

}