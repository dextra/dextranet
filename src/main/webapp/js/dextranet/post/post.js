var dextranet{
       post{
    	   fazPesquisa : function fazPesquisa() {
    		   var cons = $('#form_search_input').val();
    		   consulta.setText(cons);
    		   var ehUmNovoPost = false;
    		   var pagina = 0;

    		   if(consulta.getText() != ""){
    			   $("#form_gif_loading").css("display", "inline");
    			   dextranet.home.busquePosts(consulta.getText(), ehUmNovoPost, pagina);
    		   }
    		   //$("#form_gif_loading").css("display", "none");
    		   return false; //O retorno falso faz com que a página de pesquisa não sofra reload para index

       }
    }
}



