var postObject = function(postJson){
	this.postObjectJson = postJson;
	this.postObjectJson.visibleText = "";

	this.setHiddenText = function(){
		$(this).readmore( {
			substr_len : 300,
			substr_lines : 5,
			split_word : false,
			ellipses : '...'
		});
	}
}


postObject.getpostObjectArrayFromPostJsonArray = function(postJsonArray){
	var postObjectArray = [];
	$(postJsonArray).each(function(){
		postObjectArray.push(new postObject(this));
	});
	return postObjectArray;
}