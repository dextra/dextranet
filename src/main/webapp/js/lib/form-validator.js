(function($) {

	var ERROR_CLASS = "field-error";

	$.fn.onError = function() {
		var me = $(this);
		if (!me.is("." + ERROR_CLASS)) {
			me.addClass(ERROR_CLASS);
		}
	};

	$.fn.clearErrors = function() {
		$(this).find("." + ERROR_CLASS).removeClass(ERROR_CLASS);
	};

	var validators = {
		".not-empty" :function(input) {
			var v = $.trim(input.val());
			return v != "" ;
		},
		".gt-zero": function(input) {
			var n = parseInt(input.val());
			return !isNaN(n) && n > 0;
		},
		".email-pattern": function(input) {
			var regex = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
			return regex.test(input.val());
		},
		".not-unchecked": function(input) {
			return $('input[name="' + input.attr('name') + '"]:checked').length > 0;
		},
		".password": function(input) {
			return input.val().length >= 6;
		}
	};

	$.fn.validate = function() {
		var valid = true;
		var form = $(this);

		form.clearErrors();

		for (var v in validators) {
			var f = validators[v];

			form.find(v).each(function() {
				var i = $(this);

				if (!f(i) && !i.attr("disabled")) {
					var li = i.closest("li");
					li.onError();
					valid = false;
				}
			});
		}
		return valid;
	};
})(jQuery);