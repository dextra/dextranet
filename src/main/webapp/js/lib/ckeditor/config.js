/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here.
	// For the complete reference:
	// http://docs.ckeditor.com/#!/api/CKEDITOR.config

	// The toolbar groups arrangement, optimized for two toolbar rows.
	config.toolbarGroups = [
		{ name: 'undo' },
		{ name: 'editing', groups: [ 'spellchecker' ] },
		{ name: 'links' },
		{ name: 'insert' },
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'styles' }
	];

	config.coreStyles_strike = {
	    element: 'strike',
	    attributes: { 'class': 'StrikeThrough' },
	    overrides: 'strike'
	};

	// Remove some buttons, provided by the standard plugins, which we don't
	// need to have in the Standard(s) toolbar.
	config.removeButtons = 'Underline,Subscript,Superscript,Table';

	// Se the most common block elements.
	config.format_tags = 'p;h1;h2;h3;pre';

	//Por padrão, target="_blank"
	CKEDITOR.on('dialogDefinition', function ( e ){
		if(e.data.name == 'link'){
			e.data.definition.getContents('target').get('linkTargetType')['items'].splice(0, 3);
			e.data.definition.getContents('target').get('linkTargetType')['items'].splice(1, 1);
			e.data.definition.getContents('target').get('linkTargetType')['items'].splice(2, 2);
			e.data.definition.getContents('target').get('linkTargetType')['default']='_blank';
		}
	});

	// Make dialogs simpler.
	config.removeDialogTabs = 'image:advanced;link:advanced';
};
