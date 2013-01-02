package br.com.dextra.utils;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CommentToJson {

	public static String converterGAETextToString(Entity entity) {
		String text = ((Text) entity.getProperty("text")).getValue();
		return text;
	}

	public static JsonArray converterListaEntities(Iterable<Entity> listaComentarios) {
		JsonArray jsonList = new JsonArray();

		for (Entity entity : listaComentarios) {
			String text = converterGAETextToString(entity);
			JsonObject jsonRetorno = toJson(entity);
			jsonRetorno.addProperty("text", text);
			jsonList.add(jsonRetorno);
		}

		return jsonList;
	}

	public static JsonObject toJson(Entity entity) {
		JsonObject json = new JsonObject();

		for (Entry<String, Object> entry : entity.getProperties().entrySet()) {
			json.addProperty(entry.getKey(), entry.getValue().toString());
		}

		json.addProperty("key",entity.getKey().toString());

		return json;
	}

	public ArrayList<String> toListaDeIds(Results<ScoredDocument> results) {
		ArrayList<String> idList=new ArrayList<String>();

		for (ScoredDocument scoredDocument : results) {
				idList.add(scoredDocument.getField("id").iterator().next().getText());
		}

		return idList;
	}

}
