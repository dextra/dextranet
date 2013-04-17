$.ajaxPrefilter( "holy", function( options, originalOptions, jqXHR ) {
	options.context.messages = $.i18n.messages;
});