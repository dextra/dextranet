dextranet.equipe = {

		listar : function() {
			$.ajax( {
				type : "GET",
				url : "/s/usuario",
				dataType : "json",
				success : function(usuarios) {
					$.holy("../template/dinamico/equipe/lista_equipe.xml", { usuarios : usuarios, gravatar : dextranet.gravatarUrl });
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		}

}