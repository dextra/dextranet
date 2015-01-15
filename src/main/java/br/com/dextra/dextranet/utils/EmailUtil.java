package br.com.dextra.dextranet.utils;

import java.util.regex.Pattern;

public class EmailUtil {

	private EmailUtil() {
	}

	public static boolean isValid(String email) {
		Pattern emailRegex = Pattern.compile("[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,4}");
		return emailRegex.matcher(email).matches();
	}

}
