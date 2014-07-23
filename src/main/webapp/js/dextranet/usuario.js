dextranet.usuario = {

		logado : null,

		carregaLogout : function() {
			$.ajax( {
				type : "GET",
				url : "/s/usuario/url-logout",
				dataType : "json",
				success : function(url) {
					$.holy("../template/dinamico/acoes_usuario.xml", { url : url.url });
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		},

		exibirUsuarioLogado : function() {
			$.ajax( {
				type : "GET",
				url : "/s/usuario/logado",
				dataType : "json",
				success : function(usuario) {
					dextranet.usuario.logado = usuario;
					$.holy("../template/dinamico/usuario/usuario_logado.xml", { usuario : dextranet.usuario.logado, gravatar : dextranet.gravatarUrl });
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		},
		
		desativar : function(idUser) {
			$.ajax( {
				type : "PUT",
				url : "/s/usuario/" + idUser,
				data : {ativo : false},
				success : function() {
					$('.message').message($.i18n.usuario_mensagem_desativado_sucesso, 'success', true);
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});	
		},
		
		ativar : function(idUser) {
			$.ajax( {
				type : "PUT",
				url : "/s/usuario/" + idUser,
				data : {ativo : true},
				success : function() {
					$('.message').message($.i18n.usuario_mensagem_ativado_sucesso, 'success', true);
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});	
		},
		
		editar : function() {
			var carregaAreas = $.ajax({
										type : "GET",
										url: '/s/area',
										dataType: 'json' });

			var carregaUnidades = $.ajax({
										type : "GET",
										url: '/s/unidade',
										dataType: 'json' });

			$.when(carregaAreas, carregaUnidades).then( function(areas, unidades) {
				$.ajax( {
					type : "GET",
					url : "/s/usuario/logado",
					contentType : dextranet.application_json,
					success : function(usuario) {
						dextranet.usuario.logado = usuario;
						$.holy("../template/dinamico/usuario/perfil_usuario.xml", { usuario : dextranet.usuario.logado,
																					gravatar : dextranet.gravatarUrl,
																					areas : areas[0],
																					unidades : unidades[0] });
						dextranet.ativaMenu("sidebar_left_profile");
					},
	    			error: function(jqXHR, textStatus, errorThrown) {
	    				dextranet.processaErroNaRequisicao(jqXHR);
	    			}
				});
			});
		},

		salvar : function() {
			if ($('#frmPerfil').validate()) {
				$.ajax( {
					type : "PUT",
					url : "/s/usuario/" + dextranet.usuario.logado.id,
					data : form2js("frmPerfil"),
					success : function() {
						$('.message').message($.i18n.messages.usuario_mensagem_edicao_sucesso, 'success', true);
						dextranet.usuario.editar();
					},
	    			error: function(jqXHR, textStatus, errorThrown) {
	    				dextranet.processaErroNaRequisicao(jqXHR);
	    			}
				});
			} else {
				$('.message').message($.i18n.messages.erro_campos_obrigatorios, 'error', true);
			}
		}

}