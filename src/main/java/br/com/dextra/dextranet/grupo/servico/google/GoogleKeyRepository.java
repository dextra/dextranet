package br.com.dextra.dextranet.grupo.servico.google;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class GoogleKeyRepository extends EntidadeRepository {

	public GoogleKey persiste(GoogleKey googleKey) {
		return super.persiste(googleKey);
	}

	public void remove(String id) {
		super.remove(id, GoogleKey.class);
	}

	public GoogleKey obtemPorId(String id) throws EntityNotFoundException {
		Entity googleKey = super.obtemPorId(id, GoogleKey.class);
		return new GoogleKey(googleKey);
	}

	public List<GoogleKey> lista() {
		List<GoogleKey> googleKey = new ArrayList<GoogleKey>();

		Iterable<Entity> entidades = obterKeyGoogleGrupos();
		for (Entity entidade : entidades) {
			googleKey.add(new GoogleKey(entidade));
		}

		return googleKey;
	}

	public <T extends Entidade> Iterable<Entity> obterKeyGoogleGrupos() {
		Query query = new Query(GoogleKey.class.getName());
		PreparedQuery pquery = this.datastore.prepare(query);

		return pquery.asIterable();
	}

}
