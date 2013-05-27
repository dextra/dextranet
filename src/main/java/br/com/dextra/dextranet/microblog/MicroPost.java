package br.com.dextra.dextranet.microblog;

import java.util.Date;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.seguranca.AutenticacaoService;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.utils.TimeMachine;

import com.google.appengine.api.datastore.Entity;

public class MicroPost extends Entidade {

	private String texto;
	private Date data;
	private String usuario;
	private String usuarioMD5;

	public MicroPost(String texto) {
		this.texto = texto;
		this.usuario = obtemUsuarioLogado();
		this.usuarioMD5 = Usuario.geraMD5(this.usuario);
		this.data = new TimeMachine().dataAtual();
	}

	public MicroPost(String texto, Date data) {
		this.texto = texto;
		this.data = data;
		this.usuario = obtemUsuarioLogado();
		this.usuarioMD5 = Usuario.geraMD5(this.usuario);
	}

	public MicroPost(Entity microPostEntity) {
		super((String) microPostEntity.getProperty(MicroBlogFields.ID
				.getField()));
		this.texto = (String) microPostEntity.getProperty(MicroBlogFields.TEXTO
				.getField());
		this.data = (Date) microPostEntity.getProperty(MicroBlogFields.DATA
				.getField());
		this.usuario = (String) microPostEntity.getProperty(MicroBlogFields.USUARIO
				.getField());
		this.usuarioMD5 = (String) microPostEntity.getProperty(MicroBlogFields.USUARIOMD5
				.getField());
	}

	public String getTexto() {
		return texto;
	}

	public Date getData() {
		return data;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public String getUsuarioMD5() {
		return usuarioMD5;
	}
	
	protected String obtemUsuarioLogado() {
		return AutenticacaoService.identificacaoDoUsuarioLogado();
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(getKey(MicroPost.class));
		entidade.setProperty(MicroBlogFields.ID.getField(), getId());
		entidade.setProperty(MicroBlogFields.TEXTO.getField(), getTexto());
		entidade.setProperty(MicroBlogFields.DATA.getField(), getData());
		entidade.setProperty(MicroBlogFields.USUARIO.getField(), getUsuario());
		entidade.setProperty(MicroBlogFields.USUARIOMD5.getField(), getUsuarioMD5());
		return entidade;
	}

	@Override
	public String toString() {
		return "MicroPost [texto=" + texto + ", id=" + id + "]";
	}
}