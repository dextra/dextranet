package br.com.dextra.utils;

import java.util.UUID;

public class Utils {

	static public String geraID() {
		String id = UUID.randomUUID().toString();
		return id;
	}

}
