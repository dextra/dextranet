package br.com.dextra.dextranet.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.com.dextra.dextranet.rest.config.Application;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.google.appengine.repackaged.org.joda.time.DateTimeZone;

public class TimeMachine {

	public static final String DATA_HORA = "dd/MM/yyyy HH:mm";

	public static final String DATA = "dd/MM/yyyy";

	private SimpleDateFormat formatoDataHora = new SimpleDateFormat(DATA_HORA, Application.BRASIL);

	private SimpleDateFormat formatoData = new SimpleDateFormat(DATA, Application.BRASIL);

	public Date dataAtual() {
		
		DateTimeZone zone = DateTimeZone.forID("America/Sao_Paulo"); 
		DateTime dateTime = new DateTime(zone);
		
		return dateTime.toLocalDateTime().toDateTime().toDate();
		
	}
	
	public Date inicioDoDia(Date data) {
		Calendar inicioDoDia = new GregorianCalendar();
		inicioDoDia.setTime(data);
		inicioDoDia.set(Calendar.HOUR, 0);
		inicioDoDia.set(Calendar.MINUTE, 0);
		inicioDoDia.set(Calendar.SECOND, 0);
		inicioDoDia.set(Calendar.MILLISECOND, 0);

		formatoDataHora.setTimeZone(Application.SAO_PAULO);
		try {
			return formatoDataHora.parse(inicioDoDia.getTime().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return inicioDoDia.getTime();
	}

	public Date fimDoDia(Date data) {
		Calendar fimDoDia = new GregorianCalendar();
		fimDoDia.setTime(data);
		fimDoDia.set(Calendar.HOUR, 23);
		fimDoDia.set(Calendar.MINUTE, 59);
		fimDoDia.set(Calendar.SECOND, 59);
		fimDoDia.set(Calendar.MILLISECOND, 99);

		formatoDataHora.setTimeZone(Application.SAO_PAULO);
		try {
			return formatoDataHora.parse(fimDoDia.getTime().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fimDoDia.getTime();
	}

	public String formataData(Date data) {
		return formatoDataHora.format(data);
	}

	public Date tranformaEmData(String data) {
		try {
			SimpleDateFormat formatter = this.formatoData;
			if (data.length() > DATA.length()) {
				formatter = this.formatoDataHora;
			}

			return formatter.parse(data);
		} catch (ParseException e) {
			throw new RuntimeException("Erro ao realizar parse da data: " + data);
		}
	}

	public Date diasParaFrente(int dias) {
		return this.diasParaFrente(this.dataAtual(), dias);
	}

	public Date diasParaFrente(Date data, int dias) {
		Calendar diasPraFrente = new GregorianCalendar();
		diasPraFrente.setTime(data);
		diasPraFrente.add(Calendar.DAY_OF_MONTH, dias);

		return diasPraFrente.getTime();
	}

	public Date diasParaAtras(int dias) {
		return this.diasParaAtras(this.dataAtual(), dias);
	}

	public Date diasParaAtras(Date data, int dias) {
		Calendar diasPraFrente = new GregorianCalendar();
		diasPraFrente.setTime(data);
		diasPraFrente.add(Calendar.DAY_OF_MONTH, dias * -1);

		return diasPraFrente.getTime();
	}
}
