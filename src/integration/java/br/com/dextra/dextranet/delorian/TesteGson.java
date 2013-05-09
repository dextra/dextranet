package br.com.dextra.dextranet.delorian;

import junit.framework.Assert;


import org.junit.Before;
import org.junit.Test;

import br.com.dextra.dextranet.conteudo.post.Post;

import com.google.gson.Gson;

public class TesteGson {

	/* JSON de resposta ao criar um POST
	 * {"titulo":"titulo",
		"quantidadeDeComentarios":0,
		"dataDeAtualizacao":"May 8, 2013 4:12:47 PM",
		"usuario":"dextranet",
		"usuarioMD5":"39566cf6ac41da40deb7c6452a9ed94b",
		"conteudo":"conteudo",
		"dataDeCriacao":"May 8, 2013 4:12:47 PM",
		"quantidadeDeCurtidas":0,
		"usuariosQueCurtiram":[],
		"id":"282aa8a1-9015-41c3-ab8e-aaa8b1e04c22"}*/

	private Post novoPost;
	private Gson gson;
	private String json;


	@Before
	public void IniciaValores(){
		novoPost = new Post("dextranet", "titulo", "conteudo");
		gson = new Gson();
		json = new String(gson.toJson(novoPost));

	}

	@Test
	public void testaSerializacao(){
		Assert.assertNotSame("{\"titulo\":titulo,\"conteudo\":\"conteudo\"}", json);
	}

	@Test
	public void testaDeserializacao(){
		Post post = gson.fromJson(json, Post.class); // retorna um objeto Post.
		Assert.assertEquals(novoPost, post);
	}

}
