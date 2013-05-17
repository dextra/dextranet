package br.com.dextra.dextranet.conteudo;

import com.google.appengine.api.search.Document;

public interface ConteudoIndexavel {

	public abstract Document toDocument();

}
