var postObject = function(postJson){
	this.postObjectJson = postJson;


}


postObject.getpostObjectArrayFromPostJsonArray = function(postJsonArray){
	var postObjectArray = [];
	$(postJsonArray).each(function(){
		postObjectArray.push(new postObject(this));
	});
	return postObjectArray;
}