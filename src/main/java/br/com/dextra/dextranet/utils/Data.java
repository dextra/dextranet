package br.com.dextra.dextranet.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

	public String setSegundoDaData(int seg) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.SECOND, seg);
		return formataDataPelaBiblioteca(c.getTime());
	}

	public static Date ultimoSegundoDeHoje() {
		return ultimoSegundo(new Date());
	}

	public static Date primeiroSegundoDeHoje() {
		return primeiroSegundo(new Date());
	}

	public static Date primeiroSegundo(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);

		return c.getTime();
	}

	public static Date ultimoSegundo(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(Calendar.MILLISECOND, 999);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.HOUR_OF_DAY, 23);

		return c.getTime();
	}

	public static Date stringParaData(String dataString) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return (Date) formatter.parse(dataString);
	}

	public static Boolean anteriorADataDeHoje(Date data) {
		Date primeiroSegundoDeHoje = primeiroSegundoDeHoje();

		return igualADataDeHoje(data) ? false : !primeiroSegundoDeHoje.before(primeiroSegundo(data));
	}

	public static Boolean igualADataDeHoje(Date data) {
		return primeiroSegundoDeHoje().equals(primeiroSegundo(data));
	}

	public static Boolean posteriorAHoje(Date data) {
		return igualADataDeHoje(data) ? false : !primeiroSegundoDeHoje().after(primeiroSegundo(data));
	}

	public static Boolean igualADataDeHojeOuAnterior(Date data) {
		Date primeiroSegundoDeHoje = primeiroSegundoDeHoje();
		return igualADataDeHoje(data) || !primeiroSegundoDeHoje.before(primeiroSegundo(data));
	}
}
