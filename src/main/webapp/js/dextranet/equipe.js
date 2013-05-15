dextranet.equipe = {

		listar : function() {
			$.ajax( {
				type : "GET",
				url : "/s/usuario",
				dataType : "json",
				success : function(usuarios) {
					$.holy("../template/dinamico/equipe/lista_equipe.xml", { usuarios : usuarios, gravatar : dextranet.gravatarUrl });
					dextranet.ativaMenu("sidebar_left_team");
				},
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
			});
		},

		listarPelaInicial : function (inicial) {
			var colaboradores = $('.list-team span.nome');
			$('.list-team-empty').show();
			$(colaboradores).each( function () {
				var nomeDoColaborador = $(this).text();
				var inicialDoNomeDoColaborador = nomeDoColaborador.substring(0, 1);
				if (inicialDoNomeDoColaborador.toLowerCase() == inicial.toLowerCase()) {
					$(this).closest('.colaborador').show();
					$('.list-team-empty').hide();
				} else {
					$(this).closest('.colaborador').hide();
				}
			});
		}


}