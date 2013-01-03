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
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-hh:mm:ss-EEE");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-2"));

		String postDate = simpleDateFormat.format(data);

		//return formataData(postDate);
		return postDate;
	}

	static public String formataData(String data) {
		//retorna 2013/01/03-10:47:08-Thu
		StringBuilder dataFormatada = new StringBuilder();
		dataFormatada.append(data.substring(0,20));
		dataFormatada.append(formataDiaSemana(data.substring(20,23)));

		return dataFormatada.toString();
	}

	private static Object formataDiaSemana(String dia) {

		if (dia.equals("Seg"))
			return "Mon";
		else if (dia.equals("Ter"))
			return "Tue";
		else if (dia.equals("Qua"))
			return "Wed";
		else if (dia.equals("Qui"))
			return "Thu";
		else if (dia.equals("Sex"))
			return "Fri";
		else if (dia.equals("Sab"))
			return "Sat";
		else
			return "Sun";
	}

	@SuppressWarnings("deprecation")
	static public Date randomizaDiaDaData(Date data) {
		Date dataAtualiza = data;
		int day = (int) (Math.random() * (30 + 1));
		dataAtualiza.setDate(day);
		return dataAtualiza;
	}
}
