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

		editarPerfil : function() {
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
		}
		
}