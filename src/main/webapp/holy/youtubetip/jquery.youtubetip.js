/*Copyright 2011/2012 Dextra Sistemas

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

( function($) {

	var scriptLoaded = false;
	var loadScript = function(callback){
		if (!scriptLoaded) {
			var f = function(){
				scriptLoaded = true;
				callback();
			}
			$.getScript("http://swfobject.googlecode.com/svn/trunk/swfobject/swfobject.js", f);
		} else {
			callback();
		}
	}

	$.fn.tipYoutubeWatch = function(url){
		var playerWidth = $(this).width();
		var playerHeight = $(this).height();
		var id = $(this).attr("id");
		if (!id) {
			throw "Id is required on the <object/> to play the video";
		}

		loadScript(function() {
			swfobject.embedSWF(url + '&rel=1&border=0&fs=1&autoplay=1', id, playerWidth, playerHeight, '9.0.0', false, false, {allowfullscreen: 'true'});
		});
	}

	$.fn.youtubeTip = function(opts) {
		opts = opts || {};

		var content = $(this);
		content.html("");

		var title = $("<h2/>");
		title.text(opts.title || "Search Results");
		content.append(title);

		var innerContent = $("<div/>");
		content.append(innerContent);

		var query = opts.query || {};
		if (!query) {
			return;
		}
		var playerWidth = opts.playerWidth || 350;
		var playerHeight = opts.playerHeight || 290;

		var maxWidth = opts.maxWidth || "350px";
		var maxResults = opts.maxResults || "5";

		content.css("width", maxWidth);

		loadScript(function(){
			$.ajax({
				url: "http://gdata.youtube.com/feeds/api/videos",
				dataType: "jsonp",
				type: "GET",
				data: { q: query,
						alt: "json-in-script",
						"max-results": maxResults,
						format: "5"
					},
				success: function(data){
						var feed = data.feed;
						var entries = feed.entry || [];

						for (var i = 0; i < entries.length; i++) {
							var entry = entries[i];
							var title = entry.title.$t.substr(0, 20);
							var thumbnailUrl = entry.media$group.media$thumbnail[0].url;
							var playerUrl = entry.media$group.media$content[0].url;

							var videoDiv = $("<div/>");

							var videoTitle = $("<h3/>");
							videoTitle.text(title);
							videoDiv.append(videoTitle);

							var img = $("<img/>");

							img.css("max-width", playerWidth);
							img.attr("src", thumbnailUrl);
							img.attr("title", opts.imgTooltip || "Click to watch");
							img.attr("id", ["tip-youtube-", i].join(''));
							img.data("playerurl", playerUrl);

							img.click(function(){
								var me = $(this);
								var id = me.attr("id");
								var parent = me.parent();
								var object = $("<object/>");
								object.width(playerWidth);
								object.height(playerHeight);
								object.attr("id", id);
								parent.append(object);
								object.tipYoutubeWatch(me.data("playerurl"));
								me.remove();
							});

							videoDiv.append(img);
							innerContent.append(videoDiv);

							var btSelect = $("<input type='button' />");
							btSelect.val(opts.selectButtonLabel || "Select");
							btSelect.data("obj", {title: title, thumbnailUrl: thumbnailUrl, playerUrl: playerUrl});
							if (opts.buttonClass) {
								btSelect.addClass(opts.buttonClass);
							}

							btSelect.click(function(){
								content.hide();
								if (opts.select) {
									opts.select($(this).data("obj"));
								}
							});

							var btContainer = $("<div/>");
							btContainer.append(btSelect);
							videoDiv.append(btContainer);

							videoDiv.fadeIn();
						}
						content.show();
						if (opts.complete) {
							opts.complete();
						}
					}
			});
		});
	};
}(jQuery));
