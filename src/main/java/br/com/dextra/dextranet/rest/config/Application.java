package br.com.dextra.dextranet.rest.config;

import java.util.HashSet;
import java.util.Set;

import br.com.dextra.dextranet.rest.SampleRS;

public class Application extends javax.ws.rs.core.Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(SampleRS.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<Object>();
		return singletons;
	}
}
