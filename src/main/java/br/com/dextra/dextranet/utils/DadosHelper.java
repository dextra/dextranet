package br.com.dextra.dextranet.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

public class DadosHelper {

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
