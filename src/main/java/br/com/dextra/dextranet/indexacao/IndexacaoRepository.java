package br.com.dextra.dextranet.indexacao;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.dextra.dextranet.conteudo.Conteudo;
import br.com.dextra.dextranet.conteudo.ConteudoIndexavel;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.ScoredDocument;

public class IndexacaoRepository extends EntidadeRepository {

	private Logger logger = LoggerFactory.getLogger(IndexacaoRepository.class);

	public void indexar(ConteudoIndexavel conteudo) {
		IndexFacade.getIndex(conteudo.getClass().getName()).put(conteudo.toDocument());
	}

	public void removeIndexacao(String indexKey, String id) {
		IndexFacade.getIndex(indexKey).delete(id);
	}

	public <T extends Conteudo> List<T> buscar(Class<T> clazz, Query query) {
		Index idx = IndexFacade.getIndex(clazz.getName());

		List<T> ret = new ArrayList<T>();
		Collection<ScoredDocument> results = idx.search(query).getResults();

		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor(Entity.class);
			for (ScoredDocument doc : results) {
				String id = doc.getFields("id").iterator().next().getText();
				Entity entity = obtemPorId(id, clazz);
				ret.add(constructor.newInstance(entity));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

		return ret;
	}

	public <T extends Conteudo> List<T> buscar(Class<T> clazz, String query) {
		return buscar(clazz, Query.newBuilder().build(query));
	}

}
