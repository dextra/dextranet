package br.com.dextra.repository.post;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.com.dextra.persistencia.PostFields;
import br.com.dextra.repository.document.DocumentRepository;
import br.com.dextra.utils.EntityJsonConverter;
import br.com.dextra.utils.IndexFacade;
import br.com.dextra.utils.IndexKeys;
import br.com.dextra.utils.Utils;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortOptions;
import com.google.appengine.api.search.Index;

public class PostRepository extends BaseRepository {

	public static Iterable<Entity> buscarTodosOsPosts(int maxResults, int offSet) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query(IndexKeys.POST.getKey());

		query.addSort(PostFields.DATA_DE_ATUALIZACAO.getField(),
				SortDirection.DESCENDING);
		PreparedQuery prepared = datastore.prepare(query);

		FetchOptions opts = FetchOptions.Builder.withDefaults();
		opts.limit(maxResults);
		opts.offset(offSet);

		return prepared.asIterable(opts);
	}

	public static Iterable<Entity> buscarPosts(int maxResults, String q,
			int offset) throws EntityNotFoundException {

		ArrayList<String> listaDeIds = buscaIdsPostsFTS(maxResults, q, offset);

		ArrayList<Entity> listaResults = buscaEntitiesPost(listaDeIds);

		return listaResults;
	}

	private static ArrayList<Entity> buscaEntitiesPost(
			ArrayList<String> listaDeIds) throws EntityNotFoundException {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		ArrayList<Entity> listaResults = new ArrayList<Entity>();

		for (String id : listaDeIds) {
			Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);
			Entity e = datastore.get(key);
			listaResults.add(e);
		}
		return listaResults;
	}

	private static ArrayList<String> buscaIdsPostsFTS(int maxResults, String q,
			int offset) {

		com.google.appengine.api.search.Query query = preparaQuery(
				q);

		ArrayList<String> listaDeIds = EntityJsonConverter
				.toListaDeIds(IndexFacade.getIndex(IndexKeys.POST.getKey())
						.search(query));

		// FIXME: Set limit dentro da query nao funciona
		Collections.reverse(listaDeIds);
		ArrayList<String> arrayTemp = new ArrayList<String>();
		int f = maxResults + offset;

		if (maxResults + offset > listaDeIds.size())
			f = listaDeIds.size();
		listaDeIds.subList(offset, f);

		for (String string : listaDeIds) {
			arrayTemp.add(string);
		}
		listaDeIds = arrayTemp;

		return listaDeIds;
	}

	private static com.google.appengine.api.search.Query preparaQuery(
			String q) {

		SortOptions sortOptions = SortOptions.newBuilder().addSortExpression(
				SortExpression.newBuilder().setExpression(
						PostFields.DATA_DE_ATUALIZACAO.getField())
						.setDirection(SortExpression.SortDirection.ASCENDING)
						.setDefaultValueNumeric(0.0)).build();

		QueryOptions queryOptions = QueryOptions.newBuilder().setSortOptions(
				sortOptions).setFieldsToReturn(PostFields.ID.getField(),
				PostFields.DATA_DE_ATUALIZACAO.getField(),PostFields.TITULO.getField()).build();

		com.google.appengine.api.search.Query query = com.google.appengine.api.search.Query
				.newBuilder().setOptions(queryOptions).build(q);
		return query;
	}

	public Entity criaNovoPost(String titulo, String conteudo, String usuario) {

		String id = Utils.geraID();
		Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);
		Date data = new Date();

		return PostRepository.criaNovoPost(titulo, conteudo, usuario, id, key,
				data);
	}

	public static Entity criaNovoPost(String titulo, String conteudo,
			String usuario, String id, Key key, Date data) {

		// data = Utils.randomizaDiaDaData(data);
		String dataFormatada = Utils.formataData(data.toString());

		Entity valueEntity = criaEntityPost(titulo, conteudo, usuario, id, key,
				dataFormatada);
		persist(valueEntity);

		DocumentRepository.criarDocumentPost(titulo, conteudo, usuario, id,
				dataFormatada);

		return valueEntity;
	}

	private static Entity criaEntityPost(String titulo, String conteudo,
			String usuario, String id, Key key, String data) {
		Entity valueEntity = new Entity(key);
		valueEntity.setProperty(PostFields.ID.getField(), id);
		valueEntity.setProperty(PostFields.TITULO.getField(), titulo);

		valueEntity.setProperty(PostFields.CONTEUDO.getField(), new Text(
				conteudo));
		valueEntity.setProperty(PostFields.USUARIO.getField(), usuario);
		valueEntity.setProperty(PostFields.COMENTARIO.getField(), 0);
		valueEntity.setProperty(PostFields.LIKES.getField(), 0);
		valueEntity.setProperty(PostFields.DATA.getField(), data);
		valueEntity
				.setProperty(PostFields.DATA_DE_ATUALIZACAO.getField(), data);

		return valueEntity;
	}

}
