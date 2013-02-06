package br.com.dextra.dextranet.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

public class DataUtilsTest {
	
	@Test
	public void testaPrimeiroSegundoDoDia() {
		Calendar primeiroSegundoDeHoje = Calendar.getInstance();
		primeiroSegundoDeHoje.setTime(Data.primeiroSegundoDeHoje());

		assertEquals(0, primeiroSegundoDeHoje.get(Calendar.MILLISECOND));
		assertEquals(0, primeiroSegundoDeHoje.get(Calendar.SECOND));
		assertEquals(0, primeiroSegundoDeHoje.get(Calendar.MINUTE));
		assertEquals(0, primeiroSegundoDeHoje.get(Calendar.HOUR_OF_DAY));
		assertEquals(0, primeiroSegundoDeHoje.get(Calendar.HOUR));

		comparaDiaMesAno(primeiroSegundoDeHoje);
	}
	
	@Test
	public void testaUltimoSegundoDoDia() {
		Calendar ultimoSegundoDeHoje = Calendar.getInstance();
		ultimoSegundoDeHoje.setTime(Data.ultimoSegundoDeHoje());

		assertEquals(999, ultimoSegundoDeHoje.get(Calendar.MILLISECOND));
		assertEquals(59, ultimoSegundoDeHoje.get(Calendar.SECOND));
		assertEquals(59, ultimoSegundoDeHoje.get(Calendar.MINUTE));
		assertEquals(23, ultimoSegundoDeHoje.get(Calendar.HOUR_OF_DAY));
		assertEquals(11, ultimoSegundoDeHoje.get(Calendar.HOUR));

		comparaDiaMesAno(ultimoSegundoDeHoje);
	}

	public void comparaDiaMesAno(Calendar data) {
		Calendar hoje = Calendar.getInstance();
		
		assertEquals(hoje.get(Calendar.DAY_OF_MONTH), data.get(Calendar.DAY_OF_MONTH));
		assertEquals(hoje.get(Calendar.MONTH), data.get(Calendar.MONTH));
		assertEquals(hoje.get(Calendar.YEAR), data.get(Calendar.YEAR));
	}

	@Test
	public void testaComparacaoDeData() {
		Calendar hoje = Calendar.getInstance();

		assertTrue(Data.igualADataDeHoje(hoje.getTime()));

		Calendar outraData = Calendar.getInstance();
		outraData.set(2013, Calendar.JANUARY, 2, 12, 0, 0);

		assertTrue(Data.anteriorADataDeHoje(outraData.getTime()));
		assertFalse(Data.anteriorADataDeHoje(Data.primeiroSegundo(hoje.getTime())));
		assertFalse(Data.posteriorAHoje(outraData.getTime()));
		assertFalse(Data.anteriorADataDeHoje(hoje.getTime()));
	}
}
