package br.com.dextra.dextranet.releasenotes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ReleaseNotes implements Serializable {
	private static final long serialVersionUID = -2892451183366761785L;
	private List<ReleaseNote> notes = new ArrayList<ReleaseNote>();

	public void add(String title, String link, String date) {
		notes.add(new ReleaseNote(title, link, date));
	}

	public JsonArray toJsonArray() {
		JsonArray jsonArray = new JsonArray();

		for (ReleaseNote note : notes) {
			jsonArray.add(note.toJson());
		}

		return jsonArray;
	}

	public class ReleaseNote implements Serializable {
		private static final long serialVersionUID = -625864681084167146L;
		private String title;
		private String link;
		private String date;

		public ReleaseNote(String title, String link, String date) {
			super();
			this.title = title;
			this.link = link;
			this.date = date;
		}

		public JsonObject toJson() {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("title", title);
			jsonObject.addProperty("link", link);
			jsonObject.addProperty("date", date);
			return jsonObject;
		}
	}

}
