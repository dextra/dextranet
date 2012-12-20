// TODO: Existe um outro cara chamado JsonUtil. ELes fazem a mesma coisa? Nao deveriam estar no mesmo
// local?
package br.com.dextra.utils;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class EntityJsonConverter {

	public static JsonObject toJson(Entity entity) {
		JsonObject json = new JsonObject();
		for (Entry<String, Object> entry : entity.getProperties().entrySet()) {
			json.addProperty(entry.getKey(), entry.getValue().toString());
		}
		return json;
	}
//FIXME: Gabriel/Tonho estão com problemas que serão resolvidos rapida e futuramente!
	public static ArrayList<JsonObject> toJson(Results<ScoredDocument> results) {
		ArrayList<JsonObject> jsonList = new ArrayList<JsonObject>();
		ArrayList<String> idList=new ArrayList<String>();
		JsonObject json = new JsonObject();
		if (results != null) {
			for (ScoredDocument scoredDocument : results) {
				idList.add(scoredDocument.getId());
/*				json.addProperty("titulo", scoredDocument.getField("titulo")
						.toString());
				json.addProperty("conteudo", scoredDocument
						.getField("conteudo").toString());
				json.addProperty("usuario", scoredDocument.getField("usuario")
						.toString());
				json.addProperty("data", scoredDocument.getField("data")
						.toString());
				jsonList.add(json);
				json.remove("titulo");
				json.remove("conteudo");
				json.remove("usuario");
				json.remove("data");*/
			}
		}
		return jsonList;
	}

	public static JsonArray converterListaEntities(Iterable<Entity> listaPosts) {
		JsonArray jsonList = new JsonArray();

		for (Entity entity : listaPosts) {
			jsonList.add(toJson(entity));
		}

		return jsonList;
	}
}
