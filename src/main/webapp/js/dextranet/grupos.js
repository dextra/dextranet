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
				success : function(usuarios) {
					$.holy("../template/dinamico/grupo/lista_grupos.xml", { usuarios : usuarios, gravatar : dextranet.gravatarUrl });
					dextranet.ativaMenu("sidebar_left_grupos");
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
				var nomeDoColaborador = $(this).text();
				var inicialDoNomeDoColaborador = nomeDoColaborador.substring(0, 1);
				if (inicialDoNomeDoColaborador.toLowerCase() == inicial.toLowerCase()) {
					$(this).closest('.colaborador').show();
					$('.list-grupo-empty').hide();
				} else {
					$(this).closest('.colaborador').hide();
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