package br.com.dextra.dextranet.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.junit.Test;

public class TimeMachineTest {

	private TimeMachine timeMachine = new TimeMachine();

	@Test
	public void testaFormataData() {
		// no gregorian calendar o mes eh de 0 a 11
		Calendar calendar = new GregorianCalendar(2013, 3, 10, 10, 12);
		Assert.assertEquals("10/04/2013 10:12", timeMachine.formataData(calendar.getTime()));
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
		Calendar calendar = new GregorianCalendar(2013, 3, 10, 10, 12);
		Assert.assertEquals(calendar.getTime(), timeMachine.transformaEmData("10/04/2013 10:12"));
	}

	@Test
	public void testaInicioDoDia() {
		Calendar calendar = new GregorianCalendar(2013, 3, 10);
		Date inicioDoDia = timeMachine.inicioDoDia(calendar.getTime());
		Assert.assertEquals("10/04/2013 00:00", timeMachine.formataData(inicioDoDia));
	}

	@Test
	public void testaFimDoDia() {
		Calendar calendar = new GregorianCalendar(2013, 3, 10);
		Date inicioDoDia = timeMachine.fimDoDia(calendar.getTime());
		Assert.assertEquals("10/04/2013 23:59", timeMachine.formataData(inicioDoDia));
	}

	@Test
	public void testaDiasPraFrente() {
		Date dataBase = timeMachine.transformaEmData("10/04/2013 00:00");
		Date diasPraFrente = timeMachine.diasParaFrente(dataBase, 5);

		Assert.assertEquals("15/04/2013 00:00", timeMachine.formataData(diasPraFrente));
	}

	@Test
	public void testaDiasPraTras() {
		Date dataBase = timeMachine.transformaEmData("10/04/2013 00:00");
		Date diasPraTras = timeMachine.diasParaAtras(dataBase, 5);

		Assert.assertEquals("05/04/2013 00:00", timeMachine.formataData(diasPraTras));
	}

}
