package br.com.dextra.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class Utils {

	static public String geraID() {
		String id = UUID.randomUUID().toString();
		return id;
	}

	public static String pegaData()
	{
		Date data = new Date();
		return formataPelaBiblioteca(data);
	}

	public static String formataPelaBiblioteca(Date data) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss-EEE");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-2"));

		String postDate = simpleDateFormat.format(data);

		return postDate;
	}

	@SuppressWarnings("deprecation")
	static public Date randomizaDiaDaData(Date data) {
		Date dataAtualiza = data;
		int day = (int) (Math.random() * (30 + 1));
		dataAtualiza.setDate(day);
		return dataAtualiza;
	}
}
