package br.com.dextra.dextranet.web.post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.dextra.dextranet.web.PaginaPrincipal;
import br.com.dextra.teste.TesteFuncionalBase;


public class PostFuncionalTest extends TesteFuncionalBase{

	private PaginaPrincipal paginaPrincipal = new PaginaPrincipal(driver);

	private List<String> postsEncontrados = new ArrayList<String>();
	private List<String> postsInseridos = new ArrayList<String>();
	private List<String> comentariosEncontrados = new ArrayList<String>();
	private List<String> comentariosInseridos = new ArrayList<String>();

	private int quantidadePosts = 61;
	private int vezesQueOScrollDescera = (int) Math.round(((double)quantidadePosts/20)+0.5);
//	private String termoQueSeraPesquisado = "60";

	@Test
	public void fluxoDeCriacaoPesquisaEPaginacaoDePosts(){
		dadoQueUsuarioAcessaPaginaPrincipal().criaVariosPosts(quantidadePosts);
		quandoUsuarioDesceScrollAteFimDaPagina(vezesQueOScrollDescera);
		entaoUsuarioVisualizaOsPosts().eTodosOsPostsCriadosForamExibidos();

//		possoTambemProcurarPorUmPostInseridoParaVerificarSeOMesmoSeraEncontrado(termoQueSeraPesquisado);
//		entaoEuVoltoParaAHome();
//		procuroPorUmTermoQueRetorneTodosOsPosts();
//		paraVerificarSeTodosOsPostsPesquisadosEstaoSendoPaginados();
//		entaoEuVoltoParaAHome();
//		quandoUsuarioDesceScrollAteFimDaPagina(vezesQueOScrollDescera);
//		eContinuareiListandoTodosOsPostsQueForamInseridos();
	}

	private PostFuncionalTest dadoQueUsuarioAcessaPaginaPrincipal() {
		paginaPrincipal.acesso();
		return this;
	}

	private void criaVariosPosts(int quantidadeDePosts) {
		for(int i = 1; i <= quantidadeDePosts ; i++){
			String titulo = "Titulo de Teste Numero: " + i;
			String conteudo = "Texto do teste numero: " + i;

			PaginaNovoPost paginaNovoPost = paginaPrincipal.clicaEmNovoPost();
			paginaNovoPost.criaNovoPost(titulo, conteudo);

			// armazena o titulo do post criado para futura comparacao
			this.postsInseridos.add(titulo.toUpperCase());
		}
	}

	private void quandoUsuarioDesceScrollAteFimDaPagina(int quantidadeDeVezesQueDescoOScroll){

		for(int i= 0; i <= quantidadeDeVezesQueDescoOScroll; i++){
			paginaPrincipal.scrollAteFim();
		}
	}

	private PostFuncionalTest entaoUsuarioVisualizaOsPosts(){
		List<WebElement> htmlPostsEncontrados =  driver.findElements(By.cssSelector("div.list_stories_headline h2"));
		postsEncontrados.clear();

		for (WebElement elementoPost : htmlPostsEncontrados) {
			// obtem o titulo do post para futura comparacao
			postsEncontrados.add(elementoPost.getText().toString().toUpperCase());
		}

		return this;
	}

	private void eTodosOsPostsCriadosForamExibidos() {
		Collections.sort(postsInseridos);
		Collections.sort(postsEncontrados);
		Assert.assertEquals("Todos os posts inseridos deveriam ter sido visualizados.", postsInseridos, postsEncontrados);
	}

//	private void possoTambemProcurarPorUmPostInseridoParaVerificarSeOMesmoSeraEncontrado(String termoPesquisado) {
//		procurarPorUmPost(termoPesquisado);
//
//		Assert.assertEquals(1,this.postsEncontrados.size());
//		Assert.assertEquals("[Texto do teste numero: " + termoPesquisado + "]",this.postsEncontrados.toString());
//	}

//	private void procurarPorUmPost(String termoASerPesquisado) {
//		paginaPrincipal.writeInputText("#form_search_input",termoASerPesquisado);
//		paginaPrincipal.click("#form_search_submit");
//		//espereCarregarOFadeDeAtualizacaoDaPagina();
//		quandoUsuarioDesceScrollAteFimDaPagina(vezesQueOScrollDescera);
//		this.entaoUsuarioVisualizaOsPosts();
//	}

//	private void entaoEuVoltoParaAHome() {
//		this.paginaPrincipal.click("#button_sidebar_left_home");
//		//espereCarregarOFadeDeAtualizacaoDaPagina();
//	}

//	private void procuroPorUmTermoQueRetorneTodosOsPosts() {
//		this.procurarPorUmPost("Teste");
//	}

//	private void paraVerificarSeTodosOsPostsPesquisadosEstaoSendoPaginados() {
//		Assert.assertEquals(this.quantidadePosts,this.postsEncontrados.size());
//		Assert.assertEquals(this.postsInseridos,this.postsEncontrados);
//	}

//	private void eContinuareiListandoTodosOsPostsQueForamInseridos() {
//		entaoUsuarioVisualizaOsPosts();
//		//paraVerificarSeTodosOsPostsInseridosEstaoSendoListados();
//		//paraVerificarSeAlgumPostNaoFoiEncontrado();
//		//eParaConfrontarSeARelacaoDePostsInseridosEstaIgualARelacaoDePostsEncontrados();
//	}

	@Test
	public void fluxoDeCriacaoEListagemDeComentarios()
	{
		dadoQueUsuarioAcessaPaginaPrincipal().criaVariosPosts(1);
		depoisCrioVariosComentariosParaOsPostsCriados(3);
		entaoVisualizoOsComentarios().eTodosOsComentariosCriadosForamExibidos();
	}

	private void eTodosOsComentariosCriadosForamExibidos() {
		Assert.assertEquals("Todos os comentários inseridos deveriam ter sido visualizados.", comentariosInseridos, comentariosEncontrados);
	}

	private PostFuncionalTest entaoVisualizoOsComentarios(){
		List<WebElement> htmlComentariosEncontrados =  driver.findElements(By.cssSelector(".list_stories_comment_lead"));
		comentariosEncontrados.clear();

		for (WebElement elementoComentario : htmlComentariosEncontrados) {
			comentariosEncontrados.add(elementoComentario.getText().toString());
		}

		Collections.sort(comentariosEncontrados);
		return this;
	}

	private void depoisCrioVariosComentariosParaOsPostsCriados(int quantidadeDeComentarios) {
		PaginaNovoComentario paginaNovoComentario = paginaPrincipal.clicaEmNovoComentario();

		for(int i = 1; i <= quantidadeDeComentarios ; i++){
			String conteudo = "Texto do comentário teste numero: " + i;

			paginaNovoComentario.criaNovoComentario(conteudo);

			// armazena o conteudo dos comentarios criados para futura comparacão
			alimentarBaseDosTestesDoComentario(conteudo);
			paginaNovoComentario.waitingForLoading();
		}
	}

	private void alimentarBaseDosTestesDoComentario(String conteudo) {
		this.comentariosInseridos.add(conteudo);

		Collections.sort(comentariosInseridos);
	}

}

