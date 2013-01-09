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


public class FluxoDeCriacaoPaginacaoEPesquisaTest extends TesteFuncionalBase{

	private PaginaDextraNet dextraNet = new PaginaDextraNet(driver);

	private HashMap<String, Boolean> statusDosPosts = new HashMap<String, Boolean>();
	private ArrayList<String> postsEncontrados = new ArrayList<String>();
	private ArrayList<String> postsInseridos = new ArrayList<String>();

	private int quantidadePosts = 61;
	private int vezesQueOScrollDescera = (int) Math.round(((double)quantidadePosts/20)+0.5);
	private String termoQueSeraPesquisado = "60";

	@Test
	public void fluxoDeCriacaoPesquisaEPaginacaoDePosts(){
		dadoOSiteDaDextraNET();
		quandoEuCrioPosts(quantidadePosts);
		eDescoOScrollAteOFinalDaPaginaPor(vezesQueOScrollDescera);
		entaoEuPossoPercorrerAPaginaEListarTodosOsPosts();
		paraVerificarSeTodosOsPostsInseridosEstaoSendoListados();
		paraVerificarSeAlgumPostNaoFoiEncontrado();
		eParaConfrontarSeARelacaoDePostsInseridosEstaIgualARelacaoDePostsEncontrados();
		possoTambemProcurarPorUmPostInseridoParaVerificarSeOMesmoSeraEncontrado(termoQueSeraPesquisado);
		entaoEuVoltoParaAHome();
		procuroPorUmTermoQueRetorneTodosOsPosts();
		paraVerificarSeTodosOsPostsPesquisadosEstaoSendoPaginados();
		entaoEuVoltoParaAHome();
		eDescoOScrollAteOFinalDaPaginaPor(vezesQueOScrollDescera);
		eContinuareiListandoTodosOsPostsQueForamInseridos();
	}

	private void dadoOSiteDaDextraNET() {
		dextraNet.navigateTo("http://localhost:8080");
	}

	private void quandoEuCrioPosts(int quantidadeDePosts){

		for(int i = 1; i <= quantidadeDePosts ; i++){
			String titulo = "Titulo de Teste Numero: " + i;
			String conteudo = "Texto do teste numero: " + i;

			novoPost(titulo, conteudo);
			alimentarBaseDosTestes(conteudo);
			espereCarregarOFadeDeAtualizacaoDaPagina();
		}
	}

	private void novoPost(String titulo, String conteudo) {
		dextraNet.click("span.icon_sidebar_left_novopost");
		dextraNet.writeInputText("input#form_input_title",titulo);
		dextraNet.writeCKEditor(conteudo);
		dextraNet.click("input#form_post_submit");
	}

	private void alimentarBaseDosTestes(String conteudo) {
		this.statusDosPosts.put(conteudo, false);
		this.postsInseridos.add(conteudo);
		Collections.sort(postsInseridos);
	}

	private void espereCarregarOFadeDeAtualizacaoDaPagina() {
		try {
			Thread.sleep(650);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
			espereCarregarOFadeDeAtualizacaoDaPagina();
		}
	}

	private void entaoEuPossoPercorrerAPaginaEListarTodosOsPosts(){
		List<WebElement> textoDoComentario =  driver.findElements(By.cssSelector(".list_stories_lead"));
		postsEncontrados.clear();

		Iterator<WebElement> it = textoDoComentario.iterator();
		while(it.hasNext()){
			String post = it.next().getText().toString();
			postsEncontrados.add(post);
		}

		Collections.sort(postsEncontrados);
	}

	private void paraVerificarSeTodosOsPostsInseridosEstaoSendoListados(){
		Iterator<String> iterator = postsEncontrados.iterator();

		while (iterator.hasNext()) {
			String it = iterator.next();
			for (String post : statusDosPosts.keySet()) {
				if (post.equals(it)) {
					statusDosPosts.put(post, true);
				}
			}
		}
	}

	private void paraVerificarSeAlgumPostNaoFoiEncontrado(){
        ArrayList<String> retorno = new ArrayList<String>();

        for(String post : statusDosPosts.keySet()) {
	    	  Boolean status = statusDosPosts.get(post);
	    	  if(status.booleanValue() == false){
		          retorno.add(post);
	    	  }
	      }
		Assert.assertEquals(0,retorno.size());
	}


	private void eParaConfrontarSeARelacaoDePostsInseridosEstaIgualARelacaoDePostsEncontrados() {
		Assert.assertEquals(postsInseridos,postsEncontrados);
	}

	private void possoTambemProcurarPorUmPostInseridoParaVerificarSeOMesmoSeraEncontrado(String termoPesquisado) {
		procurarPorUmPost(termoPesquisado);

		Assert.assertEquals(1,this.postsEncontrados.size());
		Assert.assertEquals("[Texto do teste numero: " + termoPesquisado + "]",this.postsEncontrados.toString());
	}

	private void procurarPorUmPost(String termoASerPesquisado) {
		dextraNet.writeInputText("#form_search_input",termoASerPesquisado);
		dextraNet.click("#form_search_submit");
		espereCarregarOFadeDeAtualizacaoDaPagina();
		eDescoOScrollAteOFinalDaPaginaPor(vezesQueOScrollDescera);
		this.entaoEuPossoPercorrerAPaginaEListarTodosOsPosts();
	}

	private void entaoEuVoltoParaAHome() {
		this.dextraNet.click("#button_sidebar_left_home");
		espereCarregarOFadeDeAtualizacaoDaPagina();
	}

	private void procuroPorUmTermoQueRetorneTodosOsPosts() {
		this.procurarPorUmPost("Teste");
	}

	private void paraVerificarSeTodosOsPostsPesquisadosEstaoSendoPaginados() {
		Assert.assertEquals(this.quantidadePosts,this.postsEncontrados.size());
		Assert.assertEquals(this.postsInseridos,this.postsEncontrados);
	}

	private void eContinuareiListandoTodosOsPostsQueForamInseridos() {
		entaoEuPossoPercorrerAPaginaEListarTodosOsPosts();
		paraVerificarSeTodosOsPostsInseridosEstaoSendoListados();
		paraVerificarSeAlgumPostNaoFoiEncontrado();
		eParaConfrontarSeARelacaoDePostsInseridosEstaIgualARelacaoDePostsEncontrados();
	}

}

