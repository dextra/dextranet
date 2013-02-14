package br.com.dextra.dextranet.usuario;

import br.com.dextra.dextranet.persistencia.Entidade;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonObject;

public class Usuario extends Entidade{
	
	private String id;
	
	private String nickName;
	
	private String email;
	
	private BlobKey blobKeyFoto;

	public Usuario(String id, String nickName, String email, BlobKey blobKeyFoto) {
		this.id = id;
		this.nickName = nickName;
		this.email = email;
		this.blobKeyFoto = blobKeyFoto;
	}

	public Usuario(Entity usuarioEntity) {
		if (usuarioEntity != null) {
			this.id = (String) usuarioEntity.getProperty(UsuarioFields.ID.getField());
			this.nickName = (String) usuarioEntity.getProperty(UsuarioFields.NICK_NAME.getField());
			this.email = (String) usuarioEntity.getProperty(UsuarioFields.EMAIL.getField());
			this.blobKeyFoto = (BlobKey) usuarioEntity.getProperty(UsuarioFields.BLOBKEY_FOTO.getField());
		}
	}
	
	public String getId() {
		return id;
	}
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public BlobKey getBlobKeyFoto() {
		return blobKeyFoto;
	}
	
	public void setBlobKeyFoto(BlobKey blobKeyFoto) {
		this.blobKeyFoto = blobKeyFoto;
	}
	
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));
		
		entidade.setProperty(UsuarioFields.ID.getField(), this.id);
		entidade.setProperty(UsuarioFields.NICK_NAME.getField(), this.getNickName());
		entidade.setProperty(UsuarioFields.EMAIL.getField(), this.getEmail());
		entidade.setProperty(UsuarioFields.BLOBKEY_FOTO.getField(), this.getBlobKeyFoto());
		
		return entidade;
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty(UsuarioFields.ID.getField(), this.id);
		json.addProperty(UsuarioFields.NICK_NAME.getField(), this.nickName);
		json.addProperty(UsuarioFields.EMAIL.getField(), this.email);
		json.addProperty(UsuarioFields.BLOBKEY_FOTO.getField(), this.getBlobKeyFoto().toString());
		
		return json;
	}
}
