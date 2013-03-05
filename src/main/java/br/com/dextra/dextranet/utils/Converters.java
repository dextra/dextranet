package br.com.dextra.dextranet.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import br.com.dextra.dextranet.banner.Banner;
import br.com.dextra.dextranet.comment.Comment;
import br.com.dextra.dextranet.post.Post;
import br.com.dextra.dextranet.usuario.Usuario;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.gson.JsonObject;

public class Converters {

    public static JsonObject toJson(Entity entity) {
        JsonObject json = new JsonObject();
        for (Entry<String, Object> entry : entity.getProperties().entrySet()) {
            json.addProperty(entry.getKey(), entry.getValue().toString());
        }
        json.addProperty("key", entity.getKey().toString());
        return json;
    }

    public static List<String> toListaDeIds(Results<ScoredDocument> results) {
        List<String> idList = new ArrayList<String>();
        for (ScoredDocument scoredDocument : results) {
            Iterable<Field> fields = scoredDocument.getFields();
            for(Field f : fields){
                if(f.getName().equals("id")){
                    idList.add(f.getText());
                }
            }
        }
        return idList;
    }

    public static List<JsonObject> converterListaDePostsParaListaDeJson(List<Post> listaPosts) {
        List<JsonObject> jsonList = new ArrayList<JsonObject>();

        for (Post post : listaPosts) {
            jsonList.add(post.toJson());
        }

        return jsonList;
    }

    public static List<JsonObject> converterListaDeCommentParaListaDeJson(List<Comment> listaComments) {
        List<JsonObject> jsonList = new ArrayList<JsonObject>();

        for (Comment comment : listaComments) {
            jsonList.add(comment.toJson());
        }

        return jsonList;
    }

    public static List<JsonObject> converterListaDeUsuarioParaListaDeJson(List<Usuario> listaUsuario) {
        List<JsonObject> jsonList = new ArrayList<JsonObject>();

        for (Usuario usuario : listaUsuario) {
            jsonList.add(usuario.toJson());
        }

        return jsonList;
    }

    public static List<JsonObject> converterListaDeBannerParaListaDeJson(List<Banner> listaBanner) {
        List<JsonObject> jsonList = new ArrayList<JsonObject>();

        for (Banner banner : listaBanner) {
            jsonList.add(banner.toJson());
        }

        return jsonList;
    }

    public static String converterGAETextToString(Entity entity) {
        String conteudo = ((Text) entity.getProperty("conteudo")).getValue();
        return conteudo;
    }

    public boolean toBoolean(String field) {
        return Boolean.valueOf(field);
    }
}
