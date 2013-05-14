package br.com.dextra.dextranet.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

public abstract class Messages {

	private static Properties props = new Properties();
	private static InputStream in = null;

	static {
		in = Messages.class.getClassLoader().getResourceAsStream("messages.properties");
		try {
			props.load(new InputStreamReader(in));
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getMessage(String key) {
		return props.get(key).toString();
	}

	public static String getMessage(String key, Object... args) {
		return MessageFormat.format((String) Messages.getMessage(key), args);
	}

}
