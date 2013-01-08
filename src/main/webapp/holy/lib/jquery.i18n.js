(function($) {
	getBrowserLocale = function() {
		var locale = "";
		if(navigator) {
			if(navigator.language) {
				locale = navigator.language;
			} else if(navigator.browserLanguage) {
				locale = navigator.browserLanguage;
			} else if(navigator.systemLanguage) {
				locale = navigator.systemLanguage;
			} else if(navigator.userLanguage) {
				locale = navigator.userLanguage;
			}
		} else {
			locale = $.cookie('locale');
		}
		return locale.toLowerCase();
	};

	$.i18n = {};
	$.i18n.loaded = false;
	$.i18n.messages = {};
	$.i18n.locale = getBrowserLocale();
	$.i18n.attrs = ['alt', 'title', 'value'];

	$.i18n.setLocale = function(locale) {
		if(!locale) {
			return $.i18n.setLocale('en-us')
		}
		if( typeof (locale) == 'object') {
			var header = locale['Accept-Language'];
			return $.i18n.setLocale(header[0].split(',')[0]);
		}
		$.i18n.locale = locale.toLowerCase();
		$.cookie('locale', locale);
		$.i18n.load();
	}

	$.i18n.load = function(url) {
		if(!url) {
			url = 'i18n/' + $.i18n.locale;
		}

		$.ajax({
			url : url,
			dataType : 'json',
			async : false,
			success : function(words) {
				$.extend($.i18n.messages, words.messages);
				$.i18n.loaded = true;
			},
			error : function(resp) {
				if(resp.status == 404) {
					$.i18n.locale = 'pt-br';
					$.i18n.load('i18n/' + $.i18n.locale);
					return;
				}
				throw resp;
			}
		});
	}

	if($.i18n.locale) {
		$.i18n.load();
	} else {
		$.i18n.setLocale(getBrowserLocale());
	}

})(jQuery);
