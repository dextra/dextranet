dextranet.banner = {

		novo : function() {
			$.holy("../template/dinamico/banner/novo_banner.xml", {});
		},

		upload : function() {
			if ($('#frmNovoBanner').validate()) {
	            $.ajax({
	                type : "GET",
	                url : "/s/banner/url-upload",
	                success : function(url) {
	                	dextranet.banner.salvar(url);
	                },
	    			error: function(jqXHR, textStatus, errorThrown) {
	    				dextranet.processaErroNaRequisicao(jqXHR);
	    			}
	            });	
			} else {
				$('.message').message($.i18n.messages.erro_campos_obrigatorios, 'error', true);
			}
		},

		salvar : function(url) {
            $.ajax({
                type : "POST",
                url : url.url,
                form : $('#frmNovoBanner'),
                frame : true,
                complete : function() {
                	console.info("Passou por aqui!");
                    $('.message').message($.i18n.messages.banner_mensagem_sucesso, 'success', true);
                    dextranet.banner.novo();
                },
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
            });
		}
}
