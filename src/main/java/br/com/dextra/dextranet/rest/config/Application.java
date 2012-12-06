package br.com.dextra.dextranet.rest.config;

import java.util.HashSet;
import java.util.Set;

import br.com.dextra.dextranet.rest.SimpleRS;

public class Application extends javax.ws.rs.core.Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(SimpleRS.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<Object>();
		return singletons;
	}
}
