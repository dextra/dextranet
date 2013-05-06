package br.com.dextra.dextranet.microblog;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;

import com.google.gson.JsonArray;

public class MicroBlogRSTest {

	public List<MicroPost> microPosts = new ArrayList<MicroPost>();

	@Ignore
	public void testSimplePost() {
		MicroBlogRS rs = getMicroBlogRS();
		rs.post("micromessage");
		// FIXME
		// JsonArray json = JsonUtil.parseArray(rs.get());
		JsonArray json = new JsonArray();
		assertEquals(1, json.size());
		assertEquals("micromessage", json.get(0).getAsJsonObject().get("text").getAsString());
	}

	private MicroBlogRS getMicroBlogRS() {
		return new MicroBlogRS() {

			@Override
			protected MicroBlogRepository getMicroBlogRepository() {
				return new MicroBlogRepository() {

					@Override
					public void salvar(MicroPost micropost) {
						microPosts.add(micropost);
					}
				};
			}

		};
	}

	@Ignore
	public void testTwoPosts() {
		MicroBlogRS rs = getMicroBlogRS();
		rs.post("micromessage1");
		rs.post("micromessage2");
		// FIXME
		// JsonArray json = JsonUtil.parseArray(rs.get());
		JsonArray json = new JsonArray();
		assertEquals(2, json.size());
		assertEquals("micromessage1", json.get(0).getAsJsonObject().get("text").getAsString());
		assertEquals("micromessage2", json.get(1).getAsJsonObject().get("text").getAsString());
	}
}
