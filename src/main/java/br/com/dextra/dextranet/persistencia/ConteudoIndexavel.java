package br.com.dextra.dextranet.persistencia;

import com.google.appengine.api.search.Document;

public interface ConteudoIndexavel {

	public abstract Document toDocument();

}
