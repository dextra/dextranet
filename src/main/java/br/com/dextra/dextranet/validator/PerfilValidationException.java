package br.com.dextra.dextranet.validator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class PerfilValidationException extends PerfilException {
	private static final long serialVersionUID = 427137108282395456L;

	public PerfilValidationException(String kind, String key) {
		this();
		this.add(this.getClass(), key);
	}

	public PerfilValidationException(String message) {
		super(message);
		this.setData(new JsonArray());
	}

	public PerfilValidationException(Throwable throwable) {
		super(throwable);
		this.setData(new JsonArray());
	}

	public PerfilValidationException() {
		super("validation error");
		this.setData(new JsonArray());
	}

	public JsonArray getErrors() {
		return (JsonArray) this.getData();
	}

	public void add(Class<?> clazz, String msg, String... args) {
		this.add(create("entidade", clazz.getSimpleName(), "msg", msg, "args",
				args));
	}

	public void add(Class<?> clazz, String msg) {
		this.add(create("entidade", clazz.getSimpleName(), "msg", msg));
	}

	private void add(JsonObject create) {
		this.getErrors().add(create);
	}

	private static JsonObject create(Object... values) {
		if (values.length % 2 != 0) {
			throw new RuntimeException("invalid length");
		}
		JsonObject ret = new JsonObject();
		for (int i = 0; i < values.length; i += 2) {
			String name = (String) values[i];
			Object value = values[i + 1];
			if (value == null) {
				value = JsonNull.INSTANCE;
			}
			if (!(value instanceof JsonElement)) {
				value = createBasic(value);
			}
			ret.add(name, (JsonElement) value);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	private static JsonElement createBasic(Object value) {
		if (value == null) {
			return JsonNull.INSTANCE;
		} else if (value instanceof Boolean) {
			return new JsonPrimitive(((Boolean) value));
		} else if (value instanceof Number) {
			return new JsonPrimitive((Number) value);
		} else if (value instanceof String) {
			return new JsonPrimitive((String) value);
		} else if (value instanceof Iterable) {
			Iterable<Object> it = (Iterable<Object>) value;
			JsonArray ret = new JsonArray();
			for (Object object : it) {
				ret.add(createBasic(object));
			}
			return ret;
		} else if (value instanceof String[]) {
			JsonArray jsonArray = new JsonArray();
			for (String actualValue : (String[]) value) {
				jsonArray.add(new JsonPrimitive(actualValue));
			}
			return jsonArray;
		}
		throw new UnsupportedOperationException("unsupported type: " + value);
	}

	public boolean hasErrors() {
		return this.getErrors().size() > 0;
	}
}
