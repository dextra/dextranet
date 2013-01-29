package br.com.dextra.dextranet.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonUtil {

	private static Gson gson;
	private static Gson formattedGson;
	private static JsonParser parser = new JsonParser();

	private static Gson getGson() {
		if (gson == null) {
			gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonSerializer<Date>() {

				@Override
				public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
					return new JsonPrimitive(src.getTime());
				}
			}).create();
		}
		return gson;
	}

	private static Gson getFormattedGson() {
		if (formattedGson == null) {
			formattedGson = new GsonBuilder().setPrettyPrinting().create();
		}
		return formattedGson;
	}

	public static String stringify(Object json) {
		return getGson().toJson(json);
	}

	public static String stringifyFormatted(Object json) {
		return getFormattedGson().toJson(json);
	}

	public static JsonElement toJsonTree(Object json) {
		return getGson().toJsonTree(json);
	}

	public static <T> T fromJsonTree(JsonElement json, Class<T> t) {
		return getGson().fromJson(json, t);
	}

	public static JsonObject parse(String json) {
		return (JsonObject) parser.parse(json);
	}

	public static JsonArray parseArray(String json) {
		JsonParser parser = new JsonParser();
		return (JsonArray) parser.parse(json);
	}

	public static <T> List<T> getJsonPropertyAsList(String json, String propertyName, Class<T> clazz) {
		JsonArray array = (JsonArray) parse(json).get(propertyName);
		List<T> list = new ArrayList<T>(array.size());
		for (JsonElement t : array) {
			list.add(fromJsonTree(t, clazz));
		}
		return list;
	}

	public static <T> List<T> fromList(String json, Class<T[]> clazz) {
		T[] t = getGson().fromJson(json, clazz);
		return Arrays.asList(t);
	}

	public static List<String> JsonArrayToListString(String json) {
		JsonArray jsonArray = parseArray(json);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < jsonArray.size(); i++) {
			list.add(jsonArray.get(i).getAsString());
		}
		return list;
	}

}