package br.com.dextra.dextranet.comment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.gson.JsonObject;

public class CommentTest {

	@Test
	public void testCreateCommentAndAssertJson() {
		Comment comment = new Comment("Texto", "xyz", "post", true);
		JsonObject json = comment.toJson();

		assertEquals("Texto", json.get("conteudo").getAsString());
		assertEquals("post", json.get("idReference").getAsString());
		assertEquals("xyz", json.get("usuario").getAsString());
		assertTrue(json.get("tree").getAsBoolean());
	}

}
