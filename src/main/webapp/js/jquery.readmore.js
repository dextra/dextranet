/**
 * jquery.readmore
 *
 * Substring long paragraphs and make expandable with "Read More" link.
 * Paragraph will be split either at exactly 'substr_len' chars or at the next
 * space char after 'substr_len' words (default).
 *
 * @date 02 Jul 2012
 * @author Jake Trent (original author) http://www.builtbyjake.com
 * @author Mike Wendt http://www.mikewendt.net
 * @version 1.6
 */
(function ($) {
  $.fn.readmore = function (settings) {
    var defaults = {
      substr_len: 50,
      split_word: false,
      ellipses: '...'
    };

    var opts =  $.extend({}, defaults, settings);
    $(this).each(function () {
      var $this = $(this);
      var elemID = $this.attr("id");
      var eachPost = $("#"+elemID);
      console.log(eachPost.html().length);
      if (eachPost.html().length > opts.substr_len) {

        abridge(eachPost);
        linkage(eachPost);
      }else{
    	  var button = $('#' + eachPost.attr("id") + "-button");
    	  button.hide();
      }
    });

    function linkage(elem) {

      var button = $('#' + elem.attr("id") + "-button");
      elem.append(opts.more_link);
      button.click( function () {
        if(button.text().indexOf("+ Ver mais")>=0){
        console.log(elem.find(".readm-hidden").text());
    	elem.show();
        elem.find(".readm-hidden").show();
        elem.find(".readm-continue").hide();
        button.text("- Ver menos");
        }else{
        	elem.show();
            elem.find(".readm-hidden").hide();
            elem.find(".readm-continue").show();
            button.text("+ Ver mais");
        }
      });
    }

    function abridge(elem) {
      var txt = elem.html();
      var dots = "<span class='readm-continue'>" + opts.ellipses + "</span>";
      var shown = txt.substring(0, (opts.split_word ? opts.substr_len : txt.indexOf(' ', opts.substr_len))) + dots;
      var hidden =
        '<span class="readm-hidden" style="display:none;">' +
          txt.substring((opts.split_word ? opts.substr_len : txt.indexOf(' ', opts.substr_len)), txt.length) +
        '</span>';
      elem.html(shown + hidden);
    }

    return this;
  };
})(jQuery);
