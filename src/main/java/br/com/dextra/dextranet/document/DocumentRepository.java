package br.com.dextra.dextranet.document;

import br.com.dextra.dextranet.persistencia.BaseRepository;
import br.com.dextra.dextranet.persistencia.ConteudoIndexavel;
import br.com.dextra.dextranet.utils.IndexFacade;

public class DocumentRepository extends BaseRepository {

    public void indexar(ConteudoIndexavel conteudo) {
        IndexFacade.getIndex(conteudo.getClass().getName()).add(conteudo.toDocument());
    }

    public void removeIndex(String indexKey, String id) {
        IndexFacade.getIndex(indexKey).remove(id);
    }
}
