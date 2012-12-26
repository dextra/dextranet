package br.com.dextra.utils;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class EntityJsonConverter {

	// Converte uma Entity para JsonObject
	public static JsonObject toJson(Entity entity) {
		JsonObject json = new JsonObject();
		for (Entry<String, Object> entry : entity.getProperties().entrySet()) {
			json.addProperty(entry.getKey(), entry.getValue().toString());
		}
		json.addProperty("key",entity.getKey().toString());
		return json;
	}

	// Converte ResultsFTS para Lista de IDs
	public static ArrayList<String> toListaDeIds(Results<ScoredDocument> results) {
		ArrayList<String> idList=new ArrayList<String>();
		for (ScoredDocument scoredDocument : results) {
				idList.add(scoredDocument.getField("id").iterator().next().getText());
		}
		return idList;
	}

	//Converter Iterable<Entity> para JsonArray
	public static JsonArray converterListaEntities(Iterable<Entity> listaPosts) {
		JsonArray jsonList = new JsonArray();

		for (Entity entity : listaPosts) {
			jsonList.add(toJson(entity));
		}

		return jsonList;
	}


}
