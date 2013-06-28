package br.com.dextra.dextranet.releasenotes;

import java.util.List;

import br.com.http.RequestParameters;
import br.com.repositories.GithubIssue;
import br.com.repositories.GithubRepositoryClient;

public class ReleaseNotesRepository {

	public void atualizaLista() {
		GithubRepositoryClient repositoryClient = new GithubRepositoryClient(null);

		RequestParameters parameters = new RequestParameters();

		List<GithubIssue> issues = repositoryClient.findRepositoryIssues("dextra", "dextranet", parameters);

		for (GithubIssue githubIssue : issues) {
			System.out.println(githubIssue.title());
		}
	}
	
	public static void main(String[] args) {
		new ReleaseNotesRepository().atualizaLista();
	}
}
