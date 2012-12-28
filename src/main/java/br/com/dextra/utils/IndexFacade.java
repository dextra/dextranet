package br.com.dextra.utils;

import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.SearchServiceFactory;

public class IndexFacade {

	public static Index getIndex(String index) {
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(index).build();
		return SearchServiceFactory.getSearchService().getIndex(indexSpec);
	}
}
