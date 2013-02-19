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
		
		mostrarBanners : function() {
			for(var i = 1; i < $("#banner img").length; i++){
				$("#banner img").eq(i).hide();
			}
			var $containerBanner = $("#container_banner");
			var minimizado = !! $containerBanner.data('minimizado');

			!minimizado ? dextranet.banner.mudarBanner() : function(){}; 
				
			
			$("#banner .close").click(function() {
				minimizado = !! $containerBanner.data('minimizado');
				$containerBanner.animate({
					bottom : (minimizado ? '+' : '-') + '='	+ $containerBanner.height()
				}, 500, function() {
					$containerBanner.data('minimizado', !minimizado);
					$containerBanner.find('.close').text(minimizado ? '\\/' : '/\\');
					if (minimizado)
						dextranet.banner.mudarBanner();
				});
			});

            $('#container_banner').show();
		},
		
		mudarBanner : function() {
        	setTimeout(function() {	
				var a = $('#banner img').index($('#banner img:visible'));
        		$('#banner img').eq(a).hide();
        		$('#banner img').eq($('#banner img').length > a + 1 ? a + 1 : 0).fadeIn();
        		if(! $('#container_banner').data('minimizado')){
        			dextranet.banner.mudarBanner();
        		}
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
		
		inicializaDatepicker : function(novoPost, formulario) {
			var $dataInicio = formulario ? formulario.children('.date-picker.inicio') : $dataInicio;
			var $dataFim = formulario ? formulario.children('.date-picker.fim') : $dataFim; 
			var dayNamesMin = [ "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab" ];
			var monthNames = [ "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" ];

			$dataInicio.datepicker({ 
				dateFormat: "dd/mm/yy",
				dayNamesMin: dayNamesMin,
				monthNames: monthNames,
				defaultDate: "0",
				minDate: novoPost ? "0" : "",
				onClose: function( selectedDate ) {
					if(selectedDate != "")
						$dataFim.datepicker( "option", "minDate", selectedDate );
				}
			});
			
			$dataFim.datepicker({ 
				dateFormat: "dd/mm/yy",
				dayNamesMin: dayNamesMin,
				monthNames: monthNames,
				defaultDate: "0",
				minDate: novoPost ? "0" : "",
				onClose: function( selectedDate ) {
					$dataInicio.datepicker( "option", "maxDate", selectedDate );
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