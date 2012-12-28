package br.com.dextra.utils;

import java.util.Date;
import java.util.UUID;

import br.com.dextra.persistencia.PostFields;

import com.google.appengine.api.datastore.Entity;

public class Utils {

	static public String geraID() {
		String id = UUID.randomUUID().toString();
		return id;
	}

	static public Date randomizaDiaDaData(Date data) {
		Date dataAtualiza = data;
		int day = (int) (Math.random() * (30 + 1));
		dataAtualiza.setDate(day);
		return dataAtualiza;
	}

	// Wed Dec 26 10:44:10 UTC 2012
	// 0123456789012345678901234567
	// 111111111122222222

	static public String formataData(String data) {
		StringBuilder dataFormatada = new StringBuilder();
		dataFormatada.append(data.substring(24, 28));
		dataFormatada.append("/");
		dataFormatada.append(formataMes(data.substring(4, 7)));
		dataFormatada.append("/");
		dataFormatada.append(data.substring(8, 10));
		dataFormatada.append("-");
		dataFormatada.append(data.substring(11, 19));
		dataFormatada.append("-");
		dataFormatada.append(data.substring(0, 3));

		return dataFormatada.toString();
	}

	private static Object formataMes(String mes) {

		if (mes.equals("Jan"))
			return "01";
		else if (mes.equals("Feb"))
			return "02";
		else if (mes.equals("Mar"))
			return "03";
		else if (mes.equals("Apr"))
			return "04";
		else if (mes.equals("May"))
			return "05";
		else if (mes.equals("Jun"))
			return "06";
		else if (mes.equals("Jul"))
			return "07";
		else if (mes.equals("Aug"))
			return "08";
		else if (mes.equals("Sep"))
			return "09";
		else if (mes.equals("Oct"))
			return "10";
		else if (mes.equals("Nov"))
			return "11";
		else
			return "12";

	}

}
