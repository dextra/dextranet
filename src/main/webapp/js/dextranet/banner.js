dextranet.banner = {
		novoBanner : function() {	
			$.ajax({
				type : "GET",
				url : "/s/banner/uploadURL",
				success : function(url) {
					$("#new_banner").attr("action", url.url).submit();
				}
			});
			return false;
		}
}