package br.com.dextra.dextranet.utils;

import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

public class ConteudoHTML {

	private String conteudo;

	public ConteudoHTML(String conteudo) {
		this.conteudo = conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String removeJavaScript() {

		if (StringUtils.isNotEmpty(this.conteudo)) {
			try {
				InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("antisamy.xml");

				AntiSamy as = new AntiSamy();
				Policy policy = null;
				policy = Policy.getInstance(inputStream);

				try {
					return as.scan(this.conteudo, policy).getCleanHTML();
				} catch (ScanException e) {
					throw new RuntimeException("Erro ao remover codigo indevido do conteudo (Scan).", e);
				}

			} catch (PolicyException e) {
				throw new RuntimeException("Erro ao remover codigo indevido do conteudo (Policy).", e);

			}
		}

		return this.conteudo;
	}

}
