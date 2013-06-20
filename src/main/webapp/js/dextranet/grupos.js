dextranet.grupos = {

		novo : function() {
			$.ajax( {
				type : "GET",
				url : "/s/usuario",
				dataType : "json",
				success : function(usuarios) {
					$.holy("../template/dinamico/grupo/grupo.xml", { usuarios : usuarios});
					dextranet.ativaMenu("sidebar_left_grupos");
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		},

		listar : function() {
			$.ajax( {
				type : "GET",
				url : "/s/usuario",
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

//				$.ajax( {
//					type : "PUT",
//					url : "/s/usuario/" + dextranet.usuario.logado.id,
//					data : form2js("frmGrupo"),
//					success : function(data) {
//						$('.message').message($.i18n.messages.usuario_mensagem_edicao_sucesso, 'success', true);
//						dextranet.usuario.editar();
//						console.info(data);
//					},
//	    			error: function(jqXHR, textStatus, errorThrown) {
//	    				dextranet.processaErroNaRequisicao(jqXHR);
//	    			}
//				});
			} else {
				$('.message').message($.i18n.messages.erro_campos_obrigatorios, 'error', true);
			}
		}


}