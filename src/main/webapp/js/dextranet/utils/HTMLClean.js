dextranet.stripHTML = function(content,op) {
	content = content.replace(/<.*?>/g, '');
	content = content.replace(/&nbsp;/g, '');
	content = content.replace(/\n/g, '');

	if(op == 1)
		content = content.replace(/ /g, '');

	return content;
}