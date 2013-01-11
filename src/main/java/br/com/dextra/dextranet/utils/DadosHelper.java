package br.com.dextra.dextranet.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

public class DadosHelper {

	public static String geraID() {
		String id = UUID.randomUUID().toString();
		return id;
	}

	public static String pegaData() {
		return formataPelaBiblioteca(new Date());
	}

	public static String formataPelaBiblioteca(Date data) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss-EEE");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-2"));

		return simpleDateFormat.format(data);
	}

	@SuppressWarnings("deprecation")
	public static Date randomizaDiaDaData(Date data) {
		Date dataAtualiza = data;
		int day = (int) (Math.random() * (30 + 1));
		dataAtualiza.setDate(day);
		return dataAtualiza;
	}

	public String removeConteudoJS(String conteudoHTML) {
		Properties properties = new Properties();

		try {
			// FIXME: nao eh legal deixar o src como referencia
			properties.load(new FileInputStream("src/main/resources/config.properties"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Erro ao remover codigo indevido do conteudo.", e);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao remover codigo indevido do conteudo.", e);
		}

		AntiSamy as = new AntiSamy();
		Policy policy = null;

		try {
			policy = Policy.getInstance(properties.getProperty("antisamy.policyXML"));
		} catch (PolicyException e) {
			throw new RuntimeException("Erro ao remover codigo indevido do conteudo.", e);
		}

		try {
			return as.scan(conteudoHTML, policy).getCleanHTML();
		} catch (ScanException e) {
			throw new RuntimeException("Erro ao remover codigo indevido do conteudo.", e);
		} catch (PolicyException e) {
			throw new RuntimeException("Erro ao remover codigo indevido do conteudo.", e);
		}
	}

}
