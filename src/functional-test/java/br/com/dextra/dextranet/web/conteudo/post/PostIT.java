package br.com.dextra.dextranet.web.conteudo.post;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.dextra.dextranet.TesteFuncionalBase;
import br.com.dextra.dextranet.conteudo.post.PostRepository;

public class PostIT extends TesteFuncionalBase {
    private PaginaNovoPost paginaNovoPost = null;

    @After
    public void limpaDadosCriados() {
        this.limpaPostsInseridos(new PostRepository());
    }

    @Test
    public void testaPost() {
        dadoQueUsuarioAcessaPaginaPrincipal();
        String titulo = "Titulo de Teste";
        String conteudo = "Texto do teste";

        eleCriaUmPost(titulo, conteudo);
        eChecasePostExiste(titulo, conteudo);
        eleCurtePost();
        eChecaSePostFoiCurtido();
        eleExcluiPost();
        eChecasePostExisteAoExcluir(titulo, conteudo);
    }

    protected void eleCriaUmPost(String titulo, String conteudo) {
        paginaNovoPost = paginaPrincipal.clicaEmNovoPost();
        paginaNovoPost.criarNovoPost(titulo, conteudo);
    }

    private void eChecasePostExiste(String titulo, String conteudo) {
        Assert.assertTrue(paginaNovoPost.existePostPor(titulo, conteudo));
    }

    private void eChecasePostExisteAoExcluir(String titulo, String conteudo) {
        Assert.assertTrue(!paginaNovoPost.existePostPor(titulo, conteudo));
    }

    private void eleCurtePost() {
        String linkCurtir = "a#like_" + paginaNovoPost.getIdPost();
        linkCurtir += " span";
        paginaPrincipal.click(linkCurtir);
        paginaPrincipal.waitToLoad();
    }

    private void eChecaSePostFoiCurtido() {
        String numeroCurtida = "a#showLikes_" + paginaNovoPost.getIdPost();
        numeroCurtida += " .numero_curtida";
        WebElement curtidas = driver.findElement(By.cssSelector(numeroCurtida));
        Assert.assertEquals("1", curtidas.getText());
    }

    private void eleExcluiPost() {
        paginaNovoPost.excluiPost();
    }

    private void dadoQueUsuarioAcessaPaginaPrincipal() {
        paginaPrincipal.acessaPaginaPrincipal();
    }
}