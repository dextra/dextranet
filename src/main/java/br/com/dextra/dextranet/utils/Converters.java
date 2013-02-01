package br.com.dextra.dextranet.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import br.com.dextra.dextranet.banner.Banner;
import br.com.dextra.dextranet.comment.Comment;
import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.post.Post;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.gson.JsonObject;

public class Converters {

	public static JsonObject toJson(Entity entity) {
        JsonObject json = new JsonObject();
        for (Entry<String, Object> entry : entity.getProperties().entrySet()) {
                json.addProperty(entry.getKey(), entry.getValue().toString());
        }
        json.addProperty("key",entity.getKey().toString());
        return json;
	}

	public List<String> toListaDeIds(Results<ScoredDocument> results) {
		List<String> idList=new ArrayList<String>();
		for (ScoredDocument scoredDocument : results) {
				idList.add(scoredDocument.getField("id").iterator().next().getText());
		}
		return idList;
	}

	public List<JsonObject> converterListaDePostsParaListaDeJson(List<Post> listaPosts) {
		List<JsonObject> jsonList = new ArrayList<JsonObject>();

		for (Post post : listaPosts) {
			jsonList.add(post.toJson());
		}

		return jsonList;
	}

	public List<JsonObject> converterListaDeCommentParaListaDeJson(List<Comment> listaPosts) {
		List<JsonObject> jsonList = new ArrayList<JsonObject>();

		for (Comment comment : listaPosts) {
			jsonList.add(comment.toJson());
		}

		return jsonList;
	}

	public String converterGAETextToString(Entity entity) {
		String conteudo = ((Text) entity.getProperty("conteudo")).getValue();
		return conteudo;
	}

	public boolean toBoolean(String field) {
		if(field.equals("true"))
			return true;
		else
		return false;
	}

	public static List<JsonObject> toJson(List<Banner> entidades) {
		List<JsonObject> json = new ArrayList<JsonObject>();
		for (Entidade entidade : entidades) {
			json.add(entidade.toJson());
		}		
		
		return json;
	}
}
