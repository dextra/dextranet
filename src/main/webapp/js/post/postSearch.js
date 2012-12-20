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

		$('#form-search').submit(function() {
			var textSearch = $('#form-search-input').val();
			$.ajax( {
				type : 'GET',
				url : "/s/posts",
				data : "max-results=5&q=" + textSearch,
				success : function(jsonArrayPost) {
					$.holy("../template/post.xml", {"jsonArrayPost" : jsonArrayPost});
				}
			}); // $.ajax
		}); //$.submit
	});//$.ready
})(jQuery);