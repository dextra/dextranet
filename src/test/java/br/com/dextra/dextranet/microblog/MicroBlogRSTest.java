package br.com.dextra.dextranet.microblog;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.dextra.dextranet.utils.JsonUtil;

import com.google.gson.JsonArray;

public class MicroBlogRSTest {

    public List<MicroPost> microPosts = new ArrayList<MicroPost>();

    @Test
    public void testSimplePost() {
        MicroBlogRS rs = getMicroBlogRS();
        rs.post("micromessage");
        JsonArray json = JsonUtil.parseArray(rs.get());
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

    @Test
    public void testTwoPosts() {
        MicroBlogRS rs = getMicroBlogRS();
        rs.post("micromessage1");
        rs.post("micromessage2");
        JsonArray json = JsonUtil.parseArray(rs.get());
        assertEquals(2, json.size());
        assertEquals("micromessage1", json.get(0).getAsJsonObject().get("text").getAsString());
        assertEquals("micromessage2", json.get(1).getAsJsonObject().get("text").getAsString());
    }
}
