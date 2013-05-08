(function($)
{
	headerMenu = {};
	headerMenu.close = function() {
		$('#header_main_menu > li,#box_user').removeClass('active');
		$('.header_dropdown').hide();
		$('body').unbind('click.closeMenu');
	};
	headerMenu.start = function() {
		$('#header_main_menu > li > a').bind('click.openMenu', function(e) 
		{
			var li = $(this).parent('li');
			var dropdown = li.find('.header_dropdown');
			
			if (dropdown.length > 0) {
				e.preventDefault();
				e.stopPropagation();
			}

			if ( li.is('.active') === false ) {
				li.addClass('active').find('ul').click(function(e) { e.stopPropagation(); });
				dropdown.show();
				$('body').bind("click.closeMenu", function() {
					headerMenu.close();
				});
			} else {
				headerMenu.close();
			}
		});
	};

})(jQuery);