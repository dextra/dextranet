package br.com.dextra.dextranet.web.post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import br.com.dextra.teste.TesteFuncionalBase;


public class PaginacaoTest extends TesteFuncionalBase{

	private PaginaDextraNet dextraNet = new PaginaDextraNet(driver);
	private HashMap<String, Boolean> mapaDosPosts = new HashMap<String, Boolean>();
	private ArrayList<String> listaDosPostsEncontradosNaPagina = new ArrayList<String>();
	private ArrayList<String> listaDosPostsInseridosPeloTest = new ArrayList<String>();

	@Test
	public void criarPosts(){
		int quantidadePosts = 83;
		int vezes = (int) Math.round(((double)quantidadePosts/20)+0.5);

		dadoOSiteDaDextraNET();
		quandoEuCrioPosts(quantidadePosts);
		eDescoOScrollAteOFinalDaPaginaPor(vezes);
		entaoEuPossoPercorrerAPaginaEListarTodosOsPosts();
		paraVerificarSeTodosOsPostsInseridosEstaoSendoListados();
		paraVerificarSeAlgumPostNaoFoiEncontrado();
		eParaConfrontarSeARelacaoDePostsInseridosEstaIgualARelacaoDePostsEncontrados();
	}

	private void dadoOSiteDaDextraNET() {
		dextraNet.navigateTo(this.getUrlApplication());
	}

	private void quandoEuCrioPosts(int quantidadeDePosts){

		for(int i = 1; i <= quantidadeDePosts ; i++){
			String titulo = "Titulo de Teste Numero: " + i;
			String conteudo = "Texto do teste numero: " + i;
			novoPost(titulo, conteudo);
			alimentarBaseDosTestes(conteudo);
		}
	}

	private void novoPost(String titulo, String conteudo) {
		dextraNet.click("span.icon_sidebar_left_novopost");
		dextraNet.writeInputText("input#form_input_title",titulo);
		dextraNet.writeCKEditor(conteudo);
		dextraNet.click("input#form_post_submit");
	}

	private void alimentarBaseDosTestes(String conteudo) {
		this.mapaDosPosts.put(conteudo, false);
		this.listaDosPostsInseridosPeloTest.add(conteudo);
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

	private void entaoEuPossoPercorrerAPaginaEListarTodosOsPosts(){
		List<WebElement> textoDoComentario =  driver.findElements(By.cssSelector(".list_stories_lead"));
		Iterator<WebElement> it = textoDoComentario.iterator();

		while(it.hasNext()){
			String post = it.next().getText().toString();
			listaDosPostsEncontradosNaPagina.add(post);
		}
	}

	private void paraVerificarSeTodosOsPostsInseridosEstaoSendoListados(){
		Iterator<String> iterator = listaDosPostsEncontradosNaPagina.iterator();

		while (iterator.hasNext()) {
			String it = iterator.next();
			for (String post : mapaDosPosts.keySet()) {
				if (post.equals(it)) {
					mapaDosPosts.put(post, true);
				}
			}
		}

		System.out.println("Pilha de Posts Encontrados na Pagina:");
		exibeOsPosts(this.listaDosPostsEncontradosNaPagina);
	}

	private void paraVerificarSeAlgumPostNaoFoiEncontrado(){
        ArrayList<String> retorno = new ArrayList<String>();

        for(String post : mapaDosPosts.keySet()) {
	    	  Boolean status = mapaDosPosts.get(post);
	    	  if(status.booleanValue() == false){
		          retorno.add(post);
	    	  }
	      }

		System.out.println("Pilha de Posts Nao Encontrados Pagina:");
		exibeOsPosts(retorno);

		Assert.assertEquals(0,retorno.size());
	}


	private void eParaConfrontarSeARelacaoDePostsInseridosEstaIgualARelacaoDePostsEncontrados() {
		Collections.sort(listaDosPostsInseridosPeloTest);
		Collections.sort(listaDosPostsEncontradosNaPagina);

		System.out.println("Posts Inseridos: ");
		exibeOsPosts(listaDosPostsInseridosPeloTest);

		System.out.println("Posts Encontrados: ");
		exibeOsPosts(listaDosPostsEncontradosNaPagina);

		Assert.assertEquals(listaDosPostsInseridosPeloTest,listaDosPostsEncontradosNaPagina);
	}

	private void exibeOsPosts(ArrayList<String> listaDePosts) {
		Iterator<String> iterator = listaDePosts.iterator();

		while(iterator.hasNext()){
			String post = iterator.next();
			System.out.println(post);
		}
	}
}
