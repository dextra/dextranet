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
      ellipses: '...',
      more_clzz: 'readm-more',
      ellipse_clzz: 'readm-continue',
      hidden_clzz: 'readm-hidden'
    };

	console.log(this);
    var opts =  $.extend({}, defaults, settings);
    $(this).each(function () {
      var $this = $(this);
      var elemID = $this.attr("id");
      var eachPost = $("#"+elemID);
      if (eachPost.html().length > opts.substr_len) {
        abridge(eachPost);
        linkage(eachPost);
      }
    });

    function linkage(elem) {

      var button = $('#' + elem.attr("id") + "-button");
      elem.append(opts.more_link);
      button.click( function () {
        if(button.text()=="+ Ver mais"){
    	elem.show();
        elem.find(".readm-hidden").show();
        button.text("- Ver menos");
        }else{
        	elem.show();
            elem.find(".readm-hidden").hide();
            button.text("+ Ver mais");
        }
      });
    }

    function abridge(elem) {
      var txt = elem.html();
      var dots = "<span class='" + opts.ellipse_clzz + "'>" + opts.ellipses + "</span>";
      var shown = txt.substring(0, (opts.split_word ? opts.substr_len : txt.indexOf(' ', opts.substr_len))) + dots;
      var hidden =
        '<span class="' + opts.hidden_clzz + '" style="display:none;">' +
          txt.substring((opts.split_word ? opts.substr_len : txt.indexOf(' ', opts.substr_len)), txt.length) +
        '</span>';
      elem.html(shown + hidden);
    }

    return this;
  };
})(jQuery);
