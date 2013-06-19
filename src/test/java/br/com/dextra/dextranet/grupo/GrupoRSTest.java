package br.com.dextra.dextranet.grupo;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Test;

import br.com.dextra.dextranet.grupos.GrupoRepository;
import br.com.dextra.dextranet.grupos.MembroRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

public class GrupoRSTest extends TesteIntegracaoBase {
	private GrupoRepository repositorioGrupo = new GrupoRepository();
	private MembroRepository repositorioMembro = new MembroRepository();

	@After
	public void removeDadosInseridos() {
		this.limpaGrupoInseridos(repositorioGrupo);
		this.limpaMembroInseridos(repositorioMembro);
	}

	public void test() {
		fail("Not yet implemented");
	}

}
