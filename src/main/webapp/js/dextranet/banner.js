dextranet.banner = {
		novoBanner : function() {	
			$.ajax({
				type : "GET",
				url : "/s/banner/uploadURL",
				success : function(url) {
					$("#new_banner").attr("action", url.url).submit();
				}
			});
			return false;
		},

		listaBannersAtuais : function() {
			$.ajax({
				type : "GET",
				url : "/s/banner",
				data : {"atuais" : "true"},
				success : function(banners) {
					if (banners.length > 0) {
						bannerObjectArray = postObject.getpostObjectArrayFromPostJsonArray(banners);
						$(bannerObjectArray).each(function() {
							this.postObjectJson.dataDeAtualizacao = converteData(this.postObjectJson.dataDeAtualizacao).substring(5);
						});
						$.holy("../template/dinamico/banner/banner.xml", {"jsonArrayBanner" : bannerObjectArray});						
					}
				}
			});
			return false;
		},

		resizeBanners : function() {
			var size = $('#content_right').width();
			$('div#new-banner a img').css('max-width', (size - (size*.2))+ 'px');
		},

		mostrarBanners : function() {
			for(var i = 1; i < $("#new-banner a img").length; i++){
				$("#new-banner a img").eq(i).hide();
			}
			dextranet.banner.mudarBanner();
			$('#new-banner').show();
		},

		mudarBanner : function() {
			setTimeout(function() {	
				var a = $('#new-banner a img').index($('#new-banner a img:visible'));
				$('#new-banner a img').eq(a).hide();
				$('#new-banner a img').eq($('#new-banner a img').length > a + 1 ? a + 1 : 0).fadeIn();
				dextranet.banner.mudarBanner();
			}, 15000);
		},
		
		listaBanners : function() {
			$.ajax({
				type : "GET",
				url : "/s/banner",
				data : {"atuais" : "false"},
				success : function(banners) {
					if (banners.length > 0) {
						bannerObjectArray = postObject.getpostObjectArrayFromPostJsonArray(banners);
						$(bannerObjectArray).each(function() {
							this.postObjectJson.dataInicio = converteData(this.postObjectJson.dataInicio).substring(5,15);
							this.postObjectJson.dataFim = converteData(this.postObjectJson.dataFim).substring(5,15);
							this.postObjectJson.dataDeAtualizacao = converteData(this.postObjectJson.dataDeAtualizacao).substring(5,15);
						});
						$.holy("../template/dinamico/abre_pagina_edita_banner.xml", {"jsonArrayBanner" : bannerObjectArray});						
					}
				}
			});
		},
		
		editarBanner : function(idDoBanner) {
			console.log(idDoBanner)
			var dados = {
					"id" : idDoBanner,
					"titulo" : $("#titulo_" + idDoBanner).val(),
					"dataInicio" : $("#dataInicio_" + idDoBanner).val(),
					"dataFim" : $("#dataFim_" + idDoBanner).val()
			};
			
			$.ajax({
				type : "POST",
				url: "/s/banner/editar",
				data : dados,
				success : function() {
					$.ajax({
						type : "GET",
						url : "/_ah/cron"							
					})
					//trocar mensagem de sucesso e colocar calendarios na pagina de edição
					$.holy("../template/dinamico/post/mensagem_sucesso.xml", {});
				}
			});
		},
		
		validaEFormataForm : function() {
			var $dataInicio = $('#bannerDataInicio');
			var $dataFim = $('#bannerDataFim'); 
			var dayNamesMin = [ "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab" ];
			var monthNames = [ "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" ];

			$("#bannerDataInicio").datepicker({ 
				dateFormat: "dd/mm/yy",
				dayNamesMin: dayNamesMin,
				monthNames: monthNames,
				defaultDate: "0",
				minDate: "0",
				onClose: function( selectedDate ) {
					if(selectedDate != "")
						$( "#bannerDataFim" ).datepicker( "option", "minDate", selectedDate );
				}
			});
			
			$("#bannerDataFim").datepicker({ 
				dateFormat: "dd/mm/yy",
				dayNamesMin: dayNamesMin,
				monthNames: monthNames,
				defaultDate: "0",
				minDate: "0",
				onClose: function( selectedDate ) {
					$( "#bannerDataInicio" ).datepicker( "option", "maxDate", selectedDate );
				}
			});
		},

		ordenaBanners : function(orderBy, banners) {
			var parentUl = $(banners).parent();
			var elems = $(parentUl).children('li').remove();

			elems.sort(function(a,b) {
				var aDatePieces = $(a).find("[id^='" + orderBy + "']").val().split('/')
				var aCompleteDate = aDatePieces[2] + aDatePieces[1] + aDatePieces[0];

				var bDatePieces = $(b).find("[id^='" + orderBy + "']").val().split('/')
				var bCompleteDate = bDatePieces[2] + bDatePieces[1] + bDatePieces[0];

				return parseInt(aCompleteDate) < parseInt(bCompleteDate);
			});
			$(parentUl).append(elems);

		}
}