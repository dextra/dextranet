package br.com.dextra.dextranet.perfil;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.area.Area;
import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.unidade.Unidade;
import br.com.dextra.dextranet.usuario.UsuarioFields;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonObject;





public class Perfil extends Entidade {
	private String name;		 	// Obrigatorio
	private String nickName;		// Obrigatorio
	private String area; 			// Obrigatorio
	private String unit; 			// Obrigatorio
	private String branch;
	private String skype;
	private String gTalk;
	private String phoneResidence;
	private String phoneMobile; 	// Obrigatorio
	private String image;
	private boolean valido = true;  //Flag validação
	private String githubUser;

	private List<Area> areasPossiveis = new ArrayList<Area>();
	private List<Unidade> unidadesPossiveis = new ArrayList<Unidade>();

	public Perfil(String userId, String name, String nickName, String area,
			String unit, String branch, String skype, String gTalk,
			String phoneResidence, String phoneMobile, String image, String githubUser) {

		super(userId);
		this.name = name;
		this.nickName = nickName;
		this.area = area;
		this.unit = unit;
		this.branch = branch;
		this.skype = skype;
		this.gTalk = gTalk;
		this.phoneResidence = phoneResidence;
		this.phoneMobile = phoneMobile;
		this.image = image;
		this.githubUser = githubUser;
	}

	public Entity toEntity() {
		Entity entity = new Entity(this.getKey(this.getClass()));

		entity.setProperty(UsuarioFields.ID.getField(), this.id);
		entity.setProperty(PerfilFields.NAME.getField(), this.name);
		entity.setProperty(UsuarioFields.NICK_NAME.getField(), this.nickName);
		entity.setProperty(PerfilFields.AREA.getField(), this.area);
		entity.setProperty(PerfilFields.UNIT.getField(), this.unit);
		entity.setProperty(PerfilFields.BRANCH.getField(), this.branch);
		entity.setProperty(PerfilFields.SKYPE.getField(), this.skype);
		entity.setProperty(PerfilFields.GTALK.getField(), this.gTalk);
		entity.setProperty(PerfilFields.PHONERESIDENCE.getField(), this.phoneResidence);
		entity.setProperty(PerfilFields.PHONEMOBILE.getField(), this.phoneMobile);
		entity.setProperty(PerfilFields.GITHUBUSER.getField(), this.githubUser);

		return entity;
	}

	public Perfil(Entity entity) {
		if (entity != null){
			super.id = (String) entity.getProperty(UsuarioFields.ID.getField());
			this.name = (String) entity.getProperty(PerfilFields.NAME.getField());
			this.nickName = (String) entity.getProperty(UsuarioFields.NICK_NAME.getField());
			this.area = (String) entity.getProperty(PerfilFields.AREA.getField());
			this.unit = (String) entity.getProperty(PerfilFields.UNIT.getField());
			this.branch = (String) entity.getProperty(PerfilFields.BRANCH.getField());
			this.skype = (String) entity.getProperty(PerfilFields.SKYPE.getField());
			this.gTalk = (String) entity.getProperty(PerfilFields.GTALK.getField());
			this.phoneResidence = (String) entity.getProperty(PerfilFields.PHONERESIDENCE.getField());
			this.phoneMobile = (String) entity.getProperty(PerfilFields.PHONEMOBILE.getField());
			this.githubUser = (String) entity.getProperty(PerfilFields.GITHUBUSER.getField());
		}
	}

	// FIXME: Precisa mesmo disso aqui
	public Perfil(String id) {

		super.id = "id";
		this.name = "Campo Obrigatório";
		this.nickName = "nickName";
		this.areasPossiveis = new ArrayList<Area>();
		this.unidadesPossiveis = new ArrayList<Unidade>();
		this.area = "Campo Obrigatório";
		this.unit = "Campo Obrigatório";
		this.branch = "";
		this.skype = "";
		this.gTalk = "";
		this.phoneResidence = "";
		this.phoneMobile = "Campo Obrigatório";
		this.image = "";
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty(UsuarioFields.ID.getField(), this.id);
		json.addProperty(PerfilFields.NAME.getField(), this.name);
		json.addProperty(UsuarioFields.NICK_NAME.getField(), this.nickName);
		json.addProperty(PerfilFields.AREA.getField(), this.area);
		json.addProperty(PerfilFields.UNIT.getField(), this.unit);
		json.addProperty(PerfilFields.BRANCH.getField(), this.branch);
		json.addProperty(PerfilFields.SKYPE.getField(), this.skype);
		json.addProperty(PerfilFields.GTALK.getField(), this.gTalk);
		json.addProperty(PerfilFields.PHONERESIDENCE.getField(), this.phoneResidence);
		json.addProperty(PerfilFields.PHONEMOBILE.getField(), this.phoneMobile);
		return json;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getgTalk() {
		return gTalk;
	}

	public void setgTalk(String gTalk) {
		this.gTalk = gTalk;
	}

	public String getPhoneResidence() {
		return phoneResidence;
	}

	public void setPhoneResidence(String phoneResidence) {
		this.phoneResidence = phoneResidence;
	}

	public String getPhoneMobile() {
		return phoneMobile;
	}

	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Area> getAreasPossiveis() {
		return areasPossiveis;
	}

	public void setAreasPossiveis(List<Area> areasPossiveis) {
		this.areasPossiveis = areasPossiveis;
	}

	public List<Unidade> getUnidadesPossiveis() {
		return unidadesPossiveis;
	}

	public void setUnidadesPossiveis(List<Unidade> unidadesPossiveis) {
		this.unidadesPossiveis = unidadesPossiveis;
	}
}