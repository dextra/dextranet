dextranet.grupos = {

	usuariosSelecionados : [],

	usuariosInicial : [],

	usuariosRemover : [],

	googleGrupos : [],

	templateGoogleGroups : null,

	templateGoogleGroupsExterno : null,

	servicosEdicao : null,

	novo : function() {
		// Lista Serviços
		var servicos = $.ajax({
			type : "GET",
			url : "/s/servico",
			dataType : "json"
		});

		$.when(servicos).done(function(xhrServicos) {
			// xhrServicos
			$.holy("../template/dinamico/grupo/novo_grupo.xml", {servicos : xhrServicos});
			dextranet.ativaMenu("sidebar_left_grupos");
		});
	},

	listar : function() {
		// Autocomplete Pessoas
		var pessoas = $.ajax({
			url : "/s/usuario",
			dataType : "json"
		});
		// Template GoogleGrupo form
		var templateGrupos = $.get('../template/dinamico/grupo/form_ggrupo.html');
		// Template GoogleGrupoExterno form
		var templateGruposExterno = $.get('../template/dinamico/grupo/form_ggrupoExterno.html');
		// Grupos
		var grupos = $.ajax({
			type : "GET",
			url : "/s/grupo",
			dataType : "json",
		});
		var elementos = [];

		$.when(pessoas, grupos, templateGrupos, templateGruposExterno).done(function(xhrPessoas, xhrGrupos, xhrTemplateGrupos, xhrTemplateGruposExterno) {
			// xhrPessoas
			$.map(xhrPessoas[0], function(item) {
                if(item.nome != null){
                	elementos.push({username : item.username,label : stringUtils.resolveCaracteresHTML(item.nome), id : item.id});
				}
			});
			dextranet.pessoas = elementos;

			// xhrGrupos
			$.holy("../template/dinamico/grupo/lista_grupos.xml",{grupos : xhrGrupos[0]});
			dextranet.ativaMenu("sidebar_left_grupos");

			// xhrTemplates
			dextranet.grupos.templateGoogleGroups = xhrTemplateGrupos[0];
			dextranet.grupos.templateGoogleGroupsExterno = xhrTemplateGruposExterno[0];
		});

	},

	listarPelaInicial : function(inicial) {
		var colaboradores = $('.list-grupo span.nome');
		$('.list-grupo-empty').show();
		$(colaboradores).each(function() {
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
		var campoServico = true;
		$($('.servicos-content').find('.campoObrigatorio')).each(function(){
			if(!this.value){
				campoServico = false;
			}else if( $.inArray(this.value + "@dextra-sw.com", dextranet.grupos.googleGrupos) != -1){
				$("p#msg-erro").html("O grupo digitado já existe. Solicite a <a href='" + $.i18n.messages.url_redmine + "' target='_blank'>remoção</a> do grupo antes de criá-lo.")
				campoServico = false;
			}
		});

		if ($('#frmGrupo').validate() && campoServico) {
			var grupo = form2js("frmGrupo");
			var atualizar = false;

			grupo.usuarios = dextranet.grupos.usuariosSelecionados;
			grupo.servico = dextranet.grupos.provisionarServicos(atualizar);

			$.ajax({
				type : "PUT",
				url : "/s/grupo/",
				contentType : "application/json",
				dataType : "json",
				data : JSON.stringify(grupo),
				success : function(data) {
					$('.message').message($.i18n.messages.usuario_mensagem_edicao_sucesso,'success', true);
					dextranet.grupos.listar();
					dextranet.grupos.usuariosSelecionados = null;
					dextranet.grupos.googleGrupos = []
				},
				error : function(jqXHR, textStatus, errorThrown) {
					dextranet.processaErroNaRequisicao(jqXHR);
				}
			});
		} else {
			$('.message').message($.i18n.messages.erro_campos_obrigatorios,
					'error', true);
		}
	},

	editar : function(grupoId) {
		// Lista Serviços
		var servicos = $.ajax({
			type : "GET",
			url : "/s/servico",
			dataType : "json",
		});

		// Grupo
		var grupo = $.ajax({
			type : "GET",
			url : "/s/grupo/" + grupoId,
			contentType : dextranet.application_json,
		});

		$.when(servicos, grupo).done(function(xhrServicos, xhrGrupo) {
			dextranet.grupos.servicosEdicao = xhrGrupo[0].servico;
			dextranet.ativaMenu("sidebar_left_grupos");

			$.holy("../template/dinamico/grupo/editar_grupo.xml", {
				grupo : xhrGrupo[0],
				servicos : xhrServicos[0]
			});
		});
	},

	remover : function(grupoId) {
		if (confirm("Grupos podem ter serviços associados. Tem certeza que deseja excluir esse grupo?")) {
			$.ajax({
				type : "DELETE",
				url : "/s/grupo/" + grupoId,
				contentType : dextranet.application_json,
				success : function() {
					$('li#' + grupoId).slideUp(function() {
						$(this).remove();
						dextranet.grupos.listar();
					});
				},
				error : function(jqXHR, textStatus, errorThrown) {
					dextranet.processaErroNaRequisicao(jqXHR);
				}
			});
		}
	},

	removerServico : function(idGrupo, idServicoGrupo) {
		$.ajax({
			type : "DELETE",
			url : "/s/grupo/googlegrupos/grupo/" + idGrupo + "/servico/" + idServicoGrupo,
			contentType : dextranet.application_json,
			success : function() {
			},
			error : function(jqXHR, textStatus, errorThrown) {
				dextranet.processaErroNaRequisicao(jqXHR);
			}
		});
	},

	atualizar : function(grupoId, novosIntegrantes, infoBasica) {
		var campoServico = true;
		$($('.servicos-content').find('.campoObrigatorio')).each(function(){
			if(!this.value){
				campoServico = false;
			}else if( $.inArray(this.value + "@dextra-sw.com", dextranet.grupos.googleGrupos) != -1){
				$("p#msg-erro").html("O grupo digitado já existe. Solicite a <a href='" + $.i18n.messages.url_redmine + "' target='_blank'>remoção</a> do grupo antes de criá-lo.")
				campoServico = false;
			}
		});

		if ($('#frmGrupo').validate() && campoServico) {
			var usuarios = dextranet.grupos.usuariosSelecionados;
			var grupo = form2js("frmGrupo");
			var atualizar = true;

			grupo.usuarios = usuarios;
			if (infoBasica && !novosIntegrantes && !$('.novaTab')) {
				// Objeto de TODOS servicos
				var todosServicos = [];
				$('.formServico').each(	function() {
					formId = $(this).attr("id");
					idServico = $(this).find($("input[name='idServico']")).val();
					emailGrupo = $(this).find($("input[name='emailGrupoEdicao']")).val();
					todosServicos.push({idServico : idServico,emailGrupo : emailGrupo});
				});
				grupo.servico = todosServicos;
			} else {
				grupo.servico = dextranet.grupos.provisionarServicos(atualizar,	novosIntegrantes);
			}

			if (dextranet.grupos.usuariosRemover.length != 0) {
				dextranet.grupos.removerProvisionamento();
			}

			$.ajax({
				type : "PUT",
				url : "/s/grupo/" + grupoId,
				contentType : "application/json",
				dataType : "json",
				data : JSON.stringify(grupo),
				success : function(data) {
					$('.message').message($.i18n.messages.usuario_mensagem_edicao_sucesso,'success', true);
					dextranet.grupos.usuariosSelecionados = null;
					dextranet.grupos.googleGrupos = []
					dextranet.grupos.listar();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					dextranet.processaErroNaRequisicao(jqXHR);
				}
			});
		} else {
			$('.message').message($.i18n.messages.erro_campos_obrigatorios,'error', true);
		}
	},

	provisionarServicos : function(atualizar, novosIntegrantes) {
		var servicosNovosEdicao = [];
		var todosServicos = [];

		// Serviços criados pela primeira vez
		if (!atualizar) {
			var servicos = [];
			$('.ggrupo').each(function() {
				var formId = $(this).attr("id");
				servicos.push(form2js(formId));
			});
			if (servicos.length != 0) {
				for (i = 0; i < servicos.length; i++) {
					servicos[i].usuarioJSONs = dextranet.grupos.usuariosSelecionados;
				}

				$.ajax({
					type : "PUT",
					url : "/s/grupo/googlegrupos/",
					contentType : "application/json",
					dataType : "json",
					data : JSON.stringify(servicos)
				});
			}
			return servicos;
		}

		// Servicos NOVOS ao editar
		$('.novaTab').each(function() {
			var formId = $(this).attr("id");
			servicosNovosEdicao.push(form2js(formId));
		});
		if (servicosNovosEdicao.length != 0) {
			for (i = 0; i < servicosNovosEdicao.length; i++) {
				servicosNovosEdicao[i].usuarioJSONs = dextranet.grupos.usuariosSelecionados;
			}
		}

		// TODOS servicos
		$('.formServico').each(function() {
			var formId = $(this).attr("id");
			var idServico = $(this).find($("input[name='idServico']")).val();

			if($(this).find($("input[name='emailGrupo']")).is('[disabled="disabled"]')){
				var emailGrupo = $(this).find($("input[name='emailGrupoEdicao']")).val();
			}else{
				var emailGrupo = $(this).find($("input[name='emailGrupo']")).val();
			}

			todosServicos.push({idServico : idServico, emailGrupo : emailGrupo});
		});

		// Novos serviços apenas
		if (todosServicos.length != 0 && atualizar) {
			for (i = 0; i < todosServicos.length; i++) {
				todosServicos[i].usuarioJSONs = dextranet.grupos.usuariosSelecionados;
			}
			$.ajax({
				type : "PUT",
				url : "/s/grupo/googlegrupos/",
				contentType : "application/json",
				dataType : "json",
				data : JSON.stringify(todosServicos)
			});
		}
		return servicosNovosEdicao;
	},

	removerProvisionamento : function() {
		var servicos = [];

		// Objeto das tabs de servicos
		$("#listaServicos").find("form.formServico").each(function(){
			var emailGrupo;
			if($(this).find($("input[name='emailGrupo']")).is('[disabled="disabled"]')){
				emailGrupo = $(this).find($("input[name='emailGrupoEdicao']")).val();
			}else{
				emailGrupo = $(this).find($("input[name='emailGrupo']")).val();
			}
			servicos.push({	emailGrupo : emailGrupo});
		});

		if (servicos.length != 0) {
			for (i = 0; i < servicos.length; i++) {
				servicos[i].usuarioJSONs = dextranet.grupos.usuariosRemover;
			}

			$.ajax({
				type : "PUT",
				url : "/s/grupo/googlegrupos/removerIntegrantes/",
				contentType : "application/json",
				dataType : "json",
				data : JSON.stringify(servicos),
				success : function(data) {
					dextranet.grupos.usuariosRemover = [];
				},
				error : function(jqXHR, textStatus, errorThrown) {
					dextranet.processaErroNaRequisicao(jqXHR);
				}
			});
		}
	},

	listarGGrupo : function(id_servico, tab, emailGrupo, novaTab) {
		var qtdServico = $("div.itemServico").size();

		var resultado = dextranet.grupos.templateGoogleGroups.process({
			id_servico : id_servico,
			indice : qtdServico,
			emailGrupo : emailGrupo,
			novaTab : novaTab
		});
		tab.html(resultado);

	},

	listarGGrupoExterno : function(id_servico, tab){
		var qtdServico = $("div.itemServico").size();

		var resultado = dextranet.grupos.templateGoogleGroupsExterno.process({
			id_servico : id_servico,
			indice : qtdServico,
			emailGrupo : "",
			novaTab : ""
		});
		tab.html(resultado);
	}

}