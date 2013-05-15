package br.com.dextra.dextranet.microblog;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import br.com.dextra.dextranet.seguranca.AutenticacaoService;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Path("/microblog")
public class MicroBlogRS {

    @Path("/post")
    @POST
    public void post(@FormParam("text") String text) {
        MicroPost micropost = new MicroPost(text, obtemUsuarioLogado());
        MicroBlogRepository repository = getMicroBlogRepository();
        repository.salvar(micropost);
    }

    protected Usuario obtemUsuarioLogado() {
        return new UsuarioRepository().obtemUsuarioLogado();
    }

    protected MicroBlogRepository getMicroBlogRepository() {
        return new MicroBlogRepository();
    }

    @Path("/post")
    @GET
    public String get() {
        MicroBlogRepository repository = getMicroBlogRepository();
        JsonArray microPosts = new JsonArray();
        for (MicroPost microPost : repository.buscarMicroPosts()) {
            JsonObject microPostJson = new JsonObject();
            microPostJson.addProperty("text", microPost.getTexto());
            microPosts.add(microPostJson);
        }
        return microPosts.toString();
    }
}
