package br.com.dextra.teste.post;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.dextra.post.Post;
import br.com.dextra.teste.TesteFuncionalBase;
import br.com.dextra.testeSeleniumHomePage.PaginaDextraNet;

public class ListaPostTest extends TesteFuncionalBase {

	Post post = new Post();

	@Before
	public void criarUmPost(){
		post.criaNovoPost("Título de Teste", "Contúdo de Teste do Título de Teste", "Usuário de Teste");
	}

	@Test
	public void listaUmPost() throws InterruptedException{
		PaginaDextraNet site = new PaginaDextraNet(driver);
		site.navegarNaPagina("http://localhost:8080");
		Assert.assertTrue(site.obtemElemento("img.list-stories-avatar")!= null);

	}


}
