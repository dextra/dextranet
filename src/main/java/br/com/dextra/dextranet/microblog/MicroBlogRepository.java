package br.com.dextra.dextranet.microblog;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class MicroBlogRepository extends EntidadeRepository {

	public List<MicroPost> buscarMicroPosts() {
		Query query = new Query(MicroPost.class.getName());
		PreparedQuery prepared = this.datastore.prepare(query);
		return toMicroPosts(prepared.asIterable());
	}

	public List<MicroPost> lista(EntidadeOrdenacao ... criterioOrdenacao) {
		List<MicroPost> microPosts = new ArrayList<MicroPost>();
		Iterable<Entity> lista = super.lista(MicroPost.class, criterioOrdenacao);
		for (Entity entity : lista) {
			microPosts.add(new MicroPost(entity));
		}

		return microPosts;
	}

	public void remove(String id) {
		super.remove(id, MicroPost.class);
	}

	private List<MicroPost> toMicroPosts(Iterable<Entity> asIterable) {
		List<MicroPost> listaMicroPosts = new ArrayList<MicroPost>();

		for (Entity entity : asIterable) {
			listaMicroPosts.add(new MicroPost(entity));
		}

		return listaMicroPosts;
	}

	public void salvar(MicroPost micropost) {
		this.persiste(micropost);
	}
}
