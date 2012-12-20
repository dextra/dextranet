(function($) {
	$(document).ready(function() {
		$.ajax( {
			type : 'GET',
			url : "/s/posts",
			data : "max-results=5&q=",
			success : function(jsonArrayPost) {
				$.holy("../template/post.xml", {"jsonArrayPost" : jsonArrayPost});
			}
		});
		$(document).delay(1000);
	});
})(jQuery);