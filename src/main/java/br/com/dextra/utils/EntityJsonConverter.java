package br.com.dextra.utils;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonObject;

public class EntityJsonConverter {

	public static JsonObject toJson(Entity entity) {
		JsonObject json = new JsonObject();
		for (Entry<String, Object> entry : entity.getProperties().entrySet()) {
			json.addProperty(entry.getKey(), entry.getValue().toString());
		}
		return json;
	}

	public static ArrayList<JsonObject> converterListaEntities(Iterable<Entity> listaPosts) {
		ArrayList<JsonObject> jsonList=new ArrayList<JsonObject>();
//		JsonObject json = new JsonObject();

		for (Entity entity : listaPosts) {
//			json.addProperty("conteudo", entity.getProperty("conteudo").toString());
//			jsonList.add(json);
			jsonList.add(toJson(entity));
		}

		return jsonList;
	}
}
