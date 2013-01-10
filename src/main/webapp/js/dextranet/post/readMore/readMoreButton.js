dextranet.readMoreButton = {

	addButtonEvent : function(buttons,postObjectArray) {

		$(buttons).each(function(){
			var buttonID = $(this).attr("id");
			var button = $("#"+buttonID);
			var postID = buttonID.replace("_button","");

				button.click(function(){
					var postToShow = findPostInArrayByID(postID,postObjectArray);
					$("#"+postID+"_post").html(postToShow.postObjectJson.conteudo);
					//console.info("click"):
			});
		});
	},

	findPostInArrayByID : function(postID,postObjectArray){
		var postObject;

		$(postObjectArray).each(function(){
			if(this.postObjectJson.id == postID){
				postObject = this;
				return false;
			}
		});
		return postObject;
	}

}

