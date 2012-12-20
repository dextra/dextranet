
// TODO: Existe um outro cara chamado JsonUtil. ELes fazem a mesma coisa? Nao deveriam estar no mesmo
// local?
package br.com.dextra.utils;

import java.util.Map.Entry;

import com.google.appengine.api.datastore.Entity;
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

	public static JsonArray converterListaEntities(Iterable<Entity> listaPosts) {
		JsonArray jsonList=new JsonArray();

		for (Entity entity : listaPosts) {
			jsonList.add(toJson(entity));
		}

		return jsonList;
	}
}
