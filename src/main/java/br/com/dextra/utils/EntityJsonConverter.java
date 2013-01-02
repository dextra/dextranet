package br.com.dextra.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
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

	public static ArrayList<String> toListaDeIds(Results<ScoredDocument> results) {
		System.out.println(results+"/n numero de Resultados "+results.getResults().toString());
		ArrayList<String> idList=new ArrayList<String>();
		for (ScoredDocument scoredDocument : results) {
				idList.add(scoredDocument.getField("id").iterator().next().getText());
		}
		return idList;
	}

	public static JsonArray converterListaEntities(Iterable<Entity> listaPosts) {
		JsonArray jsonList = new JsonArray();

		for (Entity entity : listaPosts) {
			String conteudo = converterGAETextToString(entity);
			JsonObject jsonRetorno = toJson(entity);
			jsonRetorno.addProperty("conteudo", conteudo);
			jsonList.add(jsonRetorno);
		}

		return jsonList;
	}

	private static String converterGAETextToString(Entity entity) {
		String conteudo = ((Text) entity.getProperty("conteudo")).getValue();
		return conteudo;

	}


}
