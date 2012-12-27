/**
 * @author Jake Trent (original author) http://www.builtbyjake.com
 */
(function ($) {
  $.fn.readmore = function (settings) {
    var defaults = {
      substr_len: 500,
      substr_lines: 5,
      split_word: false,
      ellipses: '...'
    };

    var opts =  $.extend({}, defaults, settings);
    $(this).each(function () {
      var $this = $(this);
      var elemID = $this.attr("id");
      var paragraphs = $this.find("p");

      verifyAndPrepareContinueText(paragraphs,$this);
    });

    function verifyAndPrepareContinueText(paragraphs,post){
        var totalCaracteres=0;
        var totalParagraphs = 0;
        var postID;
		var tamanhoInicialParagrafo=0;
	    paragraphs.each(
	  		  function(){
	  			  var paragraph = $(this);
	  			  if(paragraph.attr("id")){
	  				  postID=paragraph.attr("id");
	  			  }
	  			  totalCaracteres=totalCaracteres+paragraph.text().length;
	  			  totalParagraphs++;
	  			  if(totalCaracteres > opts.substr_len){
	  				var showText="";
	  				var hideText="";
	  				if(tamanhoInicialParagrafo < opts.substr_len){

	  					var showLength = opts.substr_len-tamanhoInicialParagrafo-1;
	  					showText = paragraph.text().substring(0,showLength);
	  					console.log(showText.legth);
	  					hideText = '<span class="readm-hidden" style="display:none;">' +
	  				  					paragraph.text().substring(showLength,paragraph.text().length)+ "</span>";
	  					addContinueIndicator(paragraph);
		  				paragraph.html(showText+hideText);
	  				}else{
	  					$(paragraph).addClass("readm-hidden");
	  				}

	  			  }else if(totalParagraphs-1 > opts.substr_lines){
	  				  	if(totalParagraphs-2 == opts.substr_lines){
	  				  		addContinueIndicator(paragraph);
	  				  	}
	  				  	$(paragraph).addClass("readm-hidden");
	  			  }
	  			tamanhoInicialParagrafo=tamanhoInicialParagrafo+paragraph.text().length;
	  		  }
	    );


	    if(totalCaracteres > opts.substr_len || totalParagraphs-1 > opts.substr_lines){
	        hideContinuation(post);
		  		addButtonEvent(postID);
	    }else{
	    	hideSeeMoreButton(postID);
	    }
    }

    function hideContinuation(post){
    	$(post).find(".readm-hidden").hide();
    }

    function addContinueIndicator(limitParagraph){
  		var dots = "<span class='readm-continue'>" + opts.ellipses + "</span>";

  		$(limitParagraph).after(dots);
    }

    function hideSeeMoreButton(postID){
	    var button = $("#"+postID+"-button");
    	 button.hide();
    }

    function addButtonEvent(postID){
    	var button = $("#"+postID+"-button");
    	button.click(function(){
    		if(button.text().indexOf("+ Ver mais")>=0){
    			$("#"+postID+"-post").find(".readm-hidden").show();
    			$("#"+postID+"-post").find(".readm-continue").hide();
    			button.text("- Ver menos");
    		}else{
    			$("#"+postID+"-post").find(".readm-hidden").hide();
    			$("#"+postID+"-post").find(".readm-continue").show();
    			button.text("+ Ver mais");
    		}
    	});
    }
  }
})(jQuery);
