dextranet.stripHTML = function(content) {
	content = content.replace(/<.*?>/g, '');
	content = content.replace(/&nbsp;/g, '');
	content = content.replace(/\n/g, '');
	content = content.replace(/ /g, '');

	return content;
}