package br.com.dextra.dextranet.microblog;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.google.gson.JsonObject;

@Path("/microblog")
public class MicroBlogRS {

    private List<JsonObject> posts = new ArrayList<JsonObject>();

    // FIXME: no rest, nao entra acao na URI do recurso. o que manda eh o metodo HTTP
    @Path("/post")
    @POST
    public void post(@FormParam("text") String text){
//        JsonObject json = new JsonObject();
//        json.addProperty("text", text);
//        posts.add(json);

        MicroPost micropost = new MicroPost(text);
        MicroBlogRepository repository = getMicroBlogRepository();
        repository.salvar(micropost);
    }

    protected MicroBlogRepository getMicroBlogRepository() {
        return new MicroBlogRepository();
    }

    @Path("/post")
    @GET
    public String get(){
        return posts.toString();
    }
}
