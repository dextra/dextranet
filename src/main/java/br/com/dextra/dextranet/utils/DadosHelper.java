package br.com.dextra.dextranet.utils;

import java.io.InputStream;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

public class DadosHelper {

	public String removeConteudoJS(String conteudoHTML) {

		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("antisamy.xml");

			AntiSamy as = new AntiSamy();
			Policy policy = null;
			policy = Policy.getInstance(inputStream);

			try {
				return as.scan(conteudoHTML, policy).getCleanHTML();
			} catch (ScanException e) {
				throw new RuntimeException("Erro ao remover codigo indevido do conteudo (Scan).", e);
			}

		} catch (PolicyException e) {
			throw new RuntimeException("Erro ao remover codigo indevido do conteudo (Policy).", e);

		}
	}

}
