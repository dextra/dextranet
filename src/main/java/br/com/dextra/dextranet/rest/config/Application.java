package br.com.dextra.dextranet.rest.config;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import br.com.dextra.dextranet.area.AreaRS;
import br.com.dextra.dextranet.banner.BannerRS;
import br.com.dextra.dextranet.indexacao.IndexacaoRS;
import br.com.dextra.dextranet.microblog.MicroBlogRS;
import br.com.dextra.dextranet.post.PostRS;
import br.com.dextra.dextranet.post.comment.CommentRS;
import br.com.dextra.dextranet.post.curtida.CurtidaRS;
import br.com.dextra.dextranet.unidade.UnidadeRS;
import br.com.dextra.dextranet.usuario.UsuarioRS;

public class Application extends javax.ws.rs.core.Application {

	public static final String JSON_UTF8 = "application/json;charset=UTF-8";

	public static final Locale BRASIL = new Locale("pt", "BR");

	/**
	 * Adicionando todas as classes referentes a servicos REST que irao existir
	 * nesta aplicacao.
	 */
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(PostRS.class);
		classes.add(CurtidaRS.class);
		classes.add(IndexacaoRS.class);
		classes.add(CommentRS.class);

		classes.add(BannerRS.class);
		classes.add(AreaRS.class);
		classes.add(UnidadeRS.class);
		classes.add(UsuarioRS.class);
		classes.add(JacksonConfig.class);

		classes.add(MicroBlogRS.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<Object>();
		return singletons;
	}
}
