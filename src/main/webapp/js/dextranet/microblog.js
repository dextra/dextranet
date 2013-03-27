(function($) {

    $('#new_tweet').submit(function() {
        var text = $('#tweet_text').val();
        $('.tweets').prepend('<li>' + text + '</li>');

        var post = {};
        post.text = text;

        $.post("/s/microblog/post", post).done(function(data) {
            console.log($.get("/s/microblog/post"));
        });
        return false;
    });

}(jQuery));
