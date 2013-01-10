dextranet.readMoreButton = {

	addButtonEvent : function(buttons,postObjectArray) {

		console.log($.i18n.messages);

		$(postObjectArray).each(function(){
			var postJson = this.postObjectJson;
			if(postJson.conteudo != postJson.visibleText){
				var contentJQuery = $(postJson.conteudo);
				var visibleJQuery = $(postJson.visibleText);
				postJson.visibleText = dextranet.readMoreButton.addContinueText(postJson,visibleJQuery, $.i18n.messages.link_readMore);
				postJson.conteudo = dextranet.readMoreButton.addReduceText(postJson, postJson.conteudo, $.i18n.messages.link_readLess);
			}
		});
	},

	addContinueText : function(postJson, visibleJQuery, text){

		visibleJQuery = dextranet.readMoreButton.addReadMoreButton(postJson, visibleJQuery, text);

		$("#"+postJson.id+"_post").html(visibleJQuery);

		dextranet.readMoreButton.addEvent($("#"+postJson.id+"_button"), postJson);

		postJson.visibleText = dextranet.readMoreButton.getVisibleTextFromJqueryObj(visibleJQuery);

		return postJson.visibleText;


	},

	getVisibleTextFromJqueryObj : function(visibleJQuery){
		var text = "";
		visibleJQuery.each(function(){
			text = text+this.outerHTML;
		});

		return text;
	},

	addReduceText : function(postJson, contentJQuery, text){

		var button = dextranet.readMoreButton.addButton(postJson.id, text);
		postJson.conteudo = contentJQuery+button;

		return postJson.conteudo;


	},

	addReadMoreButton : function(postJson, textJQuery, text){
		var qtdParagraphs = textJQuery.size();
		var lastParagraphs = textJQuery[qtdParagraphs-1];

		var button = dextranet.readMoreButton.addButton(postJson.id ,text);
		$(lastParagraphs).append(button);

		textJQuery[qtdParagraphs-1]=lastParagraphs;
		return textJQuery;
	},

	addButton : function(postID, text){
		return '<span id="'+postID+'_button" class="list_stories_footer_call">'+text+'</span>'
	},

	addEvent : function(button, postJson){
		button.click(function(){
			if($(this).text() == $.i18n.messages.link_readMore){
				$("#"+postJson.id+"_post").html(postJson.conteudo);
				dextranet.readMoreButton.addEvent($("#"+postJson.id+"_button"), postJson);
			}else{
				$("#"+postJson.id+"_post").html(postJson.visibleText);
				dextranet.readMoreButton.addEvent($("#"+postJson.id+"_button"), postJson);

			}
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

