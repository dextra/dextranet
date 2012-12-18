package br.com.dextra.dextranet.rest.config;

import java.util.HashSet;
import java.util.Set;

import br.com.dextra.exemplo.ExemploRS;

public class Application extends javax.ws.rs.core.Application {

	/**
	 * Adicionando todas as classes referentes a servicos REST que irao existir
	 * nesta aplicacao.
	 */
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(ExemploRS.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<Object>();
		return singletons;
	}
}
