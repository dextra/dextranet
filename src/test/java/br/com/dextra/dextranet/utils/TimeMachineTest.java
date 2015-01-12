package br.com.dextra.dextranet.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.rest.config.Application;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.google.appengine.repackaged.org.joda.time.DateTimeZone;

public class TimeMachineTest {

	private TimeMachine timeMachine = new TimeMachine();

	private final DateTimeZone zone = DateTimeZone.forID(Application.TIMEZONE_SAO_PAULO);

	@Test
	public void testaFormataData() {
		// no gregorian calendar o mes eh de 0 a 11
		Calendar calendar = new GregorianCalendar(2013, 3, 10, 10, 12, 00);
		Assert.assertEquals("10/04/2013 10:12:00", timeMachine.formataData(calendar.getTime()));
	}

	@Test
	public void testaTransformaEmDataSimples() {
		// no gregorian calendar o mes eh de 0 a 11
		Calendar calendar = new GregorianCalendar(2013, 3, 10);
		Assert.assertEquals(calendar.getTime(), timeMachine.transformaEmData("10/04/2013"));
	}

	@Test
	public void testaTransformaEmDataCompleta() {
		// no gregorian calendar o mes eh de 0 a 11
		Calendar calendar = new GregorianCalendar(2013, 3, 10, 10, 12, 00);
		Assert.assertEquals(calendar.getTime(), timeMachine.transformaEmData("10/04/2013 10:12:00"));
	}

	@Test
	public void testaInicioDoDia() {
		DateTime dateTime = new DateTime(2013, 03, 10, 15, 20, 00);
		dateTime = dateTime.withZone(zone);
		Date inicioDoDia = timeMachine.inicioDoDia(dateTime.toLocalDateTime().toDateTime().toDate());
		Assert.assertEquals("10/03/2013 00:00:00", timeMachine.formataData(inicioDoDia));
	}

	@Test
	public void testaFimDoDia() {
		DateTime dateTime = new DateTime(2013, 03, 10, 15, 20, 00);
		dateTime = dateTime.withZone(zone);
		Date fimDoDia = timeMachine.fimDoDia(dateTime.toLocalDateTime().toDateTime().toDate());
		Assert.assertEquals("10/03/2013 23:59:59", timeMachine.formataData(fimDoDia));
	}

	@Test
	public void testaDiasPraFrente() {
		Date dataBase = timeMachine.transformaEmData("10/04/2013 00:00:00");
		Date diasPraFrente = timeMachine.diasParaFrente(dataBase, 5);

		Assert.assertEquals("15/04/2013 00:00:00", timeMachine.formataData(diasPraFrente));
	}

	@Test
	public void testaDiasPraTras() {
		Date dataBase = timeMachine.transformaEmData("10/04/2013 00:00:00");
		Date diasPraTras = timeMachine.diasParaAtras(dataBase, 5);

		Assert.assertEquals("05/04/2013 00:00:00", timeMachine.formataData(diasPraTras));
	}

}
