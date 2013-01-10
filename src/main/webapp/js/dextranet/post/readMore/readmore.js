(function($) {

	$.fn.readmore = function(settings) {
		var defaults = {
			substr_len : 500,
			substr_lines : 5,
			split_word : false,
			ellipses : '...'
		};
		var opts = $.extend( {}, defaults, settings);

		var post = this.get(0);

		post = setPostObjectHiddenText(post);


		function setPostObjectHiddenText(postObject){

			var postJson = post.postObjectJson;
			var postContent = postJson.conteudo;
			var paragraphs = getDividedParagraph($(postContent));
			var visibleTextArray = [];


			var totalCaracters = 0;

			$(paragraphs).each(function(){
				var paragraphJQueryObject = $(this);
				var paragraphLength = paragraphJQueryObject.text().length;
				totalCaracters += paragraphLength;

				if(totalCaracters <= opts.substr_len && paragraphs.indexOf(this) <= opts.substr_lines){
					visibleTextArray.push(getHTMLFromJQuery(paragraphJQueryObject));
				}else if(totalCaracters > opts.substr_len){
					if(totalCaracters-paragraphLength < opts.substr_len){

						var caractersLeft = totalCaracters-opts.substr_len;
						var paragraphToDivide = paragraphJQueryObject;
						var continueText =
							" <span id=\""+postJson.id+"_button\" class=\"list_stories_footer_call\">"+$.i18n.messages.link_readMore+"</span>";

						var showText = paragraphJQueryObject.text().substring(0 , paragraphLength-caractersLeft) +
						continueText;

						paragraphToDivide.html(showText);
						visibleTextArray.push(getHTMLFromJQuery(paragraphToDivide));

					}
				}
			});
			postJson.visibleText = visibleTextArray.join("");

		}

		function getDividedParagraph(rawParagraphArray){
			var dividedParagraph = [];
			$(rawParagraphArray).each(function(){
				if(this.nodeType != 3){
					dividedParagraph.push(this);
				}
			});
			return dividedParagraph;
		}

		function getHTMLFromJQuery(jqueryObject){
			return $('<div></div>').html(jqueryObject.clone()).html()
		}

	}
})(jQuery);
