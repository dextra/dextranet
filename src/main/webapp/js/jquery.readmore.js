/**
 * @author Jake Trent (original author) http://www.builtbyjake.com
 */
(function($) {
	$.fn.readmore = function(settings) {
		var defaults = {
			substr_len : 500,
			substr_lines : 5,
			split_word : false,
			ellipses : '...'
		};

		var opts = $.extend( {}, defaults, settings);
		$(this).each(function() {
			var $this = $(this);
			var elemID = $this.attr("id");
			var paragraphs = $this.children();

			verifyAndPrepareContinueText(paragraphs, $this);
		});

		function verifyAndPrepareContinueText(paragraphs, post) {
			var totalCaracteres = 0;
			var totalParagraphs = 0;
			var postID;
			var tamanhoInicialParagrafo = 0;
			paragraphs.each(function() {
						var paragraph = $(this);
						if (paragraph.attr("id")) {
							postID = paragraph.attr("id");
						}

						var children = verificarFilho(paragraph);
						children.each(function() {
									var child = $(this);
									totalCaracteres = totalCaracteres + child.text().length;
									totalParagraphs++;

									if (totalCaracteres > opts.substr_len) {
										var showText = "";
										var hideText = "";
										if (tamanhoInicialParagrafo < opts.substr_len) {

											var showLength = opts.substr_len
													- tamanhoInicialParagrafo
													- 1;
											showText = paragraph.text()
													.substring(0, showLength);
											hideText = '<span class="readm_hidden" style="display:none;">'
													+ child
															.text()
															.substring(
																	showLength,
																	$(this)
																			.text().length)
													+ "</span>";
											addContinueIndicator(paragraph);
											child.html(showText + hideText);
										} else {
											$(child).addClass(
													"readm_hidden");
										}

									} else if (totalParagraphs - 1 > opts.substr_lines) {
										if (totalParagraphs -2 == opts.substr_lines) {
											addContinueIndicator(paragraph);
										}
										child.addClass("readm_hidden");
									}
									tamanhoInicialParagrafo = tamanhoInicialParagrafo
											+ paragraph.text().length;
								});
					});

			if (totalCaracteres > opts.substr_len
					|| totalParagraphs - 1 > opts.substr_lines) {
				hideContinuation(post);
				addButtonEvent(postID);
			} else {
				hideSeeMoreButton(postID);
			}
		}

		function verificarFilho(pai) {
			if (pai.children().size() != 0) {
				return pai.children();
			} else {
				return pai;
			}
		}

		function hideContinuation(post) {
			$(post).find(".readm_hidden").hide();
		}

		function addContinueIndicator(limitParagraph) {
			var dots = "<span class='readm_continue'>" + opts.ellipses
					+ "</span>";

			$(limitParagraph).after(dots);
		}

		function hideSeeMoreButton(postID) {
			var button = $("#" + postID + "_button");
			button.hide();
		}

		function addButtonEvent(postID) {
			var button = $("#" + postID + "_button");
			button.click(function() {
				if (button.text().indexOf("+ Ver mais") >= 0) {
					$("#" + postID + "_post").find(".readm_hidden").show();
					$("#" + postID + "_post").find(".readm_continue").hide();
					button.text("- Ver menos");
				} else {
					$("#" + postID + "_post").find(".readm_hidden").hide();
					$("#" + postID + "_post").find(".readm_continue").show();
					button.text("+ Ver mais");
				}
			});
		}
	}
})(jQuery);
