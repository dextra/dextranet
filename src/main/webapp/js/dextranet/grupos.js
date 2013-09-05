dextranet.grupos = {

	usuariosSelecionados : [],

	emailsExternos : [],

	usuariosInicial : [],

	usuariosAdd : [],

	usuariosRemover : [],

	googleGrupos : [],

	templateServicosGrupo : null,

	templateGoogleGrupo : null,

	servicosEdicao : null,

	servicos : [],

	novo : function() {
		// Lista Serviços
		var servicos = $.ajax({
			type : "GET",
			url : "/s/servico",
			dataType : "json"
		});

		$.when(servicos).done(function(xhrServicos) {
			// xhrServicos
			$.holy("../template/dinamico/grupo/novo_grupo.xml", {
				servicos : xhrServicos
			});
			dextranet.ativaMenu("sidebar_left_grupos");
		});
	},

	listar : function() {
		// Autocomplete Pessoas
		var pessoas = $.ajax({
			url : "/s/usuario",
			dataType : "json"
		});
		// Template Servicos
		var templateServicosGrupo = $
				.get('../template/dinamico/servicoGrupo/templaceServico.html');
		// Template GoogleGrupoExterno form
		var templateGGrupos= $
				.get('../template/dinamico/servicoGrupo/form_GoogleGrupo.html');
		// Grupos
		var grupos = $.ajax({
			type : "GET",
			url : "/s/grupo",
			dataType : "json",
		});
		// Serviços
		var servicos = $.ajax({
			url : "/s/servico",
			dataType : "json"
		});

		var elementos = [];

		$.when(pessoas, grupos, templateGGrupos, servicos, templateServicosGrupo)
			.done(function(xhrPessoas, xhrGrupos, xhrTemplateGGrupos, xhrServicos, xhrTemplateServicosGrupo) {

			// xhrPessoas
			$.map(xhrPessoas[0],function(item) {
				if (item.nome != null) {
					elementos.push({username 	: item.username,
									label 		: stringUtils.resolveCaracteresHTML(item.nome),
									id 			: item.id});
				}
			});
			dextranet.pessoas = elementos;

			// xhrTemplateServicosGrupo
			dextranet.grupos.templateServicosGrupo = xhrTemplateServicosGrupo[0];

			// xhrGrupos
			$.holy("../template/dinamico/grupo/lista_grupos.xml",{grupos : xhrGrupos[0]	});
			dextranet.ativaMenu("sidebar_left_grupos");

			// xhrTemplates
			dextranet.grupos.templateGoogleGrupo = xhrTemplateGGrupos[0];

			//xhrServicos
			dextranet.grupos.servicos = xhrServicos[0];
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
		$($('.servicos-content').find('.campoObrigatorio')).each(function() {
			if (!this.value) {
				campoServico = false;
			} else if ($.inArray(this.value + "@dextra-sw.com", dextranet.servicos.listaGoogleGrupos) != -1) {
				$("p#msg-erro").html("O grupo digitado já existe. Solicite a <a href='"	+ $.i18n.messages.url_redmine + "' target='_blank'>remoção</a> do grupo antes de criá-lo.")
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
					dextranet.servicos.listaGoogleGrupos = []
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

	editar : function(grupoId, dono) {
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
			$.holy("../template/dinamico/grupo/editar_grupo.xml", {	grupo : xhrGrupo[0],
																	servicos : xhrServicos[0],
																	dono : dono
																   });
		});
	},

	remover : function(grupoId) {
		if (confirm($.i18n.messages.msg_excluirGrupo)) {
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

	atualizar : function(grupoId, novosIntegrantes, infoBasica, integrantesGruposExternosRemover) {
		var campoServico = true;
		$($('.servicos-content').find('.campoObrigatorio:enabled')).each(function() {
			if (!this.value) {
				campoServico = false;
			} else if ($.inArray(this.value + "@dextra-sw.com",	dextranet.servicos.listaGoogleGrupos) != -1) {
				$("p#msg-erro").html("O grupo digitado já existe. Solicite a <a href='"	+ $.i18n.messages.url_redmine + "' target='_blank'>remoção</a> do grupo antes de criá-lo.")
				campoServico = false;
			}
		});

		if ($('#frmGrupo').validate() && campoServico) {
			var usuarios = dextranet.grupos.usuariosSelecionados;
			var grupo = form2js("frmGrupo");
			var atualizar = true;
			grupo.servico = [];

			grupo.usuarios = usuarios;
			if (infoBasica && !novosIntegrantes && !$('.novaTab')) {
				// Objeto de TODOS existentes
				var todosServicos = [];
				$('.formServico:not(.ggrupoExterno)').each(function() {
					formId = $(this).attr("id");
					idServico = $(this).find($("input[name='idServico']")).val();
					emailGrupo = $(this).find($("input[name='emailGrupoEdicao']")).val();
					todosServicos.push({idServico : idServico,
										emailGrupo : emailGrupo});
				});
			} else {
				dextranet.grupos.provisionarServicos(atualizar,	novosIntegrantes);
			}

			//Caso tenha integrantes para remover provisionamento
			if (dextranet.grupos.usuariosRemover.length != 0 || integrantesGruposExternosRemover.length != 0) {
				dextranet.grupos.removerProvisionamento(integrantesGruposExternosRemover);
			}

			$(dextranet.grupos.emailsExternos).each(function() {
				grupo.servico.push({emailGrupo : this.grupoEmail,
									idServico : this.idServico,
									emailsExternos : JSON.stringify(this.emails),
									usuarioJSONs : dextranet.grupos.usuariosSelecionados,
									id : this.idServicoGrupo});
			});

			$.ajax({
				type : "PUT",
				url : "/s/grupo/" + grupoId,
				contentType : "application/json",
				dataType : "json",
				data : JSON.stringify(grupo),
				success : function(data) {
					$('.message').message($.i18n.messages.usuario_mensagem_edicao_sucesso, 'success', true);
					dextranet.grupos.usuariosSelecionados = null;
					dextranet.servicos.listaGoogleGrupos = []
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

			$(dextranet.grupos.emailsExternos).each(function() {
				servicos.push({ emailGrupo : this.grupoEmail,
								idServico : this.idServico,
								emailsExternos : JSON.stringify(this.emails),
								usuarioJSONs : dextranet.grupos.usuariosSelecionados});
			});

			if (servicos.length != 0) {
				for (i = 0; i < servicos.length; i++) {
					servicos[i].usuarioJSONs = dextranet.grupos.usuariosSelecionados;
				}
				//Provisiona
				dextranet.grupos.provisionarGGrupo(servicos)
			}
			return servicos;
		}

		// Servicos/gGrupo NOVOS ao editar
		$('.formServico.ggrupoExterno').each(function() {
			var formId = $(this).attr("id");
			var idServico = $(this).find("input[name='idServico']").val();
			var grupoEmailExterno = $(this).find("input[name='grupoEmailExterno']").val();
			var emailsExternos = [];

			// Grupo novo; emails externos
			if ($(this).find($("input[name='grupoEmailExterno']:enabled"))) {
				emailGrupo = $(this).find("input[name='emailGrupoEdicao']").val();
				$(this).find('p#emailExterno').each(function() {
					emailsExternos.push($(this).attr('email'));
				});
			}
			servicosNovosEdicao.push({	idServico 			: idServico,
						                grupoEmailExterno 	: grupoEmailExterno,
						                emailsExternos 		: emailsExternos});
		});

		if (servicosNovosEdicao.length != 0) {
			for (i = 0; i < servicosNovosEdicao.length; i++) {
				servicosNovosEdicao[i].usuarioJSONs = dextranet.grupos.usuariosSelecionados;
			}
		}

		// Servicos/gGrupo EXISTENTES ao editar
		$('.formServico:not(.ggrupoExterno)').each(	function() {
			var formId = $(this).attr("id");
			var idServico = $(this).find($("input[name='idServico']")).val();
			var emailsExternos = [];
			var emailGrupo;

			// Grupo existente
			if ($(this).find($("input[name='grupoEmailExterno']")).is('[disabled="disabled"]')) {
				emailGrupo = $(this).find("input[name='emailGrupoEdicao']").val();
				$(this).find('p#emailExterno').each(function() {
					emailsExternos.push($(this).attr('email'));
				});
			}

			if(dextranet.grupos.usuariosAdd.length != 0 || emailsExternos.length !=0){
				todosServicos.push({idServico 		: idServico,
									emailGrupo 		: emailGrupo,
									usuarioJSONs 	: dextranet.grupos.usuariosAdd,
									emailsExternos 	: JSON.stringify(emailsExternos)});
			}
		});

		// Novos serviços apenas
		if (todosServicos.length != 0 && atualizar) {
			//Provisiona
			if(servicosNovosEdicao.length != 0){
				$(servicosNovosEdicao).each(function() {
					todosServicos.push({ emailGrupo 	: this.grupoEmailExterno,
   										 idServico 		: this.idServico,
										 emailsExternos : JSON.stringify(this.emailsExternos),
										 usuarioJSONs 	: dextranet.grupos.usuariosSelecionados});
				});
			}

			dextranet.grupos.provisionarGGrupo(todosServicos);
		}
		return servicosNovosEdicao;
	},

	//Remove integrantes dos servicos
	removerProvisionamento : function(integrantesGruposExternosRemover) {
		var servicos = [];

		// Servicos EXISTENTES; emails internos
		if(dextranet.grupos.usuariosRemover.length != 0){
			$("#listaServicos").find("form.formServico:not(.novaTab, .ggrupoExterno)").each(function() {
				emailGrupo = $(this).find($("input[name='emailGrupoEdicao']")).val();
				servicos.push({	emailGrupo   : emailGrupo,
								usuarioJSONs : dextranet.grupos.usuariosRemover	});
			});
		}

		//Emails externos
		$(integrantesGruposExternosRemover).each(function() {
			servicos.push({ emailGrupo : this.emailGrupo,
							emailsExternos : this.email});
		});

		if (servicos.length != 0) {
			dextranet.grupos.removerProvisionamentoGGrupo(servicos);
		}
	},

	diffArrayObjetos : function(array1, array2){
		var bIds = {};
		var resultado;
		array1.forEach(function(obj){
		    bIds[obj.id] = obj;
		});

		resultado = array2.filter(function(obj){
		    return !(obj.id in bIds);
		});
		return resultado;
	},

	provisionarGGrupo : function(servicos){
		$.ajax({
			type : "PUT",
			url : "/s/grupo/googlegrupos/",
			contentType : "application/json",
			dataType : "json",
			async : false,
			data : JSON.stringify(servicos)
		});
	},

	removerProvisionamentoGGrupo : function(servicos){
		$.ajax({
			type : "PUT",
			url : "/s/grupo/googlegrupos/removerIntegrantes/",
			contentType : "application/json",
			dataType : "json",
			async : false,
			data : JSON.stringify(servicos),
		});
		dextranet.grupos.usuariosRemover = [];
	}
}
