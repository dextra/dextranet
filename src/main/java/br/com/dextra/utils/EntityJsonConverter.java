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
		json.addProperty("key",entity.getKey().toString());
		return json;
	}

//FIXME: Gabriel/Tonho estão com problemas que serão resolvidos rapida e futuramente!

	public static ArrayList<String> toJson(Results<ScoredDocument> results) {
		ArrayList<String> idList=new ArrayList<String>();
		JsonObject json = new JsonObject();
			for (ScoredDocument scoredDocument : results) {
				System.out.println("oi");
				idList.add(scoredDocument.getId());
		}
		System.out.println(idList.toString());
//		jsonList=Id
		return idList;
	}

	public static JsonArray converterListaEntities(Iterable<Entity> listaPosts) {
		JsonArray jsonList = new JsonArray();

		for (Entity entity : listaPosts) {
			jsonList.add(toJson(entity));
		}

		return jsonList;
	}
}
