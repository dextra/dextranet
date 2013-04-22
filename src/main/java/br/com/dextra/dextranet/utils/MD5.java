package br.com.dextra.dextranet.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static String hash(String conteudo) {
		try {
			byte[] conteudoEmBytes = conteudo.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] md5EmBytes = md.digest(conteudoEmBytes);

			// conversao obtida de
			// http://pt.gravatar.com/site/implement/images/java/
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md5EmBytes.length; ++i) {
				sb.append(Integer.toHexString((md5EmBytes[i] & 0xFF) | 0x100).substring(1, 3));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Erro ao gerar MD5: " + e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Erro ao gerar MD5: " + e.getMessage(), e);
		}
	}
}
