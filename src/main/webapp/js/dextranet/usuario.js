dextranet.usuario = {
		id : "",
		nickName : "",

		autenticacao : function() {
			$.ajax( {
				type : "GET",
				url : "/s/usuario",
				success : function(usuario) {
					dextranet.usuario.nickName = usuario.nickName;
					dextranet.usuario.id = usuario.id;

					$.holy("../template/estatico/carrega_dados_usuario.xml", usuario);
					dextranet.post.listaPost("", 0);
					dextranet.perfil.init(usuario.id);
				}
			});
		}
}