(function($)
{
	function closeMenu() {
		$('body').click(function() {
			$('#header_main_menu > li,#box_user').removeClass('active');
			$('.header_dropdown').hide();
			$('body').unbind('click');
		});
	}
		
	// Header -> Abrertura dos menus
	$(document).ready(function() {
		$('#header_main_menu > li').die().live('click', function() {
			if ( $(this).hasClass('active') === false )
			{
				$(this).addClass('active').find('ul').click(function(e) { e.stopPropagation(); });
				$(this).find('.header_dropdown').show();
				closeMenu();
			}
		});
	});

})(jQuery);