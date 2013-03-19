(function($) {

    $('#new_tweet').submit(function() {
        var text = $('#tweet_text').val();
        $('.tweets').prepend('<li>' + text + '</li>');
        return false;
    });

}(jQuery));
