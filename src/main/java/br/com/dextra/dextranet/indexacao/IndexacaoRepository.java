package br.com.dextra.dextranet.indexacao;

import br.com.dextra.dextranet.conteudo.ConteudoIndexavel;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;
import br.com.dextra.dextranet.utils.IndexFacade;

public class IndexacaoRepository extends EntidadeRepository {

	public void indexar(ConteudoIndexavel conteudo) {
		IndexFacade.getIndex(conteudo.getClass().getName()).put(conteudo.toDocument());
	}

	public void removeIndex(String indexKey, String id) {
		IndexFacade.getIndex(indexKey).delete(id);
	}
}
