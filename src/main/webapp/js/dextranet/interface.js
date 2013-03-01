(function($)
{
/**
 * Plugin p/ placeholder de inputs de formulário.
 */
	jQuery.fn.extend({
		placeholder: function(value)
		{
			$(this).val(value);
			$(this).focus(function()
			{
				if( $(this).val() === value )
				{
					$(this).val('');
				}
			});
			$(this).blur(function()
			{
				if( $(this).val() === '' )
				{
					$(this).val(value);
				}
			});
			return this;
		}
	});

/**
 * Funções básicas de interface.
 */
	$.DextranetInterface = {};
	
	$.DextranetInterface.bindHeaderDropdownsClose = function()
	{
		$('body').click(function() 
		{ 
			$('#header_main_menu > li,#box_user').removeClass('active'); 
			$('.header_dropdown').hide();
			$('body').unbind('click');
		});
	}
	
/**
 * Document.ready
 */
	$(document).ready(function()
	{
		// Header -> Abrertura dos menus
		$('#header_main_menu > li').die().live('click', function()
		{
			if ( $(this).hasClass('active') === false )
			{
				$(this).addClass('active').find('ul').click(function(e) { e.stopPropagation(); });
				$(this).find('.header_dropdown').show();
				$.DextranetInterface.bindHeaderDropdownsClose();
			}
		});
		
		// Header -> abertura da caixa de dados do usuário
		$('#box_user').die().live('click', function()
		{
			if ( $(this).hasClass('active') === false )
			{
				$(this).addClass('active').find('ul').click(function(e) { e.stopPropagation(); });
				$.DextranetInterface.bindHeaderDropdownsClose();
			}
		});
		
		// Header -> fechamento de caixas dropdown
		$('#box_user_info,#box_user_notifications').die().live('click', function()
		{
			$(this).find('.header_dropdown').show();
		});
		
		// Diálogos -> fechamento no clique
		$('.message > li').live('click', function()
		{
			$(this).animate({ 'height' : 0, 'opacity' : 0, 'paddingTop' : 0, 'paddingBottom' : 0 }, 300, function() {
				$(this).remove();
			});
		});
	
	});

})(jQuery);