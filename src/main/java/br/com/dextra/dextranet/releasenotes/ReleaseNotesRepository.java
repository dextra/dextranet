package br.com.dextra.dextranet.releasenotes;

import java.util.List;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import br.com.http.RequestParameters;
import br.com.repositories.GithubIssue;
import br.com.repositories.GithubRepositoryClient;

public class ReleaseNotesRepository {

	private static final String CACHE_KEY = "release-notes";

	public void atualizaLista() {
		MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService();
		memcacheService.delete(CACHE_KEY);
	}

	public ReleaseNotes getNotes() {
		MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService();
		ReleaseNotes notes = (ReleaseNotes) memcacheService.get(CACHE_KEY);

		if (notes == null) {
			notes = new ReleaseNotes();

			GithubRepositoryClient repositoryClient = new GithubRepositoryClient(null);

			RequestParameters parameters = new RequestParameters();
			parameters.add("state", "closed");

			List<GithubIssue> issues = repositoryClient.findRepositoryIssues("dextra", "dextranet", parameters);

			for (GithubIssue githubIssue : issues) {
				String title = githubIssue.title();
				String link = githubIssue.getJsonData().get("url").getAsString();
				String date = githubIssue.getJsonData().get("closed_at").getAsString();
				notes.add(title, link, date);
			}

			memcacheService.put(CACHE_KEY, notes);
		}

		return notes;
	}
}
