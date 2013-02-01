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
				success : function(banners) {
					if (banners.length > 0) {
						bannerObjectArray = postObject.getpostObjectArrayFromPostJsonArray(banners);
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
		}
}