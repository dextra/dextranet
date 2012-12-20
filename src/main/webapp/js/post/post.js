(function($) {
	$(document).ready(function() {
		abrePaginaInicial();
		$('#form-search').submit(function() {
			var textSearch = $('#form-search-input').val();
			$.ajax( {
				type : 'GET',
				url : "/s/posts",
				data : "max-results=5&q=" + textSearch,
				success : function(jsonArrayPost) {
					$.holy("../template/HomePage.xml", {"jsonArrayPost" : jsonArrayPost});
				}
			}); // $.ajax
		}); //$.submit

		$('#form-post-submit').click(function() {

		});//$.click cria novo post
	});//$.ready
})(jQuery);

function abrePaginaNovoPost()
{
	$.holy("../template/newPost.xml", {});
}

function abrePaginaInicial()
{
	$.ajax( {
		type : 'GET',
		url : "/s/posts",
		data : "max-results=20&q=",
		success : function(jsonArrayPost) {
			$.holy("../template/HomePage.xml", {"jsonArrayPost" : jsonArrayPost});
		}
	});
	$(document).delay(1000);
}

function criaNovoPost()
{
	var post = {
			"title" : $("#form-input-title").val(),
			"content" : $("#form-input-content").val(),
			"author" : "usuario01"
		}
		if ((post.title == "") || (post.content == ""))
			alert("Preencha todos os campos.");
		else {
			$.ajax( {
				type : "POST",
				url : "/s/post/novo",
				data : post,
				success : function(){
					abrePaginaInicial();
				}
			});
		}
}