package br.com.dextra.dextranet.rest.config;

import java.util.HashSet;

import java.util.Set;

import br.com.dextra.dextranet.comment.CommentRS;
import br.com.dextra.dextranet.curtida.CurtidaRS;
import br.com.dextra.dextranet.document.DocumentRS;
import br.com.dextra.dextranet.post.PostRS;
import br.com.dextra.dextranet.usuario.UsuarioRS;


public class Application extends javax.ws.rs.core.Application {

	/**
	 * Adicionando todas as classes referentes a servicos REST que irao existir
	 * nesta aplicacao.
	 */
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(PostRS.class);
		classes.add(CurtidaRS.class);
		classes.add(DocumentRS.class);
		classes.add(CommentRS.class);
		classes.add(UsuarioRS.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<Object>();
		return singletons;
	}
}
