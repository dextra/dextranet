package br.com.dextra.dextranet.web.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import br.com.dextra.teste.TesteFuncionalBase;


public class PaginacaoTest extends TesteFuncionalBase{

	PaginaDextraNet dextraNet = new PaginaDextraNet(driver);
	HashMap<String, Boolean> posts = new HashMap<String, Boolean>();
	Iterator<WebElement> lista = null;


	@Test
	public void dadoOSiteDaDextraNet(){
		dextraNet.navigateTo("http://localhost:8080");
		int quantidadePosts = 2;
		int vezes = (int) Math.round(((double)quantidadePosts/20)+0.5);

		quandoEuCrioPosts(quantidadePosts);
		eDescoOScrollAteOFinalDaPaginaPor(vezes);
		entaoEuPossoPercorrerAPaginaEListarTodosOsPosts();
		paraVerificarSeTodosOsPostsInseridosEstaoSendoListados();
	}

	private void eDescoOScrollAteOFinalDaPaginaPor(int quantidadeDeVezesQueDescoOScroll){

		for(int i= 0; i <= quantidadeDeVezesQueDescoOScroll;i++){
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(" +
													"0," +
													"Math.max(" +
																"document.documentElement.scrollHeight," +
																"document.body.scrollHeight," +
																"document.documentElement.clientHeight)" +
																");" +
												"");
			this.dextraNet.waitToLoad();
		}
	}




	private void quandoEuCrioPosts(int quantidadeDePosts){
		for(int i = 1; i <= quantidadeDePosts ; i++){
			dextraNet.click("span.icon_sidebar_left_novopost");
			dextraNet.writeInputText("input#form_input_title","Titulo de Teste Numero: " + i);
			dextraNet.writeCKEditor("Texto do teste numero: " + i);
			dextraNet.click("input#form_post_submit");

			posts.put(("Texto do teste numero: " + i), false);
		}
	}


	private void entaoEuPossoPercorrerAPaginaEListarTodosOsPosts(){
		List<WebElement> textoDoComentario =  driver.findElements(By.cssSelector(".list_stories_lead"));
		lista = textoDoComentario.iterator();
	}

	private void paraVerificarSeTodosOsPostsInseridosEstaoSendoListados(){
		while (lista.hasNext()) {
			WebElement it = lista.next();

			for (String post : posts.keySet()) {
				if (post.equals(it.getText().toString())) {
					posts.put(post, true);
				}
			}
		}
	}

	@Test
	public void postsNaoEncontradosNaPagina(){
        List<String> retorno = new ArrayList<String>();

        for(String post : posts.keySet()) {
	    	  Boolean status = posts.get(post);
	    	  if(status == false){
		          System.out.println(post + " " + status);
		          retorno.add(post);
	    	  }
	      }
        Assert.assertEquals(0,retorno.size());
	}

	@Test
	@Ignore
	public void postsEncontradosVersusPostsInseridos() {
	}

}
