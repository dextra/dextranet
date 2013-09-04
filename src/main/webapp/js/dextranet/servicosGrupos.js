dextranet.servicos = {

	listaGoogleGrupos : [],

	listarGruposEmail : function() {

		if (dextranet.servicos.listaGoogleGrupos.length == 0 && $.active == 0) {
			$.ajax({
				url : "/s/grupo/googlegrupos/listarGrupos",
				dataType : "json",
				success : function(emails) {
					dextranet.servicos.listaGoogleGrupos = emails;
				}
			});
		}
	},

	addServico : function(servico, id_servico, tabs, qtdServico) {
		//Template de servico
		var formServico = dextranet.grupos.templateServicosGrupo.process({	id_servico 		: id_servico,
																			servico 		: servico,
																			qtdServico 		: qtdServico,
																			idServicoGrupo 	: ""});
		tabs.append(formServico);
		var tab = $( "<div id='tabs-" + qtdServico + "'></div>").appendTo("div#listaServicos div:last");
		//Template do servico google grupo
		var resultado = dextranet.grupos.templateGoogleGrupo.process({	id_servico 		: id_servico,
																		indice 			: qtdServico,
																		emailGrupo		: "",
																		emailsExternos	: ""});
		tab.html(resultado);
	},

	addServicoExistente : function(servico, id_servico, emailGrupo, idServicoGrupo, emailsExternos) {
		//Template de servico
		var formServico = dextranet.grupos.templateServicosGrupo.process({	id_servico 		: id_servico,
																			servico 		: servico,
																			qtdServico 		: qtdServico,
																			idServicoGrupo 	: idServicoGrupo});
		tabs.append(formServico);
		var qtdServico = $("div.itemServico").size();
		var tab = $( "<div id='tabs-" + qtdServico + "'></div>").appendTo("div#listaServicos div:last");

		//Template do servico google grupo
		var resultado = dextranet.grupos.templateGoogleGrupo.process({	id_servico 		: id_servico,
																		indice 			: qtdServico,
																		emailGrupo 		: emailGrupo,
																		emailsExternos 	: $.parseJSON(emailsExternos)});
		tab.html(resultado);
		dextranet.grupos.emailsExternos.push({	grupoEmail 		: emailGrupo,
												idServico 		: id_servico,
												emails 			: emailsExternos,
												idServicoGrupo 	: idServicoGrupo});
	},

}