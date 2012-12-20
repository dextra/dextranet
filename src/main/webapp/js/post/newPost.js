$(document).ready(function() {
	$('#form-post-submit').click(function() {
		var post = {
			"title" : $("#form-input-title").val(),
			"content" : $("#form-input-content").val(),
			"author" : "usuario01"
		}
		if ((post.title == "") || (post.content == "") || (post.author == ""))
			alert("Preencha todos os campos.");
		else {
			$.ajax( {
				type : "POST",
				url : "/s/post/novo",
				data : post,
				success : function() {
//					alert("Sucesso! :)");
					location.href = "/index.html";
				}
			});
		}
	});
});