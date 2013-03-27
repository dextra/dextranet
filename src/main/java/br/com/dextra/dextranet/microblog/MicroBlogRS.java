package br.com.dextra.dextranet.microblog;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import br.com.dextra.dextranet.banner.BannerFields;

import com.google.gson.JsonObject;

@Path("/microblog")
public class MicroBlogRS {

    private static List<JsonObject> posts = new ArrayList<JsonObject>();

    @Path("/post")
    @POST
    public void post(@FormParam("text") String text){
        JsonObject json = new JsonObject();
        json.addProperty("text", text);
        posts.add(json);
    }

    @Path("/post")
    @GET
    public String get(){
        return posts.toString();
    }
}
