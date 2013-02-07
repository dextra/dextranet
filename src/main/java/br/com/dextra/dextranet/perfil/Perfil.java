package br.com.dextra.dextranet.perfil;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.area.Area;
import br.com.dextra.dextranet.unidade.Unidade;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

public class Perfil {
	private String userId; // Obrigatorio
	private String name; // Obrigatorio
	private String area; // Obrigatorio
	private String unit; // Obrigatorio
	private String branch;
	private String skype;
	private String gTalk;
	private String phoneResidence;
	private String phoneMobile; // Obrigatorio
	private String image;
	private boolean valido = true; //Flag validação

	private List<Area> areasPossiveis = new ArrayList<Area>();
	private List<Unidade> unidadesPossiveis = new ArrayList<Unidade>();

	private String nickName;

	public Perfil(String userId, String name, String nickName, String area,
			String unit, String branch, String skype, String gTalk,
			String phoneResidence, String phoneMobile, String image) {
		super();

		this.userId = userId;

		this.name = name;
		this.nickName = nickName;
		this.area = area;
		this.unit = unit;
		this.branch = branch;
		this.skype = skype;
		this.gTalk = gTalk;
		this.phoneResidence = phoneResidence;
		this.phoneMobile = phoneMobile;
		this.phoneMobile = phoneMobile;
		this.image = image;
	}

	public Entity toEntity() {
		Entity entity = new Entity(KeyFactory.createKey(this.getClass()
				.getName(), this.userId));
		entity.setProperty("id", this.getId());
		entity.setProperty("name", this.getName());
		entity.setProperty("nickName", this.getNickName());
		entity.setProperty("area", this.getArea());
		entity.setProperty("unit", this.getUnit());
		entity.setProperty("branch", this.getBranch());
		entity.setProperty("skype", this.getSkype());
		entity.setProperty("gTalk", this.getgTalk());
		entity.setProperty("phoneResidence", this.getPhoneResidence());
		entity.setProperty("phoneMobile", this.getPhoneMobile());
		entity.setProperty("image", this.getImage());
		return entity;
	}

	public Perfil(Entity entity) {
		this.userId = (String) entity.getProperty("id");
		this.name = (String) entity.getProperty("name");
		this.nickName = (String) entity.getProperty("nickName");
		this.area = (String) entity.getProperty("area");
		this.unit = (String) entity.getProperty("unit");
		this.branch = (String) entity.getProperty("branch");
		this.skype = (String) entity.getProperty("skype");
		this.gTalk = (String) entity.getProperty("gTalk");
		this.phoneResidence = (String) entity.getProperty("phoneResidence");
		this.phoneMobile = (String) entity.getProperty("phoneMobile");
		this.image = (String) entity.getProperty("image");
	}

	public String getId() {
		return userId;
	}

	public void setId(String id) {
		this.userId = id;
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

		@Override
	public String toString() {
		return "Perfil [userId=" + userId + ", name=" + name + ", area=" + area
				+ ", unit=" + unit + ", branch=" + branch + ", skype=" + skype
				+ ", gTalk=" + gTalk + ", phoneResidence=" + phoneResidence
				+ ", phoneMobile=" + phoneMobile + ", image=" + image
				+ ", valido=" + valido + ", nickName=" + nickName + "]";
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