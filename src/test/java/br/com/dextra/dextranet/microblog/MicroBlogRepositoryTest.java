package br.com.dextra.dextranet.microblog;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

public class MicroBlogRepositoryTest extends TesteIntegracaoBase {

    @Test
    public void micropostar() {
        MicroPost micropost = new MicroPost("micromessage");
        MicroBlogRepository repository = new MicroBlogRepository();
        repository.salvar(micropost);

        assertEquals(1, repository.buscarMicroPosts().size());
        assertEquals("micromessage", repository.buscarMicroPosts().get(0).getTexto());
    }
}
