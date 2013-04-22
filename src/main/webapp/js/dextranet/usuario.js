dextranet.usuario = {

		logado : null,

		exibirUsuarioLogado : function() {
			$.ajax( {
				type : "GET",
				url : "/s/usuario/logado",
				contentType : dextranet.application_json,
				success : function(usuario) {
					dextranet.usuario.logado = usuario;
					$.holy("../template/dinamico/usuario/usuario_logado.xml", { usuario : dextranet.usuario.logado, gravatar : dextranet.gravatarUrl });
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		},

		editar : function() {
			$.ajax( {
				type : "GET",
				url : "/s/usuario/logado",
				contentType : dextranet.application_json,
				success : function(usuario) {
					dextranet.usuario.logado = usuario;
					$.holy("../template/dinamico/usuario/perfil_usuario.xml", { usuario : dextranet.usuario.logado, gravatar : dextranet.gravatarUrl });
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		},

		salvar : function() {
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
		}
		
}