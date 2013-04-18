package br.com.dextra.dextranet.banner;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class BannerRepository extends EntidadeRepository {

	public void remove(String id) {
		super.remove(id, Banner.class);
	}

	public Banner obtemPorId(String id) throws EntityNotFoundException {
		Entity banner = super.obtemPorId(id, Banner.class);
		return new Banner(banner);
	}

	public List<Banner> lista(EntidadeOrdenacao... criterioOrdenacao) {
		List<Banner> banners = new ArrayList<Banner>();

		Iterable<Entity> entidades = super.lista(Banner.class, criterioOrdenacao);
		for (Entity entidade : entidades) {
			banners.add(new Banner(entidade));
		}

		return banners;
	}

}
