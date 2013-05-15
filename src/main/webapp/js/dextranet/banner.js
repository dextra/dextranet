dextranet.banner = {

		novo : function() {
			$.holy("../template/dinamico/banner/novo_banner.xml", {});
			dextranet.ativaMenu("sidebar_left_new_banner");
		},

		upload : function() {
			if ($('#frmNovoBanner').validate()) {
	            $.ajax({
	                type : "GET",
	                dataType : "json",
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
                    $('.message').message($.i18n.messages.banner_mensagem_inclusao_sucesso, 'success', true);
                    dextranet.banner.novo();
                    dextranet.banner.listarVigentes();
                },
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
            });
		},

		remover : function(id) {
            $.ajax({
                type : "DELETE",
                url : "/s/banner/" + id,
                success : function() {
                    $('.message').message($.i18n.messages.banner_mensagem_exclusao_sucesso, 'success', true);
                    dextranet.banner.listar();
                    dextranet.banner.listarVigentes();
                },
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
            });
		},

		listarVigentes : function() {
			$.ajax({
                type : "GET",
                url : "/s/banner/vigentes?max=2",
                dataType : "json",
                success : function(bannersVigentes) {
                	$.holy("../template/dinamico/banner/banners_vigentes.xml", { banners : bannersVigentes});
                	dextranet.ativaMenu("sidebar_left_new_banner");
                },
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
            });
		},

		listar : function() {
			$.ajax({
                type : "GET",
                dataType : "json",
                url : "/s/banner",
                success : function(bannersCadastrados) {
                	$.holy("../template/dinamico/banner/banners_cadastrados.xml", { banners : bannersCadastrados});
                	dextranet.ativaMenu("sidebar_left_new_banner");
                },
    			error: function(jqXHR, textStatus, errorThrown) {
    				dextranet.processaErroNaRequisicao(jqXHR);
    			}
            });
		}

}
