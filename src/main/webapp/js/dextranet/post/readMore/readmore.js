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

		post = definirVisibleTextDoPostObject(post);


		function definirVisibleTextDoPostObject(postObject){

			var postJson = post.postObjectJson;
			var postContent = postJson.conteudo;
			var paragraphs = getDividedParagraph($(postContent));
			var visibleTextArray = [];


			var totalCaracters = 0;
			var totalParagrafos = 0;

			$(paragraphs).each(function(){

				if(verificaSeEhListaOuCitacao(this)){
					var jsonRetorno = verificaExcessoERemoveLinhasExcedentes(this, totalCaracters, totalParagrafos);
					totalCaracters = jsonRetorno.totalCaracteres;
					totalParagrafos = jsonRetorno.totalParagrafos;
					visibleTextArray.push(jsonRetorno.paragrafo);


				}else{
					var paragraphJQueryObject = $(this);
					var paragraphLength = paragraphJQueryObject.text().length;
					totalCaracters += paragraphLength;
					totalParagrafos += 1;

					if(totalCaracters <= opts.substr_len && totalParagrafos <= opts.substr_lines){
						visibleTextArray.push(getHTMLFromJQuery(paragraphJQueryObject));
					}else if(totalCaracters > opts.substr_len){
						if(totalCaracters-paragraphLength < opts.substr_len){

							var caractersLeft = totalCaracters-opts.substr_len;
							var paragraphToDivide = paragraphJQueryObject;

							var showText = paragraphJQueryObject.text().substring(0 , paragraphLength-caractersLeft);

							paragraphToDivide.html(showText);
							visibleTextArray.push(getHTMLFromJQuery(paragraphToDivide));

						}
					}
				}
			});
			postJson.visibleText = visibleTextArray.join("");

		}

		function verificaExcessoERemoveLinhasExcedentes(paragrafo, totalCaracteres, totalParagrafos){
			var itens = $.makeArray($(paragrafo).children());
			var qtdItens = itens.length;
			var objetoEmString = "";
			$(paragrafo).empty();

			if(totalCaracteres < opts.substr_len && totalParagrafos < opts.substr_lines){
				$(itens).each(function(){
						if(totalCaracteres + $(this).html().legth > opts.substr_len){

						}else if(totalParagrafos + itens.indexOf(this) + 1 > opts.substr_lines){
							var indice = itens.indexOf(this);
							console.log(indice);
							console.log(itens.length - indice);
							itens = itens.splice(0,opts.substr_lines - totalParagrafos);
							console.log(itens);
							return false;
						}
				});

				totalParagrafos += qtdItens;
				$(paragrafo).append(itens);
				objetoEmString = deObjetoDOMParaString(paragrafo);
			}

			return {
				"totalCaracteres" : totalCaracteres,
				"totalParagrafos" : totalParagrafos,
				"paragrafo": objetoEmString
			};
		}

		function verificaSeEhListaOuCitacao(paragrafo){
			var paragrafoJquery = $(paragrafo);
			if(paragrafoJquery.is("ul") || paragrafoJquery.is("ol") || paragrafoJquery.is("blockquote")){
				return true;
			}else{
				return false;
			}
		}

		function deObjetoDOMParaString(objetoDOM){
			return objetoDOM.outerHTML;
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
