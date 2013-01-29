package br.com.dextra.dextranet.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Data {

	public String pegaData() {
		return formataDataPelaBiblioteca(new Date());
	}

	public String formataDataPelaBiblioteca(Date data) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH-mm-ss-EEE");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-2"));

		return simpleDateFormat.format(data);
	}

	@SuppressWarnings("deprecation")
	public String randomizaDiaDaData(Date data) {
		Date dataAtualiza = data;
		int day = (int) (Math.random() * (30 + 1));
		dataAtualiza.setDate(day);
		return null;
	}

	@SuppressWarnings("deprecation")
	public String setSegundoDaData(int seg){
		Date data = new Date();
		data.setSeconds(seg);
		return formataDataPelaBiblioteca(data);
	}
}
